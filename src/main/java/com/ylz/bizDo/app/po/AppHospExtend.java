package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**续约提醒天数设置表
 * Created by zzl on 2017/7/13.
 */
@Entity
@Table(name = "APP_HOSP_EXTEND")
//@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_WRITE)//要使用hibernate的二级缓存Cache的注释
public class AppHospExtend extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "EXT_HOSP_ID",length = 36)
    private String extHospId;//机构id
    @Column(name = "EXT_CREATE_TIME",length = 36)
    private Calendar extCreateTime;//创建时间
    @Column(name = "EXT_RED_DAY",length = 10)
    private String extRedDay;//提醒天数（红）
    @Column(name = "EXT_YELLOW_DAY",length = 10)
    private String extYellowDay;//提醒天数（黄）
    @Column(name = "EXT_GREEN_DAY",length = 10)
    private String extGreenDay;//提醒天数（绿）
    @Column(name = "EXT_HRE_STATE",length = 10)
    private String extHreState;//是否同意签约
    @Column(name = "EXT_FORMULA_MODE",length = 10)
    private String extFormulaMode;//上限方式

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtHospId() {
        return extHospId;
    }

    public void setExtHospId(String extHospId) {
        this.extHospId = extHospId;
    }

    public Calendar getExtCreateTime() {
        return extCreateTime;
    }

    public void setExtCreateTime(Calendar extCreateTime) {
        this.extCreateTime = extCreateTime;
    }

    public String getExtRedDay() {
        return extRedDay;
    }

    public void setExtRedDay(String extRedDay) {
        this.extRedDay = extRedDay;
    }

    public String getExtYellowDay() {
        return extYellowDay;
    }

    public void setExtYellowDay(String extYellowDay) {
        this.extYellowDay = extYellowDay;
    }

    public String getExtGreenDay() {
        return extGreenDay;
    }

    public void setExtGreenDay(String extGreenDay) {
        this.extGreenDay = extGreenDay;
    }

    public String getExtHreState() {
        return extHreState;
    }

    public void setExtHreState(String extHreState) {
        this.extHreState = extHreState;
    }

    public String getExtFormulaMode() {
        return extFormulaMode;
    }

    public void setExtFormulaMode(String extFormulaMode) {
        this.extFormulaMode = extFormulaMode;
    }
}
