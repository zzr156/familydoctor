package com.ylz.packcommon.common.wechat.menu.button;

import com.ylz.packcommon.common.wechat.menu.button.base.Button;

/**
 * click类型的按钮
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class ClickButton extends Button {
	
	private String type;
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}