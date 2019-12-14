package com.ylz.packcommon.common.wechat.menu.button;

import com.ylz.packcommon.common.wechat.menu.button.base.Button;

/**
 * view类型的按钮
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class ViewButton extends Button {
	
	private String type;
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
