package com.cherry.shop.user.dto.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 获取个人信息请求参数
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@Data
public class GetInfoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4385412815610607879L;
	
	private String id;// 用户ID
	
	private String username;// 用户名
	
	private String telephone;// 手机号
	
	private String nickName;// 昵称
	
	private String image;// 头像
	
	private String sex;// 性别：M、男；F、女；U、保密
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;// 生日
	
	private BigDecimal returnMoney;// 返现
	
	private BigDecimal walletMoney;// 钱包
	
	private CompanyDto company;// 企业信息
	
	private ReceiverAddrDto receiverAddr;// 默认收货地址

}
