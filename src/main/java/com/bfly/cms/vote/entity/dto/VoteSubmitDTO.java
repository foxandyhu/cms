package com.bfly.cms.vote.entity.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 提交投票DTO
 * @author andy_hulibo@163.com
 * @date 2019/9/11 20:18
 */
public class VoteSubmitDTO implements Serializable {

    private static final long serialVersionUID = 4176454395415592271L;

    public VoteSubmitDTO(){}

    /**
     * 子主题ID
     * @author andy_hulibo@163.com
     * @date 2019/9/11 20:19
     */
    private Integer subTopicId;

    /**
     * 投票选项ID
     * @author andy_hulibo@163.com
     * @date 2019/9/11 20:19
     */
    private List<Integer> items;

    /**
     * 投票文本内容
     * @author andy_hulibo@163.com
     * @date 2019/9/11 20:20
     */
    private String text;

    public int getSubTopicId() {
        return subTopicId;
    }

    public void setSubTopicId(int subTopicId) {
        this.subTopicId = subTopicId;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
