package com.cherry.shop.content.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.param.ToggleStatusParam;
import com.cherry.framework.utils.Constants;
import com.cherry.framework.utils.DateUtil;
import com.cherry.framework.utils.EntityConvertor;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.content.dto.NoticeDto;
import com.cherry.shop.content.entity.Notice;
import com.cherry.shop.content.mapper.NoticeMapper;
import com.cherry.shop.content.param.AddNoticeParam;
import com.cherry.shop.content.param.ListNoticeParam;
import com.cherry.shop.content.param.UpdateNoticeParam;
import com.cherry.shop.content.service.NoticeService;

/**
 * 公告Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Service
public class NoticeServiceImpl  extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

	@Override
	public Page<NoticeDto> listNotice(Page<Notice> page, ListNoticeParam params) {
		// 查询公告列表
		Page<Notice> notices = this.selectPage(page, 
				SQLHelper.build(Notice.class)
						 .eqStr("status", params.getStatus())
						 .geEntityWrapper()
			);
		
		// 转换Page
		return EntityConvertor.convertPage(notices, u -> {
			// 复制属性
			NoticeDto dto = new NoticeDto();
			BeanUtils.copyProperties(u, dto);
			// 设置是否激活
			dto.setActive(DateUtil.betweenDay(dto.getStartDate(), dto.getEndDate()) && Constants.STATUS_ENABLE.equals(dto.getStatus()));
			return dto;
		});
	}
	
	@Override
	public List<String> listNotice() {
		return this.selectList(
			SQLHelper.build(Notice.class)
					 .eq("status", "Y")
					 .orderBy("order", false)
					 .geEntityWrapper()
		).stream()
		 .filter(p -> DateUtil.betweenDay(p.getStartDate(), p.getEndDate()))
		 .map(Notice::getContent)
		 .collect(toList());
	}

	@Override
	public Notice getNotice(String noticeId) {
		return this.selectById(noticeId);
	}

	@Override
	public void addNotice(AddNoticeParam param) {
		Notice notice = new Notice();
		BeanUtils.copyProperties(param, notice);
		notice.setCreateTime(new Date());
		notice.setStatus(Constants.STATUS_ENABLE);
		this.insert(notice);
	}

	@Override
	public void updateNotice(UpdateNoticeParam param) {
		Notice notice = new Notice();
		BeanUtils.copyProperties(param, notice);
		this.updateById(notice);
	}
	
	@Override
	public void deleteNoticeByIds(List<String> noticeIds) {
		this.deleteBatchIds(noticeIds);
	}

	@Override
	public void toggleStatus(ToggleStatusParam params) {
		Notice notice = new Notice();
		BeanUtils.copyProperties(params, notice);
		
		this.updateById(notice);
	}
	
}
