package com.ylz.bizDo.cd.vo;

import org.apache.commons.lang.StringUtils;

public class CdAddressSvo {
	
	private String id;
	private String pid;
	private String ctcode;
	private String areaName;
	private String areaSname;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		if(StringUtils.isNotBlank(this.pid)){
			return pid;
		}
		return "";
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getCtcode() {
		return ctcode;
	}
	public void setCtcode(String ctcode) {
		this.ctcode = ctcode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaSname() {
		return areaSname;
	}
	public void setAreaSname(String areaSname) {
		this.areaSname = areaSname;
	}
	public String getResultAddr(){
		return this.id+";;;"+this.pid+";;;"+this.ctcode+";;;"+this.areaName+";;;"+this.areaSname;
	}
}
