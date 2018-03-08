package com.cherry.framework.utils;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.plugins.Page;
import com.cherry.framework.entity.BaseEntity;

/**
 * 实体转换器
 * 
 * @author 赵凡
 * @since 2017年12月28日
 * @version 1.0
 */
public class EntityConvertor {

	/**
	 * 将Page转换为Map（Key为ID，Value为实体对象）
	 * 
	 * @param page
	 *            Page
	 * @return Map（Key为ID，Value为实体对象）
	 */
	public static Map<String, ?> convertPageToMap(Page<? extends BaseEntity> page) {
		return convertListToMap(page.getRecords());
	}
	
	/**
	 * 将List转换为Map（Key为ID，Value为实体对象）
	 * 
	 * @param list
	 *            实体DTO列表
	 * @return Map（Key为ID，Value为实体对象）
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, ?> convertListToMap(List<? extends BaseEntity> list) {
		Map<String, ?> map = null;
		if (list.size() > 0) {
			map = list.stream()
					  .collect(
							  Collectors.toMap(
									  BaseEntity::getId,// Key为实体ID
									  Function.identity(),// Value为实体对象
									  (f, s) -> f// 出现重复提取第一条记录
							  )
					  );
		} else {
			map = Collections.EMPTY_MAP;
		}
		return map;
	}
	
	/**
	 * 提取实体属性列表
	 * 
	 * @param input
	 *            要提取的实体列表
	 * @param mapper
	 *            提取函数
	 * @return 提取的属性列表
	 */
	public static <T, R> List<R> extractPropertyList(Page<T> input, Function<? super T, ? extends R> mapper) {
		return extractPropertyList(input.getRecords(), mapper);
	}
	
	/**
	 * 提取实体属性列表
	 * 
	 * @param input
	 *            要提取的实体列表
	 * @param mapper
	 *            提取函数
	 * @return 提取的属性列表
	 */
	public static <T, R> List<R> extractPropertyList(List<T> input, Function<? super T, ? extends R> mapper) {
		return input.stream()
				    .map(mapper)// 提取实体的指定属性值
				    .distinct()// 去重
				    .collect(Collectors.toList());// 收集
	}
	
	/**
	 * 对列表数据执行分组
	 * 
	 * @param input
	 *            列表数据
	 * @param keyMapper
	 *            分组键映射器
	 * @return 分组列表数据
	 */
	public static <T, K> Map<K, List<T>> groupBy(List<T> input, Function<T, K> keyMapper) {
		return input.stream().collect(groupingBy(keyMapper, toList()));
	}
	
	/**
	 * 对列表数据执行分组
	 * 
	 * @param input
	 *            列表数据
	 * @param keyMapper
	 *            分组键映射器
	 * @return 分组列表数据
	 */
	public static <T, K> Map<K, List<T>> groupBy(Page<T> input, Function<T, K> keyMapper) {
		return groupBy(input.getRecords(), keyMapper);
	}
	
	/**
	 * 统计列表中的指定的BigDecimal值
	 * 
	 * @param list
	 *            实体List
	 * @param mapper
	 *            转换器
	 * @return 列表中的指定的BigDecimal值
	 */
	public static <T> BigDecimal count(List<T> list, Function<T, BigDecimal> mapper) {
		return list.stream().map(mapper).reduce(new BigDecimal(0), (t1, t2) -> t1.add(t2));
	}
	 
	/**
	 * 将实体Page转换为对应DTO的Page
	 * 
	 * @param page
	 *            实体Page
	 * @param mapper
	 *            转换器
	 * @return DTO Page
	 */
	public static <T, R> Page<R> convertPage(Page<T> page, Function<T, R> mapper) {
		List<R> newList = convertList(page.getRecords(), mapper);
		Page<R> newPage = new Page<>();
		newPage.setCurrent(page.getCurrent());
		newPage.setSize(page.getSize());
		newPage.setTotal(page.getTotal());
		newPage.setRecords(newList);
		return newPage;
	}
	
	/**
	 * 将实体List转换为对应DTO的List
	 * 
	 * @param list
	 *            实体List
	 * @param mapper
	 *            转换器
	 * @return DTO List
	 */
	public static <T, R> List<R> convertList(List<T> list, Function<T, R> mapper) {
		return list.stream().map(mapper).collect(Collectors.toList());
	}
	
}
