package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/** 中医体质辨识
 * Created by zzl on 2017/8/4.
 */
public class AppTcmQvo extends CommConditionVo {
    private String userId;//患者id
    private String question;//问题编号
    private String result;//答案
    private String drId;//医生id
    private String drName;//医生姓名
    private String teamId;//团队id
    private String userName;//居民姓名
    private String jlId;//答题记录id
    private String type;//1患者 2医生

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJlId() {
        return jlId;
    }

    public void setJlId(String jlId) {
        this.jlId = jlId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
