package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.NoticesReadType;
import com.ylz.packcommon.common.comEnum.NoticesType;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/6/20.
 */
public class AppNoticeCountEntity {
    private String people;//主键
    private String noticeType;//公告类型
    private String noticeName;//类型名称
    private String noticeCount;//统计数
    private String noticePeople;//患者id
    private String time;//时间
    private String title;
    private String content;

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) throws Exception {
        if(StringUtils.isNotBlank(this.getNoticeType())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_NOTICE, this.getNoticeType());
            if(value!=null) {
                this.noticeName = value.getCodeTitle();
            }
        }
    }

    public String getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(String noticeCount) {
        int count = 0;
        if(StringUtils.isNotBlank(this.getNoticeType())&&StringUtils.isNotBlank(this.getNoticePeople())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("receivePeople",this.getNoticePeople());
            map.put("receivePeopleOrMange","1");
            map.put("readType", NoticesReadType.WEIDU.getValue());
            map.put("noticeType",this.getNoticeType());
            String sql = "";
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getNoticePeople());
            if(user!=null){
                sql =  "SELECT" +
                        " count(1) " +
                        "FROM APP_NOTICE a " +
                        "WHERE 1=1 " +
                        "AND a.NOTICE_RECEIVE_PEOPLE IN (:receivePeople ,:receivePeopleOrMange) " +
                        "AND a.NOTICE_READ = :readType " +
                        "AND a.NOTICE_TYPE =:noticeType ";
                String sqlTag = " OR a.ID IN (" +
                        "SELECT b.NOTICE_ID FROM APP_NOTICE_RECEIVE b WHERE find_in_set(b.NOTICE_RECEIVE_PEOPLE,(" +
                        "SELECT GROUP_CONCAT(cc.LABEL_VALUE) FROM APP_LABEL_GROUP cc INNER JOIN APP_SIGN_FORM bb ON bb.ID = cc.LABEL_SIGN_ID " +
                        " WHERE bb.SIGN_PATIENT_ID =:receivePeople AND bb.SIGN_STATE ='2'))!= '0' " +
                        ") AND  a.NOTICE_READ = :readType ";
                if(this.getNoticeType().equals(NoticesType.XTXX.getValue())){
                    sql += sqlTag;
                }
                sql += " ORDER BY a.NOTICE_CREATE_TIME DESC LIMIT 1";
            }else{
                sql = "SELECT" +
                        " count(1) " +
                        "FROM APP_NOTICE b " +
                        "WHERE 1=1 " +
                        "AND b.NOTICE_RECEIVE_PEOPLE =:receivePeople " +
                        "AND b.NOTICE_READ = :readType " +
                        "AND b.NOTICE_TYPE =:noticeType ";
            }
            count = dao.getServiceDo().gteSqlCount(sql,map);
        }

        this.noticeCount = String.valueOf(count);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        if(StringUtils.isNotBlank(this.getNoticeType())&&StringUtils.isNotBlank(this.getNoticePeople())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("receivePeople",this.getNoticePeople());
            map.put("receivePeopleOrMange","1");
            map.put("noticeType",this.getNoticeType());
            String sql = "";
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getNoticePeople());
            if(user!=null){
                sql ="SELECT" +
                        " a.NOTICE_CREATE_TIME" +
                        " FROM APP_NOTICE a " +
                        "WHERE 1=1 " +
                        "AND a.NOTICE_RECEIVE_PEOPLE IN (:receivePeople ,:receivePeopleOrMange) " +
                        "AND a.NOTICE_TYPE =:noticeType ";
                String sqlTag = " OR a.ID IN (" +
                        "SELECT b.NOTICE_ID FROM APP_NOTICE_RECEIVE b WHERE find_in_set(b.NOTICE_RECEIVE_PEOPLE,(" +
                        "SELECT GROUP_CONCAT(cc.LABEL_VALUE) FROM APP_LABEL_GROUP cc INNER JOIN APP_SIGN_FORM bb ON bb.ID = cc.LABEL_SIGN_ID " +
                        " WHERE bb.SIGN_PATIENT_ID =:receivePeople AND bb.SIGN_STATE ='2'))!= '0' " +
                        ") ";
                if(this.getNoticeType().equals(NoticesType.XTXX.getValue())){
                    sql += sqlTag;
                }
                sql += " ORDER BY a.NOTICE_CREATE_TIME DESC LIMIT 1";
            }else{
                sql = "SELECT" +
                        " b.NOTICE_CREATE_TIME" +
                        " FROM APP_NOTICE b " +
                        "WHERE 1=1 " +
                        "AND b.NOTICE_RECEIVE_PEOPLE =:receivePeople " +
                        "AND b.NOTICE_TYPE =:noticeType " +
                        "ORDER BY b.NOTICE_CREATE_TIME DESC LIMIT 1";
            }
            List<Map> ls=dao.getServiceDo().findSqlMap(sql,map);
            if(ls != null && ls.size() >0) {
                time = ls.get(0).get("NOTICE_CREATE_TIME").toString().substring(0,19);
            }
        }
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(StringUtils.isNotBlank(this.getNoticeType())&&StringUtils.isNotBlank(this.getNoticePeople())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("receivePeople",this.getNoticePeople());
            map.put("receivePeopleOrMange","1");
            map.put("noticeType",this.getNoticeType());
            String sql = "";
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getNoticePeople());
            if(user!=null){
                sql = "SELECT" +
                        " a.NOTICE_TITLE " +
                        "FROM APP_NOTICE a " +
                        "WHERE 1=1 " +
                        "AND a.NOTICE_RECEIVE_PEOPLE IN (:receivePeople ,:receivePeopleOrMange) " +
                        "AND a.NOTICE_TYPE =:noticeType ";
                String sqlTag = " OR a.ID IN (" +
                        "SELECT b.NOTICE_ID FROM APP_NOTICE_RECEIVE b WHERE find_in_set(b.NOTICE_RECEIVE_PEOPLE,(" +
                        "SELECT GROUP_CONCAT(cc.LABEL_VALUE) FROM APP_LABEL_GROUP cc INNER JOIN APP_SIGN_FORM bb ON bb.ID = cc.LABEL_SIGN_ID " +
                        " WHERE bb.SIGN_PATIENT_ID =:receivePeople AND bb.SIGN_STATE ='2'))!= '0' " +
                        ") ";
                if(this.getNoticeType().equals(NoticesType.XTXX.getValue())){
                    sql += sqlTag;
                }
                sql += " ORDER BY a.NOTICE_CREATE_TIME DESC LIMIT 1";

            }else{
                sql = "SELECT" +
                        " b.NOTICE_TITLE " +
                        "FROM APP_NOTICE b " +
                        "WHERE 1=1 " +
                        "AND b.NOTICE_RECEIVE_PEOPLE =:receivePeople " +
                        "AND b.NOTICE_TYPE =:noticeType " +
                        "ORDER BY b.NOTICE_CREATE_TIME DESC LIMIT 1";
            }

            List<Map> ls =dao.getServiceDo().findSqlMap(sql,map);
            if(ls != null && ls.size() >0) {
                title = ls.get(0).get("NOTICE_TITLE").toString();
            }
        }
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if(StringUtils.isNotBlank(this.getNoticeType())&&StringUtils.isNotBlank(this.getNoticePeople())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("receivePeople",this.getNoticePeople());
            map.put("receivePeopleOrMange","1");
            map.put("noticeType",this.getNoticeType());
            String sql = "";
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getNoticePeople());
            if(user!=null){
                sql = "SELECT" +
                        " a.NOTICE_CONTENT" +
                        " FROM APP_NOTICE a " +
                        "WHERE 1=1 " +
                        "AND a.NOTICE_RECEIVE_PEOPLE IN (:receivePeople ,:receivePeopleOrMange) " +
                        "AND a.NOTICE_TYPE =:noticeType " ;
                String sqlTag = " OR a.ID IN (" +
                        "SELECT b.NOTICE_ID FROM APP_NOTICE_RECEIVE b WHERE find_in_set(b.NOTICE_RECEIVE_PEOPLE,(" +
                        "SELECT GROUP_CONCAT(cc.LABEL_VALUE) FROM APP_LABEL_GROUP cc INNER JOIN APP_SIGN_FORM bb ON bb.ID = cc.LABEL_SIGN_ID " +
                        " WHERE bb.SIGN_PATIENT_ID =:receivePeople AND bb.SIGN_STATE ='2'))!= '0' " +
                        ") ";
                if(this.getNoticeType().equals(NoticesType.XTXX.getValue())){
                    sql += sqlTag;
                }
                sql += " ORDER BY a.NOTICE_CREATE_TIME DESC LIMIT 1";
            }else{
                sql = "SELECT" +
                        " b.NOTICE_CONTENT" +
                        " FROM APP_NOTICE b " +
                        "WHERE 1=1 " +
                        "AND b.NOTICE_RECEIVE_PEOPLE =:receivePeople " +
                        "AND b.NOTICE_TYPE =:noticeType " +
                        "ORDER BY b.NOTICE_CREATE_TIME DESC LIMIT 1";
            }
            List<Map> ls =dao.getServiceDo().findSqlMap(sql,map);
            if(ls != null && ls.size() >0) {
                content = ls.get(0).get("NOTICE_CONTENT").toString();
            }
        }

        this.content = content;
    }

    public String getNoticePeople() {
        return noticePeople;
    }

    public void setNoticePeople(String noticePeople) {
        if(StringUtils.isNotBlank(this.getNoticeType())&&StringUtils.isNotBlank(this.getPeople())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("receivePeople",this.getPeople());
            map.put("receivePeopleOrMange","1");
            map.put("readType", NoticesReadType.WEIDU.getValue());
            map.put("noticeType",this.getNoticeType());
            String sql = "";
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPeople());
            if(user!=null){
                sql = "SELECT" +
                        " a.NOTICE_RECEIVE_PEOPLE" +
                        " FROM APP_NOTICE a " +
                        "WHERE 1=1 " +
                        "AND a.NOTICE_RECEIVE_PEOPLE IN (:receivePeople ,:receivePeopleOrMange) " +
                        "AND a.NOTICE_TYPE =:noticeType " ;
                String sqlTag = " OR a.ID IN (" +
                        "SELECT b.NOTICE_ID FROM APP_NOTICE_RECEIVE b WHERE find_in_set(b.NOTICE_RECEIVE_PEOPLE,(" +
                        "SELECT GROUP_CONCAT(cc.LABEL_VALUE) FROM APP_LABEL_GROUP cc INNER JOIN APP_SIGN_FORM bb ON bb.ID = cc.LABEL_SIGN_ID " +
                        " WHERE bb.SIGN_PATIENT_ID =:receivePeople AND bb.SIGN_STATE ='2'))!= '0' " +
                        ") ";
                if(this.getNoticeType().equals(NoticesType.XTXX.getValue())){
                    sql += sqlTag;
                }
                sql += " ORDER BY a.NOTICE_CREATE_TIME DESC LIMIT 1";
            }else{
                sql = "SELECT" +
                        " b.NOTICE_RECEIVE_PEOPLE" +
                        " FROM APP_NOTICE b " +
                        "WHERE 1=1 " +
                        "AND b.NOTICE_RECEIVE_PEOPLE =:receivePeople " +
                        "AND b.NOTICE_TYPE =:noticeType " +
                        "ORDER BY b.NOTICE_CREATE_TIME DESC LIMIT 1";
            }
            List<Map> ls =dao.getServiceDo().findSqlMap(sql,map);
            if(ls != null && ls.size() >0) {
                noticePeople = ls.get(0).get("NOTICE_RECEIVE_PEOPLE").toString();
            }
        }
        this.noticePeople = noticePeople;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }
}
