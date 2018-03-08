package com.cherry.shop.product.service.impl;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.EntityConvertor;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.framework.utils.StringUtil;
import com.cherry.shop.product.dto.ProductDto;
import com.cherry.shop.product.dto.api.ListProductDto;
import com.cherry.shop.product.dto.api.ListProductSpecDto;
import com.cherry.shop.product.dto.api.ProductPriceDto;
import com.cherry.shop.product.entity.Product;
import com.cherry.shop.product.entity.ProductSpec;
import com.cherry.shop.product.entity.ProductSpecParam;
import com.cherry.shop.product.entity.ProductType;
import com.cherry.shop.product.mapper.ProductMapper;
import com.cherry.shop.product.param.AddProductParam;
import com.cherry.shop.product.param.ListProductParam;
import com.cherry.shop.product.param.UpdateProductParam;
import com.cherry.shop.product.param.api.ListProductPriceParam;
import com.cherry.shop.product.service.ProductService;
import com.cherry.shop.product.service.ProductSpecParamService;
import com.cherry.shop.product.service.ProductSpecService;
import com.cherry.shop.product.service.ProductTypeService;
import com.cherry.shop.system.service.AttaService;

/**
 * 产品Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月12日
 * @version 1.0
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

	@Autowired
	private ProductTypeService productTypeService;
	
	@Autowired
	private ProductSpecService productSpecService;
	
	@Autowired
	private ProductSpecParamService productSpecParamService;
	
	@Autowired
	private AttaService attaService;
	
	@Override
	public Page<ProductDto> listProduct(Page<Product> page, ListProductParam params) {
		// 查询指定类型的所有子孙类型和自己的ID
		List<String> typeIds = productTypeService.listTypeIdByParentAndSelf(params.getType());
		
		// 查询产品列表
		Page<Product> products = this.selectPage(page, 
			SQLHelper.build(Product.class)
					 .like("name", params.getName())
					 .in("type", typeIds)
					 .eq("is_sale", params.getSale())
					 .eq("is_promotion", params.getPromotion())
					 .geEntityWrapper()
		);
		
		// 1、如果上面的typeIds列表有值，则证明使用了type作为过滤条件，即查到的产品类型都在typeIds中
		typeIds = typeIds.size() > 0 ? typeIds : 
			// 2、如果上面的typeIds列表为空，则证明没有使用type作为过滤条件，即需要重新过滤出产品分类
			products.getRecords().stream().map(Product::getType).distinct().collect(toList());
		
		// 分类ID->分类映射关系
		Map<String, ProductType> productTypeMapper = productTypeService.getProductTypeMapper(typeIds);
		
		// 转换Dto
		return EntityConvertor.convertPage(products, p -> {
			ProductDto dto = new ProductDto();
			BeanUtils.copyProperties(p, dto);
			if (StringUtils.isNotBlank(dto.getType())) {
				ProductType type = productTypeMapper.get(dto.getType());
				if (type != null) {
					dto.setTypeName(type.getName());
				}
			}
			return dto;
		});
	}

	@Override
	public ProductDto getProduct(String productId) {
		// 创建响应对象
		ProductDto dto = new ProductDto();
		// 查询产品信息
		Product product = this.selectById(productId);
		// 复制属性
		BeanUtils.copyProperties(product, dto);
		
		// 查询产品图片附件
		List<String> imageUrls = attaService.listAttaUrls(productId, AttaService.ATTA_TYPE_PRODUCT);
		dto.setImageUrls(imageUrls.toArray(new String[imageUrls.size()]));
		
		// 规格
		List<ProductSpec> specs = productSpecService.listProductSpec(productId);
		dto.setSpecs(specs);
		
		// 规格参数
		List<ProductSpecParam> param = productSpecParamService.listProductSpecParam(productId);
		dto.setParams(param);
		return dto;
	}

	@Override
	public void addProduct(AddProductParam params) {
		// 保存产品信息
		Product product = new Product();
		BeanUtils.copyProperties(params, product);
		product.setImageUrl(params.getImageUrls()[0]);
		product.setCreateTime(new Date());
		this.insert(product);
		
		// 保存产品图片附件
		attaService.insertAttas(product.getId(), AttaService.ATTA_TYPE_PRODUCT, params.getImageUrls());
		
		// 保存产品规格
		productSpecService.saveProductSpecs(product.getId(), product.getType(), params.getSpecs());
		
		// 保存产品规格属性
		productSpecParamService.saveProductSpecParams(product.getId(), params.getSpecParams());
	}

	@Override
	public void updateProduct(UpdateProductParam params) {
		// 保存产品信息
		Product product = new Product();
		BeanUtils.copyProperties(params, product);
		product.setImageUrl(params.getImageUrls()[0]);
		this.updateById(product);
		
		// 保存产品图片附件
		attaService.insertOrOverrideAttas(product.getId(), AttaService.ATTA_TYPE_PRODUCT, params.getImageUrls());
		
		// 保存产品规格
		productSpecService.saveProductSpecs(product.getId(), product.getType(), params.getSpecs());
		
		// 保存产品规格属性
		productSpecParamService.saveProductSpecParams(product.getId(), params.getSpecParams());
	}

	@Override
	public void deleteProducts(List<String> productIds) {
		if (productIds == null || productIds.size() == 0) {
			return;
		}
		
		// 删除产品
		this.deleteBatchIds(productIds);
		
		// 删除产品附件
		productIds.forEach(productId -> {
			attaService.deleteAttas(productId, AttaService.ATTA_TYPE_PRODUCT);
		});
		
		// 删除产品规格
		productSpecService.deleteProductSpecs(productIds);
		
		// 删除产品规格参数
		productSpecParamService.deleteProductSpecParams(productIds);
	}

	@Override
	public void deleteProductsByType(List<String> typeIds) {
		// 查询分类下的产品ID列表
		List<String> productIds = this.selectList(
			SQLHelper.build(Product.class)
					 .in("type", typeIds)
					 .geEntityWrapper()
		).stream().map(Product::getId).collect(toList());
		
		// 批量删除产品
		deleteProducts(productIds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<ProductPriceDto> listProductPrice(ListProductPriceParam params) {
		// 分页查询规格
		Page<ProductSpec> specs = productSpecService.listProductSpec(params);
		// 提取产品ID列表
		List<String> productIds = EntityConvertor.extractPropertyList(specs, ProductSpec::getProductId);
		
		// 查询产品
		List<Product> products = this.selectList( 
			SQLHelper.build(Product.class)
					 .in("id", productIds)
					 .geEntityWrapper()
		);
		
		// 按产品ID对产品规格进行分组
		Map<String, Product> productMapper = (Map<String, Product>) EntityConvertor.convertListToMap(products);
		
		// 转换DTO
		return EntityConvertor.convertPage(specs, s -> {
			// 创建DTO对象
			ProductPriceDto dto = new ProductPriceDto();
			// 复制属性
			BeanUtils.copyProperties(s, dto);
			// 获取对应的产品信息
			Product p = productMapper.get(s.getProductId());
			// 复制产品信息
			BeanUtils.copyProperties(p, dto);
			// 设置ID
			dto.setProductId(s.getProductId());
			dto.setSpecId(s.getId());
			dto.setIntro(StringUtil.getStringDigest(dto.getIntro(), 10));
			return dto;
		});
	}

	@Override
	public Page<ListProductDto> listProduct(com.cherry.shop.product.param.api.ListProductParam params) {
		// 查询产品列表
		Page<Product> products = this.selectPage(
			params.toPage(Product.class), 
			SQLHelper.build(Product.class)
					 .mustEq("type", params.getTypeId())
					 .isTrue("promotion", params.isPromotion())
					 .likeOr(params.getSearch(), "name", "intro")
					 .orderBy(params.getOrderByField(), params.isAsc())
					 .geEntityWrapper()
		);
		
		// 提取产品ID列表
		List<String> productIds = EntityConvertor.extractPropertyList(products, Product::getId);
		
		// 通过产品ID关联产品规格列表
		List<ProductSpec> specs = productSpecService.listProductSpec(productIds.toArray(new String[productIds.size()]));
		
		// 将产品规格按产品ID分组
		Map<String, List<ProductSpec>> mapper = EntityConvertor.groupBy(specs, ProductSpec::getProductId);
		
		// 组装Dto
		return EntityConvertor.convertPage(products, p -> {
			// 创建产品Dto
			ListProductDto dto = new ListProductDto();
			// 复制属性
			BeanUtils.copyProperties(p, dto);
			// 获取产品ID
			String productId = p.getId();
			
			// 获取指定产品的规格列表
			List<ProductSpec> specList = mapper.get(productId);
			
			// 产品规格单价比较器
			Comparator<ProductSpec> comparator = (p1, p2) -> p1.getPrice().compareTo(p2.getPrice());
			// 获取最低单价的规格
			ProductSpec minSpec = specList.stream().min(comparator).get();
			dto.setUnit(minSpec.getUnit());
			// 只有一个规格，库存显示在产品上
			if (specList.size() == 1) {
				dto.setStock(specList.get(0).getStock());
			}
			dto.setMeasureUnit(minSpec.getMeasureUnit());
			// 计算最低单价
			BigDecimal minPrice = minSpec.getPrice();
			dto.setMinPrice(minPrice);
			// 计算最高单价
			BigDecimal maxPrice = specList.stream().max(comparator).get().getPrice();
			dto.setMaxPrice(maxPrice);
			
			// 产品规格计量单价比较器
			comparator = (p1, p2) -> p1.getMeasurePrice().compareTo(p2.getMeasurePrice());
			// 计算最低计量单价
			BigDecimal minMeasurePrice = specList.stream().min(comparator).get().getMeasurePrice();
			dto.setMinMeasurePrice(minMeasurePrice);
			// 计算最高计量单价
			BigDecimal maxMeasurePrice = specList.stream().max(comparator).get().getMeasurePrice();
			dto.setMaxMeasurePrice(maxMeasurePrice);
			
			// 转换产品规格Dto
			List<ListProductSpecDto> specDtos = EntityConvertor.convertList(specList, s -> {
				ListProductSpecDto specDto = new ListProductSpecDto();
				BeanUtils.copyProperties(s, specDto);
				return specDto;
			});
			dto.setSpecs(specDtos);
			
			return dto;
		});
	}

	@Override
	public Product getProductBySpecId(String specId) {
		ProductSpec spec = productSpecService.selectOne(
			SQLHelper.build(ProductSpec.class)
					 .mustEq("id", specId)
					 .geEntityWrapper()
		);
		
		return this.selectOne(
			SQLHelper.build(Product.class)
					 .mustEq("id", spec.getProductId())
					 .geEntityWrapper());
	}
	
}
