package com.cherry.shop.user.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.Constants;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.user.dto.api.CompanyDto;
import com.cherry.shop.user.entity.Company;
import com.cherry.shop.user.entity.User;
import com.cherry.shop.user.mapper.CompanyMapper;
import com.cherry.shop.user.param.api.SearchCompanyParam;
import com.cherry.shop.user.service.CompanyService;
import com.cherry.shop.user.service.UserService;

/**
 * 企业Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

	@Autowired
	private UserService userService;
	
	@Override
	public String addCompany(String name) {
		// 查询当前执行操作的用户
		User user = userService.getCurUser();
		// 企业管理者只能绑定一个企业
		if (Constants.ROLE_MANAGER.equals(user.getRole())) {
			this.deleteById(user.getCompanyId());
		}
		
		// 创建企业
		Company company = new Company();
		// 设置参数
		company.setName(name);
		company.setCreateTime(new Date());
		company.setManagerId(user.getId());
		company.setTelephone(user.getTelephone());
		// 添加企业
		insert(company);
		
		// 更新用户的企业信息
		user.setCompanyId(company.getId());
		user.setRole(Constants.ROLE_MANAGER);// 主动创建企业的是管理者
		userService.updateById(user);
		
		return company.getId();
	}

	@Override
	public void bindCompany(String companyId) {
		User user = userService.getCurUser();
		// 企业管理者只能绑定一个企业
		if (Constants.ROLE_MANAGER.equals(user.getRole())) {
			this.deleteById(user.getCompanyId());
		}
		
		user.setCompanyId(companyId);
		user.setRole(Constants.ROLE_EMPLOYEE);// 加入企业的是员工
		userService.updateById(user);
	}

	@Override
	public Page<Company> searchCompany(SearchCompanyParam param) {
		return this.selectPage(
			param.toPage(Company.class), 
			SQLHelper.build(Company.class)
					 .like("name", param.getSearch())
					 .geEntityWrapper()
		);
	}

	@Override
	public CompanyDto getCompany(String companyId) {
		// 创建Dto
		CompanyDto dto = new CompanyDto();
		// 查询指定企业ID的企业信息
		Company company = this.selectById(companyId);
		// 复制属性
		BeanUtils.copyProperties(company, dto);
		// 企业主ID
		String managerId = company.getManagerId();
		// 查询管理者信息
		User manager = userService.selectById(managerId);
		dto.setManagerName(manager.getNickName());
		return dto;
	}

}
