package com.ylz.bizDo.plan.Entity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**产后访视记录表
 * Created by zzl on 2017/8/31.
 */
public class AppPostpartumVisitEntity {
    private String id;
    private String postVisitId;//随访外键
    private String postPatientId;//随访患者id
    private String postName;//姓名
    private String postCode;//编号
    private String postVistDate;//随访日期
    private String postChildbirthDate;//分娩日期
    private String postLeveHospitalDate;//出院日期
    private String postAnimalHeat;//体温
    private String postHealthSituation;//一般健康情况
    private String postpSychologicStatus;//一般心理状况
    private String postBloodPressureOne;//血压收缩压
    private String postBloodPressureTwo;//血压舒张压
    private String postBreast;//乳房（1未见异常 2异常）
    private String postBreastContent;//乳房异常内容
    private String postLochia;//恶露（1未见异常 2异常）
    private String postLochiaContent;//恶露异常内容
    private String postUterus;//子宫（1未见异常 2异常）
    private String postUterusContent;//子宫异常内容
    private String postWound;//伤口(1未见异常 2异常)
    private String postWoundContent;//伤口异常内容
    private String postOtherContent;//其他内容
    private String postClassify;//分类（1未见异常 2异常）
    private String postClassifyContent;//分类异常内容
    private String postGuide;//指导（1个人卫生 2心理 3营养 4母乳喂养 5新生儿护理与喂养 6其他）
    private String postGuideContent;//其他指导内容
    private String postReferral;//转诊（1无 2有）
    private String postReferralReason;//转诊原因
    private String postReferralOrg;//转诊机构
    private String postReferralDept;//转诊科室
    private String postNextVisitDate;//下次随访日期
    private String postDoctorId;//随访医生id
    private String postDoctorImage;//随访医生签名
    private String postVisitWay;//随访方式

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostVisitId() {
        return postVisitId;
    }

    public void setPostVisitId(String postVisitId) {
        this.postVisitId = postVisitId;
    }

    public String getPostPatientId() {
        return postPatientId;
    }

    public void setPostPatientId(String postPatientId) {
        this.postPatientId = postPatientId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostVistDate() {
        return postVistDate;
    }

    public void setPostVistDate(String postVistDate) {
        this.postVistDate = postVistDate;
    }

    public String getPostChildbirthDate() {
        return postChildbirthDate;
    }

    public void setPostChildbirthDate(String postChildbirthDate) {
        this.postChildbirthDate = postChildbirthDate;
    }

    public String getPostLeveHospitalDate() {
        return postLeveHospitalDate;
    }

    public void setPostLeveHospitalDate(String postLeveHospitalDate) {
        this.postLeveHospitalDate = postLeveHospitalDate;
    }

    public String getPostAnimalHeat() {
        return postAnimalHeat;
    }

    public void setPostAnimalHeat(String postAnimalHeat) {
        this.postAnimalHeat = postAnimalHeat;
    }

    public String getPostHealthSituation() {
        return postHealthSituation;
    }

    public void setPostHealthSituation(String postHealthSituation) {
        this.postHealthSituation = postHealthSituation;
    }

    public String getPostpSychologicStatus() {
        return postpSychologicStatus;
    }

    public void setPostpSychologicStatus(String postpSychologicStatus) {
        this.postpSychologicStatus = postpSychologicStatus;
    }

    public String getPostBloodPressureOne() {
        return postBloodPressureOne;
    }

    public void setPostBloodPressureOne(String postBloodPressureOne) {
        this.postBloodPressureOne = postBloodPressureOne;
    }

    public String getPostBloodPressureTwo() {
        return postBloodPressureTwo;
    }

    public void setPostBloodPressureTwo(String postBloodPressureTwo) {
        this.postBloodPressureTwo = postBloodPressureTwo;
    }

    public String getPostBreast() {
        return postBreast;
    }

    public void setPostBreast(String postBreast) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(postBreast)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, postBreast);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.postBreast = str;
    }

    public String getPostBreastContent() {
        return postBreastContent;
    }

    public void setPostBreastContent(String postBreastContent) {
        this.postBreastContent = postBreastContent;
    }

    public String getPostLochia() {
        return postLochia;
    }

    public void setPostLochia(String postLochia) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(postLochia)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, postLochia);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.postLochia = str;
    }

    public String getPostLochiaContent() {
        return postLochiaContent;
    }

    public void setPostLochiaContent(String postLochiaContent) {
        this.postLochiaContent = postLochiaContent;
    }

    public String getPostUterus() {
        return postUterus;
    }

    public void setPostUterus(String postUterus) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(postUterus)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, postUterus);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.postUterus = str;
    }

    public String getPostUterusContent() {
        return postUterusContent;
    }

    public void setPostUterusContent(String postUterusContent) {
        this.postUterusContent = postUterusContent;
    }

    public String getPostWound() {
        return postWound;
    }

    public void setPostWound(String postWound) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(postWound)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, postWound);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.postWound = str;
    }

    public String getPostWoundContent() {
        return postWoundContent;
    }

    public void setPostWoundContent(String postWoundContent) {
        this.postWoundContent = postWoundContent;
    }

    public String getPostOtherContent() {
        return postOtherContent;
    }

    public void setPostOtherContent(String postOtherContent) {
        this.postOtherContent = postOtherContent;
    }

    public String getPostClassify() {
        return postClassify;
    }

    public void setPostClassify(String postClassify) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(postClassify)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, postClassify);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.postClassify = str;
    }

    public String getPostClassifyContent() {
        return postClassifyContent;
    }

    public void setPostClassifyContent(String postClassifyContent) {
        this.postClassifyContent = postClassifyContent;
    }

    public String getPostGuide() {
        return postGuide;
    }

    public void setPostGuide(String postGuide) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(postGuide)){
            String[] ss = postGuide.split(";");
            for(String s:ss){
                SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CHFSZD, s);
                if(value!=null){
                    if(StringUtils.isBlank(str)){
                        str = value.getCodeTitle();
                    }else{
                        str +=","+ value.getCodeTitle();
                    }
                }
            }
        }
        this.postGuide = str;
    }

    public String getPostGuideContent() {
        return postGuideContent;
    }

    public void setPostGuideContent(String postGuideContent) {
        this.postGuideContent = postGuideContent;
    }

    public String getPostReferral() {
        return postReferral;
    }

    public void setPostReferral(String postReferral) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(postReferral)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFCOMMON, postReferral);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.postReferral = str;
    }

    public String getPostReferralReason() {
        return postReferralReason;
    }

    public void setPostReferralReason(String postReferralReason) {
        this.postReferralReason = postReferralReason;
    }

    public String getPostReferralOrg() {
        return postReferralOrg;
    }

    public void setPostReferralOrg(String postReferralOrg) {
        this.postReferralOrg = postReferralOrg;
    }

    public String getPostReferralDept() {
        return postReferralDept;
    }

    public void setPostReferralDept(String postReferralDept) {
        this.postReferralDept = postReferralDept;
    }

    public String getPostNextVisitDate() {
        return postNextVisitDate;
    }

    public void setPostNextVisitDate(String postNextVisitDate) {
        this.postNextVisitDate = postNextVisitDate;
    }

    public String getPostDoctorId() {
        return postDoctorId;
    }

    public void setPostDoctorId(String postDoctorId) {
        this.postDoctorId = postDoctorId;
    }

    public String getPostDoctorImage() {
        return postDoctorImage;
    }

    public void setPostDoctorImage(String postDoctorImage) {
        this.postDoctorImage = postDoctorImage;
    }

    public String getPostVisitWay() {
        return postVisitWay;
    }

    public void setPostVisitWay(String postVisitWay) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(postVisitWay)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FOLLOWWAY, postVisitWay);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.postVisitWay = str;
    }

    /**
     * 获取电话号码
     * @return
     */
    public String getPostTel(){
        if(StringUtils.isNotBlank(this.getPostPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser patientUser = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPostPatientId());
            if(patientUser!=null){
                return patientUser.getPatientTel();
            }
        }
        return "";
    }
}
