package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSignControlDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.comEnum.UserUpHpisType;
import com.ylz.packcommon.common.util.SericuryUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/11/16.
 */
@Service("appSignControlDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSignControlDaoImpl implements AppSignControlDao {

    @Autowired
    private SysDao sysDao;


    /**
     * 创建环信账号
     */
    @Override
    public void createEase() throws Exception {
        SericuryUtil p = new SericuryUtil();
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT * FROM APP_DR_USER where DR_EASE_STATE is NULL ";
        List<AppDrUser> lsDrUser = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
        if(lsDrUser != null && lsDrUser.size()>0){
            for(AppDrUser drUser : lsDrUser){
                if(drUser != null && StringUtils.isBlank(drUser.getDrEaseState())){
                    this.sysDao.getSecurityCardAsyncBean().registeredEasemobTemp(drUser.getId());
                }
            }
        }
        sql = "SELECT * FROM APP_TEAM WHERE TEAM_EASE_ROOM_ID is NULL OR TEAM_EASE_GROUP_ID IS NUll AND TEAM_DEL_STATE = '0' ";
        List<AppTeam> lsTeam = sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
        if(lsTeam != null && lsTeam.size()>0){
            for(AppTeam team : lsTeam){
                if(team != null && StringUtils.isBlank(team.getTeamEaseGroupId())){
                    this.sysDao.getSecurityCardAsyncBean().addGroupTemp(team);
                }
                if(team != null && StringUtils.isBlank(team.getTeamEaseRoomId())){
                    this.sysDao.getSecurityCardAsyncBean().addRoomTeamp(team, CommonShortType.YISHENG.getValue());
                }
            }
        }
//        map.put("SIGN_STATE", UserUpHpisType.WEIJIHUO.getValue());
//        sql = " SELECT * FROM APP_SIGN_CONTROL WHERE 1=1 ";
//        sql += " AND SIGN_STATE = :SIGN_STATE ";
//        List<AppSignControl> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppSignControl.class);
//        if(ls != null && ls.size() >0){
//            for(AppSignControl vos : ls){
//                    AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,vos.getSignDrId());
//                    if(drUser != null && StringUtils.isBlank(drUser.getDrEaseState())){
//                        this.sysDao.getSecurityCardAsyncBean().registeredEasemobTemp(drUser.getId());
//                    }
//                    AppPatientUser user = (AppPatientUser) this.sysDao.getServiceDo().find(AppPatientUser.class,vos.getSignPatientId());
//                    if(user != null && StringUtils.isBlank(user.getPatientEaseState())){
//                        this.sysDao.getSecurityCardAsyncBean().registeredEasemobTemp(user.getId());
//                    }
//                    AppTeam team = (AppTeam)this.sysDao.getServiceDo().find(AppTeam.class,vos.getSignTeamId());
//                    if(team != null && StringUtils.isBlank(team.getTeamEaseGroupId())){
//                        this.sysDao.getSecurityCardAsyncBean().addGroupTemp(team);
//                    }else{
//                        AppTeamMember member = this.sysDao.getAppTeamMemberDao().findMemByDrId(vos.getSignDrId(),team.getId());
//                        if(member == null){
//                            continue;
//                        }
//                        this.sysDao.getSecurityCardAsyncBean().addGroupMembersTemp(team.getTeamEaseGroupId(),p.encrypt(member.getMemDrId()));
//                    }
//                    if(team != null && StringUtils.isBlank(team.getTeamEaseRoomId())){
//                        this.sysDao.getSecurityCardAsyncBean().addRoomTeamp(team,CommonShortType.YISHENG.getValue());
//                    }else{
//                        AppTeamMember member = this.sysDao.getAppTeamMemberDao().findMemByDrId(vos.getSignDrId(),team.getId());
//                        if(member == null){
//                            continue;
//                        }
//                        this.sysDao.getSecurityCardAsyncBean().addRoomMembersTemp(team.getTeamEaseRoomId(),p.encrypt(member.getMemDrId()),CommonShortType.YISHENG.getValue());
//                    }
//                    vos.setSignState(UserUpHpisType.JIHUO.getValue());
//                    sysDao.getServiceDo().modify(vos);
//            }
//        }
    }
}
