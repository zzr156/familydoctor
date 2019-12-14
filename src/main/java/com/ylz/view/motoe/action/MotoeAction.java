package com.ylz.view.motoe.action;

import com.ylz.bizDo.Motoe.dao.vo.SignsurrenderVo;
import com.ylz.bizDo.Motoe.dao.vo.WebSignUpVo;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdOrgconfig;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthVo.AppBasicTcmAllQvo;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.commonEntity.*;
import com.ylz.bizDo.jtapp.commonVo.AppAgreeMentQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.AppYbSignQvo;
import com.ylz.bizDo.jtapp.drEntity.AppServeEntity;
import com.ylz.bizDo.jtapp.drEntity.PatientInfo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMotoeEntity;
import com.ylz.bizDo.jtapp.patientVo.AppTeamVo;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.bizDo.web.po.WebDrUser;
import com.ylz.bizDo.web.po.WebHospDept;
import com.ylz.bizDo.web.vo.WebUpVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("all")
@Action(
        value="motoeAction",
        results={
                @Result(name = "json", type = "json",params={"root","jsons","contentType", "application/json"}),
                @Result(name = "jList", type = "json",params={"root","jList","contentType", "application/json"}),
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","excludeNullProperties","true","contentType", "application/json"})
        }
)
public class MotoeAction extends CommonAction {
    private static final String signkey = "ylzdocortest@)!*)!";

    private static final Map<String,String> agreeIdMap=new HashMap<>();
    static{
        agreeIdMap.put("3507","6435df82-ed19-11e7-90ce-c85b76ccd5e1");//南平
        agreeIdMap.put("3503","7bad07be-b5cc-48dc-9dd3-edb65b74afb8");//莆田
        agreeIdMap.put("3501","3d3cbe49-6a50-4543-83b2-084de760c137");//福州
        agreeIdMap.put("3506","034773b9-9dea-43db-bfaa-8cebed6c4783");//漳州
        agreeIdMap.put("3504","60d31529-6902-4046-8ee1-7d851360301e");//三明
        agreeIdMap.put("3505","60d31529-6902-4046-8ee1-7d851360301e");//泉州
        agreeIdMap.put("350702","6435b6d2-ed19-11e7-90ce-c85b76ccd5e1");//南平延平区
    }

    /**
     * 居民健康档案签约状态（入参居民身份证号、居民健康档号）
     * @return
     */
    public String getAppSignState(){
        try {
            List<Map<String,Object>> totalList = new ArrayList<Map<String,Object>>();
            AppCommQvo appCommQvo = (AppCommQvo)this.getAppJson(AppCommQvo.class);
            if(appCommQvo != null){
                for(int i = 0;i < appCommQvo.getPatientList().size(); i++){
                    String patientIdNo = appCommQvo.getPatientList().get(i).getPatientIdNo();
                    String patientJmda = appCommQvo.getPatientList().get(i).getPatientJmda();
                    if(patientIdNo != "" && patientJmda != ""){
                        List<AppSignForm> list = sysDao.getAppPatientUserDao().getAppSignState(patientIdNo,patientJmda);
                        if(list.size() > 0){
                            Map<String,Object> map = new HashMap<String,Object>();
                            map.put("patientIdNo",patientIdNo);
                            map.put("patientJmda",patientJmda);
                            map.put("teamId",list.get(0).getSignTeamId());
                            map.put("patientId",list.get(0).getSignPatientId());
                            totalList.add(map);
                    }
                }
            }
                this.getAjson().setRows(totalList);
                this.getAjson().setMsgCode("800");
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return  "ajson";
    }

    /**
     * 修改居民信息
     * @return
     */
    public String updatePatientInfo() {
        try {
            AppCommQvo qvo = (AppCommQvo) this.getAppJson(AppCommQvo.class);
            if (qvo != null) {
                if(StringUtils.isNotBlank(qvo.getPatientIdno()) && StringUtils.isNotBlank(qvo.getPatientName())){
                    AppPatientUserEntity patientUser = sysDao.getAppPatientUserDao().findByUserIdNo(qvo.getPatientIdno());
                    if(patientUser != null){
                        AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,patientUser.getId());
                        if(user != null){
                            if(StringUtils.isNotBlank(qvo.getPatientName())){
                                user.setPatientName(qvo.getPatientName());
                            }
                            if(StringUtils.isNotBlank(qvo.getPatientjmda())){
                                if(StringUtils.isBlank(user.getPatientjmda())) {
                                    user.setPatientjmda(qvo.getPatientjmda());
                                }else{
                                    if(!user.getPatientjmda().equals(qvo.getPatientjmda())){
                                        user.setPatientjmda(qvo.getPatientjmda());
                                    }
                                }
                            }
                            if(StringUtils.isNotBlank(qvo.getPatientjtda())){
                                if(StringUtils.isBlank(user.getPatientjtda())) {
                                    user.setPatientjtda(qvo.getPatientjtda());
                                }else{
                                    if(!user.getPatientjmda().equals(qvo.getPatientjtda())){
                                        user.setPatientjtda(qvo.getPatientjtda());
                                    }
                                }
                            }
                            sysDao.getServiceDo().modify(user);
                            this.getAjson().setMsgCode("800");
                            this.getAjson().setMsg("修改成功!");
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("系统出错,请联系管理员!");
                        }
                    }else{
                        this.getAjson().setMsgCode("1200");
                        this.getAjson().setMsg("该居民查无信息!");
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数身份证不能为空!");
                }
            }else{
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
     * 基卫监控统计
     * @return
     */
    public String appManageIndexCountNew(){
        try{
            Map<String,Object> returnMap = new HashMap<String,Object>();
            ResidentVo qvo = (ResidentVo) getAppJson(ResidentVo.class);
            if(qvo!=null){
                String code="";
                System.out.print(qvo.getArerCode());
                if(StringUtils.isNotBlank(qvo.getHospId())){
                    if(!qvo.getHospId().contains("_")){
                        if(qvo.getArerCode().equals("3503")){
                            code =  qvo.getHospId();
                            qvo.setHospId("pt_"+code);
                        }else if(qvo.getArerCode().equals("3504")){
                            code =  qvo.getHospId();
                            qvo.setHospId("sm_"+code);
                        }else if(qvo.getArerCode().equals("3505")){
                            code =  qvo.getHospId();
                            qvo.setHospId("qz_"+code);
                        }else if(qvo.getArerCode().equals("3506")){
                            code =  qvo.getHospId();
                            qvo.setHospId("zz_"+code);
                        }else if(qvo.getArerCode().equals("3507")){
                            code =  qvo.getHospId();
                            qvo.setHospId("np_"+code);
                        }
                    }

                }
                //签约首页统计
                System.out.println("开始时间："+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
                Map<String,Object> mapIndex = sysDao.getAppSignMotoeDao().findSignAnalysisIndexMotoe(qvo);
                returnMap.put("signTotal",mapIndex);
                //签约首页统计
            if(StringUtils.isBlank(qvo.getHospId())){
                Map<String,Object> map = sysDao.getAppSignMotoeDao().findSignAnalysisListMotoe(qvo);
                returnMap.put("signTotalState",map);
            }
                System.out.println("结束时间："+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));

                this.getAjson().setMap(returnMap);
                this.getAjson().setMsgCode("800");
                this.getAjson().setQvo(qvo);
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
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
    public String appAgreeMent() {
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

                        AppTeam team = (AppTeam) this.getSysDao().getServiceDo().find(AppTeam.class, qvo.getTeamId());
                        AppPatientUserEntity entity = this.getSysDao().getAppPatientUserDao().findByUserIdNo(qvo.getPatientId());
                        if(entity != null){
                            AppPatientUser patientUser = (AppPatientUser) this.getSysDao().getServiceDo().find(AppPatientUser.class, entity.getId());
                            if (team != null && patientUser != null) {
                                AppAgreement v = this.getSysDao().getAppAgreementDao().findByHospId(team.getTeamHospId(), qvo.getQyType());
                                AppHospDept dept = (AppHospDept) this.getSysDao().getServiceDo().find(AppHospDept.class, team.getTeamHospId());
                                if (v != null && dept != null) {
                                    if("350702".equals(AreaUtils.getAreaCode(dept.getHospAreaCode(),"3"))){
                                        xyGroup = CodeGroupConstrats.GROUP_YPXY;
                                    }else{
                                        xyGroup = CodeGroupConstrats.GROUP_NPXY;
                                    }
                                    AppSignForm form = this.getSysDao().getAppSignformDao().getSignFormUserId(patientUser.getId());
                                    AppDrUser druser = null;
                                    if (form != null && form.getSignDrAssistantId() != null) {
                                        druser = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, form.getSignDrAssistantId());
                                    }
                                    CdAddress address = (CdAddress) this.getSysDao().getServiceDo().find(CdAddress.class, dept.getHospUpcityareaId());
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
                                    //团队医生
                                    AppDrUser user = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, team.getTeamDrId());
                                    AppDrUser doctor = null;
                                    if (form != null && form.getSignDrId() != null) {
                                        //签约医生
                                        doctor = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, form.getSignDrId());
                                    }
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
                                    // 家庭住址
                                    String patientAddress = "";
                                    if (StringUtils.isNotBlank(patientUser.getPatientNeighborhoodCommittee())) {
                                        CdAddress streetAddress = this.sysDao.getCdAddressDao().findByCacheCode(patientUser.getPatientNeighborhoodCommittee());
                                        if (streetAddress != null) {
                                            patientAddress += streetAddress.getAreaName();
                                        }
                                    } else if (StringUtils.isNotBlank(patientUser.getPatientStreet())) { // 街道
                                        CdAddress streetAddress = this.sysDao.getCdAddressDao().findByCacheCode(patientUser.getPatientStreet());
                                        if (streetAddress != null) {
                                            patientAddress += streetAddress.getAreaName();
                                        }
                                    } else if (StringUtils.isNotBlank(patientUser.getPatientArea())) {// 区
                                        CdAddress areaAddress = this.sysDao.getCdAddressDao().findByCacheCode(patientUser.getPatientArea());
                                        if (areaAddress != null) {
                                            patientAddress += areaAddress.getAreaName();
                                        }
                                    } else if (StringUtils.isNotBlank(patientUser.getPatientCity())) {// 市
                                        CdAddress cityAddress = this.sysDao.getCdAddressDao().findByCacheCode(patientUser.getPatientCity());
                                        if (cityAddress != null) {
                                            patientAddress += cityAddress.getAreaName();
                                        }
                                    } else if (StringUtils.isNotBlank(patientUser.getPatientProvince())) {// 省
                                        CdAddress provinceAddress = this.sysDao.getCdAddressDao().findByCacheCode(patientUser.getPatientProvince());
                                        if (provinceAddress != null) {
                                            patientAddress += provinceAddress.getAreaName();
                                        }
                                    }
                                    if (StringUtils.isNotBlank(patientUser.getPatientAddress())) {
                                        //如果详细地址里是手写的就直接取该详细地址，否则就CdAddress,南平直接取详细地址
                                        if (StringUtils.isNotBlank(patientUser.getPatientCity()) && "3507".equals(patientUser.getPatientCity().substring(0, 4))) {
                                            patientAddress = patientUser.getPatientAddress();
                                        } else if (StringUtils.isNotBlank(patientUser.getPatientAddress()) && patientUser.getPatientAddress().indexOf("福建省") != -1) {
                                            patientAddress = patientUser.getPatientAddress();
                                        } else {
                                            patientAddress += patientUser.getPatientAddress();
                                        }
                                    }
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
                                    //签约医生电话
                                    String drTel = "";
                                    if (doctor != null) {
                                        if (StringUtils.isNotBlank(doctor.getDrTel())) {
                                            drTel = doctor.getDrTel();
                                        }
                                        drName = doctor.getDrName();
                                    }
                                    String hospTel = "";
                                    if (StringUtils.isNotBlank(dept.getHospTel())) {
                                        hospTel = dept.getHospTel();
                                    }
                                    String patientCard = "";
                                    if (StringUtils.isNotBlank(patientUser.getPatientCard())) {
                                        patientCard = patientUser.getPatientCard();
                                    }
                                    String patientjmda = "";
                                    if (StringUtils.isNotBlank(patientUser.getPatientjmda())) {
                                        patientjmda = patientUser.getPatientjmda();
                                    }
                                    String drAssistantName = "";
                                    String drAssistantTel = "";
                                    if (druser != null) {
                                        if (StringUtils.isNotBlank(druser.getDrName())) {
                                            drAssistantName = druser.getDrName();
                                        }
                                        if (StringUtils.isNotBlank(druser.getDrTel())) {
                                            drAssistantTel = druser.getDrTel();
                                        }
                                    }

                                    content = content.replace("{01}", patientjmda);
                                    content = content.replace("{02}", drAssistantName);
                                    content = content.replace("{03}", drAssistantTel);
                                    content = content.replace("{1}", patientName);
                                    content = content.replace("{2}", patientIdno);
                                    content = content.replace("{3}", patientAddress);
                                    content = content.replace("{4}", patientTel);
                                    content = content.replace("{5}", areaSname);
                                    content = content.replace("{6}", hospName);
                                    content = content.replace("{8}", drTel);
                                    content = content.replace("{9}", hospTel);
                                    content = content.replace("{31}", patientCard);
                                    if (form != null) {
                                        content = content.replace("{97}",addrxx);
                                        content = content.replace("{40}", ExtendDate.getChineseYMD(form.getSignDate()));// 签约日期
                                        String ypxy = "";
                                        String npxy = "";
//                                        boolean flag = false;
//                                        boolean flags = false;
                                        AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class, form.getSignDrId());
                                        if (drUser != null) {
                                            content = content.replace("{7}", drUser.getDrName());
                                            drName = druser.getDrName();
                                        } else {
                                            content = content.replace("{7}", "");
                                            drName = "";
                                        }
                                        if (StringUtils.isNotBlank(form.getSignNum())) {
                                            content = content.replace("{30}", form.getSignNum());
                                        } else {
                                            content = content.replace("{30}", "");
                                        }
                                        content = content.replace("{23}", teamName);
                                        if (form.getSignFromDate() != null) {
                                            content = content.replace("{10}", ExtendDate.getYMD(form.getSignFromDate()));
                                            content = content.replace("{11}", ExtendDate.getYMD(form.getSignToDate()));
                                        } else {
                                            content = content.replace("{10}", map.get("start"));
                                            content = content.replace("{11}", map.get("end"));
                                        }
                                        List<AppLabelEconomics> lsE = this.getSysDao().getAppLabelGroupDao().findBySignEconomics(form.getId(), "4");
                                        if (lsE != null && lsE.size() > 0) {
                                            for (AppLabelEconomics p : lsE) {
//                                                if (!EconomicType.YBRK.getValue().equals(p.getLabelValue())) {
//                                                    flags = true;
//                                                }
                                                if(EconomicType.JDLKPKRK.getValue().equals(p.getLabelValue())){//建档立卡人口
                                                    CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,"10");
                                                    if(code!=null){
                                                        ypxy += code.getCodeTitle();
                                                    }
                                                }
                                                /*if(EconomicType.JSTSJT.getValue().equals(p.getLabelValue())){//计划生育特殊家庭人员
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
                                        List<AppLabelGroup> ls = this.getSysDao().getAppLabelGroupDao().findBySignGroup(form.getId(), "3");
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
                                            /*if (flag) {
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY, "5");
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if (flags) {
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY, "9");
                                                if (code != null) {
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }*/

                                            content = content.replace("{98}", ypxy);
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
                                            //  福州协议 特殊化多功能套打
                                            content = content.replace("{04}", "<div id='headerId'>");//页头
                                            content = content.replace("{06}", "<div id='tailId'>");//页未
                                            content = content.replace("{05}", "</div>");//页未

                                            if(StringUtils.isNotBlank(form.getUpHpis())){
                                                if(SignFormType.POSSTATE.getValue().equals(form.getUpHpis()) || SignFormType.YITIJISTATE.getValue().equals(form.getUpHpis())){
                                                    List<AppSignSubtable> list = sysDao.getAppSignSubtableDao().findBySign(form.getId(),form.getUpHpis(), CommSF.YES.getValue());
                                                    if(list!= null && list.size()>0){
                                                        if(form.getSignHospId().equals("zz_6883")){
                                                            String result = "<image style='display:block; position: absolute;bottom: 20px;left:450px;z-index: -1;' width='100' height='100' src='"+list.get(0).getImgBase64()+"'></image>";
                                                            content = content.replace("{999}",result);//甲方签名
                                                        }else{
                                                            String result = "<image  width='100' height='100' src='"+list.get(0).getImgBase64()+"'></image>";
                                                            content = content.replace("{999}",result);//甲方签名
                                                        }
                                                    }else{
                                                        content = content.replace("{999}",patientName);//甲方签名
                                                    }
                                                }else{
                                                    content = content.replace("{999}",patientName);//甲方签名
                                                }
                                            }else{
                                                content = content.replace("{999}",patientName);//甲方签名
                                            }
                                            //在建档立卡处查看的协议甲方和家庭医生签名默认直接显示姓名
                                            content = content.replace("{999}",patientName);
                                            content = content.replace("{888}",drName);

                                            if (form != null && StringUtils.isNoneBlank(form.getSigntext())) { //不知道为什么这样写总觉得不太合理
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
                                    } else {
                                        content = content.replace("{999}",patientName);//甲方签名
                                        content = content.replace("{888}",drName);//乙方签名
//                                    content = content.replace("{7}", teamName);
                                        content = content.replace("{23}", teamName);
                                        content = content.replace("{10}", map.get("start"));
                                        content = content.replace("{11}", map.get("end"));
                                        content = content.replace("{98}", "");
                                        content = content.replace("{97}","");
                                    }
                                    content = content.replace("{40}",ExtendDate.getChineseYMD(Calendar.getInstance()));
                                    this.getAjson().setMsg(content);
                                    this.getAjson().setUkey(form != null ? form.getId() : "");
                                } else {
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg("查无协议书,请联系管理员");
                                }
                            } else {
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("身份证有误,查无该居民!");
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
     * 基卫同步更新家签医生表
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public String jwSyncDrUser() {
       try {
           WebDrUser vo= (WebDrUser) this.getAppJson(WebDrUser.class);
           if(StringUtils.isBlank(vo.getId())){
               this.getJsons().setCode("900");
               this.getJsons().setMsg("医生主键为空!");
           }else{
               sysDao.getAppDrUserDao().jwSyncDrUser(vo);
           }
       }catch (Exception e){
           e.printStackTrace();
           this.getJsons().setCode("900");
           this.getJsons().setMsg(e.getMessage());

       }
        return "ajson";
    }

    /**
     * 基卫同步更新家签机构表
     * @return
     */
    public String jwSyncHospDept(){
        WebHospDept vo= (WebHospDept) this.getAppJson(WebHospDept.class);
        if(StringUtils.isBlank(vo.getId())){
            this.getJsons().setCode("900");
            this.getJsons().setMsg("机构主键为空!");
        }else{
            if("14".equals(AreaUtils.getAreaCode(vo.getHospAreaCode(),"1"))&&vo.getId().indexOf("gp_")==-1){
                vo.setId("gp_"+vo.getId());
            }
            WebHospDept webHospDept= (WebHospDept) sysDao.getServiceDo().find(WebHospDept.class,vo.getId());
            //新增
            if(webHospDept==null){
                vo.setHospState("1");
                vo.setHospLevelType("7");
                vo.setHospLevel("4");
                sysDao.getServiceDo().add(vo);
                //除南平延平区的协议内容获取
                AppAgreement v = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class,agreeIdMap.get(vo.getHospAreaCode().substring(0,4)));
                //如果是南平延平区的
                if("350702".equals(vo.getHospAreaCode().substring(0,6))){
                    v = (AppAgreement)sysDao.getServiceDo().find(AppAgreement.class,agreeIdMap.get("350702"));
                }
                if(v != null){
                    AppAgreement agreement = new AppAgreement();
                    agreement.setMentUseType("2");
                    agreement.setMentCityId(AreaUtils.getAreaCode(vo.getHospAreaCode(),"2"));
                    agreement.setMentContent(v.getMentContent());
                    agreement.setMentState("1");
                    agreement.setMentType("1");
                    agreement.setMentHospId(vo.getId());
                    agreement.setMentTitle(v.getMentTitle());
                    sysDao.getServiceDo().add(agreement);
                }
           //更新;
            }else{
                webHospDept.setId(vo.getId());
                webHospDept.setHospCode(vo.getHospCode());
                webHospDept.setHospName(vo.getHospName());
                webHospDept.setHospAddress(vo.getHospAddress());
                webHospDept.setHospTel(vo.getHospTel());
                webHospDept.setHospAreaCode(vo.getHospAreaCode());
                webHospDept.setHospLevel(vo.getHospLevel() == null ? "4" : vo.getHospLevel());
                sysDao.getServiceDo().modify(webHospDept);
            }
        }
        return "ajson";
    }

    /**
     * 省app接口测试类
     */
    public void signTest()
    {
        try{
            //signkey
            WebSignUpVo vo = new WebSignUpVo ();
            AppSignForm fvo = (AppSignForm) this.getSysDao().getServiceDo().find(AppSignForm.class, "001ddc38-d3aa-4e73-8d63-d0cda1c53df2");
            AppPatientUser uvo = (AppPatientUser) this.getSysDao().getServiceDo().find(AppPatientUser.class, fvo.getSignPatientId());
            AppDrUser dvo = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, fvo.getSignDrId());
            AppDrUser drvo = null ;
            if(StringUtils.isNotBlank(fvo.getSignDrAssistantId())){
               drvo = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, fvo.getSignDrAssistantId());
            }
            AppSignBatch batch = (AppSignBatch) this.getSysDao().getServiceDo().find(AppSignBatch.class, fvo.getSignBatchId());
            AppDrUser drbvo = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, batch.getBatchOperatorId());
            AppHospDept hvo = (AppHospDept) this.getSysDao().getServiceDo().find(AppHospDept.class, fvo.getSignHospId());
            vo.setAreaCodeCity(uvo.getPatientCity());
            vo.setAreaCodeProvince(uvo.getPatientProvince());

          // 机构
            vo.setHospId(hvo.getId());
            vo.setHospName(hvo.getHospName());
            vo.setHospAreaCode(hvo.getHospAreaCode());
            vo.setHospAddress(hvo.getHospAddress());
            vo.setHospTel(hvo.getHospTel());
          // 医生
            vo.setDrId(dvo.getId());
            vo.setDrName(dvo.getDrName());
            vo.setDrAccount(dvo.getDrAccount());
            vo.setDrPwd(dvo.getDrPwd());
            vo.setDrGender(dvo.getDrGender());
            vo.setDrTel(dvo.getDrTel());
            vo.setMemState("0");
            // 操作人
            vo.setDrOperatorId(drbvo.getId());
            vo.setDrOperatorName(drbvo.getDrName());
            vo.setDrOperatorAccount(drbvo.getDrAccount());
            vo.setDrOperatorPwd(drbvo.getDrPwd());
            vo.setDrOperatorGender(drbvo.getDrGender());
            vo.setDrOperatorTel("13502585265");
            //助理
            if(drvo != null ){
            vo.setHospOperatorId(drvo.getDrHospId());
            vo.setDrAssistantId(drvo.getId());
            vo.setDrAssistantName(drvo.getDrName());
            vo.setDrAssistantAccount("dws");
            vo.setDrAssistantPwd("123");
            vo.setDrAssistantGender(drvo.getDrGender());
            vo.setDrAssistantTel("1358256582");
            }

            // 团队
            vo.setTeamId(fvo.getSignTeamId());
            vo.setTeamName(fvo.getSignTeamName());
           //用户
            vo.setPatientId(uvo.getId());
            vo.setPatientName(uvo.getPatientName());
            vo.setPatientIdno(uvo.getPatientIdno());
            vo.setPatientCard(uvo.getPatientCard());
            vo.setPatientAddress(uvo.getPatientAddress());
            vo.setPatientTel(uvo.getPatientTel());
            vo.setPatientProvince(uvo.getPatientProvince());
            vo.setPatientCity(uvo.getPatientCity());
            vo.setPatientArea(uvo.getPatientArea());
            vo.setPatientStreet(uvo.getPatientStreet());
            vo.setPatientNeighborhoodCommittee("35012552652");
            vo.setPatientjmda(uvo.getPatientjmda());
            vo.setPatientjtda(uvo.getPatientjtda());
            // 单子
            Calendar calendatr = fvo.getSignDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String signdate = sdf.format(calendatr.getTime());
            vo.setSignDate(signdate);
            Calendar cal = fvo.getSignFromDate();
            String signfromdate = sdf.format(cal.getTime());
            vo.setSignFromDate(signfromdate);
            Calendar calen = fvo.getSignToDate();
            String signtodate = sdf.format(calen.getTime());
            vo.setSignToDate(signtodate);
            vo.setSignPayState(fvo.getSignPayState());
            vo.setSignState(fvo.getSignState());
            vo.setSignlx(fvo.getSignlx());
            vo.setSignzfpay(fvo.getSignzfpay());
            vo.setSignczpay(fvo.getSignczpay());
            vo.setSignHealth(fvo.getSignHealthGroup());
            List<AppLabelDisease> labeld = sysDao.getServiceDo().loadByPk(AppLabelDisease.class, "labelSignId", fvo.getId());
            String  Disease ="";
            if(labeld != null && labeld.size()>0){
                for (AppLabelDisease label : labeld) {
                    if(StringUtils.isBlank(Disease)){
                        Disease = label.getLabelValue();
                    }else{
                        Disease =","+ label.getLabelValue();
                    }
                }
            }
            vo.setSignDiseaseGroup(Disease);
            List<AppLabelGroup> labelg = sysDao.getServiceDo().loadByPk(AppLabelGroup.class, "labelSignId", fvo.getId());
            String  Group ="";
            if(labelg != null && labelg.size()>0){
                for (AppLabelGroup label : labelg) {
                    if(StringUtils.isBlank(Group)){
                        Group = label.getLabelValue();
                    }else{
                        Group =","+ label.getLabelValue();
                    }
                }
            }
            vo.setSignPersGroup(Group);
            List<AppLabelEconomics> labele = sysDao.getServiceDo().loadByPk(AppLabelEconomics.class, "labelSignId", fvo.getId());
            String  Economics ="";
            if(labele != null && labele.size()>0){
                for (AppLabelEconomics label : labele) {
                    if(StringUtils.isBlank(Economics)){
                        Economics = label.getLabelValue();
                    }else{
                        Economics =","+ label.getLabelValue();
                    }
                }
            }

            vo.setSignsJjType(Economics);
            JSONObject jsonParam = new JSONObject();
            CloseableHttpClient client = HttpClients.createDefault();
            AesEncrypt aesEncrypt = new AesEncrypt(signkey);
            String json = JsonUtil.toJson(vo);
            String info = aesEncrypt.encrypt(json);
            jsonParam.put("strJson",info);
            String  urlLogin ="http://192.168.30.189:7077/family-doctor/webSign.action?act=upWebSign";
            String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam, "utf-8");
            if(reslut != null){
                JSONObject obj = JSONObject.fromObject(reslut);
                String entity = obj.get("entity").toString();
                String into = aesEncrypt.decrypt(entity);
                System.out.print(into);
                JSONObject fromobject = JSONObject.fromObject(into);
                if( fromobject.get("msgCode").toString().equals("800")){
                    System.out.print("成功！");
                }else{
                    System.out.print("失败！");
                    System.out.print(fromobject.get("msg").toString());

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 通过身份证查询是否建档立卡或签约
     * cxw
     * @param json
     * @return
     */
    public String getSignInfoByCard(String jsonstring){
        AesEncrypt aesEncrypt = new AesEncrypt(signkey);
        try{
            WebUpVo p = (WebUpVo) JacksonUtils.getObject(jsonstring, WebUpVo.class);;
            String json = aesEncrypt.decrypt(p.getStrJson());
            JSONObject fromobject = JSONObject.fromObject(json);
            String orgId = fromobject.getString("orgId");
            String idCard = fromobject.getString("idCard");
            CdOrgconfig orgconfig =(CdOrgconfig) this.sysDao.getServiceDo().find(CdOrgconfig.class,orgId);
            if(orgconfig!=null){
                if(!"1".equals(orgconfig.getStartType())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("该机构没有权限访问！！");
                    return "ajson";
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("未找到该机构，请联系管理员");
                return "ajson";
            }
            AppSignForm appSignForm = this.sysDao.getAppSignformDao().findpatientIdNo(idCard);
            if(appSignForm!=null){
                List<AppLabelEconomics> ls = this.sysDao.getAppLabelGroupDao().findBySignEcon(appSignForm.getId(),"2");
                if(ls!=null && ls.size()>0){
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("该居民属于建档立卡");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("该居民已签约，不属于建档立卡");
                }
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("该居民未签约");
            }
        }catch (Exception e){
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("接口异常");
            e.printStackTrace();
        }
        return "ajson";
    }

    /**
     * web端初始化密码
     * @return
     */
    public String getInitializationDrPwd(){
        try{
            String sql = "SELECT * FROM app_dr_user where DR_PWD_STATE ='0' AND DR_TEL IS NOT NULL";
			Map<String,Object> map = new HashedMap();
            List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
            if(ls != null && ls.size() >0){
                for(AppDrUser drUser : ls){
                    if(StringUtils.isNotBlank(drUser.getDrTel())){
                        if(drUser.getDrTel().length()>=11) {
                            drUser.setDrTelPwd(Md5Util.MD5(drUser.getDrTel().substring(drUser.getDrTel().length() - 6, drUser.getDrTel().length())));
                            sysDao.getServiceDo().modify(drUser);
                        }
                    }
                }
            }
            this.getAjson().setMsg("初始化成功！");
        }catch (Exception e){
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("接口异常");
            e.printStackTrace();
        }
        return "ajson";
    }

    /**
     * 初始化编号
     * @return
     */
    public String changeValue(){
        try{
            String areaCode = PropertiesUtil.getConfValue("cityAreaCode");
            Map<String,Object> map = new HashedMap();
            //初始化服务对象编号
            String sqlO = "SELECT * FROM app_serve_object where 1=1";
            List<AppServeObject> listO = sysDao.getServiceDo().findSqlMap(sqlO,map,AppServeObject.class);
            if(listO != null && listO.size()>0){
                for(AppServeObject ll:listO){
                    if(ll.getSeroValue().indexOf(areaCode)==-1){
                        if(ll.getSeroValue().length()==1){
                            ll.setSeroValue(areaCode+"000"+ll.getSeroValue());
                        }else if(ll.getSeroValue().length()==2){
                            ll.setSeroValue(areaCode+"00"+ll.getSeroValue());
                        }else if(ll.getSeroValue().length()==3){
                            ll.setSeroValue(areaCode+"0"+ll.getSeroValue());
                        }
                    }
                    sysDao.getServiceDo().modify(ll);
                }
            }
            //初始化服务内容编号
            String sqlpk = "SELECT * FROM app_serve_package ";
            List<AppServePackage> listpk = sysDao.getServiceDo().findSqlMap(sqlpk,map,AppServePackage.class);
            if(listpk != null && listpk.size()>0){
                for(AppServePackage ll:listpk){
                    if(ll.getSerpkValue().indexOf(areaCode)==-1) {
                        if("999".equals(ll.getSerpkLeve())){
                            if(ll.getSerpkValue().length()==1){
                                ll.setSerpkValue("3500000"+ll.getSerpkValue());
                            }else if(ll.getSerpkValue().length()==2){
                                ll.setSerpkValue("350000"+ll.getSerpkValue());
                            }
                        }else{
                            if(ll.getSerpkValue().length()==1){
                                ll.setSerpkValue(areaCode+"000"+ll.getSerpkValue());
                            }else if(ll.getSerpkValue().length()==2){
                                ll.setSerpkValue(areaCode+"00"+ll.getSerpkValue());
                            }else if(ll.getSerpkValue().length()==3){
                                ll.setSerpkValue(areaCode+"0"+ll.getSerpkValue());
                            }
                        }
                    }
                    sysDao.getServiceDo().modify(ll);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";

    }
    //初始化服务组合编号
    public String changValueGroup(){
        try{
            String areaCode = PropertiesUtil.getConfValue("cityAreaCode");
            Map<String,Object> map = new HashMap<>();
            //初始化服务组合编号
            String sqlg = "SELECT * FROM app_serve_group";
            List<AppServeGroup> listG = sysDao.getServiceDo().findSqlMap(sqlg,map,AppServeGroup.class);
            if(listG != null && listG.size()>0){
                for(AppServeGroup ll:listG){
                    if(ll.getSergValue().indexOf(areaCode)==-1){
                        if(ll.getSergValue().length()==1){
                            ll.setSergValue(areaCode+"000"+ll.getSergValue());
                        }else if(ll.getSergValue().length()==2){
                            ll.setSergValue(areaCode+"00"+ll.getSergValue());
                        }else if(ll.getSergValue().length()==3){
                            ll.setSergValue(areaCode+"0"+ll.getSergValue());
                        }
                    }
                    if(StringUtils.isNotBlank(ll.getSergPkId())){
                        String[] pkValues = ll.getSergPkId().split(";");
                        String pkValue = "";
                        for(String pk:pkValues){
                            AppServePackage pka = (AppServePackage)sysDao.getServiceDo().find(AppServePackage.class,pk);
                            if(pka != null){
                                if(StringUtils.isBlank(pkValue)){
                                    pkValue = pka.getSerpkValue();
                                }else{
                                    pkValue+=";"+pka.getSerpkValue();
                                }
                            }
                        }
                        ll.setSergPkValue(pkValue);
                    }
                    if(StringUtils.isNotBlank(ll.getSergObjectId())){
                        AppServeObject object = (AppServeObject)sysDao.getServiceDo().find(AppServeObject.class,ll.getSergObjectId());
                        if(object != null){
                            ll.setSergObjectValue(object.getSeroValue());
                        }
                    }
                    sysDao.getServiceDo().modify(ll);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    public String changeValueEcon(){
        try{
            String areaCode = PropertiesUtil.getConfValue("cityAreaCode");
            Map<String,Object> map = new HashMap<>();
            //初始化经济类型编号
            String sqle = "SELECT * FROM app_serve_econ";
            List<AppServeEcon> liste = sysDao.getServiceDo().findSqlMap(sqle,map,AppServeEcon.class);
            if(liste != null && liste.size()>0){
                for(AppServeEcon ll:liste){
                    if(ll.getEconValue().indexOf(areaCode)==-1){
                        if(ll.getEconValue().length()==1){
                            ll.setEconValue(areaCode+"000"+ll.getEconValue());
                        }else if(ll.getEconValue().length()==2){
                            ll.setEconValue(areaCode+"00"+ll.getEconValue());
                        }else if(ll.getEconValue().length()==3){
                            ll.setEconValue(areaCode+"0"+ll.getEconValue());
                        }
                    }
                    sysDao.getServiceDo().modify(ll);
                }
            }
            //初始化政府补贴编号
            String sqlg = "SELECT * FROM app_serve_gov";
            List<AppServeGov> listg = sysDao.getServiceDo().findSqlMap(sqlg,map,AppServeGov.class);
            if(listg != null && listg.size()>0){
                for(AppServeGov ll:listg){
                    if(ll.getGovValue().indexOf(areaCode)==-1){
                        if(ll.getGovValue().length()==1){
                            ll.setGovValue(areaCode+"000"+ll.getGovValue());
                        }else if(ll.getGovValue().length()==2){
                            ll.setGovValue(areaCode+"00"+ll.getGovValue());
                        }else if(ll.getGovValue().length()==3){
                            ll.setGovValue(areaCode+"0"+ll.getGovValue());
                        }
                    }
                    sysDao.getServiceDo().modify(ll);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 初始化经济补贴表中的经济编号和补贴编号
     * @return
     */
    public String changeValueEG(){
        try{
            String areaCode = PropertiesUtil.getConfValue("cityAreaCode");
            Map<String,Object> map = new HashMap<>();
            //初始化服务组合编号
            String sqlg = "SELECT * FROM app_econ_and_gov";
            List<AppEconAndGov> listG = sysDao.getServiceDo().findSqlMap(sqlg,map,AppEconAndGov.class);
            if(listG != null && listG.size()>0){
                for(AppEconAndGov ll:listG){
                    if(StringUtils.isNotBlank(ll.getEagGovId())){
                        String[] govIds = ll.getEagGovId().split(";");
                        String govValue = "";
                        for(String govId:govIds){
                            AppServeGov gov = (AppServeGov)sysDao.getServiceDo().find(AppServeGov.class,govId);
                            if(gov != null){
                                if(StringUtils.isBlank(govValue)){
                                    govValue = gov.getGovValue();
                                }else{
                                    govValue+=";"+gov.getGovValue();
                                }
                            }
                        }
                        ll.setEagGovValue(govValue);
                    }
                    if(StringUtils.isNotBlank(ll.getEagEconId())){
                        AppServeEcon econ = (AppServeEcon)sysDao.getServiceDo().find(AppServeEcon.class,ll.getEagEconId());
                        if(econ != null){
                            ll.setEagEconValue(econ.getEconValue());
                        }
                    }
                    sysDao.getServiceDo().modify(ll);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 初始化服务内容编号
     * @return
     */
    public String changeMealValue(){
        try{
            String areaCode = PropertiesUtil.getConfValue("cityAreaCode");
            Map<String,Object> map = new HashMap<>();
            //初始化服务组合编号
            String sqlm = "SELECT * FROM app_serve_setmeal";
            List<AppServeSetmeal> listm = sysDao.getServiceDo().findSqlMap(sqlm,map,AppServeSetmeal.class);
            if(listm != null && listm.size()>0){
                for(AppServeSetmeal ll:listm){
                    if(ll.getSersmValue().indexOf(areaCode)==-1){
                        if(ll.getSersmValue().length()==1){
                            ll.setSersmValue(areaCode+"000"+ll.getSersmValue());
                        }else if(ll.getSersmValue().length()==2){
                            ll.setSersmValue(areaCode+"00"+ll.getSersmValue());
                        }else if(ll.getSersmValue().length()==3){
                            ll.setSersmValue(areaCode+"0"+ll.getSersmValue());
                        }
                    }
                    if(StringUtils.isNotBlank(ll.getSersmGroupId())){
                        String[] groupIds = ll.getSersmGroupId().split(";");
                        String objectValue = "";
                        String pkValue = "";
                        String groupValue = "";
                        for(String govId:groupIds){
                            AppServeGroup group = (AppServeGroup)sysDao.getServiceDo().find(AppServeGroup.class,govId);
                            if(group != null){
                                if(StringUtils.isBlank(groupValue)){
                                    groupValue = group.getSergValue();
                                }else{
                                    groupValue+=";"+group.getSergValue();
                                }
                                if(StringUtils.isBlank(pkValue)){
                                    pkValue = group.getSergPkValue();
                                }else{
                                    pkValue+=";"+group.getSergPkValue();
                                }
                                if(StringUtils.isBlank(objectValue)){
                                    objectValue = group.getSergObjectValue();
                                }else{
                                    objectValue+=";"+group.getSergObjectValue();
                                }
                            }
                        }
                        ll.setSersmGroupValue(groupValue);
                        ll.setSersmPkValue(pkValue);
                        ll.setSersmObjectValue(objectValue);
                    }
                    sysDao.getServiceDo().modify(ll);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    // 基卫对 家签 发起 死亡自动解约接口
    public String JwSignsurrender(){
        try {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            //message
            String message = getRequest().getParameter("message");
            if (StringUtils.isNotBlank(message)) {
            AesEncrypt aesEncrypt = new AesEncrypt(signkey);
            String info = aesEncrypt.decrypt(message);
            SignsurrenderVo qvo = JsonUtil.fromJson(info, SignsurrenderVo.class);
            // SignsurrenderVo qvo = (SignsurrenderVo) getAppJson(SignsurrenderVo.class);
            if (qvo != null) {
                AppSignForm vo = sysDao.getAppSignMotoeDao().findSignformJMDA(qvo);
                if (vo != null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    Date date = formatter.parse(qvo.getSignDieDate());
                    Calendar calendar = Calendar.getInstance();
                    //vo.setSignDelDate(calendar.getInstance());
                    calendar.setTime(date);
                    vo.setSignDieDate(calendar);
                    vo.setSignDelReason(qvo.getReason());
                    vo.setSignDelType("1");
                    vo.setSignState("9");
                    vo.setSignOperatorName(qvo.getSignOperatorName());
                    sysDao.getServiceDo().modify(vo);
                    this.getAjson().setMsg("成功！");
                    this.getAjson().setMsgCode("800");
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查无该居民！");
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数为空！");
            }
        }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数为空！");
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ajson";
    }

    public String findSignPatient()throws Exception
    {
        String message = getRequest().getParameter("message");
        if (StringUtils.isNotBlank(message)) {
            AesEncrypt aesEncrypt = new AesEncrypt(signkey);
            String info = aesEncrypt.decrypt(message);
            JSONObject jsonAll = JSONObject.fromObject(info);
            String patientIdno = jsonAll.get("patientIdno").toString();
            if(StringUtils.isNotBlank(patientIdno)){
                AppSignForm vo = sysDao.getAppSignMotoeDao().findSignPatient(patientIdno);
                if(vo !=null  ){
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setVo(vo);
                }else{
                    this.getAjson().setMsgCode("800");
                }
            }else{
                this.getAjson().setMsg("参数为空！");
                this.getAjson().setMsgCode("900");
            }
        }else{
            this.getAjson().setMsg("参数为空！");
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }
    /**
     * 表3-2-4 福建省建档立卡农村贫困人口家庭医生签约服务工作情况表钻取统计
     */
    public String archivingCardCount(){
        try{
            ResidentVo qvo = (ResidentVo)this.getAppJson(ResidentVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isNotBlank(qvo.getAreaId())){
                    if(qvo.getAreaId().length()<4){
                        this.getAjson().setMsg("请输入市级及以下行政区划");
                    }
                }
                if(StringUtils.isNotBlank(qvo.getAreaId())){
                    CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(qvo.getAreaId(),"2"));
                    String sourceType = "1";
                    if(cityCode != null){
                        if("0".equals(cityCode.getCodeTitle())){//查询医保数据
                            sourceType = "3";
                        }
                    }
                    qvo.setJdSourceType(sourceType);
                    List<Map<String, Object>> list =  this.sysDao.getAppSignAnalysisDao().archivingCardCount(qvo);
                    this.getAjson().setRows(list);
                    this.getAjson().setMsgCode("800");
                }else if(StringUtils.isNotBlank(qvo.getHospId())){
//                    Map<String,Object> map = this.sysDao.getAppSignAnalysisDao().findArchivingCountByHosp(qvo);
                    String sourceType = "1";
                    AppHospDept deptt = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
                    if(deptt != null){
                        if(StringUtils.isNotBlank(deptt.getHospAreaCode())){
                            CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(deptt.getHospAreaCode(),"2"));
                            if(cityCode != null){
                                if("0".equals(cityCode.getCodeTitle())){//查询医保数据
                                    sourceType = "3";
                                }
                            }
                        }
                    }
                    qvo.setJdSourceType(sourceType);
                    List<AppArchivintPeopleEntity> list = sysDao.getAppSignAnalysisDao().findArchivingPeopleByHosp(qvo);
                    int signCount = qvo.getItemCount();
//                    int zdCount = 0;
//                    if(list != null && list.size()>0){
//                        signCount = list.size();
//                        for(AppArchivintPeopleEntity ll:list){
//                            if(StringUtils.isNotBlank(ll.getKeyCrowd())){
//                                zdCount++;
//                            }
//                        }
//                    }
                    Map<String,Object> mmp = new HashMap<>();
                    mmp.put("totalSignCount",signCount);
//                    mmp.put("paidSignCount",signCount);
//                    mmp.put("keySignCount",zdCount);
                    this.getAjson().setMap(mmp);
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setRows(list);
                    this.getAjson().setMsgCode("800");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 表3-2-5 福建省2018年建档立卡农村贫困人口家庭医生签约服务分类管理报表
     * @return
     */
    public String archivingCardServeCount(){
        try{
            ResidentVo qvo = (ResidentVo)this.getAppJson(ResidentVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else if (StringUtils.isNotBlank(qvo.getAreaId())){
                CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(qvo.getAreaId(),"2"));
                String sourceType = "1";
                if(cityCode != null){
                    if("0".equals(cityCode.getCodeTitle())){//查询医保数据
                        sourceType = "3";
                    }
                }
                qvo.setJdSourceType(sourceType);
                List<Map<String, Object>> list =  this.sysDao.getAppSignAnalysisDao().archivingCardServeCount(qvo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            }else if(StringUtils.isNotBlank(qvo.getHospId())){

                String sourceType = "1";
                AppHospDept deptt = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
                if(deptt != null){
                    if(StringUtils.isNotBlank(deptt.getHospAreaCode())){
                        CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(deptt.getHospAreaCode(),"2"));
                        if(cityCode != null){
                            if("0".equals(cityCode.getCodeTitle())){//查询医保数据
                                sourceType = "3";
                            }
                        }
                    }
                }
                qvo.setJdSourceType(sourceType);

                Map<String,Object> map = sysDao.getAppSignAnalysisDao().findPovCount(qvo);
                this.getAjson().setMap(map);
                List<AppArchivintPeopleTTEntity> list = sysDao.getAppSignAnalysisDao().findArchivingTTPeopleByHosp(qvo);
                this.getAjson().setQvo(qvo);
                this.getAjson().setRows(list);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询区域团队数和机构团队信息
     * @return
     */
    public String findTeamCount(){
        try{
            ResidentVo qvo = (ResidentVo)this.getAppJson(ResidentVo.class);
            Map<String,Object> map = new HashMap<>();
            int count = 0;
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else if(StringUtils.isNotBlank(qvo.getAreaId())){
                List<Map<String,Object>> list = sysDao.getAppTeamDao().findTeamCountByAreaId(qvo.getAreaId());
                this.getAjson().setRows(list);
            }else if(StringUtils.isNotBlank(qvo.getHospId())){
                map = sysDao.getAppTeamDao().findTeamCountByHospId(qvo.getHospId());
                this.getAjson().setMap(map);
                //查询团队信息
                List<AppArchivintTeamEntity> listTeam = sysDao.getAppTeamDao().findTeamXxByHospId(qvo);

                this.getAjson().setQvo(qvo);
                this.getAjson().setRows(listTeam);

            }

//            else if(StringUtils.isNotBlank(qvo.getTeamId())){
//                List<AppArchivintPeopleEntity> list = sysDao.getAppSignformDao().findSignArchivingByTeamId(qvo);
//                map = sysDao.getAppSignformDao().findMapBySign(qvo);
//                List<AppArchivintPeopleEntity> list = sysDao.getAppSignformDao().findPeopleBySign(qvo);
//                this.getAjson().setMap(map);
//                this.getAjson().setRows(list);
//                this.getAjson().setQvo(qvo);
//            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 保存基卫中医体质辨识
     * @return
     */
    public String savaTcmFromJw(){
        try{
            AppBasicTcmAllQvo qvo = (AppBasicTcmAllQvo)getAppJson(AppBasicTcmAllQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String str = sysDao.getAppTcmSyndromeDao().savaBasicTcm(qvo);
                if("true".equals(str)){
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("上传成功");
                }else{
                    this.getAjson().setMsgCode("900");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询区域团队数和机构团队信息(改)
     * @return
     */
    public String findTeamCountTwo(){
        try{
            ResidentVo qvo = (ResidentVo)this.getAppJson(ResidentVo.class);
            Map<String,Object> map = new HashMap<>();
            int count = 0;
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else if(StringUtils.isNotBlank(qvo.getAreaId())){
                List<Map<String,Object>> list = sysDao.getAppTeamDao().findTeamCountByAreaIdTwo(qvo);
                this.getAjson().setRows(list);
            }
            /*else if(StringUtils.isNotBlank(qvo.getHospId())){
                map = sysDao.getAppTeamDao().findTeamCountByHospId(qvo.getHospId());
                this.getAjson().setMap(map);
                //查询团队信息
                List<AppArchivintTeamEntity> listTeam = sysDao.getAppTeamDao().findTeamXxByHospId(qvo);
                this.getAjson().setQvo(qvo);
                this.getAjson().setRows(listTeam);
            }*/
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询用户信息和签约信息（山西）
     * @return
     */
    public String appFindPatientInfo(){
        try{
            AppCommQvo vo = (AppCommQvo)getAppJson(AppCommQvo.class);
            if(vo != null){
                if(StringUtils.isNotBlank(vo.getPatientIdno())){
                    AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(vo.getPatientIdno());
                    Map<String,Object> map = new HashMap<String,Object>();
                    if(user != null){
                        //查询签约单
                        AppSignForm form = sysDao.getAppSignformDao().findSignFormByState(user.getId());
                        if(form != null){
                            PatientInfo info = sysDao.getAppPatientUserDao().findUserInfo(user.getId(),"0",form.getId());
                            if(info!=null){
                                HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                                qqvo.setIdno(info.getIdno());
                                qqvo.setType("2");
                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, form.getSignAreaCode().substring(0,4));
                                if(value!=null){
                                    qqvo.setUrlType(value.getCodeTitle());
                                }else{
                                    qqvo.setUrlType("14");
                                }
                                String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,null,null, DrPatientType.DR.getValue());
                                if(org.apache.commons.lang.StringUtils.isNotBlank(str)){
                                    T_dwellerfile file = sysDao.getSecurityCardAsyncBean().analysisFileOne(str, qqvo.getUrlType());
                                    if(file != null){
                                        map.put("jmdnh",file.getJmdah());
                                        String result = "";
                                        if (AddressType.FZ.getValue().equals(qqvo.getUrlType())) {

                                        } else if (AddressType.QZ.getValue().equals(qqvo.getUrlType())) {
                                            result = "qz_";
                                        } else if (AddressType.ZZ.getValue().equals(qqvo.getUrlType())) {
                                            result = "zz_";
                                        } else if (AddressType.PT.getValue().equals(qqvo.getUrlType())) {
                                            result = "pt_";
                                        } else if (AddressType.NP.getValue().equals(qqvo.getUrlType())) {
                                            result = "np_";
                                        } else if (AddressType.SM.getValue().equals(qqvo.getUrlType())) {
                                            result = "sm_";
                                        } else if (AddressType.LY.getValue().equals(qqvo.getUrlType())) {
                                            result = "ly_";
                                        } else if (AddressType.ND.getValue().equals(qqvo.getUrlType())) {
                                            result = "nd_";
                                        } else if (AddressType.XM.getValue().equals(qqvo.getUrlType())) {
                                            result = "xm_";
                                        } else if (AddressType.PINGT.getValue().equals(qqvo.getUrlType())) {
                                            result = "pg_";
                                        }
                                        map.put("orgId",result+file.getSqh());
                                    }
                                }
                                this.getAjson().setMsgCode("800");
                                this.getAjson().setVo(info);
                                this.getAjson().setMap(map);
                            }
                        }else{
                            map.put("jmdnh",user.getPatientjmda());
                            this.getAjson().setMsgCode("800");
                            this.getAjson().setVo(user);
                            this.getAjson().setMap(map);
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("未找到用户信息");
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("身份证参数不能为空");
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 根据团队和时间查询签约居民信息
     * @return
     */
    public String findSignByTeam(){
        try{
            ResidentVo qvo = (ResidentVo)this.getAppJson(ResidentVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{


            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 根据居民身份证查询该居民服务数据（福州建档立卡）
     * @return
     */
    public String getServeByIdno(){
        try{
            AppCommQvo qvo = (AppCommQvo)getAppJson(AppCommQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式不正确");
            }else{
                if(StringUtils.isBlank(qvo.getPatientIdno())){
                    this.getAjson().setMsg("身份证不能为空");
                    this.getAjson().setMsgCode("900");
                }else{
                    //根据身份证查询居民信息
                    AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(qvo.getPatientIdno());
                    if(user == null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无居民信息，请联系管理员处理");
                    }else{
                        //根据居民信息提供的主键查询签约单
                        String[] signState = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
                        AppSignForm form = sysDao.getAppSignformDao().findSignBySignState(signState,user.getId());
                        if(form != null){
                            if(StringUtils.isNotBlank(form.getSignpackageid())){//服务包不为空
                                List<AppServeEntity> list = sysDao.getAppServeSetmealDao().findSeverMealByIds(form.getSignpackageid());
                                this.getAjson().setRows(list);
                                this.getAjson().setMsgCode("800");
                            }
                        }else{
                            this.getAjson().setMsg("900");
                            this.getAjson().setMsgCode("查无签约信息，请联系管理员处理");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 根据机构查询团队信息
     * @return
     * @throws Exception
     */
    public String getTeamData() throws Exception{
        try{
            AppTeamVo qvo = (AppTeamVo)getAppJson(AppTeamVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式不正确");
            }else{
                if(StringUtils.isNotBlank(qvo.getTeamSrc())){
                    if (qvo.getTeamSrc().equals(AddressType.FZS.getValue())) {
                    } else if (qvo.getTeamSrc().equals(AddressType.QZS.getValue())) {
                        qvo.setTeamHospId("qz_"+qvo.getTeamHospId());
                    } else if (qvo.getTeamSrc().equals(AddressType.ZZS.getValue())) {
                        qvo.setTeamHospId("zz_"+qvo.getTeamHospId());
                    } else if (qvo.getTeamSrc().equals(AddressType.PTS.getValue())) {
                        qvo.setTeamHospId("pt_"+qvo.getTeamHospId());
                    } else if (qvo.getTeamSrc().equals(AddressType.NPS.getValue())) {
                        qvo.setTeamHospId("np_"+qvo.getTeamHospId());
                    } else if (qvo.getTeamSrc().equals(AddressType.SMS.getValue())) {
                        qvo.setTeamHospId("sm_"+qvo.getTeamHospId());
                    }
                }
                //查询对应机构下的对团队 返回团队id、团队名称、团队下的成员数量
                List<AppTeamMotoeEntity> ls = this.sysDao.getAppTeamDao().findTeamMotoe(qvo);
                if(ls != null && ls.size() >0){
                    this.getAjson().setRows(ls);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询福州医保签约数据
     * @return
     */
    public String findYbSign(){
        try{
            AppYbSignQvo qvo = (AppYbSignQvo)this.getAppJson(AppYbSignQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppYbSignEntity> list = sysDao.getAppSignSettlementDao().findYbSign(qvo);
                if(list != null && list.size()>0){
                    this.getAjson().setRows(list);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setQvo(qvo);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查无数据");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 保存状态
     * @return
     */
    public String saveYbPrintState(){
        try {
            AppYbSignQvo qvo = (AppYbSignQvo)this.getAppJson(AppYbSignQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("主键不能为空");
                }else{
                    AppSignSettlement settlement = (AppSignSettlement)sysDao.getServiceDo().find(AppSignSettlement.class,qvo.getId());
                    settlement.setPrintState("1");
                    sysDao.getServiceDo().modify(settlement);
                    this.getAjson().setMsg("保存成功");
                    this.getAjson().setMsgCode("800");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 按行政区划级别各级展示 签约总数，一般人群签约数，高血压签约数，
     * 糖尿病签约数、老年人签约数、孕产妇人群签约数，儿童签约数、
     * 严重精神障碍签约数、肺结核签约数；
     * @return
     */
    public String findSignManage(){
        try{
            ResidentVo qvo = (ResidentVo)this.getAppJson(ResidentVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<Map<String, Object>> list = sysDao.getAppResidentAnalysisDao().findSignManage(qvo);
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

    /**
     * 姓名、身份证、档案号、是否签约、服务类型、签约行政区划、行政区划级别
     * 签约底层人员信息
     * @return
     */
    public String findPeopleSign(){
        try{
            ResidentVo qvo = (ResidentVo)this.getAppJson(ResidentVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isNotBlank(qvo.getHospId())){
                    List<AppSignPeopleEntity> list = sysDao.getAppSignformDao().findByHospQvo(qvo);
                    this.getAjson().setRows(list);
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("机构主键不能为空!");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
}
