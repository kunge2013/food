package com.cherry.shop.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.system.dto.ConfigBlockDto;
import com.cherry.shop.system.entity.Config;
import com.cherry.shop.system.mapper.ConfigMapper;
import com.cherry.shop.system.param.Block;
import com.cherry.shop.system.service.ConfigService;

/**
 * 配置Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

	@Override
	public ConfigBlockDto getConfigBlock(Block block) {
		// 查询配置列表
		List<Config> configs = this.selectList(this.getConfigByBlockWrapper(block.getBlock()));
		
		// 封装DTO
		return new ConfigBlockDto(block.getBlock(), configs);
	}

	@Override
	public void updateConfigBlock(ConfigBlockDto block) {
		// 1、删除之前的配置信息
		this.delete(this.getConfigByBlockWrapper(block.getBlock()));
		
		// 2、重建配置信息
		// 获取配置属性列表
		Collection<Config> configs = block.getConfigMapper().values();
		// 对配置属性列表进行流处理
		configs.stream()
			   .forEach(c -> {
				   c.setBlock(block.getBlock());// 配置块
				   c.setCreateTime(new Date());// 创建时间
			   });
		// 批量保存
		this.insertBatch(new ArrayList<>(configs));
	}
	
	/**
	 * 通过块配置名获取配置的条件
	 * 
	 * @param block
	 *            配置块信息
	 * @return EntityWrapper
	 */
	private EntityWrapper<Config> getConfigByBlockWrapper(String block) {
		return SQLHelper.build(Config.class).mustEq("block", block).geEntityWrapper();
	}

}
