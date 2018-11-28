package com.bfly.cms.message.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.message.entity.CmsReceiverMessage;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.message.service.CmsMessageMng;
import com.bfly.cms.message.service.CmsReceiverMessageMng;
import com.bfly.cms.message.entity.CmsMessage;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 站内信Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 18:38
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsMessageApiAct {
    private static final Logger log = LoggerFactory.getLogger(CmsMessageApiAct.class);

    /**
     * 站内信列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:38
     */
    @RequestMapping("/message/list")
    public void list(Integer pageNo, String title, Date sendBeginTime,
                     Date sendEndTime, Boolean status, Integer box, Integer pageSize,
                     HttpServletRequest request, HttpServletResponse response) {
        CmsUser user = CmsUtils.getUser(request);
        Pagination pagination = null;
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //默认找收件箱的
        if (box == null) {
            box = 0;
        }
        if (box.equals(0)) {
            pagination = receiverMessageMng.getPage(null, null, user
                            .getId(), title, sendBeginTime, sendEndTime, status, box,
                    false, pageNo, pageSize);
        } else if (box.equals(1)) {
            // 发件箱
            pagination = messageMng.getPage(null, user.getId(), null,
                    title, sendBeginTime, sendEndTime, status, box, false,
                    pageNo, pageSize);
        } else if (box.equals(2)) {
            //草稿箱
            pagination = messageMng.getPage(null, user.getId(), null,
                    title, sendBeginTime, sendEndTime, status, box, false,
                    pageNo, pageSize);
        } else if (box.equals(3)) {
            // 垃圾箱(可能从收件箱或者从发件箱转过来)
            pagination = receiverMessageMng.getPage(null, user.getId(),
                    user.getId(), title, sendBeginTime, sendEndTime, status,
                    box, false, pageNo, pageSize);
        }
        int totalCount = pagination.getTotalCount();
        JSONArray jsonArray = new JSONArray();
        if (box.equals(1) || box.equals(2)) {
            List<CmsMessage> list = (List<CmsMessage>) pagination.getList();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, list.get(i).convertToJson());
                }
            }
        } else {
            List<CmsReceiverMessage> list = (List<CmsReceiverMessage>) pagination.getList();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, list.get(i).convertToJson());
                }
            }
        }
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        String body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 站内信详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:39
     */
    @RequestMapping("/message/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        CmsMessage bean;
        if (id != null) {
            if (id.equals(0)) {
                bean = new CmsMessage();
            } else {
                bean = messageMng.findById(id);
            }
            if (bean != null) {
                body = bean.convertToJson().toString();
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            } else {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 发送站内信
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:39
     */
    @SignValidate
    @RequestMapping("/message/send")
    public void send(CmsMessage bean, String username, Integer groupId,
                     HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getMsgTitle());
        if (!errors.hasErrors()) {
            CmsSite site = CmsUtils.getSite(request);
            CmsUser user = CmsUtils.getUser(request);
            Date now = new Date();
            CmsReceiverMessage receiverMessage = new CmsReceiverMessage();
            CmsUser msgReceiverUser = userMng.findByUsername(username);
            if (msgReceiverUser == null && (groupId == null || groupId.equals(-1))) {
                message = Constants.API_MESSAGE_PARAM_ERROR;
                code = ResponseCode.API_CODE_PARAM_ERROR;
            } else {
                if (msgReceiverUser != null) {
                    messageInfoSet(bean, receiverMessage, user, msgReceiverUser,
                            now, site, request);
                }
                if (groupId != null && !groupId.equals(-1)) {
                    List<CmsUser> users;
                    CmsUser tempUser;
                    CmsMessage tempMsg;
                    CmsReceiverMessage tempReceiverMsg;
                    if (groupId.equals(0)) {
                        // 所有未禁用会员
                        users = userMng.getList(null, null, null, null, CmsUser.USER_STATU_CHECKED, false,
                                null);
                        if (users != null && users.size() > 0) {
                            for (int i = 0; i < users.size(); i++) {
                                tempUser = users.get(i);
                                tempMsg = new CmsMessage();
                                tempMsg.setMsgTitle(bean.getMsgTitle());
                                tempMsg.setMsgContent(bean.getMsgContent());
                                tempReceiverMsg = new CmsReceiverMessage();
                                if (msgReceiverUser != null) {
                                    if (!tempUser.equals(msgReceiverUser)) {
                                        messageInfoSet(tempMsg, tempReceiverMsg, user,
                                                tempUser, now, site, request);
                                    }
                                } else {
                                    messageInfoSet(tempMsg, tempReceiverMsg, user,
                                            tempUser, now, site, request);
                                }
                            }
                        }
                    } else {
                        // 非禁用的会员
                        users = userMng.getList(null, null, null, groupId,
                                CmsUser.USER_STATU_CHECKED, false, null);
                        if (users != null && users.size() > 0) {
                            for (int i = 0; i < users.size(); i++) {
                                tempUser = users.get(i);
                                tempMsg = new CmsMessage();
                                tempMsg.setMsgTitle(bean.getMsgTitle());
                                tempMsg.setMsgContent(bean.getMsgContent());
                                tempReceiverMsg = new CmsReceiverMessage();
                                if (msgReceiverUser != null) {
                                    if (!tempUser.equals(msgReceiverUser)) {
                                        messageInfoSet(tempMsg, tempReceiverMsg, user,
                                                tempUser, now, site, request);
                                    }
                                } else {
                                    messageInfoSet(tempMsg, tempReceiverMsg, user,
                                            tempUser, now, site, request);
                                }
                            }
                        }
                    }
                }
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 保存草稿
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:43
     */
    @SignValidate
    @RequestMapping("/message/save")
    public void save(CmsMessage bean, String username, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, username, bean.getMsgTitle());
        if (!errors.hasErrors()) {
            CmsSite site = CmsUtils.getSite(request);
            CmsUser user = CmsUtils.getUser(request);
            bean.setMsgBox(2);
            bean.setMsgSendUser(user);
            CmsUser msgReceiverUser = userMng.findByUsername(username);
            if (msgReceiverUser == null) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                bean.setMsgReceiverUser(msgReceiverUser);
                bean.setMsgStatus(false);
                // 作为草稿和发件箱的区别
                bean.setSendTime(null);
                bean.setSite(site);
                messageMng.save(bean);
                CmsReceiverMessage receiverMessage = new CmsReceiverMessage(bean);
                receiverMessage.setMsgBox(2);
                receiverMessage.setMessage(bean);
                // 接收端（有一定冗余）
                receiverMessageMng.save(receiverMessage);
                cmsLogMng.operating(request, "cmsMessage.log.save", "id="
                        + bean.getId() + ";title=" + bean.getMsgTitle());
                body = "{\"id\":" + bean.getId() + "}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 选择发送
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:43
     */
    @SignValidate
    @RequestMapping("/message/tosend")
    public void toSend(Integer id,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id);
        if (!errors.hasErrors()) {
            CmsMessage bean = messageMng.findById(id);
            if (bean == null) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                bean.setMsgBox(1);
                bean.setSendTime(new Date());
                messageMng.update(bean);
                Set<CmsReceiverMessage> receiverMessageSet = bean.getReceiverMsgs();
                Iterator<CmsReceiverMessage> it = receiverMessageSet.iterator();
                CmsReceiverMessage receiverMessage;
                while (it.hasNext()) {
                    receiverMessage = it.next();
                    receiverMessage.setMsgBox(0);
                    receiverMessage.setSendTime(new Date());
                    receiverMessage.setMessage(bean);
                    // 接收端（有一定冗余）
                    receiverMessageMng.update(receiverMessage);
                }
                log.info("member CmsMessage send CmsMessage success. id={}", bean
                        .getId());
                cmsLogMng.operating(request, "cmsMessage.log.send", "id="
                        + bean.getId() + ";title=" + bean.getMsgTitle());
                body = "{\"id\":" + bean.getId() + "}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 修改消息接口
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:43
     */
    @SignValidate
    @RequestMapping("/message/update")
    public void update(CmsMessage bean, HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getMsgTitle());
        if (!errors.hasErrors()) {
            bean = messageMng.update(bean);
            // 更新发送表的信息，收件表的信息同步更新
            Set<CmsReceiverMessage> receiverMessageSet = bean.getReceiverMsgs();
            Iterator<CmsReceiverMessage> it = receiverMessageSet.iterator();
            CmsReceiverMessage receiverMessage;
            while (it.hasNext()) {
                receiverMessage = it.next();
                receiverMessage.setMsgContent(bean.getContentHtml());
                receiverMessage.setMsgReceiverUser(bean.getMsgReceiverUser());
                receiverMessage.setMsgTitle(bean.getMsgTitle());
                receiverMessage.setMessage(bean);
                // 接收端（有一定冗余）
                receiverMessageMng.update(receiverMessage);
            }
            log.info("member CmsMessage update CmsMessage success. id={}", bean
                    .getId());
            cmsLogMng.operating(request, "cmsMessage.log.update", "id="
                    + bean.getId() + ";title=" + bean.getMsgTitle());
            body = "{\"id\":" + bean.getId() + "}";
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 阅读消息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:43
     */
    @RequestMapping("/message/read")
    public void read(Integer id, Integer msgBox, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id, msgBox);
        if (!errors.hasErrors()) {
            CmsUser user = CmsUtils.getUser(request);
            if (msgBox.equals(1)) {
                CmsMessage msg = messageMng.findById(id);
                if (msg == null) {
                    message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                    code = ResponseCode.API_CODE_NOT_FOUND;
                } else {
                    body = msg.convertToJson().toString();
                    message = Constants.API_MESSAGE_SUCCESS;
                    code = ResponseCode.API_CODE_CALL_SUCCESS;
                }
            } else {
                CmsReceiverMessage receiverMessage = receiverMessageMng.findById(id);
                if (receiverMessage != null) {
                    if (receiverMessage.getMsgReceiverUser() != null &&
                            receiverMessage.getMsgReceiverUser().equals(user)) {
                        receiverMessage.setMsgStatus(true);
                        receiverMessageMng.update(receiverMessage);
                    }
                    body = receiverMessage.convertToJson().toString();
                    message = Constants.API_MESSAGE_SUCCESS;
                    code = ResponseCode.API_CODE_CALL_SUCCESS;
                } else {
                    message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                    code = ResponseCode.API_CODE_NOT_FOUND;
                }
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 任意消息详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:44
     */
    @RequestMapping("/message/forward")
    public void forward(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        if (id != null) {
            CmsReceiverMessage bean = receiverMessageMng.findById(id);
            CmsMessage msg;
            if (bean != null) {
                body = bean.convertToJson().toString();
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            } else {
                msg = messageMng.findById(id);
                if (msg != null) {
                    body = msg.convertToJson().toString();
                    message = Constants.API_MESSAGE_SUCCESS;
                    code = ResponseCode.API_CODE_CALL_SUCCESS;
                } else {
                    message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                    code = ResponseCode.API_CODE_NOT_FOUND;
                }
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 回复消息详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:44
     */
    @RequestMapping("/message/reply")
    public void reply(Integer id, HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        if (id != null) {
            CmsReceiverMessage bean = receiverMessageMng.findById(id);
            if (bean != null) {
                body = bean.convertToJson().toString();
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            } else {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 垃圾箱
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:44
     */
    @SignValidate
    @RequestMapping("/message/trash")
    public void trash(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            CmsUser user = CmsUtils.getUser(request);
            Integer[] idArr = StrUtils.getInts(ids);
            CmsReceiverMessage receiverMessage;
            CmsMessage msg;
            for (Integer i = 0; i < idArr.length; i++) {
                msg = messageMng.findById(idArr[i]);
                receiverMessage = receiverMessageMng.findById(idArr[i]);
                if (msg != null && msg.getMsgSendUser().equals(user)) {
                    // 清空发信表的同时复制该数据到收信表（收信人未空）
                    receiverMessage = new CmsReceiverMessage();
                    receiverMessage.setMsgBox(3);
                    receiverMessage.setMsgContent(msg.getMsgContent());
                    receiverMessage.setMsgSendUser(msg.getMsgSendUser());
                    receiverMessage.setMsgReceiverUser(msg.getMsgReceiverUser());
                    receiverMessage.setMsgStatus(msg.getMsgStatus());
                    receiverMessage.setMsgTitle(msg.getMsgTitle());
                    receiverMessage.setSendTime(msg.getSendTime());
                    receiverMessage.setSite(msg.getSite());
                    receiverMessage.setMessage(msg);
                    // 接收端（有一定冗余）
                    receiverMessageMng.save(receiverMessage);
                    msg.setMsgBox(3);
                    messageMng.update(msg);
                    cmsLogMng.operating(request, "cmsMessage.log.trash", "id="
                            + msg.getId() + ";title="
                            + msg.getMsgTitle());
                }
                if (receiverMessage != null
                        && receiverMessage.getMsgReceiverUser().equals(user)) {
                    receiverMessage.setMsgBox(3);
                    receiverMessageMng.update(receiverMessage);
                    cmsLogMng.operating(request, "cmsMessage.log.trash", "id="
                            + receiverMessage.getId() + ";title="
                            + receiverMessage.getMsgTitle());
                }
                log.info("member CmsMessage trash CmsMessage success. id={}",
                        idArr[i]);
            }
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 移除消息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:44
     */
    @SignValidate
    @RequestMapping("/message/revert")
    public void revert(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            CmsUser user = CmsUtils.getUser(request);
            Integer[] idArr = StrUtils.getInts(ids);
            CmsReceiverMessage receiverMessage;
            CmsMessage bean;
            for (Integer i = 0; i < idArr.length; i++) {
                receiverMessage = receiverMessageMng.findById(idArr[i]);
                // 收件箱
                if (receiverMessage != null
                        && receiverMessage.getMsgReceiverUser().equals(user)) {
                    receiverMessage.setMsgBox(0);
                    receiverMessageMng.update(receiverMessage);
                    cmsLogMng.operating(request, "cmsMessage.log.revert", "id="
                            + receiverMessage.getId() + ";title=" + receiverMessage.getMsgTitle());
                }
                //发件箱移除过来
                if (receiverMessage != null
                        && receiverMessage.getMsgSendUser().equals(user)) {
                    bean = receiverMessage.getMessage();
                    if (bean != null) {
                        bean.setMsgBox(1);
                        messageMng.update(bean);
                    }
                    receiverMessageMng.deleteById(receiverMessage.getId());
                }
                log.info("member CmsMessage revert CmsMessage success. id={}",
                        idArr[i]);
            }
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 清空消息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:45
     */
    @SignValidate
    @RequestMapping("/message/empty")
    public void empty(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            CmsReceiverMessage receiverMessage;
            Integer[] idArr = StrUtils.getInts(ids);
            for (Integer i = 0; i < idArr.length; i++) {
                // 清空收到的站内信
                receiverMessage = receiverMessageMng.findById(idArr[i]);
                if (receiverMessage != null) {
                    receiverMessageMng.deleteById(idArr[i]);
                }
                CmsMessage msg = receiverMessage.getMessage();
                if (msg != null && msg.getReceiverMsgs().size() <= 0) {
                    messageMng.deleteById(msg.getId());
                }
            }
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 未读消息
     * @author andy_hulibo@163.com
     * @date 2018/11/25 18:45
     */
    @RequestMapping("/message/unread")
    public void findUnread(HttpServletRequest request, HttpServletResponse response) {
        CmsUser user = CmsUtils.getUser(request);
        CmsSite site = CmsUtils.getSite(request);
        List<CmsReceiverMessage> list = receiverMessageMng
                .getList(site.getId(), null, user.getId(), null, null,
                        null, false, 0, false, 0, 500);
        int count = 0;
        if (list != null && list.size() > 0) {
            count = list.size();
        }
        String body = "{\"count\":" + count + "}";
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private void messageInfoSet(CmsMessage message,
                                CmsReceiverMessage receiverMessage, CmsUser sendUser,
                                CmsUser receiverUser, Date sendTime, CmsSite site,
                                HttpServletRequest request) {
        message.setMsgBox(1);
        message.setMsgSendUser(sendUser);
        message.setMsgReceiverUser(receiverUser);
        message.setMsgStatus(false);
        message.setSendTime(sendTime);
        message.setSite(site);
        messageMng.save(message);
        receiverMessage.setMsgBox(0);
        receiverMessage.setMsgContent(message.getMsgContent());
        receiverMessage.setMsgSendUser(sendUser);
        receiverMessage.setMsgReceiverUser(receiverUser);
        receiverMessage.setMsgStatus(false);
        receiverMessage.setMsgTitle(message.getMsgTitle());
        receiverMessage.setSendTime(sendTime);
        receiverMessage.setSite(site);
        receiverMessage.setMessage(message);
        // 接收端（有一定冗余）
        receiverMessageMng.save(receiverMessage);
        log.info("member CmsMessage send CmsMessage success. id={}", message
                .getId());
        cmsLogMng.operating(request, "cmsMessage.log.send", "id="
                + message.getId() + ";title=" + message.getMsgTitle());
    }

    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsMessageMng messageMng;
    @Autowired
    private CmsReceiverMessageMng receiverMessageMng;
    @Autowired
    private CmsUserMng userMng;
}
