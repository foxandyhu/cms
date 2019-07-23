package com.bfly.core.enums;

/**
 * 短信类型
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 12:53
 */
public enum TaskStatus {

    START(1, "开启"), shutdown(2, "关闭"), RUNNING(3, "正在执行");

    private int id;
    private String name;

    TaskStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获得任务状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 12:49
     */
    public static TaskStatus getStatus(int id) {
        for (TaskStatus type : TaskStatus.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
