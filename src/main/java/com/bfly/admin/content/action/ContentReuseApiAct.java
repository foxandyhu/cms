package com.bfly.admin.content.action;

import com.bfly.core.base.action.BaseAdminController;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.Content.ContentStatus;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.web.ResponseUtils;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 内容复用Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:30
 */
@Controller
@RequestMapping(value = "/api/admin")
public class ContentReuseApiAct extends BaseAdminController{

    @RequestMapping("/content/reuse_list")
    public void list(String queryStatus, Integer queryTypeId, Boolean txtImgWhole, Boolean trimHtml,
                     Boolean queryTopLevel, Boolean queryRecommend, String queryUsername, String queryTitle,
                     Integer queryOrderBy,Integer pageNo, Integer pageSize,
                     Integer format, Boolean hasCollect, Integer https,
                     HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (queryTopLevel == null) {
            queryTopLevel = false;
        }
        if (queryRecommend == null) {
            queryRecommend = false;
        }
        if (queryOrderBy == null) {
            queryOrderBy = 0;
        }
        if (format == null) {
            format = 0;
        }
        if (hasCollect == null) {
            hasCollect = false;
        }
        if (https == null) {
            https = Constants.URL_HTTP;
        }
        if (txtImgWhole == null) {
            txtImgWhole = false;
        }
        if (trimHtml == null) {
            trimHtml = false;
        }
        ContentStatus status;
        if (!StringUtils.isBlank(queryStatus)) {
            status = ContentStatus.valueOf(queryStatus);
        } else {
            status = ContentStatus.all;
        }
        Integer queryInputUserId = null;
        if (!StringUtils.isBlank(queryUsername)) {
            CmsUser u = cmsUserMng.findByUsername(queryUsername);
            if (u != null) {
                queryInputUserId = u.getId();
            } else {
                // 用户名不存在，清空。
                queryUsername = null;
            }
        } else {
            queryInputUserId = 0;
        }
        Pagination p = contentMng.getPageBySite(queryTitle, queryTypeId,
                getAdmin().getId(), queryInputUserId, queryTopLevel, queryRecommend, status,
                queryOrderBy, pageNo, pageSize);
        List<Content> list = (List<Content>) p.getList();
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson(format, https, hasCollect, true, txtImgWhole, trimHtml));
            }
        }
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        String body = jsonArray.toString();
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/content/reuse_page")
    public void getPage(String queryStatus, Integer queryTypeId,
                        Boolean queryTopLevel, Boolean queryRecommend, String queryUsername, String queryTitle,
                        Integer queryOrderBy,Integer pageNo, Integer pageSize,
                        HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (queryTopLevel == null) {
            queryTopLevel = false;
        }
        if (queryRecommend == null) {
            queryRecommend = false;
        }
        if (queryOrderBy == null) {
            queryOrderBy = 0;
        }
        ContentStatus status;
        if (!StringUtils.isBlank(queryStatus)) {
            status = ContentStatus.valueOf(queryStatus);
        } else {
            status = ContentStatus.all;
        }
        Integer queryInputUserId = null;
        if (!StringUtils.isBlank(queryUsername)) {
            CmsUser u = cmsUserMng.findByUsername(queryUsername);
            if (u != null) {
                queryInputUserId = u.getId();
            } else {
                // 用户名不存在，清空。
                queryUsername = null;
            }
        } else {
            queryInputUserId = 0;
        }
        Pagination p = contentMng.getPageCountBySite(queryTitle, queryTypeId,
               getAdmin().getId(), queryInputUserId, queryTopLevel, queryRecommend, status,
                queryOrderBy, pageNo, pageSize);
        JSONObject json = getPage(p);
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        String body = json.toString();
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private JSONObject getPage(Pagination p) {
        JSONObject json = new JSONObject();
        json.put("pageNo", p.getPageNo());
        json.put("pageSize", p.getPageSize());
        json.put("totalCount", p.getTotalCount());
        json.put("totalPage", p.getTotalPage());
        json.put("firstPage", p.isFirstPage());
        json.put("lastPage", p.isLastPage());
        json.put("prePage", p.getPrePage());
        json.put("nextPage", p.getNextPage());
        return json;
    }

    @Autowired
    private ContentMng contentMng;
    @Autowired
    private CmsUserMng cmsUserMng;
}
