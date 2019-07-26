package com.bfly.manage.friendlink;

import com.bfly.cms.friendlink.entity.FriendLink;
import com.bfly.cms.friendlink.entity.FriendLinkType;
import com.bfly.cms.friendlink.service.IFriendLinkService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 友情链接Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:53
 */
@RestController
@RequestMapping(value = "/manage/friendlink")
public class FriendLinkController extends BaseManageController {

    @Autowired
    private IFriendLinkService friendLinkService;

    /**
     * 友情链接列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:53
     */
    @GetMapping("/list")
    @ActionModel(value = "友情链接列表",need = false)
    public void listFriendLink(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());

        Map<String, Object> params = null;
        String type = getRequest().getParameter("type");
        if (type != null) {
            params = new HashMap<>(1);
            FriendLinkType linkType = new FriendLinkType();
            linkType.setId(DataConvertUtils.convertToInteger(type));
            params.put("type", linkType);
        }
        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("seq", Sort.Direction.ASC);
        Pager pager = friendLinkService.getPage(params, null, sortMap);
        if(pager.getData()!=null){
            List<FriendLink> links=(List<FriendLink>) pager.getData();
            Iterator<FriendLink> it=links.iterator();
            while(it.hasNext()){
                FriendLink link=it.next();
                if(StringUtils.hasLength(link.getLogo())){
                    link.setLogo(ResourceConfig.getServer()+link.getLogo());
                }
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 新增友情链接
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    @ActionModel(value = "新增友情链接")
    public void addFriendLink(@RequestBody @Valid FriendLink friendLink, BindingResult result, HttpServletResponse response) {
        validData(result);
        friendLinkService.save(friendLink);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改友情链接
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:01
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改友情链接")
    public void editFriendLink(@RequestBody @Valid FriendLink friendLink, BindingResult result, HttpServletResponse response) {
        validData(result);
        friendLinkService.edit(friendLink);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获取友情链接基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{linkId}")
    @ActionModel(value = "友情链接详情", need = false)
    public void viewFriendLink(@PathVariable("linkId") int linkId, HttpServletResponse response) {
        FriendLink friendLink = friendLinkService.get(linkId);
        if(friendLink!=null && StringUtils.hasLength(friendLink.getLogo())){
            friendLink.setLogo(ResourceConfig.getServer()+friendLink.getLogo());
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(friendLink));
    }

    /**
     * 删除友情链接
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除友情链接")
    public void removeFriendLink(HttpServletResponse response, @RequestBody Integer... ids) {
        friendLinkService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
