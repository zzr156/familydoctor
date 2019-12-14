package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**患者的健康教育表
 * Created by zzl on 2017/6/21.
 */
@Entity
@Table(name = "APP_USER_HEALTHED")
public class AppUserHealthED extends BasePO {
    // Fields
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "HED_USER_ID",length = 36)
    private String hedUserId;//患者id
    @Column(name = "HED_DR_ID",length = 36)
    private String hedDrId;//医生id
    @Column(name = "HED_ID",length = 36)
    private String hedId;//健康教育数据id
    @Column(name = "HED_USER_NAME", length = 50)
    private String hedUserName;//患者姓名
    @Column(name = "HED_DR_NAME", length = 50)
    private String hedDrName;//医生姓名
    @Column(name= "HED_TEAM_ID",length = 36)
    private String hedTeamId;//团队id
    @Column(name = "HED_TEAM_NAME",length = 200)
    private String hedTeamName;//团队名称
    @Column(name = "HED_TITLE",length = 200)
    private String hedTitle;//健康教育标题
    @Column(name = "HED_IMAGE_URL",length = 200)
    private String hedImageUrl;//健康教育图片
    @Column(name = "HED_CREATE_TIME" )
    private Calendar hedCreateTime;//创建时间
    @Column(name = "HED_TYPE",length = 10)
    private String hedType;//健康教育类型
    @Column(name = "HED_CONTENT")
    private String hedContent;//健康教育内容
    @Column(name = "HED_HOSP_ID",length = 36)
    private String hedHospId;//医院id
    @Column(name = "HED_AREA_CODE",length = 50)
    private String hedAreaCode;//区域编号
    @Column(name = "HED_OBJECT",length = 10)
    private String hedObject;//接收对象类型
    @Column(name = "HED_OBJECTXX")
    private String hedObjectXx;//接收具体对象对象
    @Column(name = "HED_PUSH_STATE",length = 10)
    private String hedPushState = "0";//推送状态（1是定期推送 2立即推送）
    @Column(name = "HED_PUSH_OSTATE",length = 10)
    private String hedPushOstate="0";//是否已经推送（1是 0 否）
    @Column(name = "HED_PUSH_DATE")
    private Calendar hedPushDate;//推送时间（保存定期推送时间）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHedUserId() {
        return hedUserId;
    }

    public void setHedUserId(String hedUserId) {
        this.hedUserId = hedUserId;
    }

    public String getHedDrId() {
        return hedDrId;
    }

    public void setHedDrId(String hedDrId) {
        this.hedDrId = hedDrId;
    }

    public String getHedId() {
        return hedId;
    }

    public void setHedId(String hedId) {
        this.hedId = hedId;
    }

    public String getHedUserName() {
        return hedUserName;
    }

    public void setHedUserName(String hedUserName) {
        this.hedUserName = hedUserName;
    }

    public String getHedDrName() {
        return hedDrName;
    }

    public void setHedDrName(String hedDrName) {
        this.hedDrName = hedDrName;
    }

    public String getHedTeamId() {
        return hedTeamId;
    }

    public void setHedTeamId(String hedTeamId) {
        this.hedTeamId = hedTeamId;
    }

    public String getHedTeamName() {
        return hedTeamName;
    }

    public void setHedTeamName(String hedTeamName) {
        this.hedTeamName = hedTeamName;
    }

    public String getHedTitle() {
        return hedTitle;
    }

    public void setHedTitle(String hedTitle) {
        this.hedTitle = hedTitle;
    }

    public String getHedImageUrl() {
        return hedImageUrl;
    }

    public void setHedImageUrl(String hedImageUrl) {
        this.hedImageUrl = hedImageUrl;
    }

    public Calendar getHedCreateTime() {
        return hedCreateTime;
    }

    public void setHedCreateTime(Calendar hedCreateTime) {
        this.hedCreateTime = hedCreateTime;
    }

    public String getHedType() {
        return hedType;
    }

    public void setHedType(String hedType) {
        this.hedType = hedType;
    }

    public String getHedContent() {
        return hedContent;
    }

    public void setHedContent(String hedContent) {
        this.hedContent = hedContent;
    }

    public String getHedHospId() {
        return hedHospId;
    }

    public void setHedHospId(String hedHospId) {
        this.hedHospId = hedHospId;
    }

    public String getHedAreaCode() {
        return hedAreaCode;
    }

    public void setHedAreaCode(String hedAreaCode) {
        this.hedAreaCode = hedAreaCode;
    }

    public String getHedObject() {
        return hedObject;
    }

    public void setHedObject(String hedObject) {
        this.hedObject = hedObject;
    }

    public String getHedPushState() {
        return hedPushState;
    }

    public void setHedPushState(String hedPushState) {
        this.hedPushState = hedPushState;
    }

    public String getHedObjectXx() {
        return hedObjectXx;
    }

    public void setHedObjectXx(String hedObjectXx) {
        this.hedObjectXx = hedObjectXx;
    }

    public String getHedPushOstate() {
        return hedPushOstate;
    }

    public void setHedPushOstate(String hedPushOstate) {
        this.hedPushOstate = hedPushOstate;
    }

    public Calendar getHedPushDate() {
        return hedPushDate;
    }

    public void setHedPushDate(Calendar hedPushDate) {
        this.hedPushDate = hedPushDate;
    }
}
