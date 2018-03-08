package com.cherry.shop.content.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.cherry.framework.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务热线实体类
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Hotline extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7254290297267275674L;
	
	private String phone;// 电话号码
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// 创建时间
	
	@TableField("`order`")
	private Integer order;// 顺序
	
}
