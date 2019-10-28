package com.bfly.cms.member.service.impl;

import com.bfly.cms.logs.service.ISysLogService;
import com.bfly.cms.member.dao.IMemberDao;
import com.bfly.cms.member.entity.*;
import com.bfly.cms.member.service.IMemberGroupService;
import com.bfly.cms.member.service.IMemberLoginConfigService;
import com.bfly.cms.member.service.IMemberRegistConfigService;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.system.service.IEmailService;
import com.bfly.common.DateUtil;
import com.bfly.common.StringUtil;
import com.bfly.common.ValidateUtil;
import com.bfly.common.ipseek.IPLocation;
import com.bfly.common.ipseek.IpSeekerUtil;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.AutoLoginThreadLocal;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.ServletRequestThreadLocal;
import com.bfly.core.enums.LoginType;
import com.bfly.core.enums.LogsType;
import com.bfly.core.enums.MemberStatus;
import com.bfly.core.exception.AccountUnActiveException;
import com.bfly.core.security.LoginToken;
import com.bfly.core.security.Md5PwdEncoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/7 17:39
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class MemberServiceImpl extends BaseServiceImpl<Member, Integer> implements IMemberService {

    @Autowired
    private IMemberGroupService groupService;
    @Autowired
    private IMemberDao memberDao;
    @Autowired
    private IMemberRegistConfigService registConfigService;
    @Autowired
    private IMemberLoginConfigService loginConfigService;
    @Autowired
    @Qualifier("registerEmailServiceImpl")
    private IEmailService registerEmailService;

    @Autowired
    @Qualifier("forgetPwdEmailServiceImpl")
    private IEmailService forgetPwdEmailService;

    @Autowired
    private ISysLogService logService;

    @Override
    @Cacheable(value = "beanCache", key = "'member_username_'+#userName")
    public Member getMember(String userName) {
        return memberDao.getMember(userName);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Member login(String userName, String password, boolean isRemember, LoginType type) {
        Member member;
        try {
            LoginConfig loginConfig = loginConfigService.getLoginConfig();
            if (loginConfig != null) {
                Assert.isTrue(loginConfig.isOpenLogin(), "登录功能已关闭!");
            }
            Assert.hasText(userName, type == LoginType.NORMAL ? "用户名不能为空!" : "手机号码不能为空!");
            Assert.hasText(password, type == LoginType.NORMAL ? "密码不能为空!" : "验证码不能为空!");

            Subject subject = SecurityUtils.getSubject();
            if (type == LoginType.DYNAMIC) {
                // 手机动态密码登录 不存在直接注册新用户
                Assert.isTrue(ValidateUtil.isCellPhone(userName), "手机号码格式错误!");
                // 此处校验验证码和手机号码对应关系
                member = dynamicLoginFromMobile(userName);
                userName = member.getUserName();
                password = member.getPassword();
                AutoLoginThreadLocal.set(true);
            }
            LoginToken token = new LoginToken(userName, password, isRemember, false);
            subject.login(token);
            member = (Member) subject.getPrincipal();
        } catch (UnknownAccountException e) {
            throw new RuntimeException("用户名或密码错误!");
        } catch (IncorrectCredentialsException e) {
            throw new RuntimeException("用户名或密码错误!");
        } catch (AccountUnActiveException e) {
            throw new AccountUnActiveException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return member;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "beanCache", key = "'member_username_'+#userName")
    public void logout(String userName) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    /**
     * 手机动态密码登录用户
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/6 10:30
     */
    private Member dynamicLoginFromMobile(String phone) {
        Member member = getMember(phone);
        if (member == null) {
            // 未注册则自动注册
            RegistConfig registConfig = registConfigService.getRegistConfig();
            if (registConfig != null) {
                Assert.isTrue(registConfig.isOpenRegiste(), "注册功能已关闭!");
            }
            member = new Member();
            member.setUserName(phone);
            member = register(member, true);
        }
        return member;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Member register(Member member, boolean isFromDynamicMobile) {
        RegistConfig registConfig = registConfigService.getRegistConfig();
        Assert.notNull(registConfig, "尚未启用会员注册配置,请联系管理员!");
        Assert.isTrue(registConfig.isOpenRegiste(), "注册功能已关闭!");

        if (isFromDynamicMobile) {
            // 如果手机动态密码注册 则自动默认生成随机密码并存储 且状态无需审核
            member.setStatus(MemberStatus.AVAILABLE.getId());
            member.setActivated(true);
            // 随机生成的密码长度 默认为6
            int minPwdLength = registConfig.getMinLengthPassword() <= 0 ? 6 : registConfig.getMinLengthPassword();
            member.setPassword(StringUtil.getRandomString(minPwdLength));
        } else {
            // 其他渠道注册 需要校验用户名和密码 且状态需要根据配置定
            Assert.isTrue(ValidateUtil.isEmail(member.getUserName()), "邮箱地址格式错误!");
            Assert.isTrue(member.getUserName().length() >= registConfig.getMinLengthUserName(), String.format("用户名最小长度为%d位", registConfig.getMinLengthUserName()));
            Assert.isTrue(member.getPassword().length() >= registConfig.getMinLengthPassword(), String.format("密码长度最小为%d位", registConfig.getMinLengthPassword()));

            MemberStatus status = MemberStatus.UNCHECK;
            if (!registConfig.isNeedCheckRegisted()) {
                status = MemberStatus.AVAILABLE;
            }
            member.setStatus(status.getId());

            if (StringUtils.hasLength(registConfig.getDenyUserName())) {
                String[] denyUserNames = registConfig.getDenyUserName().split(",");
                Assert.isTrue(!ArrayUtils.contains(denyUserNames, member.getUserName()), "该邮箱地址不可用!");
            }
            Member dbM = getMember(member.getUserName());
            if (dbM != null) {
                if (!dbM.isActivated()) {
                    // 抛出异常跳转到激活提示页面
                    throw new AccountUnActiveException("此账号用户尚未激活!");
                }
            }
            Assert.isNull(dbM, "该已存在相同的邮箱地址!");
            Assert.hasLength(member.getPassword(), "密码不能为空!");
            // 默认未激活
            member.setActivated(false);
        }
        MemberGroup group = groupService.getDefaultMemberGroup();
        member.setGroup(group);

        save(member);
        if (!isFromDynamicMobile) {
            // 发送邮件
            Assert.notNull(registConfig.getEmailProvider(), "为设置注册邮箱服务,请联系管理员!");
            try {
                registerEmailService.send(getRegisterMailData(member), member.getUserName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return member;
    }

    /**
     * 注册邮件发送邮件的内容参数
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/24 12:11
     */
    private Map<String, Object> getRegisterMailData(Member member) {
        Map<String, Object> params = new HashMap<>(7);
        params.put("userName", member.getUserName());
        params.put("registerIp", member.getRegisterIp());
        params.put("registerTime", DateUtil.formatterDateTimeStr(member.getRegisterTime()));

        IPLocation location = IpSeekerUtil.getIPLocation(member.getRegisterIp());
        params.put("location", location != null ? location.toString() : "");
        params.put("sendDate", DateUtil.formatterDateTimeStr(new Date()));

        String siteName = "";
        HttpServletRequest request = ServletRequestThreadLocal.get();
        if (request != null) {
            siteName = ContextUtil.getSiteConfig(request.getServletContext()).getName();
        }
        params.put("siteName", siteName);

        // 邮箱验证码由用户名,盐,密码组成
        String certifyId = DigestUtils.md5Hex(member.getUserName().concat(member.getSalt()).concat(member.getPassword()));
        params.put("certifyId", certifyId);
        return params;
    }

    @Override
    public void resendActiveMail(String userName) {
        Member member = getMember(userName);
        Assert.notNull(member, "不存在的用户信息!");
        Assert.isTrue(!member.isActivated(), "账户已是激活状态!");
        try {
            registerEmailService.send(getRegisterMailData(member), member.getUserName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "beanCache", key = "'member_username_'+#userName")
    public boolean editMemberPassword(String userName, String password) {
        Member member=getMember(userName);
        Assert.notNull(member, "会员信息不存在!");
        return editMemberPassword(member.getId(),password);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editMemberPassword(int memberId, String password) {
        Member member = get(memberId);
        Assert.notNull(member, "会员信息不存在!");
        Assert.hasLength(password, "密码不能为空!");

        RegistConfig config = registConfigService.getRegistConfig();
        int minLength = 6;
        if (config != null) {
            // 得到配置的最小密码长度
            minLength = config.getMinLengthPassword();
        }
        Assert.isTrue(password.length() >= minLength, String.format("密码长度最小为%d位", minLength));

        String salt = StringUtil.getRandomString(6);
        String encodePassword = new Md5PwdEncoder().encodePassword(password, salt);
        memberDao.editMemberPassword(memberId, encodePassword, salt);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editMemberStatus(int memberId, MemberStatus status) {
        Member member = get(memberId);
        Assert.notNull(member, "会员信息不存在!");
        Assert.notNull(status, "状态信息不存在!");
        memberDao.editMemberStatus(memberId, status.getId());
        return true;
    }

    /**
     * 检查信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/20 8:41
     */
    private void checkMember(Member member) {
        Assert.notNull(member, "会员信息为空!");

        MemberStatus status = MemberStatus.getStatus(member.getStatus());
        Assert.notNull(status, "未指定账户状态!");

        MemberGroup group = member.getGroup();
        Assert.notNull(group, "未指定会员组!");

        group = groupService.get(member.getGroup().getId());
        Assert.notNull(group, "未指定会员组!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Member member) {
        checkMember(member);

        String salt = StringUtil.getRandomString(6);
        member.setPassword(new Md5PwdEncoder().encodePassword(member.getPassword(), salt));
        member.setSalt(salt);
        member.setRegisterIp(IpThreadLocal.get());
        member.setRegisterTime(new Date());
        if (member.getMemberExt() != null && StringUtils.hasLength(member.getMemberExt().getFace())) {
            String img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(member.getMemberExt().getFace(), ResourceConfig.getFaceDir());
            member.getMemberExt().setFace(img);
        }
        if (member.getMemberExt() != null) {
            member.getMemberExt().setMember(member);
        }
        return super.save(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "beanCache", key = "'member_username_'+#member.userName")
    public boolean edit(Member member) {
        checkMember(member);
        Member orMember = get(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = -6960710939610470029L;

            {
                put("userName", member.getUserName());
            }
        });
        Assert.notNull(orMember, "不存在该会员!");
        orMember.setStatus(member.getStatus());
        orMember.setGroup(member.getGroup());
        if (member.getMemberExt() != null) {
            member.getMemberExt().setMember(orMember);
            if (orMember.getMemberExt() != null) {
                member.getMemberExt().setId(orMember.getMemberExt().getId());
            }
        }
        if (StringUtils.hasLength(member.getMemberExt().getFace())) {
            String img = ResourceConfig.getUploadTempFileToDestDirForRelativePath(member.getMemberExt().getFace(), ResourceConfig.getFaceDir());
            if (img != null) {
                member.getMemberExt().setFace(img);
            } else {
                member.getMemberExt().setFace(orMember.getMemberExt().getFace());
            }
        }
        orMember.setMemberExt(member.getMemberExt());
        return super.edit(orMember);
    }

    @Override
    public Map<String, BigInteger> getTodayAndTotalMember() {
        return memberDao.getTodayAndTotalMember();
    }

    @Override
    public List<Map<String, Object>> getLatestMember(int limit) {
        List<Map<String, Object>> list = memberDao.getLatestMember(limit);
        if (list != null) {
            String registerIp = "registerIp", status = "status", face = "face";
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                Map<String, Object> m = new HashMap<>(map.size());
                m.putAll(map);
                if (m.containsKey(registerIp)) {
                    IPLocation location = IpSeekerUtil.getIPLocation(String.valueOf(m.get(registerIp)));
                    m.put("area", location == null ? "" : location.getArea());
                }
                if (m.containsKey(status)) {
                    MemberStatus memberStatus = MemberStatus.getStatus((Integer) m.get(status));
                    m.put("statusName", memberStatus == null ? "" : memberStatus.getName());
                }
                if (m.containsKey(face)) {
                    if (StringUtils.hasLength((String) m.get(face))) {
                        m.put("face", ResourceConfig.getServer() + m.get(face));
                    }
                }
                list.set(i, m);
            }
        }
        return list;
    }

    @Override
    public Member getMemberBySession(String sessionId) {
        if (!StringUtils.hasLength(sessionId)) {
            return null;
        }
        return memberDao.getMemberBySessionId(sessionId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearMemberLoginError() {
        LoginConfig config = loginConfigService.getLoginConfig();
        int minutes = 0;
        if (config != null) {
            minutes = config.getLoginErrorTimeOut();
        }
        memberDao.clearMemberLoginError(minutes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editMemberExtInfo(MemberExt ext) {
        Member member = get(ext.getId());
        Assert.notNull(member, "不存在的会员信息!");
        if (member.getMemberExt() != null) {
            ext.setFace(member.getMemberExt().getFace());
        }
        ext.setMember(member);
        member.setMemberExt(ext);
        memberDao.save(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "beanCache", key = "'member_username_'+#userName")
    public void uploadMemberFace(String userName, String base64FaceData) {
        if (StringUtils.hasLength(base64FaceData)) {
            String[] d = base64FaceData.split("base64,");
            String dataPrefix, data;
            if (d != null && d.length == 2) {
                dataPrefix = d[0];
                data = d[1];
                String suffix;
                if ("data:image/jpeg;".equalsIgnoreCase(dataPrefix)) {
                    suffix = ".jpg";
                } else if ("data:image/x-icon;".equalsIgnoreCase(dataPrefix)) {
                    suffix = ".ico";
                } else if ("data:image/gif;".equalsIgnoreCase(dataPrefix)) {
                    suffix = ".gif";
                } else if ("data:image/png;".equalsIgnoreCase(dataPrefix)) {
                    suffix = ".png";
                } else {
                    throw new RuntimeException("上传图片格式只能为JPG,PNG,ICO!");
                }
                String filePath = ResourceConfig.getFaceDir() + File.separator + UUID.randomUUID() + suffix;
                byte[] bs = Base64Utils.decodeFromString(data);
                try {
                    FileUtils.writeByteArrayToFile(new File(filePath), bs);
                    filePath = ResourceConfig.getRelativePathForRoot(filePath);
                    Member member = getMember(userName);
                    member.getMemberExt().setFace(filePath);
                    super.edit(member);
                } catch (IOException e) {
                    throw new RuntimeException("头像修改失败!", e);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "beanCache", key = "'member_username_'+#userName")
    public void activeMemberForMail(String userName, String certifyId) {
        Member member = getMember(userName);
        Assert.notNull(member, "该用户名尚未注册!");
        Assert.isTrue(!member.isActivated(), "账户已是激活状态!");

        String code = member.getUserName().concat(member.getSalt()).concat(member.getPassword());
        code = DigestUtils.md5Hex(code);
        Assert.isTrue(code.equals(certifyId), "激活码不正确!");
        memberDao.editMemberActivated(member.getId(), true);
        logService.save(LogsType.OP_LOG, userName, IpThreadLocal.get(), null, "激活账户", certifyId, true, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void forgetPwdForMail(String userName) {
        Assert.isTrue(ValidateUtil.isEmail(userName), "邮件格式错误!");
        Member member = getMember(userName);
        Assert.notNull(member, "该邮箱地址尚未注册!");
        try {
            forgetPwdEmailService.send(getForgetPwdMailData(member), userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logService.save(LogsType.OP_LOG, userName, IpThreadLocal.get(), null, "找回密码", null, true, true);
    }

    /**
     * 找回密码邮件发送邮件的内容参数
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/21 16:10
     */
    private Map<String, Object> getForgetPwdMailData(Member member) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("userName", member.getUserName());
        params.put("sendDate", DateUtil.formatterDateTimeStr(new Date()));
        String siteName = "";
        HttpServletRequest request = ServletRequestThreadLocal.get();
        if (request != null) {
            siteName = ContextUtil.getSiteConfig(request.getServletContext()).getName();
        }
        params.put("siteName", siteName);

        // 邮箱验证码由用户名,盐,密码组成
        String certifyId = DigestUtils.md5Hex(member.getUserName().concat(member.getSalt()).concat(member.getPassword()));
        params.put("certifyId", certifyId);
        return params;
    }
}
