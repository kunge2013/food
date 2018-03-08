package com.cherry.shop.content.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 公司企业简介信息DTO
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
@Data
public class CompanyInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2989374793583286583L;

	/**
	 * 公司信息
	 * 
	 */
	@NotNull
	private CompanyInfoItemDto company;
	
	/**
	 * 企业信息
	 * 
	 */
	@NotNull
	private CompanyInfoItemDto enterprise;
	
}
