package com.bfly.cms.words.action;

import com.bfly.core.web.ApiResponse;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.words.entity.CmsSearchWords;
import com.bfly.cms.words.service.CmsSearchWordsMng;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.web.util.CmsUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 热词管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:26
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsSearchWordApiAct {

    /**
     * 热词列表API
     *
     * @param siteId    站点ID 非必选 默认当前站
     * @param word      关键词 非必选
     * @param recommend 是否推荐 默认全部  1推荐  0非推荐 非必选  默认全部
     * @param orderBy   排序 1 搜索次数降序 2搜索次数升序 3优先级降序  4优先级升序  非必选 默认1
     * @param first     开始 非必选 默认0
     * @param count     数量 非必选 默认10
     */
    @RequestMapping(value = "/searchword/list")
    public void searchWordList(
            Integer siteId, Integer recommend, String word, Integer orderBy,
            Integer first, Integer count,
            HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        if (first == null) {
            first = 0;
        }
        if (count == null) {
            count = 10;
        }
        if (siteId == null) {
            siteId = CmsUtils.getSiteId(request);
        }
        if (recommend == null) {
            recommend = 2;
        }
        if (orderBy == null) {
            orderBy = 1;
        }
        List<CmsSearchWords> list = cmsSearchWordsMng.getList(siteId, word, recommend,
                orderBy, 0, count, true);
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson());
            }
        }
        String body = jsonArray.toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @Autowired
    private CmsSearchWordsMng cmsSearchWordsMng;
}

