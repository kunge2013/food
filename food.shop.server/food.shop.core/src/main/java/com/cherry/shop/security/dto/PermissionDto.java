package com.cherry.shop.security.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cherry.framework.dto.TreeNode;

import lombok.Data;

/**
 * 权限DTO
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
@Data
public class PermissionDto implements TreeNode {

	private String id;// 权限ID
	
	private String name;// 权限名称
	
	private String code;// 权限编码
	
	private String url;// 权限URL
	
	private String icon;// 菜单图标
	
	private boolean menu;// 是否是菜单
	
	private String parentId;// 父权限ID
	
	private Integer order;// 顺序
	
	private String platform;// 平台：P、PC端；M、手机端；A、所有平台
	
	private List<PermissionDto> children = new ArrayList<>();// 子节点列表

	@Override
	public void setParentId(Serializable parentId) {
		this.parentId = (String) parentId;
	}

	@Override
	public String getText() {
		return this.getName();
	}

	@Override
	public void setText(String text) {
		this.name = text;
	}

	@Override
	public void addChildren(TreeNode children) {
		this.children.add((PermissionDto) children);
	}
	
}
