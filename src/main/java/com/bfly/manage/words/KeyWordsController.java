package com.bfly.manage.words;

import com.bfly.cms.words.entity.Keywords;
import com.bfly.cms.words.service.IKeyWordsService;
import com.bfly.core.context.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 关键词管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:49
 */
@RestController
@RequestMapping(value = "/manage/keywords")
public class KeyWordsController extends BaseManageController {

    @Autowired
    private IKeyWordsService keyWordsService;

    /**
     * 关键词列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 14:50
     */
    @GetMapping(value = "/list")
    public void listKeyWords(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = keyWordsService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增关键字
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 10:29
     */
    @PostMapping(value = "/add")
    public void addKeyWords(@Valid Keywords keywords, BindingResult result, HttpServletResponse response) {
        validData(result);
        keyWordsService.save(keywords);
        ResponseUtil.writeJson(response, keywords);
    }

    /**
     * 修改关键字
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 10:31
     */
    @PostMapping(value = "/edit")
    public void editKeyWords(@Valid Keywords keywords,BindingResult result, HttpServletResponse response) {
        validData(result);
        keyWordsService.edit(keywords);
        ResponseUtil.writeJson(response, keywords);
    }

    /**
     * 删除关键词
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 14:52
     */
    @PostMapping(value = "/del")
    public void delKeyWords(HttpServletResponse response) {
        String idStr = getRequest().getParameter("ids");
        Integer[] ids = DataConvertUtils.convertToIntegerArray(idStr.split(","));
        keyWordsService.remove(ids);
        ResponseUtil.writeJson(response, "");
    }
}
