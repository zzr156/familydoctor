package com.ylz.bizDo.web.vo;


import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressConfiguration;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

/**
 * 签约接口
 */
public class WebSignSaveVo {
    private String signId;
    private String areaCodeProvince;//行政区划(省)
    private String areaCodeCity;//行政区划（市）
    //医院信息 根据医院id查询是否存在，没有自动创建医院
    private String hospId;//医院主键
    private String hospName;//医院名称
    private String hospAreaCode;//区域编码
    private String hospAddress;//医院地址
    private String hospTel;//医院电话
    //医生信息 根据医生id查询是否存在，没有自动创建医生
    private String drId;//医生主键
    private String drName;//医生名称
    private String drAccount;//医生账号
    private String drPwd;//医生密码
    private String drGender;//医生性别
    private String drTel;//医生电话
    private String memState;// 团队角色 0：队长 1：成员
    private String signDrAssistantId;
    private String signDrAssistantTel;
    private  String signDrAssistantName;
    private String signteamnane;

    //团队 医生id查询是否已有团队 没有就根据上传的团队名称自动创建团队
    private String teamId;//团队id
    private String teamName;//团队名称
    //患者 根据患者身份证查询是否存在，没有自动创建患者 患者
    private String patientId;//患者id
    private String patientName;//患者名字
    private String patientGender;//患者性别
    private String patientIdno;//患者身份证号
    private String patientCard;//患者社保卡
    private String patientTel;//患者电话
    private String patientAddress;//患者地址
    private String patientPwd;//患者密码
    private String patientAge;//年纪
    private String signlx;//签约类型
    private String patientjtda;
    private String patientjmda;
    private String ptsignPk;
    private String patientProvince;//省
    private String patientCity;//市
    private String patientCityname;
    private String patientArea;//区
    private String patientAreaname;
    private String patientStreet;//街道
    private String patientStreetname;
    private String patientNeighborhoodCommittee;//居委会
    private String patientNeighborhoodCommitteename;//居委会
    private String signTeamName;
    //签约
    private String signDate;//签约时间
    private String signFromDate;//有效开始时间
    private String signToDate;//有效结束时间
    private String signPayState;//缴费状态 1已缴费 0：未缴费
    private String signType;//签约类型 //1家庭签约 2 vip
    private String signPersGroup;//服务人群 1普通人群 2儿童(0-6岁) 3孕产妇 4老年人 5高血压 6糖尿病 7严重精神障碍 8结核病 99未知
    private String signsJjType;//经济类型
    private String signczpay;//财政补贴
    private String signzfpay;//自费

    private String[] persGroup;
    private String[] sJjType;

    private String signWebState;
    private String FormDate;
    private String ToDate;

    private String signtext;//补充协议
    private String signpackageid;//服务包内容表id

    private String batchOperatorName;  //签约操作人
    private String batchOperatorId;     //签约操作人ID

    public String getBatchOperatorName() {
        return batchOperatorName;
    }

    public void setBatchOperatorName(String batchOperatorName) {
        this.batchOperatorName = batchOperatorName;
    }

    public String getBatchOperatorId() {
        return batchOperatorId;
    }

    public void setBatchOperatorId(String batchOperatorId) {
        this.batchOperatorId = batchOperatorId;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getPatientCityname() {
        return patientCityname;
    }

    public void setPatientCityname(String patientCityname) throws Exception {
        if(StringUtils.isNotBlank(this.getPatientCity())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = dao.getCdAddressDao().findByCode(this.getPatientCity());
            if(address!=null) {
                this.patientCityname = address.getAreaSname();
            }else{
                CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(patientCityname);
                if(addressConfiguration != null){
                    this.patientCityname = addressConfiguration.getAreaNameJw();
                }
            }
        }
    }

    public String getPatientAreaname() {
        return patientAreaname;
    }

    public void setPatientAreaname(String patientAreaname) throws Exception {
        if(StringUtils.isNotBlank(this.getPatientArea())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = dao.getCdAddressDao().findByCode(this.getPatientArea());
            if(address!=null) {
                this.patientAreaname = address.getAreaSname();
            }else{
                CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(this.getPatientArea());
                if(addressConfiguration != null){
                    this.patientAreaname = addressConfiguration.getAreaNameJw();
                }
            }
        }
    }

    public String getPatientStreetname() {
        return patientStreetname;
    }

    public void setPatientStreetname(String patientStreetname) throws Exception {
        if(StringUtils.isNotBlank(this.getPatientStreet())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = dao.getCdAddressDao().findByCode(this.getPatientStreet());
            if(address!=null) {
                this.patientStreetname = address.getAreaSname();
            }else{
                CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(this.getPatientStreet());
                if(addressConfiguration != null){
                    this.patientStreetname = addressConfiguration.getAreaNameJw();
                }
            }
        }
    }


    public String getPatientNeighborhoodCommittee() {
        return patientNeighborhoodCommittee;
    }

    public void setPatientNeighborhoodCommittee(String patientNeighborhoodCommittee) {
        this.patientNeighborhoodCommittee = patientNeighborhoodCommittee;
    }

    public String getPatientNeighborhoodCommitteename() {
        return patientNeighborhoodCommitteename;
    }

    public void setPatientNeighborhoodCommitteename(String patientNeighborhoodCommitteename) throws Exception {
        if(StringUtils.isNotBlank(this.getPatientNeighborhoodCommittee())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = dao.getCdAddressDao().findByCode(this.getPatientNeighborhoodCommittee());
            if(address!=null) {
                this.patientNeighborhoodCommitteename = address.getAreaSname();
            }else{
                CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(this.getPatientNeighborhoodCommittee());
                if(addressConfiguration != null){
                    this.patientNeighborhoodCommitteename = addressConfiguration.getAreaNameJw();
                }
            }
        }
    }

    public String getSignteamnane() {
        return signteamnane;
    }

    public void setSignteamnane(String signteamnane) {
        this.signteamnane = signteamnane;
    }

    public String getSignTeamName() {
        return signTeamName;
    }

    public void setSignTeamName(String signTeamName) {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        AppTeam team = (AppTeam) dao.getServiceDo().find(AppTeam.class,this.getTeamId());
        if(team!=null) {
            this.signTeamName = team.getTeamName();
        }
    }

    public String getSignczpay() {
        return signczpay;
    }

    public void setSignczpay(String signczpay) {
        this.signczpay = signczpay;
    }

    public String getSignzfpay() {
        return signzfpay;
    }

    public void setSignzfpay(String signzfpay) {
        this.signzfpay = signzfpay;
    }

    public String getSignDrAssistantName() {
        return signDrAssistantName;
    }

    public void setSignDrAssistantName(String signDrAssistantName) throws Exception {
        if(StringUtils.isNotBlank(this.getSignDrAssistantId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = dao.getAppDrUserDao().findByUserId(this.getSignDrAssistantId());
            if(drUser!=null) {
                this.signDrAssistantName = drUser.getDrName();
            }
        }
    }

    public String getSignWebState() {
        return signWebState;
    }

    public void setSignWebState(String signWebState) {
        this.signWebState = signWebState;
    }

    public String getFormDate() {
        return FormDate;
    }

    public void setFormDate(String formDate) {
        FormDate = formDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getSignsJjType() {
        return signsJjType;
    }

    public void setSignsJjType(String signsJjType) {
        this.signsJjType = signsJjType;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        if(StringUtils.isNotBlank(this.getHospId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept) dao.getServiceDo().find(AppHospDept.class,this.getHospId());
            if(dept!=null) {
                this.hospName = dept.getHospName();
            }
        }
    }

    public String getHospAreaCode() {
        return hospAreaCode;
    }

    public void setHospAreaCode(String hospAreaCode) {
        this.hospAreaCode = hospAreaCode;
    }

    public String getHospAddress() {
        return hospAddress;
    }

    public void setHospAddress(String hospAddress) {
        this.hospAddress = hospAddress;
    }

    public String getHospTel() {
        return hospTel;
    }

    public void setHospTel(String hospTel) {
        this.hospTel = hospTel;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) throws Exception {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = dao.getAppDrUserDao().findByUserId(this.getDrId());
            if(drUser!=null) {
                this.drName = drUser.getDrName();
            }
        }
    }

    public String getDrAccount() {
        return drAccount;
    }

    public void setDrAccount(String drAccount) {
        this.drAccount = drAccount;
    }

    public String getDrPwd() {
        return drPwd;
    }

    public void setDrPwd(String drPwd) {
        this.drPwd = drPwd;
    }

    public String getDrGender() {
        return drGender;
    }

    public void setDrGender(String drGender) {
        this.drGender = drGender;
    }

    public String getDrTel() {
        return drTel;
    }

    public void setDrTel(String drTel) throws Exception {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = dao.getAppDrUserDao().findByUserId(this.getDrId());
            if(drUser!=null) {
                this.drTel = drUser.getDrTel();
            }
        }
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        if(StringUtils.isNotBlank(this.getTeamId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppTeam team = (AppTeam) dao.getServiceDo().find(AppTeam.class,this.getTeamId());
            if(team!=null) {
                this.teamName = team.getTeamName();
            }
        }
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
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

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientPwd() {
        return patientPwd;
    }

    public void setPatientPwd(String patientPwd) {
        this.patientPwd = patientPwd;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(java.sql.Timestamp signFromDate) {
        this.signFromDate = signFromDate!=null? ExtendDate.getYMD(signFromDate):"";
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(java.sql.Timestamp signToDate) {
        this.signToDate = signToDate!=null? ExtendDate.getYMD(signToDate):"";
    }

    public String getSignPayState() {
        return signPayState;
    }

    public void setSignPayState(String signPayState) {
        this.signPayState = signPayState;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignPersGroup() {
        return signPersGroup;
    }

    public void setSignPersGroup(String signPersGroup) {
        this.signPersGroup = signPersGroup;
    }

    public String getMemState() {
        return memState;
    }

    public void setMemState(String memState) {
        this.memState = memState;
    }

    public String getAreaCodeProvince() {
        return areaCodeProvince;
    }

    public void setAreaCodeProvince(String areaCodeProvince) {
        this.areaCodeProvince = areaCodeProvince;
    }

    public String getAreaCodeCity() {
        return areaCodeCity;
    }

    public void setAreaCodeCity(String areaCodeCity) {
        this.areaCodeCity = areaCodeCity;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getSignlx() {
        return signlx;
    }

    public void setSignlx(String signlx) {
        this.signlx = signlx;
    }

    public String getPatientjtda() {
        return patientjtda;
    }

    public void setPatientjtda(String patientjtda) {
        this.patientjtda = patientjtda;
    }

    public String getPatientjmda() {
        return patientjmda;
    }

    public void setPatientjmda(String patientjmda) {
        this.patientjmda = patientjmda;
    }

    public String getPtsignPk() {
        return ptsignPk;
    }

    public void setPtsignPk(String ptsignPk) {
        this.ptsignPk = ptsignPk;
    }

    public String getSignDrAssistantId() {
        return signDrAssistantId;
    }

    public void setSignDrAssistantId(String signDrAssistantId) {
        this.signDrAssistantId = signDrAssistantId;
    }

    public String getSignDrAssistantTel() {
        return signDrAssistantTel;
    }

    public void setSignDrAssistantTel(String signDrAssistantTel) throws Exception {
        if(StringUtils.isNotBlank(this.getSignDrAssistantId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = dao.getAppDrUserDao().findByUserId(this.getSignDrAssistantId());
            if(drUser!=null) {
                this.signDrAssistantTel = drUser.getDrTel();
            }
        }
    }

    public String getPatientProvince() {
        return patientProvince;
    }

    public void setPatientProvince(String patientProvince) {
        this.patientProvince = patientProvince;
    }

    public String getPatientCity() {
        return patientCity;
    }

    public void setPatientCity(String patientCity) {
        this.patientCity = patientCity;
    }

    public String getPatientArea() {
        return patientArea;
    }

    public void setPatientArea(String patientArea) {
        this.patientArea = patientArea;
    }

    public String getPatientStreet() {
        return patientStreet;
    }

    public void setPatientStreet(String patientStreet) {
        this.patientStreet = patientStreet;
    }

    public String[] getPersGroup() {
        return persGroup;
    }

    public void setPersGroup(String[] persGroup) {
        this.persGroup = persGroup;
    }

    public String[] getsJjType() {
        return sJjType;
    }

    public void setsJjType(String[] sJjType) {
        this.sJjType = sJjType;
    }

    public String getSigntext() {
        return signtext;
    }

    public void setSigntext(String signtext) {
        this.signtext = signtext;
    }

    public String getSignpackageid() {
        return signpackageid;
    }

    public void setSignpackageid(String signpackageid) {
        this.signpackageid = signpackageid;
    }
}
