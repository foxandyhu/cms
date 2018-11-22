import com.CmsApp;
import com.bfly.cms.entity.assist.CmsSiteAccessCountHour;
import com.bfly.cms.entity.assist.CmsSiteAccessPages;
import com.bfly.cms.manager.assist.CmsSiteAccessCountHourMng;
import com.bfly.cms.manager.assist.CmsSiteAccessPagesMng;
import com.bfly.common.util.DateUtils;
import com.bfly.core.entity.CmsSite;
import com.bfly.core.manager.CmsSiteMng;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = CmsApp.class)
public class TT {

    @Autowired
    private CmsSiteAccessPagesMng mng;
    @Autowired
    private CmsSiteMng siteMng;

    @Test
    public void test() {
        Date date = new Date();
        CmsSite site = siteMng.findById(1);
        CmsSiteAccessPages bean = new CmsSiteAccessPages();
        bean.setSite(site);
        bean.setAccessDate(date);
        bean.setAccessTime(date);
        bean.setSessionId("aaaaaaaaaaaaaaaaaaaa");
        bean.setVisitSecond(11);
        bean.setPageIndex(1);
        bean.setAccessPage("ssssssssssssss");
        mng.save(bean);
    }
}
