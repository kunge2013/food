package com.cherry.shop.product.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.dto.Result;
import com.cherry.framework.utils.DateUtil;
import com.cherry.framework.utils.EntityConvertor;
import com.cherry.framework.utils.Results;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.product.dto.api.CartDto;
import com.cherry.shop.product.dto.api.CartItemDto;
import com.cherry.shop.product.entity.Cart;
import com.cherry.shop.product.entity.Product;
import com.cherry.shop.product.entity.ProductSpec;
import com.cherry.shop.product.mapper.CartMapper;
import com.cherry.shop.product.param.api.AddItemToCartParam;
import com.cherry.shop.product.service.CartService;
import com.cherry.shop.product.service.ProductService;
import com.cherry.shop.product.service.ProductSpecService;
import com.cherry.shop.system.dto.ConfigBlockDto;
import com.cherry.shop.system.param.Block;
import com.cherry.shop.system.param.ConfigName;
import com.cherry.shop.system.service.ConfigService;
import com.cherry.shop.user.entity.User;
import com.cherry.shop.user.service.UserService;

/**
 * 购物车Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月21日
 * @version 1.0
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

	@Autowired
	private ConfigService configService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductSpecService productSpecService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Result addItem(AddItemToCartParam param) {
		// 1、创建Dto
		CartDto dto = new CartDto();
		
		// 2、获取购物车配置块
		ConfigBlockDto block = configService.getConfigBlock(Block.CART);
		
		// 3、检查是否能下单
		String stopTime = block.getValue(ConfigName.STOP_TIME);
		dto.setStopTime(stopTime);
		// 超过下单时间
		if (DateUtil.compareCurLocalTime(stopTime) <= 0) {
			return Results.addOk(dto);
		}
		
		// 4、检查是否需要收服务费
		String serviceTime = block.getValue(ConfigName.SERVICE_TIME);
		// 收取服务费时间
		if (DateUtil.compareCurLocalTime(serviceTime) <= 0) {
			BigDecimal serviceFee = block.getBigDecimal(ConfigName.SERVICE_FEE);
			dto.setServiceFee(serviceFee);
		} else {
			dto.setServiceFee(new BigDecimal(0));
		}
		dto.setServiceTime(serviceTime);
		
		// 5、添加产品到购物车
		User user = userService.getCurUser();
		addItem(user, param);
		
		// 6、统计购物车产品
		List<CartItemDto> items = listMyCart(user);
		// 计算条目总价
		BigDecimal itemTotal = EntityConvertor.count(items, CartItemDto::getAmount);
		dto.setItemTotalAmount(itemTotal);
		dto.setItems(items);
		// 计算总价
		dto.setTotalAmount(dto.getItemTotalAmount().add(dto.getServiceFee()));
		
		// 7、是否满足最小订单
		BigDecimal minFee = block.getBigDecimal(ConfigName.MIN_ORDER_AMOUNT);
		dto.setMinFee(minFee);
		
		return Results.addOk(dto);
	}

	/**
	 * 添加产品到购物车
	 * 
	 * @param user
	 *            用户信息
	 * @param param
	 *            添加参数
	 */
	private void addItem(User user, AddItemToCartParam param) {
		// 检查个人购物车是否有指定条目
		Cart userCart = getUserCart(user.getId(), param.getSpecId());
		
		if (userCart == null) {// 个人购物车没有指定条目
			// 添加
			userCart = new Cart();
			
			// 通过规格查询产品信息
			Product product = productService.getProductBySpecId(param.getSpecId());
			userCart.setProductId(product.getId());
			userCart.setProductImage(product.getImageUrl());
			userCart.setProductName(product.getName());
			
			// 查询产品规格信息
			ProductSpec spec = productSpecService.selectById(param.getSpecId());
			BeanUtils.copyProperties(spec, userCart);
			userCart.setQuantity(param.getQuantity());
			userCart.setAmount(spec.getPrice().multiply(new BigDecimal(userCart.getQuantity())));// 计算条目小计
			userCart.setSpecId(param.getSpecId());
			userCart.setCreatorId(user.getId());
			userCart.setCreateTime(new Date());
			
			// 添加条目到个人购物车
			this.insert(userCart);
		} else {// 个人购物车有指定条目
			// 更新购物条目数量和条目小计
			userCart.setQuantity(userCart.getQuantity() + param.getQuantity());
			userCart.setAmount(userCart.getPrice().multiply(new BigDecimal(userCart.getQuantity())));// 计算条目小计
			this.updateById(userCart);
		}
		
		String companyId = user.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {// 指定用户已经绑定企业
			// 从企业购物车中查找指定条目
			Cart companyCart = getCompanyCart(companyId, param.getSpecId());
			
			if (companyCart != null) {// 企业购物车中有该条目
				// 合并
				companyCart.setQuantity(userCart.getQuantity() + companyCart.getQuantity());
				companyCart.setAmount(companyCart.getPrice().multiply(new BigDecimal(companyCart.getQuantity())));// 计算条目小计
			} else {
				companyCart = userCart;
				companyCart.setCompanyId(companyId);
			}
			
			// 升级个人购物车中的条目为企业购物车中的条目
			this.updateById(companyCart);
		}
	}

	@Override
	public Cart getCompanyCart(String companyId, String specId) {
		return this.selectOne(
			SQLHelper.build(Cart.class)
					 .mustEq("companyId", companyId)
					 .mustEq("specId", specId)
					 .geEntityWrapper()
		);
	}

	@Override
	public Cart getUserCart(String userId, String specId) {
		return this.selectOne(
				SQLHelper.build(Cart.class)
						 .mustEq("creatorId", userId)
						 .mustEq("specId", specId)
						 .geEntityWrapper()
			);
	}

	@Override
	public List<CartItemDto> listMyCart(User user) {
		SQLHelper<Cart> sqlHelper = SQLHelper.build(Cart.class);
		sqlHelper.mustEq("creatorId", user.getId());
		if (StringUtils.isNotBlank(user.getCompanyId())) {
			sqlHelper.or().mustEq("companyId", user.getCompanyId());
		}
		
		// 转换为Items
		List<Cart> list = this.selectList(sqlHelper.geEntityWrapper());
		return EntityConvertor.convertList(list, c -> {
			CartItemDto cartDto = new CartItemDto();
			BeanUtils.copyProperties(c, cartDto);
			return cartDto;
		});
	}

	@Override
	public Result deleteItem(List<String> itemIds) {
		this.deleteBatchIds(itemIds);
		
		// 1、创建Dto
		CartDto dto = new CartDto();
		
		// 2、获取购物车配置块
		ConfigBlockDto block = configService.getConfigBlock(Block.CART);
		
		// 3、检查是否能下单
		String stopTime = block.getValue(ConfigName.STOP_TIME);
		dto.setStopTime(stopTime);
		// 超过下单时间
		if (DateUtil.compareCurLocalTime(stopTime) >= 0) {
			return Results.delOk(dto);
		}
		
		// 4、检查是否需要收服务费
		String serviceTime = block.getValue(ConfigName.SERVICE_TIME);
		// 收取服务费时间
		if (DateUtil.compareCurLocalTime(serviceTime) >= 0) {
			BigDecimal serviceFee = block.getBigDecimal(ConfigName.SERVICE_FEE);
			dto.setServiceFee(serviceFee);
		}
		dto.setServiceTime(serviceTime);
		
		// 5、统计购物车产品
		User user = userService.getCurUser();
		List<CartItemDto> items = listMyCart(user);
		// 计算条目总价
		BigDecimal itemTotal = EntityConvertor.count(items, CartItemDto::getAmount);
		dto.setItemTotalAmount(itemTotal);
		dto.setItems(items);
		// 计算总价
		dto.setTotalAmount(dto.getItemTotalAmount().add(dto.getServiceFee()));
		
		// 6、是否满足最小订单
		BigDecimal minFee = block.getBigDecimal(ConfigName.MIN_ORDER_AMOUNT);
		dto.setMinFee(minFee);
		
		return Results.delOk(dto);
	}

}
