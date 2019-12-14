package com.ylz.bizDo.jtapp.gaiRuiEntity;

/**
 * Created by zzl on 2019/3/25.
 */
public class GaiRuiDrEntity {
    private String drId;//医生主键
    private String drName;//医生姓名
    private String drAccount;//医生账号
    private String drTel;//医生手机号

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

    public String getDrAccount() {
        return drAccount;
    }

    public void setDrAccount(String drAccount) {
        this.drAccount = drAccount;
    }

    public String getDrTel() {
        return drTel;
    }

    public void setDrTel(String drTel) {
        this.drTel = drTel;
    }
}
