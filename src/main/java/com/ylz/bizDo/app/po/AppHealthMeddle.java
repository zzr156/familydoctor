package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**发送健康指导数据表
 * Created by zzl on 2017/6/29.
 */
@Entity
@Table(name = "APP_HEALTH_MEDDLE")
public class AppHealthMeddle extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name ="HM_DR_ID",length = 36)
    private String hmDrId;//医生主键
    @Column(name ="HM_PATIENT_ID",length = 36)
    private String hmPatientId;//患者主键
    @Column(name ="HM_MOD_ID",length = 36)
    private String hmModId;//模板主键
    @Column(name ="HM_MED_TYPE",length = 10)
    private String hmMedType;//干预类型
    @Column(name ="HM_DIS_TYPE",length = 10)
    private String hmDisType;//疾病类型
    @Column(name ="HM_CONTENT")
    private String hmContent;//干预内容
    @Column(name ="HM_TITLE")
    private String hmTitle;//干预标题
    @Column(name ="HM_CREATE_TIME")
    private Calendar hmCreateTime;//干预时间
    @Column(name ="HM_IMAGE_URL",length = 200)
    private String hmImageUrl;//干预图片
    @Column(name = "HM_HOSP_ID",length = 36)
    private String hmHospId;//医院id
    @Column(name = "HM_AREA_CODE",length = 50)
    private String hmAreaCode;//区域编码
    @Column(name = "HM_TEAM_ID",length = 36)
    private String hmTeamId;//团队id
    @Column(name = "HM_GUIDE_TYPE",length = 10)
    private String hmGuideType;//指导类型（1健康干预 2健康指导 3中医药保健指导）
    @Column(name = "HM_TCM_ID",length = 36)
    private String hmTcmId;//中医药体质辨识答题记录Id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHmDrId() {
        return hmDrId;
    }

    public void setHmDrId(String hmDrId) {
        this.hmDrId = hmDrId;
    }

    public String getHmPatientId() {
        return hmPatientId;
    }

    public void setHmPatientId(String hmPatientId) {
        this.hmPatientId = hmPatientId;
    }

    public String getHmModId() {
        return hmModId;
    }

    public void setHmModId(String hmModId) {
        this.hmModId = hmModId;
    }

    public String getHmMedType() {
        return hmMedType;
    }

    public void setHmMedType(String hmMedType) {
        this.hmMedType = hmMedType;
    }

    public String getHmDisType() {
        return hmDisType;
    }

    public void setHmDisType(String hmDisType) {
        this.hmDisType = hmDisType;
    }

    public String getHmContent() {
        return hmContent;
    }

    public void setHmContent(String hmContent) {
        this.hmContent = hmContent;
    }

    public String getHmTitle() {
        return hmTitle;
    }

    public void setHmTitle(String hmTitle) {
        this.hmTitle = hmTitle;
    }

    public Calendar getHmCreateTime() {
        return hmCreateTime;
    }

    public void setHmCreateTime(Calendar hmCreateTime) {
        this.hmCreateTime = hmCreateTime;
    }

    public String getHmImageUrl() {
        return hmImageUrl;
    }

    public void setHmImageUrl(String hmImageUrl) {
        this.hmImageUrl = hmImageUrl;
    }

    public String getHmHospId() {
        return hmHospId;
    }

    public void setHmHospId(String hmHospId) {
        this.hmHospId = hmHospId;
    }

    public String getHmAreaCode() {
        return hmAreaCode;
    }

    public void setHmAreaCode(String hmAreaCode) {
        this.hmAreaCode = hmAreaCode;
    }

    public String getHmTeamId() {
        return hmTeamId;
    }

    public void setHmTeamId(String hmTeamId) {
        this.hmTeamId = hmTeamId;
    }

    public String getHmGuideType() {
        return hmGuideType;
    }

    public void setHmGuideType(String hmGuideType) {
        this.hmGuideType = hmGuideType;
    }

    public String getHmTcmId() {
        return hmTcmId;
    }

    public void setHmTcmId(String hmTcmId) {
        this.hmTcmId = hmTcmId;
    }

    /**
     * 医生姓名
     * @return
     */
    public String getHmDrName(){
        if(StringUtils.isNotBlank(this.getHmDrId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser user =(AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getHmDrId());
            if(user!=null){
                return user.getDrName();
            }
        }
        return "";
    }
}
