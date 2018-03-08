package com.cherry.shop.content.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.param.ToggleStatusParam;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.content.entity.Carousel;
import com.cherry.shop.content.mapper.CarouselMapper;
import com.cherry.shop.content.param.AddCarouselParam;
import com.cherry.shop.content.param.ListCarouselParam;
import com.cherry.shop.content.param.UpdateCarouselParam;
import com.cherry.shop.content.service.CarouselService;

/**
 * 轮播图Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

	@Override
	public List<Carousel> listCarousel(ListCarouselParam params) {
		return this.selectList(
				SQLHelper.build(Carousel.class)
						 .eqStr("platform", params.getPlatform())
						 .eqStr("type", params.getType())
						 .orderBy(params.getOrderByField(), params.isAsc())
						 .geEntityWrapper()
			);
	}
	
	@Override
	public Carousel getCarousel(String id) {
		return this.selectById(id);
	}
	
	@Override
	public void addCarousel(AddCarouselParam params) {
		Carousel carousel = new Carousel();
		BeanUtils.copyProperties(params, carousel);
		carousel.setCreateTime(new Date());
		
		this.insert(carousel);
	}
	
	@Override
	public void updateCarousel(UpdateCarouselParam params) {
		Carousel carousel = new Carousel();
		BeanUtils.copyProperties(params, carousel);
		
		this.updateById(carousel);
	}
	
	@Override
	public void deleteCarouselByIds(List<String> carouselIds) {
		this.deleteBatchIds(carouselIds);
	}

	@Override
	public void toggleStatus(ToggleStatusParam params) {
		Carousel carousel = new Carousel();
		BeanUtils.copyProperties(params, carousel);
		
		this.updateById(carousel);
	}

}
