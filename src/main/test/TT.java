import com.CmsApp;
import com.bfly.cms.comment.entity.Comment;
import com.bfly.cms.comment.entity.CommentExt;
import com.bfly.cms.comment.service.ICommentService;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.job.entity.JobResume;
import com.bfly.cms.job.service.IJobResumeService;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.entity.MemberExt;
import com.bfly.cms.member.entity.MemberGroup;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.message.entity.Letter;
import com.bfly.cms.message.entity.LetterTxt;
import com.bfly.cms.message.service.ILetterService;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.entity.UserRole;
import com.bfly.cms.user.service.IUserService;
import com.bfly.common.page.Pager;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.CommentStatus;
import com.bfly.core.enums.LetterBox;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = CmsApp.class)
@Rollback(value = false)
public class TT {

    @Autowired
    private IUserService userService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IJobResumeService resumeService;
    @Autowired
    private ILetterService letterService;

    @Test
    @Transactional
    public void list() {
        List<User> list = userService.getList();
        for (User user : list) {
            System.out.println(user.getUserName());
            for (UserRole role : user.getRoles()) {
                System.out.println(role.getName());
            }
        }
        System.out.println("*****************************************");
        List<Member> members = memberService.getList();
        for (Member member : members) {
            System.out.println(member.getUserName());
        }
    }

    @Test
    @Transactional
    public void add() {
        User user = new User();
        user.setUserName("hulibo");
        user.setPassword("hulibo");
        userService.save(user);
    }

    @Test
    @Transactional
    public void test1() {
        Map<String, Object> property = new HashMap<>();
        property.put("username", "admin");
        property.put("id", 1);
        User user = userService.get(property);
        System.out.println(user.getUserName());
    }

    @Test
    @Transactional
    public void test2() {
        Map<String, Object> property = new HashMap<>();
        property.put("username", "admin");
        property.put("id", 1);
        long count = userService.getCount(property);
        System.out.println(count);
    }

    @Test
    @Transactional
    public void test3() {
        List<User> pager = userService.getList(null);
    }


    @Test
    @Transactional
    public void addMember() {
        Member member = new Member();
        member.setUserName("23s4ssa2");
        member.setPassword("sssssss");

        MemberGroup group = new MemberGroup();
        group.setId(1);
        member.setGroup(group);

        MemberExt ext = new MemberExt();
        ext.setRealName("andy");
        member.setMemberExt(ext);
        ext.setMember(member);
        memberService.save(member);
    }

    @Test
    public void getMember() {
        Member member = memberService.get(1025);
        System.out.println(member.getUserName());
        System.out.println(member.getGroup());
        System.out.println(member.getMemberExt());
    }

    @Test
    @Transactional
    public void updateMember() {
        Member member = memberService.get(1016);
        member.setEmail("aaaaa3333@c.com");
        member.getMemberExt().setFace("aaaaaaaaaaa");
        memberService.edit(member);
    }

    @Test
    public void delMember() {
        memberService.remove(1016);
    }

    @Test
    public void getResume() {
        JobResume resume = resumeService.get(1);
        System.out.println(resume.getName());
    }


    @Test
    @Transactional
    public void saveResume() {
        JobResume resume = new JobResume();
        resume.setName("IT工程师");
        Member member = memberService.get(1021);
        resume.setMember(member);
        resumeService.save(resume);
    }

    @Test
    @Transactional
    public void saveLetter() {
        Letter letter = new Letter();
        letter.setSender("admin");
        letter.setFromAdmin(true);
        letter.setBox(LetterBox.SEND.getId());
        letter.setSendTime(new Date());

        LetterTxt txt = new LetterTxt();
        txt.setTitle("这是标题");
        txt.setContent("这是内容");
        txt.setGroupId(1);

        letter.setLetterTxt(txt);
        letterService.save(letter);
    }


    @Autowired
    private ICommentService commentService;

    @Test
    @Transactional
    public void saveComment(){
        Comment comment=new Comment();
        comment.setPostDate(new Date());
        comment.setStatus(CommentStatus.PASSED.getId());

        comment.setMemberUserName("test");
        comment.setUserName("admin");

        Content content=new Content();
        content.setId(11);

        CommentExt ext=new CommentExt();
        ext.setIp("127.0.0.1");
        ext.setText("十六客服经理迪斯科飞机数量的开发建设了");
        ext.setComment(comment);

        comment.setCommentExt(ext);
        commentService.save(comment);
    }

    @Test
    public void getComment(){
        Comment comment=commentService.get(22);
        System.out.println(comment.getUserName());
        System.out.println(comment.getCommentExt().getText());
    }

    @Test
    public void getPagerComment(){
        Pager pager=new Pager(1,10,1000);
        PagerThreadLocal.set(pager);

        Map<String,Object> params=new HashMap<>(2);
        params.put("status",1);
        params.put("recommend",true);
        pager=commentService.getPage(params);
        System.out.println(pager.getData());
    }
}
