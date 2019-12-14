package com.ylz.packcommon.hyd;

import com.jayway.jsonpath.JsonPath;
import com.ylz.packcommon.hyd.aes.AesMsgCrypt;
import com.ylz.packcommon.hyd.aes.IMsgCrypt;
import com.ylz.packcommon.hyd.aes.SHA1;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright:YLZinfo 2017 cop. ltd.
 * </p>
 * <p>
 * Company:易联众技术股份有限公司
 * Created by  ZJT on 2017/5/27.
 */
public abstract class Base {
    public static final String  HEAD_URL_SERVER="http://open.haoyd.com";
    public static final String  HEAD_URL_TEST="http://120.42.37.86:10601/egodrug-openapi";

    public static final String  HEAD_URL_LOCAL="http://127.0.0.1:8085";

//    //测试配置
//    public final static String appid = "117588a63134444a83c15379c21xxaex";
//    public final static String encodingAesKey = "11CAkiNxfkHQcK26nCTBNw==";
//    public final static String token = "ylz_test";

    //正式配置
    public final static String appid = "4512138a639jallkddd1237321xxae81";
    public final static String encodingAesKey = "4sTamJxcO38x90xbdCTB1X==";
    public final static String token = "ylz_person_health_cloud";


    public static String excute(String url,String content){
        String result = null;
        final IMsgCrypt msgCrypt = new AesMsgCrypt(appid, encodingAesKey);
        String encryptBody = msgCrypt.encrypt(content);
        String timestamp = System.currentTimeMillis() + "";
        String nonce = RandomStringUtils.randomAlphanumeric(2);
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            String sign = SHA1.getSHA1(token, timestamp, nonce, encryptBody);
            Map<String,Object> param=new HashMap<String, Object>();
            param.put(Global.APP_KEY.APP_ID,appid);
            param.put(Global.APP_KEY.NONCE,nonce);
            param.put(Global.APP_KEY.TIMESTAMP,timestamp);
            param.put(Global.APP_KEY.SIGN,sign);
            Map<String,String> header=new HashMap<String, String>();
            header.put(HttpHeaders.CONTENT_TYPE,"application/json");
            StringBuilder bd=new StringBuilder();
            bd.append(url).append("?").append(Global.APP_KEY.APP_ID).append("=").append(appid);
            bd.append("&").append(Global.APP_KEY.NONCE).append("=").append(nonce);
            bd.append("&").append(Global.APP_KEY.TIMESTAMP).append("=").append(timestamp);
            bd.append("&").append(Global.APP_KEY.SIGN).append("=").append(sign);


            HttpPost httpPost = new HttpPost(bd.toString());

            String respContent = null;

            // json方式
            StringEntity entity = new StringEntity(encryptBody,"utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            HttpResponse resp = client.execute(httpPost);
            if(resp.getStatusLine().getStatusCode() == 200) {
                HttpEntity he = resp.getEntity();
                respContent = EntityUtils.toString(he,"UTF-8");
                Boolean success = JsonPath.read(respContent, "$.success");
                String entity_="";
                if(success) {
                    entity_ = JsonPath.read(respContent, "$.entity").toString();
                    entity_= msgCrypt.decrypt(entity_);
                    result = entity_;
                } else {
                    result = respContent;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  result;
    }

}
