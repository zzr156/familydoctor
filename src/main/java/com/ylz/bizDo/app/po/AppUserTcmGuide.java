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

/**
 * 中医药保健指导记录表
 * Created by zzl on 2017/8/30.
 */
@Entity
@Table(name = "APP_USER_TCM_GUIDE")
public class AppUserTcmGuide extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "UTG_CREATE_ID",length = 36)
    private String utgCreateId;//创建人id
    @Column(name = "UTG_CORPOREITY_TYPE",length = 10)
    private String utgCorporeityType;//体质类型（1气虚质 2阳虚质 3阴虚质 4痰湿质 5湿热质 6血瘀质 7气郁质 8特禀质 9平和质）
    @Column(name = "UTG_MODERN_CULTIVATE")
    private String utgModernCultivate;//情志调摄指导内容
    @Column(name = "UTG_DIET_AFTERCARE")
    private String utgDietAftercare;//饮食调养
    @Column(name = "UTG_DAILY_LIFE_CULTIVATE")
    private String utgDailyLifeCultivate;//起居调摄
    @Column(name = "UTG_SPORTS_HEALTH")
    private String utgSportsHealth;//运动保健
    @Column(name = "UTG_MERIDIAN_HEALTH")
    private String utgMeridianHealth;//穴位保健
    @Column(name = "UTG_OTHER")
    private String utgOther;//其他
    @Column(name = "UTG_TYPE",length = 10)
    private String utgType;//类型（1医生 2医院 3系统）
    @Column(name = "UTG_CREATE_TIME")
    private Calendar utgCreateTime;//创建时间
    @Column(name = "UTG_USER_ID",length = 36)
    private String utgUserId;//患者id
    @Column(name = "UTG_TCM_ID",length = 36)
    private String utgTcmId;//中医体质辨识答题记录id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUtgCreateId() {
        return utgCreateId;
    }

    public void setUtgCreateId(String utgCreateId) {
        this.utgCreateId = utgCreateId;
    }

    public String getUtgCorporeityType() {
        return utgCorporeityType;
    }

    public void setUtgCorporeityType(String utgCorporeityType) {
        this.utgCorporeityType = utgCorporeityType;
    }

    public String getUtgModernCultivate() {
        return utgModernCultivate;
    }

    public void setUtgModernCultivate(String utgModernCultivate) {
        this.utgModernCultivate = utgModernCultivate;
    }

    public String getUtgDietAftercare() {
        return utgDietAftercare;
    }

    public void setUtgDietAftercare(String utgDietAftercare) {
        this.utgDietAftercare = utgDietAftercare;
    }

    public String getUtgDailyLifeCultivate() {
        return utgDailyLifeCultivate;
    }

    public void setUtgDailyLifeCultivate(String utgDailyLifeCultivate) {
        this.utgDailyLifeCultivate = utgDailyLifeCultivate;
    }

    public String getUtgSportsHealth() {
        return utgSportsHealth;
    }

    public void setUtgSportsHealth(String utgSportsHealth) {
        this.utgSportsHealth = utgSportsHealth;
    }

    public String getUtgMeridianHealth() {
        return utgMeridianHealth;
    }

    public void setUtgMeridianHealth(String utgMeridianHealth) {
        this.utgMeridianHealth = utgMeridianHealth;
    }

    public String getUtgOther() {
        return utgOther;
    }

    public void setUtgOther(String utgOther) {
        this.utgOther = utgOther;
    }

    public String getUtgType() {
        return utgType;
    }

    public void setUtgType(String utgType) {
        this.utgType = utgType;
    }

    public Calendar getUtgCreateTime() {
        return utgCreateTime;
    }

    public void setUtgCreateTime(Calendar utgCreateTime) {
        this.utgCreateTime = utgCreateTime;
    }

    public String getUtgUserId() {
        return utgUserId;
    }

    public void setUtgUserId(String utgUserId) {
        this.utgUserId = utgUserId;
    }

    public String getUtgTcmId() {
        return utgTcmId;
    }

    public void setUtgTcmId(String utgTcmId) {
        this.utgTcmId = utgTcmId;
    }

    /**
     * 获取医生姓名
     * @return
     */
    public String getDrName(){
        if(StringUtils.isNotBlank(this.getUtgCreateId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getUtgCreateId());
            if(drUser!=null){
                return drUser.getDrName();
            }
        }
        return "";
    }

    /**
     * 体质类型名称
     * @return
     */
    public String getStrUtgCorporeityType() throws Exception{
        if(StringUtils.isNotBlank(this.getUtgCorporeityType())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TZLX,this.getUtgCorporeityType());
            if(value != null){
                return value.getCodeTitle();
            }
        }
        return "";
    }
}
