package com.cherry.framework.entity;

import java.io.Serializable;

/**
 * 基础实体类
 * 
 * @author 赵凡
 * @since 2017年12月29日
 * @version 1.0
 */
public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2089163798365396639L;
	
	private String id;// ID

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
}
