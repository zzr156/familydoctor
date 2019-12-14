package com.ylz.view.common.action;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.entity.CdAddressEntity;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.aioEntity.AppTeamAioEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.ResidentHealthFiles;
import com.ylz.bizDo.jtapp.basicHealthEntity.ResidentRecordsEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppHospEntity;
import com.ylz.bizDo.jtapp.commonVo.AppAddressQvo;
import com.ylz.bizDo.jtapp.commonVo.AppAgreeMentQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.AppServeEntity;
import com.ylz.bizDo.jtapp.drEntity.AppSignFormListEntity;
import com.ylz.bizDo.jtapp.drEntity.DrInfo;
import com.ylz.bizDo.jtapp.drEntity.PatientInfo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPatientResgisterQvo;
import com.ylz.bizDo.jtapp.patientVo.AppTeamVo;
import com.ylz.bizDo.mangecount.entity.ManageTeamCountEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.*;

/**
 * 一体机接口
 */
@Action(
        value = "appAio",
        results = {
                @Result(name = "ajson", type = "json", params = {"root", "ajson",
                        "excludeProperties","vo.*\\.myServiceMenu,vo.*\\.recommendedService,vo.*\\.strPatientMenuRole","contentType", "application/json"})
        }
)
public class AppAioAction extends CommonAction {

    private static final int yxDay = 15;

    /**
     * 一体机登录接口
     *
     * @return
     */
    public String appLoginRegisterAio() {
        try {
            AppPatientResgisterQvo qvo = (AppPatientResgisterQvo) getAppJson(AppPatientResgisterQvo.class);
            if (qvo != null) {
                AppPatientUserEntity patient = this.getSysDao().getAppPatientUserDao().findByUserIdNoAndCard(qvo.getUserIdNo(), qvo.getUserCard());
                if (patient == null) {
                    String resultIdNo = null;
                    if (StringUtils.isNotBlank(qvo.getUserIdNo())) {
                        qvo.setUserIdNo(qvo.getUserIdNo().toLowerCase());
                        resultIdNo = CardUtil.IDCardValidate(qvo.getUserIdNo());
                    }
                    if (StringUtils.isNotBlank(resultIdNo)) {
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(resultIdNo);
                        return "ajson";
                    } else {
                        AppPatientUser user = new AppPatientUser();
                        if (StringUtils.isNotBlank(qvo.getUserIdNo())) {
                            Map<String, Object> idNos;
                            if (qvo.getUserIdNo().length() == 18) {
                                idNos = CardUtil.getCarInfo(qvo.getUserIdNo());
                            } else {
                                idNos = CardUtil.getCarInfo15W(qvo.getUserIdNo());
                            }
                            user.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));//出生日期
                            user.setPatientAge(AgeUtil.getAgeYear(user.getPatientBirthday()));//年龄
                            user.setPatientGender(idNos.get("sex").toString());//性别
                            user.setPatientIdno(qvo.getUserIdNo().toUpperCase());//身份证
                        }
                        if (StringUtils.isNotBlank(qvo.getUserName())) {
                            user.setPatientName(qvo.getUserName());
                        }
                        if (StringUtils.isNotBlank(qvo.getUserCard())) {
                            user.setPatientCard(qvo.getUserCard());
                        }
                        user.setPatientTel(qvo.getUserTel());
                        user.setPatientState(CommonEnable.QIYONG.getValue());
                        user.setPatientHealthy(CommonEnable.JINYONG.getValue());
                        user.setPatientJgState(UserJgType.WEISHEZHI.getValue());
                        user.setPatientEaseState(UserJgType.WEISHEZHI.getValue());
                        user.setPatientUpHpis(UserUpHpisType.WEIJIHUO.getValue());//用户未激活
                        this.getSysDao().getServiceDo().add(user);
                        this.getSysDao().getServiceDo().removePoFormSession(user);
                        patient = this.getSysDao().getAppPatientUserDao().findByUserIdNoAndCard(qvo.getUserIdNo(), qvo.getUserCard());
                    }
                }
                if (patient != null) {
                    AppPatientUser user = (AppPatientUser) this.getSysDao().getServiceDo().find(AppPatientUser.class, patient.getId());
                    if (StringUtils.isNotBlank(patient.getPatientNeighborhoodCommittee())) {
                        AppHospDept dept = (AppHospDept) this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientNeighborhoodCommittee());
                        if (dept != null) {
                            patient.setPatientCommunity(dept.getId());
                        } else {
                            dept = (AppHospDept) this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                            if (dept != null) {
                                patient.setPatientCommunity(dept.getId());
                            }
                        }
                    } else if (StringUtils.isNotBlank(patient.getPatientStreet())) {
                        AppHospDept dept = (AppHospDept) this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                        if (dept != null) {
                            patient.setPatientCommunity(dept.getId());
                        }
                    }
                    Md5Util util = new Md5Util();
                    String key = qvo.getUserIdNo() + ExtendDate.getYMD_h_m_s(Calendar.getInstance());
                    key = util.MD516(key);
                    AppDrPatientKey uKey = this.sysDao.getAppDrPatientKeyDao().findByDoctorOrPatientId(patient.getId());
                    String day = ExtendDateUtil.addDate(ExtendDate.getYMD(Calendar.getInstance()), ExtendDateType.DAYS.getValue(), yxDay);
                    if (uKey != null) {
                        uKey.setDrToken(key);
                        uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                        uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
                        patient.setPatientFirstState(CommonEnable.QIYONG.getValue());
                        this.sysDao.getServiceDo().modify(uKey);
                    } else {
                        uKey = new AppDrPatientKey();
                        uKey.setDrToken(key);
                        uKey.setDrPatientId(patient.getId());
                        uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                        uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
                        patient.setPatientFirstState(CommonEnable.JINYONG.getValue());
                        this.getSysDao().getServiceDo().add(uKey);
                    }
                    user.setPatientJgState(UserJgType.YISHEZHI.getValue());
                    this.sysDao.getServiceDo().modify(user);
                    this.getAjson().setUkey(key);
                    this.getAjson().setVo(patient);
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 查询省市区街道
     *
     * @return
     */
    public String appAddressResultAio() {
        try {
            AppAddressQvo qvo = (AppAddressQvo) this.getAppJson(AppAddressQvo.class);
            if (qvo != null) {
                if (StringUtils.isNotBlank(qvo.getUpId())) {
                    CdAddress entity = this.getSysDao().getCdAddressDao().findByCacheCode(qvo.getUpId());
                    if (entity != null) {
                        if (entity.getLevel().equals("3")) {
                            List<AppHospEntity> ls = this.getSysDao().getAppHospDeptDao().findByAreaId(qvo.getUpId());
                            this.getAjson().setRows(ls);
                        } else {
                            List<CdAddressEntity> ls = this.getSysDao().getCdAddressDao().findByUpIdOrNot(qvo.getUpId(),qvo.getSource());
                            this.getAjson().setRows(ls);
                        }
                    }
                } else {
                    List<CdAddressEntity> ls = this.getSysDao().getCdAddressDao().findByUpIdOrNot(qvo.getUpId());
                    this.getAjson().setRows(ls);
                }

            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }


    /**
     * 查询团队
     *
     * @return
     */
    public String appTeamListTwoAio() {
        try {
            AppTeamVo qvo = (AppTeamVo) getAppJson(AppTeamVo.class);
            if (qvo != null) {
                //根据机构id查询团队
                if (StringUtils.isNotBlank(qvo.getTeamHospId())) {
                    Map<String, Object> map = new HashMap<>();
                    AppHospDept hosp = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class, qvo.getTeamHospId());
                    //查询对应机构下的对团队 返回团队id、团队名称、团队下的成员数量
                    List<AppTeamAioEntity> teamE = this.sysDao.getAppTeamDao().findTeammList(qvo);
                    if (hosp != null) {
                        this.getAjson().setMsg(hosp.getHospName());
                        if (StringUtils.isNotBlank(hosp.getHospAreaCode())) {
                            String cityCode = AreaUtils.getAreaCode(hosp.getHospAreaCode(), "2");
                            CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                            String[] ids = hosp.getId().split("_");
                            String id = null;
                            if (ids.length > 1) {
                                id = ids[1];
                            } else {
                                id = ids[0];
                            }
                            map.put("orgId", id);
                            map.put("urlType", code.getCodeTitle());
                        }

                    }
                    this.getAjson().setRows(teamE);
                    CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(hosp.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                    if (cdAdd != null) {
                        map.put("toplimit", cdAdd.getAreaSignWay());
                    }
                    this.getAjson().setMap(map);
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 套餐内容
     *
     * @return
     */
    public String appServeAioList() {
        try {
            AppTeamVo qvo = (AppTeamVo) getAppJson(AppTeamVo.class);
            if (qvo != null) {
                //根据机构id查询套餐
                if (StringUtils.isNotBlank(qvo.getTeamHospId())) {
                    List<AppServeEntity> list = this.getSysDao().getAppServeSetmealDao().findSeverMeal(qvo.getTeamHospId());
                    this.getAjson().setRows(list);
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("机构参数不能为空!");
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 查看协议内容
     *
     * @return
     */
    public String appAgreeMentAio() {
        try {
            AppAgreeMentQvo qvo = (AppAgreeMentQvo) this.getAppJson(AppAgreeMentQvo.class);
            if (qvo != null) {
                if (StringUtils.isNotBlank(qvo.getType())) {
                    if (qvo.getType().equals(CommonUseType.XITONG.getValue())) {
                        AppAgreement v = this.getSysDao().getAppAgreementDao().findByCityId(this.getAppPatientUser().getPatientCity());
                        if (v != null) {
                            Map<String, String> map = ExtendDate.getDateVaild(Calendar.getInstance());
                            String content = v.getMentContent();
                            content = content.replace("{1}", map.get("start"));
                            content = content.replace("{2}", map.get("end"));
                            this.getAjson().setMsg(content);
                        } else {
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无协议书,请联系管理员");
                        }
                    } else {
                        String addrxx = "";
                        String xyGroup = "";
                        String drName = "";
                        AppTeam team = (AppTeam) this.getSysDao().getServiceDo().find(AppTeam.class, qvo.getTeamId());//团队信息
                        AppPatientUser patientUser = (AppPatientUser) this.getSysDao().getServiceDo().find(AppPatientUser.class, qvo.getPatientId());//患者信息
                        if (team != null && patientUser != null) {
                            AppAgreement v = this.getSysDao().getAppAgreementDao().findByHospId(team.getTeamHospId(), qvo.getQyType());
                            AppHospDept dept = (AppHospDept) this.getSysDao().getServiceDo().find(AppHospDept.class, team.getTeamHospId());
                            if (v != null && dept != null) {
                                AppSignForm form = new AppSignForm();
                                if ("1".equals(qvo.getState())) {
                                    form = null;
                                } else {
                                    form = this.getSysDao().getAppSignformDao().getSignFormUserId(patientUser.getId());
                                }
                                CdAddress address = (CdAddress) this.getSysDao().getServiceDo().find(CdAddress.class, dept.getHospUpcityareaId());
                                if("350702".equals(AreaUtils.getAreaCode(dept.getHospAreaCode(),"3"))){
                                    xyGroup = CodeGroupConstrats.GROUP_YPXY;
                                }else{
                                    xyGroup = CodeGroupConstrats.GROUP_NPXY;
                                }
                                if("4".equals(dept.getHospLevel())){//街道级查机构上一级区级地址
                                    CdAddress adrs = (CdAddress)this.getSysDao().getServiceDo().find(CdAddress.class,dept.getHospUpcityareaId());
                                    if(adrs != null){
                                        addrxx = adrs.getAreaSname();
                                    }
                                }else if("3".equals(dept.getHospLevel())){//区级直接查取本区级地址
                                    CdAddress adrs = (CdAddress)this.getSysDao().getServiceDo().find(CdAddress.class,dept.getHospAreaCode());
                                    if(adrs != null){
                                        addrxx = adrs.getAreaSname();
                                    }
                                }else if("5".equals(dept.getHospLevel())){
                                    CdAddress adrs = (CdAddress)this.getSysDao().getServiceDo().find(CdAddress.class,AreaUtils.getAreaCode(dept.getHospAreaCode(),"3")+"000000");
                                    if(adrs != null){
                                        addrxx = adrs.getAreaSname();
                                    }
                                }
                                AppDrUser user = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, team.getTeamDrId());
                                Map<String, String> map = ExtendDate.getDateVaild(Calendar.getInstance());
                                String content = v.getMentContent();
                                String patientName = "";
                                if (StringUtils.isNotBlank(patientUser.getPatientName())) {
                                    patientName = patientUser.getPatientName();
                                }
                                String patientIdno = "";
                                if (StringUtils.isNotBlank(patientUser.getPatientIdno())) {
                                    patientIdno = patientUser.getPatientIdno();
                                }
                                String patientAddress = "";
                                if (patientUser != null) {
                                    if(StringUtils.isNotBlank(patientUser.getPatientProvince())){
                                        CdAddress provience = (CdAddress) this.getSysDao().getServiceDo().find(CdAddress.class, patientUser.getPatientProvince());
                                        if(provience != null) {
                                            patientAddress += provience.getAreaSname();
                                        }
                                    }
                                    if(StringUtils.isNotBlank(patientUser.getPatientCity())){
                                        CdAddress city = (CdAddress) this.getSysDao().getServiceDo().find(CdAddress.class, patientUser.getPatientCity());
                                        if(city != null) {
                                            patientAddress += city.getAreaSname();
                                        }
                                    }
                                    if(StringUtils.isNotBlank(patientUser.getPatientArea())){
                                        CdAddress area = (CdAddress) this.getSysDao().getServiceDo().find(CdAddress.class, patientUser.getPatientArea());
                                        if(area != null) {
                                            patientAddress += area.getAreaSname();
                                        }
                                    }
                                    if(StringUtils.isNotBlank(patientUser.getPatientStreet())){
                                        CdAddress street = (CdAddress) this.getSysDao().getServiceDo().find(CdAddress.class, patientUser.getPatientStreet());
                                        if(street != null) {
                                            patientAddress += street.getAreaSname();
                                        }
                                    }
                                    if(StringUtils.isNotBlank(patientUser.getPatientNeighborhoodCommittee())){
                                        CdAddress committee = (CdAddress) this.getSysDao().getServiceDo().find(CdAddress.class, patientUser.getPatientNeighborhoodCommittee());
                                        if(committee != null) {
                                            patientAddress += committee.getAreaSname();
                                        }
                                    }
                                    if(StringUtils.isNotBlank(patientUser.getPatientAddress())){
                                        patientAddress += patientUser.getPatientAddress();
                                    }
                                }
//                                if (StringUtils.isNotBlank(patientUser.getPatientAddress())) {
//                                    patientAddress = patientUser.getPatientAddress();
//                                }
                                String patientTel = "";
                                if (StringUtils.isNotBlank(patientUser.getPatientTel())) {
                                    patientTel = patientUser.getPatientTel();
                                }
                                String areaSname = "";
                                if (StringUtils.isNotBlank(address.getAreaSname())) {
                                    areaSname = address.getAreaSname();
                                }
                                String hospName = "";
                                if (StringUtils.isNotBlank(dept.getHospName())) {
                                    hospName = dept.getHospName();
                                }
                                String teamName = "";
                                if (StringUtils.isNotBlank(team.getTeamName())) {
                                    teamName = team.getTeamName();
                                }
                                String drTel = "";
                                if(user != null){
                                    if (StringUtils.isNotBlank(user.getDrTel())) {
                                        drTel = user.getDrTel();
                                    }
                                    drName = user.getDrName();
                                }

                                String hospTel = "";
                                if (StringUtils.isNotBlank(dept.getHospTel())) {
                                    hospTel = dept.getHospTel();
                                }
                                String patientjmda = "";
                                if (StringUtils.isNotBlank(patientUser.getPatientjmda())) {
                                    patientjmda = patientUser.getPatientjmda();
                                }
                                String drAssistantName = "";
                                String drAssistantTel = "";
                                if (user != null) {
                                    if (StringUtils.isNotBlank(user.getDrName())) {
                                        drAssistantName = user.getDrName();
                                    }
                                    if (StringUtils.isNotBlank(user.getDrTel())) {
                                        drAssistantTel = user.getDrTel();
                                    }
                                }
                                content = content.replace("{01}", patientjmda);
                                content = content.replace("{7}", drAssistantName);
                                content = content.replace("{8}", drAssistantTel);
                                content = content.replace("{1}", patientName);
                                content = content.replace("{2}", patientIdno);
                                content = content.replace("{3}", patientAddress);
                                content = content.replace("{4}", patientTel);
                                content = content.replace("{5}", areaSname);
                                content = content.replace("{6}", hospName);
//                                content = content.replace("{8}", drTel);
                                content = content.replace("{9}", hospTel);
                                if (form != null) {
                                    AppDrUser drUser = null;
                                    if (StringUtils.isNotBlank(qvo.getDrId())) {
                                        drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class, qvo.getDrId());
                                    }
                                    if (drUser != null) {
                                        drTel = drUser.getDrTel();
                                        drName = drUser.getDrName();
                                        if (StringUtils.isNotBlank(drName)) {
                                            content = content.replace("{02}", drName);
                                        }
                                        if (StringUtils.isNotBlank(drTel)) {
                                            content = content.replace("{03}", drTel);
                                        }
                                    } else {
                                        content = content.replace("{02}", "");
                                        content = content.replace("{03}", "");
                                    }
                                    if (form.getSignFromDate() != null) {
                                        content = content.replace("{10}", ExtendDate.getYMD(form.getSignFromDate()));
                                        content = content.replace("{11}", ExtendDate.getYMD(form.getSignToDate()));
                                    } else {
                                        content = content.replace("{10}", map.get("start"));
                                        content = content.replace("{11}", map.get("end"));
                                    }
                                    if (StringUtils.isNotBlank(form.getSignNum())) {
                                        content = content.replace("{30}", form.getSignNum());
                                    } else {
                                        content = content.replace("{30}", "");
                                    }
                                    content = content.replace("{23}", teamName);
                                    List<AppLabelGroup> ls = this.getSysDao().getAppLabelGroupDao().findBySignGroup(form.getId(), "3");
                                    String ypxy = "";
//                                    boolean flag = false;
//                                    boolean flags = false;
                                    List<AppLabelEconomics> lsE = this.getSysDao().getAppLabelGroupDao().findBySignEconomics(form.getId(), "4");
                                    if (lsE != null && lsE.size() > 0) {
                                        for (AppLabelEconomics p : lsE) {
                                            if(EconomicType.JDLKPKRK.getValue().equals(p.getLabelValue())){//建档立卡人口
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,"10");
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                           /* if(EconomicType.JSTSJT.getValue().equals(p.getLabelValue())){//计划生育特殊家庭人员
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY,"11");
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if(EconomicType.DBH.getValue().equals(p.getLabelValue())){//低保五保人员
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY,"12");
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }*/
                                        }
                                    }
                                    if (ls != null && ls.size() > 0) {
                                        for (AppLabelGroup p : ls) {
                                            if (ResidentMangeType.PTRQ.getValue().equals(p.getLabelValue())) {//普通人群
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.ETLZLS.getValue().equals(p.getLabelValue())) {//儿童(0~6岁)
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{14}", "☑");//为0-6岁儿童进行一类疫苗接种
                                                content = content.replace("{15}", "☑");//为0-6岁儿童进行常规的体格检查
                                                content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }

                                            }
                                            if (ResidentMangeType.YCF.getValue().equals(p.getLabelValue())) {//孕产妇
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{16}", "☑");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.LNR.getValue().equals(p.getLabelValue())) {//老年人
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{17}", "☑");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                                content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.GXY.getValue().equals(p.getLabelValue())) {//高血压
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.TNB.getValue().equals(p.getLabelValue())) {//糖尿病
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }

                                            if (ResidentMangeType.YZJSZY.getValue().equals(p.getLabelValue())) {//严重精神障碍
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{19}", "☑");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.JHB.getValue().equals(p.getLabelValue())) {//结核病
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{21}", "☑");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.CJR.getValue().equals(p.getLabelValue())) {//残疾人
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup, p.getLabelValue());
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (ResidentMangeType.WEIZHI.getValue().equals(p.getLabelValue())) {//未知
                                                content = content.replace("{12}", "");//建立健康档案
                                                content = content.replace("{13}", "");//健康教育
                                                content = content.replace("{14}", "");//为0-6岁儿童进行一类疫苗接种
                                                content = content.replace("{15}", "");//为0-6岁儿童进行常规的体格检查
                                                content = content.replace("{16}", "");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                                content = content.replace("{17}", "");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                                content = content.replace("{18}", "");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                content = content.replace("{19}", "");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                                content = content.replace("{20}", "");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                content = content.replace("{21}", "");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                                content = content.replace("{22}", "");//其它国家规定免费提供的基本公共卫生服务项目
                                            }
                                        }
                                       /* if (flag) {
                                            CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY, "5");
                                            if (code != null) {
                                                ypxy += code.getCodeTitle();
                                            }
                                        }*/
                                        content = content.replace("{12}", "");//建立健康档案
                                        content = content.replace("{13}", "");//健康教育
                                        content = content.replace("{14}", "");//为0-6岁儿童进行一类疫苗接种
                                        content = content.replace("{15}", "");//为0-6岁儿童进行常规的体格检查
                                        content = content.replace("{16}", "");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                        content = content.replace("{17}", "");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                        content = content.replace("{18}", "");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                        content = content.replace("{19}", "");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                        content = content.replace("{20}", "");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                        content = content.replace("{21}", "");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                        content = content.replace("{22}", "");//其它国家规定免费提供的基本公共卫生服务项目

                                        content = content.replace("{04}", "<div id='headerId'>");//页头
                                        content = content.replace("{06}", "<div id='tailId'>");//页未
                                        content = content.replace("{05}", "</div>");//页未
                                        if (StringUtils.isNotBlank(form.getSigntext())) { //不知道为什么这样写总觉得不太合理
                                            String text = "";
                                            String[] a = form.getSigntext().split("\n");
                                            for (int i = 0; i < a.length; i++) {
                                                if (i == 0) {
                                                    text = "<p style=\"text-indent:32px;line-height:27px\">" + a[i] + "</p>";
                                                } else {
                                                    text = text + "<p style=\"text-indent:32px;line-height:27px\">" + a[i] + "</p>";
                                                }
                                            }
                                            content = content.replace("{0}", text);//特色补充协议包
                                        } else {
                                            content = content.replace("{0}", "");//特色补充协议包
                                        }
                                    }
                                   /* if (flags) {
                                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY, "9");
                                        if (code != null) {
                                            ypxy += code.getCodeTitle();
                                        }
                                    }*/
                                    content = content.replace("{97}", addrxx);
                                    content = content.replace("{98}", ypxy);
                                    /*if (StringUtils.isNotBlank(ypxy)) {
                                        AppHospDept hospDept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, form.getSignHospId());
                                        if (hospDept != null) {
                                            if (StringUtils.isNotBlank(hospDept.getHospAreaCode())) {
                                                String code = AreaUtils.getAreaCode(hospDept.getHospAreaCode(), "3");
                                                if ("3507".equals(code)) {
                                                    content = content.replace("{98}", ypxy);
                                                }
                                            }
                                        }
                                    }*/
                                    if(StringUtils.isNotBlank(form.getUpHpis())){
                                        if(SignFormType.POSSTATE.getValue().equals(form.getUpHpis()) || SignFormType.YITIJISTATE.getValue().equals(form.getUpHpis())){
                                            List<AppSignSubtable> list = sysDao.getAppSignSubtableDao().findBySign(form.getId(),form.getUpHpis(),CommSF.YES.getValue());
                                            if(list!= null && list.size()>0){
                                                if(form.getSignHospId().equals("zz_6883")){
                                                    String result = "<image style='display:block; position: absolute;bottom: 20px;left:450px;z-index: -1;' width='100' height='100' src='"+list.get(0).getImgBase64()+"'></image>";
                                                    content = content.replace("{999}",result);//甲方签名
                                                }else{
                                                    String result = "<image  width='100' height='100' src='"+list.get(0).getImgBase64()+"'></image>";
                                                    content = content.replace("{999}",result);//甲方签名
                                                }
                                            }else{
                                                content = content.replace("{999}","");//甲方签名
                                            }
//                                            String result = "<image width='100' height='100' src='"+form.getSignatureImageUrl()+"'></image>";
//                                            content = content.replace("{999}",result);//甲方签名
                                        }else{
                                            content = content.replace("{999}","");//甲方签名
                                        }
                                    }else{
                                        content = content.replace("{999}","");//甲方签名
                                    }
                                    content = content.replace("{888}","");//乙方签名
                                } else {
                                    content = content.replace("{888}","");//乙方签名
                                    content = content.replace("{999}","");//甲方签名
                                    content = content.replace("{04}", "<div id='headerId'>");//页头
                                    content = content.replace("{06}", "<div id='tailId'>");//页未
                                    content = content.replace("{05}", "</div>");//页未
                                    if (StringUtils.isNotBlank(qvo.getDrId())) {
                                        AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class, qvo.getDrId());
                                        if (drUser != null) {
                                            if (StringUtils.isNotBlank(drUser.getDrTel())) {
                                                drTel = drUser.getDrTel();
                                            } else {
                                                drTel = "";
                                            }
                                            content = content.replace("{02}", drUser.getDrName());
                                            content = content.replace("{03}", drTel);
                                        } else {
                                            content = content.replace("{02}", "");
                                            content = content.replace("{02}", "");
                                        }
                                    } else {
                                        content = content.replace("{02}", "");
                                        content = content.replace("{02}", "");
                                    }
//                                    content = content.replace("{7}", teamName);
                                    content = content.replace("{23}", teamName);
                                    content = content.replace("{0}", "");
                                    content = content.replace("{30}", "");
                                    content = content.replace("{10}", map.get("start"));
                                    content = content.replace("{11}", "");
                                    content = content.replace("{12}", "");//建立健康档案
                                    content = content.replace("{13}", "");//健康教育
                                    content = content.replace("{14}", "");//为0-6岁儿童进行一类疫苗接种
                                    content = content.replace("{15}", "");//为0-6岁儿童进行常规的体格检查
                                    content = content.replace("{16}", "");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                    content = content.replace("{17}", "");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                    content = content.replace("{18}", "");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                    content = content.replace("{19}", "");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                    content = content.replace("{20}", "");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                    content = content.replace("{21}", "");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                    content = content.replace("{22}", "");//其它国家规定免费提供的基本公共卫生服务项目
                                    content = content.replace("{98}", "");
                                    content = content.replace("{97}", "");
                                }
                                content = content.replace("{40}",ExtendDate.getChineseYMD(Calendar.getInstance()));
                                this.getAjson().setMsg(content);
                            } else {
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("查无协议书,请联系管理员");
                            }
                        } else {
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("参数格式错误");
                        }
                    }
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("类型不能为空");
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }


    /**
     * 提交申请签约接口 个人签约
     */
    public String signFormUserAio() {
        AppCommQvo qvo = (AppCommQvo) getAppJson(AppCommQvo.class);
        try {
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                AppSignForm signForm = sysDao.getAppSignformDao().findSignOne(getAppPatientUser().getId());
                if (signForm != null) {
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("已有待签约记录");
                    return "ajson";
                }
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, qvo.getDrId());
                if (drUser != null) {
                    AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
                    if (dept != null) {
                        CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                        if (cdAdd != null) {
                            AppCommQvo app = new AppCommQvo();
                            app.setSignType("0");//普通人群查询
                            if ("1".equals(cdAdd.getAreaSignWay())) {//团队上限签约人数
                                app.setTeamId(qvo.getTeamId());
                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())) {
                                    this.getAjson().setMsg("该团队签约人数已达上限，请重新选择签约团队!");
                                    this.getAjson().setMsgCode("900");
                                    return "ajson";
                                }
                            } else if ("0".equals(cdAdd.getAreaSignWay())) {
                                app.setTeamId(qvo.getTeamId());
                                int count = sysDao.getAppTeamMemberDao().findTeamPeopleCount(qvo.getTeamId());//查询整个团队人员数
                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);//查询整个团队签约数
                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop()) * count) {
                                    this.getAjson().setMsg("该团队签约人数已达上限，请重新选择签约团队!");
                                    this.getAjson().setMsgCode("900");
                                    return "ajson";
                                }
                            }
                        }

                    }
                }
                if (StringUtils.isNotBlank(qvo.getSignUpHpis())) {
                    AppSignForm form = sysDao.getAppSignformDao().signFormUser(getAppPatientUser().getId(), qvo.getTeamId(), qvo.getDrId(),
                            qvo.getSignpackageid(), qvo.getSignUpHpis(), qvo.getSignatureImageUrl(),qvo.getSignWay(),qvo.getSignImageUrl(),qvo.getSignMobileType());
                    this.getAjson().setVo(form);
                } else {
                    this.getAjson().setMsg("签约来源不能为空!");
                    this.getAjson().setMsgCode("900");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 签约单查询列表 根据用户id或团队id查询签约单列表
     */
    public String findSignFormByUserOrTeamAio() {
        AppCommQvo qvo = (AppCommQvo) getAppJson(AppCommQvo.class);
        try {
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                String result = "1";
                AppDrUser drUser = this.getAppDrUser();
                if (drUser != null) {
                    qvo.setDrId(drUser.getId());
                    //判断市是否开启家庭签约
                    if (StringUtils.isNotBlank(drUser.getDrHospId())) {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
                        if (dept != null) {
                            if (StringUtils.isNotBlank(dept.getHospAreaCode())) {
                                String city = AreaUtils.getAreaCode(dept.getHospAreaCode(), "2");
                                AppSignSetting setting = sysDao.getAppSignSettingDao().findByAreaCode(city);
                                if (setting != null) {
                                    result = setting.getSignsSignType();
                                }
                            }
                        }
                    }
                }
                List<AppSignFormListEntity> ls = new ArrayList<>();
                if ("1".equals(result)) {
                    ls = sysDao.getAppSignformDao().findSignFormByUserOrTeam(qvo);
                } else {
                    ls = sysDao.getAppSignformDao().findSignFormByUserOrTeamT(qvo);
                }
                if (ls != null && StringUtils.isNotBlank(qvo.getPatientId())) {
                    Collections.sort(ls, ComparatorUtils.getComparatorSign());
                }
                this.getAjson().setQvo(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setMsgCode("800");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 签约平台数据
     *
     * @return
     */
    public String manageSignData() {
        try {
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                if (qvo.getAreaId().length() <= 4) {
                    if (qvo.getAreaId().length() == 2) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "1"));
                    } else if (qvo.getAreaId().length() == 4) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "2"));
                    }
                    Map<String, Object> returnMap = new HashMap<>();
                    //签约数
                    Map<String, Object> signTotal = sysDao.getAppSignAnalysisDao().findSignAnalysisIndex(qvo);
                    returnMap.put("signTotal", signTotal);
                    //性别
                    Map<String, Object> mapGender = this.getSysDao().getAppResidentAnalysisDao().findCountGenderEnglish(qvo);
                    returnMap.put("mapGender", mapGender);
                    //年龄分布
                    Map<String, Object> mapAge = this.getSysDao().getAppResidentAnalysisDao().findCountAge(qvo);
                    returnMap.put("mapAge", mapAge);
                    //服务分布
                    Map<String, Object> mapPers = this.getSysDao().getAppResidentAnalysisDao().findPersGroupCountEnglish(qvo);
                    returnMap.put("mapPers", mapPers);
                    //经济类型统计
                    Map<String, Object> mapEconomic = this.getSysDao().getAppSignAnalysisDao().findSignEconomicTypeCount(qvo);
                    returnMap.put("mapEconomic", mapEconomic);
                    this.getAjson().setMap(returnMap);
                    this.getAjson().setMsgCode("800");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 签约平台数据（签约数）
     *
     * @return
     */
    public String manageSignTotalData() {
        try {
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                if (qvo.getAreaId().length() <= 4) {
                    if (qvo.getAreaId().length() == 2) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "1"));
                    } else if (qvo.getAreaId().length() == 4) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "2"));
                    }
                    Map<String, Object> returnMap = new HashMap<>();
                    //签约数
                    Map<String, Object> signTotal = sysDao.getAppSignAnalysisDao().findSignAnalysisIndex(qvo);
                    returnMap.put("signTotal", signTotal);
                    this.getAjson().setMap(returnMap);
                    this.getAjson().setMsgCode("800");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 签约平台数据（性别）
     *
     * @return
     */
    public String manageSignGenderData() {
        try {
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                if (qvo.getAreaId().length() <= 4) {
                    if (qvo.getAreaId().length() == 2) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "1"));
                    } else if (qvo.getAreaId().length() == 4) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "2"));
                    }
                    Map<String, Object> returnMap = new HashMap<>();
                    //性别
                    Map<String, Object> mapGender = this.getSysDao().getAppResidentAnalysisDao().findCountGenderEnglish(qvo);
                    returnMap.put("mapGender", mapGender);
                    this.getAjson().setMap(returnMap);
                    this.getAjson().setMsgCode("800");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 签约平台数据（年龄分布）
     *
     * @return
     */
    public String manageSignAgeData() {
        try {
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                if (qvo.getAreaId().length() <= 4) {
                    if (qvo.getAreaId().length() == 2) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "1"));
                    } else if (qvo.getAreaId().length() == 4) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "2"));
                    }
                    Map<String, Object> returnMap = new HashMap<>();
                    //年龄分布
                    Map<String, Object> mapAge = this.getSysDao().getAppResidentAnalysisDao().findCountAge(qvo);
                    returnMap.put("mapAge", mapAge);
                    this.getAjson().setMap(returnMap);
                    this.getAjson().setMsgCode("800");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 签约平台数据（服务分布）
     *
     * @return
     */
    public String manageSignPerData() {
        try {
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                if (qvo.getAreaId().length() <= 4) {
                    if (qvo.getAreaId().length() == 2) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "1"));
                    } else if (qvo.getAreaId().length() == 4) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "2"));
                    }
                    Map<String, Object> returnMap = new HashMap<>();
                    //服务分布
                    Map<String, Object> mapPers = this.getSysDao().getAppResidentAnalysisDao().findPersGroupCountEnglish(qvo);
                    returnMap.put("mapPers", mapPers);
                    this.getAjson().setMap(returnMap);
                    this.getAjson().setMsgCode("800");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 签约平台数据（经济类型）
     *
     * @return
     */
    public String manageSignEconomicData() {
        try {
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                if (qvo.getAreaId().length() <= 4) {
                    if (qvo.getAreaId().length() == 2) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "1"));
                    } else if (qvo.getAreaId().length() == 4) {
                        qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "2"));
                    }
                    Map<String, Object> returnMap = new HashMap<>();
                    //经济类型统计
                    Map<String, Object> mapEconomic = this.getSysDao().getAppSignAnalysisDao().findSignEconomicTypeCount(qvo);
                    returnMap.put("mapEconomic", mapEconomic);
                    this.getAjson().setMap(returnMap);
                    this.getAjson().setMsgCode("800");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 各市一分钟内的签约数据
     *
     * @return
     */
    public String citySignManageData() {
        try {
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                if (qvo.getAreaId().length() == 2) {
                    qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "1"));
                } else if (qvo.getAreaId().length() == 4) {
                    qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "2"));
                }
                Map<String, Object> map = this.getSysDao().getAppResidentAnalysisDao().findCountByMin(qvo);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 修改缴费状态
     *
     * @return
     */
    public String changePayStateAio() {
        try {
            AppCommQvo qvo = (AppCommQvo) getAppJson(AppCommQvo.class);
            if (qvo == null) {
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            } else {
                if (StringUtils.isNotBlank(qvo.getSignFormId())) {
                    AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, qvo.getSignFormId());
                    if (form != null) {
                        form.setSignPayState(qvo.getPayState());
                        sysDao.getServiceDo().modify(form);
                    } else {
                        this.getAjson().setMsg("查无签约单数据");
                        this.getAjson().setMsgCode("900");
                    }
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("签约单主键不能为空");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 高血压、糖尿病、综合排行数据
     * @return
     */
    public String manageChronicDisease(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if (qvo.getAreaId().length() == 2) {
                    qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "1"));
                } else if (qvo.getAreaId().length() == 4) {
                    qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "2"));
                }
                Map<String,Object> map = sysDao.getAppResidentAnalysisDao().findChronicDisease(qvo);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 履约团队排名
     * @return
     */
    public String manageTeamCount(){
        try{
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if (qvo.getAreaId().length() == 2) {
                    qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "1"));
                } else if (qvo.getAreaId().length() == 4) {
                    qvo.setAreaId(AreaUtils.getAreaCodeAll(qvo.getAreaId(), "2"));
                }
                List<ManageTeamCountEntity> list = sysDao.getAppResidentAnalysisDao().findTeamCount(qvo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
}
