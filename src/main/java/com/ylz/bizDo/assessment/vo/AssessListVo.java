package com.ylz.bizDo.assessment.vo;

import com.ylz.bizDo.assessment.po.Assessment;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by zms on 2018/6/13.
 * 考核列表展示
 */
public class AssessListVo {

    private String signId;// 签约单ID
    private String signNum;// 签约编号
    private String idno;//身份证号
    private String name;// 居民姓名
    private String sex;// 性别
    private String age;// 年龄
    private String patientTel;// 居民电话
    private String groupName;// 所属人群
    private String patientId;// 居民ID
    private String drId;// 医生ID
    private String teamId;// 团队ID
    private String signState;// 签约单状态
    private String signDate;// 签约日期
    private String signFromDate;// 签约协议开始时间
    private String signToDate;// 签约协议截止时间
    private String isExpire;// 协议是否到期
    private String assessState;// 是否已考核

    private String assessmentId;// 考核记录ID
    private Double totalScorePre;// 审核前考核总成绩
    private Double totalScoreAft;// 审核后考核总成绩
    private Double totalScore;// 考核总成绩
    private Integer detailNum;// 考核项总数量
    private Integer finishNum;// 考核完成的考核项数量
    private String isFinish;// 考核是否完成
    private String rating;// 考核评级
    private String isReview;// 是否已审核


    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getSignNum() {
        return signNum;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if (StringUtils.isNotBlank(sex)) {
            if ("1".equals(sex)) {
                sex = "男";
            } else if ("2".equals(sex)) {
                sex = "女";
            } else {
                sex = "未知";
            }
        } else {
            sex = "未知";
        }
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) throws Exception {
        Map<String, Object> idNos = new HashMap<>();
        if (StringUtils.isNotBlank(this.getIdno())) {
            if (this.getIdno().length() == 18) {
                idNos = CardUtil.getCarInfo(this.getIdno());
            } else {
                boolean idno = isNumeric(this.getIdno());
                if (idno) {
                    idNos = CardUtil.getCarInfo15W(this.getIdno());
                }
            }
            String birthday = idNos.get("birthday").toString();
            age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
        }
        this.age = age;
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        if (StringUtils.isNotBlank(groupName)) {
            String[] groupNames = groupName.split(",");
            for (String group : groupNames) {
                if ("1".equals(group) || "9".equals(group) || "99".equals(group)) {
                    if (StringUtils.isNotBlank(this.groupName) && !this.groupName.contains("健康人群")) {
                        this.groupName += ",健康人群";
                    } else {
                        this.groupName = "健康人群";
                    }
                } else if ("2".equals(group)) {
                    if (StringUtils.isNotBlank(this.groupName) && !this.groupName.contains("儿童")) {
                        this.groupName += ",儿童";
                    } else {
                        this.groupName = "儿童";
                    }
                } else if ("3".equals(group)) {
                    if (StringUtils.isNotBlank(this.groupName) && !this.groupName.contains("孕产妇")) {
                        this.groupName += ",孕产妇";
                    } else {
                        this.groupName = "孕产妇";
                    }
                } else if ("4".equals(group)) {
                    if (StringUtils.isNotBlank(this.groupName) && !this.groupName.contains("老年人")) {
                        this.groupName += ",老年人";
                    } else {
                        this.groupName = "老年人";
                    }
                } else if ("5".equals(group) || "6".equals(group)) {
                    if (StringUtils.isNotBlank(this.groupName) && !this.groupName.contains("慢性病")) {
                        this.groupName += ",慢性病";
                    } else {
                        this.groupName = "慢性病";
                    }
                } else if ("7".equals(group)) {
                    if (StringUtils.isNotBlank(this.groupName) && !this.groupName.contains("严重精神障碍")) {
                        this.groupName += ",严重精神障碍";
                    } else {
                        this.groupName = "严重精神障碍";
                    }
                } else if ("8".equals(group)) {
                    if (StringUtils.isNotBlank(this.groupName) && !this.groupName.contains("结核病")) {
                        this.groupName += ",结核病";
                    } else {
                        this.groupName = "结核病";
                    }
                }
            }
            // 一个人不可能即是健康人群又是其他人群（需求是这么定的）
            String[] groups = this.getGroupName().split(",");
            if (groups.length >= 2) {
                boolean isOther = false;
                boolean isHealth = false;
                for (String group : groups) {
                    if ("健康人群".equals(group)) {
                        isHealth = true;
                    } else {
                        isOther = true;
                    }
                }
                if (isOther && isHealth) {
                    this.groupName = "";// 清空
                    for (String group : groups) {
                        if (!"健康人群".equals(group)) {
                            if (StringUtils.isNotBlank(this.groupName)) {
                                this.groupName += "," + group;
                            } else {
                                this.groupName = group;
                            }
                        }
                    }
                }
            }
        } else {
            this.groupName = "未知";
        }
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
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
        if (signFromDate != null) {
            this.signFromDate = ExtendDate.getYMD(signFromDate);
        }
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(Timestamp signToDate) {
        if ("0".equals(this.signState) || "2".equals(this.signState)) {
            Calendar nowC = Calendar.getInstance();
            Calendar signToDateC = Calendar.getInstance();
            signToDateC.setTime(new Date(signToDate.getTime()));
            signToDateC.set(Calendar.HOUR_OF_DAY, 23);
            signToDateC.set(Calendar.MINUTE, 59);
            signToDateC.set(Calendar.SECOND, 59);
            if (ExtendDate.compare(nowC, signToDateC) < 2) {
                this.isExpire = "是";
            } else {
                this.isExpire = "否";
            }
        } else {
            this.isExpire = "是";
        }

        this.signToDate = ExtendDate.getYMD(signToDate);
    }

    public String getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(String isExpire) {
        this.isExpire = isExpire;
    }

    public String getAssessState() {
        return assessState;
    }

    public void setAssessState(String assessState) throws Exception {
        if ("1".equals(assessState)) {// 已考核
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Assessment paramObj = new Assessment();
            paramObj.setSignId(this.signId);
            Assessment assessment = dao.getAssessmentDao().findAssessment(paramObj);
            if (assessment != null) {
                // 是否考核完成
                /*if (assessment.getFinishNum() != null && assessment.getDetailNum() != null) {
                    if (assessment.getFinishNum() - assessment.getDetailNum() >= 0) {
                        this.isFinish = "是";
                    } else {
                        this.isFinish = "否";
                    }
                } else {
                    this.isFinish = "否";
                }*/
                if ("1".equals(assessment.getIsFinish())) {
                    this.isFinish = "是";
                } else {
                    this.isFinish = "否";
                }
                // 是否审核
                if ("0".equals(assessment.getIsReview()) || StringUtils.isBlank(assessment.getIsReview())) {
                    this.isReview = "否";
                    // 当前得分
                    this.totalScore = assessment.getTotalScorePre().doubleValue();
                    // 当前评级
                    if (assessment.getTotalScorePre().compareTo(new BigDecimal(60)) < 0) {
                        this.rating = "不合格";
                    } else if (assessment.getTotalScorePre().compareTo(new BigDecimal(60)) == 0) {
                        this.rating = "合格";
                    } else if (assessment.getTotalScorePre().compareTo(new BigDecimal(80)) <= 0) {
                        this.rating = "良";
                    } else if (assessment.getTotalScorePre().compareTo(new BigDecimal(80)) > 0) {
                        this.rating = "优";
                    } else {
                        this.rating = "未知";
                    }
                } else {
                    if (assessment.getTotalScoreAft().compareTo(assessment.getTotalScorePre()) < 0) {
                        this.isReview = "是（审核中）";
                    } else {
                        this.isReview = "是";
                    }
                    // 当前得分
                    this.totalScore = assessment.getTotalScoreAft().doubleValue();
                    // 当前评级
                    if (assessment.getTotalScoreAft().compareTo(new BigDecimal(60)) < 0) {
                        this.rating = "不合格";
                    } else if (assessment.getTotalScoreAft().compareTo(new BigDecimal(60)) == 0) {
                        this.rating = "合格";
                    } else if (assessment.getTotalScoreAft().compareTo(new BigDecimal(80)) <= 0) {
                        this.rating = "良";
                    } else if (assessment.getTotalScoreAft().compareTo(new BigDecimal(80)) > 0) {
                        this.rating = "优";
                    } else {
                        this.rating = "未知";
                    }
                }
            }
        }
        this.assessState = assessState;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Double getTotalScorePre() {
        return totalScorePre;
    }

    public void setTotalScorePre(Double totalScorePre) {
        this.totalScorePre = totalScorePre;
    }

    public Double getTotalScoreAft() {
        return totalScoreAft;
    }

    public void setTotalScoreAft(Double totalScoreAft) {
        this.totalScoreAft = totalScoreAft;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
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
        /*if (finishNum != null && this.detailNum != null) {
            if (finishNum.intValue() - detailNum.intValue() >= 0) {
                this.isFinish = "是";
            } else {
                this.isFinish = "否";
            }
        } else {
            this.isFinish = "否";
        }*/
        this.finishNum = finishNum;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        if ("1".equals(isFinish)) {
            this.isFinish = "是";
        } else {
            this.isFinish = "否";
        }
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIsReview() {
        return isReview;
    }

    public void setIsReview(String isReview) {
        if ("0".equals(isReview) || StringUtils.isBlank(isReview)) {
            this.isReview = "否";
            this.totalScore = this.totalScorePre;
            if (totalScorePre != null) {
                if (totalScorePre.intValue() < 60) {
                    this.rating = "不合格";
                } else if (totalScorePre.intValue() == 60) {
                    this.rating = "合格";
                } else if (totalScorePre.intValue() <= 80) {
                    this.rating = "良";
                } else {
                    this.rating = "优";
                }
            }
        } else {
            if (new BigDecimal(this.totalScoreAft).compareTo(new BigDecimal(this.totalScorePre)) < 0) {
                this.isReview = "是（审核中）";
            } else {
                this.isReview = "是";
            }
            this.totalScore = this.totalScoreAft;
            if (totalScoreAft != null) {
                if (totalScoreAft.intValue() < 60) {
                    this.rating = "不合格";
                } else if (totalScoreAft.intValue() == 60) {
                    this.rating = "合格";
                } else if (totalScoreAft.intValue() <= 80) {
                    this.rating = "良";
                } else {
                    this.rating = "优";
                }
            }
        }
    }
}
