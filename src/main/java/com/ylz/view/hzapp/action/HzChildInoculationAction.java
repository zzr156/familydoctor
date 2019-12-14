package com.ylz.view.hzapp.action;

import com.ylz.bizDo.app.po.AppChildInoculationPlan;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.basicHealthEntity.jzYy.*;
import com.ylz.bizDo.jtapp.basicHealthVo.InoculationVo;
import com.ylz.bizDo.jtapp.commonEntity.AllreservedJzEt;
import com.ylz.bizDo.jtapp.commonEntity.AppAllreservedWzxx;
import com.ylz.bizDo.jtapp.commonEntity.AppAllreservedYzxx;
import com.ylz.bizDo.jtapp.patientVo.AppChildInoculationQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

@SuppressWarnings("all")
@Action(
        value="hzInoculation",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class HzChildInoculationAction extends CommonAction{


    /**
     * 儿童未种计划
     * @return
     */
    public String getWzymBymykh(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            AppChildInoculationQvo qvo = (AppChildInoculationQvo)getAppJson(AppChildInoculationQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isNotBlank(qvo.getEtMykh()) && user != null ){
                    List<AppAllreservedWzxx> ls = this.getSysDao().getSecurityCardAsyncBean().getWzymBymykh(qvo.getEtMykh(),user.getId(),user.getPatientName(), DrPatientType.PATIENT.getValue());
                    if(ls != null && ls.size() >0){
                        this.getSysDao().getAppChildInoculationPlanDao().deleteChildMykh(qvo.getEtMykh(),user.getId());
                        for(AppAllreservedWzxx p : ls){
                            AppChildInoculationPlan v = new AppChildInoculationPlan();
                            v.setInoculationCode(p.getYmBm());
                            v.setInoculationDate(ExtendDate.getCalendar(p.getJzRq()));
                            v.setInoculationName(p.getYmJc());
                            v.setInoculationNum(p.getJzZc());
                            v.setInoculationUserId(user.getId());
                            v.setInouclationMykh(qvo.getEtMykh());
                            this.getSysDao().getServiceDo().add(v);
                        }
                        this.getAjson().setRows(ls);
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
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
     * 儿童已种计划
     * @return
     */
    public String getYzYmBymykh(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            AppChildInoculationQvo qvo = (AppChildInoculationQvo)getAppJson(AppChildInoculationQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isNotBlank(qvo.getEtMykh())){
                    List<AppAllreservedYzxx> ls = this.getSysDao().getSecurityCardAsyncBean().getYzYmBymykh(qvo.getEtMykh(),user.getId(),user.getPatientName(),DrPatientType.PATIENT.getValue());
                    this.getAjson().setRows(ls);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
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
     *  疫苗接口
     * @return
     */
        public String getInoculateYmJk(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            InoculationVo qvo = (InoculationVo)getAppJson(InoculationVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getYmlx())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("疫苗类型参数不能为空!");
                    return "ajson";
                }
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser != null){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }

                List<YiMiaoListJk> ls = this.getSysDao().getSecurityCardAsyncBean().getInoculateYmJk(qvo.getYmlx(),requestUserId,requestUserName,userType);
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 接种门诊
     * @return
     */
    public String getInoculateJzMz(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            InoculationVo qvo = (InoculationVo)getAppJson(InoculationVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getYmbm())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("疫苗编码参数不能为空!");
                    return "ajson";
                }
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser != null){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                List<DeptFqJk> ls = this.getSysDao().getSecurityCardAsyncBean().getInoculateJzMz(qvo.getYmbm(),qvo.getJzrq(),requestUserId,requestUserName,userType);
                this.getAjson().setRows(ls);
            }

        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }




    /**
     * 查询预约日期
     * @return
     */
    public String getInoculateJzYyRqMykh(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            InoculationVo qvo = (InoculationVo)getAppJson(InoculationVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getYmbm())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("疫苗编码参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getJzMzId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("接种门诊参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getJzzc())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("针次参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getMykh())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("免疫卡号参数不能为空!");
                    return "ajson";
                }
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser != null ){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }

                List<JieZhongRi> ls = this.getSysDao().getSecurityCardAsyncBean().getInoculateJzYyRqMykh(qvo.getMykh(),qvo.getYmbm(),qvo.getJzMzId(),qvo.getJzzc(),requestUserId,requestUserName,userType);
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 查询预约时间段
     * @return
     */
    public String getInoculateDateYyMykh(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            InoculationVo qvo = (InoculationVo)getAppJson(InoculationVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getYmbm())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("疫苗编码参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getJzMzId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("接种门诊参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getJzYyRq())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("预约日期参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getJzYyxx())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("接种预约信息参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getMykh())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("免疫卡号参数不能为空!");
                    return "ajson";
                }
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser != null){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                YuyueSjD vo = this.getSysDao().getSecurityCardAsyncBean().getInoculateDateYyMykh(qvo.getMykh(),qvo.getYmbm(),qvo.getJzMzId(),qvo.getJzYyRq(),qvo.getJzYyxx(),requestUserId,requestUserName,userType);
                if(vo !=null ){
                    if(StringUtils.isNotBlank(vo.getEntity())){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(vo.getEntity());
                    }else {
                        this.getAjson().setVo(vo);
                    }

                }else{

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
     * 确认预约
     * @return
     */
    public String getInoculateYuyueMykh(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            InoculationVo qvo = (InoculationVo)getAppJson(InoculationVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getYmbm())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("疫苗编码参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getJzMzId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("接种门诊参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getJzYyRq())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("预约日期参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getJzYyxx())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("接种预约信息参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getMykh())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("免疫卡号参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getYySjd())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("预约时间段参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getJzzc())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("接种针次参数不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getSfMf())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("是否免费参数不能为空!");
                    return "ajson";
                }
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser != null){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                String result = this.getSysDao().getSecurityCardAsyncBean().getInoculateYuyueMykh(qvo.getMykh(),qvo.getYmbm(),qvo.getJzMzId(),qvo.getJzYyRq(),qvo.getJzYyxx(),qvo.getJzzc(),qvo.getSfMf(),qvo.getYySjd(),requestUserId,requestUserName,userType);
                this.getAjson().setMsg(result);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 我的预约
     * @return
     */
    public String getInoculateMyYyMykh(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            InoculationVo qvo = (InoculationVo)getAppJson(InoculationVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getMykh())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("免疫卡号参数不能为空!");
                    return "ajson";
                }
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                if(user != null ){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser != null ){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                List<JzYuyueJk> ls  = this.getSysDao().getSecurityCardAsyncBean().getInoculateMyYyMykh(qvo.getMykh(),requestUserId,requestUserName,userType);
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 取消预约
     * @return
     */
    public String getInoculateQxyyMykh(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            InoculationVo qvo = (InoculationVo)getAppJson(InoculationVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getYyId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("预约主键参数不能为空!");
                    return "ajson";
                }
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser != null){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                List<JzYuyueJk> ls  = this.getSysDao().getSecurityCardAsyncBean().getInoculateQxyyMykh(qvo.getYyId(),requestUserId,requestUserName,userType);
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 儿童信息
     * @return
     */
    public String findErtongByMykh(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            InoculationVo qvo = (InoculationVo)getAppJson(InoculationVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getMykh())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("免疫卡号参数不能为空!");
                    return "ajson";
                }
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser != null){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                AllreservedJzEt vo = this.getSysDao().getSecurityCardAsyncBean().findErtongByMykh(qvo.getMykh(),requestUserId,requestUserName,userType);
                this.getAjson().setVo(vo);
            }

        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }



}
