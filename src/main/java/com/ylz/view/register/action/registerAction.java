package com.ylz.view.register.action;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.register.entity.RegisterGovEntity;
import com.ylz.bizDo.register.po.*;
import com.ylz.bizDo.register.vo.HcProjectVo;
import com.ylz.bizDo.register.vo.RegisterListVo;
import com.ylz.bizDo.register.vo.RegisterSelVo;
import com.ylz.bizDo.register.vo.RegisterUpdateParramVo;
import com.ylz.bizDo.web.vo.WebSignVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.Constant;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

@SuppressWarnings("all")
@Action(
        value="registerAction",
        results={
                @Result(name = "json", type = "json",params={"root","jsons","contentType", "application/json"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","contentType", "application/json"}),
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"})
        }
)
//签约医保登记
public class registerAction extends CommonAction{
    private List jList;
    /**
     * 初始数据
     * @return
     */
    public String commList(){
        try{
            CdUser user = (CdUser)getRequest().getSession().getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
            HealthCareParameter healthCareParameter = new HealthCareParameter();
            healthCareParameter.setHospId(user.getHospId());
            healthCareParameter.setParameterName("YBJK");
            HealthCareParameter hcp = sysDao.getRegisterDao().getHCParameter(healthCareParameter);
            String jklj00 = "";
            if(null != hcp){
                jklj00 = hcp.getParameterValue();
            }
            this.jsons.setCode(jklj00);
            this.jsons.setResult(hcp.getHospId());
        }catch (Exception e){
                new ActionException(getClass(),getAct(),getJsons(),e);
        }
        return "json";
    }

    /**
     * 查询签约信息，取回页面填充数据
     * @return
     */
    public String list() {
        try {
            AppCommQvo qvo = new AppCommQvo();
            String patientIdno = getRequest().getParameter("idno");
            if(StringUtils.isBlank(patientIdno)){
                this.newMsgJson("身份证信息丢失!，请重新读卡！");
            }
            RegisterSelVo return_qvo = new RegisterSelVo();
            qvo.setPatientIdno(patientIdno);
            //查询签约信息
            List<RegisterSelVo> ls = sysDao.getRegisterDao().findSignXxWeb(qvo);
            if(ls!=null && ls.size()>0) {
                this.jsons.setVo(ls.get(0));;
            }
        } catch (Exception e) {
                new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    /**
     * 保存签约医保登记的信息
     * @return
     */
    public String addRegisterInfo(){
        try{
            AppCommQvo qvo = new AppCommQvo();
            String patientIdno = getRequest().getParameter("idno");
            if(StringUtils.isBlank(patientIdno)){
                this.newMsgJson("身份证信息丢失!，请重新读卡！");
            }
            RegisterSelVo return_qvo = new RegisterSelVo();
            qvo.setPatientIdno(patientIdno);
            //查询签约信息
            List<RegisterSelVo> ls = sysDao.getRegisterDao().findSignXxWeb(qvo);
                if (ls != null && ls.size() > 0) {
                    return_qvo = ls.get(0);
            }
            CdUser user = this.getSessionUser();
            return_qvo.setOperationDepart(user.getCdDept().getId());
            return_qvo.setOperatorId(user.getUserId());
            return_qvo.setOperatorName(user.getUserName());
            // cjw  解决泉州之前签约选择农合的数据 先判断该居民是否 有病人费用主表
            PatientCost  vo = sysDao.getRegisterDao().getPatientCost(ls.get(0).getPatientId(),return_qvo.getId());
            String msg ="";
            if(vo == null ){  // 病人费用为空  说明之前没有成功
                //保存信息
                 msg = sysDao.getRegisterDao().addRegisterInfo(return_qvo);
            }else{
                this.jsons.setCode("700");
            }
            if(msg.equals("success")){
                this.jsons.setCode("800");
            }else{
                this.jsons.setCode("900");
            }
         }catch(Exception e){
              new ActionException(getClass(), getAct(), getJsons(), e);
         }
        return "json";
    }

    /**
     * 查询签约登记信息
     * @return
     */
    public String getRegisterInfo(){
        try{
            AppCommQvo qvo = (AppCommQvo)getJsonLay(AppCommQvo.class);
            List<PatientCostDetail> ls;
            if(qvo==null){
                qvo=new AppCommQvo();
                ls=null;
            }else{
                if(null!=qvo.getSignFormId() && StringUtils.isNotBlank(qvo.getSignFormId())){
                    ls = sysDao.getRegisterDao().getPayInfoList(qvo.getSignFormId());
                    this.getJsonLayui().setData(ls);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("签约单丢失！");
                }
            }
        }catch(Exception e){
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }

    /**
     *取需要登记的信息列表
     * @return
     */
    public String findRegisterInfo(){
        try{
            AppCommQvo qvo = new AppCommQvo();
            String patientIdno = getRequest().getParameter("idno");
            if(StringUtils.isBlank(patientIdno)){
                this.newMsgJson("身份证信息丢失!，请重新读卡！");
            }
            RegisterSelVo return_qvo = new RegisterSelVo();
            qvo.setPatientIdno(patientIdno);
            //查询签约信息
            List<RegisterSelVo> ls = sysDao.getRegisterDao().findSignXxWeb(qvo);
            if (ls != null && ls.size() > 0) {
                RegisterListVo rlvVo = null;
                String sersmPkValues = "";
                return_qvo = ls.get(0);
                if(StringUtils.isNotBlank(return_qvo.getId())){
                    rlvVo = sysDao.getRegisterDao().findRegisterInfoList(return_qvo);
                    if(null != rlvVo){
                        rlvVo.setRsVo(return_qvo);
                        //签约团队医生
//                        String doctorListStr = sysDao.getRegisterDao().getDoctorList(return_qvo.getTeamId());
                        List<RegisterGovEntity> rgeList = sysDao.getRegisterDao().getPayInfo(return_qvo.getSetMealId());
                        if(null!=rgeList && rgeList.size()>0){
                            for(RegisterGovEntity rge : rgeList) {
                                sersmPkValues += rge.getSersmPkValue();
                            }
                        }
                        String doctorListStr = sysDao.getRegisterDao().getDrInfo(return_qvo.getSignDrId());
                        /*String resultStr = rlvVo.getResultStr()+";"+doctorListStr.split(";")[1]+";"+"*"+";"+"*"+";"+"*"+";"+doctorListStr.split(";")[0];//组装医生姓名（组成14行协议）
                        rlvVo.setResultStr(resultStr);*/
//                        String drCount = doctorListStr.substring(doctorListStr.lastIndexOf(";"), doctorListStr.length());
                       // rlvVo.setDoctorListStr(doctorListStr.substring(0, doctorListStr.lastIndexOf(";")));
//                        rlvVo.setDrCount(drCount);
                        rlvVo.setDrCount(String.valueOf(doctorListStr.split(";").length/4));
                        rlvVo.setSersmPkValues(sersmPkValues);
                        System.out.print(ls.get(0).getSignPersGroup());
                        rlvVo.setSignGroup(ls.get(0).getSignPersGroup()!=null ? ls.get(0).getSignPersGroup():"");
                    }else{
                        this.jsons.setCode("900");
                        this.jsons.setMsg("没有需要登记的数据！");
                    }
                }
                if(rlvVo!=null) {
                    this.jsons.setCode("200");
                    this.jsons.setVo(rlvVo);
                }
            }else{

            }
        }catch(Exception e){
            new ActionException(getClass(),getAct(),getJsons(),e);
        }
        return "json";
    }

    /**
     * 医保登记成功后，修改本地数据
     * @return
     */
    public String RegisterJS(){
        RegisterUpdateParramVo rupVo = (RegisterUpdateParramVo)getVoJson(RegisterUpdateParramVo.class);
        CdUser user = this.getSessionUser();
        try{
            if(null!=rupVo){
                rupVo.setUser(user);
                String result = sysDao.getRegisterDao().updateInfo(rupVo);
                this.jsons.setCode(result);
            }
        }catch(Exception e){
            new ActionException(getClass(),getAct(),getJsons(),e);
        }
        return "json";
    }

    /**
     *更新挂号号
     * @return
     */
    public String modifyRegisteredNumber(){
        try{
            RegisterUpdateParramVo rupVo = (RegisterUpdateParramVo)getVoJson(RegisterUpdateParramVo.class);
            if(null!=rupVo) {
                if (StringUtils.isNotBlank(rupVo.getMainId())) {
                    PatientSettleAccounts psa = (PatientSettleAccounts) sysDao.getServiceDo().find(PatientSettleAccounts.class, rupVo.getMainId());
                    if (null != psa) {
                        psa.setHcRegistrationId(rupVo.getGhlsh0());
                        psa.setRegisteredDepartmenName(rupVo.getGhksmc());
                        sysDao.getServiceDo().modify(psa);
                        this.jsons.setCode("SUCCESS");
                    }
                }
            }
        }catch(Exception e){
            new ActionException(getClass(),getAct(),getJsons(),e);
        }
        return "json";
    }

    /**
     * 查询医保项目
     * @return
     */
    public String getHcProjectList(){
        try{
            HcProjectVo hcp = (HcProjectVo)getJson(HcProjectVo.class);
            if(null != hcp) {
                List<HealthcareChargesProject> ls = sysDao.getRegisterDao().getHcpList(hcp);
                jsons.setRowsQvo(ls,hcp);
            }
        }catch (Exception e){
            new ActionException(getClass(),getAct(),getJsons(),e);
        }
        return "json";
    }

    /**
     * 改签、退签信息
     * @return
     */
    public String changeRegister(){
        try {
            String patientIdno = getRequest().getParameter("idno");
            if (StringUtils.isBlank(patientIdno)) {
                this.newMsgJson("身份证信息丢失!，请重新读卡！");
            }
            PatientSettleAccounts psa = sysDao.getRegisterDao().getChangeRegisterInfo(patientIdno);
            if(null!=psa){
                this.jsons.setCode("800");
                this.jsons.setVo(psa);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "json";
    }
    public String changeRegisterInfo(){
        try{
            AppCommQvo qvo = new AppCommQvo();
            String patientIdno = getRequest().getParameter("idno");
            if(StringUtils.isBlank(patientIdno)){
                this.newMsgJson("身份证信息丢失!，请重新读卡！");
            }
            RegisterSelVo return_qvo = new RegisterSelVo();
            qvo.setPatientIdno(patientIdno);
            //查询签约信息
            List<RegisterSelVo> ls = sysDao.getRegisterDao().findSignXxWeb(qvo);
            if (ls != null && ls.size() > 0) {
                RegisterListVo rlvVo = null;
                String sersmPkValues = "";
                return_qvo = ls.get(0);
                if (StringUtils.isNotBlank(return_qvo.getId())) {
                    rlvVo = sysDao.getRegisterDao().findChangeRegisterInfoList(return_qvo.getId());
                    if (null != rlvVo) {
                        //签约团队医生
                        String doctorListStr = sysDao.getRegisterDao().getDrInfo(return_qvo.getSignDrId());
                        String drCount = doctorListStr.substring(doctorListStr.indexOf("drCount") + "drCount".length(), doctorListStr.length());
                        rlvVo.setDoctorListStr(doctorListStr.substring(0, doctorListStr.lastIndexOf(";")));
                        rlvVo.setDrCount(drCount);
                    }else{
                        this.jsons.setCode("900");
                        this.jsons.setMsg("没有需要登记的数据！");
                    }
                }
                if(rlvVo!=null) {
                    this.jsons.setCode("200");
                    this.jsons.setVo(rlvVo);
                }
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "json";
    }
    /**
     * 退签
     * @return
     */
    public String RegisterCancelJS(){
        RegisterUpdateParramVo rupVo = (RegisterUpdateParramVo)getVoJson(RegisterUpdateParramVo.class);
        CdUser user = this.getSessionUser();
        try{
            if(null!=rupVo){
                rupVo.setUser(user);
                String result = sysDao.getRegisterDao().cancelRegisterInfo(rupVo);
                if(result.equals("success")){
                    this.jsons.setCode("800");
                }else{
                    this.jsons.setCode("900");
                }

            }
        }catch(Exception e){
            new ActionException(getClass(),getAct(),getJsons(),e);
        }
        return "json";
    }

    /**
     * 查询签约医生的身份证号码
     * @return
     */
    public String getDoctorIdNo(){
        WebSignVo vo = (WebSignVo)getVoJson(WebSignVo.class);
        try{
            if(null!=vo){
                AppDrUser result = sysDao.getRegisterDao().getDocInfo(vo.getDrId());
                if(null != result){
                    this.jsons.setCode("800");
                    this.jsons.setVo(result);
                }
            }
        }catch(Exception e){
            new ActionException(getClass(),getAct(),getJsons(),e);
        }
        return "json";
    }
}
