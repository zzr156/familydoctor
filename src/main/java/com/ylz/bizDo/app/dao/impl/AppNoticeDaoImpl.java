package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppNoticeDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.jtapp.commonEntity.AppNoticeCountEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppNoticeListEntity;
import com.ylz.bizDo.jtapp.commonEntity.NoticeViewEntity;
import com.ylz.bizDo.jtapp.commonVo.AppNoticeCountQvo;
import com.ylz.bizDo.jtapp.drVo.AppSignsWarningRecordQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.JsonUtil;
import com.ylz.packcommon.common.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service("appNoticeDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppNoticeDaoImpl implements AppNoticeDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public void addNotices(String title, String content, String type, String createPeople, String receivePeople,String forEginId,String typeUser) throws Exception {
        try{
            AppNotice v = new AppNotice();
            v.setNoticeContent(content);
            v.setNoticeTitle(title);
            v.setNoticeCreatePeople(createPeople);
            v.setNoticeCreateTime(Calendar.getInstance());
            if(StringUtils.isNotBlank(type)){
                if(type.indexOf(",")>-1){
                    String[] ss = type.split(",");
                    v.setNoticeType(ss[0]);
                    v.setNoticeMtype(ss[1]);
                }else{
                    v.setNoticeType(type);
                }
            }
            v.setNoticeReceivePeople(receivePeople);
            v.setNoticeRead(NoticesReadType.WEIDU.getValue());
            v.setNoticeForeign(forEginId);
            sysDao.getServiceDo().add(v);
            AppNoticeListEntity vo = new AppNoticeListEntity();
            vo.setId(v.getId());
            vo.setNoticeContent(v.getNoticeContent());
            vo.setNoticeCreateName(v.getNoticeCreatePeople());
            Date date = new Date();
            Timestamp nousedate = new Timestamp(date.getTime());
            vo.setNoticeCreateTime(nousedate);
            vo.setNoticeReceiveName(v.getNoticeReceivePeople());
            vo.setNoticeTitle(v.getNoticeTitle());
            vo.setNoticeForEginKey(v.getNoticeForeign());
            vo.setNoticeType(v.getNoticeType());
            //极光推送
            String contentJson = JsonUtil.toJson(vo);
            if(receivePeople.equals("1")){
                this.sysDao.getSecurityCardAsyncBean().push_tag(receivePeople,v.getNoticeContent(),contentJson);
            }else {
                AppDrUser drUser = (AppDrUser)this.sysDao.getServiceDo().find(AppDrUser.class,receivePeople);
                if(drUser != null){
                    if(UserJgType.YISHEZHI.getValue().equals(drUser.getDrJgState())){
                        if(StringUtils.isNotBlank(drUser.getDrTel())){
                            this.sysDao.getSecurityCardAsyncBean().push_dr(drUser.getDrTel(),v.getNoticeContent(),contentJson);
                        }
                    }
                }else {
                    AppPatientUser patientUser = (AppPatientUser)this.sysDao.getServiceDo().find(AppPatientUser.class,receivePeople);
                    if(patientUser != null){
                        if(UserJgType.YISHEZHI.getValue().equals(patientUser.getPatientJgState())){
                            if(StringUtils.isNotBlank(patientUser.getPatientIdno())){
                                this.sysDao.getSecurityCardAsyncBean().push_patient(Md5Util.MD5(patientUser.getPatientIdno()),v.getNoticeContent(),contentJson);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addNoticesAllTag(String title, String content, String noticeType, String createPeople,
                                 String receivePeople, String forEginId,String type,String areaCode,boolean assigned) throws Exception{
        try{
            AppNotice v = new AppNotice();
            v.setNoticeContent(content);
            v.setNoticeTitle(title);
            v.setNoticeCreatePeople(createPeople);
            v.setNoticeCreateTime(Calendar.getInstance());
            v.setNoticeType(noticeType);
            v.setNoticeReceivePeople(receivePeople);
            v.setNoticeRead(NoticesReadType.WEIDU.getValue());
            v.setNoticeForeign(forEginId);
            sysDao.getServiceDo().add(v);

            if(org.apache.commons.lang.StringUtils.isNotBlank(receivePeople)){
                String[] ss = receivePeople.split(",");
                for(String s:ss){
                    AppNoticeFb ff = new AppNoticeFb();
                    ff.setNoticeContent("您新收到一笔系统消息，请注意查看。");
                    ff.setNoticeTitle(title);
                    ff.setNoticeCreatePeople(createPeople);
                    ff.setNoticeCreateTime(Calendar.getInstance());
                    ff.setNoticeType(NoticesType.XTXX.getValue());
                    ff.setNoticeReceivePeople(s);
                    ff.setNoticeRead(NoticesReadType.WEIDU.getValue());
                    ff.setNoticeForeign(forEginId);
                    ff.setNoticeId(v.getId());
                    sysDao.getServiceDo().add(ff);
                }
            }
            AppNoticeListEntity vo = new AppNoticeListEntity();
            vo.setId(v.getId());
            vo.setNoticeContent(v.getNoticeContent());
            vo.setNoticeCreateName(v.getNoticeCreatePeople());
            Date date = new Date();
            Timestamp nousedate = new Timestamp(date.getTime());
            vo.setNoticeCreateTime(nousedate);
            vo.setNoticeReceiveName(v.getNoticeReceivePeople());
            vo.setNoticeTitle(v.getNoticeTitle());
            vo.setNoticeForEginKey(v.getNoticeForeign());
            vo.setNoticeType(v.getNoticeType());
            //极光推送
            String contentJson = JsonUtil.toJson(vo);
            if(type.equals("allpeople")){
                this.sysDao.getSecurityCardAsyncBean().push_tag(receivePeople,v.getNoticeContent(),contentJson);
            }else{
                if(assigned){//发送tag
                    this.sysDao.getSecurityCardAsyncBean().push_tag_tagANd(receivePeople,areaCode,v.getNoticeContent(),contentJson);
                }else{//发送指定
                    AppPatientUser patientUser = (AppPatientUser)this.sysDao.getServiceDo().find(AppPatientUser.class,receivePeople);
                    if(patientUser != null){
                        if(UserJgType.YISHEZHI.getValue().equals(patientUser.getPatientJgState())){
                            if(StringUtils.isNotBlank(patientUser.getPatientIdno())){
                                this.sysDao.getSecurityCardAsyncBean().push_patient(Md5Util.MD5(patientUser.getPatientIdno()),v.getNoticeContent(),contentJson);
                            }
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<AppNoticeCountEntity> findUnReadNoticeCount(String drPatientId) throws Exception{
        Map<String, Object>map = new HashMap<String,Object>();
        map.put("receivePeople",drPatientId);
        map.put("readType",NoticesReadType.WEIDU.getValue());
        String sql = "SELECT a.NOTICE_TYPE noticeType,'' noticeName ,count(1) noticeCount," +
                " date_format((SELECT c.NOTICE_CREATE_TIME FROM APP_NOTICE c WHERE c.NOTICE_TYPE = a.NOTICE_TYPE ORDER BY c.NOTICE_CREATE_TIME DESC LIMIT 1), '%Y-%c-%d %H:%i:%s') time, " +
                " (SELECT c.NOTICE_TITLE FROM APP_NOTICE c WHERE c.NOTICE_TYPE = a.NOTICE_TYPE ORDER BY c.NOTICE_CREATE_TIME DESC LIMIT 1) title," +
                " (SELECT c.NOTICE_CONTENT FROM APP_NOTICE c WHERE c.NOTICE_TYPE = a.NOTICE_TYPE ORDER BY c.NOTICE_CREATE_TIME DESC LIMIT 1) content" +
                " FROM APP_NOTICE a WHERE 1=1";
        sql += " AND a.NOTICE_RECEIVE_PEOPLE = :receivePeople AND a.NOTICE_READ = :readType GROUP BY a.NOTICE_TYPE ";
        return sysDao.getServiceDo().findSqlMapRVo(sql,map,AppNoticeCountEntity.class);
    }


    @Override
    public List<AppNoticeListEntity> findNoticeList(AppNoticeCountQvo qvo) throws Exception{
        Map<String, Object>map = new HashMap<String,Object>();
        map.put("readType",NoticesReadType.YIDU.getValue());
        map.put("receivePeople",qvo.getDrPatientId());
        map.put("receivePeopleOrMange","1");
        map.put("noticeType",qvo.getNoticeType());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState",signStates);
        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getDrPatientId());
        String sqlUpdate = "";
        String sql = "";
        if(user!=null){//患者查询消息列表
            //查询患者签约信息
            String signSql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_STATE IN (:signState) AND SIGN_FROM_DATE<=SYSDATE() AND SIGN_TO_DATE>SYSDATE() " +
                    "AND SIGN_PATIENT_ID=:receivePeople";
            List<AppSignForm> lisF = sysDao.getServiceDo().findSqlMap(signSql,map,AppSignForm.class);
            if(lisF!=null&&lisF.size()>0){
                AppSignForm form = lisF.get(0);
                if(form.getSignDate()!=null){
                    map.put("SIGNDATE", ExtendDate.getYMD_h_m_s(form.getSignFromDate()));
                    sqlUpdate = "UPDATE APP_NOTICE t SET t.NOTICE_READ = :readType " +
                            "WHERE t.NOTICE_TYPE = :noticeType " +
                            "AND t.NOTICE_RECEIVE_PEOPLE IN(:receivePeople,:receivePeopleOrMange) " +
                            "OR t.ID IN (" +
                            "SELECT b.NOTICE_ID FROM APP_NOTICE_RECEIVE b WHERE find_in_set(b.NOTICE_RECEIVE_PEOPLE,(" +
                            "SELECT GROUP_CONCAT(cc.LABEL_VALUE) FROM APP_LABEL_GROUP cc INNER JOIN APP_SIGN_FORM bb ON bb.ID = cc.LABEL_SIGN_ID " +
                            " WHERE bb.SIGN_PATIENT_ID =:receivePeople AND bb.SIGN_STATE IN (:signState)))!= '0' " +
                            ")";

                    sql = "SELECT\n" +
                            "\tID id,\n" +
                            "\tNOTICE_TYPE noticeType,\n" +
                            "\tNOTICE_TITLE noticeTitle,\n" +
                            "\tNOTICE_CONTENT noticeContent,\n" +
                            "NOTICE_CREATE_PEOPLE createId," +
                            "\tNOTICE_CREATE_PEOPLE noticeCreateName,\n" +
                            "NOTICE_RECEIVE_PEOPLE receiveId," +
                            "\tNOTICE_RECEIVE_PEOPLE noticeReceiveName,\n" +
                            "\tNOTICE_CREATE_TIME noticeCreateTime,\n" +
                            "NOTICE_READ noticeState," +
                            "\tNOTICE_FOREIGN noticeForEginKey," +
                            "NOTICE_MTYPE noticeMtype," +
                            "'' srtState\n" +
                            "FROM\n" +
                            "\tAPP_NOTICE WHERE 1=1 " ;
//                            "AND NOTICE_CREATE_TIME>=:SIGNDATE ";
                    sql += " AND NOTICE_RECEIVE_PEOPLE IN (:receivePeople ,:receivePeopleOrMange) AND NOTICE_TYPE = :noticeType ";
                    if(qvo.getNoticeType().equals(NoticesType.XTXX.getValue())){
                        sql += " OR ID IN (" +
                                "SELECT b.NOTICE_ID FROM APP_NOTICE_RECEIVE b WHERE  " +
//                                "b.NOTICE_CREATE_TIME >=:SIGNDATE " +
                                " find_in_set(b.NOTICE_RECEIVE_PEOPLE,(" +
                                "SELECT GROUP_CONCAT(cc.LABEL_VALUE) FROM APP_LABEL_GROUP cc INNER JOIN APP_SIGN_FORM bb ON bb.ID = cc.LABEL_SIGN_ID " +
                                " WHERE bb.SIGN_PATIENT_ID =:receivePeople AND bb.SIGN_STATE IN (:signState)))!= '0' " +
                                ")";
                    }
                }
                this.sysDao.getServiceDo().update(sqlUpdate,map);
                sql +=" ORDER BY NOTICE_CREATE_TIME DESC ";
                return sysDao.getServiceDo().findSqlMapRVo(sql,map,AppNoticeListEntity.class,qvo);
            }else{

                sqlUpdate = "UPDATE APP_NOTICE t SET t.NOTICE_READ = :readType " +
                        "WHERE t.NOTICE_TYPE = :noticeType " +
                        "AND t.NOTICE_RECEIVE_PEOPLE =:receivePeople ";

                sql = "SELECT\n" +
                        "\tID id,\n" +
                        "\tNOTICE_TYPE noticeType,\n" +
                        "\tNOTICE_TITLE noticeTitle,\n" +
                        "\tNOTICE_CONTENT noticeContent,\n" +
                        "NOTICE_CREATE_PEOPLE createId," +
                        "\tNOTICE_CREATE_PEOPLE noticeCreateName,\n" +
                        "NOTICE_RECEIVE_PEOPLE receiveId," +
                        "\tNOTICE_RECEIVE_PEOPLE noticeReceiveName,\n" +
                        "\tNOTICE_CREATE_TIME noticeCreateTime,\n" +
                        "NOTICE_READ noticeState," +
                        "\tNOTICE_FOREIGN noticeForEginKey," +
                        "NOTICE_MTYPE noticeMtype," +
                        "'' srtState\n" +
                        "FROM\n" +
                        "\tAPP_NOTICE WHERE 1=1  ";
                sql += " AND NOTICE_RECEIVE_PEOPLE = :receivePeople  AND NOTICE_TYPE = :noticeType ";
                this.sysDao.getServiceDo().update(sqlUpdate,map);
                sql +=" ORDER BY NOTICE_CREATE_TIME DESC ";
                return sysDao.getServiceDo().findSqlMapRVo(sql,map,AppNoticeListEntity.class,qvo);
            }
        }else{
            sqlUpdate = "UPDATE APP_NOTICE t SET t.NOTICE_READ = :readType " +
                    "WHERE t.NOTICE_TYPE = :noticeType " +
                    "AND t.NOTICE_RECEIVE_PEOPLE =:receivePeople ";

            sql ="SELECT\n" +
                    "\tID id,\n" +
                    "\tNOTICE_TYPE noticeType,\n" +
                    "\tNOTICE_TITLE noticeTitle,\n" +
                    "\tNOTICE_CONTENT noticeContent,\n" +
                    "NOTICE_CREATE_PEOPLE createId," +
                    "\tNOTICE_CREATE_PEOPLE noticeCreateName,\n" +
                    "NOTICE_RECEIVE_PEOPLE receiveId," +
                    "\tNOTICE_RECEIVE_PEOPLE noticeReceiveName,\n" +
                    "\tNOTICE_CREATE_TIME noticeCreateTime,\n" +
                    "NOTICE_READ noticeState," +
                    "\tNOTICE_FOREIGN noticeForEginKey," +
                    "NOTICE_MTYPE noticeMtype ," +
                    "'' srtState\n" +
                    "FROM\n" +
                    "\tAPP_NOTICE WHERE 1=1 ";
            sql += " AND NOTICE_RECEIVE_PEOPLE =:receivePeople AND NOTICE_TYPE = :noticeType ";
            this.sysDao.getServiceDo().update(sqlUpdate,map);
            sql +=" ORDER BY NOTICE_CREATE_TIME DESC ";
            return sysDao.getServiceDo().findSqlMapRVo(sql,map,AppNoticeListEntity.class,qvo);
        }
    }

    @Override
    public NoticeViewEntity findNoticeView(String id) throws Exception{
        Map<String, Object>map = new HashMap<String,Object>();
        map.put("id",id);
        String sql = "SELECT\n" +
                "\tID id,\n" +
                "\tNOTICE_TITLE noticeTitle,\n" +
                "\tNOTICE_CREATE_TIME noticeCreateTime,\n" +
                "\tNOTICE_CONTENT noticeContent \n" +
                "FROM\n" +
                "\tAPP_NOTICE WHERE 1=1 ";
        sql += " AND ID = :id ORDER BY NOTICE_CREATE_TIME DESC ";
        List<NoticeViewEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,NoticeViewEntity.class);
        if(ls != null) {
            return ls.get(0);
        }
        return null;
    }

    @Override
    public List<AppNotice> findNoticeAll(AppNoticeCountQvo qvo) throws Exception{
        Map<String, Object>map = new HashMap<String,Object>();
        map.put("receivePeople",qvo.getDrPatientId());
        map.put("noticeType",qvo.getNoticeType());
        String sql = "SELECT * FROM APP_NOTICE WHERE 1=1 ";
        sql += " AND NOTICE_RECEIVE_PEOPLE = :receivePeople AND NOTICE_TYPE = :noticeType ORDER BY NOTICE_CREATE_TIME DESC ";
        return sysDao.getServiceDo().findSqlMap(sql,map,AppNotice.class,qvo);
    }

    @Override
    public AppNoticeListEntity findNoticeEntityById(String id) throws Exception{
        Map<String, Object>map = new HashMap<String,Object>();
        map.put("id",id);
        String sql = "SELECT\n" +
                "\tID id,\n" +
                "\tNOTICE_TYPE noticeType,\n" +
                "\tNOTICE_TITLE noticeTitle,\n" +
                "\tNOTICE_CONTENT noticeContent,\n" +
                "\tNOTICE_CREATE_PEOPLE noticeCreateName,\n" +
                "\tNOTICE_RECEIVE_PEOPLE noticeReceiveName,\n" +
                "\tNOTICE_CREATE_TIME noticeCreateTime,\n" +
                "\tNOTICE_FOREIGN noticeForEginKey\n" +
                "FROM\n" +
                "\tAPP_NOTICE WHERE 1=1 ";
        sql += " AND ID = :id";
        List<AppNoticeListEntity> ls =  sysDao.getServiceDo().findSqlMapRVo(sql,map,AppNoticeListEntity.class);
        if( ls != null && ls.size() >0){
            return ls.get(0);
        }
        return null;
    }

    @Override
    public List<AppNoticeCountEntity> findReadNoticeCount(String drPatientId,String type) throws Exception{
        Map<String, Object>map = new HashMap<String,Object>();
        map.put("receivePeople",drPatientId);
        map.put("readType",NoticesReadType.WEIDU.getValue());
        String sql = "";
        List<AppNoticeCountEntity> lss = new ArrayList<AppNoticeCountEntity>();
        if("1".equals(type)){
            AppSignForm form = this.sysDao.getAppSignformDao().findSignFormByUserState(drPatientId);
            String receive = null;
            if(form == null){
                receive = " a.NOTICE_RECEIVE_PEOPLE = :receivePeople ";
            }else{
                map.put("receivePeopleOrMange","1");
                String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
                map.put("signState", signStates);
                receive = " a.NOTICE_RECEIVE_PEOPLE IN (:receivePeople , :receivePeopleOrMange ) ";
                receive += "OR a.ID IN (" +
                        "SELECT b.NOTICE_ID FROM APP_NOTICE_RECEIVE b WHERE find_in_set(b.NOTICE_RECEIVE_PEOPLE,(" +
                        "SELECT GROUP_CONCAT(cc.LABEL_VALUE) FROM APP_LABEL_GROUP cc INNER JOIN APP_SIGN_FORM bb ON bb.ID = cc.LABEL_SIGN_ID " +
                        " WHERE bb.SIGN_PATIENT_ID =:receivePeople AND bb.SIGN_STATE IN (:signState)))!= '0' " +
                        ")";
            }
            sql = "SELECT " +
                    "a.NOTICE_TYPE noticeType," +
                    "'"+drPatientId+"' people,"+
                    "'' noticePeople," +
                    "'' noticeName," +
                    "'' noticeCount," +
                    "'' title," +
                    "'' content," +
                    "'' time " +
                    "FROM " +
                    "APP_NOTICE a " +
                    "WHERE  " + receive;
        }else{
            sql = "SELECT " +
                    "a.NOTICE_TYPE noticeType," +
                    "'"+drPatientId+"' people,"+
                    "'' noticeName," +
                    "a.NOTICE_RECEIVE_PEOPLE noticePeople," +
                    "'' noticeCount," +
                    "'' title," +
                    "'' content," +
                    "'' time " +
                    "FROM " +
                    "APP_NOTICE a " +
                    "WHERE a.NOTICE_RECEIVE_PEOPLE = :receivePeople  ";
        }
        sql += "GROUP BY a.NOTICE_TYPE";
        List<AppNoticeCountEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppNoticeCountEntity.class);

        return ls;
    }


    @Override
    public void updateNoticeRead(String patientId,String type) throws Exception{
        Map<String, Object>map = new HashMap<String,Object>();
        map.put("readType",NoticesReadType.YIDU.getValue());
        map.put("receivePeople",patientId);
        map.put("noticeType",type);
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState",signStates);
        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,patientId);
        String sqlUpdate = "";
        if(user!=null){
            sqlUpdate = "UPDATE APP_NOTICE t SET t.NOTICE_READ = :readType WHERE t.NOTICE_TYPE = :noticeType " +
                    "AND t.NOTICE_RECEIVE_PEOPLE IN (:receivePeople,'1') " +
                    "OR t.ID IN (" +
                    "SELECT b.NOTICE_ID FROM APP_NOTICE_RECEIVE b WHERE find_in_set(b.NOTICE_RECEIVE_PEOPLE,(" +
                    "SELECT GROUP_CONCAT(cc.LABEL_VALUE) FROM APP_LABEL_GROUP cc INNER JOIN APP_SIGN_FORM bb ON bb.ID = cc.LABEL_SIGN_ID " +
                    " WHERE bb.SIGN_PATIENT_ID =:receivePeople AND bb.SIGN_STATE IN(:signState)))!= '0' " +
                    ")";
        }else{
            sqlUpdate = "UPDATE APP_NOTICE t SET t.NOTICE_READ = :readType WHERE t.NOTICE_TYPE = :noticeType " +
                    "AND t.NOTICE_RECEIVE_PEOPLE =:receivePeople= ";
        }
        this.sysDao.getServiceDo().update(sqlUpdate,map);
    }

    @Override
    public void addNoticeTg(AppNoticeCountQvo qvo) throws Exception {
        //查询消息表，获取患者特殊消息数据集合
        Map<String,Object> map = new HashMap<String,Object>();
        //查询患者签约区域编号
        map.put("patientId",qvo.getDrPatientId());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState",signStates);
        String sql = " SELECT a.SIGN_AREA_CODE FROM APP_SIGN_FORM a WHERE a.SIGN_PATIENT_ID =:patientId AND a.SIGN_STATE IN (:signState) ";
        List<Map> ls=sysDao.getServiceDo().findSqlMap(sql,map);
        String areaCode = "";
        //有签约的做消息的插入
        if(ls != null && ls.size() >0) {
            areaCode = ls.get(0).get("SIGN_AREA_CODE").toString();
            String sqll = "SELECT a.* FROM APP_NOTICE a WHERE a.NOTICE_RECEIVE_PEOPLE='1' ";
            if(StringUtils.isNotBlank(areaCode)){
                String[] areas = new String[]{areaCode.substring(0,2)+"0000000000",areaCode.substring(0,4)+"00000000",areaCode.substring(0,6)+"000000",areaCode.substring(0,9)+"000"};
                map.put("areas",areas);
                sqll += " AND a.NOTICE_CREATE_PEOPLE IN :areas ";
            }
            sql +=  "OR a.ID IN (" +
                    "SELECT b.NOTICE_ID FROM APP_NOTICE_RECEIVE b WHERE find_in_set(b.NOTICE_RECEIVE_PEOPLE,(" +
                    "SELECT GROUP_CONCAT(cc.LABEL_VALUE) FROM APP_LABEL_GROUP cc INNER JOIN APP_SIGN_FORM bb ON bb.ID = cc.LABEL_SIGN_ID " +
                    " WHERE bb.SIGN_PATIENT_ID =:receivePeople AND bb.SIGN_STATE IN (:signState)))!= '0' " +
                    ")";
            List<AppNotice> lss = sysDao.getServiceDo().findSqlMap(sqll,map,AppNotice.class);
            if(lss!=null && lss.size()>0){
                for(AppNotice ll:lss){
                    //查询消息表中是否存在此消息
                    map.put("content",ll.getNoticeContent());
                    map.put("time",ll.getNoticeCreateTime());
                    map.put("title",ll.getNoticeTitle());
                    map.put("type",ll.getNoticeType());
                    map.put("userId",qvo.getDrPatientId());
                    map.put("fore",ll.getNoticeForeign());
                    String ssq = " SELECT * FROM APP_NOTICE WHERE NOTICE_CONTENT =:content AND NOTICE_CREATE_TIME =:time" +
                            " AND NOTICE_TITLE =:title AND NOTICE_RECEIVE_PEOPLE =:userId AND NOTICE_FOREIGN =:fore ";
                    List<AppNotice> lls = sysDao.getServiceDo().findSqlMap(ssq,map,AppNotice.class);
                    if(lls==null || lls.size()==0){
                        AppNotice notice = new AppNotice();
                        notice.setNoticeContent(ll.getNoticeContent());
                        notice.setNoticeCreateTime(ll.getNoticeCreateTime());
                        notice.setNoticeForeign(ll.getNoticeForeign());
                        notice.setNoticeRead(NoticesReadType.WEIDU.getValue());
                        notice.setNoticeReceivePeople(qvo.getDrPatientId());
                        notice.setNoticeTitle(ll.getNoticeTitle());
                        notice.setNoticeType(ll.getNoticeType());
                        sysDao.getServiceDo().add(notice);
                    }
                }
            }
        }

    }

    /**
     * 发送体征预警消息提醒
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String fsTzxxToPatient(AppSignsWarningRecordQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("SRT_CODE",qvo.getCode());
        String sql = "SELECT * FROM APP_SIGNS_RECORD_TABLE WHERE 1=1";
        sql += " AND SRT_CODE = :SRT_CODE";
        if(StringUtils.isNotBlank(qvo.getPatientId())){
            String[] patients = qvo.getPatientId().split(";");
            map.put("SRT_PATIENT_ID",patients);
            sql += " AND SRT_PATIENT_ID IN :SRT_PATIENT_ID";
        }
        List<AppNotice> notices = sysDao.getServiceDo().loadByPk(AppNotice.class,"noticeForeign",qvo.getCode());
        if(notices !=null && notices.size()>0){

        }
        List<AppSignsRecordTable> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignsRecordTable.class);
        if(list!=null && list.size()>0){
            for(AppSignsRecordTable ll:list){
                if("0".equals(ll.getSrtState())){
                    sysDao.getAppNoticeDao().addNotices("体征指标预警",
                            "您好，您已连续"+ll.getSrtDayNum()+"天未进行"+ll.getSrtDisName()+"测量，请及时进行自我健康监测。("+ll.getDrName()+":"+ll.getDrWorkName()+")",
                            NoticesType.JKJCYCTX.getValue(),ll.getSrtDrId(),ll.getSrtPatientId(),ll.getId(), DrPatientType.PATIENT.getValue());
                    ll.setSrtState("1");
                    sysDao.getServiceDo().modify(ll);
                }
            }
            return "true";
        }else{
            return "暂无数据";
        }
    }

    @Override
    public List<AppSignsRecordTable> findTzxxPeople(AppSignsWarningRecordQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("SRT_CODE",qvo.getCode());
        String sql = "SELECT * FROM APP_SIGNS_RECORD_TABLE WHERE 1=1";
        sql += " AND SRT_CODE=:SRT_CODE";
        List<AppSignsRecordTable> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignsRecordTable.class,qvo);
        return list;
    }

    @Override
    public void addOnlyNotices(String title, String content, String type, String createPeople, String receivePeople, String forEginId, String typeUser) throws Exception{
        AppNotice v = new AppNotice();
        v.setNoticeContent(content);
        v.setNoticeTitle(title);
        v.setNoticeCreatePeople(createPeople);
        v.setNoticeCreateTime(Calendar.getInstance());
        if(StringUtils.isNotBlank(type)){
            if(type.indexOf(",")>-1){
                String[] ss = type.split(",");
                v.setNoticeType(ss[0]);
                v.setNoticeMtype(ss[1]);
            }else{
                v.setNoticeType(type);
            }
        }
        v.setNoticeReceivePeople(receivePeople);
        v.setNoticeRead(NoticesReadType.WEIDU.getValue());
        v.setNoticeForeign(forEginId);
        sysDao.getServiceDo().add(v);
    }
}
