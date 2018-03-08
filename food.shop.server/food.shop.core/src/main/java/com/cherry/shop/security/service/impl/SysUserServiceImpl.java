package com.cherry.shop.security.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.dto.OptionDto;
import com.cherry.framework.param.ToggleStatusParam;
import com.cherry.framework.shiro.utils.EncryptUtils;
import com.cherry.framework.shiro.utils.ShiroUtils;
import com.cherry.framework.utils.Constants;
import com.cherry.framework.utils.EntityConvertor;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.framework.utils.TreeUtils;
import com.cherry.shop.security.dto.SysUserDto;
import com.cherry.shop.security.dto.SysUserMenuItemDto;
import com.cherry.shop.security.dto.SysUserRolesDto;
import com.cherry.shop.security.entity.Permission;
import com.cherry.shop.security.entity.SysUser;
import com.cherry.shop.security.entity.SysUserRole;
import com.cherry.shop.security.mapper.SysUserMapper;
import com.cherry.shop.security.param.AddSysUserParam;
import com.cherry.shop.security.param.ListSysUserParam;
import com.cherry.shop.security.param.SetSysUserRolesParam;
import com.cherry.shop.security.param.UpdateSysUserParam;
import com.cherry.shop.security.service.PermissionService;
import com.cherry.shop.security.service.RolePermissionService;
import com.cherry.shop.security.service.RoleService;
import com.cherry.shop.security.service.SysUserRoleService;
import com.cherry.shop.security.service.SysUserService;

/**
 * 系统用户Service实现类
 * 
 * @author 赵凡
 * @since 2017年12月28日
 * @version 1.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public Page<SysUserDto> listSysUser(Page<SysUser> page, ListSysUserParam params) {
		// 查询系统用户列表
		Page<SysUser> sysUsers = this.selectPage(page, 
			SQLHelper.build(SysUser.class)
					 .in("sex", params.getSex())
					 .in("status", params.getStatus())
					 .like("username", params.getUsername())
					 .like("realName", params.getRealName())
					 .like("telephone", params.getTelephone())
					 .like("email", params.getEmail())
					 .geEntityWrapper()
		);
		
		// 获取系统用户创建者映射关系
		final Map<String, SysUser> sysUserMap = getSysUserMapper(page, SysUser::getCreatorId);
		
		// 转换Page
		return EntityConvertor.convertPage(sysUsers, u -> {
			// 复制属性
			SysUserDto dto = new SysUserDto();
			BeanUtils.copyProperties(u, dto);
			// 获取系统用户的创建者
			SysUser sysUser = sysUserMap.get(dto.getCreatorId());
			if (sysUser != null) {
				// 设置创建者的用户名
				dto.setCreatorUsername(sysUser.getUsername());
			}
			return dto;
		});
	}

	@Override
	public SysUserDto getSysUser(String sysUserId) {
		SysUser sysUser = this.selectById(sysUserId);
		SysUserDto dto = new SysUserDto();
		BeanUtils.copyProperties(sysUser, dto);
		return dto;
	}
	
	@Override
	public SysUser getSysUserByUsername(String username) {
		return this.selectOne(
			SQLHelper.build(SysUser.class)
					 .eq("username", username)
					 .geEntityWrapper()
		);
	}

	@Override
	public SysUser getCurSysUser() {
		return this.selectById(getLoginSysUser().getId());
	}

	@Override
	public SysUser getLoginSysUser() {
		return ShiroUtils.getLoginSysUser();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Map<String, SysUser> getSysUserMapper(Page<T> page, Function<T, String> mapper) {
		// 提取系统用户ID列表
		List<String> sysUserIds = EntityConvertor.extractPropertyList(page, mapper);
		// 批量查询系统用户
		List<SysUser> creators = sysUserIds.size() > 0 ? this.selectBatchIds(sysUserIds) : Collections.EMPTY_LIST;
		// 将系统用户列表转换为系统用户ID-系统用户对象映射关系
		return (Map<String, SysUser>) EntityConvertor.convertListToMap(creators);
	}

	@Override
	public void deleteSysUserByIds(List<String> sysUserIds) {
		// 批量删除系统用户
		this.deleteBatchIds(sysUserIds);
		
		// 删除系统用户与角色的关联关系
		sysUserIds.forEach(sysUserId -> sysUserRoleService.deleteByUserId(sysUserId));
	}

	@Override
	public void addSysUser(AddSysUserParam params) {
		// 复制属性
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(params, sysUser);
		
		// 加密
		EncryptUtils.encrypt(sysUser);
		
		// 保存系统用户
		sysUser.setCreateTime(new Date());
		sysUser.setCreatorId(ShiroUtils.getLoginSysUserId());
		sysUser.setStatus(Constants.STATUS_ENABLE);
		this.insert(sysUser);
	}

	@Override
	public void updateSysUser(UpdateSysUserParam params) {
		// 复制属性
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(params, sysUser);
		
		// 更新系统用户信息
		this.updateById(sysUser);
	}

	@Override
	public void toggleStatus(ToggleStatusParam params) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(params, sysUser);
		
		this.updateById(sysUser);
	}

	@Override
	public SysUserRolesDto getSysUserRoles(String sysUserId) {
		// 创建响应对象
		SysUserRolesDto dto = new SysUserRolesDto();
		
		// 查询系统用户信息
		SysUser sysUser = this.selectById(sysUserId);
		BeanUtils.copyProperties(sysUser, dto);
		
		// 查询系统用户的角色ID列表
		List<String> roleIds = sysUserRoleService.listRoleIdsBySysUserId(sysUserId);
		dto.setRoleIds(roleIds);
		
		// 查询所有角色选项
		List<OptionDto> roleOptions = roleService.listRoleOptions();
		dto.setRoleOptions(roleOptions);
		
		return dto;
	}

	@Override
	public void setSysUserRoles(SetSysUserRolesParam params) {
		// 删除该系统用户的所有角色关系
		String sysUserId = params.getId();
		sysUserRoleService.deleteByUserId(sysUserId);
		
		// 重建该系统用户的所有角色关系
		String[] roleIds = params.getRoleIds();
		if (ArrayUtils.isNotEmpty(roleIds)) {
			List<SysUserRole> sysUserRoles = new ArrayList<>();
			for (String roleId : roleIds) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setSysUserId(sysUserId);
				sysUserRole.setRoleId(roleId);
				sysUserRoles.add(sysUserRole);
			}
			sysUserRoleService.insertBatch(sysUserRoles);
		}
	}

	@Override
	public void changePassword(String newPassword) {
		// 查询DB中最新的数据
		SysUser sysUser = this.getCurSysUser();
		// 设置新密码
		sysUser.setPassword(newPassword);
		// 加密
		EncryptUtils.encrypt(sysUser);
		// 更新密码和盐
		this.updateById(sysUser);
	}

	@Override
	public void resetPassword(String sysUserId) {
		// 通过系统用户ID查询最新系统用户信息
		SysUser sysUser = this.selectById(sysUserId);
		// 设置默认密码
		sysUser.setPassword("123456");
		// 加密
		EncryptUtils.encrypt(sysUser);
		// 更新加密信息
		this.updateById(sysUser);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserMenuItemDto> getMyMenuItems(String platform) {
		// 查询指定用户的角色ID列表
		List<String> roleIds = sysUserRoleService.listRoleIdsBySysUserId(ShiroUtils.getLoginSysUserId());
		// 查询当前系统用户所有角色合并的权限信息
		List<String> permissionIds = rolePermissionService.listPermissionIdsByRoleIds(roleIds);
		// 查询当前用户的所有权限信息列表
		List<Permission> permissions = permissionService.selectBatchIds(permissionIds);
		// 转换
		List<SysUserMenuItemDto> dtos = permissions.parallelStream()// 开始并行流处理
			   .filter(p -> p.isMenu() && p.getPlatform().equals(platform))// 过滤出菜单权限和特定平台权限
			   .sequential()// 开始顺序流处理
			   .sorted((p1, p2) -> p1.getOrder().compareTo(p2.getOrder()))// 按Order进行排序
			   .map(p -> {
				   // 转换为DTO
				   SysUserMenuItemDto dto = new SysUserMenuItemDto();
				   BeanUtils.copyProperties(p, dto);
				   dto.setId(p.getUrl() == null ? p.getId() : p.getUrl());
				   return dto;
			   }).collect(toList());// 收集
		// 转换为树结构
		return (List<SysUserMenuItemDto>) TreeUtils.toTree(dtos);
	}

}
