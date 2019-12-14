package com.ylz.packcommon.filter;

import com.ylz.packcommon.common.Constant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 事务过滤
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ylz</p>
 *
 * @author robin
 * @version 1.0
 */

public class HiFilter extends HttpServlet implements Filter
{
    private static String NEED_BREAK = "0";// 是否可以通过POSTMAN访问签约方法（0-可以，1-不可以）
    private FilterConfig filterConfig;
    private static final String FILTER_APPLIED = "__session_context_filter_applied";

    /**
     * Handle the passed-in FilterConfig
     * @param filterConfig FilterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.filterConfig = filterConfig;
    }

    /**
     * Process the request/response pair
     * @param request ServletRequest
     * @param response ServletResponse
     * @param filterChain FilterChain
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain){
        boolean boolTrue = false;
        try
        {
//            String pageFrom = ((HttpServletRequest) request).getHeader("Referer");
//            String act = request.getParameter("act");
//            NEED_BREAK = StringUtils.isBlank(request.getParameter("NEED_BREAK")) ? NEED_BREAK : request.getParameter("NEED_BREAK");
//            System.out.println(String.format("REQUEST FROM PAGE[%sd] NEED_BREAK=[%s] act=[%s]", pageFrom, NEED_BREAK, act));
//            if (StringUtils.equalsIgnoreCase(NEED_BREAK, "1") && StringUtils.contains("signAdd,signModify,", act)) {
//                if (StringUtils.isBlank(pageFrom)) {
//                    System.out.println("invalid url........!!!!!!!!!!!!!!!!!!!!!!");
//                    request.getRequestDispatcher("/open/invalid.jsp").forward(request, response);
//                    return;
//                }
//            }

           filterChain.doFilter(request, response);
//                if (request != null && request.getAttribute(FILTER_APPLIED) != null) {
//                    filterChain.doFilter(request, response);
//                }else {
//                    request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
//                    HttpServletRequest rq;
//                    rq = (HttpServletRequest) request;
//                    HttpSession session = rq.getSession();
//                    StringBuffer url = rq.getRequestURL();
//                    String tempContextUrl = url.toString();
//                    String pro = rq.getContextPath().replaceAll("/", "");
//                    String requestUrl = rq.getRequestURI();
//                    String path = requestUrl.replaceAll("/", "");
//
//                    if((StringUtils.isBlank(pro) && path.equals("/")) || pro.equals(path)){
//                        boolTrue = true;
//                    }
//                    if(boolTrue){
//                        filterChain.doFilter(request, response);
//                    }else{
//                        if (!tempContextUrl.contains("open")){
//                            if(tempContextUrl.contains("kaptcha") || tempContextUrl.contains("login")
//                                    ||  tempContextUrl.contains("hzlogin")
//                                    ||  tempContextUrl.contains("hzSign")
//                                    ||  tempContextUrl.contains("yslogin")
//                                    ||  tempContextUrl.contains("ysSign")
//                                    || tempContextUrl.contains("appCommon")
//                                    || tempContextUrl.contains("motoeAction")
//                                    || tempContextUrl.contains("safemessage") || tempContextUrl.contains("image")
//                                    || tempContextUrl.contains("download") || tempContextUrl.contains("emperorbcorder")
//                                    || tempContextUrl.contains("maintenanceMessage")
//                                    || tempContextUrl.contains("null")
//                                    || tempContextUrl.contains("smNcd")
//                                    ){
//                                if(tempContextUrl.contains("null")){
//                                    rq.getRequestDispatcher("/open/error.jsp").forward(request, response);
//                                }else{
//                                    filterChain.doFilter(request, response);
//                                }
//
//                            }else{
//                                if (session.getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF) == null) {
//                                    rq.setAttribute("timeout", "timeout");
//                                    rq.getRequestDispatcher("/open/timeout.jsp").forward(request, response);
//                                    return;
//                                }else{
//                                    filterChain.doFilter(request, response);
//                                }
//                            }
//                        } else {
//                            if(tempContextUrl.contains(".jsp")){  // open 拦截jsp页面
//                                if(tempContextUrl.contains("login.jsp") || tempContextUrl.contains("index.jsp") || tempContextUrl.contains("error.jsp")){
//                                    filterChain.doFilter(request, response);
//                                }else{
//                                    rq.getRequestDispatcher("/open/error.jsp").forward(request, response);
//                                }
//                            }else {
//                                filterChain.doFilter(request, response);
//                            }
//                        }
//                    }
//
//                }
        }

        catch (Exception ex){
            System.out.println("error on commit:servletException");
            ex.printStackTrace();
        }

    }

    //Clean up resources
    public void destroy()
    {
    }
}
