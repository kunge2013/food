package com.cherry.shop.product.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.framework.param.ToggleSwitchParam;
import com.cherry.shop.product.dto.ProductTypeDto;
import com.cherry.shop.product.entity.ProductType;
import com.cherry.shop.product.param.AddProductTypeParam;
import com.cherry.shop.product.param.ListProductTypeParam;
import com.cherry.shop.product.param.UpdateProductTypeParam;

/**
 * 产品类型Service接口
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
public interface ProductTypeService extends IService<ProductType> {

	/**
	 * 查询产品类型列表
	 * 
	 * @param params
	 *            过滤参数
	 * @return 产品类型列表
	 */
	List<ProductTypeDto> listProductType(ListProductTypeParam params);
	
	/**
	 * 查询指定类型的所有子孙类型和自己的ID
	 * 
	 * @param typeId
	 *            类型ID
	 * @return 指定类型的所有子孙类型和自己的ID
	 */
	List<String> listTypeIdByParentAndSelf(String typeId);
	
	/**
	 * 查询叶子节点产品类型列表
	 * 
	 * @return 叶子节点产品类型列表
	 */
	List<ProductType> listLeafType();
	
	/**
	 * 通过产品分类ID列表查询产品分类列表
	 * 
	 * @param typeIds
	 *            产品分类ID列表
	 * @return 产品分类列表
	 */
	List<ProductType> listProductTypeByIds(List<String> typeIds);
	
	/**
	 * 通过父类ID查询产品类型列表
	 * 
	 * @param parentId
	 *            父类ID
	 * @return 产品类型列表
	 */
	List<ProductType> listProductTypeByParentId(String parentId);
	
	/**
	 * 查询产品分类ID与产品分类实体的映射关系
	 * 
	 * @param typeIds
	 *            需要查询的产品分类ID列表
	 * @return 产品分类ID与产品分类实体的映射关系
	 */
	Map<String, ProductType> getProductTypeMapper(List<String> typeIds);

	/**
	 * 查询产品分类
	 * 
	 * @param productTypeId
	 *            产品分类ID
	 * @return 产品分类
	 */
	ProductType getProductType(String productTypeId);

	/**
	 * 添加产品分类
	 * 
	 * @param params
	 *            产品分类信息
	 */
	void addProductType(AddProductTypeParam params);

	/**
	 * 更新产品分类
	 * 
	 * @param params
	 *            产品分类信息
	 */
	void updateProductType(UpdateProductTypeParam params);

	/**
	 * 批量删除产品分类
	 * 
	 * @param productTypeIds
	 *            产品分类ID列表
	 */
	void deleteProductTypeByIds(List<String> productTypeIds);

	/**
	 * 切换产品分类显示状态
	 * 
	 * @param params
	 *            切换参数
	 */
	void toggleShow(ToggleSwitchParam params);

	/**
	 * 切换产品分类促销状态
	 * 
	 * @param params
	 *            切换参数
	 */
	void togglePromotion(ToggleSwitchParam params);

}
