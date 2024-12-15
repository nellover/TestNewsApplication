package com.example.tp_news_apptest.ApiRetr;

import com.example.tp_news_apptest.NewsArticles.Article;

import java.util.List;

public class NewsResponse {
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}