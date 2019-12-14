package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppTeamMemberDao;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.bizDo.app.vo.AppTeamMemberQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrChangeInfoEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTeamMemEntiry;
import com.ylz.bizDo.jtapp.drVo.AppDrTeamMemQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrTeamQvo;
import com.ylz.bizDo.jtapp.gaiRuiEntity.GaiRuiDrEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMemberEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/14.
 */
@Service("appTeamMemberDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppTeamMemberDaoImpl implements AppTeamMemberDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppTeamMember> findListQvo(AppTeamMemberQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_TEAM_MEMBER  as a WHERE 1=1 ";

        if(StringUtils.isNotBlank(qvo.getAppMemTeamId())){
            map.put("appMemTeamId",qvo.getAppMemTeamId());
            sql += " AND a.MEM_TEAMID = :appMemTeamId";
        }
        return sysDao.getServiceDo().findSqlMap(sql, map, AppTeamMember.class, qvo);
    }
    @Override
    public List<AppTeamMemberEntity> findMemByTeamId(String teamId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamId",teamId);
        String sql = "SELECT b.DR_TEL drtel,a.ID id,a.MEM_TEAMID memTeamid,a.MEM_DR_ID memDrId,a.MEM_DR_NAME memDrName,a.MEM_STATE memState," +
                "a.MEM_WORK_TYPE memWorkType,'' memWorkName,'' toplimit FROM APP_TEAM_MEMBER  as a INNER JOIN APP_DR_USER as b ON a.MEM_DR_ID = b.ID WHERE 1=1 ";
            sql += " AND a.MEM_TEAMID = :teamId AND b.DR_STATE='1'";

       List<AppTeamMemberEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppTeamMemberEntity.class);
       if(ls!=null){
           return ls;
       }
       return null;
    }

    @Override
    public AppTeamMember findMemByDrId(String id,String teamId) throws Exception{
        return (AppTeamMember)this.sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppTeamMember.class)
                .add(Restrictions.eq("memDrId",id))
                .add(Restrictions.eq("memTeamid",teamId))
                .uniqueResult();
    }

    @Override
    public AppTeamMember findMemberByDrId(String drId) throws Exception{
        return (AppTeamMember)this.sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppTeamMember.class)
                .add(Restrictions.eq("memDrId",drId))
                .uniqueResult();
    }

    @Override
    public List<AppTeamMember> findTeamId(String signTeamId) throws Exception{
        return this.sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppTeamMember.class)
                .add(Restrictions.eq("memTeamid",signTeamId))
                .list();
    }

    @Override
    public List<AppTeamMember> findTeamId(String signTeamId,String teamDrId) throws Exception{
        return this.sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppTeamMember.class)
                .add(Restrictions.eq("memTeamid",signTeamId))
                .add(Restrictions.ne("memDrId",teamDrId))
                .list();
    }

    /**
     * 添加团队成员
     * @param qvo
     * @throws Exception
     */
    @Override
    public void addTeamMem(AppDrTeamQvo qvo) throws Exception {
        List<AppDrTeamMemQvo> list = qvo.getTeamMems();
        AppTeam team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class,qvo.getId());
        String result = null;
        try {
            result = PropertiesUtil.getConfValue("messageCode");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(list!=null&&list.size()>0){
            for(AppDrTeamMemQvo ls:list){
                AppTeamMember table = new AppTeamMember();
                if(team!=null){
                    table.setMemTeamid(team.getId());
                    table.setMemTeamName(team.getTeamName());
                }
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,ls.getMemId());
                if(drUser!=null){
                    table.setMemDrId(drUser.getId());
                    table.setMemDrName(drUser.getDrName());
                }
                table.setMemState("1");
                table.setMemWorkType(ls.getWorkType());
                sysDao.getServiceDo().add(table);
                if(result.equals("0")){
                    //加入环信群
                    sysDao.getSecurityCardAsyncBean().addGroupMembers(team.getTeamEaseGroupId(),table.getMemDrId());
                    sysDao.getSecurityCardAsyncBean().addGroupMembers(team.getTeamEaseRoomId(),table.getMemDrId());
                }
            }
        }
    }

    /**
     * 查询团队成员列表
     * @param teamId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDrTeamMemEntiry> findTeamMem(String teamId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT a.ID id," +
                "a.MEM_TEAMID teamId," +
                "a.MEM_TEAM_NAME teamName," +
                "a.MEM_DR_ID drId," +
                "a.MEM_DR_NAME drName," +
                "a.MEM_STATE state," +
                "a.MEM_WORK_TYPE workType," +
                "'' workTypeName " +
                " FROM APP_TEAM_MEMBER a WHERE 1=1";
        map.put("teamId",teamId);
        sql += " AND a.MEM_TEAMID =:teamId";
        sql += " ORDER BY a.MEM_STATE";
        List<AppDrTeamMemEntiry> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrTeamMemEntiry.class);
        return ls;
    }

    /**
     * 删除团队成员
     * @param id
     * @throws Exception
     */
    @Override
    public void delTeamMem(String id) throws Exception {
        AppTeamMember table = (AppTeamMember)sysDao.getServiceDo().find(AppTeamMember.class,id);
        if(table!=null){

            String result = null;
            try {
                result = PropertiesUtil.getConfValue("messageCode");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(result.equals("0")){
                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,table.getMemTeamid());
                //环信剔除群
                sysDao.getSecurityCardAsyncBean().removeGroupMembers(team.getTeamEaseGroupId(),table.getMemDrId());
                sysDao.getSecurityCardAsyncBean().removeRoomMembers(team.getTeamEaseRoomId(),table.getMemDrId(), CommonShortType.YISHENG.getValue());
            }

            sysDao.getServiceDo().delete(table);
        }

    }

    /**
     * 修改团队成员
     * @param qvo
     * @throws Exception
     */
    @Override
    public AppDrTeamMemEntiry modifyTeamMem(AppDrTeamQvo qvo) throws Exception {
        AppTeamMember table = (AppTeamMember)sysDao.getServiceDo().find(AppTeamMember.class,qvo.getMemId());
        if(table!=null){
            if(StringUtils.isNotBlank(qvo.getWorkType())){
                table.setMemWorkType(qvo.getWorkType());
            }
            if(StringUtils.isNotBlank(qvo.getId())){
                table.setMemTeamid(qvo.getId());
            }
            sysDao.getServiceDo().modify(table);
            AppDrTeamMemEntiry tt = new AppDrTeamMemEntiry();
            tt.setDrId(table.getMemDrId());
            tt.setDrName(table.getMemDrName());
            tt.setId(table.getId());
            tt.setState(table.getMemState());
            tt.setTeamId(table.getMemTeamid());
            tt.setTeamName(table.getMemTeamName());
            tt.setWorkType(table.getMemWorkType());
            tt.setWorkTypeName(table.getMemWorkTypeName());
            return tt;
        }
        return null;
    }

    /**
     * 查询本机构下非本团队的医生列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDrChangeInfoEntity> findDr(AppDrTeamQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT " +
                "a.ID id," +
                "a.DR_NAME drName," +
                "a.DR_GENDER drGender," +
                "a.DR_IMAGEURL drImageUrl " +
                "FROM APP_DR_USER a WHERE 1=1";
        map.put("teamId",qvo.getId());
        map.put("hospId",qvo.getHospId());
        sql += " AND a.DR_HOSP_ID =:hospId";
        sql += " AND a.ID NOT IN (SELECT b.MEM_DR_ID FROM APP_TEAM_MEMBER b WHERE b.MEM_TEAMID =:teamId )";
        List<AppDrChangeInfoEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrChangeInfoEntity.class);
        return ls;
    }

    /**
     * 查询团队下的医生
     * @param teamId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDrUser> findAll(String teamId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamId",teamId);
        String sql = "SELECT a.* " +
                "FROM APP_DR_USER a " +
                "INNER JOIN APP_TEAM_MEMBER b ON a.ID = b.MEM_DR_ID WHERE 1=1 " +
                "AND b.MEM_TEAMID=:teamId";
        List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
        return ls;
    }


    /**
     * Dmao 查询团队成员基本信息
     */
    public List<AppTeamMemberEntity> findteamidM(String teamid) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamId",teamid);
        String sql = "SELECT\n" +
                "\tt.MEM_DR_ID memDrId,\n" +
                "\tt.MEM_DR_NAME memDrName,\n" +
                "\tt.MEM_STATE memState,\n" +
                "\tt.MEM_WORK_TYPE memWorkType,\n" +
                "\tu.DR_TEL drtel, t.MEM_TEAMID memTeamid \n" +
                "FROM\n" +
                "\tAPP_TEAM_MEMBER t,\n" +
                "\tAPP_DR_USER u\n" +
                "WHERE\n" +
                "\tt.MEM_DR_ID = u.ID\n" +
                "AND t.MEM_TEAMID =:teamId ";
        List<AppTeamMemberEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamMemberEntity.class);
        if(ls.size()>0 && ls!=null){
            return ls;
        }
        return null;
    }


    /**
     * 团队人员数
     * @param signTeamId
     * @return
     */
    @Override
    public Integer findTeamPeopleCount(String signTeamId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT count(1) FROM APP_TEAM_MEMBER WHERE 1=1 ";
        if(StringUtils.isNotBlank(signTeamId)){
            map.put("MEM_TEAMID",signTeamId);
            sql += " AND MEM_TEAMID = :MEM_TEAMID ";
        }
        return  sysDao.getServiceDo().findCount(sql,map);
    }

    @Override
    public void updateTeamName(AppTeam vo) {
        String sql = "update app_team_member set mem_team_name = :teamName" +
                " where mem_teamid = :memTeamId";
        HashMap map = new HashMap();
        map.put("teamName",vo.getTeamName());
        map.put("memTeamId",vo.getId());
        sysDao.getServiceDo().update(sql,map);
    }

    @Override
    public void updateTeamMember(AppTeam vo) throws Exception{
        if(StringUtils.isNotBlank(vo.getId())){
            List<AppTeamMember> listT = sysDao.getServiceDo().loadByPk(AppTeamMember.class,"memTeamid",vo.getId());
            if(listT != null && listT.size()>0){
                for(AppTeamMember tt:listT){
                    tt.setMemTeamName(vo.getTeamName());
                    sysDao.getServiceDo().modify(tt);
                }
            }
        }
    }

    @Override
    public String findByDrId(String drId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("drId",drId);
        String sql = "SELECT group_concat(MEM_TEAMID) cc  FROM app_team_member WHERE MEM_DR_ID=:drId ";
        List<Map> ll = sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(ll != null && ll.size()>0){
            if(ll.get(0).get("cc")!=null){
                return  ll.get(0).get("cc").toString();
            }
        }
        return null;
    }

    @Override
    public List<AppDrUser> findDrListByTeamId(String teamId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",teamId);
        map.put("DR_STATE","1");
        String sql = "SELECT a.* FROM app_dr_user a INNER JOIN app_team_member b ON a.ID = b.MEM_DR_ID WHERE b.MEM_TEAMID=:teamId " +
                "AND a.DR_STATE =:DR_STATE";
        List<AppDrUser> list = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
        return list;
    }

    @Override
    public List<GaiRuiDrEntity> findDrByTeamId(String teamId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",teamId);
        String sql = "SELECT\n" +
                "\tb.ID drId,\n" +
                "\tb.DR_NAME drName,\n" +
                "\tb.DR_ACCOUNT drAccount,\n" +
                "\tb.DR_TEL drTel\n" +
                "FROM\n" +
                "\tapp_team_member a\n" +
                "INNER JOIN app_dr_user b ON a.MEM_DR_ID = b.ID\n" +
                "WHERE\n" +
                "\ta.MEM_TEAMID = :teamId";
        List<GaiRuiDrEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,GaiRuiDrEntity.class);
        return list;
    }
}
