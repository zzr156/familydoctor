package com.ylz.bizDo.cd.vo;

import org.apache.commons.lang.StringUtils;

public class MenuSvo {
	private String id;
	private String pid;
	private String mname;
	private Integer state;
	private Integer onumber;
	private String address;
	private String menuIcon;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		if(StringUtils.isNotBlank(pid)) {
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getOnumber() {
		return onumber;
	}
	public void setOnumber(Integer onumber) {
		this.onumber = onumber;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public String getResultMenu(){
		return this.id+";;;"+this.mname;
		
	}
}
