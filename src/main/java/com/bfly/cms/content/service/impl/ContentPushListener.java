package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.entity.Content;
import com.bfly.core.config.SocialInfoConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;


@Component
public class ContentPushListener extends SimpleContentListener {
    private static final Logger log = LoggerFactory.getLogger(ContentPushListener.class);

    @Override
    public void afterChange(Content content, Map<String, Object> map) {
        super.afterChange(content, map);
    }

    @Override
    public void afterDelete(Content content) {
        super.afterDelete(content);
    }

    @Override
    public void afterSave(Content content) {
        String domain = content.getSite().getDomain();
        if (content.getSite().getConfig().getAttr().containsKey("bdToken") && content.getSite().getConfig().getAttr().containsKey("isBdSubmit")) {
            String bdToken = content.getSite().getConfig().getAttr().get("bdToken");
            String isBdSubmit = content.getSite().getConfig().getAttr().get("isBdSubmit");
            if ("true".equals(isBdSubmit)) {
                pushPost(content.getUrl(), domain, bdToken);
            }
        }

    }


    @Override
    public Map<String, Object> preChange(Content content) {
        return super.preChange(content);
    }

    @Override
    public void preDelete(Content content) {
        super.preDelete(content);
    }

    @Override
    public void preSave(Content content) {
        super.preSave(content);
    }

    /**
     * @return
     * @author andy_hulibo@163.com
     * @date 2018/11/26 13:56
     * 百度链接实时推送
     */
    public String pushPost(String parameters, String domain, String bdToken) {
        String linkSubmitUrl = socialInfoConfig.getBaidu().getLinksubmit().getAddress();
        String host = socialInfoConfig.getBaidu().getLinksubmit().getHost();
        linkSubmitUrl += "?site=" + domain + "&token=" + bdToken;
        String result = "";
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = httpClientBuilder.build();
        client = (CloseableHttpClient) wrapClient();
        HttpPost post = new HttpPost(linkSubmitUrl);
        //发送请求参数
        try {
            StringEntity s = new StringEntity(parameters, "utf-8");
            s.setContentType("application/json");
            post.setEntity(s);
            post.setHeader("Host", host);
            post.setHeader("User-Agent", "curl/7.12.1");
            post.setHeader("Content-Type", "text/plain");
            HttpResponse res = client.execute(post);
            HttpEntity entity = res.getEntity();
            String str = EntityUtils.toString(entity, "utf-8");
            result = str;
            log.info("baidu link submit result -> " + result);
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }

        return result;
    }

    private static HttpClient wrapClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLSv1");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return httpclient;

        } catch (Exception ex) {
            return null;
        }
    }

    @Autowired
    private SocialInfoConfig socialInfoConfig;

}
