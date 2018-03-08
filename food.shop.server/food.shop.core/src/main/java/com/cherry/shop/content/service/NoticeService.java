package com.cherry.shop.content.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cherry.framework.param.ToggleStatusParam;
import com.cherry.shop.content.dto.NoticeDto;
import com.cherry.shop.content.entity.Notice;
import com.cherry.shop.content.param.AddNoticeParam;
import com.cherry.shop.content.param.ListNoticeParam;
import com.cherry.shop.content.param.UpdateNoticeParam;

/**
 * 公告Service接口
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
public interface NoticeService extends IService<Notice> {
	
	/**
	 * 查询公告列表
	 * 
	 * @param page
	 *            分页参数
	 * @param params
	 *            过滤参数
	 * @return 公告列表
	 */
	Page<NoticeDto> listNotice(Page<Notice> page, ListNoticeParam params);
	
	/**
	 * 查询最新公告列表
	 * 
	 * @return 最新公告列表
	 */
	List<String> listNotice();
	
	/**
	 * 查询公告信息
	 * 
	 * @param noticeId
	 *            公告ID
	 * @return 公告信息
	 */
	Notice getNotice(String noticeId);
	
	/**
	 * 添加公告
	 * 
	 * @param param
	 *            公告信息
	 */
	void addNotice(AddNoticeParam param);

	/**
	 * 更新公告
	 * 
	 * @param param
	 *            公告信息
	 */
	void updateNotice(UpdateNoticeParam param);

	/**
	 * 批量删除公告
	 * 
	 * @param noticeIds
	 *            公告ID列表
	 */
	void deleteNoticeByIds(List<String> noticeIds);

	/**
	 * 切换公告状态
	 * 
	 * @param params
	 *            切换参数
	 */
	void toggleStatus(ToggleStatusParam params);

}
