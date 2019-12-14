package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppManageArchivingCountQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/08/28.
 */
public interface AppManageCountDao {

    /**
     * 查询最早一条签约数据
     * @return
     */
    public String findSignFirstDate() throws Exception;

    public List<String> findSignDate(String date) throws Exception;

    /**
     * 计算各个社区所有数据
     * @param date
     */
    public void totalManageCount(String date, List<AppTeamManagEntity> lsTeam) throws Exception;

    /**
     * 计算各个社区所有数据（建档立卡）
     * @param date
     */
    public void totalManageArchivingCount(String date, List<AppTeamManagEntity> lsTeam,String sourceType) throws Exception;

    /**
     * 计算各个社区所有数据所有（建档立卡）
     * @param date
     */
    public void totalManageArchivingAllCount(String date, List<AppTeamManagEntity> lsTeam,String sourceType) throws Exception;


    /**
     * 查询当月是否已生成数据
     * @param year
     * @param month
     * @param teamId
     * @return
     */
    public AppManageCount findYearMonthByHospTeamId(String year, String month,String hospId, String teamId) throws Exception;

    /**
     * 查询当月是否已生成数据
     * @param year
     * @param month
     * @param teamId
     * @return
     */
    public AppManageArchivingCount findYearMonthArchivingByHospTeamId(String year, String month,String hospId, String teamId,String jdAreaCode,String addrHospId) throws Exception;

    /**
     * 查询当月是否已生成数据
     * @param year
     * @param month
     * @return
     */
    public AppManageArchivingAllCount findYearMonthArchivingByAreaId(String year, String month,String areaCode, String hospId) throws Exception;


    /**
     * 计算各个团队所有数据
     * @param date
     * @param lsTeam
     */
    public void totalManageTeamCount(String date, List<AppTeam> lsTeam) throws Exception;

    /**
     * 查询当月是否已生成数据
     * @param year
     * @param month
     * @param teamId
     * @return
     */
    public AppManageTeamCount findYearMonthByHospIdByTeam(String year, String month, String teamId) throws Exception;

    /**
     * 建档立卡各未签原因调度统计
     * @param date
     * @param lsString
     */
    public void totalManageArchivingPeople(String date, List<AppTeamManagEntity> lsTeam,String sourceType) throws Exception;

    /**
     * 查询建档立卡各未签原因
     * @param year
     * @param month
     * @param hospId
     * @return
     */
    public AppManageArchivingPeople findArchivingByCityCode(String year, String month, String areaCode,String hospId) throws Exception;

    /**
     * 计算各个社区所有数据
     * @param date
     */
    public void totalManageChronicCount(String date, List<AppTeamManagEntity> lsTeam) throws Exception;
    /**
     * 查询当月是否已生成数据
     * @param year
     * @param month
     * @param teamId
     * @return
     */
    public AppManageChronicDisease findYearMonthByHospTeamIdByNCD(String year, String month,String hospId, String teamId) throws Exception;

    /**
     * 计算各个社区团队所有数据
     * @param date
     */
    public void totalManageTeam(String date, List<AppTeamManagEntity> lsTeam) throws Exception;

    /**
     * 查询当月是否已生成数据
     * @param year
     * @param month
     * @param teamId
     * @return
     */
    public AppManageTeam findTeamYearMonthByHospTeamId(String year, String month,String hospId, String teamId,String areaCode) throws Exception;

    /**
     * 计算各个社区所有数据
     * @param date
     */
    public void totalManageOtherCount(String date, List<AppTeamManagEntity> lsTeam) throws Exception;

    /**
     * 查询当月是否已生成数据
     * @param year
     * @param month
     * @param teamId
     * @return
     */
    public AppManageOtherCount findYearMonthByHospTeamIdO(String year, String month,String hospId, String teamId) throws Exception;

    /**
     * 计算各个社区排名统计
     * @param date
     * @param lsTeam
     * @throws Exception
     */
    public void totalManageRankCount(String date,List<AppTeamManagEntity> lsTeam) throws Exception;

    public AppManageRankCount findYearMonthByHospTeamIdT(String year,String month,String hospId,String teamId) throws Exception;


}
