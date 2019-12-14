package com.ylz.bizDo.cd.vo;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class DeptSvo {

	private String id;
	private String sid;
	private String sname;
	private String snumber;
	private String state;
	private String deptType;
	private String rid;
	private String sort;

	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSid() {
		if(StringUtils.isNotBlank(this.sid)){
			return sid;
		}
		return "";
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSnumber() {
		return snumber;
	}
	public void setSnumber(String snumber) {
		this.snumber = snumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDeptType() {
		return deptType;
	}
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	public String getRid()  throws Exception{
		String result = "";
		if(StringUtils.isNotBlank(this.id)){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			List<RoleVo> ls = dao.getRoleDo().findRoleByDeptId(id);
			if(ls != null && ls.size() >0){
				for(RoleVo p : ls){
					if(StringUtils.isBlank(result)){
						 result = p.getRname();
					}else{
						result += ","+p.getRname();
					}
				}
			}
		}
		return result;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
  //根据deptType获取部门类型名称
  	public String getDeptTypeName() throws Exception{
  		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
  		if(StringUtils.isNotBlank( this.getDeptType())){
  			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_DEPTTYPE, this.getDeptType());
  	  		if(value!=null) {
                return value.getCodeTitle();
            }
  		}
  		return "";
  	}
  	//根据state获取部门状态名称
  	public String getStateName() throws Exception{
  		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
  		if(StringUtils.isNotBlank( this.getState())){
  			CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE, this.getState());
  	  		if(value!=null) {
                return value.getCodeTitle();
            }
  		}
  		return "";
  	}
 
	public String getResultDept(){
		return this.id+";;;"+this.sname;
		
	}
}
