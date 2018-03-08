package com.cherry.shop.product.param;

import lombok.Data;

/**
 * 查询产品列表请求参数
 * 
 * @author 赵凡
 * @since 2018年1月12日
 * @version 1.0
 */
@Data
public class ListProductParam {

	private String name;// 产品名称
	
	private String type;// 产品分类
	
	private Boolean sale;// 是否在售
	
	private Boolean promotion;// 是否促销
	
}
