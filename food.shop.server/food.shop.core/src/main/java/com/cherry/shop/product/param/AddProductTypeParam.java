package com.cherry.shop.product.param;

import lombok.Data;

/**
 * 添加产品分类请求参数
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Data
public class AddProductTypeParam {

	private String id;// 产品分类ID
	
	private String name;// 产品类型名称
	
	private String parentId;// 父类型ID
	
	private String imageUrl;// 产品类型图标
	
	private boolean show;// 是否显示
	
	private boolean promotion;// 是否促销

	private Integer order;// 顺序
	
}
