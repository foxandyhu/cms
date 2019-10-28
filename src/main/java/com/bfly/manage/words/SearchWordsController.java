package com.bfly.manage.words;

import com.bfly.cms.words.entity.SearchWords;
import com.bfly.cms.words.service.ISearchWordsService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 热词管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:49
 */
@RestController
@RequestMapping(value = "/manage/searchword")
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
        Map<String, String> unexact = null;
        String searchWord = getRequest().getParameter("search");
        if (searchWord != null) {
            unexact = new HashMap<>(1);
            unexact.put("name", searchWord);
        }
        Map<String, Object> exact = null;
        String recommend = getRequest().getParameter("recommend");
        if (recommend != null) {
            exact = new HashMap<>(1);
            exact.put("recommend", Boolean.valueOf(recommend));
        }
        Pager pager = searchWordsService.getPage(exact, unexact, null);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 删除热词
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 14:52
     */
    @PostMapping(value = "/del")
    public void delSearchWords(HttpServletResponse response, @RequestBody Integer... ids) {
        searchWordsService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 更新热词推荐状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/15 15:22
     */
    @GetMapping(value = "/recommend-{searchId}-{status}")
    @ActionModel(value = "更新搜索词推荐状态")
    public void recommendSearchWords(HttpServletResponse response, @PathVariable("searchId") int searchId, @PathVariable("status") boolean status) {
        searchWordsService.editRecommend(searchId, status);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 新增搜索词
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/16 11:31
     */
    @PostMapping(value = "/add")
    @ActionModel(value = "新增搜索词")
    public void addSearchWords(HttpServletResponse response, @RequestBody SearchWords word) {
        searchWordsService.save(word);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 编辑搜索词
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/16 11:32
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "编辑搜索词")
    public void editSearchWords(HttpServletResponse response, @RequestBody SearchWords word) {
        searchWordsService.edit(word);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 搜索词详情
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/16 11:34
     */
    @GetMapping(value = "/{wordId}")
    @ActionModel(value = "搜索词详情", need = false)
    public void viewSearchWords(HttpServletResponse response, @PathVariable("wordId") int wordId) {
        SearchWords words = searchWordsService.get(wordId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(words));
    }
}
