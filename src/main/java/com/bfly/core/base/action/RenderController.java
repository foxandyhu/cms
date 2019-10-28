package com.bfly.core.base.action;

import com.bfly.cms.member.entity.Member;
import com.bfly.common.FileUtil;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.enums.SysError;
import com.bfly.core.exception.WebResponseException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

/**
 * 页面渲染controller父类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/29 11:46
 */
public class RenderController extends AbstractController {

    /**
     * 获得用户
     *
     * @return 用户
     * @author andy_hulibo@163.com
     * @date 2018/11/29 14:53
     */
    public Member getMember() {
        return ContextUtil.getLoginMember();
    }

    /**
     * URL重定向
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 16:59
     */
    public String redirect(String url) {
        return "redirect:" + url;
    }

    /**
     * 得到渲染模板路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/18 16:01
     */
    public String renderTplPath(String fileName, String... dir) {
        String pc = "pc_", mobile = "mobile_";
        String path = String.join("/", dir).concat("/");
        if (!StringUtils.hasLength(fileName)) {
            throw new RuntimeException("未指定模板!");
        }
        if (!fileName.startsWith(pc) && !fileName.startsWith(mobile)) {
            path = path.concat(isMobileRequest() ? "mobile" : "pc").concat("_");
        }
        path = path.concat(fileName);
        if (!FileUtil.checkExist(ResourceConfig.getTemplateAbsolutePath(path))) {
            throw new RuntimeException(path + "模板不存在!");
        }
        return path;
    }

    /**
     * 校验数据
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 12:50
     */
    public void validData(BindingResult result) {
        if (result.hasErrors()) {
            throw new WebResponseException(SysError.PARAM_ERROR, result.getFieldError().getDefaultMessage());
        }
    }
}
