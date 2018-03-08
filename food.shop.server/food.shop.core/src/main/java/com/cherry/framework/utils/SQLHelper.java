package com.cherry.framework.utils;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

/**
 * SQL辅助类
 * 
 * @author 赵凡
 * @since 2017年12月29日
 * @version 1.0
 * @param <T>
 *            实体
 */
public class SQLHelper<T> {

	private EntityWrapper<T> wrapper;// 实体包装器
	
	/**
	 * 创建一个SQL辅助类实例
	 * 
	 * @param wrapper
	 *            实体包装器
	 * @param page
	 *            分页参数
	 */
	private SQLHelper(EntityWrapper<T> wrapper) {
		this.wrapper = wrapper;
	}
	
	/**
	 * 创建一个SQL辅助类实例
	 * 
	 * @param clazz
	 *            实体类字节码对象
	 * @return SQL辅助类实例
	 */
	public static <T> SQLHelper<T> build(Class<T> clazz) {
		return new SQLHelper<T>(new EntityWrapper<T>());
	}
	
	/**
	 * 创建一个不带任何条件的EntityWrapper实例
	 * 
	 * @param clazz
	 *            实体类字节码对象
	 * @return 不带任何条件的EntityWrapper实例
	 */
	public static <T> EntityWrapper<T> buildEmptyWrapper(Class<T> clazz) {
		return new EntityWrapper<T>();
	}
	
	/**
	 * 添加相等条件
	 * 
	 * @param propertyName
	 *            实体属性名
	 * @param value
	 *            相等的值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> eq(String propertyName, Object value) {
		if (value != null) {
			wrapper.eq(StringUtil.convertPropName2ColName(propertyName), value);
		}
		return this;
	}
	
	/**
	 * 添加相等条件，当value为null时，添加is_null条件
	 * 
	 * @param propertyName
	 *            实体属性名
	 * @param value
	 *            相等的值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> eqOrNull(String propertyName, Object value) {
		propertyName = StringUtil.convertPropName2ColName(propertyName);
		if (value != null) {
			wrapper.eq(propertyName, value);
		} else {
			wrapper.isNull(propertyName);
		}
		return this;
	}
	
	/**
	 * 添加相等条件，当value为null或空字符串时，添加is_null条件
	 * 
	 * @param propertyName
	 *            实体属性名
	 * @param value
	 *            相等的值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> eqOrBlank(String propertyName, String value) {
		propertyName = StringUtil.convertPropName2ColName(propertyName);
		if (StringUtils.isNotBlank(value)) {
			wrapper.eq(propertyName, value);
		} else {
			wrapper.isNull(propertyName);
		}
		return this;
	}
	
	/**
	 * 添加必须相等条件，不对value进行非空校验
	 * 
	 * @param propertyName
	 *            实体属性名
	 * @param value
	 *            相等的值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> mustEq(String propertyName, String value) {
		wrapper.eq(StringUtil.convertPropName2ColName(propertyName), value);
		return this;
	}
	
	/**
	 * 添加相等条件
	 * 
	 * @param propertyName
	 *            实体属性名
	 * @param value
	 *            相等的值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> eqStr(String propertyName, String value) {
		if (StringUtils.isNotBlank(value)) {
			wrapper.eq(StringUtil.convertPropName2ColName(propertyName), value);
		}
		return this;
	}
	
	/**
	 * 添加不等于条件，如果value为null或空字符串，则不添加该条件
	 * 
	 * @param propertyName
	 *            实体属性名
	 * @param value
	 *            不相等的值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> notEqStr(String propertyName, String value) {
		if (StringUtils.isNotBlank(value)) {
			wrapper.ne(StringUtil.convertPropName2ColName(propertyName), value);
		}
		return this;
	}
	
	/**
	 * 添加是否为true条件
	 * 
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> isTrue(String propertyName, boolean value) {
		if (value) {
			wrapper.eq(StringUtil.convertPropName2ColName(propertyName), value);
		}
		return this;
	}
	
	/**
	 * 添加是否为false条件
	 * 
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> isFalse(String propertyName, boolean value) {
		if (!value) {
			wrapper.eq(StringUtil.convertPropName2ColName(propertyName), value);
		}
		return this;
	}
	
	/**
	 * 添加为null条件
	 * 
	 * @param propertyName
	 *            为null的属性字段
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> isNull(String propertyName) {
		wrapper.isNull(StringUtil.convertPropName2ColName(propertyName));
		return this;
	}
	
	/**
	 * 添加为非null条件
	 * 
	 * @param propertyName
	 *            为非null的属性字段
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> isNotNull(String propertyName) {
		wrapper.isNotNull(StringUtil.convertPropName2ColName(propertyName));
		return this;
	}
	
	/**
	 * 添加IN子句，如果values数组为null，则不进行添加
	 * 
	 * @param propertyName
	 *            实体属性名
	 * @param values
	 *            IN子句中包含的值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> in(String propertyName, Object[] values) {
		if (ArrayUtils.isNotEmpty(values)) {
			wrapper.in(StringUtil.convertPropName2ColName(propertyName), values);
		}
		return this;
	}
	
	/**
	 * 添加IN子句，如果values集合为null，则不进行添加
	 * 
	 * @param propertyName
	 *            实体属性名
	 * @param values
	 *            IN子句中包含的值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> in(String propertyName, Collection<?> values) {
		if (CollectionUtils.isNotEmpty(values)) {
			wrapper.in(StringUtil.convertPropName2ColName(propertyName), values);
		}
		return this;
	}
	
	/**
	 * 添加NOT IN子句，如果values集合为null，则不进行添加
	 * 
	 * @param propertyName
	 *            实体属性名
	 * @param values
	 *            NOT IN子句中包含的值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> notIn(String propertyName, Collection<?> values) {
		if (CollectionUtils.isNotEmpty(values)) {
			wrapper.notIn(StringUtil.convertPropName2ColName(propertyName), values);
		}
		return this;
	}
	
	/**
	 * 添加LIKE条件
	 * 
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            模糊匹配值
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> like(String propertyName, String value) {
		if (StringUtils.isNotEmpty(value)) {
			wrapper.like(StringUtil.convertPropName2ColName(propertyName), value);
		}
		return this;
	}
	
	/**
	 * 一次添加多个LIKE条件
	 * 
	 * @param value
	 *            模糊匹配值
	 * @param propertyNames
	 *            属性名列表
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> likeOr(String value, String... propertyNames) {
		if (StringUtils.isNotEmpty(value)) {
			if (ArrayUtils.isNotEmpty(propertyNames)) {
				wrapper.andNew();
				for (int i = 0; i < propertyNames.length; i++) {
					wrapper.like(StringUtil.convertPropName2ColName(propertyNames[i]), value);
					if (i == propertyNames.length - 1) {// 最后一个like条件
						wrapper.andNew(" 1=1 ");
					} else {
						wrapper.or();
					}
				} 
			}
		}
		return this;
	}
	
	/**
	 * 添加OR子句
	 * 
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> or() {
		wrapper.or();
		return this;
	}
	
	/**
	 * 设置排序
	 * 
	 * @param propertyName
	 *            排序属性
	 * @param isAsc
	 *            是否升序
	 * @return SQLHelper实例
	 */
	public SQLHelper<T> orderBy(String propertyName, boolean isAsc) {
		if (StringUtils.isNotBlank(propertyName)) {
			wrapper.orderBy(StringUtil.convertPropName2ColName(propertyName), isAsc);
		}
		return this;
	}
	
	/**
	 * 获取实体包装器
	 * 
	 * @return 实体包装器
	 */
	public EntityWrapper<T> geEntityWrapper() {
		return this.wrapper;
	}

}
