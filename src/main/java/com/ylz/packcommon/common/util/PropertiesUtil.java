package com.ylz.packcommon.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 14-10-21.
 */
public class PropertiesUtil {

    public static String getConfValue(String key) throws IOException {
        java.util.Properties props = new java.util.Properties();
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream("conf.properties");
        props.load(is);
        String s=props.getProperty(key);
        return s;
    }
    public static Boolean getConfValueBoolean(String key) throws IOException {
        java.util.Properties props = new java.util.Properties();
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream("conf.properties");
        props.load(is);
        Boolean s= Boolean.valueOf(props.getProperty(key));
        return s;
    }

    public static String getConfValueUrl(String key) throws IOException {
        java.util.Properties props = new java.util.Properties();
        File file = new File(PropertiesUtil.getConfValue("jurl"));
        FileInputStream fin = new FileInputStream(file);
        props.load(fin);
        String s=props.getProperty(key);
        return s;
    }
}
