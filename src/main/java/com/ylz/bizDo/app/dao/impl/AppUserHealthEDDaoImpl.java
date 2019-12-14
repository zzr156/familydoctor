package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppUserHealthEDDao;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.po.AppUserHealthED;
import com.ylz.bizDo.jtapp.commonEntity.AppHealthEntiry;
import com.ylz.bizDo.jtapp.commonVo.AppHealthQvo;
import com.ylz.bizDo.jtapp.commonVo.AppHealthUserQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrHealthEntity;
import com.ylz.bizDo.news.po.NewsTable;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.comEnum.NoticesType;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.FileUtils;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/21.
 */
@Service("appUserHealthEDDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppUserHealthEDDaoImpl implements AppUserHealthEDDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public String save(AppHealthUserQvo qvo) throws Exception {
        String[] ids = qvo.getUserIds().split(";");
        if(StringUtils.isNotBlank(qvo.getHedId())){
            int num = 0;
            NewsTable news = (NewsTable) this.sysDao.getServiceDo().find(NewsTable.class,qvo.getHedId());
            for(String id:ids){
                List<AppSignForm> form = this.sysDao.getServiceDo().loadByPk(AppSignForm.class,"signPatientId",id);
                //患者信息
                AppPatientUser users = (AppPatientUser) this.sysDao.getServiceDo().find(AppPatientUser.class,id);
                AppUserHealthED table = new AppUserHealthED();
                num ++;
                table.setHedDrId(qvo.getDrId());
                table.setHedId(qvo.getHedId());
                table.setHedTitle(news.getTableTitle());
                table.setHedImageUrl(news.getTableImageUrl());
                table.setHedDrName(qvo.getDrName());
                if(form!=null && form.size()>0){
                    table.setHedTeamId(form.get(0).getSignTeamId());
                    table.setHedTeamName(form.get(0).getSignTeamName());
                }
                table.setHedCreateTime(Calendar.getInstance());
                table.setHedUserId(id);
                if(users!=null){
                    table.setHedUserName(users.getPatientName());
                }
                table.setHedType(news.getTableHealthType());
                table.setHedContent(news.getTableContent());
                this.sysDao.getServiceDo().add(table);
                //推送健康资讯(发消息提醒) 您新收到一笔健康咨询
                sysDao.getAppNoticeDao().addNotices("健康资讯","您新收到一笔健康资讯",
                        NoticesType.JKZX.getValue() ,qvo.getDrId(),id,qvo.getHedId(), DrPatientType.DR.getValue());
            }
            if(news!=null){
                news.setTableTransmit(String.valueOf(Integer.parseInt(news.getTableTransmit())+num));
                this.sysDao.getServiceDo().modify(news);
            }

        }else{
            NewsTable news = new NewsTable();
            //news.set
            for(String id:ids){
                List<AppSignForm> form = this.sysDao.getServiceDo().loadByPk(AppSignForm.class,"signPatientId",id);
                //患者信息
                AppPatientUser users = (AppPatientUser) this.sysDao.getServiceDo().find(AppPatientUser.class,id);
                AppUserHealthED table = new AppUserHealthED();
                table.setHedDrId(qvo.getDrId());
                table.setHedId(qvo.getHedId());
                table.setHedTitle(qvo.getTitle());
                if(StringUtils.isNotBlank(qvo.getImageUrl())){
                    try{
//                        String path =sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),qvo.getImageName());
//                        FileUtils.decoderBase64File(qvo.getImageUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//                        table.setHedImageUrl(path);

                        Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(), CommonShortType.YISHENG.getValue());
                        table.setHedImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                table.setHedDrName(qvo.getDrName());
                if(form!=null && form.size()>0){
                    table.setHedTeamId(form.get(0).getSignTeamId());
                    table.setHedTeamName(form.get(0).getSignTeamName());
                }
                table.setHedCreateTime(Calendar.getInstance());
                table.setHedUserId(id);
                if(users!=null){
                    table.setHedUserName(users.getPatientName());
                }
                table.setHedType(qvo.getType());
                table.setHedContent(qvo.getContent());
                this.sysDao.getServiceDo().add(table);
                //推送健康资讯(发消息提醒) 您新收到一笔健康咨询
                sysDao.getAppNoticeDao().addNotices("健康资讯","您新收到一笔健康咨询",
                        NoticesType.JKZX.getValue() ,qvo.getDrId(),id,qvo.getHedId(),DrPatientType.PATIENT.getValue());
            }
        }

        return "true";
    }

    @Override
    public List<AppDrHealthEntity> findList(String drId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",drId);
        String sql = "SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.HED_TITLE title,\n" +
                "\ta.HED_IMAGE_URL imageUrl,\n" +
                "\tdate_format(a.HED_CREATE_TIME, '%Y-%c-%d %H:%i:%s') time,\n" +
                "\ta.HED_USER_ID patientId,\n" +
                "\ta.HED_USER_NAME patientName\n" +
                "FROM\n" +
                "\tAPP_USER_HEALTHED a\n" +
                "LEFT JOIN APP_SIGN_FORM b ON a.HED_USER_ID = b.SIGN_PATIENT_ID\n" +
                "WHERE a.HED_DR_ID = :drId";

        List<AppDrHealthEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrHealthEntity.class);
        return ls;
    }

    @Override
    public AppHealthEntiry findByOne(AppHealthQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",qvo.getUserId());
        map.put("healthId",qvo.getId());
        String sql = "SELECT\n" +
                "\ta.ID id,\n" +
                "\ta.HED_TITLE title,\n" +
                "\ta.HED_IMAGE_URL imageUrl,\n" +
                "\tdate_format(a.HED_CREATE_TIME, '%Y-%c-%d %H:%i:%s') time,\n" +
                "\ta.HED_DR_ID drId,\n" +
                "a.HED_DR_NAME drName," +
                "a.HED_CONTENT content," +
                "a.HED_ID newsId," +
                "'' drTypeName," +
                "a.HED_TYPE type," +
                "'' typeName," +
                "(SELECT COUNT(1) FROM APP_HEALTH_ENSHRINE WHERE HEN_HEALTH_ID = a.HED_ID AND HEN_USER_ID =:userId ) enshrineState," +
                "(SELECT b.TABLE_BROWSE FROM NEWS_TABLE b WHERE b.ID = a.HED_ID) browse," +
                "(SELECT b.TABLE_ENSHRINE FROM NEWS_TABLE b WHERE b.ID = a.HED_ID) enshrine," +
                "(SELECT b.TABLE_TRANSMIT FROM NEWS_TABLE b WHERE b.ID = a.HED_ID) transmit " +
                "FROM\n" +
                "\tAPP_USER_HEALTHED a\n" +
                "LEFT JOIN APP_SIGN_FORM b ON a.HED_USER_ID = b.SIGN_PATIENT_ID\n" +
                "WHERE a.ID = :healthId ";

        List<AppHealthEntiry> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppHealthEntiry.class);
        if(ls!=null&& ls.size()>0){
            NewsTable table =(NewsTable) sysDao.getServiceDo().find(NewsTable.class,ls.get(0).getNewsId());
            if(table!=null){
                table.setTableBrowse(String.valueOf(Integer.parseInt(table.getTableBrowse())+1));
                sysDao.getServiceDo().modify(table);
            }
            return ls.get(0);
        }
        return null;
    }
}
