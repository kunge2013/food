package com.cherry.shop.content.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.cherry.framework.dto.Result;
import com.cherry.framework.param.BatchDeleteParam;
import com.cherry.framework.param.GetParam;
import com.cherry.framework.service.FileService;
import com.cherry.framework.utils.Constants;
import com.cherry.framework.utils.Results;
import com.cherry.shop.content.entity.Article;
import com.cherry.shop.content.param.AddArticleParam;
import com.cherry.shop.content.param.ListArticleParam;
import com.cherry.shop.content.param.UpdateArticleParam;
import com.cherry.shop.content.service.ArticleService;

/**
 * 文章控制器
 * 
 * @author 崔力
 * @since 2018年1月19日
 * @version 1.0
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

	private final static Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private FileService fileService;
	/**
	 * 显示文章页面
	 * 
	 * @param mav
	 *            
	 * @return ModelAndView
	 */
	@RequestMapping("/grid")
	public ModelAndView grid(ModelAndView mav) {
		mav.setViewName("content/articleGrid");
		return mav;
	}
	
	/**
	 * 查询文章列表      
	 * @return 响应结果
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(Page<Article> page ,ListArticleParam params) {
		try{
			
			Page<Article> articlePage = articleService.listArticle(page, params);
			return Results.pageResult(articlePage);
		}catch(Exception e){
			logger.error("查询文章列表失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 根据id查询文章信息
	 * @param param
	 * @return 查询结果
	 */
	@RequestMapping("/get")
	@ResponseBody
	public Result list(GetParam param){
		try{
			Article article = articleService.getArticle(param.getId());
			return Results.queryOk(article);
		}catch(Exception e){
			logger.error("根据id查询文章失败！param:" + param.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 新增文章信息
	 * @param params
	 * @param r
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@Valid @RequestBody AddArticleParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			articleService.addArticle(params);
			
			return Results.addOk();
		} catch (Exception e) {
			logger.error("添加文章失败！params:" + params.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 更新文章信息
	 * @param params
	 * @param r
	 * @return 更新结果
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@Valid @RequestBody UpdateArticleParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			articleService.updateArticle(params);
			
			return Results.updateOk();
		} catch (Exception e) {
			logger.error("更新文章失败params:" + params.toString(), e);
			return Results.updateError();
		}
	}
	
	/**
	 * 
	 * 
	 * @param params
	 *            删除文章请求参数
	 * @param r
	 *            参数验证结果
	 * @return 删除结果
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(@Valid @RequestBody BatchDeleteParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			articleService.deleteArticleByIds(params.getIds());
			
			return Results.delOk();
		} catch (Exception e) {
			logger.error("删除文章失败！params:" + params.toString(), e);
			return Results.delError();
		}
	}
	
	/**
	 * 上传文章标题图标
	 * 
	 * @param file
	 *            MultipartFile
	 * @return 上传结果
	 */
	@RequestMapping("/uploadImage")
	@ResponseBody
	public Result uploadImage(MultipartFile file) {
		return fileService.uploadFile(file, Constants.FILE_DIR_ARTICLE);
	}
}
