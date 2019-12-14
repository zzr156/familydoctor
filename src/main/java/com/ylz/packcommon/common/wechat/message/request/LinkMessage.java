package com.ylz.packcommon.common.wechat.message.request;

import com.ylz.packcommon.common.wechat.message.request.base.BaseMessage;

/**
 * 链接消息
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class LinkMessage extends BaseMessage {
	
	// 消息标题
	private String Title;
	// 消息描述
	private String Description;
	// 消息链接
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
