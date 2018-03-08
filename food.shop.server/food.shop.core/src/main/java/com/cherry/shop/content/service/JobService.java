package com.cherry.shop.content.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.content.entity.Job;
import com.cherry.shop.content.param.AddJobParam;
import com.cherry.shop.content.param.ListJobParam;
import com.cherry.shop.content.param.UpdateJobParam;

/**
 * 人才招聘接口
 * @author cl
 * 
 */
public interface JobService extends IService<Job>{

	/**
	 * 查询招聘列表
	 */
	List<Job> listJob(ListJobParam params);
	
	
	/**
	 * 添加招聘
	 * 
	 * @param params
	 *            添加参数
	 */
	void addJob(AddJobParam params);
	
	/**
	 * 更新招聘
	 * 
	 * @param params
	 *            更新参数
	 */
	void updateJob(UpdateJobParam params);
	
	/**
	 * 批量删除招聘
	 * 
	 * @param roleIds
	 *            要删除的招聘的ID列表
	 */
	void deleteJobByIds(List<String> jobIds);
	
	/**
	 * 通过招聘ID查询招聘
	 * 
	 * @param jobId
	 *            招聘ID
	 * @return 招聘
	 */
	Job getJob(String jobId);
	

}
