package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**咨询表
 * Created by zzl on 2017/6/23.
 */
@Entity
@Table(name = "APP_CONSULT")
public class AppConsult extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "CON_CREATE_USER_ID", length = 36)
    private String conCreateUserId;//发起人id/回复者id
    @Column(name = "CON_TITLE", length = 200)
    private String conTitle;//咨询标题
    @Column(name = "CON_CONTENT")
    private String conContent;//咨询内容/回复内容
    @Column(name = "CON_REPLIER_ID",length = 36)
    private String conReolierId;//咨询对象id
    @Column(name = "CON_TYPE",length = 10)
    private String conType;//咨询类别 (公共咨询、免费咨询，患者咨询）
    @Column(name = "CON_STATE", length = 10)
    private String conState;//咨询状态 0咨询中 1咨询结束
    @Column(name = "CON_PID", length = 36)
    private String conPid;//父id
    @Column(name = "CON_CREATE_TIME")
    private Calendar conCreateTime;//咨询时间/回复时间
    @Column(name = "CON_FILE_URL",length = 200)
    private String conFileUrl;
    @Column(name = "CON_TEAM_ID",length = 36)
    private String conTeamId;//团队id
    @Column(name = "CON_HOSP_ID",length = 36)
    private String conHospId;//医院主键
    @Column(name = "CON_AREA_CODE", length = 50)
    private String conAreaCode;//区域编号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConCreateUserId() {
        return conCreateUserId;
    }

    public void setConCreateUserId(String conCreateUserId) {
        this.conCreateUserId = conCreateUserId;
    }

    public String getConTitle() {
        return conTitle;
    }

    public void setConTitle(String conTitle) {
        this.conTitle = conTitle;
    }

    public String getConContent() {
        return conContent;
    }

    public void setConContent(String conContent) {
        this.conContent = conContent;
    }

    public String getConReolierId() {
        return conReolierId;
    }

    public void setConReolierId(String conReolierId) {
        this.conReolierId = conReolierId;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public String getConState() {
        return conState;
    }

    public void setConState(String conState) {
        this.conState = conState;
    }

    public String getConPid() {
        return conPid;
    }

    public void setConPid(String conPid) {
        this.conPid = conPid;
    }

    public Calendar getConCreateTime() {
        return conCreateTime;
    }

    public void setConCreateTime(Calendar conCreateTime) {
        this.conCreateTime = conCreateTime;
    }

    public String getConFileUrl() {
        return conFileUrl;
    }

    public void setConFileUrl(String conFileUrl) {
        this.conFileUrl = conFileUrl;
    }

    public String getConTeamId() {
        return conTeamId;
    }

    public void setConTeamId(String conTeamId) {
        this.conTeamId = conTeamId;
    }

    public String getConHospId() {
        return conHospId;
    }

    public void setConHospId(String conHospId) {
        this.conHospId = conHospId;
    }

    public String getConAreaCode() {
        return conAreaCode;
    }

    public void setConAreaCode(String conAreaCode) {
        this.conAreaCode = conAreaCode;
    }
}
