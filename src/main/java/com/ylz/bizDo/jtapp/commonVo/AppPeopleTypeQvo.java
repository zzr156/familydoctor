package com.ylz.bizDo.jtapp.commonVo;

/**
 * Created by zzl on 2017/8/7.
 */
public class AppPeopleTypeQvo {
    private String findType;//3服务人群、1健康情况、2疾病类型
    private String drId;//医生id
    private String teamId;//团队id
    private String perType;//履约类型
    private String lyNum;//履约编号

    public String getFindType() {
        return findType;
    }

    public void setFindType(String findType) {
        this.findType = findType;
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

    public String getPerType() {
        return perType;
    }

    public void setPerType(String perType) {
        this.perType = perType;
    }

    public String getLyNum() {
        return lyNum;
    }

    public void setLyNum(String lyNum) {
        this.lyNum = lyNum;
    }
}
