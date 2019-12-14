package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppReferralTableDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrReferralEntity;
import com.ylz.bizDo.jtapp.drEntity.AppReferralPatientEntity;
import com.ylz.bizDo.jtapp.drEntity.AppUpHospEntity;
import com.ylz.bizDo.jtapp.drEntity.ReferralInfo;
import com.ylz.bizDo.jtapp.drVo.AppDrReferralQvo;
import com.ylz.bizDo.jtapp.drVo.AppUpHospQvo;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/12/11.
 */
@Service("appRefarralTableDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppReferralTableDaoImpl implements AppReferralTableDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 提交转诊信息
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppReferralTable subReferral(AppDrReferralQvo qvo) throws Exception {
        AppReferralTable table = new AppReferralTable();
        table.setRefPatientAddress(qvo.getAddress());
        table.setRefPatientAge(qvo.getAge());
        table.setRefPatientCard(qvo.getCard());
        table.setRefPatientGender(qvo.getSex());
        table.setRefPatientName(qvo.getName());
        table.setRefPatientIdNo(qvo.getIdNo());
        table.setRefPatientTel(qvo.getPhone());
        table.setRefPatientId(qvo.getPatientId());
        table.setRefFirstImpression(qvo.getCbyx());
        table.setRefMainHistory(qvo.getZcyy());
        table.setRefMainPastHistory(qvo.getJws());
        table.setRefTreatmentOf(qvo.getZljg());
        table.setRefOutOrgId(qvo.getOutOrgId());
        table.setRefOutDrId(qvo.getOutDrId());
        table.setRefContact(qvo.getLxfs());
        table.setRefInOrgId(qvo.getInOrgId());
        table.setRefInDeptId(qvo.getInDeptId());
        table.setRefYyDate(ExtendDate.getCalendar(qvo.getYyDate()).getTime());
        table.setRefApplyTime(ExtendDate.getCalendar(qvo.getSqTime()));
        table.setRefType(ReferralType.ZC.getValue());
        table.setRefState(ReferralState.ZZDTG.getValue());
        table.setRefTeamId(qvo.getTeamId());
        table.setRefSignId(qvo.getSignId());
        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getOutOrgId());
        if(dept != null){
            table.setRefAreaCode(dept.getHospAreaCode());
        }
        sysDao.getServiceDo().add(table);
        //发送消息提醒
        //查询医院可以接收转诊消息的医生
        List<AppDrUser> drList = findDrList(qvo.getInOrgId());
        if(drList !=null && drList.size()>0){
            for(AppDrUser drll:drList){
                sysDao.getAppNoticeDao().addNotices("转诊消息提醒",
                        "您好，"+qvo.getOutOrgName()+"申请向本院"+qvo.getInDeptName()+"转诊1位患者"+qvo.getName()+"。",
                        NoticesType.XTXX.getValue()+",3",qvo.getOutDrId(),drll.getId(),table.getId(),
                        DrPatientType.DR.getValue());
            }
        }
        //您好，鼓浪屿街道社区卫生服务中心为您预约2017-09-01转诊到厦门大学附属第一医院-心内科。请等待上级医院确认，或咨询您的家庭医生。
        sysDao.getAppNoticeDao().addNotices("转诊消息提醒",
                "您好，"+qvo.getOutOrgName()+"为您预约"+qvo.getYyDate()+"转诊到"+qvo.getInOrgName()+"-"+qvo.getInDeptName()+"。请等待上级医院确认，或咨询您的家庭医生。",
                NoticesType.XTXX.getValue(),qvo.getOutDrId(),qvo.getPatientId(),"",DrPatientType.PATIENT.getValue());
        return table;
    }

    public List<AppDrUser> findDrList(String hospId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("DR_HOSP_ID",hospId);
        map.put("DR_REFERRAL_STATE","1");
        String sql = "SELECT * FROM APP_DR_USER WHERE 1=1" +
                " AND DR_HOSP_ID=:DR_HOSP_ID AND DR_REFERRAL_STATE=:DR_REFERRAL_STATE";
        List<AppDrUser> listt = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
        return listt;
    }

    /**
     * 康复回转
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppReferralTable rehabilitation(AppDrReferralQvo qvo) throws Exception {
        AppReferralTable v = (AppReferralTable) sysDao.getServiceDo().find(AppReferralTable.class,qvo.getId());
        if(v != null){
            v.setRefRehabilitationTime(Calendar.getInstance());
            v.setRefResults(qvo.getZdResult());
            v.setRefMainResults(qvo.getZyjcResult());
            v.setRefNextAdvice(qvo.getNextAdvice());
            v.setRefType(ReferralType.ZR.getValue());
            sysDao.getServiceDo().modify(v);
        }
        return v;
    }

    /**
     * 查看转诊信息
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppDrReferralEntity findOneByReferral(AppDrReferralQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("id",qvo.getId());
        String sql = "SELECT ID id," +
                "(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=REF_SIGN_ID) disName," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=REF_SIGN_ID) disColor," +
                "REF_PATIENT_ID patientId," +
                "REF_PATIENT_NAME name," +
                "REF_PATIENT_AGE age," +
                "REF_PATIENT_IDNO idNo," +
                "REF_PATIENT_GENDER sex," +
                "REF_PATIENT_ADDRESS address," +
                "REF_PATIENT_TEL phone," +
                "REF_FIRST_IMPRESSION cbyx," +
                "REF_MAIN_HISTORY zyxbs," +
                "REF_MAIN_PAST_HISTORY zyjws," +
                "REF_TREATMENT_OF zljg," +
                "REF_OUT_ORG_ID outOrgId," +
                "REF_OUT_DR_ID outDrId," +
                "REF_CONTACT lxfs," +
                "REF_IN_ORG_ID inOrgId," +
                "REF_IN_DEPT_ID inDeptId," +
                "REF_IN_DR_ID indrId," +
                "REF_YY_DATE yyzzsj," +
                "REF_APPLY_TIME sqzzsj," +
                "REF_ACCEPTS_TIME jzsj," +
                "REF_REJECT_TIME jjzsj," +
                "REF_REJECT_REASON jjzyy," +
                "REF_TERMINATION_TIME zzsj," +
                "REF_TERMINATION_REASON zzyy," +
                "REF_REHABILITATION_TIME kfzhsj," +
                "REF_RESULTS zdjg," +
                "REF_MAIN_RESULTS zyjcjg," +
                "REF_NEXT_ADVICE xycjy," +
                "REF_TEAM_ID teamId," +
                "REF_AREA_CODE areaCode," +
                "REF_STATE state," +
                "REF_TYPE type," +
                "REF_SIGN_ID signId," +
                "'' outOrgName," +
                "'' outDrName," +
                "'' inOrgName," +
                "'' inDeptName," +
                "'' indrName " +
                "FROM APP_REFERRAL_TABLE " +
                "WHERE ID=:id";
        List<AppDrReferralEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrReferralEntity.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询转诊记录列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDrReferralEntity> findReferralList(AppDrReferralQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("outDrId",qvo.getOutDrId());
        String sql = "SELECT ID id," +
                "(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=REF_SIGN_ID) disName," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=REF_SIGN_ID) disColor," +
                "REF_PATIENT_ID patientId," +
                "REF_PATIENT_NAME name," +
                "REF_PATIENT_AGE age," +
                "REF_PATIENT_CARD card," +
                "REF_PATIENT_IDNO idNo," +
                "REF_PATIENT_GENDER sex," +
                "REF_PATIENT_ADDRESS address," +
                "REF_PATIENT_TEL phone," +
                "REF_FIRST_IMPRESSION cbyx," +
                "REF_MAIN_HISTORY zyxbs," +
                "REF_MAIN_PAST_HISTORY zyjws," +
                "REF_TREATMENT_OF zljg," +
                "REF_OUT_ORG_ID outOrgId," +
                "REF_OUT_DR_ID outDrId," +
                "REF_CONTACT lxfs," +
                "REF_IN_ORG_ID inOrgId," +
                "REF_IN_DEPT_ID inDeptId," +
                "REF_IN_DR_ID indrId," +
                "REF_YY_DATE yyzzsj," +
                "REF_APPLY_TIME sqzzsj," +
                "REF_ACCEPTS_TIME jzsj," +
                "REF_REJECT_TIME jjzsj," +
                "REF_REJECT_REASON jjzyy," +
                "REF_TERMINATION_TIME zzsj," +
                "REF_TERMINATION_REASON zzyy," +
                "REF_REHABILITATION_TIME kfzhsj," +
                "REF_RESULTS zdjg," +
                "REF_MAIN_RESULTS zyjcjg," +
                "REF_NEXT_ADVICE xycjy," +
                "REF_TEAM_ID teamId," +
                "REF_AREA_CODE areaCode," +
                "REF_STATE state," +
                "REF_TYPE type," +
                "REF_SIGN_ID signId," +
                "'' outOrgName," +
                "'' outDrName," +
                "'' inOrgName," +
                "'' inDeptName," +
                "'' indrName," +
                "'' imageUrl " +
                "FROM APP_REFERRAL_TABLE " +
                "WHERE 1=1 AND REF_OUT_DR_ID=:outDrId ";
        if(StringUtils.isNotBlank(qvo.getName())){
            map.put("name",qvo.getName()+"%");
            sql += " AND REF_PATIENT_NAME LIKE :name ";
        }
        if(StringUtils.isNotBlank(qvo.getInOrgId())){
            map.put("inOrgId",qvo.getInOrgId());
            sql += " AND REF_IN_ORG_ID=:inOrgId ";
        }
        sql += " ORDER BY REF_APPLY_TIME DESC";
        List<AppDrReferralEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrReferralEntity.class);
        return list;
    }

    /**
     * 同意、拒绝、终止、回转
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppReferralTable makeReferral(AppDrReferralQvo qvo) throws Exception {
        AppReferralTable table = (AppReferralTable) sysDao.getServiceDo().find(AppReferralTable.class,qvo.getId());
        if(table!=null) {
            table.setRefInDrId(qvo.getInDrId());
            if (ReferralState.ZZYTG.getValue().equals(qvo.getState())) {//同意
                table.setRefState(ReferralState.ZZYTG.getValue());//同意接诊
                table.setRefAcceptsTime(Calendar.getInstance());//接诊时间
                sysDao.getServiceDo().modify(table);
                //发送同意消息
                sysDao.getAppNoticeDao().addNotices("转诊消息提醒",
                        "您好，"+table.getInOrgName()+"已同意您的转诊申请。",
                        NoticesType.XTXX.getValue()+",3",qvo.getInDrId(),table.getRefOutDrId(),table.getId(),DrPatientType.DR.getValue());

                sysDao.getAppNoticeDao().addNotices("转诊消息提醒",
                        "您好，"+table.getOutOrgName()+"已成功将您转诊到"+table.getInOrgName()+"-"+table.getInDeptName()+",预约时间"+table.getStrRefYyDate()+",请及时签约就诊。",
                NoticesType.XTXX.getValue(),qvo.getInDrId(),table.getRefPatientId(),"",DrPatientType.PATIENT.getValue());
            } else if (ReferralState.ZZYJJ.getValue().equals(qvo.getState())) {//拒绝
                table.setRefState(ReferralState.ZZYJJ.getValue());//拒绝接诊
                table.setRefRejectReason(qvo.getJjReason());//拒接诊原因
                table.setRefRejectTime(Calendar.getInstance());//拒接诊时间
                sysDao.getServiceDo().modify(table);
                sysDao.getAppNoticeDao().addNotices("转诊消息提醒",
                        "您好，"+table.getInOrgName()+"-"+table.getInDeptName()+"已拒绝您的转诊申请。拒绝原因："+table.getRefRejectReason(),
                        NoticesType.XTXX.getValue()+",3",qvo.getInDrId(),table.getRefOutDrId(),table.getId(),DrPatientType.DR.getValue());
                sysDao.getAppNoticeDao().addNotices("转诊消息提醒",
                        "您好，"+table.getInOrgName()+"-"+table.getInDeptName()+"已拒绝您的转诊申请，详情请咨询您的家庭医生。",
                        NoticesType.XTXX.getValue(),qvo.getInDrId(),table.getRefPatientId(),"",DrPatientType.PATIENT.getValue());
            } else if (ReferralState.ZZ.getValue().equals(qvo.getState())) {//终止
                table.setRefType(ReferralType.ZR.getValue());
                table.setRefState(ReferralState.ZZ.getValue());//终止接诊
                table.setRefTerminationReason(qvo.getZzReason());//终止理由
                table.setRefTerminationTime(Calendar.getInstance());//终止时间
                sysDao.getServiceDo().modify(table);
                sysDao.getAppNoticeDao().addNotices("转诊消息提醒",
                        "您好，"+table.getInOrgName()+"-"+table.getInDeptName()+"已终止您的转诊申请。终止原因："+table.getRefTerminationReason(),
                        NoticesType.XTXX.getValue()+",3",qvo.getInDrId(),table.getRefOutDrId(),table.getId(),DrPatientType.DR.getValue());
                sysDao.getAppNoticeDao().addNotices("转诊消息提醒",
                        "您好，"+table.getInOrgName()+"-"+table.getInDeptName()+"已终止您的转诊申请，详情请咨询您的家庭医生。",
                        NoticesType.XTXX.getValue(),qvo.getInDrId(),table.getRefPatientId(),"",DrPatientType.PATIENT.getValue());
            } else if (ReferralState.KFHZ.getValue().equals(qvo.getState())) {//康复回转
                table.setRefState(ReferralState.KFHZ.getValue());//康复回转
                table.setRefResults(qvo.getZdResult());//诊断结果
                table.setRefMainResults(qvo.getZyjcResult());//主要检查结果
                table.setRefNextAdvice(qvo.getNextAdvice());//下一次建议
                table.setRefRehabilitationTime(Calendar.getInstance());//康复回转时间
                table.setRefType(ReferralType.ZR.getValue());
                sysDao.getServiceDo().modify(table);
                sysDao.getAppNoticeDao().addNotices("转诊消息提醒",
                        "您好，"+table.getInOrgName()+"-"+table.getInDeptName()+"已将患者"+table.getRefPatientName()+"转回医院，请及时处理。",
                        NoticesType.XTXX.getValue()+",3",qvo.getInDrId(),table.getRefOutDrId(),table.getId(),DrPatientType.DR.getValue());
            }
        }
        return table;
    }

    /**
     * 查询审核转诊记录
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDrReferralEntity> findShReferral(AppDrReferralQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        AppDrUser drUser = new AppDrUser();
        if(StringUtils.isNotBlank(qvo.getInDrId())){
            drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getInDrId());
        }
        String deptId=null;
        if(drUser!=null){
            deptId = drUser.getDrDepartmentId();
        }
        map.put("inOrgId",qvo.getInOrgId());
        map.put("inDrId",qvo.getInDrId());
        String sql = "SELECT ID id," +
                "(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=REF_SIGN_ID) disName," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=REF_SIGN_ID) disColor," +
                "REF_PATIENT_ID patientId," +
                "REF_PATIENT_NAME name," +
                "REF_PATIENT_AGE age," +
                "REF_PATIENT_IDNO idNo," +
                "REF_PATIENT_CARD card," +
                "REF_PATIENT_GENDER sex," +
                "REF_PATIENT_ADDRESS address," +
                "REF_PATIENT_TEL phone," +
                "REF_FIRST_IMPRESSION cbyx," +
                "REF_MAIN_HISTORY zyxbs," +
                "REF_MAIN_PAST_HISTORY zyjws," +
                "REF_TREATMENT_OF zljg," +
                "REF_OUT_ORG_ID outOrgId," +
                "REF_OUT_DR_ID outDrId," +
                "REF_CONTACT lxfs," +
                "REF_IN_ORG_ID inOrgId," +
                "REF_IN_DEPT_ID inDeptId," +
                "REF_IN_DR_ID indrId," +
                "REF_YY_DATE yyzzsj," +
                "REF_APPLY_TIME sqzzsj," +
                "REF_ACCEPTS_TIME jzsj," +
                "REF_REJECT_TIME jjzsj," +
                "REF_REJECT_REASON jjzyy," +
                "REF_TERMINATION_TIME zzsj," +
                "REF_TERMINATION_REASON zzyy," +
                "REF_REHABILITATION_TIME kfzhsj," +
                "REF_RESULTS zdjg," +
                "REF_MAIN_RESULTS zyjcjg," +
                "REF_NEXT_ADVICE xycjy," +
                "REF_TEAM_ID teamId," +
                "REF_AREA_CODE areaCode," +
                "REF_STATE state," +
                "REF_TYPE type," +
                "REF_SIGN_ID signId," +
                "'' outOrgName," +
                "'' outDrName," +
                "'' inOrgName," +
                "'' inDeptName," +
                "'' indrName," +
                "'' imageUrl " +
                "FROM APP_REFERRAL_TABLE " +
                "WHERE 1=1 AND REF_IN_ORG_ID=:inOrgId AND (REF_IN_DR_ID IS NULL OR REF_IN_DR_ID=:inDrId) ";
        if(StringUtils.isNotBlank(deptId)){
            map.put("deptId",deptId);
            sql += " AND REF_IN_DEPT_ID=:deptId";
        }
        sql += " ORDER BY REF_APPLY_TIME DESC";
        List<AppDrReferralEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrReferralEntity.class);
        //过期处理部分
        return list;
    }

    /**
     * 查询人员列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppReferralPatientEntity> findPeople(AppDrReferralQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",qvo.getTeamId());
        map.put("drId",qvo.getInDrId());
        String[] signStates = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
        map.put("signState", signStates);
        String sql = "SELECT " +
                "(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labelTitle," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labelColor," +
                "b.ID id," +
                "b.PATIENT_NAME name," +
                "b.PATIENT_IDNO idno," +
                "b.PATIENT_GENDER sex," +
                "b.PATIENT_CARD card," +
                "b.PATIENT_TEL tel," +
                "b.PATIENT_IMAGEURL imageUrl," +
                "b.PATIENT_ADDRESS address," +
                "a.ID signId," +
                "'' age " +
                "FROM APP_SIGN_FORM a INNER JOIN APP_PATIENT_USER b ON a.SIGN_PATIENT_ID = b.ID " +
                "WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getName())){
            map.put("PATIENT_NAME","%"+qvo.getName()+"%");
            sql += " AND b.PATIENT_NAME LIKE :PATIENT_NAME ";
        }
            sql += " AND a.SIGN_STATE IN (:signState) AND a.SIGN_TO_DATE>SYSDATE() AND a.SIGN_TEAM_ID=:teamId " +
                    "AND a.ID NOT IN (SELECT cc.REF_SIGN_ID FROM APP_REFERRAL_TABLE cc WHERE cc.REF_OUT_DR_ID =:drId AND cc.REF_STATE IN ('0','1'))";
            sql += " GROUP BY b.ID ";
        List<AppReferralPatientEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppReferralPatientEntity.class,qvo);
        return list;
    }

    /**
     * 查询上级医院列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppUpHospEntity> findHosp(AppUpHospQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("hospId",qvo.getBaseId());
        String sql = "SELECT\n" +
                "\ta.UP_HOSP_ID hospId,\n" +
                "\tb.HOSP_NAME hospName\n" +
                "FROM\n" +
                "\tAPP_UP_HOSP_TABLE a\n" +
                "INNER JOIN APP_HOSP_DEPT b ON a.UP_HOSP_ID = b.ID\n" +
                "WHERE\n" +
                "\ta.UP_ID = :hospId\n" ;
        if(StringUtils.isNotBlank(qvo.getAreaCode())){
            map.put("areaCode", AreaUtils.getAreaCode(qvo.getAreaCode())+"%");
            sql +=" AND b.HOSP_AREA_CODE LIKE :areaCode";
        }
        if(StringUtils.isNotBlank(qvo.getHospName())){
            map.put("hospName",qvo.getHospName()+"%");
            sql +=" AND b.HOSP_NAME LIKE :hospName";
        }
        List<AppUpHospEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppUpHospEntity.class);
        return list;
    }

    @Override
    public List<AppDrReferralEntity> findReferralPatient(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("patientId",qvo.getPatientId());
        String sql = "SELECT ID id," +
                "(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=REF_SIGN_ID) disName," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=REF_SIGN_ID) disColor," +
                "REF_PATIENT_ID patientId," +
                "REF_PATIENT_NAME name," +
                "REF_PATIENT_AGE age," +
                "REF_PATIENT_IDNO idNo," +
                "REF_PATIENT_CARD card," +
                "REF_PATIENT_GENDER sex," +
                "REF_PATIENT_ADDRESS address," +
                "REF_PATIENT_TEL phone," +
                "REF_FIRST_IMPRESSION cbyx," +
                "REF_MAIN_HISTORY zyxbs," +
                "REF_MAIN_PAST_HISTORY zyjws," +
                "REF_TREATMENT_OF zljg," +
                "REF_OUT_ORG_ID outOrgId," +
                "REF_OUT_DR_ID outDrId," +
                "REF_CONTACT lxfs," +
                "REF_IN_ORG_ID inOrgId," +
                "REF_IN_DEPT_ID inDeptId," +
                "REF_IN_DR_ID indrId," +
                "REF_YY_DATE yyzzsj," +
                "REF_APPLY_TIME sqzzsj," +
                "REF_ACCEPTS_TIME jzsj," +
                "REF_REJECT_TIME jjzsj," +
                "REF_REJECT_REASON jjzyy," +
                "REF_TERMINATION_TIME zzsj," +
                "REF_TERMINATION_REASON zzyy," +
                "REF_REHABILITATION_TIME kfzhsj," +
                "REF_RESULTS zdjg," +
                "REF_MAIN_RESULTS zyjcjg," +
                "REF_NEXT_ADVICE xycjy," +
                "REF_TEAM_ID teamId," +
                "REF_AREA_CODE areaCode," +
                "REF_STATE state," +
                "REF_TYPE type," +
                "REF_SIGN_ID signId," +
                "'' outOrgName," +
                "'' outDrName," +
                "'' inOrgName," +
                "'' inDeptName," +
                "'' indrName," +
                "'' imageUrl " +
                "FROM APP_REFERRAL_TABLE " +
                "WHERE 1=1 AND  REF_PATIENT_ID=:patientId ";

        sql += " ORDER BY REF_APPLY_TIME DESC";
        List<AppDrReferralEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrReferralEntity.class);
        return list;
    }

    /**
     * 保存基卫转诊信息
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String addReferral(AppDrReferralQvo qvo) throws Exception {
        if(StringUtils.isNotBlank(qvo.getRefCode())){
            AppPatientUser user = null;
            List<AppPatientUser> list = sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",qvo.getIdNo());
            if(list!=null && list.size()>0){
                user = list.get(0);
            }
            if(user==null){
                return "该用户未注册";
            }
            AppSignForm form = sysDao.getAppSignformDao().getSignFormUserId(user.getId());
            if(form==null){
                return "该用户未签约";
            }
            //根据基卫转诊编号查询转诊记录
            AppReferralTable refTable = getFindReferral(qvo.getRefCode());
            if(refTable!=null){
                refTable.setRefState(qvo.getState());
                if(StringUtils.isNotBlank(qvo.getRefAcceptsTime())){//接诊
                    refTable.setRefAcceptsTime(ExtendDate.getCalendar(qvo.getRefAcceptsTime()));//转诊接收时间
                }
                if(StringUtils.isNotBlank(qvo.getRefRejectTime())){//拒诊
                    refTable.setRefRejectTime(ExtendDate.getCalendar(qvo.getRefRejectTime()));//拒诊日期
                    refTable.setRefRejectReason(qvo.getJjReason());
                }
                if(StringUtils.isNotBlank(qvo.getRefTerminationTime())){//终止
                    refTable.setRefTerminationTime(ExtendDate.getCalendar(qvo.getRefTerminationTime()));
                    refTable.setRefTerminationReason(qvo.getZzReason());
                }
                if(StringUtils.isNotBlank(qvo.getRefRehabilitationTime())){//康复回转
                    refTable.setRefRehabilitationTime(ExtendDate.getCalendar(qvo.getRefRehabilitationTime()));
                    refTable.setRefResults(qvo.getZdResult());
                    refTable.setRefMainResults(qvo.getZyjcResult());
                    refTable.setRefNextAdvice(qvo.getNextAdvice());
                }
                sysDao.getServiceDo().modify(refTable);
                return "true";
            }else{
                AppReferralTable table = new AppReferralTable();
                table.setRefPatientAddress(qvo.getAddress());
                table.setRefPatientAge(qvo.getAge());
                table.setRefPatientCard(qvo.getCard());
                table.setRefPatientGender(qvo.getSex());
                table.setRefPatientName(qvo.getName());
                table.setRefPatientIdNo(qvo.getIdNo());
                table.setRefPatientTel(qvo.getPhone());
                table.setRefFirstImpression(qvo.getCbyx());
                table.setRefMainHistory(qvo.getZcyy());
                table.setRefMainPastHistory(qvo.getJws());
                table.setRefTreatmentOf(qvo.getZljg());
                table.setRefOutOrgId(qvo.getOutOrgId());
                table.setRefOutDrId(qvo.getOutDrId());
                table.setRefContact(qvo.getLxfs());
                table.setRefInOrgId(qvo.getInOrgId());
                table.setRefInDeptId(qvo.getInDeptId());
                table.setRefYyDate(ExtendDate.getCalendar(qvo.getYyDate()).getTime());
                table.setRefApplyTime(ExtendDate.getCalendar(qvo.getSqTime()));
                table.setRefType(ReferralType.ZC.getValue());
                table.setRefState(ReferralState.ZZDTG.getValue());
                table.setRefCode(qvo.getRefCode());

                table.setRefPatientId(user.getId());
                table.setRefTeamId(form.getSignTeamId());
                table.setRefSignId(form.getId());
                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getOutOrgId());
                if(dept != null){
                    table.setRefAreaCode(dept.getHospAreaCode());
                }
                sysDao.getServiceDo().add(table);
                return "true";
            }
        }
        return null;
    }

    /**
     * 根据转诊编号查询转诊记录
     * @param refCode
     * @return
     */
    public AppReferralTable getFindReferral(String refCode) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("refCode",refCode);
        String sql = " SELECT * FROM APP_REFERRAL_TABLE WHERE 1=1 AND REF_CODE=:refCode";
        List<AppReferralTable> list = sysDao.getServiceDo().findSqlMap(sql,map,AppReferralTable.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据机构id查询转诊居民列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<ReferralInfo> findByHospId(ResidentVo qvo) throws Exception{
        AppHospDept dept =(AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
        if(dept!=null){
            if(StringUtils.isBlank(dept.getHospLevelType())){
                return null;
            }
            Map<String,Object> map = new HashMap<>();
            map.put("deptId",qvo.getHospId());
            map.put("startTime",ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
            map.put("endTime",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
            String[] strs = new String[]{ReferralState.ZZDTG.getValue(),ReferralState.ZZYJJ.getValue(),ReferralState.ZZYGQ.getValue()};
            map.put("refState",strs);
            String sql = "SELECT\n" +
                    "\tc.ID id,\n" +
                    "\tc.PATIENT_NAME name,\n" +
                    "\tc.PATIENT_GENDER sex,\n" +
                    "\tc.PATIENT_TEL tel,\n" +
                    "\tc.PATIENT_IDNO patientIdno,\n" +
                    "\t'' age,\n" +
                    "\tc.PATIENT_CARD patientCard,\n" +
                    "\tc.PATIENT_IMAGEURL imgurl,\n" +
                    "\t(SELECT SIGN_SERVICE_A FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) ssgg,\n" +
                    "\t(SELECT SIGN_SERVICE_B FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) jjyl,\n" +
                    "\t(SELECT SIGN_TYPE FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) signType,\n" +
                    "\tc.PATIENT_ADDRESS address,\n" +
                    "\t(SELECT SIGN_URRENDER_REASON FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) signUrrenderReason,\n" +
                    "\t(SELECT SIGN_URRENDER_REASON_PATIENT FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) signUrrenderReasonPatient,\n" +
                    "\t(SELECT SIGN_OTHNER_REASON FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) signOthnerReason,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_GROUP g WHERE g.LABEL_TYPE = 3 AND g.LABEL_SIGN_ID = a.REF_SIGN_ID) signPersGroup,\n" +
                    "\t(SELECT SIGN_SERVICE_B_COLOR FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) jjylcolor,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_DISEASE g WHERE g.LABEL_TYPE = 2 AND g.LABEL_SIGN_ID = a.REF_SIGN_ID ) labtitle,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_COLOR) FROM APP_LABEL_DISEASE g WHERE g.LABEL_TYPE = 2 AND g.LABEL_SIGN_ID = a.REF_SIGN_ID ) labcolor,\n" +
                    "\tDATE_FORMAT((SELECT SIGN_DATE FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID), '%Y-%m-%d') signDate,\n" +
                    "\t(SELECT SIGN_PAY_STATE FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) signPayState\n" +
                    "FROM\n" +
                    "\tAPP_REFERRAL_TABLE a\n" +
                    "LEFT JOIN APP_LABEL_GROUP b ON a.REF_SIGN_ID = b.LABEL_SIGN_ID\n" +
                    "LEFT JOIN APP_PATIENT_USER c ON a.REF_PATIENT_ID = c.ID\n" +
                    "WHERE\n" +
                    "\t1 = 1\n" +
                    " AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime AND a.REF_STATE NOT IN :refState\n";
            if(HospType.ZHYY.getValue().equals(dept.getHospLevelType())||HospType.ZKYY.getValue().equals(dept.getHospLevelType())||
                    HospType.ZYYY.getValue().equals(dept.getHospLevelType())||HospType.ZXYJHYY.getValue().equals(dept.getHospLevelType())
                    ||HospType.MZYYY.getValue().equals(dept.getHospLevelType())||HospType.KFYY.getValue().equals(dept.getHospLevelType())||
                    HospType.FYBJY.getValue().equals(dept.getHospLevelType())){//上级医院转诊转入居民列表
                sql += " AND a.REF_IN_ORG_ID = :deptId ";
            }else if(HospType.SQWSFWZX.getValue().equals(dept.getHospLevelType())||HospType.ZXWSY.getValue().equals(dept.getHospLevelType())||
                    HospType.XZWSY.getValue().equals(dept.getHospLevelType())){//基层医院转诊转出居民列表
                sql += " AND a.REF_OUT_ORG_ID = :deptId ";
            }
            sql +=  " GROUP BY c.ID";
            List<ReferralInfo> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,ReferralInfo.class);
            return list;
        }
        return null;
    }

    /**
     * 根据上级医院id查询转诊居民列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<ReferralInfo> findUpHospt(AppCommQvo qvo) throws Exception {
        AppHospDept dept =(AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,qvo.getSignHospId());
        if(dept!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("deptId",dept.getId());
            String[] strs = new String[]{ReferralState.ZZDTG.getValue(),ReferralState.ZZYJJ.getValue(),ReferralState.ZZYGQ.getValue()};
            map.put("refState",ReferralState.ZZYTG.getValue());
            String sql = "SELECT\n" +
                    "\tc.ID id,\n" +
                    "\tc.PATIENT_NAME name,\n" +
                    "\tc.PATIENT_GENDER sex,\n" +
                    "\tc.PATIENT_TEL tel,\n" +
                    "\tc.PATIENT_IDNO patientIdno,\n" +
                    "\t'' age,\n" +
                    "\tc.PATIENT_CARD patientCard,\n" +
                    "\tc.PATIENT_IMAGEURL imgurl,\n" +
                    "\t(SELECT SIGN_SERVICE_A FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) ssgg,\n" +
                    "\t(SELECT SIGN_SERVICE_B FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) jjyl,\n" +
                    "\t(SELECT SIGN_TYPE FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) signType,\n" +
                    "\tc.PATIENT_ADDRESS address,\n" +
                    "\t(SELECT SIGN_URRENDER_REASON FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) signUrrenderReason,\n" +
                    "\t(SELECT SIGN_URRENDER_REASON_PATIENT FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) signUrrenderReasonPatient,\n" +
                    "\t(SELECT SIGN_OTHNER_REASON FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) signOthnerReason,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_GROUP g WHERE g.LABEL_TYPE = 3 AND g.LABEL_SIGN_ID = a.REF_SIGN_ID) signPersGroup,\n" +
                    "\t(SELECT SIGN_SERVICE_B_COLOR FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) jjylcolor,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_DISEASE g WHERE g.LABEL_TYPE = 2 AND g.LABEL_SIGN_ID = a.REF_SIGN_ID ) labtitle,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_COLOR) FROM APP_LABEL_DISEASE g WHERE g.LABEL_TYPE = 2 AND g.LABEL_SIGN_ID = a.REF_SIGN_ID ) labcolor,\n" +
                    "\tDATE_FORMAT((SELECT SIGN_DATE FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID), '%Y-%m-%d') signDate,\n" +
                    "\t(SELECT SIGN_PAY_STATE FROM APP_SIGN_FORM WHERE ID = a.REF_SIGN_ID) signPayState,\n" +
                    "\ta.REF_SIGN_ID signFormId,\n" +
                    "\ta.REF_STATE referralState\n" +
                    "FROM\n" +
                    "\tAPP_REFERRAL_TABLE a\n" +
                    "LEFT JOIN APP_LABEL_GROUP b ON a.REF_SIGN_ID = b.LABEL_SIGN_ID\n" +
                    "LEFT JOIN APP_PATIENT_USER c ON a.REF_PATIENT_ID = c.ID\n" +
                    "WHERE\n" +
                    "\t1 = 1\n" +
                    " AND a.REF_STATE = :refState\n";
                sql += " AND a.REF_IN_ORG_ID = :deptId ";
                if(StringUtils.isNotBlank(qvo.getPatientName())){
                    map.put("patientName","%"+qvo.getPatientName()+"%");
                    sql += " AND c.PATIENT_NAME LIKE :patientName ";
                }
            sql +=  " GROUP BY c.ID";
            List<ReferralInfo> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,ReferralInfo.class,qvo);
            return list;
        }
        return null;
    }
}
