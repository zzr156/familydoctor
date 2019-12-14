package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppPerformanceRegularFollowupDao;
import com.ylz.bizDo.jtapp.commonEntity.AppChildLyEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppLyTxEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommLyQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/11/14.
 */
@Service("appPerformanceRegularFollowupDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppPerformanceRegularFollowupDaoImpl implements AppPerformanceRegularFollowupDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 查询履约定期随访记录表
     * @param qvo
     * @return
     */
    @Override
    public List<AppLyTxEntity> findList(AppCommLyQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        Calendar cal = Calendar.getInstance();
        map.put("time",cal.get(Calendar.YEAR));
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState", signStates);
        map.put("type",qvo.getFollowType());
        String sql = "SELECT\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(1)\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_PERFORMANCE_REGULAR_FOLLOWUP\n" +
                "\t\tWHERE\n" +
                "\t\t\tPER_IDNO = a.SIGN_PATIENT_IDNO\n" +
                "\t\tAND PER_DR_ID = a.SIGN_DR_ID\n" +
                "\t\tAND PER_FOLLOW_TYPE = :type\n" +
                "\t\tAND PER_YEAR = :time\n" +
                "\t\tAND TIMESTAMPDIFF(\n" +
                "\t\t\tDAY,\n" +
                "\t\t\tb.SCOW_OUT_HOSPITAL_DATE,\n" +
                "\t\t\tPER_CREATE_DATE\n" +
                "\t\t) <= 7\n" +
                "\t) count,\n" +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\ta.SIGN_PATIENT_IDNO patientIdno\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_SIGN_CHILD_OR_WOMEN b ON a.ID = b.SCOW_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.SIGN_DR_ID = :drId\n" +
                "AND a.sign_state IN (:signState)\n" +
                "AND TIMESTAMPDIFF(\n" +
                "\tDAY,\n" +
                "\tb.SCOW_OUT_HOSPITAL_DATE,\n" +
                "\ta.SIGN_FROM_DATE\n" +
                ") <= 7 " +
                "AND DATE_FORMAT(b.SCOW_OUT_HOSPITAL_DATE,'%Y')=:time";
        List<AppLyTxEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppLyTxEntity.class);
        return list;
    }

    /**
     * 查询0-6岁儿童健康检查履约情况
     * @param qvo
     * @return
     */
    @Override
    public List<AppChildLyEntity> findChildHealth(AppCommLyQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        map.put("fwType", ResidentMangeType.ETLZLS.getValue());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState",signStates);
        String sql = "SELECT\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(1)\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_CHILD_HEALTH_PLAN cc\n" +
                "\t\tWHERE\n" +
                "\t\t\tcc.CHP_CHILD_USER_ID = a.SIGN_PATIENT_ID\n" +
                "\t\tAND cc.CHP_PLAN_DATE < NOW()\n" +
                "\t\tAND DATE_FORMAT(cc.CHP_PLAN_DATE,'%Y')=:year\n" +
                "\t) countTj,\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(1)\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_PERFORMANCE_REGULAR_FOLLOWUP cc\n" +
                "\t\tWHERE\n" +
                "\t\t\tcc.PER_IDNO = a.SIGN_PATIENT_IDNO\n" +
                "\t\tAND cc.PER_DR_ID = a.SIGN_DR_ID\n" +
                "\t\tAND cc.PER_YEAR =:year\n" +
                "\t\tAND cc.PER_FOLLOW_TYPE='2'\n" +
                "\t) count,\n" +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\ta.SIGN_PATIENT_IDNO patientIdno,\n" +
                "\ta.SIGN_DR_ID drId,\n" +
                "\ta.ID signId\n " +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.SIGN_DR_ID =:drId\n" +
                "AND a.SIGN_STATE IN (:signState)\n" +
                "AND b.LABEL_TYPE='3'\n" +
                "AND b.LABEL_VALUE=:fwType";
        List<AppChildLyEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppChildLyEntity.class);
        return list;
    }

    /**
     * 产后访视履约人员列表
     * @param qvo
     * @return
     */
    @Override
    public List<AppLyTxEntity> findPostPartumList(AppCommLyQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        map.put("type",qvo.getFwType());
        String sql = "SELECT\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(1)\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_PERFORMANCE_REGULAR_FOLLOWUP aa\n" +
                "\t\tWHERE\n" +
                "\t\t\taa.PER_IDNO = a.SIGN_PATIENT_IDNO\n" +
                "\t\tAND aa.PER_YEAR =:year\n" +
                "\t\tAND aa.PER_FOLLOW_TYPE=:type\n" +
                "AND aa.PER_DR_ID = a.SIGN_DR_ID " +
                "\t) count,\n" +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\ta.SIGN_DR_ID drId,\n" +
                "\ta.ID signId,\n" +
                "\ta.SIGN_PATIENT_IDNO patientIdno\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "INNER JOIN APP_SIGN_CHILD_OR_WOMEN c ON a.ID = c.SCOW_SIGN_ID\n" +
                "WHERE\n" +
                "\tb.LABEL_TYPE = '3'\n" +
                "AND b.LABEL_VALUE = :type\n" +
                "AND a.SIGN_DR_ID = :drId\n" +
                "AND a.SIGN_STATE = '2'\n" ;
        List<AppLyTxEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppLyTxEntity.class);
        return list;
    }
}
