package com.cherry.shop.security.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 角色的功能权限DTO
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
@Data
public class RolePermissionsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1235467768697517088L;

	private String id;// 角色ID
	
	private String name;// 角色名称
	
	private List<String> permissionIds;// 角色包含的权限ID列表
	
	private List<PermissionDto> permissionTreeData;// 权限数
	
}
