package com.ylz.packcommon.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dws on 2017-06-01.
 */
public class CrossOriginFilter implements Filter{
    private FilterConfig config = null;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public void destroy() {
        this.config = null;
    }

    /**
     *
     * @author wwhhf
     * @since 2016/5/30
     * @comment 跨域的设置
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        // 表明它允许"http://xxx"发起跨域请求
//        httpResponse.setHeader("Access-Control-Allow-Origin",
//                config.getInitParameter("*"));
//        // 表明在xxx秒内，不需要再发送预检验请求，可以缓存该结果
//        httpResponse.setHeader("Access-Control-Allow-Methods",
//                config.getInitParameter("AccessControlAllowMethods"));
//        // 表明它允许xxx的外域请求
//        httpResponse.setHeader("Access-Control-Max-Age",
//                config.getInitParameter("AccessControlMaxAge"));
//        // 表明它允许跨域请求包含xxx头
//        httpResponse.setHeader("Access-Control-Allow-Headers",
//                config.getInitParameter("content-type"));
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "AccessControlMaxAge");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
        chain.doFilter(request, response);
    }
}
