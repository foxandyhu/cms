package com.bfly.cms.channel.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "字段不能为空!")
    private String field;

    /**
     * 名称
     */
    @Column(name = "name")
    @NotBlank(message = "名称不能为空!")
    private String name;

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
     * 可选项 通常是复选框 单选框 下拉框的值
     */
    @Column(name = "opt_value")
    private String optValue;

    /**
     * 帮助信息
     */
    @Column(name = "help")
    private String help;

    /**
     * 数据类型
     *
     * @see com.bfly.core.enums.DataType
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
     * 是否启用
     */
    @Column(name = "is_enabled")
    private boolean enabled;

    /**
     * 是否必填项
     */
    @Column(name = "is_required")
    private boolean required;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}