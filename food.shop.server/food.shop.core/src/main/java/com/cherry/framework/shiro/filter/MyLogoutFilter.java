package com.cherry.framework.shiro.filter;

import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogoutFilter extends LogoutFilter {

	private static final Logger log = LoggerFactory.getLogger(MyLogoutFilter.class);
	
	@Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        Subject subject = getSubject(request, response);

        if (isPostOnlyLogout()) {

            if (!WebUtils.toHttp(request).getMethod().toUpperCase(Locale.ENGLISH).equals("POST")) {
               return onLogoutRequestNotAPost(request, response);
            }
        }

        HttpServletResponse resp = WebUtils.toHttp(response);
        resp.setContentType("application/json;charset=utf-8");
        try (PrintWriter pw = resp.getWriter()) {
            subject.logout();
            pw.println("{\"code\":\"0\",\"message\":\"登出成功！\",\"success\":true,\"data\":{}}");
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        
        return false;
    }
	
}
