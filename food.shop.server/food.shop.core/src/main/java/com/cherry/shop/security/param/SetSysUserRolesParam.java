package com.cherry.shop.security.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 设置系统用户角色请求参数
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
@Data
public class SetSysUserRolesParam {
	
	@NotNull
	private String id;// 系统用户ID
	
	private String[] roleIds;// 角色ID列表

}
