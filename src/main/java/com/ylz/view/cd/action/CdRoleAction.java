package com.ylz.view.cd.action;


import com.ylz.bizDo.cd.po.*;
import com.ylz.bizDo.cd.vo.RoleVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("serial")
@Action(
        value="role",
        results={
        		@Result(name="list", location="/intercept/cd/role/role_list.jsp"),
                @Result(name="edit", location="/intercept/cd/role/role_edit.jsp"),
                @Result(name="modify", location="/intercept/cd/role/role_modify.jsp"),
                @Result(name = "json", type = "json",params={"root","jsons",
               		 "excludeProperties","rows.*\\.rmlist,rows.*\\.cdDepts,rows.*\\.user","contentType", "text/html"}),
    		    @Result(name = "jsontreelist", type = "json",params={"root","jsonList",
               		 "excludeProperties","rows.*\\.rmlist,rows.*\\.cdDepts,rows.*\\.user","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json",params={"root","jsonVo",
              	     "excludeProperties","rmlist,cdDepts,user","contentType", "text/html"})
        }
       )
public class CdRoleAction extends CommonAction{
	

	private List<CdRole> rolelist;//角色集合
	private CdRole role;//角色
	private RoleVo qvo;//角色查询
	private List<CdMenu> menulist;//所以功能
	private List<CdRoleMenu> rmslist;//根据角色获取功
	private List<CdRoleMenuSon>  rsslist;//选中的权限

	public String jsonTreelist() {
		try{
			this.setJsonList(this.sysDao.getRoleDo().findRoleAll());
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
		return "jsontreelist";
	}
	
	public String jsonByOne(){
		   String id = this.getRequest().getParameter("id");
		   this.setJsonVo((CdRole) sysDao.getServiceDo().find(CdRole.class, id));
	       return "jsonVo";
	}
	
	//查询全部
	public String forList() {
		  if(qvo == null) {
              qvo = new RoleVo();
          }
	        return "list";
	}
	
	public String list() {
		try{
			RoleVo qvo = (RoleVo)getQvoJson(RoleVo.class);
			List<CdRole> ls = this.sysDao.getRoleDo().findRole(qvo);
			jsons.setRowsQvo(ls,qvo);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
		}
		return "json";
	}
	
	//准备添加
	public String forAddOrEdit() {
		return "edit";
		
	}
	
	public String findCmmRoleMenu(){
		try{
			String id = this.getRequest().getParameter("id");
			this.setJsonList(this.sysDao.getRoleDo().findRoleMenu(id));
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
	    return "jsontreelist";
	}
	
	public String findCmmRoleMenuSon(){
		try{
			String id = this.getRequest().getParameter("id");
			this.setJsonList(this.sysDao.getRoleDo().findRoleMenuSon(id));
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
	    return "jsontreelist";
	}
	
	
	//添加
	public String add() {
		try {
			CdRole role = (CdRole)getVoJson(CdRole.class);
			if(role != null){
				CdUser us= this.getSessionUser();
				if(us!=null){
					role.setCjrId(us.getUserId());//创建人id
					role.setXgrId(us.getUserId());//修改人id
					if(us.getCdDept()!=null){
						role.setCjdwId(us.getCdDept().getId());//创建人单位id
						role.setXgdwId(us.getCdDept().getId());//修改人单位id
					}
				}
				this.sysDao.getServiceDo().add(role);
				this.newMsgJson(this.finalSuccessrMsg);
			}else{
				this.newMsgJson(finalErrorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
			return "json";
		}
		
		return "json";
	}
	
	//修改
	public String modify() {
		try {
			CdRole role = (CdRole)getVoJson(CdRole.class);
			if(role != null){
				CdRole v = (CdRole) this.sysDao.getServiceDo().find(CdRole.class,role.getId());
				role.setCjrId(v.getCjrId());
				role.setCjdwId(v.getCjdwId());
				CdUser us= this.getSessionUser();
				if(us!=null){
					role.setXgrId(us.getUserId());//修改人id
					if(us.getCdDept()!=null){
						role.setXgdwId(us.getCdDept().getId());//修改人单位id
					}
				}
				role.setRmlist(v.getRmlist());
				role.setCdDepts(v.getCdDepts());
				role.setUser(v.getUser());
				this.sysDao.getServiceDo().removePoFormSession(v);
				this.sysDao.getServiceDo().modify(role);
				this.newMsgJson(this.finalSuccessrMsg);
			}else{
				this.newMsgJson(finalErrorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
			return "json";
		}
		
		return "json";
	}
	
	//删除
	public String delete() {
		try {
			String id = this.getRequest().getParameter("id");
			String[] ids = id.split(";");
			if(ids != null && ids.length >0){
				for(String s : ids){
					role = (CdRole) sysDao.getServiceDo().find(CdRole.class,s);//根据id获取角色
					if(role.getCdDepts().size()<1 && role.getUser().size()<1){
						CdRoleMenu m = new CdRoleMenu();
						m.setRid(role);
						this.sysDao.getServiceDo().delete(CdRole.class,s);
					}else{
						this.newMsgJson("此"+role.getRname()+"的角色正在使用,不能执行删除操作!");
						return "json";
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		this.newMsgJson(finalSuccessrMsg);
		return "json";
	}

	/**
	 * 页面初始化
	 * @return
	 */
	public String findCmmInit(){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			//状态
			List<CdCode> lsEnable = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE, CommonEnable.QIYONG.getValue());
			map.put("enable", lsEnable);
			this.getJsons().setMap(map);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
		}
		return "json";
	}
	
	public List<CdRole> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<CdRole> rolelist) {
		this.rolelist = rolelist;
	}

	public CdRole getRole() {
		return role;
	}

	public void setRole(CdRole role) {
		this.role = role;
	}
	public List<CdMenu> getMenulist() {
		return menulist;
	}
	public void setMenulist(List<CdMenu> menulist) {
		this.menulist = menulist;
	}
	public List<CdRoleMenu> getRmslist() {
		return rmslist;
	}
	public void setRmslist(List<CdRoleMenu> rmslist) {
		this.rmslist = rmslist;
	}
	public List<CdRoleMenuSon> getRsslist() {
		return rsslist;
	}
	public void setRsslist(List<CdRoleMenuSon> rsslist) {
		this.rsslist = rsslist;
	}

}
