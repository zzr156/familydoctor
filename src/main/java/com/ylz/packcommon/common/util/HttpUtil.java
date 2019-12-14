package com.ylz.packcommon.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hzk on 2016/10/9.
 */
public class HttpUtil {
    public static CloseableHttpClient httpClient = HttpClients.createDefault();
    public static HttpResponse response=null;
    public static HttpGet httpGet=null;
    public static HttpPost httpPost=null;
    public static HttpEntity entity=null;
    public static boolean pdhszx=false;
    public static int staticcs=0;
    public static  String outEntity(HttpEntity entity) throws Exception
    {
        StringBuffer sb = null;
        // 输出页面内容
        if (entity != null) {
            String charset = EntityUtils.toString(entity);
            InputStream is = entity.getContent();
            sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\t\n");
            }
            is.close();
        }
        return sb.toString();
    }
}
