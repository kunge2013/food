package com.cherry.shop.content.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cherry.shop.content.entity.Article;
import com.cherry.shop.content.param.AddArticleParam;
import com.cherry.shop.content.param.ListArticleParam;
import com.cherry.shop.content.param.UpdateArticleParam;

/**
 * 服务中心（文章）接口
 * @author cl
 * 
 */
public interface ArticleService extends IService<Article>{

	/**
	 * 查询文章列表
	 */
	Page<Article> listArticle(Page<Article> page,ListArticleParam params);
	
	
	/**
	 * 添加文章
	 * 
	 * @param params
	 *            添加参数
	 */
	void addArticle(AddArticleParam params);
	
	/**
	 * 更新文章
	 * 
	 * @param params
	 *            更新参数
	 */
	void updateArticle(UpdateArticleParam params);
	
	/**
	 * 批量删除文章
	 * 
	 * @param roleIds
	 *            要删除的文章的ID集合
	 */
	void deleteArticleByIds(List<String> articleIds);
	
	/**
	 * 通过ID查询文章
	 * 
	 * @param articleId
	 *            文章ID
	 * @return 文章
	 */
	Article getArticle(String articleId);
	

}
