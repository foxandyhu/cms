package com.bfly.cms.content.entity;

import com.bfly.core.enums.DataType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CMS模型项类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:39
 */
@Entity
@Table(name = "model_item")
public class ModelItem implements Serializable {

    private static final long serialVersionUID = -3327080382085312989L;
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
    @Column(name = "seq")
    private int seq;

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
    @Column(name = "remark")
    private String remark;

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
     * 是否自定义
     */
    @Column(name = "is_custom")
    private boolean custom;

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

    /**
     * 所属模型
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 13:55
     */
    @Column(name = "model_id")
    private int modelId;

    /**
     * 数据类型
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/5 12:41
     */
    public String getDataTypeName() {
        for (DataType type : DataType.values()) {
            if (type.getId() == getDataType()) {
                return type.getName();
            }
        }
        return null;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }


    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    private static List<ModelItem> sysModelItems = new ArrayList<>();

    /**
     * 添加系统模型项
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 18:49
     */
    public static void addSysModelItem(String field, String name, String defValue, String optValue,
                                       String remark, DataType dataType, boolean single,
                                       boolean required) {
        ModelItem item = new ModelItem();
        item.setField(field);
        item.setName(name);
        item.setDefValue(defValue);
        item.setOptValue(optValue);
        item.setRemark(remark);
        item.setDataType(dataType.getId());
        item.setSingle(single);
        item.setCustom(false);
        item.setEnabled(true);
        item.setRequired(required);
        item.setSeq(sysModelItems.size() + 1);
        item.setId(item.getSeq());
        sysModelItems.add(item);
    }

    static {
        addSysModelItem("title", "标题", "", "", "", DataType.STRING, true, false);
        addSysModelItem("shorttitle", "简短标题", "", "", "", DataType.STRING, false, false);

        addSysModelItem("tags", "Tag标签", "", "", "", DataType.STRING, true, false);
        addSysModelItem("summary", "摘要", "", "", "", DataType.TEXTAREA, true, false);
        addSysModelItem("keywords", "meta关键字", "", "", "", DataType.STRING, true, false);
        addSysModelItem("description", "meta描述", "", "", "", DataType.STRING, true, false);

        addSysModelItem("author", "作者", "", "", "", DataType.STRING, false, false);
        addSysModelItem("postdate", "发布时间", "", "", "", DataType.DATETIME, false, false);

        addSysModelItem("origin", "来源", "", "", "", DataType.STRING, false, false);
        addSysModelItem("originurl", "来源地址", "", "", "", DataType.STRING, false, false);
        addSysModelItem("link", "外部链接", "", "", "", DataType.STRING, false, false);

        addSysModelItem("top", "置顶", "", "", "级别越高置顶越靠前", DataType.SINGLEBOX, false, false);
        addSysModelItem("recommend", "推荐", "", "", "", DataType.SINGLEBOX, true, false);

        addSysModelItem("tplpc", "PC模板", "", "", "", DataType.SELECT, false, false);
        addSysModelItem("tplmobile", "手机模板", "", "", "", DataType.SELECT, false, false);

        addSysModelItem("type", "内容类型", "", "", "", DataType.SELECT, true, false);
        addSysModelItem("titleimg", "标题图", "", "", "", DataType.STRING, false, false);
        addSysModelItem("contentimg", "内容图", "", "", "", DataType.STRING, false, false);

        addSysModelItem("viewgroup", "浏览权限", "", "", "", DataType.CHECKBOX, true, false);
        addSysModelItem("share", "分享", "", "", "", DataType.SINGLEBOX, false, false);
        addSysModelItem("updown", "顶踩", "", "", "", DataType.SINGLEBOX, false, false);
        addSysModelItem("comment", "评论", "", "", "", DataType.SINGLEBOX, false, false);
        addSysModelItem("score", "评分", "", "", "", DataType.SINGLEBOX, true, false);

        addSysModelItem("txt", "内容", "", "", "", DataType.EDITOR, true, false);

        addSysModelItem("file", "文件", "", "", "", DataType.ATTACHMENTS, true, false);
        addSysModelItem("pictures", "图片集", "", "", "", DataType.PICTURES, true, false);
        addSysModelItem("attachments", "附件", "", "", "", DataType.ATTACHMENTS, true, false);
    }

    /**
     * 得到系统模型项
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 18:49
     */
    public static List<ModelItem> getSysModelItem() {
        return sysModelItems;
    }
}