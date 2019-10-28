package com.bfly.common.ipseek;

/**
 * 封装ip相关信息，目前只有两个字段，ip所在的国家和地区
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/29 10:17
 */
public class IPLocation {
    private String country = "";
    private String area = "";

    public IPLocation() {
    }

    public IPLocation(String country, String area) {
        this.country = country;
        this.area = area;
    }

    public IPLocation getCopy() {
        IPLocation ret = new IPLocation();
        ret.country = country;
        ret.area = area;
        return ret;
    }

    @Override
    public String toString() {
        return getCountry()+" "+getArea();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}