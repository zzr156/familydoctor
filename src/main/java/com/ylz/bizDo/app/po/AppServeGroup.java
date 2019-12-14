package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

/**
 * 服务组合表
 * Created by zzl on 2017/8/13.
 */
@Entity
@Table(name = "APP_SERVE_GROUP")
public class AppServeGroup extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "SERG_VALUE",length = 100)
    private String sergValue;//组合值
    @Column(name = "SERG_AREA_CODE",length = 100)
    private String sergAreaCode;//区域编号
    @Column(name = "SERG_GROUP_FEE",length = 100)
    private String sergGroupFee;//组合费用
    @Column(name = "SERG_CREATE_ID",length = 36)
    private String sergCreateId;//创建人id
    @Column(name = "SERG_CREATE_TIME")
    private Calendar sergCreateTime;//创建时间
    @Column(name = "SERG_OBJECT_VALUE",length = 50)
    private String sergObjectValue;//服务人群编号
    @Column(name = "SERG_PK_VALUE",length = 100)
    private String sergPkValue;//服务内容
    @Column(name = "SERG_PK_TITLE")
    private String sergPkTitle;//服务内容名称
    @Column(name = "SERG_OBJECT_TITLE",length = 200)
    private String sergObjectTitle;//服务人群名称
    @Column(name = "SERG_PK_TYPE")
    private String sergPkType;//服务内容类型（0基本 1特色）
    @Column(name = "SERG_OBJECT_TYPE")
    private String sergObjectType;//服务人群类型
    @Column(name = "SERG_JC_STATE",length = 10)
    private String sergJcState="0";//是否是系统组合(0否 1是)
    @Column(name = "SERG_DEPT_ID",length = 36)
    private String sergDeptId;//单位id
    @Column(name = "SERG_OPEN_STATE",length = 10)
    private String sergOpenState="0";//开启状态
    @Column(name = "SERG_LEVEL",length = 10)
    private String sergLevel;//级别
    @Column(name = "SERG_TAB_STATE",length = 10)
    private String sergTabState;//标记状态
    @Column(name = "SERG_OBJECT_ID")//长度类型是text
    private String sergObjectId;//人群id
    @Column(name = "SERG_PK_ID")//长度类型是text
    private String sergPkId;//服务id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSergValue() {
        return sergValue;
    }

    public void setSergValue(String sergValue) {
        this.sergValue = sergValue;
    }

    public String getSergAreaCode() {
        return sergAreaCode;
    }

    public void setSergAreaCode(String sergAreaCode) {
        this.sergAreaCode = sergAreaCode;
    }

    public String getSergGroupFee() {
        return sergGroupFee;
    }

    public void setSergGroupFee(String sergGroupFee) {
        this.sergGroupFee = sergGroupFee;
    }

    public String getSergCreateId() {
        return sergCreateId;
    }

    public void setSergCreateId(String sergCreateId) {
        this.sergCreateId = sergCreateId;
    }

    public Calendar getSergCreateTime() {
        return sergCreateTime;
    }

    public void setSergCreateTime(Calendar sergCreateTime) {
        this.sergCreateTime = sergCreateTime;
    }

    public String getSergObjectValue() {
        return sergObjectValue;
    }

    public void setSergObjectValue(String sergObjectValue) {
        this.sergObjectValue = sergObjectValue;
    }

    public String getSergPkValue() {
        return sergPkValue;
    }

    public void setSergPkValue(String sergPkValue) {
        this.sergPkValue = sergPkValue;
    }

    public String getSergPkTitle() {
        return sergPkTitle;
    }

    public void setSergPkTitle(String sergPkTitle) {
        this.sergPkTitle = sergPkTitle;
    }

    public String getSergObjectTitle() {
        return sergObjectTitle;
    }

    public void setSergObjectTitle(String sergObjectTitle) {
        this.sergObjectTitle = sergObjectTitle;
    }

    public String getSergPkType() {
        return sergPkType;
    }

    public void setSergPkType(String sergPkType) {
        this.sergPkType = sergPkType;
    }

    public String getSergObjectType() {
        return sergObjectType;
    }

    public void setSergObjectType(String sergObjectType) {
        this.sergObjectType = sergObjectType;
    }

    public String getSergJcState() {
        return sergJcState;
    }

    public void setSergJcState(String sergJcState) {
        this.sergJcState = sergJcState;
    }

    public String getSergDeptId() {
        return sergDeptId;
    }

    public void setSergDeptId(String sergDeptId) {
        this.sergDeptId = sergDeptId;
    }

    public String getSergOpenState() {
        return sergOpenState;
    }

    public void setSergOpenState(String sergOpenState) {
        this.sergOpenState = sergOpenState;
    }

    public String getSergLevel() {
        return sergLevel;
    }

    public void setSergLevel(String sergLevel) {
        this.sergLevel = sergLevel;
    }

    public String getSergTabState() {
        return sergTabState;
    }

    public void setSergTabState(String sergTabState) {
        this.sergTabState = sergTabState;
    }

    public String getStrSergPkTitle(){
        String ss = "";
        if(StringUtils.isNotBlank(this.getSergPkTitle())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            String[] strs = this.getSergPkTitle().split(",");
            for(String str:strs){
                List<AppServePackage> ls = dao.getServiceDo().loadByPk(AppServePackage.class,"serpkName",str);
                if(ls!=null&&ls.size()>0){
                    if(str.equals(ls.get(0).getSerpkName())){
                        if(StringUtils.isNotBlank(ls.get(0).getSerpkTime())){
                            if(StringUtils.isBlank(ss)){
                                if(!"0".equals(ls.get(0).getSerpkTime())){
                                    ss=ls.get(0).getSerpkName()+"("+ls.get(0).getSerpkTime()+"次/年)";
                                }else{
                                    ss=ls.get(0).getSerpkName();
                                }
                            }else{
                                if(!"0".equals(ls.get(0).getSerpkTime())){
                                    ss+=","+ls.get(0).getSerpkName()+"("+ls.get(0).getSerpkTime()+"次/年)";
                                }else{
                                    ss+=","+ls.get(0).getSerpkName();
                                }
                            }
                        }else{
                            if(StringUtils.isBlank(ss)){
                                ss=ls.get(0).getSerpkName();
                            }else{
                                ss+=","+ls.get(0).getSerpkName();
                            }
                        }
                    }
                }
            }
        }
        return ss;
    }

    public String getSergObjectId() {
        return sergObjectId;
    }

    public void setSergObjectId(String sergObjectId) {
        this.sergObjectId = sergObjectId;
    }

    public String getSergPkId() {
        return sergPkId;
    }

    public void setSergPkId(String sergPkId) {
        this.sergPkId = sergPkId;
    }
}
