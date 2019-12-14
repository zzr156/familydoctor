package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**指导模板
 * Created by zzl on 2017/6/20.
 */
@Entity
@Table(name = "APP_GUIDE_TEMPLATE")
public class AppGuideTemplate extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "GUIDE_TITLE", length = 200)
    private String guideTitle;//指导标题
    @Column(name = "GUIDE_CONTENT")
    private String guideContent;//指导内容
    @Column(name = "GUIDE_CREATE_TIME")
    private Calendar guideCreateTime;//创建时间
    @Column(name = "GUIDE_CREATE_ID", length = 36)
    private String guideCreateId;//创建者Id
    @Column(name = "GUIDE_TYPE",length = 10)
    private String guideType;//指导类型 1健康干预 2健康指导 3中医体质辨识保健指导
    @Column(name = "GUIDE_DISEASE_TYPE",length = 10)
    private String guideDiseaseType;//疾病类型
    @Column(name = "GUIDE_MEDDLE_TYPE",length = 10)
    private String guideMeddleType;//干预类型
    @Column(name = "GUIDE_IMAGE_URL",length = 200)
    private String guideImageUrl;//图片
    @Column(name = "GUIDE_HOSP_ID",length = 36)
    private String guideHospId;//医院id


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuideTitle() {
        return guideTitle;
    }

    public void setGuideTitle(String guideTitle) {
        this.guideTitle = guideTitle;
    }

    public String getGuideContent() {
        return guideContent;
    }

    public void setGuideContent(String guideContent) {
        this.guideContent = guideContent;
    }

    public Calendar getGuideCreateTime() {
        return guideCreateTime;
    }

    public void setGuideCreateTime(Calendar guideCreateTime) {
        this.guideCreateTime = guideCreateTime;
    }

    public String getGuideCreateId() {
        return guideCreateId;
    }

    public void setGuideCreateId(String guideCreateId) {
        this.guideCreateId = guideCreateId;
    }

    public String getGuideType() {
        return guideType;
    }

    public void setGuideType(String guideType) {
        this.guideType = guideType;
    }

    public String getGuideDiseaseType() {
        return guideDiseaseType;
    }

    public void setGuideDiseaseType(String guideDiseaseType) {
        this.guideDiseaseType = guideDiseaseType;
    }

    public String getGuideMeddleType() {
        return guideMeddleType;
    }

    public void setGuideMeddleType(String guideMeddleType) {
        this.guideMeddleType = guideMeddleType;
    }

    public String getGuideImageUrl() {
        return guideImageUrl;
    }

    public void setGuideImageUrl(String guideImageUrl) {
        this.guideImageUrl = guideImageUrl;
    }

    /**
     * 医生姓名
     * @return
     */
    public String getDrName(){
        if(StringUtils.isNotBlank(this.getGuideCreateId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser user =(AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getGuideCreateId());
            if(user!=null){
                return user.getDrName();
            }
        }
        return "";
    }

    public String getGuideHospId() {
        return guideHospId;
    }

    public void setGuideHospId(String guideHospId) {
        this.guideHospId = guideHospId;
    }
}
