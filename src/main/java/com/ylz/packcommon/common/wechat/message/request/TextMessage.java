package com.ylz.packcommon.common.wechat.message.request;

import com.ylz.packcommon.common.wechat.message.request.base.BaseMessage;

/**
 * 文本消息
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class TextMessage extends BaseMessage {
	
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
