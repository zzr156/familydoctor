package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppManageCountDao;
import com.ylz.bizDo.app.dao.AppOutpatientCountDao;
import com.ylz.bizDo.app.entity.AppOutpatientCountEntity;
import com.ylz.bizDo.app.po.AppManageCount;
import com.ylz.bizDo.app.po.AppOutpatientCount;
import com.ylz.bizDo.app.vo.AppSignVo;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.MyMathUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/1/11.
 */
@Service("appOutpatientCountDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppOutpatientCountDaoImpl implements AppOutpatientCountDao {

    @Autowired
    private SysDao sysDao;

    /*
     *门诊费用 医保年度支出调度统计
     */

    public void AppOutpatientCount(String date, List<AppTeamManagEntity> lsTeam) throws Exception{
            for(AppTeamManagEntity p : lsTeam) {
                if (StringUtils.isNotBlank(p.getAreaCode())) {
                    String[] dates = date.split("-");
                    AppOutpatientCount count = this.sysDao.getAppOutpatientCountDao().findYearOutpatienByHospTeamId(dates[0], dates[1], p.getHospId(), p.getTeamId());
                    ResidentVo qvo = new ResidentVo();
                    qvo.setYearStart(date);
                    qvo.setYearEnd(date);
                    qvo.setTeamId(p.getTeamId());
                    qvo.setHospId(p.getHospId());
                    if (count == null) {
                        count = new AppOutpatientCount();
                        count.setOutpatientYear(dates[0]);
                        count.setOutpatientMonth(dates[1]);
                        count.setOutpatientYearMonth(date);
                        count.setOutpatientHospId(p.getHospId());
                        count.setOutpatientAreaCode(p.getAreaCode());
                        count.setOutpatientTeamId(p.getTeamId());
                    }
                    AppOutpatientCountEntity countsum = this.sysDao.getAppOutpatientCountDao().findOutpatienByCount(qvo);
                    if(countsum != null){
                        double Proportion = 0;
                        int sum = 0;
                        int one = 0;
                        String outpatientLastyearCost = "";
                        String outpatientThisyearCost = "";
                        count.setOutpatientHosplevelOne(countsum.getOutpatientHosplevelOne());
                        count.setOutpatientHosplevelTwo(countsum.getOutpatientHosplevelTwo());
                        count.setOutpatientHosplevelThree(countsum.getOutpatientHosplevelThree());
                        count.setOutpatientHospcount(countsum.getOutpatientHospcount());
                        outpatientLastyearCost = String.valueOf(countsum.getOutpatientLastyearCost());
                        count.setOutpatientLastyearCost(outpatientLastyearCost);
                        outpatientThisyearCost = String.valueOf(countsum.getOutpatientThisyearCost());
                        count.setOutpatientThisyearCost(outpatientThisyearCost);
                        sum = Integer.valueOf(countsum.getOutpatientHospcount());
                        one = Integer.valueOf(countsum.getOutpatientHosplevelOne());
                        if(sum != 0){
                            Proportion = MyMathUtil.div(Double.valueOf(one),Double.valueOf(sum),4);//基础比率
                            count.setOutpatientHospProportion(String.valueOf(Proportion));
                        }else{

                            count.setOutpatientHospProportion("0.00");
                        }
                        if(StringUtils.isNotBlank(count.getId())){
                            this.sysDao.getServiceDo().modify(count);
                        }else{
                            this.sysDao.getServiceDo().add(count);
                        }
                    }
                }
            }
    }

    /**
     * 查询当月是否有数据
     * @param year
     * @param month
     * @param hospId
     * @param teamId
     * @return
     */
    @Override
    public AppOutpatientCount findYearOutpatienByHospTeamId(String year, String month, String hospId, String teamId) throws Exception{
        return (AppOutpatientCount) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppOutpatientCount.class)
                .add(Restrictions.eq("outpatientYear", year))
                .add(Restrictions.eq("outpatientYearMonth", month))
                .add(Restrictions.eq("outpatientHospId",hospId ))
                .add(Restrictions.eq("outpatientTeamId",teamId ))
                .uniqueResult();
    }

    /**
     *
     * @return
     */
    @Override
    public AppOutpatientCountEntity findOutpatienByCount(ResidentVo qvo) throws Exception{
        HashMap map = new HashMap();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
        String sql = "";
        if(StringUtils.isNotBlank(qvo.getHospId())){
            sql= " SELECT\n" +
                    "CAST( round(SUM(c.OUTPATIENT_FUND_COST),2) as char)  outpatientLastyearCost, \n" +
                    "CAST(round(cast(  SUM(n.OUTPATIENT_FUND_COST)AS DECIMAL (19,2) ),2)as char) outpatientThisyearCost ,\n" +
                    "CAST(SUM(n.OUTPATIENT_HOSP_LEVEL_ONE)as char) outpatientHosplevelOne,\n" +
                    "CAST(SUM(n.OUTPATIENT_HOSP_LEVEL_TWO)as char ) outpatientHosplevelTwo,\n" +
                    "CAST(SUM(n.OUTPATIENT_HOSP_LEVEL_THREE)as char) outpatientHosplevelThree," +
                    " CAST(SUM(n.OUTPATIENT_HOSP_LEVEL_ONE+n.OUTPATIENT_HOSP_LEVEL_TWO+n.OUTPATIENT_HOSP_LEVEL_THREE)as char) outpatientHospcount \n" +
                    "FROM\n" +
                    "\tAPP_SIGN_FORM f\n" +
                    "INNER JOIN APP_OUTPATIENT_COST c ON f.SIGN_PATIENT_ID = c.OUTPATIENT_PATIENT_ID\n" +
                    " INNER JOIN APP_OUTPATIENT_NUMBER n ON f.SIGN_PATIENT_ID = n.OUTPATIENT_PATIENT_ID\n" +
                    "WHERE f.SIGN_HOSP_ID =:signhospid " +
                    " AND f.SIGN_FROM_DATE >=:yearStart  \n" +
                    "AND f.SIGN_FROM_DATE <=:yearEnd   ";
            map.put("signhospid",qvo.getHospId());
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            sql += " AND f.SIGN_TEAM_ID =:signtamid ";
            map.put("signtamid",qvo.getTeamId());
        }

        List<AppOutpatientCountEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppOutpatientCountEntity.class);
        if(ls != null && ls.size()>0){
            if(ls.get(0).getOutpatientLastyearCost() != null ){
                return ls.get(0);
            }
            return null;
        }
        return null;
    }

}
