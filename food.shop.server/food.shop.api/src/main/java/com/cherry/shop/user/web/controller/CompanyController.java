package com.cherry.shop.user.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherry.framework.dto.Result;
import com.cherry.framework.shiro.utils.ShiroUtils;
import com.cherry.framework.utils.Results;
import com.cherry.shop.user.param.api.AddCompanyParam;
import com.cherry.shop.user.param.api.BindCompanyParam;
import com.cherry.shop.user.param.api.SearchCompanyParam;
import com.cherry.shop.user.service.CompanyService;

/**
 * 企业控制器
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 添加企业
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结构
	 * @return 请求结果
	 */
	@RequestMapping(path = "/add", method = POST)
	public Result add(@Valid AddCompanyParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 检查登录
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			return Results.opOk(companyService.addCompany(param.getName()));
		} catch (Exception e) {
			logger.error("添加并绑定企业失败！params:" + param.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 绑定企业
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 绑定结果
	 */
	@RequestMapping(path = "/bind", method = POST)
	public Result add(@Valid BindCompanyParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 检查登录
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			companyService.bindCompany(param.getCompanyId());
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("绑定企业失败！params:" + param.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 搜索企业
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 绑定结果
	 */
	@RequestMapping(path = "/search", method = GET)
	public Result search(@Valid SearchCompanyParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			return Results.pageResult(companyService.searchCompany(param));
		} catch (Exception e) {
			logger.error("搜索企业失败！params:" + param.toString(), e);
			return Results.opError();
		}
	}
	
}
