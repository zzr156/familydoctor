package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.util.ExtendDate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**notice副表
 * Created by zzl on 2017/9/24.
 */
@Entity
@Table(name="APP_NOTICE_RECEIVE")
public class AppNoticeFb  extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name="NOTICE_TYPE", length=10)
    private String noticeType;//消息类型
    @Column(name="NOTICE_TITLE", length=20)
    private String noticeTitle;//消息标题
    @Column(name="NOTICE_CONTENT")
    private String noticeContent;//消息内容
    @Column(name="NOTICE_CREATE_TIME", length=19)
    private Calendar noticeCreateTime;//消息时间
    @Column(name="NOTICE_CREATE_PEOPLE", length=36)
    private String noticeCreatePeople;//消息创建者
    @Column(name="NOTICE_READ", length=10)
    private String noticeRead;//是否阅读
    @Column(name="NOTICE_RECEIVE_PEOPLE", length=36)
    private String noticeReceivePeople;//消息接收者
    @Column(name="NOTICE_FOREIGN", length=36)
    private String noticeForeign;//对应主键
    @Column(name="NOTICE_ID",length = 36)
    private String noticeId;//消息主键

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Calendar getNoticeCreateTime() {
        return noticeCreateTime;
    }

    public void setNoticeCreateTime(Calendar noticeCreateTime) {
        this.noticeCreateTime = noticeCreateTime;
    }

    public String getNoticeCreatePeople() {
        return noticeCreatePeople;
    }

    public void setNoticeCreatePeople(String noticeCreatePeople) {
        this.noticeCreatePeople = noticeCreatePeople;
    }

    public String getNoticeRead() {
        return noticeRead;
    }

    public void setNoticeRead(String noticeRead) {
        this.noticeRead = noticeRead;
    }

    public String getNoticeReceivePeople() {
        return noticeReceivePeople;
    }

    public void setNoticeReceivePeople(String noticeReceivePeople) {
        this.noticeReceivePeople = noticeReceivePeople;
    }

    public String getNoticeForeign() {
        return noticeForeign;
    }

    public void setNoticeForeign(String noticeForeign) {
        this.noticeForeign = noticeForeign;
    }
    public String getStrTime(){
        if(this.getNoticeCreateTime()!=null){
            return ExtendDate.getYMD_h_m_s(this.getNoticeCreateTime());
        }
        return "";
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
}
