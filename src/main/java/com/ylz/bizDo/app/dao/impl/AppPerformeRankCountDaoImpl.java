package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppPerformeRankCountDao;
import com.ylz.bizDo.app.po.AppManageCount;
import com.ylz.bizDo.app.po.AppPerformeRankCount;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zzl on 2018/5/29.
 */
@Service("appPerformeRankCountDao")
@Transactional
public class AppPerformeRankCountDaoImpl implements AppPerformeRankCountDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public void totalPerformanceCount(String date, List<AppTeamManagEntity> lsTeam) throws Exception{
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeamManagEntity p : lsTeam){
                    if(StringUtils.isNotBlank(p.getAreaCode())){
                        String[] dates = date.split("-");
                        AppPerformeRankCount count = this.sysDao.getAppPerformeRankCountDao().findYearMonthByHospTeamId(dates[0],dates[1],p.getHospId(),p.getTeamId());
                        ResidentVo qvo = new ResidentVo();
                        qvo.setYearStart(date);
                        qvo.setYearEnd(date);
                        qvo.setTeamId(p.getTeamId());
                        if(count == null){
                            count = new AppPerformeRankCount();
                            count.setPrcYear(dates[0]);
                            count.setPrcMonth(dates[1]);
                            count.setPrcHospId(p.getHospId());
                            count.setPrcYearMonth(date);
                            count.setPrcAreaCode(p.getAreaCode());
                            count.setPrcTeamId(p.getTeamId());
                        }
                        AppPerformeRankCount jsonDiabetes = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findPerFollowPlan(qvo)),AppPerformeRankCount.class);//统计高血压和糖尿病随访数
                        AppManageCount jsonPers = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findPersGroupCountInitialise(qvo)),AppManageCount.class);//服务分布
                        if(jsonDiabetes != null){
                            count.setPrcDiabetesCount(jsonDiabetes.getPrcDiabetesCount());//糖尿病
                            count.setPrcHypertensionCount(jsonDiabetes.getPrcHypertensionCount());//高血压
                            int one = 0;

                            int two = 0;
                            if(StringUtils.isNotBlank(jsonDiabetes.getPrcDiabetesCount())){
                                one = Integer.parseInt(jsonDiabetes.getPrcDiabetesCount());
                            }
                            if(StringUtils.isNotBlank(jsonDiabetes.getPrcHypertensionCount())){
                                two = Integer.parseInt(jsonDiabetes.getPrcHypertensionCount());
                            }
                            int three = one+two;
                            count.setPrcTotalCount(String.valueOf(three));
                        }
                        if(jsonPers != null){
                            count.setPrcHypertensionPatientCount(jsonPers.getManageBloodCount());//高血压
                            count.setPrcDiabetesPatientCount(jsonPers.getManageDiabetesCount());//糖尿病
                            int one = 0;
                            int two = 0;
                            if(StringUtils.isNotBlank(jsonPers.getManageDiabetesCount())){
                                one = Integer.parseInt(jsonPers.getManageDiabetesCount());
                            }
                            if(StringUtils.isNotBlank(jsonPers.getManageBloodCount())){
                                two = Integer.parseInt(jsonPers.getManageBloodCount());
                            }
                            int three = one+two;
                            count.setPrcTotalPatientCount(String.valueOf(three));
                        }
                        int sfCount = 0;
                        int qyCount = 0;
                        if(StringUtils.isNotBlank(count.getPrcTotalCount())){
                            sfCount = Integer.parseInt(count.getPrcTotalCount());
                        }
                        if(StringUtils.isNotBlank(count.getPrcTotalPatientCount())){
                            qyCount = Integer.parseInt(count.getPrcTotalPatientCount());
                        }
                        BigDecimal zhRate = new BigDecimal(0);
                        if(qyCount > 0 ){
                            zhRate = new BigDecimal(sfCount*100).divide(new BigDecimal(qyCount),0,BigDecimal.ROUND_HALF_UP);
                        }
                        count.setPrcRate(String.valueOf(zhRate));
                        if(StringUtils.isNotBlank(count.getPrcDiabetesCount())&&StringUtils.isNotBlank(count.getPrcHypertensionCount())){
                            if(Integer.parseInt(count.getPrcDiabetesCount())>0||Integer.parseInt(count.getPrcHypertensionCount()) >0){
                                if(StringUtils.isNotBlank(count.getId())){
                                    this.sysDao.getServiceDo().modify(count);
                                }else{
                                    this.sysDao.getServiceDo().add(count);
                                }
                            }
                        }
                    }
                }
            }
    }
    /**
     * 查询当月是否已生成数据
     * @param year
     * @param month
     * @param teamId
     * @return
     */
    public AppPerformeRankCount findYearMonthByHospTeamId(String year, String month, String hospId, String teamId) throws Exception{
        return (AppPerformeRankCount) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppPerformeRankCount.class)
                .add(Restrictions.eq("prcYear", year))
                .add(Restrictions.eq("prcMonth", month))
                .add(Restrictions.eq("prcHospId",hospId ))
                .add(Restrictions.eq("prcTeamId",teamId ))
                .uniqueResult();
    }
}
