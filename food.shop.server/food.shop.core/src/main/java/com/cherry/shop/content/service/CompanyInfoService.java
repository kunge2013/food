package com.cherry.shop.content.service;

import com.cherry.shop.content.dto.CompanyInfoDto;

/**
 * 公司企业简介信息Service接口
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
public interface CompanyInfoService {

	/**
	 * 查询公司企业简介信息
	 * 
	 * @return 公司企业简介信息
	 */
	CompanyInfoDto getCompanyInfo();

	/**
	 * 保存公司企业简介信息
	 * 
	 * @param params
	 *            公司企业简介信息
	 */
	void saveCompanyInfo(CompanyInfoDto params);

}
