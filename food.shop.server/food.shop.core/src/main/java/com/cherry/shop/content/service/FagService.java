package com.cherry.shop.content.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.content.entity.Fag;
import com.cherry.shop.content.param.AddFagParam;
import com.cherry.shop.content.param.ListFagParam;
import com.cherry.shop.content.param.UpdateFagParam;

/**
 * 问题接口
 * @author cl
 * 
 */
public interface FagService extends IService<Fag>{

	/**
	 * 查询问题列表
	 */
	Page<Fag> listFag(Page<Fag> page,ListFagParam params);
	
	
	/**
	 * 添加问题
	 * 
	 * @param params
	 *            需要的参数
	 */
	void addFag(AddFagParam params);
	
	/**
	 * 更新问题
	 * 
	 * @param params
	 *            更新参数
	 */
	void updateFag(UpdateFagParam params);
	
	/**
	 * 批量删除问题
	 * 
	 * @param roleIds
	 *            要删除的问题的ID列表
	 */
	void deleteFagByIds(List<String> fagIds);
	
	/**
	 * 通过ID查询问题
	 * 
	 * @param jobId
	 *            问题ID
	 * @return 问题
	 */
	Fag getFag(String fagId);
	

}
