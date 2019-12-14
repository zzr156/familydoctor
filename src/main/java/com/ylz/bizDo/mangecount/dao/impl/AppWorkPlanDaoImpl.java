package com.ylz.bizDo.mangecount.dao.impl;

import com.ylz.bizDo.mangecount.dao.AppWorkPlanDao;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.ExtendDateUtil;
import com.ylz.packcommon.common.util.MyMathUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appWorkPlanDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppWorkPlanDaoImpl implements AppWorkPlanDao {
    @Autowired
    private SysDao sysDao;


    @Override
    public Map<String, Object> findWorkPlan(ResidentVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> mapCount = new HashMap<String,Object>();
        String sql =  "SELECT count(1) FROM APP_WORKING_PLAN WHERE 1=1  AND PLAN_DATE >= :startDate  AND PLAN_DATE <= :endDate " ;
        //区域统计
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaCode", AreaUtils.getAreaCode(qvo.getAreaId())+"%");
            sql += " AND PLAN_AREA_CODE LIKE :areaCode ";
        }

        //医院统计
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND PLAN_HOSP_ID = :hospId ";
        }

        //团队统计
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND PLAN_TEAM_ID = :teamId ";
        }

        //医生统计
        if(StringUtils.isNotBlank(qvo.getDrId())){
            map.put("drId",qvo.getDrId());
            sql += " AND PLAN_DR_ID = :drId ";
        }

        map.put("startDate",ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("endDate",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
        //总工作量
        String sqlZgzl = sql;
        int zgzl = this.sysDao.getServiceReadDo().findCount(sqlZgzl,map);
        mapCount.put("zgzl",zgzl);

        map.put("planState","1");//未完成状态
        //未完成量
        String sqlWwcl = sql + " AND PLAN_STATE = :planState";
        int wwcl = this.sysDao.getServiceReadDo().findCount(sqlWwcl,map);
        mapCount.put("wwcl",wwcl);

        map.put("planState","2");//已完成状态
        //已完成
        String sqlYwcl = sql + " AND PLAN_STATE = :planState";
        int ywcl = this.sysDao.getServiceReadDo().findCount(sqlYwcl,map);
        mapCount.put("ywcl",ywcl);

        double wcl = 0;
        //完成率
        if(zgzl > 0 ){
            wcl = MyMathUtil.div(Double.valueOf(ywcl),Double.valueOf(zgzl),4)*100;//完成率
        }

        mapCount.put("wcl",MyMathUtil.round(wcl,2));

        List<Map<String, Object>> chartList = new ArrayList<>();
        List<Map<String,String>> list = ExtendDateUtil.getListBetweenMonthMap(qvo.getYearStart(),qvo.getYearEnd());
        List<String> lsString = new ArrayList<String>();
        List<Integer> lsValueZjh = new ArrayList<Integer>();//总计划量
        List<Integer> lsValuewcl= new ArrayList<Integer>();//完成量
        //每月工作情况
        for (Map<String,String> m:list) {
            map.put("startDate", m.get("monthStart"));
            map.put("endDate", m.get("monthEnd"));
            //每天总计划量
            String sqlTodayZjh = sql;
            int toZjh = this.sysDao.getServiceReadDo().findCount(sqlTodayZjh,map);
            map.put("planState","2");//未完成状态
            //每天完成量
            String sqlTodayWcl =  sql + " AND PLAN_STATE = :planState";
            int toWcl = this.sysDao.getServiceReadDo().findCount(sqlTodayWcl,map);

            lsString.add(m.get("monthStart").substring(0,7));
            lsValueZjh.add(toZjh);
            lsValuewcl.add(toWcl);
        }

        mapCount.put("lsString",lsString);
        mapCount.put("lsValueZjh",lsValueZjh);
        mapCount.put("lsValuewcl",lsValuewcl);
        return mapCount;
    }
}
