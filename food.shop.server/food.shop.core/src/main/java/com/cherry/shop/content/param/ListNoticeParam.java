package com.cherry.shop.content.param;

import com.cherry.framework.param.QueryParam;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询公告列表请求参数
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ListNoticeParam extends QueryParam {

	private String status;// 状态：Y、激活；N：禁用
	
}
