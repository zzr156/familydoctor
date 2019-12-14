package com.ylz.packcommon.common.wechat.entity;

import java.io.Serializable;

public class LtInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String inCid;//栏目表流水键
	private String inTitle;//标题
	private String inContent;//内容
	private String inAuthorId;//作者ID)
	private String inAuthor;//作者
	private String inDate;//时间
	private String inIdentity;//作者身份(普通用户,医生)
	private String inQuestioning;//被提问人
	private String inUnitId;//单位ID
	private String inUnit;//单位
	private String inTop;//是否置顶
	private String inTopDate;//置顶时间
	private String inJh;//是否精华
	private String inWtfl;//问题分类
	private String msgIdentityId;//身份ID(1用户 2医生)
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getInCid() {
		return inCid;
	}


	public void setInCid(String inCid) {
		this.inCid = inCid;
	}


	public String getInTitle() {
		return inTitle;
	}


	public void setInTitle(String inTitle) {
		this.inTitle = inTitle;
	}


	public String getInContent() {
		return inContent;
	}


	public void setInContent(String inContent) {
		this.inContent = inContent;
	}


	public String getInAuthorId() {
		return inAuthorId;
	}


	public void setInAuthorId(String inAuthorId) {
		this.inAuthorId = inAuthorId;
	}


	public String getInAuthor() {
		return inAuthor;
	}


	public void setInAuthor(String inAuthor) {
		this.inAuthor = inAuthor;
	}


	public String getInDate() {
		return inDate;
	}


	public void setInDate(String inDate) {
		this.inDate = inDate;
	}


	public String getInIdentity() {
		return inIdentity;
	}


	public void setInIdentity(String inIdentity) {
		this.inIdentity = inIdentity;
	}


	public String getInQuestioning() {
		return inQuestioning;
	}


	public void setInQuestioning(String inQuestioning) {
		this.inQuestioning = inQuestioning;
	}


	public String getInUnitId() {
		return inUnitId;
	}


	public void setInUnitId(String inUnitId) {
		this.inUnitId = inUnitId;
	}


	public String getInUnit() {
		return inUnit;
	}


	public void setInUnit(String inUnit) {
		this.inUnit = inUnit;
	}


	public String getInTop() {
		return inTop;
	}


	public void setInTop(String inTop) {
		this.inTop = inTop;
	}


	public String getInTopDate() {
		return inTopDate;
	}


	public void setInTopDate(String inTopDate) {
		this.inTopDate = inTopDate;
	}


	public String getInJh() {
		return inJh;
	}


	public void setInJh(String inJh) {
		this.inJh = inJh;
	}


	public String getInWtfl() {
		return inWtfl;
	}


	public void setInWtfl(String inWtfl) {
		this.inWtfl = inWtfl;
	}
	

	public String getMsgIdentityId() {
		return msgIdentityId;
	}
	

	public void setMsgIdentityId(String msgIdentityId) {
		this.msgIdentityId = msgIdentityId;
	}
	
	
	public LtInfo() {
		super();
	}
	
//	public String getLtMsg(){
//		SysDao dao=(SysDao)SpringHelper.getBean("sysDao");
//		if(this.getId()!=null){
//			LtColumn lc=dao.getLtColumnDo().find(this.getInCid());
//			String colName=lc.getColName();
//			return colName;
//		}
//		return null;
//	}
	
	
}
