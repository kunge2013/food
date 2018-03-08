package com.cherry.shop.product.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.product.dto.ProductDto;
import com.cherry.shop.product.dto.api.ListProductDto;
import com.cherry.shop.product.dto.api.ProductPriceDto;
import com.cherry.shop.product.entity.Product;
import com.cherry.shop.product.param.AddProductParam;
import com.cherry.shop.product.param.ListProductParam;
import com.cherry.shop.product.param.UpdateProductParam;
import com.cherry.shop.product.param.api.ListProductPriceParam;

/**
 * 产品Service接口
 * 
 * @author 赵凡
 * @since 2018年1月12日
 * @version 1.0
 */
public interface ProductService extends IService<Product> {

	/**
	 * 查询产品列表
	 * 
	 * @param page
	 *            分页参数
	 * @param params
	 *            过滤参数
	 * @return 产品列表
	 */
	Page<ProductDto> listProduct(Page<Product> page, ListProductParam params);

	/**
	 * 通过产品ID查询产品信息
	 * 
	 * @param productId
	 *            产品ID
	 * @return 产品信息
	 */
	ProductDto getProduct(String productId);

	/**
	 * 添加产品
	 * 
	 * @param params
	 *            产品信息
	 */
	void addProduct(AddProductParam params);

	/**
	 * 更新产品
	 * 
	 * @param params
	 *            产品信息
	 */
	void updateProduct(UpdateProductParam params);

	/**
	 * 批量删除产品
	 * 
	 * @param productIds
	 *            产品ID列表
	 */
	void deleteProducts(List<String> productIds);

	/**
	 * 通过分类批量删除产品
	 * 
	 * @param typeIds
	 *            分类ID列表
	 */
	void deleteProductsByType(List<String> typeIds);

	/**
	 * 查询产品当前价格列表
	 * 
	 * @param params
	 *            查询参数
	 * @return 产品当前价格列表
	 */
	Page<ProductPriceDto> listProductPrice(ListProductPriceParam params);

	/**
	 * 查询产品列表
	 * 
	 * @param params
	 *            查询参数
	 * @return 产品列表
	 */
	Page<ListProductDto> listProduct(com.cherry.shop.product.param.api.ListProductParam params);

	/**
	 * 通过规格ID查询产品信息
	 * 
	 * @param specId
	 *            规格ID
	 * @return 产品信息
	 */
	Product getProductBySpecId(String specId);

}
