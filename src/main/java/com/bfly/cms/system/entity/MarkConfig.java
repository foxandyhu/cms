package com.bfly.cms.system.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 水印配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 15:55
 */
@Embeddable
public class MarkConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 开启图片水印
     */
    @Column(name = "mark_on")
    private Boolean on;

    /**
     * 图片最小宽度
     */
    @Column(name = "mark_width")
    private Integer minWidth;

    /**
     * 图片最小高度
     */
    @Column(name = "mark_height")
    private Integer minHeight;

    /**
     * 图片水印
     */
    @Column(name = "mark_image")
    private String imagePath;

    /**
     * 文字水印内容
     */
    @Column(name = "mark_content")
    private String content;

    /**
     * 文字水印大小
     */
    @Column(name = "mark_size")
    private Integer size;

    /**
     * 文字水印颜色
     */
    @Column(name = "mark_color")
    private String color;

    /**
     * 水印透明度（0-100）
     */
    @Column(name = "mark_alpha")
    private Integer alpha;

    /**
     * 水印位置(0-5)
     */
    @Column(name = "mark_position")
    private Integer pos;

    /**
     * x坐标偏移量
     */
    @Column(name = "mark_offset_x")
    private Integer offsetX;

    /**
     * y坐标偏移量
     */
    @Column(name = "mark_offset_y")
    private Integer offsetY;


    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Integer getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(Integer minWidth) {
        this.minWidth = minWidth;
    }

    public Integer getMinHeight() {
        return minHeight;
    }


    public void setMinHeight(Integer minHeight) {
        this.minHeight = minHeight;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Integer getSize() {
        return size;
    }


    public void setSize(Integer size) {
        this.size = size;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getAlpha() {
        return alpha;
    }

    public void setAlpha(Integer alpha) {
        this.alpha = alpha;
    }

    public Integer getPos() {
        return pos;
    }


    public void setPos(Integer pos) {
        this.pos = pos;
    }


    public Integer getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(Integer offsetX) {
        this.offsetX = offsetX;
    }

    public Integer getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(Integer offsetY) {
        this.offsetY = offsetY;
    }
}