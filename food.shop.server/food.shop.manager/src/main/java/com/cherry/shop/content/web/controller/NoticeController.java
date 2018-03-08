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
import com.cherry.framework.param.ToggleStatusParam;
import com.cherry.framework.utils.Results;
import com.cherry.shop.content.entity.Notice;
import com.cherry.shop.content.param.AddNoticeParam;
import com.cherry.shop.content.param.ListNoticeParam;
import com.cherry.shop.content.param.UpdateNoticeParam;
import com.cherry.shop.content.service.NoticeService;

/**
 * 公告控制器
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	private final static Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 显示公告页面
	 * 
	 * @param mav
	 *            ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping("/grid")
	public ModelAndView grid(ModelAndView mav) {
		mav.setViewName("content/noticeGrid");
		return mav;
	}
	
	/**
	 * 查询公告列表
	 * 
	 * @param page
	 *            分页参数
	 * @param params
	 *            过滤参数
	 * @return 公告列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(Page<Notice> page, ListNoticeParam params) {
		try {
			return Results.pageResult(noticeService.listNotice(page, params));
		} catch (Exception e) {
			logger.error("查询公告列表失败！page:" + page.toString() + ",params:" + params.toString() + "", e);
			return Results.queryError();
		}
	}
	
	/**
	 * 查询公告
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
			return Results.queryOk(noticeService.getNotice(params.getId()));
		} catch (Exception e) {
			logger.error("查询公告失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 添加公告
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 添加结果
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@Valid @RequestBody AddNoticeParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			noticeService.addNotice(params);
			
			return Results.addOk();
		} catch (Exception e) {
			logger.error("添加公告失败！params:" + params.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 更新公告
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 请求结果
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@Valid @RequestBody UpdateNoticeParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			noticeService.updateNotice(params);
			
			return Results.updateOk();
		} catch (Exception e) {
			logger.error("更新服务热线失败！params:" + params.toString(), e);
			return Results.updateError();
		}
	}
	
	/**
	 * 批量删除公告
	 * 
	 * @param params
	 *            删除公告请求参数
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
			
			noticeService.deleteNoticeByIds(params.getIds());
			return Results.delOk();
		} catch (Exception e) {
			logger.error("删除公告失败！params:" + params.toString(), e);
			return Results.delError();
		}
	}
	
	/**
	 * 切换公告状态
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
			
			noticeService.toggleStatus(params);
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("切换公告状态失败！params:" + params.toString(), e);
			return Results.opError();
		}
	}
	
}
