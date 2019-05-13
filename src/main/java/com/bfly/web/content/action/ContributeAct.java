//package com.bfly.web.content.action;
//
//import com.bfly.cms.channel.entity.Channel;
//import com.bfly.cms.channel.entity.Model;
//import com.bfly.cms.channel.service.ChannelMng;
//import com.bfly.cms.content.entity.*;
//import com.bfly.cms.content.service.CmsModelMng;
//import com.bfly.cms.content.service.ContentMng;
//import com.bfly.cms.content.service.ContentTypeMng;
//import com.bfly.cms.resource.service.CmsFileMng;
//import com.bfly.cms.resource.service.DbFileMng;
//import com.bfly.cms.system.entity.Oss;
//import com.bfly.cms.siteconfig.entity.Site;
//import com.bfly.cms.system.entity.Ftp;
//import com.bfly.cms.staticpage.ContentStatusChangeThread;
//import com.bfly.cms.member.entity.MemberConfig;
//import com.bfly.cms.system.service.CmsConfigContentChargeMng;
//import com.bfly.cms.system.service.CmsConfigMng;
//import com.bfly.cms.member.entity.Member;
//import com.bfly.cms.user.service.CmsUserMng;
//import com.bfly.common.page.Pagination;
//import com.bfly.common.upload.FileRepository;
//import com.bfly.common.util.StrUtils;
//import com.bfly.common.web.CookieUtils;
//import com.bfly.core.base.action.RenderController;
//import com.bfly.core.web.WebErrors;
//import com.bfly.core.web.util.CmsUtils;
//import com.octo.captcha.service.CaptchaServiceException;
//import com.octo.captcha.service.image.ImageCaptchaService;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//
//import static com.bfly.common.page.SimplePage.cpn;
//
///**
// * 会员投稿Action
// *
// * @author andy_hulibo@163.com
// * @date 2018/11/30 16:06
// */
//@Controller
//public class ContributeAct extends RenderController {
//
//    /**
//     * 校验
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/30 16:17
//     */
//    private String check(ModelMap model) {
//        MemberConfig mcfg = getSite().getConfig().getMemberConfig();
//        if (!mcfg.isMemberOn()) {
//            return renderMessagePage(model, "member.memberClose");
//        }
//        if (getMember() == null) {
//            return renderLoginPage(model);
//        }
//        return null;
//    }
//
//    /**
//     * 会员投稿列表
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/30 16:06
//     */
//    @RequestMapping(value = "/member/contribute_list.html")
//    public String list(String queryTitle, Integer modelId, Integer queryChannelId, Integer pageNo, ModelMap model) {
//        String result = check(model);
//        if (result != null) {
//            return result;
//        }
//        Pagination p = contentMng.getPageForMember(queryTitle, queryChannelId, getSite().getId(), modelId, getMember().getId(), cpn(pageNo), CookieUtils.getPageSize(getRequest()));
//        model.addAttribute("pagination", p);
//        if (!StringUtils.isBlank(queryTitle)) {
//            model.addAttribute("q", queryTitle);
//        }
//        if (modelId != null) {
//            model.addAttribute("modelId", modelId);
//        }
//        return renderPage("member/contribute_list.html", model);
//    }
//
//    /**
//     * 会员投稿添加
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/30 16:07
//     */
//    @RequestMapping(value = "/member/contribute_add.html")
//    public String add(ModelMap model) {
//        String result = check(model);
//        if (result != null) {
//            return result;
//        }
//        // 获得本站栏目列表
//        Site site = getSite();
//        Member user = getMember();
//        Set<Channel> rights = user.getGroup().getContriChannels();
//        List<Channel> topList = channelMng.getTopList(true);
//        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
//        model.addAttribute("site", site);
//        model.addAttribute("channelList", channelList);
//        model.addAttribute("sessionId", getSession().getId());
//        model.addAttribute("contentChargeConfig", cmsConfigContentChargeMng.getDefault());
//        model.addAttribute("config", cmsConfigMng.get());
//        return renderPage("member/contribute_add.html", model);
//    }
//
//    /**
//     * 会员投稿保存
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/30 16:07
//     */
//    @RequestMapping(value = "/member/contribute_save.html")
//    public String save(String title, String author, String description, String txt, String tagStr, Integer channelId, Integer modelId, String captcha, String mediaPath, String mediaType, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Short charge, Double chargeAmount, Boolean rewardPattern, Double rewardRandomMin, Double rewardRandomMax, Double[] rewardFix, String nextUrl, String typeImg, ModelMap model) {
//        String result = check(model);
//        if (result != null) {
//            return result;
//        }
//        WebErrors errors = validateSave(title, author, description, tagStr, channelId, captcha);
//        if (errors.hasErrors()) {
//            return renderErrorPage(model, errors);
//        }
//        Content c = new Content();
//        c.setSite(getSite());
//        Model defaultModel = cmsModelMng.getDefModel();
//        if (modelId != null) {
//            Model m = cmsModelMng.findById(modelId);
//            if (m != null) {
//                c.setModel(m);
//            } else {
//                c.setModel(defaultModel);
//            }
//        } else {
//            c.setModel(defaultModel);
//        }
//        ContentExt ext = new ContentExt();
//        if (StringUtils.isNotBlank(typeImg)) {
//            ext.setTypeImg(typeImg);
//        }
//        ext.setTitle(title);
//        ext.setAuthor(author);
//        ext.setDescription(description);
//        ext.setMediaPath(mediaPath);
//        ext.setMediaType(mediaType);
//        ContentTxt t = new ContentTxt();
//        t.setTxt(txt);
//        ContentType type = contentTypeMng.getDef();
//        if (type == null) {
//            throw new RuntimeException("Default ContentType not found.");
//        }
//        Integer typeId = type.getId();
//        String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", null);
//        if (c.getRecommendLevel() == null) {
//            c.setRecommendLevel((byte) 0);
//        }
//        c = contentMng.save(c, ext, t, null, null, null, tagArr, attachmentPaths, attachmentNames, attachmentFilenames, picPaths, picDescs, channelId, typeId, null, true, charge, chargeAmount, rewardPattern, rewardRandomMin, rewardRandomMax, rewardFix, getMember(), true);
//        afterContentStatusChange(c, null, ContentStatusChangeThread.OPERATE_ADD);
//        return renderSuccessPage(model, nextUrl);
//    }
//
//    /**
//     * 会员投稿修改
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/30 16:08
//     */
//
//    @RequestMapping(value = "/member/contribute_edit.html")
//    public String edit(Integer id, ModelMap model) {
//        String result = check(model);
//        if (result != null) {
//            return result;
//        }
//        WebErrors errors = validateEdit(id);
//        if (errors.hasErrors()) {
//            return renderErrorPage(model, errors);
//        }
//        Content content = contentMng.findById(id);
//        // 获得本站栏目列表
//        Set<Channel> rights = getMember().getGroup().getContriChannels();
//        List<Channel> topList = channelMng.getTopList(true);
//        List<Channel> channelList = Channel.getListForSelect(topList, rights, true);
//        model.addAttribute("content", content);
//        model.addAttribute("site", getSite());
//        model.addAttribute("channelList", channelList);
//        model.addAttribute("sessionId", getSession().getId());
//        model.addAttribute("contentChargeConfig", cmsConfigContentChargeMng.getDefault());
//        model.addAttribute("config", cmsConfigMng.get());
//        return renderPage("member/contribute_edit.html", model);
//    }
//
//    /**
//     * 会有投稿更新
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/30 16:08
//     */
//    @RequestMapping(value = "/member/contribute_update.html")
//    public String update(Integer id, String title, String author, String description, String txt, String tagStr, Integer channelId, String mediaPath, String mediaType, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Short charge, Double chargeAmount, Boolean rewardPattern, Double rewardRandomMin, Double rewardRandomMax, Double[] rewardFix, String nextUrl, ModelMap model) {
//        String result = check(model);
//        if (result != null) {
//            return result;
//        }
//        WebErrors errors = validateUpdate(id, channelId);
//        if (errors.hasErrors()) {
//            return renderErrorPage(model, errors);
//        }
//        Content c = new Content();
//        c.setId(id);
//        c.setSite(getSite());
//        ContentExt ext = new ContentExt();
//        ext.setId(id);
//        ext.setTitle(title);
//        ext.setAuthor(author);
//        ext.setDescription(description);
//        ext.setMediaPath(mediaPath);
//        ext.setMediaType(mediaType);
//        ContentTxt t = new ContentTxt();
//        t.setId(id);
//        t.setTxt(txt);
//        String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", null);
//        List<Map<String, Object>> list = contentMng.preChange(contentMng.findById(id));
//        c = contentMng.update(c, ext, t, tagArr, null, null, null, attachmentPaths, attachmentNames, attachmentFilenames, picPaths, picDescs, null, channelId, null, null, charge, chargeAmount, rewardPattern, rewardRandomMin, rewardRandomMax, rewardFix, getMember(), true);
//        afterContentStatusChange(c, list, ContentStatusChangeThread.OPERATE_UPDATE);
//        return renderSuccessPage(model, nextUrl);
//    }
//
//    /**
//     * 会员投稿删除
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/30 16:08
//     */
//    @RequestMapping(value = "/member/contribute_delete.html")
//    public String delete(Integer[] ids, String nextUrl, ModelMap model) {
//        String result = check(model);
//        if (result != null) {
//            return result;
//        }
//        WebErrors errors = validateDelete(ids);
//        if (errors.hasErrors()) {
//            return renderErrorPage(model, errors);
//        }
//        Map<Integer, List<Map<String, Object>>> map = new HashMap<>(4);
//        for (Integer id : ids) {
//            List<Map<String, Object>> list = contentMng.preChange(contentMng.findById(id));
//            map.put(id, list);
//        }
//        Content[] contents = contentMng.deleteByIds(ids);
//        for (Content c : contents) {
//            afterContentStatusChange(c, map.get(c.getId()), ContentStatusChangeThread.OPERATE_DEL);
//        }
//        return renderSuccessPage(model, nextUrl);
//    }
//
//    private WebErrors validateSave(String title, String author, String description, String tagStr, Integer channelId, String captcha) {
//        WebErrors errors = WebErrors.create(getRequest());
//        try {
//            if (!imageCaptchaService.validateResponseForID(getSession().getId(), captcha)) {
//                errors.addErrorCode("error.invalidCaptcha");
//                return errors;
//            }
//        } catch (CaptchaServiceException e) {
//            errors.addErrorCode("error.exceptionCaptcha");
//            return errors;
//        }
//        if (errors.ifBlank(title, "title", 150, true)) {
//            return errors;
//        }
//        if (errors.ifMaxLength(author, "author", 100, true)) {
//            return errors;
//        }
//        if (errors.ifMaxLength(description, "description", 255, true)) {
//            return errors;
//        }
//
//        if (errors.ifMaxLength(tagStr, "tagStr", 255, true)) {
//            return errors;
//        }
//        if (errors.ifNull(channelId, "channelId", true)) {
//            return errors;
//        }
//        if (vldChannel(errors, channelId)) {
//            return errors;
//        }
//        return errors;
//    }
//
//
//    private WebErrors validateEdit(Integer id) {
//        WebErrors errors = WebErrors.create(getRequest());
//        if (vldOpt(errors, new Integer[]{id})) {
//            return errors;
//        }
//        return errors;
//    }
//
//    private WebErrors validateUpdate(Integer id, Integer channelId) {
//        WebErrors errors = WebErrors.create(getRequest());
//        if (vldOpt(errors, new Integer[]{id})) {
//            return errors;
//        }
//        if (vldChannel(errors, channelId)) {
//            return errors;
//        }
//        return errors;
//    }
//
//    private WebErrors validateDelete(Integer[] ids) {
//        WebErrors errors = WebErrors.create(getRequest());
//        if (vldOpt(errors, ids)) {
//            return errors;
//        }
//        return errors;
//    }
//
//    private boolean vldOpt(WebErrors errors, Integer[] ids) {
//        for (Integer id : ids) {
//            if (errors.ifNull(id, "id", true)) {
//                return true;
//            }
//            Content c = contentMng.findById(id);
//            // 数据不存在
//            if (errors.ifNotExist(c, Content.class, id, true)) {
//                return true;
//            }
//            // 非本用户数据
//            if (!c.getAdmin().getId().equals(getMember().getId())) {
//                errors.noPermission(Content.class, id, true);
//                return true;
//            }
//            // 非本站点数据
//            if (!c.getSite().getId().equals(getSite().getId())) {
//                errors.notInSite(Content.class, id);
//                return true;
//            }
//            // 文章级别大于0，不允许修改
//            if (c.getCheckStep() > 0) {
//                errors.addErrorCode("member.contentChecked");
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean vldChannel(WebErrors errors, Integer channelId) {
//        Channel channel = channelMng.findById(channelId);
//        if (errors.ifNotExist(channel, Channel.class, channelId, true)) {
//            return true;
//        }
//        if (!channel.getSite().getId().equals(getSite().getId())) {
//            errors.notInSite(Channel.class, channelId);
//            return true;
//        }
//        if (!channel.getContriGroups().contains(getMember().getGroup())) {
//            errors.noPermission(Channel.class, channelId, true);
//            return true;
//        }
//        return false;
//    }
//
//    private void afterContentStatusChange(Content content, List<Map<String, Object>> list, Short operate) {
//        ContentStatusChangeThread afterThread = new ContentStatusChangeThread(content, operate, contentMng.getListenerList(), list);
//        afterThread.start();
//    }
//
//    @RequestMapping("/member/o_upload_media.html")
//    public String uploadMedia(@RequestParam(value = "mediaFile", required = false) MultipartFile file, String filename, ModelMap model) {
//        String origName = file.getOriginalFilename();
//        String ext = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
//        WebErrors errors = validateUpload(file, getRequest());
//        Site site = getSite();
//        if (errors.hasErrors()) {
//            model.addAttribute("error", errors.getErrors().get(0));
//            return renderPage("member/media_iframe.html", model);
//        }
//        try {
//            String fileUrl;
//            if (site.getConfig().getUploadToDb()) {
//                String dbFilePath = site.getConfig().getDbFileUri();
//                if (!StringUtils.isBlank(filename) && FilenameUtils.getExtension(filename).equals(ext)) {
//                    filename = filename.substring(dbFilePath.length());
//                    fileUrl = dbFileMng.storeByFilename(filename, file
//                            .getInputStream());
//                } else {
//                    fileUrl = dbFileMng.storeByExt(site.getUploadPath(), ext,
//                            file.getInputStream());
//                    // 加上访问地址
//                    fileUrl = getRequest().getContextPath() + dbFilePath + fileUrl;
//                }
//            } else if (site.getUploadFtp() != null) {
//                Ftp ftp = site.getUploadFtp();
//                String ftpUrl = ftp.getUrl();
//                if (!StringUtils.isBlank(filename) && FilenameUtils.getExtension(filename).equals(ext)) {
//                    filename = filename.substring(ftpUrl.length());
//                    fileUrl = ftp.storeByFilename(filename, file.getInputStream());
//                } else {
//                    fileUrl = ftp.storeByExt(site.getUploadPath(), ext, file.getInputStream());
//                    // 加上url前缀
//                    fileUrl = ftpUrl + fileUrl;
//                }
//            } else if (site.getUploadOss() != null) {
//                Oss oss = site.getUploadOss();
//                fileUrl = oss.storeByExt(site.getUploadPath(), ext, file.getInputStream());
//            } else {
//                String ctx = getRequest().getContextPath();
//                if (!StringUtils.isBlank(filename) && FilenameUtils.getExtension(filename).equals(ext)) {
//                    filename = filename.substring(ctx.length());
//                    fileUrl = fileRepository.storeByFilename(filename, file);
//                } else {
//                    fileUrl = fileRepository.storeByExt(site.getUploadPath(), ext, file);
//                    // 加上部署路径
//                    fileUrl = ctx + fileUrl;
//                }
//            }
//            cmsUserMng.updateUploadSize(getMember().getId(), Integer.parseInt(String.valueOf(file.getSize() / 1024)));
//            fileMng.saveFileByPath(fileUrl, fileUrl, false);
//            model.addAttribute("mediaPath", fileUrl);
//            model.addAttribute("mediaExt", ext);
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//        }
//        return renderPage("member/media_iframe.html", model);
//    }
//
//    @RequestMapping("/member/o_upload_attachment.html")
//    public String uploadAttachment(@RequestParam(value = "attachmentFile", required = false) MultipartFile file, String attachmentNum, ModelMap model) {
//        String origName = file.getOriginalFilename();
//        String ext = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
//        WebErrors errors = validateUpload(file, getRequest());
//        if (errors.hasErrors()) {
//            model.addAttribute("error", errors.getErrors().get(0));
//            return renderPage("member/attachment_iframe.html", model);
//        }
//        Site site = getSite();
//        Member user = getMember();
//        try {
//            String fileUrl;
//            if (site.getConfig().getUploadToDb()) {
//                String dbFilePath = site.getConfig().getDbFileUri();
//                fileUrl = dbFileMng.storeByExt(site.getUploadPath(), ext, file.getInputStream());
//                // 加上访问地址
//                fileUrl = getRequest().getContextPath() + dbFilePath + fileUrl;
//            } else if (site.getUploadFtp() != null) {
//                Ftp ftp = site.getUploadFtp();
//                String ftpUrl = ftp.getUrl();
//                fileUrl = ftp.storeByExt(site.getUploadPath(), ext, file.getInputStream());
//                // 加上url前缀
//                fileUrl = ftpUrl + fileUrl;
//            } else if (site.getUploadOss() != null) {
//                Oss oss = site.getUploadOss();
//                fileUrl = oss.storeByExt(site.getUploadPath(), ext, file.getInputStream());
//            } else {
//                String ctx = getRequest().getContextPath();
//                fileUrl = fileRepository.storeByExt(site.getUploadPath(), ext, file);
//                // 加上部署路径
//                fileUrl = ctx + fileUrl;
//            }
//            cmsUserMng.updateUploadSize(user.getId(), Integer.parseInt(String.valueOf(file.getSize() / 1024)));
//            fileMng.saveFileByPath(fileUrl, origName, false);
//            model.addAttribute("attachmentPath", fileUrl);
//            model.addAttribute("attachmentName", origName);
//            model.addAttribute("attachmentNum", attachmentNum);
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//        }
//        return renderPage("member/attachment_iframe.html", model);
//    }
//
//    private WebErrors validateUpload(MultipartFile file, HttpServletRequest request) {
//        String origName = file.getOriginalFilename();
//        Member user = CmsUtils.getMember(request);
//        String ext = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
//        int fileSize = (int) (file.getSize() / 1024);
//        WebErrors errors = WebErrors.create(request);
//        if (errors.ifNull(file, "file", true)) {
//            return errors;
//        }
//        if (origName != null && (origName.contains("/") || origName.contains("\\") || origName.indexOf("\0") != -1)) {
//            errors.addErrorCode("upload.error.filename", origName);
//        }
//        //非允许的后缀
//        if (!user.isAllowSuffix(ext)) {
//            errors.addErrorCode("upload.error.invalidsuffix", ext);
//            return errors;
//        }
//        //超过附件大小限制
//        if (!user.isAllowMaxFile((int) (file.getSize() / 1024))) {
//            errors.addErrorCode("upload.error.toolarge", origName, user.getGroup().getAllowMaxFile());
//            return errors;
//        }
//        //超过每日上传限制
//        if (!user.isAllowPerDay(fileSize)) {
//            long laveSize = user.getGroup().getAllowPerDay() - user.getUploadSize();
//            if (laveSize < 0) {
//                laveSize = 0;
//            }
//            errors.addErrorCode("upload.error.dailylimit", laveSize);
//        }
//        return errors;
//    }
//
//    @Autowired
//    private DbFileMng dbFileMng;
//    @Autowired
//    private CmsUserMng cmsUserMng;
//    @Autowired
//    private CmsFileMng fileMng;
//    @Autowired
//    private CmsConfigMng cmsConfigMng;
//    @Autowired
//    private CmsConfigContentChargeMng cmsConfigContentChargeMng;
//    @Autowired
//    protected ChannelMng channelMng;
//    @Autowired
//    protected ContentMng contentMng;
//    @Autowired
//    protected ContentTypeMng contentTypeMng;
//
//    @Autowired
//    protected CmsModelMng cmsModelMng;
//    @Autowired
//    protected FileRepository fileRepository;
//    @Autowired
//    protected ImageCaptchaService imageCaptchaService;
//}
