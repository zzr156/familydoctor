package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSignSettingDao;
import com.ylz.bizDo.app.po.AppSignSetting;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignSettingEntity;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/7/26.
 */
@Service("appSignSettingDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSignSettingDaoImpl implements AppSignSettingDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 保存签约管理设置
     * @param qvo
     * @throws Exception
     */
    @Override
    public AppSignSetting saveSet(AppSignSetting qvo) throws Exception {
        qvo.setSignsCreateTime(Calendar.getInstance());
        AppSignSetting table = (AppSignSetting)sysDao.getServiceDo().find(AppSignSetting.class,qvo.getId());
        if(StringUtils.isNotBlank(qvo.getSignsFree())){
            table.setSignsFree(qvo.getSignsFree());
        }
        if(StringUtils.isNotBlank(qvo.getSignsJjType())){
            if("0".equals(qvo.getSignsJjType())){
                table.setSignsJjType(null);
            }else{
                table.setSignsJjType(qvo.getSignsJjType());
            }
        }
        if(StringUtils.isNotBlank(qvo.getSignsSubsidyType())){
            table.setSignsSubsidyType(qvo.getSignsSubsidyType());
        }
        if(StringUtils.isNotBlank(qvo.getSignsOpenWork())){
            table.setSignsOpenWork(qvo.getSignsOpenWork());
            if(qvo.getSignsOpenWork().equals("0")){
                table.setSignsWorkType(null);
            }else{
                table.setSignsWorkType(qvo.getSignsWorkType());
            }
        }
        if(StringUtils.isNotBlank(qvo.getSignsSerValue())){
            if("0".equals(qvo.getSignsSerValue())){
                table.setSignsSerValue(null);
            }else{
                table.setSignsSerValue(qvo.getSignsSerValue());
            }
        }
        if(StringUtils.isNotBlank(qvo.getSignsServerType())){
            table.setSignsServerType(qvo.getSignsServerType());
        }

//        AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getSignsCreateId());
//        if(drUser!=null){
//            AppHospDept hospDept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
//            if(hospDept!=null){
//                if(StringUtils.isNotBlank(hospDept.getHospAreaCode())){
//                    qvo.setSignsAreaCode(hospDept.getHospAreaCode().substring(0,4));
//                }
//            }
//        }
        sysDao.getServiceDo().modify(table);
        return table;
    }

    /**
     * 查询医院签约服务信息
     * @param hospId
     * @return
     * @throws Exception
     */
    @Override
    public AppSignSettingEntity findByMeal(String hospId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT " +
                "ID id," +
                "SIGNS_OPEN_WORK signsOpenWork," +
                "SIGNS_WORK_TYPE signsWorkType," +
                "SIGNS_FREE signsFree," +
                "SIGNS_AREA_CODE signsAreaCode," +
                "SIGNS_ISORNOT signsIsOrNot," +
                "SIGNS_MEAL_VALUE signsMealValue," +
                "'' mealList " +
                "FROM APP_SIGN_SETTING WHERE 1=1";
        map.put("hospId",hospId);
        sql+=" AND SIGNS_DEPT_ID =:hospId";
        List<AppSignSettingEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSignSettingEntity.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    /**
     * 修改签约管理设置
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSignSetting modifySign(AppSignSetting qvo) throws Exception {
        AppSignSetting table = (AppSignSetting) sysDao.getServiceDo().find(AppSignSetting.class,qvo.getId());
        if(table!=null){
            table.setSignsMealValue(qvo.getSignsMealValue());
            table.setSignsOpenWork(qvo.getSignsOpenWork());
            table.setSignsFree(qvo.getSignsFree());
            table.setSignsCreateTime(Calendar.getInstance());
            table.setSignsWorkType(qvo.getSignsWorkType());
            table.setSignsDeptId(qvo.getSignsDeptId());
            sysDao.getServiceDo().modify(table);
            return table;
        }
        return null;
    }

    /**
     * 新增签约管理设置
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSignSetting saveSign(AppSignSetting qvo) throws Exception {
        sysDao.getServiceDo().add(qvo);
        return qvo;
    }

    /**
     * 查询市级保存的签约管理设置信息
     * @param areaCode
     * @return
     * @throws Exception
     */
    @Override
    public AppSignSetting findByAreaCode(String areaCode) throws Exception{
        return  (AppSignSetting) this.sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppSignSetting.class)
                .add(Restrictions.eq("signsAreaCode", areaCode))
                .uniqueResult();
    }
}
