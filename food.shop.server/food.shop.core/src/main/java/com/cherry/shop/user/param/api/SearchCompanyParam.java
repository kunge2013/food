package com.cherry.shop.user.param.api;

import com.cherry.framework.param.QueryPageParam;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询企业请求参数
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchCompanyParam extends QueryPageParam {

	private String search;// 搜索文本
	
}
