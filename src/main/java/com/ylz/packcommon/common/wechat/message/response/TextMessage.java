package com.ylz.packcommon.common.wechat.message.response;

import com.ylz.packcommon.common.wechat.message.response.base.BaseMessage;

/**
 * 文本消息
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class TextMessage extends BaseMessage {
	
	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
