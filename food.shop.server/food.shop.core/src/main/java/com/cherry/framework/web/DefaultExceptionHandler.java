package com.cherry.framework.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 异常处理器
 * 
 * @author 赵凡
 * @since 2017年11月25日
 * @version 1.0
 */
public class DefaultExceptionHandler extends AbstractHandlerExceptionResolver  {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mv = new ModelAndView();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		Map<String, Object> attributes = new HashMap<>();
		view.setAttributesMap(attributes);
		mv.setView(view);
		return mv;
	}

}
