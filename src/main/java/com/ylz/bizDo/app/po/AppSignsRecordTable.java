package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 体征预警人员消息记录表
 * Created by zzl on 2017/12/1.
 */
@Entity
@Table(name = "APP_SIGNS_RECORD_TABLE")
public class AppSignsRecordTable extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "SRT_PATIENT_ID",length = 36)
    private String srtPatientId;//患者id
    @Column(name = "SRT_CODE",length = 50)
    private String srtCode;//编号
    @Column(name = "SRT_CONTENT")
    private String srtContent;//内容
    @Column(name = "SRT_CREATE_TIME")
    private Calendar srtCreateTime;//创建时间
    @Column(name = "SRT_STATE",length = 10)
    private String srtState="0";//发送状态（0未发送 1已发送）
    @Column(name = "SRT_DAY_NUM",length = 10)
    private String srtDayNum;//天数
    @Column(name = "SRT_DIS_TYPE",length = 10)
    private String srtDisType;//疾病类型
    @Column(name = "SRT_DIS_NAME",length = 50)
    private String srtDisName;//疾病名称
    @Column(name = "SRT_DR_ID",length =36)
    private String srtDrId;//医生id
    @Column(name = "SRT_TEAM_ID",length = 36)
    private String srtTeamId;//团队id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrtPatientId() {
        return srtPatientId;
    }

    public void setSrtPatientId(String srtPatientId) {
        this.srtPatientId = srtPatientId;
    }

    public String getSrtCode() {
        return srtCode;
    }

    public void setSrtCode(String srtCode) {
        this.srtCode = srtCode;
    }

    public String getSrtContent() {
        return srtContent;
    }

    public void setSrtContent(String srtContent) {
        this.srtContent = srtContent;
    }

    public Calendar getSrtCreateTime() {
        return srtCreateTime;
    }

    public void setSrtCreateTime(Calendar srtCreateTime) {
        this.srtCreateTime = srtCreateTime;
    }

    public String getSrtState() {
        return srtState;
    }

    public void setSrtState(String srtState) {
        this.srtState = srtState;
    }

    public String getSrtDayNum() {
        return srtDayNum;
    }

    public void setSrtDayNum(String srtDayNum) {
        this.srtDayNum = srtDayNum;
    }

    public String getSrtDisType() {
        return srtDisType;
    }

    public void setSrtDisType(String srtDisType) {
        this.srtDisType = srtDisType;
    }

    public String getSrtDisName() {
        return srtDisName;
    }

    public void setSrtDisName(String srtDisName) {
        this.srtDisName = srtDisName;
    }

    public String getSrtDrId() {
        return srtDrId;
    }

    public void setSrtDrId(String srtDrId) {
        this.srtDrId = srtDrId;
    }

    public String getSrtTeamId() {
        return srtTeamId;
    }

    public void setSrtTeamId(String srtTeamId) {
        this.srtTeamId = srtTeamId;
    }
    public String getPatientName(){
        if(StringUtils.isNotBlank(this.getSrtPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getSrtPatientId());
            if(user!=null){
                return user.getPatientName();
            }
        }
        return "";
    }
    /**
     * 获取医生姓名
     * @return
     */
    public String getDrName(){
        if(StringUtils.isNotBlank(this.getSrtDrId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getSrtDrId());
            if(drUser!=null){
                return  drUser.getDrName();
            }
        }
        return "";
    }
    /**
     * 获取医生在团队的类型
     * @return
     */
    public String getDrWorkName() throws Exception {
        if(StringUtils.isNotBlank(this.getSrtTeamId())&&StringUtils.isNotBlank(this.getSrtDrId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppTeamMember team = dao.getAppTeamMemberDao().findMemByDrId(this.getSrtDrId(),this.getSrtTeamId());
            if(team!=null){
                return team.getMemWorkTypeName();
            }
        }
        return "";
    }

    public String getStrSrtCreateTime(){
        if(this.getSrtCreateTime()!=null){
            return ExtendDate.getYMD(this.getSrtCreateTime());
        }
        return "";
    }
}
