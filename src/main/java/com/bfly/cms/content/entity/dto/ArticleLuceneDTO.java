package com.bfly.cms.content.entity.dto;

import com.bfly.common.IDEncrypt;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章Lucene搜索DTO
 *
 * @author andy_hulibo@163.com
 * @date 2019/10/28 11:23
 */
public class ArticleLuceneDTO implements Serializable {

    private static final long serialVersionUID = -6984554830159488765L;

    private int id;
    private String title;
    private String txt;
    private Date postDate;
    private String channelPath;
    private String titleImg;
    private int status;
    private String summary;

    /**
     * 加密后的ID字符串
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/4 11:43
     */
    public String getIdStr() {
        return IDEncrypt.encode(getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getChannelPath() {
        return channelPath;
    }

    public void setChannelPath(String channelPath) {
        this.channelPath = channelPath;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
