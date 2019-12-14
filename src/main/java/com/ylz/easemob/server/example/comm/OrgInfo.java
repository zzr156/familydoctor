package com.ylz.easemob.server.example.comm;

import com.ylz.packcommon.common.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by easemob on 2017/3/31.
 */
public class OrgInfo {

    public static String ORG_NAME;
    public static String APP_NAME;
    public static final Logger logger = LoggerFactory.getLogger(OrgInfo.class);

    static {
        try {
            ORG_NAME =  PropertiesUtil.getConfValue("ORG_NAME");
            APP_NAME =  PropertiesUtil.getConfValue("APP_NAME");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
