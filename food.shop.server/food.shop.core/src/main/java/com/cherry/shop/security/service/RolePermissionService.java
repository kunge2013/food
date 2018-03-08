package com.cherry.shop.security.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.security.entity.RolePermission;

/**
 * 角色权限关系Service接口
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
public interface RolePermissionService extends IService<RolePermission> {

	/**
	 * 删除指定角色与权限的关联关系
	 * 
	 * @param roleId
	 *            角色ID
	 */
	void deleteByRoleId(String roleId);

	/**
	 * 查询角色拥有的权限ID列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色拥有的权限ID列表
	 */
	List<String> listPermissionIdsByRoleId(String roleId);

	/**
	 * 通过角色ID列表查询角色权限ID列表
	 * 
	 * @param roleIds
	 *            角色ID列表
	 * @return 角色ID列表查询角色权限ID列表
	 */
	List<String> listPermissionIdsByRoleIds(List<String> roleIds);

}
