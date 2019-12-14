package com.ylz.bizDo.message.dao;

import com.ylz.bizDo.message.vo.MessageDrInfo;
import com.ylz.bizDo.message.vo.MessagePatientInfo;
import com.ylz.bizDo.message.vo.MessageQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hzk on 2017/7/4.
 */
@Service("messageDao")
@Transactional(rollbackForClassName={"Exception"})
public class MessageDaoImpl implements MessageDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 患者根据用户id查询 团队所有医生
     * @param qvo patientId 患者id name 姓名 模糊查询
     * @return
     */
    public List<MessageDrInfo> findDrListByPatientId(MessageQvo qvo) throws Exception{
        List<MessageDrInfo> ls=new ArrayList<MessageDrInfo>();
        HashMap<String,Object> map=new HashMap<String, Object>();
        String sql="SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.DR_NAME drName,\n" +
                "\t(select code_title from CD_CODE where code_group='workType' and code_value=b.MEM_WORK_TYPE) workType,\n" +
                "\ta.DR_GOOD drGood,\n" +
                "\ta.DR_IMAGEURL drImageurl,\n" +
                "\ta.DR_GENDER drGender\n" +
                "FROM\n" +
                "\tAPP_DR_USER a LEFT JOIN APP_TEAM_MEMBER b ON a.ID = b.MEM_DR_ID\n" +
                "WHERE\n" +
                "\tb.MEM_TEAMID = (\n" +
                "\t\tSELECT\n" +
                "\t\t\tSIGN_TEAM_ID\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_SIGN_FORM\n" +
                "\t\tWHERE\n" +
                "\t\t\tSIGN_STATE = '2'\n" +
                "\t\tAND SIGN_PATIENT_ID =:SIGN_PATIENT_ID\n" +
                "\t)";
        map.put("SIGN_PATIENT_ID",qvo.getPatientId());
        if (StringUtils.isNotBlank(qvo.getName())) {
            sql+=" AND a.DR_NAME LIKE :DR_NAME";
            map.put("DR_NAME","%"+qvo.getName()+"%");
        }
        ls=this.sysDao.getServiceDo().findSqlMapRVo(sql,map,MessageDrInfo.class,qvo);
        return ls;
    }

    /**
     * 医生 用团队id查询 患者列表
     * @param qvo teamId 团队id name 姓名 模糊查询
     * @return
     */
    public List<MessagePatientInfo> findPatientListByTeamId(MessageQvo qvo) throws Exception{
        List<MessagePatientInfo> ls=new ArrayList<MessagePatientInfo>();
        HashMap<String,Object> map=new HashMap<String, Object>();
        String sql="SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.ID userId,\n" +
                "\ta.PATIENT_NAME patientName,\n" +
                "\ta.PATIENT_AGE patientAge,\n" +
                "\ta.PATIENT_GENDER patientGender,\n" +
                "\ta.PATIENT_IMAGEURL patientImageurl," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE='2' and g.LABEL_SIGN_ID=b.ID) labelColor," +
                "(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE='2' and g.LABEL_SIGN_ID=b.ID) labelTitle\n" +
                "FROM\n" +
                "\tAPP_PATIENT_USER a LEFT JOIN APP_SIGN_FORM b ON a.ID = b.SIGN_PATIENT_ID\n" +
                "WHERE\n" +
                "\tb.SIGN_STATE IN ('0','2')\n" +
                "\tAND b.SIGN_TEAM_ID = :SIGN_TEAM_ID\n"+
                "\tAND a.PATIENT_EASE_STATE IS NOT NULL";
        map.put("SIGN_TEAM_ID",qvo.getTeamId());
        if (StringUtils.isNotBlank(qvo.getName())) {
            sql+=" AND a.PATIENT_NAME LIKE :PATIENT_NAME";
            map.put("PATIENT_NAME","%"+qvo.getName()+"%");
        }
//        if (StringUtils.isNotBlank(qvo.getDrId())) {
//            sql+=" AND b.SIGN_DR_ID = :DR_ID";
//            map.put("DR_ID",qvo.getDrId());
//        }
        ls=this.sysDao.getServiceDo().findSqlMapRVo(sql,map,MessagePatientInfo.class,qvo);
        return ls;
    }

}
