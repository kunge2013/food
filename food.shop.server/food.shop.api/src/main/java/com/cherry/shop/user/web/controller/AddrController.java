package com.cherry.shop.user.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherry.framework.dto.Result;
import com.cherry.framework.utils.Results;
import com.cherry.shop.system.service.AddrService;

/**
 * 地址控制器
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@RestController
@RequestMapping("/addr")
public class AddrController {

	private static final Logger logger = LoggerFactory.getLogger(AddrController.class);
	
	@Autowired
	private AddrService addrService;
	
	/**
	 * 获取地址树
	 * 
	 * @return 地址树
	 */
	@RequestMapping(path = "/tree", method = GET)
	public Result getTree() {
		try {
			return Results.listResult(addrService.getAddrTree());
		} catch (Exception e) {
			logger.error("获取地址树失败！", e);
			return Results.queryError();
		}
	}
	
}
