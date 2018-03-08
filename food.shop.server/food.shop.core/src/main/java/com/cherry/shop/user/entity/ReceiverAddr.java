package com.cherry.shop.user.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 收货地址实体类
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ReceiverAddr extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5413298756679270399L;

	private String companyId;// 企业ID
	
	private String creatorId;// 创建者ID
	
	private String receiverName;// 收货人姓名
	
	private String telephone;// 手机号
	
	private String city;// 市
	
	private String county;// 县
	
	private String town;// 镇
	
	private String street;// 街道
	
	private String address;// 详细地址
	
	@TableField("is_default_addr")
	private boolean defaultAddr;// 默认地址
	
	private Date createTime;// 创建时间
	
}
