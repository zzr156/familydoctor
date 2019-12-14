package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppDrugGuideDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonVo.AppDrugVo;
import com.ylz.bizDo.jtapp.drEntity.AppDrDrugGuideEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrUserEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppDrugGuideBatchEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppDrugGuideEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppDrugReminderEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("appDrugGuideDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppDrugGuideDaoImpl implements AppDrugGuideDao {

    @Autowired
    private SysDao sysDao;

    /**
     * 调度时使用
     * @return
     */
    @Override
    public List<AppDrugGuide> findGuidByTime() throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        map.put("DG_SEND_STATE","0");
        String sql = "select * from APP_DRUG_GUIDE WHERE NOW() between DG_GUIDE_TIME and DG_END_TIME AND DG_SEND_STATE =:DG_SEND_STATE " ;
        return sysDao.getServiceDo().findSqlMap(sql,map,AppDrugGuide.class);
    }

    @Override
    public List<AppDrugGuideEntity> findByPid(AppDrugVo vo) throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        String sql = "SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.DG_DOC_ID doctorId,\n" +
                "\ta.DG_DRUG_ID drugId,\n" +
                "\ta.DG_GUIDE_TIME guideTime,\n" +
                "\ta.DG_DRUG_NAME drugName,\n" +
                "\ta.DG_DRUG_TYPE drugType,\n" +
                "\ta.DG_PERIOD drugPeriod,\n" +
                "\ta.DG_FREQUENCY drugFrequency,\n" +
                "\ta.DG_TAKING drugTaking,\n" +
                "\ta.DG_USAGE drugUsage,\n" +
                "\ta.DG_PERIOD_OTHER periodOther,\n" +
                "\ta.DG_FREQUENCY_OTHER frequencyOther,\n" +
                "\ta.DG_DRUG_BEGIN_TIME drugBeginTime,\n" +
                "\ta.DG_GUIDE_NOTE note,\n" +
                "\ta.DG_SPEC dgSpec,\n" +
                "\ta.DG_BATCH dgBatch,\n" +
                "\ta.DG_USE_LEVEL dgUseLevel,\n" +
                "\ta.DG_DOSAGE_UNIT dgDosageUnit,\n" +
                "\ta.DG_ALL_USE_LEVEL dgAllUseLevel ,\n" +
                "  a.DG_ALL_DOSAGE_UNIT dgAllDosageUnit\n" +
                "FROM\n" +
                "\tAPP_DRUG_GUIDE a\n" +
                "WHERE\n" +
                "\t1 = 1";
        if(StringUtils.isNotBlank(vo.getDgBatchNum())){
            map.put("DG_BATCH_NUM",vo.getDgBatchNum());
            sql += " AND a.DG_BATCH_NUM = :DG_BATCH_NUM";
        }
        sql +=" ORDER BY A.DG_GUIDE_TIME DESC ";
        return sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrugGuideEntity.class,vo);
    }

    @Override
    public List<AppDrugGuideBatchEntity> findByBatch(AppDrugVo vo) throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        String sql = "SELECT\n" +
                "\tt.DG_DOC_ID doctorId,\n" +
                "\tt.DG_GUIDE_TIME guideTime,\n" +
                "\tt.DG_BATCH_NUM drBatchNum,\n" +
                "\tt.DG_TEAM_ID teamId,\n" +
                "\tt.DG_HOSP_ID hospId,\n" +
                "\tt.DG_PATIENT_ID patientId\n" +
                "FROM\n" +
                "\tAPP_DRUG_GUIDE t\n" +
                "WHERE 1=1 ";
        if(StringUtils.isNotBlank(vo.getDrId())){
            map.put("drId",vo.getDrId());
            sql += " AND t.DG_DOC_ID = :drId";
        }
        if(StringUtils.isNotBlank(vo.getPatientId())){
            map.put("patientId",vo.getPatientId());
            sql += " AND t.DG_PATIENT_ID = :patientId";
        }
        if(StringUtils.isNotBlank(vo.getDgBatchNum())){
            map.put("DG_BATCH_NUM",vo.getDgBatchNum());
            sql += " AND t.DG_BATCH_NUM = :DG_BATCH_NUM";
        }
        sql +=" GROUP BY t.DG_BATCH_NUM ORDER BY t.DG_GUIDE_TIME DESC ";
        return sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrugGuideBatchEntity.class,vo);
    }

    /**
     * 查询医生对患者上一次指导
     * @param drId
     * @param patiendId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDrDrugGuideEntity> findByDrOrPatient(String drId, String patiendId) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        String sql = "SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.DG_DOC_ID doctorId,\n" +
                "\ta.DG_DRUG_ID drugId,\n" +
                "\ta.DG_GUIDE_TIME guideTime,\n" +
                "\ta.DG_DRUG_NAME drugName,\n" +
                "\ta.DG_DRUG_TYPE drugType,\n" +
                "\ta.DG_PERIOD drugPeriod,\n" +
                "\ta.DG_FREQUENCY drugFrequency,\n" +
                "\ta.DG_TAKING drugTaking,\n" +
                "\ta.DG_USAGE drugUsage,\n" +
                "\ta.DG_PERIOD_OTHER periodOther,\n" +
                "\ta.DG_FREQUENCY_OTHER frequencyOther,\n" +
                "\ta.DG_DRUG_BEGIN_TIME drugBeginTime,\n" +
                "\ta.DG_GUIDE_NOTE note,\n" +
                "\ta.DG_SPEC dgSpec,\n" +
                "\ta.DG_BATCH dgBatch,\n" +
                "\ta.DG_USE_LEVEL dgUseLevel,\n" +
                "\ta.DG_DOSAGE_UNIT dgDosageUnit,\n" +
                "\ta.DG_ALL_USE_LEVEL dgAllUseLevel ,\n" +
                "  a.DG_ALL_DOSAGE_UNIT dgAllDosageUnit\n" +
                "FROM\n" +
                "\tAPP_DRUG_GUIDE a\n" +
                "WHERE\n" +
                "\t1 = 1";
        map.put("drId",drId);
        sql += " AND a.DG_DOC_ID = :drId";
        map.put("patientId",patiendId);
        sql += " AND a.DG_PATIENT_ID = :patientId";
        sql +=" ORDER BY DG_GUIDE_TIME DESC ";
        List<AppDrDrugGuideEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrDrugGuideEntity.class);
        List<AppDrDrugGuideEntity> lss = new ArrayList<AppDrDrugGuideEntity>();
        if(ls!=null&&ls.size()>0){
            if(StringUtils.isNotBlank(ls.get(0).getGuideTime())){
                String time =ls.get(0).getGuideTime().substring(0,10);
                for(AppDrDrugGuideEntity ll:ls){
                    if(StringUtils.isNotBlank(ll.getGuideTime())){
                        if(time.equals(ll.getGuideTime().substring(0,10))){
                            lss.add(ll);
                        }
                    }
                }
                return lss;
            }
        }
        return null;
    }

    @Override
    public List<AppDrugWarning> findDrugWarn(String drId, String drugId) throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        map.put("drId",drId);
        map.put("drugId",drugId);
        String sql = "select * from APP_DRUG_WARNING WHERE DW_DR_ID = :drId and DW_DRUG_ID = :drugId ";
        return sysDao.getServiceDo().findSqlMap(sql,map,AppDrugWarning.class);
    }

    @Override
    public List<AppDrugWarning> findDrugCommonWarn(String drId) throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        map.put("drId",drId);
        String sql = "select * from APP_DRUG_WARNING WHERE DW_DR_ID = :drId and DW_COMMON_WARN_NUM is not null";
        return sysDao.getServiceDo().findSqlMap(sql,map,AppDrugWarning.class);
    }


    @Override
    public AppDrugWarning setDrugWarn(String drId,String drugId,String warningNum) throws Exception{
        AppDrugWarning warn = null;
        List<AppDrugWarning> list = sysDao.getAppDrugGuideDao().findDrugWarn(drId,drugId);
        if(!list.isEmpty()){
            warn = list.get(0);
            warn.setDwDrId(drId);
            warn.setDwWarnNum(warningNum);
            sysDao.getServiceDo().modify(warn);
        }else{
            warn = new AppDrugWarning();
            warn.setDwDrugId(drugId);
            warn.setDwDrId(drId);
            warn.setDwWarnNum(warningNum);
            sysDao.getServiceDo().add(warn);
        }
        List<AppDrugGuide> guideList = sysDao.getServiceDo().loadByPk(AppDrugGuide.class,"dgDocId",drId);
        if(!guideList.isEmpty()){
            for(AppDrugGuide guide:guideList){
                guide.setDgWarnNum(warningNum);
                sysDao.getServiceDo().modify(guide);
            }
        }
        return warn;
    }


    @Override
    public AppDrugWarning setCommonDrugWarn(String drId,String warnNum) throws Exception{
        AppDrugWarning warn = null;
        List<AppDrugWarning> list = sysDao.getAppDrugGuideDao().findDrugCommonWarn(drId);
        if(!list.isEmpty()){
            warn = list.get(0);
            warn.setDwDrId(drId);
            warn.setDwCommonWarnNum(warnNum);
            sysDao.getServiceDo().modify(warn);
        }else{
            warn = new AppDrugWarning();
            warn.setDwDrId(drId);
            warn.setDwCommonWarnNum(warnNum);
            sysDao.getServiceDo().add(warn);
        }
        List<AppDrugGuide> guideList = sysDao.getServiceDo().loadByPk(AppDrugGuide.class,"dgDocId",drId);
        if(!guideList.isEmpty()){
            for(AppDrugGuide guide:guideList){
                guide.setDgCommonWarnNum(warnNum);
                sysDao.getServiceDo().modify(guide);
            }
        }
        return warn;
    }

    /**
     * 添加用药指导
     * @param user
     * @param vo
     */
    @Override
    public void addGuide(AppDrUser user,AppDrugVo vo) throws Exception{
        if(vo.getPatientId()!=null){
            String[] ids = vo.getPatientId().split(";");
            if(ids!=null&&ids.length>0){
                for(int i=0;i<ids.length;i++) {
                    AppDrUserEntity drEntity = sysDao.getAppDrUserDao().findUserId(user.getId());
                    AppSerial serial = sysDao.getAppSignformDao().getAppSerial(drEntity.getDrHospAreaCode().substring(0, 4), "drug");
                    Map<String,Object> bcnum = new HashMap<>();
                    if (serial != null) {
                        bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
                        serial.setSerialNo(bcnum.get("old").toString());
                        sysDao.getServiceDo().modify(serial);
                    }
                    String[] drugIds = vo.getDrugId().split(";");
                    String[] teamIds = vo.getTeamId().split(";");
                    String[] periods = vo.getPeriod().split(";");
                    String[] frquencys = vo.getFrequency().split(";");
                    String[] allDosageUnits = vo.getAllDosageUnit().split(";");
                    String[] allUseLevels = vo.getAllUseLevel().split(";");
                    String[] useLevels = vo.getUseLevel().split(";");
                    String[] dosageUntis = vo.getDosageUnit().split(";");
                    String[] drugBeginTimes = vo.getDrugBeginTime().split(";");
                    String[] notes = null;
                    if (StringUtils.isNotBlank(vo.getNote())){
                       notes = vo.getNote().split(";");
                    }
                    String[] perioderOthers = null;
                    if(StringUtils.isNotBlank(vo.getPeriodOther())) {
                        perioderOthers = vo.getPeriodOther().split(";");
                    }
                    String[] frquencyOthers = null;
                    if(StringUtils.isNotBlank(vo.getFrequencyOther())) {
                        frquencyOthers = vo.getFrequencyOther().split(";");
                    }
                    String[] batchs = null;
                    if(StringUtils.isNotBlank(vo.getDgBatchNum())){
                        batchs = vo.getDgBatchNum().split(";");
                    }
                    if(drugIds!=null&&drugIds.length>0) {
                        for (int j=0;j<drugIds.length;j++) {
                            //判断上次的用药指导是否遵从医嘱
                            followDoctorGuide(user.getId(),ids[i],drugIds[j]);
                            AppDrugGuide guide = new AppDrugGuide();
                            guide.setDgBatchNum(bcnum.get("new").toString());//批号
                            guide.setDgDrugId(drugIds[j]);
                            AppDrug drug = (AppDrug) sysDao.getServiceDo().find(AppDrug.class,drugIds[j]);
                            if(drug!=null){
                                guide.setDgDrugName(drug.getDrugName());//药品名称
                                guide.setDgDrugType(drug.getDrugType());//药品类型
                                guide.setDgUsage(drug.getDrugUsage());//用法
                                guide.setDgTaking(drug.getDrugTaking());//时段
                                guide.setDgSpec(drug.getDrugSpec());//规格
                                guide.setDgBatch(drug.getDrugBatch());//批号
                            }
                            guide.setDgDocId(user.getId());
                            if(drEntity!=null){
                                guide.setDgHospId(drEntity.getDrHospId());
                                guide.setDgAreaCode(drEntity.getDrHospAreaCode());
                            }
                            guide.setDgTeamId(teamIds[j]);
                            guide.setDgPatientId(ids[i]);
                            guide.setDgPeriod(periods[j]);//周期
                            guide.setDgFrequency(frquencys[j]);//频率
                            if(perioderOthers!=null && perioderOthers.length >0){
                                if(StringUtils.isNotBlank(perioderOthers[j])){
                                    guide.setDgPeriodOther(perioderOthers[j]);
                                }
                            }
                            if(frquencyOthers!=null && frquencyOthers.length >0){
                                if(StringUtils.isNotBlank(frquencyOthers[j])){
                                    guide.setDgFrequencyOther(frquencyOthers[j]);
                                }
                            }
                            if(batchs!=null && batchs.length>0){
                                if(StringUtils.isNotBlank(batchs[j])){
                                    guide.setDgBatchNum(batchs[j]);
                                }
                            }
                            guide.setDgAllDosageUnit(allDosageUnits[j]);//总量单位
                            guide.setDgAllUseLevel(allUseLevels[j]);//总量
                            guide.setDgUseLevel(useLevels[j]);//用量
                            guide.setDgDosageUnit(dosageUntis[j]);//用量单位
                            guide.setDgDrugBeginTime(ExtendDate.getCalendar(drugBeginTimes[j]));//开始时间
                            guide.setDgGuideTime(Calendar.getInstance());
                            if(periods[j]!=null&&!periods[j].startsWith("0")){//"08","09"周期为”无需"，"其他"
                                Calendar cal = ExtendDate.getCalendar(drugBeginTimes[j]);
                                cal.set(Calendar.DATE, cal.get(Calendar.DATE) + Integer.parseInt(periods[j]));
                                guide.setDgEndTime(cal);
                            }
                            if(notes!=null && notes.length >0) {
                                guide.setDgGuideNote(notes[j]);//备注
                            }
                            List<AppDrugWarning> warnList = sysDao.getAppDrugGuideDao().findDrugWarn(user.getId(),drugIds[j]);
                            if(warnList!=null&&!warnList.isEmpty()){
                                guide.setDgWarnNum(warnList.get(0).getDwWarnNum());
                            }
                            List<AppDrugWarning> commonList = sysDao.getAppDrugGuideDao().findDrugCommonWarn(user.getId());
                            if(warnList!=null && !commonList.isEmpty()){
                                guide.setDgWarnNum(commonList.get(0).getDwCommonWarnNum());
                            }
                            guide.setDgGuideFrom(AppDrugGuideFrom.XZ.getValue());
                            sysDao.getServiceDo().add(guide);

                            //履约数据
                            PerformanceDataQvo qqvo = new PerformanceDataQvo();
                            AppPatientUser patientUser = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,ids[i]);
                            if(patientUser!=null){
                                AppSignForm form= sysDao.getAppSignformDao().getSignFormUserId(ids[i]);
                                if(form != null){
                                    qqvo.setPerName(patientUser.getPatientName());
                                    qqvo.setPerIdno(patientUser.getPatientIdno());
                                    qqvo.setPerType(PerformanceType.YYZD.getValue());
                                    qqvo.setPerForeign(guide.getId());
                                    qqvo.setPerSource("2");
                                    if(StringUtils.isNotBlank(form.getSignAreaCode())){
                                        CdAddress p = sysDao.getCdAddressDao().findByCode(form.getSignAreaCode());
                                        if(p != null){
                                            String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                                            if(value != null) {
                                                qqvo.setPerArea(value.getCodeTitle());
                                            }
                                        }
                                    }
                                    if(user != null){
                                        qqvo.setDrName(user.getDrName());
                                        qqvo.setDrAccount(user.getDrAccount());
                                        qqvo.setDrPwd(user.getDrPwd());
                                        qqvo.setDrGender(user.getDrGender());
                                        qqvo.setDrTel(user.getDrTel());
                                        qqvo.setDrId(user.getId());
                                        AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,user.getDrHospId());
                                        if(dept!=null){
                                            qqvo.setHospId(dept.getId());
                                            qqvo.setHospName(dept.getHospName());
                                            qqvo.setHospAreaCode(dept.getHospAreaCode());
                                            qqvo.setHospAddress(dept.getHospAddress());
                                            qqvo.setHospTel(dept.getHospTel());
                                        }
                                    }
                                    if(StringUtils.isNotBlank(qqvo.getPerArea())){
                                        if(StringUtils.isNotBlank(qqvo.getPerType())){
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
                    }
                    if(StringUtils.isNotBlank(bcnum.get("new").toString())){
                        sysDao.getAppNoticeDao().addNotices("用药指导", "您收到一份新的用药指导，请查收", NoticesType.YYZD.getValue(), user.getId(), ids[i], bcnum.get("new").toString(), DrPatientType.PATIENT.getValue());
                    }
                }
            }
        }

    }



    /**
     * 通过用户id查询用药提醒的药品
     * @param vo patientId
     * @return
     */
    @Override
    public List<AppDrugReminderEntity> findByDrugUnique(AppDrugVo vo) throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        String sql = "select a.ID drugId, a.DG_DRUG_NAME drugName " +
                " from APP_DRUG_GUIDE a WHERE 1=1 ";
        if(StringUtils.isNotBlank(vo.getPatientId())){
            map.put("patientId",vo.getPatientId());
            sql += " AND a.DG_PATIENT_ID = :patientId";
        }
        sql +=" GROUP BY a.DG_DRUG_ID  ORDER BY a.DG_GUIDE_TIME DESC ";
        return sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrugReminderEntity.class);
    }


    /**
     * 遵从医嘱
     */
    public void followDoctorGuide(String drId,String patientId,String drugId) throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        String sql = "SELECT * FROM APP_DRUG_GUIDE WHERE DG_END_TIME = (SELECT MAX(DG_END_TIME) FROM APP_DRUG_GUIDE WHERE 1=1 ";
        if(StringUtils.isNotBlank(drId)){
            map.put("drId",drId);
            sql += " AND DG_DOC_ID = :drId";
        }
        if(StringUtils.isNotBlank(patientId)){
            map.put("patientId",patientId);
            sql += " AND DG_PATIENT_ID = :patientId";
        }
        if(StringUtils.isNotBlank(drugId)){
            map.put("drugId",drugId);
            sql += " AND DG_DRUG_ID = :drugId";
        }
        sql+=")";
        List<AppDrugGuide> guide =  sysDao.getServiceDo().findSqlMap(sql,map,AppDrugGuide.class);
        if(guide!=null&&guide.size()>0){
            if(ExtendDate.compare(guide.get(0).getDgEndTime(),Calendar.getInstance())==1) {//结束时间小于当前时间，遵从医嘱
               guide.get(0).setDgFollowGuide("1");
               sysDao.getServiceDo().modify(guide.get(0));
            }else{
                guide.get(0).setDgFollowGuide("0");
                sysDao.getServiceDo().modify(guide.get(0));
            }
        }
    }

    @Override
    public List<AppDrugGuide> findGuidByTime(String usreId) throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        map.put("userId",usreId);
        map.put("DG_SEND_STATE","0");
        String sql = "select * from APP_DRUG_GUIDE WHERE NOW() between DG_GUIDE_TIME and DG_END_TIME AND DG_SEND_STATE =:DG_SEND_STATE " +
                "AND (DG_PATIENT_ID=:userId OR DG_DOC_ID=:userId)" ;
        return sysDao.getServiceDo().findSqlMap(sql,map,AppDrugGuide.class);
    }
}
