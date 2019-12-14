package com.ylz.view.hzapp.action;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.patientEntity.AppChildHealthPlanEntity;
import com.ylz.bizDo.jtapp.patientVo.AppChildHealthQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/22.
 */
@SuppressWarnings("all")
@Action(
        value="hzChild",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class HzChildHealthAction extends CommonAction {
    /**
     * 设置儿童保健信息体检计划
     * @return
     */
    public String findListChild(){
        try{
            AppChildHealthQvo qvo = (AppChildHealthQvo)getAppJson(AppChildHealthQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppChildHealthPlanEntity> table=this.sysDao.getAppChildHealthPlanDao().savePlan(qvo);
                if(table!=null){
                    this.getAjson().setRows(table);
                    this.getAjson().setMsgCode("800");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }



    /**
     * 保存/修改儿童计划
     * @return
     */
    public String saveHealth(){
        try{


            AppChildHealthQvo qvo = (AppChildHealthQvo)getAppJson(AppChildHealthQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppPatientUser user = this.getAppPatientUser();
                if(user!=null){
                    qvo.setUserId(user.getId());
                    this.sysDao.getAppChildHealthPlanDao().saveChildPlan(qvo);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("保存成功");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询儿童体检计划
     * @return
     */
    public String findHealth(){
        try{
            AppChildHealthQvo qvo = (AppChildHealthQvo)getAppJson(AppChildHealthQvo.class);
            String result = null;
            if(qvo != null) {
                result = qvo.getChildUserId();
            }
            AppPatientUser user = this.getAppPatientUser();
            List<AppChildHealthPlanEntity> ls = this.sysDao.getAppChildHealthPlanDao().findById(user.getId(),result);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("userId",user.getId());
            map.put("userName",user.getPatientName());
            this.getAjson().setMap(map);
            this.getAjson().setRows(ls);
            this.getAjson().setMsgCode("800");
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

}
