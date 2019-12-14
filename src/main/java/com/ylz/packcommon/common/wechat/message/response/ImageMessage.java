package com.ylz.packcommon.common.wechat.message.response;

import com.ylz.packcommon.common.wechat.message.response.base.BaseMessage;
import com.ylz.packcommon.common.wechat.message.response.obj.Image;

/**
 * 图片消息
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class ImageMessage extends BaseMessage {
	
	//图片
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
}
