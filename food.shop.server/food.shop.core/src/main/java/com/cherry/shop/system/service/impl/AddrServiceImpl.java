package com.cherry.shop.system.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.framework.utils.TreeUtils;
import com.cherry.shop.system.dto.AddrDto;
import com.cherry.shop.system.entity.Addr;
import com.cherry.shop.system.mapper.AddrMapper;
import com.cherry.shop.system.service.AddrService;

/**
 * 地址Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@Service
public class AddrServiceImpl extends ServiceImpl<AddrMapper, Addr> implements AddrService {

	@SuppressWarnings("unchecked")
	@Override
	public List<AddrDto> getAddrTree() {
		// 查询地址信息列表
		List<AddrDto> addrs = this.selectList(SQLHelper.buildEmptyWrapper(Addr.class)).stream().map(a -> {
			AddrDto dto = new AddrDto();
			BeanUtils.copyProperties(a, dto);
			return dto;
		}).collect(toList());
		
		return (List<AddrDto>) TreeUtils.toTree(addrs);
	}

}
