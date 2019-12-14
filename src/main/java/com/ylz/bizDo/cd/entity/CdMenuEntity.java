package com.ylz.bizDo.cd.entity;

import org.apache.commons.lang.StringUtils;

public class CdMenuEntity {

	private String id;
	private String pid;
	private String mname;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		if(StringUtils.isNotBlank(pid)){
			return pid;
		}
		return "";
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	
	
}
