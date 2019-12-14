package com.ylz.bizDo.cd.vo;


import com.ylz.packcommon.common.util.ExtendDate;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by PC on 2016/2/16.
 * 消息查询条件
 */
public class CdMsgVo {
	private String msgId;//主键
    private String msgtitle;//标题
    private String msgCreaterDate;//创建时间
    private String msgUserName;//创建者
    private String msgtext;//发送内容
    private String readDate;//读取时间
    
    
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgtitle() {
		return msgtitle;
	}
	public void setMsgtitle(String msgtitle) {
		this.msgtitle = msgtitle;
	}
	public String getMsgCreaterDate() {
		return msgCreaterDate;
	}
	public void setMsgCreaterDate(Timestamp msgCreaterDate) {
		if(msgCreaterDate != null){
			Calendar cal = new java.util.GregorianCalendar();
			cal.setTime(msgCreaterDate);
			this.msgCreaterDate = ExtendDate.getYMD_h_m(cal);
		}
	}
	public String getMsgUserName() {
		return msgUserName;
	}
	public void setMsgUserName(String msgUserName) {
		this.msgUserName = msgUserName;
	}
	public String getMsgtext() {
		return msgtext;
	}
	public void setMsgtext(String msgtext) {
		this.msgtext = msgtext;
	}
	public String getReadDate() {
		return readDate;
	}
	public void setReadDate(Timestamp readDate) {
		if(readDate != null){
			Calendar cal = new java.util.GregorianCalendar();
			cal.setTime(readDate);
			this.readDate = ExtendDate.getYMD_h_m(cal);
		}
		
	}


  
}
