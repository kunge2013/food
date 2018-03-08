package com.cherry.shop.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.content.entity.Fag;
import com.cherry.shop.content.mapper.FagMapper;
import com.cherry.shop.content.param.AddFagParam;
import com.cherry.shop.content.param.ListFagParam;
import com.cherry.shop.content.param.UpdateFagParam;
import com.cherry.shop.content.service.FagService;

/**
 * 接口实现类
 * @author cl
 *
 */
@Service
public class FagServiceImpl extends ServiceImpl<FagMapper, Fag> implements FagService{

	
	@Override
	public Page<Fag> listFag(Page<Fag> page,ListFagParam params) {
		
		return this.selectPage(page,(SQLHelper.build(Fag.class)
				.like("question", params.getQuestion())
				.orderBy("createTime", params.isAsc())
				.geEntityWrapper()));
				
	}

	@Override
	public void addFag(AddFagParam params) {
		Fag fag = new Fag();
		BeanUtils.copyProperties(params, fag);
		fag.setCreateTime(new Date());
		this.insert(fag);
	}

	@Override
	public void updateFag(UpdateFagParam params) {
		Fag fag = new Fag();
		BeanUtils.copyProperties(params, fag);
		this.updateById(fag);
		
	}

	@Override
	public void deleteFagByIds(List<String> fagIds) {
		this.deleteBatchIds(fagIds);
		
	}

	@Override
	public Fag getFag(String fagId) {
		// TODO Auto-generated method stub
		return this.selectById(fagId);
	}

}
