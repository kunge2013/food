package com.cherry.shop.content.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherry.framework.dto.Result;
import com.cherry.framework.utils.Results;
import com.cherry.shop.content.service.CompanyInfoService;

/**
 * 公司企业简介接口控制器
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
@RestController
public class CompanyInfoController {

	@Autowired
	private CompanyInfoService companyInfoService;
	
	/**
	 * 查询公司和企业信息
	 * 
	 * @return 公司和企业信息
	 */
	@RequestMapping(path = "/companyInfo", method = GET)
	public Result info() {
		return Results.queryOk(companyInfoService.getCompanyInfo());
	}
	
}
