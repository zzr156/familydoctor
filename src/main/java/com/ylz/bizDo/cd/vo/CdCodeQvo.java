package com.ylz.bizDo.cd.vo;


import com.ylz.packcommon.common.CommConditionVo;

public class CdCodeQvo extends CommConditionVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cdName;//名称
	private String cdGroup;//组名
	private String cdState;//状态
	private String cdGroupName;//
	private String cdValue;//
	public String getCdName() {
		return cdName;
	}
	public void setCdName(String cdName) {
		this.cdName = cdName;
	}
	public String getCdGroup() {
		return cdGroup;
	}
	public void setCdGroup(String cdGroup) {
		this.cdGroup = cdGroup;
	}
	public String getCdState() {
		return cdState;
	}
	public void setCdState(String cdState) {
		this.cdState = cdState;
	}
	public String getCdGroupName() {
		return cdGroupName;
	}
	public void setCdGroupName(String cdGroupName) {
		this.cdGroupName = cdGroupName;
	}

	public String getCdValue() {
		return cdValue;
	}

	public void setCdValue(String cdValue) {
		this.cdValue = cdValue;
	}
}
