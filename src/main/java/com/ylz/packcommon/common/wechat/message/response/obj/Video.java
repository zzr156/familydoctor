package com.ylz.packcommon.common.wechat.message.response.obj;

/**
 * 视频model
 * 
 * @author xyy
 * @date 2014-10-14
 *
 */
public class Video {
	
	// 媒体文件id
	private String MediaId;
	// 缩略图的媒体id
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
