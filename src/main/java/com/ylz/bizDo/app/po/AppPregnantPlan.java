package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 孕产妇保健计划表
 */
@Entity
@Table(name = "APP_PREGNANT_PLAN")
public class AppPregnantPlan extends BasePO {
    // Fields
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "PP_USER_ID", length =36 )
    private String ppUserId;//孕产妇 用户id
    @Column(name = "PP_DATE_TYPE", length =6 )
    private String ppDateType;//1预产日期， 2末次月经日期
    @Column(name = "PP_USER_DATE" )
    private Calendar ppUserDate;//用户输入日期
    @Column(name = "PP_PLAN_DATE" )
    private Calendar ppPlanDate;//保健计划日期
    @Column(name = "PP_PLAN_TITLE", length = 50)
    private String ppPlanTitle;//保健计划周次
    @Column(name = "PP_STATE", length = 6)
    private String ppState;//状态 0未体检 1已体检
    @Column(name = "PP_REMINGD_DAY",length = 10)
    private String ppRemindDay;//提前提醒天数
    @Column(name = "PP_ENABLE_ALERT", length = 6)
    private String ppEnableAlert;//是否开启提醒 1开启 0禁用
    @Column(name = "PP_READ_STATE",length = 10)
    private String ppReadState="0";//是否已发送通知0未发送 1已发送

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPpUserId() {
        return ppUserId;
    }

    public void setPpUserId(String ppUserId) {
        this.ppUserId = ppUserId;
    }

    public String getPpDateType() {
        return ppDateType;
    }

    public void setPpDateType(String ppDateType) {
        this.ppDateType = ppDateType;
    }

    public Calendar getPpUserDate() {
        return ppUserDate;
    }

    public void setPpUserDate(Calendar ppUserDate) {
        this.ppUserDate = ppUserDate;
    }

    public Calendar getPpPlanDate() {
        return ppPlanDate;
    }

    public void setPpPlanDate(Calendar ppPlanDate) {
        this.ppPlanDate = ppPlanDate;
    }

    public String getPpPlanTitle() {
        return ppPlanTitle;
    }

    public void setPpPlanTitle(String ppPlanTitle) {
        this.ppPlanTitle = ppPlanTitle;
    }

    public String getPpState() {
        return ppState;
    }

    public void setPpState(String ppState) {
        this.ppState = ppState;
    }

    public String getPpRemindDay() {
        return ppRemindDay;
    }

    public void setPpRemindDay(String ppRemindDay) {
        this.ppRemindDay = ppRemindDay;
    }

    public String getPpEnableAlert() {
        return ppEnableAlert;
    }

    public void setPpEnableAlert(String ppEnableAlert) {
        this.ppEnableAlert = ppEnableAlert;
    }

    public String getPpReadState() {
        return ppReadState;
    }

    public void setPpReadState(String ppReadState) {
        this.ppReadState = ppReadState;
    }
}
