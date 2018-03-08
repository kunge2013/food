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
import com.cherry.framework.param.BatchDeleteParam;
import com.cherry.framework.param.GetParam;
import com.cherry.framework.param.ToggleStatusParam;
import com.cherry.framework.service.FileService;
import com.cherry.framework.utils.Constants;
import com.cherry.framework.utils.Results;
import com.cherry.shop.content.param.AddCarouselParam;
import com.cherry.shop.content.param.ListCarouselParam;
import com.cherry.shop.content.param.UpdateCarouselParam;
import com.cherry.shop.content.service.CarouselService;

/**
 * 轮播图控制器
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
@Controller
@RequestMapping("/carousel")
public class CarouselController {
	
	private final static Logger logger = LoggerFactory.getLogger(CarouselController.class);
	
	@Autowired
	private CarouselService carouselService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 显示轮播图页面
	 * 
	 * @param mav
	 *            ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping("/grid")
	public ModelAndView grid(ModelAndView mav) {
		mav.setViewName("content/carouselGrid");
		return mav;
	}
	
	/**
	 * 查询轮播图列表
	 * 
	 * @param params
	 *            查询参数
	 * @return 轮播图列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(ListCarouselParam params) {
		try {
			return Results.listResult(carouselService.listCarousel(params));
		} catch (Exception e) {
			logger.error("查询轮播图失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 查询轮播图
	 * 
	 * @param params
	 *            查询参数
	 * @return 查询结果
	 */
	@RequestMapping("/get")
	@ResponseBody
	public Result get(@Valid GetParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			return Results.queryOk(carouselService.getCarousel(params.getId()));
		} catch (Exception e) {
			logger.error("查询系统用户失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 上传轮播图
	 * 
	 * @param file
	 *            MultipartFile
	 * @return 上传结果
	 */
	@RequestMapping("/uploadImage")
	@ResponseBody
	public Result uploadImage(MultipartFile file) {
		return fileService.uploadFile(file, Constants.FILE_DIR_CAROUSEL);
	}
	
	/**
	 * 添加轮播图
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 添加结果
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@Valid @RequestBody AddCarouselParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			carouselService.addCarousel(params);
			
			return Results.addOk();
		} catch (Exception e) {
			logger.error("添加轮播图失败！params:" + params.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 更新轮播图
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 请求结果
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@Valid @RequestBody UpdateCarouselParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			carouselService.updateCarousel(params);
			
			return Results.updateOk();
		} catch (Exception e) {
			logger.error("更新轮播图失败！params:" + params.toString(), e);
			return Results.updateError();
		}
	}
	
	/**
	 * 批量删除轮播图
	 * 
	 * @param params
	 *            删除轮播图请求参数
	 * @param r
	 *            参数验证结果
	 * @return 删除结果
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(@Valid @RequestBody BatchDeleteParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			carouselService.deleteBatchIds(params.getIds());
			return Results.delOk();
		} catch (Exception e) {
			logger.error("删除轮播图失败！params:" + params.toString(), e);
			return Results.delError();
		}
	}
	
	/**
	 * 切换轮播图状态
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            参数验证结果
	 * @return 切换状态结果
	 */
	@RequestMapping("/toggleStatus")
	@ResponseBody
	public Result toggleStatus(@Valid @RequestBody ToggleStatusParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			carouselService.toggleStatus(params);
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("切换轮播图失败！params:" + params.toString(), e);
			return Results.opError();
		}
	}
	
}
