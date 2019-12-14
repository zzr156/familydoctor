package com.ylz.view.ysapp.action;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppReferralTable;
import com.ylz.bizDo.app.po.AppUpHospTable;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrReferralEntity;
import com.ylz.bizDo.jtapp.drEntity.AppReferralPatientEntity;
import com.ylz.bizDo.jtapp.drEntity.AppUpHospEntity;
import com.ylz.bizDo.jtapp.drEntity.ReferralInfo;
import com.ylz.bizDo.jtapp.drVo.AppDrReferralQvo;
import com.ylz.bizDo.jtapp.drVo.AppUpHospQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientEntity;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.ReferralState;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.List;

/**
 * 转诊
 * Created by zzl on 2017/12/11.
 */
@SuppressWarnings("all")
@Action(
        value="ysReferral",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"}),
                @Result(name = "sfjson", type = "json",params={"root","ajson", "excludeProperties","planDate","contentType", "application/json"})
        }
)
public class YsReferralAction extends CommonAction {

    /**
     * 患者转诊初始信息
     * @return
     */
   /* public String initialInformation(){
        try{
            AppDrReferralQvo qvo = (AppDrReferralQvo)this.getAppJson(AppDrReferralQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                //AppDrHzEntity table = sysDao.getAppPatientUserDao()
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }*/

    /**
     * 提交转诊信息
     */
    public String subReferral(){
        try{
            AppDrReferralQvo qvo = (AppDrReferralQvo)this.getAppJson(AppDrReferralQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getPatientId())){
                    this.getAjson().setMsg("患者id不能为空");
                    this.getAjson().setMsgCode("900");
                }else{
                    AppPatientUser user =sysDao.getAppPatientUserDao().findByUserId(qvo.getPatientId());
                    if(user!=null){
                        qvo.setName(user.getPatientName());
                        qvo.setSex(user.getPatientGender());
                        qvo.setAddress(user.getPatientAddress());
                        qvo.setAge(user.getPatientAge());
                        qvo.setCard(user.getPatientCard());
                        qvo.setIdNo(user.getPatientIdno());
                        qvo.setPhone(user.getPatientTel());
                    }
                    //保存转诊信息
                    AppReferralTable table = sysDao.getAppRefarralTableDao().subReferral(qvo);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setVo(table);
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
     * 查询转诊记录列表
     */
    public String findReferralList(){
        try{
            AppDrReferralQvo qvo = (AppDrReferralQvo)this.getAppJson(AppDrReferralQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppDrReferralEntity> list = sysDao.getAppRefarralTableDao().findReferralList(qvo);
                this.getAjson().setRows(list);
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
     * 同意、拒绝、终止、回转转诊请求
     * @return
     */
    public String auditReferral(){
        try{
            AppDrReferralQvo qvo = (AppDrReferralQvo)this.getAppJson(AppDrReferralQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                /*AppDrUser user = this.getAppDrUser();
                if(user!=null){

                }*/
                AppReferralTable vo = sysDao.getAppRefarralTableDao().makeReferral(qvo);
                if(vo!=null){
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setVo(vo);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查无此转诊数据");
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
     * 康复回转
     * @return
     */
   /* public String rehabilitation(){
        try {
            AppDrReferralQvo qvo = (AppDrReferralQvo)this.getAppJson(AppDrReferralQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppReferralTable vo = sysDao.getAppRefarralTableDao().rehabilitation(qvo);
                if(vo!=null){
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setVo(vo);
                }else{
                    this.getAjson().setMsg("查无此转诊信息");
                    this.getAjson().setMsgCode("900");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }*/
    /**
     * 查看转诊数据信息
     * @return
     */
    public String findOneByReferral(){
        try {
            AppDrReferralQvo qvo = (AppDrReferralQvo)this.getAppJson(AppDrReferralQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrReferralEntity vo = sysDao.getAppRefarralTableDao().findOneByReferral(qvo);
                this.getAjson().setVo(vo);
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
     * 查询转诊审核列表
     * @return
     */
    public String findShReferral(){
        try{
            AppDrReferralQvo qvo = (AppDrReferralQvo)this.getAppJson(AppDrReferralQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppDrReferralEntity> list = sysDao.getAppRefarralTableDao().findShReferral(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setRows(list);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询上级医院列表
     * @return
     */
    public String findHospDeptList(){
        try{
            AppUpHospQvo qvo = (AppUpHospQvo)this.getAppJson(AppUpHospQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setBaseId(drUser.getDrHospId());
                }
                List<AppUpHospEntity> list = sysDao.getAppRefarralTableDao().findHosp(qvo);
                this.getAjson().setRows(list);
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
     * 查询医生居民列表
     * @return
     */
    public String findPeople(){
        try{
            AppDrReferralQvo qvo = (AppDrReferralQvo)this.getAppJson(AppDrReferralQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getTeamId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("团队id不能为空");
                    return "ajson";
                }
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setInDrId(drUser.getId());
                    List<AppReferralPatientEntity> list = sysDao.getAppRefarralTableDao().findPeople(qvo);
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setRows(list);
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
     * 根据患者id查询转诊记录
     * @return
     */
    public String findReferralPatient(){
        try{
            AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppDrReferralEntity> list = sysDao.getAppRefarralTableDao().findReferralPatient(qvo);
                this.getAjson().setRows(list);
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
     * 根据医院id查询转诊人员列表
     * @return
     */
    public String findReferralListByHospId(){
        try{
            AppCommQvo qvo = (AppCommQvo)getAppJson(AppCommQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
//                List<ReferralInfo> list = sysDao.getAppRefarralTableDao().findByHospId(qvo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
}
