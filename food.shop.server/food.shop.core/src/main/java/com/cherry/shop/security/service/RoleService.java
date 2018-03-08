package com.cherry.shop.security.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.framework.dto.OptionDto;
import com.cherry.shop.security.dto.RolePermissionsDto;
import com.cherry.shop.security.entity.Role;
import com.cherry.shop.security.param.AddRoleParam;
import com.cherry.shop.security.param.ListRoleParam;
import com.cherry.shop.security.param.SetRolePermissionsParam;
import com.cherry.shop.security.param.UpdateRoleParam;

/**
 * 角色Service接口
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
public interface RoleService extends IService<Role> {

	/**
	 * 查询角色列表
	 * 
	 * @param params
	 *            查询参数
	 * @return 角色列表
	 */
	List<Role> listRole(ListRoleParam params);

	/**
	 * 添加角色
	 * 
	 * @param params
	 *            添加参数
	 */
	void addRole(AddRoleParam params);

	/**
	 * 更新角色
	 * 
	 * @param params
	 *            更新参数
	 */
	void updateRole(UpdateRoleParam params);

	/**
	 * 批量删除角色
	 * 
	 * @param roleIds
	 *            要删除的角色ID列表
	 */
	void deleteRoleByIds(List<String> roleIds);

	/**
	 * 通过角色ID查询角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色
	 */
	Role getRole(String roleId);

	/**
	 * 查询角色选项
	 * 
	 * @return 
	 */
	List<OptionDto> listRoleOptions();

	/**
	 * 为角色分配权限
	 * 
	 * @param params
	 *            请求参数
	 */
	void setRolePermissions(SetRolePermissionsParam params);

	/**
	 * 查询指定角色拥有的权限信息
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 指定角色拥有的权限信息
	 */
	RolePermissionsDto getRolePermissions(String roleId);

}
