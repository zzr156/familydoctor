package com.ylz.task.Thread;


import com.ylz.packcommon.common.wechat.pojo.Token;
import com.ylz.packcommon.common.wechatService.LoadService;
import com.ylz.packcommon.common.wechatUtil.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenThread implements Runnable{
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
	public static Token accessToken = null;

	public void run()  {
		while (true) {
			try {
				accessToken = CommonUtil.getAccessToken(LoadService.APPID, LoadService.APPSECRT);
				if (null != accessToken) {
					log.warn("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getAccessToken());
					// 休眠7000秒
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// 如果access_token为null，60秒后再获取
					Thread.sleep(60 * 1000);
				}
			} catch (Exception e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					log.error("获取access_token-e1{}", e1);
				}
				log.error("获取access_token-e{}", e);
			}
		}
	}
}
