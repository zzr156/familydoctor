package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 市权限签约管理表
 * Created by zzl on 2017/7/26.
 */
@Entity
@Table(name = "APP_SIGN_SETTING")
public class AppSignSetting extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "SIGNS_CREATE_ID", length = 36)
    private String signsCreateId;//创建者id
    @Column(name = "SIGNS_OPEN_WORK", length = 10)
    private String signsOpenWork;//是否开启选择工作类型
    @Column(name = "SIGNS_WORK_TYPE", length = 10)
    private String signsWorkType;//工作类型
    @Column(name = "SIGNS_SUBSIDY_TYPE", length = 10)
    private String signsSubsidyType;//政府补贴类型
    @Column(name = "SIGNS_FREE", length = 50)
    private String signsFree;//签约费用
    @Column(name = "SIGNS_SERVER_TYPE", length = 100)
    private String signsServerType;//居民服务类型选择
    @Column(name = "SIGNS_SER_VALUE", length = 100)
    private String signsSerValue;//服务包
    @Column(name = "SIGNS_CREATE_TIME")
    private Calendar signsCreateTime;//创建时间
    @Column(name = "SIGNS_AREA_CODE", length = 50)
    private String signsAreaCode;//区域编码
    @Column(name = "SIGNS_ISORNOT", length = 10)
    private String signsIsOrNot;//是否对接基卫
    @Column(name = "SIGNS_JJ_TYPE", length = 100)
    private String signsJjType;//经济类型
    @Column(name = "SIGNS_MEAL_VALUE")
    private String signsMealValue;//套餐
    @Column(name = "SIGNS_DEPT_ID", length = 36)
    private String signsDeptId;//单位id
    @Column(name = "SIGNS_CITY_NAME", length = 50)
    private String signsCityName;//市名称
    @Column(name = "SIGNS_SIGN_TYPE", length = 10)
    private String signsSignType;//签约方式 1个人 2家庭
    @Column(name = "SIGNS_OPEN_JD", length = 10)
    private String signsOpenJd = "0";//是否开启建档才可签约(0关闭 1开启)
    @Column(name = "SER_SIGN_STATE", length = 10)
    private String serSignState;//签约时间状态 0不启用 1 启用
    @Column(name = "SER_SIGN_DAY", length = 10)
    private String serSignDay;//天数
    @Column(name = "SER_IMAGE_STATE", length = 10)
    private String serImageState;//是否必须上传附件(0非必须 1必须)
    @Column(name = "SER_SIGN_MODIFY_STATE", length = 10)
    private String serSignModifyState;//签约修改时间状态 0不启用 1 启用
    @Column(name = "SER_EVALUATION_STATE", length = 10)
    private String serevaluationState;//pos机是否开启评价状态 0不启用 1 启用
    @Column(name = "SER_JD_BEFORE_SIGN", length = 10)
    private String serJdBeforeSign;// 是否开启签约前先建档功能（1-启用）
    @Column(name = "SER_RESIT_ASSESS", length = 10)
    private String serResitAssess;// 是否开启绩效考核补录考核功能（1-启用）
    @Column(name = "SER_OPEN_YEAR", length = 10)
    private String serOpenYear = "0";//是否开启本年度协议（1-启用）
    @Column(name = "SER_SQYL_ONLINE", length = 10)
    private String serSqylOnline;// 基卫项目是否在线（0-不在线）


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignsCreateId() {
        return signsCreateId;
    }

    public void setSignsCreateId(String signsCreateId) {
        this.signsCreateId = signsCreateId;
    }

    public String getSignsOpenWork() {
        return signsOpenWork;
    }

    public void setSignsOpenWork(String signsOpenWork) {
        this.signsOpenWork = signsOpenWork;
    }

    public String getSignsWorkType() {
        return signsWorkType;
    }

    public void setSignsWorkType(String signsWorkType) {
        this.signsWorkType = signsWorkType;
    }

    public String getSignsSubsidyType() {
        return signsSubsidyType;
    }

    public void setSignsSubsidyType(String signsSubsidyType) {
        this.signsSubsidyType = signsSubsidyType;
    }

    public String getSignsFree() {
        return signsFree;
    }

    public void setSignsFree(String signsFree) {
        this.signsFree = signsFree;
    }

    public String getSignsServerType() {
        return signsServerType;
    }

    public void setSignsServerType(String signsServerType) {
        this.signsServerType = signsServerType;
    }

    public String getSignsSerValue() {
        return signsSerValue;
    }

    public void setSignsSerValue(String signsSerValue) {
        this.signsSerValue = signsSerValue;
    }

    public Calendar getSignsCreateTime() {
        return signsCreateTime;
    }

    public void setSignsCreateTime(Calendar signsCreateTime) {
        this.signsCreateTime = signsCreateTime;
    }

    public String getSignsAreaCode() {
        return signsAreaCode;
    }

    public void setSignsAreaCode(String signsAreaCode) {
        this.signsAreaCode = signsAreaCode;
    }

    public String getSignsIsOrNot() {
        return signsIsOrNot;
    }

    public void setSignsIsOrNot(String signsIsOrNot) {
        this.signsIsOrNot = signsIsOrNot;
    }

    public String getSignsJjType() {
        return signsJjType;
    }

    public void setSignsJjType(String signsJjType) {
        this.signsJjType = signsJjType;
    }

    public String getSignsMealValue() {
        return signsMealValue;
    }

    public void setSignsMealValue(String signsMealValue) {
        this.signsMealValue = signsMealValue;
    }

    public String getSignsDeptId() {
        return signsDeptId;
    }

    public void setSignsDeptId(String signsDeptId) {
        this.signsDeptId = signsDeptId;
    }

    /**
     * 获取创建者姓名
     *
     * @return
     */
    public String getCreateName() {
        String createName = "";
        if (StringUtils.isNotBlank(this.getSignsCreateId())) {
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdUser user = (CdUser) dao.getServiceDo().find(CdUser.class, this.getSignsCreateId());
            if (user != null) {
                createName = user.getUserName();
            } else {
                AppDrUser drUser = (AppDrUser) dao.getServiceDo().find(AppDrUser.class, this.getSignsCreateId());
                if (drUser != null) {
                    createName = drUser.getDrName();
                }
            }
        }
        return createName;
    }

    public String getSignsCityName() {
        return signsCityName;
    }

    public void setSignsCityName(String signsCityName) {
        this.signsCityName = signsCityName;
    }

    public String getSignsSignType() {
        return signsSignType;
    }

    public void setSignsSignType(String signsSignType) {
        this.signsSignType = signsSignType;
    }

    /**
     * 查询签约方式名称
     *
     * @return
     */
    public String getSignTypeName() throws Exception {
        String signTypeName = "";
        if (StringUtils.isNotBlank(this.getSignsSignType())) {
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_MODULE_MANY_SIGN, this.getSignsSignType());
            if (value != null) {
                signTypeName = value.getCodeTitle();
            }
        }
        return signTypeName;
    }

    public String getSignsOpenJd() {
        return signsOpenJd;
    }

    public void setSignsOpenJd(String signsOpenJd) {
        this.signsOpenJd = signsOpenJd;
    }

    public String getSerSignState() {
        return serSignState;
    }

    public void setSerSignState(String serSignState) {
        this.serSignState = serSignState;
    }

    public String getSerSignDay() {
        return serSignDay;
    }

    public void setSerSignDay(String serSignDay) {
        this.serSignDay = serSignDay;
    }

    public String getSerImageState() {
        return serImageState;
    }

    public void setSerImageState(String serImageState) {
        this.serImageState = serImageState;
    }

    public String getSerSignModifyState() {
        return serSignModifyState;
    }

    public void setSerSignModifyState(String serSignModifyState) {
        this.serSignModifyState = serSignModifyState;
    }

    public String getSerevaluationState() {
        return serevaluationState;
    }

    public void setSerevaluationState(String serevaluationState) {
        this.serevaluationState = serevaluationState;
    }

    public String getSerJdBeforeSign() {
        return serJdBeforeSign;
    }

    public void setSerJdBeforeSign(String serJdBeforeSign) {
        this.serJdBeforeSign = serJdBeforeSign;
    }

    public String getSerResitAssess() {
        return serResitAssess;
    }

    public void setSerResitAssess(String serResitAssess) {
        this.serResitAssess = serResitAssess;
    }

    public String getSerOpenYear() {
        return serOpenYear;
    }

    public void setSerOpenYear(String serOpenYear) {
        this.serOpenYear = serOpenYear;
    }

    public String getSerSqylOnline() {
        return serSqylOnline;
    }

    public void setSerSqylOnline(String serSqylOnline) {
        this.serSqylOnline = serSqylOnline;
    }
}
