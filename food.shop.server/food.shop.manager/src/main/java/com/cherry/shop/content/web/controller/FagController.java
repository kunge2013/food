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

import com.baomidou.mybatisplus.plugins.Page;
import com.cherry.framework.dto.Result;
import com.cherry.framework.param.BatchDeleteParam;
import com.cherry.framework.param.GetParam;
import com.cherry.framework.utils.Results;
import com.cherry.shop.content.entity.Fag;
import com.cherry.shop.content.param.AddFagParam;
import com.cherry.shop.content.param.ListFagParam;
import com.cherry.shop.content.param.UpdateFagParam;
import com.cherry.shop.content.service.FagService;

/**
 * 常见问题控制器
 * 
 * @author 崔力
 * @since 2018年1月18日
 * @version 1.0
 */
@Controller
@RequestMapping("/faq")
public class FagController {

	private final static Logger logger = LoggerFactory.getLogger(FagController.class);
	
	@Autowired
	private FagService fagService;
	
	/**
	 * 显示常见问题页面
	 * 
	 * @param mav
	 *            
	 * @return ModelAndView
	 */
	@RequestMapping("/grid")
	public ModelAndView grid(ModelAndView mav) {
		mav.setViewName("content/fagGrid");
		return mav;
	}
	
	/**
	 * 查询问题列表      
	 * @return 响应结果
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(Page<Fag> page,ListFagParam params) {
		try{
			
			Page<Fag> pageFag = fagService.listFag(page, params);
			return Results.pageResult(pageFag);
		}catch(Exception e){
			logger.error("查询问题列表失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 根据id查询问题信息
	 * @param param
	 * @return 查询结果
	 */
	@RequestMapping("/get")
	@ResponseBody
	public Result list(GetParam param){
		try{
			Fag fag = fagService.getFag(param.getId());
			return Results.queryOk(fag);
		}catch(Exception e){
			logger.error("根据id查询问题失败！param:" + param.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 新增问题信息
	 * @param params
	 * @param r
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@Valid @RequestBody AddFagParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			fagService.addFag(params);
			
			return Results.addOk();
		} catch (Exception e) {
			logger.error("添加问题失败！params:" + params.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 更新问题信息
	 * @param params
	 * @param r
	 * @return 删除结果
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@Valid @RequestBody UpdateFagParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			fagService.updateFag(params);
			
			return Results.updateOk();
		} catch (Exception e) {
			logger.error("更新问题失败params:" + params.toString(), e);
			return Results.updateError();
		}
	}
	
	/**
	 * 
	 * 
	 * @param params
	 *            删除问题请求参数
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
			
			fagService.deleteFagByIds(params.getIds());
			
			return Results.delOk();
		} catch (Exception e) {
			logger.error("删除问题失败！params:" + params.toString(), e);
			return Results.delError();
		}
	}
}
