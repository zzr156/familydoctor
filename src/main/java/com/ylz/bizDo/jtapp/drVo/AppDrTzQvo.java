package com.ylz.bizDo.jtapp.drVo;

/**体征居民列表请求参数
 * Created by zzl on 2017/11/7.
 */
public class AppDrTzQvo {
    private String drId;//医生id
    private String type;//类型（1模糊查询 2条件查询）
    private String disType;//疾病类型（201高血压 202糖尿病）
    private String patientName;//患者姓名
    private String patientId;//患者id

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

    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) {
        this.disType = disType;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
