package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 服务人群
 * Created by zzl on 2017/8/11.
 */
@Entity
@Table(name= "APP_SERVE_OBJECT")
public class AppServeObject extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "SERO_NAME",length = 100)
    private String seroName;//服务人群名称
    @Column(name = "SERO_AREA_CODE",length = 50)
    private String seroAreaCode;//新增服务人群区域编码
    @Column(name = "SERO_LEVEL",length = 10)
    private String seroLevel;//级别
    @Column(name = "SERO_STATE",length = 10)
    private String seroState;//是否设为基本公共卫生服务人群
    @Column(name = "SERO_VALUE",length = 10)
    private String seroValue;//服务人群值
    @Column(name = "SERO_CREATE_ID",length = 36)
    private String seroCreateId;//创建人Id
    @Column(name = "SERO_CREATE_TIME")
    private Calendar seroCreateTime;//创建时间
    @Column(name = "SERO_DEPT_ID",length = 36)
    private String seroDeptId;//单位id
    @Column(name = "SERO_OPEN_STATE",length = 10)
    private String seroOpenState;//是否开启 0否 1是
    @Column(name = "SERO_TYPE",length = 10)
    private String seroType;//类型
    @Column(name = "SERO_TAB_STATE",length = 10)
    private String seroTabState;//标记状态
    @Column(name = "SERO_FWTYPE",length = 10)
    private String seroFwType;//服务人群类型（1普通人群 2儿童(0-6岁) 3孕产妇 4老年人 5高血压 6糖尿病 7严重精神障碍 8结核病 9残疾人）或经济类型
    @Column(name = "SERO_LABEL_TYPE",length = 10)
    private String seroLabelType;//所属服务类型 3服务人群 4经济类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeroName() {
        return seroName;
    }

    public void setSeroName(String seroName) {
        this.seroName = seroName;
    }

    public String getSeroAreaCode() {
        return seroAreaCode;
    }

    public void setSeroAreaCode(String seroAreaCode) {
        this.seroAreaCode = seroAreaCode;
    }

    public String getSeroLevel() {
        return seroLevel;
    }

    public void setSeroLevel(String seroLevel) {
        this.seroLevel = seroLevel;
    }

    public String getSeroState() {
        return seroState;
    }

    public void setSeroState(String seroState) {
        this.seroState = seroState;
    }

    public String getSeroValue() {
        return seroValue;
    }

    public void setSeroValue(String seroValue) {
        this.seroValue = seroValue;
    }

    public String getSeroCreateId() {
        return seroCreateId;
    }

    public void setSeroCreateId(String seroCreateId) {
        this.seroCreateId = seroCreateId;
    }

    public Calendar getSeroCreateTime() {
        return seroCreateTime;
    }

    public void setSeroCreateTime(Calendar seroCreateTime) {
        this.seroCreateTime = seroCreateTime;
    }

    public String getSeroDeptId() {
        return seroDeptId;
    }

    public void setSeroDeptId(String seroDeptId) {
        this.seroDeptId = seroDeptId;
    }

    public String getSeroOpenState() {
        return seroOpenState;
    }

    public void setSeroOpenState(String seroOpenState) {
        this.seroOpenState = seroOpenState;
    }

    public String getSeroType() {
        return seroType;
    }

    public void setSeroType(String seroType) {
        this.seroType = seroType;
    }

    public String getSeroTabState() {
        return seroTabState;
    }

    public void setSeroTabState(String seroTabState) {
        this.seroTabState = seroTabState;
    }

    /**
     * 处理时间
     * @return
     */
    public String getStrCreateTime(){
        if(this.getSeroCreateTime()!=null){
            return ExtendDate.getYMD_h_m(this.getSeroCreateTime());
        }
        return "";
    }

    /**
     * 获取状态名称
     * @return
     */
    public String getStateName() throws Exception{
        if(StringUtils.isNotBlank(this.getSeroState())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFCOMMON, this.getSeroState());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getSeroFwType() {
        return seroFwType;
    }

    public void setSeroFwType(String seroFwType) {
        this.seroFwType = seroFwType;
    }

    public String getSeroLabelType() {
        return seroLabelType;
    }

    public void setSeroLabelType(String seroLabelType) {
        this.seroLabelType = seroLabelType;
    }
}
