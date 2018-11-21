package com.bfly.cms.api.admin.main;

import com.bfly.cms.annotation.SignValidate;
import com.bfly.cms.api.ApiResponse;
import com.bfly.cms.api.ApiValidate;
import com.bfly.cms.api.Constants;
import com.bfly.cms.api.ResponseCode;
import com.bfly.common.web.ResponseUtils;
import com.bfly.common.web.springmvc.RealPathResolver;
import com.bfly.core.entity.CmsSite;
import com.bfly.core.manager.CmsSiteMng;
import com.bfly.core.web.FireWallProperty;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Controller
public class CmsFireWallConfigApiAct {

    @RequestMapping("/config/firewall_get")
    public void get(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        try {
            String[] hours = StringUtils.split(fireWallProperty.getHour(), ",");
            Set<Integer> hourIds = new HashSet<>();
            for (String h : hours) {
                hourIds.add(Integer.decode(h));
            }
            String[] weeks = StringUtils.split(fireWallProperty.getWeek(), ",");
            Set<Integer> weekIds = new HashSet<>();
            for (String w : weeks) {
                weekIds.add(Integer.decode(w));
            }
            JSONObject json = new JSONObject();
            json.put("password", fireWallProperty.getPassword());
            json.put("open", fireWallProperty.getOpen());
            json.put("domain", fireWallProperty.getDomain());
            json.put("ips", fireWallProperty.getIps());
            json.put("hours", hourIds);
            json.put("weeks", weekIds);
            body = json.toString();
        } catch (Exception e) {
            message = "\"\"";
            code = ResponseCode.API_CODE_CALL_FAIL;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/config/firewall_update")
    public void update(String open, String valPassword,
                       String password, String domain, String weeks, String hours, String ips,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, open);
        if (!errors.hasErrors()) {
            boolean result = validatePassword(errors, valPassword, request, response);
            if (!result) {
                message = Constants.API_MESSAGE_PASSWORD_ERROR;
                code = ResponseCode.API_CODE_PASSWORD_ERROR;
            } else {
                try {
                    if (StringUtils.isNotBlank(password)) {
                        fireWallProperty.setPassword(password);
                    }
                    fireWallProperty.setOpen(open);
                    fireWallProperty.setDomain(domain);

                    CmsSite site = CmsUtils.getSite(request);
                    configSiteDomainAlias(site, domain);

                    fireWallProperty.setWeek(weeks);
                    fireWallProperty.setHour(hours);
                    fireWallProperty.setIps(ips);
                    fireWallProperty.write();
                    message = Constants.API_MESSAGE_SUCCESS;
                    code = ResponseCode.API_CODE_CALL_SUCCESS;
                } catch (FileNotFoundException e) {
                    message = Constants.API_MESSAGE_FILE_NOT_FOUNT;
                    code = ResponseCode.API_CODE_FILE_NOT_FOUNT;
                } catch (Exception e) {
                    message = "\"\"";
                    code = ResponseCode.API_CODE_CALL_FAIL;
                } finally {
                    fireWallProperty.reload();
                }
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private void configSiteDomainAlias(CmsSite site, String domain) {
        if (StringUtils.isNotBlank(site.getDomainAlias())) {
            if (!site.getDomainAlias().contains(domain)) {
                site.setDomainAlias(site.getDomainAlias() + "," + domain);
            }
        } else {
            site.setDomainAlias(domain);
        }
        siteManager.update(site);
    }

    private boolean validatePassword(WebErrors errors, String password, HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String pass = fireWallProperty.getPassword();
        if (pass.equals(password)) {
            result = true;
        }
        return result;
    }

    @Autowired
    private RealPathResolver realPathResolver;
    @Autowired
    private CmsSiteMng siteManager;
    @Autowired
    private FireWallProperty fireWallProperty;
}
