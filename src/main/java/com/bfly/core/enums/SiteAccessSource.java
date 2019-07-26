package com.bfly.core.enums;

/**
 * 站点访问来源
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/23 18:30
 */
public enum SiteAccessSource {

    DIRECT("direct_access"), ENGINE("engine_access"), EXTERNAL("external_access");

    private String name;

    SiteAccessSource(String name) {
        this.name = name;
    }

    /**
     * 获得访问来源类型
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 12:49
     */
    public static SiteAccessSource getType(String name) {
        for (SiteAccessSource type : SiteAccessSource.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
