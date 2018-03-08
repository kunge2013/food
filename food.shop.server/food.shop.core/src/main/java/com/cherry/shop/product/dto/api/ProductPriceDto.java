package com.cherry.shop.product.dto.api;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 产品价格Dto
 * 
 * @author 赵凡
 * @since 2018年1月16日
 * @version 1.0
 */
@Data
public class ProductPriceDto {

	private String productId;// 产品ID
	
	private String imageUrl;// 产品图片
	
	private String name;// 产品名称
	
	private String intro;// 产品介绍
	
	private String specId;// 规格ID
	
	private String unit;// 规格单位
	
	private BigDecimal price;// 规格单价
	
	private String measureUnit;// 计量单位
	
	private BigDecimal measurePrice;// 计量单价
	
}
