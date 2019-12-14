package com.ylz.bizDo.jtapp.basicHealthEntity.jzYy;


public class SysDeptJk {
	private String deptId;//门诊id
	private String deptNum;//门诊编号
	private String deptSname;//门诊名称
	private String state;//状态
	private String sort;
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptNum() {
		return deptNum;
	}
	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}
	public String getDeptSname() {
		return deptSname;
	}
	public void setDeptSname(String deptSname) {
		this.deptSname = deptSname;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public SysDeptJk(String deptId, String deptNum, String deptSname,
			String state, String sort) {
		super();
		this.deptId = deptId;
		this.deptNum = deptNum;
		this.deptSname = deptSname;
		this.state = state;
		this.sort = sort;
	}
	public SysDeptJk() {
		super();
	}
	
	
}
