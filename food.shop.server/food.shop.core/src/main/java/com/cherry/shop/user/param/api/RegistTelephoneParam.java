package com.cherry.shop.user.param.api;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 手机注册请求参数
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@Data
public class RegistTelephoneParam {

	@NotNull
	private String telephone;// 手机号
	
	private String referrer;// 推荐人手机号
	
	@NotNull
	private String password;// 密码
	
	@NotNull
	private String code;// 验证码
	
}
