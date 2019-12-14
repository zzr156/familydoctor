package com.ylz.bizDo.news.vo;

import com.ylz.packcommon.common.CommConditionVo;


public class NewsTableQvo extends CommConditionVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tableNewsTitle;//新闻标题
	private String tableNewsType;//新闻类型
	private String tableStartCjsj;//新闻开始创建时间
	private String tableEndCjsj;//新闻结束创建时间
	private String tableNewsTypeNum;//新闻类型编码
	private boolean tableImageState = false;//图片状态
	private String typeId;
	private String state;//是否启用
	private String appLy;//来源
	private String dqState;//定期推送
	
	
	
	public String getAppLy() {
		return appLy;
	}
	public void setAppLy(String appLy) {
		this.appLy = appLy;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTableStartCjsj() {
		return tableStartCjsj;
	}
	public void setTableStartCjsj(String tableStartCjsj) {
		this.tableStartCjsj = tableStartCjsj;
	}
	public String getTableEndCjsj() {
		return tableEndCjsj;
	}
	public void setTableEndCjsj(String tableEndCjsj) {
		this.tableEndCjsj = tableEndCjsj;
	}
	public String getTableNewsTitle() {
		return tableNewsTitle;
	}
	public void setTableNewsTitle(String tableNewsTitle) {
		this.tableNewsTitle = tableNewsTitle;
	}
	public String getTableNewsType() {
		return tableNewsType;
	}
	public void setTableNewsType(String tableNewsType) {
		this.tableNewsType = tableNewsType;
	}
	public String getTableNewsTypeNum() {
		return tableNewsTypeNum;
	}
	public void setTableNewsTypeNum(String tableNewsTypeNum) {
		this.tableNewsTypeNum = tableNewsTypeNum;
	}
	public boolean isTableImageState() {
		return tableImageState;
	}
	public void setTableImageState(boolean tableImageState) {
		this.tableImageState = tableImageState;
	}

	public String getDqState() {
		return dqState;
	}

	public void setDqState(String dqState) {
		this.dqState = dqState;
	}
}
