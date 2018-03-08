package com.cherry.framework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 参数拦截器
 * 
 * @author 赵凡
 * @since 2017年11月10日
 * @version 1.0
 */
public class ParametersInterceptor extends HandlerInterceptorAdapter {

	private String urlPath;// 服务请求路径
	
	private String title;// 系统标题
	
	/**
	 * 获取服务请求路径
	 * 
	 * @return 服务请求路径
	 */
	public String getUrlPath() {
		return urlPath;
	}

	/**
	 * 设置服务请求路径
	 * 
	 * @param urlPath
	 *            服务请求路径
	 */
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	/**
	 * 获取系统标题
	 * 
	 * @return 系统标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置系统标题
	 * 
	 * @param title
	 *            系统标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("urlPath", urlPath == null ? request.getContextPath() : urlPath);
		request.setAttribute("title", title);
		request.setAttribute("actionUrl", urlPath.substring(0, urlPath.length()-1) + request.getServletPath());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			modelAndView.addObject("urlPath", request.getAttribute("urlPath"));
			modelAndView.addObject("title", title);
			modelAndView.addObject("actionUrl", request.getAttribute("actionUrl"));
		}
	}

}
