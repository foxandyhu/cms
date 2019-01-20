package com.bfly.manage.lucene;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全文检索Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 17:04
 */
@RestController
@RequestMapping(value = "/manage/lucene")
public class LuceneController {

    /**
     * 生成全文索引
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 17:05
     */
    @GetMapping(value = "/create")
    public void createIndex() {

    }
}
