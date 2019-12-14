package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppHealthGuideDao;
import com.ylz.bizDo.jtapp.commonEntity.AppHealthGuideEntity;
import com.ylz.bizDo.jtapp.commonVo.AppHealthGuideQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonGuideType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/20.
 */
@Service("appHealthGuideDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppHealthGuideDaoImpl implements AppHealthGuideDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppHealthGuideEntity> findByPId(AppHealthGuideQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("type", CommonGuideType.JKZD.getValue());
        if(StringUtils.isNotBlank(qvo.getPatientId())){
            map.put("patientId",qvo.getPatientId());
            String sql = " SELECT a.ID id,a.HM_TITLE title,a.HM_CONTENT content," +
                    "a.HM_PATIENT_ID toUserId,'' toUserName,a.HM_IMAGE_URL imageUrl," +
                    "a.HM_DR_ID drId,a.HM_MED_TYPE medTypeName,a.HM_MED_TYPE medType,a.HM_DIS_TYPE disTypeName,a.HM_DIS_TYPE disType," +
                    "(SELECT MEM_WORK_TYPE FROM APP_TEAM_MEMBER WHERE ID = a.HM_TEAM_ID) drType," +
                    "date_format(a.HM_CREATE_TIME,'%Y-%c-%d %H:%i:%s') time,'' drName FROM APP_HEALTH_MEDDLE a WHERE 1=1";
            sql += " AND a.HM_PATIENT_ID =:patientId";
            sql += " AND a.HM_GUIDE_TYPE =:type";
            if(StringUtils.isNotBlank(qvo.getDate())){
                map.put("startDate",qvo.getDate()+"-01-01 00:00:00");
                map.put("endDate",qvo.getDate()+"-12-31 23:59:59");
                sql += " AND a.HM_CREATE_TIME >:startDate";
                sql += " AND a.HM_CREATE_TIME <:endDate";
            }
            sql += " ORDER BY a.HM_CREATE_TIME DESC";
            List<AppHealthGuideEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppHealthGuideEntity.class);
            if(ls!=null && ls.size()>0){
                return ls;
            }
        }
        if(StringUtils.isNotBlank(qvo.getDrId())){
            map.put("drId",qvo.getDrId());
            String sql = " SELECT a.ID id,a.HM_TITLE title,a.HM_IMAGE_URL imageUrl,a.HM_PATIENT_ID toUserId,'' toUserName,a.HM_CONTENT content," +
                    "a.HM_DR_ID drId,a.HM_MED_TYPE medTypeName,a.HM_MED_TYPE medType,a.HM_DIS_TYPE disTypeName,a.HM_DIS_TYPE disType," +
                    "date_format(a.HM_CREATE_TIME,'%Y-%c-%d %H:%i:%s') time,'' drName,'' drType FROM APP_HEALTH_MEDDLE a WHERE 1=1";
            sql += " AND a.HM_DR_ID =:drId";
            sql += " AND a.HM_GUIDE_TYPE =:type";
            if(StringUtils.isNotBlank(qvo.getDate())){
                map.put("startDate",qvo.getDate()+"-01-01 00:00:00");
                map.put("endDate",qvo.getDate()+"-12-31 23:59:59");
                sql += " AND a.HM_CREATE_TIME >:startDate";
                sql += " AND a.HM_CREATE_TIME <:endDate";
            }
            sql += " ORDER BY a.HM_CREATE_TIME DESC";
            List<AppHealthGuideEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppHealthGuideEntity.class);
            if(ls!=null && ls.size()>0){
                return ls;
            }

        }
        return null;
    }


}
