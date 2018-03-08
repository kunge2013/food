package com.cherry.shop.user.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.dto.Result;
import com.cherry.framework.shiro.utils.EncryptUtils;
import com.cherry.framework.shiro.utils.ShiroUtils;
import com.cherry.framework.utils.Constants;
import com.cherry.framework.utils.Results;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.user.dto.api.CompanyDto;
import com.cherry.shop.user.dto.api.GetInfoDto;
import com.cherry.shop.user.dto.api.ReceiverAddrDto;
import com.cherry.shop.user.entity.User;
import com.cherry.shop.user.mapper.UserMapper;
import com.cherry.shop.user.service.CompanyService;
import com.cherry.shop.user.service.ReceiverAddrService;
import com.cherry.shop.user.service.UserService;

/**
 * 用户Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月8日
 * @version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ReceiverAddrService receiverAddrService;
	
	@Value("${user.defaultImage}")
	private String defaultImage;
	
	@Override
	public User getCurUser() {
		return this.selectById(ShiroUtils.getLoginUserId());
	}

	@Override
	public User getLoginUser() {
		return ShiroUtils.getLoginUser();
	}

	@Override
	public User getUserByUsername(String username) {
		return this.selectOne(
			SQLHelper.build(User.class)
					 .mustEq("username", username)
					 .geEntityWrapper()
		);
	}

	@Override
	public User getUserByTelephone(String telephone) {
		return this.selectOne(
			SQLHelper.build(User.class)
					 .mustEq("telephone", telephone)
					 .geEntityWrapper()
		);
	}

	@Override
	public Result insertUser(User user) {
		// 信息初始化
		user.setRegistTime(new Date());// 注册时间
		user.setImage(defaultImage);// 设置默认头像
		user.setNickName(user.getUsername());// 设置默认昵称
		user.setSex(Constants.SEX_UNKNOW);// 性别
		user.setReturnMoney(new BigDecimal(0));// 返现
		user.setWalletMoney(new BigDecimal(0));// 钱包
		
		// 验证手机号是否已绑定
		String telephone = user.getTelephone();
		if (StringUtils.isNotBlank(telephone)) {
			User u = getUserByTelephone(telephone);
			if (u != null) {
				return Results.telExistError();
			}
		}
		
		// 校验推荐人是否合法
		String referrerTel = user.getReferrer();
		if (StringUtils.isNoneBlank(referrerTel)) {
			// 推荐人不能是自己 
			if (referrerTel.trim().equals(telephone.trim())) {
				return Results.refValidError();
			}
			
			User u = getUserByTelephone(referrerTel);
			if (u == null) {
				return Results.refTelNonExistError();
			}
		}
		
		// 加密
		EncryptUtils.encrypt(user);
		
		// 保存用户信息
		this.insert(user);
		
		return Results.opOk();
	}

	@Override
	public Result updatePasswordByTelephone(String telephone, String password) {
		// 验证手机号是否已绑定
		User user = getUserByTelephone(telephone);
		if (user == null) {
			return Results.telNonExistError();
		}
		
		// 加密
		user.setPassword(password);
		EncryptUtils.encrypt(user);
		
		this.updateById(user);
		
		return Results.opOk();
	}

	@Override
	public GetInfoDto getInfo() {
		// 创建Dto
		GetInfoDto dto = new GetInfoDto();
		// 查询当前最新的用户信息
		User user = this.getCurUser();
		// 复制属性
		BeanUtils.copyProperties(user, dto);
		
		// 获取当前用户的企业ID
		String companyId = user.getCompanyId();
		if (StringUtils.isNotBlank(companyId)) {
			// 查询当前用户所属企业
			CompanyDto company = companyService.getCompany(companyId);
			dto.setCompany(company);
		}
		
		// 查询默认收货地址
		ReceiverAddrDto defaultAddr = receiverAddrService.getDefaultReceiverAddr(user);
		dto.setReceiverAddr(defaultAddr);
		return dto;
	}

}
