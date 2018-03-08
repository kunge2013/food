package com.cherry.shop.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherry.shop.content.dto.CompanyInfoDto;
import com.cherry.shop.content.dto.CompanyInfoItemDto;
import com.cherry.shop.content.service.CompanyInfoService;
import com.cherry.shop.system.dto.ConfigBlockDto;
import com.cherry.shop.system.param.Block;
import com.cherry.shop.system.param.ConfigName;
import com.cherry.shop.system.service.ConfigService;

/**
 * 公司企业简介信息Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

	@Autowired
	private ConfigService configService;
	
	@Override
	public CompanyInfoDto getCompanyInfo() {
		// 获取配置块
		ConfigBlockDto block = configService.getConfigBlock(Block.COMPANY_INFO);
		
		// 创建响应对象
		CompanyInfoDto dto = new CompanyInfoDto();
		
		// 设置公司信息
		CompanyInfoItemDto company = new CompanyInfoItemDto();
		company.setContent(block.getValue(ConfigName.COMPANY_CONTENT_CONFIG_NAME));
		company.setImage(block.getValue(ConfigName.COMPANY_IMAGE_CONFIG_NAME));
		dto.setCompany(company);
		
		// 设置企业信息
		CompanyInfoItemDto enterprise = new CompanyInfoItemDto();
		enterprise.setContent(block.getValue(ConfigName.ENTERPRISE_CONTENT_CONFIG_NAME));
		enterprise.setImage(block.getValue(ConfigName.ENTERPRISE_IMAGE_CONFIG_NAME));
		dto.setEnterprise(enterprise);
		
		return dto;
	}

	@Override
	public void saveCompanyInfo(CompanyInfoDto params) {
		// 创建配置块对象
		ConfigBlockDto block = new ConfigBlockDto(Block.COMPANY_INFO.getBlock());
		
		// 添加相关配置属性
		block.addConfig(ConfigName.COMPANY_CONTENT_CONFIG_NAME, params.getCompany().getContent());
		block.addConfig(ConfigName.COMPANY_IMAGE_CONFIG_NAME, params.getCompany().getImage());
		block.addConfig(ConfigName.ENTERPRISE_CONTENT_CONFIG_NAME, params.getEnterprise().getContent());
		block.addConfig(ConfigName.ENTERPRISE_IMAGE_CONFIG_NAME, params.getEnterprise().getImage());
		
		// 更新配置块
		configService.updateConfigBlock(block);
	}

}
