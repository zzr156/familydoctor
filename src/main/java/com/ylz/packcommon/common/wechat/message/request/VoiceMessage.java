package com.ylz.packcommon.common.wechat.message.request;

import com.ylz.packcommon.common.wechat.message.request.base.BaseMessage;

/**
 * 语音消息
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class VoiceMessage extends BaseMessage {
	
	// 媒体ID
	private String MediaId;
	// 语音格式
	private String Format;
	// 语音识别结果，UTF8编码
	private String Recognition;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
}
