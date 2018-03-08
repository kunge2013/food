package com.cherry.shop.content.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.content.entity.Hotline;
import com.cherry.shop.content.mapper.HotlineMapper;
import com.cherry.shop.content.param.AddHotlineParam;
import com.cherry.shop.content.param.ListHotlineParam;
import com.cherry.shop.content.param.UpdateHotlineParam;
import com.cherry.shop.content.service.HotlineService;

/**
 * 服务热线Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Service
public class HotlineServiceImpl  extends ServiceImpl<HotlineMapper, Hotline> implements HotlineService {

	@Override
	public List<Hotline> listHotline(ListHotlineParam params) {
		return this.selectList(
			SQLHelper.build(Hotline.class)
					 .orderBy(params.getOrderByField(), params.isAsc())
					 .geEntityWrapper()
		);
	}
	
	@Override
	public Hotline getHotline(String hotlineId) {
		return this.selectById(hotlineId);
	}
	
	@Override
	public void addHotline(AddHotlineParam param) {
		Hotline hotline = new Hotline();
		BeanUtils.copyProperties(param, hotline);
		hotline.setCreateTime(new Date());
		this.insert(hotline);
		
	}
	@Override
	public void updateHotline(UpdateHotlineParam param) {
		Hotline hotline = new Hotline();
		BeanUtils.copyProperties(param, hotline);
		this.updateById(hotline);
	}

	@Override
	public void deleteHotlines(List<String> hotlineIds) {
		this.deleteBatchIds(hotlineIds);
	}

}
