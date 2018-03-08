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
import org.springframework.web.servlet.ModelAndView;

import com.cherry.framework.dto.Result;
import com.cherry.framework.param.BatchDeleteParam;
import com.cherry.framework.param.GetParam;
import com.cherry.framework.utils.Results;
import com.cherry.shop.content.param.AddHotlineParam;
import com.cherry.shop.content.param.ListHotlineParam;
import com.cherry.shop.content.param.UpdateHotlineParam;
import com.cherry.shop.content.service.HotlineService;

/**
 * 服务热线控制器
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Controller
@RequestMapping("/hotline")
public class HotlineController {
	
	private final static Logger logger = LoggerFactory.getLogger(HotlineController.class);
	
	@Autowired
	private HotlineService  hotlineService;

	/**
	 * 显示服务热线页面
	 * 
	 * @param mav
	 *            ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping("/grid")
	public ModelAndView grid(ModelAndView mav) {
		mav.setViewName("content/hotlineGrid");
		return mav;
	}
	
	/**
	 * 查询服务热线列表
	 * 
	 * @param params
	 *            查询参数
	 * @return 服务热线列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(ListHotlineParam params) {
		try {
			return Results.listResult(hotlineService.listHotline(params));
		} catch (Exception e) {
			logger.error("查询服务热线失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 查询服务热线
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
			return Results.queryOk(hotlineService.getHotline(params.getId()));
		} catch (Exception e) {
			logger.error("查询服务热线失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 添加服务热线
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 添加结果
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@Valid @RequestBody AddHotlineParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			hotlineService.addHotline(params);
			
			return Results.addOk();
		} catch (Exception e) {
			logger.error("添加服务热线失败！params:" + params.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 更新服务热线
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 请求结果
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@Valid @RequestBody UpdateHotlineParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			hotlineService.updateHotline(params);
			
			return Results.updateOk();
		} catch (Exception e) {
			logger.error("更新服务热线失败！params:" + params.toString(), e);
			return Results.updateError();
		}
	}
	
	/**
	 * 批量删除服务热线
	 * 
	 * @param params
	 *            删除服务热线请求参数
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
			
			hotlineService.deleteHotlines(params.getIds());
			return Results.delOk();
		} catch (Exception e) {
			logger.error("删除服务热线失败！params:" + params.toString(), e);
			return Results.delError();
		}
	}
	
}
