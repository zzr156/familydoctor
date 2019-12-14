package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppExerciseCount;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamExerciseEntity;

import java.util.List;
import java.util.Map;

/**
 * 履约管理
 */
public interface AppExerciseCountDao {

    /**
     * 计算各个社区所有数据
     */
    public void totalExerciseCount(String date,List<AppTeamExerciseEntity> lsExercise) throws Exception;

    /**
     * 查询当月是否已生成数据
     * @param year
     * @param teamId
     * @return
     */
    public AppExerciseCount findYearByHospTeamIdDrId(String year,String hospId, String teamId, String drId) throws Exception;

    public Map findSignPeople(String drId, String year) throws Exception;
    
	/**
	 * 查询某地区某年的数据
	 * 
	 * @param areaCode
	 *            地区
	 * @param year
	 *            年
	 * @return
	 */
	public Map findSignPeopleByAreaCodeAndYear(String areaCode, String year) throws Exception;
}
