package com.ylz.bizDo.app.vo;

/**
 * Created by WangCheng on 2018/07/30.
 */
public class AppArchivingCardPeopleVo {

    private String ptidno;//身份证

    private String dissolutionType;//状态（有带4的话是已经解约未续签的解约单信息)

    public String getPtidno() {
        return ptidno;
    }

    public void setPtidno(String ptidno) {
        this.ptidno = ptidno;
    }

    public String getDissolutionType() {
        return dissolutionType;
    }

    public void setDissolutionType(String dissolutionType) {
        this.dissolutionType = dissolutionType;
    }
}
