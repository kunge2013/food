package com.cherry.shop.content.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherry.framework.dto.Result;
import com.cherry.framework.utils.Results;
import com.cherry.shop.content.service.NoticeService;

/**
 * 公告接口控制器
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 查询最新公告列表
	 * 
	 * @return 最新公告列表
	 */
	@RequestMapping("/list")
	public Result list() {
		List<String> notices = noticeService.listNotice();
		return Results.listResult(notices);
	}
	
}
