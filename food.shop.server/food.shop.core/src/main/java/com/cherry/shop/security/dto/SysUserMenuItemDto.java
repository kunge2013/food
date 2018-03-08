package com.cherry.shop.security.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cherry.framework.dto.TreeNode;
import lombok.Data;

/**
 * 系统用户菜单项DTO
 * 
 * @author 赵凡
 * @since 2018年1月8日
 * @version 1.0
 */
@Data
public class SysUserMenuItemDto implements TreeNode {

	private String id;// 权限ID
	
	private String name;// 菜单项名称
	
	private String icon;// 菜单项图标
	
	private String parentId;// 父权限ID
	
	private List<SysUserMenuItemDto> children = new ArrayList<SysUserMenuItemDto>();// 子菜单列表

	@Override
	public Serializable getParentId() {
		return parentId;
	}

	@Override
	public void setParentId(Serializable parentId) {
		this.parentId = (String) parentId;
	}

	@Override
	public String getText() {
		return this.name;
	}

	@Override
	public void setText(String text) {
		this.name = text;
	}

	@Override
	public void addChildren(TreeNode children) {
		this.children.add((SysUserMenuItemDto) children);
	}
	
}
