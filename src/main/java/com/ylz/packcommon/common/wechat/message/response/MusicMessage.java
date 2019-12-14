package com.ylz.packcommon.common.wechat.message.response;

import com.ylz.packcommon.common.wechat.message.response.base.BaseMessage;
import com.ylz.packcommon.common.wechat.message.response.obj.Music;

;

/**
 * 音乐消息
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class MusicMessage extends BaseMessage {
	
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}
