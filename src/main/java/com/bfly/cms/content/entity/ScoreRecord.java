package com.bfly.cms.content.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 评分项
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:45
 */
@Entity
@Table(name = "score_record")
public class ScoreRecord implements Serializable {

    private static final long serialVersionUID = -368864155141029516L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 评分文章
     */
    @Column(name = "article_id")
    private int articleId;

    /**
     * 评分项
     */
    @Column(name = "score_item_id")
    private int scoreItemId;

    /**
     * 评分总量
     */
    @Column(name = "score_count")
    private int scoreCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getScoreItemId() {
        return scoreItemId;
    }

    public void setScoreItemId(int scoreItemId) {
        this.scoreItemId = scoreItemId;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount = scoreCount;
    }
}