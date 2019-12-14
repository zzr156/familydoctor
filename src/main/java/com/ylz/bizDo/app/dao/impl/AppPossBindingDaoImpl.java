package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppPossBindingDao;
import com.ylz.bizDo.app.entity.AppSignServiceRecordEntity;
import com.ylz.bizDo.app.po.AppPossBinding;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.po.AppSignServiceRecord;
import com.ylz.bizDo.jtapp.commonEntity.AppPossEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppPossPatientEntity;
import com.ylz.bizDo.jtapp.commonVo.AppPossPatientVo;
import com.ylz.bizDo.jtapp.commonVo.AppPossQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/8/20.
 */
@Service("appPossBindingDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppPossBindingDaoImpl implements AppPossBindingDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public AppPossBinding findByPossSn(AppPossQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("POSS_SN",qvo.getPossSn());
        String sql = "SELECT * FROM APP_POSS_BINDING WHERE POSS_SN =:POSS_SN ";
        List<AppPossBinding> list = sysDao.getServiceDo().findSqlMap(sql,map,AppPossBinding.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean findByGluSn(String gluSn) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("gluSn",gluSn);
        String sql = "SELECT * FROM APP_POSS_BINDING WHERE GLUCOMETER_SN=:gluSn";
        List<AppPossBinding> flag = sysDao.getServiceDo().findSqlMap(sql,map,AppPossBinding.class);
        if(flag != null && flag.size()>0){
            return false;
        }
        return true;
    }

    @Override
    public boolean findBySphSn(String sphSn) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("sphSn",sphSn);
        String sql = "SELECT * FROM APP_POSS_BINDING WHERE SPHYGMOMANOMETER_SN=:sphSn";
        List<AppPossBinding> flag = sysDao.getServiceDo().findSqlMap(sql,map,AppPossBinding.class);
        if(flag != null && flag.size()>0){
            return false;
        }
        return true;
    }

    @Override
    public AppPossPatientEntity findInformationByPatient(AppPossQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("patientId",qvo.getPatientId());
        String sql = " SELECT\n" +
                "\ta.ID patientId,\n" +
                "\ta.PATIENT_NAME patientName,\n" +
                "\ta.PATIENT_GENDER patientSex,\n" +
                "\ta.PATIENT_IDNO patientIdno,\n" +
                "\ta.PATIENT_BIRTHDAY patientBirthDay,\n" +
                "\ta.PATIENT_ADDRESS patientAddr,\n" +
                "\ta.PATIENT_TEL patientTel,\n" +
                "\tb.ID signId,\n" +
                "\tb.SIGN_FROM_DATE signFromDate,\n" +
                "\tb.SIGN_TO_DATE signToDate,\n" +
                "\tb.SIGN_DR_ID signDrId,\n" +
                "\t(SELECT DR_NAME FROM app_dr_user WHERE ID = b.SIGN_DR_ID) signDrName,\n" +
                "\t(SELECT DR_TEL FROM app_dr_user WHERE ID = b.SIGN_DR_ID) signDrTel,\n" +
                "\tb.SIGN_DR_ASSISTANT_ID signDrAssistantId,\n" +
                "\t(SELECT DR_NAME FROM app_dr_user WHERE ID = b.SIGN_DR_ASSISTANT_ID) signDrAssistantName,\n" +
                "\t(SELECT DR_TEL FROM app_dr_user WHERE ID = b.SIGN_DR_ASSISTANT_ID) signDrAssistantTel,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_SIGN_ID = b.ID AND LABEL_TYPE = '3') fwValue,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) FROM app_label_group WHERE LABEL_SIGN_ID = b.ID AND LABEL_TYPE = '3') fwTitle,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_economics WHERE LABEL_SIGN_ID = b.ID ) jjValue,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) FROM app_label_economics WHERE LABEL_SIGN_ID = b.ID ) jjTitle,\n" +
                "\ta.PATIENT_EMERGENCY_CONTACT_NAME emergencyContactName,\n" +
                "\ta.PATIENT_EMERGENCY_CONTACT_TEL emergencyContactTel\n" +
                "\tFROM\n" +
                "\tapp_patient_user a\n" +
                "\tINNER JOIN app_sign_form b ON a.ID = b.SIGN_PATIENT_ID\n" +
                "\tWHERE\n" +
                "\t1 = 1\n" +
                "\tAND a.ID = :patientId\n";

        if(StringUtils.isNotBlank(qvo.getSignState())){
            map.put("SIGN_STATE",qvo.getSignState().split(";"));
            sql +=  "\tAND b.SIGN_STATE IN (:SIGN_STATE)\n" ;
        }
        if(StringUtils.isNotBlank(qvo.getSignId())){
            map.put("signId",qvo.getSignId());
            sql += " AND b.ID = :signId ";
        }
        sql +=  "\tGROUP BY a.ID\n";
        List<AppPossPatientEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPossPatientEntity.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public AppSignServiceRecord saveInformation(AppPossPatientVo qvo) throws Exception {
        AppSignServiceRecord vo = null;
//        if(StringUtils.isNotBlank(qvo.getId())){
//            vo = (AppSignServiceRecord)sysDao.getServiceDo().find(AppSignServiceRecord.class,qvo.getId());
//            vo.setSsrPatientId(qvo.getPatientId());//签约居民主键
//            vo.setSsrPatientName(qvo.getPatientName());//签约居民姓名
//            vo.setSsrServiTime(Calendar.getInstance());//服务时间
//            vo.setSsrServeAddr(qvo.getServeAddr());//服务地点
//            vo.setSsrHP(Integer.parseInt(qvo.getHp()));//血压（高压）
//            vo.setSsrLP(Integer.parseInt(qvo.getLp()));//血压（低压）
//            vo.setSsrBloodSugar(Integer.parseInt(qvo.getBloodSugar()));//血糖值
//            vo.setSsrContentValue(qvo.getContentValue());//服务内容
//            vo.setSsrMainSituation(qvo.getMainSituation());//主要情况告知
//            vo.setSsrHealthGuidance(qvo.getHealthGuidance());//健康指导
//            vo.setSsrPatientAutograph(qvo.getPatientAutograph());//接受服务对象签名
//            vo.setSsrDrAutograph(qvo.getDrAutograph());//医生签名
//            vo.setSsrSignId(qvo.getSignId());//签约单主键
//            vo.setSsrEmergencyContactName(qvo.getEmergencyContactName());//紧急联系人
//            vo.setSsrEmergencyContactTel(qvo.getEmergencyContactTel());//紧急联系人电话
//            sysDao.getServiceDo().modify(vo);
//        }else{
            vo = new AppSignServiceRecord();
            vo.setSsrPatientId(qvo.getPatientId());//签约居民主键
            vo.setSsrPatientName(qvo.getPatientName());//签约居民姓名
            vo.setSsrServeTime(Calendar.getInstance());//服务时间
            vo.setSsrServeAddr(qvo.getServeAddr());//服务地点
            if(StringUtils.isNotBlank(qvo.getHp())){
                vo.setSsrHP(Integer.parseInt(qvo.getHp()));//血压（高压）
            }
            if(StringUtils.isNotBlank(qvo.getLp())){
                vo.setSsrLP(Integer.parseInt(qvo.getLp()));//血压（低压）
            }
            vo.setSsrBloodSugar(qvo.getBloodSugar());//血糖值
            vo.setSsrContentValue(qvo.getContentValue());//服务内容
            vo.setSsrMainSituation(qvo.getMainSituation());//主要情况告知
            vo.setSsrHealthGuidance(qvo.getHealthGuidance());//健康指导
            if(StringUtils.isNotBlank(qvo.getPatientAutograph())){
                Map<String, Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getPatientAutograph(), CommonShortType.YISHENG.getValue());
                String url = map.get("objectUrl").toString();
                String urlTop = PropertiesUtil.getConfValue("urlTop") + url.substring(url.indexOf("/picture"), url.length());
                vo.setSsrPatientAutograph(url);//接受服务对象签名外网地址
                vo.setSsrPatientWithin(urlTop);//接受服务对象签名内网地址
            }
            vo.setSsrDrAutograph(qvo.getDrAutograph());//医生签名
            vo.setSsrSignId(qvo.getSignId());//签约单主键
            if(StringUtils.isNotBlank(qvo.getSignId())){
                AppSignForm form = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,qvo.getSignId());
                if(form != null){
                    vo.setSsrAreaCode(form.getSignAreaCode());
                }
            }
            vo.setSsrEmergencyContactName(qvo.getEmergencyContactName());//紧急联系人
            vo.setSsrEmergencyContactTel(qvo.getEmergencyContactTel());//紧急联系人电话
            vo.setSsrDrId(qvo.getDrId());//医生主键
            sysDao.getServiceDo().add(vo);
//        }
        return vo;
    }

    @Override
    public List<AppPossEntity> findSignServeRecordList(AppPossQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String sql = " SELECT\n" +
                "\tID id,\n" +
                "\tSSR_CONTENT_VALUE content,\n" +
                "\tSSR_SERVE_TIME time,\n" +
                "SSR_DR_ID drId," +
                "(SELECT DR_NAME FROM app_dr_user WHERE ID = SSR_DR_ID ) drName " +
                "FROM\n" +
                "\tapp_sign_service_record\n" +
                "WHERE 1=1\n";
        map.put("patientId",qvo.getPatientId());
        sql += " AND SSR_PATIENT_ID = :patientId ";
        if(StringUtils.isNotBlank(qvo.getYear())){
            map.put("yearStart",qvo.getYear());
            sql += " AND DATE_FORMAT(SSR_SERVE_TIME, '%Y') = :yearStart ";
        }
        List<AppPossEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPossEntity.class,qvo);
        return list;
    }

    @Override
    public AppSignServiceRecordEntity findSignServeRecord(String id) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        String sql = "SELECT \n" +
                "\tb.ID id,\n" +
                "  b.SSR_PATIENT_ID  ssrPatientId,\n" +
                "  b.SSR_PATIENT_NAME  ssrPatientName,\n" +
                "  b.SSR_SERVE_TIME  ssrServeTime,\n" +
                "  b.SSR_SERVE_ADDR  ssrServeAddr,\n" +
                "  b.SSR_HP  ssrHP,\n" +
                "  b.SSR_LP  ssrLP,\n" +
                "  b.SSR_BLOOD_SUGAR  ssrBloodSugar,\n" +
                "  b.SSR_CONTENT_VALUE  ssrContentValue,\n" +
                "  b.SSR_MAIN_SITUATION  ssrMainSituation,\n" +
                "  b.SSR_HEALTH_GUIDANCE  ssrHealthGuidance,\n" +
                "  b.SSR_PATIENT_AUTOGRAPH  ssrPatientAutograph,\n" +
                "  b.SSR_PATIENT_WITHIN  ssrPatientWithin,\n" +
                "  b.SSR_DR_AUTOGRAPH  ssrDrAutograph,\n" +
                "  b.SSR_SIGN_ID  ssrSignId,\n" +
                "  b.SSR_EMERGENCY_CONTACT_NAME  ssrEmergencyContactName,\n" +
                "  b.SSR_EMERGENCY_CONTACT_TEL  ssrEmergencyContactTel,\n" +
                "  b.SSR_DR_ID  ssrDrId\n" +

//                "\t(SELECT DR_NAME FROM app_dr_user WHERE ID = b.SSR_DR_ID) signDrName,\n" +
//                "\t(SELECT DR_TEL FROM app_dr_user WHERE ID = b.SSR_DR_ID) signDrTel,\n" +
//                "\tc.SIGN_DR_ASSISTANT_ID signDrAssistantId,\n" +
//                "\t(SELECT DR_NAME FROM app_dr_user WHERE ID = c.SIGN_DR_ASSISTANT_ID) signDrAssistantName,\n" +
//                "\t(SELECT DR_TEL FROM app_dr_user WHERE ID = c.SIGN_DR_ASSISTANT_ID) signDrAssistantTel,\n" +
//                "\t(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_SIGN_ID = b.SSR_SIGN_ID AND LABEL_TYPE = '3') fwValue,\n" +
//                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) FROM app_label_group WHERE LABEL_SIGN_ID = b.SSR_SIGN_ID AND LABEL_TYPE = '3') fwTitle,\n" +
//                "\t(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_economics WHERE LABEL_SIGN_ID = b.SSR_SIGN_ID ) jjValue,\n" +
//                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) FROM app_label_economics WHERE LABEL_SIGN_ID = b.SSR_SIGN_ID ) jjTitle,\n" +
//                "\tc.SIGN_FROM_DATE signFromDate,\n" +
//                "\tc.SIGN_TO_DATE signToDate,\n" +
//                "\ta.PATIENT_GENDER patientSex,\n" +
//                "\ta.PATIENT_IDNO patientIdno,\n" +
//                "\ta.PATIENT_BIRTHDAY patientBirthDay,\n" +
//                "\ta.PATIENT_ADDRESS patientAddr,\n" +
//                "\ta.PATIENT_TEL patientTel\n" +
                "FROM app_sign_service_record b " +
//                "LEFT JOIN app_patient_user a ON a.ID = b.SSR_PATIENT_ID " +
//                "LEFT JOIN APP_SIGN_FORM c ON b.SSR_SIGN_ID = c.ID \n" +
                "WHERE 1=1 ";
        sql += " AND b.ID =:id ";
        List<AppSignServiceRecordEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSignServiceRecordEntity.class);
        if(list != null && list.size()>0){
            AppSignServiceRecordEntity vv = list.get(0);
            AppPossQvo qqvo = new AppPossQvo();
            qqvo.setPatientId(vv.getSsrPatientId());
            qqvo.setSignId(vv.getSsrSignId());
            AppPossPatientEntity vo = sysDao.getAppPossBindingDao().findInformationByPatient(qqvo);
            if(vo != null){
                vv.setFwTitle(vo.getFwTitle());
                vv.setFwValue(vo.getFwValue());
                vv.setJjTitle(vo.getJjTitle());
                vv.setJjValue(vo.getJjValue());
                vv.setPatientAddr(vo.getPatientAddr());
                vv.setPatientBirthDay(vo.getPatientBirthDay());
                vv.setPatientIdno(vo.getPatientIdno());
                vv.setPatientSex(vo.getPatientSex());
                vv.setPatientTel(vo.getPatientTel());
                vv.setSignDrAssistantId(vo.getSignDrAssistantId());
                vv.setSignDrAssistantName(vo.getSignDrAssistantName());
                vv.setSignDrAssistantTel(vo.getSignDrAssistantTel());
                vv.setSignDrName(vo.getSignDrName());
                vv.setSignDrTel(vo.getSignDrTel());
                vv.setSignFromDate(vo.getSignFromDate());
                vv.setSignToDate(vo.getSignToDate());
            }
            return vv;
        }
        return null;
    }
}
