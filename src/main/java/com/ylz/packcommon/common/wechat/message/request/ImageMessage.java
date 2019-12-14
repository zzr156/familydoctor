package com.ylz.packcommon.common.wechat.message.request;

import com.ylz.packcommon.common.wechat.message.request.base.BaseMessage;

/**
 * 图片消息
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class ImageMessage extends BaseMessage {
	
	//图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}
