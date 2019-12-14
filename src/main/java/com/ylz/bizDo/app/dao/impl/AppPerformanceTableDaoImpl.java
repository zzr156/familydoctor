package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppPerformanceTableDao;
import com.ylz.bizDo.app.po.AppServeGroup;
import com.ylz.bizDo.app.po.AppServeObject;
import com.ylz.bizDo.app.po.AppServePackage;
import com.ylz.bizDo.app.po.AppServeSetmeal;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.LabelManageType;
import com.ylz.packcommon.common.comEnum.PerformanceType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/5/25.
 */
@Service("appPerformanceTableDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppPerformanceTableDaoImpl implements AppPerformanceTableDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public int findCount(PerformanceDataQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("idNo",qvo.getPerIdno());
        map.put("sermValue",qvo.getSermValue());
        map.put("drId",qvo.getDrId());
        map.put("hospId",qvo.getHospId());
        map.put("sergValue",qvo.getSergValue());
        map.put("serpkValue",qvo.getSerpkValue());
        map.put("teamId",qvo.getTeamId());
        String sql = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tapp_performance_table\n" +
                "WHERE\n" +
                "\t1 = 1 " +
                "AND PER_PATIENT_IDNO = :idNo\n" +
                "AND PER_DR_ID =:drId\n" +
                "AND PER_HOSP_ID = :hospId\n" +
                "AND PER_SERG_VALUE =:sergValue\n" +
                "AND PER_SERM_VALUE =:sermValue\n" +
                "AND PER_SERPK_VALUE =:serpkValue\n" +
                "AND PER_TEAM_ID =:teamId";
        int count = sysDao.getServiceDo().findCount(sql,map);
        return count;
    }

    @Override
    public void addPerformanceOne(PerformanceDataQvo qqvo, String sermeal, String fwType,String perType) throws Exception{
        if(StringUtils.isNotBlank(sermeal)){
            String[] sermeals = sermeal.split(";");
            for(String mealId:sermeals){//遍历服务包主键
                AppServeSetmeal meal = (AppServeSetmeal)sysDao.getServiceDo().find(AppServeSetmeal.class,mealId);
                if(meal != null){
                    if(StringUtils.isNotBlank(meal.getSersmObjectValue())){
                        String[] groupIds = meal.getSersmGroupId().split(";");//组合编号
                        for(String groupId:groupIds){
                            AppServeGroup group = (AppServeGroup)sysDao.getServiceDo().find(AppServeGroup.class,groupId);
                            if(group != null){
                                if(StringUtils.isNotBlank(group.getSergObjectId()) && StringUtils.isNotBlank(group.getSergPkId())){
                                    AppServeObject object = (AppServeObject)sysDao.getServiceDo().find(AppServeObject.class,group.getSergObjectId());
                                    if(LabelManageType.FWRQ.getValue().equals(object.getSeroLabelType()) && fwType.indexOf(object.getSeroFwType())!=-1){//匹配服务人群
                                        List<AppServePackage> pakeges = sysDao.getAppServePackageDao().findPakege(group.getSergPkId(), perType);
                                        if(pakeges != null && pakeges.size()>0){
                                            AppServePackage pakege = pakeges.get(0);
                                            qqvo.setSermValue(meal.getSersmValue());
                                            qqvo.setSergValue(group.getSergValue());
                                            qqvo.setSerpkValue(pakege.getSerpkValue());
                                            qqvo.setSermContent(pakege.getSerpkRemark());
                                            int count = sysDao.getAppPerformanceTableDao().findCount(qqvo);
                                            count +=1;
                                            if(StringUtils.isBlank(qqvo.getPerSource())){
                                                qqvo.setPerSource("2");
                                            }
                                            qqvo.setServeNum(String.valueOf(count));
                                            sysDao.getAppPerformanceStatisticsDao().addPerformance(qqvo);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
