package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AppSignFormVo {

    /**
     * 表字段对应的属性
     */
    private String id;// 主键ID
    private String signNum;// 签约编号
    private String signPatientId;// 签约居民ID
    private Integer signPatientAge;// 签约居民年龄
    private String signPatientGender;// 签约居民性别
    private String signPatientIdNo;// 签约居民身份证
    private String signDate;// 签约时间
    private String signFromDate;// 协议开始时间
    private String signToDate;// 协议结束时间


    /**
     * 新增属性
     */
    private String patientName;// 签约居民姓名
    private String groupName;// 服务人群名称（属于多个人群时用逗号分隔）
    private String sersmName;// 套餐名称
    private String packageName;// 服务包名称
    private String assessId;// 考核ID
    private String hospId;// 机构ID
    private String drId;// 医生ID
    private String teamId;// 团队ID
    private Double totalScore;// 考核得分
    private String rating;// 考核评级
    private Integer detailNum;// 总考核项数量
    private Integer finishNum;// 考核完成数量
    private String isFinish;// 考核是否完成


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignNum() {
        return signNum;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }

    public String getSignPatientId() {
        return signPatientId;
    }

    public void setSignPatientId(String signPatientId) {
        this.signPatientId = signPatientId;
    }

    public Integer getSignPatientAge() {
        return signPatientAge;
    }

    public void setSignPatientAge(Integer signPatientAge) {
        this.signPatientAge = signPatientAge;
    }

    public String getSignPatientGender() {
        return signPatientGender;
    }

    public void setSignPatientGender(String signPatientGender) {
        if (StringUtils.isNotBlank(signPatientGender)) {
            if ("1".equals(signPatientGender)) {
                this.signPatientGender = "男";
            } else if ("2".equals(signPatientGender)) {
                this.signPatientGender = "女";
            } else {
                this.signPatientGender = "未知";
            }
        } else {
            this.signPatientGender = "未知";
        }
    }

    public String getSignPatientIdNo() {
        return signPatientIdNo;
    }

    public void setSignPatientIdNo(String signPatientIdNo) {
        this.signPatientIdNo = signPatientIdNo;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(Timestamp signDate) {
        this.signDate = ExtendDate.getYMD(signDate);
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(Timestamp signFromDate) {
        this.signFromDate = ExtendDate.getYMD(signFromDate);
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(Timestamp signToDate) {
        this.signToDate = ExtendDate.getYMD(signToDate);
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSersmName() {
        return sersmName;
    }

    public void setSersmName(String sersmName) {
        /*if (StringUtils.isNotBlank(sersmName)) {// 去重
            String[] sersmNames = sersmName.split(",");
            for (String sersm : sersmNames) {
                if (StringUtils.isNotBlank(this.sersmName)) {
                    if (!this.sersmName.contains(sersm)) {
                        this.sersmName += "," + sersm;
                    }
                } else {
                    this.sersmName = sersm;
                }
            }
        } else {
            this.sersmName = "";
        }*/
        this.sersmName = sersmName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        /*if (StringUtils.isNotBlank(packageName)) {// 去重
            String[] packageNames = packageName.split(",");
            for (String pkgName : packageNames) {
                if (StringUtils.isNotBlank(this.packageName)) {
                    if (!this.packageName.contains(pkgName)) {
                        this.packageName += "," + pkgName;
                    }
                } else {
                    this.packageName = pkgName;
                }
            }
        } else {
            this.packageName = "";
        }*/
        this.packageName = packageName;
    }

    public String getAssessId() {
        return assessId;
    }

    public void setAssessId(String assessId) {
        this.assessId = assessId;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        if (totalScore != null) {
            if (new BigDecimal(totalScore).compareTo(new BigDecimal(60)) < 0) {
                this.rating = "不合格";
            } else if (new BigDecimal(totalScore).compareTo(new BigDecimal(60)) == 0) {
                this.rating = "合格";
            } else if (new BigDecimal(totalScore).compareTo(new BigDecimal(80)) <= 0) {
                this.rating = "良";
            } else if (new BigDecimal(totalScore).compareTo(new BigDecimal(80)) > 0) {
                this.rating = "优";
            } else {
                this.rating = "未知";
            }
        }
        this.totalScore = totalScore;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getDetailNum() {
        return detailNum;
    }

    public void setDetailNum(Integer detailNum) {
        this.detailNum = detailNum;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        if ("1".equals(isFinish)) {
            this.isFinish = "已完成";
        } else {
            this.isFinish = "未完成";
        }
    }
}
