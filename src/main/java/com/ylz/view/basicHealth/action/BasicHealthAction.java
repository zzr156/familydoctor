package com.ylz.view.basicHealth.action;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthEntity.BirthCertificate.Fy_csyxzm;
import com.ylz.bizDo.jtapp.basicHealthEntity.*;
import com.ylz.bizDo.jtapp.basicHealthEntity.InfectiousReport.T_im_refDTO;
import com.ylz.bizDo.jtapp.basicHealthEntity.Neonate.Fy_etfsjlDTO;
import com.ylz.bizDo.jtapp.basicHealthEntity.Referral.Fjzl_zrzcDTO;
import com.ylz.bizDo.jtapp.basicHealthEntity.Referral.ReferralOrg;
import com.ylz.bizDo.jtapp.basicHealthEntity.findFy.*;
import com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.ClinicalDocument;
import com.ylz.bizDo.jtapp.basicHealthEntity.findpersonandjzinfo.PersonAndJzInfoDTO;
import com.ylz.bizDo.jtapp.basicHealthEntity.jktj.JmjktjAllDTO;
import com.ylz.bizDo.jtapp.basicHealthEntity.jktj.JmjktjDTO;
import com.ylz.bizDo.jtapp.basicHealthEntity.jktj.T_zxjsjb_sfDTO;
import com.ylz.bizDo.jtapp.basicHealthEntity.zyy.TzbsDTO;
import com.ylz.bizDo.jtapp.basicHealthEntity.zyy.TzbszdDTO;
import com.ylz.bizDo.jtapp.basicHealthVo.*;
import com.ylz.bizDo.jtapp.drEntity.*;
import com.ylz.bizDo.jtapp.drVo.AppDrReferralQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.plan.Entity.MxjbsfDTO;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.*;

/**基卫接口
 * Created by zzl on 2017/7/21.
 */
@SuppressWarnings("all")
@Action(
        value = "basicHealth",
        results = {
                @Result(name = "ajson", type = "json", params = {"root", "ajson","excludeNullProperties","true","contentType", "application/json"}),
                @Result(name = "basicjson", type = "json", params = {"root", "ajson","contentType", "application/json"})
        }
)
public class BasicHealthAction extends CommonAction {


     private static  final String tempNull = "[null]";



    /**
     * 患者健康档案列表
     * @param idNo
     * @param card
     * @param type;//1：门诊 2：住院 3：体检 4：妇幼
     * @return
     */
    public String getHealthInfoPatient() {
        try{
            HealthInfoQvo qvo = (HealthInfoQvo)getAppJson(HealthInfoQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByType(qvo.getType(),qvo.getIdNo());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    PersonAndJzInfoDTO vo = JsonUtil.fromJson(data.getHealthData(),PersonAndJzInfoDTO.class);
                    Calendar cal = Calendar.getInstance();
                    boolean result = ExtendDateUtil.sameDate(data.getHealthDate().getTime(),cal.getTime());
                    if(!result){
                        if(vo.getJzInfoDTOs() != null && vo.getJzInfoDTOs().size() >0){
                            this.getAjson().setVo(vo);
                        }else{
                            String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInfoSynchro(qvo.getCard(),qvo.getIdNo(),qvo.getType(),"getHealthInfoPatient",requestUserId,requestUserName,userType);
                            JSONObject jsonall = JSONObject.fromObject(str);
                            if(jsonall.get("msgCode") != null){
                                if(jsonall.get("msgCode").toString().equals("900")){
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg("数据异常!");
                                }else {
                                    if(jsonall.get("vo")!=null){
                                        data = this.getSysDao().getAppHealthDataDao().findByType(qvo.getType(),qvo.getIdNo());
                                        data.setHealthData(jsonall.get("vo").toString());
                                        data.setHealthDate(Calendar.getInstance());
                                        sysDao.getServiceDo().modify(data);
                                        vo = JsonUtil.fromJson(jsonall.get("vo").toString(),PersonAndJzInfoDTO.class);
                                        this.getAjson().setVo(vo);
                                    }else{
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("查无数据!");
                                    }
                                }
                            }
                        }
                    }else {
                        if(vo.getJzInfoDTOs() != null && vo.getJzInfoDTOs().size() >0){
                            this.getAjson().setVo(vo);
                        }else{
                            String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInfoSynchro(qvo.getCard(),qvo.getIdNo(),qvo.getType(),"getHealthInfoPatient",requestUserId,requestUserName,userType);
                            JSONObject jsonall = JSONObject.fromObject(str);
                            if(jsonall.get("msgCode") != null){
                                if(jsonall.get("msgCode").toString().equals("900")){
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg("数据异常!");
                                }else {
                                    if(jsonall.get("vo")!=null){
                                        data = this.getSysDao().getAppHealthDataDao().findByType(qvo.getType(),qvo.getIdNo());
                                        data.setHealthData(jsonall.get("vo").toString());
                                        data.setHealthDate(Calendar.getInstance());
                                        sysDao.getServiceDo().modify(data);
                                        vo = JsonUtil.fromJson(jsonall.get("vo").toString(),PersonAndJzInfoDTO.class);
                                        this.getAjson().setVo(vo);
                                    }else{
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("查无数据!");
                                    }
                                }
                            }
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),null,null,null,null,qvo.getType(),qvo.getIdNo());
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInfoSynchro(qvo.getCard(),qvo.getIdNo(),qvo.getType(),"getHealthInfoPatient",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("msgCode") != null){
                        if(jsonall.get("msgCode").toString().equals("900")){
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("数据异常!");
                        }else {
                            if(jsonall.get("vo")!=null){
                                data = this.getSysDao().getAppHealthDataDao().findByType(qvo.getType(),qvo.getIdNo());
                                data.setHealthData(jsonall.get("vo").toString());
                                sysDao.getServiceDo().modify(data);
                                PersonAndJzInfoDTO vo = JsonUtil.fromJson(jsonall.get("vo").toString(),PersonAndJzInfoDTO.class);
                                this.getAjson().setVo(vo);
                            }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("查无数据!");
                            }
                        }
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }

    /**
     * 患者健康档案列表(检查检验报告)
     *
     * @param idNo
     * @param card
     * @return
     */
    public String getHealthInfoPatientExamReport() {
        try{
            HealthInfoQvo qvo = (HealthInfoQvo)getAppJson(HealthInfoQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByType(qvo.getType(),qvo.getIdNo());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    PersonAndJzInfoDTO vo = JsonUtil.fromJson(data.getHealthData(),PersonAndJzInfoDTO.class);
                    Calendar cal = Calendar.getInstance();
                    boolean result = ExtendDateUtil.sameDate(data.getHealthDate().getTime(),cal.getTime());
                    if(!result){
                        if(vo.getJzInfoDTOs() != null && vo.getJzInfoDTOs().size() >0){
                            this.getAjson().setVo(vo);
                        }else{
                            String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInfoSynchro(qvo.getCard(),qvo.getIdNo(),qvo.getType(),"getHealthInfoPatientExamReport",requestUserId,requestUserName,userType);
                            JSONObject jsonall = JSONObject.fromObject(str);
                            if(jsonall.get("msgCode") != null){
                                if(jsonall.get("msgCode").toString().equals("900")){
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg("数据异常!");
                                }else {
                                    if(jsonall.get("vo")!=null){
                                        data = this.getSysDao().getAppHealthDataDao().findByType(qvo.getType(),qvo.getIdNo());
                                        data.setHealthData(jsonall.get("vo").toString());
                                        data.setHealthDate(Calendar.getInstance());
                                        sysDao.getServiceDo().modify(data);
                                        vo = JsonUtil.fromJson(jsonall.get("vo").toString(),PersonAndJzInfoDTO.class);
                                        this.getAjson().setVo(vo);
                                    }else{
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("查无数据!");
                                    }
                                }
                            }
                        }
                    }else {
                        if(vo.getJzInfoDTOs() != null && vo.getJzInfoDTOs().size() >0){
                            this.getAjson().setVo(vo);
                        }else{
                            String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInfoSynchro(qvo.getCard(),qvo.getIdNo(),qvo.getType(),"getHealthInfoPatientExamReport",requestUserId,requestUserName,userType);
                            JSONObject jsonall = JSONObject.fromObject(str);
                            if(jsonall.get("msgCode") != null){
                                if(jsonall.get("msgCode").toString().equals("900")){
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg("数据异常!");
                                }else {
                                    if(jsonall.get("vo")!=null){
                                        data = this.getSysDao().getAppHealthDataDao().findByType(qvo.getType(),qvo.getIdNo());
                                        data.setHealthData(jsonall.get("vo").toString());
                                        data.setHealthDate(Calendar.getInstance());
                                        sysDao.getServiceDo().modify(data);
                                        vo = JsonUtil.fromJson(jsonall.get("vo").toString(),PersonAndJzInfoDTO.class);
                                        this.getAjson().setVo(vo);
                                    }else{
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("查无数据!");
                                    }
                                }
                            }
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),null,null,null,null,qvo.getType(),qvo.getIdNo());
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInfoSynchro(qvo.getCard(),qvo.getIdNo(),null,"getHealthInfoPatientExamReport",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("msgCode") != null){
                        if(jsonall.get("msgCode").toString().equals("900")){
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("数据异常!");
                        }else {
                            if(jsonall.get("vo")!=null){
                                data = this.getSysDao().getAppHealthDataDao().findByType(qvo.getType(),qvo.getIdNo());
                                data.setHealthData(jsonall.get("vo").toString());
                                sysDao.getServiceDo().modify(data);
                                PersonAndJzInfoDTO vo = JsonUtil.fromJson(jsonall.get("vo").toString(),PersonAndJzInfoDTO.class);
                                this.getAjson().setVo(vo);
                            }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("查无数据!");
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }



    /**
     * 添加数据
     */
    public void getAddHealthData(String card,String areaCode,String ghh000,String organizationCode,String patientId,String type,String idNo){
        AppHealthData data = new AppHealthData();
        data.setHealthCard(card);
        if(StringUtils.isNotBlank(idNo)){
            data.setHealthIdno(idNo);
        }else{
            data.setAreaCode(areaCode);
            data.setGhh000(ghh000);
            data.setOrganizationCode(organizationCode);
            data.setPatientId(patientId);
        }
        data.setHealthType(type);
        data.setHealthDate(Calendar.getInstance());
        sysDao.getServiceDo().add(data);
    }

    /**
     * 门诊基本诊疗信息
     *
     * @return
     */
    public String getHealthDiagnosisBase() {
        try{
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZJBZLXX.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthDiagnosisBase",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZJBZLXX.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.MZJBZLXX.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthDiagnosisBase",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZJBZLXX.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }

    /**
     * 门诊用药记录
     */
    public String getDiagnosisMedicine() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZYYJL.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getDiagnosisMedicine",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZYYJL.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.MZYYJL.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getDiagnosisMedicine",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZYYJL.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }

    /**
     * 门诊费用明细
     *
     * @return
     */
    public String getHealthMzDiagnosisCost() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZFYMX.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData()) ){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthMzDiagnosisCost",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZFYMX.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.MZFYMX.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthMzDiagnosisCost",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZFYMX.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 门诊检查报告
     */
    public String getHealthMzJcExamReport() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZJCBG.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthMzJcExamReport",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZJCBG.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.MZJCBG.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthMzJcExamReport",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZJCBG.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 门诊检验报告
     *
     * @return
     */
    public String getHealthMzJyExamReport() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZJYBG.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthMzJyExamReport",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZJYBG.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.MZJYBG.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthMzJyExamReport",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZJYBG.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 住院基本诊疗信息
     */
    public String getHealthZyDiagnosisBase() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYJBZLXX.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthZyDiagnosisBase",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYJBZLXX.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.ZYJBZLXX.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthZyDiagnosisBase",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYJBZLXX.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 住院用药记录
     *
     * @return
     */
    public String getHealthDiagnosisMedicine() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYYYJL.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthDiagnosisMedicine",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYYYJL.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.ZYYYJL.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthDiagnosisMedicine",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYYYJL.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 住院检查报告
     */
    public String getHealthZyJcExamReport() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYJCBG.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthZyJcExamReport",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYJCBG.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.ZYJCBG.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthZyJcExamReport",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYJCBG.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 住院检验报告
     *
     * @return
     */
    public String getHealthZyJyExamReport() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYJYBG.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthZyJyExamReport",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYJYBG.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.ZYJYBG.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthZyJyExamReport",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYJYBG.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 住院费用明细
     *
     * @return
     */
    public String getHealthZyDiagnosisCost() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYFYMX.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthZyDiagnosisCost",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYFYMX.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.ZYFYMX.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthZyDiagnosisCost",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.ZYFYMX.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 长期医嘱
     */
    public String getCqyz() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.CQYZ.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getCqyz",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.CQYZ.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.CQYZ.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getCqyz",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.CQYZ.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 临时医嘱
     */
    public String getLsyz() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.LSYZ.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getLsyz",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.LSYZ.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.LSYZ.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getLsyz",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.LSYZ.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 入院记录
     */
    public String getRyjl() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.RYJL.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getRyjl",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.RYJL.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.RYJL.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getRyjl",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.RYJL.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }


            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 出院小结
     */
    public String getCyxj() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.CYXJ.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getCyxj",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.CYXJ.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.CYXJ.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getCyxj",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.CYXJ.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }


            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 病案首页
     */
    public String getBasy() {
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.BASY.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getBasy",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZJBZLXX.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.BASY.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getBasy",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.BASY.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }


            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }
    /**
     * 体检报告
     * @return
     */
    public String getmedicalReport(){
        try {
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
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
            HealthDiagnosisBaseQvo qvo = (HealthDiagnosisBaseQvo)getAppJson(HealthDiagnosisBaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthData data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.TJBG.getValue());
                if(data != null && StringUtils.isNotBlank(data.getHealthData()) && !tempNull.equals(data.getHealthData())){
                    List<ClinicalDocument> lsDetail = JsonUtil.fromJson(data.getHealthData(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                    if(lsDetail != null && lsDetail.size() >0){
                        this.getAjson().setRows(lsDetail);
                    }else{
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getHealthDiagnosisBase",requestUserId,requestUserName,userType);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("rows") != null){
                            lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                            if(lsDetail != null && lsDetail.size() >0){
                                data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.MZJBZLXX.getValue());
                                data.setHealthData(jsonall.get("rows").toString());
                                sysDao.getServiceDo().modify(data);
                                this.getAjson().setRows(lsDetail);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无数据!");
                        }
                    }
                }else{
                    if(data == null){
                        this.getAddHealthData(qvo.getCard(),qvo.getAreaCode(),qvo.getGhh000(),qvo.getOrganizationCode(),qvo.getPatientId(),CommonHealth.TJBG.getValue(),null);
                    }
                    String str = this.getSysDao().getSecurityCardAsyncBean().getHealthInSynchro(qvo.getCard(),qvo.getOrganizationCode(),qvo.getGhh000(),qvo.getPatientId(),qvo.getAreaCode(),"getmedicalReport",requestUserId,requestUserName,userType);
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null){
                        List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
                        if(lsDetail != null && lsDetail.size() >0){
                            data = this.getSysDao().getAppHealthDataDao().findByPatientId(qvo.getPatientId(),qvo.getGhh000(),CommonHealth.TJBG.getValue());
                            data.setHealthData(jsonall.get("rows").toString());
                            sysDao.getServiceDo().modify(data);
                            this.getAjson().setRows(lsDetail);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据!");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "basicjson";
    }

    /**
     * 上传居民健康档案
     * @return
     */
    public String uploadHealthCareRecord(){
        try {
            DwellerfileQvo qvo = (DwellerfileQvo)getAppJson(DwellerfileQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = "";
                String requestUserName = "";
                AppDrUser drUser = this.getAppDrUser();
                boolean flagJd = false;
                if(drUser != null){
                    flagJd = true;
                    requestUserId = drUser.getId();
                    requestUserName = drUser.getDrName();
                    if(StringUtils.isNotBlank(drUser.getDrHospId())){
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                        String hospId = "";
                        String areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode(),"2");
                        if(areaCode.equals("3503") || areaCode.equals("3504") ||
                                areaCode.equals("3505") || areaCode.equals("3506") || areaCode.equals("3507")){
                            String[] hospIds = dept.getId().split("_");
                            hospId = hospIds[1];
                        }else{
                            hospId = dept.getId();
                        }
                        if(StringUtils.isBlank(qvo.getFileQvo().getRef_ci_id())){
                            qvo.getFileQvo().setRef_ci_id(hospId);
                        }
                        if(StringUtils.isBlank(qvo.getFileQvo().getXzbh00())){
                            qvo.getFileQvo().setXzbh00(dept.getHospAreaCode());
                        }
                    }
                }
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = JSONObject.fromObject(qvo);
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=uploadHealthCareRecord";
                String state = PropertiesUtil.getConfValue("openTheInterface");
                String str = null;
                if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getFileQvo().getIdcardno(),"uploadHealthCareRecord");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, DrPatientType.DR.getValue(),"uploadHealthCareRecord");
                 if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("entity") != null && StringUtils.isNotBlank(jsonall.get("entity").toString())){
                        JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                        if(entity != null && StringUtils.isNotBlank(entity.toString())){
                            if("true".equals(entity.get("success").toString())){
                                this.getAjson().setMsg("上传成功");
                                JSONObject jsonEntity = JSONObject.fromObject(entity.get("entity"));
                                this.getAjson().setMsg(jsonEntity.get("t_dwellerfile").toString());

                                if(flagJd){//医生上传健康档案添加履约数据
                                    //获取居民身份证,判断居民是存在家签库
                                    String idno = qvo.getFileQvo().getIdcardno();
                                    if(StringUtils.isNotBlank(idno)){
                                        AppPatientUserEntity user = sysDao.getAppPatientUserDao().findByUserIdNo(idno);
                                        if(user != null){
                                            //判断是否签约
                                            AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(user.getId());
                                            if(form != null){
                                                if(StringUtils.isNotBlank(qvo.getFileQvo().getIdate()) && qvo.getFileQvo().getIdate().equals("1")){
                                                    form.setSignDelType("1");
                                                    form.setSignState("9");
                                                    form.setSignDieDate(ExtendDate.getCalendar(qvo.getFileQvo().getDdate()));
                                                    form.setSignDelReason(qvo.getFileQvo().getBeizhu());
                                                    form.setSignOperatorName(requestUserName);
                                                    sysDao.getServiceDo().modify(form);
                                                }else{
                                                    //添加履约数据
                                                    PerformanceDataQvo qqvo = new PerformanceDataQvo();
                                                    qqvo.setPerName(user.getPatientName());
                                                    qqvo.setPerIdno(user.getPatientIdno());
                                                    qqvo.setPerType(PerformanceType.JMJKDA.getValue());
//                                                qqvo.setPerForeign();
                                                    qqvo.setPerSource("2");
                                                    if(StringUtils.isNotBlank(user.getPatientCity())){
                                                        CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                                                        if(p != null){
                                                            String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                                                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                                                            if(value != null) {
                                                                qqvo.setPerArea(value.getCodeTitle());
                                                            }
                                                        }
                                                    }
                                                    qqvo.setDrName(drUser.getDrName());
                                                    qqvo.setDrAccount(drUser.getDrAccount());
                                                    qqvo.setDrPwd(drUser.getDrPwd());
                                                    qqvo.setDrGender(drUser.getDrGender());
                                                    qqvo.setDrTel(drUser.getDrTel());
                                                    qqvo.setDrId(drUser.getId());
                                                    if(StringUtils.isNotBlank(drUser.getDrHospId())){
                                                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                                        if(dept != null){
                                                            qqvo.setHospId(dept.getId());
                                                            qqvo.setHospName(dept.getHospName());
                                                            qqvo.setHospAreaCode(dept.getHospAreaCode());
                                                            qqvo.setHospAddress(dept.getHospAddress());
                                                            qqvo.setHospTel(dept.getHospTel());
                                                        }
                                                    }
                                                    String fwType = "";
                                                    String sermeal = "";//服务包id
                                                    fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                                    sermeal = form.getSignpackageid();
                                                    sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                                                }
                                            }
                                        }
                                    }
                                }

                            }else{
                                this.getAjson().setMsg(entity.toString());
                                this.getAjson().setMsgCode("900");
                            }
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
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
     * 上传签约数据
     * @return
     */
    public String uploadContract(){
        try {
            ContractInfoVo qvo = (ContractInfoVo)getAppJson(ContractInfoVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = "";
                String requestUserName = "";
                String userType = null;
                AppDrUser drUser = this.getAppDrUser();
                if(drUser != null){
                    requestUserId = drUser.getId();
                    requestUserName = drUser.getDrName();
                    userType = "2";
                }else{
                    AppPatientUser user = this.getAppPatientUser();
                    if(user != null){
                        requestUserId = user.getId();
                        requestUserName = user.getPatientName();
                        userType = "1";
                    }
                }
                String state = PropertiesUtil.getConfValue("openTheInterface");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("doctorid", qvo.getDoctorId());
                jsonParam.put("address", qvo.getAddress());
                jsonParam.put("df_id", qvo.getDf_id());
                jsonParam.put("dqsj", qvo.getDqsj());
                jsonParam.put("f_id", qvo.getF_id());
                jsonParam.put("hmname", qvo.getHname());
                jsonParam.put("linkman", qvo.getLinkman());
                jsonParam.put("mobile", qvo.getMobile());
                jsonParam.put("qyzt",qvo.getQyzt());
                jsonParam.put("qyfs", qvo.getQyfs());
                jsonParam.put("qysj", qvo.getQysj());
                jsonParam.put("service", qvo.getService());
                jsonParam.put("tel", qvo.getTel());
                jsonParam.put("yyid", qvo.getYyid());
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=uploadContract";

                String str = null;
                if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getDf_id(),"uploadContract");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"uploadContract");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("entity") != null  && StringUtils.isNotBlank(jsonall.get("entity").toString())){
                        JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                        if("true".equals(entity.get("success").toString())){
                            this.getAjson().setMsg("上传成功");
                            this.getAjson().setMsg(entity.get("entity").toString());
                        }else{
                            this.getAjson().setMsgCode("900");
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
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
     * 根据当前登录机构的ID获取居委会
     * @param orgId
     * @return
     */
    public String getCommitteeByOrg(){
        try {
            HealthCardRecodesVo qvo = (HealthCardRecodesVo)getAppJson(HealthCardRecodesVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String state = PropertiesUtil.getConfValue("openTheInterface");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                if(StringUtils.isNotBlank(qvo.getOrgId())){
                    String[] orgs = qvo.getOrgId().split("_");
                    if(orgs.length>1){
                        qvo.setOrgId(orgs[1]);
                    }
                }
                jsonParam.put("orgId",qvo.getOrgId());
                jsonParam.put("urlType",qvo.getUrlType());
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getCommitteeByOrg";
                String str = null;
                if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getOrgId(),"getCommitteeByOrg");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getCommitteeByOrg");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows") != null && jsonall.get("msgCode").equals("800")){
                        List<CommitteeEntity> ls = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<CommitteeEntity>>() {}.getType());
                        this.getAjson().setRows(ls);
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
     * 获取乡镇列表
     * @param orgId
     * @return
     */
    public String getCountryByOrg(){
        try {
            HealthCardRecodesVo qvo = (HealthCardRecodesVo)getAppJson(HealthCardRecodesVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String state = PropertiesUtil.getConfValue("openTheInterface");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                if(StringUtils.isBlank(qvo.getOrgId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("机构主键不能为空");
                    return "ajson";
                }
                String[] orgIds = qvo.getOrgId().split("_");
                if(orgIds.length >1){
                    qvo.setOrgId(orgIds[1]);
                }
                jsonParam.put("orgId",qvo.getOrgId());
                jsonParam.put("urlType",qvo.getUrlType());
                if(AddressType.XM.getValue().equals(qvo.getUrlType())){
                    System.out.println("厦门暂时无法建档");
                    this.getAjson().setMsg("900");
                    this.getAjson().setMsgCode("查无乡镇列表列表");
                    return "ajson";
                }
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getCountryByOrg";
                String str = null;
                if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getOrgId(),"getCountryByOrg");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getCountryByOrg");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<CountryEntity> ls = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<CountryEntity>>() {}.getType());
                        this.getAjson().setRows(ls);
                    }else {
                        this.getAjson().setMsg("系统错误，请联系管理员");
                        this.getAjson().setMsgCode("900");
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
     * 根据当前登录机构的ID获取调查员、建档人、医生签名列表
     * @param orgId
     * @return
     */
    public String getUtilsDoctor(){
        try {
            HealthCardRecodesVo qvo = (HealthCardRecodesVo)getAppJson(HealthCardRecodesVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String state = PropertiesUtil.getConfValue("openTheInterface");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("orgId",qvo.getOrgId());
                jsonParam.put("urlType",qvo.getUrlType());
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getUtilsDoctor";
                String str = null;
                if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getOrgId(),"getUtilsDoctor");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getUtilsDoctor");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<UtilsDoctor> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<UtilsDoctor>>() {}.getType());
                        this.getAjson().setRows(ls);
                    }else {
                        this.getAjson().setMsg("系统错误，请联系管理员");
                        this.getAjson().setMsgCode("900");
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
     *  网上搜索健康档案
     * @param idcardno 身份证号
     * @param urlType
     * @param currentPage 当前页
     * @return
     */
    public String getEnterpatientList(){
        try {
            EnterpatientVo qvo = (EnterpatientVo)getAppJson(EnterpatientVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String state = PropertiesUtil.getConfValue("openTheInterface");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("currentPage",qvo.getCurrentPage());
                if(StringUtils.isNotBlank(qvo.getRef_ci_id())) {
                    jsonParam.put("ref_ci_id", qvo.getRef_ci_id());
                }
                if(StringUtils.isNotBlank(qvo.getName())) {
                    jsonParam.put("name", qvo.getName());
                }
                if(StringUtils.isNotBlank(qvo.getVnum())) {
                    jsonParam.put("vnum", qvo.getVnum());
                }
                if(StringUtils.isNotBlank(qvo.getIdcardno())) {
                    jsonParam.put("idcardno", qvo.getIdcardno());
                }
                if(StringUtils.isNotBlank(qvo.getCcl_id())) {
                    jsonParam.put("ccl_id", qvo.getCcl_id());
                }
                if(StringUtils.isNotBlank(qvo.getXcsfrqfr())) {
                    jsonParam.put("xcsfrqfr", qvo.getXcsfrqfr());
                }
                if(StringUtils.isNotBlank(qvo.getXcsfrqto())) {
                    jsonParam.put("xcsfrqto", qvo.getXcsfrqto());
                }
                if(StringUtils.isNotBlank(qvo.getCsrqfr())) {
                    jsonParam.put("csrqfr", qvo.getCsrqfr());
                }
                if(StringUtils.isNotBlank(qvo.getCsrqto())) {
                    jsonParam.put("csrqto", qvo.getCsrqto());
                }
                if(StringUtils.isNotBlank(qvo.getBirthday())) {
                    jsonParam.put("birthday", qvo.getBirthday());
                }
                if(StringUtils.isNotBlank(qvo.getUrlType())) {
                    jsonParam.put("urlType", qvo.getUrlType());
                }
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getEnterpatientList";
                String str = null;
                if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId,requestUserName,jsonParam.toString(),"","getEnterpatientList");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getEnterpatientList");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<EnterpatientEntity> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<EnterpatientEntity>>() {}.getType());
                        this.getAjson().setRows(ls);
                    }else {
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                        this.getAjson().setMsgCode("900");
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
     * 获取中医体质辨识
     * @param orgId
     * @return
     */
    public String getConstitutionTcm(){
        try {
            ConstructionTcmVo qvo = (ConstructionTcmVo)getAppJson(ConstructionTcmVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String state = PropertiesUtil.getConfValue("openTheInterface");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("idNo",qvo.getIdNo());
                jsonParam.put("name",qvo.getName());
                jsonParam.put("urlType",qvo.getUrlType());
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getConstitutionTcm";
                String str = null;
                if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdNo(),"getConstitutionTcm");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getConstitutionTcm");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<TzbszdDTO> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<TzbszdDTO>>() {}.getType());
                        TzbsDTO TzbsDTO = JsonUtil.fromJson(jsonall.get("vo").toString(),TzbsDTO.class);
                        this.getAjson().setVo(TzbsDTO);
                        this.getAjson().setRows(ls);
                    }else {
                        this.getAjson().setMsg("系统错误，请联系管理员");
                        this.getAjson().setMsgCode("900");
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
     * 下载单个或者多个居民档案
     * @return
     */
    public String getResidentRecords(){
        try {
            HealthCardRecodesVo qvo = (HealthCardRecodesVo)getAppJson(HealthCardRecodesVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser!=null){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                String str = this.getSysDao().getSecurityCardAsyncBean().getResidentRecords(qvo.getDf_id(),null,qvo.getUrlType(),requestUserId,requestUserName,userType);
                JSONObject jsonall = JSONObject.fromObject(str);
                if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                    List<ResidentRecordsEntity> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<ResidentRecordsEntity>>() {}.getType());
                    this.getAjson().setRows(ls);
                }else {
                    this.getAjson().setMsg("系统错误，请联系管理员");
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
     *  网上搜索健康档案查询单个居民档案
     * @param idcardno 身份证号
     * @param urlType
     * @param currentPage 当前页
     * @return
     */
    public String getEnterpatientResidentRecords(){
        try {
            EnterpatientVo qvo = (EnterpatientVo)getAppJson(EnterpatientVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser!=null){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                String state = PropertiesUtil.getConfValue("openTheInterface");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("currentPage",qvo.getCurrentPage());
                if(StringUtils.isNotBlank(qvo.getRef_ci_id())) {
                    jsonParam.put("ref_ci_id", qvo.getRef_ci_id());
                }
                if(StringUtils.isNotBlank(qvo.getName())) {
                    jsonParam.put("name", qvo.getName());
                }
                if(StringUtils.isNotBlank(qvo.getVnum())) {
                    jsonParam.put("vnum", qvo.getVnum());
                }
                if(StringUtils.isNotBlank(qvo.getIdcardno())) {
                    jsonParam.put("idcardno", qvo.getIdcardno());
                }
                if(StringUtils.isNotBlank(qvo.getCcl_id())) {
                    jsonParam.put("ccl_id", qvo.getCcl_id());
                }
                if(StringUtils.isNotBlank(qvo.getXcsfrqfr())) {
                    jsonParam.put("xcsfrqfr", qvo.getXcsfrqfr());
                }
                if(StringUtils.isNotBlank(qvo.getXcsfrqto())) {
                    jsonParam.put("xcsfrqto", qvo.getXcsfrqto());
                }
                if(StringUtils.isNotBlank(qvo.getCsrqfr())) {
                    jsonParam.put("csrqfr", qvo.getCsrqfr());
                }
                if(StringUtils.isNotBlank(qvo.getCsrqto())) {
                    jsonParam.put("csrqto", qvo.getCsrqto());
                }
                if(StringUtils.isNotBlank(qvo.getBirthday())) {
                    jsonParam.put("birthday", qvo.getBirthday());
                }
                if(StringUtils.isNotBlank(qvo.getUrlType())) {
                    jsonParam.put("urlType", qvo.getUrlType());
                }
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getEnterpatientList";
                String str = null;
                if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId,requestUserName,jsonParam.toString(),"","getEnterpatientList");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getEnterpatientList");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<EnterpatientEntity> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<EnterpatientEntity>>() {}.getType());
                        str = this.getSysDao().getSecurityCardAsyncBean().getResidentRecords(ls.get(0).getDf_id(),null,qvo.getUrlType(),requestUserId,requestUserName,userType);
                        JSONObject jsonRe = JSONObject.fromObject(str);
                        if(jsonRe.get("rows")!=null && jsonRe.get("msgCode").equals("800")){
                            List<ResidentRecordsEntity> lsEntity = JsonUtil.fromJson(jsonRe.get("rows").toString(),new TypeToken<List<ResidentRecordsEntity>>() {}.getType());
                            this.getAjson().setVo(lsEntity.get(0));
                        }else {
                            this.getAjson().setMsg("系统错误，请联系管理员");
                            this.getAjson().setMsgCode("900");
                        }
                    }else {
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                        this.getAjson().setMsgCode("900");
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
     * 异步调用同步健康档案数据
     * @return
     */
    public String getHealthInfoPatientSynchro(){
        try {
            HealthInfoQvo qvo = (HealthInfoQvo)getAppJson(HealthInfoQvo.class);
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppPatientUser user = this.getAppPatientUser();
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
            this.getSysDao().getSecurityCardAsyncBean().getHealthInfoPatientSynchro(qvo.getIdNo(),qvo.getCard(),requestUserId,requestUserName,userType);
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 获取高血压糖尿病随访信息列表
     * @param urlType
     * @param df_id
     * @param mxjbbz //慢性疾病编号（1-高血压3-糖尿病）
     * @return
     */
    public String getMxjbsfList(){
        try {
            FollowUpQVo qvo = (FollowUpQVo) this.getAppJson(FollowUpQVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser!=null){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                String state = PropertiesUtil.getConfValue("openTheInterface");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("mxjbbz",qvo.getMxjbbz());
                jsonParam.put("df_id",qvo.getDf_id());
                jsonParam.put("urlType",qvo.getUrlType());
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getMxjbsfList";
                String str = null;
                if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getDf_id(),"getMxjbsfList");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getMxjbsfList");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<MxjbsfDTO> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<MxjbsfDTO>>() {}.getType());
                        if(ls != null && ls.size()>0) {
                            this.getAjson().setRows(ls);
                        }else{
                            ls = new ArrayList<MxjbsfDTO>();
                            this.getAjson().setRows(ls);
                        }
                    }else{
                        List<MxjbsfDTO> ls = new ArrayList<MxjbsfDTO>();
                        this.getAjson().setRows(ls);
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
     * 获取高血压糖尿病随访详细信息(对应4.7接口，其中 慢性疾病标志(1-高血压患者用药情况，2-糖尿病患者用药情况))
     * @param sfid00 随访编号
     * @param urlType
     * @param df_id 居民档案号
     * @param zzbh_id 症状编号
     * @param mxjbbz
     * @return
     */
    public String getMxjbsf(){
        try {
            FollowUpQVo qvo = (FollowUpQVo) this.getAppJson(FollowUpQVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser!=null){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                String state = PropertiesUtil.getConfValue("openTheInterface");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("sfid00", qvo.getSfid00());
                jsonParam.put("zzbhid", qvo.getZzbhid());
                jsonParam.put("mxjbbz", qvo.getMxjbbz());
                jsonParam.put("df_id", qvo.getDf_id());
                jsonParam.put("urlType",qvo.getUrlType());

                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getMxjbsf";
                String str = null;
                if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getSfid00(),"getMxjbsf");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getMxjbsf");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                        MxjbsfDTO ls = JsonUtil.fromJson(jsonall.get("vo").toString(),MxjbsfDTO.class);
                        this.getAjson().setVo(ls);
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
     * 获取家签服务项目列表
     * @return
     */
    public String findHomeServiceItems(){
        try {
            HomeServiceItemsQvo qvo = (HomeServiceItemsQvo) this.getAppJson(HomeServiceItemsQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().findHomeServiceItems(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                        HomeServiceItemsEntity entity = JsonUtil.fromJson(jsonall.get("vo").toString(),HomeServiceItemsEntity.class);
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setVo(entity);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
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
     * 取居民所有体检记录和指定日期体检记录
     * @return
     */
    public String findJmjktjList(){
        try {
            JmjktjQvo qvo = (JmjktjQvo) this.getAppJson(JmjktjQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().findJmjktjList(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<JmjktjDTO> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<JmjktjDTO>>() {}.getType());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(ls);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
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
     * 获取居民体检记录详细信息
     * @return
     */
    public String getJmjktj() {
        try{
            JmjktjQvo qvo = (JmjktjQvo) this.getAppJson(JmjktjQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().getJmjktj(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<JmjktjAllDTO> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<JmjktjAllDTO>>() {}.getType());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(ls);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 出生医学证明
     * @return
     */
    public String findBirthCertificate() {
        try{
            BirthCertificateQvo qvo = (BirthCertificateQvo) this.getAppJson(BirthCertificateQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().findBirthCertificate(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<Fy_csyxzm> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<Fy_csyxzm>>(){}.getType());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(ls);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 获取新生儿家庭访视记录
     * @return
     */
    public String findNeonateList() {
        try{
            NeonateQvo qvo = (NeonateQvo) this.getAppJson(NeonateQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().findNeonateList(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<Fy_etfsjlDTO> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<Fy_etfsjlDTO>>() {}.getType());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(ls);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 获取传染病报告
     * @return
     */
    public String findInfectiousReport() {
        try{
            NeonateQvo qvo = (NeonateQvo) this.getAppJson(NeonateQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().findInfectiousReport(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                        T_im_refDTO entity = JsonUtil.fromJson(jsonall.get("vo").toString(),T_im_refDTO.class);
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setVo(entity);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 根据身份证号获取患者转出信息
     * @return
     */
    public String getReferralOrg() {
        try{
            ReferralQvo qvo = (ReferralQvo) this.getAppJson(ReferralQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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

                String str = this.getSysDao().getSecurityCardAsyncBean().getReferralOrg(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<ReferralOrg> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<ReferralOrg>>() {}.getType());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(ls);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     *  获取转诊记录
     * @return
     */
    public String getReferralRcord() {
        try{
            ReferralQvo qvo = (ReferralQvo) this.getAppJson(ReferralQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().getReferralRcord(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<Fjzl_zrzcDTO> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<Fjzl_zrzcDTO>>() {}.getType());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(ls);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 首次产前随访服务
     * @return
     */
    public String findSccqsf(){
        try{
            HomeServiceItemsQvo qvo = (HomeServiceItemsQvo)getAppJson(HomeServiceItemsQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().findSccqsf(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<Fy_sccqsfDTO1> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<Fy_sccqsfDTO1>>() {}.getType());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(ls);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 产前随访服务
     * @return
     */
    public String findCqsf(){
        try{
            HomeServiceItemsQvo qvo = (HomeServiceItemsQvo)getAppJson(HomeServiceItemsQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().findCqsf(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<Fy_edwcsfDTO1> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<Fy_edwcsfDTO1>>() {}.getType());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(ls);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 产后访视
     * @return
     */
    public String findChsf(){
        try{
            HomeServiceItemsQvo qvo = (HomeServiceItemsQvo)getAppJson(HomeServiceItemsQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().findChsf(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<Fy_chfsjlDTO1> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<Fy_chfsjlDTO1>>() {}.getType());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(ls);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 重性精神病患者个人信息登记
     * @return
     */
    public String findZxjsbxx(){
        try{
            JmjktjQvo qvo = (JmjktjQvo) this.getAppJson(JmjktjQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().findZxjsbxx(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<Mb_zxjsjbDTO> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<Mb_zxjsjbDTO>>() {}.getType());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(ls);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 获取重性精神疾病随访详细信息
     * @return
     */
    public String findZxjsbsf(){
        try{
            JmjktjQvo qvo = (JmjktjQvo) this.getAppJson(JmjktjQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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

                String str = this.getSysDao().getSecurityCardAsyncBean().findZxjsbsf(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                        Mb_zxjsjbDTO1 entity = JsonUtil.fromJson(jsonall.get("vo").toString(),Mb_zxjsjbDTO1.class);
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setVo(entity);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }



    /**
     * 获取重性精神疾病随访详细信息列表
     * @return
     */
    public String findZxjsbsfList(){
        try{
            JmjktjQvo qvo = (JmjktjQvo) this.getAppJson(JmjktjQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().findZxjsbsfList(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        List<T_zxjsjb_sfDTO> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<T_zxjsjb_sfDTO>>() {}.getType());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(ls);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 保存基卫转诊数据
     * @return
     */
    public String addBasicReferral(){
        try{
            AppDrReferralQvo qvo = (AppDrReferralQvo)this.getAppJson(AppDrReferralQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                    //保存转诊信息
                    String str = sysDao.getAppRefarralTableDao().addReferral(qvo);
                    if("true".equals(str)){
                        this.getAjson().setMsgCode("800");
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(str);
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
     * 获取居民健康档案
     * @return
     */
    public String getHealthCareRecords() {
        try{
            HomeServiceItemsQvo qvo = (HomeServiceItemsQvo) this.getAppJson(HomeServiceItemsQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                        if(jsonall.get("entity") != null) {
                            JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                            if(AddressType.ND.getValue().equals(qvo.getUrlType()) || AddressType.PINGT.getValue().equals(qvo.getUrlType())){//宁德居民基础信息
                                if("800".equals(entity.get("msgCode").toString())){
                                    List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(),new TypeToken<List<AppEnterpatientEntity>>() {}.getType());
                                    if(list != null && list.size()>0){
                                        T_dwellerfile file = new T_dwellerfile();
                                        file.setCsrq(list.get(0).getBirthday().replaceAll("-",""));
                                        file.setJdrq(ExtendDate.getYYYYMMD(ExtendDate.getCalendar(list.get(0).getCdate())));
                                        file.setJmdah(list.get(0).getDf_id());
                                        file.setJmxm(list.get(0).getName());
//                                        file.setJtdah(list.get(0).get);
                                        file.setJwhbh(list.get(0).getRef_cjid());
                                        file.setMphm(list.get(0).getAdrss_hnumber());
                                        file.setSfyxda(list.get(0).getSfyxda());
                                        file.setTel(list.get(0).getTelphone());
                                        file.setSfzh(list.get(0).getIdcardno());
                                        if(StringUtils.isNotBlank(list.get(0).getAdress_pro())){
                                            CdAddress addPro = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_pro());
                                            if(addPro != null){
                                                file.setSheng(addPro.getAreaSname());
                                            }
                                        }
                                        if(StringUtils.isNotBlank(list.get(0).getAdress_city())){
                                            CdAddress addCity = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_city());
                                            if(addCity != null){
                                                file.setShi(addCity.getAreaSname());
                                            }
                                        }
                                        if(StringUtils.isNotBlank(list.get(0).getAdress_county())){
                                            CdAddress addCoun = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_county());
                                            if(addCoun != null){
                                                file.setXian(addCoun.getAreaSname());
                                            }
                                        }
                                        if(StringUtils.isNotBlank(list.get(0).getAdress_rural())){
                                            CdAddress addRural = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_rural());
                                            if(addRural != null){
                                                file.setXiang(addRural.getAreaSname());
                                            }
                                        }
                                        if(StringUtils.isNotBlank(list.get(0).getAdress_village())){
                                            file.setXzqydm(list.get(0).getAdress_village());
                                            CdAddress address = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_village());
                                            if(address != null){
                                                file.setCun(address.getAreaSname());
                                            }
                                        }
                                        file.setXb(list.get(0).getSex());
                                        this.getAjson().setVo(file);
                                    }
                                }
                            }else{
                                if ("true".equals(entity.get("success").toString())) {
                                    T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                    this.getAjson().setVo(entitys);
                                }
                            }
//                            if ("true".equals(entity.get("success").toString())) {
//                                T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
//                                this.getAjson().setVo(entitys);
//                            }
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询居民档案号和服务类型
     * @return
     */
    public String findFileAndFwType(){
        try{
            AppDrUser drUser = this.getAppDrUser();
            Map<String,Object> map = new HashMap<>();
            map.put("jmdnh",null);
            map.put("orgId",null);
            map.put("fwlx",null);
            if(drUser != null){
                if(StringUtils.isNotBlank(drUser.getDrHospId())){
                    AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                    if(dept != null){
                        HomeServiceItemsQvo qqvo = (HomeServiceItemsQvo)this.getAppJson(HomeServiceItemsQvo.class);
                        qqvo.setType("2");
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, dept.getHospAreaCode().substring(0,4));
                        if(value!=null){
                            qqvo.setUrlType(value.getCodeTitle());
                        }
                        String patientAddress = null;
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,drUser.getId(),drUser.getDrName(),DrPatientType.DR.getValue());
                        if(StringUtils.isNotBlank(str)){
                            T_dwellerfile file = sysDao.getSecurityCardAsyncBean().analysisFileOne(str, qqvo.getUrlType());
                            if(file != null){
                                map.put("jmdnh",file.getJmdah());
                                String result = "";
                                if (AddressType.FZ.getValue().equals(value.getCodeTitle())) {

                                } else if (AddressType.QZ.getValue().equals(value.getCodeTitle())) {
                                    result = "qz_";
                                } else if (AddressType.ZZ.getValue().equals(value.getCodeTitle())) {
                                    result = "zz_";
                                } else if (AddressType.PT.getValue().equals(value.getCodeTitle())) {
                                    result = "pt_";
                                } else if (AddressType.NP.getValue().equals(value.getCodeTitle())) {
                                    result = "np_";
                                } else if (AddressType.SM.getValue().equals(value.getCodeTitle())) {
                                    result = "sm_";
                                } else if (AddressType.LY.getValue().equals(value.getCodeTitle())) {
                                    result = "ly_";
                                } else if (AddressType.ND.getValue().equals(value.getCodeTitle())) {
                                    result = "nd_";
                                } else if (AddressType.XM.getValue().equals(value.getCodeTitle())) {
                                    result = "xm_";
                                } else if (AddressType.PINGT.getValue().equals(value.getCodeTitle())) {
                                    result = "pg_";
                                }
                                map.put("orgId",result+file.getSqh());
                            }
                        }
                        com.ylz.bizDo.jtapp.drEntity.AppDrPatientFwEntity vv = this.sysDao.getAppSignformDao().findFwTypeByIdno(qqvo.getIdno(),value.getCodeTitle(),drUser.getId(),drUser.getDrName(),DrPatientType.DR.getValue());
                        map.put("fwlx",vv);
                    }
                }
            }
            this.getAjson().setMsgCode("800");
            this.getAjson().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 根据身份证、机构ID、厂商dataSource获取健康体检数据公共接口
     * @return
     */
    public String findHealthByIdnoAndOrg(){
        try {
            AppAioQvo qvo = (AppAioQvo) this.getAppJson(AppAioQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
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
                String str = this.getSysDao().getSecurityCardAsyncBean().findHealthByIdnoAndOrg(qvo,requestUserId,requestUserName,userType);
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                        this.getAjson().setEntity(jsonall.get("entity").toString());
                        this.getAjson().setMsgCode("800");
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(jsonall.get("msg").toString());
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
}
