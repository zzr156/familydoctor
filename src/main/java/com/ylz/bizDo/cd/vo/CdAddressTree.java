package com.ylz.bizDo.cd.vo;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdAddressTree {
	
	private String id;
	private String ctcode;
	private String areaName;
	private String areaSname;
	private List<CdAddressTree> children;

	public List<CdAddressTree> getChildren() {
		try {
			SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");
			Map<String,Object> map=new HashMap<String,Object>();
			//List<CdAddressTree> ls=sysDao.getCdAddressDao().findTop(getId());
//			if(ls!=null && ls.size()>0){
//				return ls;
//			}
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public void setChildren(String children) {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
