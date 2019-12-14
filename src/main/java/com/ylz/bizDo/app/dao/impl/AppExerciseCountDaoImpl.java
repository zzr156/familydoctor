package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppExerciseCountDao;
import com.ylz.bizDo.app.po.AppExerciseCount;
import com.ylz.bizDo.app.po.AppManageCount;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamExerciseEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.JsonUtil;
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
 * 履约管理
 */
@Service("appExerciseCountDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppExerciseCountDaoImpl implements AppExerciseCountDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public void totalExerciseCount(String date,List<AppTeamExerciseEntity> lsExercise) throws Exception {
        String startDate = ExtendDate.getYM(ExtendDate.getYearFirst(Integer.parseInt(date)));
        String endDate = ExtendDate.getYM(ExtendDate.getYearLast(Integer.parseInt(date)));
        if(lsExercise != null && lsExercise.size() >0){
            for(AppTeamExerciseEntity p : lsExercise){
                AppExerciseCount count = this.sysDao.getAppExerciseCountDao().findYearByHospTeamIdDrId(date,p.getHospId(),p.getTeamId(),p.getDrId());
                ResidentVo qvo = new ResidentVo();
                qvo.setYearStart(startDate);
                qvo.setYearEnd(endDate);
                qvo.setTeamId(p.getTeamId());
                qvo.setDrId(p.getDrId());
                if(count == null){
                    count = new AppExerciseCount();
                    count.setManageYear(date);
                    count.setManageHospId(p.getHospId());
                    count.setManageAreaCode(p.getAreaCode());
                    count.setManageTeamId(p.getTeamId());
                    count.setManageDrId(p.getDrId());
                }
                AppExerciseCount jsonPers = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppResidentAnalysisDao().findPersGroupCountInitialise(qvo)),AppExerciseCount.class);//服务分布
                AppExerciseCount jsonAnalys = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findSignAnalysisListInitialise(qvo)),AppExerciseCount.class);//签约服务
                AppExerciseCount jsonEconomicType = JsonUtil.fromJson(JsonUtil.toJson(sysDao.getAppSignAnalysisDao().findSignEconomicTypeListInitialise(qvo)),AppExerciseCount.class);//签约服务

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
                if(jsonEconomicType != null){
                    count.setManageLowFamilyCount(jsonEconomicType.getManageLowFamilyCount());//低困户
                    count.setManageDestituteFamilyCount(jsonEconomicType.getManageDestituteFamilyCount());//特困户（五保户）
                    count.setManageSpecialPlanFamilyCount(jsonEconomicType.getManageSpecialPlanFamilyCount());//计生特殊家庭
                    count.setManageGeneralPopulationCount(jsonEconomicType.getManageGeneralPopulationCount());//一般人口
                    count.setManagePoorFamilyCount(jsonEconomicType.getManagePoorFamilyCount());//建档立卡贫困人口
                }

                if(StringUtils.isNotBlank(count.getId())){
                    this.sysDao.getServiceDo().modify(count);
                }else{
                    this.sysDao.getServiceDo().add(count);
                }
            }
        }
    }

    @Override
    public AppExerciseCount findYearByHospTeamIdDrId(String year, String hospId, String teamId, String drId) throws Exception{
        return (AppExerciseCount) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppExerciseCount.class)
                .add(Restrictions.eq("manageYear", year))
                .add(Restrictions.eq("manageHospId",hospId ))
                .add(Restrictions.eq("manageTeamId",teamId ))
                .add(Restrictions.eq("manageDrId",drId ))
                .uniqueResult();
    }

    @Override
    public Map findSignPeople(String drId, String year) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("year",year);
        map.put("drId",drId);
        String sql = "SELECT " +
                "SUM(MANAGE_PLAIN_COUNT) managePlainCount," +
                "SUM(MANAGE_CHILD_COUNT) manageChildCount," +
                "SUM(MANAGE_MATERNAL_COUNT) manageMaternalCount," +
                "SUM(MANAGE_OLD_PEOPLE_COUNT) manageOldPeopleCount," +
                "SUM(MANAGE_BLOOD_COUNT) manageBloodCount," +
                "SUM(MANAGE_DIABETES_COUNT) manageDiabetesCount," +
                "SUM(MANAGE_PSYCHOSIS_COUNT) managePsychosisCount," +
                "SUM(MANAGE_PHTHISIS_COUNT) managePhthisisCount," +
                "SUM(MANAGE_DISABLED_PEOPLE_COUNT) manageDisabledPeopleCount," +
                "SUM(MANAGE_SERVICE_UNKNOWN_COUNT) manageServiceUnknownCount," +
                "SUM(MANAGE_LOW_FAMILY_COUNT) manageLowFamilyCount," +
                "SUM(MANAGE_DESTITUTE_FAMILY_COUNT) manageDestituteFamilyCount," +
                "SUM(MANAGE_SPECIAL_PLAN_FAMILY_COUNT) manageSpecialPlanFamilyCount," +
                "SUM(MANAGE_GENERAL_POPULATION_COUNT) manageGeneralPopulationCount," +
                "SUM(MANAGE_POOR_FAMILY_COUNT) managePoorFamilyCount " +
                "FROM APP_EXERCISE_COUNT " +
                "WHERE MANAGE_YEAR = :year AND MANAGE_DR_ID =:drId ";
        List<Map> list = sysDao.getServiceDo().findSqlMap(sql,map);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

	@Override
	public Map findSignPeopleByAreaCodeAndYear(String areaCode, String year) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("year", year);
		map.put("areaCode", areaCode + "%");
		String sql = "SELECT " + "SUM(MANAGE_PLAIN_COUNT) managePlainCount,"
				+ "SUM(MANAGE_CHILD_COUNT) manageChildCount," 
				+ "SUM(MANAGE_MATERNAL_COUNT) manageMaternalCount,"
				+ "SUM(MANAGE_OLD_PEOPLE_COUNT) manageOldPeopleCount," 
				+ "SUM(MANAGE_BLOOD_COUNT) manageBloodCount,"
				+ "SUM(MANAGE_DIABETES_COUNT) manageDiabetesCount,"
				+ "SUM(MANAGE_PSYCHOSIS_COUNT) managePsychosisCount,"
				+ "SUM(MANAGE_PHTHISIS_COUNT) managePhthisisCount,"
				+ "SUM(MANAGE_DISABLED_PEOPLE_COUNT) manageDisabledPeopleCount,"
				+ "SUM(MANAGE_SERVICE_UNKNOWN_COUNT) manageServiceUnknownCount,"
				+ "SUM(MANAGE_LOW_FAMILY_COUNT) manageLowFamilyCount,"
				+ "SUM(MANAGE_DESTITUTE_FAMILY_COUNT) manageDestituteFamilyCount,"
				+ "SUM(MANAGE_SPECIAL_PLAN_FAMILY_COUNT) manageSpecialPlanFamilyCount,"
				+ "SUM(MANAGE_GENERAL_POPULATION_COUNT) manageGeneralPopulationCount,"
				+ "SUM(MANAGE_POOR_FAMILY_COUNT) managePoorFamilyCount " + "FROM APP_EXERCISE_COUNT "
				+ "WHERE MANAGE_YEAR = :year AND MANAGE_AREA_CODE like :areaCode ";
		List<Map> list = sysDao.getServiceDo().findSqlMap(sql, map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
