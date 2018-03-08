package com.cherry.framework.shiro.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.cherry.shop.security.entity.SysUser;
import com.cherry.shop.user.entity.User;

/**
 * Shiro工具类
 * 
 * @author 赵凡
 * @since 2017年12月13日
 * @version 1.0
 */
public class ShiroUtils {

	/**
	 * 获取会话ID
	 * 
	 * @return 会话ID
	 */
	public static String getSessionId() {
		return (String) SecurityUtils.getSubject().getSession().getId();
	}
	
	/**
	 * 是否已经登录
	 * 
	 * @return 如果已经登录返回true，否则返回false
	 */
	public static boolean isLogin() {
		Subject subject = SecurityUtils.getSubject();
		return subject.isAuthenticated() || subject.isRemembered();
	}
	
	/**
	 * 是否未登录
	 * 
	 * @return 如果未登录返回true，否则返回false
	 */
	public static boolean isNotLogin() {
		return !isLogin();
	}
	
	/**
	 * 获取凭证
	 * 
	 * @param clazz
	 *            凭证字节码对象
	 * @return 凭证
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getPrincipal(Class<T> clazz) {
		return (T) SecurityUtils.getSubject().getPrincipal();
	}
	
	/**
	 * 获取当前登录的系统用户
	 * 
	 * @return 当前登录的系统用户
	 */
	public static SysUser getLoginSysUser() {
		return getPrincipal(SysUser.class);
	}
	
	/**
	 * 获取当前系统用户ID
	 * 
	 * @return 当前系统用户ID
	 */
	public static String getLoginSysUserId() {
		return getLoginSysUser().getId();
	}
	
	/**
	 * 获取当前登录的用户
	 * 
	 * @return 当前登录的用户
	 */
	public static User getLoginUser() {
		return getPrincipal(User.class);
	}
	
	/**
	 * 获取当前用户ID
	 * 
	 * @return 当前用户ID
	 */
	public static String getLoginUserId() {
		return getLoginUser().getId();
	}
	
	/**
	 * 设置会话属性
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void setSessionAttr(String key, Object value) {
		SecurityUtils.getSubject().getSession().setAttribute(key, value);
	}
	
	/**
	 * 获取会话属性
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Object getSessionAttr(String key) {
		return SecurityUtils.getSubject().getSession().getAttribute(key);
	}
	
}
