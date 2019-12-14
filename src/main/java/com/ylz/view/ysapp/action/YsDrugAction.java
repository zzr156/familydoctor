package com.ylz.view.ysapp.action;


import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppDrugEntity;
import com.ylz.bizDo.jtapp.commonVo.AppDrugVo;
import com.ylz.bizDo.jtapp.drEntity.AppDrDrugGuideEntity;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.ExtendDate;
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
        value = "ysDrug",
        results = {
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"})
        }
)
public class YsDrugAction extends CommonAction {

    /**
     * 添加药品(用药模板)
     *
     * @param drugType
     * @param drugName
     * @param period
     * @param usage
     * @param taking
     * @param frequency
     * @param note
     * @param periodOther
     * @param frequencyOther
     * @return
     */
    public String appAddDrug() {
        try {
            AppDrugVo vo = (AppDrugVo) getAppJson(AppDrugVo.class);
            if (vo != null) {
                AppDrUser drUser = this.getAppDrUser();
                AppDrug drug = new AppDrug();
                drug.setDrugName(vo.getDrugName());//药品名称
                drug.setDrugType(vo.getDrugType());//药品类型
                drug.setDrugPeriod(vo.getPeriod());//服用周期
                drug.setDrugUsage(vo.getUsage());//用法
//                drug.setDrugTaking(vo.getTaking());//服用时间
                drug.setDrugFrequency(vo.getFrequency());//频次
                drug.setDrugNote(vo.getNote());//备注
                drug.setDrugBatch(vo.getDrugBatch());//批次
                drug.setDrugUseLevel(vo.getDrugUseLevel());//用量
                drug.setDrugDosageUnit(vo.getDrugDosageUnit());//用量单位
                drug.setDrugSpec(vo.getDrugSpec());//规格
                drug.setDrugPharmAcology(vo.getDrugPharmAcology());//药理
                drug.setDrugDrId(drUser.getId());//创建医生
                drug.setDrugHospId(drUser.getDrHospId());//创建医院
                if (StringUtils.isNotBlank(vo.getPeriodOther())) {
                    drug.setDrugPeriodOther(vo.getPeriodOther());
                }
                if (StringUtils.isNotBlank(vo.getFrequencyOther())) {
                    drug.setDrugFrequencyOther(vo.getFrequencyOther());
                }
                drug.setDrugAddTime(Calendar.getInstance());
                sysDao.getServiceDo().add(drug);
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("保存成功");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }



    /**
     * 药品名称模糊搜索
     *
     * @param drugName
     * @return
     */
    public String appFindDrug() {
        try {
            AppDrugVo vo = (AppDrugVo) getAppJson(AppDrugVo.class);
            if (vo != null) {
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    vo.setDrugHospId(drUser.getDrHospId());
                }
                List<AppDrugEntity> list = sysDao.getAppDrugDao().findByDrugName(vo.getDrugName(),vo.getDrugHospId());
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询所有药品
     *
     * @param 分页参数
     * @return
     */
    public String appFindAllDrug() {
        try {
            AppDrugVo vo = (AppDrugVo) getAppJson(AppDrugVo.class);
            if (vo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            } else {
                AppDrUser user = this.getAppDrUser();
                HashMap<String, Object> map = new HashMap<>();
                map.put("DRUG_HOSP_ID",user.getDrHospId());
                String sql = "select * from APP_DRUG t WHERE t.DRUG_HOSP_ID = :DRUG_HOSP_ID ";
                if(StringUtils.isNotBlank(vo.getDrugName())){
                    map.put("DRUG_NAME","%"+vo.getDrugName()+"%");
                    sql += " AND t.DRUG_NAME like :DRUG_NAME ";
                }
                sql += " order by DRUG_ADD_TIME desc ";
                List<AppDrug> list = sysDao.getServiceDo().findSqlMap(sql, map, AppDrug.class, vo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
                this.getAjson().setVo(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 添加用药指导
     *
     * @param drugId
     * @param drugName
     * @param patientId      可以有多个
     * @param period
     * @param frequency
     * @param usage
     * @param taking
     * @param drugBeginTime
     * @param note
     * @param periodOther
     * @param frequencyOther
     * @return
     */
    public String appDrugGuide() {
        try {
            AppDrugVo vo = (AppDrugVo) getAppJson(AppDrugVo.class);
            AppDrUser user = getAppDrUser();
            if (vo != null && user != null) {
                sysDao.getAppDrugGuideDao().addGuide(user, vo);
                this.getAjson().setMsg("发送成功");
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 药品数据初始化
     */
    public String appInintDrug() {
        try {
            //药品类别
            List<CdCode> typeList = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_DRUGTYPE, CommonEnable.QIYONG.getValue());
            //使用方法
            List<CdCode> usageList = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_USAGE, CommonEnable.QIYONG.getValue());
            //用药时间
            List<CdCode> takeList = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_TAKING, CommonEnable.QIYONG.getValue());
            //周期
            List<CdCode> periodList = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_PERIOD, CommonEnable.QIYONG.getValue());
            //频次
            List<CdCode> freqList = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FREQUENCY, CommonEnable.QIYONG.getValue());
            //药理
            List<CdCode> pharmList = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_PHARMACOLOGY, CommonEnable.QIYONG.getValue());
            //用量单位
            List<CdCode> dosageUnitList = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_DRUGDOSAGEUNIT, CommonEnable.QIYONG.getValue());

            //List<AppDrug> drugList = sysDao.getServiceDo().findAll(AppDrug.class);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("type", typeList);
            map.put("usage", usageList);
            map.put("takeTime", takeList);
            map.put("period", periodList);
            map.put("frequency", freqList);
            map.put("pharmacology",pharmList);
            map.put("dosageunit",dosageUnitList);
            //map.put("drug",drugList);
            this.getAjson().setMap(map);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 再次提醒
     *
     * @param drugGuideId
     */
    public String appAlertAgain() {
        try {
            AppDrugVo vo = (AppDrugVo) getAppJson(AppDrugVo.class);
            AppDrugGuide guide = (AppDrugGuide) sysDao.getServiceDo().find(AppDrugGuide.class, vo.getDrugGuideId());
            if (vo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
                return "aJson";
            }
            double warnNum = 10;
            if (guide.getDgWarnNum() != null) {
                warnNum = Double.parseDouble(guide.getDgWarnNum());
            } else if (guide.getDgCommonWarnNum() != null) {
                warnNum = Double.parseDouble(guide.getDgCommonWarnNum());
            }
            int leftday = ExtendDate.getDayNumBetween2Date(Calendar.getInstance(), guide.getDgEndTime());
            double freq = Double.parseDouble(guide.getDgFrequency());
            if ((leftday > 0 && leftday * freq < warnNum) || (leftday == 0 && freq < warnNum)) {
                AppDrug drug = (AppDrug) sysDao.getServiceDo().find(AppDrug.class, guide.getDgDrugId());
                AppPatientUser patient = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, guide.getDgPatientId());

                String num = "0";
                if (leftday != 0) {
                    num = String.valueOf((int) leftday * freq);
                } else if (freq >= 1) {
                    num = String.valueOf((int) freq);
                }
                if (drug != null && patient != null) {
                    String title = drug.getDrugName() + ",剩余次数：" + num;
                    String content1 = "您的" + drug.getStrDrugType() + ":" + drug.getDrugName() + " 可使用次数低于" + String.valueOf((int) warnNum) + "次，请及时就诊";
                    String content2 = patient.getPatientName() + "的" + drug.getStrDrugType() + ":" + drug.getDrugName() + " 可使用次数低于" + String.valueOf((int) warnNum) + "次，请及时就诊";
                    //发给患者
                    List<AppWarningSetting> setList = sysDao.getAppWarningSettingDao().findSetting(guide.getDgPatientId(), CommonWarnSet.YYDQ.getValue());
                    if (!setList.isEmpty() && setList.get(0).getWsState().equals(CommonEnable.QIYONG.getValue()) || setList.isEmpty()) {
                        //sysDao.getAppNoticeDao().addNotices(title, content1, NoticesType.YYDQJJ.getValue(), guide.getDgDocId(), guide.getDgPatientId(), guide.getId(), DrPatientType.PATIENT.getValue());
                        sysDao.getAppNoticeDao().addNotices(title, content1, NoticesType.YYDQJJ.getValue(), guide.getDgDocId(), guide.getDgPatientId(), "", DrPatientType.PATIENT.getValue());
                    }
                    //发给医生
//                    sysDao.getAppNoticeDao().addNotices(title, content2, NoticesType.YYDQJJ.getValue(), guide.getDgDocId(), guide.getDgDocId(), guide.getId(), DrPatientType.DR.getValue());
                    sysDao.getAppNoticeDao().addNotices(title, content2, NoticesType.YYDQJJ.getValue(), guide.getDgDocId(), guide.getDgDocId(), "", DrPatientType.DR.getValue());
                }
            }
            this.getAjson().setMsg("提醒成功");
            this.getAjson().setMsgCode("800");

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 添加药品到个人药库
     *
     * @param drugId
     * @param patientId 可能有多个
     */
    public String appAddPersonDrug() {
        try {
            AppDrugVo vo = (AppDrugVo) getAppJson(AppDrugVo.class);
            if (vo != null && vo.getPatientId()!=null) {
                String[] patientIds = vo.getPatientId().split(";");
                for(int i=0; i< patientIds.length ; i++){
                    List<AppPersonDrug> list = sysDao.getAppDrugDao().findUniqueDrug(patientIds[i], vo.getDrugId());
                    if (list.isEmpty()) {
                        AppPersonDrug personDrug = new AppPersonDrug();
                        personDrug.setPdPatientId(patientIds[i]);
                        personDrug.setPdDrugId(vo.getDrugId());
                        sysDao.getServiceDo().add(personDrug);
                        this.getAjson().setMsg("保存成功");
                        this.getAjson().setMsgCode("800");
                    }
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询个人药品库
     *
     * @param patientId
     * @param drugType
     * @return
     */
    public String appFindPersonDrug() {
        try {
            AppDrugVo vo = (AppDrugVo) getAppJson(AppDrugVo.class);
            if (vo != null && vo.getPatientId() != null) {
                List<AppDrugEntity> list = sysDao.getAppDrugDao().findPersonDrug(vo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 根据药品类别分类列表
     *
     * @param drugType 药品类别
     * @return
     */
    public String appDrugByType() {
        try {
            AppDrugVo vo = (AppDrugVo) getAppJson(AppDrugVo.class);
            if (vo != null) {
                HashMap<String, Object> map = new HashMap<>();
                String sql = "SELECT * FROM APP_DRUG WHERE 1=1 ";
                if (StringUtils.isNotBlank(vo.getDrugType())) {
                    sql += " AND DRUG_TYPE = :drugType ";
                    map.put("drugType", vo.getDrugType());
                }
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    map.put("hospId",drUser.getDrHospId());
                    sql += " AND DRUG_HOSP_ID=:hospId ";
                }
                sql += " ORDER BY CONVERT(DRUG_NAME USING gbk) ";
                List<AppDrug> list = sysDao.getServiceDo().findSqlMap(sql, map, AppDrug.class);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 修改药品
     * @param drugId
     * @param drugType
     * @param drugName
     * @param period
     * @param usage
     * @param taking
     * @param frequency
     * @param note
     * @param periodOther
     * @param frequencyOther
     * @return
     */
    public String appModifyDrug() {
        try {
            AppDrugVo vo = (AppDrugVo) getAppJson(AppDrugVo.class);
            if (vo != null) {
                AppDrug drug = (AppDrug) sysDao.getServiceDo().find(AppDrug.class,vo.getDrugId());
                if(StringUtils.isNotBlank(vo.getDrugType())){
                    drug.setDrugType(vo.getDrugType());//药品类型
                }
                if(StringUtils.isNotBlank(vo.getDrugName())){
                    drug.setDrugName(vo.getDrugName());//药品名称
                }
                if(StringUtils.isNotBlank(vo.getPeriod())){
                    drug.setDrugPeriod(vo.getPeriod());//服用周期
                }
                if(StringUtils.isNotBlank(vo.getFrequency())){
                    drug.setDrugFrequency(vo.getFrequency());//频次
                }
                if(StringUtils.isNotBlank(vo.getUsage())){
                    drug.setDrugUsage(vo.getUsage());//用法
                }
//                if(StringUtils.isNotBlank(vo.getTaking())){
//                    drug.setDrugTaking(vo.getTaking());//服用时间
//                }
                if(StringUtils.isNotBlank(vo.getNote())){
                    drug.setDrugNote(vo.getNote());//备注
                }
                if(StringUtils.isNotBlank(vo.getPeriodOther())){
                    drug.setDrugPeriodOther(vo.getPeriodOther());
                }
                if(StringUtils.isNotBlank(vo.getFrequencyOther())){
                    drug.setDrugFrequencyOther(vo.getFrequencyOther());
                }
                if(StringUtils.isNotBlank(vo.getDrugBatch())){
                    drug.setDrugBatch(vo.getDrugBatch());//批次
                }
                if(StringUtils.isNotBlank(vo.getDrugUseLevel())){
                    drug.setDrugUseLevel(vo.getDrugUseLevel());//用量
                }
                if(StringUtils.isNotBlank(vo.getDrugDosageUnit())){
                    drug.setDrugDosageUnit(vo.getDrugDosageUnit());//用量单位
                }
                if(StringUtils.isNotBlank(vo.getDrugSpec())){
                    drug.setDrugSpec(vo.getDrugSpec());//规格
                }
                if(StringUtils.isNotBlank(vo.getDrugPharmAcology())){
                    drug.setDrugPharmAcology(vo.getDrugPharmAcology());//药理
                }
//                drug.setDrugTaking(vo.getTaking());//服用时间
                sysDao.getServiceDo().modify(drug);
                this.getAjson().setMsg("修改成功");
                this.getAjson().setVo(drug);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 删除药品
     * @param drugId
     * @return
     */
    public String appDeleteDrug() {
        try {
            AppDrugVo vo = (AppDrugVo) getAppJson(AppDrugVo.class);
            if (vo != null && vo.getDrugId()!=null) {
                sysDao.getServiceDo().delete(AppDrug.class,vo.getDrugId());
                List<AppPersonDrug> list = sysDao.getServiceDo().loadByPk(AppPersonDrug.class,"pdDrugId",vo.getDrugId());
                for(AppPersonDrug pd:list){
                    sysDao.getServiceDo().delete(pd);
                }
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 通过用户id查询上一次用药指导
     * @param patientId
     * @return
     */
    public String appFindDrugUpGuide(){
        try {
            AppDrugVo vo = (AppDrugVo)getAppJson(AppDrugVo.class);
            if(vo != null){
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    vo.setDrId(drUser.getId());
                    List<AppDrDrugGuideEntity> ls = sysDao.getAppDrugGuideDao().findByDrOrPatient(vo.getDrId(),vo.getPatientId());
                    this.getAjson().setRows(ls);
                    this.getAjson().setMsgCode("800");
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

}
