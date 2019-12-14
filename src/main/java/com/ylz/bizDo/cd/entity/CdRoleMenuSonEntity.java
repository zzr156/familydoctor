package com.ylz.bizDo.cd.entity;

public class CdRoleMenuSonEntity {
	private String id;
	private String mid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMid() {
		return mid+";;;"+id;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}
	
	
}
