package com.ylz.bizDo.jtapp.patientVo;

/**
 * 判断签约是否上限请求参数
 * Created by zzl on 2018/1/26.
 */
public class AppToplimitQvo {
    private String teamId;//团队id
    private String drId;//医生id
    private String type;//类型(1团队 0医生)
    private String hospId;//医院id

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }
}
