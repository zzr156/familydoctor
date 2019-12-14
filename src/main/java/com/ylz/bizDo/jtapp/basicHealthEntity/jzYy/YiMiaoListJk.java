package com.ylz.bizDo.jtapp.basicHealthEntity.jzYy;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class YiMiaoListJk {
	private String ylName;//月龄名称
	private List<YiMiaoJk> ymList;
	public String getYlName() {
		return ylName;
	}
	public void setYlName(String ylName) {
		this.ylName = ylName;
	}
	public List<YiMiaoJk> getYmList() {
		return ymList;
	}
	public void setYmList(List<YiMiaoJk> ymList) {
		this.ymList = ymList;
	}
	
}
