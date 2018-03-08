package com.cherry.shop.content.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherry.framework.dto.Result;
import com.cherry.framework.utils.Results;
import com.cherry.shop.content.param.ListCarouselParam;
import com.cherry.shop.content.service.CarouselService;

/**
 * 轮播图接口控制器
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {

	@Autowired
	private CarouselService carouselService;
	
	/**
	 * 查询轮播图列表
	 * 
	 * @return 轮播图列表
	 */
	@RequestMapping(path = "/list/{platform}", method = GET)
	public Result list(@PathVariable("platform") String platform) {
		ListCarouselParam params = new ListCarouselParam();
		params.setPlatform(platform);
		params.setOrderByField("order");
		params.setAsc(false);
		return Results.listResult(
			carouselService.listCarousel(params)
		);
	}
	
}
