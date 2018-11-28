package com.bfly.cms.system.entity;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS系统配置模型项类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:08
 */
@Entity
@Table(name = "jc_config_item")
public class CmsConfigItem implements Serializable {

    /**
     * 会员注册模型
     */
    public static final int CATEGORY_REGISTER = 1;

    private static final long serialVersionUID = 1L;

    public boolean getRequired() {
        return isRequired();
    }

    @Id
    @Column(name = "modelitem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private Integer priority;

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
     * 数据类型 "1":"字符串文本","2":"整型文本","3":"浮点型文本",
     * "4":"文本区","5":"日期","6":"下拉列表","7":"复选框",
     * "8":"单选框"
     */
    @Column(name = "data_type")
    private Integer dataType;

    /**
     * 是否必填
     */
    @Column(name = "is_required")
    private boolean required;

    /**
     * 模型项目所属分类（1注册模型）
     */
    @Column(name = "category")
    private Integer category;

    @ManyToOne
    @JoinColumn(name = "config_id")
    private CmsConfig config;

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

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public CmsConfig getConfig() {
        return config;
    }

    public void setConfig(CmsConfig config) {
        this.config = config;
    }

    public JSONObject convertToJsonList() {
        JSONObject json = convertToJsonCommon();
        if (getDataType() != null) {
            Integer type = getDataType();
            if (type.equals(6) || type.equals(7) || type.equals(8)) {
                JSONArray jsonArray = new JSONArray();
                if (StringUtils.isNotBlank(getOptValue())) {
                    String value = getOptValue();
                    String[] split = value.split(",");
                    for (int i = 0; i < split.length; i++) {
                        jsonArray.put(i, split[i]);
                    }
                }
                json.put("optValue", jsonArray.toString());
            } else {
                if (StringUtils.isNotBlank(getOptValue())) {
                    json.put("optValue", getOptValue());
                } else {
                    json.put("optValue", "");
                }
            }
        }
        return json;
    }

    public JSONObject convertToJsonGet() {
        JSONObject json = convertToJsonCommon();
        if (StringUtils.isNotBlank(getOptValue())) {
            json.put("optValue", getOptValue());
        } else {
            json.put("optValue", "");
        }
        return json;
    }

    private JSONObject convertToJsonCommon() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getPriority() != null) {
            json.put("priority", getPriority());
        } else {
            json.put("priority", "");
        }
        if (getDataType() != null) {
            json.put("dataType", getDataType());
        } else {
            json.put("dataType", "");
        }
        if (getCategory() != null) {
            json.put("category", getCategory());
        } else {
            json.put("category", "");
        }
        json.put("required", getRequired());
        if (StringUtils.isNotBlank(getField())) {
            json.put("field", getField());
        } else {
            json.put("field", "");
        }
        if (StringUtils.isNotBlank(getLabel())) {
            json.put("label", getLabel());
        } else {
            json.put("label", "");
        }
        if (StringUtils.isNotBlank(getSize())) {
            json.put("size", getSize());
        } else {
            json.put("size", "");
        }
        if (StringUtils.isNotBlank(getRows())) {
            json.put("rows", getRows());
        } else {
            json.put("rows", "");
        }
        if (StringUtils.isNotBlank(getCols())) {
            json.put("cols", getCols());
        } else {
            json.put("cols", "");
        }
        if (StringUtils.isNotBlank(getHelp())) {
            json.put("help", getHelp());
        } else {
            json.put("help", "");
        }
        if (StringUtils.isNotBlank(getHelpPosition())) {
            json.put("helpPosition", getHelpPosition());
        } else {
            json.put("helpPosition", "");
        }
        if (StringUtils.isNotBlank(getDefValue())) {
            json.put("defValue", getDefValue());
        } else {
            json.put("defValue", "");
        }
        return json;
    }

    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
    }

    /**
     * 将字符串字段全部设置为非null，方便判断。
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/24 17:34
     */
    public void emptyToNull() {
        if (StringUtils.isBlank(getDefValue())) {
            setDefValue(null);
        }
        if (StringUtils.isBlank(getOptValue())) {
            setOptValue(null);
        }
        if (StringUtils.isBlank(getSize())) {
            setSize(null);
        }
        if (StringUtils.isBlank(getRows())) {
            setRows(null);
        }
        if (StringUtils.isBlank(getCols())) {
            setCols(null);
        }
        if (StringUtils.isBlank(getHelp())) {
            setHelp(null);
        }
        if (StringUtils.isBlank(getHelpPosition())) {
            setHelpPosition(null);
        }
    }
}