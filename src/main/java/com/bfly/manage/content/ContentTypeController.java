package com.bfly.manage.content;

import com.bfly.cms.content.entity.ContentType;
import com.bfly.cms.content.service.IContentTypeService;
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
import java.util.HashMap;

/**
 * 内容类型Conntroller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 9:57
 */
@RestController
@RequestMapping(value = "/manage/content/type")
public class ContentTypeController extends BaseManageController {

    @Autowired
    private IContentTypeService contentTypeService;

    /**
     * 类容类型列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 9:59
     */
    @GetMapping(value = "/list")
    public void listContentType(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = contentTypeService.getPage(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = 8771067789868205257L;

            {
                put("enabled", getRequest().getParameter("enabled"));
            }
        });
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 新增类容类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:01
     */
    @PostMapping(value = "/add")
    public void addContentType(@Valid ContentType contentType, BindingResult result, HttpServletResponse response) {
        validData(result);
        contentTypeService.save(contentType);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 编辑内容类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:02
     */
    @PostMapping(value = "/edit")
    public void editContentType(@Valid ContentType contentType,BindingResult result, HttpServletResponse response) {
        validData(result);
        contentTypeService.edit(contentType);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 查看内容类型信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:03
     */
    @GetMapping(value = "/{typeId}")
    public void viewContentType(@PathVariable("typeId") int typeId, HttpServletResponse response) {
        ContentType contentType = contentTypeService.get(typeId);
        ResponseUtil.writeJson(response, contentType);
    }

    /**
     * 删除内容类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 10:04
     */
    @PostMapping(value = "/del")
    public void delContentType(HttpServletResponse response) {
        String idStr = getRequest().getParameter("ids");
        Integer[] ids = DataConvertUtils.convertToIntegerArray(idStr.split(","));
        contentTypeService.remove(ids);
        ResponseUtil.writeJson(response, "");
    }
}
