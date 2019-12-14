package com.ylz.bizDo.jtapp.basicHealthEntity.Referral;

/**
 * Created by asus on 2017/10/31.
 */
public class ReferralOrg {

    private String orgid;//医院主键
    private String orgname;//医院名称
    private String registerdate;//转诊时间

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(String registerdate) {
        this.registerdate = registerdate;
    }
}
