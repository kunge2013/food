package com.cherry.shop.user.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.param.ToggleSwitchParam;
import com.cherry.framework.utils.EntityConvertor;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.user.dto.api.ReceiverAddrDto;
import com.cherry.shop.user.entity.ReceiverAddr;
import com.cherry.shop.user.entity.User;
import com.cherry.shop.user.mapper.ReceiverAddrMapper;
import com.cherry.shop.user.param.api.AddReceiverAddrParam;
import com.cherry.shop.user.param.api.UpdateReceiverAddrParam;
import com.cherry.shop.user.service.ReceiverAddrService;
import com.cherry.shop.user.service.UserService;

/**
 * 收货地址Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@Service
public class ReceiverAddrServiceImpl extends ServiceImpl<ReceiverAddrMapper, ReceiverAddr> implements ReceiverAddrService {

	@Autowired
	private UserService userService;
	
	@Override
	public List<ReceiverAddrDto> listMyReceiverAddr() {
		// 查询当前登录用户
		User user = userService.getCurUser();
		// 查询私有地址和企业共享地址列表
		List<ReceiverAddr> addrs = this.selectList(
			SQLHelper.build(ReceiverAddr.class)
					 .eqStr("companyId", user.getCompanyId())// 如果企业ID存在，添加OR条件
					 .or()
					 .eqStr("creatorId", user.getId())// 创建者
					 .geEntityWrapper()
		);
		
		// 找到默认地址
		Integer uIdx = null;// 个人默认地址索引
		Integer cIdx = null;// 企业默认地址索引
		for (int i = 0; i < addrs.size(); i++) {
			if (addrs.get(i).isDefaultAddr()) {
				if (StringUtils.isBlank(addrs.get(i).getCompanyId())) {// 个人地址
					uIdx = i;
				} else {
					cIdx = i;
				}
			}
		}
		if (uIdx != null) {// 有个人默认地址
			if (cIdx != null) {// 有企业默认地址
				addrs.get(cIdx).setDefaultAddr(false);
			}
		}
		
		// 转换Dto
		List<ReceiverAddrDto> dtos = EntityConvertor.convertList(addrs, a -> {
			return convertDto(a);
		});
		
		return dtos;
	}

	@Override
	public ReceiverAddrDto getReceiverAddr(String addrId) {
		ReceiverAddr addr = this.selectById(addrId);
		ReceiverAddrDto dto = convertDto(addr);
		return dto;
	}

	// 转换为Dto
	private ReceiverAddrDto convertDto(ReceiverAddr addr) {
		ReceiverAddrDto dto = new ReceiverAddrDto();
		BeanUtils.copyProperties(addr, dto);
		String region = addr.getCity() + " " + addr.getCounty() + " " + addr.getTown();
		dto.setRegion(region);
		return dto;
	}

	@Override
	public void addReceiverAddr(AddReceiverAddrParam param) {
		// 复制属性
		ReceiverAddr addr = new ReceiverAddr();
		BeanUtils.copyProperties(param, addr);
		
		// 业务处理
		process(addr, param.getRegion());
		
		// 保存地址
		this.insert(addr);
	}

	// 处理
	private void process(ReceiverAddr addr, String region) {
		// 设置创建时间
		addr.setCreateTime(new Date());
		// 切分地址
		String[] addrParts = StringUtils.split(region, " ");
		addr.setCity(addrParts[0]);
		addr.setCounty(addrParts[1]);
		addr.setTown(addrParts[2]);
		addr.setAddress(addr.getCity() + addr.getCounty() + addr.getTown() + addr.getStreet());
		
		// 查询当前登录用户
		User user = userService.getCurUser();
		addr.setCompanyId(user.getCompanyId());
		addr.setCreatorId(user.getId());
		
		// 默认地址处理
		if (addr.isDefaultAddr()) {
			cancelDefaultReceiverAddr(user);
		}
	}

	// 取消默认地址
	private void cancelDefaultReceiverAddr(User user) {
		if (StringUtils.isNotEmpty(user.getCompanyId())) {// 企业用户
			// 取消企业默认地址
			cancelAddrByCompanyId(user.getCompanyId());
		}
		// 取消个人默认地址
		cancelAddrByUserId(user.getId());
	}

	@Override
	public void updateReceiverAddr(UpdateReceiverAddrParam param) {
		// 复制属性
		ReceiverAddr addr = new ReceiverAddr();
		BeanUtils.copyProperties(param, addr);
		
		// 业务处理
		process(addr, param.getRegion());
		
		// 保存地址
		this.updateById(addr);
	}

	@Override
	public void cancelAddrByUserId(String userId) {
		ReceiverAddr addr = new ReceiverAddr();
		addr.setDefaultAddr(false);
		this.update(addr, SQLHelper.build(ReceiverAddr.class).mustEq("creatorId", userId).geEntityWrapper());
	}

	@Override
	public void cancelAddrByCompanyId(String companyId) {
		ReceiverAddr addr = new ReceiverAddr();
		addr.setDefaultAddr(false);
		this.update(addr, SQLHelper.build(ReceiverAddr.class).mustEq("companyId", companyId).geEntityWrapper());
	}

	@Override
	public void toggleDefaultReceiverAddr(ToggleSwitchParam param) {
		User user = userService.getCurUser();
		if (param.getOpen()) {// 设置默认地址
			ReceiverAddr addr = new ReceiverAddr();
			addr.setId(param.getId());
			addr.setDefaultAddr(param.getOpen());
			addr.setCompanyId(user.getCompanyId());
			this.updateById(addr);
		} else {// 取消默认地址
			cancelDefaultReceiverAddr(user);
		}
	}

	@Override
	public ReceiverAddrDto getDefaultReceiverAddr(User user) {
		// 优先加载个人默认地址
		ReceiverAddr addr = this.selectOne(
			SQLHelper.build(ReceiverAddr.class)
					 .mustEq("creatorId", user.getId())
					 .geEntityWrapper()
		);
		// 其次加载企业默认地址
		if (addr == null && StringUtils.isNotBlank(user.getCompanyId())) {
			addr = this.selectOne(
				SQLHelper.build(ReceiverAddr.class)
						 .mustEq("companyId", user.getCompanyId())
						 .geEntityWrapper()
			);
		}
		
		return convertDto(addr);
	}

}
