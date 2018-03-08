package com.cherry.shop.product.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.framework.dto.Result;
import com.cherry.shop.product.dto.api.CartItemDto;
import com.cherry.shop.product.entity.Cart;
import com.cherry.shop.product.param.api.AddItemToCartParam;
import com.cherry.shop.user.entity.User;

/**
 * 购物车Service接口
 * 
 * @author 赵凡
 * @since 2018年1月21日
 * @version 1.0
 */
public interface CartService extends IService<Cart> {

	/**
	 * 添加条目到购物车
	 * 
	 * @param param
	 *            请求参数
	 * @return 请求结果
	 */
	Result addItem(AddItemToCartParam param);
	
	/**
	 * 获取企业购物车条目
	 * 
	 * @param companyId
	 *            企业ID
	 * @param specId
	 *            规格ID
	 * @return 企业购物车条目
	 */
	Cart getCompanyCart(String companyId, String specId);
	
	/**
	 * 获取个人购物车条目
	 * 
	 * @param userId
	 *            用户ID
	 * @param specId
	 *            规格ID
	 * @return 个人购物车条目
	 */
	Cart getUserCart(String userId, String specId);
	
	/**
	 * 查询我的购物车列表
	 * 
	 * @param user
	 *            用户信息
	 * @return 我的购物车列表
	 */
	List<CartItemDto> listMyCart(User user);

	/**
	 * 从购物车批量删除条目
	 * 
	 * @param itemIds
	 *            条目ID列表
	 */
	Result deleteItem(List<String> itemIds);
	
}
