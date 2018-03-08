package com.cherry.shop.security.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 设置角色的权限请求参数
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
@Data
public class SetRolePermissionsParam {

	@NotNull
	private String id;// 角色ID
	
	private String[] permissionIds;// 权限ID列表
	
}
