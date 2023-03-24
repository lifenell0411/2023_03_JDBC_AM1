package com.KoreaIT.example.JAM.service;

import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.dao.ArticleDao;
import com.KoreaIT.example.JAM.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService() {
		this.articleDao = Container.articleDao;
	}

	public int doWrite(String title, String body) {
		return articleDao.doWrite(title, body);
	}

	public Map<String, Object> getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public int getArticlesCount(int id) {
		return articleDao.getArticlesCount(id);
	}

	public void doDelete(int id) {
		articleDao.doDelete(id);
	}

	public void doModify(int id, String title, String body) {
		articleDao.doModify(id, title, body);

	}

	public List<Article> getArticlesCount() {
		return articleDao.getArticles();
	}

}