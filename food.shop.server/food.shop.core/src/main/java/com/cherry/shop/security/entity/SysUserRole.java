package com.cherry.shop.security.entity;

import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户与角色关系实体类
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8763809630563436895L;

	private String sysUserId;// 系统用户ID
	   
	private String roleId;// 角色ID
	
}
