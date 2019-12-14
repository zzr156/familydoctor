package com.ylz.packcommon.common.ccqInterface;

import com.ylz.packcommon.common.DESEncrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
/**
 * Created by wsk on 2016/10/10.
 */
public class CcqInterUtil {

    private static final String IP_URL = "http://120.55.73.98:12080/ccqwsV2/api/rest/main";
    private static final String KEY = "laxJf3Gx";  //你们的secret_key
    private static final int CONNECT_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 20000;
    private static final String USERNAME = "hndiyizhengxin";// 用户名

    public static String ccqInterUtil(String productId,  Map<String, String> param, String type)
    {

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : param.entrySet())
        {
            if (builder.length() > 0)
            {
                builder.append("&");
            }
            builder.append(entry.getKey()+"=");
            builder.append(entry.getValue());
        }

        String  desParam =  DESEncrypt.encode(KEY, builder.toString());

        StringBuffer sbf = new StringBuffer();
        URL url = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        try
        {
            url = new URL(IP_URL);

            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");// 提交模式
            conn.setConnectTimeout(CONNECT_TIMEOUT);// 连接超时 单位毫秒
            conn.setReadTimeout(READ_TIMEOUT);// 读取超时 单位毫秒
            conn.setDoOutput(true);// 是否输入参数

            StringBuffer params = new StringBuffer();
            // 表单参数与get形式
            byte[] bytes = ("apiKey=" + productId + "&username=" + USERNAME + "&params=" + desParam + "&format="+type).getBytes();
            conn.getOutputStream().write(bytes);// 输入参数
            conn.connect();
            InputStream inStream = conn.getInputStream();
            // 读取数据编码处理

            String strRead = null;
            reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));

            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (null != reader)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                }

            }
            if (null != conn)
            {
                // 断开连接
                conn.disconnect();
            }
        }
        // System.out.println(sbf.toString());
        return  sbf.toString();
    }
}
