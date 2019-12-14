package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.LabelManageType;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签约处理用户信息
 * Created by zzl on 2017/7/27.
 */
public class AppDrPatientSignEntity {
    private String id;//签约人id
    private String signName;//签约人姓名
    private String signAge;//签约人年龄
    private String signImage;//签约人头像
    private String signTime;//申请签约时间
    private String signIdNo;//签约人身份证
    private String signCard;//签约人社保卡
    private String signTel;//签约人电话
    private String signAddr;//签约人住址
    private String signWorkType;//工作类型
    private String signPayType;//政府补贴类型
    private String signPayTypeName;//政府补贴类型名称
    private String signFee;//签约费用
    private String signTeamId;//签约团队id
    private String signTeamName;//签约团队名称
    private List drList;//团队医生
    private List fwrq;//服务人群
    private List fwb;//服务包
    private String signId;//签约单id
    private List jjlx;//经济类型
    private String gender;//性别
    private String cityCode;//市
    private List<AppServeEntity> serveList;//套餐信息


    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getSignAge() {
        return signAge;
    }

    public void setSignAge(String signAge) throws Exception {
        Map<String,Object> idNos = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(this.getSignIdNo())){

            if(StringUtils.isNotBlank(this.getSignIdNo())){
                if(this.getSignIdNo().length() == 18){
                    idNos = CardUtil.getCarInfo(this.getSignIdNo());
                }else{
                    idNos = CardUtil.getCarInfo15W(this.getSignIdNo());
                }
                String birthday = idNos.get("birthday").toString();
                signAge = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
            }
        }
        this.signAge = signAge;
    }

    public String getSignImage() {
        return signImage;
    }

    public void setSignImage(String signImage) {
        this.signImage = signImage;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getSignIdNo() {
        return signIdNo;
    }

    public void setSignIdNo(String signIdNo) {
        this.signIdNo = signIdNo;
    }

    public String getSignCard() {
        return signCard;
    }

    public void setSignCard(String signCard) {
        this.signCard = signCard;
    }

    public String getSignTel() {
        return signTel;
    }

    public void setSignTel(String signTel) {
        this.signTel = signTel;
    }

    public String getSignAddr() {
        return signAddr;
    }

    public void setSignAddr(String signAddr) {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getId());
            String address = "";
            if(user!=null){
                if(StringUtils.isNotBlank(user.getPatientNeighborhoodCommittee())){
                    address = user.getPatientNeighborhoodCommittee();
                }else if(StringUtils.isNotBlank(user.getPatientStreet())){
                    address = user.getPatientStreet();
                }else if(StringUtils.isNotBlank(user.getPatientArea())){
                    address = user.getPatientArea();
                }else if(StringUtils.isNotBlank(user.getPatientCity())){
                    address = user.getPatientCity();
                }else if(StringUtils.isNotBlank(user.getPatientProvince())){
                    address = user.getPatientProvince();
                }
                if(StringUtils.isNotBlank(address)){
                    CdAddress addres = (CdAddress)dao.getServiceDo().find(CdAddress.class,address);
                    if(addres!=null&&StringUtils.isNotBlank(user.getPatientAddress())){
                        signAddr = addres.getAreaName()+user.getPatientAddress();
                    }
                }else{
                    if(StringUtils.isNotBlank(user.getPatientAddress())){
                        signAddr = user.getPatientAddress();
                    }
                }
            }
        }
        this.signAddr = signAddr;
    }

    public String getSignWorkType() {
        return signWorkType;
    }

    public void setSignWorkType(String signWorkType) {
        this.signWorkType = signWorkType;
    }

    public String getSignPayType() {
        return signPayType;
    }

    public void setSignPayType(String signPayType) {

        this.signPayType = signPayType;
    }

    public String getSignFee() {
        return signFee;
    }

    public void setSignFee(String signFee) {
        this.signFee = signFee;
    }

    public String getSignTeamId() {
        return signTeamId;
    }

    public void setSignTeamId(String signTeamId) {
        this.signTeamId = signTeamId;
    }

    public String getSignTeamName() {
        return signTeamName;
    }

    public void setSignTeamName(String signTeamName) {
        this.signTeamName = signTeamName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getDrList() {
        return drList;
    }

    public void setDrList(String drList) {
        List llist = new ArrayList();
        if(StringUtils.isNotBlank(this.getSignTeamId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            List<AppTeamMember> list = dao.getServiceDo().loadByPk(AppTeamMember.class,"memTeamid",this.getSignTeamId());
            if(StringUtils.isNotBlank(this.getSignWorkType())){
                for(AppTeamMember ls:list){
                    if(this.getSignWorkType().equals(ls.getMemWorkType())){
                        llist.add(ls);
                    }
                }
            }
        }
        this.drList = llist;
    }

    public List getFwrq() {
        return fwrq;
    }

    public void setFwrq(String fwrq) throws Exception {
        List lss = new ArrayList();
        if(StringUtils.isNotBlank(fwrq)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            List<AppLabelManage> list = dao.getAppLabelManageDao().findByType("3");
            for(AppLabelManage ls:list){
                if(fwrq.indexOf(ls.getLabelValue())!=-1){
                    lss.add(ls);
                }

            }
        }
        this.fwrq = lss;
    }

    public List getFwb() {
        return fwb;
    }

    public void setFwb(String  fwb) throws Exception {
        List lss = new ArrayList();
        if(StringUtils.isNotBlank(fwb)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            List<CdCode> list = dao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SERVICETYPE, CommonEnable.QIYONG.getValue());
            for(CdCode ls:list){
                if(fwb.indexOf(ls.getCodeValue())!=-1){
                    lss.add(ls);
                }
            }
        }
        this.fwb = lss;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getSignPayTypeName() {
        return signPayTypeName;
    }

    public void setSignPayTypeName(String signPayTypeName)throws Exception {
        if(StringUtils.isNotBlank(this.getSignPayType())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_PAYTYPE, this.getSignPayType());
            if(value!=null) {
                signPayTypeName = value.getCodeTitle();
            }
        }
        this.signPayTypeName = signPayTypeName;
    }

    public List getJjlx() {
        return jjlx;
    }

    public void setJjlx(String jjlx) throws Exception {
        List lss = new ArrayList();
        if(StringUtils.isNotBlank(jjlx)){
            String[] jjlxss = jjlx.split(";");
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
//            List<CdCode> list = dao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JJLX, CommonEnable.QIYONG.getValue());
            for(String jjlxs:jjlxss){
                AppLabelManage manage = dao.getAppLabelManageDao().findLabelByValue(LabelManageType.JJLX.getValue(),jjlxs);
                if(manage!=null){
                    lss.add(manage);
                }
            }
        }
        this.jjlx = lss;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
                    "'"+this.getId() +"' signFormId,"+
                    "'' groupList " +
                    "FROM APP_SERVE_SETMEAL a " +
                    "WHERE a.ID IN (:serveIds)";
            list = dao.getServiceDo().findSqlMapRVo(sql,map,AppServeEntity.class);
        }
        this.serveList = list;
    }
}
