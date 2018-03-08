package com.cherry.framework.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;

import com.cherry.framework.shiro.utils.ShiroUtils;

/**
 * 回话过滤器
 * 
 * @author 赵凡
 * @since 2017年12月16日
 * @version 1.0
 */
public class SessionFilter extends AdviceFilter {

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		ShiroUtils.getSessionId();
		return true;
	}

}
