package com.cherry.shop.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.content.entity.Job;
import com.cherry.shop.content.mapper.JobMapper;
import com.cherry.shop.content.param.AddJobParam;
import com.cherry.shop.content.param.ListJobParam;
import com.cherry.shop.content.param.UpdateJobParam;
import com.cherry.shop.content.service.JobService;

@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService{

	@Override
	public List<Job> listJob(ListJobParam params) {
		// TODO Auto-generated method stub
		return this.selectList(SQLHelper.build(Job.class)
				.like("position", params.getPosition())
				.eq("show", params.getShow())
				.orderBy("publishTime", params.isAsc())
				.geEntityWrapper()
				);
	}

	@Override
	public void addJob(AddJobParam params) {
		Job job = new Job();
		BeanUtils.copyProperties(params, job);
		job.setPublishTime(new Date());
		this.insert(job);
	}

	@Override
	public void updateJob(UpdateJobParam params) {
		Job job = new Job();
		BeanUtils.copyProperties(params, job);
		this.updateById(job);
		
	}

	@Override
	public void deleteJobByIds(List<String> jobIds) {
		//批量删除招聘
		this.deleteBatchIds(jobIds);
		
	}

	@Override
	public Job getJob(String jobId) {
		// TODO Auto-generated method stub
		return this.selectById(jobId);
	}

}
