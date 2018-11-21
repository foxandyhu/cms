package com.bfly.cms.entity.main;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS模型项类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:39
 */
@Entity
@Table(name = "jc_model_item")
public class CmsModelItem implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Integer DATA_TYPE_STRING = 1;
    public static final Integer DATA_TYPE_INTEGER = 2;
    public static final Integer DATA_TYPE_FLOAT = 3;
    public static final Integer DATA_TYPE_TEXTAREA = 4;
    public static final Integer DATA_TYPE_DATE = 5;
    public static final Integer DATA_TYPE_SELECT = 6;
    public static final Integer DATA_TYPE_CHECKBOX = 7;
    public static final Integer DATA_TYPE_RADIO = 8;
    public static final Integer DATA_TYPE_ATTACHMENT = 9;
    public static final Integer DATA_TYPE_PICTRUE = 10;

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
     * 数据类型
     */
    @Column(name = "data_type")
    private Integer dataType;

    /**
     * 是否独占一行
     */
    @Column(name = "is_single")
    private Boolean single;

    /**
     * 是否栏目模型项
     */
    @Column(name = "is_channel")
    private Boolean channel;

    /**
     * 是否自定义
     */
    @Column(name = "is_custom")
    private Boolean custom;

    /**
     * 是否显示
     */
    @Column(name = "is_display")
    private Boolean display;

    /**
     * 是否必填项
     */
    @Column(name = "is_required")
    private Boolean required;

    /**
     * 图片宽度
     */
    @Column(name = "image_width")
    private Integer imageWidth;

    /**
     * 图片宽度
     */
    @Column(name = "image_height")
    private Integer imageHeight;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private CmsModel model;


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

    public Boolean getSingle() {
        return single;
    }

    public void setSingle(Boolean single) {
        this.single = single;
    }

    public Boolean getChannel() {
        return channel;
    }

    public void setChannel(Boolean channel) {
        this.channel = channel;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }


    public CmsModel getModel() {
        return model;
    }

    public void setModel(CmsModel model) {
        this.model = model;
    }


    public JSONObject convertToJsonList() {
        JSONObject json = commonJson();
        if (getDataType() != null) {
            if (getDataType().equals(6) || getDataType().equals(7) || getDataType().equals(8)) {
                JSONArray jsonArray = new JSONArray();
                String op = getOptValue();
                if (StringUtils.isNotBlank(op)) {
                    String[] split = op.split(",");
                    for (int i = 0; i < split.length; i++) {
                        jsonArray.put(i, split[i]);
                    }
                }
                json.put("optValue", jsonArray);
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

    public JSONObject convertToJson() {
        JSONObject json = commonJson();
        if (StringUtils.isNotBlank(getOptValue())) {
            json.put("optValue", getOptValue());
        } else {
            json.put("optValue", "");
        }
        return json;
    }

    //get/list共用json
    private JSONObject commonJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
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
        if (StringUtils.isNotBlank(getDefValue())) {
            json.put("defValue", getDefValue());
        } else {
            json.put("defValue", "");
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
        if (getSingle() != null) {
            json.put("single", getSingle());
        } else {
            json.put("single", "");
        }
        if (getChannel() != null) {
            json.put("channel", getChannel());
        } else {
            json.put("channel", "");
        }
        if (getCustom() != null) {
            json.put("custom", getCustom());
        } else {
            json.put("custom", "");
        }
        if (getDisplay() != null) {
            json.put("display", getDisplay());
        } else {
            json.put("display", "");
        }
        if (getRequired() != null) {
            json.put("required", getRequired());
        } else {
            json.put("required", "");
        }
        if (getImageWidth() != null) {
            json.put("imageWidth", getImageWidth());
        } else {
            json.put("imageWidth", "");
        }
        if (getImageHeight() != null) {
            json.put("imageHeight", getImageHeight());
        } else {
            json.put("imageHeight", "");
        }
        return json;
    }

    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
        if (getChannel() == null) {
            setChannel(true);
        }
        if (getCustom() == null) {
            setCustom(true);
        }
        if (getDisplay() == null) {
            setDisplay(true);
        }
        if (getSingle() == null) {
            setSingle(true);
        }
        if (getRequired() == null) {
            setRequired(false);
        }
        if (getDataType() == null) {
            setDataType(1);
        }
        if (StringUtils.isBlank(getRows())) {
            setRows("3");
        }
        if (StringUtils.isBlank(getCols())) {
            setCols("30");
        }
    }

    // 将字符串字段全部设置为非null，方便判断。
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