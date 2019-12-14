package com.ylz.packcommon.common.wechatUtil;

import com.ylz.packcommon.common.wechat.pojo.Token;
import com.ylz.task.Thread.TokenThread;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;

/**
 * 通用工具类
 * 
 * @author xyy
 * @date 2014-10-14
 */
public class CommonUtil {
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

	// 凭证获取（GET）
	public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		HttpsURLConnection conn = null;
		InputStream inputStream = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			conn = (HttpsURLConnection) url.openConnection();
			if(conn!=null&&ssf!=null){
				conn.setSSLSocketFactory(ssf);
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
			/******************20150203***********************/
				//连接超时
				conn.setConnectTimeout(10000);
				//读取超时 
				conn.setReadTimeout(10000);
			/******************20150203***********************/
				// 设置请求方式（GET/POST）
				conn.setRequestMethod(requestMethod);
				// 当outputStr不为null时向输出流写数据
				if (null != outputStr) {
					OutputStream outputStream = conn.getOutputStream();
					// 注意编码格式
					outputStream.write(outputStr.getBytes("UTF-8"));
					outputStream.close();
				}
				// 从输入流读取返回内容
				try {
					inputStream = conn.getInputStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error("HttpsURLConnection.getInputStream()超时：{}", e);
				}
				if(inputStream!=null){
					InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
					String str = null;
					StringBuffer buffer = new StringBuffer();
					while ((str = bufferedReader.readLine()) != null) {
						buffer.append(str);
					}
					// 释放资源
					bufferedReader.close();
					inputStreamReader.close();
					inputStream.close();
					jsonObject = JSONObject.fromObject(buffer.toString());
				}
			}
		}catch (Exception e) {
			log.error("httpsRequest失败：{}", e);
		} finally{
			conn.disconnect();
			inputStream = null;
		}
		return jsonObject;
	}

	/**
	 * 获取接口访问凭证
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static Token getAccessToken(String appid, String appsecret){
		Token token = null;
		String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		try {
			// 发起GET请求获取凭证
			JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
			if (null != jsonObject) {
				try {
					token = new Token();
					token.setAccessToken(jsonObject.getString("access_token"));
					token.setExpiresIn(jsonObject.getInt("expires_in"));
				} catch (JSONException e) {
					token = null;
					// 获取token失败
					log.error("获取accesstoken失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			token = null;
			log.error("获取accesstoken空"+e);
		}
		return token;
	}
	public static Token getToken(String appid, String appsecret) {
		if(TokenThread.accessToken!=null){
			return TokenThread.accessToken;
		}else{
			return getAccessToken(appid,appsecret);
		}
	}
	
	/**
	 * URL编码（utf-8）
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error("url-utf转换错"+e);
		}
		return result;
	}
	
	/**
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType 内容类型
	 * @return
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType)) {
            fileExt = ".jpg";
        } else if ("audio/mpeg".equals(contentType)) {
            fileExt = ".mp3";
        } else if ("audio/amr".equals(contentType)) {
            fileExt = ".amr";
        } else if ("video/mp4".equals(contentType)) {
            fileExt = ".mp4";
        } else if ("video/mpeg4".equals(contentType)) {
            fileExt = ".mp4";
        }
		return fileExt;
	}
}