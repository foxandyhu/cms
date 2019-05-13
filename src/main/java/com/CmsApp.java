package com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Spring Boot APP启动入口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/14 13:31
 */
@SpringBootApplication
@ServletComponentScan
public class CmsApp extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CmsApp.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CmsApp.class, args);
    }
}
