package com.cherry.shop.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.system.dto.ConfigBlockDto;
import com.cherry.shop.system.entity.Config;
import com.cherry.shop.system.param.Block;

/**
 * 配置Service接口
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
public interface ConfigService extends IService<Config> {

	/**
	 * 查询配置块
	 * 
	 * @param block
	 *            配置块名称
	 * @return 配置块
	 */
	ConfigBlockDto getConfigBlock(Block block);
	
	/**
	 * 更新配置块
	 * 
	 * @param block
	 *            配置块
	 */
	void updateConfigBlock(ConfigBlockDto block);
	
}
