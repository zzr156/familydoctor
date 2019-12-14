package com.ylz.view.sign.action;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.entity.AppArchivingCardCPEntiy;
import com.ylz.bizDo.app.entity.AppArchivingCardPeopeEntity;
import com.ylz.bizDo.app.entity.AppArchivingCheckEntity;
import com.ylz.bizDo.app.entity.AppArchivingCheckEntityVo;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppArchivingCardPeopleQvo;
import com.ylz.bizDo.app.vo.AppArchivingCheckQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.jtapp.basicHealthEntity.ResidentHealthFiles;
import com.ylz.bizDo.jtapp.basicHealthEntity.ResidentRecordsEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppArchivingCardLyEntity;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.EconomicType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.HtmlUtils;
import com.ylz.packcommon.common.util.JsonUtil;
import com.ylz.packcommon.common.util.PropertiesUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/11/7.
 */
@SuppressWarnings("all")
@Action(
        value="archivingCheck",
        results={
                @Result(name = "json", type = "json",params={"root","jsons","contentType", "application/json"}),
                @Result(name = "jList", type = "json",params={"root","jList","contentType", "application/json"}),
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","excludeNullProperties","true","contentType", "application/json"})
        }
)
public class ArchivingCheckAction extends CommonAction {
    /**
     * 查询建档立卡人员核查信息
     * @return
     */
    public String jsonByOne(){
        try{
            AppArchivingCheckQvo qvo = (AppArchivingCheckQvo)getVoJson(AppArchivingCheckQvo.class);
            if(StringUtils.isNotBlank(qvo.getId())){
                //根据建档立卡花名册主键查询花名册信息
                AppArchivingCardPeople cardPeople = (AppArchivingCardPeople)sysDao.getServiceDo().find(AppArchivingCardPeople.class,qvo.getId());
                if(cardPeople != null){
                    AppArchivingCheckEntity vo = sysDao.getAppArchivingCheckDao().findByArchivingId(cardPeople.getId(),null);
                    if(vo != null){//查询是否有核查表 有核查表初始化
                        String requestId = null;
                        String requestName = null;
                        CdUser cdUser = this.getSessionUser();
                        if(cdUser!=null){
                            requestId = cdUser.getUserId();
                            requestName = cdUser.getUserName();
                        }
                        //有核查表数据，初始不可填写字段
                        if(!"3".equals(qvo.getType())){//新增查询初始化自动获取字段
                            AppArchivingCheckEntityVo vvo = new AppArchivingCheckEntityVo();
                            vvo.setCreateName(vo.getCreateName());
                            vvo.setCreateId(vo.getCreateId());
                            vvo.setId(vo.getId());
                            vvo.setObjectType(vo.getObjectType());
                            vvo.setArchivingId(vo.getArchivingId());
                            vvo.setPatientNo(vo.getPatientNo());
                            vvo.setFamilySize(vo.getFamilySize());
                            vvo.setPovertyState(vo.getPovertyState());
                            vvo.setHouseholdRelationship(vo.getHouseholdRelationship());
                            vvo.setResidenceState(vo.getResidenceState());
                            vvo.setFpaState(vo.getFpaState());
                            vvo.setArchivingCardState(vo.getArchivingCardState());
                            vvo.setMaritalStatus(vo.getMaritalStatus());
                            vvo.setFamilyType(vo.getFamilyType());
                            vvo.setPatientCard(vo.getPatientCard());
                            vvo.setPatientTel(vo.getPatientTel());
                            vvo.setInspectorOneUrl(vo.getInspectorOneUrl());
                            vvo.setInspectorTwoUrl(vo.getInspectorTwoUrl());
                            if(StringUtils.isNotBlank(vo.getServiceType())){
                                String[] fwType = vo.getServiceType().split(";");
                                vvo.setServiceType(fwType);
                                vvo.setFwType(vo.getServiceType());
                            }
                            vvo.setSpecialFamily(vo.getSpecialFamily());
                            vvo.setTotalFee(vo.getTotalFee());
                            vvo.setIllnessName(vo.getIllnessName());
                            vvo.setPaperSignState(vo.getPaperSignState());
                            vvo.setSignAgreementState(vo.getSignAgreementState());
                            vvo.setServiceHandbookState(vo.getServiceHandbookState());
                            vvo.setContactCardState(vo.getContactCardState());
                            vvo.setTotalCost(vo.getTotalCost());
                            vvo.setZfFee(vo.getZfFee());
                            vvo.setOtherFund(vo.getOtherFund());
                            vvo.setSgcpa(vo.getSgcpa());
                            vvo.setCivilAssistance(vo.getCivilAssistance());
                            vvo.setNcmsFee(vo.getNcmsFee());
                            vvo.setKnowHelpPoorPolicy(vo.getKnowHelpPoorPolicy());
                            vvo.setSatisfiedState(vo.getSatisfiedState());
                            vvo.setCheckYwDate(vo.getCheckYwDate());
                            vvo.setCheckDate(vo.getCheckDate());

                            AppArchivingCardPeople vv = (AppArchivingCardPeople) sysDao.getServiceDo().find(AppArchivingCardPeople.class,qvo.getId());
                            if(vv != null){
                                vvo.setPatientName(vv.getArchivingPatientName());
                                vvo.setPatientSex(vv.getSex());
                                vvo.setPatientBirthDay(ExtendDate.getYMD(vv.getBirthday()));
                                vvo.setPatientIdno(vv.getArchivingPatientIdno());
                                vvo.setAreaName(vv.getAddrCountyName());
                                vvo.setStreetName(vv.getAddrRuralName());
                                vvo.setCunName(vv.getAddrVillageName());
                                vvo.setAreaCode(vv.getAddrVillageCode());
                                //户籍地暂时取建档立卡花名册中的地址
                                vvo.setDomicilePlaceCounty(vv.getAddrCountyName());
                                vvo.setDomicilePlaceTown(vv.getAddrRuralName());
                                vvo.setDomicilePlaceVillage(vv.getAddrVillageName());
                                vvo.setAccDonicilePlace(vv.getAddrCountyName()+vv.getAddrRuralName()+vv.getAddrVillageName());

//                                vvo.setObjectType(vv.getProvincialInsurance());
                            }
                            //获取家签个人信息
                            AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(vv.getArchivingPatientIdno());
                            String tel = "";
                            if(user != null){
                                vvo.setPatientCard(user.getPatientCard());
                                if(StringUtils.isNotBlank(user.getPatientTel())){
                                    tel += user.getPatientTel();
                                }

                            }
                            //传档案号和身份证调用健康档案获取居住地和户籍地（县乡村）
                            //基卫有健康档案
                            //web调用
                            String urlType = null;// 地市
                            String address = null;// 接口地址
                            JSONObject jsonParam = new JSONObject();// 调用的参数
                            CloseableHttpClient client = HttpClients.createDefault();
//                            System.out.println("调用基卫健康档案开始时间："+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                            if (StringUtils.isNotBlank(vv.getAddrCountyCode())) {
                                String areaCode = vv.getAddrCountyCode().substring(0, 4);
                                if (areaCode.equals(AddressType.FZS.getValue())) {
                                    urlType = AddressType.FZ.getValue();
                                    address = PropertiesUtil.getConfValue("FZ");
                                } else if (areaCode.equals(AddressType.QZS.getValue())) {
                                    urlType = AddressType.QZ.getValue();
                                    address = PropertiesUtil.getConfValue("QZ");
                                } else if (areaCode.equals(AddressType.ZZS.getValue())) {
                                    urlType = AddressType.ZZ.getValue();
                                    address = PropertiesUtil.getConfValue("ZZ");
                                } else if (areaCode.equals(AddressType.PTS.getValue())) {
                                    urlType = AddressType.PT.getValue();
                                    address = PropertiesUtil.getConfValue("PT");
                                } else if (areaCode.equals(AddressType.NPS.getValue())) {
                                    urlType = AddressType.NP.getValue();
                                    address = PropertiesUtil.getConfValue("NP");
                                } else if (areaCode.equals(AddressType.SMS.getValue())) {
                                    urlType = AddressType.SM.getValue();
                                    address = PropertiesUtil.getConfValue("SM");
                                }
                                jsonParam.put("idNo", vv.getArchivingPatientIdno());
                                jsonParam.put("urlType", urlType);
                                jsonParam.put("type","2");
                                String strr = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                                if(StringUtils.isNotBlank(strr)) {
                                    JSONObject jsonall = JSONObject.fromObject(strr);
                                    if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")) {
                                        if (jsonall.get("entity") != null) {
                                            JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                            if(entity != null ){
                                                if (entity.get("success") != null && "true".equals(entity.get("success").toString())) {
                                                    T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                                    vvo.setPatientJmda(entitys.getJmdah());

                                                    vvo.setResidencePlaceCounty(entitys.getXian());
                                                    vvo.setResidencePlaceTown(entitys.getXiang());
                                                    vvo.setResidencePlaceVillage(entitys.getCun());
                                                    vvo.setResidencePlace(entitys.getXian() + entitys.getXiang() + entitys.getCun());
                                                    vvo.setResidencePlaceAddr(entitys.getMphm());
                                                    //户籍地
                                                    if(StringUtils.isBlank(entitys.getHjxian())){
                                                        entitys.setHjxian("");
                                                    }
                                                    if(StringUtils.isBlank(entitys.getHjxiang())){
                                                        entitys.setHjxiang("");
                                                    }
                                                    if(StringUtils.isBlank(entitys.getHjcun())){
                                                        entitys.setHjcun("");
                                                    }
                                                    vvo.setDomicilePlaceCounty(entitys.getHjxian());
                                                    vvo.setDomicilePlaceTown(entitys.getHjxiang());
                                                    vvo.setDomicilePlaceVillage(entitys.getHjcun());

                                                    vvo.setAccDonicilePlace(entitys.getHjxian()+entitys.getHjxiang()+entitys.getHjcun());

                                                    if(StringUtils.isNotBlank(entitys.getTel())){
                                                        if(StringUtils.isNotBlank(tel)){
                                                            tel +="/"+entitys.getTel();
                                                        }else{
                                                            tel = entitys.getTel();
                                                        }
                                                    }
                                                    if (StringUtils.isNotBlank(entitys.getJdrq())) {
                                                        vvo.setJmdaTime(ExtendDate.getYMD(ExtendDate.getYYYYMMD(entitys.getJdrq())));
                                                    }
                                                }else{
                                                    this.jsons.setMsg("初始档案信息失败,请联系管理员");
                                                    this.jsons.setCode("900");
                                                    return "json";
                                                }
                                            }else{
                                                this.jsons.setMsg("初始档案信息失败,请联系管理员");
                                                this.jsons.setCode("900");
                                                return "json";
                                            }

                                        }else{
                                            this.jsons.setMsg("初始档案信息失败,请联系管理员");
                                            this.jsons.setCode("900");
                                            return "json";
                                        }
                                    }else{
                                        this.jsons.setMsg("初始档案信息失败,请联系管理员");
                                        this.jsons.setCode("900");
                                        return "json";
                                    }
                                }else{
                                    this.jsons.setMsg("初始档案信息失败,请联系管理员");
                                    this.jsons.setCode("900");
                                    return "json";
                                }
                            }
//                            System.out.println("调用基卫健康档案结束时间："+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                            //app调用档案
                            /*String urlType = "";
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vv.getAddrCountyCode().substring(0,4));
                            if(value!=null){
                                urlType = value.getCodeTitle();
                            }
                            HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                            qqvo.setIdno(vv.getArchivingPatientIdno());
                            qqvo.setType("2");
                            qqvo.setUrlType(urlType);
                            String strr = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,null,null,"2");
                            if(StringUtils.isNotBlank(strr)) {
                                JSONObject jsonall = JSONObject.fromObject(strr);
                                if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")) {
                                    if (jsonall.get("entity") != null) {
                                        JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                        if ("true".equals(entity.get("success").toString())) {
                                            T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                            vvo.setPatientJmda(entitys.getJmdah());

                                            vvo.setResidencePlaceCounty(entitys.getXian());
                                            vvo.setResidencePlaceTown(entitys.getXiang());
                                            vvo.setResidencePlaceVillage(entitys.getCun());
                                            vvo.setResidencePlace(entitys.getXian() + entitys.getXiang() + entitys.getCun());
                                            vvo.setResidencePlaceAddr(entitys.getMphm());
                                            //户籍地
                                            if(StringUtils.isBlank(entitys.getHjxian())){
                                                entitys.setHjxian("");
                                            }
                                            if(StringUtils.isBlank(entitys.getHjxiang())){
                                                entitys.setHjxiang("");
                                            }
                                            if(StringUtils.isBlank(entitys.getHjcun())){
                                                entitys.setHjcun("");
                                            }
                                            vvo.setDomicilePlaceCounty(entitys.getHjxian());
                                            vvo.setDomicilePlaceTown(entitys.getHjxiang());
                                            vvo.setDomicilePlaceVillage(entitys.getHjcun());

                                            vvo.setAccDonicilePlace(entitys.getHjxian()+entitys.getHjxiang()+entitys.getHjcun());

                                            if(StringUtils.isNotBlank(entitys.getTel())){
                                                if(StringUtils.isNotBlank(tel)){
                                                    tel +="/"+entitys.getTel();
                                                }else{
                                                    tel = entitys.getTel();
                                                }
                                            }
                                            if (StringUtils.isNotBlank(entitys.getJdrq())) {
                                                vvo.setJmdaTime(ExtendDate.getYMD(ExtendDate.getYYYYMMD(entitys.getJdrq())));
                                            }
                                        }
                                    }
                                }
                            }*/
                            vvo.setPatientTel(tel);
                            if(StringUtils.isNotBlank(vv.getSignId())){
                                AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,vv.getSignId());
                                if(form != null){
                                    if(form.getSignFromDate() != null ){
                                        vvo.setSignFromDate(ExtendDate.getChineseYMD(form.getSignFromDate()));
                                    }
                                    if(form.getSignToDate() != null){
                                        vvo.setSignToDate(ExtendDate.getChineseYMD(form.getSignToDate()));
                                    }
                                    String fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                    vvo.setFwType(fwType);
                                    if(fwType != null){
                                        vvo.setServiceType(fwType.split(","));
                                    }
                                    String jjType = sysDao.getAppLabelGroupDao().findJjValue(form.getId());
                                    if(StringUtils.isNotBlank(jjType)){
                                        if(jjType.indexOf(EconomicType.JSTSJT.getValue())!=-1){
                                            vvo.setSpecialFamily("1");
                                        }
                                    }

                                    //获取履约部分信息
//                                    System.out.println("获取履约开始时间："+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                                    String str = this.getSysDao().getSecurityCardAsyncBean().getPerformance(vv.getArchivingPatientIdno(),urlType,
                                            ExtendDate.getYMD(form.getSignFromDate()),ExtendDate.getYMD(form.getSignToDate()),requestId,requestName);
                                    if(StringUtils.isNotBlank(str)){
                                        JSONObject jsonall = JSONObject.fromObject(str);
                                        if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                                            JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                            AppArchivingCardLyEntity entitys = JsonUtil.fromJson(entity.get("entity").toString(), AppArchivingCardLyEntity.class);
                                            if(entitys != null){
                                                vvo.setJmdaTime(entitys.getFiliingDate());
                                                vvo.setJmdaLastUpdateTime(entitys.getLastUpdateDate());
                                                vvo.setSignPeNum(entitys.getHealthCheckNum());
                                                vvo.setLastPeTime(entitys.getLastHealthCheckDate());

                                                if(StringUtils.isNotBlank(entitys.getTotalCost())){
                                                    vvo.setTotalCost(entitys.getTotalCost());//总费用
                                                }

                                                if (StringUtils.isNotBlank(entitys.getZfFee())) {
                                                    vvo.setZfFee(entitys.getZfFee());//自付金额
                                                }

                                                if(StringUtils.isNotBlank(entitys.getNcmsFee())){
                                                    vvo.setNcmsFee(entitys.getNcmsFee());//新农合报销
                                                }

                                                if(StringUtils.isNotBlank(entitys.getCivilAssistance())){
                                                    vvo.setCivilAssistance(entitys.getCivilAssistance());//民政补助
                                                }

                                                if(StringUtils.isNotBlank(entitys.getSgcpa())){
                                                    vvo.setSgcpa(entitys.getSgcpa());//扶贫叠加保障补偿
                                                }

                                                if(StringUtils.isNotBlank(entitys.getOtherFund())){
                                                    vvo.setOtherFund(entitys.getOtherFund());//其他
                                                }

                                                if(entitys.getKidInfo() != null){
                                                    vvo.setEtFollowNum(entitys.getKidInfo().getFlupNum());
                                                    vvo.setEtLastFollowTime(entitys.getKidInfo().getLastflupDate());
                                                }
                                                if(entitys.getMaternalInfo() != null){
                                                    vvo.setYcfFollowNum(entitys.getMaternalInfo().getFlupNum());
                                                    vvo.setYcfLastFollowTime(entitys.getMaternalInfo().getLastflupDate());
                                                }
                                                if(entitys.getHbpInfo() != null){
                                                    vvo.setGxyFollowNum(entitys.getHbpInfo().getFlupNum());
                                                    vvo.setGxyLastFollowTime(entitys.getHbpInfo().getLastflupDate());
                                                    vvo.setGxyLastTime(entitys.getHbpInfo().getLastBpDate());
                                                    vvo.setGxyNum(entitys.getHbpInfo().getBpNum());
                                                }
                                                if(entitys.getDmInfo() != null){
                                                    vvo.setTnbNum(entitys.getDmInfo().getGluNum());
                                                    vvo.setTnbLastTime(entitys.getDmInfo().getLastGluDate());
                                                    vvo.setTnbFollowNum(entitys.getDmInfo().getFlupNum());
                                                    vvo.setTnbLastFollowTime(entitys.getDmInfo().getLastflupDate());
                                                }
                                                if(entitys.getMentalInfo() != null){
                                                    vvo.setJsbFollowNum(entitys.getMentalInfo().getFlupNum());
                                                    vvo.setJsbLastFollowTime(entitys.getMentalInfo().getLastflupDate());
                                                }
                                            }
                                        }else{
                                            this.jsons.setMsg("初始履约信息失败,请联系管理员");
                                            this.jsons.setCode("900");
                                            return "json";
                                        }
                                    }else{
                                        this.jsons.setMsg("初始履约信息失败,请联系管理员");
                                        this.jsons.setCode("900");
                                        return "json";
                                    }
//                                    System.out.println("获取履约结束时间："+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                                }

                            }else{//没有签约单，清空签约数据
                                vvo.setSignFromDate("");
                                vvo.setSignToDate("");
                                vvo.setFwType("");
                                vvo.setServiceType(null);
                                vvo.setSpecialFamily(null);
                            }
                            this.jsons.setVo(vvo);
                        }else{//不是新增直接查数据库数据返回
                            AppArchivingCardPeople vv = (AppArchivingCardPeople) sysDao.getServiceDo().find(AppArchivingCardPeople.class,qvo.getId());
                            if(vv != null){
                                vo.setAreaName(vv.getAddrCountyName());
                                vo.setStreetName(vv.getAddrRuralName());
                                vo.setCunName(vv.getAddrVillageName());
                                vo.setAreaCode(vv.getAddrVillageCode());
                            }
                            this.jsons.setVo(vo);
                        }

                    }else{//没有审核表,查询初始信息
                        AppArchivingCheckEntityVo vvo = new AppArchivingCheckEntityVo();
                        String requestId = null;
                        String requestName = null;
                        CdUser cdUser = this.getSessionUser();
                        if(cdUser!=null){
                            vvo.setCreateId(cdUser.getUserId());
                            vvo.setCreateName(cdUser.getUserName());
                            requestId = cdUser.getUserId();
                            requestName = cdUser.getUserName();
                        }
                        vvo.setArchivingId(qvo.getId());
                        AppArchivingCardPeople vv = (AppArchivingCardPeople) sysDao.getServiceDo().find(AppArchivingCardPeople.class,qvo.getId());
                        if(vv != null){
                            vvo.setAreaName(vv.getAddrCountyName());
                            vvo.setStreetName(vv.getAddrRuralName());
                            vvo.setCunName(vv.getAddrVillageName());
                            vvo.setAreaCode(vv.getAddrVillageCode());
                            vvo.setPatientName(vv.getArchivingPatientName());
                            vvo.setPatientIdno(vv.getArchivingPatientIdno());
                            vvo.setPatientSex(vv.getSex());
                            vvo.setPatientBirthDay(ExtendDate.getYMD(vv.getBirthday()));
                            vvo.setPatientJmda(vv.getRhfId());
                            vvo.setPovertyState(vv.getIsNotPoverty());

                            //户籍地暂时取建档立卡花名册中的地址
                            vvo.setDomicilePlaceCounty(vv.getAddrCountyName());
                            vvo.setDomicilePlaceTown(vv.getAddrRuralName());
                            vvo.setDomicilePlaceVillage(vv.getAddrVillageName());
                            vvo.setAccDonicilePlace(vv.getAddrCountyName()+vv.getAddrRuralName()+vv.getAddrVillageName());

                            //获取家签个人信息
                            String tel = "";
                            AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(vv.getArchivingPatientIdno());
                            if(user != null){
                                vvo.setPatientCard(user.getPatientCard());
                                if(StringUtils.isNotBlank(user.getPatientTel())){
                                    tel = user.getPatientTel();
                                }
                            }
                            //传档案号和身份证调用健康档案获取居住地和户籍地（县乡村）
                            //基卫有健康档案
                            //web调用档案方式
                            String urlType = null;// 地市
                            String address = null;// 接口地址
                            JSONObject jsonParam = new JSONObject();// 调用的参数
                            CloseableHttpClient client = HttpClients.createDefault();
                            if (StringUtils.isNotBlank(vv.getAddrCountyCode())) {
                                String areaCode = vv.getAddrCountyCode().substring(0, 4);
                                if (areaCode.equals(AddressType.FZS.getValue())) {
                                    urlType = AddressType.FZ.getValue();
                                    address = PropertiesUtil.getConfValue("FZ");
                                } else if (areaCode.equals(AddressType.QZS.getValue())) {
                                    urlType = AddressType.QZ.getValue();
                                    address = PropertiesUtil.getConfValue("QZ");
                                } else if (areaCode.equals(AddressType.ZZS.getValue())) {
                                    urlType = AddressType.ZZ.getValue();
                                    address = PropertiesUtil.getConfValue("ZZ");
                                } else if (areaCode.equals(AddressType.PTS.getValue())) {
                                    urlType = AddressType.PT.getValue();
                                    address = PropertiesUtil.getConfValue("PT");
                                } else if (areaCode.equals(AddressType.NPS.getValue())) {
                                    urlType = AddressType.NP.getValue();
                                    address = PropertiesUtil.getConfValue("NP");
                                } else if (areaCode.equals(AddressType.SMS.getValue())) {
                                    urlType = AddressType.SM.getValue();
                                    address = PropertiesUtil.getConfValue("SM");
                                }
                                jsonParam.put("idNo", vv.getArchivingPatientIdno());
                                jsonParam.put("urlType", urlType);
                                jsonParam.put("type","2");
//                                System.out.println("获取档案开始时间："+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                                String strr = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                                if(StringUtils.isNotBlank(strr)) {
                                    JSONObject jsonall = JSONObject.fromObject(strr);
                                    if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")) {
                                        if (jsonall.get("entity") != null) {
                                            JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                            if(entity != null){
                                                if (entity.get("success")!=null && "true".equals(entity.get("success").toString())) {
                                                    T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                                    vvo.setPatientJmda(entitys.getJmdah());
                                                    vvo.setResidencePlaceCounty(entitys.getXian());
                                                    vvo.setResidencePlaceTown(entitys.getXiang());
                                                    vvo.setResidencePlaceVillage(entitys.getCun());
                                                    vvo.setResidencePlace(entitys.getXian() + entitys.getXiang() + entitys.getCun());
                                                    vvo.setResidencePlaceAddr(entitys.getMphm());

                                                    //户籍地
                                                    if(StringUtils.isBlank(entitys.getHjxian())){
                                                        entitys.setHjxian("");
                                                    }
                                                    if(StringUtils.isBlank(entitys.getHjxiang())){
                                                        entitys.setHjxiang("");
                                                    }
                                                    if(StringUtils.isBlank(entitys.getHjcun())){
                                                        entitys.setHjcun("");
                                                    }
                                                    vvo.setDomicilePlaceCounty(entitys.getHjxian());
                                                    vvo.setDomicilePlaceTown(entitys.getHjxiang());
                                                    vvo.setDomicilePlaceVillage(entitys.getHjcun());

                                                    vvo.setAccDonicilePlace(entitys.getHjxian()+entitys.getHjxiang()+entitys.getHjcun());
                                                    if(StringUtils.isNotBlank(entitys.getTel())){
                                                        if(StringUtils.isNotBlank(tel)){
                                                            tel +="/"+entitys.getTel();
                                                        }else{
                                                            tel = entitys.getTel();
                                                        }
                                                    }
                                                    if (StringUtils.isNotBlank(entitys.getJdrq())) {
                                                        vvo.setJmdaTime(ExtendDate.getYMD(ExtendDate.getYYYYMMD(entitys.getJdrq())));
                                                    }
                                                }else{
                                                    this.jsons.setMsg("初始档案信息失败,请联系管理员");
                                                    this.jsons.setCode("900");
                                                    return "json";
                                                }
                                            }else{
                                                this.jsons.setMsg("初始档案信息失败,请联系管理员");
                                                this.jsons.setCode("900");
                                                return "json";
                                            }
                                        }else{
                                            this.jsons.setMsg("初始档案信息失败,请联系管理员");
                                            this.jsons.setCode("900");
                                            return "json";
                                        }
                                    }else{
                                        this.jsons.setMsg("初始档案信息失败,请联系管理员");
                                        this.jsons.setCode("900");
                                        return "json";
                                    }
                                }else{
                                    this.jsons.setMsg("初始档案信息失败,请联系管理员");
                                    this.jsons.setCode("900");
                                    return "json";
                                }
//                                System.out.println("获取档案结束时间："+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                            }

                            //app调用档案
                            /*String urlType = "";
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vv.getAddrCountyCode().substring(0,4));
                            if(value!=null){
                                urlType = value.getCodeTitle();
                            }
                            HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                            qqvo.setIdno(vv.getArchivingPatientIdno());
                            qqvo.setType("2");
                            qqvo.setUrlType(urlType);
                            String strr = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,null,null,"2");
                            if(StringUtils.isNotBlank(strr)) {
                                System.out.println("健康档案："+strr);
                                JSONObject jsonall = JSONObject.fromObject(strr);
                                if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")) {
                                    if (jsonall.get("entity") != null) {
                                        JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                        if ("true".equals(entity.get("success").toString())) {
                                            T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                            vvo.setPatientJmda(entitys.getJmdah());
                                            vvo.setResidencePlaceCounty(entitys.getXian());
                                            vvo.setResidencePlaceTown(entitys.getXiang());
                                            vvo.setResidencePlaceVillage(entitys.getCun());
                                            vvo.setResidencePlace(entitys.getXian() + entitys.getXiang() + entitys.getCun());
                                            vvo.setResidencePlaceAddr(entitys.getMphm());

                                            //户籍地
                                            if(StringUtils.isBlank(entitys.getHjxian())){
                                                entitys.setHjxian("");
                                            }
                                            if(StringUtils.isBlank(entitys.getHjxiang())){
                                                entitys.setHjxiang("");
                                            }
                                            if(StringUtils.isBlank(entitys.getHjcun())){
                                                entitys.setHjcun("");
                                            }
                                            vvo.setDomicilePlaceCounty(entitys.getHjxian());
                                            vvo.setDomicilePlaceTown(entitys.getHjxiang());
                                            vvo.setDomicilePlaceVillage(entitys.getHjcun());

                                            vvo.setAccDonicilePlace(entitys.getHjxian()+entitys.getHjxiang()+entitys.getHjcun());
                                            if(StringUtils.isNotBlank(entitys.getTel())){
                                                if(StringUtils.isNotBlank(tel)){
                                                    tel +="/"+entitys.getTel();
                                                }else{
                                                    tel = entitys.getTel();
                                                }
                                            }
                                            if (StringUtils.isNotBlank(entitys.getJdrq())) {
                                                vvo.setJmdaTime(ExtendDate.getYMD(ExtendDate.getYYYYMMD(entitys.getJdrq())));
                                            }
                                        }
                                    }
                                }
                            }*/
                            vvo.setPatientTel(tel);
                            if(StringUtils.isNotBlank(vv.getSignId())){
                                AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,vv.getSignId());
                                if(form != null){
                                    if(form.getSignFromDate() != null ){
                                        vvo.setSignFromDate(ExtendDate.getChineseYMD(form.getSignFromDate()));
                                    }
                                    if(form.getSignToDate() != null){
                                        vvo.setSignToDate(ExtendDate.getChineseYMD(form.getSignToDate()));
                                    }
                                    String fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                    vvo.setFwType(fwType);
                                    if(fwType != null){
                                        vvo.setServiceType(fwType.split(","));
                                    }
                                    String jjType = sysDao.getAppLabelGroupDao().findJjValue(form.getId());
                                    if(StringUtils.isNotBlank(jjType)){
                                        if(jjType.indexOf(EconomicType.JSTSJT.getValue())!=-1){
                                            vvo.setSpecialFamily("1");
                                        }
                                    }

                                    //获取履约部分信息 （本地测试暂不开）
                                    System.out.println("获取履约开始时间："+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                                    String str = this.getSysDao().getSecurityCardAsyncBean().getPerformance(vv.getArchivingPatientIdno(),urlType,
                                            ExtendDate.getYMD(form.getSignFromDate()),ExtendDate.getYMD(form.getSignToDate()),requestId,requestName);
                                    if(StringUtils.isNotBlank(str)){
//                                        System.out.println("履约数据："+str);
                                        JSONObject jsonall = JSONObject.fromObject(str);
                                        if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                                            JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                            AppArchivingCardLyEntity entitys = JsonUtil.fromJson(entity.get("entity").toString(), AppArchivingCardLyEntity.class);
                                            if(entitys != null){
                                                vvo.setJmdaTime(entitys.getFiliingDate());
                                                vvo.setJmdaLastUpdateTime(entitys.getLastUpdateDate());
                                                vvo.setSignPeNum(entitys.getHealthCheckNum());
                                                vvo.setLastPeTime(entitys.getLastHealthCheckDate());

                                                vvo.setTotalCost(entitys.getTotalCost());//总费用
                                                vvo.setZfFee(entitys.getZfFee());//自付金额
                                                vvo.setNcmsFee(entitys.getNcmsFee());//新农合报销
                                                vvo.setCivilAssistance(entitys.getCivilAssistance());//民政补助
                                                vvo.setSgcpa(entitys.getSgcpa());//扶贫叠加保障补偿
                                                vvo.setOtherFund(entitys.getOtherFund());//其他

                                                if(entitys.getKidInfo() != null){
                                                    vvo.setEtFollowNum(entitys.getKidInfo().getFlupNum());
                                                    vvo.setEtLastFollowTime(entitys.getKidInfo().getLastflupDate());
                                                }
                                                if(entitys.getMaternalInfo() != null){
                                                    vvo.setYcfFollowNum(entitys.getMaternalInfo().getFlupNum());
                                                    vvo.setYcfLastFollowTime(entitys.getMaternalInfo().getLastflupDate());
                                                }
                                                if(entitys.getHbpInfo() != null){
                                                    vvo.setGxyFollowNum(entitys.getHbpInfo().getFlupNum());
                                                    vvo.setGxyLastFollowTime(entitys.getHbpInfo().getLastflupDate());
                                                    vvo.setGxyLastTime(entitys.getHbpInfo().getLastBpDate());
                                                    vvo.setGxyNum(entitys.getHbpInfo().getBpNum());
                                                }
                                                if(entitys.getDmInfo() != null){
                                                    vvo.setTnbNum(entitys.getDmInfo().getGluNum());
                                                    vvo.setTnbLastTime(entitys.getDmInfo().getLastGluDate());
                                                    vvo.setTnbFollowNum(entitys.getDmInfo().getFlupNum());
                                                    vvo.setTnbLastFollowTime(entitys.getDmInfo().getLastflupDate());
                                                }
                                                if(entitys.getMentalInfo() != null){
                                                    vvo.setJsbFollowNum(entitys.getMentalInfo().getFlupNum());
                                                    vvo.setJsbLastFollowTime(entitys.getMentalInfo().getLastflupDate());
                                                }
                                            }else{
                                                this.jsons.setMsg("初始履约信息失败,请联系管理员");
                                                this.jsons.setCode("900");
                                                return "json";
                                            }
                                        }else{
                                            this.jsons.setMsg("初始履约信息失败,请联系管理员");
                                            this.jsons.setCode("900");
                                            return "json";
                                        }
                                    }else{
                                        this.jsons.setMsg("初始履约信息失败,请联系管理员");
                                        this.jsons.setCode("900");
                                        return "json";
                                    }
                                    System.out.println("获取履约结束时间："+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                                }
                            }

                        }
                        this.jsons.setVo(vvo);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(getClass(), getAct(), e);
        }
        return "json";
    }

    /**
     * 新增或修改
     * @return
     */
    public String archivingCheckAddOrModify(){
        try{
            AppArchivingCheckQvo qvo = (AppArchivingCheckQvo)getVoJson(AppArchivingCheckQvo.class);
            if(qvo != null){
                if(StringUtils.isBlank(qvo.getArchivingId())){
                    this.jsons.setMsg("建档立卡人员主键不能为空!");
                    this.jsons.setCode("900");
                }else{
                    if(StringUtils.isNotBlank(qvo.getId())){//修改
                        AppArchivingCardCheck vo = (AppArchivingCardCheck)sysDao.getServiceDo().find(AppArchivingCardCheck.class,qvo.getId());
                        if(vo!= null){
//                            vo.setAccArchivingId(qvo.getArchivingId());//建档立卡外键
                            vo.setAccPatientNo(qvo.getPatientNo());// 用户编号
                            vo.setAccFamilySize(qvo.getFamilySize());// 家庭人口数
                            vo.setAccPovertyState(qvo.getPovertyState());// 是否脱贫(0否 1是)
                            vo.setAccPatientName(qvo.getPatientName());// 姓名
                            vo.setAccPatientSex(qvo.getPatientSex());// 性别
                            vo.setAccPatientBirthDay(ExtendDate.getCalendar(qvo.getPatientBirthDay()));// 出生日期
                            vo.setAccPatientIdno(qvo.getPatientIdno());// 身份证号
                            vo.setAccHouseholdRelationship(qvo.getHouseholdRelationship());// 与户主的关系
                            vo.setAccResidenceState(qvo.getResidenceState());// 有无户籍（0无 1有）
                            vo.setAccFpaState(qvo.getFpaState());// 有无纳入计生管理（0无 1有）
                            vo.setAccArchivingCardState(qvo.getArchivingCardState());// 有无纳入建档立卡人口（0无 1有）
                            vo.setAccMaritalStatus(qvo.getMaritalStatus());// 婚姻状况（0未 1初 2再 3离 4丧偶）
                            vo.setAccFamilyType(qvo.getFamilyType());// 计生家庭户类型（1无孩 2一男 3一女 4二女 5一女一男 6一男一女 7三孩 8四孩 9五孩以上）
                            vo.setAccPatientCard(qvo.getPatientCard());// 社会保障号
                            vo.setAccPatientJmda(qvo.getPatientJmda());// 居民健康档案号
                            vo.setAccDomicilePlaceCounty(qvo.getDomicilePlaceCounty());// 户籍地（县）
                            vo.setAccDomicilePlaceTown(qvo.getDomicilePlaceTown());// 户籍地乡
                            vo.setAccDomicilePlaceVillage(qvo.getDomicilePlaceVillage());// 户籍地村
                            vo.setAccDonicilePlace(qvo.getAccDonicilePlace());//户籍地(县乡村）

                            vo.setAccResidencePlaceCounty(qvo.getResidencePlaceCounty());//  居住地县
                            vo.setAccResidencePlaceTown(qvo.getResidencePlaceTown());// 居住地乡
                            vo.setAccResidencePlaceVillage(qvo.getResidencePlaceVillage());// 居住地村
                            vo.setAccResidencePlace(qvo.getResidencePlace());// 居住地(县乡村）

                            vo.setAccResidencePlaceAddr(qvo.getResidencePlaceAddr());// 居住地详细地址（门牌号）
                            vo.setAccPatientTel(qvo.getPatientTel());// 联系电话
                            if(qvo.getServiceType()!=null && qvo.getServiceType().length>0){
                                String serviceType = "";
                                for(String fwType:qvo.getServiceType()){
                                    if(StringUtils.isBlank(serviceType)){
                                        serviceType = fwType;
                                    }else{
                                        serviceType += ";"+fwType;
                                    }
                                }
                                vo.setAccServiceType(serviceType);// 服务类型（1一般人群 2.0-6岁儿童 3孕产妇 4.65岁以上老年人 5高血压患者 6糖尿病患者 7严重精神障碍患者 8肺结核患者 9残疾人 ）
                            }

                            vo.setAccSpecialFamily(qvo.getSpecialFamily());//是否计生特殊家庭（0否 1是）
                            vo.setAccTotalFee(qvo.getTotalFee());//诊疗费用
                            if(qvo.getIllnessName()!= null && qvo.getIllnessName().length>0){
                                String illnessName = "";
                                for (String ill:qvo.getIllnessName()){
                                    if(StringUtils.isBlank(illnessName)){
                                        illnessName = ill;
                                    }else{
                                        illnessName += ";"+ill;
                                    }
                                }
                                vo.setAccIllnessName(illnessName);//大病名称
                            }
                            vo.setAccPaperSignState(qvo.getPaperSignState());//纸质签约（0未签约 1已签约）
                            vo.setAccSignAgreementState(qvo.getSignAgreementState());//有无签约协议（0无 1有）
                            vo.setAccServiceHandbookState(qvo.getServiceHandbookState());// 有无服务手册(0无 1有)
                            vo.setAccContactCardState(qvo.getContactCardState());// 有无爱心服务卡（联络卡0无 1有）
                            AppArchivingCardPeople people = (AppArchivingCardPeople) sysDao.getServiceDo().find(AppArchivingCardPeople.class,qvo.getArchivingId());
                            if(people != null){
                                if(StringUtils.isNotBlank(people.getSignId())){
                                    AppSignForm form = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,people.getSignId());
                                    if(form != null){
                                        vo.setAccSignFromDate(form.getSignFromDate());// 签约服务开始时间
                                        vo.setAccSignToDate(form.getSignToDate());// 签约服务结束时间
                                    }
                                }
                            }

                            vo.setAccNotSignReason(qvo.getNotSignReason());// 未落实签约原因
                            vo.setAccNotSignReasonOther(qvo.getNotSignReasonOther());// 未落实签约其它原因
                            if(StringUtils.isNotBlank(qvo.getJmdaTime())){
                                vo.setAccJmdaTime(ExtendDate.getCalendar(qvo.getJmdaTime()));// 居民健康档案时间
                            }
                            if(StringUtils.isNotBlank(qvo.getJmdaLastUpdateTime())){
                                vo.setAccJmdaLastUpdateTime(ExtendDate.getCalendar(qvo.getJmdaLastUpdateTime()));// 居民健康档案最后更新时间
                            }
                            vo.setAccSignPeNum(qvo.getSignPeNum());// 签约后健康体检次数
                            if(StringUtils.isNotBlank(qvo.getLastPeTime())){
                                vo.setAccLastPeTime(ExtendDate.getCalendar(qvo.getLastPeTime()));// 最后一次健康体检时间
                            }
                            vo.setAccEtFollowNum(qvo.getEtFollowNum());// 儿童随访次数
                            if(StringUtils.isNotBlank(qvo.getEtLastFollowTime())){
                                vo.setAccEtLastFollowTime(ExtendDate.getCalendar(qvo.getEtLastFollowTime()));// 儿童最后一次随访时间
                            }

                            vo.setAccYcfFollowNum(qvo.getYcfFollowNum());// 孕产妇随访次数
                            if(StringUtils.isNotBlank(qvo.getYcfLastFollowTime())){
                                vo.setAccYcfLastFollowTime(ExtendDate.getCalendar(qvo.getYcfLastFollowTime()));// 孕产妇最后一次随访时间
                            }

                            vo.setAccGxyNum(qvo.getGxyNum());// 高血压患者测血压次数
                            if(StringUtils.isNotBlank(qvo.getGxyLastTime())){
                                vo.setAccGxyLastTime(ExtendDate.getCalendar(qvo.getGxyLastTime()));// 高血压患者最后一次测血压时间
                            }

                            vo.setAccGxyFollowNum(qvo.getGxyFollowNum());// 高血压患者随访次数

                            if(StringUtils.isNotBlank(qvo.getGxyLastFollowTime())){
                                vo.setAccGxyLastFollowTime(ExtendDate.getCalendar(qvo.getGxyLastFollowTime()));//  高血压最后一次随访时间
                            }
                            vo.setAccTnbNum(qvo.getTnbNum());// 糖尿病测血糖次数
                            if(StringUtils.isNotBlank(qvo.getTnbLastTime())){
                                vo.setAccTnbLastTime(ExtendDate.getCalendar(qvo.getTnbLastTime()));// 糖尿病患者最后一次测血糖时间
                            }

                            vo.setAccTnbFollowNum(qvo.getTnbFollowNum());// 糖尿病患者随访次数
                            if(StringUtils.isNotBlank(qvo.getTnbLastFollowTime())){
                                vo.setAccTnbLastFollowTime(ExtendDate.getCalendar(qvo.getTnbLastFollowTime()));// 糖尿病患者最后一次随访时间
                            }

                            vo.setAccJsbFollowNum(qvo.getJsbFollowNum());// 严重精神障碍患者随访次数
                            if(StringUtils.isNotBlank(qvo.getJsbLastFollowTime())){
                                vo.setAccJsbLastFollowTime(ExtendDate.getCalendar(qvo.getJsbLastFollowTime()));// 严重精神障碍患者最后一次随访时间
                            }
                            vo.setAccTotalCost(qvo.getTotalCost());//总费用
                            vo.setAccZfFee(qvo.getZfFee());//自付金额
                            vo.setAccNcmsFee(qvo.getNcmsFee());//新农合报销
                            vo.setAccCivilAssistance(qvo.getCivilAssistance());//民政补助
                            vo.setAccSgcpa(qvo.getSgcpa());//扶贫叠加保障补偿
                            vo.setAccOtherFund(qvo.getOtherFund());//其他

                            vo.setAccKnowHelpPoorPolicy(qvo.getKnowHelpPoorPolicy());// 是否知晓健康扶贫政策（0不知道 1知道 2有听说，但具体内容不了解）
                            vo.setAccSatisfiedState(qvo.getSatisfiedState());// 对签约服务是否满意（1满意 2基本满意 3不满意）
                            vo.setAccInspectorOneUrl(qvo.getInspectorOneUrl());// 核查员签名（计生管理员）
                            vo.setAccInspectorTwoUrl(qvo.getInspectorTwoUrl());// 核查员签名(医务人员)
                            if(StringUtils.isNotBlank(qvo.getInspectorOneUrl())){//计生管理员签名
                                if(StringUtils.isNotBlank(qvo.getCheckDate())){
                                    vo.setAccCheckDate(ExtendDate.getCalendar(qvo.getCheckDate()));// 核查日期（计生管理员）
                                }
                            }

                            if(StringUtils.isNotBlank(qvo.getInspectorTwoUrl())){//医务人员签名
                                if(StringUtils.isNotBlank(qvo.getCheckYwDate())){
                                    vo.setAccCheckYwDate(ExtendDate.getCalendar(qvo.getCheckYwDate()));//核查日期（医务人员）
                                }
                            }
                            vo.setAccObjectType(qvo.getObjectType());
                            sysDao.getServiceDo().modify(vo);
                            this.jsons.setCode("800");
                        }
                    }else{
                        AppArchivingCardPeople people = (AppArchivingCardPeople) sysDao.getServiceDo().find(AppArchivingCardPeople.class,qvo.getArchivingId());
                        if(people != null){
                            if(StringUtils.isNotBlank(people.getSignId())){
                                AppSignForm form = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,people.getSignId());
                                if(form != null){
                                    AppArchivingCardCheck vo = new AppArchivingCardCheck();
                                    vo.setAccArchivingId(qvo.getArchivingId());//建档立卡外键
                                    vo.setAccSignId(people.getSignId());//签约单主键
                                    vo.setAccYear(ExtendDate.getYYYY(form.getSignFromDate()));//签约年
                                    vo.setAccYearMonth(ExtendDate.getYM(form.getSignFromDate()));//签约年月
                                    vo.setAccPatientNo(qvo.getPatientNo());// 用户编号
                                    vo.setAccFamilySize(qvo.getFamilySize());// 家庭人口数
                                    vo.setAccPovertyState(qvo.getPovertyState());// 是否脱贫(0否 1是)
                                    vo.setAccPatientName(qvo.getPatientName());// 姓名
                                    vo.setAccPatientSex(qvo.getPatientSex());// 性别
                                    vo.setAccPatientBirthDay(ExtendDate.getCalendar(qvo.getPatientBirthDay()));// 出生日期
                                    vo.setAccPatientIdno(qvo.getPatientIdno());// 身份证号
                                    vo.setAccHouseholdRelationship(qvo.getHouseholdRelationship());// 与户主的关系
                                    vo.setAccResidenceState(qvo.getResidenceState());// 有无户籍（0无 1有）
                                    vo.setAccFpaState(qvo.getFpaState());// 有无纳入计生管理（0无 1有）
                                    vo.setAccArchivingCardState(qvo.getArchivingCardState());// 有无纳入建档立卡人口（0无 1有）
                                    vo.setAccMaritalStatus(qvo.getMaritalStatus());// 婚姻状况（0未 1初 2再 3离 4丧偶）
                                    vo.setAccFamilyType(qvo.getFamilyType());// 计生家庭户类型（1无孩 2一男 3一女 4二女 5一女一男 6一男一女 7三孩 8四孩 9五孩以上）
                                    vo.setAccPatientCard(qvo.getPatientCard());// 社会保障号
                                    vo.setAccPatientJmda(qvo.getPatientJmda());// 居民健康档案号
                                    vo.setAccDomicilePlaceCounty(qvo.getDomicilePlaceCounty());// 户籍地（县）
                                    vo.setAccDomicilePlaceTown(qvo.getDomicilePlaceTown());// 户籍地乡
                                    vo.setAccDomicilePlaceVillage(qvo.getDomicilePlaceVillage());// 户籍地村
                                    vo.setAccDonicilePlace(qvo.getAccDonicilePlace());//户籍地(县乡村）

                                    vo.setAccResidencePlaceCounty(qvo.getResidencePlaceCounty());//  居住地县
                                    vo.setAccResidencePlaceTown(qvo.getResidencePlaceTown());// 居住地乡
                                    vo.setAccResidencePlaceVillage(qvo.getResidencePlaceVillage());// 居住地村
                                    vo.setAccResidencePlace(qvo.getResidencePlace());// 居住地(县乡村）

                                    vo.setAccResidencePlaceAddr(qvo.getResidencePlaceAddr());// 居住地详细地址（门牌号）
                                    vo.setAccPatientTel(qvo.getPatientTel());// 联系电话
                                    if(qvo.getServiceType()!=null && qvo.getServiceType().length>0){
                                        String serviceType = "";
                                        for(String fwType:qvo.getServiceType()){
                                            if(StringUtils.isBlank(serviceType)){
                                                serviceType = fwType;
                                            }else{
                                                serviceType += ";"+fwType;
                                            }
                                        }
                                        vo.setAccServiceType(serviceType);// 服务类型（1一般人群 2.0-6岁儿童 3孕产妇 4.65岁以上老年人 5高血压患者 6糖尿病患者 7严重精神障碍患者 8肺结核患者 9残疾人 ）
                                    }

                                    vo.setAccSpecialFamily(qvo.getSpecialFamily());//是否计生特殊家庭（0否 1是）
                                    vo.setAccTotalFee(qvo.getTotalFee());//诊疗费用
                                    if(qvo.getIllnessName()!= null && qvo.getIllnessName().length>0){
                                        String illnessName = "";
                                        for (String ill:qvo.getIllnessName()){
                                            if(StringUtils.isBlank(illnessName)){
                                                illnessName = ill;
                                            }else{
                                                illnessName += ";"+ill;
                                            }
                                        }
                                        vo.setAccIllnessName(illnessName);//大病名称
                                    }
                                    vo.setAccPaperSignState(qvo.getPaperSignState());//纸质签约（0未签约 1已签约）
                                    vo.setAccSignAgreementState(qvo.getSignAgreementState());//有无签约协议（0无 1有）
                                    vo.setAccServiceHandbookState(qvo.getServiceHandbookState());// 有无服务手册(0无 1有)
                                    vo.setAccContactCardState(qvo.getContactCardState());// 有无爱心服务卡（联络卡0无 1有）
                                    vo.setAccSignFromDate(form.getSignFromDate());// 签约服务开始时间
                                    vo.setAccSignToDate(form.getSignToDate());// 签约服务结束时间
                                    vo.setAccNotSignReason(qvo.getNotSignReason());// 未落实签约原因
                                    vo.setAccNotSignReasonOther(qvo.getNotSignReasonOther());// 未落实签约其它原因
                                    if(StringUtils.isNotBlank(qvo.getJmdaTime())){
                                        vo.setAccJmdaTime(ExtendDate.getCalendar(qvo.getJmdaTime()));// 居民健康档案时间
                                    }
                                    if(StringUtils.isNotBlank(qvo.getJmdaLastUpdateTime())){
                                        vo.setAccJmdaLastUpdateTime(ExtendDate.getCalendar(qvo.getJmdaLastUpdateTime()));// 居民健康档案最后更新时间
                                    }
                                    vo.setAccSignPeNum(qvo.getSignPeNum());// 签约后健康体检次数
                                    if(StringUtils.isNotBlank(qvo.getLastPeTime())){
                                        vo.setAccLastPeTime(ExtendDate.getCalendar(qvo.getLastPeTime()));// 最后一次健康体检时间
                                    }
                                    vo.setAccEtFollowNum(qvo.getEtFollowNum());// 儿童随访次数
                                    if(StringUtils.isNotBlank(qvo.getEtLastFollowTime())){
                                        vo.setAccEtLastFollowTime(ExtendDate.getCalendar(qvo.getEtLastFollowTime()));// 儿童最后一次随访时间
                                    }

                                    vo.setAccYcfFollowNum(qvo.getYcfFollowNum());// 孕产妇随访次数
                                    if(StringUtils.isNotBlank(qvo.getYcfLastFollowTime())){
                                        vo.setAccYcfLastFollowTime(ExtendDate.getCalendar(qvo.getYcfLastFollowTime()));// 孕产妇最后一次随访时间
                                    }

                                    vo.setAccGxyNum(qvo.getGxyNum());// 高血压患者测血压次数
                                    if(StringUtils.isNotBlank(qvo.getGxyLastTime())){
                                        vo.setAccGxyLastTime(ExtendDate.getCalendar(qvo.getGxyLastTime()));// 高血压患者最后一次测血压时间
                                    }

                                    vo.setAccGxyFollowNum(qvo.getGxyFollowNum());// 高血压患者随访次数

                                    if(StringUtils.isNotBlank(qvo.getGxyLastFollowTime())){
                                        vo.setAccGxyLastFollowTime(ExtendDate.getCalendar(qvo.getGxyLastFollowTime()));//  高血压最后一次随访时间
                                    }
                                    vo.setAccTnbNum(qvo.getTnbNum());// 糖尿病测血糖次数
                                    if(StringUtils.isNotBlank(qvo.getTnbLastTime())){
                                        vo.setAccTnbLastTime(ExtendDate.getCalendar(qvo.getTnbLastTime()));// 糖尿病患者最后一次测血糖时间
                                    }

                                    vo.setAccTnbFollowNum(qvo.getTnbFollowNum());// 糖尿病患者随访次数
                                    if(StringUtils.isNotBlank(qvo.getTnbLastFollowTime())){
                                        vo.setAccTnbLastFollowTime(ExtendDate.getCalendar(qvo.getTnbLastFollowTime()));// 糖尿病患者最后一次随访时间
                                    }

                                    vo.setAccJsbFollowNum(qvo.getJsbFollowNum());// 严重精神障碍患者随访次数
                                    if(StringUtils.isNotBlank(qvo.getJsbLastFollowTime())){
                                        vo.setAccJsbLastFollowTime(ExtendDate.getCalendar(qvo.getJsbLastFollowTime()));// 严重精神障碍患者最后一次随访时间
                                    }

                                    vo.setAccTotalCost(qvo.getTotalCost());//总费用
                                    vo.setAccZfFee(qvo.getZfFee());//自付金额
                                    vo.setAccNcmsFee(qvo.getNcmsFee());//新农合报销
                                    vo.setAccCivilAssistance(qvo.getCivilAssistance());//民政补助
                                    vo.setAccSgcpa(qvo.getSgcpa());//扶贫叠加保障补偿
                                    vo.setAccOtherFund(qvo.getOtherFund());//其他

                                    vo.setAccKnowHelpPoorPolicy(qvo.getKnowHelpPoorPolicy());// 是否知晓健康扶贫政策（0不知道 1知道 2有听说，但具体内容不了解）
                                    vo.setAccSatisfiedState(qvo.getSatisfiedState());// 对签约服务是否满意（1满意 2基本满意 3不满意）
                                    vo.setAccInspectorOneUrl(qvo.getInspectorOneUrl());// 核查员签名（计生管理员）
                                    vo.setAccInspectorTwoUrl(qvo.getInspectorTwoUrl());// 核查员签名(医务人员)
                                    if(StringUtils.isNotBlank(qvo.getInspectorOneUrl())){//计生管理员签名
                                        if(StringUtils.isNotBlank(qvo.getCheckDate())){
                                            vo.setAccCheckDate(ExtendDate.getCalendar(qvo.getCheckDate()));// 核查日期（计生管理员）
                                        }
                                    }

                                    if(StringUtils.isNotBlank(qvo.getInspectorTwoUrl())){//医务人员签名
                                        if(StringUtils.isNotBlank(qvo.getCheckYwDate())){
                                            vo.setAccCheckYwDate(ExtendDate.getCalendar(qvo.getCheckYwDate()));//核查日期（医务人员）
                                        }
                                    }

                                    vo.setAccCreateId(qvo.getCreateId());
                                    vo.setAccCreateName(qvo.getCreateName());
                                    vo.setAccObjectType(qvo.getObjectType());
                                    sysDao.getServiceDo().add(vo);
                                    this.jsons.setCode("800");
                                }else{
                                    this.jsons.setMsg("该居民未签约，请先签约");
                                    this.jsons.setCode("900");
                                }
                            }else{
                                this.jsons.setMsg("该居民未签约，请先签约");
                                this.jsons.setCode("900");
                            }
                        }else{
                            this.jsons.setMsg("查无该建档立卡人员信息!");
                            this.jsons.setCode("900");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "json";
    }
    /**
     * 导出数据
     * cxw
     * @throws Exception
     */
    public void pListPrintByPeoIds() throws Exception{
        AppArchivingCardPeopleQvo qvo = (AppArchivingCardPeopleQvo)getJsonLay(AppArchivingCardPeopleQvo.class);
//        AppArchivingCardPeopleQvo qvo = new AppArchivingCardPeopleQvo();
        String type = getRequest().getParameter("typeNum");
        String numberCount = getRequest().getParameter("numberCount");
        if(type.equals("2")){
            qvo.setPageSize(99999999);
        }else{
            qvo.setPageSize(Integer.valueOf(numberCount));
        }
        CdUser user = this.getSessionUser();
        if(user!=null){
            if(StringUtils.isNotBlank(user.getDrId())){
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,user.getDrId());
                if(drUser != null){
                    if(drUser.getDrRole().equals(AppRoleType.YISHENG.getValue())){
                        qvo.setRole(AppRoleType.YISHENG.getValue());
                        String teamIds = sysDao.getAppTeamMemberDao().findByDrId(drUser.getId());
                        qvo.setTeamIds(teamIds);
                    }
                }
            }
        }
        String strIds = "";
        //查询建档立卡花名册人员主键
        List<Map> LsMap = sysDao.getAppArchivingCheckDao().findByQvo(qvo);
        if(LsMap != null && LsMap.size()>0){
            strIds = LsMap.get(0).get("archivingId").toString();
        }
        if(StringUtils.isBlank(strIds)){
            return;
        }
        InputStream is2 = new FileInputStream(this.getRequest().getSession().getServletContext().getRealPath("/")+"\\intercept\\file\\jdlkhcb.xls");
        OutputStream out=this.getResponse().getOutputStream();
        this.getResponse().reset();
        this.getResponse().setHeader("Content-disposition", "attachment; filename=jdlkhcb.xls");
        this.getResponse().setContentType("application/msexcel");
        POIFSFileSystem fs = new POIFSFileSystem(is2);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);

//        HSSFRow rowT = sheet.createRow(4);
//        rowT.createCell(1).setCellValue("行政区划代码");//行政区划
//        rowT.createCell(2).setCellValue("县");//县
//        rowT.createCell(3).setCellValue("乡");//乡镇
//        rowT.createCell(4).setCellValue("村");//村
//
//        rowT.createCell(6).setCellValue("户编号");//户编号
//        rowT.createCell(7).setCellValue("家庭人口数");
//        rowT.createCell(8).setCellValue("是否脱贫");
//
//        rowT.createCell(9).setCellValue("姓名");
//        rowT.createCell(10).setCellValue("性别");
//        rowT.createCell(11).setCellValue("出生日期");
//        rowT.createCell(12).setCellValue("身份证号");
//        rowT.createCell(13).setCellValue("与户主关系");
//        rowT.createCell(14).setCellValue("婚姻状况");
//        rowT.createCell(15).setCellValue("计生家庭户类型");
//        rowT.createCell(16).setCellValue("社会保障号");
//        rowT.createCell(17).setCellValue("居民健康档案号");
//        rowT.createCell(18).setCellValue("户籍地");
//        rowT.createCell(19).setCellValue("现居住地");
//        rowT.createCell(20).setCellValue("详细地址（门牌号）");
//        rowT.createCell(21).setCellValue("联系电话");
//
//        rowT.createCell(22).setCellValue("服务类型");
//        rowT.createCell(23).setCellValue("是否计生特殊家庭");
//        rowT.createCell(24).setCellValue("大病名称");
//        rowT.createCell(25).setCellValue(ExtendDate.getYYYY(Calendar.getInstance())+"年诊疗费用总额（元）");
//
//        rowT.createCell(26).setCellValue("纸质签约");
//        rowT.createCell(27).setCellValue("电子签约");
//        rowT.createCell(28).setCellValue("签约服务开始时间");
//        rowT.createCell(29).setCellValue("签约服务终止时间");
//        rowT.createCell(30).setCellValue("有无签约协议");
//        rowT.createCell(31).setCellValue("有无服务手册");
//        rowT.createCell(32).setCellValue("有无爱心服务卡（联络卡）");
//        rowT.createCell(33).setCellValue("未落实签约原因");
//        rowT.createCell(34).setCellValue("其它原因备注栏");

        int i=5;
        for(String peopleId:strIds.split(",")){
            HSSFRow row = sheet.createRow(i);
            AppArchivingCardPeople people = (AppArchivingCardPeople)sysDao.getServiceDo().find(AppArchivingCardPeople.class,peopleId);
            if(people != null){
                AppArchivingCheckEntity table = sysDao.getAppArchivingCheckDao().findByArchivingId(peopleId,people.getSignId());
                if(table!=null){
                    row.createCell(0).setCellValue(i-4);//序号
                    row.createCell(1).setCellValue(people.getAddrVillageCode());//行政区划
                    row.createCell(2).setCellValue(people.getAddrCountyName());//县
                    row.createCell(3).setCellValue(people.getAddrRuralName());//乡镇
                    row.createCell(4).setCellValue(people.getAddrVillageName());//村

                    if(StringUtils.isNotBlank(table.getObjectType())){//所属建档立卡对象
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDLKOBJECTTYPE,table.getObjectType());
                        if(value != null){
                            row.createCell(5).setCellValue(value.getCodeTitle());
                        }
                    }


                    //扶贫办建档情况
                    row.createCell(6).setCellValue(table.getPatientNo());//户编号
                    row.createCell(7).setCellValue(table.getFamilySize());//人数
                    if(StringUtils.isNotBlank(table.getPovertyState())){//是否脱贫
                        if("1".equals(table.getPovertyState())){
                            row.createCell(8).setCellValue("是");
                        }else{
                            row.createCell(8).setCellValue("否");
                        }
                    }else{
                        row.createCell(8).setCellValue("");
                    }

                    //基本信息
                    row.createCell(9).setCellValue(table.getPatientName());//姓名
                    row.createCell(10).setCellValue(table.getPatientSex());//性别
                    row.createCell(11).setCellValue(table.getPatientBirthDay());//出生日期
                    row.createCell(12).setCellValue(table.getPatientIdno());//身份证
                    if(StringUtils.isNotBlank(table.getHouseholdRelationship())){//与户主关系
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_HZGX,table.getMaritalStatus());
                        if(value != null){
                            row.createCell(13).setCellValue(value.getCodeTitle());
                        }
                    }
                    if(StringUtils.isNotBlank(table.getMaritalStatus())){//婚姻状况
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDLKMARITALSTATUS,table.getMaritalStatus());
                        if(value != null){
                            row.createCell(14).setCellValue(value.getCodeTitle());
                        }
                    }else{
                        row.createCell(14).setCellValue("");
                    }

                    if(StringUtils.isNotBlank(table.getFamilyType())){//类型
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDLKFAMILYTYPE,table.getFamilyType());
                        if(value != null){
                            row.createCell(15).setCellValue(value.getCodeTitle());
                        }
                    }else{
                        row.createCell(15).setCellValue("");
                    }

                    row.createCell(16).setCellValue(table.getPatientCard());//社会保障号
                    row.createCell(17).setCellValue(table.getPatientJmda());//居民档案号
                    row.createCell(18).setCellValue(table.getAccDonicilePlace());//户籍地
                    row.createCell(19).setCellValue(table.getResidencePlace());//居住地
                    row.createCell(20).setCellValue(table.getResidencePlaceAddr());//详细地址
                    row.createCell(21).setCellValue(table.getPatientTel());//联系电话

                    //建档立卡贫困户类型
                    String sss = "";
                    if(StringUtils.isNotBlank(table.getServiceType())){//服务类型
                        String[] serveTypes = table.getServiceType().split(";");
                        for(String ss:serveTypes){
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_RESIDENTMANGE,ss);
                            if(value != null){
                                if(StringUtils.isBlank(sss)){
                                    sss = value.getCodeTitle();
                                }else{
                                    sss += ";"+value.getCodeTitle();
                                }
                            }
                        }
                    }
                    row.createCell(22).setCellValue(sss);
                    if(StringUtils.isNotBlank(table.getSpecialFamily())){
                        if("1".equals(table.getSpecialFamily())){//是否计生特殊家庭
                            row.createCell(23).setCellValue("是");
                        }else{
                            row.createCell(23).setCellValue("否");
                        }
                    }

                    String sns = "";
                    if(StringUtils.isNotBlank(table.getIllnessName())){//大病名称
                        String[] snss = table.getIllnessName().split(";");
                        for(String sn:snss){
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ILLNESSNAME,sn);
                            if(value != null){
                                if(StringUtils.isBlank(sns)){
                                    sns = value.getCodeTitle();
                                }else{
                                    sns += ";"+value.getCodeTitle();
                                }
                            }
                        }
                    }

                    row.createCell(24).setCellValue(sns);
                    row.createCell(25).setCellValue(table.getTotalFee());//2017年诊疗费用总额

                    //签约情况
                    if(StringUtils.isNotBlank(table.getPaperSignState())){//纸质签约
                        if("1".equals(table.getPaperSignState())){
                            row.createCell(26).setCellValue("已签约");
                        }else{
                            row.createCell(26).setCellValue("未签约");
                        }
                    }

                    if(StringUtils.isNotBlank(table.getSignFromDate())){
                        row.createCell(27).setCellValue("已签约");//电子签约
                    }else{
                        row.createCell(27).setCellValue("未签约");//电子签约
                    }

                    row.createCell(28).setCellValue(table.getSignFromDate());//签约开始时间
                    row.createCell(29).setCellValue(table.getSignToDate());//签约结束时间

                    if(StringUtils.isNotBlank(table.getSignAgreementState())){//有无签约协议
                        if("1".equals(table.getSignAgreementState())){
                            row.createCell(30).setCellValue("有");
                        }else{
                            row.createCell(30).setCellValue("无");
                        }
                    }else{
                        row.createCell(30).setCellValue("无");
                    }

                    if(StringUtils.isNotBlank(table.getServiceHandbookState())){//有无服务手册
                        if("1".equals(table.getServiceHandbookState())){
                            row.createCell(31).setCellValue("有");
                        }else{
                            row.createCell(31).setCellValue("无");
                        }
                    }else{
                        row.createCell(31).setCellValue("无");
                    }

                    if(StringUtils.isNotBlank(table.getContactCardState())){//有无爱心服务卡（联络卡）
                        if("1".equals(table.getContactCardState())){
                            row.createCell(32).setCellValue("有");
                        }else{
                            row.createCell(32).setCellValue("无");
                        }
                    }else{
                        row.createCell(32).setCellValue("无");
                    }

                    if(StringUtils.isBlank(table.getSignFromDate())){//没有电子签约
                        if(StringUtils.isNotBlank(table.getNotSignReason())){
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDLKNOTSIGNREASON,table.getNotSignReason());
                            if(value != null){
                                row.createCell(33).setCellValue(value.getCodeTitle());//未落实签约原因
                            }
                            if("13".equals(table.getNotSignReason())){//其他签约原因
                                row.createCell(34).setCellValue(table.getNotSignReasonOther());//未落实签约原因
                            }
                        }

                    }

                    /**
                     * 履约情况
                     */
                    //所有人群
                    row.createCell(35).setCellValue(table.getJmdaTime());//居民健康档案建档时间
                    row.createCell(36).setCellValue(table.getJmdaLastUpdateTime());//居民健康档案最后更新时间
                    row.createCell(37).setCellValue(table.getSignPeNum());//签约后健康体检次数
                    row.createCell(38).setCellValue(table.getLastPeTime());//最后一次健康体检时间

                    //0-6岁儿童
                    row.createCell(39).setCellValue(table.getEtFollowNum());//签约后随访次数
                    row.createCell(40).setCellValue(table.getEtLastFollowTime());//最后一次随访时间

                    //孕产妇
                    row.createCell(41).setCellValue(table.getYcfFollowNum());//签约后随访次数
                    row.createCell(42).setCellValue(table.getYcfLastFollowTime());//最后一次随访时间

                    //高血压患者
                    row.createCell(43).setCellValue(table.getGxyNum());//签约后测血压次数
                    row.createCell(44).setCellValue(table.getGxyLastTime());//最后一次测血压时间
                    row.createCell(45).setCellValue(table.getGxyFollowNum());//签约后随访次数
                    row.createCell(46).setCellValue(table.getGxyLastFollowTime());//最后一次随访时间

                    //糖尿病患者
                    row.createCell(47).setCellValue(table.getTnbNum());//签约后测血糖次数
                    row.createCell(48).setCellValue(table.getTnbLastTime());//最后一次测血糖时间
                    row.createCell(49).setCellValue(table.getTnbFollowNum());//签约后随访次数
                    row.createCell(50).setCellValue(table.getTnbLastFollowTime());//最后一次随访时间

                    //严重精神病患者
                    row.createCell(51).setCellValue(table.getJsbFollowNum());//签约后随访次数
                    row.createCell(52).setCellValue(table.getJsbLastFollowTime());//最后一次随访时间

                    //年度在基层医疗机构救治资金构成情况
                    row.createCell(53).setCellValue(table.getTotalCost());//总费用
                    row.createCell(54).setCellValue(table.getZfFee());//自付金额
                    row.createCell(55).setCellValue(table.getNcmsFee());//新农合报销
                    row.createCell(56).setCellValue(table.getCivilAssistance());//民政补助
                    row.createCell(57).setCellValue(table.getSgcpa());//扶贫叠加保障补偿
                    row.createCell(58).setCellValue(table.getOtherFund());//其他
                    //政策知晓与满意度
                    if(StringUtils.isNotBlank(table.getKnowHelpPoorPolicy())){//是否知晓健康扶贫政策
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_KNOWHELPPOORPOLICY,table.getKnowHelpPoorPolicy());
                        if(value != null){
                            row.createCell(59).setCellValue(value.getCodeTitle());
                        }
                    }

                    if(StringUtils.isNotBlank(table.getSatisfiedState())){//对签约服务是否满意
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SATISFIEDSTATE,table.getSatisfiedState());
                        if(value != null){
                            row.createCell(60).setCellValue(value.getCodeTitle());
                        }
                    }
                    i++;
                }
            }

        }
        wb.write(out);
        out.close();
    }

    public String findDrList(){
        try{
            Map<String,Object> map = new HashMap<>();
            List<CdCode> lsJs = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JSADMIN, CommonEnable.QIYONG.getValue());
            map.put("jsAdmin",lsJs);
            CdUser us= this.getSessionUser();
            if(us!=null) {
                if (us.getHospId() != null) {
                    List<AppDrUser> ls = sysDao.getAppDrUserDao().findAllByState(us.getHospId());
                    map.put("drList",ls);
                }
            }
            //查询与户主关系数据
            List<CdCode> lshz = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HZGX, CommonEnable.QIYONG.getValue());
            map.put("lsHz",lshz);
            this.jsons.setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(getClass(), getAct(), e);
        }
        return "json";
    }

    //居民信息查询
    public String findArchivingWeb(){
        try {
            AppArchivingCardPeopleQvo qvo = (AppArchivingCardPeopleQvo)getJsonLay(AppArchivingCardPeopleQvo.class);
            if(qvo==null){
                qvo=new AppArchivingCardPeopleQvo();
            }
            CdUser user = this.getSessionUser();
            if(user!=null){
                if(StringUtils.isNotBlank(user.getDrId())){
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,user.getDrId());
                    if(drUser != null){
                        if(drUser.getDrRole().equals(AppRoleType.YISHENG.getValue())){
                            qvo.setRole(AppRoleType.YISHENG.getValue());
                            qvo.setDrId(drUser.getId());
                            String teamIds = sysDao.getAppTeamMemberDao().findByDrId(drUser.getId());
                            qvo.setTeamIds(teamIds);
                        }
                    }
                }
            }
            List<AppArchivingCardCPEntiy> ls = sysDao.getAppArchivingCheckDao().findPeopleList(qvo);
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }
}
