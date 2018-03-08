package com.cherry.shop.user.entity;

import java.util.Date;

import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 企业实体类
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Company extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -812929358610866103L;
	
	private String name;// 企业名称
	
	private String telephone;// 手机号
	
	private Date createTime;// 创建时间
	
	private String managerId;// 管理者ID

}
