package com.bfly.cms.system.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 水印配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 15:55
 */
@Entity
@Table(name = "sys_watermark")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SysWaterMark implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 是否开启水印
     */
    @Column(name = "is_open_watermark")
    private boolean openWaterMark;

    /**
     * 图片最小宽度
     */
    @Column(name = "img_width")
    private int imgWidth;

    /**
     * 图片最小高度
     */
    @Column(name = "img_height")
    private int imgHeight;

    /**
     * 图片水印
     */
    @Column(name = "mark_img")
    private String img;

    /**
     * 文字水印内容
     */
    @Column(name = "mark_text")
    private String text;

    /**
     * 文字水印大小
     */
    @Column(name = "mark_size")
    private int size;

    /**
     * 文字水印颜色
     */
    @Column(name = "mark_color")
    private String color;

    /**
     * 水印透明度（0-100）
     */
    @Column(name = "mark_alpha")
    private int alpha;

    /**
     * 水印位置(0-5)
     *
     * @see com.bfly.core.enums.WaterMarkPos
     */
    @Column(name = "mark_position")
    private int pos;

    /**
     * x坐标偏移量
     */
    @Column(name = "offset_x")
    private int offsetX;

    /**
     * y坐标偏移量
     */
    @Column(name = "offset_y")
    private int offsetY;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOpenWaterMark() {
        return openWaterMark;
    }

    public void setOpenWaterMark(boolean openWaterMark) {
        this.openWaterMark = openWaterMark;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
}