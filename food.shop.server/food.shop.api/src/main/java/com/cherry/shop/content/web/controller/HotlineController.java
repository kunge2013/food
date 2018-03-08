package com.cherry.shop.content.web.controller;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherry.framework.dto.Result;
import com.cherry.framework.utils.Results;
import com.cherry.shop.content.entity.Hotline;
import com.cherry.shop.content.param.ListHotlineParam;
import com.cherry.shop.content.service.HotlineService;

/**
 * 服务热线接口控制器
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@RestController
@RequestMapping("/hotline")
public class HotlineController {

	@Autowired
	private HotlineService hotlineService;
	
	/**
	 * 查询服务热线列表
	 * 
	 * @return 服务热线列表
	 */
	@RequestMapping(path = "/list", method = GET)
	public Result list() {
		// 查询服务热线，按order降序排序
		ListHotlineParam params = new ListHotlineParam();
		params.setOrderByField("order");
		params.setAsc(false);
		return Results.listResult(
			hotlineService.listHotline(params)
						  .stream()
						  .map(Hotline::getPhone)// 提取服务电话
						  .collect(toList())
		);
	}
	
}
