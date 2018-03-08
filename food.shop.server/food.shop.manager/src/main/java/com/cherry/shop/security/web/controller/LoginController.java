package com.cherry.shop.security.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cherry.framework.dto.Result;
import com.cherry.framework.utils.Results;

/**
 * 登录控制器
 * 
 * @author 赵凡
 * @since 2018年1月7日
 * @version 1.0
 */
@Controller
public class LoginController {

	/**
	 * 登录界面
	 * 
	 */
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "login";
	}
	
	/**
	 * 登录
	 * 
	 * @param mav
	 *            ModelAndView
	 * @param req
	 *            HttpServletRequest
	 * @return 登录视图
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Result login(HttpServletRequest req) {
		// 获取登录异常类名
		String className = (String) req.getAttribute("shiroLoginFailure");
		
		// 登录异常处理
		if (UnknownAccountException.class.getName().equals(className) || 
				IncorrectCredentialsException.class.getName().equals(className)) {
			return Results.opError("用户名或密码错误！");
		} else if (DisabledAccountException.class.getName().equals(className)) {
			return Results.opError("该用户被禁用！");
		} else {
			return Results.opOk();
		}
	}
	
}
