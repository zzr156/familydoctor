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

/** 服务包数据表
 * Created by zzl on 2017/8/11.
 */
@Entity
@Table(name = "APP_SERVE_PACKAGE")
public class AppServePackage extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "SERPK_NAME",length = 200 )
    private String serpkName;//服务包名称
    @Column(name = "SERPK_AREA_CODE",length = 100)
    private String serpkAreaCode;//新增服务包区域编码
    @Column(name = "SERPK_LEVE",length = 10)
    private String serpkLeve;//新增服务包所属级别(0系统 1)
    @Column(name = "SERPK_OPEN_STATE",length = 10)
    private String serpkOpenState;//是否开启频次状态
    @Column(name = "SERPK_NUM",length = 10)
    private String serpkNum="0";//频次
    @Column(name = "SERPK_REMARK")
    private String serpkRemark;//服务介绍
    @Column(name = "SERPK_IMAGE_URL",length = 100)
    private String serpkImageUrl;//服务图标
    @Column(name = "SERPK_CREATE_TIME")
    private Calendar serpkCreateTime;//创建时间
    @Column(name = "SERPK_CREATE_ID",length = 36)
    private String serpkCreateId;//创建者id
    @Column(name = "SERPK_VALUE",length = 50)
    private String serpkValue;//服务包值
    @Column(name = "SERPK_TYPE",length = 10)
    private String serpkType;//服务类型
    @Column(name = "SERPK_DEPT_ID",length = 36)
    private String serpkDeptId;//单位id
    @Column(name = "SERPK_BASE_TYPE",length = 10)
    private String serpkBaseType;//是否是特色服务（0否 1是）
    @Column(name = "SERPK_STATE",length = 10)
    private String serpkState;//是否开放
    @Column(name = "SERPK_TAB_STATE",length = 10)
    private String serpkTabState;//标记状态
    @Column(name = "SERPK_INTERVAL_TYPE",length = 10)
    private String serpkIntervalType;//间隔方式
    @Column(name = "SERPK_TIME",length = 10)
    private String serpkTime;//频次
    @Column(name = "SERPK_ITEM",length = 10)
    private String serpkItem;//服务事项
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerpkName() {
        return serpkName;
    }

    public void setSerpkName(String serpkName) {
        this.serpkName = serpkName;
    }

    public String getSerpkAreaCode() {
        return serpkAreaCode;
    }

    public void setSerpkAreaCode(String serpkAreaCode) {
        this.serpkAreaCode = serpkAreaCode;
    }

    public String getSerpkLeve() {
        return serpkLeve;
    }

    public void setSerpkLeve(String serpkLeve) {
        this.serpkLeve = serpkLeve;
    }

    public String getSerpkOpenState() {
        return serpkOpenState;
    }

    public void setSerpkOpenState(String serpkOpenState) {
        this.serpkOpenState = serpkOpenState;
    }

    public String getSerpkNum() {
        if(StringUtils.isBlank(serpkNum)){
            return "";
        }
        return serpkNum;
    }

    public void setSerpkNum(String serpkNum) {
        this.serpkNum = serpkNum;
    }

    public String getSerpkRemark() {
        return serpkRemark;
    }

    public void setSerpkRemark(String serpkRemark) {
        this.serpkRemark = serpkRemark;
    }

    public String getSerpkImageUrl() {
        return serpkImageUrl;
    }

    public void setSerpkImageUrl(String serpkImageUrl) {
        this.serpkImageUrl = serpkImageUrl;
    }

    public Calendar getSerpkCreateTime() {
        return serpkCreateTime;
    }

    public void setSerpkCreateTime(Calendar serpkCreateTime) {
        this.serpkCreateTime = serpkCreateTime;
    }

    public String getSerpkCreateId() {
        return serpkCreateId;
    }

    public void setSerpkCreateId(String serpkCreateId) {
        this.serpkCreateId = serpkCreateId;
    }

    public String getSerpkValue() {
        return serpkValue;
    }

    public void setSerpkValue(String serpkValue) {
        this.serpkValue = serpkValue;
    }

    public String getSerpkType() {
        return serpkType;
    }

    public void setSerpkType(String serpkType) {
        this.serpkType = serpkType;
    }

    public String getSerpkDeptId() {
        return serpkDeptId;
    }

    public void setSerpkDeptId(String serpkDeptId) {
        this.serpkDeptId = serpkDeptId;
    }

    public String getSerpkBaseType() {
        return serpkBaseType;
    }

    public void setSerpkBaseType(String serpkBaseType) {
        this.serpkBaseType = serpkBaseType;
    }

    public String getSerpkState() {
        return serpkState;
    }

    public void setSerpkState(String serpkState) {
        this.serpkState = serpkState;
    }

    public String getSerpkTabState() {
        return serpkTabState;
    }

    public void setSerpkTabState(String serpkTabState) {
        this.serpkTabState = serpkTabState;
    }

    /**
     * 获取是否开启频次名称
     * @return
     */
    public String getStrOpenState() throws Exception{
        if(StringUtils.isNotBlank(this.getSerpkOpenState())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFCOMMON, this.getSerpkOpenState());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 获取时间
     * @return
     */
    public String getStrCeateTime(){
        if(this.getSerpkCreateTime()!=null){
            return ExtendDate.getYMD(this.getSerpkCreateTime());
        }
        return "";
    }

    /**
     * 获取服务图标名称
     * @return
     */
    public String getSerImageName() throws Exception{
        if(StringUtils.isNotBlank(this.getSerpkImageUrl())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ICON, this.getSerpkImageUrl());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 获取单位名称
     * @return
     */
    public String getDeptName(){
        if(StringUtils.isNotBlank(this.getSerpkDeptId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getSerpkDeptId());
            if(dept!=null){
                return dept.getHospName();
            }
        }
        return "";
    }

    public String getSerpkIntervalType() {
        return serpkIntervalType;
    }

    public void setSerpkIntervalType(String serpkIntervalType) {
        this.serpkIntervalType = serpkIntervalType;
    }

    public String getSerpkTime() {
        return serpkTime;
    }

    public void setSerpkTime(String serpkTime) {
        this.serpkTime = serpkTime;
    }

    public String getSerpkItem() {
        return serpkItem;
    }

    public void setSerpkItem(String serpkItem) {
        this.serpkItem = serpkItem;
    }
}
