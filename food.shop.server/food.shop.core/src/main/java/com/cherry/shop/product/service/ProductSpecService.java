package com.cherry.shop.product.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.product.entity.ProductSpec;
import com.cherry.shop.product.param.SaveProductSpecParam;
import com.cherry.shop.product.param.api.ListProductPriceParam;

/**
 * 产品规格Service接口
 * 
 * @author 赵凡
 * @since 2018年1月15日
 * @version 1.0
 */
public interface ProductSpecService extends IService<ProductSpec> {

	/**
	 * 查询产品规格列表
	 * 
	 * @param params
	 *            查询参数
	 * @return 产品规格列表
	 */
	Page<ProductSpec> listProductSpec(ListProductPriceParam params);
	
	/**
	 * 查询规格参数列表
	 * 
	 * @param productIds
	 *            产品ID列表
	 * @return 规格参数列表
	 */
	List<ProductSpec> listProductSpec(String... productIds);

	/**
	 * 保存产品规格信息
	 * 
	 * @param productId
	 *            产品ID
	 * @param type
	 *            产品分类
	 * @param specs
	 *            产品规格列表
	 */
	void saveProductSpecs(String productId, String type, List<SaveProductSpecParam> specs);

	/**
	 * 删除产品规格
	 * 
	 * @param productIds
	 *            产品ID列表
	 */
	void deleteProductSpecs(List<String> productIds);

}
