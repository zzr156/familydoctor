package com.ylz.view.cd.action;


/**
 * 职位管理
 */

import com.ylz.bizDo.cd.po.CdPosition;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.cd.vo.PositionQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.exception.DaoException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

@Action(
		value="position",
		results={
				@Result(name="list", location="/intercept/cd/position/position_list.jsp"),
				@Result(name = "edit", location = "/intercept/cd/position/position_edit.jsp"),
				@Result(name = "jsonVo", type = "json", params = { "root", "jsonVo",
						"excludeProperties", "user" ,"contentType", "text/html"}),
				@Result(name = "json", type = "json", params = { "root", "jsons",
						"excludeProperties", "rows.*\\.user" ,"contentType", "text/html"}),
				@Result(name = "jsontreelist", type = "json",params={"root","jsonList",
						"excludeProperties",".*\\.slist,.*\\.roles,.*\\.user","contentType", "text/html"})
		}
)
public class CdPositionAction extends CommonAction{
	

	
	private static final long serialVersionUID = 1L;
	private List<CdPosition> list;
	private CdPosition vo;
	private PositionQvo qvo;
			
	
	public String jsonTreelist() {
		try{
			this.setJsonList(this.sysDao.getPositionDao().findAll());
			return "jsontreelist";
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
	}
	
	public String jsonByOne(){
		   String id = this.getRequest().getParameter("id");
		   this.setJsonVo((CdPosition) sysDao.getServiceDo().find(CdPosition.class, id));
	       return "jsonVo";
	}
	/**
	 * 查询全部
	 * @return
	 */
	public String forList() {
		return "list";
	}
	
	/**
	 * 查询全部
	 * @return
	 */
	public String list() {
		try{
			PositionQvo qvo = (PositionQvo)getQvoJson(PositionQvo.class);
			List<CdPosition> ls = sysDao.getPositionDao().findCdPosition(qvo);
			jsons.setRowsQvo(ls,qvo);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
		}
		return "json";
	}
	
	/**
	 * 准备新增或者修改
	 * @return
	 */
	public String forAddOrEdit(){
		return "edit";
	}
	/**
	 * 添加
	 * @return
	 */
	public String add() {
		try {
			CdPosition vo = (CdPosition)getVoJson(CdPosition.class);
			if(vo != null){
				List<CdPosition> p=this.sysDao.getServiceDo().loadByPk(CdPosition.class, "num", vo.getNum());
				if(p!=null&&p.size()>0){
					this.newMsgJson("此职位已存在");
					return "json";
				}
				CdUser us= this.getSessionUser();
				if(us!=null){
					vo.setCjrId(us.getUserId());//创建人id
					vo.setXgrId(us.getUserId());//修改人id
		        	if(us.getCdDept()!=null){
		        		vo.setCjdwId(us.getCdDept().getId());//创建人单位id
		        		vo.setXgdwId(us.getCdDept().getId());//修改人单位id
		        	}
		        }
				this.sysDao.getServiceDo().add(vo);
				this.newMsgJson(finalSuccessrMsg);
			}else{
				this.newMsgJson(finalErrorMsg);
			}
		} catch (DaoException e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		return "json";
	}
	
	/**
	 * 更新
	 * @return
	 */
	public String modify() {
		try {
			CdPosition vo = (CdPosition)getVoJson(CdPosition.class);
			if(vo != null){
				CdPosition p = this.sysDao.getPositionDao().findIsPosition(vo.getNum(), vo.getId());
				if(p!=null){
					this.newMsgJson("此职位已存在!");
					return "json";
				}
				p=(CdPosition) this.sysDao.getServiceDo().find(CdPosition.class,vo.getId());
				vo.setCjrId(p.getCjrId());
				vo.setCjdwId(p.getCjdwId());
	            CdUser us= this.getSessionUser();
	            if(us!=null){
	            	vo.setXgrId(us.getUserId());//修改人id
		        	if(us.getCdDept()!=null){
		        		vo.setXgdwId(us.getCdDept().getId());//修改人单位id
		        	}
		        }
	            this.sysDao.getServiceDo().removePoFormSession(p);
				this.sysDao.getServiceDo().modify(vo);
				this.newMsgJson(finalSuccessrMsg);
			}else{
				this.newMsgJson(finalErrorMsg);
			}
		}catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		return "json";		
	}
	

	/**
	 * 批量删除
	 * @return
	 */
	public String delete() {
		try {
			String id = this.getRequest().getParameter("id");
			if(StringUtils.isNotBlank(id)){
				String[] ids = id.split(";");
				for(String i :ids){
					// 如果角色在用,不能删除
					CdPosition find = (CdPosition) sysDao.getServiceDo().find(CdPosition.class,i);
					if (find.getUser().size() < 1) {
						this.sysDao.getServiceDo().delete(CdPosition.class,i);
					} else {
						this.newMsgJson("删除失败!职位'" + find.getAccount() + "'正在被使用!");
						return "json";
					}
				}
			}
		
		} catch (DaoException e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		this.newMsgJson(finalSuccessrMsg);
		return "json";
	}
	
	
	public List<CdPosition> getList() {
		return list;
	}
	
	public void setList(List<CdPosition> list) {
		this.list = list;
	}
	
	public CdPosition getVo() {
		return vo;
	}
	
	public void setVo(CdPosition vo) {
		this.vo = vo;
	}
	public PositionQvo getQvo() {
		return qvo;
	}
	public void setQvo(PositionQvo qvo) {
		this.qvo = qvo;
	}

	
}