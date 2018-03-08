package com.cherry.shop.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.system.dto.AddrDto;
import com.cherry.shop.system.entity.Addr;

/**
 * 地址Service接口
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
public interface AddrService extends IService<Addr> {

	/**
	 * 获取地址树
	 * 
	 * @return 地址树
	 */
	List<AddrDto> getAddrTree();

}
