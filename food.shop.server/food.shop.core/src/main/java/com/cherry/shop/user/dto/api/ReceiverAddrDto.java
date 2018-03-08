package com.cherry.shop.user.dto.api;

import java.io.Serializable;

import lombok.Data;

/**
 * 收货地址Dto
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@Data
public class ReceiverAddrDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2844466273761259449L;

	private String id;// 收货地址ID
	
	private String receiverName;// 收货人姓名
	
	private String telephone;// 手机号
	
	private String region;// 所属区域
	
	private String street;// 街道
	
	private String address;// 详细地址
	
	private boolean defaultAddr;// 默认地址
	
}
