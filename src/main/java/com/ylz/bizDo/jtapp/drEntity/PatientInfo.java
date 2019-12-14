package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.patientEntity.AppHealthListEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPatientHealthListQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.FollowPlanState;
import com.ylz.packcommon.common.comEnum.LabelManageType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by hzk on 2017/6/25.
 */
public class PatientInfo {
    private String id;//患者id
    private String signFormId;//签约单主键
    private String name;//姓名
    private String sex;//性别 1男 2女
    private String age;//年龄
    private String imgurl;//头像
    private String qyzt;//签约状态1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约 8拒签
    private String qylx;//签约类型 1家庭签约 2 vip
    private String ssgg;//三师工管 2代表签约
    private String jjyl;//居家养老 2代表签约
    private String jjylcolor;//居家养老颜色
    private String labtitle;//疾病类型 多逗号隔开 例如 肝炎,恶性肿瘤,糖尿病
    private String labcolor;//疾病类型颜色 多逗号隔开 例如 red,yellow
    private String province;//省
    private String city;//市
    private String area;
    private String street;
    private String committee;
    private String address;//地址
    private String phone;//手机号
    private String idno;//身份证号
    private String card;//社保卡号
    private String fromDate;//签约开始日期
    private String endDate;//签约结束日期
    private String payDate;//缴费时间
    private String fwrq;//服务人群
    private String jkqk;//健康情况
    private String jq;
    private String signPayState;//是否繳費
    private String qyType;//签约类型
    private String signTeamId;//签约团队主键
    private String cityCode;
    private String signJjType;
    private List<AppServeEntity> serveList;//套餐信息
    private String referralState;
    private String signHoapId;//签约机构id
    private String signDrId;//签约医生id

    private String isFollow;//是否有随访
    private String isGuide;//是否有健康指导
    private String isEducate;//是否有健康教育

    private List<AppSignSubEntity> imgUrls;//附件图片信息
    private String renewState;//是否需要转签或续签（0不需要 1需要）
    private String signGoToSignState;//是否转签（1已转签 2已续签）

    public String getSignPayState() {
        return signPayState;
    }

    public void setSignPayState(String signPayState) {
        this.signPayState = signPayState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
//        if(StringUtils.isNotBlank(sex)){
//            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
//            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CODESEX,sex);
//            if(value!=null)
//                this.sex = value.getCodeTitle();
//        }
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) throws Exception{
        Map<String,Object> idNos = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(this.getIdno())){
            if(this.getIdno().length() == 18){
                idNos = CardUtil.getCarInfo(this.getIdno());
            }else{
                idNos = CardUtil.getCarInfo15W(this.getIdno());
            }
            String birthday = idNos.get("birthday").toString();
            age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
        }

        this.age = age;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getSsgg() {
        return ssgg;
    }

    public void setSsgg(String ssgg) {
        this.ssgg = ssgg;
    }

    public String getJjyl() {
        return jjyl;
    }

    public void setJjyl(String jjyl) {
        this.jjyl = jjyl;
    }

    public String getJjylcolor() {
        return jjylcolor;
    }

    public void setJjylcolor(String jjylcolor) {
        this.jjylcolor = jjylcolor;
    }

    public String getLabtitle() {
        return labtitle;
    }

    public void setLabtitle(String labtitle) {
        this.labtitle = labtitle;
    }

    public String getLabcolor() {
        return labcolor;
    }

    public void setLabcolor(String labcolor) {
        this.labcolor = labcolor;
    }

    public String getQylx() {
        return qylx;
    }

    public void setQylx(String qylx)  throws Exception{
        jq=qylx;
        if(StringUtils.isNotBlank(qylx)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_HOUSESIGNING,qylx);
            if(value!=null) {
                this.qylx = value.getCodeTitle();
            }
        }
    }

    public String getQyzt() {
        return qyzt;
    }

    public void setQyzt(String qyzt) {
        this.qyzt = qyzt;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        if(StringUtils.isNotBlank(province)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = (CdAddress) dao.getServiceDo().find(CdAddress.class,province);
            if(address!=null) {
                this.province = address.getAreaSname();
            }
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if(StringUtils.isNotBlank(city)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = (CdAddress) dao.getServiceDo().find(CdAddress.class,city);
            if(address!=null) {
                this.city = address.getAreaSname();
            }
        }
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        if(StringUtils.isNotBlank(area)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = (CdAddress) dao.getServiceDo().find(CdAddress.class,area);
            if(address!=null) {
                this.area = address.getAreaSname();
            }
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if(StringUtils.isNotBlank(street)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = (CdAddress) dao.getServiceDo().find(CdAddress.class,street);
            if(address!=null) {
                this.street = address.getAreaSname();
            }
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = ExtendDate.getChineseYMD(fromDate);
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = ExtendDate.getChineseYMD(endDate);
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(Timestamp payDate) {
        if(payDate != null){
            this.payDate = ExtendDate.getChineseYMD(payDate);
        }else{
            this.payDate = "";
        }

    }

    public String getFwrq() {
        return fwrq;
    }

    public void setFwrq(String fwrq) {
        this.fwrq = fwrq;
    }

    public String getJkqk() {
        return jkqk;
    }

    public void setJkqk(String jkqk) throws Exception {
        if(StringUtils.isNotBlank(jkqk)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppLabelManage label = dao.getAppLabelManageDao().findLabelByValue("1",jkqk);
            if(label!=null) {
                this.jkqk =  label.getLabelTitle();
            }
        }
    }

    public String getJQ(){
        return jq;
    }

    public String getQyType() {
        return qyType;
    }

    public void setQyType(String qyType) {
        this.qyType = qyType;
    }

    public String getSignTeamId() {
        return signTeamId;
    }

    public void setSignTeamId(String signTeamId) {
        this.signTeamId = signTeamId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode)  throws Exception{
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress p = dao.getCdAddressDao().findByCode(cityCode);
        if(p != null){
            String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
            if(value != null) {
                cityCode = value.getCodeTitle();
            }
        }

        this.cityCode = cityCode;
    }

    public String getSignJjType() {
        return signJjType;
    }

    public void setSignJjType(String signJjType) {
//        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
//        CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JJLX,signJjType);
//        AppLabelManage value = dao.getAppLabelManageDao().findLabelByValue(LabelManageType.JJLX.getValue(),signJjType);
//        if(value != null)
//            signJjType = value.getLabelTitle();
        this.signJjType = signJjType;
    }

    public List<AppServeEntity> getServeList() {
        return serveList;
    }

    public void setServeList(String serveList) {
        List<AppServeEntity> list = new ArrayList<>();
        if(StringUtils.isNotBlank(serveList)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("serveIds",serveList.split(";"));
            String sql = " SELECT " +
                    "a.ID serveId," +
                    "a.SERSM_NAME serveName," +
                    "a.SERSM_GROUP_ID groupId," +
                    "a.SERSM_TOTAL_FEE fee," +
                    "'"+this.getSignFormId() +"' signFormId,"+
                    "'' groupList " +
                    "FROM APP_SERVE_SETMEAL a " +
                    "WHERE a.ID IN (:serveIds)";
            list = dao.getServiceDo().findSqlMapRVo(sql,map,AppServeEntity.class);
        }
        this.serveList = list;
    }

    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        if(StringUtils.isNotBlank(committee)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = (CdAddress) dao.getServiceDo().find(CdAddress.class,committee);
            if(address!=null) {
                this.committee = address.getAreaSname();
            }
        }
    }
    public String getReferralState() {
        return referralState;
    }

    public void setReferralState(String referralState) {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao =(SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("patientId",this.getId());
            String sql = "SELECT * FROM APP_REFERRAL_TABLE WHERE 1=1 " +
                    " AND REF_PATIENT_ID =:patientId " +
                    " AND REF_STATE IN ('0','1') ";
            sql += " ORDER BY\n" +
                    "\tREF_APPLY_TIME DESC ";
            List<AppReferralTable> list = dao.getServiceDo().findSqlMap(sql,map,AppReferralTable.class);
            if(list!=null && list.size()>0){
                referralState = list.get(0).getRefState();
            }
        }
        this.referralState = referralState;
    }

    public String getSignFormId() {
        return signFormId;
    }

    public void setSignFormId(String signFormId) {
        this.signFormId = signFormId;
    }

    /**
     * 获取团队名称
     * @return
     */
    public String getSignTeamName(){
        if(StringUtils.isNotBlank(this.getSignTeamId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppTeam team = (AppTeam)dao.getServiceDo().find(AppTeam.class,this.getSignTeamId());
            if(team!=null){
                return team.getTeamName();
            }
        }
        return "";
    }

    public String getSignHoapId() {
        return signHoapId;
    }

    public void setSignHoapId(String signHoapId) {
        this.signHoapId = signHoapId;
    }

    /**
     * 获取机构名称
     * @return
     */
    public String getHospName(){
        if(StringUtils.isNotBlank(this.getSignHoapId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getSignHoapId());
            if(dept!=null){
                return dept.getHospName();
            }
        }
        return "";
    }

    public String getSignDrId() {
        return signDrId;
    }

    public void setSignDrId(String signDrId) {
        this.signDrId = signDrId;
    }

    /**
     * 获取医生姓名
     * @return
     */
    public String getDrName(){
        if(StringUtils.isNotBlank(this.getSignDrId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getSignDrId());
            if(drUser!=null){
                return drUser.getDrName();
            }
        }
        return "";
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        String state = "0";
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("patientId",this.getId());
            map.put("state", FollowPlanState.YIWANCHENG.getValue());
            String sql = "SELECT COUNT(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_STATE=:state";
            int count = dao.getServiceDo().findCount(sql,map);
            if(count>0){
                state = "1";
            }
        }
        this.isFollow = state;
    }

    public String getIsGuide() {
        return isGuide;
    }

    public void setIsGuide(String isGuide) {
        String state = "0";
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("patientId",this.getId());
            String sql = "SELECT count(1) FROM app_health_meddle WHERE HM_PATIENT_ID=:patientId";
            int count = dao.getServiceDo().findCount(sql,map);
            if(count>0){
                state = "1";
            }
        }
        this.isGuide = state;
    }

    public String getIsEducate() {
        return isEducate;
    }

    public void setIsEducate(String isEducate) throws Exception {
        String state = "0";
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientHealthListQvo qvo = new AppPatientHealthListQvo();
            qvo.setPatientId(this.getId());
            List<AppHealthListEntity> ls = dao.getNewsTableDao().findByUserId(qvo);
            if(ls != null && ls.size()>0 ){
                state = "1";
            }
        }
        this.isEducate = state;
    }

    public List<AppSignSubEntity> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        List<AppSignSubEntity> listUrl = new ArrayList<>();
        if(StringUtils.isNotBlank(this.getSignFormId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("signId",this.getSignFormId());
            String sql = "SELECT ID id," +
                    "SIGN_ID signId," +
                    "IMG_URL imgUrl," +
                    "'' intranetIp," +
                    "'' extranetIp," +
                    "TYPE type " +
                    "FROM APP_SIGN_SUBTABLE WHERE SIGN_ID=:signId AND IS_AUTOGRAPH ='0' ";
            listUrl = dao.getServiceDo().findSqlMapRVo(sql,map,AppSignSubEntity.class);
        }
        this.imgUrls = listUrl;
    }

    public String getRenewState() {
        return renewState;
    }

    public void setRenewState(Timestamp renewState) {
        String rs = "0";
        if(renewState != null){
            Calendar cal = Calendar.getInstance();
            int year1 = cal.get(Calendar.YEAR);
            int mm = cal.get(Calendar.MONTH);//现在的月份
            Calendar cal1 = ExtendDate.getCalendar(ExtendDate.getYMD_h_m_s(renewState));
            int year2 = cal1.get(Calendar.YEAR);
            int m = cal1.get(Calendar.MONTH);//有效期日期月份
            if(year1==year2){
                if(m-mm<=1&&m-mm>=0){
                    rs = "1";
                }else{
                    rs = "0";
                }
            }else{
                rs = "0";
            }
        }else{
            rs = "0";
        }
        this.renewState = rs;
    }

    public String getSignGoToSignState() {
        return signGoToSignState;
    }

    public void setSignGoToSignState(String signGoToSignState) {
        this.signGoToSignState = signGoToSignState;
    }
}
