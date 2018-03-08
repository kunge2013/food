package com.cherry.shop.user.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.user.dto.api.CompanyDto;
import com.cherry.shop.user.entity.Company;
import com.cherry.shop.user.param.api.SearchCompanyParam;

/**
 * 企业Service接口
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
public interface CompanyService extends IService<Company> {

	/**
	 * 添加企业
	 * 
	 * @param name
	 *            企业名称
	 * @return 企业ID
	 */
	String addCompany(String name);

	/**
	 * 绑定企业
	 * 
	 * @param companyId
	 *            企业ID
	 */
	void bindCompany(String companyId);
	
	/**
	 * 搜索企业列表
	 * 
	 * @param param
	 *            搜索参数
	 * @return 企业列表
	 */
	Page<Company> searchCompany(SearchCompanyParam param);

	/**
	 * 获取企业信息
	 * 
	 * @return 企业信息
	 */
	CompanyDto getCompany(String companyId);

}
