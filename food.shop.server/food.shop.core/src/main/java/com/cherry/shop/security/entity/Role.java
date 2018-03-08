package com.cherry.shop.security.entity;

import java.util.Date;

import com.cherry.framework.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体类
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2386089262181160337L;

	private String name;// 角色名称
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// 创建时间
	
}
