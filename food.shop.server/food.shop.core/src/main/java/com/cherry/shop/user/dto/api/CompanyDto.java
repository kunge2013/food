package com.cherry.shop.user.dto.api;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 企业Dto
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@Data
public class CompanyDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5484929013240801596L;
	
	private String id;// 企业ID
	
	private String name;// 企业名称
	
	private String telephone;// 手机号
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// 创建时间
	
	private String managerId;// 管理者ID
	
	private String managerName;// 企业管理者

}
