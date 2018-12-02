package com.bfly.admin.lucene.action;

import com.bfly.core.web.ApiResponse;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.lucene.service.LuceneContentSvc;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.util.CmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Lucene Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 13:43
 */
@Controller
@RequestMapping(value = "/api/admin")
public class LuceneContentAct {

    /**
     * 创建全文索引
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:42
     */
    @SignValidate
    @RequestMapping("/lucene/create")
    public void create(Integer channelId, Date startDate,
                       Date endDate, Integer startId, Integer max,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message;
        String code;
        Integer siteId = CmsUtils.getSiteId(request);
        if (max == null) {
            max = 1000;
        }
        try {
            Integer lastId = luceneContentSvc.createIndex(siteId, channelId,
                    startDate, endDate, startId, max);
            if (lastId != null) {
                body = "{\"lastId\":" + lastId + "}";
            } else {
                body = "{\"lastId\":\"\"}";
            }
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        } catch (Exception e) {
            message = Constants.API_MESSAGE_CREATE_ERROR;
            code = ResponseCode.API_CODE_CALL_FAIL;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @Autowired
    private LuceneContentSvc luceneContentSvc;

}
