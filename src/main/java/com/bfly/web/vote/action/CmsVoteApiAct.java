//package com.bfly.web.vote.action;
//
//import com.bfly.cms.member.entity.Member;
//import com.bfly.cms.vote.entity.VoteTopic;
//import com.bfly.cms.webservice.entity.WsRecord;
//import com.bfly.cms.webservice.service.ApiRecordMng;
//import com.bfly.common.util.ArrayUtils;
//import com.bfly.common.web.ResponseUtils;
//import com.bfly.core.Constants;
//import com.bfly.core.web.ApiResponse;
//import com.bfly.core.web.ApiValidate;
//import com.bfly.core.web.ResponseCode;
//import com.bfly.core.web.WebErrors;
//import com.bfly.core.web.util.CmsUtils;
//import org.apache.commons.lang.StringUtils;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author andy_hulibo@163.com
// * @date 2018/11/27 11:45
// */
//@Controller
//public class CmsVoteApiAct extends AbstractVote {
//
//    /**
//     * 调查列表API
//     *
//     * @param siteId 站点ID 非必选 默认当前站
//     * @param def    是否默认  非必选 默认全部  true默认  false 非默认
//     * @param first  开始 非必选 默认0
//     * @param count  数量 非必选 默认10
//     */
//    @RequestMapping(value = "/api/front/vote/list")
//    public void cmsVoteList(Integer siteId,
//                            Boolean def, Integer first, Integer count,
//                            HttpServletRequest request, HttpServletResponse response)
//            throws JSONException {
//        if (siteId == null) {
//            siteId = CmsUtils.getSiteId(request);
//        }
//        if (first == null) {
//            first = 0;
//        }
//        if (count == null) {
//            count = 10;
//        }
//        List<VoteTopic> list = cmsVoteTopicMng.getList(
//                def, siteId, first, count);
//        JSONArray jsonArray = new JSONArray();
//        if (list != null && list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                jsonArray.put(i, list.get(i).convertToJson(false));
//            }
//        }
//        String body = jsonArray.toString();
//        String message = Constants.API_MESSAGE_SUCCESS;
//        String code = ResponseCode.API_CODE_CALL_SUCCESS;
//        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
//        ResponseUtils.renderApiJson(response, request, apiResponse);
//    }
//
//    /**
//     * 投票信息获取
//     *
//     * @param id 投票ID
//     */
//    @RequestMapping(value = "/api/front/vote/get")
//    public void cmsVoteGet(Integer id, HttpServletRequest request, HttpServletResponse response) throws JSONException {
//        String body = "\"\"";
//        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
//        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
//        if (id != null) {
//            VoteTopic voteTopic = cmsVoteTopicMng.findById(id);
//            if (voteTopic != null) {
//                body = voteTopic.convertToJson(true).toString();
//                message = Constants.API_MESSAGE_SUCCESS;
//                code = ResponseCode.API_CODE_CALL_SUCCESS;
//            } else {
//                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
//                code = ResponseCode.API_CODE_NOT_FOUND;
//            }
//        }
//        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
//        ResponseUtils.renderApiJson(response, request, apiResponse);
//    }
//
//
//    /**
//     * 投票API
//     *
//     * @param siteId     站点ID 非必选 默认当前站
//     * @param voteId     投票ID 必选
//     * @param subIds     调查题目ID 逗号,分隔  必选
//     * @param itemIds    投票的调查题目选择性子项id  逗号,分隔  必选
//     * @param subTxtIds  投票的调查题目选文本性项id  非必选
//     * @param replys     投票的调查题目选文本性项回复内容  非必选
//     * @param sessionKey 会话标志   非必选
//     * @param appId      appid 必选
//     * @param nonce_str  随机数 必选
//     * @param sign       签名 必选
//     */
//    @RequestMapping(value = "/api/member/vote/save")
//    public void cmsVoteSave(
//            Integer siteId,
//            Integer voteId, String subIds,
//            String itemIds, String subTxtIds, String replys,
//            String sessionKey,
//            String appId, String nonce_str, String sign,
//            HttpServletRequest request,
//            HttpServletResponse response, ModelMap model) throws JSONException {
//        String body = "\"\"";
//        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
//        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
//        WebErrors errors = WebErrors.create(request);
//        Member user = CmsUtils.getUser(request);
//        //验证公共非空参数
//        errors = ApiValidate.validateRequiredParams(request, errors, appId,
//                nonce_str, sign, voteId, subIds, itemIds);
//        if (!errors.hasErrors()) {
//            VoteTopic voteTopic = cmsVoteTopicMng.findById(voteId);
//            if (voteTopic != null) {
//                //签名数据不可重复利用
//                WsRecord record = apiRecordMng.findBySign(sign, appId);
//                if (record != null) {
//                    message = Constants.API_MESSAGE_REQUEST_REPEAT;
//                } else {
//                    Integer[] intSubIds = ArrayUtils.parseStringToArray(subIds);
//                    Integer[] intSubTxtIds = ArrayUtils.parseStringToArray(subTxtIds);
//                    String[] reply = null;
//                    if (StringUtils.isNotBlank(replys)) {
//                        reply = replys.split(Constants.API_ARRAY_SPLIT_STR);
//                    }
//                    VoteTopic vote = voteByApi(user, voteId, intSubIds,
//                            parseStringToArrayList(itemIds), intSubTxtIds,
//                            reply, request, response, model);
//                    if (vote != null) {
//                        body = "{\"id\":" + "\"" + vote.getId() + "\"}";
//                        message = Constants.API_MESSAGE_SUCCESS;
//                        code = ResponseCode.API_CODE_CALL_SUCCESS;
//                    } else {
//                        Object voteResult = model.get("status");
//                        body = "{\"voteResult\":\"" + voteResult + "\"}";
//                        message = Constants.API_MESSAGE_PARAM_ERROR;
//                        code = ResponseCode.API_CODE_PARAM_ERROR;
//                    }
//                }
//            } else {
//                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
//                code = ResponseCode.API_CODE_NOT_FOUND;
//            }
//        }
//        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
//        ResponseUtils.renderApiJson(response, request, apiResponse);
//    }
//
//    public static List<Integer[]> parseStringToArrayList(String ids) {
//        if (StringUtils.isNotBlank(ids)) {
//            List<Integer[]> li = new ArrayList<Integer[]>();
//            String[] listArray = ids.split(Constants.API_LIST_SPLIT_STR);
//            for (String array : listArray) {
//                Integer[] intArray = ArrayUtils.parseStringToArray(array);
//                li.add(intArray);
//            }
//            return li;
//        } else {
//            return null;
//        }
//    }
//
//    @Autowired
//    private ApiRecordMng apiRecordMng;
//}
//
