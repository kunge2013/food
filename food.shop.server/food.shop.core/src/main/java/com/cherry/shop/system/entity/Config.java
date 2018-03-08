package com.cherry.shop.system.entity;

import java.util.Date;

import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置实体类
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Config extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4096099128410460687L;
	
	/**
	 * 创建一个Config实例
	 * 
	 */
	public Config() {
		super();
	}
	
	/**
	 * 创建一个Config实例
	 * 
	 * @param block
	 *            配置块
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 */
	public Config(String block, String name, String value) {
		super();
		this.block = block;
		this.name = name;
		this.value = value;
	}

	private String block;// 块
	
	private String name;// 属性名
	
	private String value;// 属性值
	
	private String remark;// 属性值
	
	private Date createTime;// 创建时间
	
}
