package com.cherry.framework.dto;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.cherry.framework.utils.SpringIoC;

/**
 * 查询响应结果DTO
 * 
 * @author 赵凡
 * @since 2017年12月28日
 * @version 1.0
 */
public class QueryResult extends Result {

	private Integer total;// 总记录数
	
	private Integer pageCount;// 总页数
	
	private Integer curPage;// 当前页码
	
	private Integer pageSize;// 每页最大记录数
	
	/**
	 * 创建查询响应结果DTO
	 * 
	 * @param code
	 *            响应码，0表示成功，其它表示失败
	 * @param message
	 *            响应消息
	 * @param success
	 *            是否成功
	 * @param page
	 *            响应数据
	 */
	public QueryResult(String code, String message, boolean success, Page<?> page) {
		super(code, SpringIoC.getMessage(message), success, (Serializable) page.getRecords());
		this.total = page.getTotal();
		this.pageCount = page.getPages();
		this.curPage = page.getCurrent();
		this.pageSize = page.getSize();
	}
	
	/**
	 * 创建查询响应结果DTO
	 * 
	 * @param code
	 *            响应码，0表示成功，其它表示失败
	 * @param message
	 *            响应消息
	 * @param success
	 *            是否成功
	 * @param list
	 *            响应数据
	 */
	public QueryResult(String code, String message, boolean success, List<?> list) {
		super(code, SpringIoC.getMessage(message), success, (Serializable) list);
		this.total = list.size();
		this.pageCount = 1;
		this.curPage = 1;
		this.pageSize = Integer.MAX_VALUE;
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
	}

	/**
	 * 获取总记录数
	 * 
	 * @return 总记录数
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * 设置总记录数
	 * 
	 * @param total
	 *            总记录数
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * 获取总页面数
	 * 
	 * @return 总页面数
	 */
	public Integer getPageCount() {
		return pageCount;
	}

	/**
	 * 设置总页面数
	 * 
	 * @param pageCount
	 *            总页面数
	 */
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 获取当前页码
	 * 
	 * @return 当前页码
	 */
	public Integer getCurPage() {
		return curPage;
	}

	/**
	 * 设置当前页码
	 * 
	 * @param curPage
	 *            当前页码
	 */
	public void setsurPage(Integer curPage) {
		this.curPage = curPage;
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页记录数
	 * 
	 * @param pageSize
	 *            每页记录数
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
