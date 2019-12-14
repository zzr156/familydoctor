package com.ylz.bizDo.cd.vo;


import com.ylz.packcommon.common.CommConditionVo;

public class DeptQvo extends CommConditionVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deptName;//部门名称
	private String cdInstitution;//机构
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getCdInstitution() {
		return cdInstitution;
	}
	public void setCdInstitution(String cdInstitution) {
		this.cdInstitution = cdInstitution;
	}
	
	
	

	
}
