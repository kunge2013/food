package com.cherry.shop.security.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.dto.OptionDto;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.security.dto.PermissionDto;
import com.cherry.shop.security.dto.RolePermissionsDto;
import com.cherry.shop.security.entity.Role;
import com.cherry.shop.security.entity.RolePermission;
import com.cherry.shop.security.mapper.RoleMapper;
import com.cherry.shop.security.param.AddRoleParam;
import com.cherry.shop.security.param.ListRoleParam;
import com.cherry.shop.security.param.SetRolePermissionsParam;
import com.cherry.shop.security.param.UpdateRoleParam;
import com.cherry.shop.security.service.PermissionService;
import com.cherry.shop.security.service.RolePermissionService;
import com.cherry.shop.security.service.RoleService;

/**
 * 角色Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public List<Role> listRole(ListRoleParam params) {
		return this.selectList(
				SQLHelper.build(Role.class)
				 		 .like("name", params.getName())
				 		 .orderBy("createTime", params.isAsc())
				 		 .geEntityWrapper()
				);
	}

	@Override
	public void addRole(AddRoleParam params) {
		Role role = new Role();
		role.setName(params.getName());
		role.setCreateTime(new Date());
		
		this.insert(role);
	}

	@Override
	public void updateRole(UpdateRoleParam params) {
		Role role = new Role();
		BeanUtils.copyProperties(params, role);
		
		this.updateById(role);
	}

	@Override
	public void deleteRoleByIds(List<String> roleIds) {
		// 批量删除角色
		this.deleteBatchIds(roleIds);
		
		// 删除角色和权限的关联关系
		roleIds.forEach(roleId -> rolePermissionService.deleteByRoleId(roleId));
	}

	@Override
	public Role getRole(String roleId) {
		return this.selectById(roleId);
	}

	@Override
	public List<OptionDto> listRoleOptions() {
		// 查询系统所有角色
		ListRoleParam params = new ListRoleParam();
		params.setAsc(false);
		List<Role> roles = listRole(params);
		
		// 转换为DTO
		return roles.stream().map(r -> {
			OptionDto option = new OptionDto();
			option.setDisabled(false);
			option.setValue(r.getId());
			option.setLabel(r.getName());
			return option;
		}).collect(toList());
	}

	@Override
	public void setRolePermissions(SetRolePermissionsParam params) {
		// 清空当前角色的权限列表
		String roleId = params.getId();
		rolePermissionService.deleteByRoleId(roleId);
		
		// 重建角色与权限的关系
		String[] permissionIds = params.getPermissionIds();
		if (ArrayUtils.isNotEmpty(permissionIds)) {
			List<RolePermission> rolePermissions = new ArrayList<>();
			for (String permissionId : permissionIds) {
				RolePermission rolePermission = new RolePermission();
				rolePermission.setRoleId(roleId);
				rolePermission.setPermissionId(permissionId);
				rolePermissions.add(rolePermission);
			}
			rolePermissionService.insertBatch(rolePermissions);
		}
	}

	@Override
	public RolePermissionsDto getRolePermissions(String roleId) {
		// 创建响应对象
		RolePermissionsDto dto = new RolePermissionsDto();
		
		// 查询角色信息
		Role role = this.selectById(roleId);
		BeanUtils.copyProperties(role, dto);
		
		// 查询角色拥有的权限ID列表
		List<String> permissionIds = rolePermissionService.listPermissionIdsByRoleId(roleId);
		dto.setPermissionIds(permissionIds);
		
		// 查询权限树
		List<PermissionDto> permissionTreeData = permissionService.getPermissionTree();
		dto.setPermissionTreeData(permissionTreeData);
		
		return dto;
	}

}
