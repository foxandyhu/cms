package com.bfly.manage.words;

import com.bfly.cms.words.entity.SearchWords;
import com.bfly.cms.words.service.ISearchWordsService;
import com.bfly.core.context.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 热词管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:49
 */
@RestController
@RequestMapping(value = "/manage/searchwords")
public class SearchWordsController extends BaseManageController {

    @Autowired
    private ISearchWordsService searchWordsService;

    /**
     * 热词列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 14:50
     */
    @GetMapping(value = "/list")
    public void listSearchWords(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = searchWordsService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增热词
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 10:37
     */
    @PostMapping(value = "/add")
    public void addSearchWords(@Valid SearchWords searchWords, BindingResult result, HttpServletResponse response) {
        validData(result);
        searchWordsService.save(searchWords);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑热词
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 10:38
     */
    @PostMapping(value = "/edit")
    public void editSearchWords(@Valid SearchWords searchWords,BindingResult result, HttpServletResponse response) {
        validData(result);
        searchWordsService.edit(searchWords);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 热词详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 10:39
     */
    @GetMapping(value = "/{searchWordsId}")
    public void viewSearchWords(@PathVariable("searchWordsId") int searchWordsId, HttpServletResponse response) {
        SearchWords searchWords = searchWordsService.get(searchWordsId);
        ResponseUtil.writeJson(response, searchWords);
    }

    /**
     * 删除热词
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 14:52
     */
    @PostMapping(value = "/del")
    public void delSearchWords(HttpServletResponse response) {
        String idStr = getRequest().getParameter("ids");
        Integer[] ids = DataConvertUtils.convertToIntegerArray(idStr.split(","));
        searchWordsService.remove(ids);
        ResponseUtil.writeJson(response, "");
    }
}
