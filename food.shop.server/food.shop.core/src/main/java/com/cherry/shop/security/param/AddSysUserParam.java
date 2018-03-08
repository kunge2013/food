package com.cherry.shop.security.param;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 添加系统用户请求参数
 * 
 * @author 赵凡
 * @since 2018年1月2日
 * @version 1.0
 */
@Data
public class AddSysUserParam {

	@NotNull
	private String username;// 用户名
	
	private String image;// 头像
	
	@NotNull
	private String password;// 密码
	
	@NotNull
	private String realName;// 真实姓名
	
	private Date birthday;// 出生年月
	
	private String sex;// 性别：M、男；F、女；U、未知
	
	private String telephone;// 手机号
	
	private String email;// 邮箱地址
	
}
