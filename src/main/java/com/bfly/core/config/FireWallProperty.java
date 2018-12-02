package com.bfly.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

/**
 * 防火墙配置文件
 * @author andy_hulibo@163.com
 * @date 2018/11/19 11:10
 */
@Component("fireWallProperty")
@PropertySource(value= "classpath:firewall.properties")
@ConfigurationProperties(prefix = "firewall")
public class FireWallProperty {

    private static final String FIREWALL_FILE= "firewall.properties";
    private String password;
    private String open;
    private String domain;
    private String hour;
    private String week;
    private String ips;

    /**
     * 重新写入文件
     * @author andy_hulibo@163.com
     * @date 2018/11/19 14:05
     */
    public void write() throws Exception{
        Properties properties =new Properties();
        properties.setProperty("firewall.password",getPassword());
        properties.setProperty("firewall.open",getOpen());
        properties.setProperty("firewall.domain",getDomain());
        properties.setProperty("firewall.hour",getHour());
        properties.setProperty("firewall.week",getWeek());
        properties.setProperty("firewall.ips",getIps());

        try(OutputStream out = new FileOutputStream(getFireWallFile())){
            properties.store(out, "update firewall config");
        }
    }

    /**
     * 重新加载文件
     * @author andy_hulibo@163.com
     * @date 2018/11/19 11:36
     */
    public void reload(){
        Properties p = new Properties();
        try(FileInputStream in = new FileInputStream(getFireWallFile())) {
            p.load(in);
            setOpen(p.getProperty("firewall.open"));
            setDomain(p.getProperty("firewall.domain"));
            setIps(p.getProperty("firewall.ips"));
            setWeek(p.getProperty("firewall.week"));
            setHour(p.getProperty("firewall.hour"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到防火墙配置文件
     * @author andy_hulibo@163.com
     * @date 2018/11/19 11:18
     */
    public File getFireWallFile(){
        ClassPathResource resource=new ClassPathResource(FIREWALL_FILE);
        File file= null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }
}
