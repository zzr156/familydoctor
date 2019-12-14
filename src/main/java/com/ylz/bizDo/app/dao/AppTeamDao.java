package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.app.vo.AppTeamQvo;
import com.ylz.bizDo.cd.entity.AddressHospEntity;
import com.ylz.bizDo.jtapp.aioEntity.AppTeamAioEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppArchivintTeamEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTeamMangeEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTeamEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrTeamQvo;
import com.ylz.bizDo.jtapp.gaiRuiEntity.GaiRuiTeamEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamExerciseEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMotoeEntity;
import com.ylz.bizDo.jtapp.patientVo.AppTeamVo;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangeTeamEntity;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeCountQvo;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/14.
 */
public interface AppTeamDao  {
    //根据分页查询
    public List<AppTeam> findListQvo(AppTeamQvo qvo) throws Exception;
    public List<AppTeam> findListQvo2(AppTeamQvo qvo) throws Exception;
    //查询全部
    public List<AppTeam> findAll(String deptId) throws Exception;
    //根据机构id查询该机构下的团队数据
    public List<AppTeamEntity> findTeam(String teamHospId) throws Exception;
    //添加团队
    public void addTeam(AppTeam vo) throws Exception;

    //根据名称模糊搜索团队
    public List<AppTeamEntity> findTeamByName(String teamName) throws Exception;
    //添加团队
    public AppDrTeamEntity saveTeam(AppDrTeamQvo qvo) throws Exception;
    //查询团队列表
    public List<AppDrTeamEntity> findTeamList(AppDrTeamQvo qvo) throws Exception;
    //删除团队
    public void delTeam(String teamId) throws Exception;
    //修改团队
    public AppDrTeamEntity modifyTeam(AppDrTeamQvo qvo) throws Exception;

    public List<AppTeamMangeEntity> findTeamManage(String teamHospId) throws Exception;

    public List<AddressHospEntity> findTeamManageTs(String id) throws Exception;

    /**
     * 根据医生id查询此医生所在的团队列表
     * @param drId
     * @return
     * @throws Exception
     */
    public List<AppDrTeamEntity> findOneByTeam(String drId) throws Exception;

    /**
     * 根据qvo查询团队信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppTeamEntity> findTeamm(AppTeamVo qvo) throws Exception;

    /**
     * 根据qvo查询团队信息
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppTeamMotoeEntity> findTeamMotoe(AppTeamVo qvo) throws Exception;

    public AppTeam findHospDrId(String hospId, String drId) throws Exception;

    /**
     * 查询团队
     * @return
     */
    public List<AppTeam> findtTeamAll() throws Exception;

    /**
     * 查询变更团队列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<YsChangeTeamEntity> findChangeTeam(YsChangeCountQvo qvo) throws Exception;

    /**
     * 统计获取数据
     * @return
     */
    public List<AppTeamManagEntity> findByManageCont() throws Exception;

    /**
     * 统计获取数据
     * @return
     */
    public List<AppTeamExerciseEntity> findByExerciseCount() throws Exception;

    /**
     * 查询一体机团队
     * @param qvo
     * @return
     */
    public List<AppTeamAioEntity> findTeammList(AppTeamVo qvo) throws Exception;

    /**
     * 根据团队主键查询团队
     * @param teamid
     * @return
     */
    public AppTeam findteamById(String teamid) throws Exception;

    //根据地区id查询团队
    public List<AppTeamEntity> findByAreaCode(String areaCode) throws Exception;
    //根据医院机构的地区areacode查询团队
    public List<AppTeamEntity> findByHospAreaCode(String areaCode) throws Exception;

    /**
     * 根据teamName查询团队信息
     * @param teamName
     * @return
     */
    public AppTeam findTeamByTeamName(String teamName) throws Exception;

    /**
     * 根据区域查询团队数据
     * @param areaCode
     * @return
     */
    public List<AppTeamManagEntity> findManageContByAreaCode(String[] areaCode) throws Exception;

    /**
     * 根据区域查询团队数
     * @param areaCode
     * @return
     */
    public List<Map<String,Object>> findTeamCountByAreaId(String areaCode) throws Exception;

    /**
     * 根据机构主键查询团队数
     * @param hospId
     * @return
     */
    public  List<Map<String,Object>> findTeamByHospId(String hospId) throws Exception;

    public List<AppTeam> findTeamByHospIdAndState(String hospId) throws Exception;

    /**
     * 查询尤溪团队
     * @param areaCode
     * @return
     */
    public List<AppTeamManagEntity> findManageNCD(String areaCode) throws Exception;

    /**
     * 根据机构id查询团队和成员信息
     * @param qvo
     * @return
     */
    public List<AppArchivintTeamEntity> findTeamXxByHospId(ResidentVo qvo) throws Exception;

    public Map<String,Object> findTeamCountByHospId(String hospId) throws Exception;

    /**
     * 根据区域统计团队数
     * @param qvo
     * @return
     */
    public List<Map<String,Object>> findTeamCountByAreaIdTwo(ResidentVo qvo) throws Exception;

    public List<AppTeamManagEntity> findManageContByAreaCodeAndTime(String[] areaCode,String time) throws Exception;

    /**
     * 根据区域查询团队数据(新不带删除团队)
     * @param areaCode
     * @return
     */
    public List<AppTeamManagEntity> findManageContByAreaCodeNew(String[] areaCode) throws Exception;

    /**
     * 统计获取数据(不带删除团队)
     * @return
     */
    public List<AppTeamManagEntity> findByManageContNew() throws Exception;

    /**
     * 查询尤溪团队(不带删除团队)
     * @param areaCode
     * @return
     */
    public List<AppTeamManagEntity> findManageNCDNew(String areaCode) throws Exception;

    public List<AppTeamManagEntity> findManageContByAreaCodeAndTimeNew(String[] areaCode,String time) throws Exception;

    /**
     * 根据机构主键查询该机构已删除团队
     * @param hospId
     * @return
     */
    public String findTeamIdsByHospId(String hospId) throws Exception;

    /**
     * 根据机构id、查询时间范围查询已删团队信息
     * @param hospId
     * @param yearStart
     * @param yearEnd
     * @return
     */
    public List<AppTeam> findTeamByTime(String hospId,String yearStart,String yearEnd) throws Exception;

    /**
     * 导入团队和成员数据
     * @param map
     * @return
     * @throws Exception
     */
    public String addImportExcelTeam(Map<Integer, String> map) throws Exception;

    /**
     * 根据机构id查询团队列表
     * @param hospId
     * @return
     * @throws Exception
     */
    public List<GaiRuiTeamEntity> findTeamByGaiRuiHospId(String hospId) throws Exception;

}
