package com.cherry.shop.user.param.api;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 绑定企业请求参数
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@Data
public class BindCompanyParam {

	@NotNull
	private String companyId;// 企业ID
	
}
