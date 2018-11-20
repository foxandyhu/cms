package com.jeecms.common.web.springmvc;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * 数据绑定初始化类
 * @author andy_hulibo@163.com
 * @date 2018/11/20 10:37
 */
public class BindingInitializer implements WebBindingInitializer {

	/**
	 * 初始化数据绑定
	 * 可能request提交过来的date为空字符会自动转换或去掉
	 * @author andy_hulibo@163.com
	 * @date 2018/11/20 10:37
	 */
	@Override
    public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new DateTypeEditor());
	}
}
