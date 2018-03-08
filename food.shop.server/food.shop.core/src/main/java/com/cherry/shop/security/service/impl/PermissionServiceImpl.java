package com.cherry.shop.security.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.framework.utils.TreeUtils;
import com.cherry.shop.security.dto.PermissionDto;
import com.cherry.shop.security.entity.Permission;
import com.cherry.shop.security.mapper.PermissionMapper;
import com.cherry.shop.security.service.PermissionService;

/**
 * 权限Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

	@SuppressWarnings("unchecked")
	@Override
	public List<PermissionDto> getPermissionTree() {
		// 查询所有的权限列表
		List<Permission> permissions = this.selectList(
			SQLHelper.build(Permission.class)
					 .orderBy("order", true)
					 .geEntityWrapper()
		);
		
		// 转换为DTO
		List<PermissionDto> permDtos = permissions.stream().map(p -> {
			PermissionDto dto = new PermissionDto();
			BeanUtils.copyProperties(p, dto);
			return dto;
		}).collect(toList());
		
		// 转换为树结构
		return (List<PermissionDto>) TreeUtils.toTree(permDtos);
	}

}
