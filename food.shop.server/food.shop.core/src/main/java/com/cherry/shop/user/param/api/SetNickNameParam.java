package com.cherry.shop.user.param.api;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 设置昵称请求参数
 * 
 * @author 赵凡
 * @since 2018年1月20日
 * @version 1.0
 */
@Data
public class SetNickNameParam {

	@NotNull
	private String nickName;// 昵称
	
}
