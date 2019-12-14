package com.ylz.packcommon.common.wechat.message.response;

import com.ylz.packcommon.common.wechat.message.request.base.BaseMessage;
import com.ylz.packcommon.common.wechat.message.response.obj.Voice;

/**
 * 语音消息
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class VoiceMessage extends BaseMessage {
	
	// 语音
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
