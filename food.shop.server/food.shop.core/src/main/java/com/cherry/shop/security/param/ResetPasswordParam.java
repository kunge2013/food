package com.cherry.shop.security.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 重置密码请求参数
 * 
 * @author 赵凡
 * @since 2018年1月8日
 * @version 1.0
 */
@Data
public class ResetPasswordParam {

	@NotNull
	private String sysUserId;// 系统用户ID
	
}
