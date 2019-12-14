package com.ylz.view.cd.action;


import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdMenu;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.cd.vo.MenuTwoVo;
import com.ylz.bizDo.cd.vo.SysCommQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@SuppressWarnings("serial")
@Action(
        value="menu",
        results={
        		@Result(name="list", location="/intercept/cd/menu/menu_list.jsp"),
        		@Result(name="edit", location="/intercept/cd/menu/menu_edit.jsp"),
        		@Result(name="add", location="/intercept/cd/menuSon/power.jsp"),
        		@Result(name = "jsonVo", type = "json",params={"root","jsonVo",
		        		"excludeProperties","pid,slist,sonlist,rmlist,children","contentType", "text/html"}),
                @Result(name = "json", type = "json",params={"root","jsons","excludeProperties","rows.*\\.slist,rows.*\\.sonlist,rows.*\\.rmlist,rows.*\\.children,rows.*\\.pid","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","excludeProperties",".*\\.slist,.*\\.sonlist,.*\\.rmlist","contentType", "text/html"}),
				@Result(name = "menujson", type = "json",params={"root","jsons","contentType", "text/html"}),
        }
)
public class CdMenuAction extends CommonAction{
	


	
	
	private String addson;//用来判断添加是否为子菜单
	private List<CdMenu> sonlist;//所以得菜单
	private CdMenu menu;//菜单
	private CdMenu menu1;
	private List<CdMenu> menulist;//所以菜单集合
    
	public String jsonByOne(){
		   String id = this.getRequest().getParameter("id");
		   this.setJsonVo((CdMenu) sysDao.getServiceDo().find(CdMenu.class, id));
	       return "jsonVo";
	}
	/**
	 * 树结构查询
	 * @return
	 */
    public String jsonTreelist() {
    	try{
			this.setJsonList(this.sysDao.getMenuDo().top());
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
		return "jsontreelist";
    }
    public String jsontreeList(){
    	try{
			this.setJsonList(this.sysDao.getMenuDo().findStateTrue());
			//this.setJsonList(this.sysDao.getMenuDo().findSByState());
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
		return "jsontreelist";
    }
    public String jsonlist(){
    	try{
			this.setJsonList(this.sysDao.getMenuDo().findSByState());
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
		return "jsontreelist";
    }
    /**
     * 查询菜单
     * @return
     */
    public String findMenulist(){
    	try{
			this.setJsonList(this.sysDao.getMenuDo().findMenuList());
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
	    return "jsontreelist";
	}

	/**
	 * 查询菜单(一级)
	 * @return
	 */
	public String findCmmTopMenulist(){
		try {
			CdUser user = getSessionUser();
			AppHospDept hosp = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, getSessionUser().getHospId());
			if (hosp != null) {
				this.getJsons().setVo(hosp.getHospAreaCode());
			} else {
				this.getJsons().setVo(getSessionUser().getCdDept().getArea());
			}
			this.getJsons().setRows(this.sysDao.getMenuDo().findTopMenulist(getSessionUser().getUserId()));
		} catch (Exception e) {
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
		return "menujson";
	}

	/**
	 * 查询二级菜单
	 * @return
	 */
	public String findTwoList(){
		try {
			SysCommQvo qvo = (SysCommQvo)getJson(SysCommQvo.class);
			List<MenuTwoVo> ls=sysDao.getMenuDo().findTwoListAc(qvo.getId(),getSessionUser().getUserId());
			getJsons().setRows(ls);
		} catch (Exception e) {
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
		return "menujson";
	}

    /**
     * 查询
     * @return
     */
	public String list() {
		try{
			this.setJsonList(this.sysDao.getMenuDo().findStateTrue());
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}

        return "jsontreelist";
    }
    
	/**
	 * 查询所有
	 * @return
	 */
	public String forList(){
		try{
			this.setMenulist(this.sysDao.getMenuDo().selectAll());
			return "list";
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
	}
	/**
	 * 准备新增或者修改
	 * @return
	 */
	public String forAddOrEdit(){
		return "edit";
	}
	/**
	 *准备添加功能 
	 */
	public String forAdd(){
		return "add";
	}
    /**
     * 添加保存
     * @return
     */
	public String add() {
		try {
			CdMenu menu = (CdMenu)getVoJson(CdMenu.class);
			menu.setId(null);
			if(StringUtils.isBlank(menu.getPid().getId())){
				menu.setPid(null);
	          }
			CdUser us= this.getSessionUser();
			if(us!=null){
				menu.setCjrId(us.getUserId());//创建人id
				menu.setXgrId(us.getUserId());//修改人id
				menu.setXgsj(new Date());//修改时间
	        	if(us.getCdDept()!=null){
	        		menu.setCjdwId(us.getCdDept().getId());//创建人单位id
	        		menu.setXgdwId(us.getCdDept().getId());//修改人单位id
	        	}
	        }
			this.sysDao.getServiceDo().add(menu);
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
	 * 修改
	 * @return
	 */
	public String modify() {
		try{
		    //修改
			CdMenu menu = (CdMenu)getVoJson(CdMenu.class);
            menu.setSlist(this.sysDao.getMenuDo().finRM(menu.getId().toString()));
            if(StringUtils.isBlank(menu.getPid().getId())){
				menu.setPid(null);
	        }
            CdMenu me=(CdMenu) this.sysDao.getServiceDo().find(CdMenu.class,menu.getId());
            menu.setCjrId(me.getCjrId());
            menu.setCjdwId(me.getCjdwId());
            CdUser us= this.getSessionUser();
            if(us!=null){
            	menu.setXgrId(us.getUserId());//修改人id
            	menu.setXgsj(new Date());//修改时间
	        	if(us.getCdDept()!=null){
	        		menu.setXgdwId(us.getCdDept().getId());//修改人单位id
	        	}
	        }
            //session出现实体重复时的处理
			this.sysDao.getServiceDo().removePoFormSession(me);
            this.sysDao.getServiceDo().modify(menu);

        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        this.newMsgJson(finalSuccessrMsg);
        return "json";
	}
	/**.delete(CdMenu.class,id);
            }else{
                this.newMsgJson("请先删除子菜单");
                return "json";
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
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
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
		}
		return "json";
	}
	
	public CdMenu getMenu() {
		return menu;
	}
	public void setMenu(CdMenu menu) {
		this.menu = menu;
	}
	public List<CdMenu> getMenulist() {
		return menulist;
	}
	public void setMenulist(List<CdMenu> menulist) {
		this.menulist = menulist;
	}
	public String getAddson() {
		return addson;
	}
	public void setAddson(String addson) {
		this.addson = addson;
	}
	public List<CdMenu> getSonlist() {
		return sonlist;
	}
	public void setSonlist(List<CdMenu> sonlist) {
		this.sonlist = sonlist;
	}
	public CdMenu getMenu1() {
		return menu1;
	}
	public void setMenu1(CdMenu menu1) {
		this.menu1 = menu1;
	}
}
