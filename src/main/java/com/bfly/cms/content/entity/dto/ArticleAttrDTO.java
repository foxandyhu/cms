package com.bfly.cms.content.entity.dto;

import java.io.Serializable;

/**
 * 文章扩展属性类DTO
 * @author andy_hulibo@163.com
 * @date 2019/9/10 16:54
 */
public class ArticleAttrDTO implements Serializable {

    private int articleId;
    private String name;
    private String value;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
