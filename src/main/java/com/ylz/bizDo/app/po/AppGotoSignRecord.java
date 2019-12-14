package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**转签记录表
 * Created by zzl on 2017/10/26.
 */
@Entity
@Table(name = "APP_GOTOSIGN_RECORD")
public class AppGotoSignRecord  extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "GTS_SIGN_ID",length = 36)
    private String gtsSignId;//签约单id
    @Column(name = "GTS_PATIENT_ID",length = 36)
    private String gtsPatientId;//患者id
    @Column(name = "GTS_DR_ID",length = 36)
    private String gtsDrId;//医生id
    @Column(name = "GTS_AREA_CODE",length = 12)
    private String gtsAreaCode;//区域编号
    @Column(name = "GTS_HOSP_ID",length = 36)
    private String gtsHospId;//医院id
    @Column(name = "GTS_TEAM_ID",length = 36)
    private String gtsTeamId;//团队id
    @Column(name = "GTS_CREATE_TIME")
    private Calendar gtsCreateTime;//创建时间
    @Column(name = "GTS_REASON_VALUE",length = 50)
    private String gtsReasonValue;//转签原因
    @Column(name = "GTS_OLD_SIGN_ID",length = 36)
    private String gtsOldSignId;//旧的签约单id
    @Column(name = "GTS_OLD_DR_ID",length = 36)
    private String gtsOldDrId;//旧的签约医生id
    @Column(name = "GTS_OLD_AREA_CODE",length = 12)
    private String gtsOldAreaCode;//旧的签约区域编码
    @Column(name = "GTS_OLD_HOSP_ID",length = 36)
    private String gtsOldHospId;//旧的签约医院
    @Column(name = "GTS_OLD_TEAM_ID",length = 36)
    private String gtsOldTeamId;//旧的团队id
    @Column(name = "GTS_SIGN_STATE",length = 10)
    private String gtsSignState="0";//转签状态 0申请转签却未同意 1申签转签，医生同意
    @Column(name = "GTS_TYPE",length = 10)
    private String gtsType;//转签类别1签约单快过期转签 2

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGtsSignId() {
        return gtsSignId;
    }

    public void setGtsSignId(String gtsSignId) {
        this.gtsSignId = gtsSignId;
    }

    public String getGtsPatientId() {
        return gtsPatientId;
    }

    public void setGtsPatientId(String gtsPatientId) {
        this.gtsPatientId = gtsPatientId;
    }

    public String getGtsDrId() {
        return gtsDrId;
    }

    public void setGtsDrId(String gtsDrId) {
        this.gtsDrId = gtsDrId;
    }

    public String getGtsAreaCode() {
        return gtsAreaCode;
    }

    public void setGtsAreaCode(String gtsAreaCode) {
        this.gtsAreaCode = gtsAreaCode;
    }

    public String getGtsHospId() {
        return gtsHospId;
    }

    public void setGtsHospId(String gtsHospId) {
        this.gtsHospId = gtsHospId;
    }

    public String getGtsTeamId() {
        return gtsTeamId;
    }

    public void setGtsTeamId(String gtsTeamId) {
        this.gtsTeamId = gtsTeamId;
    }

    public Calendar getGtsCreateTime() {
        return gtsCreateTime;
    }

    public void setGtsCreateTime(Calendar gtsCreateTime) {
        this.gtsCreateTime = gtsCreateTime;
    }

    public String getGtsReasonValue() {
        return gtsReasonValue;
    }

    public void setGtsReasonValue(String gtsReasonValue) {
        this.gtsReasonValue = gtsReasonValue;
    }

    public String getGtsOldDrId() {
        return gtsOldDrId;
    }

    public void setGtsOldDrId(String gtsOldDrId) {
        this.gtsOldDrId = gtsOldDrId;
    }

    public String getGtsOldAreaCode() {
        return gtsOldAreaCode;
    }

    public void setGtsOldAreaCode(String gtsOldAreaCode) {
        this.gtsOldAreaCode = gtsOldAreaCode;
    }

    public String getGtsOldHospId() {
        return gtsOldHospId;
    }

    public void setGtsOldHospId(String gtsOldHospId) {
        this.gtsOldHospId = gtsOldHospId;
    }

    public String getGtsOldTeamId() {
        return gtsOldTeamId;
    }

    public void setGtsOldTeamId(String gtsOldTeamId) {
        this.gtsOldTeamId = gtsOldTeamId;
    }

    public String getGtsOldSignId() {
        return gtsOldSignId;
    }

    public void setGtsOldSignId(String gtsOldSignId) {
        this.gtsOldSignId = gtsOldSignId;
    }

    public String getGtsSignState() {
        return gtsSignState;
    }

    public void setGtsSignState(String gtsSignState) {
        this.gtsSignState = gtsSignState;
    }

    /**
     * 获取转签原因名称
     * @return
     */
    public String getStrGtsReason() throws Exception{
        String ss = "";
        if(StringUtils.isNotBlank(this.getGtsReasonValue())){
            String[] strs = this.getGtsReasonValue().split(";");
            for(String str:strs){
                SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ZZREASON, str);
                if(value!=null){
                    if(StringUtils.isBlank(ss)){
                        ss=value.getCodeTitle();
                    }else{
                        ss+=","+value.getCodeTitle();
                    }
                }
            }
        }
        return ss;
    }
}
