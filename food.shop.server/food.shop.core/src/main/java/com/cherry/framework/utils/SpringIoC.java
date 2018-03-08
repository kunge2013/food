package com.cherry.framework.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Spring IoC容器辅助类
 * 
 * @author 赵凡
 * @since 2017年11月1日
 * @version 1.0
 */
public class SpringIoC implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringIoC.applicationContext = applicationContext;
	}
	
	/**
	 * 从Spring容器中获取Bean
	 * 
	 * @param name
	 *            Bean名称
	 * @param requiredType
	 *            期望的类型
	 * @return Spring容器中指定名称和期望类型的Bean
	 */
	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}
	
	/**
	 * 从Spring容器中获取Bean
	 * 
	 * @param requiredType
	 *            期望的类型
	 * @return Spring容器中指定名称和期望类型的Bean
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}
	
	/**
	 * 获取国际化消息
	 * 
	 * @param code
	 *            代码
	 * @return 国际化消息
	 */
	public static String getMessage(String code) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return applicationContext.getMessage(code, null, code, request.getLocale());
	}
	
}
