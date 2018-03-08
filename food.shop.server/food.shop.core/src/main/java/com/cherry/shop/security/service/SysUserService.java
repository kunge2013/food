package com.cherry.shop.security.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cherry.framework.param.ToggleStatusParam;
import com.cherry.shop.security.dto.SysUserDto;
import com.cherry.shop.security.dto.SysUserMenuItemDto;
import com.cherry.shop.security.dto.SysUserRolesDto;
import com.cherry.shop.security.entity.SysUser;
import com.cherry.shop.security.param.AddSysUserParam;
import com.cherry.shop.security.param.ListSysUserParam;
import com.cherry.shop.security.param.SetSysUserRolesParam;
import com.cherry.shop.security.param.UpdateSysUserParam;

/**
 * 系统用户Service接口
 * 
 * @author 赵凡
 * @since 2017年12月28日
 * @version 1.0
 */
public interface SysUserService extends IService<SysUser> {

	/**
	 * 查询系统用户列表
	 * 
	 * @param page
	 *            分页参数
	 * @param params
	 *            查询请求参数
	 * @return 系统用户列表
	 */
	Page<SysUserDto> listSysUser(Page<SysUser> page, ListSysUserParam params);
	
	/**
	 * 获取系统用户映射关系
	 * 
	 * @param page
	 *            包含系统用户ID的实体列表
	 * @param mapper
	 *            系统用户ID提取器
	 * @return 系统用户映射关系
	 */
	<T> Map<String, SysUser> getSysUserMapper(Page<T> page, Function<T, String> mapper);

	/**
	 * 批量删除系统用户
	 * 
	 * @param sysUserIds
	 *            系统用户ID
	 */
	void deleteSysUserByIds(List<String> sysUserIds);

	/**
	 * 添加系统用户
	 * 
	 * @param params
	 *            系统用户信息
	 */
	void addSysUser(AddSysUserParam params);

	/**
	 * 更新系统用户
	 * 
	 * @param params
	 *            系统用户信息
	 */
	void updateSysUser(UpdateSysUserParam params);

	/**
	 * 切换系统用户状态
	 * 
	 * @param params
	 *            切换参数
	 */
	void toggleStatus(ToggleStatusParam params);

	/**
	 * 查询系统用户
	 * 
	 * @param sysUserId
	 *            系统用户ID
	 * @return 系统用户
	 */
	SysUserDto getSysUser(String sysUserId);
	
	/**
	 * 通过用户名获取系统用户
	 * 
	 * @param username
	 *            用户名
	 * @return 系统用户
	 */
	SysUser getSysUserByUsername(String username);
	
	/**
	 * 获取当前登录的系统用户，数据库中最新的数据
	 * 
	 * @return 当前登录的系统用户，数据库中最新的数据
	 */
	SysUser getCurSysUser();
	
	/**
	 * 获取当前登录的系统用户，可能不是最新的数据
	 * 
	 * @return 当前登录的系统用户，可能不是最新的数据
	 */
	SysUser getLoginSysUser();

	/**
	 * 查询系统用户角色信息
	 * 
	 * @param sysUserId
	 *            系统用户ID
	 * @return 系统用户角色信息
	 */
	SysUserRolesDto getSysUserRoles(String sysUserId);

	/**
	 * 设置系统用户角色信息
	 * 
	 * @param params
	 *            请求参数
	 */
	void setSysUserRoles(SetSysUserRolesParam params);

	/**
	 * 修改密码
	 * 
	 * @param newPassword
	 *            新密码
	 */
	void changePassword(String newPassword);
	
	/**
	 * 查询当前用户所在平台的菜单项
	 * 
	 * @param platform
	 *            平台
	 * @return 当前用户所在平台的菜单项
	 */
	List<SysUserMenuItemDto> getMyMenuItems(String platform);

	/**
	 * 重置系统用户密码
	 * 
	 * @param sysUserId
	 *            系统用户Id
	 */
	void resetPassword(String sysUserId);
	
}
