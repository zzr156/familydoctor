package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppPerformeRankCount;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;

import java.util.List;

/**
 * Created by zzl on 2018/5/29.
 */
public interface AppPerformeRankCountDao {

    /**
     * 计算各个社区所有数据
     * @param date
     */
    public void totalPerformanceCount(String date, List<AppTeamManagEntity> lsTeam) throws Exception;
    /**
     * 查询当月是否已生成数据
     * @param year
     * @param month
     * @param teamId
     * @return
     */
    public AppPerformeRankCount findYearMonthByHospTeamId(String year, String month, String hospId, String teamId) throws Exception;
}
