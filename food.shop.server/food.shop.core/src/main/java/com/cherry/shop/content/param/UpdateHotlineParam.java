package com.cherry.shop.content.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 更新服务热线请求参数
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
@Data
public class UpdateHotlineParam {

	@NotNull
	private String id;// 服务热线ID
	
	@NotNull
	private String phone;// 电话号码
	
	@NotNull
	private Integer order;// 顺序
	
}
