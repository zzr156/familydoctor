package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.po.AppSignSetting;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by hzk on 2017/6/19.
 * 签约单列表
 */
public class AppSignFormListEntity {
    private String signFormId;//签约单
    private String signState;//签约状态 1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约 11同意转签
    private String patientName;//签约人员姓名
    private String patientGender;//性别
    private String patientAge;//年龄
    private String patientId;//患者主键
    private String patientImageurl;//头像
    private String teamHospName;//医院名称 签约机构
    private String teamName;//团队名称
    private String teamId;//团队id 查看协议单使用
    private String signFromDate;//有效开始时间
    private String signToDate;//有效结束时间
    private String signWay;//签约方式 0代表自己 1代表家人代签 2代表医生代签
    //医生端
    private String signDate;////签约时间
    private String patientIdno;//身份证号
    private String patientCard;//社保卡
    private String patientTel;//电话
    private String drName;//医生名称
    private String fwImageName;//服务图片名称
    private String fwb;//服务包
    private String renewState="0";//续签状态
    private OldSign oldSign;//上一次签约单信息
    private String signOthnerReason;//拒签原因
    private String signGoToSignState;//是否转签 （0否 1是）
    private String batchId;//签约批次id
    private String count;//签约条数
    private String payState;//缴费状态
    private List<AppServeEntity> serveList;//套餐信息
    private String drImageurl;//医生头像
    private String drGender;//医生性别
    private String teamState;//医生团队角色 0：队长 1：成员
    private String teamWorkType;//医生在团队的工作类型1.健康管理师,2.专科医生,3.全科医生,4.医技人员,5.公卫医师,6.社区护士
    private String drId;//医生id

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(Timestamp signDate) {
        if(signDate!=null) {
            this.signDate = ExtendDate.getYMD(signDate);
        }
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(Timestamp signFromDate) {
        if(signFromDate!=null) {
            this.signFromDate = ExtendDate.getYMD(signFromDate);
        }
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(Timestamp signToDate) {
        if(signToDate!=null) {
            this.signToDate = ExtendDate.getYMD(signToDate);
        }
    }

    public String getSignFormId() {
        return signFormId;
    }

    public void setSignFormId(String signFormId) {
        this.signFormId = signFormId;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientImageurl() {
        return patientImageurl;
    }

    public void setPatientImageurl(String patientImageurl) {
        this.patientImageurl = patientImageurl;
    }

    public String getTeamHospName() {
        return teamHospName;
    }

    public void setTeamHospName(String teamHospName) {
        this.teamHospName = teamHospName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getSignWay() {
        return signWay;
    }

    public void setSignWay(String signWay) {
        this.signWay = signWay;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) throws Exception {
        if(StringUtils.isNotBlank(patientGender)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CODESEX, patientGender);
            if(value!=null) {
                this.patientGender = value.getCodeTitle();
            }

        }

    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) throws Exception{
        Map<String,Object> idNos = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(this.getPatientIdno())){
            if(this.getPatientIdno().length() == 18){
                idNos = CardUtil.getCarInfo(this.getPatientIdno());
            }else{
                idNos = CardUtil.getCarInfo15W(this.getPatientIdno());
            }
            String birthday = idNos.get("birthday").toString();
            String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
            patientAge = age;
        }
        this.patientAge = patientAge;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFwImageName() {
        return fwImageName;
    }

    public void setFwImageName(String fwImageName) {
        this.fwImageName = fwImageName;
    }

    public String getFwb() {
        return fwb;
    }

    public void setFwb(String fwb) {
        this.fwb = fwb;
    }

    public String getRenewState() {
        return renewState;
    }

    public void setRenewState(String renewState) throws Exception{
        boolean flag = false;
        String gotoSignState = "0";
        //根据签约单查询该签约是否是漳州签约单，是要开放随时续签
        if(StringUtils.isNotBlank(this.getSignFormId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppSignForm form = (AppSignForm)dao.getServiceDo().find(AppSignForm.class,this.getSignFormId());
            if(form != null){
                gotoSignState = form.getSignGoToSignState();
                //如果开放随时续签没有和本年度签约情况相关连就直接判断是哪个地市的，如果关联则可做成配置 (未续签的)
                if(AddressType.ZZS.getValue().equals(AreaUtils.getAreaCode(form.getSignAreaCode(),"2")) && "0".equals(form.getSignGoToSignState())){
                    flag = true;
                }
                //配置判断
              /*  AppSignSetting setting = dao.getAppSignSettingDao().findByAreaCode(AreaUtils.getAreaCode(form.getSignAreaCode(),"2"));
                if(setting != null){
                    if("1".equals(setting.getSerOpenYear()) && "0".equals(form.getSignGoToSignState()){
                        flag = true;
                    }
                }*/
            }
        }
        if(flag){
            renewState = "1";
        }else{
            if(StringUtils.isNotBlank(this.getSignToDate())){
                Calendar cal = Calendar.getInstance();
                int year1 = cal.get(Calendar.YEAR);
                int mm = cal.get(Calendar.MONTH);//现在的月份
                Calendar cal1 = ExtendDate.getCalendar(this.getSignToDate());
                int year2 = cal1.get(Calendar.YEAR);
                int m = cal1.get(Calendar.MONTH);//有效期日期月份
                if(year1==year2){
                    if(m-mm<=1&&m-mm>=0){
                        renewState = "1";
                    }else{
                        renewState = "0";
                    }
                }else{
                    renewState = "0";
                }
            }else{
                renewState = "0";
            }
        }
        this.renewState = renewState;
    }

    public OldSign getOldSign() {
        return oldSign;
    }

    public void setOldSign(String oldSign) {
        if(StringUtils.isNotBlank(this.getPatientId()) && "1".equals(this.getSignState())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            OldSign oldForm = new OldSign();
            Map<String,Object> map = new HashMap<String,Object>();
            String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
            map.put("signState", signStates);
            map.put("patientId",this.getPatientId());
            String sql = "SELECT " +
                    "a.SIGN_PATIENT_ID patientId," +
                    "a.ID signId," +
                    "(SELECT GROUP_CONCAT(g.LABEL_TITLE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID ) fwName," +
                    "(SELECT GROUP_CONCAT(g.LABEL_VALUE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID ) fwValue," +
                    "(SELECT GROUP_CONCAT(g.LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID ) illName," +
                    "(SELECT GROUP_CONCAT(g.LABEL_VALUE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID ) illValue," +
                    "a.SIGN_HEALTH_GROUP healthValue," +
                    "'' healthName " +
                    "FROM APP_SIGN_FORM a " +
                    "WHERE 1=1 ";
            sql += " AND a.SIGN_PATIENT_ID =:patientId AND a.SIGN_STATE IN (:signState) ORDER BY a.SIGN_FROM_DATE ";
            List<OldSign> ls = dao.getServiceDo().findSqlMapRVo(sql,map,OldSign.class);
            if(ls!=null && ls.size()>0){
                oldForm = ls.get(0);
            }
            this.oldSign = oldForm;
        }else{
            this.oldSign = null;
        }
    }

    public String getSignOthnerReason() {
        return signOthnerReason;
    }

    public void setSignOthnerReason(String signOthnerReason) {
        this.signOthnerReason = signOthnerReason;
    }

    public String getSignGoToSignState() {
        return signGoToSignState;
    }

    public void setSignGoToSignState(String signGoToSignState) {
        this.signGoToSignState = signGoToSignState;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        /*String bb =  "";
        if(StringUtils.isNotBlank(batchId)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("batchId",batchId);
            String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM WHERE SIGN_BATCH_ID=:batchId";
            int num = dao.getServiceDo().findCount(sql,map);
            if(num>1){
                bb = batchId;
            }
        }*/
        this.batchId = batchId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        if(count!=null){
            this.count = String.valueOf(count);
        }else{
            this.count = "0";
        }
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public List<AppServeEntity> getServeList() {
        return serveList;
    }

    public void setServeList(String serveList) {
        List<AppServeEntity> list = new ArrayList<>();
        if(StringUtils.isNotBlank(serveList) && StringUtils.isNotBlank(this.getSignFormId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("serveIds",serveList.split(";"));
            String sql = " SELECT " +
                    "a.ID serveId," +
                    "a.SERSM_NAME serveName," +
                    "a.SERSM_GROUP_ID groupId," +
                    "a.SERSM_TOTAL_FEE fee," +
                    "'"+this.getSignFormId() +"' signFormId,"+
                    "'' groupList " +
                    "FROM APP_SERVE_SETMEAL a " +
                    "WHERE a.ID IN (:serveIds)";
            list = dao.getServiceDo().findSqlMapRVo(sql,map,AppServeEntity.class);
        }
        this.serveList = list;
    }

    public String getDrImageurl() {
        return drImageurl;
    }

    public void setDrImageurl(String drImageurl) {
        this.drImageurl = drImageurl;
    }

    public String getDrGender() {
        return drGender;
    }

    public void setDrGender(String drGender) {
        this.drGender = drGender;
    }

    public String getTeamState() {
        return teamState;
    }

    public void setTeamState(String teamState) {
        this.teamState = teamState;
    }

    public String getTeamWorkType() {
        return teamWorkType;
    }

    public void setTeamWorkType(String teamWorkType) {
        this.teamWorkType = teamWorkType;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }
}
