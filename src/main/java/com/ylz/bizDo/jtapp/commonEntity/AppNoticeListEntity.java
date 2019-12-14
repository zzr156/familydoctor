package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignsRecordTable;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.NoticesType;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by asus on 2017/6/20.
 */
public class AppNoticeListEntity {
    private String id;//公告主键
    private String noticeTitle;//标题
    private String noticeContent;//内容
    private String noticeCreateName;//创建人
    private String noticeCreateTime;//创建时间
    private String noticeReceiveName;//接收人
    private String noticeForEginKey;//对应主键
    private String noticeType;//类型
    private String noticeState;//阅读状态（0未读 1已读）
    private String receiveId;//接收者id
    private String createId;//创建者id
    private String noticeMtype;//小类分类
    private String srtState;//是否发送过提醒

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        this.noticeCreateTime = ExtendDate.getYMD_h_m_s(noticeCreateTime);
    }

    public String getNoticeForEginKey() {
        return noticeForEginKey;
    }

    public void setNoticeForEginKey(String noticeForEginKey) {
        this.noticeForEginKey = noticeForEginKey;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeCreateName() {
        return noticeCreateName;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public void setNoticeCreateName(String noticeCreateName) {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        if(StringUtils.isNotBlank(noticeCreateName)){
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class, noticeCreateName);
            if(drUser != null){
                this.noticeCreateName = drUser.getDrName();
            }else{
                AppPatientUser patientUser = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class, noticeCreateName);
                if(patientUser != null){
                    this.noticeCreateName = patientUser.getPatientName();
                }else{
                    this.noticeCreateName = noticeCreateName;
                }
            }
        }else {
            this.noticeCreateName = noticeCreateName;
        }
    }

    public String getNoticeReceiveName() {
        return noticeReceiveName;
    }

    public void setNoticeReceiveName(String noticeReceiveName) {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        if(StringUtils.isNotBlank(noticeReceiveName)){
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class, noticeReceiveName);
            if(drUser != null){
                this.noticeReceiveName = drUser.getDrName();
            }else{
                AppPatientUser patientUser = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class, noticeReceiveName);
                if(patientUser != null){
                    this.noticeReceiveName = patientUser.getPatientName();
                }else{
                    this.noticeReceiveName = noticeReceiveName;
                }
            }
        }else {
            this.noticeReceiveName = noticeReceiveName;
        }
    }

    public String getNoticeState() {
        return noticeState;
    }

    public void setNoticeState(String noticeState) {
        this.noticeState = noticeState;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getNoticeMtype() {
        return noticeMtype;
    }

    public void setNoticeMtype(String noticeMtype) {
        this.noticeMtype = noticeMtype;
    }

    public String getSrtState() {
        return srtState;
    }

    public void setSrtState(String srtState) {
        if(NoticesType.TZZSYJ.getValue().equals(this.getNoticeType()) && "1".equals(this.getNoticeMtype())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            List<AppSignsRecordTable> list = dao.getServiceDo().loadByPk(AppSignsRecordTable.class,"srtCode",this.getNoticeForEginKey());
            if(list!=null && list.size()>0){
                boolean flag = true;
                for(AppSignsRecordTable ll:list){
                    if("0".equals(ll.getSrtState())){
                        flag=false;
                    }
                }
                if(flag){
                    srtState = "1";
                }else{
                    srtState = "0";
                }
            }
        }
        this.srtState = srtState;
    }
}
