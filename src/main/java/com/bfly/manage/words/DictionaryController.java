package com.bfly.manage.words;

import com.bfly.cms.words.entity.Dictionary;
import com.bfly.cms.words.service.IDictionaryService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    @ActionModel(value = "获取数据字典集合", need = false)
    public void listDictionary(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap<String, Object>(3) {
            private static final long serialVersionUID = -9126101626116724049L;

            {
                String type = request.getParameter("type");
                if (type != null) {
                    put("type", request.getParameter("type"));
                }
            }
        };
        Map<String, Sort.Direction> sort = new HashMap<String, Sort.Direction>(1) {
            private static final long serialVersionUID = -9126101626116724049L;

            {
                put("type", Sort.Direction.ASC);
            }
        };
        Pager pager = dictionaryService.getPage(property, null, sort, null);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 根据类型查询所有的数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 11:24
     */
    @GetMapping(value = "/type/{type}.html")
    @ActionModel(value = "根据类型查询数据字典", need = false)
    public void getAllDictionaryByType(@PathVariable("type") String type, HttpServletResponse response) {
        Map<String, Object> exactMap = new HashMap<>(1);
        exactMap.put("type", type);
        List<Dictionary> list = dictionaryService.getList(exactMap);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 添加数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:32
     */
    @PostMapping(value = "/add")
    @ActionModel(value = "新增数据字典")
    public void addDictionary(@RequestBody @Valid Dictionary dictionary, BindingResult result, HttpServletResponse response) {
        validData(result);
        dictionaryService.save(dictionary);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:33
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改数据字典")
    public void editDictionary(@RequestBody @Valid Dictionary dictionary, BindingResult result, HttpServletResponse response) {
        validData(result);
        dictionaryService.edit(dictionary);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 删除数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:37
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除数据字典")
    public void removeDictionary(HttpServletResponse response, @RequestBody Integer... dirId) {
        dictionaryService.remove(dirId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 数据字典详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:38
     */
    @GetMapping(value = "/{dictionaryId}")
    @ActionModel(value = "获取数据字典详情", need = false)
    public void viewDictionary(@PathVariable("dictionaryId") int dictionaryId, HttpServletResponse response) {
        Dictionary dictionary = dictionaryService.get(dictionaryId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(dictionary));
    }

    /**
     * 获得数据字典类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:39
     */
    @RequestMapping(value = "/types")
    @ActionModel(value = "获取数据字典类型集合", need = false)
    public void listDictionayType(HttpServletResponse response) {
        List<String> types = dictionaryService.getTypes();
        ResponseUtil.writeJson(response, ResponseData.getSuccess(types));
    }
}
