package com.ylz.bizDo.cd.vo;


import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by PC on 2016/2/16.
 * 消息查询条件
 */
public class CdMsgQvo extends CommConditionVo {
    private String msgtitle;//标题
    private String sendstartdate;//发送开始时间
    private String sendenddate;//发送结束时间
    private String msgtext;//发送内容
    private String senduserid;//发送者id
    private String accepteruserid;//接收者id
    private String isread;//是否已读
    private String contentId;//sp id
    private String spState;//流程状态
    private String msgType;//消息类型
    
    
    
    
    public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getSpState() {
		return spState;
	}

	public void setSpState(String spState) {
		this.spState = spState;
	}

	public String getMsgtitle() {
        return msgtitle;
    }

    public void setMsgtitle(String msgtitle) {
        this.msgtitle = msgtitle;
    }

    public String getSendstartdate() {
        return sendstartdate;
    }

    public void setSendstartdate(String sendstartdate) {
        this.sendstartdate = sendstartdate;
    }

    public String getSendenddate() {
        return sendenddate;
    }

    public void setSendenddate(String sendenddate) {
        this.sendenddate = sendenddate;
    }

    public String getMsgtext() {
        return msgtext;
    }

    public void setMsgtext(String msgtext) {
        this.msgtext = msgtext;
    }

    public String getSenduserid() {
        return senduserid;
    }

    public void setSenduserid(String senduserid) {
        this.senduserid = senduserid;
    }

    public String getAccepteruserid() {
        return accepteruserid;
    }

    public void setAccepteruserid(String accepteruserid) {
        this.accepteruserid = accepteruserid;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }
}
