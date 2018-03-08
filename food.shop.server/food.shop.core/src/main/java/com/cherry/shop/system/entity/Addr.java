package com.cherry.shop.system.entity;

import java.util.Date;

import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地址实体类
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Addr extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3894489424017805837L;

	private String address;// 地址
	
	private String parentId;// 父级地址ID
	
	private Date createTime;// 创建时间
	
}
