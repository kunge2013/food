package com.cherry.shop.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.system.entity.Atta;

/**
 * 附件Service接口
 * 
 * @author 赵凡
 * @since 2018年1月15日
 * @version 1.0
 */
public interface AttaService extends IService<Atta> {
	
	public static final String ATTA_TYPE_PRODUCT = "P";

	/**
	 * 批量保存附件
	 * 
	 * @param masterId
	 *            主件ID
	 * @param type
	 *            附件类型
	 * @param attaUrls
	 *            附件URL列表
	 */
	void insertAttas(String masterId, String type, String... attaUrls);
	
	/**
	 * 批量保存或覆盖附件
	 * 
	 * @param masterId
	 *            主件ID
	 * @param type
	 *            附件类型
	 * @param attaUrls
	 *            附件URL列表
	 */
	void insertOrOverrideAttas(String masterId, String type, String... attaUrls);
	
	/**
	 * 批量删除附件
	 * 
	 * @param masterId
	 *            主件ID
	 * @param type
	 *            附件类型
	 */
	void deleteAttas(String masterId, String type);
	
	/**
	 * 查询附件列表
	 * 
	 * @param masterId
	 *            主件ID
	 * @param type
	 *            附件类型
	 * @return 附件列表
	 */
	List<Atta> listAttas(String masterId, String type);
	
	/**
	 * 查询附件URL列表
	 * 
	 * @param masterId
	 *            主件ID
	 * @param type
	 *            附件类型
	 * @return 附件URL列表
	 */
	List<String> listAttaUrls(String masterId, String type);
	
}
