package com.cherry.shop.content.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cherry.framework.dto.Result;
import com.cherry.framework.service.FileService;
import com.cherry.framework.utils.Constants;
import com.cherry.framework.utils.Results;
import com.cherry.shop.content.dto.CompanyInfoDto;
import com.cherry.shop.content.service.CompanyInfoService;

/**
 * 公司企业简介控制器
 * 
 * @author 赵凡
 * @since 2018年1月11日
 * @version 1.0
 */
@Controller
@RequestMapping("/companyInfo")
public class CompanyInfoController {
	
	private final static Logger logger = LoggerFactory.getLogger(CompanyInfoController.class);

	@Autowired
	private CompanyInfoService companyInfoService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 显示公司私企简介页面
	 * 
	 * @param mav
	 *            ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping("/grid")
	public ModelAndView grid(ModelAndView mav) {
		CompanyInfoDto companyInfo = companyInfoService.getCompanyInfo();
		mav.addObject("companyInfo", companyInfo);
		mav.setViewName("content/companyInfoGrid");
		return mav;
	}

	/**
	 * 保存公司企业简介信息
	 * 
	 * @param params
	 *            公司企业简介信息
	 * @param r
	 *            验证结果
	 * @return 保存结果
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Result save(@Valid @RequestBody CompanyInfoDto params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			companyInfoService.saveCompanyInfo(params);
			return Results.opOk();
		} catch (Exception e) {
			logger.error("保存公司企业简介信息失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 上传图片
	 * 
	 * @param file
	 *            MultipartFile
	 * @return 上传结果
	 */
	@RequestMapping("/uploadImage")
	@ResponseBody
	public Result uploadImage(MultipartFile file) {
		return fileService.uploadFile(file, Constants.FILE_DIR_COMPANY_INFO);
	}
	
}
