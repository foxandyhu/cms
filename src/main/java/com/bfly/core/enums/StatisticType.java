package com.bfly.core.enums;

/**
 * 统计维度
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/24 8:15
 */
public enum StatisticType {

    ALL("all"), SOURCE("source"), ENGINE("engine"), LINK("link"),
    AREA("area"), BROWSER("browser");

    private String name;

    StatisticType(String name) {
        this.name = name;
    }

    /**
     * 获得统计类型
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 12:49
     */
    public static StatisticType getType(String name) {
        for (StatisticType type : StatisticType.values()) {
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
