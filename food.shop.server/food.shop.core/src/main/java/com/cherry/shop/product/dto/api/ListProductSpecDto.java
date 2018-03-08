package com.cherry.shop.product.dto.api;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 产品列表规格Dto
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@Data
public class ListProductSpecDto {

	private String id;// 产品规格ID
	
	private String unit;// 规格单位
	
	private Integer stock;// 规格库存
	
	private BigDecimal price;// 规格单价
	
	private String measureUnit;// 计量单位
	
	private BigDecimal measurePrice;// 计量单价
	
}
