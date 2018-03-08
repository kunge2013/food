package com.cherry.shop.product.dto.api;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * 查询产品列表Dto
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@Data
public class ListProductDto {

	private String id;// 产品ID
	
	private String name;// 产品名称
	
	private String imageUrl;// 产品图片地址
	
	private BigDecimal minPrice;// 最小规格单价
	
	private BigDecimal maxPrice;// 最大规格单价
	
	private BigDecimal minMeasurePrice;// 最小计量单价
	
	private BigDecimal maxMeasurePrice;// 最大计量单位
	
	private String unit;// 规格单位
	
	private Integer stock;// 规格库存
	
	private String measureUnit;// 计量单位
	
	private List<ListProductSpecDto> specs;// 产品规格列表
	
}
