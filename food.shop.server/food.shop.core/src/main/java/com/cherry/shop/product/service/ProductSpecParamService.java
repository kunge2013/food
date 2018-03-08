package com.cherry.shop.product.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.product.entity.ProductSpecParam;
import com.cherry.shop.product.param.SaveProductSpecParamsParam;

/**
 * 产品规格参数Service接口
 * 
 * @author 赵凡
 * @since 2018年1月16日
 * @version 1.0
 */
public interface ProductSpecParamService extends IService<ProductSpecParam> {

	/**
	 * 保存产品规格参数
	 * 
	 * @param productId
	 *            产品ID
	 * @param specParams
	 *            产品规格参数列表
	 */
	void saveProductSpecParams(String productId, List<SaveProductSpecParamsParam> specParams);

	/**
	 * 查询规格参数列表
	 * 
	 * @param productId
	 *            产品ID
	 * @return 规格参数列表
	 */
	List<ProductSpecParam> listProductSpecParam(String productId);

	/**
	 * 删除规格参数
	 * 
	 * @param productIds
	 *            产品ID列表
	 */
	void deleteProductSpecParams(List<String> productIds);

}
