package com.cherry.shop.security.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.security.entity.RolePermission;
import com.cherry.shop.security.mapper.RolePermissionMapper;
import com.cherry.shop.security.service.RolePermissionService;

/**
 * 角色权限关系Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

	@Override
	public void deleteByRoleId(String roleId) {
		this.delete(getRoleIdWrapper(roleId));
	}

	@Override
	public List<String> listPermissionIdsByRoleId(String roleId) {
		// 查询指定角色的权限信息列表
		List<RolePermission> list = this.selectList(getRoleIdWrapper(roleId));
		// 提取角色的权限ID列表
		return list.stream().map(RolePermission::getPermissionId).collect(toList());
	}
	
	// 获取roleId等值条件包装器
	private static Wrapper<RolePermission> getRoleIdWrapper(String roleId) {
		return SQLHelper.build(RolePermission.class)
		 		 .eq("roleId", roleId)
		 		 .geEntityWrapper();
	}

	@Override
	public List<String> listPermissionIdsByRoleIds(List<String> roleIds) {
		return this.selectList(// 查询角色权限关系列表
			SQLHelper.build(RolePermission.class)
		 		 	 .in("roleId", roleIds)
		 		     .geEntityWrapper()
			).parallelStream()// 开始流处理
			 .map(RolePermission::getPermissionId)// 提取权限ID
			 .distinct()// 对权限ID去重
			 .collect(toList());// 收集权限ID
	}

}
