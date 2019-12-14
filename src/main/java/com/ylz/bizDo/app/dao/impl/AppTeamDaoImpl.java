package com.ylz.bizDo.app.dao.impl;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.dao.AppTeamDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppTeamQvo;
import com.ylz.bizDo.cd.entity.AddressHospEntity;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.jtapp.aioEntity.AppTeamAioEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppArchivintTeamEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppManageTeamEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTeamMangeEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTeamEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrTeamMemQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrTeamQvo;
import com.ylz.bizDo.jtapp.gaiRuiEntity.GaiRuiTeamEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamExerciseEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMotoeEntity;
import com.ylz.bizDo.jtapp.patientVo.AppTeamVo;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangeTeamEntity;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeCountQvo;
import com.ylz.bizDo.mangecount.entity.ManageCountEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.bizDo.web.po.WebTeam;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * Created by zzl on 2017/6/14.
 */
@Service("appTeamDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppTeamDaoImpl implements AppTeamDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppTeam> findListQvo(AppTeamQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT a.* FROM APP_TEAM  as a INNER JOIN APP_HOSP_DEPT c ON a.TEAM_HOSP_ID = c.ID  WHERE 1=1 AND a.TEAM_DEL_STATE = '0' ";
       //团队名称
       if(StringUtils.isNotBlank(qvo.getAppTeamId())){
           map.put("appTeamId","%"+qvo.getAppTeamId()+"%");
           sql += " AND a.TEAM_NAME LIKE :appTeamId";
       }
       //家庭医生
       if(StringUtils.isNotBlank(qvo.getAppTeamDrId())){
           map.put("appTeamDrName","%"+qvo.getAppTeamDrName()+"%");
           sql += " AND a.TEAM_DR_NAME LIKE :appTeamDrName";
       }
       //有效标志
        if(StringUtils.isNotBlank(qvo.getAppTeamState())){
           map.put("appTeamState",qvo.getAppTeamState());
           sql += " AND a.TEAM_STATE = :appTeamState";
        }
        //机构名称
        if(StringUtils.isNotBlank(qvo.getAppTeamHospName())){
            map.put("appTeamHospName", "%"+qvo.getAppTeamHospName()+"%");
            sql += " AND a.TEAM_HOSP_NAME like :appTeamHospName";
        }
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isBlank(qvo.getCity()) && StringUtils.isBlank(qvo.getArea()) && StringUtils.isBlank(qvo.getDrHospId())){
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getPro())+"%");
            sql += " AND c.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isNotBlank(qvo.getCity()) && StringUtils.isBlank(qvo.getArea()) && StringUtils.isBlank(qvo.getDrHospId())){
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getCity())+"%");
            sql += " AND c.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isNotBlank(qvo.getCity()) && StringUtils.isNotBlank(qvo.getArea()) && StringUtils.isBlank(qvo.getDrHospId())){
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getArea())+"%");
            sql += " AND c.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isNotBlank(qvo.getCity()) && StringUtils.isNotBlank(qvo.getArea()) && StringUtils.isNotBlank(qvo.getDrHospId())){
            map.put("appDrHospId",qvo.getDrHospId());
            sql += " AND c.ID = :appDrHospId";
        }
        //查询未删除的团队
        sql += " AND a.TEAM_DEL_STATE = '0' ";
        sql += " ORDER BY a.TEAM_CREATE_TIME ASC";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppTeam.class, qvo);

    }

    public List<AppTeam> findListQvo2(AppTeamQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT a.* FROM APP_TEAM  as a INNER JOIN APP_HOSP_DEPT c ON a.TEAM_HOSP_ID = c.ID  WHERE 1=1 AND a.TEAM_DEL_STATE = '0' ";
        //团队名称
        if(StringUtils.isNotBlank(qvo.getAppTeamId())){
            map.put("appTeamId","%"+qvo.getAppTeamId()+"%");
            sql += " AND a.TEAM_NAME LIKE :appTeamId";
        }
        //家庭医生
        if(StringUtils.isNotBlank(qvo.getAppTeamDrId())){
            map.put("appTeamDrId","%"+qvo.getAppTeamDrId()+"%");
            sql += " AND a.TEAM_DR_NAME LIKE :appTeamDrId";
        }
        //有效标志
        if(StringUtils.isNotBlank(qvo.getAppTeamState())){
            map.put("appTeamState",qvo.getAppTeamState());
            sql += " AND a.TEAM_STATE = :appTeamState";
        }
        //机构名称
        if(StringUtils.isNotBlank(qvo.getAppTeamHospName())){
            map.put("appTeamHospName", "%"+qvo.getAppTeamHospName()+"%");
            sql += " AND a.TEAM_HOSP_NAME like :appTeamHospName";
        }
        if(StringUtils.isNotBlank(qvo.getPro())){
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getPro())+"%");
            sql += " AND c.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getCity())){
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getCity())+"%");
            sql += " AND c.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getArea())){
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getArea())+"%");
            sql += " AND c.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getDrHospId())){
            map.put("appDrHospId",qvo.getDrHospId());
            sql += " AND c.ID = :appDrHospId";
        }
        sql += " AND a.TEAM_DEL_STATE = '0' ";
        sql += " ORDER BY a.TEAM_CREATE_TIME desc";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppTeam.class, qvo);

    }

    @Override
    public List<AppTeam> findAll(String deptId) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        String sql = "SELECT * FROM APP_TEAM  as a WHERE 1=1 AND a.TEAM_DEL_STATE = '0' ";
        if(StringUtils.isNotBlank(deptId)){
            map.put("deptId",deptId);
            sql += " AND a.TEAM_HOSP_ID =:deptId";
        }
        List<AppTeam> ls =sysDao.getServiceDo().findSqlMap(sql, map, AppTeam.class);
        return ls;
    }

    @Override
    public List<AppTeamEntity> findTeam(String teamHospId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamHospId",teamHospId);
        map.put("TEAM_STATE","1");
        String sql = "SELECT a.ID teamId,a.TEAM_NAME teamName, '' num,a.TEAM_DR_ID drId,a.TEAM_DR_NAME drName,'' drList FROM APP_TEAM a" +
                " WHERE a.TEAM_HOSP_ID = :teamHospId AND a.TEAM_DEL_STATE = '0' ";
        //查询未删除团队
        sql += " AND a.TEAM_DEL_STATE = '0' ";
        sql += " AND a.TEAM_STATE =:TEAM_STATE ";
        List<AppTeamEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppTeamEntity.class);
        if(ls!=null){
            return ls;
        }
        return null;
    }

    @Override
    public void addTeam(AppTeam vo) throws Exception {
        Calendar cal = Calendar.getInstance();
        vo.setTeamCreateTime(cal);
        this.sysDao.getServiceDo().add(vo);
        AppTeamMember v = new AppTeamMember();
        v.setMemDrId(vo.getTeamDrId());
        v.setMemTeamid(vo.getId());
        v.setMemTeamName(vo.getTeamName());
        v.setMemWorkType("3");
        v.setMemState("0");
        v.setMemDrName(vo.getTeamDrName());
        AppDrUser duer= (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,vo.getTeamDrId());
        v.setDrRole(duer.getDrJobTitle());
        v.setDrTel(duer.getDrTel());
        sysDao.getServiceDo().add(v);
    }

    @Override
    public List<AppTeamEntity> findTeamByName(String teamName) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamName","%"+teamName+"%");
        String sql = "SELECT a.ID teamId,a.TEAM_NAME teamName, '' num FROM APP_TEAM a , APP_TEAM_MEMBER b" +
                " WHERE a.ID = b.MEM_TEAMID AND (a.TEAM_NAME like :teamName or b.MEM_DR_NAME LIKE :teamName) AND a.TEAM_DEL_STATE = '0' ";
        sql += "GROUP BY a.ID";
        List<AppTeamEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppTeamEntity.class);
        if(ls!=null){
            return ls;
        }
        return null;
    }

    /**
     * 新增团队
     * @param qvo
     * @throws Exception
     */
    @Override
    public AppDrTeamEntity saveTeam(AppDrTeamQvo qvo) throws Exception {
        AppTeam team = new AppTeam();
        AppDrUser drUser =(AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
        if(drUser!=null){
            team.setTeamHospId(drUser.getDrHospId());
            team.setTeamHospName(drUser.getDrHospName());
            team.setTeamTel(drUser.getDrTel());
            team.setTeamDrId(drUser.getId());
            team.setTeamDrName(drUser.getDrName());
            team.setTeamDrCode(drUser.getDrCode());
            team.setTeamState(CommonEnable.QIYONG.getValue());
//            team.setTeamCode(qvo.getTeamCode());
//            team.setTeamSort(qvo.getTeamSort());
//            team.setTeamRemarks(qvo.getTeamRemarks());
            team.setTeamName(qvo.getTeamName());
//            team.setTeamType(qvo.getType());
            team.setTeamCreateTime(Calendar.getInstance());
            sysDao.getServiceDo().add(team);
            AppTeamMember teamMember = new AppTeamMember();
            teamMember.setMemDrId(drUser.getId());
            teamMember.setMemDrName(drUser.getDrName());
            teamMember.setMemWorkType(qvo.getWorkType());
            teamMember.setMemState("0");
            teamMember.setMemTeamid(team.getId());
            teamMember.setMemTeamName(team.getTeamName());
            sysDao.getServiceDo().add(teamMember);
            List<AppDrTeamMemQvo> list= qvo.getTeamMems();
            if(list!=null&&list.size()>0){
                for(AppDrTeamMemQvo ls:list){
                    AppDrUser user = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,ls.getMemId());
                    if(user!=null){
                        AppTeamMember tt = new AppTeamMember();
                        tt.setMemWorkType(ls.getWorkType());
                        tt.setMemTeamid(team.getId());
                        tt.setMemTeamName(team.getTeamName());
                        tt.setMemDrId(user.getId());
                        tt.setMemDrName(user.getDrName());
                        tt.setMemState("1");
                        sysDao.getServiceDo().add(tt);
                    }
                }
            }
            AppDrTeamEntity teamTable = new AppDrTeamEntity();
            teamTable.setId(team.getId());
            teamTable.setType(team.getTeamType());
            teamTable.setDrId(team.getTeamDrId());
            teamTable.setDrName(team.getTeamDrName());
            teamTable.setTeamName(team.getTeamName());


            String result = null;
            try {
                result = PropertiesUtil.getConfValue("messageCode");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(result.equals("0")){
                //环信新群
                sysDao.getSecurityCardAsyncBean().addGroup(team);
                sysDao.getSecurityCardAsyncBean().addRoom(team, CommonShortType.YISHENG.getValue());
            }

            return teamTable;
        }
        return null;
    }

    /**
     * 查询团队列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDrTeamEntity> findTeamList(AppDrTeamQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT a.ID id," +
                "a.TEAM_NAME teamName," +
                "a.TEAM_CODE teamCode," +
                "a.TEAM_DR_ID drId," +
                "a.TEAM_DR_NAME drName," +
                "a.TEAM_REMARKS remarks," +
                "a.TEAM_TEL tel,a.TEAM_TYPE type," +
                "'' typeName," +
                "b.MEM_WORK_TYPE workType," +
                "'' workTypeName," +
                "b.MEM_STATE memState," +
                "DATE_FORMAT(a.TEAM_CREATE_TIME, '%Y-%c-%d %H:%i:%s') time, " +
                " a.TEAM_EASE_GROUP_ID teamEaseGroupId "+
                " FROM APP_TEAM a INNER JOIN APP_TEAM_MEMBER b ON a.TEAM_DR_ID = b.MEM_DR_ID  WHERE 1=1 AND a.ID = b.MEM_TEAMID " +
                " AND a.TEAM_DEL_STATE = '0' ";
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND a.TEAM_HOSP_ID =:hospId";
        }
        sql += " ORDER BY a.TEAM_SORT";
        List<AppDrTeamEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrTeamEntity.class);
        return ls;
    }

    /**
     * 删除团队
     * @param teamId
     * @throws Exception
     */
    @Override
    public void delTeam(String teamId) throws Exception {
        List<AppTeamMember> lss = sysDao.getServiceDo().loadByPk(AppTeamMember.class,"memTeamid",teamId);
        if(lss!=null&&lss.size()>0){
            for(AppTeamMember ls:lss){
                sysDao.getServiceDo().delete(ls);
            }
        }
        AppTeam table = (AppTeam) sysDao.getServiceDo().find(AppTeam.class,teamId);
        String result = null;
        try {
            result = PropertiesUtil.getConfValue("messageCode");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result.equals("0")){
            //环信删群
           sysDao.getSecurityCardAsyncBean().delGroup(table.getTeamEaseGroupId());
           sysDao.getSecurityCardAsyncBean().delRoom(table.getTeamEaseRoomId());
        }
        if(table!=null){
            sysDao.getServiceDo().delete(table);
        }
    }

    @Override
    public AppDrTeamEntity modifyTeam(AppDrTeamQvo qvo) throws Exception {
        AppTeam team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class,qvo.getId());
        if(team!=null){
            if(StringUtils.isNotBlank(qvo.getTeamState())){
                team.setTeamState(qvo.getTeamState());
            }
            if(StringUtils.isNotBlank(qvo.getTeamName())){
                team.setTeamName(qvo.getTeamName());
            }
            if(StringUtils.isNotBlank(qvo.getHospId())){
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
                if(dept!=null){
                    team.setTeamHospId(dept.getId());
                    team.setTeamHospName(dept.getHospName());
                }
            }
            if(StringUtils.isNotBlank(qvo.getType())){
                team.setTeamType(qvo.getType());
            }
            if(StringUtils.isNotBlank(qvo.getTeamRemarks())){
                team.setTeamRemarks(qvo.getTeamRemarks());
            }
            if(qvo.getTeamSort()!=null){
                team.setTeamSort(qvo.getTeamSort());
            }
            if(StringUtils.isNotBlank(qvo.getDrId())){
                //修改家庭医生
                team.setTeamDrId(qvo.getDrId());
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                List<AppTeamMember> lss = sysDao.getServiceDo().loadByPk(AppTeamMember.class,"memTeamid",team.getId());
                if(lss!=null&&lss.size()>0){
                    boolean flag = false;
                    for(AppTeamMember ls:lss){
                        ls.setMemState("1");//此团队中的所有成员都改为队员
                        if(ls.getMemDrId().equals(qvo.getDrId())){//该医生在此团队中只要修改原队长为队员，该医生为队长
                            flag = true;
                            ls.setMemState("0");
                            if(StringUtils.isNotBlank(qvo.getWorkType())){
                                ls.setMemWorkType(qvo.getWorkType());
                            }
                        }
                        sysDao.getServiceDo().modify(ls);
                    }
                    if(!flag){//该医生没有在此团队中
                        AppTeamMember tt = new AppTeamMember();
                        tt.setMemState("0");
                        if(drUser!=null){
                            tt.setMemDrId(drUser.getId());
                            tt.setMemDrName(drUser.getDrName());
                        }
                        tt.setMemTeamName(team.getTeamName());
                        tt.setMemTeamid(team.getId());
                        if(StringUtils.isNotBlank(qvo.getWorkType())){
                            tt.setMemWorkType(qvo.getWorkType());
                        }
                        sysDao.getServiceDo().add(tt);
                        team.setTeamDrId(drUser.getId());
                        team.setTeamDrName(drUser.getDrName());
                        team.setTeamDrCode(drUser.getDrCode());
                        team.setTeamTel(drUser.getDrTel());
                    }
                }
            }
            sysDao.getServiceDo().modify(team);
            //修改成员表中的团队名称
            List<AppTeamMember> ls = sysDao.getServiceDo().loadByPk(AppTeamMember.class,"memTeamid",team.getId());
            if(ls!=null && ls.size()>0){
                for(AppTeamMember l:ls){
                    l.setMemTeamName(team.getTeamName());
                    sysDao.getServiceDo().modify(l);
                }
            }
            AppDrTeamEntity tm = new AppDrTeamEntity();
            tm.setId(team.getId());
            tm.setTeamName(team.getTeamName());
            tm.setTel(team.getTeamTel());
            tm.setDrId(team.getTeamDrId());
            tm.setDrName(team.getTeamDrName());
            tm.setRemarks(team.getTeamRemarks());
            tm.setTeamCode(team.getTeamCode());
            tm.setTime(team.getStrTeamCreateTime());
            tm.setType(team.getTeamType());
            tm.setTypeName(team.getStrTypeName());
            return tm;
        }
        return null;
    }

    @Override
    public List<AppTeamMangeEntity> findTeamManage(String teamHospId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamHospId",teamHospId);
        String sql = "SELECT a.ID teamId,a.TEAM_NAME teamName, '0' teamState FROM APP_TEAM a" +
                " WHERE a.TEAM_HOSP_ID = :teamHospId AND a.TEAM_DEL_STATE = '0' ORDER BY a.TEAM_SORT  ";
        List<AppTeamMangeEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppTeamMangeEntity.class);
        if(ls!=null){
            return ls;
        }
        return null;
    }

    @Override
    public List<AddressHospEntity> findTeamManageTs(String teamHospId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamHospId",teamHospId);
        String sql = "SELECT a.ID id,a.TEAM_NAME name, '0' state,'3' level FROM APP_TEAM a" +
                " WHERE a.TEAM_HOSP_ID = :teamHospId AND a.TEAM_DEL_STATE = '0'  ORDER BY a.TEAM_SORT ";
        List<AddressHospEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AddressHospEntity.class);
        if(ls!=null){
            return ls;
        }
        return null;
    }

    @Override
    public List<AppDrTeamEntity> findOneByTeam(String drId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",drId);
//        String sql = " SELECT a.ID id, a.TEAM_NAME teamName," +
//                "a.TEAM_CODE teamCode," +
//                "a.TEAM_DR_ID drId," +
//                "a.TEAM_DR_NAME drName," +
//                "a.TEAM_REMARKS remarks," +
//                "a.TEAM_TEL tel,a.TEAM_TYPE type," +
//                "'' typeName," +
//                "b.MEM_WORK_TYPE workType," +
//                "'' workTypeName," +
//                "b.MEM_STATE memState," +
//                "DATE_FORMAT(a.TEAM_CREATE_TIME, '%Y-%c-%d %H:%i:%s') time " +
//                "FROM APP_TEAM_MEMBER b INNER JOIN APP_TEAM a ON a.ID = b.MEM_TEAMID  WHERE 1=1 " +
//                "AND b.MEM_DR_ID =:drId";
        String sql = " SELECT a.ID id, a.TEAM_NAME teamName," +
                "a.TEAM_CODE teamCode," +
                "a.TEAM_DR_ID drId," +
                "a.TEAM_DR_NAME drName," +
                "a.TEAM_REMARKS remarks," +
                "a.TEAM_TEL tel,a.TEAM_TYPE type," +
                "'' typeName," +
                "b.MEM_WORK_TYPE workType," +
                "'' workTypeName," +
                "b.MEM_STATE memState," +
                "DATE_FORMAT(a.TEAM_CREATE_TIME, '%Y-%c-%d %H:%i:%s') time, " +
                " a.TEAM_EASE_GROUP_ID teamEaseGroupId,"+
                " a.TEAM_EASE_ROOM_ID teamEaseRoomId, "+
                " a.TEAM_EASE_GROUP_NAME teamEaseGroupName,"+
                " a.TEAM_EASE_ROOM_NAME teamEaseRoomName "+
                "FROM APP_TEAM a INNER JOIN APP_TEAM_MEMBER b ON a.ID = b.MEM_TEAMID  WHERE 1=1 AND a.TEAM_DR_ID = b.MEM_DR_ID AND a.TEAM_DEL_STATE = '0' " +
                " AND a.ID IN (SELECT c.MEM_TEAMID FROM APP_TEAM_MEMBER c WHERE c.MEM_DR_ID =:drId) ";

        List<AppDrTeamEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map, AppDrTeamEntity.class);
        return ls;
    }

    /**
     * 根据条件查询团队信息
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppTeamEntity> findTeamm(AppTeamVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamHospId",qvo.getTeamHospId());
        String sql = "SELECT a.ID teamId,a.TEAM_NAME teamName,a.TEAM_HOSP_ID hospId, '' num,a.TEAM_DR_ID drId,a.TEAM_DR_NAME drName,'' drList," +
                "'' toplimit " +
                "FROM APP_TEAM a" +
                " WHERE a.TEAM_HOSP_ID = :teamHospId AND a.TEAM_DEL_STATE = '0' ";
        if(StringUtils.isNotBlank(qvo.getTeamName())){
            map.put("name","%"+qvo.getTeamName()+"%");
            sql += " AND (a.TEAM_NAME LIKE :name OR a.ID IN (SELECT b.MEM_TEAMID FROM APP_TEAM_MEMBER b WHERE b.MEM_DR_NAME LIKE :name))";
        }
        List<AppTeamEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppTeamEntity.class);
        if(ls!=null){
            return ls;
        }
        return null;
    }

    /**
     * 根据条件查询团队信息
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppTeamMotoeEntity> findTeamMotoe(AppTeamVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamHospId",qvo.getTeamHospId());
        String sql = "SELECT\n" +
                "\ta.ID teamId,\n" +
                "\ta.TEAM_NAME teamName,\n" +
                "\ttSUBSTRING(a.TEAM_HOSP_ID,4) orgId,\n" +
                "\ta.TEAM_TEL teamTel,\n" +
                "\t'3' teamServiceType,\n" +
                "\t'02,03' teamDiseaseType,\n" +
                "\t'' teamServiceArea,\n" +
                "\t'' teamServiceAreaName,\n" +
                "\t'' teamMembers,\n" +
                "'"+qvo.getTeamSrc()+"' src \n" +
                "FROM\n" +
                "\tAPP_TEAM a " +
                " WHERE a.TEAM_HOSP_ID = :teamHospId AND a.TEAM_DEL_STATE = '0' ";
        List<AppTeamMotoeEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppTeamMotoeEntity.class);
        if(ls!=null){
            return ls;
        }
        return null;
    }

    @Override
    public AppTeam findHospDrId(String hospId, String drId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tAPP_TEAM AS a INNER JOIN app_team_member AS b ON a.ID = b.MEM_TEAMID\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "AND a.TEAM_DEL_STATE = '0' ";

        //家庭医生
        if(StringUtils.isNotBlank(drId)){
            map.put("appTeamDrId",drId);
            sql += " AND b.MEM_DR_ID = :appTeamDrId ";
        }
        //机构名称
        if(StringUtils.isNotBlank(hospId)){
            map.put("appTeamHospId", hospId);
            sql += " AND a.TEAM_HOSP_ID = :appTeamHospId";
        }
        sql += " ORDER BY a.TEAM_CREATE_TIME ASC";
        List<AppTeam> ls = this.sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
        if(ls != null && ls.size()>0){
            return  ls.get(0);
        }
        return null;
    }

    /**
     * 根据teamName查询团队信息
     * @param teamName
     * @return
     */
    @Override
    public AppTeam findTeamByTeamName(String teamName) throws Exception{
        Map map = new HashMap();
        String sql = "select * from app_team a where 1=1 AND a.TEAM_DEL_STATE = '0' ";
        if(StringUtils.isNotEmpty(teamName)){
            map.put("teamName",teamName);
            sql += " and a.TEAM_NAME = :teamName";
        }
        List<AppTeam> list = this.sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AppTeam> findtTeamAll() throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_TEAM  as a WHERE 1=1 AND a.TEAM_DEL_STATE = '0' ";
        List<AppTeam> ls = this.sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
        return ls;
    }

    /**
     * 查询变更团队列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<YsChangeTeamEntity> findChangeTeam(YsChangeCountQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        map.put("statet", SignFormType.YQY.getValue());
       /* String sql = "SELECT " +
                "a.ID id," +
                "a.TEAM_NAME teamName," +
                "b.MEM_STATE state," +
                "(SELECT COUNT(1) FROM APP_SIGN_FORM WHERE SIGN_TEAM_ID =a.ID AND SIGN_STATE =:statet) signCount " +
                "FROM APP_TEAM a INNER JOIN APP_TEAM_MEMBER b ON a.ID = b.MEM_TEAMID " +
                "WHERE b.MEM_DR_ID = :drId";
        sql += " ORDER BY b.MEM_STATE";*/
       String sql = "SELECT\n" +
               "\ta.ID id,\n" +
               "\ta.TEAM_NAME teamName,\n" +
               "\tb.MEM_STATE state,\n" +
               "\t(\n" +
               "\t\tSELECT\n" +
               "\t\t\tCOUNT(1)\n" +
               "\t\tFROM\n" +
               "\t\t\tAPP_SIGN_FORM\n" +
               "\t\tWHERE\n" +
               "\t\t\tSIGN_TEAM_ID = a.ID\n" +
               "\t\tAND SIGN_STATE =:statet\n" +
               "\t) signCount\n" +
               "FROM\n" +
               "\tAPP_TEAM a\n" +
               "INNER JOIN APP_TEAM_MEMBER b ON a.ID = b.MEM_TEAMID WHERE b.MEM_DR_ID = :drId AND a.TEAM_DEL_STATE = '0' \n" +
               "ORDER BY\n" +
               "\tb.MEM_STATE";
        List<YsChangeTeamEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,YsChangeTeamEntity.class);
        return ls;
    }

    @Override
    public List<AppTeamManagEntity> findByManageCont() throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tt.ID teamId,\n" +
                "\tt.TEAM_NAME teamName,\n" +
                "\td.ID hospId,\n" +
                "\td.HOSP_AREA_CODE areaCode\n" +
                "FROM\n" +
                "\tAPP_TEAM T\n" +
                "INNER JOIN APP_HOSP_DEPT D ON t.TEAM_HOSP_ID = d.ID\n" +
                "WHERE d.HOSP_AREA_CODE IS NOT NULL  ";
        List<AppTeamManagEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);
        return ls;
    }

    @Override
    public List<AppTeamExerciseEntity> findByExerciseCount() throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tu.ID drId,\n" +
                "\tt.MEM_TEAMID teamId,\n" +
                "\td.ID hospId,\n" +
                "\td.HOSP_AREA_CODE areaCode\n" +
                "FROM\n" +
                "\tAPP_DR_USER U\t\n" +
                "INNER JOIN APP_HOSP_DEPT D ON u.DR_HOSP_ID = d.ID\n" +
                "INNER JOIN APP_TEAM_MEMBER T ON t.MEM_DR_ID = u.ID\n" +
                "WHERE\n" +
                "\td.HOSP_AREA_CODE IS NOT NULL\n" +
                "AND t.MEM_TEAMID IS NOT NULL";
        List<AppTeamExerciseEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamExerciseEntity.class);
        return ls;
    }

    /**
     * 查询一体机团队
     * @param qvo
     * @return
     */
    @Override
    public List<AppTeamAioEntity> findTeammList(AppTeamVo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamHospId",qvo.getTeamHospId());
        String sql = "SELECT a.ID teamId,a.TEAM_NAME teamName,a.TEAM_HOSP_ID hospId, '' num,a.TEAM_DR_ID drId,a.TEAM_DR_NAME drName,'' drList " +
                "FROM APP_TEAM a" +
                " WHERE a.TEAM_HOSP_ID = :teamHospId AND a.TEAM_DEL_STATE = '0' ";
        if(StringUtils.isNotBlank(qvo.getTeamName())){
            map.put("name","%"+qvo.getTeamName()+"%");
            sql += " AND (a.TEAM_NAME LIKE :name OR a.ID IN (SELECT b.MEM_TEAMID FROM APP_TEAM_MEMBER b WHERE b.MEM_DR_NAME LIKE :name))";
        }
        List<AppTeamAioEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppTeamAioEntity.class);
        if(ls!=null){
            return ls;
        }
        return null;
    }

    /**
     * 根据团队主键查询团队
     * @param teamid
     * @return
     */
    @Override
    public AppTeam findteamById(String teamid) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_TEAM t  WHERE 1=1 AND t.TEAM_DEL_STATE = '0' \n";
        if(StringUtils.isNotBlank(teamid)){
            map.put("teamid",teamid);
            sql += " AND t.ID = :teamid";
        }
        List<AppTeam> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
        if(ls != null && ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    @Override
    public List<AppTeamEntity> findByAreaCode(String areaCode) throws Exception{
        String sql = "select id teamId,team_name teamName from app_team where team_hosp_id in(\n" +
                "select id from app_hosp_dept where hosp_upcityarea_id = :areaCode) AND TEAM_DEL_STATE = '0' ";
        HashMap map = new HashMap();
        map.put("areaCode",areaCode);
        List<AppTeamEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamEntity.class);
        if(ls != null && ls.size() > 0){
            return ls;
        }
        return null;
    }

    @Override
    public List<AppTeamEntity> findByHospAreaCode(String areaCode) throws Exception{
        String sql = "select id teamId,team_name teamName from app_team where team_hosp_id in(\n" +
                "select id from app_hosp_dept where hosp_cityarea_id = :areaCode) AND TEAM_DEL_STATE = '0' ";
        HashMap map = new HashMap();
        map.put("areaCode",areaCode);
        List<AppTeamEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamEntity.class);
        if(ls != null && ls.size() > 0){
            return ls;
        }
        return null;
    }

    /**
     * 根据区域查询团队数据
     * @param areaCode
     * @return
     */
    @Override
    public List<AppTeamManagEntity> findManageContByAreaCode(String[] areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("areaCode",areaCode);
        String sql = "SELECT\n" +
                "\tt.ID teamId,\n" +
                "\tt.TEAM_NAME teamName,\n" +
                "\td.ID hospId,\n" +
                "\td.HOSP_AREA_CODE areaCode,\n" +
                "\td.HOSP_LEVEL_TYPE hospLevelType\n" +
                "FROM\n" +
                "\tAPP_TEAM T\n" +
                "INNER JOIN APP_HOSP_DEPT D ON t.TEAM_HOSP_ID = d.ID\n" +
                "WHERE d.HOSP_AREA_CODE IS NOT NULL " ;
        sql += " AND LEFT(d.HOSP_AREA_CODE,4) IN (:areaCode) ";
        List<AppTeamManagEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);
        return ls;
    }

    @Override
    public List<Map<String,Object>> findTeamCountByAreaId(String areaCode) throws Exception{
        List<Map<String,Object>> list = new ArrayList<>();
        if(StringUtils.isNotBlank(areaCode)) {
            int ll = areaCode.length();
            if (areaCode.length() == 2) {//省 区划
                areaCode = areaCode + "0000000000";
            } else if (areaCode.length() == 4) {//市 区划
                areaCode = areaCode + "00000000";
            } else if (areaCode.length() == 6) {//区 区划
                areaCode = areaCode + "000000";
            } else if (areaCode.length() == 9) {//街道 区划
                areaCode = areaCode + "000";
            }
            int total = 0;
            int drTotal = 0;
            if (AreaUtils.getAreaCode(areaCode).length() == 6 || AreaUtils.getAreaCode(areaCode).length() == 5) {
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getAppHospDeptDao().findUpHospListRead(areaCode);
                if(hospDepts != null && hospDepts.size()>0){
                    Map<String,Object> mapp = new HashMap<>();
                    for(AppHospDept hosp:hospDepts){
                        if(hosp.getHospState().equals("1")){
                            Map<String, Object> map = getFindTeamByArea(null, hosp);
                            total +=Integer.parseInt(map.get("teamCount").toString());
                            drTotal += Integer.parseInt(map.get("drCount").toString());
                            list.add(map);
                        }
                    }
                    mapp.put("name","合计");
                    mapp.put("code","");
                    mapp.put("teamCount",total);
                    mapp.put("drCount",drTotal);
                    list.add(mapp);
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(areaCode);
                if(lsCdAddress != null && lsCdAddress.size()>0) {
                    Map<String,Object> mapp = new HashMap<>();
                    for (CdAddress address : lsCdAddress) {
                        String code = address.getCtcode().substring(0, 4);
                        if (AddressType.FZS.getValue().equals(code) || AddressType.SMS.getValue().equals(code) || AddressType.ZZS.getValue().equals(code) ||
                                AddressType.QZS.getValue().equals(code) || AddressType.PTS.getValue().equals(code) || AddressType.NPS.getValue().equals(code)) {
                            Map<String,Object> map = getFindTeamByArea(address,null);
                            total +=Integer.parseInt(map.get("teamCount").toString());
                            drTotal += Integer.parseInt(map.get("drCount").toString());
                            list.add(map);
                        }
                    }

                    mapp.put("name","合计");
                    mapp.put("code","");
                    mapp.put("teamCount",total);
                    mapp.put("drCount",drTotal);
                    list.add(mapp);
                }
            }
        }
        return list;
    }

    @Override
    public  List<Map<String,Object>> findTeamByHospId(String hospId) throws Exception{
        List<Map<String,Object>> listMap = new ArrayList<>();
        List<AppTeam> list = sysDao.getAppTeamDao().findTeamByHospIdAndState(hospId);
        if(list != null && list.size()>0){
            for(AppTeam team:list){
                Map<String,Object> map = this.getFindTeamByHospId(team);
                listMap.add(map);
            }
        }
        Collections.sort(listMap, ComparatorUtils.getComparator());
        return listMap;
    }

    public Map<String,Object> getFindTeamByArea(CdAddress address,AppHospDept hosp) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("STATE","1");
        map.put("SIGN_STATE",new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()});
        String sql = "SELECT\n" +
                "\ta.ID teamId," +
                "(select COUNT(1) from app_sign_form where sign_state IN (:SIGN_STATE) AND sign_team_id = a.id) cott \n" +
                "FROM\n" +
                "\tapp_team a\n" +
                "INNER JOIN app_hosp_dept b ON a.TEAM_HOSP_ID = b.ID\n" +
                "WHERE 1=1 AND a.TEAM_DEL_STATE = '0' \n" +
                " AND a.TEAM_STATE = :STATE AND b.HOSP_STATE =:STATE  ";
        String drSql = "SELECT COUNT(1) FROM APP_DR_USER a INNER JOIN app_hosp_dept b ON a.DR_HOSP_ID = b.ID WHERE 1=1 AND DR_STATE =:STATE AND b.HOSP_STATE =:STATE ";
        if(address != null){
            if("2".equals(address.getLevel())){//各市数据统计
                map.put("areaCode",address.getCtcode().substring(0,4)+"%");
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"2"));
            }else if("3".equals(address.getLevel())){//各区数据
                map.put("areaCode",address.getCtcode().substring(0,6)+"%");
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"3"));
                //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
                if("350698000000".equals(address.getCtcode())){//台商投资区
                    map.put("areaCode","350681102%");
                }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                    map.put("areaCode","350681501%");
                }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                    map.put("areaCode","350622450%");
                }else if("350681000000".equals(address.getCtcode())){
                    map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                    sql += " AND b.HOSP_AREA_CODE NOT IN (:notAreaCode) ";
                    drSql += " AND b.HOSP_AREA_CODE NOT IN (:notAreaCode) ";
                }else if("350622000000".equals(address.getCtcode())){
                    map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                    sql += " AND b.HOSP_AREA_CODE NOT IN (:notAreaCode) ";
                    drSql += " AND b.HOSP_AREA_CODE NOT IN (:notAreaCode) ";
                }

            }else if("4".equals(address.getLevel())){//各街道数据
                map.put("areaCode",address.getCtcode().substring(0,9)+"%");
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"4"));
            }else if("5".equals(address.getLevel())){//各居委会数据
                map.put("areaCode",address.getCtcode());
                returnMap.put("code",address.getCtcode());
            }
            returnMap.put("name",address.getAreaSname());
            sql += " AND b.HOSP_AREA_CODE LIKE :areaCode";
            drSql += " AND b.HOSP_AREA_CODE LIKE :areaCode ";
        }
        if(hosp != null){
            map.put("hospId",hosp.getId());
            sql += " AND b.ID = :hospId ";
            drSql += " AND b.ID = :hospId ";
            returnMap.put("name",hosp.getHospName());
            returnMap.put("hospId",hosp.getId());
        }
        String sqlcc = "SELECT count(1) from ( "+sql+" ) cc WHERE cc.cott >0 ";
        int count = sysDao.getServiceDo().findCount(sqlcc,map);
        int drCount = sysDao.getServiceDo().findCount(drSql,map);
        returnMap.put("teamCount",count);
        returnMap.put("drCount",drCount);
        return returnMap;
    }

    @Override
    public List<AppTeam> findTeamByHospIdAndState(String hospId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("hospId",hospId);
        map.put("state","1");
        String sql = "SELECT * FROM app_team WHERE TEAM_HOSP_ID = :hospId and TEAM_STATE = :state AND TEAM_DEL_STATE = '0' ";
        List<AppTeam> list = sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
        return list;
    }

    public Map<String,Object> getFindTeamByHospId(AppTeam team) throws Exception{
        Map<String,Object> returnMap =new HashMap<>();
        returnMap.put("teamName",team.getTeamName());
        returnMap.put("teamId",team.getId());
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",team.getId());
        map.put("SIGN_STATE",new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()});
        String sql = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tapp_sign_form\n" +
                "WHERE\n" +
                "\tSIGN_STATE IN (:SIGN_STATE)\n" +
                "AND SIGN_TEAM_ID = :teamId";
        int count = sysDao.getServiceDo().findCount(sql,map);
        returnMap.put("count",count);
        return returnMap;
    }

    @Override
    public List<AppTeamManagEntity> findManageNCD(String areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("areaCode",AreaUtils.getAreaCode(areaCode,"3"));
        String sql = "SELECT\n" +
                "\tt.ID teamId,\n" +
                "\tt.TEAM_NAME teamName,\n" +
                "\td.ID hospId,\n" +
                "\td.HOSP_AREA_CODE areaCode\n" +
                "FROM\n" +
                "\tAPP_TEAM T\n" +
                "INNER JOIN APP_HOSP_DEPT D ON t.TEAM_HOSP_ID = d.ID\n" +
                "WHERE d.HOSP_AREA_CODE IS NOT NULL  ";
        if(StringUtils.isNotBlank(areaCode)){
            sql += " AND LEFT(d.HOSP_AREA_CODE,6) = :areaCode ";
            map.put("areaCode",AreaUtils.getAreaCode(areaCode,"3"));
        }
        List<AppTeamManagEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);
        return ls;
    }

    @Override
    public List<AppArchivintTeamEntity> findTeamXxByHospId(ResidentVo qvo) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("hospId",qvo.getHospId());
        map.put("state","1");
        map.put("SIGN_STATE",new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()});
        String ss = "";
        String teamSs = "";
        if(org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("startTime",qvo.getYearStart() + " 00:00:00");
            ss += "  AND b.SIGN_FROM_DATE >=:startTime ";
            teamSs += " AND a.TEAM_CREATE_TIME >=:startTime ";
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("endTime",qvo.getYearEnd() + " 23:59:59");
            ss += " AND b.SIGN_FROM_DATE <=:endTime ";
            teamSs += " AND a.TEAM_CREATE_TIME <=:endTime ";
        }
        String sql = "SELECT\n" +
                "\ta.ID teamId,\n" +
                "\ta.TEAM_NAME teamName,\n" +
                "\ta.TEAM_DR_ID firstDrId,\n" +
                "\ta.TEAM_DR_NAME firstDrName,\n" +
                "\t(SELECT GROUP_CONCAT(MEM_DR_ID) FROM app_team_member WHERE MEM_TEAMID = a.ID) memberName,\n" +
                "\t(SELECT count(MEM_DR_NAME) FROM app_team_member WHERE MEM_TEAMID = a.ID) memberCount,\n" +
                "\t(SELECT count(1) FROM app_sign_form b WHERE b.SIGN_TEAM_ID = a.ID AND b.SIGN_STATE IN (:SIGN_STATE) "+ss+") count\n" +
                "FROM\n" +
                "\tapp_team a\n" +
                "WHERE\n" +
                "\ta.TEAM_HOSP_ID = :hospId\n" +
                "AND a.TEAM_STATE = :state AND a.TEAM_DEL_STATE = '0' "+teamSs+" ORDER BY count DESC ";
        String sqlll = "select * from ("+sql+") ccc where count>0 ";
        List<AppArchivintTeamEntity> list = sysDao.getServiceDo().findSqlMapRVo(sqlll,map,AppArchivintTeamEntity.class,qvo);
        if(list != null && list.size()>0){
            for(AppArchivintTeamEntity ls:list){
                map.put("teamId",ls.getTeamId());
                List<AppLabelManage> listM = sysDao.getAppLabelManageDao().findByType("3");
                String sqlMap = "SELECT count(1) FROM (\n" +
                        "SELECT\n" +
                        "\tb.*\n" +
                        "FROM\n" +
                        "\tapp_sign_form b\n" +
                        "INNER JOIN app_label_group c ON b.ID = c.LABEL_SIGN_ID\n" +
                        "WHERE\n" +
                        "\tc.LABEL_TYPE = '3'\n" +
                        "AND c.LABEL_VALUE = :labelValue\n" +
                        "AND b.SIGN_TEAM_ID =:teamId AND b.SIGN_STATE IN (:SIGN_STATE) "+ss+"\n" +
                        "GROUP BY b.ID) cc ";
                if(listM != null && listM.size()>0){
                    for(AppLabelManage manage:listM){
                        map.put("labelValue",manage.getLabelValue());

                        if(ResidentMangeType.PTRQ.getValue().equals(manage.getLabelValue())){
                            int count = sysDao.getServiceDo().findCount(sqlMap,map);
                            ls.setManagePlainCount(String.valueOf(count));
                        }else if(ResidentMangeType.ETLZLS.getValue().equals(manage.getLabelValue())){
                            int count = sysDao.getServiceDo().findCount(sqlMap,map);
                            ls.setManageChildCount(String.valueOf(count));
                        }else if(ResidentMangeType.YCF.getValue().equals(manage.getLabelValue())){
                            int count = sysDao.getServiceDo().findCount(sqlMap,map);
                            ls.setManageMaternalCount(String.valueOf(count));
                        }else if(ResidentMangeType.LNR.getValue().equals(manage.getLabelValue())){
                            int count = sysDao.getServiceDo().findCount(sqlMap,map);
                            ls.setManageOldPeopleCount(String.valueOf(count));
                        }else if(ResidentMangeType.GXY.getValue().equals(manage.getLabelValue())){
                            int count = sysDao.getServiceDo().findCount(sqlMap,map);
                            ls.setManageBloodCount(String.valueOf(count));
                        }else if(ResidentMangeType.TNB.getValue().equals(manage.getLabelValue())){
                            int count = sysDao.getServiceDo().findCount(sqlMap,map);
                            ls.setManageDiabetesCount(String.valueOf(count));
                        }else if(ResidentMangeType.YZJSZY.getValue().equals(manage.getLabelValue())){
                            int count = sysDao.getServiceDo().findCount(sqlMap,map);
                            ls.setManagePsychosisCount(String.valueOf(count));
                        }else if(ResidentMangeType.JHB.getValue().equals(manage.getLabelValue())){
                            int count = sysDao.getServiceDo().findCount(sqlMap,map);
                            ls.setManagePhthisisCount(String.valueOf(count));
                        }
                    }
                }
            }


        }
        /*if(list != null && list.size()>0){
            for(AppArchivintTeamEntity ls:list){
                //通过团队id 和时间查询服务人群数
                ResidentVo qqvo = new ResidentVo();
                qqvo.setTeamId(ls.getTeamId());
                if(StringUtils.isNotBlank(qvo.getYearStart())){
                    qqvo.setYearStart(qvo.getYearStart().substring(0,7));
                }else{
                    Calendar nowC = Calendar.getInstance();
                    nowC.add(Calendar.YEAR,-1);
                    qqvo.setYearStart(ExtendDate.getYM(nowC));
                }
                if(StringUtils.isNotBlank(qvo.getYearEnd())){
                    qqvo.setYearEnd(qvo.getYearEnd().substring(0,7));
                }else{
                    qqvo.setYearEnd(ExtendDate.getYM(Calendar.getInstance()));
                }
                String mapPers = sysDao.getAppResidentAnalysisDao().findPersGroupCountFocusGroups(qqvo);//服务分布
                List<ManageCountEntity> lss = JsonUtil.fromJson(mapPers,new TypeToken<List<ManageCountEntity>>() {}.getType());
                if(lss != null && lss.size()>0){
                    for(ManageCountEntity countEntity:lss){
                        if("managePlainCount".equals(countEntity.getTitle())){
                            ls.setManagePlainCount(countEntity.getValue());
                        }else if("manageChildCount".equals(countEntity.getTitle())){
                            ls.setManageChildCount(countEntity.getValue());
                        }else if("manageMaternalCount".equals(countEntity.getTitle())){
                            ls.setManageMaternalCount(countEntity.getValue());
                        }else if("manageOldPeopleCount".equals(countEntity.getTitle())){
                            ls.setManageOldPeopleCount(countEntity.getValue());
                        }else if("manageBloodCount".equals(countEntity.getTitle())){
                            ls.setManageBloodCount(countEntity.getValue());
                        }else if("manageDiabetesCount".equals(countEntity.getTitle())){
                            ls.setManageDiabetesCount(countEntity.getValue());
                        }else if("managePsychosisCount".equals(countEntity.getTitle())){
                            ls.setManagePsychosisCount(countEntity.getValue());
                        }else if("managePhthisisCount".equals(countEntity.getTitle())){
                            ls.setManagePhthisisCount(countEntity.getValue());
                        }
                    }
                }
            }

        }*/
        return list;
    }

    @Override
    public Map<String, Object> findTeamCountByHospId(String hospId) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        AppHospDept hosp = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,hospId);
        Map<String, Object> map = getFindTeamByArea(null, hosp);
        returnMap.put("teamCount",map.get("teamCount").toString());
        returnMap.put("drCount",map.get("drCount").toString());
        return returnMap;
    }
//团队总数、乡镇医院团队数、社区医院团队数、团队成员数、家庭签约数
    @Override
    public List<Map<String, Object>> findTeamCountByAreaIdTwo(ResidentVo qvo) throws Exception{
        List<Map<String,Object>> list = new ArrayList<>();
        int totalTeamCount = 0;//总团队数
        int totalTeamMcount = 0;//签约团队成员总数
        int xzTeamCount = 0;//乡镇医院团队数
        int sqTeamCount = 0;//社区医院团队数
        int xzTeamMCount = 0;//乡镇医院团队成员数
        int sqTeamMCount = 0;//社区医院团队成员数
        int totalDrCount = 0;//参与签约的家庭医生数
        int xzDrCount = 0;//乡镇参与签约的家庭医生数
        int sqDrCount = 0;//社区参与签约的家庭医生数
        String areaCode = "";
        if(StringUtils.isNotBlank(qvo.getAreaId())) {
            int ll = qvo.getAreaId().length();
            areaCode = qvo.getAreaId();
            if (areaCode.length() == 2) {//省 区划
                areaCode = areaCode + "0000000000";
            } else if (areaCode.length() == 4) {//市 区划
                areaCode = areaCode + "00000000";
            } else if (areaCode.length() == 6) {//区 区划
                areaCode = areaCode + "000000";
            } else if (areaCode.length() == 9) {//街道 区划
                areaCode = areaCode + "000";
            }
            if (AreaUtils.getAreaCode(areaCode).length() == 6 || AreaUtils.getAreaCode(areaCode).length() == 5) {
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getAppHospDeptDao().findUpHospListRead(areaCode);
                if(hospDepts != null && hospDepts.size()>0){
                    Map<String,Object> mapp = new HashMap<>();
                    for(AppHospDept hosp:hospDepts){
                        if(hosp.getHospState().equals("1")){
                            Map<String, Object> map = getFindTeamByAreaTwo(null, hosp,qvo.getYearStart(),qvo.getYearEnd());
                            totalTeamCount += Integer.parseInt(map.get("teamCount").toString());
                            xzTeamCount += Integer.parseInt(map.get("xzTeamCount").toString());
                            sqTeamCount += Integer.parseInt(map.get("sqTeamCount").toString());
                            totalTeamMcount += Integer.parseInt(map.get("teamMCount").toString());
                            xzTeamMCount += Integer.parseInt(map.get("xzTeamMCount").toString());
                            sqTeamMCount += Integer.parseInt(map.get("sqTeamMCount").toString());
                            totalDrCount += Integer.parseInt(map.get("drCount").toString());
                            xzDrCount += Integer.parseInt(map.get("xzDrCount").toString());
                            sqDrCount += Integer.parseInt(map.get("sqDrCount").toString());
                            list.add(map);

                        }
                    }
                    mapp.put("name","合计");
                    mapp.put("code","");
                    mapp.put("teamCount",totalTeamCount);
                    mapp.put("xzTeamCount",xzTeamCount);
                    mapp.put("sqTeamCount",sqTeamCount);

                    mapp.put("teamMcount",totalTeamMcount);
                    mapp.put("xzTeamMCount",xzTeamMCount);
                    mapp.put("sqTeamMCount",sqTeamMCount);

                    mapp.put("drCount",totalDrCount);
                    mapp.put("xzDrCount",xzDrCount);
                    mapp.put("sqDrCount",sqDrCount);
                    list.add(mapp);
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(areaCode);
                if(lsCdAddress != null && lsCdAddress.size()>0) {
                    Map<String,Object> mapp = new HashMap<>();
                    for (CdAddress address : lsCdAddress) {
                        String code = address.getCtcode().substring(0, 4);
                        if (AddressType.FZS.getValue().equals(code) || AddressType.SMS.getValue().equals(code) || AddressType.ZZS.getValue().equals(code) ||
                                AddressType.QZS.getValue().equals(code) || AddressType.PTS.getValue().equals(code) || AddressType.NPS.getValue().equals(code)) {
                            Map<String,Object> map = getFindTeamByAreaTwo(address,null,qvo.getYearStart(),qvo.getYearEnd());
                            totalTeamCount += Integer.parseInt(map.get("teamCount").toString());
                            xzTeamCount += Integer.parseInt(map.get("xzTeamCount").toString());
                            sqTeamCount += Integer.parseInt(map.get("sqTeamCount").toString());
                            totalTeamMcount += Integer.parseInt(map.get("teamMCount").toString());
                            xzTeamMCount += Integer.parseInt(map.get("xzTeamMCount").toString());
                            sqTeamMCount += Integer.parseInt(map.get("sqTeamMCount").toString());
                            totalDrCount += Integer.parseInt(map.get("drCount").toString());
                            xzDrCount += Integer.parseInt(map.get("xzDrCount").toString());
                            sqDrCount += Integer.parseInt(map.get("sqDrCount").toString());
                            list.add(map);
                        }
                    }

                    mapp.put("name","合计");
                    mapp.put("code","");
                    mapp.put("teamCount",totalTeamCount);
                    mapp.put("xzTeamCount",xzTeamCount);
                    mapp.put("sqTeamCount",sqTeamCount);

                    mapp.put("teamMcount",totalTeamMcount);
                    mapp.put("xzTeamMCount",xzTeamMCount);
                    mapp.put("sqTeamMCount",sqTeamMCount);

                    mapp.put("drCount",totalDrCount);
                    mapp.put("xzDrCount",xzDrCount);
                    mapp.put("sqDrCount",sqDrCount);
                    list.add(mapp);
                }
            }
        }
        return list;
    }

    public Map<String, Object> getFindTeamByAreaTwo(CdAddress address,AppHospDept hosp,String startTime,String endTime) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        int totalTeamCount = 0;//总签约团队数
        int totalTeamMCount = 0;//签约团队成员总数
        int xzTeamCount = 0;//乡镇医院签约团队数
        int sqTeamCount = 0;//社区医院签约团队数
        int xzTeamMCount = 0;//乡镇医院签约团队成员数
        int sqTeamMCount = 0;//社区医院签约团队成员数
        int totalDrCount = 0;//参与签约的家庭医生数
        int xzDrCount = 0;//乡镇参与签约的家庭医生数
        int sqDrCount = 0;//社区参与签约的家庭医生数
        String nowYmd = ExtendDate.getYYYYMMD(Calendar.getInstance());
        int nowDay = Integer.parseInt(nowYmd);
        int endDay = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("STATE","1");
        map.put("xzValue", HospType.XZWSY.getValue());
        map.put("sqValue",HospType.SQWSFWZX.getValue());
        map.put("SIGN_STATE",new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()});
        String sqlXz = "SELECT count(1) from app_manage_team where 1=1 AND MANAGE_SIGN_STATE = :STATE " +
                "AND MANAGE_HOSP_LEVE =:xzValue";
        String sqlSq = "SELECT count(1) from app_manage_team where 1=1 AND MANAGE_SIGN_STATE = :STATE " +
                "AND MANAGE_HOSP_LEVE =:sqValue";
        String sqlXzCount = "SELECT " +
                "SUM(MANAGE_MEMBER_COUNT) manageMemBerCount,\n" +
                "SUM(MANAGE_SIGN_MEMBER_COUNT) manageSignMemberCount\n" +
                "FROM app_manage_team\n" +
                "WHERE 1=1\n" +
                "AND MANAGE_SIGN_STATE = :STATE " +
                "AND MANAGE_HOSP_LEVE =:xzValue";
        String sqlSqCount =  "SELECT " +
                "SUM(MANAGE_MEMBER_COUNT) manageMemBerCount,\n" +
                "SUM(MANAGE_SIGN_MEMBER_COUNT) manageSignMemberCount\n" +
                "FROM app_manage_team\n" +
                "WHERE 1=1\n" +
                "AND MANAGE_SIGN_STATE = :STATE " +
                "AND MANAGE_HOSP_LEVE =:sqValue ";

        //当天乡镇
        String nowSqlXz = "SELECT\n" +
                "\ta.ID teamId,\n" +
                "\t(SELECT count(1) FROM app_team_member c WHERE c.MEM_TEAMID = a.ID ) memCount,\n" +
                "\t(SELECT count(1) FROM app_sign_form WHERE SIGN_STATE IN (:SIGN_STATE) AND SIGN_TEAM_ID = a.ID) signCount,\n" +
                "\tNULL drCount\n" +
                "FROM\n" +
                "\tapp_team a\n" +
                "INNER JOIN app_hosp_dept b ON a.TEAM_HOSP_ID = b.ID WHERE 1=1 AND b.HOSP_STATE=:STATE AND a.TEAM_STATE=:STATE AND a.TEAM_DEL_STATE = '0'  " +
                "AND HOSP_LEVEL_TYPE =:xzValue ";

        String nowSqlSq = "SELECT\n" +
                "\ta.ID teamId,\n" +
                "\t(SELECT count(1) FROM app_team_member c WHERE c.MEM_TEAMID = a.ID ) memCount,\n" +
                "\t(SELECT count(1) FROM app_sign_form WHERE SIGN_STATE IN (:SIGN_STATE) AND SIGN_TEAM_ID = a.ID) signCount,\n" +
                "\tNULL drCount\n" +
                "FROM\n" +
                "\tapp_team a\n" +
                "INNER JOIN app_hosp_dept b ON a.TEAM_HOSP_ID = b.ID WHERE 1=1 AND b.HOSP_STATE=:STATE AND a.TEAM_STATE=:STATE " +
                "AND HOSP_LEVEL_TYPE =:sqValue  AND a.TEAM_DEL_STATE = '0' ";


        if(StringUtils.isNotBlank(endTime)){//判断是否需查询当天的签约团队数
            endDay = Integer.parseInt(endTime.replaceAll("-",""));
        }

        if(address != null){
            if("2".equals(address.getLevel())){//各市数据统计
                map.put("areaCode",address.getCtcode().substring(0,4)+"%");
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"2"));
            }else if("3".equals(address.getLevel())){//各区数据
                map.put("areaCode",address.getCtcode().substring(0,6)+"%");
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"3"));
                //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
                if("350698000000".equals(address.getCtcode())){//台商投资区
                    map.put("areaCode","350681102%");
                }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                    map.put("areaCode","350681501%");
                }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                    map.put("areaCode","350622450%");
                }else if("350681000000".equals(address.getCtcode())){
                    map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                    sqlXz += " AND MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlSq += " AND MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlXzCount += " AND MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlSqCount += " AND MANAGE_AREA_CODE NOT IN (:notAreaCode)  ";
                    nowSqlSq += " AND b.HOSP_AREA_CODE NOT IN (:notAreaCode)  ";
                    nowSqlXz += " AND b.HOSP_AREA_CODE NOT IN (:notAreaCode) ";
                }else if("350622000000".equals(address.getCtcode())){
                    map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                    sqlXz += " AND MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlSq += " AND MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlXzCount += " AND MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlSqCount += " AND MANAGE_AREA_CODE NOT IN (:notAreaCode)  ";
                    nowSqlSq += " AND b.HOSP_AREA_CODE NOT IN (:notAreaCode)  ";
                    nowSqlXz += " AND b.HOSP_AREA_CODE NOT IN (:notAreaCode) ";
                }

            }else if("4".equals(address.getLevel())){//各街道数据
                map.put("areaCode",address.getCtcode().substring(0,9)+"%");
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"4"));
            }else if("5".equals(address.getLevel())){//各居委会数据
                map.put("areaCode",address.getCtcode());
                returnMap.put("code",address.getCtcode());
            }
            returnMap.put("name",address.getAreaSname());
            sqlXz += " AND MANAGE_AREA_CODE LIKE :areaCode";
            sqlSq += " AND MANAGE_AREA_CODE LIKE :areaCode";
            sqlXzCount += " AND MANAGE_AREA_CODE LIKE :areaCode";
            sqlSqCount += " AND MANAGE_AREA_CODE LIKE :areaCode ";
            nowSqlSq += " AND b.HOSP_AREA_CODE LIKE :areaCode ";
            nowSqlXz += " AND b.HOSP_AREA_CODE LIKE :areaCode ";
        }
        if(hosp != null){
            map.put("hospId",hosp.getId());
            sqlXz += " AND MANAGE_HOSP_ID = :hospId ";
            sqlSq += " AND MANAGE_HOSP_ID = :hospId ";
            sqlXzCount += " AND MANAGE_HOSP_ID = :hospId ";
            sqlSqCount += " AND MANAGE_HOSP_ID = :hospId ";

            nowSqlSq += " AND b.ID =:hospId ";
            nowSqlXz += " AND b.ID =:hospId ";
            returnMap.put("name",hosp.getHospName());
            returnMap.put("hospId",hosp.getId());
        }

        if(StringUtils.isNotBlank(startTime)){
            map.put("startTime",startTime + " 00:00:00");
            sqlXz += " AND MANAGE_TEAM_CREATE_TIME >= :startTime ";
            sqlSq += " AND MANAGE_TEAM_CREATE_TIME >= :startTime ";

            sqlXzCount += " AND MANAGE_TEAM_CREATE_TIME >= :startTime ";
            sqlSqCount += " AND MANAGE_TEAM_CREATE_TIME >= :startTime ";
        }
        if(StringUtils.isNotBlank(endTime)){
            map.put("endTime",endTime + " 23:59:59");
            sqlXz += " AND MANAGE_TEAM_CREATE_TIME <= :endTime ";
            sqlSq += " AND MANAGE_TEAM_CREATE_TIME <= :endTime ";

            sqlXzCount += " AND MANAGE_TEAM_CREATE_TIME <= :endTime ";
            sqlSqCount += " AND MANAGE_TEAM_CREATE_TIME <= :endTime ";
        }

        xzTeamCount = sysDao.getServiceDo().findCount(sqlXz,map);
        sqTeamCount = sysDao.getServiceDo().findCount(sqlSq,map);
        List<Map> mapsXz = sysDao.getServiceDo().findSqlMap(sqlXzCount,map);
        if(mapsXz != null && mapsXz.size()>0){
            if(mapsXz.get(0).get("manageMemBerCount")!= null){
                xzTeamMCount = (int)Double.parseDouble(mapsXz.get(0).get("manageMemBerCount").toString());
            }
            if(mapsXz.get(0).get("manageSignMemberCount") != null ){
                xzDrCount = (int)Double.parseDouble(mapsXz.get(0).get("manageSignMemberCount").toString());
            }
        }
        List<Map> mapsSq = sysDao.getServiceDo().findSqlMap(sqlSqCount,map);
        if(mapsSq != null && mapsSq.size()>0){
            if(mapsSq.get(0).get("manageMemBerCount")!= null){
                sqTeamMCount = (int)Double.parseDouble(mapsSq.get(0).get("manageMemBerCount").toString());
            }
            if(mapsSq.get(0).get("manageSignMemberCount") != null ){
                sqDrCount = (int)Double.parseDouble(mapsSq.get(0).get("manageSignMemberCount").toString());
            }
        }

        if(endDay>=nowDay){//查询当天签约团队数
            map.put("nowDay",ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("endDay",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");


        }

        totalTeamCount = xzTeamCount+sqTeamCount;
        totalTeamMCount = xzTeamMCount + sqTeamMCount;
        totalDrCount = xzDrCount + sqDrCount;
        returnMap.put("teamCount",totalTeamCount);
        returnMap.put("xzTeamCount",xzTeamCount);
        returnMap.put("sqTeamCount",sqTeamCount);

        returnMap.put("teamMCount",totalTeamMCount);
        returnMap.put("xzTeamMCount",xzTeamMCount);
        returnMap.put("sqTeamMCount",sqTeamMCount);

        returnMap.put("drCount",totalDrCount);
        returnMap.put("xzDrCount",xzDrCount);
        returnMap.put("sqDrCount",sqDrCount);

        return returnMap;
    }

    @Override
    public List<AppTeamManagEntity> findManageContByAreaCodeAndTime(String[] areaCode, String time) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("areaCode",areaCode);
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(time)+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(time)+" 23:59:59");
        map.put("STATE","1");
        String sql = "SELECT\n" +
                "\tt.ID teamId,\n" +
                "\tt.TEAM_NAME teamName,\n" +
                "\td.ID hospId,\n" +
                "\td.HOSP_AREA_CODE areaCode,\n" +
                "\td.HOSP_LEVEL_TYPE hospLevelType\n" +
                "FROM\n" +
                "\tAPP_TEAM T\n" +
                "INNER JOIN APP_HOSP_DEPT D ON t.TEAM_HOSP_ID = d.ID\n" +
                "WHERE d.HOSP_AREA_CODE IS NOT NULL AND LEFT(d.HOSP_AREA_CODE,4) IN (:areaCode) " +
                "AND t.TEAM_CREATE_TIME >=:yearStart AND t.TEAM_CREATE_TIME<=:yearEnd AND d.HOSP_STATE =:STATE AND t.TEAM_STATE =:STATE AND t.TEAM_DEL_STATE ='0'  ";

        String sqll = "SELECT\n" +
                "\tt.ID teamId,\n" +
                "\tt.TEAM_NAME teamName,\n" +
                "\td.ID hospId,\n" +
                "\td.HOSP_AREA_CODE areaCode,\n" +
                "\td.HOSP_LEVEL_TYPE hospLevelType\n" +
                "FROM\n" +
                "\tAPP_TEAM T\n" +
                "INNER JOIN APP_HOSP_DEPT D ON t.TEAM_HOSP_ID = d.ID\n" +
                "WHERE d.HOSP_AREA_CODE IS NOT NULL AND LEFT(d.HOSP_AREA_CODE,4) IN (:areaCode) " +
                "AND t.HS_CREATE_DATE >=:yearStart AND t.HS_CREATE_DATE<=:yearEnd AND d.HOSP_STATE =:STATE AND t.TEAM_STATE =:STATE AND t.TEAM_DEL_STATE ='0' " ;
        String ssql =  sql +" UNION "+sqll;
        List<AppTeamManagEntity> ls = sysDao.getServiceDo().findSqlMapRVo(ssql,map,AppTeamManagEntity.class);
        return ls;
    }

    /**
     * 根据区域查询团队数据(新不带删除团队) t.TEAM_DEL_STATE = '0'
     * @param areaCode
     * @return
     */
    @Override
    public List<AppTeamManagEntity> findManageContByAreaCodeNew(String[] areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("areaCode",areaCode);
        String sql = "SELECT\n" +
                "\tt.ID teamId,\n" +
                "\tt.TEAM_NAME teamName,\n" +
                "\td.ID hospId,\n" +
                "\td.HOSP_AREA_CODE areaCode,\n" +
                "\td.HOSP_LEVEL_TYPE hospLevelType\n" +
                "FROM\n" +
                "\tAPP_TEAM T\n" +
                "INNER JOIN APP_HOSP_DEPT D ON t.TEAM_HOSP_ID = d.ID\n" +
                "WHERE d.HOSP_AREA_CODE IS NOT NULL AND LEFT(d.HOSP_AREA_CODE,4) IN (:areaCode) AND t.TEAM_DEL_STATE = '0' ";
        List<AppTeamManagEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);
        return ls;
    }

    @Override
    public List<AppTeamManagEntity> findByManageContNew() throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tt.ID teamId,\n" +
                "\tt.TEAM_NAME teamName,\n" +
                "\td.ID hospId,\n" +
                "\td.HOSP_AREA_CODE areaCode\n" +
                "FROM\n" +
                "\tAPP_TEAM T\n" +
                "INNER JOIN APP_HOSP_DEPT D ON t.TEAM_HOSP_ID = d.ID\n" +
                "WHERE d.HOSP_AREA_CODE IS NOT NULL AND t.TEAM_DEL_STATE = '0' ";
        List<AppTeamManagEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);
        return ls;
    }

    @Override
    public List<AppTeamManagEntity> findManageNCDNew(String areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("areaCode",AreaUtils.getAreaCode(areaCode,"3"));
        String sql = "SELECT\n" +
                "\tt.ID teamId,\n" +
                "\tt.TEAM_NAME teamName,\n" +
                "\td.ID hospId,\n" +
                "\td.HOSP_AREA_CODE areaCode\n" +
                "FROM\n" +
                "\tAPP_TEAM T\n" +
                "INNER JOIN APP_HOSP_DEPT D ON t.TEAM_HOSP_ID = d.ID\n" +
                "WHERE d.HOSP_AREA_CODE IS NOT NULL AND t.TEAM_DEL_STATE = '0'  ";
        if(StringUtils.isNotBlank(areaCode)){
            sql += " AND LEFT(d.HOSP_AREA_CODE,6) = :areaCode ";
            map.put("areaCode",AreaUtils.getAreaCode(areaCode,"3"));
        }
        List<AppTeamManagEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);
        return ls;
    }

    /**
     * 不带删除团队
     * @param areaCode
     * @param time
     * @return
     */
    @Override
    public List<AppTeamManagEntity> findManageContByAreaCodeAndTimeNew(String[] areaCode, String time) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("areaCode",areaCode);
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(time)+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(time)+" 23:59:59");
        map.put("STATE","1");
        String sql = "SELECT\n" +
                "\tt.ID teamId,\n" +
                "\tt.TEAM_NAME teamName,\n" +
                "\td.ID hospId,\n" +
                "\td.HOSP_AREA_CODE areaCode,\n" +
                "\td.HOSP_LEVEL_TYPE hospLevelType\n" +
                "FROM\n" +
                "\tAPP_TEAM T\n" +
                "INNER JOIN APP_HOSP_DEPT D ON t.TEAM_HOSP_ID = d.ID\n" +
                "WHERE d.HOSP_AREA_CODE IS NOT NULL AND LEFT(d.HOSP_AREA_CODE,4) IN (:areaCode) " +
                "AND t.TEAM_CREATE_TIME >=:yearStart AND t.TEAM_CREATE_TIME<=:yearEnd AND d.HOSP_STATE =:STATE AND t.TEAM_STATE =:STATE AND t.TEAM_DEL_STATE = '0'  ";

        String sqll = "SELECT\n" +
                "\tt.ID teamId,\n" +
                "\tt.TEAM_NAME teamName,\n" +
                "\td.ID hospId,\n" +
                "\td.HOSP_AREA_CODE areaCode,\n" +
                "\td.HOSP_LEVEL_TYPE hospLevelType\n" +
                "FROM\n" +
                "\tAPP_TEAM T\n" +
                "INNER JOIN APP_HOSP_DEPT D ON t.TEAM_HOSP_ID = d.ID\n" +
                "WHERE d.HOSP_AREA_CODE IS NOT NULL AND LEFT(d.HOSP_AREA_CODE,4) IN (:areaCode) " +
                "AND t.HS_CREATE_DATE >=:yearStart AND t.HS_CREATE_DATE<=:yearEnd AND d.HOSP_STATE =:STATE AND t.TEAM_STATE =:STATE " +
                "AND t.TEAM_DEL_STATE = '0' ";
        String ssql =  sql +" UNION "+sqll;
        List<AppTeamManagEntity> ls = sysDao.getServiceDo().findSqlMapRVo(ssql,map,AppTeamManagEntity.class);
        return ls;
    }

    @Override
    public String findTeamIdsByHospId(String hospId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("hospId",hospId);
        map.put("delState","1");
        String sql = "SELECT\n" +
                "\tGROUP_CONCAT(ID) teamIds\n" +
                "FROM\n" +
                "\tapp_team\n" +
                "WHERE\n" +
                "\tTEAM_HOSP_ID =:hospId\n" +
                "AND TEAM_DEL_STATE = :delState ";
        List<Map> list = sysDao.getServiceDo().findSql(sql);
        if(list != null && list.size()>0){
            String teamIds = list.get(0).get("teamIds").toString();
            if(StringUtils.isNotBlank(teamIds)){
                return teamIds;
            }
        }
        return null;
    }

    @Override
    public List<AppTeam> findTeamByTime(String hospId, String yearStart, String yearEnd) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("hospId",hospId);
        String sqlTeam = "SELECT a.ID FROM app_team a WHERE a.TEAM_HOSP_ID = :hospId\n" +
                "AND a.TEAM_DEL_STATE = '1'\n" ;
        if(StringUtils.isNotBlank(yearStart)){
            map.put("startTime",ExtendDate.getFirstDayOfMonth(yearStart)+" 00:00:00");
            sqlTeam += " AND a.TEAM_DEL_TIME >= :startTime ";
        }
        boolean flag = false;
        if(StringUtils.isNotBlank(yearEnd)){
            //判断yearEnd是否大于当前年月
            //取当前年月数值
            String nowYM = ExtendDate.getYM(Calendar.getInstance());
            nowYM = nowYM.replace("-","");
            String strYM = yearEnd.replace("-","");
            if(Integer.parseInt(strYM)<Integer.parseInt(nowYM)){
                flag = true;
            }
            map.put("endTime",ExtendDate.getLastDayOfMonth(yearEnd)+" 23:59:59");
            sqlTeam += " AND a.TEAM_DEL_TIME <= :endTime ";
        }
        String sql = "";
        if(flag){
            sql = "SELECT * FROM APP_TEAM WHERE 1=1 AND TEAM_HOSP_ID =:hospId AND ID NOT IN ("+sqlTeam+" ) ";
        }else{
            sql = "SELECT * FROM APP_TEAM WHERE 1=1 AND TEAM_DEL_STATE = '0'" +
                    " AND TEAM_HOSP_ID =:hospId ";
        }
        List<AppTeam> list = sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
        if(list != null && list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public String addImportExcelTeam(Map<Integer, String> map) throws Exception {
        String result1 = "成功导入"+map.size()+"条";
        AppTeam team = null;
        for (int i = 0; i < map.size(); i++) {//循环每条记录数据
            int num = i + 1;
            String[] ss = map.get(i).split("\\|");//取每条记录的每一个字段
            //先根据是否是队长判断该条记录是否要添加团队数据
            if(StringUtils.isBlank(ss[0])){
                throw new Exception("导入失败：第"+num+"条，团队名称不能为空");
            }else if(StringUtils.isBlank(ss[2])){
                throw new Exception("导入失败：第"+num+"条，医生账号不能为空");
            }else if(StringUtils.isBlank(ss[3])){
                throw new Exception("导入失败：第"+num+"条，医生姓名不能为空");
            }else if(StringUtils.isBlank(ss[4])){
                throw new Exception("导入失败：第"+num+"条，是否队长不能为空");
            }else if(StringUtils.isBlank(ss[5])){
                throw new Exception("导入失败：第"+num+"条，医生工作类型不能为空");
            }else{
                //根据医生账号查询医生数据
                AppDrUser drUser = sysDao.getAppDrUserDao().findDrByCode(ss[2],ss[3]);
                if(drUser == null){
                    throw new Exception("导入失败：第"+num+"条，查无医生数据,请先上传医生信息");
                }else{
                    //根据医生信息查询机构信息
                    AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                    if(dept == null ){
                        throw new Exception("导入失败：第"+num+"条，查无机构数据,请先上传机构信息");
                    }else{
                        if("0".equals(ss[4])){//true为队长
                            team = new AppTeam();//队长说明开始新的团队插入
                            team.setTeamName(ss[0]);
                            team.setTeamCode(ss[1]);
                            team.setTeamHospId(dept.getId());
                            team.setTeamHospName(dept.getHospName());
                            team.setTeamDrId(drUser.getId());
                            team.setTeamDrName(drUser.getDrName());
                            team.setTeamDrCode(drUser.getDrCode());
                            team.setTeamState("1");
                            team.setTeamDelState("0");
                            team.setTeamTel(drUser.getDrTel());
                            team.setTeamType(TeamType.JTQY.getValue());
                            team.setTeamCreateTime(Calendar.getInstance());
                            sysDao.getServiceDo().add(team);
                        }
                        //先判断是否已有成员数据
                        AppTeamMember teamMember = sysDao.getAppTeamMemberDao().findMemByDrId(drUser.getId(),team.getId());
                        if(teamMember == null){//查无成员数据添加数据，有的话不做操作
                            teamMember = new AppTeamMember();
                            teamMember.setMemTeamid(team.getId());
                            teamMember.setMemTeamName(team.getTeamName());
                            teamMember.setDrTel(drUser.getDrTel());
                            teamMember.setMemDrId(drUser.getId());
                            teamMember.setMemDrName(drUser.getDrName());
                            teamMember.setMemWorkType(ss[5]);
                            if(ss.length == 7){
                                teamMember.setDrRole(ss[6]);
                            }
                            teamMember.setMemState(ss[4]);
                            sysDao.getServiceDo().add(teamMember);
                        }
                    }
                }
            }
        }
        return result1;
    }

    @Override
    public List<GaiRuiTeamEntity> findTeamByGaiRuiHospId(String hospId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("hospId",hospId);
        String sql = "SELECT ID teamId," +
                "TEAM_NAME teamName," +
                "TEAM_CODE teamCode," +
                "TEAM_DR_ID teamDrId," +
                "TEAM_DR_NAME teamDrName " +
                "FROM APP_TEAM WHERE TEAM_HOSP_ID = :hospId " +
                "AND TEAM_DEL_STATE = '0' " +
                "AND TEAM_STATE = '1'";
        List<GaiRuiTeamEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,GaiRuiTeamEntity.class);
        return list;
    }
}
