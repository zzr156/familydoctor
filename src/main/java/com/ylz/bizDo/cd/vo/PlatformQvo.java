package com.ylz.bizDo.cd.vo;

import com.ylz.packcommon.common.CommConditionVo;

public class PlatformQvo extends CommConditionVo {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String platformItem;//功能项
	private String platformState;//功能项状态
	private String id;//id

	public String getPlatformItem() {
		return platformItem;
	}
	public void setPlatformItem(String platformItem) {
		this.platformItem = platformItem;
	}
	public String getPlatformState() {
		return platformState;
	}
	public void setPlatformState(String platformState) {
		this.platformState = platformState;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}




}
