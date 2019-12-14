package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.bizDo.app.vo.AppTeamMemberQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrChangeInfoEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTeamMemEntiry;
import com.ylz.bizDo.jtapp.drVo.AppDrTeamQvo;
import com.ylz.bizDo.jtapp.gaiRuiEntity.GaiRuiDrEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMemberEntity;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;

/**
 * Created by zzl on 2017/6/14.
 */
public interface AppTeamMemberDao  {
    //分页查询数据
    public List<AppTeamMember> findListQvo(AppTeamMemberQvo qvo) throws Exception;
    //根据团队id查询成员信息
    public List<AppTeamMemberEntity> findMemByTeamId(String teamId) throws Exception;
    //根据医生主键和团队主键查询成员信息
    public AppTeamMember findMemByDrId(String id,String teamId) throws Exception;
    //根据医生主键查询成员信息
    public AppTeamMember findMemberByDrId(String drId) throws Exception;

    public List<AppTeamMember> findTeamId(String signTeamId) throws Exception;
    public List<AppTeamMember> findTeamId(String signTeamId,String teamDrId) throws Exception;
    //添加团队成员
    public void addTeamMem(AppDrTeamQvo qvo) throws Exception;
    //查询成员
    public List<AppDrTeamMemEntiry> findTeamMem(String teamId) throws Exception;
    //删除团队成员
    public void delTeamMem(String id) throws Exception;
    //修改团队成员
    public AppDrTeamMemEntiry modifyTeamMem(AppDrTeamQvo qvo) throws Exception;

    /**
     * 查询本机构下非本团队的医生列表
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppDrChangeInfoEntity> findDr(AppDrTeamQvo qvo) throws Exception;

    /**
     * 查询团队下的医生信息
     * @param teamId
     * @return
     * @throws Exception
     */
    public List<AppDrUser> findAll(String teamId) throws Exception;


    //Dmao 团队id查询团队成员基本信息
    public List<AppTeamMemberEntity> findteamidM(String teamid)throws  Exception;

    /**
     * 团队人员数
     * @param signTeamId
     * @return
     */
    public Integer findTeamPeopleCount(String signTeamId) throws Exception;

    /**
     * 修改团队名称
     * @param vo
     */
    public void updateTeamName(AppTeam vo) throws Exception;

    public void updateTeamMember(AppTeam vo) throws Exception;

    /**
     * 根据医生id查询所以团队id
     * @param drId
     * @return
     */
    public String findByDrId(String drId) throws Exception;

    /**
     * 根据团队id查询在职医生集合
     * @param teamId
     * @return
     */
    public List<AppDrUser> findDrListByTeamId(String teamId) throws Exception;

    /**
     * 根据团队查询团队成员（盖瑞）
     * @param teamId
     * @return
     * @throws Exception
     */
    public List<GaiRuiDrEntity> findDrByTeamId(String teamId) throws Exception;
}
