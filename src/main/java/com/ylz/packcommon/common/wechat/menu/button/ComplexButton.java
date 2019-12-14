package com.ylz.packcommon.common.wechat.menu.button;

import com.ylz.packcommon.common.wechat.menu.button.base.Button;

import java.util.List;

/**
 * 复合类型的按钮
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class ComplexButton extends Button {
	
	private List<Button> sub_button;

	public List<Button> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<Button> sub_button) {
		this.sub_button = sub_button;
	}
}
