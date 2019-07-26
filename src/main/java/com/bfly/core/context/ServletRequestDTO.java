package com.bfly.core.context;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.security.Principal;
import java.util.*;

/**
 * ServletRequest 转换为自定义对象
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/26 19:02
 */
public class ServletRequestDTO implements Serializable {


    private static final long serialVersionUID = 8454938188863182071L;

    /**
     * HttpServletRequest转换为自定义对象
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/26 19:39
     */
    public static ServletRequestDTO to(HttpServletRequest request) throws Exception {
        ServletRequestDTO dto = new ServletRequestDTO();
        dto.setRequestedSessionIdFromCookie(request.isRequestedSessionIdFromCookie());
        dto.setRequestedSessionIdFromURL(request.isRequestedSessionIdFromURL());
        dto.setRequestedSessionIdValid(request.isRequestedSessionIdValid());
        dto.setSession(request.getSession());
        dto.setServletPath(request.getServletPath());
        dto.setRequestURI(request.getRequestURI());
        dto.setRequestURL(request.getRequestURL());
        dto.setUserPrincipal(request.getUserPrincipal());
        dto.setRequestedSessionId(request.getRequestedSessionId());
        dto.setQueryString(request.getQueryString());
        dto.setRemoteUser(request.getRemoteUser());
        dto.setContextPat(request.getContextPath());
        dto.setPathTranslated(request.getPathTranslated());
        dto.setMethod(request.getMethod());
        dto.setPathInfo(request.getPathInfo());
        dto.setCookies(request.getCookies());
        dto.setHeaderNames(request.getHeaderNames());
        dto.setDispatcherType(request.getDispatcherType());
        dto.setAuthType(request.getAuthType());
        dto.setAsyncStarted(request.isAsyncStarted());
        dto.setAsyncSupported(request.isAsyncSupported());
        dto.setLocalAddr(request.getLocalAddr());
        dto.setLocalName(request.getLocalName());
        dto.setLocalPort(request.getLocalPort());
        dto.setRemotePort(request.getRemotePort());
        dto.setSecure(request.isSecure());
        dto.setLocale(request.getLocale());
        dto.setLocales(request.getLocales());
        dto.setRemoteAddr(request.getRemoteAddr());
        dto.setRemoteHost(request.getRemoteHost());
        dto.setServerName(request.getServerName());
        dto.setServerPort(request.getServerPort());
        dto.setScheme(request.getScheme());
        dto.setProtocol(request.getProtocol());
        dto.setParameterMap(request.getParameterMap());
        dto.setParameterNames(request.getParameterNames());
        dto.setContentType(request.getContentType());
        dto.setContentLengthLong(request.getContentLengthLong());
        dto.setContentLength(request.getContentLength());
        dto.setCharacterEncoding(request.getCharacterEncoding());
        dto.setAttributeNames(request.getAttributeNames());

        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String header = enumeration.nextElement().toLowerCase();
            dto.headers.put(header, request.getHeader(header));
        }
        return dto;
    }

    private Map<String, String> headers = new HashMap<>(1);
    private String protocol;
    private String scheme;
    private String serverName;
    private int serverPort;
    private String remoteAddr;
    private String remoteHost;
    private Locale locale;
    private Enumeration<Locale> locales;
    private boolean secure;
    private int remotePort;
    private String localName;
    private String localAddr;
    private int localPort;
    private boolean asyncStarted;
    private boolean asyncSupported;
    private AsyncContext asyncContext;
    private DispatcherType dispatcherType;
    private String authType;
    private Cookie[] cookies;
    private Enumeration<String> headerNames;
    private String method;
    private String pathInfo;
    private String pathTranslated;
    private String contextPat;
    private String queryString;
    private String remoteUser;
    private java.security.Principal userPrincipal;
    private String requestedSessionId;
    private String requestURI;
    private StringBuffer requestURL;
    private String servletPath;
    private HttpSession session;
    private String changeSessionId;
    private boolean requestedSessionIdValid;
    private boolean requestedSessionIdFromCookie;
    private boolean requestedSessionIdFromURL;
    private Collection<Part> parts;

    private Enumeration<String> attributeNames;
    private String characterEncoding;
    private int contentLength;
    private long contentLengthLong;
    private String contentType;
    private Enumeration<String> parameterNames;
    private Map<String, String[]> parameterMap;

    public String getParameter(String name) {
        String[] value = getParameterMap().get(name);
        if (value == null) {
            return null;
        }
        return value[0];

    }

    public String getHeader(String name) {
        name = name.toLowerCase();
        return headers.get(name);
    }


    public Enumeration<String> getAttributeNames() {
        return attributeNames;
    }

    public void setAttributeNames(Enumeration<String> attributeNames) {
        this.attributeNames = attributeNames;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public long getContentLengthLong() {
        return contentLengthLong;
    }

    public void setContentLengthLong(long contentLengthLong) {
        this.contentLengthLong = contentLengthLong;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Enumeration<String> getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(Enumeration<String> parameterNames) {
        this.parameterNames = parameterNames;
    }

    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Enumeration<Locale> getLocales() {
        return locales;
    }

    public void setLocales(Enumeration<Locale> locales) {
        this.locales = locales;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLocalAddr() {
        return localAddr;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public boolean isAsyncStarted() {
        return asyncStarted;
    }

    public void setAsyncStarted(boolean asyncStarted) {
        this.asyncStarted = asyncStarted;
    }

    public boolean isAsyncSupported() {
        return asyncSupported;
    }

    public void setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
    }

    public AsyncContext getAsyncContext() {
        return asyncContext;
    }

    public void setAsyncContext(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    public DispatcherType getDispatcherType() {
        return dispatcherType;
    }

    public void setDispatcherType(DispatcherType dispatcherType) {
        this.dispatcherType = dispatcherType;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public Enumeration<String> getHeaderNames() {
        return headerNames;
    }

    public void setHeaderNames(Enumeration<String> headerNames) {
        this.headerNames = headerNames;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public String getPathTranslated() {
        return pathTranslated;
    }

    public void setPathTranslated(String pathTranslated) {
        this.pathTranslated = pathTranslated;
    }

    public String getContextPat() {
        return contextPat;
    }

    public void setContextPat(String contextPat) {
        this.contextPat = contextPat;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public Principal getUserPrincipal() {
        return userPrincipal;
    }

    public void setUserPrincipal(Principal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    public String getRequestedSessionId() {
        return requestedSessionId;
    }

    public void setRequestedSessionId(String requestedSessionId) {
        this.requestedSessionId = requestedSessionId;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public StringBuffer getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(StringBuffer requestURL) {
        this.requestURL = requestURL;
    }

    public String getServletPath() {
        return servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getChangeSessionId() {
        return changeSessionId;
    }

    public void setChangeSessionId(String changeSessionId) {
        this.changeSessionId = changeSessionId;
    }

    public boolean isRequestedSessionIdValid() {
        return requestedSessionIdValid;
    }

    public void setRequestedSessionIdValid(boolean requestedSessionIdValid) {
        this.requestedSessionIdValid = requestedSessionIdValid;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return requestedSessionIdFromCookie;
    }

    public void setRequestedSessionIdFromCookie(boolean requestedSessionIdFromCookie) {
        this.requestedSessionIdFromCookie = requestedSessionIdFromCookie;
    }

    public boolean isRequestedSessionIdFromURL() {
        return requestedSessionIdFromURL;
    }

    public void setRequestedSessionIdFromURL(boolean requestedSessionIdFromURL) {
        this.requestedSessionIdFromURL = requestedSessionIdFromURL;
    }

    public Collection<Part> getParts() {
        return parts;
    }

    public void setParts(Collection<Part> parts) {
        this.parts = parts;
    }
}
