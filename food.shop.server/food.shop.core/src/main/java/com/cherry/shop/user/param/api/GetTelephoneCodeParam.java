package com.cherry.shop.user.param.api;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 获取短信验证码请求参数
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@Data
public class GetTelephoneCodeParam {

	@NotNull
	private String telephone;// 手机号
	
}
