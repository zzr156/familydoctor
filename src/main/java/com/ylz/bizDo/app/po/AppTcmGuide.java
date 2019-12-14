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

/**中医体质辨识模板表
 * Created by zzl on 2017/8/29.
 */
@Entity
@Table(name = "APP_TCM_GUIDE")
public class AppTcmGuide extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "TCMG_CREATE_ID",length = 36)
    private String tcmgCreateId;//创建者id
    @Column(name = "TCMG_CORPOREITY_TYPE",length = 10)
    private String tcmgCorporeityType;//体质类型（1气虚质 2阳虚质 3阴虚质 4痰湿质 5湿热质 6血瘀质 7气郁质 8特禀质 9平和质）
    @Column(name = "TCMG_MODERN_CULTIVATE")
    private String tcmgModernCultivate;//情志调摄指导内容
    @Column(name = "TCMG_DIET_AFTERCARE")
    private String tcmgDietAftercare;//饮食调养
    @Column(name = "TCMG_DAILY_LIFE_CULTIVATE")
    private String tcmgDailyLifeCultivate;//起居调摄
    @Column(name = "TCMG_SPORTS_HEALTH")
    private String tcmgSportsHealth;//运动保健
    @Column(name = "TCMG_MERIDIAN_HEALTH")
    private String tcmgMeridianHealth;//穴位保健
    @Column(name = "TCMG_OTHER")
    private String tcmgOther;//其他
    @Column(name = "TCMG_TYPE",length = 10)
    private String tcmgType;//类型（1医生 2医院 3系统）
    @Column(name = "TCMG_CREATE_TIME")
    private Calendar tcmgCreateTime;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTcmgCreateId() {
        return tcmgCreateId;
    }

    public void setTcmgCreateId(String tcmgCreateId) {
        this.tcmgCreateId = tcmgCreateId;
    }

    public String getTcmgCorporeityType() {
        return tcmgCorporeityType;
    }

    public void setTcmgCorporeityType(String tcmgCorporeityType) {
        this.tcmgCorporeityType = tcmgCorporeityType;
    }

    public String getTcmgModernCultivate() {
        return tcmgModernCultivate;
    }

    public void setTcmgModernCultivate(String tcmgModernCultivate) {
        this.tcmgModernCultivate = tcmgModernCultivate;
    }

    public String getTcmgDietAftercare() {
        return tcmgDietAftercare;
    }

    public void setTcmgDietAftercare(String tcmgDietAftercare) {
        this.tcmgDietAftercare = tcmgDietAftercare;
    }

    public String getTcmgDailyLifeCultivate() {
        return tcmgDailyLifeCultivate;
    }

    public void setTcmgDailyLifeCultivate(String tcmgDailyLifeCultivate) {
        this.tcmgDailyLifeCultivate = tcmgDailyLifeCultivate;
    }

    public String getTcmgSportsHealth() {
        return tcmgSportsHealth;
    }

    public void setTcmgSportsHealth(String tcmgSportsHealth) {
        this.tcmgSportsHealth = tcmgSportsHealth;
    }

    public String getTcmgMeridianHealth() {
        return tcmgMeridianHealth;
    }

    public void setTcmgMeridianHealth(String tcmgMeridianHealth) {
        this.tcmgMeridianHealth = tcmgMeridianHealth;
    }

    public String getTcmgOther() {
        return tcmgOther;
    }

    public void setTcmgOther(String tcmgOther) {
        this.tcmgOther = tcmgOther;
    }

    public String getTcmgType() {
        return tcmgType;
    }

    public void setTcmgType(String tcmgType) {
        this.tcmgType = tcmgType;
    }

    public Calendar getTcmgCreateTime() {
        return tcmgCreateTime;
    }

    public void setTcmgCreateTime(Calendar tcmgCreateTime) {
        this.tcmgCreateTime = tcmgCreateTime;
    }

    /**
     * 获取体质类型名称
     * @return
     */
    public String getStrtCmgCorporeityType() throws Exception{
        if(StringUtils.isNotBlank(this.getTcmgCorporeityType())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TZLX,this.getTcmgCorporeityType());
            if(value != null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }
}
