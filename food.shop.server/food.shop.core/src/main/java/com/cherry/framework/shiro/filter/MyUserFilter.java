package com.cherry.framework.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 扩展User过滤去
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
public class MyUserFilter extends UserFilter {

	private String loginFormUrl;// 登录表单URL
	
	public String getLoginFormUrl() {
		return loginFormUrl;
	}

	public void setLoginFormUrl(String loginFormUrl) {
		this.loginFormUrl = loginFormUrl;
	}

	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		String loginFormUrl = getLoginFormUrl();
        WebUtils.issueRedirect(request, response, loginFormUrl);
	}

}
