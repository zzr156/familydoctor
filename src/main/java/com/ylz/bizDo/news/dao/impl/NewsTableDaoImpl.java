package com.ylz.bizDo.news.dao.impl;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppHealthEntiry;
import com.ylz.bizDo.jtapp.commonVo.AppHealthEducationQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrHealthListEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrHealthListQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrQvo;
import com.ylz.bizDo.jtapp.drVo.AppHealthToQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppHealthListEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPatientHealthListQvo;
import com.ylz.bizDo.news.dao.NewsTableDao;
import com.ylz.bizDo.news.po.NewsTable;
import com.ylz.bizDo.news.vo.NewsMsgQvo;
import com.ylz.bizDo.news.vo.NewsTablePo;
import com.ylz.bizDo.news.vo.NewsTableQvo;
import com.ylz.bizDo.smjq.po.NcdNewTable;
import com.ylz.bizDo.smjq.smVo.AppNcdHEQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
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

@Service("newsTableDao")
@Transactional(rollbackForClassName={"Exception"})
public class NewsTableDaoImpl implements NewsTableDao {

    @Autowired
    private SysDao sysDao;

    /**
     * 查询新闻发布内容
     *
     * @return
     */
    public List<NewsTablePo> findAllNewsTable(NewsTableQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "SELECT t.ID as id,t.TABLE_TITLE as tableTitle,t.TABLE_CONTENT as tableContent," +
                " t.TABLE_CJRXM as tableCjrxm,t.TABLE_CJSJ as strTableCjsj," +
                " t.TABLE_BROWSE as tableBrowse,t.TABLE_STATE as strTableState," +
                " t.TABLE_HEALTH_TYPE as strTableType FROM NEWS_TABLE T LEFT JOIN news_type a ON t.table_type= a.id WHERE 1=1 ";
        if (StringUtils.isNotBlank(qvo.getTableNewsTitle())) {
            map.put("TABLETITLE", "%" + qvo.getTableNewsTitle() + "%");
            sql += " AND T.TABLE_TITLE LIKE :TABLETITLE ";
        }
        if (StringUtils.isNotBlank(qvo.getTableNewsType())) {
            map.put("TABLETYPE", qvo.getTableNewsType());
            sql += " AND T.TABLE_HEALTH_TYPE = :TABLETYPE ";
        }
        if (StringUtils.isNotBlank(qvo.getTableStartCjsj())) {
            map.put("TABLESTARTCJSJ", qvo.getTableStartCjsj());
            sql += " AND T.TABLE_CJSJ >= :TABLESTARTCJSJ ";
        }
        if (StringUtils.isNotBlank(qvo.getTableEndCjsj())) {
//			String end = ExtendDateUtil.addDate(qvo.getTableEndCjsj(), ExtendDateType.DAYS.getValue(), 1);
            map.put("TABLEENDCJSJ", qvo.getTableEndCjsj());
            sql += " AND T.TABLE_CJSJ <= :TABLEENDCJSJ ";
        }
        if (qvo.isTableImageState()) {
            sql += " AND T.TABLE_IMAGEURL IS NOT NULL ";
        }
        if (StringUtils.isNotBlank(qvo.getTableNewsTypeNum())) {
            map.put("TYPENUM", qvo.getTableNewsTypeNum());
            sql += " AND T.type_num=:TYPENUM ";
        }
        if(StringUtils.isNotBlank(qvo.getDqState())){
            map.put("dqState",qvo.getDqState());
            sql += " AND T.TABLE_PUSH_STATE =:dqState ";
        }
        if (StringUtils.isNotBlank(qvo.getSort())) {
            if (qvo.getSort().equals("strTableCjsj")) {
                sql += " ORDER BY T.TABLE_CJSJ " + qvo.getOrder();
            }
        } else {
            sql += " ORDER BY T.TABLE_CJSJ DESC";
        }
        return sysDao.getServiceDo().findSqlMapRVo(sql, map, NewsTablePo.class, qvo);
    }

    public List<NewsTablePo> findAllNewsTable(NewsMsgQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "SELECT t.ID as id,t.TABLE_TITLE as tableTitle,t.TABLE_CONTENT as tableContent," +
                " t.TABLE_CJRXM as tableCjrxm,t.TABLE_CJSJ as strTableCjsj," +
                " t.TABLE_BROWSE as tableBrowse,t.TABLE_STATE as strTableState," +
                " t.TABLE_TYPE as strTableType FROM NEWS_TABLE T LEFT JOIN news_type a ON t.table_type= a.id WHERE 1=1 ";
        sql += " ORDER BY T.TABLE_CJSJ DESC";
        return sysDao.getServiceDo().findSqlMapRVo(sql, map, NewsTablePo.class, qvo);
    }

    public int findNewsTableCount(String cjsjStart, String cjsjEnd) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cjsjStart", cjsjStart);
        map.put("cjsjEnd", cjsjEnd);
        String sql = " SELECT count(1)  FROM NEWS_TABLE a WHERE 1=1";
        sql += " AND a.table_cjsj >=:cjsjStart AND a.table_cjsj <=:cjsjEnd";
        return sysDao.getServiceDo().gteSqlCount(sql, map);
    }

    @Override
    public AppHealthEntiry findById(String id)  throws Exception {//查看健康教育详细内容
        //增加浏览次数
        NewsTable news = (NewsTable) this.sysDao.getServiceDo().find(NewsTable.class, id);
        if (news != null) {
            news.setTableBrowse(String.valueOf(Integer.parseInt(news.getTableBrowse()) + 1));
            this.sysDao.getServiceDo().modify(news);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableId", id);
        String sql = "SELECT a.ID id," +
                "a.HED_TITLE title," +
                "date_format(a.HED_CREATE_TIME,'%Y-%c-%d %H:%i:%s') time," +
                "a.HED_TYPE type," +
                "a.HED_CONTENT content," +
                "a.HED_IMAGE_URL imageUrl," +
                "a.HED_DR_NAME drName" +
                " FROM APP_USER_HEALTHED a WHERE 1=1";
        sql += " AND a.ID = :tableId";
        List<AppHealthEntiry> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppHealthEntiry.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AppHealthListEntity> findByUserId(AppPatientHealthListQvo qvo)  throws Exception {//个人查询健康教育列表
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "";
        String sql1 = "";
        if (StringUtils.isNotBlank(qvo.getPatientId())) {
            map.put("userId", qvo.getPatientId());
            sql = "SELECT\n" +
                    "\ta.HED_TITLE title,\n" +
                    "\ta.HED_IMAGE_URL imageUrl,\n" +
                    "\ta.HED_DR_ID drId," +
                    "a.HED_ID hedId,\n" +
                    "'' browse," +
                    "'' transmit," +
                    "'' enshrine," +
                    "\ta.HED_DR_NAME drName,\n" +
                    "\t(SELECT MEM_WORK_TYPE FROM APP_TEAM_MEMBER WHERE MEM_TEAMID = a.HED_TEAM_ID AND MEM_DR_ID =a.HED_DR_ID) drType," +
                    "'' drTypeName,\n" +
                    "\ta.HED_CREATE_TIME time,\n" +
                    "\ta.ID id ," +
                    "\ta.HED_CONTENT content,\n" +
                    "\ta.HED_TYPE type,\n" +
                    "a.HED_USER_ID toUserId," +
                    "a.HED_USER_NAME toUserName," +
                    "\t'' typeName\n" +
                    "FROM\n" +
                    "\tAPP_USER_HEALTHED a\n" +
                    "JOIN APP_DR_USER b ON a.HED_DR_ID = b.ID WHERE 1=1";

            List<AppSignForm> list = sysDao.getServiceDo().loadByPk(AppSignForm.class,"signPatientId",qvo.getPatientId());
            boolean flag = false;
            if(list!=null && list.size()>0){//判断签约状态
                for(AppSignForm ls:list){
                    if(SignFormType.YQY.getValue().equals(ls.getSignState())||SignFormType.YUQY.getValue().equals(ls.getSignState())){
                        flag= true;
                        map.put("signDate",ExtendDate.getYMD_h_m_s(ls.getSignFromDate()));
                    }
                }
            }
            if(flag){//已签约
                sql = "SELECT\n" +
                        "\ta.HED_TITLE title,\n" +
                        "\ta.HED_IMAGE_URL imageUrl,\n" +
                        "\ta.HED_DR_ID drId," +
                        "a.HED_ID hedId,\n" +
                        "null browse," +
                        "null transmit," +
                        "null enshrine," +
                        "\ta.HED_DR_NAME drName,\n" +
                        "\t(SELECT MEM_WORK_TYPE FROM APP_TEAM_MEMBER WHERE MEM_TEAMID = a.HED_TEAM_ID AND MEM_DR_ID =a.HED_DR_ID) drType," +
                        "null drTypeName,\n" +
                        "\ta.HED_CREATE_TIME time,\n" +
                        "\ta.ID id ," +
                        "\ta.HED_CONTENT content,\n" +
                        "\ta.HED_TYPE type,\n" +
                        "a.HED_USER_ID toUserId," +
                        "a.HED_USER_NAME toUserName," +
                        "\tnull typeName\n" +
                        "FROM\n" +
                        "\tAPP_USER_HEALTHED a\n" +
                        "JOIN APP_DR_USER b ON a.HED_DR_ID = b.ID WHERE 1=1";

                sql += " AND (a.HED_USER_ID=:userId OR (a.HED_USER_ID =:xtUserId AND a.HED_CREATE_TIME>=:signDate) )";
                String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
                map.put("signState",signStates);
                sql1 = "SELECT " +
                        "a.HED_TITLE title," +
                        "a.HED_IMAGE_URL imageUrl," +
                        "a.HED_DR_ID drId," +
                        "a.HED_ID hedId," +
                        "null browse," +
                        "null transmit," +
                        "null enshrine," +
                        "null drName," +
                        "null drType," +
                        "null drTypeName," +
                        "a.HED_CREATE_TIME time," +
                        "a.ID id ," +
                        "a.HED_CONTENT content," +
                        "a.HED_TYPE type," +
                        "a.HED_USER_ID toUserId," +
                        "a.HED_USER_NAME toUserName," +
                        "null typeName " +
                        "FROM " +
                        "APP_USER_HEALTHED a " +
                        "WHERE a.ID IN (SELECT b.NOTICE_FOREIGN FROM APP_NOTICE_RECEIVE b WHERE b.NOTICE_CREATE_TIME >= :signDate AND (find_in_set(b.NOTICE_RECEIVE_PEOPLE,(" +
                        "SELECT GROUP_CONCAT(cc.LABEL_VALUE) FROM APP_LABEL_GROUP cc INNER JOIN APP_SIGN_FORM bb ON bb.ID = cc.LABEL_SIGN_ID " +
                        "WHERE bb.SIGN_PATIENT_ID =:userId AND bb.SIGN_STATE IN (:signState)))!='0'))";
            }else{
                sql +=" AND a.HED_USER_ID=:userId";
            }

        }else if (StringUtils.isNotBlank(qvo.getDrId())) {
            map.put("drId", qvo.getDrId());
            sql = "SELECT\n" +
                    "\ta.HED_TITLE title,\n" +
                    "\ta.HED_IMAGE_URL imageUrl,\n" +
                    "\ta.HED_DR_ID drId,\n" +
                    "a.HED_ID hedId,\n" +
                    "'' browse," +
                    "'' transmit," +
                    "'' enshrine," +
                    "\ta.HED_DR_NAME drName,\n" +
                    "\t(SELECT MEM_WORK_TYPE FROM APP_TEAM_MEMBER WHERE MEM_TEAMID = a.HED_TEAM_ID AND MEM_DR_ID =a.HED_DR_ID) drType," +
                    "'' drTypeName,\n" +
                    "\ta.HED_CREATE_TIME time,\n" +
                    "\ta.ID id," +
                    "a.HED_CONTENT content," +
                    "\ta.HED_TYPE type," +
                    "a.HED_USER_ID toUserId," +
                    "a.HED_USER_NAME toUserName," +
                    "\t'' typeName\n" +
                    "FROM\n" +
                    "\tAPP_USER_HEALTHED a\n" +
                    "JOIN APP_DR_USER b ON a.HED_DR_ID = b.ID WHERE a.HED_DR_ID=:drId";

        }

        if (StringUtils.isNotBlank(qvo.getType())) {
            map.put("type", qvo.getType());
            sql += " AND a.HED_TYPE = :type";
        }
        if (StringUtils.isNotBlank(qvo.getDate())) {
            map.put("startDate", qvo.getDate() + "-01-01 00:00:00");
            map.put("endDate", qvo.getDate() + "-12-31 23:59:59");
            sql += " AND a.HED_CREATE_TIME >:startDate";
            sql += " AND a.HED_CREATE_TIME <:endDate";

        }
        if(StringUtils.isNotBlank(sql1)){
            map.put("xtUserId","1");
           sql = " SELECT * FROM ("+sql +" UNION "+sql1+") ab ORDER BY ab.time DESC";
        }else{
            sql += " ORDER BY a.HED_CREATE_TIME DESC";
        }

        List<AppHealthListEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppHealthListEntity.class);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public List<AppHealthListEntity> findByIsEnshrine(AppPatientHealthListQvo qvo)  throws Exception {//个人查询收藏健康教育列表
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", qvo.getPatientId());
        String sql = "SELECT " +
                "a.ID AS id," +
                "a.TABLE_TITLE AS title," +
                "a.TABLE_HEALTH_TYPE AS type," +
                "a.TABLE_IMAGEURL imageUrl " +
                "FROM NEWS_TABLE a," +
                "APP_HEALTH_ENSHRINE b WHERE 1=1";
        sql += " AND b.HEN_USER_ID =:userId AND b.HEN_HEALTH_ID=a.ID";
        sql += " ORDER BY a.TABLE_CJSJ DESC";
        List<AppHealthListEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppHealthListEntity.class);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public List<AppDrHealthListEntity> findDrByEnshrine(AppDrHealthListQvo qvo)  throws Exception {//医生查询收藏健康教育列表
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", qvo.getId());
        map.put("isEnshrine", qvo.getIsEnshrine());
        String sql = "SELECT a.ID AS id,a.TABLE_TITLE AS title,a.TABLE_HEALTH_TYPE AS type,a.TABLE_IMAGEURL imageUrl FROM NEWS_TABLE a,APP_HEALTH_ENSHRINE b WHERE 1=1";
        sql += " AND b.HEN_USER_ID =:userId AND b.HEN_HEALTH_ID=a.ID";
        sql += " ORDER BY a.TABLE_CJSJ DESC";
        List<AppDrHealthListEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppDrHealthListEntity.class);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 医生查询所有健康教育模板
     *
     * @return
     * @throws DaoException
     */
    @Override
    public List<AppDrHealthListEntity> findAll(AppDrHealthListQvo qvo)  throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "SELECT" +
                " a.ID id," +
                "a.TABLE_TITLE title," +
                "a.TABLE_IMAGEURL imageUrl," +
                "a.TABLE_HEALTH_TYPE type," +
                "a.TABLE_BROWSE browse," +
                "a.TABLE_ENSHRINE enshrine," +
                "date_format(a.TABLE_CJSJ, '%Y-%c-%d %H:%i:%s') time," +
                "a.TABLE_TRANSMIT transmit," +
                "a.TABLE_CJRID drId," +
                "'' drName," +
                "'' drTypeName" +
                " FROM NEWS_TABLE a WHERE 1=1";
        if (StringUtils.isNotBlank(qvo.getContent())) {//根据标题或内容相匹配查询健康教育列表
            map.put("content", "%" + qvo.getContent() + "%");
            sql += " AND a.TABLE_TITLE LIKE :content";
            sql += " OR a.TABLE_CONTENT LIKE :content";
        }
        if (StringUtils.isNotBlank(qvo.getId())) {
            map.put("drId", qvo.getId());
            sql += " AND a.TABLE_CJRID =:drId";
        }
        sql += " ORDER BY a.TABLE_CJSJ DESC";
        List<AppDrHealthListEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppDrHealthListEntity.class);
        if (ls != null && ls.size() > 0) {
            return ls;
        }
        return null;
    }

    @Override
    public void saveHealth(AppHealthEducationQvo qvo) throws Exception {
        NewsTable news = new NewsTable();
        AppDrUser dr = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
        AppHospDept hosp = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,dr.getDrHospId());
            if (StringUtils.isBlank(qvo.getNewId())) {
                news.setTableHealthType(qvo.getType());
                news.setTableTitle(qvo.getTitle());
                news.setTableContent(qvo.getContent());
                if (StringUtils.isNotBlank(qvo.getImageUrl())) {
//                    String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"), PropertiesUtil.getConfValue("filePictureYz"), qvo.getImageName());
//                    FileUtils.decoderBase64File(qvo.getImageUrl(), PropertiesUtil.getConfValue("filePicture") + path);
//                    news.setTableImageUrl(path);
                    Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(),CommonShortType.YISHENG.getValue());
                    news.setTableImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
                }
                Calendar cal = Calendar.getInstance();
                news.setTableCjsj(cal);
                news.setTableCjrid(qvo.getDrId());
                news.setTableCjrxm(qvo.getDrName());
                news.setTableHospId(qvo.getHospId());
                news.setTableType("0");
                this.sysDao.getServiceDo().add(news);

            } else {
                news = (NewsTable) sysDao.getServiceDo().find(NewsTable.class, qvo.getNewId());
            }
            if (StringUtils.isNotBlank(qvo.getPatientId())) {
                String[] patientIds = qvo.getPatientId().split(";");
                int num = 0;
                for (String patientId : patientIds) {
                    num++;
                    AppUserHealthED table = new AppUserHealthED();
                    table.setHedId(news.getId());
                    table.setHedUserId(patientId);
                    table.setHedDrId(qvo.getDrId());
                    if(hosp!=null){
                        table.setHedHospId(hosp.getId());
                        table.setHedAreaCode(hosp.getHospAreaCode());
                    }
                    table.setHedCreateTime(Calendar.getInstance());
                    table.setHedId(news.getId());
                    table.setHedType(news.getTableHealthType());
                    table.setHedDrName(qvo.getDrName());
                    table.setHedContent(news.getTableContent());
                    AppPatientUser user = (AppPatientUser) this.sysDao.getServiceDo().find(AppPatientUser.class, patientId);
                    if (user != null) {
                        table.setHedUserName(user.getPatientName());
                    }
//                    List<AppSignForm> form = this.sysDao.getServiceDo().loadByPk(AppSignForm.class, "signPatientId", patientId);
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(patientId);
                    if (form != null) {
                        table.setHedTeamId(form.getSignTeamId());
                        table.setHedTeamName(form.getSignTeamName());
                    }
                    table.setHedTitle(news.getTableTitle());
                    table.setHedImageUrl(news.getTableImageUrl());
                    this.sysDao.getServiceDo().add(table);

                    //推送健康资讯(发消息提醒) 您新收到一笔健康资讯
                    sysDao.getAppNoticeDao().addNotices("健康教育", "您新收到一笔健康教育，请注意查看。",
                            NoticesType.JKZX.getValue(), qvo.getDrId(), patientId, table.getId(), DrPatientType.PATIENT.getValue());
                    //履约数据
                    PerformanceDataQvo qqvo = new PerformanceDataQvo();
                    //患者信息
                    qqvo.setPerName(user.getPatientName());
                    qqvo.setPerIdno(user.getPatientIdno());
                    qqvo.setPerForeign(table.getId());
                    if(StringUtils.isNotBlank(user.getPatientCity())){
                        CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                        if(p != null){
                            String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                            if(value != null) {
                                qqvo.setPerArea(value.getCodeTitle());
                            }
                        }
                    }
                    qqvo.setPerSource("2");//app
                    //医院信息
                    qqvo.setHospId(hosp.getId());
                    qqvo.setHospName(hosp.getHospName());
                    qqvo.setHospAreaCode(hosp.getHospAreaCode());
                    qqvo.setHospAddress(hosp.getHospAddress());
                    qqvo.setHospTel(hosp.getHospTel());
                    //医生信息
                    qqvo.setDrId(dr.getId());
                    qqvo.setDrName(dr.getDrName());
                    qqvo.setDrAccount(dr.getDrAccount());
                    qqvo.setDrPwd(dr.getDrPwd());
                    qqvo.setDrGender(dr.getDrGender());
                    qqvo.setDrTel(dr.getDrTel());
                    qqvo.setPerType(PerformanceType.JKJY.getValue());
                    if(StringUtils.isNotBlank(qqvo.getPerArea())){
                        if(StringUtils.isNotBlank(qqvo.getPerType())){
//                            String result = null;
//                            if (qqvo.getPerArea().equals(AddressType.FZ.getValue())) {
//
//                            } else if (qqvo.getPerArea().equals(AddressType.QZ.getValue())) {
//                                result = "qz_";
//                            } else if (qqvo.getPerArea().equals(AddressType.ZZ.getValue())) {
//                                result = "zz_";
//                            } else if (qqvo.getPerArea().equals(AddressType.PT.getValue())) {
//                                result = "pt_";
//                            } else if (qqvo.getPerArea().equals(AddressType.NP.getValue())) {
//                                result = "np_";
//                            } else if (qqvo.getPerArea().equals(AddressType.SM.getValue())) {
//                                result = "sm_";
//                            }
//                            if(StringUtils.isNotBlank(result)){
//                                qqvo.setDrId(result+qqvo.getDrId());
//                                qqvo.setHospId(result+qqvo.getHospId());
//                            }
                            if(form != null){
                                String fwType = "";
                                String sermeal = "";//服务包id
                                fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                sermeal = form.getSignpackageid();
                                sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                            }
                        }
                    }
                }
                news.setTableTransmit(String.valueOf(Integer.parseInt(news.getTableTransmit()) + num));
                this.sysDao.getServiceDo().modify(news);

            }
    }

    /**
     * 查询健康教育模板列表
     *
     * @param qvo
     * @return
     * @throws DaoException
     */
    @Override
    public List<AppHealthEntiry> findList(AppDrQvo qvo)  throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("enshrineId", qvo.getEnshrineId());
        String sql = " SELECT a.Id id,a.TABLE_TITLE title,date_format(a.TABLE_CJSJ, '%Y-%c-%d %H:%i:%s') time," +
//                "(SELECT COUNT(1) FROM APP_HEALTH_ENSHRINE WHERE HEN_HEALTH_ID = a.ID AND HEN_USER_ID =:enshrineId ) enshrineState," +
                "a.TABLE_HEALTH_TYPE type,'' typeName,a.TABLE_CJRID drId,'' drTypeName,a.TABLE_IMAGEURL imageUrl,a.TABLE_TRANSMIT transmit,a.TABLE_CONTENT content," +
                "a.TABLE_ENSHRINE enshrine,a.TABLE_BROWSE browse,a.TABLE_CJRXM drName  FROM NEWS_TABLE a WHERE 1=1";
        if (StringUtils.isNotBlank(qvo.getUserId())) {
            map.put("userId", qvo.getUserId());
            sql += " AND a.TABLE_CJRID =:userId";
            sql += " AND a.TABLE_TYPE ='0' ";
        }
        if(StringUtils.isNotBlank(qvo.getXtmb())){
            map.put("xtmb",qvo.getXtmb());
            sql += " AND a.TABLE_TYPE = :xtmb";
        }

        if (StringUtils.isNotBlank(qvo.getContent())) {
            map.put("content", "%" + qvo.getContent() + "%");
            sql += " AND ( a.TABLE_TITLE LIKE :content OR a.TABLE_CONTENT LIKE :content)";
        }else  if(StringUtils.isBlank(qvo.getUserId()) && StringUtils.isBlank(qvo.getXtmb())){//不是查个人也不是查系统即查医院
            map.put("hospId",qvo.getHospId());
            sql +=" AND a.TABLE_HOSP_ID =:hospId";
            sql += " AND a.TABLE_TYPE='2' ";
        }
        if (StringUtils.isNotBlank(qvo.getHealthType())) {
            map.put("healthType", qvo.getHealthType());
            sql += " AND a.TABLE_HEALTH_TYPE = :healthType";
        }
        if(StringUtils.isNotBlank(qvo.getStartTime())){
            map.put("startTime",qvo.getStartTime());
            sql += " AND a.TABLE_CJSJ >=:startTime ";
        }
        if(StringUtils.isNotBlank(qvo.getEndTime())){
            map.put("endTime",qvo.getEndTime());
            sql += " AND a.TABLE_CJSJ <=:endTime ";
        }

        sql += " ORDER BY a.TABLE_CJSJ DESC";
        List<AppHealthEntiry> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppHealthEntiry.class);
        return ls;
    }

    /**
     * 新增公共健康教育模板
     *
     * @param qvo
     * @throws Exception
     */
    @Override
    public NewsTable addHealth(AppHealthEducationQvo qvo) throws Exception {
        NewsTable news = new NewsTable();
        news.setTableHealthType(qvo.getType());
        news.setTableTitle(qvo.getTitle());
        news.setTableContent(qvo.getContent());
        news.setTableType("2");
        if (StringUtils.isNotBlank(qvo.getImageUrl())) {
//            String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"), PropertiesUtil.getConfValue("filePictureYz"), qvo.getImageName());
//            FileUtils.decoderBase64File(qvo.getImageUrl(), PropertiesUtil.getConfValue("filePicture") + path);
//            news.setTableImageUrl(path);

            Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(),CommonShortType.YISHENG.getValue());
            news.setTableImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
        }
        Calendar cal = Calendar.getInstance();
        news.setTableCjsj(cal);
        if(StringUtils.isNotBlank(qvo.getDrId())){
            AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(qvo.getDrId());
            if(drUser!=null){
                news.setTableHospId(drUser.getDrHospId());
                news.setTableCjrxm(qvo.getDrName());
            }
        }
        news.setTableCjrid(qvo.getDrId());
        news.setTableTransmit("0");
        news.setTableEnshrine("0");
        news.setTableBrowse("0");
        this.sysDao.getServiceDo().add(news);
        return news;
    }

    /**
     * 修改健康教育模板
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppHealthEntiry modifyHealth(AppHealthEducationQvo qvo) throws Exception {
        NewsTable newsTable = (NewsTable) sysDao.getServiceDo().find(NewsTable.class, qvo.getNewId());
        if (StringUtils.isNotBlank(qvo.getContent())) {
            newsTable.setTableContent(qvo.getContent());
        }
        if (StringUtils.isNotBlank(qvo.getTitle())) {
            newsTable.setTableTitle(qvo.getTitle());
        }
        if (StringUtils.isNotBlank(qvo.getImageUrl())) {
//            String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"), PropertiesUtil.getConfValue("filePictureYz"), qvo.getImageName());
//            FileUtils.decoderBase64File(qvo.getImageUrl(), PropertiesUtil.getConfValue("filePicture") + path);
//            newsTable.setTableImageUrl(path);
            Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(),CommonShortType.YISHENG.getValue());
            newsTable.setTableImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
        }
        if (StringUtils.isNotBlank(qvo.getType())) {
            newsTable.setTableHealthType(qvo.getType());
        }
        sysDao.getServiceDo().modify(newsTable);
        /*Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",newsTable.getId());
        String sql = " SELECT a.Id id,a.TABLE_TITLE title,date_format(a.TABLE_CJSJ, '%Y-%c-%d %H:%i:%s') time," +
                "(CASE WHEN (SELECT count(*) num FROM APP_HEALTH_ENSHRINE b WHERE b.HEN_USER_ID =:enshrineId AND b.HEN_HEALTH_ID = a.ID ) = 0 THEN '0' " +
                "ELSE '1' END) enshrineState," +
                "a.TABLE_HEALTH_TYPE type,'' typeName,a.TABLE_CJRID drId,'' drTypeName,a.TABLE_IMAGEURL imageUrl,a.TABLE_TRANSMIT transmit,a.TABLE_CONTENT content," +
                "a.TABLE_ENSHRINE enshrine,a.TABLE_BROWSE browse,a.TABLE_CJRXM drName  FROM NEWS_TABLE a WHERE 1=1 AND a.ID =:id";
        List<AppHealthEntiry> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppHealthEntiry.class);
        if(ls!=null && ls.size()>0 ){
            return ls.get(0);
        }*/
        AppHealthEntiry table = new AppHealthEntiry();
        table.setEnshrine(newsTable.getTableEnshrine());
        table.setType(newsTable.getTableHealthType());
        table.setTransmit(newsTable.getTableTransmit());
        table.setTitle(newsTable.getTableTitle());
        table.setImageUrl(newsTable.getTableImageUrl());
        table.setTime(newsTable.getStrTableCjsj());
        table.setId(newsTable.getId());
        table.setDrId(newsTable.getTableCjrid());
        table.setDrName(newsTable.getTableCjrxm());
        table.setContent(newsTable.getTableContent());
        table.setBrowse(newsTable.getTableBrowse());
        table.setDrTypeName("");
        table.setTypeName("");
        return table;
    }

    /**
     * 根据权限发送健康教育
     * @param qvo
     */
    @Override
    public void fsHealthRole(AppHealthEducationQvo qvo)  throws Exception {
        String areaCode = "";
        NewsTable table = (NewsTable)sysDao.getServiceDo().find(NewsTable.class,qvo.getNewId());
        AppUserHealthED healthED = new AppUserHealthED();
        AppDrUser user = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
        if(user!=null){
            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getDrHospId());
            if(dept!=null){
                if(user.getDrRole().indexOf(AppRoleType.SHENG.getValue())!=-1){
                    areaCode = dept.getHospAreaCode().substring(0,2)+"0000000000";
                }else if(user.getDrRole().indexOf(AppRoleType.SHI.getValue())!=-1){
                    areaCode = dept.getHospAreaCode().substring(0,4)+"00000000";
                }else if(user.getDrRole().indexOf(AppRoleType.QU.getValue())!=-1){
                    areaCode = dept.getHospAreaCode().substring(0,6)+"000000";
                }else if(user.getDrRole().indexOf(AppRoleType.SHEQU.getValue())!=-1){
                    areaCode = dept.getHospAreaCode().substring(0,9)+"000";
                }else if(user.getDrRole().indexOf(AppRoleType.YISHENG.getValue())!=-1){
                    areaCode = user.getId();
                }
                healthED.setHedAreaCode(dept.getHospAreaCode());
                healthED.setHedHospId(dept.getId());
                healthED.setHedUserId("1");
                healthED.setHedDrName(user.getDrName());
                healthED.setHedContent(table.getTableContent());
                healthED.setHedType(table.getTableHealthType());
                healthED.setHedCreateTime(Calendar.getInstance());
                healthED.setHedDrName(user.getDrName());
                healthED.setHedDrId(user.getId());
                healthED.setHedImageUrl(table.getTableImageUrl());
                healthED.setHedTitle(table.getTableTitle());
                healthED.setHedId(table.getId());
                sysDao.getServiceDo().add(healthED);
                //推送健康资讯(发消息提醒) 您新收到一笔健康资讯
                sysDao.getAppNoticeDao().addNotices("健康教育", "您新收到一笔健康教育，请注意查看。",
                        NoticesType.JKZX.getValue(), areaCode, "1", healthED.getId(), null);
            }

        }

    }

    @Override
    public String fsHealthAll(AppHealthToQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("teamId",qvo.getTeamId());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("state", signStates);
        map.put("type",qvo.getLabelType());

//        String sql = "SELECT a.* FROM APP_PATIENT_USER a INNER JOIN APP_SIGN_FORM b on a.ID = b.SIGN_PATIENT_ID where b.SIGN_STATE= :state AND b.SIGN_TEAM_ID =:teamId";
//        if(StringUtils.isNotBlank(qvo.getFwrq())){
//            map.put("fwrq",qvo.getFwrq());
//            sql += "b.ID in (select c.* from APP_LABEL_GROUP c where c.LABEl_TYPE=:fwrq GROUP BY c.LABEL_SIGN_ID)";
//        }

        if(StringUtils.isNotBlank(qvo.getLabelValue())){
            String[] strs = qvo.getLabelValue().split(";");
            map.put("value",strs);
        }
        String sql = "";
        if(LabelManageType.FWRQ.getValue().equals(qvo.getLabelType())){
            sql ="SELECT c.* FROM APP_PATIENT_USER c WHERE c.ID IN \n" +
                    "(SELECT\n" +
                    "\ta.SIGN_PATIENT_ID\n" +
                    "FROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "WHERE\n" +
                    "\tb.LABEL_TYPE = :type AND a.SIGN_TEAM_ID = :teamId AND a.SIGN_STATE IN (:state) \n" +
                    "AND b.LABEL_VALUE IN (:value) )";
        }else if(LabelManageType.JBLX.getValue().equals(qvo.getLabelType())){
            sql ="SELECT c.* FROM APP_PATIENT_USER c WHERE c.ID IN \n" +
                    "(SELECT\n" +
                    "\ta.SIGN_PATIENT_ID\n" +
                    "FROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "INNER JOIN APP_LABEL_DISEASE b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "WHERE\n" +
                    "\tb.LABEL_TYPE = :type AND a.SIGN_TEAM_ID = :teamId AND a.SIGN_STATE IN (:state) \n" +
                    "AND b.LABEL_VALUE IN (:value) )";
        }else{
            sql = "SELECT c.* FROM APP_PATIENT_USER c WHERE c.ID IN\n"+
                    "(SELECT\n" +
                    "\ta.SIGN_PATIENT_ID\n" +
                    "FROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "WHERE\n" +
                    "\ta.SIGN_HEALTH_GROUP IN (:value) AND a.SIGN_TEAM_ID = :teamId AND a.SIGN_STATE IN (:state) \n" +
                    " )";
        }

        List<AppPatientUser> list = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class);
        if(list!=null&&list.size()>0){
            NewsTable news = (NewsTable) sysDao.getServiceDo().find(NewsTable.class,qvo.getNewsId());
            int num=0;
            AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
            for(AppPatientUser ls:list){
                num++;
                AppUserHealthED table = new AppUserHealthED();
                table.setHedId(news.getId());
                table.setHedUserId(ls.getId());
                table.setHedDrId(drUser.getId());
                if(dept!=null){
                    table.setHedHospId(dept.getId());
                    table.setHedAreaCode(dept.getHospAreaCode());
                }
                table.setHedCreateTime(Calendar.getInstance());
                table.setHedId(news.getId());
                table.setHedType(news.getTableHealthType());
                table.setHedDrName(drUser.getDrName());
                table.setHedContent(news.getTableContent());
                table.setHedUserName(ls.getPatientName());
//                List<AppSignForm> form = this.sysDao.getServiceDo().loadByPk(AppSignForm.class, "signPatientId", ls.getId());
                AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(ls.getId());
                if (form != null ) {
                    table.setHedTeamId(form.getSignTeamId());
                    table.setHedTeamName(form.getSignTeamName());
                }
                table.setHedTitle(news.getTableTitle());
                table.setHedImageUrl(news.getTableImageUrl());
                this.sysDao.getServiceDo().add(table);
                sysDao.getAppNoticeDao().addNotices("健康教育", "您新收到一笔健康教育，请注意查看。",
                        NoticesType.JKZX.getValue(), qvo.getDrId(), ls.getId(), table.getId(), DrPatientType.PATIENT.getValue());

                //履约数据
                PerformanceDataQvo qqvo = new PerformanceDataQvo();
                //患者信息
                qqvo.setPerName(ls.getPatientName());
                qqvo.setPerIdno(ls.getPatientIdno());
                qqvo.setPerForeign(table.getId());
                if(StringUtils.isNotBlank(ls.getPatientCity())){
                    CdAddress p = sysDao.getCdAddressDao().findByCode(ls.getPatientCity());
                    if(p != null){
                        String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                        if(value != null) {
                            qqvo.setPerArea(value.getCodeTitle());
                        }
                    }
                }
                qqvo.setPerSource("2");//app
                //医院信息
                qqvo.setHospId(dept.getId());
                qqvo.setHospName(dept.getHospName());
                qqvo.setHospAreaCode(dept.getHospAreaCode());
                qqvo.setHospAddress(dept.getHospAddress());
                qqvo.setHospTel(dept.getHospTel());
                //医生信息
                qqvo.setDrId(drUser.getId());
                qqvo.setDrName(drUser.getDrName());
                qqvo.setDrAccount(drUser.getDrAccount());
                qqvo.setDrPwd(drUser.getDrPwd());
                qqvo.setDrGender(drUser.getDrGender());
                qqvo.setDrTel(drUser.getDrTel());
                qqvo.setPerType(PerformanceType.JKJY.getValue());
                if(StringUtils.isNotBlank(qqvo.getPerArea())){
                    if(StringUtils.isNotBlank(qqvo.getPerType())){
//                        String result = null;
//                        if (qqvo.getPerArea().equals(AddressType.FZ.getValue())) {
//
//                        } else if (qqvo.getPerArea().equals(AddressType.QZ.getValue())) {
//                            result = "qz_";
//                        } else if (qqvo.getPerArea().equals(AddressType.ZZ.getValue())) {
//                            result = "zz_";
//                        } else if (qqvo.getPerArea().equals(AddressType.PT.getValue())) {
//                            result = "pt_";
//                        } else if (qqvo.getPerArea().equals(AddressType.NP.getValue())) {
//                            result = "np_";
//                        } else if (qqvo.getPerArea().equals(AddressType.SM.getValue())) {
//                            result = "sm_";
//                        }
//                        if(StringUtils.isNotBlank(result)){
//                            qqvo.setDrId(result+qqvo.getDrId());
//                            qqvo.setHospId(result+qqvo.getHospId());
//                        }
                        if(form != null){
                            String fwType = "";
                            String sermeal = "";//服务包id
                            fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                            sermeal = form.getSignpackageid();
                            sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                        }
                    }
                }
            }
            news.setTableTransmit(String.valueOf(Integer.parseInt(news.getTableTransmit())+num));
            sysDao.getServiceDo().modify(news);
            return "true";
        }else{
            return "暂无该类型人员信息";
        }
    }

    /**
     * 查询定期发送数据
     * @return
     * @throws Exception
     */
    @Override
    public List<AppUserHealthED> findByPush()  throws Exception  {
        Map<String,Object> map = new HashMap<String,Object>();
        String date = ExtendDate.getYMD(Calendar.getInstance());
        map.put("date",date);
        map.put("fsState","0");
        map.put("pushState","1");
        String sql = "SELECT * FROM APP_USER_HEALTHED WHERE HED_PUSH_STATE =:pushState " +
                "AND HED_PUSH_OSTATE =:fsState " +
                "AND DATE_FORMAT(HED_PUSH_DATE,'%Y-%m-%d') =:date";
        List<AppUserHealthED> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppUserHealthED.class);
        return ls;
    }

    /**
     * 添加慢病健康教育和推送
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String saveHealthByNcd(AppNcdHEQvo qvo) throws Exception {
        NcdNewTable vo = null;
        vo = (NcdNewTable) sysDao.getServiceDo().find(NcdNewTable.class,qvo.getId());
        AppHospDept hosp = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getTableHospId());
        if(hosp == null){
            throw new Exception("查无机构数据!");
        }
        AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
        if(drUser == null){
            throw new Exception("查无推送医生数据!");
        }
        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());

        if(vo == null){//如果没有查到，则先添加模板
            vo = new NcdNewTable();
            vo.setId(qvo.getId());
            vo.setTableTitle(qvo.getTableTitle());
            vo.setTableContent(qvo.getTableContent());
            vo.setTableHealthType(qvo.getTableHealthType());
            vo.setTableCjsj(Calendar.getInstance());
            vo.setTableType("1");//系统模板
            vo.setTableSource("慢病系统");
            vo.setTableHospId(qvo.getTableHospId());
            vo.setTableCjrid(qvo.getTableCjrid());
            vo.setTableCjrxm(qvo.getTableCjrxm());
            sysDao.getServiceDo().add(vo);
        }else{//有做修改,只做标题和内容修改
            vo.setTableTitle(qvo.getTableTitle());
            vo.setTableContent(qvo.getTableContent());
        }

        //根据发送对象发送健康教育信息给患者
        if(StringUtils.isNotBlank(qvo.getTableObject())){//根据服务对象发送
            //根据医生查询团队
            String sqlTeam = "SELECT aa.MEM_TEAMID FROM app_team_member aa WHERE aa.MEM_DR_ID = :drId ";
            Map<String,Object> map = new HashMap<>();
            map.put("value",qvo.getTableObject().split(";"));
            map.put("drId",drUser.getId());
            map.put("type","3");
            map.put("state",new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()});
            String sql ="SELECT c.* FROM APP_PATIENT_USER c WHERE c.ID IN \n" +
                    "(SELECT\n" +
                    "\ta.SIGN_PATIENT_ID\n" +
                    "FROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "WHERE\n" +
                    "\tb.LABEL_TYPE = :type  AND a.SIGN_TEAM_ID IN ("+sqlTeam+")  AND a.SIGN_STATE IN (:state) \n" +
                    "AND b.LABEL_VALUE IN (:value) )";
            List<AppPatientUser> list = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class);
            if(list!=null&&list.size()>0){
                int num=0;
                for(AppPatientUser ls:list){
                    num++;
                    AppUserHealthED table = new AppUserHealthED();
                    table.setHedId(vo.getId());
                    table.setHedUserId(ls.getId());
                    table.setHedDrId(drUser.getId());
                    if(hosp!=null){
                        table.setHedHospId(dept.getId());
                        table.setHedAreaCode(dept.getHospAreaCode());
                    }
                    table.setHedCreateTime(Calendar.getInstance());
                    table.setHedId(vo.getId());
                    table.setHedType(vo.getTableHealthType());
                    table.setHedDrName(drUser.getDrName());
                    table.setHedContent(vo.getTableContent());
                    table.setHedUserName(ls.getPatientName());
//                List<AppSignForm> form = this.sysDao.getServiceDo().loadByPk(AppSignForm.class, "signPatientId", ls.getId());
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(ls.getId());
                    if (form != null ) {
                        table.setHedTeamId(form.getSignTeamId());
                        table.setHedTeamName(form.getSignTeamName());
                    }
                    table.setHedTitle(vo.getTableTitle());
                    table.setHedImageUrl(vo.getTableImageUrl());
                    this.sysDao.getServiceDo().add(table);
                    sysDao.getAppNoticeDao().addNotices("健康教育", "您新收到一笔健康教育，请注意查看。",
                            NoticesType.JKZX.getValue(), drUser.getId(), ls.getId(), table.getId(), DrPatientType.PATIENT.getValue());

                    //履约数据
                    PerformanceDataQvo qqvo = new PerformanceDataQvo();
                    //患者信息
                    qqvo.setPerName(ls.getPatientName());
                    qqvo.setPerIdno(ls.getPatientIdno());
                    qqvo.setPerForeign(table.getId());
                    if(StringUtils.isNotBlank(ls.getPatientCity())){
                        CdAddress p = sysDao.getCdAddressDao().findByCode(ls.getPatientCity());
                        if(p != null){
                            String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                            if(value != null) {
                                qqvo.setPerArea(value.getCodeTitle());
                            }
                        }
                    }
                    qqvo.setPerSource("2");//app
                    //医院信息
                    qqvo.setHospId(dept.getId());
                    qqvo.setHospName(dept.getHospName());
                    qqvo.setHospAreaCode(dept.getHospAreaCode());
                    qqvo.setHospAddress(dept.getHospAddress());
                    qqvo.setHospTel(dept.getHospTel());
                    //医生信息
                    qqvo.setDrId(drUser.getId());
                    qqvo.setDrName(drUser.getDrName());
                    qqvo.setDrAccount(drUser.getDrAccount());
                    qqvo.setDrPwd(drUser.getDrPwd());
                    qqvo.setDrGender(drUser.getDrGender());
                    qqvo.setDrTel(drUser.getDrTel());
                    qqvo.setPerType(PerformanceType.JKJY.getValue());
                    if(StringUtils.isNotBlank(qqvo.getPerArea())){
                        if(StringUtils.isNotBlank(qqvo.getPerType())){
                            if(form != null){
                                String fwType = "";
                                String sermeal = "";//服务包id
                                fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                sermeal = form.getSignpackageid();
                                sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                            }
                        }
                    }
                }
                vo.setTableTransmit(String.valueOf(Integer.parseInt(vo.getTableTransmit())+num));
                sysDao.getServiceDo().modify(vo);
            }else{
                throw new Exception("查无相关可推送人员数据");
            }

        }else if(StringUtils.isNotBlank(qvo.getTablePeopleList())){//根据具体人员发送
            String[] patientIdNos = qvo.getTablePeopleList().split(";");
            int num = 0;
            for (String idno : patientIdNos) {
                AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(idno);
                if(user != null){
                    num++;
                    AppUserHealthED table = new AppUserHealthED();
                    table.setHedId(vo.getId());
                    table.setHedUserId(user.getId());
                    table.setHedDrId(qvo.getTableCjrid());
                    table.setHedHospId(dept.getId());
                    table.setHedAreaCode(dept.getHospAreaCode());
                    table.setHedCreateTime(Calendar.getInstance());
                    table.setHedType(vo.getTableHealthType());
                    table.setHedDrName(drUser.getDrName());
                    table.setHedContent(vo.getTableContent());
                    if (user != null) {
                        table.setHedUserName(user.getPatientName());
                    }
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(user.getId());
                    if (form != null) {
                        table.setHedTeamId(form.getSignTeamId());
                        table.setHedTeamName(form.getSignTeamName());
                    }
                    table.setHedTitle(vo.getTableTitle());
                    table.setHedImageUrl(vo.getTableImageUrl());
                    this.sysDao.getServiceDo().add(table);

                    //推送健康资讯(发消息提醒) 您新收到一笔健康资讯
                    sysDao.getAppNoticeDao().addNotices("健康教育", "您新收到一笔健康教育，请注意查看。",
                            NoticesType.JKZX.getValue(), drUser.getId(), user.getId(), table.getId(), DrPatientType.PATIENT.getValue());
                    //履约数据
                    PerformanceDataQvo qqvo = new PerformanceDataQvo();
                    //患者信息
                    qqvo.setPerName(user.getPatientName());
                    qqvo.setPerIdno(user.getPatientIdno());
                    qqvo.setPerForeign(table.getId());
                    if(StringUtils.isNotBlank(user.getPatientCity())){
                        CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                        if(p != null){
                            String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                            if(value != null) {
                                qqvo.setPerArea(value.getCodeTitle());
                            }
                        }
                    }
                    qqvo.setPerSource("2");//app
                    //医院信息
                    qqvo.setHospId(dept.getId());
                    qqvo.setHospName(dept.getHospName());
                    qqvo.setHospAreaCode(dept.getHospAreaCode());
                    qqvo.setHospAddress(dept.getHospAddress());
                    qqvo.setHospTel(dept.getHospTel());
                    //医生信息
                    qqvo.setDrId(drUser.getId());
                    qqvo.setDrName(drUser.getDrName());
                    qqvo.setDrAccount(drUser.getDrAccount());
                    qqvo.setDrPwd(drUser.getDrPwd());
                    qqvo.setDrGender(drUser.getDrGender());
                    qqvo.setDrTel(drUser.getDrTel());
                    qqvo.setPerType(PerformanceType.JKJY.getValue());
                    if(StringUtils.isNotBlank(qqvo.getPerArea())){
                        if(StringUtils.isNotBlank(qqvo.getPerType())){
                            if(form != null){
                                String fwType = "";
                                String sermeal = "";//服务包id
                                fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                sermeal = form.getSignpackageid();
                                sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                            }
                        }
                    }
                }else{
                    throw new Exception("查无身份证为："+idno+"的签约居民信息");
                }
            }
            vo.setTableTransmit(String.valueOf(Integer.parseInt(vo.getTableTransmit()) + num));
            this.sysDao.getServiceDo().modify(vo);
        }else{
            throw new Exception("发送对象不能为空");
        }
        return "true";
    }
}
