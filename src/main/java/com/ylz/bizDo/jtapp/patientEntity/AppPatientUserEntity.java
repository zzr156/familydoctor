package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.bizDo.app.entity.AppMenuEntity;
import com.ylz.bizDo.app.entity.AppMyServiceEntity;
import com.ylz.bizDo.app.po.AppModuleRole;
import com.ylz.bizDo.app.po.AppMunicipalAuthority;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.po.AppSignSetting;
import com.ylz.bizDo.app.vo.AppMenuQvo;
import com.ylz.bizDo.app.vo.AppMenuRoleQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressConfiguration;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppGluEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppPressureEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.AppMenuModuleRoleType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录
 */
public class AppPatientUserEntity {
    private String id;
    private String patientName;//名字
    private String patientGender;//性别
    private String patientBirthday;//出生日期
    private String patientIdno;//身份证号
    private String patientCard;//社保卡
    private String patientAge;//年龄
    private String patientTel;//电话
    private String patientAddress;//地址
    private String patientImageurl;//头像
    private String patientImageName;//头像图片后缀
    private String patientProvince;//省
    private String patientCity;//市
    private String patientArea;//区
    private String patientStreet;//街道
    private String patientNeighborhoodCommittee;//居委会
    private String patientState;//状态
    private String patientHealthy;//是否授权健康档案
    private String patientFirstState = "1";//第一次登录
    private String patientCommunity;//社区
    private String signType;//签约类型 1家庭签约 2 vip
    private String retireHome;//居家养老 2代表签约
    private String retireHomeColor;//居家养老颜色
    private String labTitle;//疾病类型 多逗号隔开 例如 高血压,糖尿病
    private String labColor;//疾病类型颜色 多逗号隔开 例如 red,yellow
    private String patientProvinceName;//省
    private String patientCityName;//市
    private String patientAreaName;//区
    private String patientStreetName;//街道
    private String patientNeighborhoodCommitteeName;//居委会
    private String cityCode;//城市代码
    private String easeId;//环信用户名
    private String paitentIdCardTemp;//临时社保卡
    private String patientWeight;//体重
    private String patientHeight;//身高
    private String patientBmi;//BMI
    private String pwdState;//是否修改过密码（0无 1有）
    private String patientEHCId;//电子健康卡主键
    private String patientEHCNo;//电子健康卡卡号
    private String openEHCState;//是否开启电子健康卡(1开启)


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
            this.patientGender = patientGender;

    }

    public String getPatientBirthday() {
        return patientBirthday;
    }

    public void setPatientBirthday(Date patientBirthday) {
        if(patientBirthday != null) {
            this.patientBirthday = ExtendDate.getYMD(patientBirthday);
        }else{
            this.patientBirthday = "";
        }
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        if(StringUtils.isNotBlank(patientBirthday)){
            this.patientAge = AgeUtil.getAgeYear(ExtendDate.getCalendar(patientBirthday));
        }

    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientImageurl() {
        return patientImageurl;
    }

    public void setPatientImageurl(String patientImageurl) {
        this.patientImageurl = patientImageurl;
    }

    public String getPatientProvince() {
        return patientProvince;
    }

    public void setPatientProvince(String patientProvince) {
        this.patientProvince = patientProvince;
    }

    public String getPatientCity() {
        return patientCity;
    }

    public void setPatientCity(String patientCity) {
        this.patientCity = patientCity;
    }

    public String getPatientArea() {
        return patientArea;
    }

    public void setPatientArea(String patientArea) {
        this.patientArea = patientArea;
    }

    public String getPatientStreet() {
        return patientStreet;
    }

    public void setPatientStreet(String patientStreet) {
        this.patientStreet = patientStreet;
    }

    public String getPatientCommunity() {
        return patientCommunity;
    }

    public void setPatientCommunity(String patientCommunity) {
        this.patientCommunity = patientCommunity;
    }

    public String getPatientState() {
        return patientState;
    }

    public void setPatientState(String patientState) {
        this.patientState = patientState;
    }

    public String getPatientHealthy() {
        return patientHealthy;
    }

    public void setPatientHealthy(String patientHealthy) {
        this.patientHealthy = patientHealthy;
    }

    public String getPatientFirstState() {
        return patientFirstState;
    }

    public void setPatientFirstState(String patientFirstState) {
        this.patientFirstState = patientFirstState;
    }

    public String getPatientImageName() {
        return patientImageName;
    }

    public void setPatientImageName(String patientImageName) {
        this.patientImageName = patientImageName;
    }

    public String getPatientNeighborhoodCommittee() {
        return patientNeighborhoodCommittee;
    }

    public void setPatientNeighborhoodCommittee(String patientNeighborhoodCommittee) {
        this.patientNeighborhoodCommittee = patientNeighborhoodCommittee;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getRetireHome() {
        return retireHome;
    }

    public void setRetireHome(String retireHome) {
        this.retireHome = retireHome;
    }

    public String getRetireHomeColor() {
        return retireHomeColor;
    }

    public void setRetireHomeColor(String retireHomeColor) {
        this.retireHomeColor = retireHomeColor;
    }

    public String getLabTitle() {
        return labTitle;
    }

    public void setLabTitle(String labTitle) {
        this.labTitle = labTitle;
    }

    public String getLabColor() {
        return labColor;
    }

    public void setLabColor(String labColor) {
        this.labColor = labColor;
    }


    public String getPatientProvinceName() {
        return patientProvinceName;
    }

    public void setPatientProvinceName(String patientProvinceName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress address = dao.getCdAddressDao().findByCode(patientProvinceName);
        if(address!=null) {
            this.patientProvinceName = address.getAreaSname();
        }else{
            CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(patientProvinceName);
            if(addressConfiguration != null){
                this.patientProvinceName = addressConfiguration.getAreaNameJw();
            }
        }
    }

    public String getPatientCityName() {
        return patientCityName;
    }

    public void setPatientCityName(String patientCityName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress address = dao.getCdAddressDao().findByCode(patientCityName);
        if(address!=null) {
            this.patientCityName = address.getAreaSname();
        }else{
            CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(patientCityName);
            if(addressConfiguration != null){
                this.patientCityName = addressConfiguration.getAreaNameJw();
            }
        }
    }

    public String getPatientAreaName() {
        return patientAreaName;
    }

    public void setPatientAreaName(String patientAreaName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress address = dao.getCdAddressDao().findByCode(patientAreaName);
        if(address!=null) {
            this.patientAreaName = address.getAreaSname();
        }else{
            CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(patientAreaName);
            if(addressConfiguration != null){
                this.patientAreaName = addressConfiguration.getAreaNameJw();
            }
        }
    }

    public String getPatientStreetName() {
        return patientStreetName;
    }

    public void setPatientStreetName(String patientStreetName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress address = dao.getCdAddressDao().findByCode(patientStreetName);
        if(address!=null && !AreaUtils.getAreaCode(patientStreetName,"2").equals(AddressType.NPS.getValue())) {
            this.patientStreetName = address.getAreaSname();
        }else{
            CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(patientStreetName);
            if(addressConfiguration != null){
                this.patientStreetName = addressConfiguration.getAreaNameJw();
            }
        }
    }

    public String getPatientNeighborhoodCommitteeName() {
        return patientNeighborhoodCommitteeName;
    }

    public void setPatientNeighborhoodCommitteeName(String patientNeighborhoodCommitteeName) throws Exception {
        if(StringUtils.isBlank(patientNeighborhoodCommitteeName)){
            this.patientNeighborhoodCommitteeName = "";
        }else{
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = dao.getCdAddressDao().findByCode(patientNeighborhoodCommitteeName);
            if(address!=null && !AreaUtils.getAreaCode(patientNeighborhoodCommitteeName,"2").equals(AddressType.NPS.getValue())) {
                this.patientNeighborhoodCommitteeName = address.getAreaSname();
            }else{
                CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(patientNeighborhoodCommitteeName);
                if(addressConfiguration != null){
                    this.patientNeighborhoodCommitteeName = addressConfiguration.getAreaNameJw();
                }else{
                    this.patientNeighborhoodCommitteeName = "";
                }
            }
        }
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress p = dao.getCdAddressDao().findByCode(cityCode);
        if(p != null){
            String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
            if(value != null) {
                this.cityCode = value.getCodeTitle();
            }
        }else{
            this.cityCode = cityCode;
        }
    }

    public String getEaseId() {
        return easeId;
    }

    public void setEaseId(String easeId) {
        try{
            SericuryUtil p = new SericuryUtil();
            this.easeId = p.encrypt(easeId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getPaitentIdCardTemp() {
        return paitentIdCardTemp;
    }

    public void setPaitentIdCardTemp(String paitentIdCardTemp) {
        this.paitentIdCardTemp = paitentIdCardTemp;
    }


    public String getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(String patientWeight) {
        if(StringUtils.isNotBlank(patientWeight)){
            this.patientWeight = patientWeight;
        }else{
            this.patientWeight = "";
        }

    }

    public String getPatientHeight() {
        return patientHeight;
    }

    public void setPatientHeight(String patientHeight) {
        if(StringUtils.isNotBlank(patientHeight)){
            this.patientHeight = patientHeight;
        }else{
            this.patientHeight = "";
        }
    }

    public String getPatientBmi() {
        return patientBmi;
    }

    public void setPatientBmi(String patientBmi) {
        if(StringUtils.isNotBlank(patientBmi)){
            this.patientBmi = patientBmi;
        }else{
            this.patientBmi = "";
        }
    }

    /**
     * 血糖
     * @return
     */
    public AppGluEntity getStrBloodglu() throws Exception {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppGluEntity entity = dao.getAppUserBloodgluDao().findLatest(this.getId());
            if(entity != null){
                return entity;
            }else{
                entity = new AppGluEntity();
                return entity;
            }
        }


        return null;
    }

    /**
     * 血压值
     * @return
     */
    public AppPressureEntity getStrBloodpress() throws Exception {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppPressureEntity entity = dao.getAppUserBloodpressureDao().findLatest(this.getId());
            if(entity != null){
                return entity;
            }else{
                entity = new AppPressureEntity();
                return entity;
            }
        }
        return null;
    }

    /**
     * 权限菜单
     * @return
     */
    public List<AppMenuEntity> getStrPatientMenuRole() throws Exception {
        List<AppMenuEntity> ls = new ArrayList<AppMenuEntity>();
        if(StringUtils.isNotBlank(this.getPatientCity())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppSignForm form = dao.getAppSignformDao().findSignFormByUserState(this.getId());
            AppMenuRoleQvo qvo = new AppMenuRoleQvo();
            if(form != null){
                qvo.setHospId(form.getSignHospId());
            }else {
                qvo.setAreaCode(this.getPatientCity());
            }
            qvo.setMenuType(AppMenuModuleRoleType.PATIENT.getValue());
            ls = dao.getAppModuleRoleDao().findMenuRole(qvo);
            if(ls == null){
                qvo.setHospId(null);
                qvo.setAreaCode(this.getPatientCity());
                ls = dao.getAppModuleRoleDao().findMenuRole(qvo);
            }
        }else{
            AppMenuQvo qvo = new AppMenuQvo();
            qvo.setType(AppMenuModuleRoleType.PATIENT.getValue());
            qvo.setMenuCode(AppMenuModuleRoleType.PATIENT.getValue());
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            ls = dao.getAppMenuDao().findMenuEntity(qvo);
        }
        return  ls;
    }

    /**
     * 推荐服务
     * @return
     */
    public List<AppMenuEntity> getRecommendedService() throws Exception {
        List<AppMenuEntity> ls = new ArrayList<AppMenuEntity>();
        if(this.getStrPatientMenuRole() != null && this.getStrPatientMenuRole().size() >0){
            for(AppMenuEntity p : this.getStrPatientMenuRole()){
                if(p.getMenuModule().equals("1")){
                    ls.add(p);
                }
            }
        }
        return ls;
    }

    /**
     * 我的服务
     * @return
     */
    public List<AppMyServiceEntity> getMyServiceMenu() throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        AppMenuRoleQvo qvo = new AppMenuRoleQvo();
        qvo.setDrPaiteintId(this.getId());
        List<AppMyServiceEntity> ls = dao.getAppMyServiceMenuDao().findMenuRole(qvo);
        if(ls == null){
            if(this.getStrPatientMenuRole() != null && this.getStrPatientMenuRole().size() >0){
                dao.getAppMyServiceMenuDao().addMySerViceMenu(this.getStrPatientMenuRole(),this.getId());
                ls = dao.getAppMyServiceMenuDao().findMenuRole(qvo);
            }
        }else{
            List<AppMenuEntity> list = this.getStrPatientMenuRole();
            if(list!=null && list.size()>0){
                if(list.size()==ls.size()){

                }else{
                    dao.getAppMyServiceMenuDao().delServiceMenu(this.getId());
                    dao.getAppMyServiceMenuDao().addMySerViceMenu(list, this.getId());
                    ls = dao.getAppMyServiceMenuDao().findMenuRole(qvo);
                    return ls;
                }
            }
        }
        return ls;
    }



    /**
     * 别名
     * @return
     */
    public String getAreaAlias() throws Exception {
        String result = "健康中国";
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        if(StringUtils.isNotBlank(this.getPatientProvince()) &&
                StringUtils.isBlank(this.getPatientCity())){
            CdAddress p = dao.getCdAddressDao().findByCode(this.getPatientProvince());
            if(p != null) {
                result = p.getAreaAlias();
            }
        }else if(StringUtils.isNotBlank(this.getPatientProvince()) &&
                StringUtils.isNotBlank(this.getPatientCity())){
            AppMunicipalAuthority authority = dao.getAppMunicipalAuthorityDao().findByAreaCode(this.getPatientCity());
            if(authority != null){
                //1开启
                if(CommonEnable.QIYONG.getValue().equals(authority.getAtreState())){
                    if(StringUtils.isNotBlank(this.getPatientArea())){
                        CdAddress p = dao.getCdAddressDao().findByCode(this.getPatientArea());
                        if(p != null) {
                            result = p.getAreaAlias();
                        }
                    }else{
                        CdAddress p = dao.getCdAddressDao().findByCode(this.getPatientCity());
                        if(p != null) {
                            result = p.getAreaAlias();
                        }
                    }
                }else {
                    CdAddress p = dao.getCdAddressDao().findByCode(this.getPatientCity());
                    if(p != null) {
                        result = p.getAreaAlias();
                    }
                }
            }else {
                CdAddress p = dao.getCdAddressDao().findByCode(this.getPatientCity());
                if(p != null) {
                    result = p.getAreaAlias();
                }
            }
        }
        return result;
    }

    /**
     * 签约医生
     * @return
     */
    public String getSignDrId() throws Exception {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppSignForm sign = dao.getAppSignformDao().findSignFormByUser(this.getId());
            if(sign != null ){
                return sign.getSignDrId();
            }
        }
        return  "";
    }

    /**
     * 签约团队
     * @return
     */
    public String getSignTeamId() throws Exception {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppSignForm sign = dao.getAppSignformDao().findSignFormByUser(this.getId());
            if(sign != null ){
                return sign.getSignTeamId();
            }
        }
        return  "";
    }

    public String getPatientObject() {
        String result = "";
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("patientId",this.getId());
            String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
            map.put("state", signStates);
            String sql = "SELECT\n" +
                    "\tGROUP_CONCAT(c.LABEL_TYPE) LABELTYPE\n" +
                    "FROM \n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\ta.LABEL_TYPE LABEL_TYPE\n" +
                    "\t\tFROM\n" +
                    "\t\t\tAPP_LABEL_GROUP a\n" +
                    "\t\tINNER JOIN APP_SIGN_FORM b ON a.LABEL_SIGN_ID = b.ID\n" +
                    "\t\tWHERE\n" +
                    "\t\t\tb.SIGN_PATIENT_ID =:patientId\n" +
                    "\t\tAND b.SIGN_STATE IN (:state)\n" +
                    "\t\tAND a.LABEL_TYPE != '4'\n" +
                    "\t\tGROUP BY\n" +
                    "\t\t\ta.LABEL_TYPE,a.LABEL_SIGN_ID\n" +
                    "\t\tORDER BY\n" +
                    "\t\t\ta.LABEL_TYPE\n" +
                    "\t) c";
            List<Map> ls = dao.getServiceDo().findSqlMap(sql,map);
            if(ls != null && ls.size() >0){
                if(ls.get(0).get("LABELTYPE") != null) {
                    result = ls.get(0).get("LABELTYPE").toString();
                }
            }
        }
        return result;
    }

    /**
     * 极光推送别名
     * @return
     */
    public String getJpushCode(){
        if(StringUtils.isNotBlank(this.getPatientIdno())){
            return Md5Util.MD5(this.getPatientIdno());
        }else {
            return "";
        }

    }

    public String getJpushSigndrCode() throws Exception {
        if(StringUtils.isNotBlank(this.getSignDrId())){
            return Md5Util.MD5(this.getSignDrId());
        }else{
            return "";
        }
    }

    public String getPwdState() {
        if(StringUtils.isBlank(this.pwdState)){
            return "0";
        }
        return pwdState;
    }

    public void setPwdState(String pwdState) {
            this.pwdState = pwdState;
    }



    public String getStrManySignRole() throws Exception {
        String result = "";
        if(StringUtils.isNotBlank(this.getPatientCity())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            String city = AreaUtils.getAreaCode(this.getPatientCity(),"2");
            AppSignSetting setting = dao.getAppSignSettingDao().findByAreaCode(city);
           if(setting != null){
               result = setting.getSignsSignType();
           }
        }
        return  result;
    }
    /**
     * 签约医生
     * @return
     */
    public String getSignHospId() throws Exception {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppSignForm sign = dao.getAppSignformDao().findSignFormByUser(this.getId());
            if(sign != null ){
                return sign.getSignHospId();
            }
        }
        return  "";
    }

    /**
     * 是否开启建档
     * @return
     */
    public String getOpenRecord() throws Exception {
        String result = "";
        if(StringUtils.isNotBlank(this.getPatientCity())){
            String city = AreaUtils.getAreaCode(this.getPatientCity(),"2");
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppSignSetting setting = dao.getAppSignSettingDao().findByAreaCode(city);
            if(setting != null){
                result = setting.getSignsOpenJd();
            }
        }
        return  result;
    }

    public String getPatientEHCId() {
        return patientEHCId;
    }

    public void setPatientEHCId(String patientEHCId) {
        this.patientEHCId = patientEHCId;
    }

    public String getPatientEHCNo() {
        return patientEHCNo;
    }

    public void setPatientEHCNo(String patientEHCNo) {
        this.patientEHCNo = patientEHCNo;
    }

    public String getOpenEHCState() {
        return openEHCState;
    }

    public void setOpenEHCState(String openEHCState) throws Exception{
        openEHCState = "0";
        if(this.getStrPatientMenuRole() != null && this.getStrPatientMenuRole().size() >0){
            for(AppMenuEntity p : this.getStrPatientMenuRole()){
                if("4".equals(p.getMenuModule())&&"电子健康卡".equals(p.getMenuName())){
                    openEHCState = "1";
                }
            }
        }
        this.openEHCState = openEHCState;
    }
}
