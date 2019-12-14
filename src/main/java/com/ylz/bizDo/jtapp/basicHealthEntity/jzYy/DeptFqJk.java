package com.ylz.bizDo.jtapp.basicHealthEntity.jzYy;

import java.util.List;


public class DeptFqJk {
	private String deptNum;
	private String deptname;//区域名称
	private List<SysDeptJk> deptList;//区域下的门诊集合
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public List<SysDeptJk> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<SysDeptJk> deptList) {
		this.deptList = deptList;
	}
	public String getDeptNum() {
		return deptNum;
	}
	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}
	
	
}
