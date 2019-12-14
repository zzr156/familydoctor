package com.ylz.view.common.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationCountEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationDrEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationViewEntity;
import com.ylz.bizDo.jtapp.commonVo.AppDrEvaluationQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.EvaluationType;
import com.ylz.packcommon.common.util.MyMathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Action(
        value = "appEvaluation",
        results = {
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"})
        }
)
public class AppDrEvaluationAction extends CommonAction{

    private static final String anonymous = "匿名";

    /**
     * 添加评价
     * @return
     */
        public String appAddEvaluation() {
        try {
            AppPatientUser user = this.getAppPatientUser();
            AppDrEvaluationQvo vo = (AppDrEvaluationQvo) this.getAppJson(AppDrEvaluationQvo.class);
            if (vo != null) {
                if(user == null){
                    AppPatientUserEntity userEntity = sysDao.getAppPatientUserDao().findByUserIdNo(vo.getPatientIdNo());
                    if(userEntity != null){
                        user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,userEntity.getId());
                    }
                }
                String result = sysDao.getAppDrEvaluationDao().appAddEvaluation(vo,user);
                if(StringUtils.isBlank(result)){
                    this.getAjson().setMsg("添加成功！");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg(result);
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }


    /**
     * 患者端评价列表查询
     * @return
     */
    public String appFindEvaluationPatient() {
        try {
            AppPatientUser user = this.getAppPatientUser();
            if(user != null){
                List<AppDrEvaluationEntity> ls = this.getSysDao().getAppDrEvaluationDao().findEvaluationPatient(user.getId());
                if(ls != null && ls.size()>0){
                    this.getAjson().setRows(ls);
                }
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("登录方式异常!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 患者端评价医生详细查询
     * @return
     */
    public String appFindEvaluationPatientView() {
        try {
            AppPatientUser user = this.getAppPatientUser();
            AppDrEvaluationQvo qvo = (AppDrEvaluationQvo) this.getAppJson(AppDrEvaluationQvo.class);
            if(qvo != null){
                Map<String,Object> map = new HashMap<String,Object>();
                List<AppDrEvaluationCountEntity> lsEntity = this.getSysDao().getAppDrEvaluationDao().findEvaluationCount(qvo);
                List<AppDrEvaluationViewEntity> ls = this.getSysDao().getAppDrEvaluationDao().findEvaluationPatientView(qvo);
                if(ls != null && ls.size()>0){
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setRows(ls);
                }
                if(lsEntity != null&& lsEntity.size() >0){
                    map.put("total",lsEntity);
                    this.getAjson().setMap(map);
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 医生端评价
     * @return
     */
    public String appFindEvaluationDr(){
        try {
            AppDrEvaluationQvo qvo = (AppDrEvaluationQvo) this.getAppJson(AppDrEvaluationQvo.class);
            if(qvo != null){
                AppDrUser drUser = this.getAppDrUser();
                if(drUser != null){
                    qvo.setDrId(drUser.getId());
                    Map<String,Object> map = new HashMap<String,Object>();
                    List<AppDrEvaluationCountEntity> lsEntity = this.getSysDao().getAppDrEvaluationDao().findEvaluationCount(qvo);
                    AppDrEvaluationDrEntity entity = this.getSysDao().getAppDrEvaluationDao().findEvaluationDr(drUser.getId());
                    List<AppDrEvaluationViewEntity> ls = this.getSysDao().getAppDrEvaluationDao().findEvaluationPatientView(qvo);
                    if(ls != null && ls.size()>0){
                        this.getAjson().setQvo(qvo);
                        this.getAjson().setRows(ls);
                    }
                    if(entity != null){
                        this.getAjson().setVo(entity);
                    }
                    if(lsEntity != null&& lsEntity.size() >0){
                        map.put("total",lsEntity);
                        this.getAjson().setMap(map);
                    }
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("登录方式异常!");
                }
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }
}
