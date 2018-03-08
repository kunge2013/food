package com.cherry.framework.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * 后台管理认证过滤器
 * 
 * @author 赵凡
 * @since 2018年1月7日
 * @version 1.0
 */
public class ManagerAuthenticationFilter extends FormAuthenticationFilter {

	// 为了修复使用AXIOS提交POST请求无法获取参数的问题
	protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest);
    }
	
}
