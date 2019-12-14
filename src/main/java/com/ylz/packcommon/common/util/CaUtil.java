package com.ylz.packcommon.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hzk on 2017/2/4.
 */
public class CaUtil {
    public static String getUserName(){
        String user = "";
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object c = request.getAttribute("javax.servlet.request.X509Certificate");
        java.security.cert.X509Certificate[] cc = (java.security.cert.X509Certificate[]) c;

        if (c != null)
        {
            String subject = cc[0].getSubjectDN().toString();
            if (subject != null)
            {
                String[] ss = subject.split(",");
                if (ss != null)
                {
                    for (int i = 0; i < ss.length; i++)
                    {
                        if (ss[i] != null && ss[i].startsWith("CN"))
                        {
                            user = ss[i].split("=")[1];
                            break;
                        }
                    }
                }
            }
        }
        return user;
    }
}
