package com.cherry.framework.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;

/**
 * 跨域过滤器
 * 
 * @author 赵凡
 * @since 2017年12月16日
 * @version 1.0
 */
public class CrosFilter extends AdviceFilter {

	private String exposeHeaders;// 服务器暴露的响应头列表
	
	private String allowHeaders;// 服务器允许接收的请求头列表

	/**
	 * 获取服务器暴露的响应头列表
	 * 
	 * @return 服务器暴露的响应头列表
	 */
	public String getExposeHeaders() {
		return exposeHeaders;
	}

	/**
	 * 设置服务器暴露的响应头列表
	 * 
	 * @param headers
	 *            服务器暴露的响应头列表
	 */
	public void setExposeHeaders(String exposeHeaders) {
		this.exposeHeaders = exposeHeaders;
	}

	/**
	 * 获取服务器允许接收的请求头列表
	 * 
	 * @return 服务器允许接收的请求头列表
	 */
	public String getAllowHeaders() {
		return allowHeaders;
	}

	/**
	 * 设置服务器允许接收的请求头列表
	 * 
	 * @param allowHeaders
	 *            服务器允许接收的请求头列表
	 */
	public void setAllowHeaders(String allowHeaders) {
		this.allowHeaders = allowHeaders;
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Access-Control-Expose-Headers", exposeHeaders);
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");// 允许的方法
		resp.setHeader("Access-Control-Allow-Headers", allowHeaders);
		return true;
	}
	
}
