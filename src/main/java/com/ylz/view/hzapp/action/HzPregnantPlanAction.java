package com.ylz.view.hzapp.action;


import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppPregnantPlan;
import com.ylz.bizDo.jtapp.patientEntity.AppPregnantEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPregnantPlanQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 孕产妇保健计划action.
 */
@SuppressWarnings("all")
@Action(
        value="hzPlan",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class HzPregnantPlanAction extends CommonAction {



    /**
     * 新增孕产妇体检计划
     * @param  type --1预产日期， 2末次月经日期
     * @param  date --"2018-06-01" --输入日期
     * @return
     */
    public String appPregnantPlan(){
        try {
            AppPregnantPlanQvo vo = (AppPregnantPlanQvo)getAppJson(AppPregnantPlanQvo.class);
            AppPatientUser user = getAppPatientUser();
            if(vo != null && user!=null){
                List<AppPregnantEntity> list = sysDao.getAppPregnantPlanDao().findPlan(user.getId(),vo.getType(),vo.getDate());
                if(list!=null){
                    for (int i = 0; i <list.size() ; i++) {
                        if(new Date().getTime()>(list.get(i).getPlanDate().getTime())){
                            list.get(i).setState(CommonEnable.QIYONG.getValue());
                        }
                    }
                    this.getAjson().setRows(list);
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("系统错误");
                }

            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 修改孕产妇体检计划
     * @param  planId --保健计划id
     * @param  state --1已体检 0未体检
     * @param  date --修改保健计划时间
     * @return
     */
    public String appModifyPregnantPlan(){
        try {
            AppPregnantPlanQvo vo = (AppPregnantPlanQvo)getAppJson(AppPregnantPlanQvo.class);
            if(vo != null){
                AppPregnantPlan plan = (AppPregnantPlan) sysDao.getServiceDo().find(AppPregnantPlan.class,vo.getPlanId());
                if(StringUtils.isNotBlank(vo.getState())){
                    plan.setPpState(vo.getState());
                }
                if(StringUtils.isNotBlank(vo.getDate())){
                    plan.setPpPlanDate(ExtendDate.getCalendar(vo.getDate()));
                }
                sysDao.getServiceDo().modify(plan);
                AppPregnantEntity entity = new AppPregnantEntity();
                entity.setId(plan.getId());
                entity.setPlanTitle(plan.getPpPlanTitle());
                entity.setPlanDate(plan.getPpPlanDate().getTime());
                entity.setState(plan.getPpState());
                this.getAjson().setVo(entity);
                this.getAjson().setMsgCode("800");
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询当前用户孕产妇保健计划
     * @return
     */
    public String appFindPregnantPlan(){
        try {
            AppPatientUser user = getAppPatientUser();
            if(user!=null){
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("userId",user.getId());
                String sql = "SELECT ID id, PP_PLAN_DATE planDate, PP_PLAN_TITLE planTitle, PP_STATE state FROM APP_PREGNANT_PLAN WHERE PP_USER_ID = :userId  ORDER BY PP_PLAN_DATE ";
                List<AppPregnantEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPregnantEntity.class);
                for (int i = 0; i <list.size() ; i++) {
                    if(new Date().getTime()>(list.get(i).getPlanDate().getTime())){
                        list.get(i).setState(CommonEnable.QIYONG.getValue());
                    }
                }
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 保健提醒天数设置
     */
    public String appHealthCareSetting(){
        try {
            AppPregnantPlanQvo vo = (AppPregnantPlanQvo)getAppJson(AppPregnantPlanQvo.class);
            if(vo != null){
                sysDao.getAppPregnantPlanDao().setHealthCare(vo);
                this.getAjson().setMsg("设置成功");
                this.getAjson().setMsgCode("800");
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 删除当前用户孕产妇保健计划
     * @return
     */
    public String appDeletePregnantPlan(){
        try {
            AppPatientUser user = getAppPatientUser();
            if(user!=null){
                List<AppPregnantPlan> plans = sysDao.getServiceDo().loadByPk(AppPregnantPlan.class,"ppUserId",user.getId());
                if(plans!=null && plans.size()>0){
                    for (AppPregnantPlan p : plans) {
                        sysDao.getServiceDo().delete(p);
                    }
                }
                this.getAjson().setMsg("删除成功");
                this.getAjson().setMsgCode("800");
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }





}
