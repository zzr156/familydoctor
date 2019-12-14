package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**工作类型表
 * Created by zzl on 2017/8/17.
 */
@Entity
@Table(name = "APP_WORK_TYPE")
public class AppWorkType extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "WORK_VALUE",length = 10)
    private String workValue;//工作类型值
    @Column(name = "WORK_TITLE",length =100)
    private String workTitle;//工作类型名称
    @Column(name = "WORK_DEPT_ID",length = 36)
    private String workDeptId;//单位id
    @Column(name = "WORK_CREATE_ID",length = 36)
    private String workCreateId;//创建人id
    @Column(name = "WORK_CREATE_TIME")
    private Calendar workCreateTime;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkValue() {
        return workValue;
    }

    public void setWorkValue(String workValue) {
        this.workValue = workValue;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public String getWorkDeptId() {
        return workDeptId;
    }

    public void setWorkDeptId(String workDeptId) {
        this.workDeptId = workDeptId;
    }

    public String getWorkCreateId() {
        return workCreateId;
    }

    public void setWorkCreateId(String workCreateId) {
        this.workCreateId = workCreateId;
    }

    public Calendar getWorkCreateTime() {
        return workCreateTime;
    }

    public void setWorkCreateTime(Calendar workCreateTime) {
        this.workCreateTime = workCreateTime;
    }
}
