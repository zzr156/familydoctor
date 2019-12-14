package com.ylz.packcommon.common.wechat.message.event;


import com.ylz.packcommon.common.wechat.message.event.base.BaseEvent;

/**
 * 自定义菜单事件
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class MenuEvent extends BaseEvent {
	
	// 事件KEY值，与自定义菜单接口中KEY值对应
	private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
}
