package com.cherry.shop.user.param.api;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 设置出生日期
 * 
 * @author 赵凡
 * @since 2018年1月20日
 * @version 1.0
 */
@Data
public class SetBirthdayParam {

	@NotNull
	private Date birthday;
	
}
