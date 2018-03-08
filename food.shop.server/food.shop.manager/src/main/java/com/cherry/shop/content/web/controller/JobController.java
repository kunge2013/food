package com.cherry.shop.content.web.controller;

import java.util.List;

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
import com.cherry.shop.content.entity.Job;
import com.cherry.shop.content.param.AddJobParam;
import com.cherry.shop.content.param.ListJobParam;
import com.cherry.shop.content.param.UpdateJobParam;
import com.cherry.shop.content.service.JobService;

/**
 * 人才招聘控制器
 * 
 * @author 崔力
 * @since 2018年1月17日
 * @version 1.0
 */
@Controller
@RequestMapping("/job")
public class JobController {

	private final static Logger logger = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	private JobService JobService;
	
	/**
	 * 显示招聘页面
	 * 
	 * @param mav
	 *            
	 * @return ModelAndView
	 */
	@RequestMapping("/grid")
	public ModelAndView grid(ModelAndView mav) {
		mav.setViewName("content/jobGrid");
		return mav;
	}
	
	/**
	 * 查询招聘列表      
	 * @return 响应结果
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(ListJobParam params) {
		try{
			
			List<Job> jobs = JobService.listJob(params);
			return Results.listResult(jobs);
		}catch(Exception e){
			logger.error("查询招聘列表失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 根据id查询招聘信息
	 * @param param
	 * @return 查询结果
	 */
	@RequestMapping("/get")
	@ResponseBody
	public Result list(GetParam param){
		try{
			Job job = JobService.getJob(param.getId());
			return Results.queryOk(job);
		}catch(Exception e){
			logger.error("根据id查询招聘失败！param:" + param.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 新增招聘信息
	 * @param params
	 * @param r
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@Valid @RequestBody AddJobParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			JobService.addJob(params);
			
			return Results.addOk();
		} catch (Exception e) {
			logger.error("添加招聘失败！params:" + params.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 更新招聘信息
	 * @param params
	 * @param r
	 * @return 删除结果
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@Valid @RequestBody UpdateJobParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			JobService.updateJob(params);
			
			return Results.updateOk();
		} catch (Exception e) {
			logger.error("更新招聘失败params:" + params.toString(), e);
			return Results.updateError();
		}
	}
	
	/**
	 * 
	 * 
	 * @param params
	 *            删除招聘请求参数
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
			
			JobService.deleteJobByIds(params.getIds());
			
			return Results.delOk();
		} catch (Exception e) {
			logger.error("删除招聘失败！params:" + params.toString(), e);
			return Results.delError();
		}
	}
}
