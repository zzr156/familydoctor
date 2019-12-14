package com.ylz.bizDo.jtapp.drVo;

/**咨询记录请求参数
 * Created by zzl on 2017/10/25.
 */
public class AppConsultRecordQvo {
    private String drId;
    private String patientId;
    private String teamId;

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
}
