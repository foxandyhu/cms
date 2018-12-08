package com.bfly.cms.statistic.entity;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.member.entity.Member;

import java.text.NumberFormat;
import java.util.Date;

/**
 * @author Tom
 */
public class WorkLoadStatistic {
    public static final String PERCENTSIGN = "%";
    public static final double COEFFICIENT = 0.8;

    /**
     * 统计模式
     */
    public enum CmsWorkLoadStatisticGroup {
        day, week, month, year
    }

    public enum CmsWorkLoadStatisticDateKind {
        release, check
    }

    public String getPercent() {
        return NumberFormat.getPercentInstance().format(
                count / (total == 0 ? 1.0 : total + 0.0));
    }

    public String getBarWidth() {
        return (int) ((Integer.parseInt(getPercent().replace(PERCENTSIGN, ""))) * COEFFICIENT)
                + PERCENTSIGN;
    }


    public WorkLoadStatistic() {
        super();
    }

    public WorkLoadStatistic(Channel channel, Date date, Long count) {
        super();
        this.channel = channel;
        this.date = date;
        this.count = count;
    }

    public WorkLoadStatistic(Channel channel, Member author,
                             Member reviewer, Long count) {
        super();
        this.channel = channel;
        this.author = author;
        this.reviewer = reviewer;
        this.count = count;
    }

    public WorkLoadStatistic(Member author, Member reviewer, Date date,
                             Long count) {
        super();
        this.author = author;
        this.reviewer = reviewer;
        this.date = date;
        this.count = count;
    }

    public WorkLoadStatistic(String description, Channel channel, Member author,
                             Member reviewer, Date date, Long count, Long total) {
        super();
        this.channel = channel;
        this.author = author;
        this.reviewer = reviewer;
        this.date = date;
        this.count = count;
        this.total = total;
        this.description = description;
    }

    private Channel channel;
    private Member author;
    private Member reviewer;
    private Date date;
    private Long count;
    private Long total;
    private String description;


    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Member getAuthor() {
        return author;
    }

    public void setAuthor(Member author) {
        this.author = author;
    }

    public Member getReviewer() {
        return reviewer;
    }

    public void setReviewer(Member reviewer) {
        this.reviewer = reviewer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
