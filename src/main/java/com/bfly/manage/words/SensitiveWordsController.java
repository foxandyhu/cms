package com.bfly.manage.words;

import com.bfly.cms.words.entity.SensitiveWords;
import com.bfly.cms.words.service.ISensitiveWordsService;
import com.bfly.common.ResponseData;
import com.bfly.core.context.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    @ActionModel(value = "敏感词列表", need = false)
    public void listSensitiveWords(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Map<String, String> params = null;
        String searchWord = getRequest().getParameter("search");
        if (searchWord != null) {
            params = new HashMap<>(1);
            params.put("word", searchWord);
        }
        Pager pager = sensitiveWordsService.getPage(null, params, null);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 新增敏感词
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 10:19
     */
    @PostMapping(value = "/add")
    @ActionModel("新增敏感词")
    public void addSensitiveWords(@RequestBody @Valid SensitiveWords sensitiveWords, BindingResult result, HttpServletResponse response) {
        validData(result);
        sensitiveWordsService.save(sensitiveWords);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改敏感词
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 10:21
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改敏感词")
    public void editSensitiveWords(@RequestBody @Valid SensitiveWords sensitiveWords, BindingResult result, HttpServletResponse response) {
        validData(result);
        sensitiveWordsService.edit(sensitiveWords);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 删除敏感词
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 14:52
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除敏感词")
    public void delSensitiveWords(HttpServletResponse response, @RequestBody Integer... ids) {
        sensitiveWordsService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 铭感词详情
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/15 12:34
     */
    @GetMapping(value = "/{sensitiveId}")
    @ActionModel(value = "查看敏感词详情", need = false)
    public void viewSensitiveWords(@PathVariable("sensitiveId") int sensitiveId, HttpServletResponse response) {
        SensitiveWords words = sensitiveWordsService.get(sensitiveId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(words));
    }
}
