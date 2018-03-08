package com.cherry.shop.system.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cherry.framework.dto.TreeNode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 地址Dto
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@Data
public class AddrDto implements TreeNode, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4144740613661304156L;

	private String id;// 地址ID

	private String address;// 地址
	
	private String parentId;// 父级地址ID
	
	private Date createTime;// 创建时间
	
	private List<AddrDto> children = new ArrayList<>();// 子地址 
	
	@Override
	public Serializable getId() {
		return this.id;
	}

	@Override
	public Serializable getParentId() {
		return this.parentId;
	}

	@Override
	public void setParentId(Serializable parentId) {
		this.parentId = (String) parentId;
	}

	@JsonIgnore
	@Override
	public String getText() {
		return this.address;
	}

	@Override
	public void setText(String text) {
		this.address = text;
	}

	@Override
	public void addChildren(TreeNode children) {
		this.children.add((AddrDto) children);
	}

	@Override
	public List<? extends TreeNode> getChildren() {
		return this.children;
	}

}
