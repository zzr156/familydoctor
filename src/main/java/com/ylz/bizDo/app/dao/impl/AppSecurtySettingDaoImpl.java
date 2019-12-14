package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSecurtySettingDao;
import com.ylz.bizDo.app.po.AppSecurtySetting;
import com.ylz.bizDo.jtapp.patientVo.AppSecurtyQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 安全设置
 */
@Service("appSecurtySettingDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSecurtySettingDaoImpl implements AppSecurtySettingDao {

    @Autowired
    private SysDao sysDao;


    /**
     * 查询安全设置数据
     * @param qvo
     * @return
     */
    @Override
    public List<AppSecurtySetting> findListQvo(AppSecurtyQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SECURTY_SETTING t WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getDrPatientId())){
            map.put("SECURTY_DR_PATIENT_ID",qvo.getDrPatientId());
            sql += " AND t.SECURTY_DR_PATIENT_ID = :SECURTY_DR_PATIENT_ID ";
        }
        return this.sysDao.getServiceDo().findSqlMap(sql,map,AppSecurtySetting.class);
    }

    /**
     * 添加安全设置
     * @param drPatientId
     * @param type
     * @param state
     * @return
     */
    @Override
    public AppSecurtySetting addSecurty(String drPatientId, String type, String state) throws Exception{
        AppSecurtySetting setting = new AppSecurtySetting();
        setting.setSecurtyDrPatientId(drPatientId);
        setting.setSecurtyType(type);
        setting.setSecurtyState(state);
        this.sysDao.getServiceDo().add(setting);
        return setting;
    }

    /**
     * 根据用户和类型查询
     * @param drPatientId
     * @param type
     * @return
     */
    @Override
    public String findByUserTypeId(String drPatientId, String type) throws Exception{
        String result = CommonEnable.JINYONG.getValue();
        if(CommonShortType.HUANGZHE.getValue().equals(type)){
            result = CommonEnable.JINYONG.getValue();
        }else{
            result = CommonEnable.QIYONG.getValue();
        }

        AppSecurtySetting p =  (AppSecurtySetting) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppSecurtySetting.class)
                .add(Restrictions.eq("securtyDrPatientId", drPatientId))
                .add(Restrictions.eq("securtyType", type))
                .uniqueResult();
        if(p != null){
                result = p.getSecurtyState();
        }
        return result;
    }
}
