package com.ylz.view.hzapp.action;


import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.jtapp.commonVo.AppDrugVo;
import com.ylz.bizDo.jtapp.patientEntity.AppDrugGuideBatchEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppDrugGuideEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppDrugPlanEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppDrugReminderEntity;
import com.ylz.bizDo.jtapp.patientVo.AppDrugPlanVo;
import com.ylz.packaccede.common.action.CommonAction;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 家庭药箱action.
 */
@SuppressWarnings("all")
@Action(
        value="hzDrug",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class HzDrugAction extends CommonAction {



    /**
     * 通过用户id查询用药指导
     * @param patientId
     * @return
     */
    public String appFindDrugGuideBatch(){
        try {
            AppDrugVo vo = (AppDrugVo)getAppJson(AppDrugVo.class);
            if(vo != null){
                if(getAppDrUser()!=null){
                    vo.setDrId(getAppDrUser().getId());
                }
                List<AppDrugGuideBatchEntity> list = sysDao.getAppDrugGuideDao().findByBatch(vo);
                this.getAjson().setQvo(vo);
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
     * 通过用户batch查询用药指导
     * @param patientId
     * @return
     */
    public String appFindDrugGuide(){
                try {
                        AppDrugVo vo = (AppDrugVo)getAppJson(AppDrugVo.class);
                        if(vo != null){
                                List<AppDrugGuideEntity> list = sysDao.getAppDrugGuideDao().findByPid(vo);
                                List<AppDrugGuideBatchEntity> ls = sysDao.getAppDrugGuideDao().findByBatch(vo);
                                if(ls != null){
                                    this.getAjson().setVo(ls.get(0));
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
     * 药品存量预警设置
     * @param drugId 药品id
     * @param warnNum 预警次数
     */
    public String appDrugWarning(){
        try {
            AppDrugVo vo = (AppDrugVo)getAppJson(AppDrugVo.class);
            if(vo != null){
                AppDrugWarning warn = sysDao.getAppDrugGuideDao().setDrugWarn(getAppDrUser().getId(),vo.getDrugId(),vo.getWarningNum());
                this.getAjson().setVo(warn);
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
     * 删除药品存量预警设置
     * @param id
     */
    public String appDeleteDrugWarning(){
        try {
            AppDrugVo vo = (AppDrugVo)getAppJson(AppDrugVo.class);
            if(vo != null){
                sysDao.getServiceDo().delete(AppDrugWarning.class,vo.getId());
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("删除成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 通用药品存量预警设置
     * @param commonWarnNum 通用预警次数
     */
    public String appDrugCommonWarning(){
        try {
            AppDrugVo vo = (AppDrugVo)getAppJson(AppDrugVo.class);
            if(vo != null){
                AppDrugWarning warn = sysDao.getAppDrugGuideDao().setCommonDrugWarn(getAppDrUser().getId(),vo.getCommonWarnNum());
                this.getAjson().setVo(warn);
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
     * 查询所有用药预警设置
     */
    public String appFindWarning(){
        try {
            AppDrugVo qvo = (AppDrugVo)getAppJson(AppDrugVo.class);
            AppDrUser user = getAppDrUser();
            if(user != null && qvo!=null){
                List<AppDrugWarning> list = sysDao.getServiceDo().loadByPk(AppDrugWarning.class,"dwDrId",user.getId(),qvo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
                this.getAjson().setQvo(qvo);
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
     * 添加用药提醒
     */
    public String appDrugReminder(){
        try {
            AppDrugPlanVo qvo = (AppDrugPlanVo)getAppJson(AppDrugPlanVo.class);
            if(qvo!=null && getAppPatientUser()!=null){
                AppDrugPlan drugPlan = new AppDrugPlan();
                drugPlan.setDpPatientId(getAppPatientUser().getId());
                drugPlan.setDpState(qvo.getState());
                drugPlan.setDpRemindTime(qvo.getTime());
                if(StringUtils.isNotBlank(qvo.getMonday())){
                    drugPlan.setMonday(qvo.getMonday());
                }
                if(StringUtils.isNotBlank(qvo.getTuesday())){
                    drugPlan.setTuesday(qvo.getTuesday());
                }
                if(StringUtils.isNotBlank(qvo.getWednesday())){
                    drugPlan.setWednesday(qvo.getWednesday());
                }
                if(StringUtils.isNotBlank(qvo.getThursday())){
                    drugPlan.setThursday(qvo.getThursday());
                }
                if(StringUtils.isNotBlank(qvo.getFriday())){
                    drugPlan.setFriday(qvo.getFriday());
                }
                if(StringUtils.isNotBlank(qvo.getSaturday())){
                    drugPlan.setSaturday(qvo.getSaturday());
                }
                if(StringUtils.isNotBlank(qvo.getSunday())){
                    drugPlan.setSunday(qvo.getSunday());
                }
                drugPlan.setOnlyOne(qvo.getOnlyOne());
                drugPlan.setDpCreateTime(Calendar.getInstance());
                sysDao.getServiceDo().add(drugPlan);
                if(StringUtils.isNotBlank(qvo.getDrugId())){
                    String[] drugs = qvo.getDrugId().split(";");
                    for(String drugId:drugs){
                        AppDrugPlanExtend extend = new AppDrugPlanExtend();
                        extend.setDrugPlanId(drugPlan.getId());
                        extend.setDrugId(drugId);//用药指导ID
                        sysDao.getServiceDo().add(extend);
                    }
                }
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("添加成功");
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
     * 修改用药提醒
     */
    public String modifyDrugReminder(){
        try {
            AppDrugPlanVo qvo = (AppDrugPlanVo)getAppJson(AppDrugPlanVo.class);
            if(qvo!=null){
                AppDrugPlan drugPlan = (AppDrugPlan) sysDao.getServiceDo().find(AppDrugPlan.class,qvo.getId());
                if(StringUtils.isNotBlank(qvo.getState())){
                    drugPlan.setDpState(qvo.getState());
                }
                if(StringUtils.isNotBlank(qvo.getTime())){
                    drugPlan.setDpRemindTime(qvo.getTime());
                }

                if(StringUtils.isNotBlank(qvo.getMonday())){
                    drugPlan.setMonday(qvo.getMonday());
                }
                if(StringUtils.isNotBlank(qvo.getTuesday())){
                    drugPlan.setTuesday(qvo.getTuesday());
                }
                if(StringUtils.isNotBlank(qvo.getWednesday())){
                    drugPlan.setWednesday(qvo.getWednesday());
                }
                if(StringUtils.isNotBlank(qvo.getThursday())){
                    drugPlan.setThursday(qvo.getThursday());
                }
                if(StringUtils.isNotBlank(qvo.getFriday())){
                    drugPlan.setFriday(qvo.getFriday());
                }
                if(StringUtils.isNotBlank(qvo.getSaturday())){
                    drugPlan.setSaturday(qvo.getSaturday());
                }
                if(StringUtils.isNotBlank(qvo.getSunday())){
                    drugPlan.setSunday(qvo.getSunday());
                }
                if(StringUtils.isNotBlank(qvo.getOnlyOne())){
                    drugPlan.setOnlyOne(qvo.getOnlyOne());
                }
                sysDao.getServiceDo().modify(drugPlan);
                if(StringUtils.isNotBlank(qvo.getDrugId())){
                    List<AppDrugPlanExtend> ls = sysDao.getServiceDo().loadByPk(AppDrugPlanExtend.class,"drugPlanId",drugPlan.getId());
                    for(AppDrugPlanExtend extend:ls){
                        sysDao.getServiceDo().delete(extend);
                    }
                    String[] drugs = qvo.getDrugId().split(";");
                    for(String drugId:drugs){
                        AppDrugPlanExtend extend = new AppDrugPlanExtend();
                        extend.setDrugPlanId(drugPlan.getId());
                        extend.setDrugId(drugId);
                        sysDao.getServiceDo().add(extend);
                    }
                }
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("修改成功");
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
     * 删除用药提醒
     */
    public String deleteDrugReminder(){
        try {
            AppDrugPlanVo qvo = (AppDrugPlanVo)getAppJson(AppDrugPlanVo.class);
            if(qvo!=null){
                sysDao.getServiceDo().delete(AppDrugPlan.class,qvo.getId());
                List<AppDrugPlanExtend> ls = sysDao.getServiceDo().loadByPk(AppDrugPlanExtend.class,"drugPlanId",qvo.getId());
                for(AppDrugPlanExtend extend:ls){
                    sysDao.getServiceDo().delete(extend);
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

    /**
     * 查询用药提醒列表
     */
    public String appFindDrugReminder(){
        try {
            AppPatientUser patientUser = getAppPatientUser();
            if(patientUser!=null){
                HashMap<String,Object> map = new HashMap<>();
                map.put("patientId",patientUser.getId());
                String sql = "select ID id,'' drugName,'' drugId,DP_REMIND_TIME time,DP_STATE state," +
                        " MONDAY monday,TUESDAY tuesday,WEDNESDAY wednesday,THURSDAY thursday,FRIDAY friday,SATURDAY saturday,SUNDAY sunday,ONLY_ONE onlyOne " +
                        " from APP_DRUG_PLAN WHERE DP_PATIENT_ID = :patientId order by DP_CREATE_TIME desc ";
                List<AppDrugPlanEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map, AppDrugPlanEntity.class);
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
     * 通过用户id查询用药提醒的药品
     * @param patientId
     * @return
     */
    public String appFindRemindDrug(){
        try {
            AppDrugVo vo = (AppDrugVo)getAppJson(AppDrugVo.class);
            if(vo != null){
                List<AppDrugReminderEntity> list = sysDao.getAppDrugGuideDao().findByDrugUnique(vo);
                this.getAjson().setQvo(vo);
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




}
