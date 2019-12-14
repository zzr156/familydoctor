package com.ylz.bizDo.smjq.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by zzl on 2019/4/19.
 */
@Entity
@Table(name = "NEWS_TABLE")
public class NcdNewTable extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
    private String id;//主键
    @Column(name = "TABLE_TITLE", length = 200)
    private String tableTitle;//标题
    @Column(name = "TABLE_CONTENT")
    private String tableContent;//内容
    @Column(name = "TABLE_CJRID", length = 36)
    private String tableCjrid;//创建人ID
    @Column(name = "TABLE_CJRXM", length = 20)
    private String tableCjrxm;//创建人姓名
    @Column(name = "TABLE_CJSJ", length = 19)
    private Calendar tableCjsj;//创建时间
    @Column(name = "TABLE_BROWSE", length = 20)
    private String tableBrowse = "0";//浏览次数
    @Column(name = "TABLE_STATE", length = 2)
    private String tableState ="1";//状态
    @Column(name = "TABLE_TYPE", length = 36)
    private String tableType="0";//新闻类别 1系統 2医院
    @Column(name = "TABLE_IMAGEURL", length = 36)
    private String tableImageUrl;//图片路径
    @Column(name = "TABLE_SOURCE", length = 100)
    private String tableSource;//新闻来源

    @Column(name = "TABLE_SPEAKER", length = 50)
    private String tableSpeaker;//主讲人
    @Column(name = "TABLE_ORG", length = 200)
    private String tableOrg;//主办单位
    @Column(name = "TABLE_ADDRESS", length = 200 )
    private String tableAddress;//地点
    @Column(name = "TABLE_HEALTH_TYPE", length = 10)
    private String tableHealthType;//健康教育类型
    @Column(name = "TABLE_SPEAKER_INTRODUCE", length = 200)
    private String tableSpeakerIntroduce;//主讲人介绍
    @Column(name = "TABLE_ACCESSORY_URL", length = 200)
    private String tableAccessoryUrl;//附件
    @Column(name = "TABLE_TIME")
    private Calendar tableTime;//时间
    @Column(name = "TABLE_ENSHRINE", length = 200)
    private String tableEnshrine = "0" ;//收藏数
    @Column(name = "TABLE_TRANSMIT",length = 200)
    private String tableTransmit = "0";//发布数
    @Column(name = "TABLE_PUSH_STATE" ,length = 10)
    private String tablePushState="0";//定期推送状态（1定期推送 2立即推送）
    @Column(name = "TABLE_PUSH_DATE")
    private Calendar tablePushDate;//定期推送日期
    @Column(name = "TABLE_PUSH_OBJECT",length = 20)
    private String tablePushObject;//接收对象类型（1服务人群 2健康情况 3疾病类型 4指导人员 5全部人员）
    @Column(name = "TABLE_PUSH_OSTATE",length = 10)
    private String tablePushOstate;//是否已经推送（1已推送）
    @Column(name = "TABLE_OBJECT",length = 20)
    private String tableObject;//具体接收对象（接收对象类型中的小项）
    @Column(name = "TABLE_PEOPLE_LIST")
    private String tablePeopleList;//指定人员id
    @Column(name = "TABLE_PEOPLE_NAME")
    private String tablePeopleName;//指定人员姓名
    @Column(name = "TABLE_HOSP_ID",length = 36)
    private String tableHospId;//机构id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableTitle() {
        return tableTitle;
    }

    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }

    public String getTableContent() {
        return tableContent;
    }

    public void setTableContent(String tableContent) {
        this.tableContent = tableContent;
    }

    public String getTableCjrid() {
        return tableCjrid;
    }

    public void setTableCjrid(String tableCjrid) {
        this.tableCjrid = tableCjrid;
    }

    public String getTableCjrxm() {
        return tableCjrxm;
    }

    public void setTableCjrxm(String tableCjrxm) {
        this.tableCjrxm = tableCjrxm;
    }

    public Calendar getTableCjsj() {
        return tableCjsj;
    }

    public void setTableCjsj(Calendar tableCjsj) {
        this.tableCjsj = tableCjsj;
    }

    public String getTableBrowse() {
        return tableBrowse;
    }

    public void setTableBrowse(String tableBrowse) {
        this.tableBrowse = tableBrowse;
    }

    public String getTableState() {
        return tableState;
    }

    public void setTableState(String tableState) {
        this.tableState = tableState;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableImageUrl() {
        return tableImageUrl;
    }

    public void setTableImageUrl(String tableImageUrl) {
        this.tableImageUrl = tableImageUrl;
    }

    public String getTableSource() {
        return tableSource;
    }

    public void setTableSource(String tableSource) {
        this.tableSource = tableSource;
    }

    public String getTableSpeaker() {
        return tableSpeaker;
    }

    public void setTableSpeaker(String tableSpeaker) {
        this.tableSpeaker = tableSpeaker;
    }

    public String getTableOrg() {
        return tableOrg;
    }

    public void setTableOrg(String tableOrg) {
        this.tableOrg = tableOrg;
    }

    public String getTableAddress() {
        return tableAddress;
    }

    public void setTableAddress(String tableAddress) {
        this.tableAddress = tableAddress;
    }

    public String getTableHealthType() {
        return tableHealthType;
    }

    public void setTableHealthType(String tableHealthType) {
        this.tableHealthType = tableHealthType;
    }

    public String getTableSpeakerIntroduce() {
        return tableSpeakerIntroduce;
    }

    public void setTableSpeakerIntroduce(String tableSpeakerIntroduce) {
        this.tableSpeakerIntroduce = tableSpeakerIntroduce;
    }

    public String getTableAccessoryUrl() {
        return tableAccessoryUrl;
    }

    public void setTableAccessoryUrl(String tableAccessoryUrl) {
        this.tableAccessoryUrl = tableAccessoryUrl;
    }

    public Calendar getTableTime() {
        return tableTime;
    }

    public void setTableTime(Calendar tableTime) {
        this.tableTime = tableTime;
    }

    public String getTableEnshrine() {
        return tableEnshrine;
    }

    public void setTableEnshrine(String tableEnshrine) {
        this.tableEnshrine = tableEnshrine;
    }

    public String getTableTransmit() {
        return tableTransmit;
    }

    public void setTableTransmit(String tableTransmit) {
        this.tableTransmit = tableTransmit;
    }

    public String getTablePushState() {
        return tablePushState;
    }

    public void setTablePushState(String tablePushState) {
        this.tablePushState = tablePushState;
    }

    public Calendar getTablePushDate() {
        return tablePushDate;
    }

    public void setTablePushDate(Calendar tablePushDate) {
        this.tablePushDate = tablePushDate;
    }

    public String getTablePushObject() {
        return tablePushObject;
    }

    public void setTablePushObject(String tablePushObject) {
        this.tablePushObject = tablePushObject;
    }

    public String getTablePushOstate() {
        return tablePushOstate;
    }

    public void setTablePushOstate(String tablePushOstate) {
        this.tablePushOstate = tablePushOstate;
    }

    public String getTableObject() {
        return tableObject;
    }

    public void setTableObject(String tableObject) {
        this.tableObject = tableObject;
    }

    public String getTablePeopleList() {
        return tablePeopleList;
    }

    public void setTablePeopleList(String tablePeopleList) {
        this.tablePeopleList = tablePeopleList;
    }

    public String getTablePeopleName() {
        return tablePeopleName;
    }

    public void setTablePeopleName(String tablePeopleName) {
        this.tablePeopleName = tablePeopleName;
    }

    public String getTableHospId() {
        return tableHospId;
    }

    public void setTableHospId(String tableHospId) {
        this.tableHospId = tableHospId;
    }
}
