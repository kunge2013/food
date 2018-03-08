package com.cherry.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.CharUtils;

/**
 * 字符串工具类
 * 
 * @author 赵凡
 * @since 2017年11月29日
 * @version 1.0
 */
public class StringUtil {

	/**
	 * HTML标签模式对象
	 * 
	 */
	public static final Pattern HTML_PATTERN = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
	
	/**
	 * 过滤HTML标签
	 * 
	 * @param input
	 *            输入字符串
	 * @return 过滤掉HTML标签的字符串
	 */
	public static String filterHTML(String input) {
		if (input == null) {
			input = "";
		}
	    Matcher matcher = HTML_PATTERN.matcher(input);
	  	return matcher.replaceAll("");
	}
	
	/**
	 * 获取字符串的摘要
	 * 
	 * @param input
	 *            输入字符串
	 * @param length
	 *            摘要长度
	 * @return 字符串的摘要字符串
	 */
	public static String getStringDigest(String input, int length) {
		String out = filterHTML(input);
		if (out.length() > length) {
			return out.substring(0, length);
		}
		return out;
	}
	
	/**
	 * 驼峰式属性名转换为小写+下划线式数据库列名
	 * 
	 * @param propertyName
	 *            驼峰式属性名
	 * @return 小写+下划线式数据库列名
	 */
	public static String convertPropName2ColName(String propertyName) {
		// 空值处理
		if (propertyName == null) {
			propertyName = "";
		}
		
		// 是否已经转义
		boolean escape = propertyName.startsWith("`");
		
		// 转换后的列名字符串缓冲区
		StringBuilder sb = new StringBuilder();
		
		// 将属性名转换为字符数组
		char[] chars = propertyName.toCharArray();
		// 变量字符数组转换为列名字符串
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (CharUtils.isAsciiAlphaUpper(c)) {// 大写字母
				// [大写字符]替换为[_小写字符]
				if (i != 0) {// 首字母是大写直接转小写
					sb.append("_");
				}
				sb.append(CharUtils.toString(c).toLowerCase());
			} else {// 非大写字母
				// 非大写字符原样输出
				sb.append(c);
			}
		}
		
		// 返回列名符串	处理特殊字段名
		String r = sb.toString();
		if (!escape) {// 未转义，进行转义
			r = "`" + r + "`";
		}
		return r;
	} 
	
	/**
	 * 小写+下划线式数据库列名=>驼峰式属性名
	 * 
	 * @param columnName
	 *            小写+下划线式数据库列名
	 * @return 驼峰式属性名
	 */
	public static String convertColName2PropName(String columnName) {
		// 空值处理
		if (columnName == null) {
			columnName = "";
		}
		
		// 转换后的属性名字符串缓冲区
		StringBuilder sb = new StringBuilder();
		
		// 将列名转换为字符数组
		char[] chars = columnName.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			// 下划线
			if (c == '_') {
				// 下划线后面一个字符的索引
				int j = i + 1;
				// 下划线后面有字符就转换为小写字符
				if (j < chars.length) {
					sb.append(CharUtils.toString(chars[j]).toUpperCase());
					i ++;
				}
			} else {// 非下划线
                sb.append(c);  
            }
		}
		
		// 返回属性名符串
		return sb.toString();
	}
	
}
