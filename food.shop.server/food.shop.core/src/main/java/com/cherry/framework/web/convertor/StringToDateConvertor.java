package com.cherry.framework.web.convertor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * java.lang.String->java.util.Date的类型转换器
 * 
 * @author 赵凡
 * @since 2017年11月2日
 * @version 1.0
 */
public class StringToDateConvertor implements Converter<String, Date> {

	private List<String> patterns;// 日期格式列表
	
	/**
	 * 获取日期格式列表
	 * 
	 * @return 日期格式列表
	 */
	public List<String> getPatterns() {
		return patterns;
	}

	/**
	 * 设置日期格式列表
	 * 
	 * @param patterns
	 *            日期格式列表
	 */
	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
	}
	
	@Override
	public Date convert(String source) {
		
		Date target = null;
		
		if (!StringUtils.isEmpty(source)) {
			for (int i = 0; i < patterns.size(); i++) {
				try {
					target = parseDate(source, patterns.get(i));
				} catch (ParseException e) {
					continue;
				}
			}
		}
		
		return target;
	}
	
	/**
	 * 解析日期
	 * 
	 * @param source
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return 解析后得到的日期对象
	 * @throws ParseException
	 *             日期解析异常
	 */
	private Date parseDate(String source, String pattern) throws ParseException {
		return new SimpleDateFormat(pattern).parse(source);
	}

}
