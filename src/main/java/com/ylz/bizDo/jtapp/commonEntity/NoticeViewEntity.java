package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.packcommon.common.util.ExtendDate;

import java.sql.Timestamp;

/**
 * Created by asus on 2017/6/21.
 */
public class NoticeViewEntity {
    private String noticeTitle;//标题
    private String noticeCreateTime;//创建时间
    private String noticeContent;//内容

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeCreateTime() {
        return noticeCreateTime;
    }

    public void setNoticeCreateTime(Timestamp noticeCreateTime) {
        this.noticeCreateTime = ExtendDate.getYMD_h_m(noticeCreateTime);
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
}
