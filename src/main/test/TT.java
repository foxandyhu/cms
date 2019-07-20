import com.CmsApp;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.entity.MemberExt;
import com.bfly.cms.member.entity.MemberGroup;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.entity.UserRole;
import com.bfly.cms.user.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

        MemberGroup group=new MemberGroup();
        group.setId(1);
        member.setGroup(group);

        MemberExt ext = new MemberExt();
        ext.setRealName("andy");
        member.setMemberExt(ext);
        ext.setMember(member);
        memberService.save(member);
    }

    @Test
    public void getMember(){
        Member member=memberService.get(1025);
        System.out.println(member.getUserName());
        System.out.println(member.getGroup());
        System.out.println(member.getMemberExt());
    }

    @Test
    @Transactional
    public void updateMember(){
        Member member=memberService.get(1016);
        member.setEmail("aaaaa3333@c.com");
        member.getMemberExt().setFace("aaaaaaaaaaa");
        memberService.edit(member);
    }

    @Test
    public void delMember(){
        memberService.remove(1016);
    }
}
