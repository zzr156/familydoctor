package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 签约服务团队配置表
 * Created by zzl on 2017/8/17.
 */
@Entity
@Table(name="APP_SIGN_SERVE_TEAM")
public class AppSignServeTeam extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "SST_WORK_STATE",length = 10)
    private String sstWorkState;//签约团队是否包含特定工作类型
    @Column(name = "SST_WORK_VALUE",length = 100)
    private String sstWorkValue;//特定工作类型
    @Column(name = "SST_SING_STATE",length = 10)
    private String sstSignState;//签约人数上限是否开启
    @Column(name = "SST_SIGN_TO_TEAM",length = 100)
    private String sstSignToTeam = "0";//团队签约总人数上限
    @Column(name = "SST_SIGN_TO_DR",length = 100)
    private String sstSignToDr = "0";//医生个人签约人数上限
    @Column(name = "SST_DEPT_ID",length = 36)
    private String sstDeptId;//机构id
    @Column(name = "SST_CREATE_ID",length = 36)
    private String sstCreateId;//创建人id
    @Column(name = "SST_CREATE_TIME")
    private Calendar sstCreateTime;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSstWorkState() {
        return sstWorkState;
    }

    public void setSstWorkState(String sstWorkState) {
        this.sstWorkState = sstWorkState;
    }

    public String getSstWorkValue() {
        return sstWorkValue;
    }

    public void setSstWorkValue(String sstWorkValue) {
        this.sstWorkValue = sstWorkValue;
    }

    public String getSstSignState() {
        return sstSignState;
    }

    public void setSstSignState(String sstSignState) {
        this.sstSignState = sstSignState;
    }

    public String getSstSignToTeam() {
        return sstSignToTeam;
    }

    public void setSstSignToTeam(String sstSignToTeam) {
        this.sstSignToTeam = sstSignToTeam;
    }

    public String getSstSignToDr() {
        return sstSignToDr;
    }

    public void setSstSignToDr(String sstSignToDr) {
        this.sstSignToDr = sstSignToDr;
    }

    public String getSstDeptId() {
        return sstDeptId;
    }

    public void setSstDeptId(String sstDeptId) {
        this.sstDeptId = sstDeptId;
    }

    public String getSstCreateId() {
        return sstCreateId;
    }

    public void setSstCreateId(String sstCreateId) {
        this.sstCreateId = sstCreateId;
    }

    public Calendar getSstCreateTime() {
        return sstCreateTime;
    }

    public void setSstCreateTime(Calendar sstCreateTime) {
        this.sstCreateTime = sstCreateTime;
    }
}
