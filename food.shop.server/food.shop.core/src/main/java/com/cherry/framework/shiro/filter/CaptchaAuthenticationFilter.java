package com.cherry.framework.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.framework.shiro.CaptchaToken;

/**
 * 带验证码的认证过滤器
 * 
 * @author 赵凡
 * @since 2017年11月25日
 * @version 1.0
 */
public class CaptchaAuthenticationFilter extends FormAuthenticationFilter {
	
	private static final Logger log = LoggerFactory.getLogger(CaptchaAuthenticationFilter.class);

	/**
	 * 默认图形验证码参数名
	 * 
	 */
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	
	// 图形验证码参数名
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	
	/**
	 * 获取图形验证码参数名
	 * 
	 * @return 图形验证码参数名
	 */
	public String getCaptchaParam() {
		return captchaParam;
	}

	/**
	 * 设置图形验证码参数名
	 * 
	 * @param captchaParam
	 *            图形验证码参数名
	 */
	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new CaptchaToken(username, password, rememberMe, host, captcha);
	}
	
	// 未认证进该方法
//	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        if (isLoginRequest(request, response)) {// 与loginUrl参数对比，确定当前请求是否是登录请求
//            if (isLoginSubmission(request, response)) {// 判断是否是HTTP POST提交
//                if (log.isTraceEnabled()) {
//                    log.trace("尝试登录！");
//                }
//                return executeLogin(request, response);// 执行登录
//            } else {
//                if (log.isTraceEnabled()) {
//                    log.trace("登录页面！");
//                }
//                return true;
//            }
//        } else {
//            if (log.isTraceEnabled()) {
//                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
//                        "Authentication url [" + getLoginUrl() + "]");
//            }
//
//            //saveRequestAndRedirectToLogin(request, response);
//            //return false;
//            boolean flag = executeLogin(request, response);
//            if (!flag) {
//            	saveRequestAndRedirectToLogin(request, response);
//            }
//            return flag;
//        }
//    }
	
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }
	
	/**
	 * 重定向到登录
	 * 
	 */
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        String loginUrl = getLoginUrl() + "?token=" + request.getParameter("token");
        WebUtils.issueRedirect(request, response, loginUrl);
    }
	
	/**
	 * 登录成功
	 * 
	 */
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
		//issueSuccessRedirect(request, response);
		//we handled the success redirect directly, prevent the chain from continuing:
		return true;
	}
	
	/**
	 * 获取图形验证码
	 * 
	 * @param request
	 *            ServletRequest
	 * @return 图形验证码
	 */
	protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }
	
}
