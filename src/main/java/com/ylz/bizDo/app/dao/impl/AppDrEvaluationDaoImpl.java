package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppDrEvaluationDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationCountEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationDrEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationViewEntity;
import com.ylz.bizDo.jtapp.commonVo.AppDrEvaluationQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommSF;
import com.ylz.packcommon.common.comEnum.EvaluationType;
import com.ylz.packcommon.common.util.ArrContrastUtils;
import com.ylz.packcommon.common.util.MyMathUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/07/23.
 */
@Service("appDrEvaluationDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppDrEvaluationDaoImpl implements AppDrEvaluationDao{

    @Autowired
    private SysDao sysDao;
    private static final String anonymous = "匿名";

    /**
     * 根据患者主键查询评价医生列表
     * @param id
     * @return
     */
    @Override
    public List<AppDrEvaluationEntity> findEvaluationPatient(String id) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("EVALUATION_PATIENT_ID",id);
        String sql = "SELECT\n" +
                "\tEVALUATION_PATIENT_ID patientId,\n" +
                "\tEVALUATION_DR_ID drId,\n" +
                "\tEVALUATION_DR_NAME drName,\n" +
                "\tEVALUATION_DR_GENDER drGender,\n" +
                "\tEVALUATION_TEAM_ID teamId,\n" +
                "\t'' countAvgResult\n" +
                "FROM\n" +
                "\tAPP_DR_EVALUATION\n" +
                "WHERE\n" +
                "\tEVALUATION_PATIENT_ID = :EVALUATION_PATIENT_ID\n" +
                "GROUP BY\n" +
                "\tEVALUATION_DR_ID";
        return sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrEvaluationEntity.class);
    }

    @Override
    public List<AppDrEvaluationViewEntity> findEvaluationPatientView(AppDrEvaluationQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tEVALUATION_PATIENT_ID patientId,\n" +
                "\tEVALUATION_PATIENT_NAME patientName,\n" +
                "\tEVALUATION_PATIENT_GENDER patientGender,\n" +
                "\tEVALUATION_COMPETENCE professionalAbility,\n" +
                "\tEVALUATION_SERVICE_ATTITUDE serviceAttitude,\n" +
                "\tEVALUATION_RECOVERY_SPEED recoverySpeed,\n" +
                "\tEVALUATION_CONTENT content,\n" +
                "\tEVALUATION_DATE date, " +
                "\tEVALUATION_METHOD_TYPE methodType " +
                "FROM\n" +
                "\tAPP_DR_EVALUATION t\n" +
                "WHERE 1=1\n";
        if(StringUtils.isNotBlank(qvo.getDrId())){
            map.put("EVALUATION_DR_ID",qvo.getDrId());
            sql += " AND t.EVALUATION_DR_ID = :EVALUATION_DR_ID ";
        }
        if(StringUtils.isNotBlank(qvo.getType())){
            map.put("EVALUATION_TYPE",qvo.getType());
            sql += " AND t.EVALUATION_TYPE = :EVALUATION_TYPE ";
        }
        sql += " ORDER BY EVALUATION_DATE DESC ";
        return sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrEvaluationViewEntity.class,qvo);
    }

    @Override
    public List<AppDrEvaluationViewEntity> findEvaluationPatientView(String drId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tEVALUATION_PATIENT_ID patientId,\n" +
                "\tEVALUATION_PATIENT_NAME patientName,\n" +
                "\tEVALUATION_PATIENT_GENDER patientGender,\n" +
                "\tEVALUATION_COMPETENCE professionalAbility,\n" +
                "\tEVALUATION_SERVICE_ATTITUDE serviceAttitude,\n" +
                "\tEVALUATION_CONTENT content,\n" +
                "\tEVALUATION_DATE date, " +
                "\tEVALUATION_METHOD_TYPE methodType " +
                "FROM\n" +
                "\tAPP_DR_EVALUATION t\n" +
                "WHERE 1=1\n";
        if(StringUtils.isNotBlank(drId)){
            map.put("EVALUATION_DR_ID",drId);
            sql += " AND t.EVALUATION_DR_ID = :EVALUATION_DR_ID ";
        }
        sql += " ORDER BY EVALUATION_DATE DESC ";
        return sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrEvaluationViewEntity.class);
    }

    @Override
    public AppDrEvaluationDrEntity findEvaluationDr(String drId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("EVALUATION_DR_ID",drId);
        String sql = "SELECT\n" +
                "\tsum(EVALUATION_SERVICE_ATTITUDE) serviceAttitude,\n" +
                "\tSUM(EVALUATION_COMPETENCE) professionalAbility,\n" +
                "\tSUM(EVALUATION_RECOVERY_SPEED) recoverySpeed,\n" +
                "\tSUM(EVALUATION_AVERAGE) avegage,\n" +
                "\tcount(1) size,\n" +
                "\t'5' drAllAvegage\n" +
                "FROM\n" +
                "\tAPP_DR_EVALUATION\n" +
                "WHERE\n" +
                "\tEVALUATION_DR_ID = :EVALUATION_DR_ID";
        List<AppDrEvaluationDrEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrEvaluationDrEntity.class);
        if(ls != null && ls.size() >0){
            return ls.get(0);
        }
        return null;
    }

    @Override
    public List<AppDrEvaluationCountEntity> findEvaluationCount(AppDrEvaluationQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sqlGroup = " SELECT EVALUATION_TYPE type,count(1) total FROM APP_DR_EVALUATION WHERE 1=1 ";
        String sqlAll = " SELECT '4' type,count(1) total FROM APP_DR_EVALUATION WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getDrId())){
            map.put("EVALUATION_DR_ID",qvo.getDrId());
            sqlGroup += " AND EVALUATION_DR_ID = :EVALUATION_DR_ID GROUP BY EVALUATION_TYPE ";
            sqlAll += " AND EVALUATION_DR_ID = :EVALUATION_DR_ID ";
        }
        String sql = sqlGroup +" UNION "+sqlAll;
        List<AppDrEvaluationCountEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrEvaluationCountEntity.class);
        String[] a = new String[]{"1","2","3","4"};
        String bResult = null;
        if(ls != null && ls.size()>0){
            for(AppDrEvaluationCountEntity p :ls){
                if(StringUtils.isNotBlank(bResult)){
                    bResult += ";"+p.getType();
                }else {
                    bResult = p.getType();
                }
            }
        }
        String result = null;
        String[] arrResult = ArrContrastUtils.arrContrast(a, bResult.split(";"));
        for (String strResult : arrResult) {
            if(StringUtils.isNotBlank(result)){
                result += ";"+strResult;
            }else{
                result = strResult;
            }
        }
        if(StringUtils.isNotBlank(result)){
            String[] results = result.split(";");
            if(results.length >0){
                for(String s : results){
                    AppDrEvaluationCountEntity entity = new AppDrEvaluationCountEntity();
                    entity.setType(s);
                    entity.setTotal(new BigInteger("0"));
                    ls.add(entity);
                }
            }
        }

        return ls;
    }

    @Override
    public String appAddEvaluation(AppDrEvaluationQvo vo, AppPatientUser user) throws Exception{
        String result = null;
        AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(vo.getDrId());
        if(drUser != null ){
            AppDrEvaluation p = new AppDrEvaluation();
            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
            AppSignForm form = sysDao.getAppSignformDao().getSignFormUserId(user.getId());
            if(form != null){
                p.setEvaluationAreaCode(dept.getHospAreaCode());
                p.setEvaluationHospId(dept.getId());
                p.setEvaluationDrId(drUser.getId());
                p.setEvaluationDrName(drUser.getDrName());
                p.setEvaluationDrGender(drUser.getDrGender());
                p.setEvaluationTeamId(form.getSignTeamId());
                p.setEvaluationContent(vo.getContent());
                p.setEvaluationDate(Calendar.getInstance());
                p.setEvaluationPatientId(user.getId());
                if(vo.getAnonymous().equals("0")){
                    p.setEvaluationPatientName(user.getPatientName());
                }else {
                    p.setEvaluationPatientName(anonymous);
                }
                p.setEvaluationMethodType(vo.getMethodType());
                p.setEvaluationPatientGender(user.getPatientGender());
                p.setEvaluationProfessionalAbility(vo.getProfessionalAbility());
                p.setEvaluationServiceAttitude(vo.getServiceAttitude());
                p.setEvaluationRecoverySpeed(vo.getRecoverySpeed());
                double average = MyMathUtil.add(Double.parseDouble(p.getEvaluationProfessionalAbility()),Double.parseDouble(p.getEvaluationServiceAttitude()));
                average = MyMathUtil.add(average,Double.parseDouble(p.getEvaluationRecoverySpeed()))/3;
                if(average > 3){
                    p.setEvaluationType(EvaluationType.HP.getValue());
                }else if(average >2 && average <= 3){
                    p.setEvaluationType(EvaluationType.ZP.getValue());
                }else{
                    p.setEvaluationType(EvaluationType.CP.getValue());
                }
                p.setEvaluationAverage(String.valueOf(MyMathUtil.round(average,2)));
                this.sysDao.getServiceDo().add(p);
                //评价类型等于3建档,4签约,5随访
                if(vo.getMethodType().equals("3") || vo.getMethodType().equals("4") || vo.getMethodType().equals("5")){
                    if(StringUtils.isBlank(vo.getEvaluationUrl())){
                        result = "评价签名不能为空!";
                    }else{
                        //类型为评价
                        sysDao.getAppSignformDao().saveImage(vo.getEvaluationUrl(),form.getId(),"7", CommSF.YES.getValue(),form.getSignAreaCode());
                    }
                }
            }else{
                result = "该患者还未签约，无法使用评价功能！";
            }
        }else{
            result = "查无医生信息";
        }
        return result;
    }
}
