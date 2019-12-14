package com.ylz.bizDo.plan.Entity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by lintingjie on 2017/7/26.
 */
public class FollowInfo {

    private String followId;//随访id
    private String followType;//随访类型，高血压等
    private String visitThisType;//此次随访分类 控制满意等
    private String referral;//转诊情况
    private String followNum;//对此类型的第几次随访
    private String patientName;//患者姓名
    private String patientGender;//患者性别
    private String patientAge;//患者年龄

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;

    }

    public String getFollowTypeName() throws Exception{
        if(StringUtils.isNotBlank(this.getFollowType())) {
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode code = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_RESIDENTMANGE,this.getFollowType());
            if(code!=null){
                return code.getCodeTitle();
            }
        }
        return "";
    }

    public String getVisitThisType() {
        return visitThisType;
    }

    public void setVisitThisType(String visitThisType) throws Exception {
        if(StringUtils.isNotBlank(visitThisType)) {
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode code = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CCFFFL,visitThisType);
            if(code!=null){
                this.visitThisType = code.getCodeTitle();
            }
        }
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getFollowNum() {
        return followNum;
    }

    public void setFollowNum(String followNum) {
        this.followNum = followNum;
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

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        try{
            Map<String,Object> idNos;
            if(patientAge.length() == 18){
                idNos = CardUtil.getCarInfo(patientAge);
            }else{
                idNos = CardUtil.getCarInfo15W(patientAge);
            }
            String birthday = idNos.get("birthday").toString();
            String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
            this.patientAge = age;
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
