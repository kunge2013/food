package com.cherry.shop.security.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限实体类
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Permission extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6061841383957238344L;

	private String name;// 权限名称
	
	private String code;// 权限编码
	
	private String url;// 权限URL
	
	private String icon;// 菜单图标

	@TableField("is_menu")
	private boolean menu;// 是否是菜单
	
	private String parentId;// 父权限ID
	
	@TableField("`order`")
	private Integer order;// 顺序
	
	private String platform;// 平台：P、PC端；M、手机端；A、所有平台
	
}

