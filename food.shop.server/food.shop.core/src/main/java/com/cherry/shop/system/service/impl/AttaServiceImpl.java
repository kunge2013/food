package com.cherry.shop.system.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.system.entity.Atta;
import com.cherry.shop.system.mapper.AttaMapper;
import com.cherry.shop.system.service.AttaService;

/**
 * 附件Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月15日
 * @version 1.0
 */
@Service
public class AttaServiceImpl extends ServiceImpl<AttaMapper, Atta> implements AttaService {

	@Override
	public void insertAttas(String masterId, String type, String... attaUrls) {
		if (ArrayUtils.isNotEmpty(attaUrls)) {
			List<Atta> attas = new ArrayList<>();
			Date uploadTime = new Date();
			for (String attaUrl : attaUrls) {
				Atta atta = new Atta();
				atta.setMasterId(masterId);
				atta.setType(type);
				atta.setUrl(attaUrl);
				atta.setUploadTime(uploadTime);
				attas.add(atta);
			}
			this.insertBatch(attas);
		}
	}

	@Override
	public void insertOrOverrideAttas(String masterId, String type, String... attaUrls) {
		// 先删除
		deleteAttas(masterId, type);
		
		// 后重建
		insertAttas(masterId, type, attaUrls);
	}

	@Override
	public void deleteAttas(String masterId, String type) {
		this.delete(buildAttaEntityWrapper(masterId, type));
	}

	@Override
	public List<Atta> listAttas(String masterId, String type) {
		return this.selectList(buildAttaEntityWrapper(masterId, type));
	}
	
	// 构建附件类型条件
	private static EntityWrapper<Atta> buildAttaEntityWrapper(String masterId, String type) {
		return SQLHelper.build(Atta.class)
	 	 		 .mustEq("masterId", masterId)
	 	 		 .mustEq("type", type)
	 	 		 .geEntityWrapper();
	}

	@Override
	public List<String> listAttaUrls(String masterId, String type) {
		return listAttas(masterId, type).stream().map(Atta::getUrl).collect(toList());
	}

}
