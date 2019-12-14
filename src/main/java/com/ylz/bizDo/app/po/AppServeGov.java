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

/**政府补贴类型
 * Created by zzl on 2017/8/16.
 */
@Entity
@Table(name="APP_SERVE_GOV")
public class AppServeGov extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "GOV_VALUE",length = 100)
    private String govValue;//政府补贴类型值
    @Column(name = "GOV_TITLE",length = 100)
    private String govTitle;//政府补贴名称
    @Column(name = "GOV_AREA_CODE",length = 50)
    private String govAreaCode;//区域编号
    @Column(name = "GOV_STATE",length = 10)
    private String govState;//标记状态
    @Column(name = "GOV_DEPT_ID",length = 36)
    private String govDeptId;//单位id
    @Column(name = "GOV_JC_TYPE",length = 10)
    private String govJcType;//是否是系统经济类型
    @Column(name = "GOV_CREATE_ID",length = 36)
    private String govCreateId;//创建人id
    @Column(name = "GOV_CREATE_TIME")
    private Calendar govCreateTime;//创建时间
    @Column(name = "GOV_OPEN_STATE",length = 10)
    private String govOpenState;//是否开启
    @Column(name = "GOV_LEVEL",length = 10)
    private String govLevel;//等级
    @Column(name = "GOV_TAB_STATE",length = 10)
    private String govTabState;//标记状态
    @Column(name = "GOV_TYPE",length = 10)
    private String govType;//类型
    @Column(name = "GOV_MONEY",length = 100)
    private String govMoney="0";//价格
    @Column(name="GOV_HC_PROJECT_ID", length=36)
    private String govHcProjectId;//医保项目编号
    @Column(name="GOV_HC_PROJECT_NAME",length=255)
    private String govHcProjectName;//医保项目名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGovValue() {
        return govValue;
    }

    public void setGovValue(String govValue) {
        this.govValue = govValue;
    }

    public String getGovTitle() {
        return govTitle;
    }

    public void setGovTitle(String govTitle) {
        this.govTitle = govTitle;
    }

    public String getGovAreaCode() {
        return govAreaCode;
    }

    public void setGovAreaCode(String govAreaCode) {
        this.govAreaCode = govAreaCode;
    }

    public String getGovState() {
        return govState;
    }

    public void setGovState(String govState) {
        this.govState = govState;
    }

    public String getGovDeptId() {
        return govDeptId;
    }

    public void setGovDeptId(String govDeptId) {
        this.govDeptId = govDeptId;
    }

    public String getGovJcType() {
        return govJcType;
    }

    public void setGovJcType(String govJcType) {
        this.govJcType = govJcType;
    }

    public String getGovCreateId() {
        return govCreateId;
    }

    public void setGovCreateId(String govCreateId) {
        this.govCreateId = govCreateId;
    }

    public Calendar getGovCreateTime() {
        return govCreateTime;
    }

    public void setGovCreateTime(Calendar govCreateTime) {
        this.govCreateTime = govCreateTime;
    }

    public String getGovOpenState() {
        return govOpenState;
    }

    public void setGovOpenState(String govOpenState) {
        this.govOpenState = govOpenState;
    }

    public String getGovLevel() {
        return govLevel;
    }

    public void setGovLevel(String govLevel) {
        this.govLevel = govLevel;
    }

    public String getGovTabState() {
        return govTabState;
    }

    public void setGovTabState(String govTabState) {
        this.govTabState = govTabState;
    }

    public String getGovType() {
        return govType;
    }

    public void setGovType(String govType) {
        this.govType = govType;
    }

    public String getGovMoney() {
        return govMoney;
    }

    public void setGovMoney(String govMoney) {
        this.govMoney = govMoney;
    }

    /**
     * 获取类型名称
     * @return
     */
    public String getStrGovType() throws Exception{
        if(StringUtils.isNotBlank(this.getGovType())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_GOVTYPE, this.getGovType());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getGovHcProjectId() {
        return govHcProjectId;
    }

    public void setGovHcProjectId(String govHcProjectId) {
        this.govHcProjectId = govHcProjectId;
    }

    public String getGovHcProjectName() {
        return govHcProjectName;
    }

    public void setGovHcProjectName(String govHcProjectName) {
        this.govHcProjectName = govHcProjectName;
    }
}
