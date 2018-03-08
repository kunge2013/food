package com.cherry.shop.content.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.framework.param.ToggleStatusParam;
import com.cherry.shop.content.entity.Carousel;
import com.cherry.shop.content.param.AddCarouselParam;
import com.cherry.shop.content.param.ListCarouselParam;
import com.cherry.shop.content.param.UpdateCarouselParam;

/**
 * 轮播图Service接口
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
public interface CarouselService extends IService<Carousel>{
	
	/**
	 * 查询轮播图列表
	 * 
	 * @param params
	 *            分页参数
	 * @return 轮播图列表
	 */
	List<Carousel> listCarousel(ListCarouselParam params);
	
	/**
	 * 查询轮播图
	 * 
	 * @param id
	 *            轮播图ID
	 * @return 轮播图
	 */
	Carousel getCarousel(String id);
	
	/**
	 * 新增轮播图
	 * 
	 * @param param
	 *            轮播图信息
	 */
	void addCarousel(AddCarouselParam param);

	/**
	 * 更新轮播图
	 * 
	 * @param param
	 *            轮播图信息
	 */
	void updateCarousel(UpdateCarouselParam param);
	
	/**
	 * 批量删除轮播图
	 * 
	 * @param carouselIds
	 *            轮播图ID列表
	 */
	void  deleteCarouselByIds(List<String> carouselIds);

	/**
	 * 切换轮播图状态
	 * 
	 * @param params
	 *            请求参数
	 */
	void toggleStatus(ToggleStatusParam params);
	
}
