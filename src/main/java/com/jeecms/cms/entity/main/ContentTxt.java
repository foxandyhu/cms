package com.jeecms.cms.entity.main;

import org.hibernate.annotations.Cache;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS内容文本
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:39
 */
@Entity
@Table(name = "jc_content_txt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentTxt implements Serializable {
    private static final long serialVersionUID = 1L;

    //ueditor采用分页
    public static String PAGE_START = "[NextPage]";
    public static String PAGE_END = "[/NextPage]";


    public int getTxtCount() {
        String txt = getTxt();
        if (StringUtils.isBlank(txt)) {
            return 1;
        } else {
            return StringUtils.countMatches(txt, PAGE_START) + 1;
        }
    }

    public String getTxtByNo(int pageNo) {
        String txt = getTxt();
        if (StringUtils.isBlank(txt) || pageNo < 1) {
            return null;
        }
        int start = 0, end = 0;
        for (int i = 0; i < pageNo; i++) {
            // 如果不是第一页
            if (i != 0) {
                start = txt.indexOf(PAGE_END, end);
                if (start == -1) {
                    return null;
                } else {
                    start += PAGE_END.length();
                }
            }
            end = txt.indexOf(PAGE_START, start);
            if (end == -1) {
                end = txt.length();
            }
        }
        return txt.substring(start, end);
    }

    public String getTitleByNo(int pageNo) {
        if (pageNo < 1) {
            return null;
        }
        String title = getContent().getTitle();
        if (pageNo == 1) {
            return title;
        }
        String txt = getTxt();
        int start = 0, end = 0;
        for (int i = 1; i < pageNo; i++) {
            start = txt.indexOf(PAGE_START, end);
            if (start == -1) {
                return title;
            } else {
                start += PAGE_START.length();
            }
            end = txt.indexOf(PAGE_END, start);
            if (end == -1) {
                return title;
            }
        }
        String result = txt.substring(start, end);
        if (!StringUtils.isBlank(result)) {
            return result;
        } else {
            return title;
        }
    }

    public void init() {
        blankToNull();
    }

    public void blankToNull() {
        if (StringUtils.isBlank(getTxt())) {
            setTxt(null);
        }
        if (StringUtils.isBlank(getTxt1())) {
            setTxt1(null);
        }
        if (StringUtils.isBlank(getTxt2())) {
            setTxt2(null);
        }
        if (StringUtils.isBlank(getTxt3())) {
            setTxt3(null);
        }
    }


    /**
     * 是否所有属性都为空
     *
     * @return
     */
    public boolean isAllBlank() {
        return StringUtils.isBlank(getTxt()) && StringUtils.isBlank(getTxt1())
                && StringUtils.isBlank(getTxt2())
                && StringUtils.isBlank(getTxt3());
    }

    @Id
    @Column(name = "content_id",unique = true,nullable = false)
    private Integer id;

    /**
     * 文章内容
     */
    @Column(name = "txt")
    private String txt;

    /**
     * 扩展内容1
     */
    @Column(name = "txt1")
    private String txt1;

    /**
     * 扩展内容2
     */
    @Column(name = "txt2")
    private String txt2;

    /**
     * 扩展内容3
     */
    @Column(name = "txt3")
    private String txt3;

    @OneToOne
    @MapsId
    @JoinColumn(name = "content_id")
    private Content content;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }


    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    public String getTxt2() {
        return txt2;
    }


    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }


    public String getTxt3() {
        return txt3;
    }

    public void setTxt3(String txt3) {
        this.txt3 = txt3;
    }


    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

}