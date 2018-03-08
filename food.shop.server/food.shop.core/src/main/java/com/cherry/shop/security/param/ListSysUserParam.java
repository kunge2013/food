package com.cherry.shop.security.param;

import lombok.Data;

/**
 * 查询系统用户列表请求参数
 * 
 * @author 赵凡
 * @since 2017年12月28日
 * @version 1.0
 */
@Data
public class ListSysUserParam {

	private String[] sex;// 性别
	
	private String[] status;// 状态
	
	private String username;// 用户名
	
	private String realName;// 真实姓名
	
	private String telephone;// 手机号
	
	private String email;// 邮箱地址
	
}
