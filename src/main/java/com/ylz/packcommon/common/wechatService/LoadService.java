package com.ylz.packcommon.common.wechatService;

import com.ylz.packcommon.common.wechatUtil.AdvancedUtil;
import com.ylz.packcommon.common.wechatUtil.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class LoadService {
	
	private static Logger log = LoggerFactory.getLogger(LoadService.class);

	public static  String DOMAIN = null;
	public static  String WEBSERVICE = null;
	public static  String NWWD = null;
	public static  String APPID = null;
	public static  String TOKEN = null;
	public static  String APPSECRT = null;
	public static  String NAMESPACE = null;
	public static  String VB = null;
	static{
		Properties p = new Properties();
		//加载配置文件
		if(p!=null){
			try {
				p.load(LoadService.class.getResourceAsStream("/load.properties"));
				DOMAIN=p.getProperty("domain");
				WEBSERVICE=p.getProperty("webservice");
				NWWD=p.getProperty("nwwd");
				APPID=p.getProperty("appid");
				TOKEN=p.getProperty("token");
				APPSECRT=p.getProperty("appsecrt");
				NAMESPACE=p.getProperty("namespace");
				VB=p.getProperty("vb");
				System.out.println("Loading load property!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取cookie存的参数
	 */
	public static String getLCK(HttpServletRequest request, String param, String cs){
		String data=cs;
		try {
			Cookie[] ck = request.getCookies();
			if(ck!=null&&ck.length>0){
				for(int i=0;i<ck.length;i++){ 
					//System.out.println(ck[i].getName()+ck[i].getValue());
					if(ck[i]!=null&&ck[i].getName()!=null&&ck[i].getName().equals(param)&& StringUtils.isNotBlank(ck[i].getValue())){
						data+=";;;"+ck[i].getValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("getck--"+e);
		}
		return data;
	}
	/**  
	 * 存参数进cookie
	 */
	public static void saveCK(HttpServletRequest request, HttpServletResponse response, String param, String cs, int day){
		try {
			Cookie cka = new Cookie(param, cs);
			cka.setMaxAge(day*24*60*60);//1年
			response.addCookie(cka);
//		    Cookie ckb = new Cookie("WCIRPWD", pwd);
//			ckb.setMaxAge(365*24*60*60);//1年
//			response.addCookie(ckb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("saveck--"+e);
		}
	}
	//删除cook
	public static void delCK(HttpServletResponse response, String param){
		try {
			Cookie ckb = new Cookie(param, null);
			ckb.setMaxAge(0);
			response.addCookie(ckb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("delck--"+e);
		}
	}
	/**
	 * 获取opid,nick
	 */
	public static String getSCCS(HttpServletRequest request, String str, int k){
		String cs= null;
		if(k==1){
			cs=(String) request.getSession(true).getAttribute(str);
		}else{
			cs=(String) request.getSession().getAttribute(str);
		}
		try {
			if(cs==null){
				Cookie[] ck = request.getCookies();
				if(ck!=null&&ck.length>0){
					for(int i=0;i<ck.length;i++){
						if(ck[i]!=null&&ck[i].getName()!=null&&ck[i].getValue()!=null&&(str).equals(ck[i].getName().toString())){
							request.getSession().setAttribute(str,ck[i].getValue().toString());
							cs=ck[i].getValue().toString();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("getSCCS--"+e);
		}
		return cs;
	}
	public static String getCK(HttpServletRequest request, String str){
		String cs= null;
		try {
			Cookie[] ck = request.getCookies();
			if(ck!=null&&ck.length>0){
				for(int i=0;i<ck.length;i++){
					if(ck[i]!=null&&ck[i].getName()!=null&&ck[i].getValue()!=null&&(str).equals(ck[i].getName().toString())){
						cs=ck[i].getValue().toString();
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("getCCS--"+e);
		}
		return cs;
	}
	
	/**
	 * 发送消息
	 */
	public static void sendMessage(String opid,String content,String msg){
		try {
			//String appSecret = "99d06230ae20dedad97ffc076f1d7251";//APPSECRET
			if(StringUtils.isNotBlank(APPSECRT)){
				//获取接口访问凭证
				String accessToken = CommonUtil.getToken(APPID, APPSECRT).getAccessToken();
				//组装文本客服消息
				String jsonTextMsg = AdvancedUtil.makeTextCustomMessage(opid,content);
				//发送客服消息
				AdvancedUtil.sendCustomMessage(accessToken,jsonTextMsg,opid,msg);
			}else{
				log.warn("appSecret is empty");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("sendmessage--"+e);
		}
	}
}
