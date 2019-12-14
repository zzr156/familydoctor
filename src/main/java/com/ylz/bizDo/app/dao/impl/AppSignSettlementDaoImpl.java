package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSignSettlementDao;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.commonEntity.AppYbSignEntity;
import com.ylz.bizDo.jtapp.commonVo.AppYbSignQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2019/3/11.
 */
@Service("appSignSettlementDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSignSettlementDaoImpl implements AppSignSettlementDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 查询福州医保签约数据
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppYbSignEntity> findYbSign(AppYbSignQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT ID id,\n" +
                "PATIENT_NAME xm0000,\n" +
                "PATIENT_ID_NO sfzh00,\n" +
                "PATIENT_CARD id0000,\n" +
                "SIGN_DR_ID qyysId,"+
                "(SELECT DR_NAME FROM APP_DR_USER WHERE ID = SIGN_DR_ID ) qyysxm,\n" +
                "SIGN_HOSP_ID qyjg00,\n" +
                "(SELECT HOSP_NAME FROM APP_HOSP_DEPT WHERE ID = SIGN_HOSP_ID ) qyjgName, " +
                "SIGN_FROM_DATE kssj00,\n" +
                "SIGN_END_DATE jzsj00,\n" +
                "TRANSACTION_CODE funid,\n" +
                "SIGN_SERVICE_PAY je0000,\n" +
                "FUND_PAY jjzfe0,\n" +
                "ACCOUNT_PAY zhzfe0,\n" +
                "PERSON_PAY grzfe0,\n" +
                "PUBLIC_PAY gwzfe0 " +
                "FROM APP_SIGN_SETTLEMENT " +
                "WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getPatientIdno())){
            map.put("PATIENT_ID_NO",qvo.getPatientIdno());
            sql += " AND PATIENT_ID_NO =:PATIENT_ID_NO ";
        }
        if(StringUtils.isNotBlank(qvo.getCard())){
            map.put("PATIENT_CARD",qvo.getCard());
            sql += " AND PATIENT_CARD =:PATIENT_CARD ";
        }
        if(StringUtils.isNotBlank(qvo.getOrgId())){
            map.put("SIGN_HOSP_ID",qvo.getOrgId());
            sql += " AND SIGN_HOSP_ID =:SIGN_HOSP_ID ";
        }
        List<AppYbSignEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppYbSignEntity.class,qvo);
        return list;
    }
}
