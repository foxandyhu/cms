package com.bfly.admin.content.action;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.service.ChannelMng;
import com.bfly.cms.content.entity.CmsTopic;
import com.bfly.cms.content.service.CmsTopicMng;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.resource.service.CmsFileMng;
import com.bfly.cms.resource.service.TplManager;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.ChineseCharToEn;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.common.web.springmvc.MessageResolver;
import com.bfly.core.Constants;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.base.action.BaseAdminController;
import com.bfly.core.exception.ApiException;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CoreUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.bfly.core.Constants.TPLDIR_TOPIC;

/**
 * 专题管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/27 10:27
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsTopicApiAct extends BaseAdminController {
    private static final Logger log = LoggerFactory.getLogger(CmsTopicApiAct.class);

    /**
     * 专题列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/27 10:28
     */
    @RequestMapping("/topic/list")
    public void list(String initials, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getPage(initials, pageNo, pageSize);
        int totalCount = page.getTotalCount();
        List<CmsTopic> list = (List<CmsTopic>) page.getList();
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson());
            }
        }
        String body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/topic/get")
    public void get(Integer id, HttpServletResponse response, HttpServletRequest request) {
        CmsTopic bean;
        if (id == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        if (id.equals(0)) {
            bean = new CmsTopic();
        } else {
            bean = manager.findById(id);
        }
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean.init();
        String body = bean.convertToJson().toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/topic/save")
    public void save(CmsTopic bean, Integer channelId, String channelIds, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName(), bean.getPriority(), bean.getRecommend());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] channelIdArray = StrUtils.getInts(channelIds);
        bean.init();
        //添加首字母拼音字段数据
        bean.setInitials(ChineseCharToEn.getAllFirstLetter(bean.getName()));
        bean = manager.save(bean, channelId, channelIdArray);
        fileMng.updateFileByPath(bean.getContentImg(), true, null);
        fileMng.updateFileByPath(bean.getTitleImg(), true, null);
        log.info("save CmsTopic id={}", bean.getId());
        cmsLogMng.operating(request, "cmsTopic.log.save", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + "\"" + bean.getId() + "\"}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/topic/update")
    public void update(CmsTopic bean, Integer channelId, String channelIds, String oldTitleImg, String oldContentImg, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName(), bean.getPriority(), bean.getRecommend());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] channelIdArray = StrUtils.getInts(channelIds);
        //更新首字母拼音字段数据
        bean.setInitials(ChineseCharToEn.getAllFirstLetter(bean.getName()));
        bean = manager.update(bean, channelId, channelIdArray);
        //旧标题图
        fileMng.updateFileByPath(oldTitleImg, false, null);
        //旧内容图
        fileMng.updateFileByPath(oldContentImg, false, null);
        fileMng.updateFileByPath(bean.getContentImg(), true, null);
        fileMng.updateFileByPath(bean.getTitleImg(), true, null);
        log.info("update CmsTopic id={}.", bean.getId());
        cmsLogMng.operating(request, "cmsTopic.log.update", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + "\"" + bean.getId() + "\"}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/topic/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        try {
            Integer[] idArray = StrUtils.getInts(ids);
            CmsTopic[] beans = manager.deleteByIds(idArray);
            for (CmsTopic bean : beans) {
                fileMng.updateFileByPath(bean.getContentImg(), false, null);
                fileMng.updateFileByPath(bean.getTitleImg(), false, null);
                log.info("delete CmsTopic id={}", bean.getId());
                cmsLogMng.operating(request, "cmsTopic.log.delete", "id=" + bean.getId() + ";name=" + bean.getName());
            }
        } catch (Exception e) {
            throw new ApiException("删除出错", ResponseCode.API_CODE_DELETE_ERROR);
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/topic/priority")
    public void priority(String ids, String priorities, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, priorities);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArray = StrUtils.getInts(ids);
        Integer[] priority = StrUtils.getInts(priorities);
        errors = validatePriority(errors, idArray, priority);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_ERROR);
        }
        manager.updatePriority(idArray, priority);
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/topic/by_channel")
    public void channel(Integer channelId, HttpServletRequest request, HttpServletResponse response) {
        List<CmsTopic> list;
        if (channelId != null && !channelId.equals(0)) {
            Channel channel = channelMng.findById(channelId);
            if (channel == null) {
                throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
            }
            list = manager.getListByChannel(channelId);
        } else {
            list = manager.getListForTag(null, false, 0, Integer.MAX_VALUE);
        }
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, byChannelToJson(list.get(i)));
            }
        }
        String body = jsonArray.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/topic/tpl_list")
    public void tplList(HttpServletRequest request, HttpServletResponse response) {
        List<String> list = getTplList(request, null);
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i));
            }
        }
        String body = jsonArray.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private List<String> getTplList(HttpServletRequest request, String tpl) {
        CmsSite site = siteMng.getSite();
        List<String> tplList = tplManager.getNameListByPrefix(site.getSolutionPath() + "/" + TPLDIR_TOPIC + "/");
        String tplIndex = MessageResolver.getMessage(request, "tpl.topicIndex");
        tplList = CoreUtils.tplTrim(tplList, site.getTplPath(), tpl, tplIndex);
        return tplList;
    }

    private JSONObject byChannelToJson(CmsTopic bean) {
        JSONObject json = new JSONObject();
        if (bean.getId() != null) {
            json.put("id", bean.getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(bean.getName())) {
            json.put("name", bean.getName());
        } else {
            json.put("name", "");
        }
        return json;
    }

    private WebErrors validatePriority(WebErrors errors, Integer[] arr1, Integer[] arr2) {
        if (arr1 != null && arr2 != null) {
            if (arr1.length != arr2.length) {
                errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
                return errors;
            }
        }
        return errors;
    }

    @Autowired
    private TplManager tplManager;
    @Autowired
    private ChannelMng channelMng;
    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsFileMng fileMng;
    @Autowired
    private CmsTopicMng manager;
    @Autowired
    private CmsSiteMng siteMng;
}
