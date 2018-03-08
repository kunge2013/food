package com.cherry.framework.param;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.plugins.Page;

import lombok.Data;

/**
 * 分页查询请求参数
 * 
 * @author 赵凡
 * @since 2018年1月16日
 * @version 1.0
 */
@Data
public class QueryPageParam {

	@NotNull
	@Min(1)
	private Integer pageNum;// 当前页码
	
	@NotNull
	@Min(1)
	@Max(500)
	private Integer pageSize;// 每页记录数
	
	/**
	 * 转换为Page参数
	 * 
	 * @param clazz
	 *            分页的实体类字节码对象
	 * @return Page参数
	 */
	public <T> Page<T> toPage(Class<T> clazz) {
		return new Page<T>(pageNum, pageSize);
	}
	
}
