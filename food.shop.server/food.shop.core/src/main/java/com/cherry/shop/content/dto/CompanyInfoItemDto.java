package com.cherry.shop.content.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 公司企业简介信息条目DTO
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
@Data
public class CompanyInfoItemDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5202006279414194127L;

	@NotNull
	private String content;// 内容
	
	@NotNull
	private String image;// 图片
	
}
