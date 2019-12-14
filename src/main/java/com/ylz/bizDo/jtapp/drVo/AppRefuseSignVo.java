package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * 拒绝签约
 */
public class AppRefuseSignVo extends CommConditionVo {

    private String idno;//拒绝签约用户身份证
    private String name;//姓名
    private String card;//社保卡号
    private String drId;//医生id
    private String teamId;//团队id
    private String id;

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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
