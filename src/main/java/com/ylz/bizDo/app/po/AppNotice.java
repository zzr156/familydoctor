package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.util.ExtendDate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;


/**
 * AppNotice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="APP_NOTICE")
public class AppNotice  extends BasePO {


    // Fields    
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
     private String id;//主键
     @Column(name="NOTICE_TYPE", length=10)
     private String noticeType;//消息类型
     @Column(name="NOTICE_TITLE", length=20)
     private String noticeTitle;//消息标题
     @Column(name="NOTICE_CONTENT", length=65535)
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
    @Column(name="NOTICE_MTYPE",length = 10)
    private String noticeMtype;//小类类别


    /** default constructor */
    public AppNotice() {
    }

	/** minimal constructor */
    public AppNotice(String id) {
        this.id = id;
    }

    /** full constructor */
    public AppNotice(String id, String noticeType, String noticeTitle, String noticeContent, Calendar noticeCreateTime,
                     String noticeCreatePeople, String noticeRead, String noticeReceivePeople,String noticeForeign,String noticeMtype) {
    	this.id = id;
        this.noticeType = noticeType;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeCreateTime = noticeCreateTime;
        this.noticeCreatePeople = noticeCreatePeople;
        this.noticeRead = noticeRead;
        this.noticeReceivePeople = noticeReceivePeople;
        this.noticeForeign = noticeForeign;
        this.noticeMtype = noticeMtype;
    }

   
    // Property accessors
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getNoticeType() {
        return this.noticeType;
    }
    
	public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeTitle() {
        return this.noticeTitle;
    }
    
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return this.noticeContent;
    }
    
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Calendar getNoticeCreateTime() {
        return this.noticeCreateTime;
    }
    
    public void setNoticeCreateTime(Calendar noticeCreateTime) {
        this.noticeCreateTime = noticeCreateTime;
    }

    public String getNoticeCreatePeople() {
        return this.noticeCreatePeople;
    }
    
    public void setNoticeCreatePeople(String noticeCreatePeople) {
        this.noticeCreatePeople = noticeCreatePeople;
    }

    public String getNoticeRead() {
        return this.noticeRead;
    }
    
    public void setNoticeRead(String noticeRead) {
        this.noticeRead = noticeRead;
    }

    public String getNoticeReceivePeople() {
        return this.noticeReceivePeople;
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

    public String getNoticeMtype() {
        return noticeMtype;
    }

    public void setNoticeMtype(String noticeMtype) {
        this.noticeMtype = noticeMtype;
    }
}