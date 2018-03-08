package com.cherry.shop.user.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.framework.param.ToggleSwitchParam;
import com.cherry.shop.user.dto.api.ReceiverAddrDto;
import com.cherry.shop.user.entity.ReceiverAddr;
import com.cherry.shop.user.entity.User;
import com.cherry.shop.user.param.api.AddReceiverAddrParam;
import com.cherry.shop.user.param.api.UpdateReceiverAddrParam;

/**
 * 收货地址Service接口
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
public interface ReceiverAddrService extends IService<ReceiverAddr> {

	/**
	 * 查询我的收货地址列表，个人地址的默认地址优先级较高
	 * 
	 * @return 我的收货地址列表
	 */
	List<ReceiverAddrDto> listMyReceiverAddr();
	
	/**
	 * 通过收货地址ID查询收货地址信息
	 * 
	 * @param addrId
	 *            收货地址ID
	 * @return 收货地址信息
	 */
	ReceiverAddrDto getReceiverAddr(String addrId);
	
	/**
	 * 添加收货地址
	 * 
	 * @param param
	 *            收货地址信息
	 */
	void addReceiverAddr(AddReceiverAddrParam param);
	
	/**
	 * 修改收货地址
	 * 
	 * @param param
	 *            收货地址信息
	 */
	void updateReceiverAddr(UpdateReceiverAddrParam param);
	
	/**
	 * 取消个人默认地址
	 * 
	 * @param userId
	 *            用户ID
	 */
	void cancelAddrByUserId(String userId);
	
	/**
	 * 取消企业默认地址
	 * 
	 * @param companyId
	 *            企业ID
	 */
	void cancelAddrByCompanyId(String companyId);

	/**
	 * 设置/取消默认收货地址
	 * 
	 * @param param
	 *            请求参数
	 */
	void toggleDefaultReceiverAddr(ToggleSwitchParam param);

	/**
	 * 查询指定用户的默认地址
	 * 
	 * @param user
	 *            用户信息
	 * @return 指定用户的默认地址
	 */
	ReceiverAddrDto getDefaultReceiverAddr(User user);

}
