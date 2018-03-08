package com.cherry.shop.content.service;

import java.util.List;
import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.content.entity.Hotline;
import com.cherry.shop.content.param.AddHotlineParam;
import com.cherry.shop.content.param.ListHotlineParam;
import com.cherry.shop.content.param.UpdateHotlineParam;

/**
 * 服务热线Service接口
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
public interface HotlineService extends IService<Hotline> {
	
	/**
	 * 查询服务热线列表
	 * 
	 * @param params
	 *            查询参数
	 * @return 服务热线列表
	 */
	List<Hotline> listHotline(ListHotlineParam params);
	
	/**
	 * 查询服务热线
	 * 
	 * @param hotlineId
	 *            服务热线ID
	 * @return 服务热线
	 */
	Hotline getHotline(String hotlineId);
	
	/**
	 * 添加服务热线
	 * 
	 * @param param
	 *            服务热线信息
	 */
	void addHotline(AddHotlineParam param);

	/**
	 * 更新服务热线
	 * 
	 * @param param
	 *            服务热线信息
	 */
	void updateHotline(UpdateHotlineParam param);

	/**
	 * 批量删除服务热线
	 * 
	 * @param hotlineIds
	 *            服务热线ID列表
	 */
	void deleteHotlines(List<String> hotlineIds);
	
}
