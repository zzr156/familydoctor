package com.ylz.bizDo.app.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionContext;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.*;
import com.ylz.bizDo.jtapp.basicHealthEntity.AppEnterpatientEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppSignPeopleEntity;
import com.ylz.bizDo.jtapp.commonVo.*;
import com.ylz.packaccede.fiter.LogEntityInterceptor;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.ylz.bizDo.app.dao.AppSignformDao;
import com.ylz.bizDo.app.entity.AppHfsSignSscEntity;
import com.ylz.bizDo.app.entity.AppWebSignFormListEntity;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppArchivintPeopleEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppFamilyInfo;
import com.ylz.bizDo.jtapp.drEntity.AppDrCountEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrPatientFwEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrPatientSignEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrSignPeoleListEntity;
import com.ylz.bizDo.jtapp.drEntity.AppSignFormEntity;
import com.ylz.bizDo.jtapp.drEntity.AppSignFormListEntity;
import com.ylz.bizDo.jtapp.drEntity.ToplimitEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppSMSignEntity;
import com.ylz.bizDo.jtapp.patientVo.AppFamilySignQvo;
import com.ylz.bizDo.jtapp.patientVo.AppFamilySignSonQvo;
import com.ylz.bizDo.jtapp.patientVo.AppGoToSignQvo;
import com.ylz.bizDo.jtapp.patientVo.AppPatientQyQvo;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangeMsgEntity;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeCountQvo;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeSureQvo;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.bizDo.news.Entity.SignPeopleEntity;
import com.ylz.bizDo.news.vo.NewsTableQqvo;
import com.ylz.bizDo.smjq.smEntity.AppPeopleBasicEntity;
import com.ylz.bizDo.smjq.smEntity.AppSmSignEntity;
import com.ylz.bizDo.smjq.smEntity.AppSmyxPatientEntity;
import com.ylz.bizDo.smjq.smVo.AppSmPeopleBasicVo;
import com.ylz.bizDo.smjq.smVo.AppSmyxPatientVo;
import com.ylz.bizDo.web.vo.WebSignSaveVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.exception.DaoException;

import net.sf.json.JSONObject;

/**
 * Created by zzl on 2017/6/14.
 */
@Service("appSignformDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSignformDaoImpl implements AppSignformDao {

    private static final String menuModule = "4";//模块类型
    private static final String menuValue = "6";//结果数据
    private static final String npAreaCode = "3507";//南平行政区划
    @Autowired
    private SysDao sysDao;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(AppSignformDaoImpl.class);

    /**
     * 个人签约
     *
     * @param patientId 患者
     * @param teamid    团队
     * @param drId      医生id
     */
    @Override
    public AppSignForm signFormUser(String patientId, String teamid, String drId, String signpackageid, String signUpHpis, String signatureImageUrl, String signWay, String signImageUrl,String signMobileType) throws Exception {
        AppSignBatch batch = new AppSignBatch();//批次
        AppSignForm form = new AppSignForm();//签约单
        AppTeam teamvo = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, teamid);//团队
        AppPatientUser uservo = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, patientId);//患者
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchTeamId(teamvo.getId());
        batch.setBatchTeamName(teamvo.getTeamName());
        batch.setBatchCreatePersid(uservo.getId());
        batch.setBatchPatientName(uservo.getPatientName());
        //组织批次号
        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, teamvo.getTeamHospId());
        if (dept != null && dept.getHospAreaCode() != null) {
            AppSerial serial = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
            if (serial != null) {
                Map<String, Object> bcnum = this.getNum(serial.getSerialNo(), SignFormType.APPSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
        }
        batch.setBatchOperatorId(drId);
        AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(drId);
        if (drUser != null) {
            batch.setBatchOperatorName(drUser.getDrName());
        }
        //组织编码
        if (dept != null && dept.getHospAreaCode() != null) {
            batch.setBatchAreaCode(dept.getHospAreaCode());
        }
        sysDao.getServiceDo().add(batch);
        form.setUpHpis(signUpHpis);
        form.setSignBatchId(batch.getId());
        //组织编码
        if (dept != null && dept.getHospAreaCode() != null) {
            form.setSignHospId(dept.getId());
            form.setSignAreaCode(dept.getHospAreaCode());
            AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
            if (serialSign != null) {
                Map<String, Object> sinum = this.getNum(serialSign.getSerialNo(), SignFormType.APPSTATE.getValue());
                serialSign.setSerialNo(sinum.get("old").toString());
                sysDao.getServiceDo().modify(serialSign);
                form.setSignNum(sinum.get("new").toString());//签约编码
            }
        }
        //
        form.setSignPatientId(uservo.getId());
        form.setSignDate(Calendar.getInstance());
        form.setSignpackageid(signpackageid);
        form.setSignMobileType(signMobileType);

        //是否直接同意签约
        AppHospExtend extend = this.sysDao.getAppHospExtendDao().findByHospId(form.getSignHospId());
        boolean flagAgree = false;
        if (extend != null) {
            if (extend.getExtHreState().equals(AppFlagAgree.KAIQI.getValue())) {
//                form.setSignState("2");//2:已签约
                form.setSignState(SignFormType.YUQY.getValue());//预签约
                form.setSignFromDate(Calendar.getInstance());
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 1);
                end.add(Calendar.DATE, -1);
                form.setSignToDate(end);
                flagAgree = true;
            } else {
                form.setSignState("1");//1:待签约
            }
        } else {
            form.setSignState("1");//1:待签约
        }
        form.setSignPayState("0");//0：未缴费
        form.setSignType("1");//1家庭签约
        form.setSignTeamId(teamvo.getId());
        form.setSignTeamName(teamvo.getTeamName());
        if (StringUtils.isNotBlank(signWay)) {
            form.setSignWay(signWay);//签约方式
        } else {
            form.setSignWay("0");//签约方式
        }
        form.setSignPatientAge(Integer.parseInt(uservo.getPatientAge()));
        form.setSignPatientGender(uservo.getPatientGender());
        form.setSignPatientIdNo(uservo.getPatientIdno());
        form.setSignDrId(drId);//绑定医生
        form.setSignContractState("0");//1是 0否
        form.setSignGreenState("0");//1是 0否
        form.setSignYellowState("0");//1是 0否
        form.setSignRedState("0");//1是 0否
        if (StringUtils.isNotBlank(signpackageid)) {
            String areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode(), "2");
            if (areaCode != null) {
                if (!npAreaCode.equals(areaCode)) {
                    String text = sysDao.getAppSignformDao().findPkRemark(signpackageid);
                    form.setSigntext(text);
                }
            } else {
                String text = sysDao.getAppSignformDao().findPkRemark(signpackageid);
                form.setSigntext(text);
            }
//            form.setSignczpay("");
//            form.setSignzfpay("");
//            if("3501".equals(areaCode)){//福州做财政补助和自费
//                String[] mealIds = signpackageid.split(";");
//                double zfpay=0.0;//自费
//                double czpay=0.0;//财政补贴
//                double totalFee = 0.0;//总费用
//                for(String mealId:mealIds){
//                    AppServeSetmeal meal = (AppServeSetmeal)sysDao.getServiceDo().find(AppServeSetmeal.class,mealId);
//                    if(meal!=null){
//                        if("1".equals(meal.getSersmJcState())){//基础包才有补贴
//                            if(StringUtils.isNotBlank(meal.getSersmTotalFee())){//总费用
//                                totalFee += Double.parseDouble(meal.getSersmTotalFee());
//                                if(StringUtils.isNotBlank(meal.getSersmTotalOneFee())){//自费费用
//                                    zfpay += Double.parseDouble(meal.getSersmTotalOneFee());
//                                }
//                            }
//                            //获取补贴数据
//                            if(StringUtils.isNotBlank(meal.getSersmJjId())){
//                                String[] jjIds = meal.getSersmJjId().split(";");
//                                String btids = "";
//                                for(String jjid:jjIds){
//                                    AppEconAndGov eag = (AppEconAndGov)sysDao.getServiceDo().find(AppEconAndGov.class,jjid);
//                                    if(eag != null){
//                                        if(StringUtils.isNotBlank(eag.getEagGovId())){
//                                            if(StringUtils.isBlank(btids)){
//                                                btids = eag.getEagGovId();
//                                            }else{
//                                                btids += ";"+eag.getEagGovId();
//                                            }
//                                        }
//                                    }
//                                }
//                                if(StringUtils.isNotBlank(btids)){
//                                    Map<String,Object> map = new HashMap<>();
//                                    map.put("btIds",btids.split(";"));
//                                    String sql = "SELECT sum(GOV_MONEY) money FROM APP_SERVE_GOV WHERE ID IN (:btIds) ";
//                                    sysDao.getServiceDo().findSqlMap(sql,map);
//                                }
//
//                            }
//                        }else{
//                            totalFee += Double.parseDouble(meal.getSersmTotalFee());
//                            zfpay = totalFee;
//                        }
//                    }
//                }
//                czpay = totalFee-zfpay;
//                form.setSignczpay(String.valueOf(czpay));
//                if(czpay<=0){
//                    form.setSignczpay("0");
//                }
//                form.setSignzfpay(String.valueOf(zfpay));
//            }
        }


        if (StringUtils.isNotBlank(signatureImageUrl)) {
            Map<String, Object> map = sysDao.getIoUtils().getCtyunOosSample(signatureImageUrl, CommonShortType.HUANGZHE.getValue());
            form.setSignatureImageUrl(map.get("objectUrl").toString());
        }
        sysDao.getServiceDo().add(form);
        if (StringUtils.isNotBlank(signImageUrl)) {
            sysDao.getAppSignformDao().saveImage(signImageUrl, form.getId(), form.getUpHpis(), CommSF.NOT.getValue(), form.getSignAreaCode());
        }
//        //app签名
        if(StringUtils.isNotBlank(signatureImageUrl)){
            sysDao.getAppSignformDao().saveImage(signatureImageUrl,form.getId(),form.getUpHpis(),CommSF.YES.getValue(),form.getSignAreaCode());
        }
        if (flagAgree) {
            //计算自费情况
            if (StringUtils.isNotBlank(form.getSignpackageid())) {//
                Map<String, Object> map = sysDao.getAppSignformDao().mealCost(form.getSignpackageid(), form, null, null);
                if (map.get("zfpay") != null) {
                    form.setSignzfpay(map.get("zfpay").toString());
                }
                if (map.get("czpay") != null) {
                    form.setSignczpay(map.get("czpay").toString());
                }
                sysDao.getServiceDo().modify(form);
            }
            this.SignFormNotices(form);

            //添加建档立卡数据


//            String title = "签约成功消息";
//            String content = "您好，您的家庭医生签约申请已审核通过!";
//            sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), drId, uservo.getId(), form.getId(), DrPatientType.PATIENT.getValue());
        } else {
            String title = "签约消息";
            String content = uservo.getPatientName() + "," + uservo.getStrPatientGender() + "," + uservo.getPatientAge() + "岁,于" + ExtendDate.getYMD_h_m(Calendar.getInstance()) + "申请签约!";
            if (drId == null) {
                List<AppTeamMember> members = sysDao.getServiceDo().loadByPk(AppTeamMember.class, "memDrId", teamid);
                for (AppTeamMember m : members) {
                    sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), uservo.getId(), m.getMemDrId(), form.getId(), DrPatientType.DR.getValue());
                }
            } else {
                sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), uservo.getId(), drId, form.getId(), DrPatientType.DR.getValue());
            }
        }
        return form;
    }

    /**
     * 根据用户id或团队id查询签约单列表
     *
     * @param qvo
     * @return
     */
    @Override
    public List<AppSignFormListEntity> findSignFormByUserOrTeam(AppCommQvo qvo) throws Exception{
        List<AppSignFormListEntity> ls;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contractState", CommSF.NOT.getValue());
//        String sql = "SELECT * FROM ( ";
//        String sqlPatient = "";
//        String sqlCreate = "";
//        String sqlAll = "SELECT\n" +
//                "\ta.ID signFormId,\n" +
//                "\ta.SIGN_STATE signState,\n" +
//                "\t(SELECT PATIENT_NAME from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientName,\n" +
//                "\t(SELECT PATIENT_GENDER from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientGender,\n" +
//               // "\t(SELECT PATIENT_AGE from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientAge,\n" +
//                "\t(SELECT PATIENT_IMAGEURL from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientImageurl,\n" +
//                "\ta.SIGN_PATIENT_ID patientId,\n" +
//                "\t(SELECT TEAM_HOSP_NAME from APP_TEAM where ID=a.SIGN_TEAM_ID) teamHospName,\n" +
//                "\ta.SIGN_TEAM_NAME teamName,\n" +
//                "\ta.SIGN_TEAM_ID teamId,\n" +
//                "\ta.SIGN_FROM_DATE signFromDate,\n" +
//                "\ta.SIGN_TO_DATE signToDate,\n" +
//                "\ta.SIGN_DATE signDate,\n" +
//                "\ta.SIGN_WAY signWay,\n" +
//                "\t(SELECT PATIENT_IDNO from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientIdno,\n" +
//                "null patientAge," +
//                "(SELECT GROUP_CONCAT(e.SER_TITLE) FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN (SELECT g.LABEL_VALUE from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID GROUP BY g.LABEL_VALUE) ) fwb," +
//                "(SELECT GROUP_CONCAT(e.SER_IMAGE_NAME) FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN (SELECT g.LABEL_VALUE from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID GROUP BY g.LABEL_VALUE) ) fwImageName," +
//                "\t(SELECT PATIENT_CARD from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientCard,\n" +
//                "\t(SELECT PATIENT_TEL from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientTel,\n" +
//                "\t(SELECT DR_NAME from APP_DR_USER where ID=a.SIGN_DR_ID) drName\n" +
//                "FROM\n" +
//                "\tAPP_SIGN_FORM a,\n" +
//                "\tAPP_SIGN_BATCH b\n" +
//                "WHERE\n" +
//                "\ta.SIGN_BATCH_ID = b.ID ";
//        sqlPatient = sqlAll;
//        sqlCreate = sqlAll;
//        if(StringUtils.isNotBlank(qvo.getPatientId())){
//
////            sql.append(" AND a.SIGN_PATIENT_ID =:SIGN_PATIENT_ID \n" +
////                    "AND b.BATCH_CREATE_PERSID =:BATCH_CREATE_PERSID ");
//            map.put("SIGN_PATIENT_ID",qvo.getPatientId());
//            map.put("BATCH_CREATE_PERSID",qvo.getPatientId());
//            sqlPatient += " AND a.SIGN_PATIENT_ID =:SIGN_PATIENT_ID \n";
//            sqlCreate += " AND b.BATCH_CREATE_PERSID =:BATCH_CREATE_PERSID \n";
//
//        }
//        if(StringUtils.isNotBlank(qvo.getTeamId())){
//            map.put("SIGN_TEAM_ID",qvo.getTeamId());
//            sqlPatient += " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID  \n";
//            sqlCreate += " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID  \n";
//        }
//        /*if(StringUtils.isNotBlank(qvo.getDrId())){
//            sql.append(" AND a.SIGN_DR_ID=:SIGN_DR_ID ");
//            map.put("SIGN_DR_ID",qvo.getDrId());
//        }*/
//        if(StringUtils.isNotBlank(qvo.getSignState())){
//            sqlPatient += " AND a.SIGN_STATE=:SIGN_STATE  \n";
//            sqlCreate += " AND a.SIGN_STATE=:SIGN_STATE  \n";
//            map.put("SIGN_STATE",qvo.getSignState());
//        }
//        if(StringUtils.isNotBlank(qvo.getSignFormId())){
//            sqlPatient += " AND a.ID=:ID  \n";
//            sqlCreate += " AND a.ID=:ID  \n";
//            map.put("ID",qvo.getSignFormId());
//        }
//        sql += sqlPatient + " UNION " + sqlCreate + " ) t ORDER BY  t.signDate  DESC";
//        sqlPatient += " ORDER BY  a.SIGN_DATE  DESC ";
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "\ta.ID signFormId,\n" +
                "\ta.SIGN_STATE signState,\n" +
                "\td.PATIENT_NAME patientName,\n" +
                "\td.PATIENT_GENDER patientGender,\n" +
                "\td.PATIENT_IMAGEURL patientImageurl,\n" +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\t(SELECT TEAM_HOSP_NAME from APP_TEAM where ID=a.SIGN_TEAM_ID) teamHospName,\n" +
                "\ta.SIGN_TEAM_NAME teamName,\n" +
                "\ta.SIGN_TEAM_ID teamId,\n" +
                "\ta.SIGN_FROM_DATE signFromDate,\n" +
                "\ta.SIGN_TO_DATE signToDate,\n" +
                "\ta.SIGN_DATE signDate,\n" +
                "\ta.SIGN_WAY signWay,\n" +
                "\td.PATIENT_IDNO patientIdno,\n" +
                "\t'' patientAge,\n" +
                "\t(SELECT GROUP_CONCAT(e.SER_TITLE) FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN (SELECT g.LABEL_VALUE from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID GROUP BY g.LABEL_VALUE) ) fwb,\n" +
                "\t(SELECT GROUP_CONCAT(e.SER_IMAGE_NAME) FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN (SELECT g.LABEL_VALUE from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID GROUP BY g.LABEL_VALUE) ) fwImageName,\t\n" +
                "\td.PATIENT_CARD patientCard,\n" +
                "\td.PATIENT_TEL patientTel,\n" +
                "\tc.DR_NAME drName,\n" +
                "\tc.DR_IMAGEURL drImageurl,\n" +
                "\tc.DR_GENDER drGender,\n" +
                "\t(SELECT MEM_STATE FROM APP_TEAM_MEMBER WHERE MEM_TEAMID = a.SIGN_TEAM_ID AND MEM_DR_ID=a.SIGN_DR_ID ) teamState,\n" +
                "\t(SELECT MEM_WORK_TYPE FROM APP_TEAM_MEMBER WHERE MEM_TEAMID = a.SIGN_TEAM_ID AND MEM_DR_ID=a.SIGN_DR_ID ) teamWorkType,\n" +
                "'' renewState,\n" +
                "'' oldSign,\n" +
                "a.SIGN_GOTO_SIGN_STATE signGoToSignState,\n" +
                "a.SIGN_OTHNER_REASON signOthnerReason,\n" +
                "a.SIGN_PAY_STATE payState,\n" +
                "a.SIGN_PACKAGEID serveList,\n" +
                "a.SIGN_DR_ID drId, \n" +
                "'' batchId\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a INNER JOIN\tAPP_SIGN_BATCH b ON a.SIGN_BATCH_ID = b.ID\n" +
                "INNER JOIN app_dr_user c ON a.SIGN_DR_ID = c.ID\n" +
                "INNER JOIN APP_PATIENT_USER d ON a.SIGN_PATIENT_ID = d.ID\n" +
                "WHERE\n" +
                "\t1=1 ");
        //batchId 为空是表示非家庭版签约
        if (StringUtils.isNotBlank(qvo.getPatientId())) {
            sql.append(" AND a.SIGN_PATIENT_ID =:SIGN_PATIENT_ID \n");
//            "AND b.BATCH_CREATE_PERSID =:BATCH_CREATE_PERSID "
            map.put("SIGN_PATIENT_ID", qvo.getPatientId());
            map.put("BATCH_CREATE_PERSID", qvo.getPatientId());
        }
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            sql.append(" AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID ");
            map.put("SIGN_TEAM_ID", qvo.getTeamId());
        }
        if (StringUtils.isNotBlank(qvo.getDrId())) {
            sql.append(" AND a.SIGN_DR_ID=:SIGN_DR_ID ");
            map.put("SIGN_DR_ID", qvo.getDrId());
        }
        /*if(StringUtils.isNotBlank(qvo.getDrId())){
            sql.append(" AND a.SIGN_DR_ID=:SIGN_DR_ID ");
            map.put("SIGN_DR_ID",qvo.getDrId());
        }*/
        if (StringUtils.isNotBlank(qvo.getSignState())) {
            if ("1".equals(qvo.getSignState())) {
                String[] strs = new String[]{SignFormType.YUZQ.getValue(), SignFormType.DQY.getValue()};
                map.put("strs", strs);
                sql.append(" AND a.SIGN_STATE IN :strs");
            } else if (SignFormType.YQY.getValue().equals(qvo.getSignState())) {
                String[] strs = new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue(), SignFormType.ZQ.getValue()};
                map.put("strs", strs);
                sql.append(" AND a.SIGN_STATE IN (:strs)");
            } else {
                sql.append(" AND a.SIGN_STATE=:SIGN_STATE ");
                map.put("SIGN_STATE", qvo.getSignState());
            }
        }
        if (StringUtils.isNotBlank(qvo.getSignFormId())) {
            sql.append(" AND a.ID=:ID ");
            map.put("ID", qvo.getSignFormId());
        }
        sql.append(" ORDER BY a.SIGN_DATE DESC");
        ls = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AppSignFormListEntity.class, qvo);
        if (ls != null && ls.size() > 0) {
            return ls;
        }
        return ls;
    }

    /**
     * 同意签约
     *
     * @param signFormId
     * @param signPersGroup     labelValue 居民分组 服务人群 多分号隔开
     * @param signHealthGroup   labelValue 健康分布
     * @param labelGruops       labelValue 疾病类型接口 多分号隔开
     * @param signsJjType       经济类型
     * @param patientjmda
     * @param patientjtda
     * @param signlx
     * @param drId              医生主键
     * @param signDrAssistantId 助理主键
     * @return
     */
    @Override
    public AppSignForm agreeSignForm(String signFormId, String signPersGroup, String signHealthGroup, String labelGruops, String fee,
                                     String payType, String signsJjType, String patientjmda, String patientjtda, String signlx, String drId, String signDrAssistantId) throws Exception {
        AppSignForm sign = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, signFormId);

        if (sign != null) {
            refuseToSign(sign.getSignPatientId(), sign.getSignTeamId(), sign.getSignDrId());//判断拒签
          /*  AppSignForm oldForm = sysDao.getAppSignformDao().findSignFormByUser(sign.getSignPatientId());//已签约单（未续约）
            if (oldForm != null) {
                oldForm.setSignContractState("1");//已续约
                sysDao.getServiceDo().modify(oldForm);//续签处理旧签约单
            }
*/
            //转签处理
            if (SignFormType.YUZQ.getValue().equals(sign.getSignState())) {
                Calendar caa = sign.getSignFromDate();
                sign.setSignState(SignFormType.ZQ.getValue());
                caa.add(Calendar.YEAR, 1);
                caa.add(Calendar.DATE, -1);
                List<AppGotoSignRecord> goToSigns = sysDao.getServiceDo().loadByPk(AppGotoSignRecord.class, "gtsSignId", sign.getId());
                if (goToSigns != null && goToSigns.size() > 0) {
                    AppGotoSignRecord goToSign = goToSigns.get(0);
                    if (goToSign != null) {
                        goToSign.setGtsSignState("1");
                        sysDao.getServiceDo().modify(goToSign);
                    }
                }
                sign.setSignToDate(caa);
            } else {
                sign.setSignState(SignFormType.YUQY.getValue());
                sign.setSignFromDate(Calendar.getInstance());
//                sign.setSignState(SignFormType.YQY.getValue());
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 1);
                end.add(Calendar.DATE, -1);
                sign.setSignToDate(end);
                //查询该地市是否开启本年度签约协议
                AppSignSetting setting = sysDao.getAppSignSettingDao().findByAreaCode(AreaUtils.getAreaCode(sign.getSignAreaCode(),"2"));
                if(setting != null){
                    if("1".equals(setting.getSerOpenYear())){
                        String startDate = ExtendDate.getYYYY(Calendar.getInstance())+"-01-01";
                        String endDate = ExtendDate.getYYYY(Calendar.getInstance())+"-12-31";
                        sign.setSignFromDate(ExtendDate.getCalendar(startDate));
                        sign.setSignToDate(ExtendDate.getCalendar(endDate));
                    }
                }
            }
            sign.setSignPayType(payType);
            sign.setSignFee(fee);
            sign.setSignlx(signlx);

            //改服务人群为多选
            if (StringUtils.isNotBlank(signPersGroup)) {
                String[] groups = signPersGroup.split(";");
                if (groups != null && groups.length > 0) {
                    sign.setSignPersGroup(groups[0]);//支持之前版
                    sysDao.getAppLabelGroupDao().addLabel(groups, sign.getId(), sign.getSignTeamId(), sign.getSignAreaCode(), LabelManageType.FWRQ.getValue());
                }
            }
            sign.setSignHealthGroup(signHealthGroup);
            //疾病类型
            if (StringUtils.isNotBlank(labelGruops)) {
                String[] groups = labelGruops.split(";");
                if (groups != null && groups.length > 0) {
                    sysDao.getAppLabelGroupDao().addLabel(groups, sign.getId(), sign.getSignTeamId(), sign.getSignAreaCode(), LabelManageType.JBLX.getValue());
                }
            }
            boolean jjlx = false;
            //经济类型
            if (StringUtils.isNotBlank(signsJjType)) {
                String[] groups = signsJjType.split(";");
                for (String jjlxs : groups) {
                    if (EconomicType.JDLKPKRK.getValue().equals(jjlxs)) {
                        jjlx = true;
                    }
                }
                sign.setSignsJjType(groups[0]);
                if (groups != null && groups.length > 0) {
                    sysDao.getAppLabelGroupDao().addLabel(groups, sign.getId(), sign.getSignTeamId(), sign.getSignAreaCode(), LabelManageType.JJLX.getValue());
                }
            }

            List<AppTeamMember> lsTeam = this.sysDao.getAppTeamMemberDao().findTeamId(sign.getSignTeamId());
            if (lsTeam != null && lsTeam.size() > 0) {
                for (AppTeamMember v : lsTeam) {
                    this.sysDao.getSecurityCardAsyncBean().addFridenSignl(sign.getSignPatientId(), v.getMemDrId());
                }
            }
            AppTeam teamvo = (AppTeam) this.sysDao.getServiceDo().find(AppTeam.class, sign.getSignTeamId());
            if (teamvo != null) {
                this.sysDao.getSecurityCardAsyncBean().addRoomMembers(teamvo.getTeamEaseRoomId(), sign.getSignPatientId(), CommonShortType.HUANGZHE.getValue());
            }
            if(StringUtils.isBlank(sign.getUpHpis())){
                sign.setUpHpis("1");
            }
            //计算自费情况
            if (StringUtils.isNotBlank(sign.getSignpackageid())) {//
                Map<String, Object> map = sysDao.getAppSignformDao().mealCost(sign.getSignpackageid(), sign, signPersGroup, signsJjType);
                if (map.get("zfpay") != null) {
                    sign.setSignzfpay(map.get("zfpay").toString());
                }
                if (map.get("czpay") != null) {
                    sign.setSignczpay(map.get("czpay").toString());
                }
            }
            if (StringUtils.isNotBlank(drId)) {//医生主键不为空，说明签约选择其他医生
                sign.setSignDrId(drId);//修改签约医生
            }
            if (StringUtils.isNotBlank(signDrAssistantId)) {//助理医生主键不为空
                sign.setSignDrAssistantId(signDrAssistantId);//添加助理
            }

            //一键注册时默认选择机构服务包
     /*       if(StringUtils.isBlank(sign.getSignpackageid())){
                 if(StringUtils.isBlank(sign.getSignHospId())){
                     throw new Exception("签约机构主键不能为空");
                 }else{
                     AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,sign.getSignHospId());
                     if(dept == null){
                         throw new Exception("查无机构信息");
                     }else{
                         if(StringUtils.isBlank(dept.getHospMealId())){
                             throw new Exception("查无机构默认服务包配置，请先配置");
                         }else{
                             sign.setSignpackageid(dept.getHospMealId());
                         }
                     }
                 }
            }*/

            sysDao.getServiceDo().modify(sign);

            AppPatientUser patientUser = sysDao.getAppPatientUserDao().findByUserId(sign.getSignPatientId());
            patientUser.setPatientjmda(patientjmda);
            patientUser.setPatientjtda(patientjtda);
            //判断该居民地址信息是否为空，为空默认赋值机构的地址
            if(StringUtils.isBlank(patientUser.getPatientStreet()) && StringUtils.isBlank(patientUser.getPatientNeighborhoodCommittee())){//取街道和居委会判断
                patientUser.setPatientProvince(AreaUtils.getAreaCode(sign.getSignAreaCode(),"1")+"0000000000");
                patientUser.setPatientCity(AreaUtils.getAreaCode(sign.getSignAreaCode(),"2")+"00000000");
                patientUser.setPatientArea(AreaUtils.getAreaCode(sign.getSignAreaCode(),"3")+"000000");
                patientUser.setPatientStreet(AreaUtils.getAreaCode(sign.getSignAreaCode(),"4")+"000");
            }

            sysDao.getServiceDo().modify(patientUser);

            //添加建档立卡数据
            if (jjlx) {
                String[] fwTypes = signPersGroup.split(";");
                sysDao.getAppSignformDao().addOrModifyArchivingSign(sign.getSignPatientIdNo(), sign.getId(), sign.getSignDrId(), sign.getSignTeamId(), sign.getSignState(), fwTypes, sign.getSignAreaCode(), sign.getSignHospId(), sign.getSignFromDate(), patientUser);
            }

            AppHealthFile file = sysDao.getAppHealthFileDao().findFileByPatientId(sign.getSignPatientId());
            if (file != null) {
                if ("0".equals(file.getHfState())) {
                    sysDao.getAppNoticeDao().addNotices("建档消息", "您已成功签约家庭医生，建议完善您的健康档案信息", NoticesType.JDXX.getValue(),
                            sign.getSignDrId(), sign.getSignPatientId(), file.getId(), DrPatientType.PATIENT.getValue());
                }
            } else {
                //查询基卫档案信息


            }
            this.SignFormNotices(sign);
            //判断服务人群是否包含儿童、孕产妇
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, sign.getSignDrId());
            if (drUser != null) {
                userType = "2";
                requestUserId = drUser.getId();
                requestUserName = drUser.getDrName();
            }
            if (StringUtils.isNotBlank(signPersGroup)) {
                String[] strs = signPersGroup.split(";");
                for (String str : strs) {
                    String codeTitle = "";
                    if (ResidentMangeType.ETLZLS.getValue().equals(str) || ResidentMangeType.YCF.getValue().equals(str)) {//儿童或孕产妇
                        String type = "";
                        String idno = "";
                        if (ResidentMangeType.ETLZLS.getValue().equals(str)) {
                            type = "1";//儿童
                            //查询母亲身份证（查询计生接口）

                            List<AppFamilyInfo> lsInfo = sysDao.getSecurityCardAsyncBean().getFetchFamily(sign.getSignPatientIdNo(), sign.getPatientName(), requestUserId, requestUserName, userType);
                            if (lsInfo != null && lsInfo.size() > 0) {
                                for (AppFamilyInfo ls : lsInfo) {
                                    if ("7".equals(ls.getRelation())) {
                                        idno = ls.getCode();
                                    }
                                }
                            }
                        } else {
                            type = "2";//孕产妇
                            idno = sign.getSignPatientIdNo();
                        }
                        AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, sign.getSignPatientId());
                        if (StringUtils.isNotBlank(user.getPatientCity())) {
                            CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                            if (p != null) {
                                String code = AreaUtils.getAreaCode(p.getCtcode(), p.getLevel());
                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, code);
                                if (value != null) {
                                    codeTitle = value.getCodeTitle();
                                }
                            }
                        }
                        sysDao.getSecurityCardAsyncBean().findChildOrWoman(sign.getId(), sign.getSignPatientId(), idno, type, codeTitle, requestUserId, requestUserName, userType);
                    }
                }
            }


        }
        return sign;
    }

    /**
     * 发送签约通知消息
     *
     * @param sign
     */
    public void SignFormNotices(AppSignForm sign) throws Exception{
        String title = "签约成功消息";
        String content = "您好，您的家庭医生签约申请已审核通过!";
        String yhContent = "您好，您的家庭医生签约申请已审核通过!";
        String type = NoticesType.QYXX.getValue() + ",0";//直接显示网页
        AppMenuRoleQvo qvo = new AppMenuRoleQvo();
        qvo.setHospId(sign.getSignHospId());
        qvo.setMenuType(AppMenuModuleRoleType.PATIENT.getValue());
        qvo.setMenuModule(menuModule);//我的
        String result = sysDao.getAppModuleRoleDao().findMenuRoleString(qvo);
        if (StringUtils.isBlank(result)) {
            qvo.setHospId(null);
            qvo.setAreaCode(sign.getSignAreaCode());
            result = sysDao.getAppModuleRoleDao().findMenuRoleString(qvo);
        }
        if (StringUtils.isNotBlank(result)) {
            if (result.indexOf(menuValue) > -1) {
                content = "您好，您的家庭医生签约申请已审核通过 ,请及时缴费!";
                type = NoticesType.QYXX.getValue() + ",1";//显示支付
            }
        }
        sysDao.getAppNoticeDao().addNotices(title, content, type, sign.getSignDrId(), sign.getSignPatientId(), sign.getId(), DrPatientType.PATIENT.getValue());

        //查询是否开启调用互联网接口
        String openState = PropertiesUtil.getConfValue("openInterNetState");
        if("1".equals(openState)) {//开启
            //调接口推送同意签约消息到互联网医院患者端
            AppInternetNewsQvo qqvo = new AppInternetNewsQvo();
            AppInternetNewsSonQvo sonQvo = new AppInternetNewsSonQvo();
            qqvo.setMsgType("YW001");
            qqvo.setTitle(title);
            qqvo.setMsgShowType("2");
            AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,sign.getSignPatientId());
            if(user != null){
                sonQvo.setType("QYSQ");
                qqvo.setType("3");
                qqvo.setUserId(user.getPatientEHCId());
                qqvo.setUserName(user.getPatientName());
                qqvo.setIdType("01");
                qqvo.setIdNo(user.getPatientIdno());
                qqvo.setPhone(user.getPatientTel());

                sonQvo.setPatientIdNo(user.getPatientIdno());
                sonQvo.setPatientCard(user.getPatientCard());
                sonQvo.setPatientName(user.getPatientName());
                sonQvo.setPatientTel(user.getPatientTel());
                sonQvo.setPatientNeighborhoodCommittee(user.getPatientNeighborhoodCommittee());
                sonQvo.setEhcId(user.getPatientEHCId());
                sonQvo.setEhcCardNo(user.getPatientEHCNo());
                sonQvo.setDeviceType("");
                qqvo.setUrlParam(sonQvo);

            }
            qqvo.setHospId(sign.getSignHospId());
            qqvo.setHospName(sign.getHosptName());
            AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,sign.getSignDrId());
            if(drUser != null){
                qqvo.setDoctorId(drUser.getId());
                qqvo.setDoctorName(drUser.getDrName());
            }
            qqvo.setContent(yhContent);
            sysDao.getSecurityCardAsyncBean().sendOutInternetNews(qqvo);
        }
    }


    public AppSerial getAppSerial(String code, String type) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder("SELECT * from APP_SERIAL a where a.SERIAL_CODE=:SERIAL_CODE and a.SERIAL_TYPE=:SERIAL_TYPE");
        map.put("SERIAL_CODE", code);
        map.put("SERIAL_TYPE", type);
        List<AppSerial> s = sysDao.getServiceDo().findSqlMap(sql.toString(), map, AppSerial.class);
        if (s != null && !s.isEmpty()) {
            return s.get(0);
        }
        return null;
    }

    public Map<String, Object> getNum(String s, String type) throws Exception{
        Map<String, Object> mm = new HashMap<>();
        String str = "";
        if (ExtendDate.getYYYYMMD(Calendar.getInstance()).equals(s.substring(1, 9))) {
            int i = Integer.valueOf(s.substring(9)) + 1;
            String y = String.valueOf(i);
            while (y.length() < 6) {
                y = "0" + y;
            }
            str = s.substring(0, 1) + ExtendDate.getYYYYMMD(Calendar.getInstance()) + y;
        } else {
            str = s.substring(0, 1) + ExtendDate.getYYYYMMD(Calendar.getInstance()) + "000001";
        }
        mm.put("old", str);
        if (SignFormType.APPSTATE.getValue().equals(type)) {//app
            mm.put("new", "APP" + str);
        } else if (SignFormType.WEBSTATE.getValue().equals(type)) {//web
            mm.put("new", "WEB" + str);
        } else if (SignFormType.WECHATSTATE.getValue().equals(type)) {
            mm.put("new", "WEIXIN" + str);
        } else if (SignFormType.YITIJISTATE.getValue().equals(type)) {
            mm.put("new", "YITIJI" + str);
        } else if (SignFormType.POSSTATE.getValue().equals(type)) {
            mm.put("new", "POS" + str);
        } else if (SignFormType.FOLLOWSTATE.getValue().equals(type)) {
            mm.put("new", "FOLLOW" + str);
        } else {
            mm.put("new", str);
        }
        return mm;
    }


    @Override
    public AppSignForm findSignFormByUser(String userId) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        String[] str = new String[]{SignFormType.YUQY.getValue(), SignFormType.DQY.getValue(), SignFormType.YQY.getValue()};
        map.put("state", str);
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_STATE IN :state AND SIGN_CONTRACT_STATE = 0 ";
        if (StringUtils.isNotBlank(userId)) {
            sql += " AND SIGN_PATIENT_ID = :userId";
            map.put("userId", userId);

        }
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 医生代签约 医生代签审核直接通过
     *
     * @param patientId                    患者id
     * @param teamid                       团队
     * @param signPersGroup                居民分组 多分号隔开
     * @param signHealthGroup              健康分布
     * @param labelGruops                  疾病类型接口 多分号隔开
     * @param drId                         医生id
     * @param patientAddress
     * @param patientProvince
     * @param patientCity
     * @param patientArea
     * @param patientStreet
     * @param patientNeighborhoodCommittee
     * @param signDrAssistantId            助理医生
     * @param signMobileType 移动端类型（1安卓 2ios）
     */
    @Override
    public AppSignForm drAgreeSignForm(String patientId, String teamid, String signPersGroup, String signHealthGroup, String labelGruops, String drId,
                                       String patientAddress, String patientProvince, String patientCity, String patientArea, String patientStreet,
                                       String patientNeighborhoodCommittee, String signsJjType, String signpackageid, String signlx,
                                       String patientjtda, String patientjmda, String signUpHpis, String signatureImageUrl, String signPhotoImageUr, String signImageUrl, String signDrAssistantId,String signMobileType) throws Exception {

        //判断服务人群是否是有问题
        if(StringUtils.isNotBlank(signPersGroup)){
            String[] pg = signPersGroup.split(";");
            if(pg.length !=1 ){//说明有多个服务人群
                if(signPersGroup.indexOf(ResidentMangeType.PTRQ.getValue()) !=-1){//多人群中含有普通人群,说明有问题
                    throw new Exception("服务人群，普通人群和其他人群不能同时存在");
                }
                if(signPersGroup.indexOf(ResidentMangeType.WEIZHI.getValue()) !=-1){//多人群中含有未知人群,说明有问题
                    throw new Exception("服务人群，未知人群和其他人群不能同时存在");
                }
            }
        }

        refuseToSign(patientId, teamid, drId);
        AppSignBatch batch = new AppSignBatch();//批次
        AppSignForm form = new AppSignForm();//签约单
//        AppTeam teamvo = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, teamid);//团队
        if (StringUtils.isNotBlank(teamid)) {
            AppTeam teamvo = sysDao.getAppTeamDao().findteamById(teamid);
            AppPatientUser uservo = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, patientId);//患者
            uservo.setPatientjtda(patientjtda);
            uservo.setPatientjmda(patientjmda);
            if (StringUtils.isNotBlank(patientProvince)) {
//            uservo.setPatientAddress(patientAddress);
                uservo.setPatientProvince(patientProvince);
            }
            if (StringUtils.isNotBlank(patientCity)) {
                uservo.setPatientCity(patientCity);
            }
            if (StringUtils.isNotBlank(patientArea)) {
                uservo.setPatientArea(patientArea);
            }
            if (StringUtils.isNotBlank(patientStreet)) {
                uservo.setPatientStreet(patientStreet);
            }
            if (StringUtils.isNotBlank(patientNeighborhoodCommittee)) {
                uservo.setPatientNeighborhoodCommittee(patientNeighborhoodCommittee);
            }
            if (StringUtils.isNotBlank(patientAddress)) {
                uservo.setPatientAddress(patientAddress);
            }
            sysDao.getServiceDo().modify(uservo);
            batch.setBatchCreateDate(Calendar.getInstance());
            batch.setBatchTeamId(teamvo.getId());
            batch.setBatchTeamName(teamvo.getTeamName());
            batch.setBatchCreatePersid(uservo.getId());
            batch.setBatchPatientName(uservo.getPatientName());
            //组织批次号
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, teamvo.getTeamHospId());
            if (dept != null) {
                AppSerial serial = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
                if (serial != null) {
                    Map<String, Object> bcnum = this.getNum(serial.getSerialNo(), SignFormType.APPSTATE.getValue());
                    serial.setSerialNo(bcnum.get("old").toString());
                    sysDao.getServiceDo().modify(serial);
                    batch.setBatchNum(bcnum.get("new").toString());//批次号
                }
            }
            //
            batch.setBatchOperatorId(drId);
            AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(drId);
            if (drUser != null) {
                batch.setBatchOperatorName(drUser.getDrName());
            }
            if (dept != null && dept.getHospAreaCode() != null) {
                batch.setBatchAreaCode(dept.getHospAreaCode());
            }
            sysDao.getServiceDo().add(batch);
            form.setSignBatchId(batch.getId());
            //组织编码
            if (dept != null && dept.getHospAreaCode() != null) {
                form.setSignHospId(dept.getId());
                form.setSignAreaCode(dept.getHospAreaCode());
                AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                if (serialSign != null) {
                    Map<String, Object> sinum = this.getNum(serialSign.getSerialNo(), SignFormType.APPSTATE.getValue());
                    serialSign.setSerialNo(sinum.get("old").toString());
                    sysDao.getServiceDo().modify(serialSign);
                    form.setSignNum(sinum.get("new").toString());//签约编码
                }
            }
            //
            form.setSignPatientId(uservo.getId());
            form.setSignDate(Calendar.getInstance());
            form.setSignPayState("0");//0：未缴费
            form.setSignType("1");//1家庭签约
            form.setSignTeamId(teamvo.getId());
            form.setSignTeamName(teamvo.getTeamName());
            form.setSignWay("2");//医生代签
//        form.setSignState("2");//已签约
            form.setSignState(SignFormType.YUQY.getValue());//预签约
            form.setSignFromDate(Calendar.getInstance());
            form.setSignPatientGender(uservo.getPatientGender());
            form.setSignPatientAge(Integer.parseInt(uservo.getPatientAge()));
            form.setSignPatientIdNo(uservo.getPatientIdno());
            form.setSignlx(signlx);
            Calendar end = Calendar.getInstance();
            end.add(Calendar.YEAR, 1);
            end.add(Calendar.DATE, -1);
            form.setSignToDate(end);
            form.setSignpackageid(signpackageid);
            if (StringUtils.isNotBlank(signpackageid)) {
                String areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode(), "2");
                if (areaCode != null) {
                    if (!npAreaCode.equals(areaCode)) {
                        String text = sysDao.getAppSignformDao().findPkRemark(signpackageid);
                        form.setSigntext(text);
                    }
                } else {
                    String text = sysDao.getAppSignformDao().findPkRemark(signpackageid);
                    form.setSigntext(text);
                }

            }
//        form.setSignsJjType(signsJjType);
            //改服务人群为多选
            if (StringUtils.isNotBlank(signPersGroup)) {
                String[] groups = signPersGroup.split(";");
                if (groups != null && groups.length > 0) {
                    form.setSignPersGroup(groups[0]);//支持之前版
                }
            }
            form.setSignHealthGroup(signHealthGroup);
            form.setSignDrId(drId);
            form.setSignContractState("0");//1是 0否
            form.setSignGreenState("0");//1是 0否
            form.setSignYellowState("0");//1是 0否
            form.setSignRedState("0");//1是 0否
            if (StringUtils.isBlank(signUpHpis)) {
                form.setUpHpis("1");
            } else {
                form.setUpHpis(signUpHpis);
            }

            //计算自费情况
            if (StringUtils.isNotBlank(form.getSignpackageid())) {//
                Map<String, Object> map = sysDao.getAppSignformDao().mealCost(form.getSignpackageid(), form, signPersGroup, signsJjType);
                if (map.get("zfpay") != null) {
                    form.setSignzfpay(map.get("zfpay").toString());
                }
                if (map.get("czpay") != null) {
                    form.setSignczpay(map.get("czpay").toString());
                }
            }
            if (StringUtils.isNotBlank(signDrAssistantId)) {//助理医生不为空
                form.setSignDrAssistantId(signDrAssistantId);//添加助理医生
            }
            form.setSignMobileType(signMobileType);

            //判断是否开启本年度签约协议
            AppSignSetting setting = sysDao.getAppSignSettingDao().findByAreaCode(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"));
            if(setting != null ){
                if("1".equals(setting.getSerOpenYear())){
                    String startDate = ExtendDate.getYYYY(Calendar.getInstance())+"-01-01";
                    String endDate = ExtendDate.getYYYY(Calendar.getInstance())+"-12-31";
                    form.setSignFromDate(ExtendDate.getCalendar(startDate));
                    form.setSignToDate(ExtendDate.getCalendar(endDate));
                }
            }
            sysDao.getServiceDo().add(form);
            //改服务人群为多选
            if (StringUtils.isNotBlank(signPersGroup)) {
                String[] groups = signPersGroup.split(";");
                if (groups != null && groups.length > 0) {
                    sysDao.getAppLabelGroupDao().addLabel(groups, form.getId(), form.getSignTeamId(), form.getSignAreaCode(), LabelManageType.FWRQ.getValue());
                }
            }
            //疾病类型
            if (StringUtils.isNotBlank(labelGruops)) {
                String[] groups = labelGruops.split(";");
                if (groups != null && groups.length > 0) {
                    sysDao.getAppLabelGroupDao().addLabel(groups, form.getId(), form.getSignTeamId(), form.getSignAreaCode(), LabelManageType.JBLX.getValue());
                }
            }
            boolean jjlxFlag = false;
            //经济类型
            if (StringUtils.isNotBlank(signsJjType)) {
                String[] jjlx = signsJjType.split(";");
                for (String jj : jjlx) {
                    if (EconomicType.JDLKPKRK.getValue().equals(jj)) {
                        jjlxFlag = true;
                    }
                }
                form.setSignsJjType(jjlx[0]);
                if (jjlx != null && jjlx.length > 0) {
                    sysDao.getAppLabelGroupDao().addLabel(jjlx, form.getId(), form.getSignTeamId(), form.getSignAreaCode(), LabelManageType.JJLX.getValue());
                }
            }
            //添加建档立卡数据
            if (jjlxFlag) {
                String[] fwTypes = signPersGroup.split(";");
                sysDao.getAppSignformDao().addOrModifyArchivingSign(form.getSignPatientIdNo(), form.getId(), form.getSignDrId(), form.getSignTeamId(), form.getSignState(), fwTypes, form.getSignAreaCode(), form.getSignHospId(), form.getSignFromDate(), uservo);
            }
            if (uservo != null) {
                if (uservo.getPatientUpHpis().equals(UserUpHpisType.JIHUO.getValue())) {
                    List<AppTeamMember> lsTeam = this.sysDao.getAppTeamMemberDao().findTeamId(form.getSignTeamId());
                    if (lsTeam != null && lsTeam.size() > 0) {
                        for (AppTeamMember v : lsTeam) {
                            this.sysDao.getSecurityCardAsyncBean().addFridenSignl(form.getSignPatientId(), v.getMemDrId());
                        }
                    }
                    if (teamvo != null) {
                        this.sysDao.getSecurityCardAsyncBean().addRoomMembers(teamvo.getTeamEaseRoomId(), form.getSignPatientId(), CommonShortType.HUANGZHE.getValue());
                    }
                }
            }
            //上传图片
            if (StringUtils.isNotBlank(signImageUrl)) {
                sysDao.getAppSignformDao().saveImage(signImageUrl, form.getId(), form.getUpHpis(), CommSF.NOT.getValue(), form.getSignAreaCode());
            }
            //pos机上传签名版和摄像头
            if (StringUtils.isNotBlank(form.getUpHpis())) {
                if (SignFormType.POSSTATE.getValue().equals(form.getUpHpis())) {
                    if(StringUtils.isNotBlank(signatureImageUrl)){
                        sysDao.getAppSignformDao().saveImage(signatureImageUrl, form.getId(), form.getUpHpis(), CommSF.YES.getValue(), form.getSignAreaCode());
//                    Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(signatureImageUrl,CommonShortType.YISHENG.getValue());
//                    form.setSignatureImageUrl(map.get("objectUrl").toString());
//                    Map<String,Object> map1 = sysDao.getIoUtils().getCtyunOosSample(signPhotoImageUr,CommonShortType.YISHENG.getValue());
//                    form.setSignPhotoImageUrl(map1.get("objectUrl").toString());
                    }
                }
            }
//        String title = "签约成功消息";
//        String content = "您好，您的家庭医生签约申请已审核通过,请及时缴费!";
//        sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), drId, uservo.getId(), form.getId(), DrPatientType.PATIENT.getValue());
            this.SignFormNotices(form);
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            if (drUser != null) {
                userType = "2";
                requestUserId = drUser.getId();
                requestUserName = drUser.getDrName();
            }
            //判断服务人群是否包含儿童、孕产妇
            if (StringUtils.isNotBlank(signPersGroup)) {
                String[] strs = signPersGroup.split(";");
                for (String str : strs) {
                    String codeTitle = "";
                    if (ResidentMangeType.ETLZLS.getValue().equals(str) || ResidentMangeType.YCF.getValue().equals(str)) {//儿童或孕产妇
                        String type = "";
                        String idno = "";
                        if (ResidentMangeType.ETLZLS.getValue().equals(str)) {
                            type = "1";//儿童

                            //查询母亲身份证（查询计生接口）
                            List<AppFamilyInfo> lsInfo = sysDao.getSecurityCardAsyncBean().getFetchFamily(form.getSignPatientIdNo(), form.getPatientName(), requestUserId, requestUserName, userType);
                            if (lsInfo != null && lsInfo.size() > 0) {
                                for (AppFamilyInfo ls : lsInfo) {
                                    if ("7".equals(ls.getRelation())) {
                                        idno = ls.getCode();
                                    }
                                }
                            }
                        } else {
                            type = "2";//孕产妇
                            idno = form.getSignPatientIdNo();
                        }
                        AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, form.getSignPatientId());
                        if (StringUtils.isNotBlank(user.getPatientCity())) {
                            CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                            if (p != null) {
                                String code = AreaUtils.getAreaCode(p.getCtcode(), p.getLevel());
                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, code);
                                if (value != null) {
                                    codeTitle = value.getCodeTitle();
                                }
                            }
                        }
                        sysDao.getSecurityCardAsyncBean().findChildOrWoman(form.getId(), form.getSignPatientId(), idno, type, codeTitle, requestUserId, requestUserName, userType);
                    }
                }
            }
        } else {
            throw new Exception("查无团队信息,无法签约!");
        }
        return form;
    }

    @Override
    public AppDrCountEntity findSignCount(String teamId, String userId) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("teamId", teamId);//团队主键
        map.put("userId", userId);//用戶主鍵
        map.put("type", "3");//类型
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("ZQYRS", signStates);
        String sqlStr = " AND t.SIGN_TEAM_ID= :teamId  "/*AND t.SIGN_DR_ID = :userId*/;

        map.put("PTRQ", ResidentMangeType.PTRQ.getValue());
        String sqlPtrq = "SELECT COUNT(1) FROM ( SELECT t.SIGN_PATIENT_ID FROM APP_SIGN_FORM t INNER JOIN APP_LABEL_GROUP C  ON t.ID = C.LABEL_SIGN_ID WHERE  C.LABEL_VALUE = :PTRQ AND c.LABEL_TYPE = :type AND t.SIGN_STATE IN (:ZQYRS) " + sqlStr + " GROUP BY t.SIGN_PATIENT_ID ) a";

        String[] mbrq = {ResidentMangeType.GXY.getValue(), ResidentMangeType.TNB.getValue(), ResidentMangeType.JHB.getValue()};
        map.put("MBRQ", mbrq);
        String sqlMbrq = "SELECT COUNT(1) FROM ( SELECT t.SIGN_PATIENT_ID FROM APP_SIGN_FORM t INNER JOIN APP_LABEL_GROUP C  ON t.ID = C.LABEL_SIGN_ID WHERE  C.LABEL_VALUE IN ( :MBRQ) AND c.LABEL_TYPE = :type AND t.SIGN_STATE IN (:ZQYRS) " + sqlStr + " GROUP BY t.SIGN_PATIENT_ID ) a";

        map.put("LNRQ", ResidentMangeType.LNR.getValue());
        String sqlLnrq = "SELECT COUNT(1) FROM ( SELECT  t.SIGN_PATIENT_ID FROM APP_SIGN_FORM t INNER JOIN APP_LABEL_GROUP C  ON t.ID = C.LABEL_SIGN_ID  WHERE C.LABEL_VALUE = :LNRQ  AND c.LABEL_TYPE = :type AND t.SIGN_STATE IN (:ZQYRS) " + sqlStr + " GROUP BY t.SIGN_PATIENT_ID ) a";


        String sqlZqyrs = "SELECT count(1) ptrq FROM APP_SIGN_FORM t WHERE t.SIGN_STATE IN (:ZQYRS) " + sqlStr;

        String[] dshrs = {SignFormType.DQY.getValue(), SignFormType.JYZ.getValue(), SignFormType.YUZQ.getValue()};
        map.put("DSHRS", dshrs);
        String sqlDshrs = "SELECT count(1) ptrq FROM APP_SIGN_FORM t WHERE t.SIGN_STATE IN ( :DSHRS) " + sqlStr + " AND t.SIGN_DR_ID = :userId ";

        //查询本市开放的经济类型
        AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, userId);
        String jjlxs = "";
        if (drUser != null) {
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
            if (dept != null) {
                if (StringUtils.isNotBlank(dept.getHospAreaCode())) {
                    String areaCode = dept.getHospAreaCode().substring(0, 4);
                    List<AppSignSetting> listSet = sysDao.getServiceDo().loadByPk(AppSignSetting.class, "signsAreaCode", areaCode);
                    if (listSet != null && listSet.size() > 0) {
                        jjlxs = listSet.get(0).getSignsJjType();
                    }
                }
            }
        }
        String sqlJstsjt = "";//计生特殊家庭
        String sqlJdlkpkrk = "";//建档立卡贫困人口
        String sqlDbh = "";//低保户
        String sqlTkh = "";//特困户
        if (StringUtils.isNotBlank(jjlxs)) {
            String[] jjlxss = jjlxs.split(";");
            for (String jjlx : jjlxss) {
                if (EconomicType.JSTSJT.getValue().equals(jjlx)) {
                    map.put("JSTSJT", EconomicType.JSTSJT.getValue());
                    sqlJstsjt = "SELECT COUNT(1) FROM ( SELECT t.SIGN_PATIENT_ID FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID WHERE b.LABEL_VALUE =:JSTSJT  AND b.LABEL_TYPE='4' AND t.SIGN_STATE IN (:ZQYRS) " + sqlStr + " GROUP BY t.SIGN_PATIENT_ID ) a";
                } else if (EconomicType.JDLKPKRK.getValue().equals(jjlx)) {
                    map.put("JDLKPKRK", EconomicType.JDLKPKRK.getValue());
                    sqlJdlkpkrk = "SELECT COUNT(1) FROM ( SELECT t.SIGN_PATIENT_ID FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID WHERE b.LABEL_VALUE =:JDLKPKRK  AND b.LABEL_TYPE='4' AND t.SIGN_STATE IN (:ZQYRS) " + sqlStr + " GROUP BY t.SIGN_PATIENT_ID ) a";
                } else if (EconomicType.DBH.getValue().equals(jjlx)) {
                    map.put("DBH", EconomicType.DBH.getValue());
                    sqlDbh = "SELECT COUNT(1) FROM ( SELECT t.SIGN_PATIENT_ID FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID WHERE b.LABEL_VALUE =:DBH  AND b.LABEL_TYPE='4' AND t.SIGN_STATE IN (:ZQYRS) " + sqlStr + " GROUP BY t.SIGN_PATIENT_ID ) a";
                } else if (EconomicType.TKH.getValue().equals(jjlx)) {
                    map.put("TKH", EconomicType.TKH.getValue());
                    sqlTkh = "SELECT COUNT(1) FROM ( SELECT t.SIGN_PATIENT_ID FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID WHERE b.LABEL_VALUE =:TKH  AND b.LABEL_TYPE='4' AND t.SIGN_STATE IN (:ZQYRS) " + sqlStr + " GROUP BY t.SIGN_PATIENT_ID ) a";
                }
            }
        }
        String sql = "SELECT (" + sqlPtrq + ")ptrq,(" + sqlMbrq + ")mbrq,(" + sqlLnrq + ")lnrq,(" + sqlZqyrs + ")zqyrs,(" + sqlDshrs + ") dshrs";
        if (StringUtils.isNotBlank(sqlJdlkpkrk)) {
            sql += ",(" + sqlJdlkpkrk + ") jdlkrcpkrq ";
        }
        if (StringUtils.isNotBlank(sqlJstsjt)) {
            sql += ",(" + sqlJstsjt + ") jstsjtrq ";
        }
        if (StringUtils.isNotBlank(sqlDbh)) {
            sql += ",(" + sqlDbh + ") dbh ";
        }
        if (StringUtils.isNotBlank(sqlTkh)) {
            sql += ",(" + sqlTkh + ") tkh ";
        }
        sql += ",'' sftz ";

        sql += " FROM DUAL ";
        List<AppDrCountEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql, map, AppDrCountEntity.class);
        if (ls != null && ls.size() > 0) {
            return ls.get(0);
        }
        return new AppDrCountEntity();
    }

    /**
     * 个人代签约
     *
     * @param patientId  代签人
     * @param patientIds 多患者id使用;号隔开
     * @param teamid     团队 代签人自己的团队
     * @param drId       医生id
     */
    @Override
    public AppSignBatch patientSignFormUser(String patientId, String patientIds, String teamid, String drId, String signpackageid, String signUpHpis, String signImageUrl) throws Exception {
        AppSignBatch batch = new AppSignBatch();//批次
        String telNum = "";
        AppTeam teamvo = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, teamid);//团队
        AppPatientUser uservo = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, patientId);//患者
        telNum = uservo.getPatientTel();
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchTeamId(teamvo.getId());
        batch.setBatchTeamName(teamvo.getTeamName());
        batch.setBatchCreatePersid(uservo.getId());
        batch.setBatchPatientName(uservo.getPatientName());
        //组织批次号
        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, teamvo.getTeamHospId());
        if (dept != null && dept.getHospAreaCode() != null) {
            AppSerial serial = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
            if (serial != null) {
                Map<String, Object> bcnum = this.getNum(serial.getSerialNo(), SignFormType.APPSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
        }
        batch.setBatchOperatorId(drId);
        AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(drId);
        if (drUser != null) {
            batch.setBatchOperatorName(drUser.getDrName());
        }
        if (dept != null && dept.getHospAreaCode() != null) {
            batch.setBatchAreaCode(dept.getHospAreaCode());
        }
        sysDao.getServiceDo().add(batch);

        //添加签约单
        String[] ps = patientIds.split(";");
        String[] tc = signpackageid.split("&");//套餐
        String[] imgss = signImageUrl.split("&");//附件
        if (ps != null && ps.length > 0 && tc != null && tc.length > 0) {
            int i = 0;
            int fnum = 0;
            for (String p : ps) {
                //先判断当前被代签人是否已经签约
                AppSignForm form = sysDao.getAppSignformDao().findSignById(p);
                if(form != null){//已有有效签约单或待签约单跳过
                    fnum++;
                    if(ps.length == 1){//如果只代一人签约且此人已经有签约单或待签约单
                        if(SignFormType.DQY.getValue().equals(form.getSignState())){
                            throw new Exception("代签约失败，该居民已有待签约数据");
                        }else{
                            throw new Exception("代签约失败，该居民已经签约");
                        }
                    }else{//多人则跳过此人
                        continue;
                    }
                }else{
                    form = new AppSignForm();
                }
                if (StringUtils.isNotBlank(signUpHpis)) {
                    form.setUpHpis(signUpHpis);
                } else {
                    form.setUpHpis("1");
                }
                AppPatientUser uservos = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, p);
                if (StringUtils.isBlank(uservos.getPatientTel())) {
                    uservos.setPatientTel(telNum);
                    sysDao.getServiceDo().modify(uservos);
                }
                form.setSignBatchId(batch.getId());
                //组织编码
                if (dept != null && dept.getHospAreaCode() != null) {
                    form.setSignHospId(dept.getId());
                    form.setSignAreaCode(dept.getHospAreaCode());
                    AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                    if (serialSign != null) {
                        Map<String, Object> sinum = this.getNum(serialSign.getSerialNo(), SignFormType.APPSTATE.getValue());
                        serialSign.setSerialNo(sinum.get("old").toString());
                        sysDao.getServiceDo().modify(serialSign);
                        form.setSignNum(sinum.get("new").toString());//签约编码
                    }
                }
                form.setSignPatientAge(Integer.parseInt(uservos.getPatientAge()));
                form.setSignPatientGender(uservos.getPatientGender());
                form.setSignPatientIdNo(uservos.getPatientIdno());
                form.setSignPatientId(p);
                form.setSignpackageid(tc[i]);
                double num = 0;
                if (StringUtils.isNotBlank(tc[i])) {
                    String[] serverIdss = tc[i].split(";");
                    for (String serverId : serverIdss) {
                        AppServeSetmeal meal = (AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class, serverId);
                        if (meal != null) {
                            if (StringUtils.isNotBlank(meal.getSersmTotalOneFee())) {
                                num += Double.parseDouble(meal.getSersmTotalOneFee());
                            }
                        }
                    }
                    String text = sysDao.getAppSignformDao().findPkRemark(tc[i]);
                    form.setSigntext(text);
                }
                form.setSignzfpay(String.valueOf(num));
                form.setSignDate(Calendar.getInstance());
                AppHospExtend extend = this.sysDao.getAppHospExtendDao().findByHospId(form.getSignHospId());
                boolean flagAgree = false;
                if (extend != null) {
                    if (extend.getExtHreState().equals(AppFlagAgree.KAIQI.getValue())) {
                        form.setSignFromDate(Calendar.getInstance());
                        Calendar end = Calendar.getInstance();
                        end.add(Calendar.YEAR, 1);
                        end.add(Calendar.DATE, -1);
                        form.setSignToDate(end);
                        form.setSignState("2");//2:已签约
                        flagAgree = true;
                    } else {
                        form.setSignState("1");//1:待签约
                    }
                } else {
                    form.setSignState("1");//1:待签约
                }
                form.setSignPayState("0");//0：未缴费
                form.setSignType("1");//1家庭签约
                form.setSignTeamId(teamvo.getId());
                form.setSignTeamName(teamvo.getTeamName());
                form.setSignWay("1");//1代表家人代签
                form.setSignDrId(drId);//绑定医生
                form.setSignContractState("0");//1是 0否
                form.setSignGreenState("0");//1是 0否
                form.setSignYellowState("0");//1是 0否
                form.setSignRedState("0");//1是 0否
                sysDao.getServiceDo().add(form);
                if (StringUtils.isNotBlank(imgss[i])) {
                    sysDao.getAppSignformDao().saveImage(imgss[i], form.getId(), form.getUpHpis(), CommSF.NOT.getValue(), form.getSignAreaCode());
                }
                if (flagAgree) {
                    //计算自费情况
                    if (StringUtils.isNotBlank(form.getSignpackageid())) {//
                        Map<String, Object> map = sysDao.getAppSignformDao().mealCost(form.getSignpackageid(), form, null, null);
                        if (map.get("zfpay") != null) {
                            form.setSignzfpay(map.get("zfpay").toString());
                        }
                        if (map.get("czpay") != null) {
                            form.setSignczpay(map.get("czpay").toString());
                        }
                        sysDao.getServiceDo().modify(form);
                    }
//                    String title = "签约成功消息";
//                    String content = "您好，您的家庭医生签约申请已审核通过!,请及时缴费!";
//                    sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), drId, uservo.getId(), form.getId(), DrPatientType.PATIENT.getValue());
                    this.SignFormNotices(form);
                } else {
                    String title = "签约消息";
                    String content = uservos.getPatientName() + "," + uservos.getStrPatientGender() + "," + uservos.getPatientAge() + "岁,于" + ExtendDate.getYMD_h_m(Calendar.getInstance()) + "申请签约!";
                    if (drId == null) {
                        List<AppTeamMember> members = sysDao.getServiceDo().loadByPk(AppTeamMember.class, "memDrId", teamid);
                        for (AppTeamMember m : members) {
                            sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), uservo.getId(), m.getMemDrId(), form.getId(), DrPatientType.DR.getValue());
                        }
                    } else {
                        sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), uservo.getId(), drId, form.getId(), DrPatientType.DR.getValue());
                    }
                }
                if (teamvo != null) {
                    this.sysDao.getSecurityCardAsyncBean().addRoomMembers(teamvo.getTeamEaseRoomId(), form.getSignPatientId(), CommonShortType.HUANGZHE.getValue());
                }
                i++;
            }
        }
        return batch;
    }

    /**
     * 更改居民分组
     */
    @Override
    public List<AppSignForm> changeGroup(AppCommQvo qvo) throws Exception {
        //修改分组应该修改当前有效签约单
        AppSignForm sign = sysDao.getAppSignformDao().getSignFormUserId(qvo.getPatientId());
        List<AppSignForm> returnList = new ArrayList<AppSignForm>();
        if(sign != null){
            if (StringUtils.isNotBlank(qvo.getSignPersGroup())) {
                String[] groups = qvo.getSignPersGroup().split(";");
                if (groups != null && groups.length > 0) {
                    sysDao.getAppLabelGroupDao().addLabel(groups, sign.getId(), sign.getSignTeamId(), sign.getSignAreaCode(), LabelManageType.FWRQ.getValue());
                }
                sign.setSignPersGroup(groups[0]);//支持之前版
            }
            if (StringUtils.isNotBlank(qvo.getSignHealthGroup())) {
                sign.setSignHealthGroup(qvo.getSignHealthGroup());
            }
            if (StringUtils.isNotBlank(qvo.getLabelGruops())) {
                String[] groups = qvo.getLabelGruops().split(";");
                if (groups != null && groups.length > 0) {//修改疾病标签
                    //groups[i] = "1|red";  String[] s1 = g.split("\\|");
                    List<AppMarkingLogItem> list = new ArrayList<>();
                    for (String disLabel : groups) {
                        String newValue = "";
                        String oldValue = "";
                        String disId = "";
                        String[] disLabels = disLabel.split("\\|");
                        if (DiseaseType.GXY.getValue().equals(disLabels[0])) {//高血压
                            AppLabelDisease disease = sysDao.getAppLabelGroupDao().findDiseaseByOne(DiseaseType.GXY.getValue(), sign.getId());
                            if (disease != null) {
                                disId = disease.getId();
                                oldValue = disease.getLabelColor();
                                if (disLabels.length > 1) {
                                    newValue = disLabels[1];
                                } else {
                                    newValue = "gray";
                                }
                            } else {
                                oldValue = "gray";
                                if (disLabels.length > 1) {
                                    newValue = disLabels[1];
                                } else {
                                    newValue = "gray";
                                }
                            }
                            if (!newValue.equals(oldValue)) {
                                AppMarkingLogItem item = new AppMarkingLogItem();
                                item.setBusinessId(disId);
                                item.setKey("labelColor");
                                item.setType(DiseaseType.GXY.getValue());
                                item.setOldValue(oldValue);
                                item.setNewValue(newValue);
                                item.setBusinessTable("APP_LABEL_DISEASE");
                                list.add(item);
                            }
                        } else if (DiseaseType.TNB.getValue().equals(disLabels[0])) {//糖尿病
                            AppLabelDisease disease = sysDao.getAppLabelGroupDao().findDiseaseByOne(DiseaseType.TNB.getValue(), sign.getId());
                            if (disease != null) {
                                disId = disease.getId();
                                oldValue = disease.getLabelColor();
                                if (disLabels.length > 1) {
                                    newValue = disLabels[1];
                                } else {
                                    newValue = "gray";
                                }
                            } else {
                                oldValue = "gray";
                                if (disLabels.length > 1) {
                                    newValue = disLabels[1];
                                } else {
                                    newValue = "gray";
                                }
                            }
                            if (!newValue.equals(oldValue)) {
                                AppMarkingLogItem item = new AppMarkingLogItem();
                                item.setBusinessId(disId);
                                item.setKey("labelColor");
                                item.setType(DiseaseType.TNB.getValue());
                                item.setOldValue(oldValue);
                                item.setNewValue(newValue);
                                item.setBusinessTable("APP_LABEL_DISEASE");
                                list.add(item);
                            }
                        }
                    }
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, sign.getSignDrId());
                    if (list != null && list.size() > 0 && drUser != null) {
                        sysDao.getAppMarkingLogDao().saveMarkingLog(sign.getSignPatientId(), "分标管理", list, drUser.getDrHospId(), drUser.getDrName(), drUser.getId());
                    }
                    sysDao.getAppLabelGroupDao().addLabel(groups, sign.getId(), sign.getSignTeamId(), sign.getSignAreaCode(), LabelManageType.JBLX.getValue());
                }
            }
            if(StringUtils.isNotBlank(qvo.getSignsJjType())){//修改经济类型标签
                boolean flag = false;
                //先判断该居民是否是建档立卡患者，是建档立卡的话，建档立卡经济类型必须有
                String jjType = sysDao.getAppLabelGroupDao().findJjValue(sign.getId());
                if(jjType != null){
                    if(jjType.indexOf(EconomicType.JDLKPKRK.getValue())!=-1){
                        flag = true;
                    }
                }
                if(flag){//原先经济类型包含建档立卡
                    //1判断传入参数是不是一般人口，是的话不做修改，返回提示
                    if(qvo.getSignsJjType().indexOf(EconomicType.YBRK.getValue())!=-1){
                        throw new Exception("该居民原先为建档立卡经济类型，不可修改为一般人口");
                    }else{//传入参数不是一般人口，判断传参是否包含建档立卡经济类型，没有做添加，有则直接修改经济类型标签
                        if(qvo.getSignsJjType().indexOf(EconomicType.JDLKPKRK.getValue())!=-1){
                            sysDao.getAppLabelGroupDao().addLabel(qvo.getSignsJjType().split(";"),sign.getId(),sign.getSignTeamId(),sign.getSignAreaCode(),LabelManageType.JJLX.getValue());
                        }else{
                            //判断传入参数最后一位是分号还是数值
                            if(qvo.getSignsJjType().substring(qvo.getSignsJjType().length()-1,qvo.getSignsJjType().length()).equals(";")){//是分号
                                qvo.setSignsJjType(qvo.getSignsJjType()+EconomicType.JDLKPKRK.getValue());
                            }else{
                                qvo.setSignsJjType(qvo.getSignsJjType()+";"+EconomicType.JDLKPKRK.getValue());
                            }
                            sysDao.getAppLabelGroupDao().addLabel(qvo.getSignsJjType().split(";"),sign.getId(),sign.getSignTeamId(),sign.getSignAreaCode(),LabelManageType.JJLX.getValue());
                        }
                    }
                }else{//原先经济类型非建档立卡可直接修改
                    sysDao.getAppLabelGroupDao().addLabel(qvo.getSignsJjType().split(";"),sign.getId(),sign.getSignTeamId(),sign.getSignAreaCode(),LabelManageType.JJLX.getValue());
                }
            }

//            else{
//                List<AppLabelDisease> list = sysDao.getServiceDo().loadByPk(AppLabelDisease.class,"labelSignId",sign.getId());
//                if(list!=null && list.size()>0){
//                    for (AppLabelDisease ll:list){
//                        sysDao.getServiceDo().delete(ll);
//                    }
//                }
//            }

//            else{
//                List<AppLabelGroup> labelList = sysDao.getServiceDo().loadByPk(AppLabelGroup.class,"labelSignId",sign.getId());
//                for(AppLabelGroup label:labelList){
//                    if(label.getLabelType().equals("2")) {
//                        sysDao.getServiceDo().delete(label);
//                    }
//                }
//            }
            sysDao.getServiceDo().modify(sign);
        }
        return returnList;
    }

    /**
     * 拒绝签约居民重新签约
     */
    public void refuseToSign(String patientId, String teamid, String drId) throws Exception{
        AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, patientId);
        AppRefuseSign refuseSign = sysDao.getAppRefuseSignDao().findByIdno(user.getPatientIdno());
        if (refuseSign != null) {
            refuseSign.setRsSignDrId(drId);
            refuseSign.setRsSignTeamId(teamid);
            AppDrUser doc = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, drId);
            AppHospDept hosp = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, doc.getDrHospId());
            refuseSign.setRsSignHospId(hosp.getId());
            refuseSign.setRsSignHospAreaCode(hosp.getHospAreaCode());
            refuseSign.setRsSignTime(Calendar.getInstance());
            sysDao.getServiceDo().modify(refuseSign);
        }
    }


    @Override
    public List<AppSignForm> findAll() throws Exception{
        return sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppSignForm.class)
                .list();
    }

    @Override
    public List<AppSignForm> findSignFormByTeam(String teamId, String signState) throws Exception{
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("teamId", teamId);
        map.put("signState", signState);
        String sql = "select * from APP_SIGN_FORM where SIGN_TEAM_ID= :teamId and SIGN_STATE = :signState ";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
    }

    @Override
    public List<AppSignForm> findByGreen() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", "2");
        map.put("greenState", CommSF.NOT.getValue());
        map.put("conState", CommSF.NOT.getValue());
        String sql = " SELECT a.* " +
                "FROM " +
                "APP_SIGN_FORM a INNER JOIN APP_HOSP_EXTEND b ON a.SIGN_HOSP_ID = b.EXT_HOSP_ID " +
                "WHERE " +
                "a.SIGN_TO_DATE <= date_sub(NOW(),interval -cast(b.EXT_GREEN_DAY AS SIGNED) day) " +
                "AND a.SIGN_STATE =:state AND a.SIGN_GREEN_STATE =:greenState AND a.SIGN_CONTRACT_STATE =:conState";
        List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        return ls;
    }

    @Override
    public List<AppSignForm> findByYellow() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", "2");
        map.put("yellowState", CommSF.NOT.getValue());
        map.put("conState", CommSF.NOT.getValue());
        String sql = " SELECT a.* " +
                "FROM " +
                "APP_SIGN_FORM a INNER JOIN APP_HOSP_EXTEND b ON a.SIGN_HOSP_ID = b.EXT_HOSP_ID " +
                "WHERE " +
                "a.SIGN_TO_DATE <= date_sub(NOW(),interval -cast(b.EXT_YELLOW_DAY AS SIGNED) day) " +
                "AND a.SIGN_STATE =:state AND a.SIGN_YELLOW_STATE =:yellowState AND a.SIGN_CONTRACT_STATE =:conState";
        List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        return ls;
    }

    @Override
    public List<AppSignForm> findByRed() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", "2");
        map.put("redState", CommSF.NOT.getValue());
        map.put("conState", CommSF.NOT.getValue());
        String sql = " SELECT a.* " +
                "FROM " +
                "APP_SIGN_FORM a INNER JOIN APP_HOSP_EXTEND b ON a.SIGN_HOSP_ID = b.EXT_HOSP_ID " +
                "WHERE " +
                "a.SIGN_TO_DATE <= date_sub(NOW(),interval -cast(b.EXT_RED_DAY AS SIGNED) day) " +
                "AND a.SIGN_STATE =:state AND a.SIGN_RED_STATE =:redState AND a.SIGN_CONTRACT_STATE =:conState";
        List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        return ls;
    }

    /**
     * 患者续签
     *
     * @param patientId
     * @param qvo       renew 是否续签原团队 1是 0否
     * @param qvo       teamId
     * @param qvo       drId
     * @return
     */
    @Override
    public AppSignForm reSignForm(String patientId, AppCommQvo qvo) throws Exception {
        AppSignBatch batch = new AppSignBatch();//批次
        AppSignForm form = new AppSignForm();//签约单
        //查询上一个签约单
        AppSignForm oldForm = sysDao.getAppSignformDao().findSignFormByUser(patientId);//已签约单
        if (oldForm == null) {
            throw new DaoException(this.getClass(), "当前患者未签约");
        }
        String teamId = oldForm.getSignTeamId();
        String drId = oldForm.getSignDrId();
        if (CommonEnable.JINYONG.getValue().equals(qvo.getRenew())) {
            teamId = qvo.getTeamId();
            drId = qvo.getDrId();
        }
        AppTeam teamvo = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, teamId);//团队
        AppPatientUser uservo = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, patientId);//患者
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchTeamId(teamvo.getId());
        batch.setBatchTeamName(teamvo.getTeamName());
        batch.setBatchCreatePersid(uservo.getId());
        batch.setBatchPatientName(uservo.getPatientName());
        //组织批次号
        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, teamvo.getTeamHospId());
        if (dept != null && dept.getHospAreaCode() != null) {
            AppSerial serial = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
            if (serial != null) {
                Map<String, Object> bcnum = this.getNum(serial.getSerialNo(), SignFormType.APPSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
        }
        //
        batch.setBatchOperatorId(drId);
        AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(drId);
        if (drUser != null) {
            batch.setBatchOperatorName(drUser.getDrName());
        }
        //组织编码
        if (dept != null && dept.getHospAreaCode() != null) {
            batch.setBatchAreaCode(dept.getHospAreaCode());
        }
        sysDao.getServiceDo().add(batch);
        form.setSignBatchId(batch.getId());
        //组织编码
        if (dept != null && dept.getHospAreaCode() != null) {
            form.setSignHospId(dept.getId());
            form.setSignAreaCode(dept.getHospAreaCode());
            AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
            if (serialSign != null) {
                Map<String, Object> sinum = this.getNum(serialSign.getSerialNo(), SignFormType.APPSTATE.getValue());
                serialSign.setSerialNo(sinum.get("old").toString());
                sysDao.getServiceDo().modify(serialSign);
                form.setSignNum(sinum.get("new").toString());//签约编码
            }
        }
        //
        form.setSignPatientId(patientId);
        form.setSignDate(Calendar.getInstance());
        AppHospExtend extend = this.sysDao.getAppHospExtendDao().findByHospId(teamvo.getTeamHospId());
        boolean flagAgree = false;
        if (extend != null) {
            if (extend.getExtHreState().equals(AppFlagAgree.KAIQI.getValue())) {//是否开启同意签约
                form.setSignState("2");//2:已签约
                flagAgree = true;
                oldForm.setSignContractState("1");//是否已续约 1是 0否
                sysDao.getServiceDo().modify(oldForm);
            } else {
                form.setSignState("1");//1:待签约
            }
        } else {
            form.setSignState("1");//1:待签约
        }
        form.setSignPayState("0");//0：未缴费
        form.setSignType(oldForm.getSignType());//1家庭签约
        form.setSignTeamId(teamId);
        form.setSignTeamName(teamvo.getTeamName());
        form.setSignWay("0");//个人发起签约
        form.setSignPatientAge(Integer.parseInt(uservo.getPatientAge()));
        form.setSignPatientGender(uservo.getPatientGender());
        form.setSignPatientIdNo(uservo.getPatientIdno());
        form.setSignDrId(drId);//绑定医生
        form.setSignContractState("0");//是否已续约 1是 0否
        form.setSignGreenState("0");//1是 0否
        form.setSignYellowState("0");//1是 0否
        form.setSignRedState("0");//1是 0否
        sysDao.getServiceDo().add(form);
        if (flagAgree) {
            form.setSignDate(Calendar.getInstance());
            Calendar end = Calendar.getInstance();
            end.add(Calendar.YEAR, 1);
            end.add(Calendar.DATE, -1);
            form.setSignToDate(end);
            if (CommonEnable.QIYONG.getValue().equals(qvo.getRenew())) {//续签原团队
                form.setSignPersGroup(oldForm.getSignPersGroup());
                form.setSignHealthGroup(oldForm.getSignHealthGroup());
                List<AppLabelGroup> labelList = sysDao.getServiceDo().loadByPk(AppLabelGroup.class, "labelSignId", oldForm.getId());
                for (AppLabelGroup g : labelList) {
                    AppLabelGroup alg = new AppLabelGroup();
                    alg.setLabelId(g.getLabelId());
                    alg.setLabelSignId(form.getId());
                    alg.setLabelTeamId(teamId);
                    alg.setLabelTitle(g.getLabelTitle());
                    alg.setLabelValue(g.getLabelValue());
                    alg.setLabelType(g.getLabelType());
                    alg.setLabelColor(g.getLabelColor());
                    alg.setLabelAreaCode(form.getSignAreaCode());
                    sysDao.getServiceDo().add(alg);
                }
            } else {
                List<AppTeamMember> lsTeam = this.sysDao.getAppTeamMemberDao().findTeamId(form.getSignTeamId());
                if (lsTeam != null && lsTeam.size() > 0) {
                    for (AppTeamMember v : lsTeam) {
                        this.sysDao.getSecurityCardAsyncBean().addFridenSignl(form.getSignPatientId(), v.getMemDrId());
                    }
                }
            }
            if (teamvo != null) {
                this.sysDao.getSecurityCardAsyncBean().addRoomMembers(teamvo.getTeamEaseRoomId(), form.getSignPatientId(), CommonShortType.HUANGZHE.getValue());
            }
            String title = "签约成功消息";
            String content = "您好，您的家庭医生续签已审核通过,请及时缴费!";
            sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue() + "," + NoticesMType.XQXX.getValue(), drId, patientId, form.getId(), DrPatientType.PATIENT.getValue());
        } else {
            String title = "续签提醒消息";
            String content = uservo.getPatientName() + "," + uservo.getStrPatientGender() + "," + uservo.getPatientAge() + "于" + ExtendDate.getYMD_h_m(Calendar.getInstance()) + "申请续签!";
            if (oldForm.getSignDrId() == null) {
                List<AppTeamMember> members = sysDao.getServiceDo().loadByPk(AppTeamMember.class, "memDrId", oldForm.getSignTeamId());
                for (AppTeamMember m : members) {
                    sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue() + "," + NoticesMType.XQXX.getValue(), uservo.getId(), m.getMemDrId(), form.getId(), DrPatientType.DR.getValue());
                }
            } else {
                sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue() + "," + NoticesMType.XQXX.getValue(), uservo.getId(), oldForm.getSignDrId(), form.getId(), DrPatientType.DR.getValue());
            }
        }
        sysDao.getServiceDo().modify(form);
        return form;
    }

    @Override
    public AppSignForm getSignFormUserId(String userId) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_STATE IN ('0','2')";
        if (StringUtils.isNotBlank(userId)) {
            sql += " AND SIGN_PATIENT_ID = :userId";
            map.put("userId", userId);
        }
        // sql += " AND SIGN_FROM_DATE <=SYSDATE() AND SIGN_TO_DATE >SYSDATE() ";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询签约信息
     *
     * @param userId
     * @return
     */
    @Override
    public AppDrPatientSignEntity findPatient(String userId, String signState) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        String sql = "SELECT " +
                "a.ID id," +
                "b.ID signId," +
                "a.PATIENT_NAME signName," +
                "a.PATIENT_IDNO signIdNo," +
                "a.PATIENT_CARD signCard," +
                "a.PATIENT_TEL signTel," +
                "a.PATIENT_IMAGEURL signImage," +
                "a.PATIENT_GENDER gender," +
                "DATE_FORMAT(b.SIGN_DATE, '%Y-%c-%d %H:%i:%s') signTime," +
                "'' signAge," +
                "'' signAddr," +
                "(SELECT g.SIGNS_WORK_TYPE FROM APP_SIGN_SETTING g WHERE left(b.SIGN_AREA_CODE,4) = g.SIGNS_AREA_CODE) signWorkType," +
                "(SELECT g.SIGNS_SUBSIDY_TYPE FROM APP_SIGN_SETTING g WHERE left(b.SIGN_AREA_CODE,4) = g.SIGNS_AREA_CODE) signPayType," +
                "'' signPayTypeName," +
                "(SELECT g.SIGNS_FREE FROM APP_SIGN_SETTING g WHERE left(b.SIGN_AREA_CODE,4) = g.SIGNS_AREA_CODE) signFee," +
                "(SELECT g.SIGNS_SERVER_TYPE FROM APP_SIGN_SETTING g WHERE left(b.SIGN_AREA_CODE,4) = g.SIGNS_AREA_CODE) fwrq," +
                "(SELECT g.SIGNS_SER_VALUE FROM APP_SIGN_SETTING g WHERE left(b.SIGN_AREA_CODE,4) = g.SIGNS_AREA_CODE) fwb," +
                "(SELECT g.SIGNS_JJ_TYPE FROM APP_SIGN_SETTING g WHERE left(b.SIGN_AREA_CODE,4) = g.SIGNS_AREA_CODE) jjlx," +
                "b.SIGN_TEAM_ID signTeamId," +
                "b.SIGN_TEAM_NAME signTeamName," +
                "'' drList, " +
                "a.PATIENT_CITY cityCode," +
                "b.SIGN_PACKAGEID serveList " +
                "FROM APP_PATIENT_USER a INNER JOIN APP_SIGN_FORM b ON a.ID = b.SIGN_PATIENT_ID " +
                "WHERE a.ID =:userId";
        if (StringUtils.isNotBlank(signState)) {
            map.put("SIGN_STATE", signState);
            sql += " AND b.SIGN_STATE = :SIGN_STATE ";
        } else {
            String[] signStates = new String[]{SignFormType.YUZQ.getValue(), SignFormType.DQY.getValue()};
            map.put("SIGN_STATE", signStates);
            sql += " AND b.SIGN_STATE IN:SIGN_STATE ";
        }
        List<AppDrPatientSignEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppDrPatientSignEntity.class);
        if (ls != null && ls.size() > 0) {
            return ls.get(0);
        }
        return null;
    }

    /**
     * 查询待签约记录
     *
     * @param userId
     * @return
     */
    @Override
    public AppSignForm findSignOne(String userId) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("state", SignFormType.DQY.getValue());
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_PATIENT_ID=:userId AND SIGN_STATE=:state";
        List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (ls != null && ls.size() > 0) {
            return ls.get(0);
        }
        return null;
    }

    /**
     * 查询居民服务类型
     *
     * @param userId
     * @param
     * @param
     * @throws Exception
     */
    @Override
    public AppDrPatientFwEntity findFwType(String userId, String cityCode, String requestUserId, String requestUserName, String userType) throws Exception {
        AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, userId);
        if (user != null) {
            String state = PropertiesUtil.getConfValue("openTheInterface");
            CloseableHttpClient client = HttpClients.createDefault();
            if(AddressType.ND.getValue().equals(cityCode)){
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("idNo",user.getPatientIdno());
                jsonParam.put("urlType", cityCode);
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getHealthCareRecords";
                String str = null;
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                System.out.println(str);
                if (StringUtils.isNotBlank(str)) {
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")) {
                        if (jsonall.get("entity") != null) {
                            if (!jsonall.get("entity").toString().equals("null")) {
                                JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                if(entity != null && "800".equals(entity.get("msgCode").toString())) {
                                    List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(), new TypeToken<List<AppEnterpatientEntity>>() {
                                    }.getType());
                                    if (list != null && list.size() > 0) {
                                        AppEnterpatientEntity vv = list.get(0);
                                        com.ylz.bizDo.jtapp.basicHealthEntity.AppDrPatientFwEntity fwVo= vv.getPersGroup();
                                        if(fwVo != null){
                                            AppDrPatientFwEntity ls = new AppDrPatientFwEntity();
                                            ls.setIs06child(fwVo.getIs06child());
                                            ls.setIslnr(fwVo.getIslnr());
                                            ls.setIsycf(fwVo.getIsycf());
                                            ls.setIsgxy(fwVo.getIsgxy());
                                            ls.setIstnb(fwVo.getIstnb());
                                            ls.setIsjhb(fwVo.getIsjhb());
                                            ls.setIscjr(fwVo.getIscjr());
                                            ls.setIszxjsb(fwVo.getIszxjsb());
                                            ls.setComservice(fwVo.getComservice());
                                            return ls;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }else{
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("idNo", user.getPatientIdno());
                jsonParam.put("urlType", cityCode);
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findFwType";
                String str = null;
                if (state.equals(OpenTheInterfaceState.NOT.getValue())) {
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                } else {
                    str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId, requestUserName, jsonParam.toString(), user.getPatientIdno(), "findFwType");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(), requestUserId, requestUserName, str, userType, "findFwType");
                if (StringUtils.isNotBlank(str)) {
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")) {
                        AppDrPatientFwEntity ls = JSON.parseObject(jsonall.get("vo").toString(), AppDrPatientFwEntity.class);
                        return ls;
                    }
                }
            }

        }
        return null;
    }

    /**
     * 根据个人姓名、身份证、电话查询签约信息
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSMSignEntity findSignXx(AppPatientQyQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("state", signStates);
        String sql = "SELECT " +
                "a.SIGN_DR_ID drId," +
                "'' drCode," +
                "'' drTel," +
                "'' drName," +
                "'' drSex," +
                "a.SIGN_HOSP_ID hospId," +
                "'' hospName," +
                "(SELECT GROUP_CONCAT(LABEL_VALUE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) fwType," +
                "'' fwbName," +
                "'' zypj," +
                "'' fwpj," +
                "'' pjList " +
                "FROM APP_SIGN_FORM a INNER JOIN APP_PATIENT_USER b ON a.SIGN_PATIENT_ID = b.ID WHERE 1=1 AND a.SIGN_STATE IN (:state)";
        if (StringUtils.isNotBlank(qvo.getName())) {
            map.put("name", qvo.getName());
            sql += " AND b.PATIENT_NAME =:name";
        }
        if (StringUtils.isNotBlank(qvo.getIdNo())) {
            map.put("idNo", qvo.getIdNo());
            sql += " AND b.PATIENT_IDNO = :idNo";
        }
        if (StringUtils.isNotBlank(qvo.getTel())) {
            map.put("tel", qvo.getTel());
            sql += " AND b.PATIENT_TEL =:tel";
        }
        List<AppSMSignEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppSMSignEntity.class);
        if (ls != null && ls.size() > 0) {
            return ls.get(0);
        }
        return null;
    }


    /**
     * 根据患者主键查询签约单
     *
     * @return
     */
    @Override
    public List<AppSignForm> findByPatientId(String patientId) throws Exception{
        return sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppSignForm.class)
                .add(Restrictions.eq("signPatientId", patientId))
                .list();
    }


    @Override
    public AppSignForm findSignFormByUserState(String userId) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("state", signStates);
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_PATIENT_ID=:userId AND SIGN_STATE IN (:state)";
        List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (ls != null && ls.size() > 0) {
            return ls.get(0);
        }
        return null;
    }

    /**
     * @param patientId
     * @param signDelType
     * @param signDelReason
     * @param signDieDate
     */
    @Override
    public void signDelPatient(String patientId, String signDelType, String signDelReason, String signDieDate) throws Exception{
        AppSignForm form = this.findSignFormByUserState(patientId);
        if (form != null) {
            form.setSignState(SignFormType.SC.getValue());
            if (signDelType.equals(SignFormDelType.SW.getValue())) {
                form.setSignDieDate(ExtendDate.getCalendar(signDieDate));
            }
            form.setSignDelType(signDelType);
            form.setSignDelDate(Calendar.getInstance());
            form.setSignDelReason(signDelReason);
            this.sysDao.getServiceDo().modify(form);
        }
    }

    @Override
    public AppSignForm findBySignweb(String patientId, String teamid, String drId) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", "2");
        map.put("yellowState", CommSF.NOT.getValue());
        map.put("conState", CommSF.NOT.getValue());
        String sql = " ";
        List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if(ls != null && ls.size() >0){
            return ls.get(0);
        }
        return null;
    }

    /**
     * 申请变更
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String changeState(YsChangeCountQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("state", signStates);
        map.put("drId", qvo.getDrId());
        String sql = "";
        if ("0".equals(qvo.getChangeType())) { //全选医生在这个团队下所签的患者.
            map.put("teamId", qvo.getTeamId());
            sql = " SELECT * FROM APP_SIGN_FORM WHERE SIGN_STATE IN (:state) " +
                    "AND SIGN_DR_ID=:drId AND SIGN_TEAM_ID =:teamId AND SIGN_FROM_DATE<=SYSDATE() AND SIGN_TO_DATE >SYSDATE()";
        } else if ("1".equals(qvo.getChangeType())) {
            if (StringUtils.isBlank(qvo.getPatientId())) {
                return "false";
            }
            map.put("patientId", qvo.getPatientId().split(";"));
            sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_STATE IN (:state) " +
                    "AND SIGN_DR_ID=:drId  " +
                    "AND SIGN_PATIENT_ID IN (:patientId)";
            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                map.put("teamId", qvo.getTeamId());
                sql += " AND SIGN_TEAM_ID =:teamId";
            }
        }/*else{
            //全选服务人群、疾病类型、健康情况中的一种
            if(StringUtils.isNotBlank(qvo.getLabelType())){
                String[] labelTypes = qvo.getLabelType().split(";");
                if(labelTypes!=null && labelTypes.length>0){
                    map.put("labelType",labelTypes);
                    sql = "SELECT\n" +
                            "\ta.*\n" +
                            "FROM\n" +
                            "\tAPP_SIGN_FORM a\n" +
                            "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                            "WHERE\n" +
                            "\tb.LABEL_TYPE IN :labelType\n" +
                            "AND a.SIGN_STATE =:state'\n" +
                            "AND a.SIGN_TEAM_ID =:teamId\n" +
                            "AND a.SIGN_DR_ID =:drId\n" +
                            "GROUP BY\n" +
                            "\ta.ID";
                }
            }else{
                return "false";
            }
        }*/
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (list != null && list.size() > 0) {
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, qvo.getHospId());
            Map<String, Object> sinum = new HashMap<>();
            if (dept != null) {
                AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                if (serialSign != null) {
                    sinum = this.getNum(serialSign.getSerialNo(), SignFormType.APPSTATE.getValue());
                    serialSign.setSerialNo(sinum.get("old").toString());
                    sysDao.getServiceDo().modify(serialSign);
                }
            }
            for (AppSignForm ls : list) {
                ls.setSignChangeState(ChangeState.SQBG.getValue());
                ls.setSignChangeDr(qvo.getChangeDr());
                ls.setSignChangeTeam(qvo.getChangeTeam());
                ls.setSignChangeDate(Calendar.getInstance());
                sysDao.getServiceDo().modify(ls);
                AppSignChange sc = new AppSignChange();
                sc.setChangeDate(Calendar.getInstance());
                sc.setChangeDrId(qvo.getChangeDr());
                sc.setChangeTeamId(qvo.getChangeTeam());
                sc.setChangeDr(qvo.getDrId());
                sc.setChangeTeam(qvo.getTeamId());
                sc.setChangeNum(sinum.get("new").toString());
                sc.setChangeSignId(ls.getId());
                sc.setChangeUserId(ls.getSignPatientId());
                sc.setChangeState(ChangeState.SQBG.getValue());
                sysDao.getServiceDo().add(sc);
                System.out.println(sc.getId());
            }
            //消息提示 qvo.getDrId()申请医生 qvo.getChangeDr() 变更医生
            sysDao.getAppNoticeDao().addNotices("变更消息提醒", "您有一个变更申请，请注意查看", NoticesType.XTXX.getValue(), qvo.getDrId(),
                    qvo.getChangeDr(), sinum.get("new").toString(), DrPatientType.DR.getValue());
            return "true";
        } else {
            return "false";
        }

    }

    /**
     * 变更审核
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<YsChangeMsgEntity> findChangeList(YsChangeCountQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("drId", qvo.getDrId());
        map.put("state", ChangeState.SQBG.getValue());
        /*String sqlCount = "SELECT count(1) FROM APP_SIGN_FORM " +
                "WHERE SIGN_CHANGE_DR=:drId ";*/
        String sql1 = "SELECT  " +
                "(SELECT COUNT(1) FROM APP_SIGN_CHANGE WHERE CHANGE_DR_ID =:drId " +
                " AND CHANGE_NUM = a.CHANGE_NUM) count," +
                "DATE_FORMAT(a.CHANGE_DATE, '%Y-%m-%d %H:%i:%s') time," +
                "DATE_FORMAT(a.CHANGE_AGREE_DATE, '%Y-%m-%d %H:%i:%s') agreeTime," +
                "a.CHANGE_STATE changeState," +
                "a.CHANGE_DR_ID changeDrId," +
                "a.CHANGE_TEAM_ID changeTeamId," +
                "a.CHANGE_DR drId," +
                "a.CHANGE_NUM num," +
                "'2' type," +
                "a.CHANGE_TEAM teamId " +
                "FROM APP_SIGN_CHANGE a WHERE " +
                "a.CHANGE_DR_ID =:drId  " +
                "AND a.CHANGE_STATE =:state " +
                "GROUP BY a.CHANGE_NUM ";
        /*String sql2 = "SELECT  " +
                "(SELECT COUNT(1) FROM APP_SIGN_CHANGE WHERE CHANGE_DR =:drId " +
                " AND CHANGE_NUM = a.CHANGE_NUM) count," +
                "DATE_FORMAT(a.CHANGE_DATE, '%Y-%m-%d %H:%i:%s') time," +
                "DATE_FORMAT(a.CHANGE_AGREE_DATE, '%Y-%m-%d %H:%i:%s') agreeTime," +
                "a.CHANGE_STATE changeState," +
                "a.CHANGE_DR_ID changeDrId," +
                "a.CHANGE_TEAM_ID changeTeamId," +
                "a.CHANGE_DR drId," +
                "a.CHANGE_NUM num," +
                "'1' type," +
                "a.CHANGE_TEAM teamId " +
                "FROM APP_SIGN_CHANGE a WHERE " +
                "a.CHANGE_DR =:drId  " +
                "AND a.CHANGE_STATE =:state " +
                "GROUP BY a.CHANGE_NUM ";*/
//        String sql = " SELECT * FROM (" + sql1 + " UNION " + sql2 + ") c ORDER BY c.time DESC";
        List<YsChangeMsgEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql1, map, YsChangeMsgEntity.class);
        List<YsChangeMsgEntity> lsss = new ArrayList<YsChangeMsgEntity>();
        String num = "";
        List<AppWarningSetting> lss = sysDao.getServiceDo().loadByPk(AppWarningSetting.class, "wsType", "22");
        if (lss != null && lss.size() > 0) {
            num = lss.get(0).getWsNum();
            if (ls != null && ls.size() > 0) {
                for (YsChangeMsgEntity s : ls) {
                    if (ChangeState.SQBG.getValue().equals(s.getChangeState())) {
                        Calendar dd = ExtendDate.getCalendar(s.getTime());
                        dd.add(Calendar.DATE, Integer.parseInt(num));
                        Calendar date = Calendar.getInstance();
                        int nn = date.compareTo(dd);
                        if (nn > 0) {
                            s.setChangeState(ChangeState.BGGQ.getValue());
                            List<AppSignChange> ll = sysDao.getServiceDo().loadByPk(AppSignChange.class, "changeNum", s.getNum());
                            if (ll != null && ll.size() > 0) {
                                for (AppSignChange l : ll) {
                                    l.setChangeState(ChangeState.BGGQ.getValue());
                                    sysDao.getServiceDo().modify(l);
                                }
                            }
                        } else {
                            lsss.add(s);
                        }
                    }
                }
            }
        }
        return lsss;
    }

    /**
     * 同意或拒绝变更
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String sureChange(YsChangeSureQvo qvo) throws Exception {
        String signId = "";
        List<AppSignChange> lscs = sysDao.getServiceDo().loadByPk(AppSignChange.class, "changeNum", qvo.getNum());
        String teamName = "";
        String drId = "";
        if (lscs != null && lscs.size() > 0) {
            for (AppSignChange lsc : lscs) {
                if (StringUtils.isBlank(teamName)) {
                    AppTeam team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, lsc.getChangeTeamId());
                    if (team != null) {
                        teamName = team.getTeamName();
                    }
                }
                if (StringUtils.isBlank(drId)) {
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, lsc.getChangeDr());
                    if (drUser != null) {
                        drId = drUser.getId();
                        if(StringUtils.isBlank(qvo.getChangeOperatorId())){
                            qvo.setChangeOperatorId(drId);
                            qvo.setChangeOperatorName(drUser.getDrName());
                        }
                    }
                }
                AppSignForm s = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, lsc.getChangeSignId());
                if (StringUtils.isNotBlank(lsc.getChangeSignId())) {
                    signId += lsc.getChangeSignId() + ";";
                }
                if ("1".equals(qvo.getChangeType())) {//同意
                    lsc.setChangeState(ChangeState.TYBG.getValue());
                    lsc.setChangeAgreeDate(Calendar.getInstance());
                    sysDao.getServiceDo().modify(lsc);
                    if (s != null) {
                        String signstate = s.getSignState(); //先定义 存签约状态 0 或2
                        s.setSignState(SignFormType.YJY.getValue());
                        sysDao.getServiceDo().modify(s);
                        AppSignForm ss = new AppSignForm();
                        AppTeam team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, lsc.getChangeTeamId());
                        s.setSignChangeState(ChangeState.TYBG.getValue());
                        s.setSignState(SignFormType.BG.getValue());
                        sysDao.getServiceDo().modify(s);
                        AppSignBatch batch = new AppSignBatch();//批次
                        batch.setBatchCreateDate(Calendar.getInstance());
                        batch.setBatchTeamId(lsc.getChangeTeamId());
                        batch.setBatchTeamName(team.getTeamName());
                        batch.setBatchCreatePersid(s.getSignPatientId());
                        batch.setBatchPatientName(s.getPatientName());
                        //组织批次号
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, team.getTeamHospId());
                        if (dept != null) {
                            AppSerial serial = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
                            if (serial != null) {
                                Map<String, Object> bcnum = this.getNum(serial.getSerialNo(), qvo.getUpHpis());
                                serial.setSerialNo(bcnum.get("old").toString());
                                sysDao.getServiceDo().modify(serial);
                                batch.setBatchNum(bcnum.get("new").toString());//批次号
                            }
                        }
                        //batch.setBatchOperatorId(drId);

                        batch.setBatchOperatorId(qvo.getBatchOperatorId());
//                        AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(drId);
//                        if (drUser != null) {
//                            batch.setBatchOperatorName(drUser.getDrName());
//                        }
                        batch.setBatchOperatorName(qvo.getBatchOperatorName());
                        batch.setBatchAreaCode(s.getSignAreaCode());

                        //签约操作人还是原来变更前
                        //根据签约单查询签约批次表
                        AppSignBatch oldBatch = (AppSignBatch) sysDao.getServiceDo().find(AppSignBatch.class,s.getSignBatchId());
                        if(oldBatch != null){
                            batch.setBatchOperatorId(oldBatch.getBatchOperatorId());
                            batch.setBatchOperatorName(oldBatch.getBatchOperatorName());
                        }
                        //添加变更操作人
                        batch.setBatchChangeOperatorId(qvo.getChangeOperatorId());
                        batch.setBatchChangeOperatorName(qvo.getChangeOperatorName());
                        sysDao.getServiceDo().add(batch);
                        ss.setSignState(signstate);
                        ss.setSignFee(s.getSignFee());
                        ss.setSignPayType(s.getSignPayType());
                        ss.setSignYellowState(s.getSignYellowState());
                        ss.setSignGreenState(s.getSignGreenState());
                        ss.setSignAreaCode(s.getSignAreaCode());
                        ss.setSignContractState(s.getSignContractState());
//                        cal.add(Calendar.MINUTE, x);
                        //默认加5分钟,防止解约后再申请签约判断返回变更记录
                        Calendar cal = s.getSignDate();
                        cal.add(Calendar.MINUTE,5);
                        ss.setSignDate(cal);

                        ss.setSignDelDate(s.getSignDelDate());
                        ss.setSignDelReason(s.getSignDelReason());
                        ss.setSignDelType(s.getSignDelType());
                        ss.setSignDieDate(s.getSignDieDate());
                        ss.setSignDrId(lsc.getChangeDrId());
                        ss.setSignFromDate(s.getSignFromDate());
                        ss.setSignHealthGroup(s.getSignHealthGroup());
                        ss.setSignHospId(s.getSignHospId());
                        //组织编码
                        AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                        if (serialSign != null) {
                            Map<String, Object> sinum = this.getNum(serialSign.getSerialNo(), qvo.getUpHpis());
                            serialSign.setSerialNo(sinum.get("old").toString());
                            sysDao.getServiceDo().modify(serialSign);
                            ss.setSignNum(sinum.get("new").toString());//签约编码
                        }
                        ss.setSignOthnerReason(s.getSignOthnerReason());
                        ss.setSignPatientAge(s.getSignPatientAge());
                        ss.setSignPatientGender(s.getSignPatientGender());
                        ss.setSignPatientIdNo(s.getSignPatientIdNo());
                        ss.setSignPatientId(s.getSignPatientId());
                        ss.setSignPayState(s.getSignPayState());
                        ss.setSignPersGroup(s.getSignPersGroup());
                        ss.setSignRedState(s.getSignRedState());
                        ss.setSignServiceA(s.getSignServiceA());
                        ss.setSignServiceADate(s.getSignServiceADate());
                        ss.setSignServiceAPayState(s.getSignServiceAPayState());
                        ss.setSignServiceB(s.getSignServiceB());
                        ss.setSignServiceBColor(s.getSignServiceBColor());
                        ss.setSignServiceBDate(s.getSignServiceBDate());
                        ss.setSignServiceBPayState(s.getSignServiceBPayState());
                        ss.setSignsJjType(s.getSignsJjType());
                        ss.setSignSurrenderDate(s.getSignSurrenderDate());
                        ss.setSignTeamId(lsc.getChangeTeamId());
                        if (team != null) {
                            ss.setSignTeamName(team.getTeamName());
                        }
                        ss.setSignToDate(s.getSignToDate());
                        ss.setSignType(s.getSignType());
                        ss.setSignUrrenderReason(s.getSignUrrenderReason());
                        ss.setSignUrrenderReasonPatient(s.getSignUrrenderReasonPatient());
                        ss.setSignWay(s.getSignWay());
                        ss.setUpHpis(qvo.getUpHpis());
//                        ss.setUpHpis(s.getUpHpis());
                        ss.setSignBatchId(batch.getId());
                        // 新字段
                        ss.setSignDrAssistantId(qvo.getChangeDrAssistantId());
                        ss.setSignWebState(s.getSignWebState());
                        ss.setSignlx(s.getSignlx());
                        ss.setSignczpay(s.getSignczpay());
                        ss.setSignzfpay(s.getSignzfpay());
                        ss.setSigntext(s.getSigntext());
                        ss.setSignpackageid(s.getSignpackageid());

                        sysDao.getServiceDo().add(ss);
                        //变更成功修改建档立卡花名册中的人员签约信息
                        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,ss.getSignPatientId());
                        if(user != null){
                            String sourceType = "1";//民政数据
                            CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(ss.getSignAreaCode(),"2"));
                            if(cityCode != null){
                                if("0".equals(cityCode.getCodeTitle())) {//查询医保数据
                                    sourceType = "3";
                                }
                            }
                            //根据身份证查询是否是建档立卡
                            AppArchivingCardPeople people = sysDao.getAppArchivingCardPeopeDao().findPeopleByIdno(user.getPatientIdno(),sourceType);
                            if(people != null){
                                String[] fwTypes = null;
                                String fwType = sysDao.getAppLabelGroupDao().findFwValue(s.getId());
                                if(StringUtils.isNotBlank(fwType)){
                                    fwTypes = fwType.split(",");
                                }
                                sysDao.getAppSignformDao().addOrModifyArchivingSign(ss.getSignPatientIdNo(),ss.getId(),ss.getSignDrId(),ss.getSignTeamId(),ss.getSignState(),fwTypes,ss.getSignAreaCode(),ss.getSignHospId(),ss.getSignFromDate(),user);
                            }
                        }
                        //变更前的签约单若有生成过签约报告的话、新生成的签约单保留原有的健康评估报告、若已经生成的话无需再次生成报告、让之前的报告跟新的签约单一一对应、add by WangCheng
//                        Criteria criteria = sysDao.getServiceDo().getSessionFactory().getCurrentSession().createCriteria(AppHealthReport.class);
//                        criteria.add(Restrictions.eq("signLableId",s.getId()));
//                        AppHealthReport appHealthReport = (AppHealthReport)criteria.uniqueResult();
//                        if(appHealthReport != null){
//                            appHealthReport.setSignLableId(ss.getId());
//                            sysDao.getServiceDo().modify(appHealthReport);
//                        }

                        List<AppLabelGroup> list = sysDao.getServiceDo().loadByPk(AppLabelGroup.class, "labelSignId", s.getId());
                        if (list != null) {
                            for (AppLabelGroup ll : list) {
                                AppLabelGroup lg = new AppLabelGroup();
                                lg.setLabelColor(ll.getLabelColor());
                                lg.setLabelId(ll.getLabelId());
                                lg.setLabelSignId(ss.getId());
                                lg.setLabelTeamId(ss.getSignTeamId());
                                lg.setLabelTitle(ll.getLabelTitle());
                                lg.setLabelType(ll.getLabelType());
                                lg.setLabelValue(ll.getLabelValue());
                                lg.setLabelAreaCode(ss.getSignAreaCode());
                                sysDao.getServiceDo().add(lg);
                            }
                        }
                        List<AppLabelDisease> listD = sysDao.getServiceDo().loadByPk(AppLabelDisease.class, "labelSignId", s.getId());
                        if (listD != null && listD.size() > 0) {
                            for (AppLabelDisease ll : listD) {
                                AppLabelDisease lg = new AppLabelDisease();
                                lg.setLabelColor(ll.getLabelColor());
                                lg.setLabelId(ll.getLabelId());
                                lg.setLabelSignId(ss.getId());
                                lg.setLabelTeamId(ss.getSignTeamId());
                                lg.setLabelTitle(ll.getLabelTitle());
                                lg.setLabelType(ll.getLabelType());
                                lg.setLabelValue(ll.getLabelValue());
                                lg.setLabelAreaCode(ss.getSignAreaCode());
                                sysDao.getServiceDo().add(lg);
                            }
                        }
                        List<AppLabelEconomics> ls = sysDao.getServiceDo().loadByPk(AppLabelEconomics.class, "labelSignId", s.getId());
                        if (ls != null) {
                            for (AppLabelEconomics ll : ls) {
                                AppLabelEconomics lg = new AppLabelEconomics();
                                lg.setLabelColor(ll.getLabelColor());
                                lg.setLabelId(ll.getLabelId());
                                lg.setLabelSignId(ss.getId());
                                lg.setLabelTeamId(ss.getSignTeamId());
                                lg.setLabelTitle(ll.getLabelTitle());
                                lg.setLabelType(ll.getLabelType());
                                lg.setLabelValue(ll.getLabelValue());
                                lg.setLabelAreaCode(ss.getSignAreaCode());
                                sysDao.getServiceDo().add(lg);
                            }
                        }
                    }
                } else {//拒绝
                    lsc.setChangeState(ChangeState.JJBG.getValue());
                    lsc.setChangeAgreeDate(Calendar.getInstance());
                    lsc.setChangeReason(qvo.getReason());
                    sysDao.getServiceDo().modify(lsc);
                    if (s != null) {
                        s.setSignChangeState(ChangeState.JJBG.getValue());
                        sysDao.getServiceDo().modify(s);
                    }

                }
            }
            if ("1".equals(qvo.getChangeType())) {//同意
                //同意变更消息提醒
                sysDao.getAppNoticeDao().addNotices("变更消息提醒", "您好，" + teamName + "的" + qvo.getDrName() + "医生已接收您的变更申请。", NoticesType.XTXX.getValue(),
                        qvo.getDrId(), drId, qvo.getNum(), DrPatientType.DR.getValue());
            } else if ("2".equals(qvo.getChangeType())) {//拒绝
                //变更拒绝消息提醒
                sysDao.getAppNoticeDao().addNotices("变更消息提醒", "您好，" + teamName + "的" + qvo.getDrName() + "医生已拒绝您的变更申请。拒绝原因：" + qvo.getReason(), NoticesType.XTXX.getValue(),
                        qvo.getDrId(), drId, qvo.getNum(), DrPatientType.DR.getValue());
            }
        }
        return "true";
    }

    /**
     * 查询变更记录
     *
     * @param drId
     * @return
     * @throws Exception
     */
    @Override
    public List<YsChangeMsgEntity> findChangeHList(String drId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("drId", drId);//当前登入医生
        map.put("state", ChangeState.SQBG.getValue());
        //医生申请的变更记录
        String sqlOne = "SELECT  " +
                "(SELECT COUNT(1) FROM APP_SIGN_CHANGE WHERE CHANGE_NUM = a.CHANGE_NUM" +
                ") count," +
                "DATE_FORMAT(a.CHANGE_DATE, '%Y-%m-%d %H:%i:%s') time," +
                "DATE_FORMAT(a.CHANGE_AGREE_DATE, '%Y-%m-%d %H:%i:%s') agreeTime," +
                "a.CHANGE_STATE changeState," +
                "a.CHANGE_DR_ID changeDrId," +
                "a.CHANGE_TEAM_ID changeTeamId," +
                "a.CHANGE_DR drId," +
                "a.CHANGE_TEAM teamId," +
                "a.CHANGE_NUM num," +
                "'1' type " +
                "FROM APP_SIGN_CHANGE a WHERE " +
                "a.CHANGE_DR =:drId " +
                "GROUP BY a.CHANGE_NUM ";
        //医生审核的变更记录
        String sqlTwo = "SELECT  " +
                "(SELECT COUNT(1) FROM APP_SIGN_CHANGE WHERE CHANGE_NUM = a.CHANGE_NUM " +
                ") count," +
                "DATE_FORMAT(a.CHANGE_DATE, '%Y-%m-%d %H:%i:%s') time," +
                "DATE_FORMAT(a.CHANGE_AGREE_DATE, '%Y-%m-%d %H:%i:%s') agreeTime," +
                "a.CHANGE_STATE changeState," +
                "a.CHANGE_DR_ID changeDrId," +
                "a.CHANGE_TEAM_ID changeTeamId," +
                "a.CHANGE_DR drId," +
                "a.CHANGE_TEAM teamId," +
                "a.CHANGE_NUM num," +
                "'2' type " +
                "FROM APP_SIGN_CHANGE a WHERE " +
                "a.CHANGE_DR_ID=:drId AND a.CHANGE_STATE !=:state " +
                "GROUP BY a.CHANGE_NUM ";
        String sql = "SELECT * FROM (" + sqlOne + " UNION " + sqlTwo + ") c ORDER BY c.time DESC";
        List<YsChangeMsgEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, YsChangeMsgEntity.class);
        return ls;
    }

    /**
     * 查询签约人员信息
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<SignPeopleEntity> findPeople(NewsTableQqvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("state", signStates);
        String sql = "SELECT a.SIGN_PATIENT_ID id," +
                "b.PATIENT_NAME patientName," +
                "'' sex," +
                "'' age," +
                "a.SIGN_HOSP_ID signOrg," +
                "a.SIGN_DR_ID signDr," +
                "DATE_FORMAT(a.SIGN_FROM_DATE, '%Y-%m-%d %H:%i:%s') signTime " +
                "FROM APP_SIGN_FORM a INNER JOIN APP_PATIENT_USER b ON a.SIGN_PATIENT_ID = b.ID " +
                "WHERE a.SIGN_STATE IN (:state)";
        if ("2".equals(qvo.getType())) {
            if (!AppRoleType.TUANDUI.getValue().equals(qvo.getAreaCode()) && !AppRoleType.YISHENG.getValue().equals(qvo.getAreaCode())) {
                map.put("areaCode", qvo.getAreaCode() + "%");
                sql += " AND a.SIGN_AREA_CODE LIKE :areaCode";
            }

        }
        if (StringUtils.isNotBlank(qvo.getPatientName())) {
            map.put("patientName", "%" + qvo.getPatientName() + "%");
            sql += " AND b.PATIENT_NAME LIKE :patientName";
        }
        List<SignPeopleEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, SignPeopleEntity.class, qvo);
        return ls;
    }

    /**
     * 查询患者续签签约单
     *
     * @param signFormId
     * @return
     * @throws Exception
     */
    @Override
    public List<AppSignForm> renewalReminder(String signFormId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("signFormId", signFormId.split(";"));
        map.put("renewState", CommSF.NOT.getValue());
        String sql = " SELECT a.* " +
                "FROM " +
                "APP_SIGN_FORM a " +
                "WHERE 1=1 " +
                "AND a.ID IN (:signFormId) ";
        List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        return ls;
    }

    /**
     * 查询可续约签约单列表
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppSignFormEntity> findRenewalList(AppCommQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        map.put("statTime", ExtendDate.getYMD(Calendar.getInstance()));
        map.put("endTime", ExtendDate.getYMD(cal));
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "\ta.ID signFormId,\n" +
                "\ta.SIGN_STATE signState,\n" +
                "\t(SELECT PATIENT_NAME from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientName,\n" +
                "\t(SELECT PATIENT_GENDER from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientGender,\n" +
                "\t(SELECT PATIENT_IMAGEURL from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientImageurl,\n" +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\t(SELECT TEAM_HOSP_NAME from APP_TEAM where ID=a.SIGN_TEAM_ID) teamHospName,\n" +
                "\ta.SIGN_TEAM_NAME teamName,\n" +
                "\ta.SIGN_TEAM_ID teamId,\n" +
                "\ta.SIGN_FROM_DATE signFromDate,\n" +
                "\ta.SIGN_TO_DATE signToDate,\n" +
                "\ta.SIGN_DATE signDate,\n" +
                "\ta.SIGN_WAY signWay,\n" +
                "\ta.SIGN_RENEW_STATE signRenewState,\n" +
                "\t(SELECT PATIENT_IDNO from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientIdno,\n" +
                "'' patientAge," +
                "(SELECT GROUP_CONCAT(e.SER_TITLE) FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN (SELECT g.LABEL_VALUE from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID GROUP BY g.LABEL_VALUE) ) fwb," +
                "(SELECT GROUP_CONCAT(e.SER_IMAGE_NAME) FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN (SELECT g.LABEL_VALUE from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID GROUP BY g.LABEL_VALUE) ) fwImageName," +
                "\t(SELECT PATIENT_CARD from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientCard,\n" +
                "\t(SELECT PATIENT_TEL from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientTel,\n" +
                "\t(SELECT DR_NAME from APP_DR_USER where ID=a.SIGN_DR_ID) drName,\n" +
                "\ta.SIGN_GOTO_SIGN_STATE signGoToSignState\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "WHERE\n" +
                "\t1=1 ");
        sql.append(" AND a.SIGN_TO_DATE>=:statTime AND a.SIGN_TO_DATE<=:endTime");
        if (StringUtils.isNotBlank(qvo.getPatientId())) {
            sql.append(" AND a.SIGN_PATIENT_ID =:SIGN_PATIENT_ID \n");
//            "AND b.BATCH_CREATE_PERSID =:BATCH_CREATE_PERSID "
            map.put("SIGN_PATIENT_ID", qvo.getPatientId());
            map.put("BATCH_CREATE_PERSID", qvo.getPatientId());
        }
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            sql.append(" AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID ");
            map.put("SIGN_TEAM_ID", qvo.getTeamId());
        }
        map.put("drId", qvo.getDrId());
        sql.append(" AND a.SIGN_DR_ID =:drId");
        sql.append(" AND a.SIGN_STATE IN (:SIGN_STATE)");
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("SIGN_STATE", signStates);
        map.put("xqState", "0");
        sql.append(" AND a.SIGN_CONTRACT_STATE=:xqState ");

        if (StringUtils.isNotBlank(qvo.getSignFormId())) {
            sql.append(" AND a.ID=:ID ");
            map.put("ID", qvo.getSignFormId());
        }
        sql.append(" ORDER BY a.SIGN_DATE DESC");
        List<AppSignFormEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AppSignFormEntity.class, qvo);
        if (ls != null && ls.size() > 0) {
            return ls;
        }
        return ls;
    }

    @Override
    public ToplimitEntity findDrCount(AppDrUser drUser) throws Exception {
        ToplimitEntity vo = new ToplimitEntity();
        vo.setToplimit("0");
        Map<String, Object> map = new HashMap<String, Object>();
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("signState", signStates);
        map.put("contractState", CommSF.NOT.getValue());
        map.put("drId", drUser.getId());
        String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM WHERE 1=1";
        sql += " AND SIGN_STATE IN (:signState) AND SIGN_DR_ID = :drId";
//        AND SIGN_CONTRACT_STATE = :contractState
        int count = sysDao.getServiceDo().findCount(sql, map);
        List<AppSignServeTeam> ssTeamList = sysDao.getServiceDo().loadByPk(AppSignServeTeam.class, "sstDeptId", drUser.getDrHospId());
        if (ssTeamList != null && ssTeamList.size() > 0) {
            AppSignServeTeam ssTeam = ssTeamList.get(0);
            if (CommSF.YES.getValue().equals(ssTeam.getSstSignState())) {//开启签约上限
                if (count < Integer.parseInt(ssTeam.getSstSignToDr())) {
                    vo.setToplimit("0");//还没上限
                    vo.setLastName(Integer.parseInt(ssTeam.getSstSignToDr()) - count);
                } else {
                    vo.setToplimit("1");//签约上限
                    vo.setLastName(0);
                }
            }
        }
        return vo;
    }

    /**
     * 判断团队是否签约上限
     *
     * @param teamId
     * @return
     * @throws Exception
     */
    @Override
    public String findTeamCount(String teamId) throws Exception {
        AppTeam team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, teamId);
        if (team != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
            map.put("signState", signStates);
            map.put("contractState", CommSF.NOT.getValue());
            map.put("teamId", team.getId());
            String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM WHERE 1=1";
            sql += " AND SIGN_STATE IN (:signState) AND SIGN_TEAM_ID = :teamId";
//            AND SIGN_CONTRACT_STATE = :contractState
            int count = sysDao.getServiceDo().findCount(sql, map);
            List<AppSignServeTeam> ssTeamList = sysDao.getServiceDo().loadByPk(AppSignServeTeam.class, "sstDeptId", team.getTeamHospId());
            if (ssTeamList != null && ssTeamList.size() > 0) {
                AppSignServeTeam ssTeam = ssTeamList.get(0);
                if (CommSF.YES.getValue().equals(ssTeam.getSstSignState())) {//开启签约上限
                    if (count < Integer.parseInt(ssTeam.getSstSignToTeam())) {
                        return "0";//还没上限
                    } else {
                        return "1";//签约上限
                    }
                }
            }
        }
        return "0";
    }

    @Override
    public ToplimitEntity findToTeamCount(String teamId) throws Exception {
        ToplimitEntity vo = new ToplimitEntity();
        vo.setToplimit("0");
        AppTeam team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, teamId);
        if (team != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
            map.put("signState", signStates);
            map.put("contractState", CommSF.NOT.getValue());
            map.put("teamId", team.getId());
            String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM WHERE 1=1";
            sql += " AND SIGN_STATE IN (:signState) AND SIGN_CONTRACT_STATE = :contractState AND SIGN_TEAM_ID = :teamId";
            int count = sysDao.getServiceDo().findCount(sql, map);
            List<AppSignServeTeam> ssTeamList = sysDao.getServiceDo().loadByPk(AppSignServeTeam.class, "sstDeptId", team.getTeamHospId());
            if (ssTeamList != null && ssTeamList.size() > 0) {
                AppSignServeTeam ssTeam = ssTeamList.get(0);
                if (CommSF.YES.getValue().equals(ssTeam.getSstSignState())) {//开启签约上限
                    if (count < Integer.parseInt(ssTeam.getSstSignToTeam())) {
                        Integer num = Integer.parseInt(ssTeam.getSstSignToTeam()) - count;
                        vo.setToplimit("0");//还没上限
                        vo.setLastName(num);
                    } else {
                        vo.setToplimit("1");//签约上限
                        vo.setLastName(0);
                    }
                }
            }
        }
        return vo;
    }

    /**
     * 根据医生id和患者id查询签约单信息
     *
     * @param patientId
     * @param drId
     * @return
     * @throws Exception
     */
    @Override
    public AppSignForm findByPatientDr(String patientId, String drId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("drId", drId);
        map.put("patientId", patientId);
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("signState", signStates);
        map.put("xq", CommSF.NOT.getValue());
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE 1=1 ";
        sql += " AND SIGN_DR_ID =:drId AND SIGN_PATIENT_ID =:patientId AND SIGN_STATE IN (:signState) AND SIGN_CONTRACT_STATE =:xq";
        List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (ls != null && ls.size() > 0) {
            return ls.get(0);
        }
        return null;
    }

    /**
     * 患者转签
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String goToSign(AppGoToSignQvo qvo) throws Exception {
        if (StringUtils.isBlank(qvo.getDrId())) {
            throw new Exception("医生id不能为空");
        }
        if (StringUtils.isBlank(qvo.getTeamId())) {
            throw new Exception("团队id不能为空");
        }
        if (StringUtils.isNotBlank(qvo.getSignUpHpis())) {
            throw new Exception("签约来源不能为空");
        }
        AppSignForm oldSignForm = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, qvo.getSignId());
        if (oldSignForm != null) {
            AppSignBatch batch = new AppSignBatch();//批次
            AppTeam teamvo = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, qvo.getTeamId());//团队
            if (teamvo == null) {
                throw new Exception("找不到团队信息");
            }
            AppPatientUser uservo = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, qvo.getPatientId());//患者
            batch.setBatchCreateDate(Calendar.getInstance());
            batch.setBatchTeamId(teamvo.getId());
            batch.setBatchTeamName(teamvo.getTeamName());
            batch.setBatchCreatePersid(uservo.getId());
            batch.setBatchPatientName(uservo.getPatientName());
            //组织批次号
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, teamvo.getTeamHospId());
            if (dept != null && dept.getHospAreaCode() != null) {
                AppSerial serial = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
                if (serial != null) {
                    Map<String, Object> bcnum = this.getNum(serial.getSerialNo(), SignFormType.APPSTATE.getValue());
                    serial.setSerialNo(bcnum.get("old").toString());
                    sysDao.getServiceDo().modify(serial);
                    batch.setBatchNum(bcnum.get("new").toString());//批次号
                }
            }
            batch.setBatchOperatorId(qvo.getDrId());
            AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(qvo.getDrId());
            if (drUser != null) {
                batch.setBatchOperatorName(drUser.getDrName());
            }
            if (dept != null && dept.getHospAreaCode() != null) {
                batch.setBatchAreaCode(dept.getHospAreaCode());
            }
            sysDao.getServiceDo().add(batch);
            AppSignForm form = new AppSignForm();//签约单
            form.setUpHpis(qvo.getSignUpHpis());
            form.setSignFromDate(oldSignForm.getSignToDate());
            form.setSignBatchId(batch.getId());
            //组织编码
            if (dept != null && dept.getHospAreaCode() != null) {
                form.setSignHospId(dept.getId());
                form.setSignAreaCode(dept.getHospAreaCode());
                AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                if (serialSign != null) {
                    Map<String, Object> sinum = this.getNum(serialSign.getSerialNo(), SignFormType.APPSTATE.getValue());
                    serialSign.setSerialNo(sinum.get("old").toString());
                    sysDao.getServiceDo().modify(serialSign);
                    form.setSignNum(sinum.get("new").toString());//签约编码
                }
            }
            //
            form.setSignPatientId(uservo.getId());
            form.setSignDate(Calendar.getInstance());
            //是否直接同意签约
            AppHospExtend extend = this.sysDao.getAppHospExtendDao().findByHospId(form.getSignHospId());
            boolean flagAgree = false;
            if (extend != null) {
                if (extend.getExtHreState().equals(AppFlagAgree.KAIQI.getValue())) {
                    form.setSignState(SignFormType.ZQ.getValue());//11已转约
                    Calendar end = oldSignForm.getSignToDate();
                    end.add(Calendar.YEAR, 1);
                    end.add(Calendar.DATE, -1);
                    form.setSignToDate(end);
                    flagAgree = true;
                } else {
                    form.setSignState(SignFormType.YUZQ.getValue());//1:预签约
                }
            } else {
                form.setSignState(SignFormType.YUZQ.getValue());//1:预签约
            }
            form.setSignPayState("0");//0：未缴费
            form.setSignType("1");//1家庭签约
            form.setSignTeamId(teamvo.getId());
            form.setSignTeamName(teamvo.getTeamName());
            form.setSignWay("0");//个人发起签约
            form.setSignPatientAge(Integer.parseInt(uservo.getPatientAge()));
            form.setSignPatientGender(uservo.getPatientGender());
            form.setSignPatientIdNo(uservo.getPatientIdno());
            form.setSignDrId(qvo.getDrId());//绑定医生
            form.setSignContractState("0");//1是 0否
            form.setSignGreenState("0");//1是 0否
            form.setSignYellowState("0");//1是 0否
            form.setSignRedState("0");//1是 0否
            form.setSignpackageid(qvo.getSignpackageid());
            if (StringUtils.isNotBlank(qvo.getSignpackageid())) {
                String areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode(), "2");
                if (areaCode != null) {
                    if (!npAreaCode.equals(areaCode)) {
                        String text = sysDao.getAppSignformDao().findPkRemark(qvo.getSignpackageid());
                        form.setSigntext(text);
                    }
                } else {
                    String text = sysDao.getAppSignformDao().findPkRemark(qvo.getSignpackageid());
                    form.setSigntext(text);
                }
//            form.setSignczpay("");
//            form.setSignzfpay("");
                if ("3501".equals(areaCode)) {//福州做财政补助和自费
                    String[] mealIds = qvo.getSignpackageid().split(";");
                    double zfpay = 0.0;//自费
                    double czpay = 0.0;//财政补贴
                    double totalFee = 0.0;//总费用
                    for (String mealId : mealIds) {
                        AppServeSetmeal meal = (AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class, mealId);
                        if (meal != null) {
                            if (StringUtils.isNotBlank(meal.getSersmTotalFee())) {//总费用
                                totalFee += Double.parseDouble(meal.getSersmTotalFee());
                                if (StringUtils.isNotBlank(meal.getSersmTotalOneFee())) {//自费费用
                                    zfpay += Double.parseDouble(meal.getSersmTotalOneFee());
                                }
                            }
                        }
                    }
                    czpay = totalFee - zfpay;
                    form.setSignczpay(String.valueOf(czpay));
                    if (czpay <= 0) {
                        form.setSignczpay("0");
                    }
                    form.setSignzfpay(String.valueOf(zfpay));
                }
            }

            sysDao.getServiceDo().add(form);
            oldSignForm.setSignRenewOrGoToSignDate(Calendar.getInstance());
            oldSignForm.setSignGoToSignState("1");//转签
            if (oldSignForm.getSignTeamId().equals(form.getSignTeamId()) && !oldSignForm.getSignDrId().equals(form.getSignDrId())) {
                oldSignForm.setSignGotoSignType("1");//医生转签
            } else {
                oldSignForm.setSignGotoSignType("2");
            }
            sysDao.getServiceDo().modify(oldSignForm);

            if (flagAgree) {
                this.SignFormNotices(form);
//                String title = "签约成功消息";
//                String content = "您好，您的家庭医生签约申请已审核通过!";
//                sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), qvo.getDrId(), uservo.getId(), form.getId(), DrPatientType.PATIENT.getValue());
            } else {
                String title = "签约消息";
                String content = uservo.getPatientName() + "," + uservo.getStrPatientGender() + "," + uservo.getPatientAge() + "岁,于" + ExtendDate.getYMD_h_m(Calendar.getInstance()) + "申请签约!";
                if (qvo.getDrId() == null) {
                    List<AppTeamMember> members = sysDao.getServiceDo().loadByPk(AppTeamMember.class, "memDrId", qvo.getTeamId());
                    for (AppTeamMember m : members) {
                        sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), uservo.getId(), m.getMemDrId(), form.getId(), DrPatientType.DR.getValue());
                    }
                } else {
                    sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), uservo.getId(), qvo.getDrId(), form.getId(), DrPatientType.DR.getValue());
                }
            }
            AppGotoSignRecord gotoSign = new AppGotoSignRecord();

            gotoSign.setGtsOldAreaCode(oldSignForm.getSignAreaCode());
            gotoSign.setGtsCreateTime(Calendar.getInstance());
            gotoSign.setGtsOldDrId(oldSignForm.getSignDrId());
            gotoSign.setGtsOldHospId(oldSignForm.getSignHospId());
            gotoSign.setGtsOldSignId(oldSignForm.getId());
            gotoSign.setGtsOldTeamId(oldSignForm.getSignTeamId());
            gotoSign.setGtsPatientId(oldSignForm.getSignPatientId());
            gotoSign.setGtsReasonValue(qvo.getReason());

            gotoSign.setGtsTeamId(form.getSignTeamId());
            gotoSign.setGtsSignId(form.getId());
            gotoSign.setGtsHospId(form.getSignHospId());
            gotoSign.setGtsDrId(form.getSignDrId());
            gotoSign.setGtsAreaCode(form.getSignAreaCode());
            if (flagAgree) {
                gotoSign.setGtsSignState("1");
            } else {
                gotoSign.setGtsSignState("0");
            }
            sysDao.getServiceDo().add(gotoSign);
            if (StringUtils.isNotBlank(qvo.getReason())) {
                String[] ss = qvo.getReason().split(";");
                for (String s : ss) {
                    AppGotosignFb tt = new AppGotosignFb();
                    tt.setGsId(gotoSign.getId());
                    tt.setGsReasonType(s);
                    sysDao.getServiceDo().add(tt);
                }
            }
        } else {
            throw new Exception("找不到签约信息");
        }
        return "true";
    }

    @Override
    public List<AppSignForm> findByGreen(String userId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("state", signStates);
        map.put("greenState", CommSF.NOT.getValue());
        map.put("conState", CommSF.NOT.getValue());
        String sql = " SELECT a.* " +
                "FROM " +
                "APP_SIGN_FORM a INNER JOIN APP_HOSP_EXTEND b ON a.SIGN_HOSP_ID = b.EXT_HOSP_ID " +
                "WHERE " +
                "a.SIGN_TO_DATE <= date_sub(NOW(),interval -cast(b.EXT_GREEN_DAY AS SIGNED) day) " +
                "AND a.SIGN_STATE IN (:state) AND a.SIGN_GREEN_STATE =:greenState AND a.SIGN_CONTRACT_STATE =:conState" +
                " AND a.SIGN_PATIENT_ID=:userId";
        List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        return ls;
    }

    @Override
    public List<AppSignForm> findByYellow(String userId)  throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("state", signStates);
        map.put("yellowState", CommSF.NOT.getValue());
        map.put("conState", CommSF.NOT.getValue());
        String sql = " SELECT a.* " +
                "FROM " +
                "APP_SIGN_FORM a INNER JOIN APP_HOSP_EXTEND b ON a.SIGN_HOSP_ID = b.EXT_HOSP_ID " +
                "WHERE " +
                "a.SIGN_TO_DATE <= date_sub(NOW(),interval -cast(b.EXT_YELLOW_DAY AS SIGNED) day) " +
                "AND a.SIGN_STATE IN (:state) AND a.SIGN_YELLOW_STATE =:yellowState AND a.SIGN_CONTRACT_STATE =:conState" +
                " AND a.SIGN_PATIENT_ID=:userId";
        List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        return ls;
    }

    @Override
    public List<AppSignForm> findByRed(String userId) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("state", signStates);
        map.put("redState", CommSF.NOT.getValue());
        map.put("conState", CommSF.NOT.getValue());
        String sql = " SELECT a.* " +
                "FROM " +
                "APP_SIGN_FORM a INNER JOIN APP_HOSP_EXTEND b ON a.SIGN_HOSP_ID = b.EXT_HOSP_ID " +
                "WHERE " +
                "a.SIGN_TO_DATE <= date_sub(NOW(),interval -cast(b.EXT_RED_DAY AS SIGNED) day) " +
                "AND a.SIGN_STATE IN (:state) AND a.SIGN_RED_STATE =:redState AND a.SIGN_CONTRACT_STATE =:conState " +
                "AND a.SIGN_PATIENT_ID=:userId";
        List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        return ls;
    }

    /**
     * 判断是否签约
     */
    public AppWebSignFormListEntity signfind(AppWebSignQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = " SELECT f.ID id,f.SIGN_HOSP_ID signhospid,d.HOSP_NAME hospname,f.SIGN_STATE signState,f.SIGN_DATE signDate ,u.PATIENT_NAME patientName from APP_PATIENT_USER u ,APP_SIGN_FORM f ,APP_HOSP_DEPT d  where u.ID=f.SIGN_PATIENT_ID and f.SIGN_HOSP_ID = d.ID and f.SIGN_STATE in ('0','2')   ";
        if (StringUtils.isNoneBlank(qvo.getPtidno())) {
            map.put("PATIENT_IDNO", qvo.getPtidno());
            sql += "and u.PATIENT_IDNO= :PATIENT_IDNO";
        }

        if (StringUtils.isNoneBlank(qvo.getPtsscno())) {
            map.put("PATIENT_CARD", qvo.getPtsscno());
            sql += " and u.PATIENT_CARD= :PATIENT_CARD ";
        }
        List<AppWebSignFormListEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppWebSignFormListEntity.class);
        if (ls.size() > 0 && ls != null) {
            return ls.get(0);
        }
        return null;
    }

    /**
     * 查询签约最旧那条
     *
     * @param patientId
     * @param drId
     * @return
     */
    @Override
    public String findMinDate(String patientId, String drId) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "SELECT\n" +
                "\tMIN(SIGN_FROM_DATE) signDate \n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM t\n" +
                "WHERE\n" +
                "1=1\n";
        if (StringUtils.isNotBlank(patientId)) {
            map.put("SIGN_PATIENT_ID", patientId);
            sql += " AND t.SIGN_PATIENT_ID = :SIGN_PATIENT_ID ";
        }
        if (StringUtils.isNotBlank(drId)) {
            map.put("SIGN_DR_ID", drId);
            sql += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";

        }
        List ls = this.sysDao.getServiceDo().findSqlMap(sql, map);
        if (ls != null && ls.size() > 0) {
            Map<String, Object> lsMap = (Map<String, Object>) ls.get(0);
            String year = ExtendDate.getYYYY(Calendar.getInstance());
            if (lsMap.get("signDate") != null) {
                year = lsMap.get("signDate").toString();
            }
            return year;
        }
        return null;
    }

    /**
     * 签约筛选
     */
    public List<AppHfsSignSscEntity> findsignsx(AppHfsSignSscQvo hfsvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNoneBlank(hfsvo.getPtorg())) {
            String sql = "SELECT *  FROM ( SELECT f.ID id, s.PT_NAME ptname, s.PT_GENDER ptgender,u.PATIENT_AGE ptnl,s.PT_ID_NO ptidno,s.PT_TELEPHONE pttelephone,s.PT_REGION ptregion, " +
                    " s.PT_LX ptlx,s.PT_USER_NAME ptusername ,s.PT_NATION ptnation,f.SIGN_TEAM_NAME signteamname ,f.SIGN_STATE signwebstate,s.PT_USER_PH ptuserph,s.PT_NATURE ptnature,s.PT_SCC_NO ptsccno " +
                    "  from APP_HFS_SIGN_SSC s LEFT JOIN  APP_PATIENT_USER u on s.PT_ID_NO=u.PATIENT_IDNO LEFT JOIN APP_SIGN_FORM f on u.ID=f.SIGN_PATIENT_ID  where 1=1  ";
            if (StringUtils.isNoneBlank(hfsvo.getPtname())) {
                sql += " and s.PT_NAME like:ptname  ";
                map.put("ptname", "%" + hfsvo.getPtname() + "%");
            } else if (StringUtils.isNoneBlank(hfsvo.getPtidno())) {
                if (StringUtils.isNoneBlank(hfsvo.getPtfamily())) {
                    sql += " and  s.PT_JTBH =(SELECT A.PT_JTBH FROM APP_HFS_SIGN_SSC A WHERE A.PT_LX = '3' and A.PT_ID_NO =:ptidno) ";
                    map.put("ptidno", hfsvo.getPtidno());
                } else {
                    sql += " and s.PT_ID_NO =:ptidno ";
                    map.put("ptidno", hfsvo.getPtidno());
                }
            } else if (StringUtils.isNoneBlank(hfsvo.getPtlx())) {
                if (StringUtils.isNoneBlank(hfsvo.getPtnature())) {
                    sql += " and s.PT_LX =:ptlx    ";
                    map.put("ptlx", hfsvo.getPtlx());

                    for (int i = 0; i < hfsvo.getPtnature().length; i++) {
                        if (i == 0) {
                            sql += " and s.PT_NATURE LIKE '%" + hfsvo.getPtnature()[i] + "%' ";
                        } else {
                            sql += " or s.PT_NATURE LIKE '%" + hfsvo.getPtnature()[i] + "%' ";
                        }
                    }

                } else {
                    sql += " and s.PT_LX =:ptlx  ";
                    map.put("ptlx", hfsvo.getPtlx());
                }

            } else if (StringUtils.isNoneBlank(hfsvo.getSignwebstate())) {

                if (hfsvo.getSignwebstate().equals("3")) {
                    sql += " and f.SIGN_STATE IS NULL ";
                } else {
                    sql += " and f.SIGN_STATE =:signweb";
                    map.put("signweb", hfsvo.getSignwebstate());
                }
            } else if (StringUtils.isNoneBlank(hfsvo.getPtregion())) {
                sql += " and s.PT_REGION LIKE:ptregion";
                map.put("ptregion", "%" + hfsvo.getPtregion() + "%");
            } else if (StringUtils.isNoneBlank(hfsvo.getPtteamid())) {
                sql += " and f.SIGN_TEAM_ID=:signteamid ";
                map.put("signteamid", hfsvo.getPtteamid());
            }
            if (StringUtils.isNoneBlank(hfsvo.getPtlx())) {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, "pt_" + hfsvo.getPtorg());
                if (dept != null) {
                    if (StringUtils.isNoneBlank(dept.getHospUpcityareaId())) {
                        Map<String, Object> plmap = new HashMap<String, Object>();
                        String pl = "select * from app_hosp_dept where hosp_area_code =:hospAreaCode";
                        plmap.put("hospAreaCode", dept.getHospUpcityareaId());
                        List<AppHospDept> sjdept = sysDao.getServiceDo().findSqlMap(pl, plmap, AppHospDept.class);
                        if (sjdept != null && sjdept.size() > 0) {
                            if (StringUtils.isNoneBlank(sjdept.get(0).getId())) {
                                sql += "and s.PT_ORGID IN ('" + hfsvo.getPtorg() + "','" + sjdept.get(0).getId().substring(3, sjdept.get(0).getId().length()) + "','1962')";
                            } else {
                                sql += " and s.PT_ORGID=:PTORGID ";
                                map.put("PTORGID", hfsvo.getPtorg());
                            }
                        } else {
                            sql += " and s.PT_ORGID=:PTORGID ";
                            map.put("PTORGID", hfsvo.getPtorg());
                        }
                    } else {
                        sql += " and s.PT_ORGID=:PTORGID ";
                        map.put("PTORGID", hfsvo.getPtorg());
                    }
                } else {
                    sql += " and s.PT_ORGID=:PTORGID ";
                    map.put("PTORGID", hfsvo.getPtorg());
                }
            } else {
                sql += " and s.PT_ORGID=:PTORGID ";
                map.put("PTORGID", hfsvo.getPtorg());
            }
            sql += " ) a WHERE (a.signwebstate IN ('0','2') or a.signwebstate is null ) ";
            List<AppHfsSignSscEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppHfsSignSscEntity.class, hfsvo);
            if (ls != null && ls.size() > 0) {
                return ls;
            }
        }
        return null;
    }

    /**
     * Dmao
     * 签约查看
     *
     * @param
     * @return
     * @throws Exception
     */
    public WebSignSaveVo signLook(AppWebSignQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNoneBlank(qvo.getId()) || StringUtils.isNoneBlank(qvo.getPtidno())) {
            String sql = " SELECT\n" +
                    "\tu.ID patientId,\n" +
                    "\ts.SIGN_TEAM_ID teamId,\n" +
                    "\ts.ID signId,\n" +
                    "\tu.PATIENT_NAME patientName,\n" +
                    "\tu.PATIENT_GENDER patientGender,\n" +
                    "\tu.PATIENT_IDNO patientIdno,\n" +
                    "\tu.PATIENT_AGE patientAge,\n" +
                    "\tu.PATIENT_CARD patientCard,\n" +
                    "\tu.PATIENT_JMDA patientjmda,\n" +
                    "\tu.PATIENT_JTDA patientjtda,\n" +
                    "\tu.PATIENT_TEL patientTel,\n" +
                    "\tu.PATIENT_ADDRESS patientAddress,\n" +
                    "\tu.PATIENT_CITY patientCity,\n" +
                    "\t'' patientCityname,\n" +
                    "\tu.PATIENT_AREA patientArea,\n" +
                    "\t'' patientAreaname,\n" +
                    "\tu.PATIENT_STREET patientStreet,\n" +
                    "\t'' patientStreetname,\n" +
                    "\tu.PATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommittee,\n" +
                    "\t'' patientNeighborhoodCommitteename,\n" +
                    "\ts.SIGN_HOSP_ID hospId,\n" +
                    "\t'' hospName,\n" +
                    "\t'' teamName,\n" +
                    "\ts.SIGN_DR_ASSISTANT_ID signDrAssistantId,\n" +
                    "\t'' signDrAssistantName,\n" +
                    "\t'' signDrAssistantTel,\n" +
                    "\ts.SIGN_JJ_TYPE signsJjType,\n" +
                    "\ts.SIGN_FROM_DATE signFromDate,\n" +
                    "\ts.SIGN_TO_DATE signToDate,\n" +
                    "\ts.SIGN_CZ_PAY signczpay,\n" +
                    "\ts.SIGN_ZF_PAY signzfpay,\n" +
                    "\ts.SIGN_LX signlx,\n" +
                    "\ts.SIGN_DR_ID drId,\n" +
                    "\t'' drName,\n" +
                    "\t'' drTel,\n" +
                    "\ts.SIGN_TEAM_NAME signTeamName, " +
                    " s.SIGN_TEXT signtext,\n" +
                    "\ts.SIGN_PACKAGEID signpackageid, \n" +
                    "\ta.BATCH_OPERATOR_NAME batchOperatorName,a.BATCH_OPERATOR_ID batchOperatorId \n" +
                    "FROM\n" +
                    "\tAPP_SIGN_FORM s\n" +
                    "\t LEFT JOIN APP_SIGN_BATCH a ON s.SIGN_BATCH_ID=a.ID \n" +
                    "INNER JOIN APP_PATIENT_USER u ON s.SIGN_PATIENT_ID = u.ID\n" +
                    "WHERE\n" +
                    "\t1 = 1 ";
            if (StringUtils.isNoneBlank(qvo.getId())) {
                map.put("ID", qvo.getId());
                sql += " AND s.ID = :ID ";
            } else if (StringUtils.isNoneBlank(qvo.getPtidno())) {
                map.put("PATIENT_IDNO", qvo.getPtidno());
                sql += " AND u.PATIENT_IDNO =  :PATIENT_IDNO ";
            }
            List<WebSignSaveVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, WebSignSaveVo.class);
            if (ls != null && ls.size() > 0) {
                return ls.get(0);
            }
        }
        return null;
    }

    //居民签约信息查询
    @Override
    public List<AppSignVo> findSignXxWeb(AppCommQvo qvo) throws Exception {
        String signsJjType = "0";
        String age = "''";
        if ((StringUtils.isNotBlank(qvo.getSignrenew()) && StringUtils.isNotBlank(qvo.getDissolutionType()))) {
            age = "null";
            signsJjType = " ( SELECT count(1) FROM app_sign_form f WHERE f.SIGN_PATIENT_ID = a.SIGN_PATIENT_ID and  f.SIGN_STATE  IN ('2', '0'))";
        }
        HashMap map = new HashMap();
        boolean patientuser = false;
        boolean labelgroup = false;
        boolean labeleconmics = false;
        boolean signbatch = false;
        if(StringUtils.isNotBlank(qvo.getServiceA()) || (qvo.getPersGroup() != null && qvo.getPersGroup().length>0) ){
            labelgroup = true;
        }
        if( (qvo.getSignsJjTypes() != null && qvo.getSignsJjTypes().length>0) ){
            labeleconmics = true;
        }
        if(StringUtils.isNotBlank(qvo.getBatchOperatorName())){
            signbatch = true;
        }
        if(StringUtils.isNotBlank(qvo.getPatientName()) || StringUtils.isNotBlank(qvo.getPatientIdno()) || StringUtils.isNotBlank(qvo.getPatientCity())
                || StringUtils.isNotBlank(qvo.getPatientArea()) || StringUtils.isNotBlank(qvo.getPatientStreet()) || StringUtils.isNotBlank(qvo.getPatientNeighborhoodCommittee())
                || StringUtils.isNotBlank(qvo.getPatientAddress()) ){
            patientuser = true;
        }
        String sqlcount = "SELECT\n" +
                "\ta.ID id "+
                "FROM\n" +
                "\tAPP_SIGN_FORM a \n" ;
                if(labelgroup){
                    sqlcount += " LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID " ;
                }
                if(labeleconmics){
                    sqlcount += " LEFT JOIN APP_LABEL_ECONOMICS e on a.ID = e.LABEL_SIGN_ID " ;
                }
                if(signbatch){
                    sqlcount += " LEFT JOIN APP_SIGN_BATCH s ON a.SIGN_BATCH_ID=s.ID " ;
                }
                if(patientuser){
                    sqlcount += " LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID " ;
                }
                sqlcount += "where 1=1  ";
        String sql = "SELECT\n" +
                "\ta.ID id, CONCAT(DATE_FORMAT(a.SIGN_FROM_DATE,'%Y/%m/%d'),\"至\",DATE_FORMAT(a.SIGN_TO_DATE,'%Y/%m/%d')) signToDate, \n '" +
                qvo.getSigngwpay() + "' signgwpay,\n '" +
                qvo.getSignybpay() + "' signybpay,\n" +
                "\tc.ID patientId,\n" +
                "\ta.SIGN_AREA_CODE signareacode,\n" +
                "\ta.SIGN_TEAM_ID teamId,\n" +
                "\ta.SIGN_NUM signNum,\n" +
                "\ta.SIGN_CONTRACT_STATE contractCode,\n" +
                "\tc.PATIENT_NAME name,\n" +
                "\tc.PATIENT_IDNO patientIdno,\n" +
                age + " age," +
                "\tc.PATIENT_TEL tel, a.SIGN_ZF_PAY signzfpay, \n" +
                //"\ta.SIGN_JJ_TYPE signsJjType, \n" +
                "\ta.SIGN_LX signlx,\n" +
                "\ta.SIGN_PACKAGEID packageId,\n" +
                "\ta.SIGN_DATE signDate,\n" +
                "\ta.SIGN_PAY_STATE signPayState, \n" +
                "\ta.SIGN_STATE signState,\n" +
                "\ta.SIGN_CZ_PAY signczpay,\n" +
                "\ta.SIGN_DR_ID signDrId,\n" +
                "\t(SELECT DR_NAME from APP_DR_USER g where g.ID=a.SIGN_DR_ID) signDrName,\n" +
                "\tc.PATIENT_GENDER sex,\n" +
                "\tc.PATIENT_CARD patientCard,\n" +
                "\ta.SIGN_TYPE signType, c.PATIENT_ADDRESS patientAddress,\n" +
                "(SELECT t.AREA_SNAME from cp_city_area t where t.AREA_CODE = c.PATIENT_NEIGHBORHOOD_COMMITTEE) patientNeighborhoodCommittee,\n" +
                "a.SIGN_TEAM_NAME signTeamName,\n" +
                "s.BATCH_OPERATOR_NAME batchOperatorName,s.BATCH_OPERATOR_ID batchOperatorId,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) signPersGroup,\n " +
                " ( SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_ECONOMICS c WHERE c.LABEL_SIGN_ID=a.ID ) signsJjType, " +
                "\ta.UP_HPIS upHpis, c.PATIENT_JMDA patientjmda,\n" +
                signsJjType + " signQyCount , " +
                "a.SIGN_HEALTH_REPORT_STATE signHealthReportState,\n" +
                "\tc.PATIENT_JTDA patientjtda," +
                "s.BATCH_CHANGE_OPERATOR_NAME batchChangeOperatorName," +
                "s.BATCH_CHANGE_OPERATOR_ID batchChangeOperatorId  \n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID " +
                " LEFT JOIN APP_LABEL_ECONOMICS e on a.ID = e.LABEL_SIGN_ID " +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID LEFT " +
                "JOIN APP_SIGN_BATCH s ON a.SIGN_BATCH_ID=s.ID\n" +
                "where 1=1  ";

        // 是否续签查询
        if (StringUtils.isNotBlank(qvo.getSignrenew())) {
            // 续签查询提前的时间范围
            String signToDateSql = "";
            if (StringUtils.isNotBlank(qvo.getWarningDay())) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, +Integer.valueOf(qvo.getWarningDay()));
                String warningDay = ExtendDate.getYMD(calendar);
                signToDateSql = " and a.SIGN_TO_DATE < :calDate ";// 续签管理初始化增加签约状态0,2、alter by WangCheng
                map.put("calDate", warningDay);
            } else {
                String hospAreaCode = (String) ActionContext.getContext().getSession().get("HospAreaCode");
                // 查询该地市是否开启本年度签约协议
                AppSignSetting setting = sysDao.getAppSignSettingDao().findByAreaCode(AreaUtils.getAreaCode(hospAreaCode, "2"));
                if (setting != null) {
                    Calendar cal = Calendar.getInstance();
                    if ("1".equals(setting.getSerOpenYear())) {// 截止时间在当年最后一天前的都可续签
                        cal.set(Calendar.MONTH, 11);
                        cal.set(Calendar.DATE, 31);
                    } else {// 提前一个月续签
                        cal.add(Calendar.MONTH, +1);
                    }
                    String calDate = ExtendDate.getYMD(cal);
                    signToDateSql = " and a.SIGN_TO_DATE < :calDate ";// 续签管理初始化增加签约状态0,2、alter by WangCheng
                    map.put("calDate", calDate);
                } else {
                    throw new Exception("请先完善配置表！");
                }
            }
            // 签约单状态
            if (StringUtils.isNotBlank(qvo.getDissolutionType())) {// 查询到期任未续签的数据
                sql = sql + " and a.SIGN_STATE='4' ";
                sqlcount = sqlcount + " and a.SIGN_STATE='4' ";
            } else if (StringUtils.isNotBlank(qvo.getShowRenew())) {
                if ("1".equals(qvo.getShowRenew())) {// 查询续签后未生效的数据
                    sql = sql + " and a.SIGN_STATE = '98' ";
                    sqlcount = sqlcount + " and a.SIGN_STATE = '98' ";
                } else if ("0".equals(qvo.getShowRenew())) {// 查询还未续签的有效签约数据
                    sql = sql + signToDateSql + " and a.SIGN_STATE in ('0', '2') and a.SIGN_GOTO_SIGN_STATE != '2' ";
                    sqlcount = sqlcount + signToDateSql + " and a.SIGN_STATE in ('0', '2') and a.SIGN_GOTO_SIGN_STATE != '2' ";
                }
            } else {//查询续签的签约单和未续签数据
                sql = sql + " and (a.SIGN_STATE = '98' or (a.SIGN_STATE in ('0', '2') and a.SIGN_GOTO_SIGN_STATE != '2' " + signToDateSql + ")) ";
                sqlcount = sqlcount + " and (a.SIGN_STATE = '98' or (a.SIGN_STATE in ('0', '2') and a.SIGN_GOTO_SIGN_STATE != '2' " + signToDateSql + ")) ";
            }
        } else {
            if(StringUtils.isNotEmpty(qvo.getSignUrrenderState())){
                if("1".equals(qvo.getSignUrrenderState())){
                    sql = sql + " and a.SIGN_STATE = '4' and a.SIGN_URRENDER_REASON = '签约到期，自动解约'";
                    sqlcount = sqlcount + " and a.SIGN_STATE = '4' and a.SIGN_URRENDER_REASON = '签约到期，自动解约'";
                }else {
                    sql = sql + " and (a.SIGN_STATE in ('0', '2') ) ";
                    sqlcount = sqlcount + " and (a.SIGN_STATE in ('0', '2') ) ";
                }
            }else {
                sql = sql + " and (a.SIGN_STATE in ('0', '2') ) ";
                sqlcount = sqlcount + " and (a.SIGN_STATE in ('0', '2') ) ";
            }
        }

        if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
            sql = sql + " AND a.SIGN_AREA_CODE  LIKE :SIGN_AREA_CODE ";
            sqlcount = sqlcount + " AND a.SIGN_AREA_CODE  LIKE :SIGN_AREA_CODE ";
            map.put("SIGN_AREA_CODE", "%"+qvo.getSignAreaCode()+"%");
        }else {
            //机构
            if (StringUtils.isNotBlank(qvo.getSignHospId())) {
                sql = sql + " AND a.SIGN_HOSP_ID=:SIGN_HOSP_ID ";
                sqlcount = sqlcount + " AND a.SIGN_HOSP_ID=:SIGN_HOSP_ID ";
                map.put("SIGN_HOSP_ID", qvo.getSignHospId());
            }
        }

        //模糊搜索 姓名
        if (StringUtils.isNotBlank(qvo.getPatientName())) {
            if(StringUtils.isNotEmpty(qvo.getSignRegister())){//医保对接的界面查询不模糊查询
                map.put("patientName",qvo.getPatientName());
                sql = sql + " AND c.PATIENT_NAME = :patientName ";
                sqlcount = sqlcount + " AND c.PATIENT_NAME = :patientName ";
            }else {
                sql = sql + " AND c.PATIENT_NAME LIKE :searchValue ";
                sqlcount = sqlcount + " AND c.PATIENT_NAME LIKE :searchValue ";
                map.put("searchValue", "%"+qvo.getPatientName() + "%");
            }
        }
        //医社保卡号
        if(StringUtils.isNotEmpty(qvo.getPatientCard())){
            map.put("patientCard",qvo.getPatientCard());
            sql = sql + " AND c.PATIENT_CARD = :patientCard ";
            sqlcount = sqlcount + " AND c.PATIENT_CARD = :patientCard ";
        }
        //团队
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            sql = sql + " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID ";
            sqlcount = sqlcount + " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID ";
            map.put("SIGN_TEAM_ID", qvo.getTeamId());
            //医生
            if (StringUtils.isNotBlank(qvo.getDrId())) {
                sql = sql + " AND a.SIGN_DR_ID=:SIGN_DR_ID ";
                sqlcount = sqlcount + " AND a.SIGN_DR_ID=:SIGN_DR_ID ";
                map.put("SIGN_DR_ID", qvo.getDrId());
            }
        }
        //身份证
        if (StringUtils.isNotBlank(qvo.getPatientIdno())) {
            sql = sql + " AND c.PATIENT_IDNO=:PATIENT_IDNO ";
            sqlcount = sqlcount + " AND c.PATIENT_IDNO=:PATIENT_IDNO ";
            map.put("PATIENT_IDNO", qvo.getPatientIdno());
        }
        //居民健康档案
        if (StringUtils.isNotBlank(qvo.getPatientjmda())) {
            sql = sql + " AND c.PATIENT_JMDA=:PATIENT_JMDA ";
            sqlcount = sqlcount + " AND c.PATIENT_JMDA=:PATIENT_JMDA ";
            map.put("PATIENT_JMDA", qvo.getPatientjmda());
        }
        //自费金额
        if (StringUtils.isNotBlank(qvo.getSignzfpay())) {
            sql = sql + " AND a.SIGN_ZF_PAY=:SIGN_ZF_PAY ";
            sqlcount = sqlcount + " AND a.SIGN_ZF_PAY=:SIGN_ZF_PAY ";
            map.put("SIGN_ZF_PAY", qvo.getSignzfpay());
        }
        //签约日期开始
        if (StringUtils.isNotBlank(qvo.getSignDateStart())) {
            sql = sql + " AND a.SIGN_DATE>=:SIGN_DATE_START ";
            sqlcount = sqlcount + " AND a.SIGN_DATE>=:SIGN_DATE_START ";
            map.put("SIGN_DATE_START", qvo.getSignDateStart() + " 00:00:00");
        }
//        else {
//            //如果没有带签约日期的起始日期来查的话默认查当前时间的前一个月内的签约信息、加快后台返回的速度、add by WangCheng
//            Calendar nowCalendar = Calendar.getInstance();
//            nowCalendar.add(Calendar.MONTH,-1);
//            String findDate = ExtendDate.getYMD(nowCalendar);
//            map.put("findDate", findDate);
//            sql = sql + " AND a.SIGN_DATE >= :findDate ";
//        }
        //签约日期结束
        if (StringUtils.isNotBlank(qvo.getSignDateEnd())) {
            sql = sql + " AND a.SIGN_DATE<=:SIGN_DATE_END ";
            sqlcount = sqlcount + " AND a.SIGN_DATE<=:SIGN_DATE_END ";
            map.put("SIGN_DATE_END", qvo.getSignDateEnd() + " 23:59:59");
        }
        //协议日期开始(起始)
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql = sql + " AND a.SIGN_FROM_DATE>=:SIGN_FROM_DATE_START ";
            sqlcount = sqlcount + " AND a.SIGN_FROM_DATE>=:SIGN_FROM_DATE_START ";
            map.put("SIGN_FROM_DATE_START", qvo.getSignFromDateStart() + " 00:00:00");
        }
        //协议日期结束（结束）
        if (StringUtils.isNotBlank(qvo.getSignFromDateEnd())) {
            sql = sql + " AND a.SIGN_FROM_DATE<=:SIGN_FROM_DATE_END ";
            sqlcount = sqlcount + " AND a.SIGN_FROM_DATE<=:SIGN_FROM_DATE_END ";
            map.put("SIGN_FROM_DATE_END", qvo.getSignFromDateEnd() + " 23:59:59");
        }
        //协议结束日期(起始)
        if(StringUtils.isNotEmpty(qvo.getSignToDateStart())){
            map.put("SIGN_TO_DATE_START", qvo.getSignToDateStart() + " 00:00:00");
            sql = sql + " AND a.SIGN_TO_DATE>=:SIGN_TO_DATE_START ";
            sqlcount = sqlcount + " AND a.SIGN_TO_DATE>=:SIGN_TO_DATE_START ";
        }
        //协议结束日期(结束)
        if(StringUtils.isNotEmpty(qvo.getSignToDateEnd())){
            map.put("SIGN_TO_DATE_END", qvo.getSignToDateEnd() + " 23:59:59");
            sql = sql + " AND a.SIGN_TO_DATE<=:SIGN_TO_DATE_END ";
            sqlcount = sqlcount + " AND a.SIGN_TO_DATE<=:SIGN_TO_DATE_END ";
        }
        //医保类型
        if (StringUtils.isNotBlank(qvo.getSignlx())) {
            sql = sql + " AND a.SIGN_LX=:SIGN_LX ";
            sqlcount = sqlcount + " AND a.SIGN_LX=:SIGN_LX ";
            map.put("SIGN_LX", qvo.getSignlx());
        }
        //人口经济性质
        /*if (qvo.getSignsJjTypes() != null && qvo.getSignsJjTypes().length > 0) {
            sql = sql + " AND a.SIGN_JJ_TYPE in (:SIGN_JJ_TYPE) ";
            map.put("SIGN_JJ_TYPE", qvo.getSignsJjTypes());
        }*/
        if (qvo.getSignsJjTypes() != null && qvo.getSignsJjTypes().length > 0) {
            sql = sql + " and e.LABEL_TYPE = '4' and e.LABEL_VALUE in (:SIGN_JJ_TYPE) ";
            sqlcount = sqlcount + " and e.LABEL_TYPE = '4' and e.LABEL_VALUE in (:SIGN_JJ_TYPE) ";
            map.put("SIGN_JJ_TYPE", qvo.getSignsJjTypes());
        }
        //助理姓名
        if (StringUtils.isNotBlank(qvo.getSignDrAssistantId())) {
            sql = sql + " AND  a.SIGN_DR_ASSISTANT_ID =:SIGN_DR_ASSISTANT_ID ";
            sqlcount = sqlcount + " AND  a.SIGN_DR_ASSISTANT_ID =:SIGN_DR_ASSISTANT_ID  ";
            map.put("SIGN_DR_ASSISTANT_ID", qvo.getSignDrAssistantId());
        }
        //vip
        if (StringUtils.isNotBlank(qvo.getVip()) && qvo.getVip().equals("1")) {
            sql = sql + " AND a.SIGN_TYPE='2'";
            sqlcount = sqlcount + " AND a.SIGN_TYPE='2'";
        } else {
            sql = sql + " AND a.SIGN_TYPE='1'";
            sqlcount = sqlcount + " AND a.SIGN_TYPE='1'";
        }
        //到期
        if (StringUtils.isNotBlank(qvo.getExpire()) && qvo.getExpire().equals("1")) {
            sql = sql + " AND a.SIGN_TO_DATE>SYSDATE()";
            sqlcount = sqlcount + " AND a.SIGN_TO_DATE>SYSDATE()";
        }
        String plg = "";
        //疾病类型
        if (StringUtils.isNotBlank(qvo.getLabelGruops())) {
            String[] lg = qvo.getLabelGruops().split(";");
            if (lg != null && lg.length > 0) {
                for (String l : lg) {
                    if (org.apache.commons.lang.StringUtils.isBlank(plg)) {
                        plg = l;
                    } else {
                        plg += ";" + l;
                    }
                }
            }
        }
        //健康情况
        if (StringUtils.isNotBlank(qvo.getSignHealthGroup())) {
            String[] hg = qvo.getSignHealthGroup().split(";");
            if (hg != null && hg.length > 0) {
                sql = sql + " and a.SIGN_HEALTH_GROUP  in (:SIGN_HEALTH_GROUP)";
                sqlcount = sqlcount + " and a.SIGN_HEALTH_GROUP  in (:SIGN_HEALTH_GROUP)";
                map.put("SIGN_HEALTH_GROUP", hg);
            }
        }
        //三师
        if (StringUtils.isNotBlank(qvo.getServiceA()) && qvo.getServiceA().equals("1")) {
            sql = sql + " AND a.SIGN_SERVICE_A='2'";
            sqlcount = sqlcount + " AND a.SIGN_SERVICE_A='2'";
            String bacolor[] = new String[3];
            int bia = 0;
            if (StringUtils.isNotBlank(qvo.getServiceAred()) && qvo.getServiceAred().equals("1")) {
                bacolor[bia] = "red";
                bia++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceAyellow()) && qvo.getServiceAyellow().equals("1")) {
                bacolor[bia] = "yellow";
                bia++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceAgreen()) && qvo.getServiceAgreen().equals("1")) {
                bacolor[bia] = "green";
                bia++;
            }
            if ((StringUtils.isNotBlank(qvo.getServiceAred()) && qvo.getServiceAred().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceAyellow()) && qvo.getServiceAyellow().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceAgreen()) && qvo.getServiceAgreen().equals("1"))) {
                sql = sql + " AND b.LABEL_COLOR in (:LABEL_COLOR)";
                sqlcount = sqlcount + " AND b.LABEL_COLOR in (:LABEL_COLOR)";
                map.put("LABEL_COLOR", bacolor);
            }
        }
        //居家
        if (StringUtils.isNotBlank(qvo.getServiceB()) && qvo.getServiceB().equals("1")) {
            sql = sql + " AND a.SIGN_SERVICE_B='2'";
            sqlcount = sqlcount + " AND a.SIGN_SERVICE_B='2'";
            String bcolor[] = new String[3];
            int bi = 0;
            if (StringUtils.isNotBlank(qvo.getServiceBred()) && qvo.getServiceBred().equals("1")) {
                bcolor[bi] = "red";
                bi++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceByellow()) && qvo.getServiceByellow().equals("1")) {
                bcolor[bi] = "yellow";
                bi++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceBgreen()) && qvo.getServiceBgreen().equals("1")) {
                bcolor[bi] = "green";
                bi++;
            }
            if ((StringUtils.isNotBlank(qvo.getServiceBred()) && qvo.getServiceBred().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceByellow()) && qvo.getServiceByellow().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceBgreen()) && qvo.getServiceBgreen().equals("1"))) {
                sql = sql + " AND a.SIGN_SERVICE_B_COLOR in (:SIGN_SERVICE_B_COLOR)";
                sqlcount = sqlcount + " AND a.SIGN_SERVICE_B_COLOR in (:SIGN_SERVICE_B_COLOR)";
                map.put("SIGN_SERVICE_B_COLOR", bcolor);
            }
        }
        //详细地址
        if (StringUtils.isNoneBlank(qvo.getPatientCity())) {
            sql = sql + " and c.PATIENT_CITY =:city ";
            sqlcount = sqlcount + " and c.PATIENT_CITY =:city ";
            map.put("city", qvo.getPatientCity());
            if (StringUtils.isNoneBlank(qvo.getPatientArea())) {
                sql = sql + " and c.PATIENT_AREA =:area ";
                sqlcount = sqlcount + " and c.PATIENT_AREA =:area ";
                map.put("area", qvo.getPatientArea());
                if (StringUtils.isNoneBlank(qvo.getPatientStreet())) {
                    sql = sql + " and c.PATIENT_STREET =:street  ";
                    sqlcount = sqlcount + " and c.PATIENT_STREET =:street  ";
                    map.put("street", qvo.getPatientStreet());
                    if (StringUtils.isNoneBlank(qvo.getPatientNeighborhoodCommittee())) {
                        sql = sql + " and c.PATIENT_NEIGHBORHOOD_COMMITTEE =:committtee  ";
                        sqlcount = sqlcount + " and c.PATIENT_NEIGHBORHOOD_COMMITTEE =:committtee  ";
                        map.put("committtee", qvo.getPatientNeighborhoodCommittee());
                    }
                }

            }
        }
        if (StringUtils.isNoneBlank(qvo.getBatchOperatorName())) {
            sql = sql + " and s.BATCH_OPERATOR_NAME LIKE :operatorname ";
            sqlcount = sqlcount + " and s.BATCH_OPERATOR_NAME LIKE :operatorname ";
            map.put("operatorname",  "%"+qvo.getBatchOperatorName() + "%");
        }
        if (StringUtils.isNoneBlank(qvo.getPatientAddress())) {
            sql = sql += " and c.PATIENT_ADDRESS LIKE :address ";
            sqlcount = sqlcount += " and c.PATIENT_ADDRESS LIKE :address ";
            map.put("address", "%"+qvo.getPatientAddress() + "%");
        }
        //服务人群
        int num = 0;
        if (qvo.getPersGroup() != null && qvo.getPersGroup().length > 0) {
            /*for(int i=0;i<qvo.getPersGroup().length;i++){
                sql=sql+" AND x.signPers"+qvo.getPersGroup()[i]+" ='"+qvo.getPersGroup()[i]+"' ";
            }*/
            sql = sql += " and b.LABEL_TYPE='3' and b.LABEL_VALUE IN ( :value ) ";
            sqlcount = sqlcount += " and b.LABEL_TYPE='3' and b.LABEL_VALUE IN ( :value ) ";
            map.put("value", qvo.getPersGroup());
            num = qvo.getPersGroup().length;
        }
        //来源
        if (StringUtils.isNotBlank(qvo.getUpHpis())) {
            sql = sql + " AND a.UP_HPIS =:UP_HPIS ";
            sqlcount = sqlcount + " AND a.UP_HPIS =:UP_HPIS ";
            map.put("UP_HPIS", qvo.getUpHpis());
        }
        //协议状态
        if (StringUtils.isNotBlank(qvo.getSignPrintFlag())) {
            sql = sql + " AND a.SIGN_PRINT_FLAG =:SIGN_PRINT_FLAG ";
            sqlcount = sqlcount + " AND a.SIGN_PRINT_FLAG =:SIGN_PRINT_FLAG ";
            map.put("SIGN_PRINT_FLAG", qvo.getSignPrintFlag());
        }
        //签约状态
        if (StringUtils.isNotBlank(qvo.getSignState())) {
            sql = sql + " AND a.SIGN_STATE =:SIGN_STATE ";
            sqlcount = sqlcount + " AND a.SIGN_STATE =:SIGN_STATE ";
            map.put("SIGN_STATE", qvo.getSignState());
        }

        if (StringUtils.isNotBlank(qvo.getSignWay())) {
            sql = sql + " AND a.SIGN_WAY =:SIGN_WAY ";
            sqlcount = sqlcount + " AND a.SIGN_WAY =:SIGN_WAY ";
            map.put("SIGN_WAY", qvo.getSignWay());
        }
        //是否已续签
        if (StringUtils.isNoneBlank(qvo.getRenew())) {
            sql = sql + " and a.SIGN_CONTRACT_STATE=:CONTRACT_STATE";
            sqlcount = sqlcount + " and a.SIGN_CONTRACT_STATE=:CONTRACT_STATE";
            map.put("CONTRACT_STATE", qvo.getRenew());
        }

        //单独居委会(南平)
        if (StringUtils.isNoneBlank(qvo.getPatientNeighborhoodCommittee())) {
            sql = sql + " and c.PATIENT_NEIGHBORHOOD_COMMITTEE =:committtee  ";
            sqlcount = sqlcount + " and c.PATIENT_NEIGHBORHOOD_COMMITTEE =:committtee  ";
            map.put("committtee", qvo.getPatientNeighborhoodCommittee());
        }

        //是否查看健康报告、add by WangCheng
        if(StringUtils.isNotEmpty(qvo.getSignHealthReportState())){
            map.put("signHealthReportState",qvo.getSignHealthReportState());
            sql = sql + " and a.SIGN_HEALTH_REPORT_STATE = :signHealthReportState";
            sqlcount = sqlcount + " and a.SIGN_HEALTH_REPORT_STATE = :signHealthReportState";
        }

        sql = sql + " GROUP BY a.ID HAVING COUNT(*)>= " + num;
        sqlcount = sqlcount + " GROUP BY a.ID HAVING COUNT(*)>= " + num;
        if (StringUtils.isNotBlank(qvo.getSignrenew())) {
            if (StringUtils.isNotBlank(qvo.getDissolutionType())) {  // 是已解约状态 并且没有已签约的的数据
                if ("1".equals(qvo.getShowHistory())) {// 显示历史签约单
                    sql = " SELECT * FROM ( " + sql + " )c where 1=1 and  signQyCount=0 ";
                    sqlcount =  " SELECT * FROM ( " + sqlcount + " )c where 1=1 and  signQyCount=0  ";
                } else {
                    sql = " SELECT " +
                            " SUBSTRING_INDEX(GROUP_CONCAT(\n" +
                            "\t\t\t\tc.id ORDER BY c.signDate DESC\n" +
                            "\t\t\t),',',1) id,\n" +
                            "SUBSTRING_INDEX(GROUP_CONCAT(\n" +
                            "\t\t\t\tc.signToDate ORDER BY c.signDate DESC\n" +
                            "\t\t\t),',',1) signToDate,\n" +
                            "c.signgwpay,\n" +
                            "c.signybpay,\n" +
                            "c.patientId,\n" +
                            "c.signareacode,\n" +
                            "c.teamId,\n" +
                            "SUBSTRING_INDEX(GROUP_CONCAT(\n" +
                            "\t\t\t\tc.signNum ORDER BY c.signDate DESC\n" +
                            "\t\t\t),',',1) signNum,\n" +
                            "c.contractCode,\n" +
                            "c.name,\n" +
                            "c.patientIdno,\n" +
                            "c.age,\n" +
                            "c.tel,\n" +
                            "c.signzfpay,\n" +
                            "c.signlx,\n" +
                            "STR_TO_DATE(SUBSTRING_INDEX(GROUP_CONCAT(\n" +
                            "\t\t\t\tc.signDate ORDER BY c.signDate DESC\n" +
                            "\t\t\t),',',1), '%Y-%m-%d %H:%i:%s') signDate,\n" +
                            "c.signPayState,\n" +
                            "c.signState,\n" +
                            "c.signczpay,\n" +
                            "c.signDrId,\n" +
                            "c.signDrName,\n" +
                            "c.sex,\n" +
                            "c.patientCard,\n" +
                            "c.signType,\n" +
                            "c.patientAddress,\n" +
                            "c.signTeamName,\n" +
                            "c.batchOperatorName,\n" +
                            "c.batchOperatorId,\n" +
                            "c.signPersGroup,\n" +
                            "c.signsJjType,\n" +
                            "c.upHpis,\n" +
                            "c.patientjmda,\n" +
                            "c.signQyCount,\n" +
                            "c.signHealthReportState,\n" +
                            "c.patientjtda " +
                            " FROM ( " + sql + " )c where 1=1 and  signQyCount=0 ";
                    sqlcount =  " SELECT * FROM ( " + sqlcount + " )c where 1=1 and  signQyCount=0  ";

                }
            }
        }
        //System.out.println(sql);
        List<AppSignVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql,sqlcount, map, AppSignVo.class, qvo);
        // 服务类型
        if(ls != null && ls.size() > 0){
            return ls;
        }
        return null;
    }


    /**
     * 查看所有的签约信息、分省、市、区县、社区等级
     * WangCheng
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppSignVo> findAllSignForm(AppCommQvo qvo) throws Exception {
        String signsJjType = "0";
        String age = "''";
        if ((StringUtils.isNotBlank(qvo.getSignrenew()) && StringUtils.isNotBlank(qvo.getDissolutionType()))) {
            age = "null";
            signsJjType = " ( SELECT count(1) FROM app_sign_form f WHERE f.SIGN_PATIENT_ID = a.SIGN_PATIENT_ID and  f.SIGN_STATE  IN ('2', '0'))";
        }
        HashMap map = new HashMap();
        String sql = "SELECT\n" +
                "\ta.ID id, CONCAT(DATE_FORMAT(a.SIGN_FROM_DATE,'%Y/%m/%d'),\"至\",DATE_FORMAT(a.SIGN_TO_DATE,'%Y/%m/%d')) signToDate, \n '" +
                qvo.getSigngwpay() + "' signgwpay,\n '" +
                qvo.getSignybpay() + "' signybpay,\n" +
                "\tc.ID patientId,\n" +
                "\ta.SIGN_AREA_CODE signareacode,\n" +
                "\ta.SIGN_TEAM_ID teamId,\n" +
                "\ta.SIGN_NUM signNum,\n" +
                "\ta.SIGN_CONTRACT_STATE contractCode,\n" +
                "\tc.PATIENT_NAME name,\n" +
                "\tc.PATIENT_IDNO patientIdno,\n" +
                age + " age," +
                "\tc.PATIENT_TEL tel, a.SIGN_ZF_PAY signzfpay, \n" +
                "\ta.SIGN_LX signlx,\n" +
                "\ta.SIGN_DATE signDate,\n" +
                "\ta.SIGN_PAY_STATE signPayState, \n" +
                "\ta.SIGN_STATE signState,\n" +
                "\ta.SIGN_CZ_PAY signczpay,\n" +
                "\ta.SIGN_DR_ID signDrId,\n" +
                "\t(SELECT DR_NAME from APP_DR_USER g where g.ID=a.SIGN_DR_ID) signDrName,\n" +
                "\tc.PATIENT_GENDER sex,\n" +
                "\tc.PATIENT_CARD patientCard,\n" +
                "\ta.SIGN_TYPE signType, c.PATIENT_ADDRESS patientAddress,\n" +
                "a.SIGN_TEAM_NAME signTeamName,\n" +
                "s.BATCH_OPERATOR_NAME batchOperatorName,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) signPersGroup,\n " +
                " ( SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_ECONOMICS c WHERE c.LABEL_SIGN_ID=a.ID ) signsJjType, " +
                "\ta.UP_HPIS upHpis, c.PATIENT_JMDA patientjmda,\n" +
                signsJjType + " signQyCount , " +
                "\tc.PATIENT_JTDA patientjtda  \n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID " +
                " LEFT JOIN APP_LABEL_ECONOMICS e on a.ID = e.LABEL_SIGN_ID " +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID LEFT " +
                "JOIN APP_SIGN_BATCH s ON a.SIGN_BATCH_ID=s.ID\n" +
                "where 1=1  ";

        // 续签判断
        if (StringUtils.isNotBlank(qvo.getSignrenew())) {
            if (StringUtils.isNotBlank(qvo.getDissolutionType())) {
                sql = sql + " and a.SIGN_STATE='4' ";
            } else {
                Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。   提前一个月需要续签的数据
                cal.add(Calendar.MONTH, +1);//取当前日期的一个月.
                String calDate = ExtendDate.getYMD(cal);
                sql = sql + " AND a.SIGN_TO_DATE <:calDate AND a.SIGN_STATE in ('0','2') AND a.SIGN_GOTO_SIGN_STATE!='2' ";//续签管理初始化增加签约状态0,2、alter by WangCheng
                map.put("calDate", calDate);
            }
        } else {
            sql = sql + " and (a.SIGN_STATE='2' or a.SIGN_STATE='0') ";
        }

        //判断级别
        if (StringUtils.isNotEmpty(qvo.getFindLevel())) {
            if("1".equals(qvo.getFindLevel()) || "2".equals(qvo.getFindLevel()) || "3".equals(qvo.getFindLevel())){
                if(StringUtils.isNotEmpty(qvo.getSignAreaCode())){
                    map.put("signAreaCode",qvo.getSignAreaCode() + "%");
                    sql = sql + " and a.SIGN_AREA_CODE like :signAreaCode";
                }
            }else if("4".equals(qvo.getFindLevel())){//社区
                if(StringUtils.isNotEmpty(qvo.getSignAreaCode())){
                    map.put("signAreaCode",qvo.getSignAreaCode());
                    sql = sql + " and a.SIGN_AREA_CODE = :signAreaCode";
                }
            }
        }

        //模糊搜索 姓名
        if (StringUtils.isNotBlank(qvo.getPatientName())) {
            sql = sql + " AND c.PATIENT_NAME LIKE :searchValue ";
            map.put("searchValue", "%" + qvo.getPatientName() + "%");
        }
        //团队
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            sql = sql + " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID ";
            map.put("SIGN_TEAM_ID", qvo.getTeamId());
            //医生
            if (StringUtils.isNotBlank(qvo.getDrId())) {
                sql = sql + " AND a.SIGN_DR_ID=:SIGN_DR_ID ";
                map.put("SIGN_DR_ID", qvo.getDrId());
            }
        }
        //身份证
        if (StringUtils.isNotBlank(qvo.getPatientIdno())) {
            sql = sql + " AND c.PATIENT_IDNO=:PATIENT_IDNO ";
            map.put("PATIENT_IDNO", qvo.getPatientIdno());
        }
        //居民健康档案
        if (StringUtils.isNotBlank(qvo.getPatientjmda())) {
            sql = sql + " AND c.PATIENT_JMDA=:PATIENT_JMDA ";
            map.put("PATIENT_JMDA", qvo.getPatientjmda());
        }
        //自费金额
        if (StringUtils.isNotBlank(qvo.getSignzfpay())) {
            sql = sql + " AND a.SIGN_ZF_PAY=:SIGN_ZF_PAY ";
            map.put("SIGN_ZF_PAY", qvo.getSignzfpay());
        }
        //签约日期开始
        if (StringUtils.isNotBlank(qvo.getSignDateStart())) {
            sql = sql + " AND a.SIGN_DATE>=:SIGN_DATE_START ";
            map.put("SIGN_DATE_START", qvo.getSignDateStart() + " 00:00:00");
        }
        //签约日期结束
        if (StringUtils.isNotBlank(qvo.getSignDateEnd())) {
            sql = sql + " AND a.SIGN_DATE<=:SIGN_DATE_END ";
            map.put("SIGN_DATE_END", qvo.getSignDateEnd() + " 23:59:59");
        }
        //协议日期开始
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql = sql + " AND a.SIGN_FROM_DATE>=:SIGN_FROM_DATE_START ";
            map.put("SIGN_FROM_DATE_START", qvo.getSignFromDateStart() + " 00:00:00");
        }
        //协议日期结束
        if (StringUtils.isNotBlank(qvo.getSignFromDateEnd())) {
            sql = sql + " AND a.SIGN_FROM_DATE<=:SIGN_FROM_DATE_END ";
            map.put("SIGN_FROM_DATE_END", qvo.getSignFromDateEnd() + " 23:59:59");
        }
        //医保类型
        if (StringUtils.isNotBlank(qvo.getSignlx())) {
            sql = sql + " AND a.SIGN_LX=:SIGN_LX ";
            map.put("SIGN_LX", qvo.getSignlx());
        }
        //人口经济性质
        /*if (qvo.getSignsJjTypes() != null && qvo.getSignsJjTypes().length > 0) {
            sql = sql + " AND a.SIGN_JJ_TYPE in (:SIGN_JJ_TYPE) ";
            map.put("SIGN_JJ_TYPE", qvo.getSignsJjTypes());
        }*/
        if (qvo.getSignsJjTypes() != null && qvo.getSignsJjTypes().length > 0) {
            sql = sql + " and e.LABEL_TYPE = '4' and e.LABEL_VALUE in (:SIGN_JJ_TYPE) ";
            map.put("SIGN_JJ_TYPE", qvo.getSignsJjTypes());
        }
        //助理姓名
        if (StringUtils.isNotBlank(qvo.getSignDrAssistantId())) {
            sql = sql + " AND ( a.SIGN_DR_ASSISTANT_ID =:SIGN_DR_ASSISTANT_ID ) ";
            map.put("SIGN_DR_ASSISTANT_ID", qvo.getSignDrAssistantId());
        }
        //vip
        if (StringUtils.isNotBlank(qvo.getVip()) && qvo.getVip().equals("1")) {
            sql = sql + " AND a.SIGN_TYPE='2'";
        } else {
            sql = sql + " AND a.SIGN_TYPE='1'";
        }
        //到期
        if (StringUtils.isNotBlank(qvo.getExpire()) && qvo.getExpire().equals("1")) {
            sql = sql + " AND a.SIGN_TO_DATE>SYSDATE()";
        }
        String plg = "";
        //疾病类型
        if (StringUtils.isNotBlank(qvo.getLabelGruops())) {
            String[] lg = qvo.getLabelGruops().split(";");
            if (lg != null && lg.length > 0) {
                for (String l : lg) {
                    if (org.apache.commons.lang.StringUtils.isBlank(plg)) {
                        plg = l;
                    } else {
                        plg += ";" + l;
                    }
                }
            }
        }
        //健康情况
        if (StringUtils.isNotBlank(qvo.getSignHealthGroup())) {
            String[] hg = qvo.getSignHealthGroup().split(";");
            if (hg != null && hg.length > 0) {
                sql = sql + " and a.SIGN_HEALTH_GROUP  in (:SIGN_HEALTH_GROUP)";
                map.put("SIGN_HEALTH_GROUP", hg);
            }
        }
        //三师
        if (StringUtils.isNotBlank(qvo.getServiceA()) && qvo.getServiceA().equals("1")) {
            sql = sql + " AND a.SIGN_SERVICE_A='2'";
            String bacolor[] = new String[3];
            int bia = 0;
            if (StringUtils.isNotBlank(qvo.getServiceAred()) && qvo.getServiceAred().equals("1")) {
                bacolor[bia] = "red";
                bia++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceAyellow()) && qvo.getServiceAyellow().equals("1")) {
                bacolor[bia] = "yellow";
                bia++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceAgreen()) && qvo.getServiceAgreen().equals("1")) {
                bacolor[bia] = "green";
                bia++;
            }
            if ((StringUtils.isNotBlank(qvo.getServiceAred()) && qvo.getServiceAred().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceAyellow()) && qvo.getServiceAyellow().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceAgreen()) && qvo.getServiceAgreen().equals("1"))) {
                sql = sql + " AND b.LABEL_COLOR in (:LABEL_COLOR)";
                map.put("LABEL_COLOR", bacolor);
            }
        }
        //居家
        if (StringUtils.isNotBlank(qvo.getServiceB()) && qvo.getServiceB().equals("1")) {
            sql = sql + " AND a.SIGN_SERVICE_B='2'";
            String bcolor[] = new String[3];
            int bi = 0;
            if (StringUtils.isNotBlank(qvo.getServiceBred()) && qvo.getServiceBred().equals("1")) {
                bcolor[bi] = "red";
                bi++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceByellow()) && qvo.getServiceByellow().equals("1")) {
                bcolor[bi] = "yellow";
                bi++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceBgreen()) && qvo.getServiceBgreen().equals("1")) {
                bcolor[bi] = "green";
                bi++;
            }
            if ((StringUtils.isNotBlank(qvo.getServiceBred()) && qvo.getServiceBred().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceByellow()) && qvo.getServiceByellow().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceBgreen()) && qvo.getServiceBgreen().equals("1"))) {
                sql = sql + " AND a.SIGN_SERVICE_B_COLOR in (:SIGN_SERVICE_B_COLOR)";
                map.put("SIGN_SERVICE_B_COLOR", bcolor);
            }
        }
        //详细地址
        if (StringUtils.isNoneBlank(qvo.getPatientCity())) {
            sql = sql + " and c.PATIENT_CITY =:city ";
            map.put("city", qvo.getPatientCity());
            if (StringUtils.isNoneBlank(qvo.getPatientArea())) {
                sql = sql + " and c.PATIENT_AREA =:area ";
                map.put("area", qvo.getPatientArea());
                if (StringUtils.isNoneBlank(qvo.getPatientStreet())) {
                    sql = sql + " and c.PATIENT_STREET =:street  ";
                    map.put("street", qvo.getPatientStreet());
                    if (StringUtils.isNoneBlank(qvo.getPatientNeighborhoodCommittee())) {
                        sql = sql + " and c.PATIENT_NEIGHBORHOOD_COMMITTEE =:committtee  ";
                        map.put("committtee", qvo.getPatientNeighborhoodCommittee());
                    }
                }

            }
        }
        if (StringUtils.isNoneBlank(qvo.getBatchOperatorName())) {
            sql = sql + " and s.BATCH_OPERATOR_NAME LIKE :operatorname ";
            map.put("operatorname", "%" + qvo.getBatchOperatorName() + "%");
        }
        if (StringUtils.isNoneBlank(qvo.getPatientAddress())) {
            sql = sql += " and c.PATIENT_ADDRESS LIKE :address ";
            map.put("address", "%" + qvo.getPatientAddress() + "%");
        }
        //服务人群
        int num = 0;
        if (qvo.getPersGroup() != null && qvo.getPersGroup().length > 0) {
            /*for(int i=0;i<qvo.getPersGroup().length;i++){
                sql=sql+" AND x.signPers"+qvo.getPersGroup()[i]+" ='"+qvo.getPersGroup()[i]+"' ";
            }*/
            sql = sql += " and b.LABEL_TYPE='3' and b.LABEL_VALUE IN ( :value ) ";
            map.put("value", qvo.getPersGroup());
            num = qvo.getPersGroup().length;
        }
        //来源
        if (StringUtils.isNotBlank(qvo.getUpHpis())) {
            sql = sql + " AND a.UP_HPIS =:UP_HPIS ";
            map.put("UP_HPIS", qvo.getUpHpis());
        }
        //协议状态
        if (StringUtils.isNotBlank(qvo.getSignPrintFlag())) {
            sql = sql + " AND a.SIGN_PRINT_FLAG =:SIGN_PRINT_FLAG ";
            map.put("SIGN_PRINT_FLAG", qvo.getSignPrintFlag());
        }
        //签约状态
        if (StringUtils.isNotBlank(qvo.getSignState())) {
            sql = sql + " AND a.SIGN_STATE =:SIGN_STATE ";
            map.put("SIGN_STATE", qvo.getSignState());
        }

        if (StringUtils.isNotBlank(qvo.getSignWay())) {
            sql = sql + " AND a.SIGN_WAY =:SIGN_WAY ";
            map.put("SIGN_WAY", qvo.getSignWay());
        }
        //是否已续签
        if (StringUtils.isNoneBlank(qvo.getRenew())) {
            sql = sql + " and a.SIGN_CONTRACT_STATE=:CONTRACT_STATE";
            map.put("CONTRACT_STATE", qvo.getRenew());
        }

        //单独居委会(南平)
        if (StringUtils.isNoneBlank(qvo.getPatientNeighborhoodCommittee())) {
            sql = sql + " and c.PATIENT_NEIGHBORHOOD_COMMITTEE =:committtee  ";
            map.put("committtee", qvo.getPatientNeighborhoodCommittee());
        }

        sql = sql + " GROUP BY a.ID HAVING COUNT(*)>= " + num;
        if (StringUtils.isNotBlank(qvo.getSignrenew())) {
            if (StringUtils.isNotBlank(qvo.getDissolutionType())) {  // 是已解约状态 并且没有已签约的的数据
                sql = " SELECT * FROM ( " + sql + " )c where 1=1 and  signQyCount=0 ";
            }
        }

        //System.out.println(sql);
        List<AppSignVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppSignVo.class, qvo);
        // 服务类型
//        if (ls != null && ls.size() > 0) {
//            for (int i = 0; i < ls.size(); i++) {
//                ls.get(i).setSigngwpay(qvo.getSigngwpay());
//                ls.get(i).setSignybpay(qvo.getSignybpay());
//            }
//        }
        return ls;
    }

    /**
     * 建档立卡贫困人口签约查询
     * WangCheng
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppSignVo> findPoorSign(AppCommQvo qvo) throws Exception {
        HashMap map = new HashMap();
        map.put("SIGN_STATE",new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()});
        String sql = "SELECT\n" +
                "\ta.ID id, CONCAT(DATE_FORMAT(a.SIGN_FROM_DATE,'%Y/%m/%d'),\"至\",DATE_FORMAT(a.SIGN_TO_DATE,'%Y/%m/%d')) signToDate, \n '" +
                qvo.getSigngwpay() + "' signgwpay,\n '" +
                qvo.getSignybpay() + "' signybpay,\n" +
                "\tc.ID patientId,\n" +
                "\ta.SIGN_AREA_CODE signareacode,\n" +
                "\ta.SIGN_TEAM_ID teamId,\n" +
                "\ta.SIGN_NUM signNum,\n" +
                "\tc.PATIENT_NAME name,\n" +
                "\tc.PATIENT_IDNO patientIdno,\n" +
                "\tc.PATIENT_JMDA patientJmda,\n" +
                "'' age," +
                "\tc.PATIENT_TEL tel, a.SIGN_ZF_PAY signzfpay, \n" +
                "\ta.SIGN_LX signlx,\n" +
                "\ta.SIGN_DATE signDate,\n" +
                "\ta.SIGN_PAY_STATE signPayState, \n" +
                "\ta.SIGN_STATE signState,\n" +
                "\ta.SIGN_CZ_PAY signczpay,\n" +
                "\ta.SIGN_DR_ID signDrId,\n" +
                "\t(SELECT DR_NAME from APP_DR_USER g where g.ID=a.SIGN_DR_ID) signDrName,\n" +
                "\tc.PATIENT_GENDER sex,\n" +
                "\tc.PATIENT_CARD patientCard,\n" +
                "\ta.SIGN_TYPE signType, c.PATIENT_ADDRESS patientAddress,\n" +
                "a.SIGN_TEAM_NAME signTeamName,\n" +
                "s.BATCH_OPERATOR_NAME batchOperatorName,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) signPersGroup,\n " +
                " ( SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_ECONOMICS c WHERE c.LABEL_SIGN_ID=a.ID ) signsJjType, " +
                "\ta.UP_HPIS upHpis \n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID " +
                " LEFT JOIN APP_LABEL_ECONOMICS e on a.ID = e.LABEL_SIGN_ID " +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID LEFT " +
                "JOIN APP_SIGN_BATCH s ON a.SIGN_BATCH_ID=s.ID\n" +
                "where 1=1 and a.SIGN_STATE IN (:SIGN_STATE) ";

        //团队
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            map.put("SIGN_TEAM_ID", qvo.getTeamId());
            sql = sql + " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID ";
        }
        //人口经济性质
        if (StringUtils.isNotEmpty(qvo.getEconomicType())) {
            map.put("SIGN_JJ_TYPE", qvo.getEconomicType());
            sql = sql + " and e.LABEL_TYPE = '4' and e.LABEL_VALUE = :SIGN_JJ_TYPE";
        }
        int num = 0;

        sql = sql + " GROUP BY a.ID HAVING COUNT(*)>= " + num;
        List<AppSignVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppSignVo.class, qvo);
        if (ls != null && ls.size() > 0) {
            return ls;
        }
        return null;
    }


    @Override
    public Integer findSignXxCount(AppCommQvo qvo) throws Exception {
        HashMap map = new HashMap();
        String[] state = {SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};//状态
        String[] fwState = {ResidentMangeType.PTRQ.getValue(), ResidentMangeType.WEIZHI.getValue()};
        String[] jjState = {EconomicType.JDLKPKRK.getValue(), EconomicType.DBH.getValue(), EconomicType.TKH.getValue(), EconomicType.JSTSJT.getValue()};
        map.put("SIGN_STATE", state);
        map.put("SIGN_PERS_GROUP", fwState);
        map.put("SIGN_JJ_TYPE", jjState);
        if (qvo.getSignType().equals("0")) {
            String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE)";
            //团队
            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                sql += " AND t.SIGN_TEAM_ID=:SIGN_TEAM_ID ";
                map.put("SIGN_TEAM_ID", qvo.getTeamId());

            }
            //医生
            if (StringUtils.isNotBlank(qvo.getDrId())) {
                sql += " AND t.SIGN_DR_ID=:SIGN_DR_ID ";
                map.put("SIGN_DR_ID", qvo.getDrId());
            }
            int result = sysDao.getServiceDo().findCount(sql, map);
            return result;
        } else {
            String sqlGroup = "SELECT t.* FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) AND b.LABEL_TYPE='3'";
            String sqlEconomics = "SELECT t.* FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE IN (:SIGN_JJ_TYPE) AND b.LABEL_TYPE='4' ";

            //团队
            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                sqlGroup += " AND t.SIGN_TEAM_ID=:SIGN_TEAM_ID ";
                sqlEconomics += " AND t.SIGN_TEAM_ID=:SIGN_TEAM_ID ";
                map.put("SIGN_TEAM_ID", qvo.getTeamId());

            }
            //医生
            if (StringUtils.isNotBlank(qvo.getDrId())) {
                sqlGroup += " AND t.SIGN_DR_ID=:SIGN_DR_ID ";
                sqlEconomics += " AND t.SIGN_DR_ID=:SIGN_DR_ID ";
                map.put("SIGN_DR_ID", qvo.getDrId());
            }
            String sql = " SELECT count(1) FROM ( " + sqlGroup + " UNION " + sqlEconomics + " ) c";
            int result = sysDao.getServiceDo().findCount(sql, map);
            return result;
        }
    }

    /**
     * 查询医生履约人员列表
     *
     * @param qvo
     * @return
     */
    @Override
    public List<AppDrSignPeoleListEntity> findSignFormList(AppCommLyQvo qvo) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        String resultServe = "";
        map.put("SERVE_ROLE_HOSP_ID", qvo.getHospId());
        String sqlHosp = "SELECT * FROM APP_SERVE_ROLE c WHERE 1=1 AND c.SERVE_ROLE_HOSP_ID =:SERVE_ROLE_HOSP_ID";
        List lsHosp = sysDao.getServiceDo().findSqlMap(sqlHosp, map);
        map.put("SERVE_ROLE_AREA_CODE", qvo.getAreaCode());
        String sqlArea = "SELECT * FROM APP_SERVE_ROLE c WHERE 1=1 AND  c.SERVE_ROLE_AREA_CODE =:SERVE_ROLE_AREA_CODE";
        List lsArea = sysDao.getServiceDo().findSqlMap(sqlArea, map);
        if (lsArea != null && lsArea.size() > 0) {
            resultServe = " AND c.SERVE_ROLE_AREA_CODE = :SERVE_ROLE_AREA_CODE ";
        }
        if (lsHosp != null && lsHosp.size() > 0) {
            resultServe = " AND c.SERVE_ROLE_HOSP_ID = :SERVE_ROLE_HOSP_ID ";
        }
        map.put("value", qvo.getPerType());
        map.put("drId", qvo.getDrId());
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("signState", signStates);
        Calendar cal = Calendar.getInstance();
        map.put("year", cal.get(Calendar.YEAR));
        String sql = "SELECT\n" +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\ta.SIGN_PATIENT_IDNO patientIdno," +
                "\ta.ID signId,\n" +
                "\ta.SIGN_DR_ID drId,\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tmax(s.SON_SERVE_SET_SPACE) serSpace\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_SERVE_ROLE c\n" +
                "\t\tINNER JOIN APP_SERVE_ROLE_SON s ON c.ID = s.SON_SERVE_ROLE_ID\n" +
                "\t\tINNER JOIN APP_SERVE_SETTING st ON st.ID = s.SON_SERVE_ID\n" +
                "\t\tWHERE\n" +
                "\t\t\t1 = 1\n" +
                "\t\tAND st.SER_OBJECT_VALUE in (SELECT g.LABEL_VALUE FROM APP_LABEL_GROUP g WHERE g.LABEL_TYPE = 3 AND g.LABEL_SIGN_ID = a.ID)\n" +
                resultServe +
                "\t\tAND st.SER_VALUE in (SELECT e.SER_VALUE FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN ( SELECT g.LABEL_VALUE FROM APP_LABEL_GROUP g WHERE g.LABEL_TYPE = 3 AND g.LABEL_SIGN_ID = a.ID GROUP BY g.LABEL_VALUE) AND e.SER_VALUE =:value)\n" +
                "\t\tGROUP BY\n" +
                "\t\t\tst.SER_VALUE\n" +
                "\t\tORDER BY\n" +
                "\t\t\tst.SER_OBJECT_VALUE,\n" +
                "\t\t\tst.SER_VALUE\n" +
                "  ) serSpace," +
                "'" + qvo.getPerType() + "' fwbValue,\n" +
                "\t'' signCount\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "AND a.SIGN_DR_ID =:drId\n" +
                "AND a.SIGN_STATE IN (:signState)\n" +
                "AND (SELECT GROUP_CONCAT(e.SER_TITLE) FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN ( SELECT g.LABEL_VALUE FROM APP_LABEL_GROUP g WHERE g.LABEL_TYPE = 3 AND g.LABEL_SIGN_ID = a.ID GROUP BY g.LABEL_VALUE ) AND e.SER_VALUE =:value) IS NOT NULL\n";
        if (ResidentMangeType.ETLZLS.getValue().equals(qvo.getFwType())) {
            if ("1".equals(qvo.getFollowType())) {
                sql = " SELECT\n" +
                        "\ta.SIGN_PATIENT_ID patientId,\n" +
                        "\ta.SIGN_PATIENT_IDNO patientIdno,\n" +
                        "\ta.ID signId,\n" +
                        "\ta.SIGN_DR_ID drId,\n" +
                        "'" + qvo.getFollowType() + "' followType, " +
                        "'' signCount " +
                        "FROM\n" +
                        "\tAPP_SIGN_FORM a\n" +
                        "INNER JOIN APP_SIGN_CHILD_OR_WOMEN b ON a.ID = b.SCOW_SIGN_ID\n" +
                        "WHERE\n" +
                        "\ta.SIGN_DR_ID = :drId\n" +
                        "AND a.sign_state IN (:signState)\n" +
                        "AND TIMESTAMPDIFF(\n" +
                        "\tDAY,\n" +
                        "\tb.SCOW_OUT_HOSPITAL_DATE,\n" +
                        "\ta.SIGN_FROM_DATE\n" +
                        ") <= 7 AND DATE_FORMAT(b.SCOW_OUT_HOSPITAL_DATE,'%Y')=:year";

            }
        }
        List<AppDrSignPeoleListEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppDrSignPeoleListEntity.class);
        return list;
    }

    /**
     * 查询过期签约单列表
     *
     * @return
     */
    @Override
    public List<AppSignForm> findOverdue() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("signState", signStates);
        Calendar nowCal = Calendar.getInstance();
        nowCal.add(Calendar.DATE,-1);
        map.put("lastTime",ExtendDate.getYMD(nowCal)+" 23:59:59");//因为调度时间是今天凌晨，所以查询昨天到期的签约单
        String sql = "SELECT * FROM APP_SIGN_FORM " +
                "WHERE 1=1 ";
        sql += " AND SIGN_STATE IN (:signState)";
        sql += " AND SIGN_TO_DATE <=:lastTime " ;
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        return list;
    }

    /**
     * 续签(续签原团队和医生)
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSignForm renewSign(AppCommQvo qvo) throws Exception {
        AppSignForm oldForm = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, qvo.getSignFormId());
        if (oldForm == null) {
            throw new Exception("当前患者未签约");
        }
        AppSignBatch batch = new AppSignBatch();
        AppSignForm form = new AppSignForm();
        form.setUpHpis("1");
        AppTeam team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, oldForm.getSignTeamId());
        AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, qvo.getPatientId());
        batch.setBatchCreateDate(Calendar.getInstance());
        batch.setBatchTeamId(team.getId());
        batch.setBatchTeamName(team.getTeamName());
        batch.setBatchCreatePersid(user.getId());
        batch.setBatchPatientName(user.getPatientName());
        //组织批次号
        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, team.getTeamHospId());
        if (dept != null && dept.getHospAreaCode() != null) {
            AppSerial serial = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
            if (serial != null) {
                Map<String, Object> bcnum = this.getNum(serial.getSerialNo(), SignFormType.APPSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                batch.setBatchNum(bcnum.get("new").toString());//批次号
            }
            AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
            if (serialSign != null) {
                Map<String, Object> sinum = this.getNum(serialSign.getSerialNo(), SignFormType.APPSTATE.getValue());
                serialSign.setSerialNo(sinum.get("old").toString());
                sysDao.getServiceDo().modify(serialSign);
                form.setSignNum(sinum.get("new").toString());//签约编码
            }
        }
        batch.setBatchOperatorId(oldForm.getSignDrId());
        AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(oldForm.getSignDrId());
        if (drUser != null) {
            batch.setBatchOperatorName(drUser.getDrName());
        }
        batch.setBatchAreaCode(dept.getHospAreaCode());
        sysDao.getServiceDo().add(batch);
        String formCal = ExtendDate.getYMD_h_m_s(oldForm.getSignToDate());
        Calendar call = ExtendDate.getCalendar(formCal);
        call.add(Calendar.DATE, 1);
        form.setSignFromDate(call);
        Calendar ccc = Calendar.getInstance();
        String nowTime = ExtendDate.getYMD(ccc) + " 00:00:00";
        boolean ff = false;//判断是否现在就执行续签
        boolean jj = false;//判断是否是建档立卡经济类型
        if (call.compareTo(ExtendDate.getCalendar(nowTime)) == 1) {
            form.setSignState(SignFormType.XQ.getValue());//续签
        } else {
            form.setSignState(SignFormType.YUQY.getValue());
            oldForm.setSignState(SignFormType.YJY.getValue());
            sysDao.getServiceDo().modify(oldForm);
            ff = true;
        }

        String oldCal = ExtendDate.getYMD_h_m_s(oldForm.getSignToDate());
        Calendar cal = ExtendDate.getCalendar(oldCal);
        cal.add(Calendar.YEAR, 1);
        form.setSignToDate(cal);

        form.setSignPatientId(user.getId());
        form.setSignPatientAge(Integer.parseInt(user.getPatientAge()));
        form.setSignPatientGender(user.getPatientGender());
        form.setSignPatientIdNo(user.getPatientIdno());
        form.setSignPayState(qvo.getPayType());//0：未缴费 1:已缴费
        form.setSignType(oldForm.getSignType());//1家庭签约
        form.setSignBatchId(batch.getId());
        form.setSignPersGroup(oldForm.getSignPersGroup());
        form.setSignHealthGroup(oldForm.getSignHealthGroup());
        form.setSignTeamId(team.getId());
        form.setSignTeamName(team.getTeamName());
        form.setSignHospId(dept.getId());
        form.setSignAreaCode(dept.getHospAreaCode());
        form.setSignWay("0");//个人发起签约
        form.setSignDate(Calendar.getInstance());
        form.setSignDrId(oldForm.getSignDrId());//绑定医生
        form.setSignServiceA(oldForm.getSignServiceA());
        form.setSignServiceADate(oldForm.getSignServiceADate());
        form.setSignServiceAPayState(oldForm.getSignServiceAPayState());
        form.setSignServiceB(oldForm.getSignServiceB());
        form.setSignServiceBDate(oldForm.getSignServiceBDate());
        form.setSignServiceBPayState(oldForm.getSignServiceBPayState());
        form.setSignServiceBColor(oldForm.getSignServiceBColor());
        form.setSignContractState("0");//是否已续约 1是 0否
        form.setSignGreenState("0");//1是 0否
        form.setSignYellowState("0");//1是 0否
        form.setSignRedState("0");//1是 0否
        form.setSignsJjType(oldForm.getSignsJjType());
        form.setSignChangeState("0");
        form.setSignRenewState("0");
        form.setSignGoToSignState("0");
        form.setSignpackageid(qvo.getSignpackageid());
        if (StringUtils.isNotBlank(qvo.getSignpackageid())) {
            String text = sysDao.getAppSignformDao().findPkRemark(qvo.getSignpackageid());
            form.setSigntext(text);
        }
        //判断是否开启本年度签约协议
        AppSignSetting setting = sysDao.getAppSignSettingDao().findByAreaCode(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"));
        if(setting != null){
            if("1".equals(setting.getSerOpenYear())){//开启续签的签约单开始时间是旧的结束时间+1到旧的结束时间所在的年份12月31日
                String yearTime = ExtendDate.getYMD_h_m_s(oldForm.getSignToDate());
                Calendar startDate = ExtendDate.getCalendar(yearTime);
                startDate.add(Calendar.DATE,1);
                String endDate = ExtendDate.getYYYY(startDate)+"-12-31";
                form.setSignFromDate(startDate);
                form.setSignToDate(ExtendDate.getCalendar(endDate));
            }
        }
        sysDao.getServiceDo().add(form);
        oldForm.setSignGoToSignState("2");
        oldForm.setSignRenewOrGoToSignDate(Calendar.getInstance());
//        oldForm.setSignContractState("1");//是否已续约 1是 0否
        sysDao.getServiceDo().modify(oldForm);
        //服务人群
        List<AppLabelGroup> labelList = sysDao.getServiceDo().loadByPk(AppLabelGroup.class, "labelSignId", oldForm.getId());
        if (labelList != null && labelList.size() > 0) {
            for (AppLabelGroup g : labelList) {
                AppLabelGroup alg = new AppLabelGroup();
                alg.setLabelId(g.getLabelId());
                alg.setLabelSignId(form.getId());
                alg.setLabelTeamId(team.getId());
                alg.setLabelTitle(g.getLabelTitle());
                alg.setLabelValue(g.getLabelValue());
                alg.setLabelType(g.getLabelType());
                alg.setLabelColor(g.getLabelColor());
                alg.setLabelAreaCode(form.getSignAreaCode());
                sysDao.getServiceDo().add(alg);
            }
        }
        //疾病类型
        List<AppLabelDisease> labelDiseases = sysDao.getServiceDo().loadByPk(AppLabelDisease.class, "labelSignId", oldForm.getId());
        if (labelDiseases != null && labelDiseases.size() > 0) {
            for (AppLabelDisease g : labelDiseases) {
                AppLabelDisease alg = new AppLabelDisease();
                alg.setLabelId(g.getLabelId());
                alg.setLabelSignId(form.getId());
                alg.setLabelTeamId(team.getId());
                alg.setLabelTitle(g.getLabelTitle());
                alg.setLabelValue(g.getLabelValue());
                alg.setLabelType(g.getLabelType());
                alg.setLabelColor(g.getLabelColor());
                alg.setLabelAreaCode(form.getSignAreaCode());
                sysDao.getServiceDo().add(alg);
            }
        }
        //经济类型
        List<AppLabelEconomics> listE = sysDao.getServiceDo().loadByPk(AppLabelEconomics.class, "labelSignId", oldForm.getId());
        if (listE != null && listE.size() > 0) {
            for (AppLabelEconomics g : listE) {
                if (EconomicType.JDLKPKRK.getValue().equals(g.getLabelValue())) {
                    jj = true;
                }
                AppLabelEconomics alg = new AppLabelEconomics();
                alg.setLabelId(g.getLabelId());
                alg.setLabelSignId(form.getId());
                alg.setLabelTeamId(team.getId());
                alg.setLabelTitle(g.getLabelTitle());
                alg.setLabelValue(g.getLabelValue());
                alg.setLabelType(g.getLabelType());
                alg.setLabelColor(g.getLabelColor());
                alg.setLabelAreaCode(form.getSignAreaCode());
                sysDao.getServiceDo().add(alg);
            }
        }
        if (ff && jj) {
            String fwTypepss = sysDao.getAppLabelGroupDao().findFwValue(oldForm.getId());
            String[] fwTypesss = null;
            if (StringUtils.isNotBlank(fwTypepss)) {
                fwTypesss = fwTypepss.split(",");
            }
            sysDao.getAppSignformDao().addOrModifyArchivingSign(form.getSignPatientIdNo(), form.getId(), form.getSignDrId(), form.getSignTeamId(), form.getSignState(), fwTypesss, form.getSignAreaCode(), form.getSignHospId(), form.getSignFromDate(), user);
        }

        sysDao.getAppNoticeDao().addNotices("续签提醒",
                "您好，您的居民" + user.getPatientName() + "（" + user.getStrPatientGender() + "," + user.getPatientAge() + "岁）已续签成功。",
                NoticesType.QYXX.getValue(), user.getId(), form.getSignDrId(), form.getId(), DrPatientType.DR.getValue());
        return form;
    }

    /**
     * 申请变更
     * cjw
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String changeStates(YsChangeCountQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("state", signStates);
        map.put("drId", qvo.getDrId());
        String sql = "";
        if ("0".equals(qvo.getChangeType())) { //全选医生在这个团队下所签的患者.
            map.put("teamId", qvo.getTeamId());
            sql = " SELECT * FROM APP_SIGN_FORM WHERE SIGN_STATE IN (:state) " +
                    "AND SIGN_DR_ID=:drId AND SIGN_TEAM_ID =:teamId";
        } else if ("1".equals(qvo.getChangeType())) {
            if (StringUtils.isBlank(qvo.getPatientId())) {
                return "false";
            }
            map.put("patientId", qvo.getPatientId().split(";"));
            //cjw
            sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_STATE in (:state) " +
                    "AND SIGN_DR_ID=:drId  " +
                    "AND SIGN_PATIENT_ID IN (:patientId)";
            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                map.put("teamId", qvo.getTeamId());
                sql += " AND SIGN_TEAM_ID =:teamId";
            }
        }/*else{
            //全选服务人群、疾病类型、健康情况中的一种
            if(StringUtils.isNotBlank(qvo.getLabelType())){
                String[] labelTypes = qvo.getLabelType().split(";");
                if(labelTypes!=null && labelTypes.length>0){
                    map.put("labelType",labelTypes);
                    sql = "SELECT\n" +
                            "\ta.*\n" +
                            "FROM\n" +
                            "\tAPP_SIGN_FORM a\n" +
                            "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                            "WHERE\n" +
                            "\tb.LABEL_TYPE IN :labelType\n" +
                            "AND a.SIGN_STATE =:state'\n" +
                            "AND a.SIGN_TEAM_ID =:teamId\n" +
                            "AND a.SIGN_DR_ID =:drId\n" +
                            "GROUP BY\n" +
                            "\ta.ID";
                }
            }else{
                return "false";
            }
        }*/
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (list != null && list.size() > 0) {
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, qvo.getHospId());
            Map<String, Object> sinum = new HashMap<>();
            if (dept != null) {
                AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                if (serialSign != null) {
                    sinum = this.getNum(serialSign.getSerialNo(), SignFormType.APPSTATE.getValue());
                    serialSign.setSerialNo(sinum.get("old").toString());
                    sysDao.getServiceDo().modify(serialSign);
                }
            }



            for (AppSignForm ls : list) {
                ls.setSignChangeState(ChangeState.SQBG.getValue());
                ls.setSignChangeDr(qvo.getChangeDr());
                ls.setSignChangeTeam(qvo.getChangeTeam());
                ls.setSignChangeDate(Calendar.getInstance());
                sysDao.getServiceDo().modify(ls);
                AppSignChange sc = new AppSignChange();
                sc.setChangeDate(Calendar.getInstance());
                sc.setChangeDrId(qvo.getChangeDr());
                sc.setChangeTeamId(qvo.getChangeTeam());
                sc.setChangeDr(qvo.getDrId());
                sc.setChangeTeam(qvo.getTeamId());
                sc.setChangeNum(sinum.get("new").toString());
                sc.setChangeSignId(ls.getId());
                sc.setChangeUserId(ls.getSignPatientId());
                sc.setChangeState(ChangeState.SQBG.getValue());
                sysDao.getServiceDo().add(sc);
            }
            //消息提示 qvo.getDrId()申请医生 qvo.getChangeDr() 变更医生
            sysDao.getAppNoticeDao().addNotices("变更消息提醒", "您有一个变更申请，请注意查看", NoticesType.XTXX.getValue(), qvo.getDrId(),
                    qvo.getChangeDr(), sinum.get("new").toString(), DrPatientType.DR.getValue());
            return sinum.get("new").toString();
        } else {
            return "false";
        }

    }


    /**
     * cjw
     * 身份证 查询
     */

    public AppSignForm findpatientIdNo(String patientidno) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "";
        if (StringUtils.isNotBlank(patientidno)) {
            sql = " SELECT * from APP_SIGN_FORM u where u.SIGN_PATIENT_IDNO=:patientidno ";
            map.put("patientidno", patientidno);
            List<AppSignForm> vo = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
            if (vo != null && vo.size() > 0) {
                return vo.get(0);
            }
        }
        return null;
    }


    /**
     * 获取转签数据列表
     *
     * @return
     */
    @Override
    public List<AppSignForm> findByDate() throws Exception{
        Map<String, Object> map = new HashMap<>();
//        String[] strs = new String[]{SignFormType.ZQ.getValue(),SignFormType.XQ.getValue()};
        map.put("SIGN_STATE", SignFormType.ZQ.getValue());
        map.put("lastTime",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_STATE = :SIGN_STATE AND SIGN_FROM_DATE<=:lastTime ";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        return list;
    }

    /**
     * 家庭版签约申请
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppSignForm> signFormFamily(AppFamilySignQvo qvo) throws Exception {
        List<AppSignForm> list = new ArrayList<>();
        if ("2".equals(qvo.getType())) {
            AppSignForm signForm = findByPeople(qvo.getPatientId());
            if (signForm != null) {
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, signForm.getSignHospId());
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, signForm.getSignDrId());
                if (qvo.getSonList() != null && qvo.getSonList().size() > 0) {
                    for (AppFamilySignSonQvo sign : qvo.getSonList()) {
                        AppSignForm form = new AppSignForm();//签约单
                        form.setUpHpis("1");
                        AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, sign.getPatientId());//患者
                        AppTeam teamvo = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, signForm.getSignTeamId());
                        form.setSignBatchId(signForm.getSignBatchId());
                        //组织编码
                        if (dept != null && dept.getHospAreaCode() != null) {
                            form.setSignHospId(dept.getId());
                            form.setSignAreaCode(dept.getHospAreaCode());
                            AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                            if (serialSign != null) {
                                Map<String, Object> sinum = this.getNum(serialSign.getSerialNo(), SignFormType.APPSTATE.getValue());
                                serialSign.setSerialNo(sinum.get("old").toString());
                                sysDao.getServiceDo().modify(serialSign);
                                form.setSignNum(sinum.get("new").toString());//签约编码
                            }
                        }
                        //
                        form.setSignPatientId(sign.getPatientId());
                        form.setSignDate(Calendar.getInstance());
                        //是否直接同意签约
                        AppHospExtend extend = this.sysDao.getAppHospExtendDao().findByHospId(form.getSignHospId());
                        boolean flagAgree = false;
                        if (extend != null) {
                            if (extend.getExtHreState().equals(AppFlagAgree.KAIQI.getValue())) {
                                form.setSignState("2");//2:已签约
                                form.setSignFromDate(Calendar.getInstance());
                                Calendar end = Calendar.getInstance();
                                end.add(Calendar.YEAR, 1);
                                end.add(Calendar.DATE, -1);
                                form.setSignToDate(end);
                                flagAgree = true;
                            } else {
                                form.setSignState("1");//1:待签约
                            }
                        } else {
                            form.setSignState("1");//1:待签约
                        }
                        form.setSignPayState("0");//0：未缴费
                        form.setSignType("1");//1家庭签约
                        form.setSignTeamId(teamvo.getId());
                        form.setSignTeamName(teamvo.getTeamName());
                        form.setSignWay("1");//代签约
                        form.setSignPatientAge(Integer.parseInt(user.getPatientAge()));
                        form.setSignPatientGender(user.getPatientGender());
                        form.setSignPatientIdNo(user.getPatientIdno());
                        form.setSignDrId(qvo.getDrId());//绑定医生
                        form.setSignContractState("0");//1是 0否
                        form.setSignGreenState("0");//1是 0否
                        form.setSignYellowState("0");//1是 0否
                        form.setSignRedState("0");//1是 0否
                        form.setSignpackageid(sign.getServerIds());//保存套餐id
                        double num = 0;
                        if (StringUtils.isNotBlank(sign.getServerIds())) {
                            String[] serverIdss = sign.getServerIds().split(";");
                            for (String serverId : serverIdss) {
                                AppServeSetmeal meal = (AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class, serverId);
                                if (meal != null) {
                                    if (StringUtils.isNotBlank(meal.getSersmTotalOneFee())) {
                                        num += Double.parseDouble(meal.getSersmTotalOneFee());
                                    }
                                }
                            }
                            String text = sysDao.getAppSignformDao().findPkRemark(sign.getServerIds());
                            form.setSigntext(text);
                            if ("3501".equals(AreaUtils.getAreaCode(form.getSignAreaCode(), "2"))) {//福州做财政补助和自费
                                String[] mealIds = sign.getServerIds().split(";");
                                double zfpay = 0.0;//自费
                                double czpay = 0.0;//财政补贴
                                double totalFee = 0.0;//总费用
                                for (String mealId : mealIds) {
                                    AppServeSetmeal meal = (AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class, mealId);
                                    if (meal != null) {
                                        if (StringUtils.isNotBlank(meal.getSersmTotalFee())) {//总费用
                                            totalFee += Double.parseDouble(meal.getSersmTotalFee());
                                            if (StringUtils.isNotBlank(meal.getSersmTotalOneFee())) {//自费费用
                                                zfpay += Double.parseDouble(meal.getSersmTotalOneFee());
                                            }
                                        }
                                    }
                                }
                                czpay = totalFee - zfpay;
                                form.setSignczpay(String.valueOf(czpay));
                                if (czpay <= 0) {
                                    form.setSignczpay("0");
                                }
                                form.setSignzfpay(String.valueOf(zfpay));
                            }
                        }
                        form.setSignzfpay(String.valueOf(num));
                        sysDao.getServiceDo().add(form);
                        list.add(form);
                        if (flagAgree) {
                            String title = "签约成功消息";
                            String content = "您好，您的家庭医生签约申请已审核通过!";
                            sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), qvo.getDrId(), user.getId(), form.getId(), DrPatientType.PATIENT.getValue());
                        } else {
                            String title = "签约消息";
                            String content = user.getPatientName() + "," + user.getStrPatientGender() + "," + user.getPatientAge() + "岁,于" + ExtendDate.getYMD_h_m(Calendar.getInstance()) + "申请签约!";
                            if (qvo.getDrId() == null) {
                                List<AppTeamMember> members = sysDao.getServiceDo().loadByPk(AppTeamMember.class, "memDrId", qvo.getTeamId());
                                for (AppTeamMember m : members) {
                                    sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), user.getId(), m.getMemDrId(), form.getId(), DrPatientType.DR.getValue());
                                }
                            } else {
                                sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), user.getId(), qvo.getDrId(), form.getId(), DrPatientType.DR.getValue());
                            }
                        }
                    }
                }

            }
        } else {
            AppSignBatch batch = new AppSignBatch();//批次
            AppTeam teamvo = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, qvo.getTeamId());//团队
            AppPatientUser uservo = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, qvo.getPatientId());//患者
            batch.setBatchCreateDate(Calendar.getInstance());
            batch.setBatchTeamId(teamvo.getId());
            batch.setBatchTeamName(teamvo.getTeamName());
            batch.setBatchCreatePersid(uservo.getId());
            batch.setBatchPatientName(uservo.getPatientName());
            //组织批次号
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, teamvo.getTeamHospId());
            if (dept != null && dept.getHospAreaCode() != null) {
                AppSerial serial = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
                if (serial != null) {
                    Map<String, Object> bcnum = this.getNum(serial.getSerialNo(), SignFormType.APPSTATE.getValue());
                    serial.setSerialNo(bcnum.get("old").toString());
                    sysDao.getServiceDo().modify(serial);
                    batch.setBatchNum(bcnum.get("new").toString());//批次号
                }
            }
            batch.setBatchOperatorId(qvo.getDrId());
            AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(qvo.getDrId());
            if (drUser != null) {
                batch.setBatchOperatorName(drUser.getDrName());
            }
            //组织编码
            if (dept != null && dept.getHospAreaCode() != null) {
                batch.setBatchAreaCode(dept.getHospAreaCode());
            }
            sysDao.getServiceDo().add(batch);
            if (qvo.getSonList() != null && qvo.getSonList().size() > 0) {
                for (AppFamilySignSonQvo sign : qvo.getSonList()) {
                    AppSignForm form = new AppSignForm();//签约单
                    AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, sign.getPatientId());//患者
                    form.setSignBatchId(batch.getId());
                    //组织编码
                    if (dept != null && dept.getHospAreaCode() != null) {
                        form.setSignHospId(dept.getId());
                        form.setSignAreaCode(dept.getHospAreaCode());
                        AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                        if (serialSign != null) {
                            Map<String, Object> sinum = this.getNum(serialSign.getSerialNo(), SignFormType.APPSTATE.getValue());
                            serialSign.setSerialNo(sinum.get("old").toString());
                            sysDao.getServiceDo().modify(serialSign);
                            form.setSignNum(sinum.get("new").toString());//签约编码
                        }
                    }
                    //
                    form.setSignPatientId(sign.getPatientId());
                    form.setSignDate(Calendar.getInstance());
                    //是否直接同意签约
                    AppHospExtend extend = this.sysDao.getAppHospExtendDao().findByHospId(form.getSignHospId());
                    boolean flagAgree = false;
                    if (extend != null) {
                        if (extend.getExtHreState().equals(AppFlagAgree.KAIQI.getValue())) {
                            form.setSignState("2");//2:已签约
                            form.setSignFromDate(Calendar.getInstance());
                            Calendar end = Calendar.getInstance();
                            end.add(Calendar.YEAR, 1);
                            end.add(Calendar.DATE, -1);
                            form.setSignToDate(end);
                            flagAgree = true;
                        } else {
                            form.setSignState("1");//1:待签约
                        }
                    } else {
                        form.setSignState("1");//1:待签约
                    }
                    form.setSignPayState("0");//0：未缴费
                    form.setSignType("1");//1家庭签约
                    form.setSignTeamId(teamvo.getId());
                    form.setSignTeamName(teamvo.getTeamName());
                    form.setSignWay("0");//个人发起签约
                    form.setSignPatientAge(Integer.parseInt(user.getPatientAge()));
                    form.setSignPatientGender(user.getPatientGender());
                    form.setSignPatientIdNo(user.getPatientIdno());
                    form.setSignDrId(qvo.getDrId());//绑定医生
                    form.setSignContractState("0");//1是 0否
                    form.setSignGreenState("0");//1是 0否
                    form.setSignYellowState("0");//1是 0否
                    form.setSignRedState("0");//1是 0否
                    form.setSignpackageid(sign.getServerIds());//保存套餐id
                    double num = 0;
                    if (StringUtils.isNotBlank(sign.getServerIds())) {
                        String[] serverIdss = sign.getServerIds().split(";");
                        for (String serverId : serverIdss) {
                            AppServeSetmeal meal = (AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class, serverId);
                            if (meal != null) {
                                if (StringUtils.isNotBlank(meal.getSersmTotalOneFee())) {
                                    num += Double.parseDouble(meal.getSersmTotalOneFee());
                                }
                            }
                        }
                        String text = sysDao.getAppSignformDao().findPkRemark(sign.getServerIds());
                        form.setSigntext(text);

                    }
                    form.setSignzfpay(String.valueOf(num));
                    sysDao.getServiceDo().add(form);
                    list.add(form);
                    if (flagAgree) {
                        String title = "签约成功消息";
                        String content = "您好，您的家庭医生签约申请已审核通过!";
                        sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), qvo.getDrId(), user.getId(), form.getId(), DrPatientType.PATIENT.getValue());
                    } else {
                        String title = "签约消息";
                        String content = user.getPatientName() + "," + user.getStrPatientGender() + "," + user.getPatientAge() + "岁,于" + ExtendDate.getYMD_h_m(Calendar.getInstance()) + "申请签约!";
                        if (qvo.getDrId() == null) {
                            List<AppTeamMember> members = sysDao.getServiceDo().loadByPk(AppTeamMember.class, "memDrId", qvo.getTeamId());
                            for (AppTeamMember m : members) {
                                sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), user.getId(), m.getMemDrId(), form.getId(), DrPatientType.DR.getValue());
                            }
                        } else {
                            sysDao.getAppNoticeDao().addNotices(title, content, NoticesType.QYXX.getValue(), user.getId(), qvo.getDrId(), form.getId(), DrPatientType.DR.getValue());
                        }
                    }
                }
            }
        }


        return list;
    }

    public AppSignForm findByPeople(String patientId) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", patientId);
        String[] signStates = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("SIGN_STATE", signStates);
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_PATIENT_ID=:patientId AND SIGN_STATE IN (:SIGN_STATE) " +
                "AND SIGN_FROM_DATE<=SYSDATE() AND SIGN_TO_DATE >=SYSDATE()";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据签约批次id查询签约列表
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppSignFormListEntity> findByBatchId(AppCommQvo qvo) throws Exception {
        List<AppSignFormListEntity> ls;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("batchId", qvo.getBatchId());
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "\ta.ID signFormId,\n" +
                "\ta.SIGN_STATE signState,\n" +
                "\t(SELECT PATIENT_NAME from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientName,\n" +
                "\t(SELECT PATIENT_GENDER from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientGender,\n" +
                "\t(SELECT PATIENT_IMAGEURL from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientImageurl,\n" +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\t(SELECT TEAM_HOSP_NAME from APP_TEAM where ID=a.SIGN_TEAM_ID) teamHospName,\n" +
                "\ta.SIGN_TEAM_NAME teamName,\n" +
                "\ta.SIGN_TEAM_ID teamId,\n" +
                "\ta.SIGN_FROM_DATE signFromDate,\n" +
                "\ta.SIGN_TO_DATE signToDate,\n" +
                "\ta.SIGN_DATE signDate,\n" +
                "\ta.SIGN_WAY signWay,\n" +
                "\t(SELECT PATIENT_IDNO from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientIdno,\n" +
                "'' patientAge," +
                "(SELECT GROUP_CONCAT(e.SER_TITLE) FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN (SELECT g.LABEL_VALUE from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID GROUP BY g.LABEL_VALUE) ) fwb," +
                "(SELECT GROUP_CONCAT(e.SER_IMAGE_NAME) FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN (SELECT g.LABEL_VALUE from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID GROUP BY g.LABEL_VALUE) ) fwImageName," +
                "\t(SELECT PATIENT_CARD from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientCard,\n" +
                "\t(SELECT PATIENT_TEL from APP_PATIENT_USER where ID=a.SIGN_PATIENT_ID) patientTel,\n" +
                "\t(SELECT DR_NAME from APP_DR_USER where ID=a.SIGN_DR_ID) drName,\n" +
                "'' renewState," +
                "'' oldSign," +
                "a.SIGN_GOTO_SIGN_STATE signGoToSignState," +
                "a.SIGN_OTHNER_REASON signOthnerReason " +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "WHERE\n" +
                "\ta.SIGN_BATCH_ID = :batchId ");

        if (StringUtils.isNotBlank(qvo.getSignState())) {
            if ("1".equals(qvo.getSignState())) {
                String[] strs = new String[]{SignFormType.YUZQ.getValue(), SignFormType.DQY.getValue()};
                map.put("strs", strs);
                sql.append(" AND a.SIGN_STATE IN :strs");
            } else if (SignFormType.YQY.getValue().equals(qvo.getSignState())) {
                String[] strs = new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue(), SignFormType.ZQ.getValue()};
                map.put("strs", strs);
                sql.append(" AND a.SIGN_STATE IN :strs");
            } else {
                sql.append(" AND a.SIGN_STATE=:SIGN_STATE ");
                map.put("SIGN_STATE", qvo.getSignState());
            }
        }
        sql.append(" ORDER BY a.SIGN_NUM ASC");
        ls = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AppSignFormListEntity.class, qvo);
        if (ls != null && ls.size() > 0) {
            return ls;
        }
        return ls;
    }

    /**
     * 根据用户id或团队id查询签约单列表
     *
     * @param qvo
     * @return
     */
    @Override
    public List<AppSignFormListEntity> findSignFormByUserOrTeamT(AppCommQvo qvo)throws Exception {
        List<AppSignFormListEntity> ls;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contractState", CommSF.NOT.getValue());
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "\t(SELECT ID FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=b.ID AND SIGN_PATIENT_ID = b.BATCH_CREATE_PERSID) signFormId,\n" +
                "\t(SELECT SIGN_STATE FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=b.ID AND SIGN_PATIENT_ID = b.BATCH_CREATE_PERSID) signState,\n" +
                "\t(SELECT PATIENT_NAME from APP_PATIENT_USER where ID=b.BATCH_CREATE_PERSID) patientName,\n" +
                "\t(SELECT PATIENT_GENDER from APP_PATIENT_USER where ID=b.BATCH_CREATE_PERSID) patientGender,\n" +
                "\t(SELECT PATIENT_IMAGEURL from APP_PATIENT_USER where ID=b.BATCH_CREATE_PERSID) patientImageurl,\n" +
                "\tb.BATCH_CREATE_PERSID patientId,\n" +
                "\t(SELECT TEAM_HOSP_NAME from APP_TEAM where ID=a.SIGN_TEAM_ID) teamHospName,\n" +
                "\ta.SIGN_TEAM_NAME teamName,\n" +
                "\ta.SIGN_TEAM_ID teamId,\n" +
                "\t(SELECT SIGN_FROM_DATE FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=b.ID AND SIGN_PATIENT_ID = b.BATCH_CREATE_PERSID) signFromDate,\n" +
                "\t(SELECT SIGN_TO_DATE FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=b.ID AND SIGN_PATIENT_ID = b.BATCH_CREATE_PERSID) signToDate,\n" +
                "\tb.BATCH_CREATE_DATE signDate,\n" +
                "\t(SELECT SIGN_WAY FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=b.ID AND SIGN_PATIENT_ID = b.BATCH_CREATE_PERSID) signWay,\n" +
                "\t(SELECT PATIENT_IDNO from APP_PATIENT_USER where ID=b.BATCH_CREATE_PERSID) patientIdno,\n" +
                "'' patientAge," +
                "(SELECT GROUP_CONCAT(e.SER_TITLE) FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN (SELECT g.LABEL_VALUE from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=(SELECT ID FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=b.ID AND SIGN_PATIENT_ID = b.BATCH_CREATE_PERSID) GROUP BY g.LABEL_VALUE) ) fwb," +
                "(SELECT GROUP_CONCAT(e.SER_IMAGE_NAME) FROM APP_SERVE_SETTING e WHERE e.SER_OBJECT_VALUE IN (SELECT g.LABEL_VALUE from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=(SELECT ID FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=b.ID AND SIGN_PATIENT_ID = b.BATCH_CREATE_PERSID) GROUP BY g.LABEL_VALUE) ) fwImageName," +
                "\t(SELECT PATIENT_CARD from APP_PATIENT_USER where ID=b.BATCH_CREATE_PERSID) patientCard,\n" +
                "\t(SELECT PATIENT_TEL from APP_PATIENT_USER where ID=b.BATCH_CREATE_PERSID) patientTel,\n" +
                "\t(SELECT DR_NAME from APP_DR_USER where ID=a.SIGN_DR_ID) drName,\n" +
                "'' renewState," +
                "'' oldSign," +
                "(SELECT COUNT(1) FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID = b.ID) count," +
                "(SELECT SIGN_GOTO_SIGN_STATE FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=b.ID AND SIGN_PATIENT_ID = b.BATCH_CREATE_PERSID) signGoToSignState," +
                "(SELECT SIGN_OTHNER_REASON FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=b.ID AND SIGN_PATIENT_ID = b.BATCH_CREATE_PERSID) signOthnerReason," +
                "(SELECT SIGN_PAY_STATE FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=b.ID AND SIGN_PATIENT_ID = b.BATCH_CREATE_PERSID) payState," +
                "(SELECT SIGN_PACKAGEID FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=b.ID AND SIGN_PATIENT_ID = b.BATCH_CREATE_PERSID) serveList," +
                "b.ID batchId\n" +
                "FROM APP_SIGN_BATCH b INNER JOIN APP_SIGN_FORM a ON b.ID = a.SIGN_BATCH_ID\n" +
                "WHERE\n" +
                "\t1=1 ");

        if (StringUtils.isNotBlank(qvo.getPatientId())) {
            sql.append(" AND a.SIGN_PATIENT_ID =:SIGN_PATIENT_ID \n");
//            "AND b.BATCH_CREATE_PERSID =:BATCH_CREATE_PERSID "
            map.put("SIGN_PATIENT_ID", qvo.getPatientId());
            map.put("BATCH_CREATE_PERSID", qvo.getPatientId());
        }
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            sql.append(" AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID ");
            map.put("SIGN_TEAM_ID", qvo.getTeamId());
        }
        if (StringUtils.isNotBlank(qvo.getDrId())) {
            sql.append(" AND a.SIGN_DR_ID=:SIGN_DR_ID ");
            map.put("SIGN_DR_ID", qvo.getDrId());
        }
        if (StringUtils.isNotBlank(qvo.getSignState())) {
            if ("1".equals(qvo.getSignState())) {
                String[] strs = new String[]{SignFormType.YUZQ.getValue(), SignFormType.DQY.getValue()};
                map.put("strs", strs);
                sql.append(" AND a.SIGN_STATE IN (:strs)");
            } else if (SignFormType.YQY.getValue().equals(qvo.getSignState())) {
                String[] strs = new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue(), SignFormType.ZQ.getValue()};
                map.put("strs", strs);
                sql.append(" AND a.SIGN_STATE IN (:strs)");
            } else {
                sql.append(" AND a.SIGN_STATE=:SIGN_STATE ");
                map.put("SIGN_STATE", qvo.getSignState());
            }
        }
        if (StringUtils.isNotBlank(qvo.getSignFormId())) {
            sql.append(" AND a.ID=:ID ");
            map.put("ID", qvo.getSignFormId());
        }
        sql.append(" GROUP BY b.ID ORDER BY a.SIGN_DATE DESC ");
        ls = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AppSignFormListEntity.class, qvo);
        if (ls != null && ls.size() > 0) {
            return ls;
        }
        return ls;
    }


    /**
     * cxw
     * 通过身份证查询与医保未交互的签约信息
     */
    @Override
    public AppSignForm findSignWebByPtIdNo(String PtIdNo, String signState) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String sql = "";
        if (StringUtils.isNotBlank(PtIdNo)) {
            sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_STATE =:signState AND SIGN_PATIENT_IDNO =:patientidno ";
            map.put("patientidno", PtIdNo);
            map.put("signState", signState);
            List<AppSignForm> vo = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
            if (vo != null && vo.size() > 0) {
                return vo.get(0);
            }
        }
        return null;
    }

    /**
     * 查询续签数据
     *
     * @return
     */
    @Override
    public List<AppSignForm> findChangeRenewalList() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("signState", SignFormType.XQ.getValue());
        map.put("lastTime",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
        String sql = " SELECT * FROM APP_SIGN_FORM " +
                "WHERE SIGN_FROM_DATE <=:lastTime AND SIGN_STATE=:signState";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        return list;
    }

    /**
     * 通过用户id查询 签约信息 type 0预签约 2已签约  02 预+已签约（IN）
     * cxw
     *
     * @param userId
     * @param type
     * @return
     */
    @Override
    public AppSignForm findSignFormByUserId(String userId, String type) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        if (type.equals("02")) {
            String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_PATIENT_ID=:userId AND SIGN_STATE IN ('0','2') ";
            List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
            if (ls != null && ls.size() > 0) {
                return ls.get(0);
            }
        } else {
            map.put("state", type);
            String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_PATIENT_ID=:userId AND SIGN_STATE=:state";
            List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
            if (ls != null && ls.size() > 0) {
                return ls.get(0);
            }
        }
        return null;
    }

    /**
     * 个人端提交转签
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String subChangeUser(AppGoToSignQvo qvo) throws Exception {
        //查询签约单信息
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", qvo.getPatientId());
        String[] signStates = new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue()};
        map.put("signState", signStates);
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE 1=1 ";
        sql += " AND SIGN_PATIENT_ID =:patientId AND SIGN_STATE IN (:signState) " +
                "AND SIGN_FROM_DATE <=SYSDATE() AND SIGN_TO_DATE >=SYSDATE() ";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (list != null && list.size() > 0) {
            AppSignForm oldSign = list.get(0);
            AppSignBatch batch = new AppSignBatch();//批次
            AppTeam teamvo = (AppTeam) sysDao.getServiceDo().find(AppTeam.class, qvo.getTeamId());//团队
            if (teamvo == null) {
                throw new Exception("找不到团队信息");
            }
            AppPatientUser uservo = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, qvo.getPatientId());//患者
            batch.setBatchCreateDate(Calendar.getInstance());
            batch.setBatchTeamId(teamvo.getId());
            batch.setBatchTeamName(teamvo.getTeamName());
            batch.setBatchCreatePersid(uservo.getId());
            batch.setBatchPatientName(uservo.getPatientName());
            //组织批次号
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, teamvo.getTeamHospId());
            if (dept != null && dept.getHospAreaCode() != null) {
                AppSerial serial = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
                if (serial != null) {
                    Map<String, Object> bcnum = this.getNum(serial.getSerialNo(), SignFormType.APPSTATE.getValue());
                    serial.setSerialNo(bcnum.get("old").toString());
                    sysDao.getServiceDo().modify(serial);
                    batch.setBatchNum(bcnum.get("new").toString());//批次号
                }
            }
            batch.setBatchOperatorId(qvo.getDrId());
            AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(qvo.getTeamId());
            if (drUser != null) {
                batch.setBatchOperatorName(drUser.getDrName());
            }
            if (dept != null && dept.getHospAreaCode() != null) {
                batch.setBatchAreaCode(dept.getHospAreaCode());
            }
            sysDao.getServiceDo().add(batch);
            AppSignForm sign = new AppSignForm();
            sign.setUpHpis("1");
            sign.setSignState(oldSign.getSignState());
            if (dept != null && dept.getHospAreaCode() != null) {
                sign.setSignHospId(dept.getId());
                sign.setSignAreaCode(dept.getHospAreaCode());
                AppSerial serialSign = this.getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                if (serialSign != null) {
                    Map<String, Object> sinum = this.getNum(serialSign.getSerialNo(), SignFormType.APPSTATE.getValue());
                    serialSign.setSerialNo(sinum.get("old").toString());
                    sysDao.getServiceDo().modify(serialSign);
                    sign.setSignNum(sinum.get("new").toString());//签约编码
                }
            }
            sign.setSignPatientId(oldSign.getSignPatientId());
            sign.setSignPatientAge(oldSign.getSignPatientAge());
            sign.setSignPatientGender(oldSign.getSignPatientGender());
            sign.setSignPatientIdNo(oldSign.getSignPatientIdNo());
            sign.setSignDate(oldSign.getSignDate());
//            sign.setSignFromDate()


        } else {
            return "查不到签约信息";
        }
        return null;
    }

    /**
     * 根据条件查询签约单
     *
     * @param patientId 患者id
     * @param drId      医生id
     * @return
     */
    @Override
    public AppSignForm findFormByDr(String patientId, String drId) throws Exception{
        Map<String, Object> map = new HashMap<>();
        String[] signState = new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};
        map.put("signState", signState);
        String sql = " SELECT * FROM APP_SIGN_FORM WHERE 1=1 AND SIGN_STATE IN (:signState)";
        if (StringUtils.isNotBlank(patientId)) {
            map.put("patientId", patientId);
            sql += " AND SIGN_PATIENT_ID =:patientId ";
        }
        if (StringUtils.isNotBlank(drId)) {
            map.put("drId", drId);
            sql += " AND SIGN_DR_ID = :drId ";
        }
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public String findPkRemark(String pkid) throws Exception {
        String text = "";
        String[] pkIds = pkid.split(";");
        for (String pkIdd : pkIds) {
            String mealName = "";
            String pkTitle = "";
            AppServeSetmeal meal = (AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class, pkIdd);
            if (meal != null) {
                mealName = " " + meal.getSersmName() + " ";
                String groupIds = meal.getSersmGroupId();
                if (StringUtils.isNotBlank(groupIds)) {
                    String[] groupIdss = groupIds.split(";");
                    for (String groupId : groupIdss) {
                        AppServeGroup ls = (AppServeGroup) sysDao.getServiceDo().find(AppServeGroup.class, groupId);
                        if (ls != null) {
                            if (StringUtils.isNotBlank(ls.getSergPkId())) {
                                String[] ids = ls.getSergPkId().split(";");
                                for (String pId : ids) {
                                    AppServePackage pk = (AppServePackage) sysDao.getServiceDo().find(AppServePackage.class, pId);
                                    if (pk != null) {
                                        if (StringUtils.isNotBlank(pk.getSerpkRemark())) {
                                            if (StringUtils.isBlank(pkTitle)) {
                                                pkTitle = pk.getSerpkRemark();
                                            } else {
                                                pkTitle += ";" + pk.getSerpkRemark();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (StringUtils.isNotBlank(mealName) && StringUtils.isNotBlank(pkTitle)) {
                if (StringUtils.isBlank(text)) {
                    text = mealName + pkTitle;
                } else {
                    text += "  " + mealName + pkTitle;
                }
            }
        }
        return text;
    }

    @Override
    public List<AppSignForm> findFormByDr(String drId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("SIGN_DR_ID", drId);
        String[] signState = new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue()};
        map.put("SIGN_STATE", signState);
		String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_DR_ID = :SIGN_DR_ID AND SIGN_STATE IN (:SIGN_STATE) AND SIGN_PACKAGEID IS NOT NULL ";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        return list;
    }


    //居民签约信息查询
    @Override
    public List<AppSignVo> findSignToexamine(AppCommQvo qvo) throws Exception {
        HashMap map = new HashMap();
        String sql = "SELECT\n" +
                "\ta.ID id,\n '" +
                qvo.getSigngwpay() + "' signgwpay,\n '" +
                qvo.getSignybpay() + "' signybpay,\n" +
                "\tc.ID patientId,\n" +
                "\ta.SIGN_TEAM_ID teamId,\n" +
                "\ta.SIGN_NUM signNum,\n" +
                "\tc.PATIENT_NAME name,\n" +
                "\tc.PATIENT_IDNO patientIdno,\n" +
                "'' age," +
                "\tc.PATIENT_TEL tel, a.SIGN_ZF_PAY signzfpay, \n" +
                //"\ta.SIGN_JJ_TYPE signsJjType, \n" +
                "\ta.SIGN_LX signlx,\n" +
                "\ta.SIGN_DATE signDate,\n" +
                "\ta.SIGN_STATE signState,\n" +
                "\ta.SIGN_CZ_PAY signczpay,\n" +
                "\ta.SIGN_DR_ID signDrId,\n" +
                "\t(SELECT DR_NAME from APP_DR_USER g where g.ID=a.SIGN_DR_ID) signDrName,\n" +
                "\tc.PATIENT_GENDER sex,\n" +
                "\tc.PATIENT_CARD patientCard,\n" +
                "\ta.SIGN_TYPE signType, c.PATIENT_ADDRESS patientAddress,\n" +
                "a.SIGN_TEAM_NAME signTeamName,\n" +
                "s.BATCH_OPERATOR_NAME batchOperatorName,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) signPersGroup,\n " +
                " ( SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_ECONOMICS c WHERE c.LABEL_SIGN_ID=a.ID ) signsJjType, " +
                "\ta.SIGN_PAY_STATE signPayState, \n" +
                "\ta.UP_HPIS upHpis \n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID " +
                " LEFT JOIN APP_LABEL_ECONOMICS e on a.ID = e.LABEL_SIGN_ID " +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID LEFT " +
                "JOIN APP_SIGN_BATCH s ON a.SIGN_BATCH_ID=s.ID\n" +
                "where 1=1 and a.SIGN_STATE='1' and a.UP_HPIS ='1'  ";
        //机构
        if (StringUtils.isNotBlank(qvo.getSignHospId())) {
            sql = sql + " AND a.SIGN_HOSP_ID=:SIGN_HOSP_ID ";
            map.put("SIGN_HOSP_ID", qvo.getSignHospId());
        }
        //模糊搜索 姓名
        if (StringUtils.isNotBlank(qvo.getPatientName())) {
            sql = sql + " AND c.PATIENT_NAME LIKE :searchValue ";
            map.put("searchValue", "%" + qvo.getPatientName() + "%");
        }
        //团队
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            sql = sql + " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID ";
            map.put("SIGN_TEAM_ID", qvo.getTeamId());
            //医生
            if (StringUtils.isNotBlank(qvo.getDrId())) {
                sql = sql + " AND a.SIGN_DR_ID=:SIGN_DR_ID ";
                map.put("SIGN_DR_ID", qvo.getDrId());
            }
        }
        //身份证
        if (StringUtils.isNotBlank(qvo.getPatientIdno())) {
            sql = sql + " AND c.PATIENT_IDNO=:PATIENT_IDNO ";
            map.put("PATIENT_IDNO", qvo.getPatientIdno());
        }
        //居民健康档案
        if (StringUtils.isNotBlank(qvo.getPatientjmda())) {
            sql = sql + " AND c.PATIENT_JMDA=:PATIENT_JMDA ";
            map.put("PATIENT_JMDA", qvo.getPatientjmda());
        }
        //自费金额
        if (StringUtils.isNotBlank(qvo.getSignzfpay())) {
            sql = sql + " AND a.SIGN_ZF_PAY=:SIGN_ZF_PAY ";
            map.put("SIGN_ZF_PAY", qvo.getSignzfpay());
        }
        //签约日期开始
        if (StringUtils.isNotBlank(qvo.getSignDateStart())) {
            sql = sql + " AND a.SIGN_DATE>=:SIGN_DATE_START ";
            map.put("SIGN_DATE_START", qvo.getSignDateStart() + " 00:00:00");
        }
        //签约日期结束
        if (StringUtils.isNotBlank(qvo.getSignDateEnd())) {
            sql = sql + " AND a.SIGN_DATE<=:SIGN_DATE_END ";
            map.put("SIGN_DATE_END", qvo.getSignDateEnd() + " 23:59:59");
        }
        //协议日期开始
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql = sql + " AND a.SIGN_FROM_DATE>=:SIGN_FROM_DATE_START ";
            map.put("SIGN_FROM_DATE_START", qvo.getSignFromDateStart() + " 00:00:00");
        }
        //协议日期结束
        if (StringUtils.isNotBlank(qvo.getSignFromDateEnd())) {
            sql = sql + " AND a.SIGN_FROM_DATE<=:SIGN_FROM_DATE_END ";
            map.put("SIGN_FROM_DATE_END", qvo.getSignFromDateEnd() + " 23:59:59");
        }
        //医保类型
        if (StringUtils.isNotBlank(qvo.getSignlx())) {
            sql = sql + " AND a.SIGN_LX=:SIGN_LX ";
            map.put("SIGN_LX", qvo.getSignlx());
        }
        //人口经济性质
        /*if (qvo.getSignsJjTypes() != null && qvo.getSignsJjTypes().length > 0) {
            sql = sql + " AND a.SIGN_JJ_TYPE in (:SIGN_JJ_TYPE) ";
            map.put("SIGN_JJ_TYPE", qvo.getSignsJjTypes());
        }*/
        if (qvo.getSignsJjTypes() != null && qvo.getSignsJjTypes().length > 0) {
            sql = sql + " and e.LABEL_TYPE = '4' and e.LABEL_VALUE in (:SIGN_JJ_TYPE) ";
            map.put("SIGN_JJ_TYPE", qvo.getSignsJjTypes());
        }
        //助理姓名
        if (StringUtils.isNotBlank(qvo.getSignDrAssistantId())) {
            sql = sql + " AND ( c.SIGN_DR_ASSISTANT_ID =:SIGN_DR_ASSISTANT_ID ) ";
            map.put("SIGN_DR_ASSISTANT_ID", qvo.getSignDrAssistantId());
        }
        //vip
        if (StringUtils.isNotBlank(qvo.getVip()) && qvo.getVip().equals("1")) {
            sql = sql + " AND a.SIGN_TYPE='2'";
        } else {
            sql = sql + " AND a.SIGN_TYPE='1'";
        }
        //到期
        if (StringUtils.isNotBlank(qvo.getExpire()) && qvo.getExpire().equals("1")) {
            sql = sql + " AND a.SIGN_TO_DATE>SYSDATE()";
        }
        String plg = "";
        //疾病类型
        if (StringUtils.isNotBlank(qvo.getLabelGruops())) {
            String[] lg = qvo.getLabelGruops().split(";");
            if (lg != null && lg.length > 0) {
                for (String l : lg) {
                    if (org.apache.commons.lang.StringUtils.isBlank(plg)) {
                        plg = l;
                    } else {
                        plg += ";" + l;
                    }
                }
            }
        }
        //健康情况
        if (StringUtils.isNotBlank(qvo.getSignHealthGroup())) {
            String[] hg = qvo.getSignHealthGroup().split(";");
            if (hg != null && hg.length > 0) {
                sql = sql + " and a.SIGN_HEALTH_GROUP  in (:SIGN_HEALTH_GROUP)";
                map.put("SIGN_HEALTH_GROUP", hg);
            }
        }
        //三师
        if (StringUtils.isNotBlank(qvo.getServiceA()) && qvo.getServiceA().equals("1")) {
            sql = sql + " AND a.SIGN_SERVICE_A='2'";
            String bacolor[] = new String[3];
            int bia = 0;
            if (StringUtils.isNotBlank(qvo.getServiceAred()) && qvo.getServiceAred().equals("1")) {
                bacolor[bia] = "red";
                bia++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceAyellow()) && qvo.getServiceAyellow().equals("1")) {
                bacolor[bia] = "yellow";
                bia++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceAgreen()) && qvo.getServiceAgreen().equals("1")) {
                bacolor[bia] = "green";
                bia++;
            }
            if ((StringUtils.isNotBlank(qvo.getServiceAred()) && qvo.getServiceAred().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceAyellow()) && qvo.getServiceAyellow().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceAgreen()) && qvo.getServiceAgreen().equals("1"))) {
                sql = sql + " AND b.LABEL_COLOR in (:LABEL_COLOR)";
                map.put("LABEL_COLOR", bacolor);
            }
        }
        //居家
        if (StringUtils.isNotBlank(qvo.getServiceB()) && qvo.getServiceB().equals("1")) {
            sql = sql + " AND a.SIGN_SERVICE_B='2'";
            String bcolor[] = new String[3];
            int bi = 0;
            if (StringUtils.isNotBlank(qvo.getServiceBred()) && qvo.getServiceBred().equals("1")) {
                bcolor[bi] = "red";
                bi++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceByellow()) && qvo.getServiceByellow().equals("1")) {
                bcolor[bi] = "yellow";
                bi++;
            }
            if (StringUtils.isNotBlank(qvo.getServiceBgreen()) && qvo.getServiceBgreen().equals("1")) {
                bcolor[bi] = "green";
                bi++;
            }
            if ((StringUtils.isNotBlank(qvo.getServiceBred()) && qvo.getServiceBred().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceByellow()) && qvo.getServiceByellow().equals("1")) || (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getServiceBgreen()) && qvo.getServiceBgreen().equals("1"))) {
                sql = sql + " AND a.SIGN_SERVICE_B_COLOR in (:SIGN_SERVICE_B_COLOR)";
                map.put("SIGN_SERVICE_B_COLOR", bcolor);
            }
        }
        //详细地址
        if (StringUtils.isNoneBlank(qvo.getPatientCity())) {
            sql = sql + " and c.PATIENT_CITY =:city ";
            map.put("city", qvo.getPatientCity());
            if (StringUtils.isNoneBlank(qvo.getPatientArea())) {
                sql = sql + " and c.PATIENT_AREA =:area ";
                map.put("area", qvo.getPatientArea());
                if (StringUtils.isNoneBlank(qvo.getPatientStreet())) {
                    sql = sql + " and c.PATIENT_STREET =:street  ";
                    map.put("street", qvo.getPatientStreet());
                    if (StringUtils.isNoneBlank(qvo.getPatientNeighborhoodCommittee())) {
                        sql = sql + " and c.PATIENT_NEIGHBORHOOD_COMMITTEE =:committtee  ";
                        map.put("committtee", qvo.getPatientNeighborhoodCommittee());
                    }
                }

            }
        }
        if (StringUtils.isNoneBlank(qvo.getBatchOperatorName())) {
            sql = sql + " and s.BATCH_OPERATOR_NAME LIKE :operatorname ";
            map.put("operatorname", "%" + qvo.getBatchOperatorName() + "%");
        }
        if (StringUtils.isNoneBlank(qvo.getPatientAddress())) {
            sql = sql += " and c.PATIENT_ADDRESS LIKE :address ";
            map.put("address", "%" + qvo.getPatientAddress() + "%");
        }
        //服务人群
        int num = 0;
        if (qvo.getPersGroup() != null && qvo.getPersGroup().length > 0) {
            /*for(int i=0;i<qvo.getPersGroup().length;i++){
                sql=sql+" AND x.signPers"+qvo.getPersGroup()[i]+" ='"+qvo.getPersGroup()[i]+"' ";
            }*/
            sql = sql += " and b.LABEL_TYPE='3' and b.LABEL_VALUE IN ( :value ) ";
            map.put("value", qvo.getPersGroup());
            num = qvo.getPersGroup().length;
        }
        //来源
        if (StringUtils.isNotBlank(qvo.getUpHpis())) {
            sql = sql + " AND a.UP_HPIS =:UP_HPIS ";
            map.put("UP_HPIS", qvo.getUpHpis());
        }
        //协议状态
        if (StringUtils.isNotBlank(qvo.getSignPrintFlag())) {
            sql = sql + " AND a.SIGN_PRINT_FLAG =:SIGN_PRINT_FLAG ";
            map.put("SIGN_PRINT_FLAG", qvo.getSignPrintFlag());
        }
        //签约状态
        if (StringUtils.isNotBlank(qvo.getSignState())) {
            sql = sql + " AND a.SIGN_STATE =:SIGN_STATE ";
            map.put("SIGN_STATE", qvo.getSignState());
        }

        sql = sql + " GROUP BY a.ID HAVING COUNT(*)>= " + num;

        List<AppSignVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppSignVo.class, qvo);
        // 服务类型
//        if (ls != null && ls.size() > 0) {
//            for (int i = 0; i < ls.size(); i++) {
//                ls.get(i).setSigngwpay(qvo.getSigngwpay());
//                ls.get(i).setSignybpay(qvo.getSignybpay());
//            }
//        }
        return ls;
    }

    @Override
    public List<AppSignForm> findSignFormListByIds(String[] ids) throws Exception {
        HashMap map = new HashMap();
        StringBuilder hql = new StringBuilder("from AppSignForm where id in(:ids)");
        map.put("ids", ids);
        List<AppSignForm> ls = sysDao.getServiceDo().findHqlMap(hql.toString(), map);
        return ls;
    }

    @Override
    public AppSignForm findSignFormByState(String patientId) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", patientId);
        map.put("signStates", new String[]{SignFormType.DQY.getValue(), SignFormType.YUQY.getValue(), SignFormType.YQY.getValue()});
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_PATIENT_ID =:patientId AND SIGN_STATE IN (:signStates)";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AppSignForm> findSignFormByTeamId(String teamId) throws Exception {
        StringBuilder hql = new StringBuilder();
        hql.append("from AppSignForm where signTeamId = :signTeamId and signState in ('0','2')");
        HashMap map = new HashMap();
        map.put("signTeamId", teamId);
        List<AppSignForm> list = sysDao.getServiceDo().findHqlMap(hql.toString(), map);
        return list;
    }

    /**
     * 死亡解约
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSignForm surrenderSign(WebSurrenderSignVo qvo) throws Exception {
        List<AppSurrenderSign> list = sysDao.getServiceDo().loadByPk(AppSurrenderSign.class, "patientIdNo", qvo.getPatientIdNo());
        AppSignForm form = null;
        AppSurrenderSign vv = null;
        if (list != null && list.size() > 0) {
            vv = list.get(0);
        } else {
            vv = new AppSurrenderSign();
            vv.setOrgId(qvo.getDeptId());
            vv.setDrId(qvo.getDrId());
            vv.setPatientTimeDeath(ExtendDate.getCalendar(qvo.getDissolutionTime()));
            vv.setPatientCauseDeath(qvo.getDissolutionCausa());
            vv.setPatientIdNo(qvo.getPatientIdNo());
            sysDao.getServiceDo().add(vv);
        }
        if (vv != null) {
            if ("0".equals(vv.getPatientState())) {//未解约状态
                Map<String, Object> map = new HashMap<>();
                map.put("patientIdNo", vv.getPatientIdNo());
                map.put("signState", new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue()});
                String sql = "SELECT\n" +
                        "\ta.*\n" +
                        "FROM\n" +
                        "\tapp_sign_form a\n" +
                        "WHERE 1=1 AND a.SIGN_PATIENT_IDNO =:patientIdNo AND a.SIGN_STATE IN (:signState) ";
                List<AppSignForm> lisF = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
                if (lisF != null && lisF.size() > 0) {
                    form = lisF.get(0);
                    form.setSignState(SignFormType.YJY.getValue());
                    form.setSignDelType("1");//死亡
                    form.setSignDieDate(vv.getPatientTimeDeath());
                    form.setSignDelReason(vv.getPatientCauseDeath());
                    sysDao.getServiceDo().modify(form);
                    vv.setPatientState("1");
                    sysDao.getServiceDo().modify(vv);
                    if(StringUtils.isNotBlank(form.getSignPatientIdNo())){
                        List<AppArchivingCardPeople> listP = sysDao.getServiceDo().loadByPk(AppArchivingCardPeople.class,"archivingPatientIdno",form.getSignPatientIdNo());
                        if(listP != null && listP.size()>0){
                            for(AppArchivingCardPeople pp:listP){
                                pp.setSignState(null);
                                pp.setNotSignReason("1");//死亡
                                pp.setSignId(null);
                                pp.setDrId(null);
                                pp.setTeamId(null);
                                pp.setHospId(null);
                                pp.setSignFromDate(null);
                                sysDao.getServiceDo().modify(pp);
                            }
                        }
                    }else{
                        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,form.getSignPatientId());
                        if(user != null){
                            if(StringUtils.isNotBlank(user.getPatientIdno())){
                                List<AppArchivingCardPeople> listP = sysDao.getServiceDo().loadByPk(AppArchivingCardPeople.class,"archivingPatientIdno",user.getPatientIdno());
                                if(listP != null && listP.size()>0){
                                    for(AppArchivingCardPeople pp:listP){
                                        pp.setSignState(null);
                                        pp.setNotSignReason("1");//死亡
                                        pp.setSignId(null);
                                        pp.setDrId(null);
                                        pp.setTeamId(null);
                                        pp.setHospId(null);
                                        pp.setSignFromDate(null);
                                        sysDao.getServiceDo().modify(pp);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return form;
    }

    @Override
    public AppSignForm findFormByJmda(String patientIdNo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("patientIdNo", patientIdNo);
        map.put("signState", new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue()});
        String sql = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tapp_sign_form a\n" +
                "WHERE 1=1 AND a.SIGN_PATIENT_IDNO =:patientIdNo AND a.SIGN_STATE IN (:signState) ";
        List<AppSignForm> lisF = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (lisF != null && lisF.size() > 0) {
            return lisF.get(0);
        }
        return null;
    }

    /**
     * 根据居民档案号查询死亡状态
     *
     * @param jmdah
     * @return
     * @throws Exception
     */
    @Override
    public String findDieState(String jmdah) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("jmdah", jmdah);
        String sql = "SELECT count(1) FROM app_sign_form a " +
                "INNER JOIN app_patient_user b ON a.SIGN_PATIENT_ID = b.ID \n" +
                "WHERE b.PATIENT_JMDA=:jmdah AND a.SIGN_DEL_TYPE='1'";
        int count = sysDao.getServiceDo().findCount(sql, map);
        if (count > 0) {
            return "1";
        } else {
            return "0";
        }
    }

    @Override
    public AppSignForm findSignById(String patientId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", patientId);
        map.put("signState", new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue(), SignFormType.DQY.getValue()});
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_PATIENT_ID =:patientId AND SIGN_STATE IN (:signState) ";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 解约 查询列表
     * WangCheng
     */
    public List<AppSignVo> findDisslution(AppCommQvo qvo) throws Exception {
        HashMap map = new HashMap();
        String sql = "SELECT\n" +
                "\tf.ID id,  f.SIGN_AREA_CODE signareacode ,\n" +
                "\tu.PATIENT_NAME name ,\n" +
                "  u.PATIENT_GENDER sex,\n" +
                "\tu.PATIENT_IDNO  patientIdno,\n" +
                "  f.SIGN_DATE signDate,\n" +
                "  f.SIGN_PAY_STATE signPayState,\n" +
                "  f.SIGN_GOTO_SIGN_STATE signGoToSignState,\n" +
                "\tf.SIGN_STATE signState,\n" +
                "\tf.SIGN_FROM_DATE signFromDate,\n" +
                "\tf.SIGN_TO_DATE signToDateOther,\n" +
                " (\n" +
                "\t\tSELECT\n" +
                "\t\t\tDR_NAME\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_DR_USER g\n" +
                "\t\tWHERE\n" +
                "\t\t\tg.ID = f.SIGN_DR_ID\n" +
                "\t) signDrName,\n" +
                "  f.SIGN_TEAM_NAME signTeamName,\n" +
                "  f.UP_HPIS upHpis,\n" +
                "\tc.ID packageId,\n" +
                "\tc.SERSM_NAME sersmName,\n" +
                "\tu.PATIENT_JMDA patientjmda,\n" +
                "\tu.PATIENT_JTDA patientjtda,\n" +
                "\tf.SIGN_DEL_TYPE signDelType,\n" +
                "\tf.SIGN_DEL_REASON signDelReason,\n" +
                "\tf.SIGN_DIE_DATE signDieDate,\n" +
                "\tf.SIGN_DEL_DATE signDelDate , f.SIGN_OPERATOR_NAME signOperatorName ,\n" +
                "\tf.SIGN_SURRENDER_DATE signSurrenderDate,\n" +
                "\tf.SIGN_URRENDER_REASON signUrrenderReason \n" +
                "\n" +
                "FROM\n" +
                "\tapp_sign_form f LEFT JOIN app_patient_user u on f.SIGN_PATIENT_ID = u.ID \n" +
                "\t LEFT JOIN APP_SERVE_SETMEAL c ON f.SIGN_PACKAGEID = c.ID \n" +
                "WHERE 1=1 ";

        //机构
        if (StringUtils.isNotBlank(qvo.getSignHospId())) {
            sql = sql + " AND f.SIGN_HOSP_ID=:SIGN_HOSP_ID ";
            map.put("SIGN_HOSP_ID", qvo.getSignHospId());
        }
        //模糊搜索 姓名
        if (StringUtils.isNotBlank(qvo.getPatientName())) {
            sql = sql + " AND u.PATIENT_NAME LIKE :searchValue ";
            map.put("searchValue", "%" + qvo.getPatientName() + "%");
        }
        //团队
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            sql = sql + " AND f.SIGN_TEAM_ID=:SIGN_TEAM_ID ";
            map.put("SIGN_TEAM_ID", qvo.getTeamId());
            //医生
            if (StringUtils.isNotBlank(qvo.getDrId())) {
                sql = sql + " AND f.SIGN_DR_ID=:SIGN_DR_ID ";
                map.put("SIGN_DR_ID", qvo.getDrId());
            }
        }
        //身份证
        if (StringUtils.isNotBlank(qvo.getPatientIdno())) {
            sql = sql + " AND u.PATIENT_IDNO=:PATIENT_IDNO ";
            map.put("PATIENT_IDNO", qvo.getPatientIdno());
        }
        //居民健康档案
        if (StringUtils.isNotBlank(qvo.getPatientjmda())) {
            sql = sql + " AND c.PATIENT_JMDA=:PATIENT_JMDA ";
            map.put("PATIENT_JMDA", qvo.getPatientjmda());
        }
        //签约日期开始
        if (StringUtils.isNotBlank(qvo.getSignDateStart())) {
            sql = sql + " AND f.SIGN_DATE>=:SIGN_DATE_START ";
            map.put("SIGN_DATE_START", qvo.getSignDateStart() + " 00:00:00");
        }
        //签约日期结束
        if (StringUtils.isNotBlank(qvo.getSignDateEnd())) {
            sql = sql + " AND f.SIGN_DATE<=:SIGN_DATE_END ";
            map.put("SIGN_DATE_END", qvo.getSignDateEnd() + " 23:59:59");
        }
        //协议日期开始
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql = sql + " AND f.SIGN_FROM_DATE>=:SIGN_FROM_DATE_START ";
            map.put("SIGN_FROM_DATE_START", qvo.getSignFromDateStart() + " 00:00:00");
        }
        //协议日期结束
        if (StringUtils.isNotBlank(qvo.getSignFromDateEnd())) {
            sql = sql + " AND f.SIGN_FROM_DATE<=:SIGN_FROM_DATE_END ";
            map.put("SIGN_FROM_DATE_END", qvo.getSignFromDateEnd() + " 23:59:59");
        }
        //人口经济性质
        /*if (qvo.getSignsJjTypes() != null && qvo.getSignsJjTypes().length > 0) {
            sql = sql + " AND a.SIGN_JJ_TYPE in (:SIGN_JJ_TYPE) ";
            map.put("SIGN_JJ_TYPE", qvo.getSignsJjTypes());
        }*/
        //助理姓名
        if (StringUtils.isNotBlank(qvo.getSignDrAssistantId())) {
            sql = sql + " AND ( f.SIGN_DR_ASSISTANT_ID =:SIGN_DR_ASSISTANT_ID ) ";
            map.put("SIGN_DR_ASSISTANT_ID", qvo.getSignDrAssistantId());
        }
        //vip
        if (StringUtils.isNotBlank(qvo.getVip()) && qvo.getVip().equals("1")) {
            sql = sql + " AND f.SIGN_TYPE='2'";
        } else {
            sql = sql + " AND f.SIGN_TYPE='1'";
        }
        //到期
        if (StringUtils.isNotBlank(qvo.getExpire()) && qvo.getExpire().equals("1")) {
            sql = sql + " AND f.SIGN_TO_DATE>SYSDATE()";
        }
        //详细地址
        if (StringUtils.isNoneBlank(qvo.getPatientCity())) {
            sql = sql + " and u.PATIENT_CITY =:city ";
            map.put("city", qvo.getPatientCity());
            if (StringUtils.isNoneBlank(qvo.getPatientArea())) {
                sql = sql + " and u.PATIENT_AREA =:area ";
                map.put("area", qvo.getPatientArea());
                if (StringUtils.isNoneBlank(qvo.getPatientStreet())) {
                    sql = sql + " and u.PATIENT_STREET =:street  ";
                    map.put("street", qvo.getPatientStreet());
                    if (StringUtils.isNoneBlank(qvo.getPatientNeighborhoodCommittee())) {
                        sql = sql + " and u.PATIENT_NEIGHBORHOOD_COMMITTEE =:committtee  ";
                        map.put("committtee", qvo.getPatientNeighborhoodCommittee());
                    }
                }

            }
        }
        if (StringUtils.isNoneBlank(qvo.getPatientAddress())) {
            sql = sql += " and u.PATIENT_ADDRESS LIKE :address ";
            map.put("address", "%" + qvo.getPatientAddress() + "%");
        }
        //来源
        if (StringUtils.isNotBlank(qvo.getUpHpis())) {
            sql = sql + " AND f.UP_HPIS =:UP_HPIS ";
            map.put("UP_HPIS", qvo.getUpHpis());
        }
        //签约状态
        if (StringUtils.isNotBlank(qvo.getSignState())) {
            sql = sql + " AND f.SIGN_STATE =:SIGN_STATE ";
            map.put("SIGN_STATE", qvo.getSignState());
        }
        // 解约原因
        if (StringUtils.isNotBlank(qvo.getReason())) {
            if ("1".equals(qvo.getReason())) {
                sql = sql + " AND f.SIGN_URRENDER_REASON = :SIGN_URRENDER_REASON ";
                map.put("SIGN_URRENDER_REASON", "签约到期，自动解约");
            } else if ("2".equals(qvo.getReason())) {
                sql = sql + " AND f.SIGN_URRENDER_REASON LIKE :SIGN_URRENDER_REASON ";
                map.put("SIGN_URRENDER_REASON", "%死亡%");
            } else if ("3".equals(qvo.getReason())) {
                sql = sql + " AND (f.SIGN_URRENDER_REASON != :REASONONE AND f.SIGN_URRENDER_REASON NOT LIKE :REASONTWO OR f.SIGN_URRENDER_REASON IS NULL) ";
                map.put("REASONONE", "签约到期，自动解约");
                map.put("REASONTWO", "%死亡%");
            }
        }
//        if (StringUtils.isNotBlank(qvo.getDissolutionType())) {
//            if (qvo.getDissolutionType().equals("0")) {
//                sql = sql + " and  f.SIGN_STATE = '4' and f.SIGN_URRENDER_REASON ='签约到期，自动解约' ";
//            } else {
//                sql = sql + " and  f.SIGN_STATE=:SignOthnerReason ";
//                map.put("SignOthnerReason", qvo.getDissolutionType());
//
//            }
//        }
        //modify by WangCheng
        sql = sql + " and f.SIGN_STATE in ('4', '9') ";
        //如果ShowHistory是1的话、显示为续签的解约单,2的话查看所有的解约单信息包括已经续签的、add by WangCheng
        if(StringUtils.isNotEmpty(qvo.getShowHistory())){
            if("1".equals(qvo.getShowHistory())){
                sql = sql + " group by u.PATIENT_IDNO";
                sql = " select * from (" + sql + ")c where 1=1";
             }else {
                sql = sql + " AND f.SIGN_GOTO_SIGN_STATE != '2'";
                sql = " select * from (" + sql + ")c where 1=1";
//                sql = " SELECT " +
//                        " SUBSTRING_INDEX(GROUP_CONCAT(\n" +
//                        "\t\t\t\tc.id ORDER BY c.signDate DESC\n" +
//                        "\t\t\t),',',1) id,\n" +
//                        "c.signareacode,\n" +
//                        "c.name,\n" +
//                        "c.patientIdno,\n" +
//                        "STR_TO_DATE(SUBSTRING_INDEX(GROUP_CONCAT(\n" +
//                        "\t\t\t\tc.signDate ORDER BY c.signDate DESC\n" +
//                        "\t\t\t),',',1), '%Y-%m-%d %H:%i:%s') signDate,\n" +
//                        "c.signPayState,\n" +
//                        "c.signState,\n" +
//                        "STR_TO_DATE(SUBSTRING_INDEX(GROUP_CONCAT(\n" +
//                        "\t\t\t\tc.signFromDate ORDER BY c.signDate DESC\n" +
//                        "\t\t\t),',',1), '%Y-%m-%d %H:%i:%s') signFromDate,\n" +
//                        "STR_TO_DATE(SUBSTRING_INDEX(GROUP_CONCAT(\n" +
//                        "\t\t\t\tc.signToDateOther ORDER BY c.signDate DESC\n" +
//                        "\t\t\t),',',1), '%Y-%m-%d %H:%i:%s') signToDateOther,\n" +
//                        "c.signDrName,\n" +
//                        "c.signTeamName,\n" +
//                        "c.upHpis,\n" +
//                        "c.patientjmda,\n" +
//                        "c.patientjtda,\n" +
//                        "c.signDelType,\n" +
//                        "c.signDelReason,\n" +
//                        "STR_TO_DATE(SUBSTRING_INDEX(GROUP_CONCAT(\n" +
//                        "\t\t\t\tc.signDieDate ORDER BY c.signDate DESC\n" +
//                        "\t\t\t),',',1), '%Y-%m-%d %H:%i:%s') signDieDate,\n" +
//                        "STR_TO_DATE(SUBSTRING_INDEX(GROUP_CONCAT(\n" +
//                        "\t\t\t\tc.signDelDate ORDER BY c.signDate DESC\n" +
//                        "\t\t\t),',',1), '%Y-%m-%d %H:%i:%s') signDelDate,\n" +
//                        "c.signOperatorName,\n" +
//                        "STR_TO_DATE(SUBSTRING_INDEX(GROUP_CONCAT(\n" +
//                        "\t\t\t\tc.signSurrenderDate ORDER BY c.signDate DESC\n" +
//                        "\t\t\t),',',1), '%Y-%m-%d %H:%i:%s') signSurrenderDate,\n" +
//                        "c.signUrrenderReason\n" +
//                        " FROM ( " + sql + " )c where 1=1 ";
            }
        }
        List<AppSignVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppSignVo.class, qvo);
        if (ls != null && ls.size() > 0) {
            return ls;
        }
        return null;
    }

    /**
     * 查询解约列表数据（导出专用）
     * WangCheng
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppSignVo> listDissolutionData(AppCommQvo qvo) throws Exception {
        HashMap map = new HashMap();
        String sql = "SELECT" +
                " f.ID AS id," +
                " u.PATIENT_NAME AS name," +
                " u.PATIENT_GENDER AS sex," +
                " u.PATIENT_IDNO AS patientIdno," +
                " u.PATIENT_CARD AS patientCard," +
                " f.SIGN_SURRENDER_DATE AS signSurrenderDate," +
                " f.SIGN_URRENDER_REASON AS signUrrenderReason," +
                " \'\' AS renewDate," +
                " c.ID AS packageId," +
                " c.SERSM_NAME AS sersmName," +
                " f.SIGN_TEAM_NAME AS signTeamName," +
                " ( SELECT DR_NAME FROM APP_DR_USER g WHERE g.ID = f.SIGN_DR_ID ) AS signDrName," +
                " f.UP_HPIS AS upHpis" +
                " FROM app_sign_form f" +
                " LEFT JOIN app_patient_user u ON f.SIGN_PATIENT_ID = u.ID" +
                " LEFT JOIN APP_SERVE_SETMEAL c ON f.SIGN_PACKAGEID = c.ID" +
                " WHERE 1 = 1";
        //机构
        if (StringUtils.isNotBlank(qvo.getSignHospId())) {
            sql = sql + " AND f.SIGN_HOSP_ID=:SIGN_HOSP_ID ";
            map.put("SIGN_HOSP_ID", qvo.getSignHospId());
        }
        //模糊搜索 姓名
        if (StringUtils.isNotBlank(qvo.getPatientName())) {
            sql = sql + " AND u.PATIENT_NAME LIKE :searchValue ";
            map.put("searchValue", "%" + qvo.getPatientName() + "%");
        }
        //团队
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            sql = sql + " AND f.SIGN_TEAM_ID=:SIGN_TEAM_ID ";
            map.put("SIGN_TEAM_ID", qvo.getTeamId());
            //医生
            if (StringUtils.isNotBlank(qvo.getDrId())) {
                sql = sql + " AND f.SIGN_DR_ID=:SIGN_DR_ID ";
                map.put("SIGN_DR_ID", qvo.getDrId());
            }
        }
        //身份证
        if (StringUtils.isNotBlank(qvo.getPatientIdno())) {
            sql = sql + " AND u.PATIENT_IDNO=:PATIENT_IDNO ";
            map.put("PATIENT_IDNO", qvo.getPatientIdno());
        }
        //居民健康档案
        if (StringUtils.isNotBlank(qvo.getPatientjmda())) {
            sql = sql + " AND c.PATIENT_JMDA=:PATIENT_JMDA ";
            map.put("PATIENT_JMDA", qvo.getPatientjmda());
        }
        //签约日期开始
        if (StringUtils.isNotBlank(qvo.getSignDateStart())) {
            sql = sql + " AND f.SIGN_DATE>=:SIGN_DATE_START ";
            map.put("SIGN_DATE_START", qvo.getSignDateStart() + " 00:00:00");
        }
        //签约日期结束
        if (StringUtils.isNotBlank(qvo.getSignDateEnd())) {
            sql = sql + " AND f.SIGN_DATE<=:SIGN_DATE_END ";
            map.put("SIGN_DATE_END", qvo.getSignDateEnd() + " 23:59:59");
        }
        //协议日期开始
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql = sql + " AND f.SIGN_FROM_DATE>=:SIGN_FROM_DATE_START ";
            map.put("SIGN_FROM_DATE_START", qvo.getSignFromDateStart() + " 00:00:00");
        }
        //协议日期结束
        if (StringUtils.isNotBlank(qvo.getSignFromDateEnd())) {
            sql = sql + " AND f.SIGN_FROM_DATE<=:SIGN_FROM_DATE_END ";
            map.put("SIGN_FROM_DATE_END", qvo.getSignFromDateEnd() + " 23:59:59");
        }
        //助理姓名
        if (StringUtils.isNotBlank(qvo.getSignDrAssistantId())) {
            sql = sql + " AND ( f.SIGN_DR_ASSISTANT_ID =:SIGN_DR_ASSISTANT_ID ) ";
            map.put("SIGN_DR_ASSISTANT_ID", qvo.getSignDrAssistantId());
        }
        //vip
        if (StringUtils.isNotBlank(qvo.getVip()) && qvo.getVip().equals("1")) {
            sql = sql + " AND f.SIGN_TYPE='2'";
        } else {
            sql = sql + " AND f.SIGN_TYPE='1'";
        }
        //到期
        if (StringUtils.isNotBlank(qvo.getExpire()) && qvo.getExpire().equals("1")) {
            sql = sql + " AND f.SIGN_TO_DATE>SYSDATE()";
        }
        //详细地址
        if (StringUtils.isNoneBlank(qvo.getPatientCity())) {
            sql = sql + " and u.PATIENT_CITY =:city ";
            map.put("city", qvo.getPatientCity());
            if (StringUtils.isNoneBlank(qvo.getPatientArea())) {
                sql = sql + " and u.PATIENT_AREA =:area ";
                map.put("area", qvo.getPatientArea());
                if (StringUtils.isNoneBlank(qvo.getPatientStreet())) {
                    sql = sql + " and u.PATIENT_STREET =:street  ";
                    map.put("street", qvo.getPatientStreet());
                    if (StringUtils.isNoneBlank(qvo.getPatientNeighborhoodCommittee())) {
                        sql = sql + " and u.PATIENT_NEIGHBORHOOD_COMMITTEE =:committtee  ";
                        map.put("committtee", qvo.getPatientNeighborhoodCommittee());
                    }
                }

            }
        }
        if (StringUtils.isNoneBlank(qvo.getPatientAddress())) {
            sql = sql += " and u.PATIENT_ADDRESS LIKE :address ";
            map.put("address", "%" + qvo.getPatientAddress() + "%");
        }
        //来源
        if (StringUtils.isNotBlank(qvo.getUpHpis())) {
            sql = sql + " AND f.UP_HPIS =:UP_HPIS ";
            map.put("UP_HPIS", qvo.getUpHpis());
        }
        //签约状态
        if (StringUtils.isNotBlank(qvo.getSignState())) {
            sql = sql + " AND f.SIGN_STATE =:SIGN_STATE ";
            map.put("SIGN_STATE", qvo.getSignState());
        }
//        if (StringUtils.isNotBlank(qvo.getDissolutionType())) {
//            if (qvo.getDissolutionType().equals("0")) {
//                sql = sql + " and  f.SIGN_STATE in ('4','9') ";
//            } else {
//                sql = sql + " and  f.SIGN_STATE=:SignOthnerReason ";
//                map.put("SignOthnerReason", qvo.getDissolutionType());
//
//            }
//        }
        sql = sql + " and f.SIGN_STATE = '4' and f.SIGN_URRENDER_REASON ='签约到期，自动解约' ";
        //导出查到的数据直接固定死、一定是未续签的签约单信息、modify by WangCheng
        sql = sql + " AND f.SIGN_GOTO_SIGN_STATE = '0'";
        List<AppSignVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppSignVo.class, qvo);
        if (ls != null && ls.size() > 0) {
            return ls;
        }
        return null;
    }

    /**
     * 根据服务包计算自费和补贴
     *
     * @param signpackageid
     * @param sign
     * @param signPersGroup
     * @param signsJjType
     * @return
     */
    @Override
    public Map<String, Object> mealCost(String signpackageid, AppSignForm sign, String signPersGroup, String signsJjType) throws Exception{

        Map<String, Object> map = new HashMap<>();
        String[] mealIds = signpackageid.split(";");
        double total = 0.0;
        double czpay = 0.0;
        double zfpay = 0.0;
        double btfee = 0.0;//补贴的费用
        for (String mealId : mealIds) {
            AppServeSetmeal meal = (AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class, mealId);
            boolean flagg = false;
            if (meal != null) {
//                if("1".equals(meal.getSersmJcState())){//是基础包
                //该方式跟web取值一样
                if(StringUtils.isNotBlank(meal.getSersmTotalOneFee())){
                    zfpay += Double.parseDouble(meal.getSersmTotalOneFee());
                }
                //取配制表进行自费判断
                /*if(AddressType.SMS.getValue().equals(sign.getSignAreaCode())){//三明地区服务包费用直接去服务包数据表个人需付总金额字段
                    zfpay += Double.parseDouble(meal.getSersmTotalOneFee());
                }else{
                    if (StringUtils.isNotBlank(sign.getSignAreaCode())) {
                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SIGNCODE, AreaUtils.getAreaCode(sign.getSignAreaCode(), "2"));
                        if (code != null) {//每个地市固定补贴费用
                            String title = code.getCodeTitle();
                            if (StringUtils.isNotBlank(title)) {
                                String[] titles = title.split(";");
                                for (String tt : titles) {
                                    btfee += Double.parseDouble(tt);
                                }
                            }
                        }
                    }
                    //特殊的补贴情况
                    if (StringUtils.isNotBlank(meal.getSersmJjId())) {
                        String[] jjIds = meal.getSersmJjId().split(";");
                        for (String jjId : jjIds) {
                            AppEconAndGov eag = (AppEconAndGov) sysDao.getServiceDo().find(AppEconAndGov.class, jjId);
                            if (eag != null) {
                                boolean flag = false;
                                if (StringUtils.isNotBlank(eag.getEagEconId())) {
                                    AppServeEcon econ = (AppServeEcon) sysDao.getServiceDo().find(AppServeEcon.class, eag.getEagEconId());
                                    if (econ != null) {
                                        //判断是否居民是否符合此补贴情况
                                        if (LabelManageType.FWRQ.getValue().equals(econ.getEconLabelType())) {//根据服务人群判断
                                            if (StringUtils.isNotBlank(signPersGroup) && signPersGroup.indexOf(econ.getEconFwType()) != -1) {
                                                //满足补贴条件
                                                flag = true;
                                            }
                                        } else if (LabelManageType.JJLX.getValue().equals(econ.getEconLabelType())) {//根据经济类型判断
                                            if (StringUtils.isNotBlank(signsJjType) && signsJjType.indexOf(econ.getEconFwType()) != -1) {
                                                flag = true;
                                            }
                                        }
                                    }
                                }
                                if (flag && StringUtils.isNotBlank(eag.getEagGovId())) {
                                    String[] govIds = eag.getEagGovId().split(";");
                                    for (String govId : govIds) {
                                        AppServeGov gov = (AppServeGov) sysDao.getServiceDo().find(AppServeGov.class, govId);
                                        if (gov != null) {
                                            if (gov.getGovTitle().indexOf("全补贴") != -1) {//全补贴情况
                                                flagg = true;
                                                break;
                                            }
                                            czpay += Double.parseDouble(gov.getGovMoney());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (flagg) {//全补贴
                        btfee += Double.parseDouble(meal.getSersmTotalFee());
                    }
                    if (Double.parseDouble(meal.getSersmTotalFee()) - (czpay + btfee) >= 0) {
                        int yh = 0;
                        if (AddressType.QZS.equals(AreaUtils.getAreaCode(sign.getSignAreaCode(), "2"))) {
                            if (StringUtils.isNotBlank(meal.getSersmDiscount())) {
                                yh = Integer.parseInt(meal.getSersmDiscount());
                            }
                        }
                        zfpay += Double.parseDouble(meal.getSersmTotalFee()) - (czpay + btfee + yh);
                    }
//                }else{
//                    zfpay += Double.parseDouble(meal.getSersmTotalFee());
//                }
                }*/
            }
        }
        map.put("czpay", czpay);
        map.put("zfpay", zfpay);
        return map;
    }

    @Override
    public AppSignSetting findsignSetting(String areaCode) throws Exception {

        HashMap map = new HashMap();
        String sql = "";
        if (StringUtils.isNotBlank(areaCode)) {
            sql = " SELECT * from app_sign_setting s where s.SIGNS_AREA_CODE =:code ";
            map.put("code", areaCode);
            List<AppSignSetting> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignSetting.class);
            if (ls != null && ls.size() > 0) {
                return ls.get(0);
            }
        }
        return null;
    }

    @Override
    public void saveImage(String signImageUrl, String signId, String upHpis, String type, String areaCode) throws Exception {
        String[] imageUrls = signImageUrl.split(";");
        for (String imageUrl : imageUrls) {
            AppSignSubtable table = new AppSignSubtable();
            Map<String, Object> mapp = sysDao.getIoUtils().getCtyunOosSample(imageUrl, CommonShortType.YISHENG.getValue());
            String url = mapp.get("objectUrl").toString();
//            table.setImgUrl();PropertiesUtil.getConfValue(
            String urlTop = PropertiesUtil.getConfValue("urlTop") + url.substring(url.indexOf("/picture"), url.length());
            table.setImgUrl(url);
            table.setImgBase64(urlTop);
            table.setSignId(signId);
            table.setType(upHpis);
            table.setSignAreaCode(areaCode);
            if (CommSF.YES.getValue().equals(type)) {
                table.setIsAutograph(CommSF.YES.getValue());
            }
            sysDao.getServiceDo().add(table);
            String slog = "上传签约单主键:=="+signId+"附件图片主键:=="+table.getId()+"上传类型:=="+type+"signImageUrl:"+signImageUrl;
            if (StringUtils.isNotBlank(slog)) {
                logger.info(slog);
            }
        }
    }

    @Override
    public AppSmSignEntity findSignBypatientId(String patientId) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", patientId);
        map.put("SIGN_STATE", new String[]{SignFormType.YUQY.getValue(), SignFormType.DQY.getValue(), SignFormType.YQY.getValue()});
        String sql = "SELECT\n" +
                "\tSIGN_TEAM_ID teamId,\n" +
                "\t'' teamName,\n" +
                "\tSIGN_HOSP_ID orgId,\n" +
                "\t'' orgName,\n" +
                "\tSIGN_DR_ID drId,\n" +
                "\t'' drName,\n" +
                "\t'' drTel\n" +
                "FROM\n" +
                "\tapp_sign_form\n" +
                "WHERE\n" +
                "\tSIGN_STATE IN (:SIGN_STATE)\n" +
                "AND SIGN_PATIENT_ID = :patientId ";
        List<AppSmSignEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppSmSignEntity.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询机构糖尿病高血压居民
     *
     * @param qvo
     * @return
     */
    @Override
    public List<AppPeopleBasicEntity> findPeopleByOrg(AppSmPeopleBasicVo qvo) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("orgId", qvo.getOrgId());
        map.put("SIGN_STATE", new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()});
        String sy = "";
        String sss = "";
        if ("1".equals(qvo.getType())) {//单查高血压
            sy = " AND cc.labelValue = '201' ";
            sss = " AND g.LABEL_VALUE IN ('201','202') ";
        } else if ("2".equals(qvo.getType())) {//糖尿病
            sy = " AND cc.labelValue = '202' ";
            sss = " AND g.LABEL_VALUE IN ('201','202') ";
        } else if("3".equals(qvo.getType())){//精神病
            sy = " AND cc.labelValue = '207' ";
            sss = " AND g.LABEL_VALUE IN ('201','202') ";
        }else if("4".equals(qvo.getType())){//结核病
            sy = " AND cc.labelValue = '208' ";
            sss = " AND g.LABEL_VALUE IN ('201','202') ";
        }else {
            if ("0".equals(qvo.getType())) {//全部或关系
                sy = " AND (find_in_set('" + DiseaseType.GXY.getValue() + "',cc.labelValue) OR find_in_set('" + DiseaseType.TNB.getValue() + "',cc.labelValue) )";
            } else {//既是高血压 又是糖尿病
                String[] types = qvo.getType().split(";");
                for(String typee:types){
                    if("1".equals(typee)){
                        sy += " AND find_in_set('" + DiseaseType.GXY.getValue() + "',cc.labelValue)";
                    }else if("2".equals(typee)){
                        sy += " AND find_in_set('" + DiseaseType.TNB.getValue() + "',cc.labelValue)";
                    }
                }
            }
            sss = " AND g.LABEL_VALUE IN ('201','202')";
        }
        String sqlcolor = "";
        //颜色标签
        if (StringUtils.isNotBlank(qvo.getColorType())) {
            String colors = "";
            String[] colorTypes = qvo.getColorType().split(";");
            for (String colorType : colorTypes) {
                String color = "";
                if ("1".equals(colorType)) {
                    color = "red";
                } else if ("2".equals(colorType)) {
                    color = "yellow";
                } else if ("3".equals(colorType)) {
                    color = "green";
                } else if ("4".equals(colorType)) {
                    color = "gray";
                }
                if (StringUtils.isBlank(colors)) {
                    colors = color;
                } else {
                    colors += ";" + color;
                }
            }

            if (StringUtils.isNotBlank(colors)) {
                String[] ssss = colors.split(";");
                map.put("colorsss", ssss);
                sss += "  AND g.LABEL_COLOR IN (:colorsss) ";
                for (String colorsy : ssss) {
                    if (StringUtils.isBlank(sqlcolor)) {
                        sqlcolor = " AND (find_in_set('" + colorsy + "',cc.labelColor) ";
                    } else {
                        sqlcolor += " OR find_in_set('" + colorsy + "',cc.labelColor) ";
                    }
                }
                if (StringUtils.isNotBlank(sqlcolor)) {
                    sqlcolor += " ) ";
                }
            }

        }

        String sql = "SELECT\n" +
                "\ta.ID patientId,\n" +
                "\ta.PATIENT_NAME patientName,\n" +
                "\ta.PATIENT_IDNO patientIdno,\n" +
                "\ta.PATIENT_TEL paitentTel,\n" +
                "\ta.PATIENT_GENDER patientGender,\n" +
                "\ta.PATIENT_JMDA patientJmdah,\n" +
                "\tb.SIGN_TEAM_ID teamId,\n" +
                "\t(SELECT TEAM_NAME  FROM APP_TEAM WHERE ID = b.SIGN_TEAM_ID) teamName,\n" +
                "\tb.SIGN_HOSP_ID orgId,\n" +
                "\t(SELECT HOSP_NAME FROM APP_HOSP_DEPT WHERE ID = b.SIGN_HOSP_ID) orgName,\n" +
                "\ta.PATIENT_PROVINCE province,\n" +
                "\ta.PATIENT_CITY city,\n" +
                "\ta.PATIENT_AREA area,\n" +
                "\ta.PATIENT_STREET street,\n" +
                "\ta.PATIENT_ADDRESS addr,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_VALUE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=b.ID " + sss + ") labelValue,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=b.ID " + sss + ") labelTitle,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_COLOR) FROM APP_LABEL_DISEASE g WHERE g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=b.ID " + sss + ") labelColor,\n" +
                "\ta.PATIENT_X xaxis,\n" +
                "\ta.PATIENT_Y yaxis,\n" +
                "\tb.SIGN_DR_ID drId,\n" +
                "\tnull gluValue,\n" +
                "\tnull hbpValue,\n" +
                "\t(SELECT DR_NAME FROM APP_DR_USER WHERE ID = b.SIGN_DR_ID) drName,\n" +
                "\t(SELECT DR_TEL FROM APP_DR_USER WHERE ID = b.SIGN_DR_ID) drTel\n" +
                "FROM\n" +
                "\tapp_patient_user a\n" +
                "INNER JOIN app_sign_form b ON a.ID = b.SIGN_PATIENT_ID\n" +
                "INNER JOIN app_label_disease c ON b.ID = c.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\tb.SIGN_STATE IN (:SIGN_STATE)\n";

        //默认带机构id查询
        if (StringUtils.isNotBlank(qvo.getOrgId())) {
            map.put("orgId", qvo.getOrgId());
            sql += " AND b.SIGN_HOSP_ID = :orgId ";
        }
        //根据区域查询
        if (StringUtils.isNotBlank(qvo.getAreaId())) {
            map.put("areaId", qvo.getAreaId() + "%");
            sql += " AND b.SIGN_AREA_CODE LIKE :areaId ";
        }
        //团队id查询
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            map.put("teamId", qvo.getTeamId());
            sql += " AND b.SIGN_TEAM_ID =:teamId ";
        }
        //居民姓名模糊查询
        if (StringUtils.isNotBlank(qvo.getPatientName())) {
            map.put("patientName", qvo.getPatientName() + "%");
            sql += " AND a.PATIENT_NAME LIKE :patientName ";
        }
        //医生id查询
        if (StringUtils.isNotBlank(qvo.getDrId())) {
            map.put("drId", qvo.getDrId());
            sql += " AND b.SIGN_DR_ID =:drId ";
        }
        //居民身份证查询
        if (StringUtils.isNotBlank(qvo.getPatientIdno())) {
            map.put("idno", qvo.getPatientIdno());
            sql += " AND a.PATIENT_IDNO =:idno ";
        }
        //居民性别查询
        if (StringUtils.isNotBlank(qvo.getSex())) {
            map.put("sex", qvo.getSex());
            sql += " AND a.PATIENT_GENDER =:sex ";
        }
        //联系电话模糊查询
        if (StringUtils.isNotBlank(qvo.getPatientTel())) {
            map.put("tel", "%" + qvo.getPatientTel() + "%");
            sql += " AND a.PATIENT_TEL LIKE :tel ";
        }
        if(StringUtils.isNotBlank(qvo.getPatientId())){
            map.put("patientId",qvo.getPatientId());
            sql += " AND a.ID =:patientId ";
        }

        String ssql = "SELECT * FROM ("+sql+" ) cc WHERE 1=1 AND cc.orgName IS NOT NULL AND cc.teamName IS NOT NULL "+sy;
//        if("1".equals(qvo.getColourType())){//红标
//            ssql += " AND find_in_set('red',cc.labelColor)";
//        }else if("2".equals(qvo.getColourType())){//黄标
//            ssql += " AND find_in_set('yellow',cc.labelColor)";
//        }else if("3".equals(qvo.getColourType())){//绿标
//            ssql += " AND find_in_set('green',cc.labelColor)";
//        }

       /* //疾病类型查询
        if("0".equals(qvo.getType())){//全部（或）
            ssql +=" AND (find_in_set('"+DiseaseType.GXY.getValue()+"',cc.labelValue) OR find_in_set('"+DiseaseType.TNB.getValue()+"',cc.LabelValue)) ";
        }else if("1".equals(qvo.getType())){
            ssql += " AND find_in_set('"+DiseaseType.GXY.getValue()+"',cc.labelValue) ";
        }else if("2".equals(qvo.getType())){
            ssql += " AND find_in_set('"+DiseaseType.TNB.getValue()+"',cc.LabelValue) ";
        }else {
            ssql +=" AND find_in_set('"+DiseaseType.GXY.getValue()+"',cc.labelValue) AND find_in_set('"+DiseaseType.TNB.getValue()+"',cc.LabelValue) ";
        }
        //色标类型查询
        if("0".equals(qvo.getColorType())){//全部

        }else if("1".equals(qvo.getColorType())){//红标

        }*/

        if (StringUtils.isNotBlank(sqlcolor)) {
            ssql += sqlcolor;
        }
        ssql += " GROUP BY cc.patientId ";
        List<AppPeopleBasicEntity> list = sysDao.getServiceDo().findSqlMapRVo(ssql, map, AppPeopleBasicEntity.class, qvo);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 修改建档立卡人群签约状态
     *
     * @param idno      身份证号
     * @param signId    签约单id
     * @param drId      医生id
     * @param teamId    团队id
     * @param signState 签约状态
     * @param groups    服务人群
     * @param areaCode  区域编码
     * @param hospId    机构主键
     * @param signDate  签约时间
     */
    @Override
    public void addOrModifyArchivingSign(String idno, String signId, String drId, String teamId, String signState, String[] groups, String areaCode, String hospId, Calendar signDate, AppPatientUser user) throws Exception{
        List<AppArchivingCardPeople> list = sysDao.getServiceDo().loadByPk(AppArchivingCardPeople.class, "archivingPatientIdno", idno);
        if (list != null && list.size() > 0) {
            for (AppArchivingCardPeople ll : list) {
                if(StringUtils.isNotBlank(ll.getAddrRuralCode())){
                    if(AreaUtils.getAreaCode(areaCode,"2").equals(AreaUtils.getAreaCode(ll.getAddrRuralCode(),"2"))){
                        ll.setTeamId(teamId);
                        ll.setDrId(drId);
                        ll.setSignId(signId);
                        ll.setSignState(signState);
                        ll.setSignFromDate(signDate);
                        ll.setHospId(hospId);
                        ll.setRhfId(user.getPatientjmda());
                        sysDao.getServiceDo().modify(ll);
                        sysDao.getAppLabelGroupDao().addLabel(groups, ll.getId(), teamId, areaCode, LabelManageType.JDLK.getValue());
                    }
                }

                /*else{
                    ll.setTeamId(null);
                    ll.setDrId(null);
                    ll.setSignId(null);
                    ll.setSignState(null);
                    ll.setSignFromDate(null);
                    ll.setHospId(null);
                    ll.setRhfId(null);
                    if(StringUtils.isBlank(ll.getNotSignReason())){
                        ll.setNotSignReason("21");
                    }
                    sysDao.getServiceDo().modify(ll);
                }*/
            }
        }
//        else {
//            if(StringUtils.isNotBlank(user.getPatientStreet())){
//                if(AreaUtils.getAreaCode(areaCode,"2").equals(AreaUtils.getAreaCode(user.getPatientStreet(),"2"))){
//                    AppArchivingCardPeople vo = new AppArchivingCardPeople();
//                    if (user != null) {
//                        if (StringUtils.isNotBlank(user.getPatientArea())) {
//                            vo.setAddrCountyCode(user.getPatientArea());
//                            CdAddress address = sysDao.getCdAddressDao().findByCacheCode(user.getPatientArea());
//                            if (address != null) {
//                                vo.setAddrCountyName(address.getAreaSname());
//                            }
//                        }
//                        if (StringUtils.isNotBlank(user.getPatientStreet())) {
//                            vo.setAddrRuralCode(user.getPatientStreet());
//                            CdAddress address = sysDao.getCdAddressDao().findByCacheCode(user.getPatientStreet());
//                            if (address != null) {
//                                vo.setAddrRuralName(address.getAreaSname());
//                            }
//                        }
//                        if (StringUtils.isNotBlank(user.getPatientNeighborhoodCommittee())) {
//                            vo.setAddrVillageCode(user.getPatientNeighborhoodCommittee());
//                            CdAddress address = sysDao.getCdAddressDao().findByCacheCode(user.getPatientNeighborhoodCommittee());
//                            if (address != null) {
//                                vo.setAddrVillageName(address.getAreaSname());
//                            }
//                        }
//                        vo.setPatientTel(user.getPatientTel());
//                        vo.setArchivingPatientName(user.getPatientName());
//                        vo.setRhfId(user.getPatientjmda());
//                        vo.setArchivingPatientIdno(idno);
//                        vo.setHospId(hospId);
//                        vo.setTeamId(teamId);
//                        vo.setDrId(drId);
//                        vo.setSignId(signId);
//                        vo.setSignFromDate(signDate);
//                        vo.setSignState(signState);
//                        vo.setSourceType("2");
//                        vo.setAge(user.getPatientAge());
//                        vo.setSex(user.getPatientGender());
//                        vo.setBirthday(user.getPatientBirthday());
//                        vo.setNotSignReason("15");
//                        sysDao.getServiceDo().add(vo);
//                        sysDao.getAppLabelGroupDao().addLabel(groups, vo.getId(), teamId, areaCode, LabelManageType.JDLK.getValue());
//                    }
//                }
//            }
//        }
    }

    @Override
    public List<AppArchivintPeopleEntity> findSignArchivingByTeamId(ResidentVo qvo) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("teamId", qvo.getTeamId());
        String[] fwState = {ResidentMangeType.PTRQ.getValue(), ResidentMangeType.WEIZHI.getValue()};
        map.put("LABEL_VALUE", fwState);
        String sql = "SELECT\n" +
                "\ta.ARCHIVING_PATIENT_NAME name,\n" +
                "\ta.ARCHIVING_PATIENT_IDNO idno,\n" +
                "\t(SELECT TEAM_NAME FROM app_team WHERE ID = a.TEAM_ID) teamName,\n" +
                "\t(SELECT DR_NAME FROM app_dr_user WHERE ID = a.DR_ID) drName,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) FROM app_label_archiving WHERE LABEL_ARCHIVING_ID = a.ID AND LABEL_VALUE NOT IN (:LABEL_VALUE)) keyCrowd,\n" +
                "a.SIGN_FROM_DATE signDate,\n" +
                "'' agreement,\n" +
                "'' other\n" +
                "FROM\n" +
                "\tapp_archivingcard_people a INNER JOIN app_sign_form b ON a.SIGN_ID = b.ID\n" +
                "WHERE 1=1\n" +
                "AND a.SIGN_ID IS NOT NULL\n" +
                "AND a.SOURCE_TYPE = '1'\n" +
                "AND b.SIGN_TEAM_ID =:teamId ";
        if (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearStart())) {
            map.put("startTime", qvo.getYearStart() + " 00:00:00");
            sql += " AND a.SIGN_FROM_DATE >=:startTime ";
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearEnd())) {
            map.put("endTime", qvo.getYearEnd() + " 23:59:59");
            sql += " AND a.SIGN_FROM_DATE <=:endTime ";
        }
        sql += " GROUP BY a.ARCHIVING_PATIENT_IDNO";
        List<AppArchivintPeopleEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppArchivintPeopleEntity.class, qvo);
        return list;
    }

    @Override
    public List<AppArchivintPeopleEntity> findPeopleBySign(ResidentVo qvo) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("teamId", qvo.getTeamId());
        String[] fwState = {ResidentMangeType.PTRQ.getValue(), ResidentMangeType.WEIZHI.getValue()};
        map.put("LABEL_VALUE", fwState);
        map.put("SIGN_STATE", new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue()});
        String sql = "SELECT\n" +
                "\ta.PATIENT_NAME name,\n" +
                "\ta.PATIENT_IDNO idno,\n" +
                "\t(SELECT TEAM_NAME FROM app_team WHERE ID = b.SIGN_TEAM_ID) teamName,\n" +
                "\t(SELECT DR_NAME FROM app_dr_user WHERE ID = b.SIGN_DR_ID) drName,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) FROM app_label_group WHERE LABEL_SIGN_ID = b.ID AND LABEL_VALUE NOT IN (:LABEL_VALUE)) keyCrowd,\n" +
                "b.SIGN_FROM_DATE signDate,\n" +
                "'' sourceType," +
                "'' agreement,\n" +
                "'' other\n" +
                "FROM\n" +
                "\tapp_patient_user a INNER JOIN app_sign_form b ON a.ID = b.SIGN_PATIENT_ID\n" +
                "WHERE 1=1\n" +
                "AND b.SIGN_STATE IN (:SIGN_STATE)\n" +
                "AND b.SIGN_TEAM_ID =:teamId ";
        if (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearStart())) {
            map.put("startTime", qvo.getYearStart() + " 00:00:00");
            sql += " AND b.SIGN_FROM_DATE >=:startTime ";
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearEnd())) {
            map.put("endTime", qvo.getYearEnd() + " 23:59:59");
            sql += " AND b.SIGN_FROM_DATE <=:endTime ";
        }
        sql += " GROUP BY a.PATIENT_IDNO";
        List<AppArchivintPeopleEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppArchivintPeopleEntity.class, qvo);
        return list;
    }

    @Override
    public Map<String, Object> findMapBySign(ResidentVo qvo) throws Exception{
        Map<String, Object> returnMap = new HashMap<>();
        int yqy = 0;
        int zds = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("teamId", qvo.getTeamId());
        String[] fwState = {ResidentMangeType.PTRQ.getValue(), ResidentMangeType.WEIZHI.getValue()};
        map.put("LABEL_VALUE", fwState);
        map.put("SIGN_STATE", new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue()});
        String yqySql = "SELECT a.* FROM app_sign_form a WHERE 1=1 AND a.SIGN_STATE IN (:SIGN_STATE) AND a.SIGN_TEAM_ID =:teamId ";
        String zdSql = " SELECT a.* FROM app_sign_form a INNER JOIN app_label_group b ON a.ID = b.LABEL_SIGN_ID " +
                "WHERE 1=1 AND a.SIGN_STATE IN (:SIGN_STATE) AND a.SIGN_TEAM_ID =:teamId" +
                " AND b.LABEL_VALUE NOT IN (:LABEL_VALUE) ";
        if (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearStart())) {
            map.put("startTime", qvo.getYearStart() + " 00:00:00");
            yqySql += " AND a.SIGN_FROM_DATE >=:startTime ";
            zdSql += " AND a.SIGN_FROM_DATE >=:startTime ";
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearEnd())) {
            map.put("endTime", qvo.getYearEnd() + " 23:59:59");
            yqySql += " AND a.SIGN_FROM_DATE <=:endTime ";
            zdSql += " AND a.SIGN_FROM_DATE <=:endTime ";
        }
        yqySql += " GROUP BY a.SIGN_PATIENT_ID";
        zdSql += " GROUP BY a.ID ";
        String sqlyqy = "SELECT COUNT(1) FROM (" + yqySql + ") cc";
        yqy = sysDao.getServiceDo().findCount(sqlyqy, map);
        String sqlzd = " SELECT COUNT(1) FROM (" + zdSql + ") cc";
        zds = sysDao.getServiceDo().findCount(sqlzd, map);
        returnMap.put("totalSignCount", yqy);
        returnMap.put("paidSignCount", yqy);
        returnMap.put("keySignCount", zds);
        return returnMap;
    }

    /**
     * 查询三明尤溪高血压和糖尿病居民姓名（暂时三明尤溪专用）
     *
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppSmyxPatientEntity> findPeopleList(AppSmyxPatientVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("areaCode", qvo.getAreaCode() + "%");
        map.put("SIGN_STATE", new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()});
        map.put("fwType", new String[]{ResidentMangeType.GXY.getValue(), ResidentMangeType.TNB.getValue(),
                ResidentMangeType.JHB.getValue(),ResidentMangeType.YZJSZY.getValue()});
        String sql = "SELECT\n" +
                "\ta.ID patientId,\n" +
                "\ta.PATIENT_NAME patientName,\n" +
                "\t'' addr\n" +
                "FROM\n" +
                "\tapp_patient_user a\n" +
                "INNER JOIN app_sign_form b ON a.ID = b.SIGN_PATIENT_ID\n" +
                "INNER JOIN app_label_group c ON b.ID = c.LABEL_SIGN_ID \n" +
                "WHERE b.SIGN_STATE IN (:SIGN_STATE)\n" +
                "AND b.SIGN_AREA_CODE LIKE :areaCode\n" +
                "AND c.LABEL_VALUE IN (:fwType)\n" +
                "GROUP BY a.ID";
        List<AppSmyxPatientEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppSmyxPatientEntity.class);
        return list;
    }

    @Override
    public List<Map> findSetmealCountByAreaCodeAndYear(String areaCode, String year) throws Exception{
        HashMap map = new HashMap();
        map.put("areaCode", areaCode + "%");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);

        map.put("startYear", dateNowStr + " 00:00:00");
        map.put("endYear", year + "-12-31 23:59:59");

        String[] signState = new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue()};
        map.put("signState", signState);
        StringBuffer sb = new StringBuffer("");
        sb.append(" SELECT ");
        sb.append(" 	aa.SIGN_PACKAGEID as SIGN_PACKAGEID, ");
        sb.append(" 	aa.LABEL_VALUE as LABEL_VALUE, ");
        sb.append(" 	COUNT(aa.SIGN_PACKAGEID) as SIGN_NUMBER, ");
        sb.append(" 	aa.SIGN_AREA_CODE as SIGN_AREA_CODE ");
        sb.append(" FROM ");
        sb.append(" 	( ");
        sb.append(" 		SELECT ");
        sb.append(" 			fm.ID AS ID, ");
        sb.append(" 			GROUP_CONCAT(LABEL_VALUE) AS LABEL_VALUE, ");
        sb.append(" 			fm.SIGN_PACKAGEID AS SIGN_PACKAGEID, ");
        sb.append(" 			fm.SIGN_AREA_CODE AS SIGN_AREA_CODE ");
        sb.append(" 		FROM ");
        sb.append(" 			app_sign_form fm ");
        sb.append(" 		LEFT JOIN app_label_group gp ON fm.ID = gp.LABEL_SIGN_ID ");
        sb.append(" 		WHERE ");
        sb.append(" 			gp.LABEL_TYPE = '3' ");
        sb.append(" 		AND fm.sign_area_code like :areaCode ");
		sb.append(" 		AND fm.SIGN_FROM_DATE >= :startYear  ");
		sb.append(" 		AND fm.SIGN_FROM_DATE <= :endYear  ");
		sb.append(" 		GROUP BY ");
		sb.append(" 			fm.ID ");
		sb.append(" 	) aa ");
		sb.append(" GROUP BY ");
		sb.append(" 	aa.aa.LABEL_VALUE, ");
		sb.append(" 	aa.SIGN_PACKAGEID, ");
		sb.append(" 	aa.SIGN_AREA_CODE ");

		List<Map> list = sysDao.getServiceDo().findSqlMap(sb.toString(), map);
		return list;
	}

	/**
	 * 获取某医生 某年已签约的服务包和数量
	 *
	 * @version 2018-05-08
	 * @author lyy
	 * @param drId
	 * @param year
	 * @return
	 */
	@Override
	public List<Map> findSetmealCountByDrIdAndYear(String drId, String year) throws Exception{
		HashMap map = new HashMap();
		map.put("SIGN_DR_ID", drId);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);

		map.put("startYear", dateNowStr + " 00:00:00");
		map.put("endYear", year + "-12-31 23:59:59");

		String[] signState = new String[] { SignFormType.YUQY.getValue(), SignFormType.YQY.getValue() };
		map.put("signState", signState);
		StringBuffer sb = new StringBuffer("");
		sb.append(" SELECT ");
		sb.append(" 	aa.SIGN_PACKAGEID as SIGN_PACKAGEID, ");
		sb.append(" 	aa.LABEL_VALUE as LABEL_VALUE, ");
		sb.append(" 	COUNT(aa.SIGN_PACKAGEID) as SIGN_NUMBER, ");
		sb.append(" 	aa.SIGN_DR_ID as SIGN_DR_ID ");
		sb.append(" FROM ");
		sb.append(" 	( ");
		sb.append(" 		SELECT ");
		sb.append(" 			fm.ID AS ID, ");
		sb.append(" 			GROUP_CONCAT(LABEL_VALUE) AS LABEL_VALUE, ");
		sb.append(" 			fm.SIGN_PACKAGEID AS SIGN_PACKAGEID, ");
		sb.append(" 			fm.SIGN_DR_ID AS SIGN_DR_ID ");
		sb.append(" 		FROM ");
		sb.append(" 			app_sign_form fm ");
		sb.append(" 		LEFT JOIN app_label_group gp ON fm.ID = gp.LABEL_SIGN_ID ");
		sb.append(" 		WHERE ");
		sb.append(" 			gp.LABEL_TYPE = '3' ");
		sb.append(" 		AND fm.SIGN_DR_ID = :SIGN_DR_ID ");
        sb.append(" 		AND fm.SIGN_STATE in :signState ");
		sb.append(" 		AND fm.SIGN_FROM_DATE >= :startYear  ");
        sb.append(" 		AND fm.SIGN_FROM_DATE <= :endYear  ");
        sb.append(" 		GROUP BY ");
        sb.append(" 			fm.ID ");
        sb.append(" 	) aa ");
        sb.append(" GROUP BY ");
        sb.append(" 	aa.aa.LABEL_VALUE, ");
		sb.append(" 	aa.SIGN_PACKAGEID, ");
		sb.append(" 	aa.SIGN_DR_ID ");

        List<Map> list = sysDao.getServiceDo().findSqlMap(sb.toString(), map);
        return list;
    }

    @Override
    public AppDrPatientFwEntity findFwTypeByIdno(String idno, String cityCode, String requestUserId, String requestUserName, String userType) throws Exception {
        String state = PropertiesUtil.getConfValue("openTheInterface");
        CloseableHttpClient client = HttpClients.createDefault();
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("idNo", idno);
        jsonParam.put("urlType", cityCode);
        String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findFwType";
        String str = null;
        if (state.equals(OpenTheInterfaceState.NOT.getValue())) {
            str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
        } else {
            str = sysDao.getSecurityCardAsyncBean().getDateBasic(requestUserId, requestUserName, jsonParam.toString(), idno, "findFwType");
        }
        sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(), requestUserId, requestUserName, str, userType, "findFwType");
        if (StringUtils.isNotBlank(str)) {
            JSONObject jsonall = JSONObject.fromObject(str);
            if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")) {
                AppDrPatientFwEntity ls = JSON.parseObject(jsonall.get("vo").toString(), AppDrPatientFwEntity.class);
                return ls;
            }
        }
	    return null;
    }

    /**
     * 查询某个居民的签约协议期限内的健康档案调阅记录
     *
     * @param patientIdno  居民身份证号
     * @param signFromDate 协议开始日期
     * @param signToDate   协议截止日期
     * @return
     */
    @Override
    public List<AppReadFileLog> findReadFileLog(String patientIdno, Calendar signFromDate, Calendar signToDate) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("patientIdno", patientIdno);
        map.put("signFromDate", ExtendDate.getYMD_h_m_s(signFromDate));
        map.put("signToDate", ExtendDate.getYMD_h_m_s(signToDate));

        StringBuilder sql = new StringBuilder();
        sql.append(" select * from app_read_file_log t ");
        sql.append(" where t.patient_idno = :patientIdno ");
        sql.append(" and t.hs_create_date between  :signFromDate and  :signToDate ");

        return sysDao.getServiceDo().findSqlMap(sql.toString(), map, AppReadFileLog.class);
    }

    @Override
    public List<AppSignForm> findOverdueSign() throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("SIGN_STATE",SignFormType.YJY.getValue());
        map.put("reason","签约到期，自动解约");
        map.put("areaCode","3501%");
        String sql = "SELECT a.* FROM app_sign_form a " +
                "INNER JOIN app_patient_user b ON a.SIGN_PATIENT_ID = b.ID\n" +
                "INNER JOIN app_hosp_dept c ON a.SIGN_HOSP_ID = c.ID\n" +
                "INNER JOIN app_team d ON a.SIGN_TEAM_ID = d.ID\n" +
                "WHERE a.SIGN_STATE =:SIGN_STATE\n" +
                "AND a.SIGN_URRENDER_REASON = :reason\n" +
                "AND a.SIGN_GOTO_SIGN_STATE = '0'\n" +
                "AND a.SIGN_AREA_CODE LIKE :areaCode " +
                "AND d.TEAM_DEL_STATE = '0' \n";
        CommConditionVo qvo = new CommConditionVo();
        qvo.setPageSize(10000);
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class,qvo);
        return list;
    }

    /**
     * 根据ID查询签约信息
     *
     * @param id 签约单ID
     * @return 签约信息（包含服务人群名称、特色服务包名称等）
     */
    @Override
    public AppSignFormVo findAppSignFormById(String id) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        StringBuilder sql = new StringBuilder();
        sql.append(" select t1.id id, t1.sign_num signNum, t1.sign_patient_id signPatientId, ");
        sql.append(" t1.sign_hosp_id hospId, t1.sign_team_id teamId, t1.sign_dr_id drId, ");
        sql.append(" t1.sign_patient_age signPatientAge, t1.sign_patient_gender signPatientGender, ");
        sql.append(" t1.sign_patient_idno signPatientIdNo, t1.sign_date signDate, ");
        sql.append(" t1.sign_from_date signFromDate, t1.sign_to_date signToDate, t1.sign_text sersmName, ");
        sql.append(" t2.patient_name patientName, ");
        sql.append(" group_concat(t3.label_title) groupName, ");
        sql.append(" t6.id assessId, t6.total_score_pre totalScore, t6.detail_num detailNum, t6.finish_num finishNum, ");
        sql.append(" t6.is_finish isFinish ");
        sql.append(" from app_sign_form t1 left join app_patient_user t2 on t1.sign_patient_id = t2.id ");
        sql.append(" left join app_label_group t3 on t1.id = t3.label_sign_id ");
        sql.append(" left join assessment t6 on t6.sign_id = t1.id ");
        sql.append(" where t1.id = :id ");
        List<AppSignFormVo> list = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AppSignFormVo.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean findSignByteam(String teamId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",teamId);
        map.put("SIGN_STATE",new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()});
        String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM WHERE SIGN_TEAM_ID =:teamId  ";
        int count = sysDao.getServiceDo().findCount(sql,map);
        if(count>0){
            return true;
        }
        return false;
    }

    /**
     * 根据居民身份证查看是否有已经签约未过期的签约信息
     * WangCheng
     * @param patientIdno
     * @return
     */
    public AppSignForm findSignFrom(String patientIdno)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "select * from app_sign_form a where 1=1";
        if(StringUtils.isNotEmpty(patientIdno)){
            map.put("patientIdno",patientIdno);
            sql += " and a.SIGN_PATIENT_IDNO = :patientIdno";
        }
        sql += " and a.SIGN_STATE in ('0','2'，'98')";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据身份证查看是否存在已经续签的签约单信息
     * WangCheng
     * @param patientIdno
     * @param dissolutionType
     * @return
     */
    public AppSignForm findSignFromByIdNo(String patientIdno,String dissolutionType)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "select * from app_sign_form a where 1=1";
        if(StringUtils.isNotEmpty(patientIdno)){
            map.put("patientIdno",patientIdno);
            sql += " and a.SIGN_PATIENT_IDNO = :patientIdno";
        }
        if(StringUtils.isNotEmpty(dissolutionType)){
            sql += " and a.SIGN_STATE in ('0','2','98')";
        }else {
            sql += " and a.SIGN_STATE = '98'";
        }
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询签约起始日期默认一个月的权限
     * WangCheng
     * @param signAreaCode
     * @return
     */
    public AppSignFormSetting findSignStartSate(String signAreaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "select * from app_sign_form_setting a where 1=1";
        if(StringUtils.isNotEmpty(signAreaCode)){
            map.put("signAreaCode",signAreaCode);
            sql += " and a.HOSP_AREA_SHORT = :signAreaCode";
        }
        List<AppSignFormSetting> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignFormSetting.class);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public AppSignForm findSignBySignState(String[] signState,String patientId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        if(signState != null && signState.length>0){
            map.put("SIGN_STATE",signState);
            map.put("SIGN_PATIENT_ID",patientId);
            String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_STATE IN (:SIGN_STATE) AND SIGN_PATIENT_ID =:SIGN_PATIENT_ID ";
            List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
            if(list != null && list.size()>0){
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 查询高血压、糖尿病、精神病、结核病或的关系
     * @param qvo
     * @return
     */
    @Override
    public List<AppPeopleBasicEntity> findPeopleByOrgNew(AppSmPeopleBasicVo qvo) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("orgId", qvo.getOrgId());
        map.put("SIGN_STATE", new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()});
        String sy = "";
        String sss = "";
        int num = 1;
        if ("1".equals(qvo.getType())) {//单查高血压
            sy = " AND cc.labelValue = '201' ";
            sss = " AND g.LABEL_VALUE IN ('201','202','207','208') ";
        } else if ("2".equals(qvo.getType())) {//糖尿病
            sy = " AND cc.labelValue = '202' ";
            sss = " AND g.LABEL_VALUE IN ('201','202','207','208') ";
        } else if("3".equals(qvo.getType())){//精神病
            sy = " AND cc.labelValue = '207' ";
            sss = " AND g.LABEL_VALUE IN ('201','202','207','208') ";
        }else if("4".equals(qvo.getType())){//结核病
            sy = " AND cc.labelValue = '208' ";
            sss = " AND g.LABEL_VALUE IN ('201','202','207','208') ";
        }else {
            if ("0".equals(qvo.getType())) {//全部或关系
                sy = " AND (find_in_set('" + DiseaseType.GXY.getValue() + "',cc.labelValue) OR find_in_set('" + DiseaseType.TNB.getValue() + "',cc.labelValue) OR find_in_set('" + DiseaseType.YZJSZA.getValue()+ "',cc.labelValue) OR find_in_set('" + DiseaseType.JHB.getValue()+ "',cc.labelValue))";
            } else {//多选且的关系
                String[] types = qvo.getType().split(";");
                num = types.length;
                for(String typee:types){
                    if("1".equals(typee)){
                        sy += " AND find_in_set('" + DiseaseType.GXY.getValue() + "',cc.labelValue)";
                    }else if("2".equals(typee)){
                        sy += " AND find_in_set('" + DiseaseType.TNB.getValue() + "',cc.labelValue)";
                    }else if("3".equals(typee)){
                        sy += " AND find_in_set('" + DiseaseType.YZJSZA.getValue() + "',cc.labelValue)";
                    }else if("4".equals(typee)){
                        sy += " AND find_in_set('" + DiseaseType.JHB.getValue() + "',cc.labelValue)";
                    }
                }
            }
            sss = " AND g.LABEL_VALUE IN ('201','202','207','208')";
        }
        String sqlcolor = "";
        //颜色标签
        if (StringUtils.isNotBlank(qvo.getColorType())) {
            String colors = "";
            String[] colorTypes = qvo.getColorType().split(";");
            for (String colorType : colorTypes) {
                String color = "";
                if ("1".equals(colorType)) {
                    color = "red";
                } else if ("2".equals(colorType)) {
                    color = "yellow";
                } else if ("3".equals(colorType)) {
                    color = "green";
                } else if ("4".equals(colorType)) {
                    color = "gray";
                }
                if (StringUtils.isBlank(colors)) {
                    colors = color;
                } else {
                    colors += ";" + color;
                }
            }

            if (StringUtils.isNotBlank(colors)) {
                String[] ssss = colors.split(";");
                map.put("colorsss", ssss);
                sss += "  AND g.LABEL_COLOR IN (:colorsss) ";
                for (String colorsy : ssss) {
                    if (StringUtils.isBlank(sqlcolor)) {
                        sqlcolor = " AND (find_in_set('" + colorsy + "',cc.labelColor) ";
                    } else {
                        sqlcolor += " OR find_in_set('" + colorsy + "',cc.labelColor) ";
                    }
                }
                if (StringUtils.isNotBlank(sqlcolor)) {
                    sqlcolor += " ) ";
                }
            }

        }

        String sql = "SELECT\n" +
                "\ta.ID patientId,\n" +
                "\ta.PATIENT_NAME patientName,\n" +
                "\ta.PATIENT_IDNO patientIdno,\n" +
                "\ta.PATIENT_TEL paitentTel,\n" +
                "\ta.PATIENT_GENDER patientGender,\n" +
                "\ta.PATIENT_JMDA patientJmdah,\n" +
                "\tb.SIGN_TEAM_ID teamId,\n" +
                "\t(SELECT TEAM_NAME  FROM APP_TEAM WHERE ID = b.SIGN_TEAM_ID) teamName,\n" +
                "\tb.SIGN_HOSP_ID orgId,\n" +
                "\t(SELECT HOSP_NAME FROM APP_HOSP_DEPT WHERE ID = b.SIGN_HOSP_ID) orgName,\n" +
                "\ta.PATIENT_PROVINCE province,\n" +
                "\ta.PATIENT_CITY city,\n" +
                "\ta.PATIENT_AREA area,\n" +
                "\ta.PATIENT_STREET street,\n" +
                "\ta.PATIENT_ADDRESS addr,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_VALUE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=b.ID " + sss + ") labelValue,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=b.ID " + sss + ") labelTitle,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_COLOR) FROM APP_LABEL_DISEASE g WHERE g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=b.ID " + sss + ") labelColor,\n" +
                "\t(SELECT COUNT(1) FROM APP_LABEL_DISEASE g WHERE g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=b.ID " + sss + ") countT,\n" +
                "\ta.PATIENT_X xaxis,\n" +
                "\ta.PATIENT_Y yaxis,\n" +
                "\tb.SIGN_DR_ID drId,\n" +
                "\tnull gluValue,\n" +
                "\tnull hbpValue,\n" +
                "\t(SELECT DR_NAME FROM APP_DR_USER WHERE ID = b.SIGN_DR_ID) drName,\n" +
                "\t(SELECT DR_TEL FROM APP_DR_USER WHERE ID = b.SIGN_DR_ID) drTel\n" +
                "FROM\n" +
                "\tapp_patient_user a\n" +
                "INNER JOIN app_sign_form b ON a.ID = b.SIGN_PATIENT_ID\n" +
                "INNER JOIN app_label_disease c ON b.ID = c.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\tb.SIGN_STATE IN (:SIGN_STATE)\n";

        //默认带机构id查询
        if (StringUtils.isNotBlank(qvo.getOrgId())) {
            map.put("orgId", qvo.getOrgId());
            sql += " AND b.SIGN_HOSP_ID = :orgId ";
        }
        //根据区域查询
        if (StringUtils.isNotBlank(qvo.getAreaId())) {
            map.put("areaId", qvo.getAreaId() + "%");
            sql += " AND b.SIGN_AREA_CODE LIKE :areaId ";
        }
        //团队id查询
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            map.put("teamId", qvo.getTeamId());
            sql += " AND b.SIGN_TEAM_ID =:teamId ";
        }
        //居民姓名模糊查询
        if (StringUtils.isNotBlank(qvo.getPatientName())) {
            map.put("patientName", qvo.getPatientName() + "%");
            sql += " AND a.PATIENT_NAME LIKE :patientName ";
        }
        //医生id查询
        if (StringUtils.isNotBlank(qvo.getDrId())) {
            map.put("drId", qvo.getDrId());
            sql += " AND b.SIGN_DR_ID =:drId ";
        }
        //居民身份证查询
        if (StringUtils.isNotBlank(qvo.getPatientIdno())) {
            map.put("idno", qvo.getPatientIdno());
            sql += " AND a.PATIENT_IDNO =:idno ";
        }
        //居民性别查询
        if (StringUtils.isNotBlank(qvo.getSex())) {
            map.put("sex", qvo.getSex());
            sql += " AND a.PATIENT_GENDER =:sex ";
        }
        //联系电话模糊查询
        if (StringUtils.isNotBlank(qvo.getPatientTel())) {
            map.put("tel", "%" + qvo.getPatientTel() + "%");
            sql += " AND a.PATIENT_TEL LIKE :tel ";
        }
        if(StringUtils.isNotBlank(qvo.getPatientId())){
            map.put("patientId",qvo.getPatientId());
            sql += " AND a.ID =:patientId ";
        }

        //详细地址查询
        if(StringUtils.isNotBlank(qvo.getAddr())){
            map.put("addr","%"+qvo.getAddr()+"%");
            sql += " AND a.PATIENT_ADDRESS LIKE :addr ";
        }


        String ssql = "SELECT * FROM ("+sql+" ) cc WHERE 1=1 AND cc.orgName IS NOT NULL AND cc.teamName IS NOT NULL "+sy;
        if (StringUtils.isNotBlank(sqlcolor)) {
            ssql += sqlcolor;
        }

        ssql += " GROUP BY cc.patientId ";
        if(num>1){
            ssql += " HAVING cc.countT = "+num ;
        }
        List<AppPeopleBasicEntity> list = sysDao.getServiceDo().findSqlMapRVo(ssql, map, AppPeopleBasicEntity.class, qvo);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;


        /*Map<String,Object> map = new HashMap<>();
        map.put("orgId", qvo.getOrgId());
        map.put("SIGN_STATE", new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()});
        String sss = " AND g.LABEL_VALUE IN ('201','202','207','208') ";
        String sy = "";
        String stype = "";
        if(StringUtils.isNotBlank(qvo.getType())){
            String[] typess = qvo.getType().split(";");
            for(int i=0;i<typess.length;i++){//遍历查询类型（1高血压 2糖尿病 3精神病 4结核病）
                if("1".equals(typess[i])){
                    stype = "  find_in_set('" + DiseaseType.GXY.getValue() + "',cc.labelValue) ";
                }else if("2".equals(typess[i])){
                    stype = "  find_in_set('" + DiseaseType.TNB.getValue() + "',cc.labelValue)";
                }else if("3".equals(typess[i])){
                    stype = "  find_in_set('" + DiseaseType.YZJSZY.getValue() + "',cc.labelValue)";
                }else if("4".equals(typess[i])){
                    stype = " find_in_set('" + DiseaseType.JHB.getValue() + "',cc.labelValue) ";
                }
                if(i==0){
                    sy = " AND ("+ stype ;
                }else{
                    sy += " OR "+stype;
                }
                if(i==typess.length-1){
                    sy += " ) ";
                }
            }
        }

        String sql = "SELECT\n" +
                "\ta.ID patientId,\n" +
                "\ta.PATIENT_NAME patientName,\n" +
                "\ta.PATIENT_IDNO patientIdno,\n" +
                "\ta.PATIENT_TEL paitentTel,\n" +
                "\ta.PATIENT_GENDER patientGender,\n" +
                "\ta.PATIENT_JMDA patientJmdah,\n" +
                "\tb.SIGN_TEAM_ID teamId,\n" +
                "\t(SELECT TEAM_NAME  FROM APP_TEAM WHERE ID = b.SIGN_TEAM_ID) teamName,\n" +
                "\tb.SIGN_HOSP_ID orgId,\n" +
                "\t(SELECT HOSP_NAME FROM APP_HOSP_DEPT WHERE ID = b.SIGN_HOSP_ID) orgName,\n" +
                "\ta.PATIENT_PROVINCE province,\n" +
                "\ta.PATIENT_CITY city,\n" +
                "\ta.PATIENT_AREA area,\n" +
                "\ta.PATIENT_STREET street,\n" +
                "\ta.PATIENT_ADDRESS addr,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_VALUE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=b.ID " + sss + ") labelValue,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=b.ID " + sss + ") labelTitle,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_COLOR) FROM APP_LABEL_DISEASE g WHERE g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=b.ID " + sss + ") labelColor,\n" +
                "\ta.PATIENT_X xaxis,\n" +
                "\ta.PATIENT_Y yaxis,\n" +
                "\tb.SIGN_DR_ID drId,\n" +
                "\tnull gluValue,\n" +
                "\tnull hbpValue,\n" +
                "\t(SELECT DR_NAME FROM APP_DR_USER WHERE ID = b.SIGN_DR_ID) drName,\n" +
                "\t(SELECT DR_TEL FROM APP_DR_USER WHERE ID = b.SIGN_DR_ID) drTel\n" +
                "FROM\n" +
                "\tapp_patient_user a\n" +
                "INNER JOIN app_sign_form b ON a.ID = b.SIGN_PATIENT_ID\n" +
                "INNER JOIN app_label_disease c ON b.ID = c.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\tb.SIGN_STATE IN (:SIGN_STATE)\n";

        //默认带机构id查询
        if (StringUtils.isNotBlank(qvo.getOrgId())) {
            map.put("orgId", qvo.getOrgId());
            sql += " AND b.SIGN_HOSP_ID = :orgId ";
        }
        //根据区域查询
        if (StringUtils.isNotBlank(qvo.getAreaId())) {
            map.put("areaId", qvo.getAreaId() + "%");
            sql += " AND b.SIGN_AREA_CODE LIKE :areaId ";
        }
        //团队id查询
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            map.put("teamId", qvo.getTeamId());
            sql += " AND b.SIGN_TEAM_ID =:teamId ";
        }
        //居民姓名模糊查询
        if (StringUtils.isNotBlank(qvo.getPatientName())) {
            map.put("patientName", qvo.getPatientName() + "%");
            sql += " AND a.PATIENT_NAME LIKE :patientName ";
        }
        //医生id查询
        if (StringUtils.isNotBlank(qvo.getDrId())) {
            map.put("drId", qvo.getDrId());
            sql += " AND b.SIGN_DR_ID =:drId ";
        }
        //居民身份证查询
        if (StringUtils.isNotBlank(qvo.getPatientIdno())) {
            map.put("idno", qvo.getPatientIdno());
            sql += " AND a.PATIENT_IDNO =:idno ";
        }
        //居民性别查询
        if (StringUtils.isNotBlank(qvo.getSex())) {
            map.put("sex", qvo.getSex());
            sql += " AND a.PATIENT_GENDER =:sex ";
        }
        //联系电话模糊查询
        if (StringUtils.isNotBlank(qvo.getPatientTel())) {
            map.put("tel", "%" + qvo.getPatientTel() + "%");
            sql += " AND a.PATIENT_TEL LIKE :tel ";
        }
        if(StringUtils.isNotBlank(qvo.getPatientId())){
            map.put("patientId",qvo.getPatientId());
            sql += " AND a.ID =:patientId ";
        }

        String ssql = "SELECT * FROM ("+sql+" ) cc WHERE 1=1 AND cc.orgName IS NOT NULL AND cc.teamName IS NOT NULL "+sy;
        ssql += " GROUP BY cc.patientId ";
        List<AppPeopleBasicEntity> list = sysDao.getServiceDo().findSqlMapRVo(ssql, map, AppPeopleBasicEntity.class, qvo);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;*/
    }

    @Override
    public Map<String, Object> findMapCount(AppSmPeopleBasicVo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("signState",new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()});
        int gxyCount = 0;//高血压人数
        int tnbCount = 0;//糖尿病人数
        int jsbCount = 0;//精神病人数
        int jhbCount = 0;//结核病人数

        String sql = "SELECT \n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tapp_sign_form a\n" +
                "INNER JOIN app_patient_user c ON c.ID = a.SIGN_PATIENT_ID " +
                "INNER JOIN app_label_group b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.SIGN_STATE IN :signState\n" +
                " AND b.LABEL_TYPE = '3' ";
        sql += " AND b.LABEL_VALUE = :VALUE ";





        //默认带机构id查询
        if (StringUtils.isNotBlank(qvo.getOrgId())) {
            map.put("orgId", qvo.getOrgId());
            sql += " AND a.SIGN_HOSP_ID = :orgId ";
        }
        //根据区域查询
        if (StringUtils.isNotBlank(qvo.getAreaId())) {
            map.put("areaId", qvo.getAreaId() + "%");
            sql += " AND a.SIGN_AREA_CODE LIKE :areaId ";
        }
        //团队id查询
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            map.put("teamId", qvo.getTeamId());
            sql += " AND a.SIGN_TEAM_ID =:teamId ";
        }
        //医生id查询
        if (StringUtils.isNotBlank(qvo.getDrId())) {
            map.put("drId", qvo.getDrId());
            sql += " AND a.SIGN_DR_ID =:drId ";
        }
        String sqll = "SELECT COUNT(1) FROM ("+sql +" GROUP BY a.ID ) cc ";
        List<AppLabelManage> lisM = sysDao.getAppLabelManageDao().findByType("3");
        if(lisM != null && lisM.size()>0){
            for(AppLabelManage ll:lisM){
                map.put("VALUE",ll.getLabelValue());
                if(ResidentMangeType.GXY.getValue().equals(ll.getLabelValue())){
                    gxyCount = sysDao.getServiceDo().findCount(sqll,map);
                }else if(ResidentMangeType.TNB.getValue().equals(ll.getLabelValue())){
                    tnbCount = sysDao.getServiceDo().findCount(sqll,map);
                }else if(ResidentMangeType.YZJSZY.getValue().equals(ll.getLabelValue())){
                    jsbCount = sysDao.getServiceDo().findCount(sqll,map);
                }else if(ResidentMangeType.JHB.getValue().equals(ll.getLabelValue())){
                    jhbCount = sysDao.getServiceDo().findCount(sqll,map);
                }
            }
        }
        returnMap.put("gxyCount",gxyCount);
        returnMap.put("tnbCount",tnbCount);
        returnMap.put("jsbCount",jsbCount);
        returnMap.put("jhbCount",jhbCount);
        return returnMap;
    }

    @Override
    public List<AppSmyxPatientEntity> findPeopleListNew(AppSmyxPatientVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<>();

        map.put("SIGN_STATE", new String[]{SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()});
        map.put("fwType", new String[]{ResidentMangeType.JHB.getValue(),ResidentMangeType.YZJSZY.getValue()});
        String sql = "SELECT\n" +
                "\ta.ID patientId,\n" +
                "\ta.PATIENT_NAME patientName,\n" +
                "\t'' addr\n" +
                "FROM\n" +
                "\tapp_patient_user a\n" +
                "INNER JOIN app_sign_form b ON a.ID = b.SIGN_PATIENT_ID\n" +
                "INNER JOIN app_label_group c ON b.ID = c.LABEL_SIGN_ID \n" +
                "WHERE b.SIGN_STATE IN (:SIGN_STATE)\n" +
                "AND c.LABEL_VALUE IN (:fwType)\n" +
                "AND (a.PATIENT_X IS NULL OR a.PATIENT_Y IS NULL OR a.PATIENT_X = '' OR a.PATIENT_Y = '' ) " ;

        if(StringUtils.isNotBlank(qvo.getAreaCode())){
            map.put("areaCode", qvo.getAreaCode() + "%");
            sql +=  " AND b.SIGN_AREA_CODE LIKE :areaCode\n " ;
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND b.SIGN_HOSP_ID = :hospId ";
        }

        sql += " GROUP BY a.ID";
        List<AppSmyxPatientEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppSmyxPatientEntity.class);
        return list;
    }

    @Override
    public List<AppSignForm> findSignByRepeatSign() throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("SIGN_STATE",SignFormType.YJY.getValue());
        map.put("reason","身份证重复,签约单重复");
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE 1=1 AND SIGN_STATE =:SIGN_STATE AND SIGN_URRENDER_REASON =:reason ";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
        if(list != null && list.size()>0){
            return list;
        }
        return null;
    }

    /**
     * 根据居民身份证查询签约信息
     * @param idno
     * @return
     * @throws Exception
     */
    @Override
    public AppSignForm findByPatientIdno(String idno) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("idno",idno);
        String sql = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tapp_sign_form a\n" +
                "INNER JOIN app_patient_user b ON a.SIGN_PATIENT_ID = b.ID\n" +
                "AND a.SIGN_STATE IN ('0', '2')\n" +
                "AND b.PATIENT_IDNO = :idno ";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public String addImportExcelSign(Map<Integer, String> map) throws Exception {
        String result1 = "成功导入"+map.size()+"条";
        System.out.println("总数"+map.size());
        for (int i = 0; i < map.size(); i++) {//循环每条记录数据
            int num = i + 1;
            System.out.println("第"+num+"条");
            String[] ss = map.get(i).split("\\|");//取每条记录的每一个字段

            //先根据是否是队长判断该条记录是否要添加团队数据
            if(org.apache.commons.lang.StringUtils.isBlank(ss[0])){
                throw new Exception( "导入失败：第"+num+"条,居民姓名不能为空");
            }else if(org.apache.commons.lang.StringUtils.isBlank(ss[1])){
                throw new Exception( "导入失败：第"+num+"条,身份证不能为空");
            }
//            else if(org.apache.commons.lang.StringUtils.isBlank(ss[3])){
//                throw new Exception( "导入失败：第"+num+"条,居民社保卡不能为空");
//            }
            else if(org.apache.commons.lang.StringUtils.isBlank(ss[4])){
                throw new Exception( "导入失败：第"+num+"条,居民健康档案号不能为空");
            }else if(org.apache.commons.lang.StringUtils.isBlank(ss[5])){
                throw new Exception( "导入失败：第"+num+"条,现在地址不能为空");
            }else if(org.apache.commons.lang.StringUtils.isBlank(ss[6])){
                throw new Exception( "导入失败：第"+num+"条,村/居委会行政区划不能为空");
            }else if(org.apache.commons.lang.StringUtils.isBlank(ss[7])){
                throw new Exception( "导入失败：第"+num+"条,机构编号不能为空");
            }else if(org.apache.commons.lang.StringUtils.isBlank(ss[8])){
                throw new Exception( "导入失败：第"+num+"条,医生账号不能为空");
            }else if(org.apache.commons.lang.StringUtils.isBlank(ss[9])) {
                throw new Exception( "导入失败：第"+num+"条,医生姓名不能为空");
            }else if(org.apache.commons.lang.StringUtils.isBlank(ss[12])) {
                throw new Exception( "导入失败：第"+num+"条,缴费状态不能为空");
            }else if(org.apache.commons.lang.StringUtils.isBlank(ss[13])) {
                throw new Exception( "导入失败：第"+num+"条,服务人群不能为空");
            }else if(org.apache.commons.lang.StringUtils.isBlank(ss[14])) {
                throw new Exception( "导入失败：第"+num+"条,经济类型不能为空");
            }else if(org.apache.commons.lang.StringUtils.isBlank(ss[15])) {
                throw new Exception( "导入失败：第"+num+"条,服务包编号不能为空");
            }else if(org.apache.commons.lang.StringUtils.isBlank(ss[16])) {
                throw new Exception( "导入失败：第"+num+"条,签约时间不能为空");
            }else{
                //先判断机构、医生是否存在
                //根据机构编码查询机构信息
                AppHospDept dept = sysDao.getAppHospDeptDao().findHospByCode(ss[7]);
                if(dept == null){
                    throw new Exception( "导入失败：第"+num+"条,查无机构数据，请先上传机构信息！");
                }

                //根据签约医生账号和姓名查询医生信息
                AppDrUser drUser = sysDao.getAppDrUserDao().findDrByCode(ss[8],ss[9]);
                if(drUser == null){
                    throw new Exception( "导入失败：第"+num+"条,查无签约医生数据，请先上传医生信息！");
                }
                //根据助理医生账号和姓名查询医生信息
                AppDrUser zlDr = null;
                if(StringUtils.isNotBlank(ss[10]) && StringUtils.isNotBlank(ss[11])){//助理不为空
                    zlDr = sysDao.getAppDrUserDao().findDrByCode(ss[8],ss[9]);
                    if(zlDr == null){
                        throw new Exception( "导入失败：第"+num+"条,查无助理医生数据，请先上传医生信息！");
                    }
                }
                //根据团队编码查询团队信息
//                AppTeam team = sysDao.getAppTeamDao().findTeamByCode(ss[16]);
                //查询根据机构id和医生id查询团队信息
                AppTeam team = sysDao.getAppTeamDao().findHospDrId(dept.getId(),drUser.getId());
                if(team == null){//团队没有，默认创建团队
                    throw new Exception( "导入失败：第"+num+"条,查无团队数据，请先上传团队信息！");

                    /*team = new AppTeam();
                    team.setTeamTel(drUser.getDrTel());
                    team.setTeamDrCode(drUser.getDrCode());
                    team.setTeamDrName(drUser.getDrName());
                    team.setTeamDrId(drUser.getId());
                    team.setTeamState(CommonEnable.QIYONG.getValue());
                    team.setTeamHospId(dept.getId());
                    team.setTeamCreateTime(Calendar.getInstance());
                    team.setTeamHospName(dept.getHospName());
                    team.setTeamName(drUser.getDrName()+"服务团队");
                    team.setTeamType(TeamType.JTQY.getValue());
                    sysDao.getServiceDo().add(team);
                    //添加团队成员
                    AppTeamMember teamMember = new AppTeamMember();
                    teamMember.setMemState("0");
                    teamMember.setMemDrId(drUser.getId());
                    teamMember.setMemDrName(drUser.getDrName());
                    teamMember.setMemTeamid(team.getId());
                    teamMember.setDrTel(drUser.getDrTel());

                    sysDao.getServiceDo().add(teamMember);*/
//                    sysDao.getServiceDo().getSessionFactory().getCurrentSession().beginTransaction().commit();

                }

                //先根据身份证查询居民是否已经注册了
                AppPatientUser user = null;
                List<AppPatientUser> liUser = sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",ss[1]);
                if(liUser != null && liUser.size()>0){
                    if(liUser.size()>1){
                        continue;
                    }else{
                        user = liUser.get(0);
                    }
                }
                if(user == null){//如未注册
                    user = new AppPatientUser();
                    user.setPatientName(ss[0]);
                    user.setPatientIdno(ss[1]);
                    //根据身份证算居民的出生日期、性别
                    Map<String,Object> idNos;
                    if(ss[1].length() == 18){
                        idNos = CardUtil.getCarInfo(ss[1]);
                    }else{
                        idNos = CardUtil.getCarInfo15W(ss[1]);
                    }
                    user.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
                    user.setPatientAge(AgeUtil.getAgeYear(user.getPatientBirthday()));
                    user.setPatientGender(idNos.get("sex").toString());
                    if(StringUtils.isNotBlank(ss[2])){
                        user.setPatientTel(ss[2]);
                        //根据联系电话添加默认密码
                        if(ss[2].length() >= 6){
                            user.setPatientPwd(Md5Util.MD5(ss[2].substring(ss[2].length()-6,ss[2].length())));
                        }
                    }
                    user.setPatientCard(ss[3]);
                    user.setPatientProvince(ss[6].substring(0,2)+"0000000000");
                    user.setPatientCity(ss[6].substring(0,4)+"00000000");
                    user.setPatientArea(ss[6].substring(0,6)+"000000");
                    user.setPatientStreet(ss[6].substring(0,9)+"000");
                    user.setPatientNeighborhoodCommittee(ss[6]);
                    user.setPatientAddress(ss[5]);
                    user.setPatientjmda(ss[4]);
                    user.setPatientUpHpis(UserUpHpisType.WEIJIHUO.getValue());
                    user.setPatientState(CommonEnable.QIYONG.getValue());
                    user.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(user.getPatientName()));
                    sysDao.getServiceDo().add(user);
                }else{
                    //存在还要判断是否已经签约
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUser(user.getId());
                    if(form != null){
                        throw new Exception( "导入失败：第"+num+"条,该居民已签约，不可重复签约");
                    }
                }

                AppSignBatch batch = new AppSignBatch();

                if(StringUtils.isNotBlank(user.getPatientCity())) {
                    AppSerial serial = sysDao.getAppSignformDao().getAppSerial(user.getPatientCity().substring(0, 4), "batch");
                    if(serial!=null) {
                        Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.WEBSTATE.getValue());
                        serial.setSerialNo(bcnum.get("old").toString());
                        sysDao.getServiceDo().modify(serial);
                        batch.setBatchNum(bcnum.get("new").toString());//批次号
                    }
                }
                batch.setBatchPatientName(user.getPatientName());
                batch.setBatchCreatePersid(user.getId());

                batch.setBatchTeamId(team.getId());
                batch.setBatchTeamName(team.getTeamName());
                batch.setBatchCreateDate(Calendar.getInstance());
                batch.setBatchOperatorId(drUser.getId());
                batch.setBatchOperatorName(drUser.getDrName());
                batch.setBatchAreaCode(dept.getHospAreaCode());
                sysDao.getServiceDo().add(batch);

                //添加签约单
                AppSignForm signvo = new AppSignForm();
                signvo.setSignState(SignFormType.YUQY.getValue());
                if(PayType.YIJIAOFEI.getValue().equals(ss[12])){
                    signvo.setSignState(SignFormType.YQY.getValue());
                }
                signvo.setSignDrId(drUser.getId());
                signvo.setSignBatchId(batch.getId());
                signvo.setSignHospId(dept.getId());
                signvo.setSignTeamId(team.getId());

                signvo.setSignTeamName(team.getTeamName());
                if(zlDr != null){
                    signvo.setSignDrAssistantId(zlDr.getId());
                }
                boolean flag = ExtendDate.getCalendarFlag(ss[16]);
                if(!flag){
                    throw new Exception( "导入失败：第"+num+"条,签约时间格式不正确");
                }
                Calendar signDate = ExtendDate.getCalendar(ss[16]);
                Calendar calendar = Calendar.getInstance();
                if(signDate.getTimeInMillis() > calendar.getTimeInMillis()){
                    throw new Exception( "导入失败：第"+num+"条,签约时间不能大于当前系统日期");
                }
                signvo.setSignDate(signDate);
                signvo.setSignPatientId(user.getId());
                signvo.setSignPatientAge(Integer.parseInt(user.getPatientAge()));
                signvo.setSignPatientGender(user.getPatientGender());
                signvo.setSignPatientIdNo(user.getPatientIdno());
                signvo.setSignFromDate(signDate);
                Calendar cal = ExtendDate.getCalendar(ss[16]);
                cal.add(Calendar.YEAR,1);
                cal.add(Calendar.DATE,-1);
                signvo.setSignToDate(cal);

                signvo.setSignPayState(ss[12]);//支付状态
                signvo.setSignType("1");//签约类型
                signvo.setUpHpis("2");//签约来源
                signvo.setSignWay("2");//医生代签
                signvo.setSignAreaCode(dept.getHospAreaCode());
                // 原经济类型
                // signvo.setSignsJjType(vo.getSignsJjType());
//                signvo.setSignczpay(vo.getSignczpay());
//                signvo.setSignzfpay(vo.getSignzfpay());
//                signvo.setSignlx(vo.getSignlx());
                signvo.setSignzfpay("0");
                signvo.setSignContractState("0");//1是 0否
                signvo.setSignGreenState("0");//1是 0否
                signvo.setSignYellowState("0");//1是 0否
                signvo.setSignRedState("0");//1是 0否
                signvo.setSignHealthGroup("199");//健康分布
                signvo.setUpHpis("6");
                AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(user.getPatientCity().substring(0, 4), "sign");
                if (serialSign != null) {
                    Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo(),SignFormType.WEBSTATE.getValue());
                    serialSign.setSerialNo(sinum.get("old").toString());
                    sysDao.getServiceDo().modify(serialSign);
                    signvo.setSignNum(sinum.get("new").toString());//签约编码
                }

                //查询服务包
                String[] pkIds = ss[15].split(";");
                String pkIdss = null;
                double zfee = 0;
                String nrAll = "";
                for(String pkId:pkIds){
                    AppServeSetmeal meal = sysDao.getAppServeSetmealDao().findByValue(pkId);
                    if(meal != null){
                        if(StringUtils.isBlank(pkIdss)){
                            pkIdss = meal.getId();
                        }else{
                            pkIdss +=";"+meal.getId();
                        }
                        if(StringUtils.isNotBlank(meal.getSersmFee())){
                            zfee += Double.parseDouble(meal.getSersmTotalOneFee());
                        }
                        String nr = "";
                        //查询组合数据
                        if(StringUtils.isNotBlank(meal.getSersmGroupId())){
                            List<AppServeGroup> groups = sysDao.getAppServeGroupDao().findGroups(meal.getSersmGroupId());
                            if(groups != null && groups.size()>0){
                                for(AppServeGroup group:groups){
                                    if(StringUtils.isNotBlank(group.getSergPkId())){//查询服务内容数据获取服务内容介绍
                                        List<AppServePackage> packages = sysDao.getAppServePackageDao().findPakeges(group.getSergPkId());
                                        if(packages != null){
                                            for(AppServePackage pac:packages){
                                                if(StringUtils.isNotBlank(pac.getSerpkRemark())){
                                                    if(StringUtils.isBlank(nr)){
                                                        nr = pac.getSerpkRemark();
                                                    }else{
                                                        //去除重复的服务内容介绍
                                                        if(!nr.contains(pac.getSerpkRemark())){
                                                            nr += pac.getSerpkRemark();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(StringUtils.isNotBlank(nr)){
                            if(StringUtils.isBlank(nrAll)){
                                nrAll = meal.getSersmName()+":"+nr;
                            }else{
                                nrAll += "\n"+meal.getSersmName() + ":"+nr;
                            }
                        }

                    }
                }
                signvo.setSignpackageid(pkIdss);
                signvo.setSignzfpay(String.valueOf(zfee));
                signvo.setSigntext(nrAll);
                sysDao.getServiceDo().add(signvo);
                //添加服务人群和疾病类型标签
                sysDao.getAppLabelGroupDao().addLabel(ss[13].split(";"),signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(),LabelManageType.FWRQ.getValue());
                String[] disLabels = ss[13].split(";");
                for(String disLabel:disLabels){
                    if(ResidentMangeType.GXY.getValue().equals(disLabel)){//高血压添加高血压疾病标签
                        AppLabelManage labelManage = sysDao.getAppLabelManageDao().findLabelByValue("2","201");
                        AppLabelDisease disease = new AppLabelDisease();
                        disease.setLabelAreaCode(signvo.getSignAreaCode());
                        disease.setLabelValue(labelManage.getLabelValue());
                        disease.setLabelType(labelManage.getLabelType());
                        disease.setLabelTitle(labelManage.getLabelTitle());
                        disease.setLabelId(labelManage.getId());
                        disease.setLabelSignId(signvo.getId());
                        disease.setLabelTeamId(signvo.getSignTeamId());
                        disease.setLabelColor("gray");
                        sysDao.getServiceDo().add(disease);
                    }else if(ResidentMangeType.TNB.getValue().equals(disLabel)){//糖尿病添加糖尿病标签
                        AppLabelManage labelManage = sysDao.getAppLabelManageDao().findLabelByValue("2","202");
                        AppLabelDisease disease = new AppLabelDisease();
                        disease.setLabelAreaCode(signvo.getSignAreaCode());
                        disease.setLabelValue(labelManage.getLabelValue());
                        disease.setLabelType(labelManage.getLabelType());
                        disease.setLabelTitle(labelManage.getLabelTitle());
                        disease.setLabelId(labelManage.getId());
                        disease.setLabelSignId(signvo.getId());
                        disease.setLabelTeamId(signvo.getSignTeamId());
                        disease.setLabelColor("gray");
                        sysDao.getServiceDo().add(disease);
                    }else if(ResidentMangeType.YZJSZY.getValue().equals(disLabel)){//精神病207
                        AppLabelManage labelManage = sysDao.getAppLabelManageDao().findLabelByValue("2","207");
                        AppLabelDisease disease = new AppLabelDisease();
                        disease.setLabelAreaCode(signvo.getSignAreaCode());
                        disease.setLabelValue(labelManage.getLabelValue());
                        disease.setLabelType(labelManage.getLabelType());
                        disease.setLabelTitle(labelManage.getLabelTitle());
                        disease.setLabelId(labelManage.getId());
                        disease.setLabelSignId(signvo.getId());
                        disease.setLabelTeamId(signvo.getSignTeamId());
                        disease.setLabelColor("gray");
                        sysDao.getServiceDo().add(disease);
                    }else if(ResidentMangeType.JHB.getValue().equals(disLabel)){//结核病
                        AppLabelManage labelManage = sysDao.getAppLabelManageDao().findLabelByValue("2","208");
                        AppLabelDisease disease = new AppLabelDisease();
                        disease.setLabelAreaCode(signvo.getSignAreaCode());
                        disease.setLabelValue(labelManage.getLabelValue());
                        disease.setLabelType(labelManage.getLabelType());
                        disease.setLabelTitle(labelManage.getLabelTitle());
                        disease.setLabelId(labelManage.getId());
                        disease.setLabelSignId(signvo.getId());
                        disease.setLabelTeamId(signvo.getSignTeamId());
                        disease.setLabelColor("gray");
                        sysDao.getServiceDo().add(disease);
                    }
                }
                sysDao.getAppLabelGroupDao().addLabel(ss[14].split(";"),signvo.getId(),signvo.getSignTeamId(),signvo.getSignAreaCode(), LabelManageType.JJLX.getValue());

            }
        }
        return result1;
    }

    @Override
    public List<AppSignForm> findListSignByUserId(String userId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("SIGN_STATE",new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()});
        String sql = "SELECT * FROM APP_SIGN_FORM WHERE SIGN_PATIENT_ID =:userId AND SIGN_STATE IN (:SIGN_STATE) ORDER BY SIGN_DATE ";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
        return list;
    }

    /**
     * 姓名、身份证、档案号、是否签约、服务类型、签约行政区划、行政区划级别
     * 签约底层人员信息
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppSignPeopleEntity> findByHospQvo(ResidentVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("hospId",qvo.getHospId());
        map.put("SIGN_STATE",new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()});

        String sql = "SELECT " +
                "b.PATIENT_NAME patientName," +
                "b.PATIENT_IDNO patientIdno," +
                "a.SIGN_STATE signState," +
                "b.PATIENT_JMDA patientJmda," +
                "(SELECT GROUP_CONCAT(LABEL_TITLE) FROM app_label_group WHERE LABEL_SIGN_ID = a.ID AND LABEL_TYPE = '3') fwTitle," +
                "a.SIGN_AREA_CODE signAreaCode," +
                "(SELECT LEVEL_NAME FROM cp_city_area WHERE CITY_AREA_ID = a.SIGN_AREA_CODE) areaLeve " +
                "FROM " +
                "app_sign_form a " +
                "INNER JOIN app_patient_user b ON a.SIGN_PATIENT_ID = b.id " +
                "WHERE " +
                "a.SIGN_STATE IN (:SIGN_STATE) " +
                "AND a.SIGN_HOSP_ID =:hospId";
        if(StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("startTime",qvo.getYearStart()+" 00:00:00");
            sql += " AND a.SIGN_FROM_DATE >=:startTime ";
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("endTime",qvo.getYearEnd()+" 23:59:59");
            sql += " AND a.SIGN_FROM_DATE <=:endTime ";
        }
        sql += " GROUP BY b.ID ";
        List<AppSignPeopleEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSignPeopleEntity.class,qvo);
        return list;
    }
}
