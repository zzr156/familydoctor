package com.ylz.packcommon.filter;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyStrutsFilter extends StrutsPrepareAndExecuteFilter{

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getRequestURI();                 
        if (url.contains("/ueditor/jsp/")) {                        
            chain.doFilter(req, res);         
        }else{                        
            super.doFilter(req, res, chain);         
        } 
    }
}
