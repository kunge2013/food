package com.cherry.shop.security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cherry.framework.shiro.utils.ShiroUtils;

/**
 * HOME控制器
 * 
 * @author 赵凡
 * @since 2018年1月6日
 * @version 1.0
 */
@Controller
public class HomeController {

	/**
	 * 首页
	 * 
	 */
	@RequestMapping("/index")
	public ModelAndView index(ModelAndView mav) {
		mav.addObject("curSysUser", ShiroUtils.getLoginSysUser());
		return mav;
	}
	
}
