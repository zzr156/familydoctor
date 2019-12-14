package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**儿童体检计划表
 * Created by zzl on 2017/6/22.
 */
@Entity
@Table(name = "APP_CHILD_HEALTH_PLAN ")
public class AppChildHealthPlan extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "CHP_USER_ID",length = 36)
    private String chpUserId;//用户id
    @Column(name = "CHP_CHILD_USER_ID",length = 36)
    private String chpChildUserId;//儿童主键
    @Column(name = "CHP_CHILD_NAME",length = 36)
    private String chpChildName;//儿童姓名
    @Column(name = "CHP_BIRTHDAY")
    private Calendar chpBirthDay;//出生年月日
    @Column(name = "CHP_PLAN_DATE")
    private Calendar chpPlanDate;//体检日期
    @Column(name = "CHP_TITLE", length = 200)
    private String chpTitle;//说明
    @Column(name = "CHP_STATE", length = 10)
    private String chpState="0";//状态 0未完成 1完成
    @Column(name = "CHP_TXSTATE",length = 10)
    private String chpTxState="0";//提醒状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChpUserId() {
        return chpUserId;
    }

    public void setChpUserId(String chpUserId) {
        this.chpUserId = chpUserId;
    }

    public Calendar getChpBirthDay() {
        return chpBirthDay;
    }

    public void setChpBirthDay(Calendar chpBirthDay) {
        this.chpBirthDay = chpBirthDay;
    }

    public Calendar getChpPlanDate() {
        return chpPlanDate;
    }

    public void setChpPlanDate(Calendar chpPlanDate) {
        this.chpPlanDate = chpPlanDate;
    }

    public String getChpTitle() {
        return chpTitle;
    }

    public void setChpTitle(String chpTitle) {
        this.chpTitle = chpTitle;
    }

    public String getChpState() {
        return chpState;
    }

    public void setChpState(String chpState) {
        this.chpState = chpState;
    }

    public String getChpTxState() {
        return chpTxState;
    }

    public void setChpTxState(String chpTxState) {
        this.chpTxState = chpTxState;
    }

    public String getChpChildUserId() {
        return chpChildUserId;
    }

    public void setChpChildUserId(String chpChildUserId) {
        this.chpChildUserId = chpChildUserId;
    }

    public String getChpChildName() {
        return chpChildName;
    }

    public void setChpChildName(String chpChildName) {
        this.chpChildName = chpChildName;
    }
}
