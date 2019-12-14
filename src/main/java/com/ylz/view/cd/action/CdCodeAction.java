package com.ylz.view.cd.action;


/**
 * 基础数据管理
 */

import com.ylz.bizDo.cd.entity.CdCodeEntity;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.vo.CdCodeQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.exception.DaoException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Action(
		value="code",
		results={
				@Result(name="list", location="/intercept/cd/code/code_list.jsp"),
				@Result(name="listJs", location="/intercept/cd/cdJs/code_js_list.jsp"),
				@Result(name="edit", location="/intercept/cd/code/code_edit.jsp"),
				@Result(name="editJs", location="/intercept/cd/cdJs/code_js_edit.jsp"),
				@Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
				@Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
				@Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
		}
)
public class CdCodeAction extends CommonAction {
	

	private static final long serialVersionUID = 1L;
	private List<CdCode> list;
	private CdCode vo;
	private CdCodeQvo qvo;

	/**
	 * 根据组别,状态查询下拉
	 * @return
	 */
	public String jsonTreelist() {
		try{
			String group = this.getRequest().getParameter("group");
			this.setJsonList(this.getSysDao().getCodeDao().findGroupList(group, "1"));
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
		return "jsontreelist";
	}
	
	/**
	 * 查询单条记录
	 * @return
	 */
	public String jsonByOne(){
		   String id = this.getRequest().getParameter("id");
		   this.setJsonVo((CdCode) sysDao.getServiceDo().find(CdCode.class, id));
	       return "jsonVo";
	}
	
	/**
	 * 查询组名
	 * @return
	 */
	public String findCmmCodeGroup(){
		try{
			String strJson = this.getRequest().getParameter("strJson");
			List<CdCodeEntity> ls = this.getSysDao().getCodeDao().findCmmCodeGroup(strJson);
			if(ls != null && ls.size() >0){
				this.setJsonList(ls);
			}
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
	     return "jsontreelist";
	}
	
	/**
	 * 准备新增或者修改
	 * @return
	 */
	public String forAddOrEdit(){
		return "edit";
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
			CdCodeQvo qvo = (CdCodeQvo)getQvoJson(CdCodeQvo.class);
			List<CdCode> ls = sysDao.getCodeDao().findGroupListQvo(qvo);
			jsons.setRowsQvo(ls,qvo);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
		}
		return "json";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String add() {
		try {
			CdCode vo = (CdCode)getVoJson(CdCode.class);
			if (vo != null) {
				sysDao.getServiceDo().add(vo);
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
			CdCode vo = (CdCode)getVoJson(CdCode.class);
			if (vo != null) {
				CdCode code = (CdCode) sysDao.getServiceDo().find(CdCode.class,vo.getId());
				code.setCodeGroup(vo.getCodeGroup());//组名
				code.setCodeTitle(vo.getCodeTitle());//名称
				code.setCodeValue(vo.getCodeValue());//值
				code.setCodeSort(vo.getCodeSort());//排序
				code.setCodeState(vo.getCodeState());//状态
				code.setCodeRemark(vo.getCodeRemark());//备注
				this.sysDao.getServiceDo().removePoFormSession(code);
	            this.sysDao.getServiceDo().modify(code);
				this.newMsgJson(finalSuccessrMsg);
			}else{
				this.newMsgJson(finalErrorMsg);
			}
		}catch (DaoException e) {
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
			String[] ids = id.split(";");
			if(ids != null && ids.length >0){
				for(String s : ids){
					sysDao.getServiceDo().delete(CdCode.class,s);
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
	
	public List<CdCode> getList() {
		return list;
	}

	public void setList(List<CdCode> list) {
		this.list = list;
	}

	public CdCode getVo() {
		return vo;
	}

	public void setVo(CdCode vo) {
		this.vo = vo;
	}

	public CdCodeQvo getQvo() {
		return qvo;
	}

	public void setQvo(CdCodeQvo qvo) {
		this.qvo = qvo;
	}

	/**
	 * 准备新增或者修改
	 * @return
	 */
	public String forAddOrEditJs(){
		return "editJs";
	}

	/**
	 * 查询全部
	 * @return
	 */
	public String forListJs() {
		return "listJs";
	}
	/**
	 * 查询全部
	 * @return
	 */
	public String listJs() {
		try{
			CdCodeQvo qvo = (CdCodeQvo)getQvoJson(CdCodeQvo.class);
			List<CdCode> ls = sysDao.getCodeDao().findJsGroupListQvo(qvo);
			jsons.setRowsQvo(ls,qvo);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
		}
		return "json";
	}

	/**
	 * 添加
	 * @return
	 */
	public String addJs() {
		try {
			CdCode vo = (CdCode)getVoJson(CdCode.class);
			if (vo != null) {
				vo.setCodeGroup("jsAdmin");
				vo.setCodeRemark("计生核查员");
				sysDao.getServiceDo().add(vo);
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
	public String modifyJs() {
		try {
			CdCode vo = (CdCode)getVoJson(CdCode.class);
			if (vo != null) {
				CdCode code = (CdCode) sysDao.getServiceDo().find(CdCode.class,vo.getId());
				code.setCodeTitle(vo.getCodeTitle());//姓名
				code.setCodeValue(vo.getCodeValue());//手机号
				code.setCodeSort(vo.getCodeSort());//排序
				code.setCodeState(vo.getCodeState());//状态
				this.sysDao.getServiceDo().removePoFormSession(code);
				this.sysDao.getServiceDo().modify(code);
				this.newMsgJson(finalSuccessrMsg);
			}else{
				this.newMsgJson(finalErrorMsg);
			}
		}catch (DaoException e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		return "json";
	}
}