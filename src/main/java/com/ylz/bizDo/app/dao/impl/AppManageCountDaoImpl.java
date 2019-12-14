package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppManageCountDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppManageArchivingCountQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by asus on 2017/08/28.
 */
@Service("appManageCountDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppManageCountDaoImpl implements AppManageCountDao {

    @Autowired
    private SysDao sysDao;


    /**
     * 查询最早一条签约数据
     * @return
     */
    @Override
    public String findSignFirstDate() throws Exception{
        String result = null;
        String sql = " SELECT DATE_FORMAT(SIGN_DATE,'%Y-%m-%d') SIGN_DATE  FROM APP_SIGN_FORM  GROUP BY SIGN_DATE  LIMIT 1 ";
        List<Map> ls = this.sysDao.getServiceDo().findSql(sql);
        if(ls != null && ls.size() >0){
            if(ls.get(0).get("SIGN_DATE") != null) {
                result = ls.get(0).get("SIGN_DATE").toString();
            }
        }
        return result;
    }

    @Override
    public List<String> findSignDate(String date) throws Exception {
        List<String> ls = new ArrayList<String>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("SIGN_DATE",date);
        String sql = " SELECT r.SIGN_DATE  FROM (SELECT DATE_FORMAT(SIGN_DATE,'%Y-%m') SIGN_DATE  FROM APP_SIGN_FORM) r where r.SIGN_DATE < :SIGN_DATE GROUP BY r.SIGN_DATE ";
        List<Map> lsMap = this.sysDao.getServiceDo().findSqlMap(sql,map);
        if(lsMap != null && lsMap.size() >0){
            for(Map p : lsMap){
                if(p.get("SIGN_DATE") != null ){
                    ls.add(p.get("SIGN_DATE").toString());
                }
            }
        }
        return ls;
    }

    /**
     * 计算各个社区所有数据
     * @param date
     */
    @Override
    public void totalManageCount(String date, List<AppTeamManagEntity> lsTeam) throws Exception {
        try{
            if(lsTeam != null && lsTeam.size()>0){
                long start = Calendar.getInstance().getTimeInMillis();
                System.out.println(date+"开始时间:"+ ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                for(AppTeamManagEntity p : lsTeam){
                    AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,p.getTeamId());
                    if(team == null){
                        continue;
                    }
                    if (StringUtils.isNotBlank(p.getAreaCode())) {
                        CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(p.getAreaCode(),"2"));
                        String[] dates = date.split("-");
                        AppManageCount count = this.sysDao.getAppManageCountDao().findYearMonthByHospTeamId(dates[0], dates[1], p.getHospId(), p.getTeamId());
                        ResidentVo qvo = new ResidentVo();
                        qvo.setYearStart(date);
                        qvo.setYearEnd(date);
                        qvo.setTeamId(p.getTeamId());
                        if(cityCode != null){
                            qvo.setJdSourceType(cityCode.getCodeTitle());
                        }
                        if (count == null) {
                            count = new AppManageCount();
                            count.setManageYear(dates[0]);
                            count.setManageMonth(dates[1]);
                            count.setManageHospId(p.getHospId());
                            count.setManageYearMonth(date);
                            count.setManageAreaCode(p.getAreaCode());
                            count.setManageTeamId(p.getTeamId());
                        }
                        /*else{
                            if(team == null){
                                sysDao.getServiceDo().delete(count);
                                continue;
                            }
                        }*/
                        AppManageCount jsonGender = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findCountGenderInitialise(qvo)), AppManageCount.class);//性别
                        AppManageCount jsonAge = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findCountAgeInitialise(qvo)), AppManageCount.class);//年龄分布
                        AppManageCount jsonPay = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findPayStateCountInitialise(qvo)), AppManageCount.class);//是否支付
                        AppManageCount jsonHealth = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findHealthGroupCountInitialise(qvo)), AppManageCount.class);//健康分布
                        AppManageCount jsonPers = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findPersGroupCountInitialise(qvo)), AppManageCount.class);//服务分布
                        AppManageCount jsonAnalys = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findSignAnalysisListInitialise(qvo)), AppManageCount.class);//签约服务
                        AppManageCount jsonEconomicType = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findSignEconomicTypeListInitialise(qvo)), AppManageCount.class);//经济类型
                        AppManageCount jsonRenew = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findSignRenew(qvo)), AppManageCount.class);//续签数
                        AppManageCount jsonTotalRenew = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findSignTotalRenew(qvo)), AppManageCount.class);//上一年度签约数
                        AppManageCount jsonRenewPers = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findServeRenew(qvo)), AppManageCount.class);//服务续签人数
                        AppManageCount jsonGoToSign = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findGoToSignT(qvo)), AppManageCount.class);//转签数
                        AppManageCount jsonGoToArea = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findGoToSignArea(qvo)), AppManageCount.class);//跨区转签数
                        AppManageCount jsonGoToHosp = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findGoToSignHosp(qvo)), AppManageCount.class);//跨社区转签数
                        AppManageCount jsonGoToTeam = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findGoToSignTeam(qvo)), AppManageCount.class);//跨团队转签数

                        if (jsonGoToArea != null) {//跨区转签数
                            count.setManageGoToSignArea(jsonGoToArea.getManageGoToSignArea());
                        }
                        if (jsonGoToHosp != null) {//跨社区转签数
                            count.setManageGoToSignHosp(jsonGoToHosp.getManageGoToSignHosp());
                        }
                        if (jsonGoToTeam != null) {//跨团队转签数
                            count.setManageGoToSignTeam(jsonGoToTeam.getManageGoToSignTeam());
                        }
                        if (jsonGoToSign != null) {//转签数
                            count.setManageGoToSignCount(jsonGoToSign.getManageGoToSignCount());
                        }
                        if (jsonGender != null) {
                            count.setManageMaleCount(jsonGender.getManageMaleCount());//男
                            count.setManageFemaleCount(jsonGender.getManageFemaleCount());//女
                            count.setManageGenderTotalCount(jsonGender.getManageGenderTotalCount());//统计
                        }
                        if (jsonAge != null) {
                            count.setManage06Count(jsonAge.getManage06Count());//0~6
                            count.setManage718Count(jsonAge.getManage718Count());//7~18
                            count.setManage1930Count(jsonAge.getManage1930Count());//19~30
                            count.setManage3150Count(jsonAge.getManage3150Count());//31~50
                            count.setManage5165Count(jsonAge.getManage5165Count());//51~65
                            count.setManageGreater65Count(jsonAge.getManageGreater65Count());//大于65
                        }
                        if (jsonPay != null) {
                            count.setManageAlreadyPaidCount(jsonPay.getManageAlreadyPaidCount());//已缴费
                            count.setManageUnpaidCount(jsonPay.getManageUnpaidCount());//未缴费
                        }
                        if (jsonHealth != null) {
                            count.setManageHealthCount(jsonHealth.getManageHealthCount());//健康人群
                            count.setManageBeillCount(jsonHealth.getManageBeillCount());//患病人群
                            count.setManageHighRiskCount(jsonHealth.getManageHighRiskCount());//高危人群
                            count.setManageConvalescenceCount(jsonHealth.getManageConvalescenceCount());//恢复期人群
                            count.setManageHealthUnknownCount(jsonHealth.getManageHealthUnknownCount());//健康情况未知
                        }
                        if (jsonPers != null) {
                            count.setManagePlainCount(jsonPers.getManagePlainCount());//普通人群
                            count.setManageChildCount(jsonPers.getManageChildCount());//0~6岁儿童
                            count.setManageMaternalCount(jsonPers.getManageMaternalCount());//孕产妇
                            count.setManageOldPeopleCount(jsonPers.getManageOldPeopleCount());//老年人
                            count.setManageBloodCount(jsonPers.getManageBloodCount());//高血压
                            count.setManageDiabetesCount(jsonPers.getManageDiabetesCount());//糖尿病
                            count.setManagePhthisisCount(jsonPers.getManagePhthisisCount());//结核病
                            count.setManagePsychosisCount(jsonPers.getManagePsychosisCount());//精神病
                            count.setManageDisabledPeopleCount(jsonPers.getManageDisabledPeopleCount());//残疾人
                            count.setManageServiceUnknownCount(jsonPers.getManageServiceUnknownCount());//服务人群其他
                            count.setManageNxgCount(jsonPers.getManageNxgCount());//脑血管
                            count.setManageGxbCount(jsonPers.getManageGxbCount());//冠心病
                            count.setManageAzCount(jsonPers.getManageAzCount());//癌症
                        }
                        if (jsonAnalys != null) {
                            count.setManageSignCount(jsonAnalys.getManageSignCount());//签约数
                            count.setManageKeySignCount(jsonAnalys.getManageKeySignCount());//重点签约数
                            count.setManageSignAppCount(jsonAnalys.getManageSignAppCount());//APP签约数
                            count.setManageSignWebCount(jsonAnalys.getManageSignWebCount());//web签约数
                            count.setManageSignWechatCount(jsonAnalys.getManageSignWechatCount());//微信签约数
                            count.setManageSignAioCount(jsonAnalys.getManageSignAioCount());//一体机签约数
                            count.setManageSignPosCount(jsonAnalys.getManageSignPosCount());//pos机签约数
                        }
                        if (jsonEconomicType != null) {
                            count.setManageEconomicSignCount(jsonEconomicType.getManageEconomicSignCount());//重点人群签约(经济)
                            count.setManageLowFamilyCount(jsonEconomicType.getManageLowFamilyCount());//低困户
                            count.setManageDestituteFamilyCount(jsonEconomicType.getManageDestituteFamilyCount());//特困户（五保户）
                            count.setManageSpecialPlanFamilyCount(jsonEconomicType.getManageSpecialPlanFamilyCount());//计生独伤残家庭
                            count.setManageGeneralPopulationCount(jsonEconomicType.getManageGeneralPopulationCount());//一般人口
                            count.setManagePoorFamilyCount(jsonEconomicType.getManagePoorFamilyCount());//建档立卡贫困人口
                            count.setManageJsdznCount(jsonEconomicType.getManageJsdznCount());//计生独子女户
                            count.setManageJssnCount(jsonEconomicType.getManageJssnCount());//计生双女户
                            count.setManagePkhCount(jsonEconomicType.getManagePkhCount());//贫困户
                            count.setManageQtJjCount(jsonEconomicType.getManageQtJjCount());//其他经济类型
                        }
                        if (jsonRenew != null) {
                            count.setManageRenew(jsonRenew.getManageRenew());//续签人数
                        }
                        if (jsonTotalRenew != null) {
                            count.setManageTotalRenew(jsonTotalRenew.getManageTotalRenew());//总签约数
                        }
                        if (jsonRenewPers != null) {
                            count.setManageRenewPlainCount(jsonRenewPers.getManageRenewPlainCount());//普通人群续签数
                            count.setManageRenewChildCount(jsonRenewPers.getManageRenewChildCount());//0~6岁儿童续签数
                            count.setManageRenewMaternalCount(jsonRenewPers.getManageRenewMaternalCount());//孕产妇续签数
                            count.setManageRenewOldPeopleCount(jsonRenewPers.getManageRenewOldPeopleCount());//老年人续签数
                            count.setManageRenewBloodCount(jsonRenewPers.getManageRenewBloodCount());//高血压续签数
                            count.setManageRenewDiabetesCount(jsonRenewPers.getManageRenewDiabetesCount());//糖尿病续签数
                            count.setManageRenewPhthisisCount(jsonRenewPers.getManageRenewPhthisisCount());//结核病续签数
                            count.setManageRenewPsychosisCount(jsonRenewPers.getManageRenewPsychosisCount());//精神病续签数
                            count.setManageRenewDisabledPeopleCount(jsonRenewPers.getManageRenewDisabledPeopleCount());//残疾人续签数
                            count.setManageRenewServiceUnknownCount(jsonRenewPers.getManageRenewServiceUnknownCount());//服务人群未知续签数
                            count.setManageRenewNxgCount(jsonRenewPers.getManageRenewNxgCount());//脑血管续签数
                            count.setManageRenewGxbCount(jsonRenewPers.getManageRenewGxbCount());//冠心病续签数
                            count.setManageRenewAzCount(jsonRenewPers.getManageRenewAzCount());//癌症续签数
                        }
                        if (StringUtils.isNotBlank(count.getManageSignCount())) {
                            if (Integer.parseInt(count.getManageSignCount()) > 0) {
                                if (StringUtils.isNotBlank(count.getId())) {
                                    this.sysDao.getServiceDo().modify(count);
                                } else {
                                    this.sysDao.getServiceDo().add(count);
                                }
                            }
                        }

                    }
                }
                System.out.println(date+"结束时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                Long s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
                System.out.println(date+"次总耗时:"+s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 计算各个社区所有数据（建档立卡）
     * @param date
     */
    public void totalManageArchivingCount(String date, List<AppTeamManagEntity> lsTeam,String sourceType)  throws Exception{
        try{
            if(lsTeam != null && lsTeam.size()>0){
                long start = Calendar.getInstance().getTimeInMillis();
                System.out.println(date+"开始时间:"+ ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                for(AppTeamManagEntity p : lsTeam){
                    if(StringUtils.isNotBlank(p.getJdAreaCode())){
                        String[] dates = date.split("-");
                        AppManageArchivingCount count = this.sysDao.getAppManageCountDao().findYearMonthArchivingByHospTeamId(dates[0],dates[1],p.getHospId(),p.getTeamId(),p.getJdAreaCode(),p.getAddrHospId());
                        ResidentVo qvo = new ResidentVo();
                        qvo.setYearStart(date);
                        qvo.setYearEnd(date);
                        qvo.setTeamId(p.getTeamId());
                        qvo.setJdAreaCode(p.getJdAreaCode());
                        qvo.setJdSourceType(sourceType);
                        qvo.setHospId(p.getAddrHospId());
                        if(count == null){
                            count = new AppManageArchivingCount();
                            count.setManageYear(dates[0]);
                            count.setManageMonth(dates[1]);
                            count.setManageHospId(p.getHospId());
                            count.setManageYearMonth(date);
                            count.setManageAreaCode(p.getAreaCode());
                            count.setManageTeamId(p.getTeamId());
                            count.setManageJdAreaCode(p.getJdAreaCode());
                            count.setAddrHospId(p.getAddrHospId());
                        }
                        AppManageArchivingCount jsonPers = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findSignAnalysisArchivingListInitialise(qvo)),AppManageArchivingCount.class);//签约服务建档立卡

                        if(jsonPers != null){
                            count.setManageSignCount(jsonPers.getManageSignCount());//签约数
                            count.setManageKeySignCount(jsonPers.getManageKeySignCount());//重点人群
                            count.setManagePlainCount(jsonPers.getManagePlainCount());//普通人群
                            count.setManageChildCount(jsonPers.getManageChildCount());//0~6岁儿童
                            count.setManageMaternalCount(jsonPers.getManageMaternalCount());//孕产妇
                            count.setManageOldPeopleCount(jsonPers.getManageOldPeopleCount());//老年人
                            count.setManageBloodCount(jsonPers.getManageBloodCount());//高血压
                            count.setManageDiabetesCount(jsonPers.getManageDiabetesCount());//糖尿病
                            count.setManagePhthisisCount(jsonPers.getManagePhthisisCount());//结核病
                            count.setManagePsychosisCount(jsonPers.getManagePsychosisCount());//精神病
                            count.setManageDisabledPeopleCount(jsonPers.getManageDisabledPeopleCount());//残疾人
                            count.setManageServiceUnknownCount(jsonPers.getManageServiceUnknownCount());//服务人群未知
                            count.setManageSpecialPlanFamilyCount(jsonPers.getManageSpecialPlanFamilyCount());//计生
                        }

                        if(StringUtils.isNotBlank(count.getManageSignCount())){
                            if(Integer.parseInt(count.getManageSignCount()) >0){
                                if(StringUtils.isNotBlank(count.getId())){
                                    this.sysDao.getServiceDo().modify(count);
                                }else{
                                    this.sysDao.getServiceDo().add(count);
                                }
                            }
//                            System.out.println("数据:"+count.getManageSignCount());
                        }

                    }
                }
                System.out.println(date+"结束时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                Long s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
                System.out.println(date+"次总耗时:"+s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 计算各个社区所有数据所有（建档立卡）
     * @param date
     */
    public void totalManageArchivingAllCount(String date, List<AppTeamManagEntity> lsTeam,String sourceType) throws Exception{
        try{
            if(lsTeam != null && lsTeam.size()>0){
                long start = Calendar.getInstance().getTimeInMillis();
                System.out.println(date+"开始时间:"+ ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                for(AppTeamManagEntity p : lsTeam){
                    if(StringUtils.isNotBlank(p.getAreaCode())){
                        String[] dates = date.split("-");
                        AppManageArchivingAllCount count = this.sysDao.getAppManageCountDao().findYearMonthArchivingByAreaId(dates[0],dates[1],p.getAreaCode(),p.getHospId());
                        ResidentVo qvo = new ResidentVo();
                        qvo.setYearStart(date);
                        qvo.setYearEnd(date);
                        qvo.setAreaId(p.getAreaCode());
                        qvo.setHospId(p.getHospId());
                        qvo.setJdSourceType(sourceType);
                        if(count == null){
                            count = new AppManageArchivingAllCount();
                            count.setManageYear(dates[0]);
                            count.setManageMonth(dates[1]);
                            count.setManageYearMonth(date);
                            count.setManageAreaCode(p.getAreaCode());
                            count.setAddrHospId(p.getHospId());
                        }
                        AppManageArchivingAllCount jsonPers = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findSignAnalysisArchivingAllListInitialise(qvo)),AppManageArchivingAllCount.class);//签约服务建档立卡

                        if(jsonPers != null){
                            count.setManageSignCount(jsonPers.getManageSignCount());//签约数
                            count.setManageNotSignCount(jsonPers.getManageNotSignCount());//未签约数
                            count.setManageKeySignCount(jsonPers.getManageKeySignCount());//重点人群
                            count.setManagePlainCount(jsonPers.getManagePlainCount());//普通人群
                            count.setManageChildCount(jsonPers.getManageChildCount());//0~6岁儿童
                            count.setManageMaternalCount(jsonPers.getManageMaternalCount());//孕产妇
                            count.setManageOldPeopleCount(jsonPers.getManageOldPeopleCount());//老年人
                            count.setManageBloodCount(jsonPers.getManageBloodCount());//高血压
                            count.setManageDiabetesCount(jsonPers.getManageDiabetesCount());//糖尿病
                            count.setManagePhthisisCount(jsonPers.getManagePhthisisCount());//结核病
                            count.setManagePsychosisCount(jsonPers.getManagePsychosisCount());//精神病
                            count.setManageDisabledPeopleCount(jsonPers.getManageDisabledPeopleCount());//残疾人
                            count.setManageServiceUnknownCount(jsonPers.getManageServiceUnknownCount());//服务人群未知
                        }

                        if(StringUtils.isNotBlank(count.getManageSignCount())){
                            if(StringUtils.isNotBlank(count.getId())){
                                this.sysDao.getServiceDo().modify(count);
                            }else{
                                this.sysDao.getServiceDo().add(count);
                            }
                        }
                    }
                }
                System.out.println(date+"结束时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                Long s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
                System.out.println(date+"次总耗时:"+s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public AppManageCount getCopyCount(AppManageCount count,Map<String,Object> map) throws Exception {
        AppManageCount countMap = JsonUtil.fromJson(JsonUtil.toJson(map),AppManageCount.class);
        CopyUtils.Copy(countMap,count);
        return count;
    }

    /**
     * 查询当月是否已生成数据(医院)
     * @param year
     * @param month
     * @param hospId
     * @param teamId
     * @return
     */
    @Override
    public AppManageCount findYearMonthByHospTeamId(String year, String month,String hospId, String teamId) throws Exception {
        return (AppManageCount) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppManageCount.class)
                .add(Restrictions.eq("manageYear", year))
                .add(Restrictions.eq("manageMonth", month))
                .add(Restrictions.eq("manageHospId",hospId ))
                .add(Restrictions.eq("manageTeamId",teamId ))
                .uniqueResult();
    }

    /**
     * 查询当月是否已生成数据
     * @param year
     * @param month
     * @param teamId
     * @return
     */
    public AppManageArchivingCount findYearMonthArchivingByHospTeamId(String year, String month,String hospId, String teamId,String jdAreaCode,String addrHospId) throws Exception{
        return (AppManageArchivingCount) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppManageArchivingCount.class)
                .add(Restrictions.eq("manageYear", year))
                .add(Restrictions.eq("manageMonth", month))
                .add(Restrictions.eq("manageHospId",hospId ))
                .add(Restrictions.eq("manageTeamId",teamId ))
                .add(Restrictions.eq("manageJdAreaCode",jdAreaCode))
                .add(Restrictions.eq("addrHospId",addrHospId))
                .uniqueResult();
    }

    /**
     * 查询当月是否已生成数据
     * @param year
     * @param month
     * @return
     */
    public AppManageArchivingAllCount findYearMonthArchivingByAreaId(String year, String month,String areaCode,String hospId) throws Exception{
        return (AppManageArchivingAllCount) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppManageArchivingAllCount.class)
                .add(Restrictions.eq("manageYear", year))
                .add(Restrictions.eq("manageMonth", month))
                .add(Restrictions.eq("manageAreaCode",areaCode ))
                .add(Restrictions.eq("addrHospId",hospId ))
                .uniqueResult();
    }

    /**
     * 查询当月是否已生成数据(团队)
     * @param year
     * @param month
     * @param teamId
     * @return
     */
    @Override
    public AppManageTeamCount findYearMonthByHospIdByTeam(String year, String month, String teamId) throws Exception {
        return (AppManageTeamCount) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppManageTeamCount.class)
                .add(Restrictions.eq("manageYear", year))
                .add(Restrictions.eq("manageMonth", month))
                .add(Restrictions.eq("manageTeamId", teamId))
                .uniqueResult();
    }

    /**
     * 计算各个团队所有数据
     * @param date
     */
    @Override
    public void totalManageTeamCount(String date, List<AppTeam> lsTeam) throws Exception {
        try{
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeam p : lsTeam){
                    String[] dates = date.split("-");
                    AppManageTeamCount count = this.sysDao.getAppManageCountDao().findYearMonthByHospIdByTeam(dates[0],dates[1],p.getId());
                    ResidentVo qvo = new ResidentVo();
                    qvo.setYearStart(date);
                    qvo.setYearEnd(date);
                    qvo.setTeamId(p.getId());
                    if(count == null){
                        count = new AppManageTeamCount();
                        count.setManageYear(dates[0]);
                        count.setManageMonth(dates[1]);
                        count.setManageHospId(p.getTeamHospId());
                        count.setManageYearMonth(date);
                        count.setManageTeamId(p.getId());
                    }
                    AppManageCount jsonGender = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findCountGenderInitialise(qvo)),AppManageCount.class);//性别
                    AppManageCount jsonAge = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findCountAgeInitialise(qvo)),AppManageCount.class);//年龄分布
                    AppManageCount jsonPay = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findPayStateCountInitialise(qvo)),AppManageCount.class);//是否支付
                    AppManageCount jsonHealth = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findHealthGroupCountInitialise(qvo)),AppManageCount.class);//健康分布
                    AppManageCount jsonPers = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findPersGroupCountInitialise(qvo)),AppManageCount.class);//服务分布
                    AppManageCount jsonAnalys = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findSignAnalysisListInitialise(qvo)),AppManageCount.class);//签约服务
                    if(jsonGender != null){
                        count.setManageMaleCount(jsonGender.getManageMaleCount());//男
                        count.setManageFemaleCount(jsonGender.getManageFemaleCount());//女
                        count.setManageGenderTotalCount(jsonGender.getManageGenderTotalCount());//统计
                    }
                    if(jsonAge != null){
                        count.setManage06Count(jsonAge.getManage06Count());//0~6
                        count.setManage718Count(jsonAge.getManage718Count());//7~18
                        count.setManage1930Count(jsonAge.getManage1930Count());//19~30
                        count.setManage3150Count(jsonAge.getManage3150Count());//31~50
                        count.setManage5165Count(jsonAge.getManage5165Count());//51~65
                        count.setManageGreater65Count(jsonAge.getManageGreater65Count());//大于65
                    }
                    if(jsonPay != null){
                        count.setManageAlreadyPaidCount(jsonPay.getManageAlreadyPaidCount());//已缴费
                        count.setManageUnpaidCount(jsonPay.getManageUnpaidCount());//未缴费
                    }
                    if(jsonHealth != null){
                        count.setManageHealthCount(jsonHealth.getManageHealthCount());//健康人群
                        count.setManageBeillCount(jsonHealth.getManageBeillCount());//患病人群
                        count.setManageHighRiskCount(jsonHealth.getManageHighRiskCount());//高危人群
                        count.setManageConvalescenceCount(jsonHealth.getManageConvalescenceCount());//恢复期人群
                        count.setManageHealthUnknownCount(jsonHealth.getManageHealthUnknownCount());//健康情况未知
                    }
                    if(jsonPers != null){
                        count.setManagePlainCount(jsonPers.getManagePlainCount());//普通人群
                        count.setManageChildCount(jsonPers.getManageChildCount());//0~6岁儿童
                        count.setManageMaternalCount(jsonPers.getManageMaternalCount());//孕产妇
                        count.setManageOldPeopleCount(jsonPers.getManageOldPeopleCount());//老年人
                        count.setManageBloodCount(jsonPers.getManageBloodCount());//高血压
                        count.setManageDiabetesCount(jsonPers.getManageDiabetesCount());//糖尿病
                        count.setManagePhthisisCount(jsonPers.getManagePhthisisCount());//结核病
                        count.setManagePsychosisCount(jsonPers.getManagePsychosisCount());//精神病
                        count.setManageDisabledPeopleCount(jsonPers.getManageDisabledPeopleCount());//残疾人
                        count.setManageServiceUnknownCount(jsonPers.getManageServiceUnknownCount());//服务人群未知
                    }
                    if(jsonAnalys != null){
                        count.setManageSignCount(jsonAnalys.getManageSignCount());//签约数
                        count.setManageKeySignCount(jsonAnalys.getManageKeySignCount());//重点签约数
                    }

                    if(StringUtils.isNotBlank(count.getId())){
                        this.sysDao.getServiceDo().modify(count);
                    }else{
                        this.sysDao.getServiceDo().add(count);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 建档立卡各未签原因调度统计
     * @param date
     * @param lsString
     */
    @Override
    public void totalManageArchivingPeople(String date, List<AppTeamManagEntity> lsTeam,String sourceType) throws Exception {
            if(lsTeam != null && lsTeam.size()>0){
                long start = Calendar.getInstance().getTimeInMillis();
                System.out.println(date+"开始时间:"+ ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                for(AppTeamManagEntity p : lsTeam){
                    if(StringUtils.isNotBlank(p.getAreaCode())){
                        String[] dates = date.split("-");
                        AppManageArchivingPeople count = this.sysDao.getAppManageCountDao().findArchivingByCityCode(dates[0],dates[1],p.getAreaCode(),p.getHospId());
                        ResidentVo qvo = new ResidentVo();
                        qvo.setYearStart(date);
                        qvo.setYearEnd(date);
                        qvo.setAreaId(p.getAreaCode());
                        qvo.setHospId(p.getHospId());
                        qvo.setJdSourceType(sourceType);
                        if(count == null){
                            count = new AppManageArchivingPeople();
                            count.setManageYear(dates[0]);
                            count.setManageMonth(dates[1]);
                            count.setManageYearMonth(date);
                            count.setStreetCode(p.getAreaCode());
                            count.setAddrHospId(p.getHospId());
                        }
                        AppManageArchivingPeople jsonArc = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findManageArchiving(qvo)),AppManageArchivingPeople.class);
                        if(jsonArc != null){
                            count.setManageTotal(jsonArc.getManageTotal());//合计未签约数
                            count.setTotalArchivingCount(jsonArc.getTotalArchivingCount());//建档立卡人员数
                            count.setTotalSignCount(jsonArc.getTotalSignCount());//累计已签约人数
                            count.setDieCount(jsonArc.getDieCount());//未签约死亡数
                            count.setMissCount(jsonArc.getMissCount());//失联失踪人数
                            count.setMoveOutCount(jsonArc.getMoveOutCount());//迁出人数
                            count.setLongTimeOutCount(jsonArc.getLongTimeOutCount());//长期外出人数
                            count.setRefuseSignCount(jsonArc.getRefuseSignCount());//拒签人数
                            count.setBeExecotedCount(jsonArc.getBeExecotedCount());//服刑人数
                            count.setNameRepeatCount(jsonArc.getNameRepeatCount());//名单重复人数
                            count.setMentalPatientCount(jsonArc.getMentalPatientCount());//精神病人住康复医院，家属外出人数
                            count.setPerformMilitaryService(jsonArc.getPerformMilitaryService());//服兵役人数
                            count.setForeignNationality(jsonArc.getForeignNationality());//外籍人数
                            count.setMarryOutCount(jsonArc.getMarryOutCount());//外嫁人数
                            count.setNewPersonnelCount(jsonArc.getNewPersonnelCount());//新增人员
                            count.setNoConnectionCount(jsonArc.getNoConnectionCount());//联系不上人数
                            count.setIdnoDiscrepancyCount(jsonArc.getIdnoDiscrepancyCount());//身份名字不符人数
                            count.setNotSignGoOut(jsonArc.getNotSignGoOut());//暂时外出无法签约
                            count.setFieldArchivingCount(jsonArc.getFieldArchivingCount());//外地建档人数
                            count.setQuitCount(jsonArc.getQuitCount());//退出人数
                            count.setNotCardCount(jsonArc.getNotCardCount());//无社保卡人数
                            count.setGoAbroadCount(jsonArc.getGoAbroadCount());//出国人数
                            count.setOtherCount(jsonArc.getOtherCount());//其它人数
                            count.setUnfilledCount(jsonArc.getUnfilledCount());//未填写人数
                            count.setSignLowInsuredCount(jsonArc.getSignLowInsuredCount());//累计签约人数中低保户人数
                        }
                        if(StringUtils.isNotBlank(count.getId())){
                            this.sysDao.getServiceDo().modify(count);
                        }else{
                            this.sysDao.getServiceDo().add(count);
                        }
                    }
                }
                System.out.println(date+"结束时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                Long s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
                System.out.println(date+"次总耗时:"+s);
            }
//
//        if(lsString != null && lsString.size()>0){
//            for (CdAddress addr:lsAddr){
//                String code = addr.getCtcode().substring(0,4);
//                if(AddressType.FZS.getValue().equals(code)||AddressType.SMS.getValue().equals(code)||AddressType.ZZS.getValue().equals(code)||
//                        AddressType.QZS.getValue().equals(code)||AddressType.PTS.getValue().equals(code)||AddressType.NPS.getValue().equals(code)){
//                    AppManageArchivingPeople count = this.sysDao.getAppManageCountDao().findArchivingByCityCode(addr.getCtcode());
//                    if(count == null){
//                        count = new AppManageArchivingPeople();
//                        count.setCityCode(addr.getCtcode());
//                    }
//                    AppManageArchivingPeople jsonArc = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findManageArchiving(code)),AppManageArchivingPeople.class);
//                    if(jsonArc != null){
//                        count.setTotalArchivingCount(jsonArc.getTotalArchivingCount());//建档立卡人员数
//                        count.setTotalSignCount(jsonArc.getTotalSignCount());//累计已签约人数
//                        count.setDieCount(jsonArc.getDieCount());//未签约死亡数
//                        count.setMissCount(jsonArc.getMissCount());//失联失踪人数
//                        count.setMoveOutCount(jsonArc.getMoveOutCount());//迁出人数
//                        count.setLongTimeOutCount(jsonArc.getLongTimeOutCount());//长期外出人数
//                        count.setRefuseSignCount(jsonArc.getRefuseSignCount());//拒签人数
//                        count.setBeExecotedCount(jsonArc.getBeExecotedCount());//服刑人数
//                        count.setNameRepeatCount(jsonArc.getNameRepeatCount());//名单重复人数
//                        count.setMentalPatientCount(jsonArc.getMentalPatientCount());//精神病人住康复医院，家属外出人数
//                        count.setPerformMilitaryService(jsonArc.getPerformMilitaryService());//服兵役人数
//                        count.setForeignNationality(jsonArc.getForeignNationality());//外籍人数
//                        count.setMarryOutCount(jsonArc.getMarryOutCount());//外嫁人数
//                        count.setNewPersonnelCount(jsonArc.getNewPersonnelCount());//新增人员
//                        count.setNoConnectionCount(jsonArc.getNoConnectionCount());//联系不上人数
//                        count.setIdnoDiscrepancyCount(jsonArc.getIdnoDiscrepancyCount());//身份名字不符人数
//                        count.setNotSignGoOut(jsonArc.getNotSignGoOut());//暂时外出无法签约
//                        count.setFieldArchivingCount(jsonArc.getFieldArchivingCount());//外地建档人数
//                        count.setQuitCount(jsonArc.getQuitCount());//退出人数
//                        count.setNotCardCount(jsonArc.getNotCardCount());//无社保卡人数
//                        count.setGoAbroadCount(jsonArc.getGoAbroadCount());//出国人数
//                        count.setOtherCount(jsonArc.getOtherCount());//其它人数
//                        count.setUnfilledCount(jsonArc.getUnfilledCount());//未填写人数
//                    }
//                    if(StringUtils.isNotBlank(count.getId())){
//                        this.sysDao.getServiceDo().modify(count);
//                    }else{
//                        this.sysDao.getServiceDo().add(count);
//                    }
//                }
//            }
//        }
    }

    @Override
    public AppManageArchivingPeople findArchivingByCityCode(String year, String month, String areaCode,String hospId) throws Exception {
        return (AppManageArchivingPeople) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppManageArchivingPeople.class)
                .add(Restrictions.eq("manageYear", year))
                .add(Restrictions.eq("manageMonth", month))
                .add(Restrictions.eq("addrHospId",hospId ))
                .add(Restrictions.eq("streetCode",areaCode ))
                .uniqueResult();
    }

    @Override
    public void totalManageChronicCount(String date, List<AppTeamManagEntity> lsTeam)  throws Exception{
            if(lsTeam != null && lsTeam.size()>0){
                long start = Calendar.getInstance().getTimeInMillis();
                System.out.println(date+"开始时间:"+ ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                for(AppTeamManagEntity p : lsTeam){
                    if(StringUtils.isNotBlank(p.getAreaCode())){
                        String[] dates = date.split("-");
                        AppManageChronicDisease count = this.sysDao.getAppManageCountDao().findYearMonthByHospTeamIdByNCD(dates[0],dates[1],p.getHospId(),p.getTeamId());
                        ResidentVo qvo = new ResidentVo();
                        qvo.setYearStart(date);
                        qvo.setYearEnd(date);
                        qvo.setTeamId(p.getTeamId());
                        if(count == null){
                            count = new AppManageChronicDisease();
                            count.setManageYear(dates[0]);
                            count.setManageMonth(dates[1]);
                            count.setManageHospId(p.getHospId());
                            count.setManageYearMonth(date);
                            count.setManageAreaCode(p.getAreaCode());
                            count.setManageTeamId(p.getTeamId());
                        }
                        AppManageChronicDisease json = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findChronic(qvo)),AppManageChronicDisease.class);
                        if(json != null){
                            count.setManageSignCount(json.getManageSignCount());//签约数
                            count.setManageNcdCount(json.getManageNcdCount());//慢病人数(有高血压后糖尿病)
                            count.setManageBloodCount(json.getManageBloodCount());//只有高血压人数
                            count.setManageDiabetesCount(json.getManageDiabetesCount());//只有糖尿病人数
                            count.setManageDisBloodCount(json.getManageDisBloodCount());//既是高血压又是糖尿病人数
                            count.setManageBloodRedCount(json.getManageBloodRedCount());//高血压红标人数
                            count.setManageBloodYellowCount(json.getManageBloodYellowCount());//高血压黄标人数
                            count.setManageBloodGreenCount(json.getManageBloodGreenCount());//高血压绿标人数
                            count.setManageBloodGrayCount(json.getManageBloodGrayCount());//高血压灰标人数
                            count.setManageDiabetesRedCount(json.getManageDiabetesRedCount());//糖尿病红标人数
                            count.setManageDiabetesYellowCount(json.getManageDiabetesYellowCount());//糖尿病黄标人数
                            count.setManageDiabetesGreenCount(json.getManageDiabetesGreenCount());//糖尿病绿标人数
                            count.setManageDiabetesGrayCount(json.getManageDiabetesGrayCount());//糖尿病灰标人数
                            count.setManageLowFamilyCount(json.getManageLowFamilyCount());//低保户(慢病)
                            count.setManageDestituteFamilyCount(json.getManageDestituteFamilyCount());//特困户（五保户）(慢病)
                            count.setManageSpecialPlanFamilyCount(json.getManageSpecialPlanFamilyCount());//计生特殊家庭(慢病)
                            count.setManageGeneralPopulationCount(json.getManageGeneralPopulationCount());//一般人口(慢病)
                            count.setManagePoorFamilyCount(json.getManagePoorFamilyCount());//建档立卡贫困人口(慢病)
                        }
                        if(StringUtils.isNotBlank(count.getManageSignCount())){
                            if(Integer.parseInt(count.getManageSignCount()) >0){
                                if(StringUtils.isNotBlank(count.getId())){
                                    this.sysDao.getServiceDo().modify(count);
                                }else{
                                    this.sysDao.getServiceDo().add(count);
                                }
                            }
                        }
                    }
                }
                System.out.println(date+"结束时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                Long s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
                System.out.println(date+"次总耗时:"+s);
            }
    }

    @Override
    public AppManageChronicDisease findYearMonthByHospTeamIdByNCD(String year, String month, String hospId, String teamId)  throws Exception{
        return (AppManageChronicDisease) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppManageChronicDisease.class)
                .add(Restrictions.eq("manageYear", year))
                .add(Restrictions.eq("manageMonth", month))
                .add(Restrictions.eq("manageHospId",hospId ))
                .add(Restrictions.eq("manageTeamId",teamId ))
                .uniqueResult();
    }

    @Override
    public void totalManageTeam(String date, List<AppTeamManagEntity> lsTeam)  throws Exception{
            if(lsTeam != null && lsTeam.size()>0){
                long start = Calendar.getInstance().getTimeInMillis();
                System.out.println(date+"开始时间:"+ ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                for(AppTeamManagEntity p : lsTeam){
                    if(StringUtils.isNotBlank(p.getTeamId())){
                        AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,p.getTeamId());
                        if(team != null){
                            if (StringUtils.isNotBlank(p.getAreaCode())) {
                                String[] dates = date.split("-");
                                AppManageTeam count = this.sysDao.getAppManageCountDao().findTeamYearMonthByHospTeamId(dates[0], dates[1], p.getHospId(), p.getTeamId(),p.getAreaCode());
                                ResidentVo qvo = new ResidentVo();
                                qvo.setYearStart(date);
                                qvo.setYearEnd(date);
                                qvo.setTeamId(p.getTeamId());
                                if (count == null) {
                                    count = new AppManageTeam();
                                    count.setManageYear(dates[0]);
                                    count.setManageMonth(dates[1]);
                                    count.setManageHospId(p.getHospId());
                                    count.setManageYearMonth(date);
                                    count.setManageAreaCode(p.getAreaCode());
                                    count.setManageTeamId(p.getTeamId());
                                    count.setManageHospLeve(p.getHospLevelType());
                                    if(team.getTeamCreateTime() == null){
                                        count.setManageTeamCreateTime(team.getHsCreateDate());
                                    }else{
                                        count.setManageTeamCreateTime(team.getTeamCreateTime());
                                    }
                                }
                                AppManageTeam jsonTeam = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findManageTeam(qvo)), AppManageTeam.class);//服务分布
                                if (jsonTeam != null) {
                                    count.setManageMemberCount(jsonTeam.getManageMemberCount());//成员数量
                                    count.setManageSignMemberCount(jsonTeam.getManageSignMemberCount());//签约医生数量
                                    count.setManageSignState(jsonTeam.getManageSignState());//是否是签约团队（0否 1是）
                                }
                                if (StringUtils.isNotBlank(count.getId())) {
                                    this.sysDao.getServiceDo().modify(count);
                                } else {
                                    this.sysDao.getServiceDo().add(count);
                                }

                            }
                        }
                    }

                }
                System.out.println(date+"结束时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                Long s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
                System.out.println(date+"次总耗时:"+s);
            }
    }

    @Override
    public AppManageTeam findTeamYearMonthByHospTeamId(String year, String month, String hospId, String teamId,String areaCode)  throws Exception{
        return (AppManageTeam) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppManageTeam.class)
                .add(Restrictions.eq("manageYear", year))
                .add(Restrictions.eq("manageMonth", month))
                .add(Restrictions.eq("manageHospId",hospId))
                .add(Restrictions.eq("manageTeamId",teamId))
                .add(Restrictions.eq("manageAreaCode",areaCode))
                .uniqueResult();
    }

    /**
     * 计算各个社区所有数据
     * @param date
     */
    @Override
    public void totalManageOtherCount(String date, List<AppTeamManagEntity> lsTeam) throws Exception {
        try{
            if(lsTeam != null && lsTeam.size()>0){
                long start = Calendar.getInstance().getTimeInMillis();
                System.out.println(date+"开始时间:"+ ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                for(AppTeamManagEntity p : lsTeam){
                    AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,p.getTeamId());
                    if(team == null){
                        continue;
                    }
                    if (StringUtils.isNotBlank(p.getAreaCode())) {
                        String[] dates = date.split("-");
                        AppManageOtherCount count = this.sysDao.getAppManageCountDao().findYearMonthByHospTeamIdO(dates[0], dates[1], p.getHospId(), p.getTeamId());
                        ResidentVo qvo = new ResidentVo();
                        qvo.setYearStart(date);
                        qvo.setYearEnd(date);
                        qvo.setTeamId(p.getTeamId());
                        if (count == null) {
                            count = new AppManageOtherCount();
                            count.setManageYear(dates[0]);
                            count.setManageMonth(dates[1]);
                            count.setManageHospId(p.getHospId());
                            count.setManageYearMonth(date);
                            count.setManageAreaCode(p.getAreaCode());
                            count.setManageTeamId(p.getTeamId());
                        }

                        //随访量、健康指导、健康教育、求助量、未缴费人数统计
                        AppManageOtherCount jsonOther = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findOtherCount(qvo)), AppManageOtherCount.class);//随访量
                        if(jsonOther != null){
                            count.setManageSfCount(jsonOther.getManageSfCount());//随访数量
                            count.setManageGuideCount(jsonOther.getManageGuideCount());//健康指导数
                            count.setManageHdCount(jsonOther.getManageHdCount());//健康教育数
                            count.setManageHelpCount(jsonOther.getManageHelpCount());//求助数
                            count.setManageNotPayCount(jsonOther.getManageNotPayCount());//未缴费人数
                        }
                        if(StringUtils.isBlank(count.getId())){
                            sysDao.getServiceDo().add(count);
                        }else{
                            sysDao.getServiceDo().modify(count);
                        }

                    }
                }
                System.out.println(date+"结束时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                Long s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
                System.out.println(date+"次总耗时:"+s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查询当月是否已生成数据(医院)
     * @param year
     * @param month
     * @param hospId
     * @param teamId
     * @return
     */
    @Override
    public AppManageOtherCount findYearMonthByHospTeamIdO(String year, String month,String hospId, String teamId) throws Exception {
        return (AppManageOtherCount) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppManageOtherCount.class)
                .add(Restrictions.eq("manageYear", year))
                .add(Restrictions.eq("manageMonth", month))
                .add(Restrictions.eq("manageHospId",hospId ))
                .add(Restrictions.eq("manageTeamId",teamId ))
                .uniqueResult();
    }

    @Override
    public void totalManageRankCount(String date, List<AppTeamManagEntity> lsTeam) throws Exception {
        try{
            if(lsTeam != null && lsTeam.size()>0){
                long start = Calendar.getInstance().getTimeInMillis();
                System.out.println(date+"开始时间:"+ ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                for(AppTeamManagEntity p : lsTeam){
                    AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,p.getTeamId());
                    if(team == null){
                        continue;
                    }
                    if (StringUtils.isNotBlank(p.getAreaCode())) {
                        String[] dates = date.split("-");
                        AppManageRankCount count = this.sysDao.getAppManageCountDao().findYearMonthByHospTeamIdT(dates[0], dates[1], p.getHospId(), p.getTeamId());
                        ResidentVo qvo = new ResidentVo();
                        qvo.setYearStart(date);
                        qvo.setYearEnd(date);
                        qvo.setTeamId(p.getTeamId());
                        if (count == null) {
                            count = new AppManageRankCount();
                            count.setManageYear(dates[0]);
                            count.setManageMonth(dates[1]);
                            count.setManageHospId(p.getHospId());
                            count.setManageYearMonth(date);
                            count.setManageAreaCode(p.getAreaCode());
                            count.setManageTeamId(p.getTeamId());
                        }

                        //随访量、健康指导、健康教育、求助量、未缴费人数统计
                        AppManageRankCount jsonOther = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findRankCount(qvo)), AppManageRankCount.class);//随访量
                        if(jsonOther != null){
                            count.setManageTotalFollow(jsonOther.getManageTotalFollow());//总随访量
                            count.setManageCompleteFollow(jsonOther.getManageCompleteFollow());//完成随访量
                            count.setManageTotalHealth(jsonOther.getManageTotalHealth());//总健康干预量
                            count.setManageCompleteHealth(jsonOther.getManageCompleteHealth());//完成健康干预量
                            count.setManageTotalWork(jsonOther.getManageTotalWork());//总工作量
                            count.setManageCompleteWork(jsonOther.getManageCompleteWork());//完成工作量
                            count.setManageRefuseSign(jsonOther.getManageRefuseSign());//拒签量
                            count.setManageGoToSign(jsonOther.getManageGoToSign());//转签量
                        }
                        if(StringUtils.isBlank(count.getId())){
                            sysDao.getServiceDo().add(count);
                        }else{
                            sysDao.getServiceDo().modify(count);
                        }

                    }
                }
                System.out.println(date+"结束时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                Long s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
                System.out.println(date+"次总耗时:"+s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public AppManageRankCount findYearMonthByHospTeamIdT(String year, String month, String hospId, String teamId) throws Exception {
        return (AppManageRankCount) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppManageRankCount.class)
                .add(Restrictions.eq("manageYear", year))
                .add(Restrictions.eq("manageMonth", month))
                .add(Restrictions.eq("manageHospId",hospId ))
                .add(Restrictions.eq("manageTeamId",teamId ))
                .uniqueResult();
    }
}
