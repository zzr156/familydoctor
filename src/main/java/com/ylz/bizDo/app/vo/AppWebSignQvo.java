package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * 签约查询.
 */
public class AppWebSignQvo extends CommConditionVo {
    private String ptidno;
    private String ptsscno;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPtidno() {
        return ptidno;
    }

    public void setPtidno(String ptidno) {
        this.ptidno = ptidno;
    }

    public String getPtsscno() {
        return ptsscno;
    }

    public void setPtsscno(String ptsscno) {
        this.ptsscno = ptsscno;
    }
}
