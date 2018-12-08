package com.bfly.core.config;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期编辑器根据日期字符串长度判断是长日期还是短日期。只支持yyyy-MM-dd，yyyy-MM-dd HH:mm:ss两种格式。
 * 扩展支持yyyy,yyyy-MM日期格式
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/6 14:34
 */
public class DateTypeEditor extends PropertyEditorSupport {

    private static final DateFormat DF_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat DF_SHORT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DF_YEAR = new SimpleDateFormat("yyyy");
    private static final DateFormat DF_MONTH = new SimpleDateFormat("yyyy-MM");

    /**
     * 短类型日期长度
     */
    private static final int SHORT_DATE = 10;

    private static final int YEAR_DATE = 4;

    private static final int MONTH_DATE = 7;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        text = text.trim();
        if (!StringUtils.hasText(text)) {
            setValue(null);
            return;
        }
        try {
            if (text.length() <= YEAR_DATE) {
                setValue(new java.sql.Date(DF_YEAR.parse(text).getTime()));
            } else if (text.length() <= MONTH_DATE) {
                setValue(new java.sql.Date(DF_MONTH.parse(text).getTime()));
            } else if (text.length() <= SHORT_DATE) {
                setValue(new java.sql.Date(DF_SHORT.parse(text).getTime()));
            } else {
                setValue(new java.sql.Timestamp(DF_LONG.parse(text).getTime()));
            }
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Could not parse date: " + ex.getMessage());
        }
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        return (value != null ? DF_LONG.format(value) : "");
    }
}