package com.bfly.core.config.editor;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换器
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/19 12:38
 */
public class DateEditor extends PropertyEditorSupport {

    public static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public static final int DATE_LENGTH = 10;


    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        return (value != null ? dateTimeFormatter.format(value) : "");
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        text = text.trim();
        if (!StringUtils.hasText(text)) {
            setValue(null);
            return;
        }
        try {
            if (text.length() <= DATE_LENGTH) {
                setValue(new Date(dateFormatter.parse(text).getTime()));
            } else {
                setValue(new Date(dateTimeFormatter.parse(text).getTime()));
            }
        } catch (Exception ex) {
            IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
            iae.initCause(ex);
            throw iae;
        }
    }
}
