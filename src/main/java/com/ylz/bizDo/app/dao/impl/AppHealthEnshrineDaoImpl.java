package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppHealthEnshrineDao;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHealthEnshrine;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppUserHealthED;
import com.ylz.bizDo.jtapp.commonEntity.AppEnshrineHealthEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppHealthEntiry;
import com.ylz.bizDo.jtapp.commonVo.AppEnshrineHealthQvo;
import com.ylz.bizDo.jtapp.commonVo.AppHealthQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrQvo;
import com.ylz.bizDo.jtapp.patientVo.AppPatientHealthQvo;
import com.ylz.bizDo.news.po.NewsTable;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/22.
 */
@Service("appHealthEnshrineDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppHealthEnshrineDaoImpl implements AppHealthEnshrineDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public String saveEnshrine(AppEnshrineHealthQvo qvo) throws Exception {//收藏
        AppHealthEnshrine table = new AppHealthEnshrine();
        NewsTable news = (NewsTable) this.sysDao.getServiceDo().find(NewsTable.class,qvo.getHealthId());
        table.setHenUserId(qvo.getUserId());
        table.setHenUserName(qvo.getUserName());
        table.setHenHealthId(qvo.getHealthId());
        table.setHenHealthTitle(news.getTableTitle());
        table.setHenImageUrl(news.getTableImageUrl());
        this.sysDao.getServiceDo().add(table);
        if(news!=null){
            news.setTableEnshrine(String.valueOf(Integer.parseInt(news.getTableEnshrine())+1));
            this.sysDao.getServiceDo().modify(news);
        }
        return "true";
    }

    /**
     * 医生收藏健康教育模板
     * @param qvo
     * @throws Exception
     */
    @Override
    public void enshrineHD(AppEnshrineHealthQvo qvo) throws Exception {
        if(StringUtils.isNotBlank(qvo.getHealthId())){
            String[] healthIds = qvo.getHealthId().split(";");
            for(String healthId:healthIds) {
                AppHealthEnshrine table = new AppHealthEnshrine();
                NewsTable news = (NewsTable) this.sysDao.getServiceDo().find(NewsTable.class,healthId);
                table.setHenUserId(qvo.getUserId());
                table.setHenUserName(qvo.getUserName());
                table.setHenHealthId(healthId);
                if(news!=null){
                    news.setTableEnshrine(String.valueOf(Integer.parseInt(news.getTableEnshrine())+1));
                    this.sysDao.getServiceDo().modify(news);
                    table.setHenImageUrl(news.getTableImageUrl());
                    table.setHenHealthTitle(news.getTableTitle());
                    table.setHenContent(news.getTableContent());
                    table.setHenType(news.getTableHealthType());
                    table.setHenTime(Calendar.getInstance());
                    table.setHenCreateId(news.getTableCjrid());
                    table.setHenCreateTime(ExtendDate.getCalendar(news.getStrTableCjsj()));
                }
                this.sysDao.getServiceDo().add(table);
            }
        }
    }

    /**
     * 查询收藏列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppHealthEntiry> findList(AppDrQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",qvo.getUserId());
        String sql = " select a.ID id,a.HEN_HEALTH_TITLE title,a.HEN_CONTENT content,date_format(a.HEN_TIME, '%Y-%c-%d %H:%i:%s') time," +
                "a.HEN_IMAGE_URL imageUrl,a.HEN_TYPE type,a.HEN_CREATE_ID drId,'' drName,'' typeName,'' drTypeName FROM APP_HEALTH_ENSHRINE a" +
                " WHERE 1=1 AND a.HEN_USER_ID =:userId";
        if(StringUtils.isNotBlank(qvo.getHealthType())){
            map.put("type",qvo.getHealthType());
            sql += " AND a.HEN_TYPE =:type";
        }
        List<AppHealthEntiry> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppHealthEntiry.class);
        return ls;
    }

    /**
     * 个人收藏健康教育
     * @param qvo
     * @throws Exception
     */
    @Override
    public void enshrineHDP(AppPatientHealthQvo qvo) throws Exception {
        if(StringUtils.isNotBlank(qvo.getHealthId())){
            String[] healthIds = qvo.getHealthId().split(";");
            for(String healthId:healthIds){
                AppHealthEnshrine table = new AppHealthEnshrine();
                AppUserHealthED healthED = (AppUserHealthED)this.sysDao.getServiceDo().find(AppUserHealthED.class,healthId);
                if(healthED!=null){
                    NewsTable news = (NewsTable) this.sysDao.getServiceDo().find(NewsTable.class,healthED.getId());
                    table.setHenUserId(qvo.getUserId());
                    table.setHenUserName(qvo.getUserName());
                    table.setHenHealthId(healthId);
                    if(news!=null){
                        news.setTableEnshrine(String.valueOf(Integer.parseInt(news.getTableEnshrine())+1));
                        this.sysDao.getServiceDo().modify(news);
                    }
                    table.setHenImageUrl(healthED.getHedImageUrl());
                    table.setHenHealthTitle(healthED.getHedTitle());
                    table.setHenContent(healthED.getHedContent());
                    table.setHenType(healthED.getHedType());
                    table.setHenTime(Calendar.getInstance());
                    table.setHenCreateId(healthED.getHedDrId());
                    table.setHenCreateTime(healthED.getHedCreateTime());
                    this.sysDao.getServiceDo().add(table);
                }
            }
        }
    }

    /**
     * 收藏健康教育
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppHealthEnshrine saveHealth(AppHealthQvo qvo) throws Exception {
        NewsTable news = (NewsTable)sysDao.getServiceDo().find(NewsTable.class,qvo.getId());
        if(news!=null){
            AppHealthEnshrine table = new AppHealthEnshrine();
            table.setHenCreateTime(news.getTableCjsj());
            table.setHenCreateId(news.getTableCjrid());
            table.setHenTime(Calendar.getInstance());
            table.setHenType(news.getTableHealthType());
            table.setHenContent(news.getTableContent());
            table.setHenHealthTitle(news.getTableTitle());
            table.setHenImageUrl(news.getTableImageUrl());
            table.setHenHealthId(news.getId());
            table.setHenUserId(qvo.getUserId());
            if(DrPatientType.PATIENT.getValue().equals(qvo.getType())){
                if(StringUtils.isNotBlank(qvo.getHedId())){
                    AppUserHealthED healthED = (AppUserHealthED)sysDao.getServiceDo().find(AppUserHealthED.class,qvo.getHedId());
                    if(healthED!=null){
                        table.setHenCreateId(healthED.getHedDrId());
                        table.setHenCreateTime(healthED.getHedCreateTime());
                    }
                }
                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getUserId());
                if(user!=null){
                    table.setHenUserName(user.getPatientName());
                }
            }else if(DrPatientType.DR.getValue().equals(qvo.getType())){
                AppDrUser user = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getUserId());
                if(user!=null){
                    table.setHenUserName(user.getDrName());
                }
            }
            sysDao.getServiceDo().add(table);
            news.setTableEnshrine(String.valueOf(Integer.parseInt(news.getTableEnshrine())+1));
            sysDao.getServiceDo().modify(news);
            return table;
        }
        return null;
    }

    /**
     * 查询收藏健康教育列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppEnshrineHealthEntity> findEnshrineList(AppHealthQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",qvo.getUserId());
        String sql = "SELECT a.ID id," +
                "a.HEN_HEALTH_ID newsId," +
                "a.HEN_HEALTH_TITLE title," +
                "a.HEN_CONTENT content," +
                "a.HEN_IMAGE_URL imageUrl," +
                "date_format(a.HEN_TIME, '%Y-%c-%d %H:%i:%s') timeStart," +
                "date_format(a.HEN_CREATE_TIME, '%Y-%c-%d %H:%i:%s') timeCreate," +
                "a.HEN_CREATE_ID drId," +
                "(SELECT DR_NAME FROM APP_DR_USER WHERE ID = a.HEN_CREATE_ID) drName," +
                "(SELECT TABLE_ENSHRINE FROM NEWS_TABLE WHERE ID = a.HEN_HEALTH_ID) scl," +
                "(SELECT TABLE_BROWSE FROM NEWS_TABLE WHERE ID = a.HEN_HEALTH_ID) lll," +
                "(SELECT TABLE_TRANSMIT FROM NEWS_TABLE WHERE ID = a.HEN_HEALTH_ID) tsl " +
                "FROM APP_HEALTH_ENSHRINE a WHERE 1=1 AND a.HEN_USER_ID =:userId";
        if(StringUtils.isNotBlank(qvo.getContent())){
            map.put("content","%"+qvo.getContent()+"%");
            sql += " AND (a.HEN_CREATE_ID IN (select ID from APP_DR_USER WHERE DR_NAME LIKE :content) OR a.HEN_HEALTH_TITLE LIKE :content OR a.HEN_CONTENT LIKE :content)";
        }
        sql += " ORDER BY HEN_TIME DESC ";
        List<AppEnshrineHealthEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppEnshrineHealthEntity.class);
        return ls;
    }

    /**
     * 查看收藏健康教育详细内容
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppEnshrineHealthEntity findEnshrine(String id) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        String sql = "SELECT a.ID id," +
                "a.HEN_HEALTH_ID newsId," +
                "a.HEN_HEALTH_TITLE title," +
                "a.HEN_CONTENT content," +
                "a.HEN_IMAGE_URL imageUrl," +
                "date_format(a.HEN_TIME, '%Y-%c-%d %H:%i:%s') timeStart," +
                "date_format(a.HEN_CREATE_TIME, '%Y-%c-%d %H:%i:%s') timeCreate," +
                "a.HEN_CREATE_ID drId," +
                "(SELECT DR_NAME FROM APP_DR_USER WHERE ID = a.HEN_CREATE_ID) drName," +
                "(SELECT TABLE_ENSHRINE FROM NEWS_TABLE WHERE ID = a.HEN_HEALTH_ID) scl," +
                "(SELECT TABLE_BROWSE FROM NEWS_TABLE WHERE ID = a.HEN_HEALTH_ID) lll," +
                "(SELECT TABLE_TRANSMIT FROM NEWS_TABLE WHERE ID = a.HEN_HEALTH_ID) tsl " +
                "FROM APP_HEALTH_ENSHRINE a WHERE 1=1 AND a.ID =:id";
        List<AppEnshrineHealthEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppEnshrineHealthEntity.class);
        if(ls!=null&&ls.size()>0){
            NewsTable table = (NewsTable)sysDao.getServiceDo().find(NewsTable.class,ls.get(0).getNewsId());
            if(table!=null){
                table.setTableBrowse(String.valueOf(Integer.parseInt(table.getTableBrowse())+1));
                sysDao.getServiceDo().modify(table);
            }
            return ls.get(0);
        }
        return null;
    }

    /**
     * 查询是否已收藏健康教育模板
     * @param id
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public AppHealthEnshrine find(String id, String userId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("newsId",id);
        map.put("userId",userId);
        String sql = "SELECT * FROM APP_HEALTH_ENSHRINE WHERE HEN_HEALTH_ID =:newsId AND HEN_USER_ID=:userId";
        List<AppHealthEnshrine> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppHealthEnshrine.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    @Override
    public AppHealthEnshrine findByQvo(AppHealthQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",qvo.getUserId());
        map.put("newId",qvo.getId());
        String sql = "SELECT * FROM APP_HEALTH_ENSHRINE WHERE HEN_HEALTH_ID =:newId AND HEN_USER_ID=:userId";
        List<AppHealthEnshrine> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppHealthEnshrine.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }
}