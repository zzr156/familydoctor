package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppDrUserDao;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.bizDo.app.vo.AppDrUserQvo;
import com.ylz.bizDo.cd.dao.SysLogModifyDo;
import com.ylz.bizDo.cd.entity.AddressHospEntity;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.jtapp.aioEntity.AppDrInfoAioEntity;
import com.ylz.bizDo.jtapp.drEntity.*;
import com.ylz.bizDo.jtapp.drVo.AppDrHospDrQvo;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangeDrEntity;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeCountQvo;
import com.ylz.bizDo.smjq.smEntity.AppSmLoginEntity;
import com.ylz.bizDo.web.po.WebDrUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.CommonLoginType;
import com.ylz.packcommon.common.comEnum.SignFormWay;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.Md5Util;
import com.ylz.packcommon.common.util.PinyinUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appDrUserDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppDrUserDaoImpl implements AppDrUserDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppDrUser> findList(AppDrUserQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_DR_USER  as a WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isBlank(qvo.getCity()) && StringUtils.isBlank(qvo.getArea()) && StringUtils.isBlank(qvo.getDrHospId())){
            sql = "SELECT a.* FROM APP_DR_USER a LEFT JOIN APP_HOSP_DEPT b ON a.DR_HOSP_ID = b.ID WHERE 1=1";
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getPro())+"%");
            sql += " AND b.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isNotBlank(qvo.getCity()) && StringUtils.isBlank(qvo.getArea()) && StringUtils.isBlank(qvo.getDrHospId())){
            sql = "SELECT a.* FROM APP_DR_USER a LEFT JOIN APP_HOSP_DEPT b ON a.DR_HOSP_ID = b.ID WHERE 1=1";
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getCity())+"%");
            sql += " AND b.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isNotBlank(qvo.getCity()) && StringUtils.isNotBlank(qvo.getArea()) && StringUtils.isBlank(qvo.getDrHospId())){
            sql = "SELECT a.* FROM APP_DR_USER a LEFT JOIN APP_HOSP_DEPT b ON a.DR_HOSP_ID = b.ID WHERE 1=1";
            map.put("appDrHospAreaCode", AreaUtils.getAreaCode(qvo.getArea())+"%");
            sql += " AND b.HOSP_AREA_CODE LIKE :appDrHospAreaCode";
        }
        if(StringUtils.isNotBlank(qvo.getPro()) && StringUtils.isNotBlank(qvo.getCity()) && StringUtils.isNotBlank(qvo.getArea()) && StringUtils.isNotBlank(qvo.getDrHospId())){
            sql = "SELECT a.* FROM APP_DR_USER a LEFT JOIN APP_HOSP_DEPT b ON a.DR_HOSP_ID = b.ID WHERE 1=1";
            map.put("appDrHospId",qvo.getDrHospId());
            sql += " AND b.ID = :appDrHospId";
        }

        if(StringUtils.isNotBlank(qvo.getAppDrAccount())){
            map.put("appDrAccount", qvo.getAppDrAccount());
            sql += " AND a.DR_ACCOUNT = :appDrAccount";
        }
        if(StringUtils.isNotBlank(qvo.getAppDrName())){
            map.put("appDrName", "%"+qvo.getAppDrName()+"%");
            sql += " AND a.DR_NAME like :appDrName";
        }
        if(StringUtils.isNotBlank(qvo.getAppDrState())){
            map.put("appDrState",qvo.getAppDrState());
            sql += " AND a.DR_STATE = :appDrState";
        }
        return sysDao.getServiceDo().findSqlMap(sql, map, AppDrUser.class, qvo);
    }

    @Override
    public AppDrUser findByTelPhone(String tel) throws Exception{
        return (AppDrUser) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppDrUser.class)
                .add(Restrictions.eq("drTel", tel))
//                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public List<AppDrUserEntity> findByUser(String userAccount, String md5UserPassword, String selectType) throws Exception{
        HashMap map = new HashMap();
        String sql="SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.ID easeId,\n" +
                "\ta.DR_NAME drName,\n" +
                "\ta.DR_HOSP_ID drHospId,\n" +
                "\ta.DR_CODE drCode,\n" +
                "\ta.DR_ACCOUNT drAccount,\n"+
                "\ta.DR_GENDER drGender,\n" +
                "\ta.DR_TEL drTel,\n" +
                "\ta.DR_IMAGEURL drImageUrl,\n" +
                "\ta.DR_IMAGE_NAME drImageName,\n" +
                "\ta.DR_INTRO drIntro,\n" +
                "\ta.DR_GOOD drGood,\n" +
                "\ta.DR_TYPE drType,\n" +
                "\ta.DR_JOB_TITLE drJobTitle,\n" +
                "\ta.DR_STATE drState,\t\n" +
                "\ta.DR_ROLE drRole,\t\n" +
                "\tb.HOSP_ADDRESS hospAddress,\t\n" +
                "\tb.HOSP_AREA_CODE drHospAreaCode," +
                "'' drHospName," +
                "'' drTeamId," +
                "\tb.HOSP_AREA_CODE cityCode," +
                "\ta.DR_PWD_STATE drPwdState," +
                "b.HOSP_LEVEL_TYPE drHospType," +
                "a.DR_ZX_STATE state," +
                "'' openJdState, " +
                "'' evaluationState " +
                " FROM\n" +
                "\tAPP_DR_USER\n a JOIN APP_HOSP_DEPT b ON a.DR_HOSP_ID = b.ID" +
                " WHERE 1=1 AND a.DR_STATE = :STATE ";//查询在职医生
        map.put("STATE","1");
        if(CommonLoginType.ZHANGHAO.getValue().equals(selectType)){
            map.put("userAccount",userAccount);
//            map.put("md5UserPassword",md5UserPassword);
//            AND DR_PWD = :md5UserPassword
            if(userAccount.length() == 11){
                sql+=" AND DR_TEL = :userAccount ";
            }else{
                sql+=" AND DR_ACCOUNT = :userAccount";
            }
        }else if(CommonLoginType.YSZJ.getValue().equals(selectType)){//医生主键登入
            map.put("userAccount",userAccount);
                sql += " AND a.ID = :userAccount";
        }else{
            map.put("userAccount",userAccount);
            sql+=" AND DR_TEL = :userAccount ";
        }
        sql += " ORDER BY DR_ZX_STATE DESC ";
        List<AppDrUserEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppDrUserEntity.class);
        if(ls != null && ls.size() >0){
            return  ls;
        }
        return null;
    }

    public AppDrUserEntity findUserById(String drId) throws Exception{
        HashMap map = new HashMap();
        String sql="SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.ID easeId,\n" +
                "\ta.DR_NAME drName,\n" +
                "\ta.DR_HOSP_ID drHospId,\n" +
                "\ta.DR_CODE drCode,\n" +
                "\ta.DR_ACCOUNT drAccount,\n" +
                "\ta.DR_GENDER drGender,\n" +
                "\ta.DR_TEL drTel,\n" +
                "\ta.DR_IMAGEURL drImageUrl,\n" +
                "\ta.DR_IMAGE_NAME drImageName,\n" +
                "\ta.DR_INTRO drIntro,\n" +
                "\ta.DR_GOOD drGood,\n" +
                "\ta.DR_TYPE drType,\n" +
                "\ta.DR_JOB_TITLE drJobTitle,\n" +
                "\ta.DR_STATE drState,\t\n" +
                "\ta.DR_ROLE drRole,\t\n" +
                "\tb.HOSP_ADDRESS hospAddress,\t\n" +
                "\tb.HOSP_AREA_CODE drHospAreaCode,'' drHospName,'' drTeamId,\tb.HOSP_AREA_CODE cityCode FROM\n" +
                "\tAPP_DR_USER\n" +
                " a JOIN APP_HOSP_DEPT b ON a.DR_HOSP_ID = b.ID WHERE 1=1  AND a.ID =:drId ";
        map.put("drId",drId);

        List<AppDrUserEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppDrUserEntity.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;
    }

    @Override
    public AppDrUser findByUserWeb(String account, String md5UserPassword) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_DR_USER WHERE 1=1";
        map.put("account",account);
        map.put("password",md5UserPassword);
        sql += " AND DR_ACCOUNT =:account AND DR_PWD =:password AND DR_STATE='1' ";
        List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    public AppDrUser findByUserWebOrg(String account,String orgid) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_DR_USER WHERE 1=1";
        map.put("account",account);
        map.put("orgid",orgid);
        sql += " AND DR_ACCOUNT =:account AND DR_HOSP_ID =:orgid AND DR_STATE='1'";
        List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    /**
     *  查询变更医生列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<YsChangeDrEntity> findChangeDrList(YsChangeCountQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        map.put("hospId",qvo.getHospId());
        map.put("drState","1");//在职医生
        String sql = "SELECT " +
                "ID id," +
                "DR_NAME name," +
                "DR_GENDER sex," +
                "DR_IMAGEURL imageUrl," +
                "DR_TYPE drType " +
                "FROM APP_DR_USER " +
                "WHERE  DR_HOSP_ID=:hospId AND DR_STATE=:drState ";
//        sql += " AND ID != :drId";
        if(StringUtils.isNotBlank(qvo.getDrName())){
            map.put("drName","%"+qvo.getDrName()+"%");
            sql += " AND DR_NAME LIKE :drName";
        }
        List<YsChangeDrEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,YsChangeDrEntity.class);
        return ls;
    }

    @Override
    public AppDrUser findByUserId(String drPatientId) throws Exception{
        return (AppDrUser) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppDrUser.class)
                .add(Restrictions.eq("id", drPatientId))
//                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public List<AppDrUser> findByType(String drType,String deptId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_DR_USER  as a WHERE 1=1 ";
       map.put("drType",drType);
       map.put("deptId",deptId);
       sql += " AND a.DR_TYPE = :drType AND a.DR_HOSP_ID =:deptId";
        List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppDrUser.class);
        if(ls!=null && ls.size()>0){
            return ls;
        }
        return null;
    }

    @Override
    public List<AppDrUser> findAll(String hospId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();

        String sql = "SELECT * FROM APP_DR_USER  as a WHERE 1=1 ";
        if(StringUtils.isNotBlank(hospId)){
            map.put("hospId",hospId);
            sql += " AND a.DR_HOSP_ID = :hospId";
        }
        List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
        if(ls!=null && ls.size()>0){
            return ls;
        }
        return null;
    }

    /**
     * 根据医生id和团队id查询医生详细信息
     * @param id
     * @return
     */
    public DrInfo findDrInfoByid(String id,String teamId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.DR_NAME drName,\n" +
                "\ta.DR_IMAGEURL drImageurl,\n" +
                "\tx.MEM_STATE teamState,\n" +
                "\tx.MEM_WORK_TYPE teamWorkType,\n" +
                "\t(SELECT b.HOSP_NAME from APP_HOSP_DEPT b where b.ID=a.DR_HOSP_ID) drHospName,\n" +
                "\ta.DR_GOOD drGood,\n" +
                "\ta.DR_INTRO drIntro,\n" +
                " x.MEM_TEAMID teamId," +
                "\ta.DR_HOSP_ID hospId,\n" +
                "\t'' zxCount,\n" +
                "\t'' sfCount,\n" +
                "\t'' dyyCount,\n" +
                "\t'' jkjyCount,\n" +
                "\t'' jkzdCount,\n" +
                "\t'' drLabel,\n" +
                "\t'' serveList\n" +
                "FROM\n" +
                "\tAPP_DR_USER a,APP_TEAM_MEMBER x\n" +
                "WHERE\n" +
                "\ta.ID=x.MEM_DR_ID\n" +
                "\tand x.MEM_TEAMID=:MEM_TEAMID\n" +
                "\tand a.ID =:ID";
        map.put("ID",id);
        map.put("MEM_TEAMID",teamId);
        map.put("way", SignFormWay.DBYS.getValue());
        List<DrInfo> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, DrInfo.class);
        if(ls!=null && ls.size()>0) {
            return ls.get(0);
        }
        return null;
    }

    /**
     * 根据医生id和团队id查询医生详细信息
     * @param drId
     * @param teamId
     * @return
     */
    @Override
    public DrInfoEntity findDrInfo(String drId, String teamId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.DR_NAME drName,\n" +
                "\ta.DR_IMAGEURL drImageurl,\n" +
                "\tx.MEM_STATE teamState,\n" +
                "\tx.MEM_WORK_TYPE teamWorkType,\n" +
                "\t(SELECT b.HOSP_NAME from APP_HOSP_DEPT b where b.ID=a.DR_HOSP_ID) drHospName,\n" +
                "\ta.DR_GOOD drGood,\n" +
                "\ta.DR_INTRO drIntro,\n" +
                " x.MEM_TEAMID teamId," +
                "\ta.DR_HOSP_ID hospId,\n" +
                "\t'' serveList\n" +
                "FROM\n" +
                "\tAPP_DR_USER a,APP_TEAM_MEMBER x\n" +
                "WHERE\n" +
                "\ta.ID=x.MEM_DR_ID\n" +
                "\tand x.MEM_TEAMID=:MEM_TEAMID\n" +
                "\tand a.ID =:ID";
        map.put("ID",drId);
        map.put("MEM_TEAMID",teamId);
        map.put("way", SignFormWay.DBYS.getValue());
        List<DrInfoEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, DrInfoEntity.class);
        if(ls!=null && ls.size()>0) {
            return ls.get(0);
        }
        return null;
    }

    @Override
    public AppDrUserEntity findUserId(String id) throws Exception{
        HashMap map = new HashMap();
        String sql="SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.ID easeId,\n" +
                "\ta.DR_NAME drName,\n" +
                "\ta.DR_HOSP_ID drHospId,\n" +
                "\ta.DR_CODE drCode,\n" +
                "\ta.DR_ACCOUNT drAccount,\n"+
                "\ta.DR_GENDER drGender,\n" +
                "\ta.DR_TEL drTel,\n" +
                "\ta.DR_IMAGEURL drImageUrl,\n" +
                "\ta.DR_IMAGE_NAME drImageName,\n" +
                "\ta.DR_INTRO drIntro,\n" +
                "\ta.DR_GOOD drGood,\n" +
                "\ta.DR_TYPE drType,\n" +
                "\ta.DR_JOB_TITLE drJobTitle,\n" +
                "\ta.DR_STATE drState,\t\n" +
                "\tb.HOSP_ADDRESS hospAddress,\t\n" +
                "\tb.HOSP_AREA_CODE drHospAreaCode," +
                "a.DR_ROLE drRole," +
                "'' drHospName," +
                "'' drTeamId," +
                "\tb.HOSP_AREA_CODE cityCode" +
                " FROM\n" +
                "\tAPP_DR_USER\n a JOIN APP_HOSP_DEPT b ON a.DR_HOSP_ID = b.ID" +
                " WHERE 1=1 ";
            map.put("id",id);
            sql+=" AND a.ID = :id ";
        List<AppDrUserEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppDrUserEntity.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;
    }

    @Override
    public List<AppDrUser> findByHosp(String hospId) throws Exception {

        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_DR_USER  AS a WHERE 1=1 ";
        map.put("hospId",hospId);
        map.put("type","4");
        sql += " AND a.DR_HOSP_ID = :hospId AND a.DR_TYPE =:type and a.DR_STATE = '1'";
        return this.sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);

    }

    @Override
    public List<AppDrUser> findListByHospId(String hospId, String drId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_DR_USER  AS a WHERE 1=1 AND a.DR_STATE='1' ";//在职的医生
        map.put("hospId",hospId);
        map.put("drId",drId);
        sql += " AND a.DR_HOSP_ID = :hospId AND a.ID <> :drId";
        return this.sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
    }

    @Override
    public List<AppDrUser> findByEase() throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_DR_USER  AS a WHERE 1=1 ";
        sql += " AND a.DR_EASE_STATE IS NULL";
        return this.sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
    }


    /**
     * 查询医院下非团长的医生
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDrChangeInfoEntity> findDrList(AppDrHospDrQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("type","4");
       /* String sql = " SELECT a.ID id,a.DR_NAME drName,a.DR_GENDER drGender,DR_IMAGEURL drImageUrl FROM APP_DR_USER a " +
                "LEFT JOIN APP_TEAM_MEMBER b ON a.ID = b.MEM_DR_ID WHERE 1=1 AND b.MEM_STATE !=:state";*/
       String sql = "SELECT " +
               "a.ID id," +
               "a.DR_NAME drName," +
               "a.DR_GENDER drGender," +
               "a.DR_IMAGEURL drImageUrl " +
               "FROM APP_DR_USER a WHERE 1=1" +
               " AND a.DR_TYPE =:type AND a.DR_STATE='1' " ;
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND a.DR_HOSP_ID = :hospId";
        }
        List<AppDrChangeInfoEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrChangeInfoEntity.class);
        return ls;
    }

    @Override
    public boolean findDrUserByTel(String tel) throws Exception{
        boolean result = false;
        AppDrUser user  = (AppDrUser) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppDrUser.class)
                .add(Restrictions.eq("drTel", tel))
//                .setCacheable(true)
                .uniqueResult();
        if(user != null){
            result = true;
        }
        return result;
    }

    @Override
    public List<AddressHospEntity> findByHospIdNotTs(String upId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT a.ID id,a.DR_NAME name,'0' state,'4' level FROM APP_DR_USER  as a WHERE 1=1 AND a.DR_STATE='1' ";
        if(StringUtils.isNotBlank(upId)){
            map.put("upId",upId);
            sql += " AND a.DR_HOSP_ID = :upId";
        }
        sql += " ORDER BY DR_CODE ASC ";
        List<AddressHospEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AddressHospEntity.class);
        return ls;
    }

    @Override
    public List<AppDrUser> findByAccount(String account) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_DR_USER WHERE 1=1";
        map.put("account",account);
        sql += " AND DR_ACCOUNT =:account OR DR_TEL =:account AND DR_STATE='1' ";
        List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
        return ls;
    }

    @Override
    public List<AppDrMAccountEntity> findByDrTel(String drTel) throws Exception{

        Map<String,Object> map = new HashMap<>();
        map.put("drTel",drTel);
        String sql = "SELECT a.ID drId," +
                "a.DR_NAME drName," +
                "b.HOSP_NAME hospName," +
                "a.DR_ZX_STATE state," +
                "a.DR_ACCOUNT drAccount " +
                " FROM app_dr_user a INNER JOIN app_hosp_dept b ON a.DR_HOSP_ID = b.ID \n" +
                "LEFT JOIN APP_DR_PATIENT_KEY c ON a.ID = c.DR_PATIENT_ID\n" +
                "WHERE a.DR_TEL=:drTel\n" +
                " AND a.DR_STATE ='1' " +
                "ORDER BY c.HS_UPDATE_TIME DESC";
        List<AppDrMAccountEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrMAccountEntity.class);
        return list;
    }

    @Override
    public List<AppDrInfoAioEntity> findDrInfoByidList(String teamId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.DR_NAME drName,\n" +
                "\ta.DR_IMAGEURL drImageurl,\n" +
                "\tx.MEM_STATE teamState,\n" +
                "\tx.MEM_WORK_TYPE teamWorkType,\n" +
                "\t(SELECT b.HOSP_NAME from APP_HOSP_DEPT b where b.ID=a.DR_HOSP_ID) drHospName,\n" +
                "\ta.DR_GOOD drGood,\n" +
                "\ta.DR_INTRO drIntro,\n" +
                " x.MEM_TEAMID teamId," +
                "\ta.DR_HOSP_ID hospId \n" +
                "FROM\n" +
                "\tAPP_DR_USER a,APP_TEAM_MEMBER x\n" +
                "WHERE\n" +
                "\ta.ID=x.MEM_DR_ID\n" +
                "\tAND a.DR_STATE ='1'\n" +
                "\tand x.MEM_TEAMID=:MEM_TEAMID\n";
        map.put("MEM_TEAMID",teamId);
        List<AppDrInfoAioEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql, map, AppDrInfoAioEntity.class);
        if(ls!=null && ls.size()>0) {
            return ls;
        }
        return null;
    }

    @Override
    public Map<String, Object> findDrUserName(String drPatientId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_DR_USER a  WHERE 1=1 \n";
        if(StringUtils.isNotBlank(drPatientId)){
            map.put("drPatientId",drPatientId);
            sql += " AND a.ID = :drPatientId";
        }

        List<Map> ls=sysDao.getServiceReadDo().findSqlMap(sql, map);
        if(ls!=null && ls.size()>0) {
            return ls.get(0);
        }
        return null;
    }

    /**
     * 基卫同步接口
     * @param vo
     */
    @Override
    public void jwSyncDrUser(WebDrUser vo)throws Exception{
        WebDrUser webDrUser= (WebDrUser) sysDao.getServiceDo().find(WebDrUser.class,vo.getId());
        //新增
        if(webDrUser==null){
            if(StringUtils.isBlank(vo.getDrRole())){
                vo.setDrRole("9");//医生权限默认为9
            }
            vo.setDrType("4");//医生类型默认为家庭医生
            vo.setDrState("1");//医生状态默认为启用
            vo.setDrs("0");//默认修改密码
            if(StringUtils.isNotBlank(vo.getDrTel())){
                if(vo.getDrTel().length()>=11) {
                    vo.setDrTelPwd(Md5Util.MD5(vo.getDrTel().substring(vo.getDrTel().length()-6,vo.getDrTel().length())));
                }
            }
            sysDao.getServiceDo().add(vo);
//            SysLogModifyDo dao= (SysLogModifyDo) SpringHelper.getBean("sysLogModifyDo");
//            dao.addSysLogModify(null,"基卫添加",vo.getClass().getName(), vo.getId(),vo.toString(),"jwSyncDrUser");

            //更新
        }else{
            webDrUser.setDrTypeRole(vo.getDrTypeRole());
            webDrUser.setDrHospId(vo.getDrHospId());
            webDrUser.setDrName(vo.getDrName());
            webDrUser.setDrAccount(vo.getDrAccount());
            if(StringUtils.isBlank(webDrUser.getDrTel())){
                if(StringUtils.isNotBlank(vo.getDrTel())){
                    if(vo.getDrTel().length()>=11) {
                        webDrUser.setDrTelPwd(Md5Util.MD5(vo.getDrTel().substring(vo.getDrTel().length()-6,vo.getDrTel().length())));
                    }
                }
            }else{
                if(StringUtils.isBlank(webDrUser.getDrTelPwd())){
                    if(webDrUser.getDrs().equals("0")){
                        if(StringUtils.isNotBlank(vo.getDrTel())){
                            webDrUser.setDrTelPwd(Md5Util.MD5(vo.getDrTel().substring(vo.getDrTel().length()-6,vo.getDrTel().length())));
                        }else{
                            if(StringUtils.isNotBlank(webDrUser.getDrTel())){
                                webDrUser.setDrTelPwd(Md5Util.MD5(webDrUser.getDrTel().substring(webDrUser.getDrTel().length()-6,webDrUser.getDrTel().length())));
                            }else{
                                webDrUser.setDrTelPwd(vo.getDrPwd());
                            }
                        }
                    }else {
                        webDrUser.setDrTelPwd(vo.getDrPwd());
                    }
                }else{
                    if("0".equals(webDrUser.getDrs())){//未修改过密码
                        webDrUser.setDrTelPwd(Md5Util.MD5(webDrUser.getDrTel().substring(webDrUser.getDrTel().length()-6,webDrUser.getDrTel().length())));
                    }
                }
            }
            webDrUser.setDrPwd(vo.getDrPwd());
            webDrUser.setDrGender(vo.getDrGender());
            webDrUser.setDrTel(vo.getDrTel());
            webDrUser.setDrIdno(vo.getDrIdno());//医生身份证
            webDrUser.setDrState(vo.getDrState());//医生状态(1-启用,0-停用)
            if(StringUtils.isBlank(vo.getDrType())){
                webDrUser.setDrType("4");
            }
            if(StringUtils.isNotBlank(webDrUser.getDrName())) {
                webDrUser.setDrBopomofo(PinyinUtil.getPinYinHeadChar(webDrUser.getDrName()));
            }
            if(StringUtils.isNotBlank(vo.getDrRole())){
                webDrUser.setDrRole(vo.getDrRole());
            }
            sysDao.getServiceDo().modify(webDrUser);

            //同步登入密码到web登入的用户表（cd_user）
            List<CdUser> listUser = sysDao.getUserDo().findUserByAccountDrId(webDrUser.getDrAccount(),webDrUser.getId(),webDrUser.getDrHospId());
            if(listUser!= null && listUser.size()>0){
                for(CdUser user:listUser){
                    if(!webDrUser.getDrPwd().equals(user.getUserPassword())){
                        user.setUserPassword(webDrUser.getDrPwd());
                        sysDao.getServiceDo().modify(user);
                    }
                }
            }

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("MEM_DR_ID",webDrUser.getId());
            String sql = "SELECT * FROM APP_TEAM_MEMBER t WHERE 1=1 AND t.MEM_DR_ID = :MEM_DR_ID";
            List<AppTeamMember> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppTeamMember.class);
            if(ls != null && ls.size() >0){
                for(AppTeamMember v : ls){
                    v.setMemDrName(webDrUser.getDrName());
                    v.setDrTel(webDrUser.getDrTel());
                    sysDao.getServiceDo().modify(v);
                }
            }
        }
    }

    /**
     * 三明医生登录返回信息
     * @param drId
     * @return
     * @throws Exception
     */
    @Override
    public AppSmLoginEntity findDrinfoxx(String drId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("drId",drId);
        String sql = "SELECT\n" +
                "\ta.ID drId,\n" +
                "\ta.DR_NAME drName,\n" +
                "\tb.ID orgId,\n" +
                "\tb.HOSP_NAME orgName,\n" +
                "'' listTeam\n" +
                "FROM\n" +
                "\tapp_dr_user a\n" +
                "INNER JOIN app_hosp_dept b ON a.DR_HOSP_ID = b.ID\n" +
                "WHERE\n" +
                "\ta.ID =:drId ";
        List<AppSmLoginEntity> ll = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSmLoginEntity.class);
        if(ll != null && ll.size()>0){
            return ll.get(0);
        }
        return null;
    }

    @Override
    public List<AppDrUserPossEntity> findByUserPoss(String userAccount, String md5UserPassword, String selectType)throws Exception {
        HashMap map = new HashMap();
        String sql="SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.ID easeId,\n" +
                "\ta.DR_NAME drName,\n" +
                "\ta.DR_HOSP_ID drHospId,\n" +
                "\ta.DR_CODE drCode,\n" +
                "\ta.DR_ACCOUNT drAccount,\n"+
                "\ta.DR_GENDER drGender,\n" +
                "\ta.DR_TEL drTel,\n" +
                "\ta.DR_IMAGEURL drImageUrl,\n" +
                "\ta.DR_IMAGE_NAME drImageName,\n" +
                "\ta.DR_INTRO drIntro,\n" +
                "\ta.DR_GOOD drGood,\n" +
                "\ta.DR_TYPE drType,\n" +
                "\ta.DR_JOB_TITLE drJobTitle,\n" +
                "\ta.DR_STATE drState,\t\n" +
                "\ta.DR_ROLE drRole,\t\n" +
                "\tb.HOSP_ADDRESS hospAddress,\t\n" +
                "\tb.HOSP_AREA_CODE drHospAreaCode," +
                "'' drHospName," +
                "'' drTeamId," +
                "\tb.HOSP_AREA_CODE cityCode," +
                "\ta.DR_PWD_STATE drPwdState," +
                "b.HOSP_LEVEL_TYPE drHospType," +
                "a.DR_ZX_STATE state," +
                "'' openJdState, " +
                "'' evaluationState," +
                "a.DR_ABROAB_URL drAbroabUrl " +
                " FROM\n" +
                "\tAPP_DR_USER\n a JOIN APP_HOSP_DEPT b ON a.DR_HOSP_ID = b.ID" +
                " WHERE 1=1 AND a.DR_STATE = :STATE ";//查询在职医生
        map.put("STATE","1");
        if(CommonLoginType.ZHANGHAO.getValue().equals(selectType)){
            map.put("userAccount",userAccount);
//            map.put("md5UserPassword",md5UserPassword);
//            AND DR_PWD = :md5UserPassword
            if(userAccount.length() == 11){
                sql+=" AND DR_TEL = :userAccount ";
            }else{
                sql+=" AND DR_ACCOUNT = :userAccount";
            }
        }else if(CommonLoginType.YSZJ.getValue().equals(selectType)){//医生主键登入
            map.put("userAccount",userAccount);
            sql += " AND a.ID = :userAccount";
        }else{
            map.put("userAccount",userAccount);
            sql+=" AND DR_TEL = :userAccount ";
        }
        sql += " ORDER BY DR_ZX_STATE DESC ";
        List<AppDrUserPossEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppDrUserPossEntity.class);
        if(ls != null && ls.size() >0){
            return  ls;
        }
        return null;
    }

    @Override
    public List<AppDrUser> findAllByState(String hospId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drState","1");
        String sql = "SELECT * FROM APP_DR_USER  as a WHERE 1=1 AND a.DR_STATE =:drState ";
        if(StringUtils.isNotBlank(hospId)){
            map.put("hospId",hospId);
            sql += " AND a.DR_HOSP_ID = :hospId";
        }
        List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
        if(ls!=null && ls.size()>0){
            return ls;
        }
        return null;
    }

    @Override
    public AppDrUser findDrByCode(String account,String drName) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("account",account);
        String sql = "SELECT * FROM APP_DR_USER WHERE 1=1 ";
        sql += " AND DR_ACCOUNT =:account ";
        if(StringUtils.isNotBlank(drName)){
            map.put("drName",drName);
            sql += " AND DR_NAME =:drName ";
        }
        List<AppDrUser> list = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 医生数据导入
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public String addImportExcelDr(Map<Integer, String> map) throws Exception {
        String result1 = "成功导入"+map.size()+"条";
        for (int i = 0; i < map.size(); i++) {//循环每条记录数据
            int num = i + 1;
            String[] ss = map.get(i).split("\\|");//取每条记录的每一个字段
            //先根据是否是队长判断该条记录是否要添加团队数据
            if(StringUtils.isBlank(ss[0])){
                throw new Exception("导入失败：第"+num+"条，医生姓名不能为空");
            }else if(StringUtils.isBlank(ss[1])){
                throw new Exception("导入失败：第"+num+"条，机构编码不能为空");
            }else if(StringUtils.isBlank(ss[2])){
                throw new Exception("导入失败：第"+num+"条，医生编码不能为空");
            }else if(StringUtils.isBlank(ss[3])){
                throw new Exception("导入失败：第"+num+"条，医生账号不能为空");
            }else if(StringUtils.isBlank(ss[6])){
                throw new Exception("导入失败：第"+num+"条，机构级别不能为空");
            }else if(StringUtils.isBlank(ss[7])) {
                throw new Exception("导入失败：第"+num+"条，机构类型不能为空");
            }else{
                //根据账号查询该账号是否已被使用
                List<AppDrUser> drList = sysDao.getAppDrUserDao().findByAccount(ss[3]);
                if(drList != null && drList.size()>0){
                    throw new Exception("导入失败：第"+num+"条，医生账号已存在");
                }
                AppHospDept dept = sysDao.getAppHospDeptDao().findHospByCode(ss[1]);
                if(dept != null){
                    AppDrUser user = new AppDrUser();
                    user.setDrName(ss[0]);
                    user.setDrHospId(dept.getId());
                    user.setDrCode(ss[2]);
                    user.setDrAccount(ss[3]);
                    user.setDrPwd(Md5Util.MD5(ss[4]));
                    user.setDrTelPwd(Md5Util.MD5(ss[4]));
                    user.setDrGender(ss[5]);
                    user.setDrTel(ss[6]);
                    user.setDrIdno(ss[7]);
                    user.setDrType(ss[8]);
                    user.setDrJobTitle(ss[9]);
                    user.setDrIntro(ss[10]);
                    user.setDrGood(ss[11]);
                    user.setDrState(ss[12]);
                    user.setDrRole(ss[13]);
                    if(ss.length<15){

                    }else{
                        user.setDrTypeRole(ss[14]);
                    }
                    sysDao.getServiceDo().add(user);
                }else{
                    throw new Exception("导入失败：第"+num+"条，医生机构信息不存在，请先导入机构数据");
                }
            }
        }
        return result1;
    }
}
