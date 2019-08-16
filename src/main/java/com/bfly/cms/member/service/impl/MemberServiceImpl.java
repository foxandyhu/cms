package com.bfly.cms.member.service.impl;

import com.bfly.cms.member.dao.IMemberDao;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.entity.MemberGroup;
import com.bfly.cms.member.service.IMemberGroupService;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.common.StringUtil;
import com.bfly.common.ipseek.IPLocation;
import com.bfly.common.ipseek.IpSeekerUtil;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.enums.MemberStatus;
import com.bfly.core.security.Md5PwdEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Member getMember(String userName) {
        return memberDao.getMember(userName);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Member login(String username, String password) {
        Member member = null;
        boolean loginSuccess = false;
        try {
            Assert.hasText(username, "用户名不能为空!");
            Assert.hasText(password, "密码不能为空!");
            member = get(new HashMap<String, Object>(1) {{
                put("username", username);
            }});
            Assert.notNull(member, "用户名或密码不正确!");
            loginSuccess = true;
            boolean flag = new Md5PwdEncoder().isPasswordValid(member.getPassword(), password);
            Assert.isTrue(flag, "用户名或密码错误!");
            Assert.isTrue(MemberStatus.UNCHECK.getId() != member.getStatus(), "此账号正在审核中!");
            Assert.isTrue(MemberStatus.DISABLE.getId() != member.getStatus(), "此账号已被禁用!");
        } finally {
            updateLoginInfo(member, loginSuccess);
        }
        return member;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editMemberPassword(int memberId, String password) {
        Member member = get(memberId);
        Assert.notNull(member, "会员信息不存在!");
        Assert.hasLength(password, "密码不能为空!");

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
     * 更新登录信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:33
     */
    protected void updateLoginInfo(Member member, boolean loginSuccess) {
        if (loginSuccess) {
            member.setLoginCount(member.getLoginCount() + 1);
            member.setLastLoginTime(new Date());
            member.setLastLoginIp(IpThreadLocal.get());
            member.setErrorTime(null);
            member.setErrorIp(null);
            member.setErrorCount(0);
        } else {
            member.setLastLoginTime(null);
            member.setLastLoginIp(null);
            member.setErrorTime(new Date());
            member.setErrorIp(IpThreadLocal.get());
            member.setErrorCount(member.getErrorCount() + 1);
        }
        super.edit(member);
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
        if (StringUtils.hasLength(member.getMemberExt().getFace())) {
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
    public boolean edit(Member member) {
        checkMember(member);
        Member orMember = get(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = -6960710939610470029L;

            {
                put("userName", member.getUserName());
            }
        });
        Assert.notNull(orMember, "不能存在该会员!");
        orMember.setEmail(member.getEmail());
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
}
