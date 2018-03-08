package com.cherry.shop.product.dto.api;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 购物车条目Dto
 * 
 * @author 赵凡
 * @since 2018年1月21日
 * @version 1.0
 */
@Data
public class CartItemDto {

	private String id;// 条目ID
	
	private String productId;// 产品ID
	
	private String productName;// 产品名称
	
	private String productImage;// 产品图片
	
	private String specId;// 规格ID
	
	private String measureUnit;// 计量单位
	
	private BigDecimal measurePrice;// 计量单价
	
	private String unit;// 规格单位
	
	private BigDecimal price;// 规格单价
	
	private Integer quantity;// 规格数量
	
	private BigDecimal amount;// 条目小计
	
}
