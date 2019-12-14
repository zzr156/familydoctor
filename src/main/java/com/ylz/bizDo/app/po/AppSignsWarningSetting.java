package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**医生设置所管居民未更新体征数据的预警时间表
 * Created by zzl on 2017/11/2.
 */
@Entity
@Table(name = "APP_SIGNS_WARNING_SETTING")
public class AppSignsWarningSetting extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "SWS_CREATE_ID",length =36 )
    private String swsCreateId;//创建者id
    @Column(name = "SWS_USER_ID",length = 36)
    private String swsUserId;//用户id
    @Column(name = "SWS_CREATE_TIME")
    private Calendar swsCreateTime;//创建时间
    @Column(name = "SWS_RED_SET",length = 10)
    private String swsRedSet = "0";//红级预警设置
    @Column(name = "SWS_YELLOW_SET",length = 10)
    private String swsYellowSet = "0";//黄级预警设置
    @Column(name = "SWS_GREEN_SET",length = 10)
    private String swsGreenSet = "0";//绿级预警设置
    @Column(name = "SWS_OPEN_STATE",length = 10)
    private String swsOpenState ="0";//是否开启（0否 1是）
    @Column(name = "SWS_FIRST_SET",length = 10)
    private String swsFirstSet;//初始设置
    @Column(name = "SWS_DIS_TYPE",length = 10)
    private String swsDisType;//疾病类型
    @Column(name = "SWS_TYPE",length = 10)
    private String swsType="0";//通用设置（1为通用设置）
    @Column(name = "SWS_RED_SWITCH",length = 10)
    private String swsRedSwitch="0";//红标开关
    @Column(name = "SWS_YELLOW_SWITCH",length = 10)
    private String swsYellowSwitch="0";//黄标开关
    @Column(name = "SWS_GREEN_SWITCH",length = 10)
    private String swsGreenSwitch="0";//绿标开关
    @Column(name = "SWS_GRAY_SET",length = 10)
    private String swsGraySet = "0";//灰级预警设置
    @Column(name = "SWS_GRAY_SWITCH",length = 10)
    private String swsGraySwitch;//灰标开关

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSwsCreateId() {
        return swsCreateId;
    }

    public void setSwsCreateId(String swsCreateId) {
        this.swsCreateId = swsCreateId;
    }

    public String getSwsUserId() {
        return swsUserId;
    }

    public void setSwsUserId(String swsUserId) {
        this.swsUserId = swsUserId;
    }

    public Calendar getSwsCreateTime() {
        return swsCreateTime;
    }

    public void setSwsCreateTime(Calendar swsCreateTime) {
        this.swsCreateTime = swsCreateTime;
    }

    public String getSwsRedSet() {
        return swsRedSet;
    }

    public void setSwsRedSet(String swsRedSet) {
        this.swsRedSet = swsRedSet;
    }

    public String getSwsYellowSet() {
        return swsYellowSet;
    }

    public void setSwsYellowSet(String swsYellowSet) {
        this.swsYellowSet = swsYellowSet;
    }

    public String getSwsGreenSet() {
        return swsGreenSet;
    }

    public void setSwsGreenSet(String swsGreenSet) {
        this.swsGreenSet = swsGreenSet;
    }

    public String getSwsOpenState() {
        return swsOpenState;
    }

    public void setSwsOpenState(String swsOpenState) {
        this.swsOpenState = swsOpenState;
    }

    public String getSwsFirstSet() {
        return swsFirstSet;
    }

    public void setSwsFirstSet(String swsFirstSet) {
        this.swsFirstSet = swsFirstSet;
    }

    public String getSwsDisType() {
        return swsDisType;
    }

    public void setSwsDisType(String swsDisType) {
        this.swsDisType = swsDisType;
    }

    public String getSwsType() {
        return swsType;
    }

    public void setSwsType(String swsType) {
        this.swsType = swsType;
    }

    public String getSwsRedSwitch() {
        return swsRedSwitch;
    }

    public void setSwsRedSwitch(String swsRedSwitch) {
        this.swsRedSwitch = swsRedSwitch;
    }

    public String getSwsYellowSwitch() {
        return swsYellowSwitch;
    }

    public void setSwsYellowSwitch(String swsYellowSwitch) {
        this.swsYellowSwitch = swsYellowSwitch;
    }

    public String getSwsGreenSwitch() {
        return swsGreenSwitch;
    }

    public void setSwsGreenSwitch(String swsGreenSwitch) {
        this.swsGreenSwitch = swsGreenSwitch;
    }

    public String getSwsGraySet() {
        return swsGraySet;
    }

    public void setSwsGraySet(String swsGraySet) {
        this.swsGraySet = swsGraySet;
    }

    public String getSwsGraySwitch() {
        return swsGraySwitch;
    }

    public void setSwsGraySwitch(String swsGraySwitch) {
        this.swsGraySwitch = swsGraySwitch;
    }
}
