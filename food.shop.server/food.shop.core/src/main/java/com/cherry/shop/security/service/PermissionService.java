package com.cherry.shop.security.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.security.dto.PermissionDto;
import com.cherry.shop.security.entity.Permission;

/**
 * 权限Service接口
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
public interface PermissionService extends IService<Permission> {

	/**
	 * 查询整个权限树
	 * 
	 * @return 整个权限树
	 */
	List<PermissionDto> getPermissionTree();

}
