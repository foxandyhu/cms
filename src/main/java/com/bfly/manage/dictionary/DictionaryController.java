package com.bfly.manage.dictionary;

import com.bfly.cms.dictionary.entity.Dictionary;
import com.bfly.cms.dictionary.service.IDictionaryService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:21
 */
@RestController
@RequestMapping(value = "/manage/dictionary")
public class DictionaryController extends BaseManageController {

    @Autowired
    private IDictionaryService dictionaryService;

    /**
     * 数据字典列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:22
     */
    @GetMapping(value = "/list")
    public void listDictionary(HttpServletRequest request, HttpServletResponse response) {
        ContextUtil.initPager(request);
        Map<String, Object> property = new HashMap<String, Object>(3) {
            private static final long serialVersionUID = -9126101626116724049L;

            {
                put("type", request.getParameter("type"));
            }
        };
        Pager pager = dictionaryService.getPage(property);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 添加数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:32
     */
    @PostMapping(value = "/add")
    public void addDictionary(@Valid Dictionary dictionary, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        validData(result);
        dictionaryService.save(dictionary);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:33
     */
    @PostMapping(value = "/edit")
    public void editDictionary(@Valid Dictionary dictionary,BindingResult result, HttpServletResponse response) {
        validData(result);
        dictionaryService.edit(dictionary);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 删除数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:37
     */
    @PostMapping(value = "/del")
    public void removeDictionary(HttpServletRequest request, HttpServletResponse response) {
        String dictionaryIdStr = request.getParameter("ids");
        Integer[] dictionaryIds = DataConvertUtils.convertToIntegerArray(dictionaryIdStr.split(","));
        dictionaryService.remove(dictionaryIds);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 数据字典详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:38
     */
    @GetMapping(value = "/{dictionaryId}")
    public void viewDictionary(@PathVariable("dictionaryId") int dictionaryId, HttpServletResponse response) {
        Dictionary dictionary = dictionaryService.get(dictionaryId);
        ResponseUtil.writeJson(response, dictionary);
    }

    /**
     * 获得数据字典类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:39
     */
    @RequestMapping(value = "/types")
    public void listDictionayType(HttpServletResponse response) {
        List<String> types = dictionaryService.getTypes();
        ResponseUtil.writeJson(response, types);
    }
}
