package com.ylz.bizDo.web.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 服务包表
 * Created by zzl on 2018/5/28.
 */
@Entity
@Table(name = "APP_SERVE_SETMEAL")
public class WebServeSetmeal extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
    private String id;//主键
    @Column(name = "SERSM_VALUE",length = 50)
    private String sersmValue;//套餐值
    @Column(name = "SERSM_NAME",length = 100)
    private String sersmName;//套餐名称
    @Column(name = "SERSM_GROUP_VALUE")
    private String sersmGroupValue;//组合值
    @Column(name = "SERSM_OBJECT_VALUE")
    private String sersmObjectValue;//服务人群值
    @Column(name = "SERSM_OBJECT_TITLE")
    private String sersmObjectTitle;//服务人群名称
    @Column(name = "SERSM_OBJECT_TYPE")
    private String sersmObjectType;//服务人群类型
    @Column(name = "SERSM_PK_VALUE")
    private String sersmPkValue;//服务内容值
    @Column(name = "SERSM_PK_TITLE")
    private String sersmPkTitle;//服务内容名称
    @Column(name = "SERSM_PK_TYPE")
    private String sersmPkType;//服务内容类型
    @Column(name = "SERSM_TOTAL_FEE",length = 100)
    private String sersmTotalFee;//套餐总费用
    @Column(name = "SERSM_DOWN_STATE",length = 10)
    private String sersmDownState;//是否含减免
    @Column(name = "SERSM_JJ_TYPE",length = 50)
    private String sersmJjType;////减免对象经济类型
    @Column(name = "SERSM_SUBSIDY_WAY",length = 10)
    private String sersmSubsidyWay;//政府补贴方式
    @Column(name = "SERSM_FEE",length = 100)
    private String sersmFee;//套餐实付金额
    @Column(name = "SERSM_YXTIME_TYPE",length = 10)
    private String sersmYxTimeType;//套餐有效期方式
    @Column(name = "SERSM_START_TIME",length = 50)
    private String sersmStartTime;//有效开始时间
    @Column(name = "SERSM_END_TIME",length = 50)
    private String sersmEndTime;//有效结束时间
    @Column(name = "SERSM_BG_DR",length = 10)
    private String sersmBgDr="0";//有效期间是否可变更医生 0否 1是
    @Column(name = "SERSM_BOOK")
    private String sersmBook;//协议
    @Column(name = "SERSM_IMAGE_URL")
    private String sersmImageUrl;//图片url
    @Column(name = "SERSM_IMAGE_NAME")
    private String sersmImageName;//图片名称
    @Column(name = "SERSM_CREATE_DEPT",length = 36)
    private String sersmCreateDept;//创建单位
    @Column(name = "SERSM_CREATE_ID",length = 36)
    private String sersmCreateId;//创建人id
    @Column(name = "SERSM_CREATE_TIME")
    private Calendar sersmCreateTime;//创建时间
    @Column(name = "SERSM_AREA_CODE",length = 100)
    private String sersmAreaCode;//区域编号
    @Column(name = "SERSM_JC_STATE",length = 10)
    private String sersmJcState="0";//是否是基础服务套餐 0否 1是
    @Column(name = "SERSM_OPEN_STATE",length = 10)
    private String sersmOpenState="0";//是否开启 0否 1是
    @Column(name = "SERSM_LEVEL",length = 10)
    private String sersmLevel;//级别
    @Column(name = "SERSM_JJ_ID")
    private String sersmJjId;//经济类型id
    @Column(name = "SERSM_TAB_STATE",length = 10)
    private String sersmTabState;//标记状态
    @Column(name = "SERSM_OPEN_AREA")
    private String sersmOpenArea;//开放区域
    @Column(name = "SERSM_ONE_FEE",length = 255)
    private String sersmOneFee;//个人需支付费用
    @Column(name = "SERSM_TOTAL_ONE_FEE",length = 20)
    private String sersmTotalOneFee;//个人需付总金额
    @Column(name = "SERSM_GROUP_ID")//长度类型 text
    private String sersmGroupId;//组合id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSersmValue() {
        return sersmValue;
    }

    public void setSersmValue(String sersmValue) {
        this.sersmValue = sersmValue;
    }

    public String getSersmName() {
        return sersmName;
    }

    public void setSersmName(String sersmName) {
        this.sersmName = sersmName;
    }

    public String getSersmGroupValue() {
        return sersmGroupValue;
    }

    public void setSersmGroupValue(String sersmGroupValue) {
        this.sersmGroupValue = sersmGroupValue;
    }

    public String getSersmObjectValue() {
        return sersmObjectValue;
    }

    public void setSersmObjectValue(String sersmObjectValue) {
        this.sersmObjectValue = sersmObjectValue;
    }

    public String getSersmObjectTitle() {
        return sersmObjectTitle;
    }

    public void setSersmObjectTitle(String sersmObjectTitle) {
        this.sersmObjectTitle = sersmObjectTitle;
    }

    public String getSersmObjectType() {
        return sersmObjectType;
    }

    public void setSersmObjectType(String sersmObjectType) {
        this.sersmObjectType = sersmObjectType;
    }

    public String getSersmPkValue() {
        return sersmPkValue;
    }

    public void setSersmPkValue(String sersmPkValue) {
        this.sersmPkValue = sersmPkValue;
    }

    public String getSersmPkTitle() {
        return sersmPkTitle;
    }

    public void setSersmPkTitle(String sersmPkTitle) {
        this.sersmPkTitle = sersmPkTitle;
    }

    public String getSersmPkType() {
        return sersmPkType;
    }

    public void setSersmPkType(String sersmPkType) {
        this.sersmPkType = sersmPkType;
    }

    public String getSersmTotalFee() {
        return sersmTotalFee;
    }

    public void setSersmTotalFee(String sersmTotalFee) {
        this.sersmTotalFee = sersmTotalFee;
    }

    public String getSersmDownState() {
        return sersmDownState;
    }

    public void setSersmDownState(String sersmDownState) {
        this.sersmDownState = sersmDownState;
    }

    public String getSersmJjType() {
        return sersmJjType;
    }

    public void setSersmJjType(String sersmJjType) {
        this.sersmJjType = sersmJjType;
    }

    public String getSersmSubsidyWay() {
        return sersmSubsidyWay;
    }

    public void setSersmSubsidyWay(String sersmSubsidyWay) {
        this.sersmSubsidyWay = sersmSubsidyWay;
    }

    public String getSersmFee() {
        return sersmFee;
    }

    public void setSersmFee(String sersmFee) {
        this.sersmFee = sersmFee;
    }

    public String getSersmYxTimeType() {
        return sersmYxTimeType;
    }

    public void setSersmYxTimeType(String sersmYxTimeType) {
        this.sersmYxTimeType = sersmYxTimeType;
    }

    public String getSersmStartTime() {
        return sersmStartTime;
    }

    public void setSersmStartTime(String sersmStartTime) {
        this.sersmStartTime = sersmStartTime;
    }

    public String getSersmEndTime() {
        return sersmEndTime;
    }

    public void setSersmEndTime(String sersmEndTime) {
        this.sersmEndTime = sersmEndTime;
    }

    public String getSersmBgDr() {
        return sersmBgDr;
    }

    public void setSersmBgDr(String sersmBgDr) {
        this.sersmBgDr = sersmBgDr;
    }

    public String getSersmBook() {
        return sersmBook;
    }

    public void setSersmBook(String sersmBook) {
        this.sersmBook = sersmBook;
    }

    public String getSersmImageUrl() {
        return sersmImageUrl;
    }

    public void setSersmImageUrl(String sersmImageUrl) {
        this.sersmImageUrl = sersmImageUrl;
    }

    public String getSersmImageName() {
        return sersmImageName;
    }

    public void setSersmImageName(String sersmImageName) {
        this.sersmImageName = sersmImageName;
    }

    public String getSersmCreateDept() {
        return sersmCreateDept;
    }

    public void setSersmCreateDept(String sersmCreateDept) {
        this.sersmCreateDept = sersmCreateDept;
    }

    public String getSersmCreateId() {
        return sersmCreateId;
    }

    public void setSersmCreateId(String sersmCreateId) {
        this.sersmCreateId = sersmCreateId;
    }

    public Calendar getSersmCreateTime() {
        return sersmCreateTime;
    }

    public void setSersmCreateTime(Calendar sersmCreateTime) {
        this.sersmCreateTime = sersmCreateTime;
    }

    public String getSersmAreaCode() {
        return sersmAreaCode;
    }

    public void setSersmAreaCode(String sersmAreaCode) {
        this.sersmAreaCode = sersmAreaCode;
    }

    public String getSersmJcState() {
        return sersmJcState;
    }

    public void setSersmJcState(String sersmJcState) {
        this.sersmJcState = sersmJcState;
    }

    public String getSersmOpenState() {
        return sersmOpenState;
    }

    public void setSersmOpenState(String sersmOpenState) {
        this.sersmOpenState = sersmOpenState;
    }

    public String getSersmLevel() {
        return sersmLevel;
    }

    public void setSersmLevel(String sersmLevel) {
        this.sersmLevel = sersmLevel;
    }

    public String getSersmJjId() {
        return sersmJjId;
    }

    public void setSersmJjId(String sersmJjId) {
        this.sersmJjId = sersmJjId;
    }

    public String getSersmTabState() {
        return sersmTabState;
    }

    public void setSersmTabState(String sersmTabState) {
        this.sersmTabState = sersmTabState;
    }

    public String getSersmOpenArea() {
        return sersmOpenArea;
    }

    public void setSersmOpenArea(String sersmOpenArea) {
        this.sersmOpenArea = sersmOpenArea;
    }

    public String getSersmOneFee() {
        return sersmOneFee;
    }

    public void setSersmOneFee(String sersmOneFee) {
        this.sersmOneFee = sersmOneFee;
    }

    public String getSersmTotalOneFee() {
        return sersmTotalOneFee;
    }

    public void setSersmTotalOneFee(String sersmTotalOneFee) {
        this.sersmTotalOneFee = sersmTotalOneFee;
    }

    public String getSersmGroupId() {
        return sersmGroupId;
    }

    public void setSersmGroupId(String sersmGroupId) {
        this.sersmGroupId = sersmGroupId;
    }
}
