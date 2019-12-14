package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by zzl on 2018/8/22.
 */
public class AppArchivintTeamEntity {
    private String teamId;//团队id
    private String teamName;//团队名称
    private String firstDrId;//第一责任人id
    private String firstDrName;//第一责任人姓名
    private String memberName;//成员名称
    private String memberCount;//成员人数
    private String count;//签约数
    private String drTel;//医生电话

    private String managePlainCount;//一般人口数
    private String manageChildCount;//0-6岁儿童
    private String manageMaternalCount;//孕产妇
    private String manageOldPeopleCount;//老年人
    private String manageBloodCount;//高血压
    private String manageDiabetesCount;//糖尿病
    private String managePsychosisCount;//严重精神病患者
    private String managePhthisisCount;//结核病

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
        this.teamName = teamName;
    }

    public String getFirstDrId() {
        return firstDrId;
    }

    public void setFirstDrId(String firstDrId) {
        this.firstDrId = firstDrId;
    }

    public String getFirstDrName() {
        return firstDrName;
    }

    public void setFirstDrName(String firstDrName) {
        if(StringUtils.isNotBlank(this.getFirstDrId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getFirstDrId());
            if(drUser != null){
                if(StringUtils.isNotBlank(drUser.getDrTel())){
                    firstDrName = drUser.getDrName()+"("+drUser.getDrTel()+")";
                }
            }
        }
        this.firstDrName = firstDrName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        String mDrName = "";
        if(StringUtils.isNotBlank(memberName)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            String[] drIds = memberName.split(",");
            for(String drId:drIds){
                String mTel = "";
                AppDrUser drUser = (AppDrUser) dao.getServiceDo().find(AppDrUser.class,drId);
                if(drUser != null){
                    if(StringUtils.isNotBlank(drUser.getDrTel())){
                        mTel = "("+drUser.getDrTel()+")";
                    }
                    if(StringUtils.isBlank(mDrName)){
                        mDrName = drUser.getDrName()+mTel;
                    }else{
                        mDrName += ","+drUser.getDrName()+mTel;
                    }
                }
            }
        }
        this.memberName = mDrName;
    }

    public String getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(BigInteger memberCount) {
        if(memberCount != null){
            this.memberCount = String.valueOf(memberCount);
        }else{
            this.memberCount = "0";
        }
    }

    public String getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        if(count != null){
            this.count = String.valueOf(count);
        }else{
            this.count = "0";
        }
    }

    public String getDrTel() {
        return drTel;
    }

    public void setDrTel(String drTel) {
        this.drTel = drTel;
    }

    public String getManagePlainCount() {
        return managePlainCount;
    }

    public void setManagePlainCount(String managePlainCount) {
        this.managePlainCount = managePlainCount;
    }

    public String getManageChildCount() {
        return manageChildCount;
    }

    public void setManageChildCount(String manageChildCount) {
        this.manageChildCount = manageChildCount;
    }

    public String getManageMaternalCount() {
        return manageMaternalCount;
    }

    public void setManageMaternalCount(String manageMaternalCount) {
        this.manageMaternalCount = manageMaternalCount;
    }

    public String getManageOldPeopleCount() {
        return manageOldPeopleCount;
    }

    public void setManageOldPeopleCount(String manageOldPeopleCount) {
        this.manageOldPeopleCount = manageOldPeopleCount;
    }

    public String getManageBloodCount() {
        return manageBloodCount;
    }

    public void setManageBloodCount(String manageBloodCount) {
        this.manageBloodCount = manageBloodCount;
    }

    public String getManageDiabetesCount() {
        return manageDiabetesCount;
    }

    public void setManageDiabetesCount(String manageDiabetesCount) {
        this.manageDiabetesCount = manageDiabetesCount;
    }

    public String getManagePsychosisCount() {
        return managePsychosisCount;
    }

    public void setManagePsychosisCount(String managePsychosisCount) {
        this.managePsychosisCount = managePsychosisCount;
    }

    public String getManagePhthisisCount() {
        return managePhthisisCount;
    }

    public void setManagePhthisisCount(String managePhthisisCount) {
        this.managePhthisisCount = managePhthisisCount;
    }
}
