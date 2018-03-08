package com.cherry.shop.security.dto;

import java.io.Serializable;
import java.util.List;

import com.cherry.framework.dto.OptionDto;

import lombok.Data;

/**
 * 系统用户角色信息DTO
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
@Data
public class SysUserRolesDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6350310790792840716L;
	
	private String id;// 系统用户ID
	
	private String username;// 系统用户名
	
	private List<String> roleIds;// 系统用户拥有的角色ID列表
	
	private List<OptionDto> roleOptions;// 角色选项

}
