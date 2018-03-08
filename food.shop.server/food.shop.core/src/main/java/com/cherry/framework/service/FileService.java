package com.cherry.framework.service;

import org.springframework.web.multipart.MultipartFile;

import com.cherry.framework.dto.Result;

/**
 * 文件服务接口
 * 
 * @author 赵凡
 * @since 2017年11月26日
 * @version 1.0
 */
public interface FileService {

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            MultipartFile
	 * @param dirName
	 *            存放的子目录名称
	 * @return 上传结果
	 */
	public Result uploadFile(MultipartFile file, String dirName);
	
}
