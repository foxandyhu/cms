package com.bfly.cms.channel.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS模型项类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:39
 */
@Entity
@Table(name = "model_item")
public class ModelItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 字段
     */
    @Column(name = "field")
    private String field;

    /**
     * 名称
     */
    @Column(name = "item_label")
    private String label;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private int priority;

    /**
     * 默认值
     */
    @Column(name = "def_value")
    private String defValue;

    /**
     * 可选项
     */
    @Column(name = "opt_value")
    private String optValue;

    /**
     * 长度
     */
    @Column(name = "text_size")
    private String size;

    /**
     * 文本行数
     */
    @Column(name = "area_rows")
    private String rows;

    /**
     * 文本列数
     */
    @Column(name = "area_cols")
    private String cols;

    /**
     * 帮助信息
     */
    @Column(name = "help")
    private String help;

    /**
     * 帮助位置
     */
    @Column(name = "help_position")
    private String helpPosition;

    /**
     * 数据类型
     */
    @Column(name = "data_type")
    private int dataType;

    /**
     * 是否独占一行
     */
    @Column(name = "is_single")
    private boolean single;

    /**
     * 是否栏目模型项
     */
    @Column(name = "is_channel")
    private boolean channel;

    /**
     * 是否自定义
     */
    @Column(name = "is_custom")
    private Boolean custom;

    /**
     * 是否显示
     */
    @Column(name = "is_display")
    private boolean display;

    /**
     * 是否必填项
     */
    @Column(name = "is_required")
    private boolean required;

    /**
     * 图片宽度
     */
    @Column(name = "image_width")
    private int imageWidth;

    /**
     * 图片宽度
     */
    @Column(name = "image_height")
    private int imageHeight;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDefValue() {
        return defValue;
    }

    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }

    public String getOptValue() {
        return optValue;
    }

    public void setOptValue(String optValue) {
        this.optValue = optValue;
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

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public boolean isChannel() {
        return channel;
    }

    public void setChannel(boolean channel) {
        this.channel = channel;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}