import com.CmsApp;
import com.bfly.cms.member.entity.Member;
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
            System.out.println(user.getUsername());
            for (UserRole role : user.getRoles()) {
                System.out.println(role.getName());
            }
        }
        System.out.println("*****************************************");
        List<Member> members = memberService.getList();
        for (Member member : members) {
            System.out.println(member.getUsername());
        }
    }

    @Test
    @Transactional
    public void add() {
        User user = new User();
        user.setUsername("hulibo");
        user.setPassword("hulibo");
        user.setSessionId("ssssssssssssss");
        userService.save(user);
    }

    @Test
    @Transactional
    public void test1() {
        Map<String, Object> property = new HashMap<>();
        property.put("username", "admin");
        property.put("id", 1);
        User user = userService.get(property);
        System.out.println(user.getUsername());
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
        User user = userService.login("admin", "password");
    }
}
