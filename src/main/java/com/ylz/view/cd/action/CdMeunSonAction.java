package com.ylz.view.cd.action;


import com.ylz.bizDo.cd.po.CdMenu;
import com.ylz.bizDo.cd.po.CdMenuSon;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.Constant;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
@Action(
        value="son",
        results={
                @Result(name="add", location="/intercept/cd/menuSon/power.jsp"),
                @Result(name="delete",location="/intercept/cd/menuSon/power.jsp"),
                @Result(name="list",location="/intercept/cd/menuSon/power.jsp"),
                @Result(name = "json", type = "json",params={"root","jsons","excludeProperties",".*\\.mid","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","excludeProperties",".*\\.mid","contentType", "text/html"})
        }
        )
public class CdMeunSonAction extends CommonAction {
	private CdMenuSon son;//接收页面输入
	private List<CdMenuSon> lists;//查询
	private List<CdMenu> sonlist;//所以得菜单
	private CdMenu menu;

	 //查询全部
	 public String list() {
		menu=(CdMenu) this.sysDao.getServiceDo().find(CdMenu.class,this.getMenu().getId());
		son=null;
		return "list";
	}

	 /**
	   * 查询菜单
	   * @return
     */
    public String findMenuSonId(){
    	try{
			String id = this.getRequest().getParameter("mid");
			this.setJsonList(this.sysDao.getSonDo().findMenuSonId(id));
			return "jsontreelist";
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}

	}
    
	/**
	 * 添加
	 * @return
	 */
	public String add() {
		try{
			/*if(isvers("addTime"))
				this.newMsgJson(finalErrorMsg);*/
			CdMenuSon son = (CdMenuSon)getVoJson(CdMenuSon.class);
			menu=(CdMenu) this.sysDao.getServiceDo().find(CdMenu.class,son.getMid().getId());
			son.setMid(menu);
			if(menu.getPid()!=null){
				sonlist=this.sysDao.getMenuDo().selectAll();
			}
			CdUser us= this.getSessionUser();
			if(us!=null){
				son.setCjrId(us.getUserId());//创建人id
				son.setXgrId(us.getUserId());//修改人id
	        	if(us.getCdDept()!=null){
	        		son.setCjdwId(us.getCdDept().getId());//创建人单位id
	        		son.setXgdwId(us.getCdDept().getId());//修改人单位id
	        	}
	        }
			this.sysDao.getServiceDo().add(son);
		}catch(Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		this.newMsgJson(finalSuccessrMsg);
		return "json";
	}
    /**
     * 删除
     * @return
     */
	public String delete() {
		try{
			String id = this.getRequest().getParameter("id");
			String mid = this.getRequest().getParameter("mid");
			/*if(delisvers("menu_del",id))
				this.newMsgJson(finalErrorMsg);*/
			CdMenu cd = (CdMenu)this.sysDao.getServiceDo().find(CdMenu.class,mid);
			if(cd.getPid()!=null){
				sonlist=this.sysDao.getMenuDo().selectAll();
			}
			this.sysDao.getServiceDo().delete(CdMenuSon.class,id);
		}catch(Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		this.newMsgJson(finalSuccessrMsg);
		return "json";
	}

	public String findCmmMenuSonRole(){
		try{
			String loginMenuId = this.getRequest().getParameter("loginMenuId");
			// 获取用户
			CdUser user = (CdUser) this.getRequest().getSession(true).getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
			if (user == null) {
				return com.opensymphony.xwork2.Action.LOGIN;
			}
			List<CdMenuSon> son = new ArrayList<CdMenuSon>() ;
			if(user.getRole()!=null&&user.getRole().size()>0){
				String[] rids=new String[user.getRole().size()];
				for (int i = 0; i <user.getRole().size(); i++) {
					rids[i]=user.getRole().get(i).getId();
				}
				if(rids!=null&&rids.length>0){
					List<CdMenuSon> sonList = this.getSysDao().getSonDo().findSonRoId(rids, loginMenuId);
					for(CdMenuSon i : sonList){
						son.add(i);
					}
				}
			}
			if(user.getCdDept().getRoles() != null && user.getCdDept().getRoles().size() >0){
				String[] rids=new String[user.getCdDept().getRoles().size()];
				for (int i = 0; i <user.getCdDept().getRoles().size(); i++) {
					rids[i] = user.getCdDept().getRoles().get(i).getId();
				}
				if(rids != null && rids.length>0){
					List<CdMenuSon> sonList =this.sysDao.getSonDo().findSonRoId(rids, loginMenuId);
					for(CdMenuSon i : sonList){
						son.add(i);
					}
				}
			}
			this.setJsonList(son);
			return "jsontreelist";
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
	}
	
	public CdMenuSon getSon() {
		return son;
	}

	public void setSon(CdMenuSon son) {
		this.son = son;
	}

	public List<CdMenuSon> getLists() {
		return lists;
	}

	public void setLists(List<CdMenuSon> lists) {
		this.lists = lists;
	}
	public CdMenu getMenu() {
		return menu;
	}
	public void setMenu(CdMenu menu) {
		this.menu = menu;
	}
	public List<CdMenu> getSonlist() {
		return sonlist;
	}
	public void setSonlist(List<CdMenu> sonlist) {
		this.sonlist = sonlist;
	}
	
	
}
