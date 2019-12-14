package com.ylz.packcommon.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.*;
/**
 * Created by hzk on 2017/7/29.
 */
public class HttpPostUtils {
    private static List<HttpClient> conns = Collections.synchronizedList(new ArrayList<HttpClient>());

    private static HttpClient getConn()
    {

        return newConn();

    }

    private static HttpClient newConn()
    {
        try
        {
            return new DefaultHttpClient();
        }
        catch (Exception e)
        {
            return new DefaultHttpClient();
        }
    }

    public void testSll()
    {

    }

    private static SSLSocketFactory ssl()
    {

        return null;
    }

    public static String doPostJson(String jsonData, String url) throws Exception
    {
        HttpClient httpclient = null;
        try
        {
            HttpPost httpRequest = new HttpPost(url);

            HttpEntity httpentity = new StringEntity(jsonData, "UTF-8");
            httpRequest.setEntity(httpentity);
            httpclient = getConn();

            HttpResponse httpResponse = httpclient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {

                String strrs = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(strrs);
                strrs = (strrs == null ? "{}" : strrs);
                return strrs;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (httpclient != null)
            {
                httpclient.getConnectionManager().closeExpiredConnections();
                httpclient.getConnectionManager().shutdown();
            }
            // conns.add(httpclient);
        }
        return null;
    }

    /**
     * 实际上就是一个GET 调用
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String doPost(String url) throws Exception
    {
        HttpClient httpclient = null;
        try
        {
            HttpPost httpRequest = new HttpPost(url);
            httpRequest.getParams().setParameter("http.protocol.content-charset", HTTP.UTF_8);
            httpRequest.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
            httpRequest.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
            httpRequest.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET, HTTP.UTF_8);
            httpRequest.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET, HTTP.UTF_8);
            httpclient = getConn();

            HttpResponse httpResponse = httpclient.execute(httpRequest);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {

                String strrs = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(strrs);
                strrs = (strrs == null ? "{}" : strrs);
                return strrs;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (httpclient != null)
            {
                httpclient.getConnectionManager().closeExpiredConnections();
                httpclient.getConnectionManager().shutdown();
            }
            // conns.add(httpclient);
        }
        return null;
    }
    /**
     *
     * @param props props格式为Map<parameterName,parameterValue>
     * @param url
     * @return
     * @throws Exception
     */
    public static String doPost(Map<String, String> props, String url) throws Exception
    {
        HttpClient httpclient = null;
        try
        {
            HttpPost httpRequest = new HttpPost(url);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if (props != null)
            {

                Set<String> kyes = props.keySet();
                if (kyes != null) {
                    for (String k : kyes)
                    {
                        BasicNameValuePair p = new BasicNameValuePair(k, props.get(k));
                        params.add(p);

                    }
                }
            }
            HttpEntity httpentity = new UrlEncodedFormEntity(params, "UTF-8");
            httpRequest.setEntity(httpentity);
            httpclient = getConn();

            HttpResponse httpResponse = httpclient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {

                String strrs = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(strrs);
                strrs = (strrs == null ? "{}" : strrs);
                return strrs;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (httpclient != null)
            {
                httpclient.getConnectionManager().closeExpiredConnections();
                httpclient.getConnectionManager().shutdown();
            }
            // conns.add(httpclient);
        }
        return null;
    }
}
