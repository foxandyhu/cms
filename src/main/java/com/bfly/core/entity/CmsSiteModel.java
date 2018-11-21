package com.bfly.core.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS站点信息模型类
 * @author andy_hulibo@163.com
 * @date 2018/11/15 17:50
 */
@Entity
@Table(name = "jc_site_model")
public class CmsSiteModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "model_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *字段
     */
    @Column(name = "field")
    private String field;

    /**
     *名称
     */
    @Column(name = "model_label")
    private String label;

    /**
     *排列顺序
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     *上传路径
     */
    @Column(name = "upload_path")
    private String uploadPath;

    /**
     *长度
     */
    @Column(name = "text_size")
    private String size;

    /**
     *文本行数
     */
    @Column(name = "area_rows")
    private String rows;

    /**
     *文本列数
     */
    @Column(name = "area_cols")
    private String cols;

    /**
     *帮助信息
     */
    @Column(name = "help")
    private String help;

    /**
     *帮助位置
     */
    @Column(name = "help_position")
    private String helpPosition;

    /**
     *0:编辑器;1:文本框;2:文本区;3:图片;4:附件
     */
    @Column(name = "data_type")
    private Integer dataType;

    /**
     *是否独占一行
     */
    @Column(name = "is_single")
    private Boolean single;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    public String getUploadPath() {
        return uploadPath;
    }


    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }


    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }


    public String getHelp() {
        return help;
    }


    public void setHelp(String help) {
        this.help = help;
    }


    public String getHelpPosition() {
        return helpPosition;
    }


    public void setHelpPosition(String helpPosition) {
        this.helpPosition = helpPosition;
    }


    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }


    public Boolean getSingle() {
        return single;
    }


    public void setSingle(Boolean single) {
        this.single = single;
    }


}