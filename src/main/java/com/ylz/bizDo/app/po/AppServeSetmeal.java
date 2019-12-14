package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

/**套餐表
 * Created by zzl on 2017/8/13.
 */
@Entity
@Table(name = "APP_SERVE_SETMEAL")
public class AppServeSetmeal extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "SERSM_VALUE",length = 50)
    private String sersmValue;//套餐值
    @Column(name = "SERSM_NAME",length = 100)
    private String sersmName;//套餐名称
    @Column(name = "SERSM_GROUP_VALUE")
    private String sersmGroupValue;//组合值
    @Column(name = "SERSM_OBJECT_VALUE")
    private String sersmObjectValue;//服务人群值
    @Column(name = "SERSM_OBJECT_TITLE")
    private String sersmObjectTitle;//服务人群名称
    @Column(name = "SERSM_OBJECT_TYPE")
    private String sersmObjectType;//服务人群类型
    @Column(name = "SERSM_PK_VALUE")
    private String sersmPkValue;//服务内容值
    @Column(name = "SERSM_PK_TITLE")
    private String sersmPkTitle;//服务内容名称
    @Column(name = "SERSM_PK_TYPE")
    private String sersmPkType;//服务内容类型
    @Column(name = "SERSM_TOTAL_FEE",length = 100)
    private String sersmTotalFee;//套餐总费用
    @Column(name = "SERSM_DOWN_STATE",length = 10)
    private String sersmDownState;//是否含减免
    @Column(name = "SERSM_JJ_TYPE",length = 50)
    private String sersmJjType;////减免对象经济类型
    @Column(name = "SERSM_SUBSIDY_WAY",length = 10)
    private String sersmSubsidyWay;//政府补贴方式
    @Column(name = "SERSM_FEE",length = 100)
    private String sersmFee;//套餐实付金额
    @Column(name = "SERSM_YXTIME_TYPE",length = 10)
    private String sersmYxTimeType;//套餐有效期方式
    @Column(name = "SERSM_START_TIME",length = 50)
    private String sersmStartTime;//有效开始时间
    @Column(name = "SERSM_END_TIME",length = 50)
    private String sersmEndTime;//有效结束时间
    @Column(name = "SERSM_BG_DR",length = 10)
    private String sersmBgDr="0";//有效期间是否可变更医生 0否 1是
    @Column(name = "SERSM_BOOK")
    private String sersmBook;//协议
    @Column(name = "SERSM_IMAGE_URL")
    private String sersmImageUrl;//图片url
    @Column(name = "SERSM_IMAGE_NAME")
    private String sersmImageName;//图片名称
    @Column(name = "SERSM_CREATE_DEPT",length = 36)
    private String sersmCreateDept;//创建单位
    @Column(name = "SERSM_CREATE_ID",length = 36)
    private String sersmCreateId;//创建人id
    @Column(name = "SERSM_CREATE_TIME")
    private Calendar sersmCreateTime;//创建时间
    @Column(name = "SERSM_AREA_CODE",length = 100)
    private String sersmAreaCode;//区域编号
    @Column(name = "SERSM_JC_STATE",length = 10)
    private String sersmJcState="0";//是否是基础服务套餐 0否 1是
    @Column(name = "SERSM_OPEN_STATE",length = 10)
    private String sersmOpenState="0";//是否开启 0否 1是
    @Column(name = "SERSM_LEVEL",length = 10)
    private String sersmLevel;//级别
    @Column(name = "SERSM_JJ_ID")
    private String sersmJjId;//经济类型id
    @Column(name = "SERSM_TAB_STATE",length = 10)
    private String sersmTabState;//标记状态
    @Column(name = "SERSM_OPEN_AREA")
    private String sersmOpenArea;//开放区域
    @Column(name = "SERSM_ONE_FEE",length = 255)
    private String sersmOneFee;//个人需支付费用
    @Column(name = "SERSM_TOTAL_ONE_FEE",length = 20)
    private String sersmTotalOneFee;//个人需付总金额
    @Column(name = "SERSM_GROUP_ID")//长度类型 text
    private String sersmGroupId;//组合id

    @Column(name = "SERSM_IS_DISCOUNT",length = 10)
    private String sersmIsDiscount;//泉州是否个人支付优惠（0否 1是）
    @Column(name = "SERSM_DISCOUNT",length = 20)
    private String  sersmDiscount="0";//泉州个人支付优惠金额

    @Column(name = "SERSM_CODE",length = 50)
    private String sersmCode;//福州签约编号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSersmValue() {
        return sersmValue;
    }

    public void setSersmValue(String sersmValue) {
        this.sersmValue = sersmValue;
    }

    public String getSersmName() {
        return sersmName;
    }

    public void setSersmName(String sersmName) {
        this.sersmName = sersmName;
    }

    public String getSersmObjectValue() {
        return sersmObjectValue;
    }

    public void setSersmObjectValue(String sersmObjectValue) {
        this.sersmObjectValue = sersmObjectValue;
    }

    public String getSersmObjectTitle() {
        return sersmObjectTitle;
    }

    public void setSersmObjectTitle(String sersmObjectTitle) {
        this.sersmObjectTitle = sersmObjectTitle;
    }

    public String getSersmPkValue() {
        return sersmPkValue;
    }

    public void setSersmPkValue(String sersmPkValue) {
        this.sersmPkValue = sersmPkValue;
    }

    public String getSersmPkTitle() {
        return sersmPkTitle;
    }

    public void setSersmPkTitle(String sersmPkTitle) {
        this.sersmPkTitle = sersmPkTitle;
    }

    public String getSersmTotalFee() {
        return sersmTotalFee;
    }

    public void setSersmTotalFee(String sersmTotalFee) {
        this.sersmTotalFee = sersmTotalFee;
    }

    public String getSersmDownState() {
        return sersmDownState;
    }

    public void setSersmDownState(String sersmDownState) {
        this.sersmDownState = sersmDownState;
    }

    public String getSersmJjType() {
        return sersmJjType;
    }

    public void setSersmJjType(String sersmJjType) {
        this.sersmJjType = sersmJjType;
    }

    public String getSersmSubsidyWay() {
        return sersmSubsidyWay;
    }

    public void setSersmSubsidyWay(String sersmSubsidyWay) {
        this.sersmSubsidyWay = sersmSubsidyWay;
    }

    public String getSersmFee() {
        return sersmFee;
    }

    public void setSersmFee(String sersmFee) {
        this.sersmFee = sersmFee;
    }

    public String getSersmYxTimeType() {
        return sersmYxTimeType;
    }

    public void setSersmYxTimeType(String sersmYxTimeType) {
        this.sersmYxTimeType = sersmYxTimeType;
    }

    public String getSersmStartTime() {
        return sersmStartTime;
    }

    public void setSersmStartTime(String sersmStartTime) {
        this.sersmStartTime = sersmStartTime;
    }

    public String getSersmEndTime() {
        return sersmEndTime;
    }

    public void setSersmEndTime(String sersmEndTime) {
        this.sersmEndTime = sersmEndTime;
    }

    public String getSersmBgDr() {
        return sersmBgDr;
    }

    public void setSersmBgDr(String sersmBgDr) {
        this.sersmBgDr = sersmBgDr;
    }

    public String getSersmBook() {
        return sersmBook;
    }

    public void setSersmBook(String sersmBook) {
        this.sersmBook = sersmBook;
    }

    public String getSersmCreateDept() {
        return sersmCreateDept;
    }

    public void setSersmCreateDept(String sersmCreateDept) {
        this.sersmCreateDept = sersmCreateDept;
    }

    public String getSersmCreateId() {
        return sersmCreateId;
    }

    public void setSersmCreateId(String sersmCreateId) {
        this.sersmCreateId = sersmCreateId;
    }

    public Calendar getSersmCreateTime() {
        return sersmCreateTime;
    }

    public void setSersmCreateTime(Calendar sersmCreateTime) {
        this.sersmCreateTime = sersmCreateTime;
    }

    public String getSersmAreaCode() {
        return sersmAreaCode;
    }

    public void setSersmAreaCode(String sersmAreaCode) {
        this.sersmAreaCode = sersmAreaCode;
    }

    public String getSersmImageUrl() {
        return sersmImageUrl;
    }

    public void setSersmImageUrl(String sersmImageUrl) {
        this.sersmImageUrl = sersmImageUrl;
    }

    public String getSersmImageName() {
        return sersmImageName;
    }

    public void setSersmImageName(String sersmImageName) {
        this.sersmImageName = sersmImageName;
    }

    public String getSersmObjectType() {
        return sersmObjectType;
    }

    public void setSersmObjectType(String sersmObjectType) {
        this.sersmObjectType = sersmObjectType;
    }

    public String getSersmPkType() {
        return sersmPkType;
    }

    public void setSersmPkType(String sersmPkType) {
        this.sersmPkType = sersmPkType;
    }

    public String getSersmGroupValue() {
        return sersmGroupValue;
    }

    public void setSersmGroupValue(String sersmGroupValue) {
        this.sersmGroupValue = sersmGroupValue;
    }

    public String getSersmJcState() {
        return sersmJcState;
    }

    public void setSersmJcState(String sersmJcState) {
        this.sersmJcState = sersmJcState;
    }

    public String getSersmOpenState() {
        return sersmOpenState;
    }

    public void setSersmOpenState(String sersmOpenState) {
        this.sersmOpenState = sersmOpenState;
    }

    public String getSersmLevel() {
        return sersmLevel;
    }

    public void setSersmLevel(String sersmLevel) {
        this.sersmLevel = sersmLevel;
    }

    public String getSersmJjId() {
        return sersmJjId;
    }

    public void setSersmJjId(String sersmJjId) {
        this.sersmJjId = sersmJjId;
    }

    public String getSersmTabState() {
        return sersmTabState;
    }

    public void setSersmTabState(String sersmTabState) {
        this.sersmTabState = sersmTabState;
    }

    public String getSersmOpenArea() {
        return sersmOpenArea;
    }

    public void setSersmOpenArea(String sersmOpenArea) {
        this.sersmOpenArea = sersmOpenArea;
    }

    public String getSersmGroupId() {
        return sersmGroupId;
    }

    public void setSersmGroupId(String sersmGroupId) {
        this.sersmGroupId = sersmGroupId;
    }

    /**
     * 获取减免状态名称
     * @return
     */
    public String getStrSmDownState() throws Exception{
        if(StringUtils.isNotBlank(this.getSersmDownState())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFCOMMON, this.getSersmDownState());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 获取经济类型名称
     * @return
     */
    public String getStrSmJjType(){
        String str = "";
        if(StringUtils.isNotBlank(this.getSersmJjId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            String[] jjlxs = this.getSersmJjId().split(";");
            for(String jjlx:jjlxs){
//                List<AppServeEcon> values= dao.getServiceDo().loadByPk(AppServeEcon.class,"econValue",jjlx);
                AppEconAndGov eag = (AppEconAndGov)dao.getServiceDo().find(AppEconAndGov.class,jjlx);
                if(eag!=null){
                    if(StringUtils.isNotBlank(eag.getEagEconId())){
                        AppServeEcon values =(AppServeEcon) dao.getServiceDo().find(AppServeEcon.class,eag.getEagEconId());
                        if(values!=null){
                            if(StringUtils.isBlank(str)){
                                str = values.getEconTitle();
                            }else{
                                str += ","+values.getEconTitle();
                            }
                        }
                    }

                }

            }
        }
        return str;
    }

    /**
     * 获取政府补贴方式名称
     * @return
     */
    public String getStrsmSubsidyWay(){
        String str = "";
        if(StringUtils.isNotBlank(this.getSersmJjId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            String[] btfss = this.getSersmJjId().split(";");
            for(String btfs:btfss){
                AppEconAndGov eag = (AppEconAndGov)dao.getServiceDo().find(AppEconAndGov.class,btfs);
                if(eag!=null){
                    if(StringUtils.isNotBlank(eag.getEagGovId())){
                        String[] govs = eag.getEagGovId().split(";");
                        for(String gov:govs){
//                            List<AppServeGov> sgs = dao.getServiceDo().loadByPk(AppServeGov.class,"govValue",gov);
                            AppServeGov sgs = (AppServeGov)dao.getServiceDo().find(AppServeGov.class,gov);
                            if(sgs!=null){
                                if(StringUtils.isBlank(str)){
                                    str = sgs.getGovTitle();
                                }else{
                                    str += ","+sgs.getGovTitle();
                                }
                            }
                        }
                    }
                }
            }
        }
        return str;
    }

    /**
     * 有效期是否可变更医生
     * @return
     */
    public String getStrSmBgDr() throws Exception{
        if(StringUtils.isNotBlank(this.getSersmBgDr())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFCOMMON, this.getSersmBgDr());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getStrSersmPkTitle(){
        String ss = "";
        if(StringUtils.isNotBlank(this.getSersmGroupId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            String[] groups = this.getSersmGroupId().split(";");
            for (String groupId:groups){
                AppServeGroup group = (AppServeGroup) dao.getServiceDo().find(AppServeGroup.class,groupId);
                if(group!=null){
                    if(StringUtils.isNotBlank(group.getSergPkId())){
                        String[] pks = group.getSergPkId().split(";");
                        for (String pkId:pks){
                            AppServePackage pk = (AppServePackage) dao.getServiceDo().find(AppServePackage.class,pkId);
                            if(pk!=null){
                                if(StringUtils.isNotBlank(pk.getSerpkTime())){
                                    if(StringUtils.isBlank(ss)){
                                        if(!"0".equals(pk.getSerpkTime())){
                                            ss=pk.getSerpkName()+"("+pk.getSerpkTime()+"次/年)";
                                        }else{
                                            ss=pk.getSerpkName();
                                        }
                                    }else{
                                        if(!"0".equals(pk.getSerpkTime())){
                                            ss+=","+pk.getSerpkName()+"("+pk.getSerpkTime()+"次/年)";
                                        }else{
                                            ss+=","+pk.getSerpkName();
                                        }
                                    }
                                }else{
                                    if(StringUtils.isBlank(ss)){
                                        ss=pk.getSerpkName();
                                    }else{
                                        ss+=","+pk.getSerpkName();
                                    }
                                }

                            }
                        }
                    }
                }
            }
           /* String[] sstrs = this.getSersmPkTitle().split(";");
            for(String sstr:sstrs){
                String[] strs = sstr.split(",");
                for(String str:strs){
                    List<AppServePackage> ls = dao.getServiceDo().loadByPk(AppServePackage.class,"serpkName",str);

                    if(ls!=null&&ls.size()>0){
                        if(str.equals(ls.get(0).getSerpkName())){
                            if(StringUtils.isBlank(ss)){
                                if(!"0".equals(ls.get(0).getSerpkNum())){
                                    ss=ls.get(0).getSerpkName()+"("+ls.get(0).getSerpkNum()+"次/年)";
                                }else{
                                    ss=ls.get(0).getSerpkName();
                                }
                            }else{
                                if(!"0".equals(ls.get(0).getSerpkNum())){
                                    ss+=","+ls.get(0).getSerpkName()+"("+ls.get(0).getSerpkNum()+"次/年)";
                                }else{
                                    ss+=","+ls.get(0).getSerpkName();
                                }
                            }
                        }
                    }
                }
            }*/
        }
        return ss;
    }

    public String getStrSersmPkTitles(){
        String ss = "";
        if(StringUtils.isNotBlank(this.getSersmGroupId())) {
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            String[] groups = this.getSersmGroupId().split(";");
            for (String groupId : groups) {
                AppServeGroup group = (AppServeGroup) dao.getServiceDo().find(AppServeGroup.class, groupId);
                if (group != null) {
                    if(StringUtils.isNotBlank(group.getSergObjectId())){
                        AppServeObject serveObject = (AppServeObject)dao.getServiceDo().find(AppServeObject.class,group.getSergObjectId());
                        if(serveObject != null){
                            if("1".equals(serveObject.getSeroState())){
                                ss+="<label style='color: black'><b>"+serveObject.getSeroName()+":</b>";
                            }else{
                                ss+="<label style='color: orange'><b>"+serveObject.getSeroName()+":</b>";
                            }
                        }
                    }
                    if (StringUtils.isNotBlank(group.getSergPkId())) {
                        String[] pks = group.getSergPkId().split(";");
                        for (String pkId : pks) {
                            AppServePackage pk = (AppServePackage) dao.getServiceDo().find(AppServePackage.class, pkId);
                            if (pk != null) {
                                if (StringUtils.isNotBlank(pk.getSerpkTime())) {
                                    if (StringUtils.isBlank(ss)) {
                                        if(!"0".equals(pk.getSerpkNum())){
                                            ss=pk.getSerpkName()+"("+pk.getSerpkNum()+"次/年)";
                                        }else{
                                            ss=pk.getSerpkName();
                                        }
                                    } else {
                                        if (!"0".equals(pk.getSerpkTime())) {
                                            ss += "," + pk.getSerpkName() + "(" + pk.getSerpkTime() + "次/年)";
                                        } else {
                                            ss += "," + pk.getSerpkName();
                                        }
                                    }
                                } else {
                                    if(!"0".equals(pk.getSerpkNum())){
                                        ss+=pk.getSerpkName()+"("+pk.getSerpkNum()+"次/年)"+",";
                                    }else{
                                        ss+=pk.getSerpkName()+",";
                                    }
                                }

                            }
                        }
                    }
                }
                ss+="</label><br>";
            }
        }



        /*if(StringUtils.isNotBlank(this.getSersmPkTitle())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            String[] rqs = this.getSersmObjectTitle().split(",");
            String[] fwnrs = this.getSersmPkTitle().split(";");
            String[] sersmPkTypes = this.getSersmObjectType().split(";");
            for(int i=0;i<fwnrs.length;i++){
                String[] strs = fwnrs[i].split(",");
                if("1".equals(sersmPkTypes[i])){
                    ss+="<label style='color: black'><b>"+rqs[i]+":</b>";
                }else{
                    ss+="<label style='color: orange'><b>"+rqs[i]+":</b>";
                }
                for(String str:strs){
                    List<AppServePackage> ls = dao.getServiceDo().loadByPk(AppServePackage.class,"serpkName",str);
                    if(ls!=null&&ls.size()>0){
                        if(str.equals(ls.get(0).getSerpkName())){
                            if(StringUtils.isBlank(ss)){
                                if(!"0".equals(ls.get(0).getSerpkNum())){
                                    ss=ls.get(0).getSerpkName()+"("+ls.get(0).getSerpkNum()+"次/年)";
                                }else{
                                    ss=ls.get(0).getSerpkName();
                                }
                            }else{
                                if(!"0".equals(ls.get(0).getSerpkNum())){
                                    ss+=ls.get(0).getSerpkName()+"("+ls.get(0).getSerpkNum()+"次/年)"+",";
                                }else{
                                    ss+=ls.get(0).getSerpkName()+",";
                                }
                            }
                        }
                    }
                }
                ss+="</label><br>";
            }
        }*/
        return ss;
    }

    /**
     * 获取开放区域名称
     * @return
     */
    public String getStrAreaName(){
        String ss = "";
        if(StringUtils.isNotBlank(this.getSersmOpenArea())){
            String[] strs = this.getSersmOpenArea().split(",");
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            for(String str:strs){
                CdAddress address = (CdAddress) dao.getServiceDo().find(CdAddress.class,str);
                if(address!=null){
                    ss+=address.getAreaSname()+",";
                }
            }
        }
        return ss;
    }

    public String getSersmOneFee() {
        return sersmOneFee;
    }

    public void setSersmOneFee(String sersmOneFee) {
        this.sersmOneFee = sersmOneFee;
    }

    public String getSersmTotalOneFee() {
        return sersmTotalOneFee;
    }

    public void setSersmTotalOneFee(String sersmTotalOneFee) {
        this.sersmTotalOneFee = sersmTotalOneFee;
    }

    public String getSersmIsDiscount() {
        return sersmIsDiscount;
    }

    public void setSersmIsDiscount(String sersmIsDiscount) {
        this.sersmIsDiscount = sersmIsDiscount;
    }

    public String getSersmDiscount() {
        return sersmDiscount;
    }

    public void setSersmDiscount(String sersmDiscount) {
        this.sersmDiscount = sersmDiscount;
    }

    public String getSersmCode() {
        return sersmCode;
    }

    public void setSersmCode(String sersmCode) {
        this.sersmCode = sersmCode;
    }
}
