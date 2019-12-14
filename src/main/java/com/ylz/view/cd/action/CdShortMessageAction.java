package com.ylz.view.cd.action;

import com.ylz.bizDo.cd.po.CdShortMessage;
import com.ylz.bizDo.cd.vo.CdShortMessageQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

@Action(
		value="cdsm",
		results={
				@Result(name="list", location="/intercept/cd/cdsm/cdsm_list.jsp"),
				@Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
				@Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
				@Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
		}
)
public class CdShortMessageAction extends CommonAction {


	
	private static final long serialVersionUID = 1L;
	private List<CdShortMessage> list;
	private CdShortMessage vo;
	private CdShortMessageQvo qvo;
	
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
			CdShortMessageQvo qvo = (CdShortMessageQvo)getQvoJson(CdShortMessageQvo.class);
			List<CdShortMessage> ls = sysDao.getCdShortMessageDao().findListQvo(qvo);
			jsons.setRowsQvo(ls,qvo);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
		}
		return "json";
	}
	
	
	public List<CdShortMessage> getList() {
		return list;
	}
	public void setList(List<CdShortMessage> list) {
		this.list = list;
	}
	public CdShortMessage getVo() {
		return vo;
	}
	public void setVo(CdShortMessage vo) {
		this.vo = vo;
	}
	public CdShortMessageQvo getQvo() {
		return qvo;
	}
	public void setQvo(CdShortMessageQvo qvo) {
		this.qvo = qvo;
	}
}
