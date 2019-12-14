package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by asus on 2017/6/20.
 */
public class AppNoticeCountQvo extends CommConditionVo {
        private String drPatientId;//接受者主键
    private String noticeType;//消息类型
    private String noticdId;//主键

    public String getDrPatientId() {
        return drPatientId;
    }

    public void setDrPatientId(String drPatientId) {
        this.drPatientId = drPatientId;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticdId() {
        return noticdId;
    }

    public void setNoticdId(String noticdId) {
        this.noticdId = noticdId;
    }
}
