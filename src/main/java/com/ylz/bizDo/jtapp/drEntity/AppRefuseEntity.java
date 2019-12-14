package com.ylz.bizDo.jtapp.drEntity;

/**
 * Created by lintingjie on 2017/7/1.
 */
public class AppRefuseEntity {

    private String id;//拒管表id
    private String idno;//拒管居民身份证
    private String name;//名字
    private String card;//社保卡号
    private String time;//拒管时间
    private String drId;
    private String teamId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
