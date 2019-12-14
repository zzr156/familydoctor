package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppDrugDao;
import com.ylz.bizDo.app.po.AppPersonDrug;
import com.ylz.bizDo.jtapp.commonEntity.AppDrugEntity;
import com.ylz.bizDo.jtapp.commonVo.AppDrugVo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appDrugDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppDrugDaoImpl implements AppDrugDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppDrugEntity> findByDrugName(String drugName,String hospId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();

        String sql = "SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.DRUG_NAME drugName,\n" +
                "\ta.DRUG_TYPE drugType,\n" +
                "\ta.DRUG_PERIOD drugPeriod,\n" +
                "\ta.DRUG_FREQUENCY drugFrequency,\n" +
                "\ta.DRUG_USAGE drugUsage,\n" +
                "\ta.DRUG_TAKING drugTaking,\n" +
                "\ta.DRUG_PERIOD_OTHER drugPeriodOther,\n" +
                "\ta.DRUG_FREQUENCY_OTHER drugFrequencyOther,\n" +
                "\ta.DRUG_SPEC drugSpec,\n" +
                "\ta.DRUG_PHARM_ACOLOGY drugPharmAcology,\n" +
                "\ta.DRUG_USE_LEVEL drugUseLevel,\n" +
                "\ta.DRUG_DOSAGE_UNIT drugDosageUnit\n" +
                "FROM\n" +
                "\tAPP_DRUG a\n" +
                "WHERE 1=1 ";
        if(StringUtils.isNotBlank(drugName)){
            map.put("drugName","%"+drugName+"%");
            sql += " AND a.DRUG_NAME LIKE :drugName";
        }
        if(StringUtils.isNotBlank(hospId)){
            map.put("hospId",hospId);
            sql += " AND a.DRUG_HOSP_ID =:hospId";
        }
        return this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrugEntity.class);
    }

    @Override
    public List<AppDrugEntity> findPersonDrug(AppDrugVo vo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "select b.ID id, b.DRUG_NAME drugName, b.DRUG_TYPE drugType, b.DRUG_PERIOD drugPeriod, b.DRUG_FREQUENCY drugFrequency, " +
                "b.DRUG_USAGE drugUsage, b.DRUG_TAKING drugTaking, b.DRUG_PERIOD_OTHER drugPeriodOther,b.DRUG_FREQUENCY_OTHER drugFrequencyOther " +
                " from APP_PERSON_DRUG a, APP_DRUG b where a.PD_DRUG_ID = b.ID ";
        if(StringUtils.isNotBlank(vo.getPatientId())){
            map.put("patientId",vo.getPatientId());
            sql += " AND a.PD_PATIENT_ID = :patientId ";
        }
        if(StringUtils.isNotBlank(vo.getDrugType())){
            map.put("drugType",vo.getDrugType());
            sql += " AND b.DRUG_TYPE = :drugType ";
        }
        sql+=" group by a.PD_DRUG_ID";
        return this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrugEntity.class);
    }

    /**
     * 查询个人药品库中是否已存在该药品
     * @param patientId
     * @param drugId
     * @return
     */
    @Override
    public List<AppPersonDrug> findUniqueDrug(String patientId, String drugId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("patientId",patientId);
        map.put("drugId",drugId);
        String sql = "select * from APP_PERSON_DRUG " +
                "  where PD_PATIENT_ID = :patientId and PD_DRUG_ID = :drugId ";
        sql+=" group by PD_DRUG_ID";
        return this.sysDao.getServiceDo().findSqlMap(sql,map,AppPersonDrug.class);
    }
}
