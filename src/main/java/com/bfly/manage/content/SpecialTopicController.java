package com.bfly.manage.content;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.content.entity.SpecialTopic;
import com.bfly.cms.content.service.ISpecialTopicService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 专题Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/6 15:34
 */
@RestController
@RequestMapping(value = "/manage/topic")
public class SpecialTopicController extends BaseManageController {

    @Autowired
    private ISpecialTopicService topicService;

    /**
     * 专题列表
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:35
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "专题列表", need = false)
    public void listSpecialTopic(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());

        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("seq", Sort.Direction.ASC);
        Pager pager = topicService.getPage(null, null, sortMap);
        if (pager.getData() != null) {
            List<SpecialTopic> topics = pager.getData();
            topics.forEach(topic -> {
                if (StringUtils.hasLength(topic.getTitleImg())) {
                    topic.setTitleImg(ResourceConfig.getServer() + topic.getTitleImg());
                }
                if (StringUtils.hasLength(topic.getContentImg())) {
                    topic.setContentImg(ResourceConfig.getServer() + topic.getContentImg());
                }
            });

        }
        JSONObject json = JsonUtil.toJsonFilter(pager);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 添加专题
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:39
     */
    @PostMapping(value = "/add")
    @ActionModel("添加专题")
    public void addSpecialTopic(@RequestBody @Valid SpecialTopic topic, BindingResult result, HttpServletResponse response) {
        validData(result);
        topicService.save(topic);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }


    /**
     * 修改专题
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:39
     */
    @PostMapping(value = "/edit")
    @ActionModel("编辑专题")
    public void editSpecialTopic(@RequestBody @Valid SpecialTopic topic, BindingResult result, HttpServletResponse response) {
        validData(result);
        topicService.edit(topic);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查看专题信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:40
     */
    @GetMapping(value = "/{topicId}")
    @ActionModel(value = "栏目详情", need = false)
    public void viewSpecialTopic(@PathVariable("topicId") int topicId, HttpServletResponse response) {
        SpecialTopic topic = topicService.get(topicId);
        if (topic != null) {
            if (StringUtils.hasLength(topic.getTitleImg())) {
                topic.setTitleImg(ResourceConfig.getServer() + topic.getTitleImg());
            }
            if (StringUtils.hasLength(topic.getContentImg())) {
                topic.setContentImg(ResourceConfig.getServer() + topic.getContentImg());
            }
        }
        JSONObject json = JsonUtil.toJsonFilter(topic);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 删除专题
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:41
     */
    @PostMapping(value = "/del")
    @ActionModel("删除专题")
    public void delSpecialTopic(HttpServletResponse response, @RequestBody Integer... ids) {
        topicService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 专题排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:41
     */
    @GetMapping(value = "/sort/{upItemId}-{downItemId}")
    @ActionModel("专题排序")
    public void sortSpecialTopic(HttpServletResponse response, @PathVariable("upItemId") int upItemId, @PathVariable("downItemId") int downItemId) {
        topicService.sortSpecialTopic(upItemId, downItemId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获得专题模板
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 16:36
     */
    @GetMapping(value = "/template")
    @ActionModel(value = "获得专题模板",need = false)
    public void getSpecialTopicTemplate(HttpServletResponse response) {
        JSONObject json = new JSONObject();

        String pcPath = ResourceConfig.getTemplateForPcPath("topic");
        Object[] pcTpl = getTpl(pcPath);
        json.put("pc", pcTpl);

        String mobilePath = ResourceConfig.getTemplateForMobilePath("topic");
        Object[] mobileTpl = getTpl(mobilePath);
        json.put("mobile", mobileTpl);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 读取目录模板文件名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/5 19:50
     */
    private Object[] getTpl(String path) {
        File mobileFile = new File(path);
        if (mobileFile.exists()) {
            Stream<File> stream = Stream.of(mobileFile.listFiles());
            Stream<String> streamFileName = stream.filter(file -> !file.isDirectory()).map(file -> file.getName());
            return streamFileName.toArray();
        }
        return null;
    }

    /**
     * 获得文章关联的专题
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 20:04
     */
    @GetMapping(value = "/article/{articleId}")
    @ActionModel(value = "获得文章关联的专题", need = false)
    public void getSpecialTopicForArticle(HttpServletResponse response, @PathVariable("articleId") int articleId) {
        List<SpecialTopic> topics = topicService.getSpecialTopicForArticle(articleId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(topics));
    }
}
