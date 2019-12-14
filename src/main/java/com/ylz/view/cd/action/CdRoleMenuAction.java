package com.ylz.view.cd.action;


import com.ylz.bizDo.cd.po.*;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;
import java.util.Set;
@SuppressWarnings("serial")
@Action(
        value="roleMenu",
        results={
        		@Result(name="list", location="/intercept/cd/role/role_list.jsp"),
        		@Result(name="edit", location="/intercept/cd/role/role_menuson.jsp"),
        		@Result(name = "json", type = "json",params={"root","jsons",
                  		 "excludeProperties","rows.*\\.rmlist,rows.*\\.cdDepts,rows.*\\.user","contentType", "text/html"}),
        		@Result(name = "jsontreelist", type = "json",params={"root","jsonList",
                   		 "excludeProperties","rows.*\\.rmlist,rows.*\\.cdDepts,rows.*\\.user","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json",params={"root","jsonVo",
              	     "excludeProperties","rmlist,cdDepts","contentType", "text/html"})
        }
        )
public class CdRoleMenuAction extends CommonAction {
	private List<CdRole> rolelist;//角色集合
	private Set<String> menuck;//主菜单的id
	private List<String> sonck;//子菜单的id(权限)
	private CdRole role;//角色
	private CdRoleMenu rm;//角色主菜单
	private CdRoleMenuSon rs;//角色权限
	private List<CdMenu> menulist;//所以功能
	private List<CdRoleMenu> rmslist;//根据角色获取功
	private List<CdRoleMenuSon>  rsslist;//选中的权限

	/**
	 * 准备新增或修改
	 * @return
	 */
	public String forAddOrEdit(){
		return "edit";
	}
	
	
	/**
	 * 添加
	 * @return
	 */
	public String addOrEdit() {
	try{
		String[] mids=null;
		String[] sids=null;
		String menuids = this.getRequest().getParameter("mids");
		String menusonids = this.getRequest().getParameter("msids");
		String rid = this.getRequest().getParameter("rid");
		CdRole role = (CdRole)sysDao.getServiceDo().find(CdRole.class, rid);
		if(StringUtils.isNotBlank(menuids)){
			mids=menuids.split(",");
		}
		if(StringUtils.isNotBlank(menusonids)){
			sids=menusonids.split(",");
		}
		if(mids!=null && mids.length>0){
			List<CdRoleMenu> ls = sysDao.getRoleMenuDo().findRidList(rid);
			role.setRmlist(null);
			sysDao.getServiceDo().saveUpdate(role);
			if(ls != null && ls.size() >0){
				for(CdRoleMenu v : ls){
					sysDao.getServiceDo().delete(v);
				}
			}
			for(String m:mids){
				CdRoleMenu cm=new CdRoleMenu();
				CdMenu menu = (CdMenu)sysDao.getServiceDo().find(CdMenu.class, m);
				cm.setMid(menu);
				cm.setRid(role);
				CdUser us= this.getSessionUser();
				if(us!=null){
					cm.setCjrId(us.getUserId());//创建人id
					cm.setXgrId(us.getUserId());//修改人id
					if(us.getCdDept()!=null){
						cm.setCjdwId(us.getCdDept().getId());//创建人单位id
						cm.setXgdwId(us.getCdDept().getId());//修改人单位id
					}
				}
				sysDao.getServiceDo().saveUpdate(cm);
				if(sids!=null && sids.length>0){
					for(String s:sids){
						String[] is = s.split(";;;");
						if(is[0].equals(m)){
							CdRoleMenuSon sm=new CdRoleMenuSon();
							sm.setSid(is[1]);
							sm.setRmid(cm);
							if(us!=null){
								sm.setCjrId(us.getUserId());//创建人id
								sm.setXgrId(us.getUserId());//修改人id
								if(us.getCdDept()!=null){
									sm.setCjdwId(us.getCdDept().getId());//创建人单位id
									sm.setXgdwId(us.getCdDept().getId());//修改人单位id
								}
							}
							sysDao.getServiceDo().saveUpdate(sm);
						}
					
					}
				}
				
			}
			this.newMsgJson(this.finalSuccessrMsg);
		}
	} catch (Exception e) {
		e.printStackTrace();
		new ActionException(this.getClass(), e.getMessage());
		this.newMsgJson(this.finalErrorMsg);
		return "json";
	}
	
	return "json";
	}
	
	
	public List<CdRoleMenuSon> getRsslist() {
		return rsslist;
	}

	public void setRsslist(List<CdRoleMenuSon> rsslist) {
		this.rsslist = rsslist;
	}


	public Set<String> getMenuck() {
		return menuck;
	}

	public void setMenuck(Set<String> menuck) {
		this.menuck = menuck;
	}


	public List<String> getSonck() {
		return sonck;
	}
	public void setSonck(List<String> sonck) {
		this.sonck = sonck;
	}
	public CdRoleMenu getRm() {
		return rm;
	}

	public void setRm(CdRoleMenu rm) {
		this.rm = rm;
	}

	public CdRoleMenuSon getRs() {
		return rs;
	}

	public void setRs(CdRoleMenuSon rs) {
		this.rs = rs;
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
	public List<CdRole> getRolelist() {
		return rolelist;
	}
	public void setRolelist(List<CdRole> rolelist) {
		this.rolelist = rolelist;
	}

	

	
}
