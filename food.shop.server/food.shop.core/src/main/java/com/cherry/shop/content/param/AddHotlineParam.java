package com.cherry.shop.content.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 添加角色请求参数
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
@Data
public class AddHotlineParam {

	@NotNull
	private String phone;// 电话号码
	
	@NotNull
	private Integer order;// 顺序
	
}
