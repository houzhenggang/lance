package com.framework.web.utils;

import com.framework.utils.RegExpUtils;
import com.framework.utils.StringUtils;
import com.framework.web.pojo.AccessLog;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-04-24 21:51
 * To change this template use File | Settings | File Templates.
 */
public class WebUtils {
    /**
     * 获取URI的路径,如路径为http://www.babasport.com/action/post.htm?method=add, 得到的值为"/action/post.htm"
     *
     * @param request
     * @return
     */
    public static String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * 获取完整请求路径(含内容路径及请求参数)
     *
     * @param request
     * @return
     */
    public static String getRequestURIWithParam(HttpServletRequest request) {
        return getRequestURI(request) + (StringUtils.isBlank(request.getQueryString()) ? "" : "?" + request.getQueryString());
    }

    /**
     * 添加cookie
     *
     * @param response
     * @param name     cookie的名称
     * @param value    cookie的值
     * @param maxAge   cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie的值
     *
     * @param request
     * @param name    cookie的名称
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie.getValue();
        }
        return null;
    }

    protected static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
        return cookieMap;
    }


    /*
    * 获取ip
    * */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /*
    * 获取请求头信息
    * */
    public static  Map<String, List<String>> getHeaders(HttpServletRequest request) {
        Map<String, List<String>> headers = Maps.newHashMap();
        Enumeration<String> namesEnumeration = request.getHeaderNames();
        while (namesEnumeration.hasMoreElements()) {
            String name = namesEnumeration.nextElement();
            Enumeration<String> valueEnumeration = request.getHeaders(name);
            List<String> values = Lists.newArrayList();
            while (valueEnumeration.hasMoreElements()) {
                values.add(valueEnumeration.nextElement());
            }
            headers.put(name, values);
        }
        return headers;
    }

    /*
    * 获取所有参数
    * */
    public static Map<String, String[]> getParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        return params;
    }

    public static AccessLog getAccessLog(HttpServletRequest request) {
        AccessLog log = new AccessLog();
        log.setJsessionId(request.getRequestedSessionId());
        log.setIp(getIpAddr(request));
        log.setUrl(getRequestURIWithParam(request));
        log.setReferer((null != request.getHeader("Referer")) ? request.getHeader("Referer") : request.getHeader("referer"));
        log.setParams(getParams(request));
        log.setAccept(request.getHeader("accept"));
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setHeaders(getHeaders(request));

        return log;
    }

    public static int getPage(HttpServletRequest request) {
        return getParams(request,"page",1);

    }

    public static int getRows(HttpServletRequest request) {
        return getParams(request,"rows",20);
    }

    public static String getParams(HttpServletRequest request, String key, String defaultValue) {
        return StringUtils.isNotBlank(request.getParameter(key)) ? request.getParameter(key) : defaultValue;
    }

    public static int getParams(HttpServletRequest request, String key, int defaultValue) {
        String value = request.getParameter(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        if (RegExpUtils.compileJAk(RegExpUtils.UP_ZERO_NUM, value)) {
            return Integer.parseInt(value);
        }
        return defaultValue;
    }

    /*
   *设置值
   * */
    public static HttpServletRequest setHttpServletRequestValues(HttpServletRequest request) {
        HttpServletRequest val = request;
        Map<String, String[]> params = request.getParameterMap();
        for (Map.Entry<String, String[]> obj : params.entrySet()) {
            val.setAttribute(obj.getKey(),(obj.getValue().length==1)?obj.getValue()[0]:obj.getValue());
        }
        return val;
    }
}
