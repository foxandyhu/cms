package com.bfly.admin.comment.action;

import com.bfly.cms.comment.entity.CmsGuestbook;
import com.bfly.cms.comment.entity.CmsGuestbookCtg;
import com.bfly.cms.comment.entity.CmsGuestbookExt;
import com.bfly.cms.comment.service.CmsGuestbookCtgMng;
import com.bfly.cms.comment.service.CmsGuestbookMng;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.base.action.BaseAdminController;
import com.bfly.core.exception.ApiException;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
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
 * 留言管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 16:44
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsGuestbookAdminApiAct extends BaseAdminController {

    private static final Logger log = LoggerFactory.getLogger(CmsGuestbookAdminApiAct.class);

    /**
     * 留言管理
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 16:44
     */
    @RequestMapping("/guestbook/list")
    public void list(Integer queryCtgId, Boolean queryRecommend, Short queryChecked, Integer pageNo, Integer pageSize, HttpServletResponse response, HttpServletRequest request) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Integer[] ctgIds;
        List<CmsGuestbookCtg> ctgs = cmsGuestbookCtgMng.getList();
        ctgIds = CmsGuestbookCtg.fetchIds(ctgs);
        Pagination page = manager.getPage(queryCtgId, ctgIds, null, queryRecommend, queryChecked, true, false, pageNo, pageSize);
        int totalCount = page.getTotalCount();
        List<CmsGuestbook> list = (List<CmsGuestbook>) page.getList();
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

    @RequestMapping("/guestbook/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        if (id == null) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsGuestbook bean;
        if (id.equals(0)) {
            bean = new CmsGuestbook();
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
    @RequestMapping("/guestbook/save")
    public void save(CmsGuestbook bean, CmsGuestbookExt ext, Integer ctgId, HttpServletResponse response, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ctgId);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsGuestbookCtg ctg = cmsGuestbookCtgMng.findById(ctgId);
        if (ctg == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        bean.init();
        String ip = RequestUtils.getIpAddr(request);
        bean = manager.save(bean, ext, ctgId, ip);
        log.info("save CmsGuestbook id={}", bean.getId());
        cmsLogMng.operating(request, "cmsGuestbook.log.save", "id=" + bean.getId() + ";title=" + bean.getTitle());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/guestbook/update")
    public void update(CmsGuestbook bean, CmsGuestbookExt ext, Integer ctgId, String oldreply, HttpServletResponse response, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ctgId, bean.getId());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsGuestbookCtg ctg = cmsGuestbookCtgMng.findById(ctgId);
        CmsGuestbook guestbook = manager.findById(bean.getId());
        if (ctg == null || guestbook == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        Date now = new Date();
        if (StringUtils.isNotBlank(ext.getReply()) && !ext.getReply().equals(oldreply)) {
            bean.setReplayTime(now);
            if (bean.getAdmin() != null) {
                if (!bean.getAdmin().getId().equals(getAdmin().getId())) {
                    bean.setAdmin(getAdmin());
                }
            } else {
                bean.setAdmin(getAdmin());
            }
        }
        bean = manager.update(bean, ext, ctgId);
        log.info("update CmsGuestbook id={}.", bean.getId());
        cmsLogMng.operating(request, "cmsGuestbook.log.update", "id=" + bean.getId() + ";title=" + bean.getTitle());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/guestbook/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateArr(idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        CmsGuestbook[] beans = manager.deleteByIds(idArr);
        for (CmsGuestbook bean : beans) {
            log.info("delete CmsGuestbook id={}", bean.getId());
            cmsLogMng.operating(request, "cmsGuestbook.log.delete", "id=" + bean.getId() + ";title=" + bean.getTitle());
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/guestbook/check")
    public void check(String ids, Short isCheck, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, isCheck);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateArr(idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        CmsGuestbook[] beans = manager.checkByIds(idArr, getAdmin(), isCheck);
        for (CmsGuestbook bean : beans) {
            log.info("check CmsGuestbook id={}", bean.getId());
            log.info("cancelCheck CmsGuestbook id={}", bean.getId());
            cmsLogMng.operating(request, "cmsGuestbook.log.check", "id=" + bean.getId() + ";title=" + bean.getTitle());
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/guestbook/recommend")
    public void recommend(Integer id, Boolean isRecommend, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id, isRecommend);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsGuestbook bean = manager.findById(id);
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean.setRecommend(isRecommend);
        manager.update(bean, bean.getExt(), bean.getCtg().getId());
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/guestbook/reply")
    public void replay(Integer id, String reply, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id, reply);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsGuestbook bean = manager.findById(id);
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean.getExt().setReply(reply);
        Date now = new Date();
        bean.setReplayTime(now);
        bean.setAdmin(getAdmin());
        manager.update(bean, bean.getExt(), bean.getCtg().getId());
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateArr(Integer[] ids, HttpServletRequest request) {
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
        CmsGuestbook entity = manager.findById(id);
        if (errors.ifNotExist(entity, CmsGuestbook.class, id, false)) {
            return true;
        }
        return false;
    }

    @Autowired
    private CmsGuestbookCtgMng cmsGuestbookCtgMng;
    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsGuestbookMng manager;
}
