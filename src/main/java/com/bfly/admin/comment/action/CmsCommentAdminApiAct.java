package com.bfly.admin.comment.action;

import com.bfly.cms.comment.entity.CmsComment;
import com.bfly.cms.comment.entity.CmsCommentExt;
import com.bfly.cms.comment.service.CmsCommentMng;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.DateUtils;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.base.action.BaseAdminController;
import com.bfly.core.exception.ApiException;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
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
import java.util.Date;
import java.util.List;

/**
 * 评论管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 16:25
 */
@Controller("adminCmsCommentApiAct")
@RequestMapping(value = "/api/admin")
public class CmsCommentAdminApiAct extends BaseAdminController {
    private static final Logger log = LoggerFactory.getLogger(CmsCommentAdminApiAct.class);

    /**
     * 查看文章全部评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 16:28
     */
    @RequestMapping("/comment/list_by_content")
    public void list(Integer queryContentId, Short queryChecked, Boolean queryRecommend, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        if (queryContentId == null) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getPage(queryContentId, null, queryChecked, queryRecommend, true, pageNo, pageSize);
        int totalCount = page.getTotalCount();
        List<CmsComment> list = (List<CmsComment>) page.getList();
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

    /**
     * 评论列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 16:25
     */
    @RequestMapping("/comment/list")
    public void newList(Integer queryContentId, Short queryChecked, Boolean queryRecommend, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getNewPage(queryContentId, queryChecked, queryRecommend, pageNo, pageSize);
        List<CmsComment> list = (List<CmsComment>) page.getList();
        int totalCount = page.getTotalCount();
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, createCommentJson(list.get(i)));
            }
        }
        String body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/comment/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        if (id == null) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsComment bean;
        if (id.equals(0)) {
            bean = new CmsComment();
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
    @RequestMapping("/comment/update")
    public void update(CmsComment bean, CmsCommentExt ext, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        errors = validateUpdate(errors, bean.getId());
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        // 若回复内容不为空而且回复更新，则设置回复时间，已最新回复时间为准
        if (StringUtils.isNotBlank(ext.getReply())) {
            bean.setReplayTime(new Date());
            bean.setReplayUser(CmsUtils.getUser(request));
        }
        bean = manager.update(bean, ext);
        log.info("update CmsComment id={}.", bean.getId());
        cmsLogMng.operating(request, "cmsComment.log.update", "id=" + bean.getId());
        String body = "{\"id\":\"" + bean.getId() + "\"}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/comment/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateExist(idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        try {
            CmsComment[] beans = manager.deleteByIds(idArr);
            for (CmsComment bean : beans) {
                log.info("delete CmsComment id={}", bean.getId());
                cmsLogMng.operating(request, "cmsComment.log.delete", "id=" + bean.getId());
            }
        } catch (Exception e) {
            log.error("删除出错", e);
            throw new ApiException("删除出错", ResponseCode.API_CODE_DELETE_ERROR);
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 审核
     *
     * @param ids
     * @param isCheck  0未审核 1审核通过 2审核不通过
     * @param request
     * @param response
     */
    @SignValidate
    @RequestMapping("/comment/check")
    public void check(String ids, Short isCheck, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, isCheck);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateExist(idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        CmsComment[] beans = manager.checkByIds(idArr, isCheck);
        for (CmsComment bean : beans) {
            log.info("check CmsGuestbook id={}", bean.getId());
            cmsLogMng.operating(request, "cmsComment.log.check", "id=" + bean.getId() + ";title=" + bean.getReplayHtml());
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/comment/recommend")
    public void recommend(Integer id, Boolean isRecommend, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id, isRecommend);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsComment bean = manager.findById(id);
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean.setRecommend(isRecommend);
        manager.update(bean, bean.getCommentExt());
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/comment/reply")
    public void reply(Integer id, String reply, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id, reply);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsComment bean = manager.findById(id);
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean.getCommentExt().setReply(reply);
        manager.update(bean, bean.getCommentExt());
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }


    private JSONObject createCommentJson(CmsComment bean) {
        JSONObject json = new JSONObject();
        if (bean.getId() != null) {
            json.put("id", bean.getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(bean.getText())) {
            json.put("text", bean.getText());
        } else {
            json.put("text", "");
        }
        if (bean.getChecked() != null) {
            json.put("checked", bean.getChecked());
        } else {
            json.put("checked", "");
        }
        if (bean.getCommentUser() != null && StringUtils.isNotBlank(bean.getCommentUser().getUsername())) {
            json.put("commenteUsername", bean.getCommentUser().getUsername());
        } else {
            json.put("commenteUsername", "");
        }
        if (bean.getCreateTime() != null) {
            json.put("createTime", DateUtils.parseDateToTimeStr(bean.getCreateTime()));
        } else {
            json.put("createTime", "");
        }
        if (StringUtils.isNotBlank(bean.getIp())) {
            json.put("ip", bean.getIp());
        } else {
            json.put("ip", "");
        }
        if (bean.getContent() != null && bean.getContent().getId() != null) {
            json.put("contentId", bean.getContent().getId());
        } else {
            json.put("contentId", "");
        }
        if (bean.getContent() != null && StringUtils.isNotBlank(bean.getContent().getTitle())) {
            json.put("contentTitle", bean.getContent().getTitle());
        } else {
            json.put("contentTitle", "");
        }
        if (bean.getContent() != null && bean.getContent().getChannel() != null && StringUtils.isNotBlank(bean.getContent().getChannel().getName())) {
            json.put("channelName", bean.getContent().getChannel().getName());
        } else {
            json.put("channelName", "");
        }
        if (bean.getContent() != null && StringUtils.isNotBlank(bean.getContent().getUrl())) {
            json.put("contentURL", bean.getContent().getUrl());
        } else {
            json.put("contentURL", "");
        }
        if (bean.getCommentExt() != null && bean.getCommentExt().getId() != null) {
            json.put("replyId", bean.getCommentExt().getId());
        } else {
            json.put("replyId", "");
        }
        if (bean.getCommentExt() != null && StringUtils.isNotBlank(bean.getCommentExt().getReply())) {
            json.put("replyContent", bean.getCommentExt().getReply());
        } else {
            json.put("replyContent", "");
        }
        if (bean.getRecommend() != null) {
            json.put("recommend", bean.getRecommend());
        } else {
            json.put("recommend", "");
        }
        return json;
    }


    private WebErrors validateUpdate(WebErrors errors, Integer id) {
        if (vldExist(id, errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateExist(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifEmpty(ids, "ids", false)) {
            return errors;
        }
        for (Integer id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        CmsComment entity = manager.findById(id);
        return errors.ifNotExist(entity, CmsComment.class, id, false);
    }

    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsCommentMng manager;
}
