package com.cherry.framework.utils;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import org.joda.time.DateTime;

/**
 * 时间工具包
 * 
 * @author 赵凡
 * @since 2017年11月11日
 * @version 1.0
 */
public class DateUtil {

	public static final SimpleDateFormat TODAY_FORMAT = new SimpleDateFormat("HH:mm");
	
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	/**
	 * 将cur日期添加days天
	 * 
	 * @param cur
	 *            日期
	 * @param days
	 *            天数
	 * @return 计算后的日期
	 */
	public static Date addDays(Date cur, int days) {
		DateTime dt = new DateTime(cur);
		dt = dt.plusDays(days);
		return dt.toDate();
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            要格式化的日期
	 * @return 格式化的日期
	 */
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		} else if (org.apache.commons.lang3.time.DateUtils.isSameDay(date, new Date())) {// 今天
			return "今天" + TODAY_FORMAT.format(date);
		} else {// 过去
			return FORMAT.format(date);
		}
	}
	
	/**
	 * 判断当前时间是否在指定时间区间内
	 * 
	 * @param start
	 *            开始区间
	 * @param end
	 *            结束区间
	 * @return 在指定区间返回true，否则返回false
	 */
	public static boolean betweenDay(Date start, Date end) {
		// 没有指定区间
		if (start == null && end == null) {
			return true;
		}
		
		// 当前时间
		Date cur = new Date();
		
		boolean startFlag = true;
		if (start != null) {
			startFlag = start.before(cur);
		}
		
		boolean endFlag = true;
		if (end != null) {
			// 结束区间+1天
			end = org.apache.commons.lang3.time.DateUtils.addDays(end, 1);
			endFlag = end.after(cur);
		}
		
		return startFlag && endFlag;
	}
	
	/**
	 * timeStr时间字符串与当前本地时间进行比较，如果timeStr是将来时间则返回正数
	 * 
	 * @param timeStr
	 *            字符串时间，格式HH:mm
	 * @return 如果timeStr是将来时间则返回正数
	 */
	public static int compareCurLocalTime(String timeStr) {
		// 当前时间
		LocalTime curTime = LocalTime.now();
		// 指定时间
		String[] times = timeStr.split(":");
		LocalTime time = LocalTime.of(Integer.valueOf(times[0]), Integer.valueOf(times[1]));
		return time.compareTo(curTime);
	}
	
}
