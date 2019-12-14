package com.ylz.packcommon.common.wechat.encrypt.decrypt;

import com.ylz.packcommon.common.wechat.encrypt.WXBizMsgCrypt;
import com.ylz.packcommon.common.wechat.entity.Encrpt;
import com.ylz.packcommon.common.wechatUtil.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Map;

public class Decrypt {
	
	private static Logger log = LoggerFactory.getLogger(Decrypt.class);
	
	private static final String appId="wxe07f4e9eb5c4c4c1";
	private static final String token="grid_wechat_reservation";
	private static final String encodingAesKey="1k6FyAMbzFZMi22s5Dpu8NtZixSb7JSHjOHhL2GrlMo";
	private static WXBizMsgCrypt pc =null;
	
	/**
	 * 消息体加密
	 * @param timestamp
	 * @param nonce
	 * @param replyMsg
	 * @return
	 */
	public static String encrypt(String timestamp,String nonce,String replyMsg){
		try {
			if(pc==null){
				pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			}
			String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
			return mingwen;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("消息体加密encrypt()-"+e);
		}
		return null;
	}
	
	/**
	 * 消息体解密
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param mingwen
	 * @return
	 */
	public static String dencrypt(String signature,String timestamp,String nonce,String mingwen){
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(mingwen);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			Element root = document.getDocumentElement(); 
			NodeList nodelist1 = root.getElementsByTagName("Encrypt");
			String encrypt = nodelist1.item(0).getTextContent();
			String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
			String fromXML = String.format(format, encrypt);
			if(pc==null){
				pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			}
			String dencrypt = pc.decryptMsg(signature, timestamp, nonce, fromXML);
			//System.out.println("解密后明文: " + dencrypt);
			sr.close();
			sr=null;is=null;
			return dencrypt;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("消息体解密dencrypt()-"+e);
		}
		return null;
	}
	
	/**
	 * 获取解密后的map
	 * @param requestMap
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static Map<String, String> getDecryptMap(Map<String, String> requestMap,String signature,String timestamp,String nonce){
		try {
			if(requestMap!=null){
				Encrpt encrpt = new Encrpt();
				encrpt.setEncrypt(requestMap.get("Encrypt"));
				encrpt.setToUserName(requestMap.get("ToUserName"));
				if(encrpt!=null&&encrpt.getEncrypt()!=null){
					String encrptStr = MessageUtil.encrptToXml(encrpt);
					if(encrptStr!=null){
						String dencrptStr = Decrypt.dencrypt(signature,timestamp, nonce, encrptStr);
						if(dencrptStr!=null){
							Map<String, String> map = MessageUtil.StrXml(dencrptStr);
							if(map!=null){
								System.out.println("encrypt");
								return map;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("获取解密后的map-getDecryptMap()-"+e);
		}
		return requestMap;
	}	
	
}
