package com.cherry.shop.security.entity;

import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色与权限关系实体类
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RolePermission extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8230356431137722947L;

	private String roleId;// 角色ID
	   
	private String permissionId;// 权限ID
	
}
