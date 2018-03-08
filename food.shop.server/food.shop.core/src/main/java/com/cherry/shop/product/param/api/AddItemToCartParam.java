package com.cherry.shop.product.param.api;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 添加产品到购物车请求参数
 * 
 * @author 赵凡
 * @since 2018年1月21日
 * @version 1.0
 */
@Data
public class AddItemToCartParam {

	@NotNull
	private String specId;// 规格ID
	
	@NotNull
	private Integer quantity;// 规格数量
	
}
