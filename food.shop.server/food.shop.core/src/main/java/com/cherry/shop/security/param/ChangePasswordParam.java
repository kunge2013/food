package com.cherry.shop.security.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 修改密码请求参数
 * 
 * @author 赵凡
 * @since 2018年1月8日
 * @version 1.0
 */
@Data
public class ChangePasswordParam {

	@NotNull
	private String password;// 密码
	
}
