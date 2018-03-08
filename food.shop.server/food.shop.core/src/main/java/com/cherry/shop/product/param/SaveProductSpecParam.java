package com.cherry.shop.product.param;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 保存产品规格请求参数
 * 
 * @author 赵凡
 * @since 2018年1月16日
 * @version 1.0
 */
@Data
public class SaveProductSpecParam {
	
	@NotNull
	private String unit;// 规格单位
	
	@NotNull
	private BigDecimal price;// 规格单价
	
	@NotNull
	private Integer stock;// 库存，规格数量
	
	@NotNull
	private String measureUnit;// 计量单位
	
	@NotNull
	private BigDecimal measurePrice;// 计量单价
	
	private Integer measureStock;// 计量数量
	
	private boolean sale;// 是否销售
	
}
