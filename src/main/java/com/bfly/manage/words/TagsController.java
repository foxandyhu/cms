package com.bfly.manage.words;

import com.bfly.cms.words.entity.Tags;
import com.bfly.cms.words.service.ITagsService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * 文章内容Tags Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:49
 */
@RestController
@RequestMapping(value = "/manage/tags")
public class TagsController extends BaseManageController {

    @Autowired
    private ITagsService tagsService;

    /**
     * 文章内容标签列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 14:50
     */
    @GetMapping(value = "/list")
    public void listTags(HttpServletResponse response) {
        ContextUtil.initPager(getRequest());
        Pager pager = tagsService.getPage(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = 7922136427435487363L;

            {
                put("name", getRequest().getParameter("name"));
            }
        });
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 添加文章内容Tags
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 9:51
     */
    @PostMapping(value = "/add")
    public void addTags(@Valid Tags tags, BindingResult result, HttpServletResponse response) {
        validData(result);
        tagsService.save(tags);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑问政内容Tags
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 9:52
     */
    @PostMapping(value = "/edit")
    public void editTags(@Valid Tags tags,BindingResult result, HttpServletResponse response) {
        validData(result);
        tagsService.edit(tags);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 文章内容Tags详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 9:53
     */
    @GetMapping(value = "/{tagsId}")
    public void viewTags(@PathVariable("tagsId") int tagsId, HttpServletResponse response) {
        Tags tags = tagsService.get(tagsId);
        ResponseUtil.writeJson(response, tags);
    }

    /**
     * 删除文章内容Tags
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 14:52
     */
    @PostMapping(value = "/del")
    public void delTags(HttpServletResponse response) {
        String tagsIdStr = getRequest().getParameter("ids");
        Integer[] tagIds = DataConvertUtils.convertToIntegerArray(tagsIdStr.split(","));
        tagsService.remove(tagIds);
        ResponseUtil.writeJson(response, "");
    }
}
