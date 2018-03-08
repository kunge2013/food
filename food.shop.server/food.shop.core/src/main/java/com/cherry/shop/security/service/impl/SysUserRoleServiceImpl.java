package com.cherry.shop.security.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.security.entity.SysUserRole;
import com.cherry.shop.security.mapper.SysUserRoleMapper;
import com.cherry.shop.security.service.SysUserRoleService;

/**
 * 系统用户与角色关系Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

	@Override
	public List<String> listRoleIdsBySysUserId(String sysUserId) {
		// 查询指定系统用户的角色信息列表
		List<SysUserRole> list = this.selectList(getSysUserIdWrapper(sysUserId));
		// 提取系统用户的角色ID列表
		return list.stream().map(SysUserRole::getRoleId).collect(toList());
	}

	@Override
	public void deleteByUserId(String sysUserId) {
		this.delete(getSysUserIdWrapper(sysUserId));
	}
	
	// 获取sysUserId等值条件包装器
	private static Wrapper<SysUserRole> getSysUserIdWrapper(String sysUserId) {
		return SQLHelper.build(SysUserRole.class)
						.eq("sysUserId", sysUserId)
						.geEntityWrapper();
	}

}
