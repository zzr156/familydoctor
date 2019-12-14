package com.ylz.view.cd.action;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdDissolutionWarning;
import com.ylz.bizDo.cd.po.CdMessage;
import com.ylz.bizDo.cd.vo.CdDissolutionWarningVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Action(value = "message", results = {
		@Result(name = "list", location = "/intercept/cd/message/message_list.jsp"),
		@Result(name = "json", type = "json",params={"root","jsons","contentType", "text/html"})
})
public class CdMessageAction extends CommonAction {
	private CdMessage vo;
	
	
	//消息管理菜单链接
    public String forList() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return "list";
    }
    /**
     * 查询数据
     */
    public String list(){
    	try{
			List<CdMessage> ls = sysDao.getCdMessageDao().find();
			jsons.setVo(ls);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
		}
		return "json";
    }
    /**
     * 保存
     * @return
     */
    public String add(){
    	try{
    		CdMessage vo = (CdMessage)getVoJson(CdMessage.class);
    		CdMessage cdMessage = (CdMessage) this.sysDao.getServiceDo().find(CdMessage.class,vo.getId());
    		if(cdMessage!=null){
    			//session出现实体重复时的处理
    			this.sysDao.getServiceDo().removePoFormSession(cdMessage);
    			this.sysDao.getServiceDo().modify(vo);
    		}else{
    			this.sysDao.getServiceDo().add(vo);
    		}
        	this.newMsgJson(this.finalSuccessrMsg);
    	}catch(Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
    	}
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
    
    
	public CdMessage getVo() {
		return vo;
	}
	public void setVo(CdMessage vo) {
		this.vo = vo;
	}

	/**
	 * 获取当前机构下医生的解约预警设置
	 * WangCheng
	 * @return
	 */
	public String getDissolutionWarning(){
		try {
			CdDissolutionWarningVo vo = (CdDissolutionWarningVo)getVoJson(CdDissolutionWarningVo.class);
			if(vo != null){
				CdDissolutionWarning ls = sysDao.getCdMessageDao().getDissolutionWarning(vo);
				jsons.setVo(ls);
			}else {
				this.jsons.setMsg("参数为空！");
				this.jsons.setCode("1001");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "json";
	}

	/**
	 * 解约预警保存
	 * WangCheng
	 * @return
	 */
	public String addDissolutionWarning(){
		try {
			CdDissolutionWarning dissolutionWarning = (CdDissolutionWarning)getVoJson(CdDissolutionWarning.class);
			if(StringUtils.isNotEmpty(dissolutionWarning.getId())){
				CdDissolutionWarning dissolutionWarningOld = (CdDissolutionWarning)sysDao.getServiceDo().find(CdDissolutionWarning.class,dissolutionWarning.getId());
				dissolutionWarningOld.setRedWarningDay(dissolutionWarning.getRedWarningDay());
				dissolutionWarningOld.setRedWarningState(dissolutionWarning.getRedWarningState());
				dissolutionWarningOld.setYellowWarningDay(dissolutionWarning.getYellowWarningDay());
				dissolutionWarningOld.setYellowWarningState(dissolutionWarning.getYellowWarningState());
				dissolutionWarningOld.setGreenWarningDay(dissolutionWarning.getGreenWarningDay());
				dissolutionWarningOld.setGreenWarningState(dissolutionWarning.getGreenWarningState());
				this.sysDao.getServiceDo().modify(dissolutionWarningOld);
			}else {
				sysDao.getServiceDo().add(dissolutionWarning);
			}
			this.newMsgJson(this.finalSuccessrMsg);
		}catch (Exception e){
			e.printStackTrace();
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		return "json";
	}

}
