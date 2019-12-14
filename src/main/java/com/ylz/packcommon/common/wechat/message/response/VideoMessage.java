package com.ylz.packcommon.common.wechat.message.response;


import com.ylz.packcommon.common.wechat.message.request.base.BaseMessage;
import com.ylz.packcommon.common.wechat.message.response.obj.Video;

/**
 * 视频消息
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class VideoMessage extends BaseMessage {
	
	// 视频
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
