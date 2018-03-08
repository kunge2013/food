package com.cherry.shop.security.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.security.entity.SysUserRole;

/**
 * 系统用户与角色关系Service接口
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
public interface SysUserRoleService extends IService<SysUserRole> {

	/**
	 * 查询系统用户拥有的角色ID列表
	 * 
	 * @param sysUserId
	 *            系统用户ID
	 * @return 系统用户拥有的角色ID列表
	 */
	List<String> listRoleIdsBySysUserId(String sysUserId);

	/**
	 * 删除指定系统用户的角色关系
	 * 
	 * @param sysUserId
	 *            系统用户ID
	 */
	void deleteByUserId(String sysUserId);

}
