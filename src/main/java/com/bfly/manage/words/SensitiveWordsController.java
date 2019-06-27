package com.bfly.manage.words;

import com.bfly.cms.words.entity.SensitiveWords;
import com.bfly.cms.words.service.ISensitiveWordsService;
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
 * 敏感词管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:49
 */
@RestController
@RequestMapping(value = "/manage/sensitive")
public class SensitiveWordsController extends BaseManageController {

    @Autowired
    private ISensitiveWordsService sensitiveWordsService;

    /**
     * 敏感词列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 14:50
     */
    @GetMapping(value = "/list")
    public void listSensitiveWords(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = sensitiveWordsService.getPage(null);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增敏感词
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 10:19
     */
    @PostMapping(value = "/add")
    public void addSensitiveWords(@Valid SensitiveWords sensitiveWords, BindingResult result, HttpServletResponse response) {
        validData(result);
        sensitiveWordsService.save(sensitiveWords);
        ResponseUtil.writeJson(response, sensitiveWords);
    }

    /**
     * 编辑敏感词
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 10:21
     */
    @PostMapping(value = "/edit")
    public void editSensitiveWords(@Valid SensitiveWords sensitiveWords,BindingResult result, HttpServletResponse response) {
        validData(result);
        sensitiveWordsService.edit(sensitiveWords);
        ResponseUtil.writeJson(response, sensitiveWords);
    }

    /**
     * 删除敏感词
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 14:52
     */
    @PostMapping(value = "/del")
    public void delSensitiveWords(HttpServletResponse response) {
        String idStr = getRequest().getParameter("ids");
        Integer[] ids = DataConvertUtils.convertToIntegerArray(idStr.split(","));
        sensitiveWordsService.remove(ids);
        ResponseUtil.writeJson(response, "");
    }
}
