package com.cherry.framework.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.cherry.framework.dto.Result;
import com.cherry.framework.service.FileService;
import com.cherry.framework.utils.Results;

/**
 * 本地文件服务实现类
 * 
 * @author 赵凡
 * @since 2017年11月26日
 * @version 1.0
 */
public class LocalFileServiceImpl implements FileService {

	private static final Logger logger = LoggerFactory.getLogger(LocalFileServiceImpl.class);
	
	private String fileServerSavepath;
	
	private String fileServerHttppath;
	
	public String getFileServerSavepath() {
		return fileServerSavepath;
	}

	public void setFileServerSavepath(String fileServerSavepath) {
		this.fileServerSavepath = fileServerSavepath;
	}

	public String getFileServerHttppath() {
		return fileServerHttppath;
	}

	public void setFileServerHttppath(String fileServerHttppath) {
		this.fileServerHttppath = fileServerHttppath;
	}

	@Override
	public Result uploadFile(MultipartFile file, String dirName) {
		String filePath = dirName + "/" + UUID.randomUUID().toString();
		try {
			File dest = new File(fileServerSavepath, filePath);
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			file.transferTo(dest);
		} catch (IllegalStateException | IOException e) {
			String errorMsg = "上传文件[" + file.getOriginalFilename() + "]失败！";
			logger.error(errorMsg, e);
			return Results.uploadError();
		}
		return Results.uploadOk(fileServerHttppath + filePath);
	}
	
}