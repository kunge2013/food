package com.cherry.shop.security.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 系统用户DTO
 * 
 * @author 赵凡
 * @since 2017年12月28日
 * @version 1.0
 */
@Data
public class SysUserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1708601601897596713L;

	private String id;// 系统用户ID
	
	private String username;// 用户名
	
	private String image;// 头像
	
	private String realName;// 真实姓名
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;// 出生年月
	
	private String sex;// 性别：M、男；F、女；U、未知
	
	private String telephone;// 手机号
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// 创建时间
	
	private String creatorId;// 创建者
	
	private String creatorUsername;// 创建者用户名
	
	private String status;// 用户状态
	
	private String email;// 邮箱地址
	
}
