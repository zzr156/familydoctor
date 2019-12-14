package com.ylz.bizDo.cd.entity;


public class CdAddressEntity {
	
	private String id;
	private String areaName;
	private String areaSname;
	private String areaState;
	private String hospName;// 医院名称
	private String areaSource;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getAreaState() {
		return areaState;
	}

	public void setAreaState(String areaState) {
		this.areaState = areaState;
	}

	public String getHospName() {
		return hospName;
	}

	public void setHospName(String hospName) {
		this.hospName = hospName;
	}

	public String getAreaSource() {
		return areaSource;
	}

	public void setAreaSource(String areaSource) {
		this.areaSource = areaSource;
	}
}
