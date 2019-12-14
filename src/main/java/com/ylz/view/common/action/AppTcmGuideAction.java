package com.ylz.view.common.action;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppTcmGuide;
import com.ylz.bizDo.jtapp.commonEntity.AppNewTcmLookEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppNewUserTcmGuideEntity;
import com.ylz.bizDo.jtapp.commonVo.*;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

/**
 * 中医药体质辨识指导接口
 * Created by zzl on 2017/8/29.
 */
@SuppressWarnings("all")
@Action(
        value="appTcmGuide",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class AppTcmGuideAction extends CommonAction {
    /**
     * 根据条件查找中医药保健指导模板
     * @return
     */
    public String findGuideMod(){
        try{
            AppNewTcmQvo qvo = (AppNewTcmQvo)getAppJson(AppNewTcmQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                    qvo.setHospId(drUser.getDrHospId());
                }
                List<AppNewTcmGuideQvo> ls = sysDao.getAppTcmGuideDao().findGuideMod(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 保存或修改中医药保健指导
     * @return
     */
    public String saveTcmGuide(){
        try{
            AppTcmListGuideQvo qvo = (AppTcmListGuideQvo)getAppJson(AppTcmListGuideQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                    qvo.setHospId(drUser.getDrHospId());
                    List<AppNewTcmGuideQvo> ls=sysDao.getAppTcmGuideDao().saveTcmGuide(qvo);
                    this.getAjson().setRows(ls);
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
     * 发送中医药保健指导
     * @return
     */
    public String fsGuide(){
        try{
            AppNewTcmVo qvo = (AppNewTcmVo)getAppJson(AppNewTcmVo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                qvo.setDrId(drUser.getId());
                String msg = sysDao.getAppTcmGuideDao().fsGuide(qvo);
                if(msg.equals("true")){
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("发送指导成功");
                }else{
                    this.getAjson().setMsg(msg);
                    this.getAjson().setMsgCode("900");
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
     * 根据用户id查询中医药指导记录
     * @return
     */
    public String findGuideList(){
        try{
            AppUserTcmGuideQvo qvo = (AppUserTcmGuideQvo)getAppJson(AppUserTcmGuideQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(DrPatientType.DR.getValue().equals(qvo.getType())){
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser!=null){
                        qvo.setUserId(drUser.getId());
                    }
                }else if(DrPatientType.PATIENT.getValue().equals(qvo.getType())){
                    AppPatientUser patientUser = this.getAppPatientUser();
                    if(patientUser!=null){
                        qvo.setUserId(patientUser.getId());
                    }
                }
                List<AppNewUserTcmGuideEntity> ls = sysDao.getAppTcmGuideDao().findGuideList(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 删除中医药保健指导模板
     * @return
     */
    public String deleteTcmGuide(){
        try{
            AppUserTcmGuideQvo qvo = (AppUserTcmGuideQvo)getAppJson(AppUserTcmGuideQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    AppTcmGuide tg = (AppTcmGuide)sysDao.getServiceDo().find(AppTcmGuide.class,qvo.getId());
                    if(tg!=null){
                        if(drUser.getDrRole().indexOf(AppRoleType.SHEQU.getValue())!=-1){//有医院权限可删除医院模板
                            if(tg.getTcmgCreateId().equals(drUser.getId())||tg.getTcmgCreateId().equals(drUser.getDrHospId())){
                                sysDao.getServiceDo().delete(tg);
                                this.getAjson().setMsg("删除成功");
                                this.getAjson().setMsgCode("800");
                            }
                        }else{//没有医院权限
                            if(tg.getTcmgCreateId().equals(drUser.getId())){
                                sysDao.getServiceDo().delete(tg);
                                this.getAjson().setMsg("删除成功");
                                this.getAjson().setMsgCode("800");
                            }
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("找不到相关模板信息");
                    }
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
     * 查询发送指导记录
     * @return
     */
    public String findGuideResult(){
        try{
            AppTcmGuideQvo qvo = (AppTcmGuideQvo)getAppJson(AppTcmGuideQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppNewTcmLookEntity> ls = sysDao.getAppHealthMeddleDao().findByTcmGuide(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
}
