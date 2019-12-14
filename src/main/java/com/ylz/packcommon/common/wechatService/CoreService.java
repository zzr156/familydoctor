package com.ylz.packcommon.common.wechatService;

import com.ylz.packcommon.common.wechat.encrypt.decrypt.Decrypt;
import com.ylz.packcommon.common.wechat.message.response.NewsMessage;
import com.ylz.packcommon.common.wechat.message.response.TextMessage;
import com.ylz.packcommon.common.wechat.message.response.base.BaseMessage;
import com.ylz.packcommon.common.wechat.message.response.obj.Article;
import com.ylz.packcommon.common.wechatUtil.MessageUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 核心服务类
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class CoreService {
	
	private static Logger log = LoggerFactory.getLogger(CoreService.class);
	private static Properties p = new Properties();
	static{
		//加载配置文件
		if(p!=null){
			try {
				p.load(CoreService.class.getResourceAsStream("/reply.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request, String signature, String timestamp, String nonce) {
		// xml格式的消息数据
		String respXml = null;
		Boolean flag = false;
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			//String encrypt_type =request.getParameter("encrypt_type");
			//加密
			if(requestMap!=null&&requestMap.get("Encrypt")!=null){
				requestMap=Decrypt.getDecryptMap(requestMap,signature,timestamp,nonce);
				flag=true;
			}
			if(requestMap!=null){
				
				// 发送方帐号
				String fromUserName = requestMap.get("FromUserName");
				//save openid
				//validateUser(request,fromUserName);
				// 开发者微信号
				String toUserName = requestMap.get("ToUserName");
				if(StringUtils.isNotBlank(fromUserName)&& StringUtils.isNotBlank(toUserName)){
					// 消息类型
					String msgType = requestMap.get("MsgType");
					// 未设置的默认回复
//					textMessage.setContent("请点击底部菜单进行有效操作"); 
//					respXml = reply(respXml,"moren",p,fromUserName,toUserName);
					// 来源事件 
					if (msgType!=null&&msgType.equals(MessageUtil.REQUEST_MESSAGE_TYPE_EVENT)) {
						
						// 事件类型
						String eventType = requestMap.get("Event");
						// 关注
						if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
							//读取配置文件的关注回复
							respXml = reply(respXml,"subscribe",p,fromUserName,toUserName);
							if(fromUserName!=null&&fromUserName.length()>5){
								log.warn("subscribe:"+fromUserName.substring(fromUserName.length()-5, fromUserName.length())+"-----EventKey:"+requestMap.get("EventKey"));
							}
						}
						// 取消关注
						else if (eventType!=null&&eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
							// TODO 取消订阅后用户不会再收到公众账号发送的消息
							return null;
						}
						// 扫描带参数二维码
						else if (eventType!=null&&eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
							// TODO 处理扫描带参数二维码事件
							log.warn("SMewmEW-----EventKey:"+requestMap.get("EventKey"));
							return null;
						}
						//自定义菜单(VIEW)
						else if (eventType!=null&&eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
							return null;
						}
						// 自定义菜单(CLICK)
						else if (eventType!=null&&eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
							// 事件KEY值，与创建菜单时的key值对应
							String eventKey = requestMap.get("EventKey");
							// 根据key值判断用户点击的按钮
							//3*5菜单
							if(eventKey!=null&&eventKey.contains("m")&&eventKey.contains("key")){
								if(eventKey.contains("km")){
									for(int j=1;j<=3;j++){
										String m="km"+j+"key";
										if(eventKey.equals(m)){
											respXml=reply(respXml,m,p,fromUserName,toUserName);
											break;
										}
									}
								}else{
									for(int i=1;i<=3;i++){
										for(int j=1;j<=5;j++){
											String m="m"+i+j+"key";
											if(eventKey.equals(m)){
												respXml=reply(respXml,m,p,fromUserName,toUserName);
												break;
											}
										}
										
									}
								}
							}else{
								return null;
							}
						}
					}
					// 来源消息
					else{
						// 文本,图片，语音，链接消息转多客服
						if (msgType!=null&&(msgType.equals(MessageUtil.REQUEST_MESSAGE_TYPE_TEXT)
								||msgType.equals(MessageUtil.REQUEST_MESSAGE_TYPE_IMAGE)
								||msgType.equals(MessageUtil.REQUEST_MESSAGE_TYPE_VOICE)
								||msgType.equals(MessageUtil.REQUEST_MESSAGE_TYPE_LINK))) {
							BaseMessage baseMessage = new BaseMessage();
							baseMessage.setToUserName(fromUserName);
							baseMessage.setFromUserName(toUserName);
							baseMessage.setCreateTime(System.currentTimeMillis());
							baseMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_CUSTOMER);
							respXml = MessageUtil.messageToXml(baseMessage);
							System.out.println("customer");
						}
					}
				}
				
			}
		} catch (Exception e) {
			log.error("coreservice处理微信发来的请求出错!___"+e);
		}
		if(StringUtils.isNotBlank(respXml)&&flag){
			respXml= Decrypt.encrypt(timestamp, nonce, respXml);
		}
//		System.out.println("respXml:"+respXml);
		return respXml;
	}
	
	//回复信息模板
	public static String reply(String str,String m,Properties p,String fromUserName,String toUserName){
		
		if(p.getProperty(m)!=null){
			//文本信息
			if("text".equals(p.getProperty(m))){
				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(System.currentTimeMillis());
				textMessage.setMsgType(MessageUtil.RESPONSE_MESSAGE_TYPE_TEXT);
				textMessage.setContent(p.getProperty(m+"_text_content"));
				str = MessageUtil.messageToXml(textMessage);
			}
			//图文信息
			if("news".equals(p.getProperty(m))){
				//允许所发的图片的最大数
				Integer km =1;
				if(p.getProperty("news_max")!=null){
					km = Integer.parseInt(p.getProperty("news_max"));
					if(km>10) {
                        km=10;
                    }
                    if(km<1) {
                        km=1;//最多10张图
                    }
				}
				List<Article> articleList = new ArrayList<Article>();
				for(int k=1;k<=km;k++){
					if(p.getProperty(m+"_news"+k+"_title")!=null){
						Article article = new Article();
						article.setTitle(p.getProperty(m+"_news"+k+"_title"));
						if(p.getProperty(m+"_news"+k+"_description")!=null) {
                            article.setDescription(p.getProperty(m+"_news"+k+"_description"));
                        } else {
                            article.setDescription("");
                        }
						if(p.getProperty(m+"_news"+k+"_picurl")!=null) {
                            article.setPicUrl(p.getProperty(m+"_news"+k+"_picurl"));
                        } else {
                            article.setPicUrl("");
                        }
						if(p.getProperty(m+"_news"+k+"_url")!=null) {
                            article.setUrl(p.getProperty(m+"_news"+k+"_url"));
                        } else {
                            article.setUrl("");
                        }
						articleList.add(article);
					}
				}
				if(articleList!=null&&articleList.size()>0){
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(System.currentTimeMillis());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					str = MessageUtil.messageToXml(newsMessage);
				}
			}
			//语音信息
			//图片信息
		}
		return str;
	}
	
}
