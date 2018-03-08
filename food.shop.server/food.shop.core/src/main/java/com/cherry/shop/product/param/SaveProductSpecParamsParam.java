package com.cherry.shop.product.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 保存产品规格参数请求参数
 * 
 * @author 赵凡
 * @since 2018年1月16日
 * @version 1.0
 */
@Data
public class SaveProductSpecParamsParam {

	@NotNull
	private String paramName;// 参数名
	
	@NotNull
	private String paramValue;// 参数值
	
}
