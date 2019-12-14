package com.ylz.bizDo.cd.vo;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;



public class DeptVo {
	
	private String id;//部门编码
	private String deptId;//部门id
	private String text;//树名称
	private List<DeptVo> children;//树
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public List<DeptVo> getChildren() {
		return children;
	}
	public void setChildren(String children) throws Exception {
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		if(StringUtils.isNotBlank(this.getDeptId())){
			this.children = dao.getCdDeptDao().findParentId(this.getDeptId());
				
		}else{
			this.children = new ArrayList<DeptVo>();
		}
	}
	
	
	
	

	
}
