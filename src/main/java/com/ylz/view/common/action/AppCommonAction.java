package com.ylz.view.common.action;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.entity.*;
import com.ylz.bizDo.app.entity.AppSignFormEntity;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppHepVideoQvo;
import com.ylz.bizDo.app.vo.AppMenuRoleQvo;
import com.ylz.bizDo.app.vo.AppSystemVsersionQvo;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.entity.AddressHospEntity;
import com.ylz.bizDo.cd.entity.CdAddressEntity;
import com.ylz.bizDo.cd.po.*;
import com.ylz.bizDo.jtapp.basicHealthEntity.AppEnterpatientEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.ResidentHealthFiles;
import com.ylz.bizDo.jtapp.basicHealthEntity.ResidentRecordsEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.basicHealthVo.InoculationVo;
import com.ylz.bizDo.jtapp.commonEntity.*;
import com.ylz.bizDo.jtapp.commonVo.*;
import com.ylz.bizDo.jtapp.drEntity.*;
import com.ylz.bizDo.jtapp.drVo.AppFileAuditQvo;
import com.ylz.bizDo.jtapp.drVo.AppLyQvo;
import com.ylz.bizDo.jtapp.drVo.AppWarningSettingQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientPerfectDataEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;
import com.ylz.bizDo.web.po.WebLabelGroup;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.Constrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import com.ylz.packcommon.hyd.Base;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.eclipse.jetty.util.HostMap;

import java.util.*;

/**
 * 医生个人信息接口action.
 */
@SuppressWarnings("all")
@Action(
        value = "appCommon",
        results = {
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"})
        }
)
public class AppCommonAction extends CommonAction {

    private static final  int yxDay = 15;
    private static final String agreeId = "9ac4acce-6d5e-4979-9258-10a818af129c";
    private static int allPage = 0;
    private static int page = 0;





    /**
     * 获取手机验证码
     *
     * @return
     */
    public String appTelShort() {
        try {
            Map<String,Object> map = new HashMap<String, Object>();
//            map.put("STATE","0");
//            if(allPage == 0){
//                String sqlCount = "SELECT count(1) FROM APP_PATIENT_USER where PATIENT_EASE_STATE IS NULL";
//                int allCount = sysDao.getServiceDo().findCount(sqlCount,map);
//                allPage  =(allCount+500-1)/500;
//            }
//            if(allPage != 0){
//                for (int i = 0; i < allPage; i++) {
//                    page++;
//                    sysDao.getSecurityCardAsyncBean().upDatePatientEase(page);
//                }
//            }

//            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findByManageCont();
//            List<String> ls = new ArrayList<String>();
////            String startDate = sysDao.getAppManageCountDao().findSignFirstDate();
//            String startDate = "2016-01-01";
//            String endDate = ExtendDate.getYMD(Calendar.getInstance());
//            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
//            if(ls != null && ls.size() >0){
//                for(String s : ls){
//                    sysDao.getSecurityCardAsyncBean().totalManageCount(s,lsTeam);
//                }
//            }
            String userId = null;
            AppShrotQvo qvo = (AppShrotQvo) getAppJson(AppShrotQvo.class);
            String code = "";
            if (qvo != null) {
                if (StringUtils.isNotBlank(qvo.getType())) {
                    boolean telResult = AccountValidatorUtil.isMobile(qvo.getTel());
                    if(telResult){
                        if (qvo.getType().equals(CommonShortType.HUANGZHE.getValue())) {
                            AppPatientUser user = this.getSysDao().getAppPatientUserDao().findByTelPhone(qvo.getTel());
                            if (user != null) {
                                userId = user.getId();
                                code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneYzm(user.getId(), user.getPatientIdno(), user.getPatientTel());
                            } else {
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("手机号不存在，请输入正确手机号！");
                                return "ajson";
                            }
                        } else if (qvo.getType().equals(CommonShortType.YISHENG.getValue())) {
                            AppDrUser doc = new AppDrUser();
                            List<AppDrUserEntity> ls = sysDao.getAppDrUserDao().findByUser(qvo.getTel(),null,CommonLoginType.ZHANGHAO.getValue());
//                            if(ls != null && ls.size() == 1){
//                                doc = this.getSysDao().getAppDrUserDao().findByTelPhone(qvo.getTel());
//                            }else
                            if(ls != null  && ls.size() > 0){
//                                this.getAjson().setMsgCode("900");
//                                this.getAjson().setMsg(qvo.getTel()+"该手机号码,有多条重复数据,请联系管理人员,进行处理!");
//                                return "ajson";
                                userId = ls.get(0).getId();
                                code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneYzm(ls.get(0).getId(), ls.get(0).getDrTel(), ls.get(0).getDrTel());
                            }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("手机号不存在，请输入正确手机号！");
                                return "ajson";
                            }
//                            if (doc != null) {
//                                code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneYzm(doc.getId(), doc.getDrAccount(), doc.getDrTel());
//                            } else {
//                                this.getAjson().setMsgCode("900");
//                                this.getAjson().setMsg("手机号不存在，请输入正确手机号！");
//                                return "ajson";
//                            }
                        } else if (qvo.getType().equals(CommonShortType.ZHUCE.getValue())) {
                            code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneYzm(null, qvo.getTel(), qvo.getTel());
                        }else if (qvo.getType().equals(CommonShortType.BANGDINGTEL.getValue())) {
                            code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneYzm(null, qvo.getTel(), qvo.getTel());
                        }
                        String content = String.format(Constrats.MSG_LOGIN, code);
                        String result = PropertiesUtil.getConfValue("messageCode");
                        //回执ID
                        int hzId  = 0;
                        //int hzId = MessageDtklUtil.send(phone, content);
                        CdShortMessage msg = new CdShortMessage(userId,qvo.getTel(),content,String.valueOf(hzId),Calendar.getInstance(),null);
                        //正式的通过发送手机短信
                        if (result.equals("0")) {
                            AppMessageEntityXml xml = this.getSysDao().getAppMsgCodeDao().sendMessageNew(qvo.getTel(), content);
                            if(xml!= null){
                                if(xml.getMessage() != null){
                                    msg.setMsgCzId(xml.getMessage().getMsgid());
                                }else{
                                    if(StringUtils.isNotBlank(xml.getCode())) {
                                        msg.setMsgCzId(xml.getCode());
                                    }
                                }
                                if(xml.getCode().equals("03") && StringUtils.isNotBlank(xml.getCode())){
                                    this.getAjson().setMsg("短信已经发送,请注意查收!");
                                }else{
                                    this.getAjson().setMsg("短信异常,暂时无法使用!");
                                }
                            }else{
                                this.getAjson().setMsg("短信异常,暂时无法使用!");
                            }
                        } else {
                            this.getAjson().setMsg(content);
                        }
                        this.sysDao.getServiceDo().add(msg);
                       //蒲公英测试版直接返回验证码
//                        this.getAjson().setMsg(content);
                    }else {
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("不是有效的手机号码!");
                    }
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
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
     * 根据地址转换base64
     *
     * @return
     */
    public String appBaseImageUrl() {
        try {
            AppBaseImageQvo qvo = (AppBaseImageQvo) this.getAppJson(AppBaseImageQvo.class);
            if (qvo != null) {
                if (StringUtils.isNotBlank(qvo.getImageUrl())) {
                    this.getAjson().setMsg(FileUtils.encodeBase64File(qvo.getImageUrl()));
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
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
    public String appAddressResult() {
        try {
            AppAddressQvo qvo = (AppAddressQvo) this.getAppJson(AppAddressQvo.class);
            if (qvo != null) {
//                List<CdAddressEntity> ls = this.getSysDao().getCdAddressDao().findByUpIdOrNot(qvo.getUpId());
                List<CdAddressEntity> ls = this.getSysDao().getCdAddressDao().findByQvo(qvo);
                this.getAjson().setRows(ls);
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
     * 查询社区接口
     *
     * @return
     */
    public String appHospResult() {
        try {
            AppAddressQvo qvo = (AppAddressQvo) this.getAppJson(AppAddressQvo.class);
            if (qvo != null) {
                if (StringUtils.isNotBlank(qvo.getUpId())) {
                    List<AppHospEntity> ls = this.getSysDao().getAppHospDeptDao().findByAreaId(qvo.getUpId());
                    this.getAjson().setRows(ls);
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("街道主键不能为空");
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
                        AppTeam team = (AppTeam) this.getSysDao().getServiceDo().find(AppTeam.class, qvo.getTeamId());//团队信息
                        AppPatientUser patientUser = (AppPatientUser)this.getSysDao().getServiceDo().find(AppPatientUser.class,qvo.getPatientId());//患者信息
                        if (team != null && patientUser != null) {
                            AppHospDept dept = (AppHospDept) this.getSysDao().getServiceDo().find(AppHospDept.class, team.getTeamHospId());
                            if(dept != null){
                                if("350702".equals(AreaUtils.getAreaCode(dept.getHospAreaCode(),"3"))){
                                    xyGroup = CodeGroupConstrats.GROUP_YPXY;
                                }else{
                                    xyGroup = CodeGroupConstrats.GROUP_NPXY;
                                }
                                AppAgreement vv = this.getSysDao().getAppAgreementDao().findByHospId(team.getTeamHospId(), qvo.getQyType());
                                if(vv == null){

                                    AppAgreement vvv = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class, agreeId);
                                    AppAgreement agreement = new AppAgreement();
                                    String agreeAreaCode = AreaUtils.getAreaCode(dept.getHospAreaCode(), "2");
                                    agreement.setMentUseType("2");
                                    agreement.setMentCityId(agreeAreaCode);
                                    agreement.setMentContent(vvv.getMentContent());
                                    agreement.setMentState("1");
                                    agreement.setMentType("1");
                                    agreement.setMentHospId(dept.getId());
                                    agreement.setMentTitle(vvv.getMentTitle());
                                    sysDao.getServiceDo().add(agreement);
                                    sysDao.getServiceDo().removePoFormSession(agreement);
                                }
                            }
                            AppAgreement v = this.getSysDao().getAppAgreementDao().findByHospId(team.getTeamHospId(), qvo.getQyType());
                            if (v != null && dept != null) {
                                AppSignForm form = new AppSignForm();
                                if("1".equals(qvo.getState())){
                                    form=null;
                                }else{
                                    form = this.getSysDao().getAppSignformDao().getSignFormUserId(patientUser.getId());
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

                                AppDrUser user = null;
                                if(form != null){
                                    user = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, form.getSignDrId());
                                }else{
                                    user = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, qvo.getDrId());
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
                                String patientCard = "";
                                if (StringUtils.isNotBlank(patientUser.getPatientCard())) {
                                    patientCard = patientUser.getPatientCard();
                                }
                                String patientjmda="";
                                if(StringUtils.isNotBlank(patientUser.getPatientjmda())){
                                    patientjmda= patientUser.getPatientjmda();
                                }
                                String drAssistantName="";
                                String drAssistantTel="";
                                if(user != null ){
                                    if(StringUtils.isNotBlank(user.getDrName())){
                                        drAssistantName = user.getDrName();
                                    }
                                    if(StringUtils.isNotBlank(user.getDrTel())){
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
                                content = content.replace("{31}", patientCard);
                                if(form != null){
                                    content = content.replace("{97}",addrxx);
                                    AppDrUser drUser = null;
                                    if(StringUtils.isNotBlank(qvo.getDrId())){
                                        drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                                    }
                                    if(drUser != null){
                                        drTel = drUser.getDrTel();
                                        drName = drUser.getDrName();
                                        if(StringUtils.isNotBlank(drName)) {
                                            content = content.replace("{02}", drName);
                                        }
                                        if(StringUtils.isNotBlank(drTel)){
                                            content = content.replace("{03}", drTel);
                                        }
                                    }else {
                                        content = content.replace("{02}", "");
                                        content = content.replace("{03}", "");
                                    }
                                    if(form.getSignFromDate() != null){
                                        content = content.replace("{10}", ExtendDate.getYMD(form.getSignFromDate()));
                                        content = content.replace("{11}", ExtendDate.getYMD(form.getSignToDate()));
                                    }else{
                                        content = content.replace("{10}", map.get("start"));
                                        content = content.replace("{11}", map.get("end"));
                                    }
                                    if(StringUtils.isNotBlank(form.getSignNum())){
                                        content = content.replace("{30}", form.getSignNum());
                                    }else{
                                        content = content.replace("{30}", "");
                                    }
                                    content = content.replace("{23}", teamName);
                                    List<AppLabelGroup> ls = this.getSysDao().getAppLabelGroupDao().findBySignGroup(form.getId(),"3");
                                    String ypxy = "";
//                                    boolean flag = false;
//                                    boolean flags = false;
                                    List<AppLabelEconomics> lsE = this.getSysDao().getAppLabelGroupDao().findBySignEconomics(form.getId(),"4");
                                    if(lsE != null && lsE.size()>0){
                                        for(AppLabelEconomics p:lsE){//经济类型
//                                            if(!EconomicType.YBRK.getValue().equals(p.getLabelValue())){
//                                                flags = true;
//                                            }
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
                                    if(ls != null && ls.size() >0){
                                        for(AppLabelGroup p : ls){
                                            if(ResidentMangeType.PTRQ.getValue().equals(p.getLabelValue())){//普通人群
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if(ResidentMangeType.ETLZLS.getValue().equals(p.getLabelValue())){//儿童(0~6岁)
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{14}", "☑");//为0-6岁儿童进行一类疫苗接种
                                                content = content.replace("{15}", "☑");//为0-6岁儿童进行常规的体格检查
                                                content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }

                                            }
                                            if(ResidentMangeType.YCF.getValue().equals(p.getLabelValue())){//孕产妇
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{16}", "☑");//为孕产妇孕期进行基本保健指导，开展至少5次孕期保健服务和2次产后访视
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if(ResidentMangeType.LNR.getValue().equals(p.getLabelValue())){//老年人
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{17}", "☑");//为65岁以上老年人每年1次健康体检，包括血生化、血尿常规、心电图、B超等辅助检查
                                                content = content.replace("{20}", "☑");//为老年人提供中医药健康管理服务，同时在儿童不同年龄阶段对儿童家长进行儿童中医药健康指导
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if(ResidentMangeType.GXY.getValue().equals(p.getLabelValue())){//高血压
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if(ResidentMangeType.TNB.getValue().equals(p.getLabelValue())){//糖尿病
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{18}", "☑");//为原发性高血压、2型糖尿病等慢性病患者提供定期随访、用药指导，每年免费体检1次等服务
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }

                                            if(ResidentMangeType.YZJSZY.getValue().equals(p.getLabelValue())){//严重精神障碍
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{19}", "☑");//对居家重性精神疾病患者提供随访服务和每年健康体检1次
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if(ResidentMangeType.JHB.getValue().equals(p.getLabelValue())){//结核病
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{21}", "☑");//为结核病患者提供用药、饮食、运动、心理等健康指导
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if(ResidentMangeType.CJR.getValue().equals(p.getLabelValue())){//残疾人
                                                content = content.replace("{12}", "☑");//建立健康档案
                                                content = content.replace("{13}", "☑");//健康教育
                                                content = content.replace("{22}", "☑");//其它国家规定免费提供的基本公共卫生服务项目
                                                CdCode code = sysDao.getCodeDao().findCodeGroupValue(xyGroup,p.getLabelValue());
                                                if(code!=null){
                                                    ypxy += code.getCodeTitle();
                                                }
                                            }
                                            if(ResidentMangeType.WEIZHI.getValue().equals(p.getLabelValue())){//未知
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
//                                        if(flag){
//                                            CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY,"5");
//                                            if(code!=null){
//                                                ypxy += code.getCodeTitle();
//                                            }
//                                        }
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

                                        content = content.replace("{04}","");//页头
                                        content = content.replace("{06}","");//页未
                                        content = content.replace("{05}","");//页未
                                    }
//                                    if(flags){
//                                        CdCode code = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YPXY,"9");
//                                        if(code!=null){
//                                            ypxy += code.getCodeTitle();
//                                        }
//                                    }
                                    content = content.replace("{98}", ypxy);
                                    /*if(StringUtils.isNotBlank(ypxy)){
                                        AppHospDept hospDept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,form.getSignHospId());
                                        if(hospDept!=null){
                                            if(StringUtils.isNotBlank(hospDept.getHospAreaCode())){
                                                String code = AreaUtils.getAreaCode(hospDept.getHospAreaCode(),"3");
                                                if("3507".equals(code)){
                                                    content=content.replace("{98}",ypxy);
                                                }
                                            }
                                        }
                                    }*/
                                    if(StringUtils.isNotBlank(form.getUpHpis())){
                                        if(SignFormType.POSSTATE.getValue().equals(form.getUpHpis()) || SignFormType.YITIJISTATE.getValue().equals(form.getUpHpis())){
                                            List<AppSignSubtable> list = sysDao.getAppSignSubtableDao().findBySign(form.getId(),form.getUpHpis(),CommSF.YES.getValue());
                                            if(list!= null && list.size()>0){
                                                String result = "<image width='100' height='100' src='"+list.get(0).getImgUrl()+"'></image>";
                                                content = content.replace("{999}",result);//甲方签名
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
                                    if(StringUtils.isNotBlank(form.getSigntext())){ //不知道为什么这样写总觉得不太合理
                                        String text="";
                                        String[] a =form.getSigntext().split("\n");
                                        for(int i=0;i<a.length;i++){
                                            if(i==0){
                                                text="<p style=\"text-indent:32px;line-height:27px\">"+a[i]+"</p>";
                                            }else{
                                                text=text+"<p style=\"text-indent:32px;line-height:27px\">"+a[i]+"</p>";
                                            }
                                        }
                                        text = text.replace("null","");
                                        content=content.replace("{0}",text);//特色补充协议包
                                    }else{
                                        content=content.replace("{0}","");//特色补充协议包
                                    }
                                    content = content.replace("{888}","");//乙方签名
                                }else{
                                    content = content.replace("{97}","");
                                    content = content.replace("{999}","");//甲方签名
                                    content = content.replace("{888}","");//乙方签名
                                    content = content.replace("{04}","");//页头
                                    content = content.replace("{06}","");//页未
                                    content = content.replace("{05}","");//页未
                                    if(StringUtils.isNotBlank(qvo.getDrId())){
                                        AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                                        if(drUser != null){
                                            if(StringUtils.isNotBlank(drUser.getDrTel())){
                                                drTel = drUser.getDrTel();
                                            }else{
                                                drTel = "";
                                            }
                                            content = content.replace("{02}",drUser.getDrName());
                                            content = content.replace("{03}", drTel);
                                        }else {
                                            content = content.replace("{02}", "");
                                            content = content.replace("{02}", "");
                                        }
                                    }else{
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
                                    content = content.replace("{98}","");
                                }
                                if(StringUtils.isNotBlank(qvo.getWechatType())){
                                    content = content.replace("<div","<view");
                                    content = content.replace("</div","</view");
                                    content = content.replace("<p","<view");
                                    content = content.replace("</p","</view");
                                    content = content.replace("<span","<text");
                                    content = content.replace("</span","</text");
//                                    content = content.replace("</span","</text");
                                    content = content.replace("&nbsp;","");
//                                    <text decode="{{true}}" space="{{true}}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</text>

                                }
                                content = content.replace("{40}",ExtendDate.getChineseYMD(Calendar.getInstance()));
                                this.getAjson().setMsg(content);
                                if(org.apache.commons.lang3.StringUtils.isNotBlank(dept.getHospCachetAbroabUrl())){
                                    this.getAjson().setEntity(dept.getHospCachetAbroabUrl());
                                }
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
     * 根据设备类型查询设备
     *
     * @return
     */
    public String appFindDev() {
        try {
            AppDeviceVo qvo = (AppDeviceVo) getAppJson(AppDeviceVo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                List<AppDeviceEntity> devList = this.getSysDao().getAppDeviceDao().findByType(qvo);
                this.getAjson().setRows(devList);
                this.getAjson().setMsgCode("800");
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 服务人群接口 第二版
     *
     * @return
     */
    public String signPersGroupTwo() {
        try {
            List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
            this.getAjson().setRows(ls);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
    /**
     * 服务人群接口
     *
     * @return
     */
    public String signPersGroup() {
        try {
            List<CdCode> value = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_RESIDENTMANGE, CommonEnable.QIYONG.getValue());
            this.getAjson().setRows(value);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }



    /**
     * 查看健康教育具体内容
     *
     * @return
     */
    public String appLookHealth() {
        try {
            AppHealthQvo qvo = (AppHealthQvo) getAppJson(AppHealthQvo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                AppHealthEntiry vo = this.sysDao.getAppUserHealthEDDao().findByOne(qvo);
                this.getAjson().setVo(vo);
                this.getAjson().setMsgCode("800");
               /* AppHealthEntiry appHealth = this.sysDao.getNewsTableDao().findById(qvo.getId());
                this.getAjson().setVo(appHealth);
                this.getAjson().setMsgCode("800");*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 签约类型接口
     *
     * @return
     */
    public String signType() {
        try {
            List<CdCode> value = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HOUSESIGNING, CommonEnable.QIYONG.getValue());
            this.getAjson().setRows(value);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 疾病类型接口
     *
     * @return
     */
    public String labelGruops() {
        try {
            List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("2");
            this.getAjson().setRows(ls);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 健康情况接口
     *
     * @return
     */
    public String signHealthGroup() {
        try {
            List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("1");
            this.getAjson().setRows(ls);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 颜色接口
     *
     * @return
     */
    public String codeColor() {
        try {
            List<CdCode> value = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_COLOR, CommonEnable.QIYONG.getValue());
            this.getAjson().setRows(value);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 根据患者id或医生id查询指导list
     *
     * @return
     */
    public String findHealthGuide() {
        try {
            AppHealthGuideQvo qvo = (AppHealthGuideQvo) getAppJson(AppHealthGuideQvo.class);
            if (qvo == null) {
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            } else {
                if (StringUtils.isNotBlank(qvo.getPatientId()) || StringUtils.isNotBlank(qvo.getDrId())) {
                    List<AppHealthGuideEntity> ls = this.sysDao.getAppHealthGuideDao().findByPId(qvo);
                    this.getAjson().setRows(ls);
                    this.getAjson().setMsgCode("800");
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
     * 给患者发健康教育
     *
     * @return
     */
    public String saveHealthToUser() {
        try {
            AppHealthUserQvo qvo = (AppHealthUserQvo) getAppJson(AppHealthUserQvo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                String flag = this.sysDao.getAppUserHealthEDDao().save(qvo);
                if (flag.equals("true")) {
                    this.getAjson().setMsg("添加健康教育成功");
                    this.getAjson().setMsgCode("800");
                } else {
                    this.getAjson().setMsg("系统错误，请联系管理员");
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
     * 收藏健康教育
     *
     * @return
     */
    public String saveEnshrine() {
        try {
            AppEnshrineHealthQvo qvo = (AppEnshrineHealthQvo) getAppJson(AppEnshrineHealthQvo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                if (StringUtils.isNotBlank(qvo.getUserId()) && StringUtils.isNotBlank(qvo.getHealthId())) {
                    String flag = this.sysDao.getAppHealthEnshrineDao().saveEnshrine(qvo);
                    if (flag.equals("true")) {
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setMsg("收藏成功");
                    }
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数值不正确");
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
     * 干预类型接口
     *
     * @return
     */
    public String labelMeddle() {
        try {
            Map<String, Object> map = new HashMap<>();
            List<AppMeddleEntity> meddle = this.getSysDao().getCodeDao().findMeddle(CodeGroupConstrats.GROUP_MEDDLETYPE, CommonEnable.QIYONG.getValue());
            // List<AppLabelManage> ls=this.getSysDao().getServiceDo().loadByPk(AppLabelManage.class,"labelType","2");
            List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("2");
            map.put("meddle", meddle);
            map.put("illType", ls);
            this.getAjson().setMap(map);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 服务人群、健康情况、疾病类型
     *
     * @return
     */
    public String residentGroup() {
        try {
            Map<String, Object> map = new HashMap<>();
            //疾病类型
            List<AppLabelManage> illType = this.sysDao.getAppLabelManageDao().findByType("2");
            //健康情况
            List<AppLabelManage> healthCase = this.sysDao.getAppLabelManageDao().findByType("1");
            //服务人群
            List<CdCode> serveGroups = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_RESIDENTMANGE, CommonEnable.QIYONG.getValue());
            //签约类型
            List<CdCode> qyType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HOUSESIGNING, CommonEnable.QIYONG.getValue());
            map.put("healthCase", healthCase);
            map.put("illType", illType);
            map.put("serveGroups", serveGroups);
            map.put("contractType", qyType);
            this.getAjson().setMap(map);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 服务人群、健康情况、疾病类型
     *
     * @return
     */
    public String residentGroupTwo() {
        try {
            Map<String, Object> map = new HashMap<>();
            //疾病类型
            List<AppLabelManage> illType = this.sysDao.getAppLabelManageDao().findByType("2");
            //健康情况
            List<AppLabelManage> healthCase = this.sysDao.getAppLabelManageDao().findByType("1");
            //服务人群
            List<AppLabelManage> serveGroups = this.sysDao.getAppLabelManageDao().findByType("3");
            //服务人群
            List<AppLabelManage> serveEconomics = this.sysDao.getAppLabelManageDao().findByType("4");
            //签约类型
            List<CdCode> qyType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HOUSESIGNING, CommonEnable.QIYONG.getValue());
            map.put("healthCase", healthCase);
            map.put("illType", illType);
            map.put("serveGroups", serveGroups);
            map.put("contractType", qyType);
            map.put("serveEconomics", serveEconomics);
            this.getAjson().setMap(map);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 获取环信信息
     *
     * @return
     */
    public String getEaseInfo() {
        try {
            AppEaseVo qvo = (AppEaseVo) getAppJson(AppEaseVo.class);
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                if (StringUtils.isNotBlank(qvo.getEaseId())) {
                    SericuryUtil util = new SericuryUtil();
                    String id = util.decrypt(qvo.getEaseId());
                    AppEaseEntity entity = new AppEaseEntity();
                    AppPatientUser user = (AppPatientUser) this.getSysDao().getServiceDo().find(AppPatientUser.class, id);
                    if(user != null){
                        entity.setUserId(user.getId());
                        entity.setUserGender(user.getPatientGender());
                        entity.setUserImageUrl(user.getPatientImageurl());
                        entity.setUserName(user.getPatientName());
                        entity.setUserImageName(user.getPatientImageName());
                        entity.setUserType(CommonShortType.HUANGZHE.getValue());
                    }else{
                        AppDrUser drUser = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class, id);
                        if(drUser != null) {
                            entity.setUserId(drUser.getId());
                            entity.setUserImageName(drUser.getDrImageName());
                            entity.setUserName(drUser.getDrName());
                            entity.setUserImageUrl(drUser.getDrImageurl());
                            entity.setUserGender(drUser.getDrGender());
                            entity.setUserType(CommonShortType.YISHENG.getValue());
                        }
                    }
                    this.getAjson().setVo(entity);
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数值不正确");
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
     * 消息设置
     *
     * @return
     */
    public String appWarningSetting() {
        try {
            AppWarningSettingQvo vo = (AppWarningSettingQvo) getAppJson(AppWarningSettingQvo.class);
            if (vo != null) {
                if (CommonWarnSet.XYNZ.getValue().equals(vo.getType())) {
                    List<AppBloodpressure> list = sysDao.getAppBloodpressureDao().findByUserId(vo.getUserId());
                    if (list != null && list.size() > 0) {
                        AppBloodpressure bp = list.get(0);
                        if (vo.getClock1() != null) {
                            bp.setBpClockOne(vo.getClock1());
                        }
                        if (vo.getClock2()!= null) {
                            bp.setBpClockTwo(vo.getClock2());
                        }
                        if (vo.getClock3() != null) {
                            bp.setBpClockThr(vo.getClock3());
                        }
                        if (vo.getClock4() != null) {
                            bp.setBpClockFour(vo.getClock4());
                        }
                        sysDao.getServiceDo().modify(bp);
                        String imei = bp.getBpDevId();
                        this.sysDao.getSecurityCardAsyncBean().pressureClock(bp.getBpDevId(), bp.getBpClockOne(), bp.getBpClockTwo(), bp.getBpClockThr(), bp.getBpClockFour());
                        this.getAjson().setVo(vo);
                        this.getAjson().setMsgCode("800");
                    } else {
                        this.getAjson().setMsg("未找到绑定设备");
                        this.getAjson().setMsgCode("900");
                    }
                }else {
                    List<AppWarningSetting> list = sysDao.getAppWarningSettingDao().findSetting(vo.getUserId(), vo.getType());
                    if (list.isEmpty()) {
                        AppWarningSetting set = new AppWarningSetting();
                        set.setWsUserId(vo.getUserId());
                        set.setWsType(vo.getType());
                        set.setWsState(vo.getState());
                        if (StringUtils.isNotBlank(vo.getNum())) {
                            set.setWsNum(vo.getNum());
                        }
                        sysDao.getServiceDo().add(set);
                        this.getAjson().setVo(vo);
                    } else {
                        AppWarningSetting set = list.get(0);
                        set.setWsUserId(vo.getUserId());
                        set.setWsState(vo.getState());
                        set.setWsType(vo.getType());
                        if (StringUtils.isNotBlank(vo.getNum())) {
                            set.setWsNum(vo.getNum());
                        }
                        sysDao.getServiceDo().modify(set);
                        this.getAjson().setVo(vo);
                    }
                    this.getAjson().setMsgCode("800");
                }
            } else {
                this.getAjson().setMsg("参数错误");
                this.getAjson().setMsgCode("900");
            }


        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 查询消息设置
     *
     * @param userId
     * @return
     */
    public String appFindSetting() {
        try {
            AppWarningSettingQvo vo = (AppWarningSettingQvo) getAppJson(AppWarningSettingQvo.class);
            if (vo.getUserId() != null) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                List<AppWarningSetting> setlist = sysDao.getServiceDo().loadByPk(AppWarningSetting.class, "wsUserId", vo.getUserId());
                if (setlist != null && setlist.size() > 0) {
                    map.put("setList", setlist);
                }
                List<AppBloodpressure> bpList = sysDao.getAppBloodpressureDao().findByUserId(vo.getUserId());
                if (bpList != null && bpList.size() > 0) {
                    map.put("pressure", bpList.get(0));
                }
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsg("参数错误");
                this.getAjson().setMsgCode("900");
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }


    /**
     * 好药店登录接口
     * @return
     */
    public String appEgodrugOpenLogin(){
        try {
            AppPatientUser user = this.getAppPatientUser();
            if(user != null){
                String url=Base.HEAD_URL_SERVER + "/api/user/async";
                String content = "";
                Map<String,String> testParam=new HashMap<String, String>();
                testParam.put("openId",user.getId());
                testParam.put("identity",user.getPatientIdno());
                testParam.put("username",user.getPatientTel());
                testParam.put("name",user.getPatientName());
                testParam.put("ssid",user.getPatientCard());
                content= JSON.toJSONString(testParam);
                String result = Base.excute(url,content);
                EgodrugEntity entity = JsonUtil.fromJson(result,EgodrugEntity.class);
                this.getAjson().setVo(entity);
            }else {
                this.getAjson().setMsg("查无该用户数据！");
                this.getAjson().setMsgCode("900");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 查询健康教育具体内容
     * @return
     */
    public String findHEDContent(){
        try{
            AppHealthQvo qvo = (AppHealthQvo) getAppJson(AppHealthQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppHealthEntiry vo = this.sysDao.getAppUserHealthEDDao().findByOne(qvo);
                this.getAjson().setVo(vo);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询健康指导具体内容
     * @return
     */
    public String findHGContent(){
        try{
            AppEnshrineHealthQvo qvo = (AppEnshrineHealthQvo)this.getAppJson(AppEnshrineHealthQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrMeddleEntity vo = this.sysDao.getAppHealthMeddleDao().findById(qvo.getHealthId());
                this.getAjson().setVo(vo);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 登录失效后根据主键重新获取用户token
     * @return
     */
    public String getAppCommonAjson(){
        try{
            AppUserTokenQvo qvo = (AppUserTokenQvo)this.getAppJson(AppUserTokenQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isNotBlank(qvo.getCommonId())){
                    AppUserTokenEntity v = new AppUserTokenEntity();
                    AppPatientUser patientUser = this.getSysDao().getAppPatientUserDao().findByUserId(qvo.getCommonId());
                    if(patientUser != null){
                        if(StringUtils.isNotBlank(qvo.getType())){
                            if(qvo.getType().equals("1") || qvo.getType().equals("4")){
                                AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findByUser(patientUser.getId(),null,CommonLoginType.JMZJ.getValue());
                                v.setPatientuser(patient);
                            }
                        }
                        Md5Util util = new Md5Util();
                        String key = patientUser.getPatientIdno()+ ExtendDate.getYMD_h_m_s(Calendar.getInstance());
                        key = util.MD516(key);
                        AppDrPatientKey uKey = this.sysDao.getAppDrPatientKeyDao().findByDoctorOrPatientId(patientUser.getId());
                        String day = ExtendDateUtil.addDate(ExtendDate.getYMD(Calendar.getInstance()), ExtendDateType.DAYS.getValue(),yxDay);
                        if(uKey != null){
                            if(qvo.getType().equals("1")){
                                uKey.setDrToken(key);
                            }else if(qvo.getType().equals("4")){
                                uKey.setDrTvToken(key);
                            }
                            uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                            uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
                            uKey.setDrPatientLastDate(uKey.getHsUpdateTime());
                            this.sysDao.getServiceDo().modify(uKey);
                        }else{
                            uKey = new AppDrPatientKey();
                            if(qvo.getType().equals("1")){
                                uKey.setDrToken(key);
                            }else if(qvo.getType().equals("4")){
                                uKey.setDrTvToken(key);
                            }
                            uKey.setDrPatientId(patientUser.getId());
                            uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                            uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
                            uKey.setDrPatientLastDate(Calendar.getInstance());
                            this.getSysDao().getServiceDo().add(uKey);
                        }
                        v.setCommonEntity(key);
                    }else{
                        AppDrUser drUser = this.getSysDao().getAppDrUserDao().findByUserId(qvo.getCommonId());
                        if(drUser != null){
                            if(StringUtils.isNotBlank(qvo.getType())){
                                if(qvo.getType().equals("3")){
                                    List<AppDrUserPossEntity> ls = sysDao.getAppDrUserDao().findByUserPoss(drUser.getId(),null,CommonLoginType.YSZJ.getValue());
                                    v.setPoss(ls.get(0));
                                }else if(qvo.getType().equals("2")){
                                    List<AppDrUserEntity> ls = sysDao.getAppDrUserDao().findByUser(drUser.getId(),null,CommonLoginType.YSZJ.getValue());
                                    v.setDruser(ls.get(0));
                                }
                            }
                            Md5Util util = new Md5Util();
                            String key = drUser.getDrTel()+ ExtendDate.getYMD_h_m_s(Calendar.getInstance());
                            key = util.MD516(key);
                            AppDrPatientKey uKey = this.sysDao.getAppDrPatientKeyDao().findByDoctorOrPatientId(drUser.getId());
                            String day = ExtendDateUtil.addDate(ExtendDate.getYMD(Calendar.getInstance()), ExtendDateType.DAYS.getValue(),yxDay);
                            if(uKey != null){
                                uKey.setDrToken(key);
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.yisheng.getValue());
                                uKey.setDrPatientLastDate(uKey.getHsUpdateTime());
                                this.sysDao.getServiceDo().modify(uKey);
                            }else{
                                uKey = new AppDrPatientKey();
                                uKey.setDrToken(key);
                                uKey.setDrPatientId(drUser.getId());
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.yisheng.getValue());
                                uKey.setDrPatientLastDate(Calendar.getInstance());
                                this.getSysDao().getServiceDo().add(uKey);
                            }
                            v.setCommonEntity(key);
                        }
                    }
                    this.getAjson().setVo(v);
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
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
     * 用户退出
     * @return
     */
    public String getAppUserExit(){
        try{
            AppUserTokenQvo qvo = (AppUserTokenQvo)this.getAppJson(AppUserTokenQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isNotBlank(qvo.getCommonUserId())){
                    AppPatientUser patientUser = this.getSysDao().getAppPatientUserDao().findByUserId(qvo.getCommonId());
                    if(patientUser != null){
                        patientUser.setPatientJgState(UserJgType.WEISHEZHI.getValue());
                        this.getSysDao().getServiceDo().modify(patientUser);
                    }else{
                        AppDrUser drUser = this.getSysDao().getAppDrUserDao().findByUserId(qvo.getCommonId());
                        if(drUser != null){
                            drUser.setDrJgState(UserJgType.WEISHEZHI.getValue());
                            this.getSysDao().getServiceDo().modify(drUser);
                        }
                    }
                    this.getAjson().setMsg("退出成功!");
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
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
     * 签约管理设置居民服务类型、服务包、经济类型
     * @return
     */
    public String findSignServe(){
        try{
            AppDrUser drUser = this.getAppDrUser();
            //居民服务
            List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
            //服务包
            List<AppMeddleEntity> serveGroups = this.getSysDao().getCodeDao().findMeddle(CodeGroupConstrats.GROUP_SERVICETYPE, CommonEnable.QIYONG.getValue());
            //经济类型
            List<AppMeddleEntity> jjType = this.getSysDao().getAppLabelManageDao().findByMeddle(LabelManageType.JJLX.getValue());
            //工作类型
            List<AppMeddleEntity> workType = this.getSysDao().getCodeDao().findMeddle(CodeGroupConstrats.GROUP_WORKTYPE, CommonEnable.QIYONG.getValue());
            //政府费用补贴
            List<AppMeddleEntity> zfbt = this.getSysDao().getCodeDao().findMeddle(CodeGroupConstrats.GROUP_PAYTYPE, CommonEnable.QIYONG.getValue());
            if(drUser!=null){
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept.getHospAreaCode()!=null){
                    List<AppSignSetting> vo = this.sysDao.getServiceDo().loadByPk(AppSignSetting.class,"signsAreaCode",dept.getHospAreaCode().substring(0,4));
                    if(vo!=null&&vo.size()>0){
                        this.getAjson().setVo(vo.get(0));
                    }
                }
            }

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("jmfw",ls);
            map.put("fwb",serveGroups);
            map.put("jjlx",jjType);
            map.put("gzlx",workType);
            map.put("zfbt",zfbt);
            this.getAjson().setMap(map);
            this.getAjson().setMsgCode("800");
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 查询省市区街道医生
     *
     * @return
     */
    public String appAddressResultDoctor() {
        try {
            AppAddressQvo qvo = (AppAddressQvo) this.getAppJson(AppAddressQvo.class);
            if (qvo != null) {
                if(StringUtils.isNotBlank(qvo.getUpId())){
                    CdAddress entity = this.getSysDao().getCdAddressDao().findByCacheCode(qvo.getUpId());
                    if(entity != null){
                        if(entity.getLevel().equals("1")){
                            List<AddressHospEntity> ls = this.getSysDao().getCdAddressDao().findByUpIdOrNotTs(qvo.getUpId());
                            this.getAjson().setRows(ls);
                        }else if(entity.getLevel().equals("2")){
                            List<AddressHospEntity> ls = this.getSysDao().getCdAddressDao().findByUpIdOrNotTs(qvo.getUpId());
                            this.getAjson().setRows(ls);
                        }else if (entity.getLevel().equals("3")){
                            List<AddressHospEntity> ls = this.getSysDao().getAppHospDeptDao().findByAreaTsId(qvo.getUpId());
                            this.getAjson().setRows(ls);
                        }
                    }else{
                        List<AddressHospEntity> ls = this.getSysDao().getAppDrUserDao().findByHospIdNotTs(qvo.getUpId());
                        this.getAjson().setRows(ls);
                    }
                }else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
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
     * 更新我的服务数据
     * @return
     */
    public String getMyServiceUpdate(){
        try{
            AppMenuRoleQvo qvo = (AppMenuRoleQvo)this.getAppJson(AppMenuRoleQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                this.getSysDao().getAppMyServiceMenuDao().addMySerViceMenu(qvo.getDrPaiteintId(),qvo.getMenuId());
                List<AppMyServiceEntity> ls = this.getSysDao().getAppMyServiceMenuDao().findMenuRole(qvo);
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 系统更新接口
     * @return
     */
    public String getSystemUpdate(){
        try{
            AppSystemVsersionQvo qvo = (AppSystemVsersionQvo)this.getAppJson(AppSystemVsersionQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppSystemVersionEntity vo = this.getSysDao().getAppSystemVsersionDao().findSystemVersion(qvo);
                if(vo != null){
                    if(StringUtils.isNotBlank(vo.getVsersionCode()) && StringUtils.isNotBlank(qvo.getVersion())){
                        if(Integer.parseInt(qvo.getVersion()) < Integer.parseInt(vo.getVsersionCode())){
                            vo.setSystemUpdate("1");
                        }else{
                            vo.setSystemUpdate("2");
                        }
                    }
                    this.getAjson().setVo(vo);
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
     * 基卫提醒接口
     * @return
     */
    public String getWarnNotice(){
        try{
            AppWarnNoticeQvo qvo = (AppWarnNoticeQvo)this.getAppJson(AppWarnNoticeQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isNotBlank(qvo.getReceiveArea())){
                    String result = null;
                    if (qvo.getReceiveArea().equals(AddressType.FZ.getValue())) {

                    } else if (qvo.getReceiveArea().equals(AddressType.QZ.getValue())) {
                        result = "qz_";
                    } else if (qvo.getReceiveArea().equals(AddressType.ZZ.getValue())) {
                        result = "zz_";
                    } else if (qvo.getReceiveArea().equals(AddressType.PT.getValue())) {
                        result = "pt_";
                    } else if (qvo.getReceiveArea().equals(AddressType.NP.getValue())) {
                        result = "np_";
                    } else if (qvo.getReceiveArea().equals(AddressType.SM.getValue())) {
                        result = "sm_";
                    }
                    if(StringUtils.isNotBlank(result)){
                        qvo.setReceivePeople(result+qvo.getReceivePeople());
                    }
                    this.getSysDao().getAppNoticeDao().addNotices(qvo.getReceiveTitle(),qvo.getReceiveContent(),
                            NoticesType.GZJHTX.getValue(),"wjgly",qvo.getReceivePeople(),null,DrPatientType.DR.getValue());
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("添加成功!");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("接收地区不能为空");
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
     * 患者调度
     */
    public String hzScheduling(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
                sysDao.getSecurityCardAsyncBean().patientScheduling(user.getId());
            }
            this.getAjson().setMsgCode("800");
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }

        return "ajson";
    }

    /**
     * 医生调度
     */

    public String ysScheduling(){
        try{
            AppDrUser drUser = this.getAppDrUser();
            if(drUser!=null){
                sysDao.getSecurityCardAsyncBean().ysScheduling(drUser);
            }
            this.getAjson().setMsgCode("800");
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 履约数据
     * @return
     */
    public String appPerformanceData(){
        try{
            PerformanceDataQvo qvo = (PerformanceDataQvo)this.getAppJson(PerformanceDataQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isNotBlank(qvo.getPerArea())){
                    if(StringUtils.isNotBlank(qvo.getPerType())){
//                        String result = null;
//                        if (qvo.getPerArea().equals(AddressType.FZ.getValue())) {
//
//                        } else if (qvo.getPerArea().equals(AddressType.QZ.getValue())) {
//                            result = "qz_";
//                        } else if (qvo.getPerArea().equals(AddressType.ZZ.getValue())) {
//                            result = "zz_";
//                        } else if (qvo.getPerArea().equals(AddressType.PT.getValue())) {
//                            result = "pt_";
//                        } else if (qvo.getPerArea().equals(AddressType.NP.getValue())) {
//                            result = "np_";
//                        } else if (qvo.getPerArea().equals(AddressType.SM.getValue())) {
//                            result = "sm_";
//                        }
//                        if(StringUtils.isNotBlank(result)){
//                            qvo.setDrId(result+qvo.getDrId());
//                            qvo.setHospId(result+qvo.getHospId());
//                        }
                        AppSignForm form = sysDao.getAppSignformDao().findFormByJmda(qvo.getPerIdno());
                        if(form!=null){
                            qvo.setTeamId(form.getSignTeamId());
                            String sermeal = form.getSignpackageid();
                            String fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                            sysDao.getAppPerformanceTableDao().addPerformanceOne(qvo,sermeal,fwType,qvo.getPerType());
                            this.getAjson().setMsgCode("800");
                            this.getAjson().setMsg("添加成功!");
                        }
//                        this.getSysDao().getAppPerformanceStatisticsDao().addPerformanceData(qvo);
//                        this.getAjson().setMsgCode("800");
//                        this.getAjson().setMsg("添加成功!");
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("接收类型不能为空");
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("接收地区不能为空");
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
     * 履约钻取数据
     * @return
     */
    public String findPerList(){
        try{
            AppCommLyQvo qvo = (AppCommLyQvo)this.getAppJson(AppCommLyQvo.class);
			System.out.println("===== 开始履约钻取数据 " + ExtendDate.getYMD_h_m_s(Calendar.getInstance()) + " =====");
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
				System.out.println("查询数据:" + JsonUtil.toJson(qvo) + "");
                AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(qvo.getDrId());
                if(drUser!=null){
                    List<AppPerformanceRecordEntity> list = new ArrayList<>();
                    if(PerType.JKJY.getValue().equals(qvo.getFwType())){//健康教育
						list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.JKJY.getValue());
						// list = sysDao.getSchedulingAsyncBean().healthEdution(drUser,"","");
                    }else if(PerType.JKZD.getValue().equals(qvo.getFwType())){//健康指导
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.JKZD.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().healthGuide(drUser,"","");
                    }else if(PerType.XSEFS.getValue().equals(qvo.getFwType())){//新生儿家庭访视
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.XSEFS.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().newChildVisit(drUser,"","");
                    }else if(PerType.ETJKTJ.getValue().equals(qvo.getFwType())){//0-6岁儿童健康检查
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.ETJKTJ.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().childHealth(drUser,"","");
                    }else if(PerType.ETZYYJKZD.getValue().equals(qvo.getFwType())){//中医药健康指导
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.ETZYYJKZD.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().tcmHealthGuide(drUser,"","");
                    }else if(PerType.YQBJFW.getValue().equals(qvo.getFwType())){//孕产妇保健
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.YQBJFW.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().prenatalCare(drUser,"","");
                    }else if(PerType.CHFS.getValue().equals(qvo.getFwType())){//产后访视
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.CHFS.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().postpartumVisit(drUser,"","");
                    }else if(PerType.CHJKJC.getValue().equals(qvo.getFwType())){//产后42天健康检查记录
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.CHJKJC.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().postpartum(drUser,"","");
                    }else if(PerType.JKTJ.getValue().equals(qvo.getFwType())){//健康体检
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.JKTJ.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().healthyCheckUp(drUser,"","");
                    }else if(PerType.YYZD.getValue().equals(qvo.getFwType())){//用药指导
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.YYZD.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().guidelines(drUser,"","");
                    }else if(PerType.GXYSF.getValue().equals(qvo.getFwType())){//高血压定期随访
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.GXYSF.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().HighBloodPressure(drUser,"","");
                    }else if(PerType.TNBSF.getValue().equals(qvo.getFwType())){//糖尿病定期随访
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.TNBSF.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().diabetes(drUser,"","");
                    }else if(PerType.ZXJSBSF.getValue().equals(qvo.getFwType())){//重性精神疾病定期随访
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.ZXJSBSF.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().MentalIllness(drUser,"","");
                    }else if(PerType.JHBSF.getValue().equals(qvo.getFwType())){//肺结核病定期随访
                    	list = sysDao.getSchedulingAsyncBean().findUnfinishedPerListByServerSetMeal(drUser.getId(), PerType.JHBSF.getValue());
                    	// list = sysDao.getSchedulingAsyncBean().tuberculosis(drUser,"","");
                    }
                    
                    Map<String,Object> returnMap = new HashMap<>();
                    List<AppPerformanceRecordEntity> listPt = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listEt = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listYcf = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listLr = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listGxy = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listTnb = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listJsb = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listJhb = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listCjr = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listWz = new ArrayList<>();
                    int num = 0;
                    if(list != null && list.size()>0){
                        for(AppPerformanceRecordEntity ll:list){
                            num++;
                            if(StringUtils.isNotBlank(ll.getFwType())){//服务人群
                                String[] strs = ll.getFwType().split(",");
                                for(String str:strs){
                                    if(ResidentMangeType.PTRQ.getValue().equals(str)){
                                        listPt.add(ll);
                                    }
                                    if(ResidentMangeType.ETLZLS.getValue().equals(str)){
                                        listEt.add(ll);
                                    }
                                    if(ResidentMangeType.YCF.getValue().equals(str)){
                                        listYcf.add(ll);
                                    }
                                    if(ResidentMangeType.LNR.getValue().equals(str)){
                                        listLr.add(ll);
                                    }
                                    if(ResidentMangeType.GXY.getValue().equals(str)){
                                        listGxy.add(ll);
                                    }
                                    if(ResidentMangeType.TNB.getValue().equals(str)){
                                        listTnb.add(ll);
                                    }
                                    if(ResidentMangeType.YZJSZY.getValue().equals(str)){
                                        listJsb.add(ll);
                                    }
                                    if(ResidentMangeType.JHB.getValue().equals(str)){
                                        listJhb.add(ll);
                                    }
                                    if(ResidentMangeType.CJR.getValue().equals(str)){
                                        listCjr.add(ll);
                                    }
                                    if(ResidentMangeType.WEIZHI.getValue().equals(str)){
                                        listWz.add(ll);
                                    }
                                }
                            }
                        }
                        returnMap.put("listPt",listPt);
                        returnMap.put("listEt",listEt);
                        returnMap.put("listYcf",listYcf);
                        returnMap.put("listLr",listLr);
                        returnMap.put("listGxy",listGxy);
                        returnMap.put("listTnb",listTnb);
                        returnMap.put("listJsb",listJsb);
                        returnMap.put("listJhb",listJhb);
                        returnMap.put("listCjr",listCjr);
                        returnMap.put("listWz",listWz);
                    }
                    this.getAjson().setEntity(String.valueOf(num));
                    this.getAjson().setMap(returnMap);
                    this.getAjson().setMsgCode("800");
                }
            }
			System.out.println("===== 结束履约钻取数据 " + ExtendDate.getYMD_h_m_s(Calendar.getInstance()) + " =====");
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }



    /**
     * 履约钻取数据
     * @return
     */
    public String findPerListWeb(){
        try{
            AppCommLyQvo qvo = (AppCommLyQvo)this.getQvoJson(AppCommLyQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(qvo.getDrId());
                if(drUser!=null){
                    List<AppPerformanceRecordEntity> list = new ArrayList<>();
                    if(PerType.JKJY.getValue().equals(qvo.getFwType())){//健康教育
                        list = sysDao.getSchedulingAsyncBean().healthEdution(drUser,"","");
                    }else if(PerType.JKZD.getValue().equals(qvo.getFwType())){//健康指导
                        list = sysDao.getSchedulingAsyncBean().healthGuide(drUser,"","");
                    }else if(PerType.XSEFS.getValue().equals(qvo.getFwType())){//新生儿家庭访视
                        list = sysDao.getSchedulingAsyncBean().newChildVisit(drUser,"","");
                    }else if(PerType.ETJKTJ.getValue().equals(qvo.getFwType())){//0-6岁儿童健康检查
                        list = sysDao.getSchedulingAsyncBean().childHealth(drUser,"","");
                    }else if(PerType.ETZYYJKZD.getValue().equals(qvo.getFwType())){//中医药健康指导
                        list = sysDao.getSchedulingAsyncBean().tcmHealthGuide(drUser,"","");
                    }else if(PerType.YQBJFW.getValue().equals(qvo.getFwType())){//孕产妇保健
                        list = sysDao.getSchedulingAsyncBean().prenatalCare(drUser,"","");
                    }else if(PerType.CHFS.getValue().equals(qvo.getFwType())){//产后访视
                        list = sysDao.getSchedulingAsyncBean().postpartumVisit(drUser,"","");
                    }else if(PerType.CHJKJC.getValue().equals(qvo.getFwType())){//产后42天健康检查记录
                        list = sysDao.getSchedulingAsyncBean().postpartum(drUser,"","");
                    }else if(PerType.JKTJ.getValue().equals(qvo.getFwType())){//健康体检
                        list = sysDao.getSchedulingAsyncBean().healthyCheckUp(drUser,"","");
                    }else if(PerType.YYZD.getValue().equals(qvo.getFwType())){//用药指导
                        list = sysDao.getSchedulingAsyncBean().guidelines(drUser,"","");
                    }else if(PerType.GXYSF.getValue().equals(qvo.getFwType())){//高血压定期随访
                        list = sysDao.getSchedulingAsyncBean().HighBloodPressure(drUser,"","");
                    }else if(PerType.TNBSF.getValue().equals(qvo.getFwType())){//糖尿病定期随访
                        list = sysDao.getSchedulingAsyncBean().diabetes(drUser,"","");
                    }else if(PerType.ZXJSBSF.getValue().equals(qvo.getFwType())){//重性精神疾病定期随访
                        list = sysDao.getSchedulingAsyncBean().MentalIllness(drUser,"","");
                    }else if(PerType.JHBSF.getValue().equals(qvo.getFwType())){//肺结核病定期随访
                        list = sysDao.getSchedulingAsyncBean().tuberculosis(drUser,"","");
                    }
                    Map<String,Object> returnMap = new HashMap<>();
                    List<AppPerformanceRecordEntity> listPt = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listEt = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listYcf = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listLr = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listGxy = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listTnb = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listJsb = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listJhb = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listCjr = new ArrayList<>();
                    List<AppPerformanceRecordEntity> listWz = new ArrayList<>();
                    int num = 0;
                    if(list != null && list.size()>0){
                        for(AppPerformanceRecordEntity ll:list){
                            num++;
                            if(StringUtils.isNotBlank(ll.getFwType())){//服务人群
                                String[] strs = ll.getFwType().split(",");
                                for(String str:strs){
                                    if(ResidentMangeType.PTRQ.getValue().equals(str)){
                                        listPt.add(ll);
                                    }
                                    if(ResidentMangeType.ETLZLS.getValue().equals(str)){
                                        listEt.add(ll);
                                    }
                                    if(ResidentMangeType.YCF.getValue().equals(str)){
                                        listYcf.add(ll);
                                    }
                                    if(ResidentMangeType.LNR.getValue().equals(str)){
                                        listLr.add(ll);
                                    }
                                    if(ResidentMangeType.GXY.getValue().equals(str)){
                                        listGxy.add(ll);
                                    }
                                    if(ResidentMangeType.TNB.getValue().equals(str)){
                                        listTnb.add(ll);
                                    }
                                    if(ResidentMangeType.YZJSZY.getValue().equals(str)){
                                        listJsb.add(ll);
                                    }
                                    if(ResidentMangeType.JHB.getValue().equals(str)){
                                        listJhb.add(ll);
                                    }
                                    if(ResidentMangeType.CJR.getValue().equals(str)){
                                        listCjr.add(ll);
                                    }
                                    if(ResidentMangeType.WEIZHI.getValue().equals(str)){
                                        listWz.add(ll);
                                    }
                                }
                            }
                        }
                        returnMap.put("listPt",listPt);
                        returnMap.put("listEt",listEt);
                        returnMap.put("listYcf",listYcf);
                        returnMap.put("listLr",listLr);
                        returnMap.put("listGxy",listGxy);
                        returnMap.put("listTnb",listTnb);
                        returnMap.put("listJsb",listJsb);
                        returnMap.put("listJhb",listJhb);
                        returnMap.put("listCjr",listCjr);
                        returnMap.put("listWz",listWz);
                    }
                    this.getAjson().setEntity(String.valueOf(num));
                    this.getAjson().setMap(returnMap);
                    this.getAjson().setMsgCode("800");
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
     * 统计初始化功能
     *
     * @return
     */
    public String appManageInitialize() {
        try {
            AppCommQvo qvo = (AppCommQvo) this.getVoJson(AppCommQvo.class);
            if(qvo != null){
                if(StringUtils.isNotBlank(qvo.getSignDate())){
                    List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findByManageCont();
                    List<String> ls = new ArrayList<String>();
                    String startDate = qvo.getSignDate();
                    String endDate = ExtendDate.getYMD(Calendar.getInstance());
                    ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
                    if(ls != null && ls.size() >0){
                        for(String s : ls){
                            sysDao.getSecurityCardAsyncBean().totalManageCount(s,lsTeam);
                        }
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("signDate参数不能为空!");
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
     * 初始化多条经济性质数据
     * @return
     */
    public String appLabelEconomicsInitialize() {
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = " SELECT c.ID,c.SIGN_TEAM_ID,c.SIGN_AREA_CODE,t.RKPKG FROM app_sign_form c INNER JOIN WEB_SIGN_TEMP t on c.SIGN_PATIENT_IDNO = t.PT_ID_NO where t.RKPKG IS NOT NULL AND t.RKPKG LIKE '%,%'";
            List<Map> ls = sysDao.getServiceDo().findSqlMap(sql,map);
            if(ls != null && ls.size() >0){
                for(Map maps : ls){
                    String signsJjType = maps.get("RKPKG").toString();
                    String signId = maps.get("ID").toString();
                    String signTeamId = maps.get("SIGN_TEAM_ID").toString();
                    String signAreaCode = maps.get("SIGN_AREA_CODE").toString();
                    if(StringUtils.isNotBlank(signsJjType)){
                        String[] groups = signsJjType.split(",");
                        if (groups != null && groups.length > 0) {
                            sysDao.getAppLabelGroupDao().addLabel(groups,signId,signTeamId,signAreaCode, LabelManageType.JJLX.getValue());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }
    /**
     * 初始化糖尿病与残疾人数据 (针对莆田旧数据)
     * @return
     */
    public String appLabelGroupsInitialize() {
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = " SELECT * FROM app_sign_form where SIGN_JJ_TYPE IS NOT NULL AND ( SIGN_JJ_TYPE LIKE '%6%' OR SIGN_JJ_TYPE LIKE '%9%' ) AND SIGN_STATE IN ('0','2') ";
            List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
            if(ls != null && ls.size() >0){
                for(AppSignForm sign : ls){
                    String signsJjType = sign.getSignsJjType();
                    if(StringUtils.isNotBlank(signsJjType)){
                        String[] groups = signsJjType.split(",");
                        if (groups != null && groups.length > 0) {
                            sysDao.getAppLabelGroupDao().addLabel(groups,sign.getId(),sign.getSignTeamId(),sign.getSignAreaCode(), LabelManageType.FWRQ.getValue());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 查询医院科室
     * @return
     */
    public String findHospDept(){
        try{
            AppFindDeptQvo qvo = (AppFindDeptQvo)this.getAppJson(AppFindDeptQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppFindDeptEntity> list = sysDao.getAppHospitalDepartmentsDao().findDeptList(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setRows(list);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 点击履约通知提醒查询人员列表
     * @return
     */
    public String findLyPeopleList(){
        try{
            AppPeopleTypeQvo qvo = (AppPeopleTypeQvo)this.getAppJson(AppPeopleTypeQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(PerType.JKJY.getValue().equals(qvo.getPerType())|| PerType.JKZD.getValue().equals(qvo.getPerType())){
                    Map<String,Object> map = new HashMap<String,Object>();
                    List<AppPeopleHealthEntity> lsCount = sysDao.getAppPatientUserDao().findLyCount(qvo);
                    List<AppComLyPeopleEntity> ls =sysDao.getAppPatientUserDao().findByLyType(qvo);
                    map.put("count",lsCount);
                    this.getAjson().setRows(ls);
                    this.getAjson().setMap(map);
                }else{
                    //姓名，性别，出生日期，疾病类型,联系电话
                    List<AppLyPatientEntity> list = sysDao.getAppPatientUserDao().findLyPatient(qvo);
                    this.getAjson().setRows(list);
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
     * 根据签约批次表id查询签约单列表
     * @return
     */
    public String findByBatchId(){
        try{
            AppCommQvo qvo = (AppCommQvo)this.getAppJson(AppCommQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppSignFormListEntity> list = sysDao.getAppSignformDao().findByBatchId(qvo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 发送履约通知
     * @return
     */
    public String performanceNotice(){
        try{
            AppLyQvo qvo = (AppLyQvo)this.getAppJson(AppLyQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                String flag = sysDao.getAppPerFormanceDao().performanceNotice(qvo);
                if(!flag.equals("true")){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg(flag);
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
     * cjw
     */
    public void updatelabel()throws  Exception
    {
        Map<String,Object> map = new HashMap<String,Object>();
        String sqlCount = " SELECT count(1) FROM APP_SIGN_FORM  f INNER JOIN WEB_SIGN_TEMP t on f.SIGN_PATIENT_IDNO = t.PT_ID_NO  ";
        int allCount = sysDao.getServiceDo().findCount(sqlCount,map);
        allPage  =(allCount+500-1)/500;
        if(allPage != 0){
            for (int i = 0; i < allPage; i++) {
                page++;
                sysDao.getSecurityCardAsyncBean().InLabelGrooup(page);
            }
        }
    }


    /**
     *  cjw
     *  初始化服务人群
     *  建一张临时表 web_sign_temp
     */
    public String InLabelGrooup()
    {
        try{

            Map<String,Object> map = new HashMap<String,Object>();
            String sql = " SELECT f.ID ,f.SIGN_TEAM_ID ,t.SVS_PKG ,f.SIGN_AREA_CODE FROM APP_SIGN_FORM  f INNER JOIN WEB_SIGN_TEMP t on f.SIGN_PATIENT_IDNO = t.PT_ID_NO  ";
            List<AppSignFormEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSignFormEntity.class);
            if(ls != null && ls.size() >0){
                for(AppSignFormEntity sign : ls){
                    String signPersGroup = sign.getSignPersGroup();
                    if(StringUtils.isNotBlank(signPersGroup)){
                        String[] groups = signPersGroup.split(",");
                        if (groups != null && groups.length > 0) {
                            for(String s : groups){
                                String result = s;
                                if(s.equals("99")){
                                    result = "9";
                                }else if(s.equals("9")){
                                    result = "6";
                                }
                                String gsql = " SELECT *  from app_label_group g where g.LABEL_TYPE = '3' and  g.LABEL_SIGN_ID =:signId  and g.LABEL_VALUE =:value  ";
                                map.put("signId",sign.getId());
                                map.put("value",result);
                                List<AppLabelGroup> gls = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelGroup.class);
                                if(gls != null && gls.size()>0){

                                }else{
                                    AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("3",result);
                                    if (manage != null) {
                                        AppLabelGroup alg = new AppLabelGroup();
                                        alg.setLabelId(manage.getId());
                                        alg.setLabelSignId(sign.getId());
                                        alg.setLabelTeamId(sign.getSignTeamId());
                                        alg.setLabelTitle(manage.getLabelTitle());
                                        alg.setLabelValue(manage.getLabelValue());
                                        alg.setLabelType(manage.getLabelType());
                                        alg.setLabelAreaCode(sign.getSignAreaCode());
                                        sysDao.getServiceDo().add(alg);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }

        return "ajson";
    }

    /**
     * 查询患者提交的健康档案
     * @return
     */
    public String findFile(){
        try{
            AppCommQvo qvo = (AppCommQvo)this.getAppJson(AppCommQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if(user!=null){
                    userType = "1";
                    qvo.setPatientId(user.getId());
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser!=null){
                        userType = "2";
                        qvo.setDrId(drUser.getId());
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                List<AppHealthFile> files = sysDao.getAppHealthFileDao().findFile(qvo);
                if(files!=null && files.size()>0){
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setRows(files);
                }else{
                    if(StringUtils.isNotBlank(qvo.getPatientId())){
                        AppSignForm form = sysDao.getAppSignformDao().findFormByDr(qvo.getPatientId(),null);
                        if(form!=null){
                            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,form.getSignHospId());
                            if(dept!=null){
                                HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                                qqvo.setIdno(this.getAppPatientUser().getPatientIdno());
                                qqvo.setType("2");
                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, dept.getHospAreaCode().substring(0,4));
                                if(value!=null){
                                    qqvo.setUrlType(value.getCodeTitle());
                                }
                                String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,requestUserId,requestUserName,userType);
                                if(StringUtils.isNotBlank(str)){
                                    JSONObject jsonall = JSONObject.fromObject(str);
                                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                                        if(jsonall.get("entity") != null) {
                                            if(!jsonall.get("entity").toString().equals("null")){
                                                JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                                if(AddressType.ND.getValue().equals(qqvo.getUrlType()) || AddressType.PINGT.getValue().equals(qqvo.getUrlType())){//宁德居民档案
                                                    if(entity != null && "800".equals(entity.get("msgCode").toString())){
                                                        List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(),new TypeToken<List<AppEnterpatientEntity>>() {}.getType());
                                                        if(list != null && list.size()>0){
                                                            AppEnterpatientEntity vv = list.get(0);
                                                            ResidentHealthFiles t_dwellerfile = new ResidentHealthFiles();
                                                            t_dwellerfile.setAdress_city(vv.getAdress_city());
                                                            t_dwellerfile.setAdress_county(vv.getAdress_county());
                                                            t_dwellerfile.setAdress_pro(vv.getAdress_pro());
                                                            t_dwellerfile.setAdress_rural(vv.getAdress_rural());
                                                            t_dwellerfile.setAdress_village(vv.getAdress_village());
                                                            t_dwellerfile.setAdrss_hnumber(vv.getAdrss_hnumber());
                                                            t_dwellerfile.setBirthday(vv.getBirthday());
                                                            t_dwellerfile.setSex(vv.getSex());
                                                            t_dwellerfile.setDf_id(vv.getDf_id());
                                                            t_dwellerfile.setName(vv.getName());
                                                            t_dwellerfile.setJdrq00(vv.getCdate());
                                                            t_dwellerfile.setTelphone(vv.getTelphone());
                                                            t_dwellerfile.setRef_ci_id(vv.getRef_ci_id());
                                                            t_dwellerfile.setRef_cjid(vv.getRef_cjid());
                                                            t_dwellerfile.setJname(vv.getJname());
//                                                            t_dwellerfile.setBeizhu(vv.getDreason());
                                                            JSONObject json = JSONObject.fromObject(t_dwellerfile);//将java对象转换为json对象
                                                            String sstr = json.toString();
                                                            AppHealthFile file = sysDao.getAppHealthFileDao().findFileByPatientId(getAppPatientUser().getId());
                                                            if(file == null){
                                                                file = new AppHealthFile();
                                                                file.setHfTeamId(qvo.getTeamId());
                                                                file.setHfDrId(qvo.getDrId());
                                                                file.setHfAreaCode(dept.getHospAreaCode());
                                                                file.setHfHospId(dept.getId());
                                                                file.setHfPatientId(getAppPatientUser().getId());
                                                                file.setHfAuditState("1");
                                                                file.setData(sstr);
                                                                sysDao.getServiceDo().add(file);
                                                                files.add(file);
                                                            }
                                                        }
                                                    }
                                                }else{
                                                    if ("true".equals(entity.get("success").toString())) {
                                                        T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(),T_dwellerfile.class);
                                                        if(entitys!=null){
                                                            if(StringUtils.isNotBlank(entitys.getJmdah())){
                                                                //基卫有健康档案
                                                                String strr = this.getSysDao().getSecurityCardAsyncBean().getResidentRecords(entitys.getJmdah(),null,value.getCodeTitle(),requestUserId,requestUserName,userType);
                                                                JSONObject jsonall1 = JSONObject.fromObject(strr);
                                                                if(jsonall1.get("rows")!=null && jsonall1.get("msgCode").equals("800")){
                                                                    List<ResidentRecordsEntity> ls = JsonUtil.fromJson(jsonall1.get("rows").toString(),new TypeToken<List<ResidentRecordsEntity>>() {}.getType());
                                                                    if(ls!=null && ls.size()>0){
                                                                        ResidentHealthFiles t_dwellerfile = ls.get(0).getT_dwellerfile();
                                                                        if(t_dwellerfile!=null){
                                                                            JSONObject json = JSONObject.fromObject(t_dwellerfile);//将java对象转换为json对象
                                                                            String sstr = json.toString();
                                                                            AppHealthFile file = sysDao.getAppHealthFileDao().findFileByPatientId(getAppPatientUser().getId());
                                                                            if(file==null){
                                                                                file = new AppHealthFile();
                                                                                file.setData(sstr);
                                                                                file.setHfTeamId(qvo.getTeamId());
                                                                                file.setHfDrId(qvo.getDrId());
                                                                                file.setHfAreaCode(dept.getHospAreaCode());
                                                                                file.setHfHospId(dept.getId());
                                                                                file.setHfPatientId(getAppPatientUser().getId());
                                                                                file.setHfAuditState("1");
                                                                                sysDao.getServiceDo().add(file);
                                                                                files.add(file);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //查无档案信息返回基本信息
                        AppPatientPerfectDataEntity vvo = new AppPatientPerfectDataEntity();
                        if(user != null){
                            vvo.setPatientName(user.getPatientName());
                            vvo.setPatientIdNo(user.getPatientIdno());
                            vvo.setPatientCard(user.getPatientCard());
                            vvo.setPatientTel(user.getPatientTel());
                            vvo.setPatientNeighborhoodCommittee(user.getPatientNeighborhoodCommittee());
                            vvo.setEhcId(user.getPatientEHCId());
                            vvo.setEhcCardNo(user.getPatientEHCNo());
                            this.getAjson().setVo(vvo);
                        }
                    }
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setRows(files);


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
     * 居民未建档提醒
     * @return
     */
    public String remindFile(){
        try{
            AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppDrUser drUser = this.getAppDrUser();
                if(drUser != null){
                    userType = "2";
                    requestUserId = drUser.getId();
                    requestUserName = drUser.getDrName();
                }
                if(StringUtils.isNotBlank(this.getAppDrUser().getDrHospId())){
                    AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,this.getAppDrUser().getDrHospId());
                    if(dept!=null){
                        //判断该医院所在市是否开启建档才可签约限制
                        if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                            List<AppSignSetting> sets = sysDao.getServiceDo().loadByPk(AppSignSetting.class,"signsAreaCode",dept.getHospAreaCode().substring(0,4));
                            if(sets!=null && sets.size()>0){
                                if("1".equals(sets.get(0).getSignsOpenJd())){//开启建档签约限制，要去查询健康档案
                                    //查询本地档案
                                    AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(qvo.getPatientIdno());
                                    if(user!=null){
                                        AppHealthFile file = sysDao.getAppHealthFileDao().findFileByPatientId(user.getId());
                                        if(file==null){
                                            HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                                            qqvo.setIdno(qvo.getPatientIdno());
                                            qqvo.setType("2");
                                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, dept.getHospAreaCode().substring(0,4));
                                            if(value!=null){
                                                qqvo.setUrlType(value.getCodeTitle());
                                            }
                                            String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,requestUserId,requestUserName,userType);
                                            if(AddressType.ND.getValue().equals(qqvo.getUrlType()) || AddressType.PINGT.getValue().equals(qqvo.getUrlType())){//宁德
                                                if(StringUtils.isNotBlank(str)){
                                                    JSONObject jsonall = JSONObject.fromObject(str);
                                                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                                                        if(jsonall.get("entity") != null) {
                                                            if (!jsonall.get("entity").toString().equals("null")) {
                                                                JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                                                if(entity != null && "800".equals(entity.get("msgCode").toString())){
                                                                    List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(),new TypeToken<List<AppEnterpatientEntity>>() {}.getType());
                                                                    if(list != null && list.size()>0){
                                                                        if(StringUtils.isBlank(list.get(0).getDf_id())){
                                                                            this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                                                            this.getAjson().setMsgCode("900");
                                                                            return "ajson";
                                                                        }
                                                                    }else{
                                                                        this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                                                        this.getAjson().setMsgCode("900");
                                                                        return "ajson";
                                                                    }
                                                                }else{
                                                                    this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                                                    this.getAjson().setMsgCode("900");
                                                                    return "ajson";
                                                                }
                                                            }else{
                                                                this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                                                this.getAjson().setMsgCode("900");
                                                                return "ajson";
                                                            }
                                                        }else{
                                                            this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                                            this.getAjson().setMsgCode("900");
                                                            return "ajson";
                                                        }
                                                    }else{
                                                        this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                                        this.getAjson().setMsgCode("900");
                                                        return "ajson";
                                                    }
                                                }else{
                                                    this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                                    this.getAjson().setMsgCode("900");
                                                    return "ajson";
                                                }
                                            }else{
                                                if(StringUtils.isNotBlank(str)){
                                                    JSONObject jsonall = JSONObject.fromObject(str);
                                                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                                                        if(jsonall.get("entity") != null) {
                                                            if(!jsonall.get("entity").toString().equals("null")){
                                                                JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                                                if ("true".equals(entity.get("success").toString())) {
                                                                    T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(),T_dwellerfile.class);
                                                                    if(entitys!=null){
                                                                        this.getAjson().setVo(entitys);
                                                                        if(StringUtils.isNotBlank(entitys.getJmdah())){

                                                                        }else{
                                                                            this.getAjson().setMsg("该居民当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                                                            this.getAjson().setMsgCode("901");
                                                                            return "ajson";
                                                                        }
                                                                    }else{
                                                                        this.getAjson().setMsg("该居民当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                                                        this.getAjson().setMsgCode("901");
                                                                        return "ajson";
                                                                    }
                                                                }else{
                                                                    this.getAjson().setMsg("该居民当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                                                    this.getAjson().setMsgCode("901");
                                                                    return "ajson";
                                                                }
                                                            }
                                                        }else{
                                                            this.getAjson().setMsg("该居民当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                                            this.getAjson().setMsgCode("901");
                                                            return "ajson";
                                                        }
                                                    }else{
                                                        this.getAjson().setMsg("该居民当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                                        this.getAjson().setMsgCode("901");
                                                        return "ajson";
                                                    }
                                                }else{
                                                    this.getAjson().setMsg("该居民当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                                    this.getAjson().setMsgCode("901");
                                                    return "ajson";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    public String findHealthFile(){
        try{
            AppFileAuditQvo qvo = (AppFileAuditQvo)this.getAppJson(AppFileAuditQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isNotBlank(qvo.getId())){
                    AppHealthFile file = (AppHealthFile)sysDao.getServiceDo().find(AppHealthFile.class,qvo.getId());
                    if(file!=null){
                        this.getAjson().setVo(file);
                        this.getAjson().setMsgCode("800");
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查不到对应数据");
                    }
                }else {
                    this.getAjson().setMsg("id不能为空！");
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
     * 查询计免儿童全程标准方案
     * @return
     */
    public String findMySchByBth(){
        InoculationVo qvo = (InoculationVo)this.getAppJson(InoculationVo.class);
        if(qvo!=null){
            if(StringUtils.isNotBlank(qvo.getChildBirth())){
                List<AlgorithEpiVoList> ls = this.getSysDao().getSecurityCardAsyncBean().getInoculateMySchByBth(qvo.getChildBirth());
                this.getAjson().setRows(ls);
            }else{
                this.getAjson().setMsg("出生日期参数格式错误");
                this.getAjson().setMsgCode("900");
            }
        }else{
            this.getAjson().setMsg("参数格式错误");
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 根据地址简称返回区划
     * @return
     */
    public String getAreaCode(){
        try{
            AppCommQvo qvo = (AppCommQvo)this.getAppJson(AppCommQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
//                  String areaCode = sysDao.getAppCityAreaDao().findBySname(qvo.getAreaName(),qvo.getAreaSname());
                  List<CdAddress> list = sysDao.getServiceDo().loadByPk(CdAddress.class,"areaSname",qvo.getAreaSname());
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
     * 测试方法
     * @return
     */
    public String abcAbc(){
        try {
            System.out.println("开始调度changeArchivingSignLabel方法");
            String sql = "SELECT * FROM app_sign_form WHERE ID IN ('3758c38b-ea7c-4e4c-a596-21cbc6761729'," +
                    "'5d182cfc-77e5-481c-836b-e6a150046c09'," +
                    "'b0ceeed4-d78f-43f0-886c-3a10b8c08cd3'," +
                    "'66c0cd85-e0d0-41d0-904b-881ba051977d'," +
                    "'ad327334-5d1c-4e98-b53e-3126159dbfff'," +
                    "'255d7958-8d48-40fc-9ad1-bb06ec1f452f'," +
                    "'076d1691-8059-4cb0-8797-349b05b1d500'," +
                    "'f46a793c-4f28-4545-a76f-1b1e2175ca04'," +
                    "'744eb869-4318-401e-b82b-d8b7db8ed974'," +
                    "'58697a04-047d-4bed-9f14-c97abab1250e'," +
                    "'806b9113-7fbf-466d-b3f1-7a73d87e0e97'," +
                    "'796344dd-195e-4f3f-88b8-cc54a3dca315'," +
                    "'98199b82-38f5-4ce5-a73b-5c15b1060744'," +
                    "'33a41150-dc32-4e26-b27e-cf87c89c2a48'," +
                    "'cd411665-cf87-4aa6-ac93-49628cabae28'," +
                    "'8a5fad12-726e-491f-bc9a-ef9a856bdd9d'," +
                    "'81f973e4-fb3d-4a66-aa2d-b35a2a262feb'," +
                    "'630c2015-cfd3-45a8-9df7-a30cb5457e36'," +
                    "'dfbf93d7-86fa-4a34-8053-7545893db100'," +
                    "'f7d8d202-5356-4369-88ee-e78c12bb981d'," +
                    "'64ff2d6c-4744-4595-a2e1-b58d5e886c39'," +
                    "'071fa690-baf7-4205-b804-65bd62860c03'," +
                    "'e803b8d5-f296-49ae-b5ac-6a836cbe07c5'," +
                    "'021e91ba-ea11-4d69-8217-fb891cad46a9'," +
                    "'0cd24bf1-30ec-4aaa-a188-28219967595f'," +
                    "'577b5bcc-023c-43b2-8323-9c4e665d4223'," +
                    "'9b488b2b-ea41-48d1-9685-c1481d70a649'," +
                    "'bd9b8cfc-aeb7-4e0a-af27-405f5a4d3988'," +
                    "'44b7d1b4-a3f0-4df3-aa9f-dcb432544d82'," +
                    "'1e0f6766-cfbc-48ba-933f-e8f3c601c119'," +
                    "'86bc0855-55c4-4ef9-993f-a81b1abd9b1c'," +
                    "'30bfa351-33b9-4ec1-9b8a-b1e860f3a25c'," +
                    "'cafc6c56-d783-4fee-9aaa-dd7726d70945'," +
                    "'ed8d8047-54f5-406e-bbc4-0fd2859d6dbe'," +
                    "'8954058a-74df-409b-9f51-d1fb7d93f347'," +
                    "'51a6c2fc-c120-4ea0-b133-bda4d57c9bee'," +
                    "'1191d654-f2d0-4862-8a2a-0d667829ebd5'," +
                    "'2907d68d-f06f-48d7-83d4-a3d3223da6c4'," +
                    "'ba01fb33-1635-46c4-b1aa-2dc18fd710c7'," +
                    "'b28f04c6-ede0-4aaf-b898-324029028dae')";
            List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql,new HashMap<String, Object>(),AppSignForm.class);
            if(list != null && list.size()>0){
                for(AppSignForm ll:list){
                    sysDao.getServiceDo().delete(ll);
                }
            }
            //三明签约单重复
           /* Map<String,Object> map = new HashMap<>();
            String sql = "SELECT\n" +
                    "\tSIGN_PATIENT_ID,\n" +
                    "\tcount(1)\n" +
                    "FROM\n" +
                    "\tapp_sign_form\n" +
                    "WHERE\n" +
                    "\tSIGN_STATE IN ('0', '2')\n" +
                    "GROUP BY\n" +
                    "\tSIGN_PATIENT_ID\n" +
                    "HAVING\n" +
                    "\tcount(1) > 1";
            List<Map> lisMap = sysDao.getServiceDo().findSqlMap(sql,map);
            System.out.println(lisMap.size());
            if(lisMap != null && lisMap.size()>0){
                for(Map mm:lisMap){
                    if(mm.get("SIGN_PATIENT_ID")!= null){
                        //根据患者id查询患者信息
                        AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,mm.get("SIGN_PATIENT_ID").toString());
                        if(user != null){
                            //通过患者id查询有效签约单集合
                            List<AppSignForm> list = sysDao.getAppSignformDao().findListSignByUserId(user.getId());
                            if(list!= null && list.size()>0){
                                for(int i=0;i<list.size();i++){
                                    if(i!=0){
                                        sysDao.getServiceDo().delete(list.get(i));
                                    }
                                }
                            }
                        }
                    }
                }
            }*/
            //处理建档立卡标签表和签约服务人群标签表不相等数据
            /*Map<String,Object> map = new HashMap<>();
            String sql = "SELECT * FROM (\n" +
                    "SELECT\n" +
                    "a.ID,\n" +
                    "a.ARCHIVING_PATIENT_IDNO,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_VALUE ORDER BY LABEL_VALUE) FROM app_label_archiving WHERE LABEL_ARCHIVING_ID = a.ID ) arValue,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_VALUE ORDER BY LABEL_VALUE) FROM app_label_group WHERE LABEL_SIGN_ID = a.SIGN_ID ) signValue\n" +
                    "FROM\n" +
                    "\tapp_archivingcard_people a\n" +
                    "WHERE\n" +
                    "\ta.SOURCE_TYPE = '3'\n" +
                    "AND a.SIGN_ID IS NOT NULL  ) cc WHERE cc.arValue != cc.signValue ";
            List<Map> lisMap = sysDao.getServiceDo().findSqlMap(sql,map);
            System.out.println(lisMap.size());
            if(lisMap != null && lisMap.size()>0){
                for(Map mm:lisMap){
                    if(mm.get("ID")!= null){
                        AppArchivingCardPeople pp = (AppArchivingCardPeople)sysDao.getServiceDo().find(AppArchivingCardPeople.class,mm.get("ID").toString());
                        if(pp != null){
                            if(StringUtils.isNotBlank(pp.getSignId())){
                                AppSignForm form =(AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,pp.getSignId());
                                if(form != null){
                                    String fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                    String[] fwTypes = null;
                                    if (StringUtils.isNotBlank(fwType)) {
                                        fwTypes = fwType.split(",");
                                    }

                                    if(fwTypes != null && fwTypes.length>0){
                                        boolean flag = false;
                                        if(fwTypes.length>1 && fwType.indexOf("1") !=-1){
                                            String urlType = "";
                                            String address = "";
                                            //服务人群不止一个，且有普通人群，查询基卫服务人群确定该居民服务类型
                                            if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.FZS.getValue())) {
                                                urlType = AddressType.FZ.getValue();
                                                address = PropertiesUtil.getConfValue("FZurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.QZS.getValue())) {
                                                urlType = AddressType.QZ.getValue();
                                                address = PropertiesUtil.getConfValue("QZurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.ZZS.getValue())) {
                                                urlType = AddressType.ZZ.getValue();
                                                address = PropertiesUtil.getConfValue("ZZurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.PTS.getValue())) {
                                                urlType = AddressType.PT.getValue();
                                                address = PropertiesUtil.getConfValue("PTurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.NPS.getValue())) {
                                                urlType = AddressType.NP.getValue();
                                                address = PropertiesUtil.getConfValue("NPurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.SMS.getValue())) {
                                                urlType = AddressType.SM.getValue();
                                                address = PropertiesUtil.getConfValue("SMurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.CS.getValue())) {
                                                urlType = AddressType.CS.getValue();
                                                address = PropertiesUtil.getConfValue("CSurlFwType");
                                            }else{
                                                urlType = AddressType.SXS.getValue();
                                                address = PropertiesUtil.getConfValue("SXurlFwType");
                                            }
                                            JSONObject jsonParam = new JSONObject();// 调用的参数
                                            jsonParam.put("idNo", pp.getArchivingPatientIdno());
                                            jsonParam.put("urlType", urlType);
                                            CloseableHttpClient client = HttpClients.createDefault();
                                            String result = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                                            if (result != null) {// 接口调用正常
                                                JSONObject jsonAll = JSONObject.fromObject(result);
                                                if ("800".equals(jsonAll.get("msgCode").toString())) {
                                                    if(jsonAll.get("vo").toString() != null){
                                                        AppDrPatientFwEntity ls = JSON.parseObject(jsonAll.get("vo").toString(), AppDrPatientFwEntity.class);
                                                        if(ls != null){
                                                            if("1".equals(ls.getIs06child())){//0-6儿童
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIslnr())){//老年人
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIsycf())){//孕产妇
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIsgxy())){//高血压
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIstnb())){//糖尿病
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIsjhb())){//结核病
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIscjr())){//残疾人
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIszxjsb())){//重性精神病
                                                                flag = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if(flag){//是重点人群，删除普通人群服务
                                                AppLabelGroup group = sysDao.getAppLabelGroupDao().findGroupBySignAndValue(form.getId(),"1");
                                                if(group != null){
                                                    sysDao.getServiceDo().delete(group);
                                                }
                                                fwType = fwType.replace("1,","");
                                                sysDao.getAppLabelGroupDao().addLabel(fwType.split(","), pp.getId(), form.getSignTeamId(), form.getSignAreaCode(), LabelManageType.JDLK.getValue());
                                            }
                                        }else{
                                            sysDao.getAppLabelGroupDao().addLabel(fwTypes, pp.getId(), form.getSignTeamId(), form.getSignAreaCode(), LabelManageType.JDLK.getValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }*/
//            List<AppArchivingCardPeople> list = sysDao.getAppArchivingCardPeopeDao().findByNotSignId();
//            if(list != null && list.size()>0){
//                for(AppArchivingCardPeople pp:list){
//                    AppSignForm form =sysDao.getAppSignformDao().findByPatientIdno(pp.getArchivingPatientIdno());
//                    if(form != null){
//                        String fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
//                        String[] fwTypes = null;
//                        if (StringUtils.isNotBlank(fwType)) {
//                            fwTypes = fwType.split(",");
//                        }
//                        if(fwTypes != null && fwTypes.length>0){
//                            sysDao.getAppLabelGroupDao().addLabel(fwTypes, pp.getId(), form.getSignTeamId(), form.getSignAreaCode(), LabelManageType.JDLK.getValue());
//                        }
//
//                    }
//                }
//            }
            System.out.println("结束调度changeArchivingSignLabel方法");
//            this.sysDao.getSecurityCardAsyncBean().getPlannedPeopleNew("2018","11","350100000000","3501");
            //处理建档立卡标签表中既是普通人群又是重点人群数据,
           /* Map<String,Object> map = new HashMap<>();
            String sql = "SELECT\n" +
                    "\t*\n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tLABEL_ARCHIVING_ID,\n" +
                    "\t\t\tGROUP_CONCAT(LABEL_VALUE) LABEL_VALUE\n" +
                    "\t\tFROM\n" +
                    "\t\t\tAPP_LABEL_ARCHIVING\n" +
                    "\t\tWHERE\n" +
                    "\t\t\tLABEL_ARCHIVING_ID IN (\n" +
                    "\t\t\t\tSELECT\n" +
                    "\t\t\t\t\tID\n" +
                    "\t\t\t\tFROM\n" +
                    "\t\t\t\t\tAPP_ARCHIVINGCARD_PEOPLE\n" +
                    "\t\t\t\tWHERE\n" +
                    "\t\t\t\t\tSOURCE_TYPE = '3'\n" +
                    "\t\t\t\tAND SIGN_ID IS NOT NULL\n" +
                    "\t\t\t)\n" +
                    "\t\tAND LABEL_TYPE = '3'\n" +
                    "\t\tGROUP BY\n" +
                    "\t\t\tLABEL_ARCHIVING_ID\n" +
                    "\t) cc\n" +
                    "WHERE\n" +
                    "\tfind_in_set('1', cc.LABEL_VALUE) AND cc.LABEL_VALUE != '1' " +
                    "AND cc.LABEL_VALUE != '1,1' AND cc.LABEL_VALUE != '1,1,1' AND cc.LABEL_VALUE !='1,1,1,1'\n" +
                    "AND cc.LABEL_VALUE != '1,1,1,1,1' AND cc.LABEL_VALUE != '1,1,1,1,1,1'\n" +
                    "ORDER BY\n" +
                    "\tcc.LABEL_VALUE DESC";
                List<Map> list = sysDao.getServiceDo().findSql(sql);
                if(list != null && list.size()>0){
                    //遍历list
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).get("LABEL_ARCHIVING_ID") != null){
                            //获取建档立卡花名册数据主键
                            String archId = list.get(i).get("LABEL_ARCHIVING_ID").toString();
                            //通过主键查询所有建档立卡标签表数据
                            List<AppLabelArchiving> lists = sysDao.getServiceDo().loadByPk(AppLabelArchiving.class,"labelArchivingId",archId);
                            if(lists != null && lists.size()>0){
                                //遍历lists删除普通人群的记录 保留重点人群数据
                                for(AppLabelArchiving vo:lists){
                                    if(ResidentMangeType.PTRQ.getValue().equals(vo.getLabelValue())){
                                        sysDao.getServiceDo().delete(vo);
                                    }
                                }
                            }
                        }
                    }
                }*/
            //测试互联网医院消息推送
//            AppInternetNewsQvo qvo = (AppInternetNewsQvo) this.getAppJson(AppInternetNewsQvo.class);
//            sysDao.getSecurityCardAsyncBean().sendOutInternetNews(qvo);
            //排名调度
            /*List<String> ls = new ArrayList<String>();
            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            //获取配置的城市地区，判断是否是省内还是其他省 调度工程要加配置cityJdCode(城市编码前四位 例:cityJdCode=3501(代表福州市))
            String cityCode = PropertiesUtil.getConfValue("cityJdCode");
            boolean flag = false;
            if(StringUtils.isNotBlank(cityCode)){//目前只有判断福建省和山西省就行
                if("14".equals(cityCode.substring(0,2))){//山西省
                    flag = true;
                }
            }
            String[] areaCode = null;
            if(flag){
                areaCode = new String[]{AddressType.GPS.getValue()};
            }else{
                areaCode = new String[]{AddressType.FZS.getValue(),AddressType.SMS.getValue(),AddressType.NPS.getValue(),
                        AddressType.PTS.getValue(),AddressType.ZZS.getValue(),AddressType.QZS.getValue()};
            }
            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findManageContByAreaCode(areaCode);
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageRankCount(s,lsTeam);
                }
            }*/

            //处理服务人群和疾病类型不相对等数据
            //处理福州东街社区高血压和糖尿病服务人群没有相应疾病类型
            /*Map<String,Object> map = new HashMap<>();
            map.put("hospId","6930");
            String sql = "SELECT a.* FROM app_label_group a INNER JOIN app_sign_form b ON a.LABEL_SIGN_ID = b.ID WHERE b.SIGN_STATE IN ('0','2')\n" +
                    "AND b.SIGN_HOSP_ID = :hospId AND a.LABEL_VALUE IN('5','6') AND a.LABEL_TYPE = '3' ";
            List<AppLabelGroup> list = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelGroup.class);
            if(list != null && list.size()>0){
                for(AppLabelGroup group:list){
                    if(ResidentMangeType.GXY.getValue().equals(group.getLabelValue())){//高血压
                        AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("2","201");//高血压
                        AppLabelDisease disease = new AppLabelDisease();
                        disease.setLabelColor("gary");
                        disease.setLabelTeamId(group.getLabelTeamId());
                        disease.setLabelSignId(group.getLabelSignId());
                        disease.setLabelId(manage.getId());
                        disease.setLabelTitle(manage.getLabelTitle());
                        disease.setLabelType(manage.getLabelType());
                        disease.setLabelValue(manage.getLabelValue());
                        disease.setLabelAreaCode(group.getLabelAreaCode());
                        sysDao.getServiceDo().add(disease);
                    }else if(ResidentMangeType.TNB.getValue().equals(group.getLabelValue())){//糖尿病
                        AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("2","202");//糖尿病
                        AppLabelDisease disease = new AppLabelDisease();
                        disease.setLabelColor("gary");
                        disease.setLabelTeamId(group.getLabelTeamId());
                        disease.setLabelSignId(group.getLabelSignId());
                        disease.setLabelId(manage.getId());
                        disease.setLabelTitle(manage.getLabelTitle());
                        disease.setLabelType(manage.getLabelType());
                        disease.setLabelValue(manage.getLabelValue());
                        disease.setLabelAreaCode(group.getLabelAreaCode());
                        sysDao.getServiceDo().add(disease);
                    }
                }
            }*/

            //有高血压服务人群没高血压疾病类型
            /*Map<String,Object> map = new HashMap<>();
            String sql = " select a.* from app_label_group a where a.label_type='3' and a.label_value = '5' and label_area_code = '350102004000' and " +
                    " a.label_sign_id not in (select b.label_sign_id from app_label_disease b where b.label_value = '201' ) group by a.label_sign_id ";
            List<AppLabelGroup> lisg = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelGroup.class);
            if(lisg != null && lisg.size()>0){
                AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("2","201");//高血压
                for(AppLabelGroup group:lisg){
                    AppLabelDisease disease = new AppLabelDisease();
                    disease.setLabelColor("gary");
                    disease.setLabelTeamId(group.getLabelTeamId());
                    disease.setLabelSignId(group.getLabelSignId());
                    disease.setLabelId(manage.getId());
                    disease.setLabelTitle(manage.getLabelTitle());
                    disease.setLabelType(manage.getLabelType());
                    disease.setLabelValue(manage.getLabelValue());
                    disease.setLabelAreaCode(group.getLabelAreaCode());
                    sysDao.getServiceDo().add(disease);

                }
            }
            //有糖尿病服务人群没糖尿病疾病类型
            sql = " select a.* from app_label_group a where a.label_type='3' and a.label_value = '6' and " +
                    " a.label_sign_id not in (select b.label_sign_id from app_label_disease b where b.label_value = '202' ) group by a.label_sign_id ";
            List<AppLabelGroup> list = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelGroup.class);
            if(list != null && list.size()>0){
                AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("2","202");//糖尿病
                for(AppLabelGroup group:list){

                    AppLabelDisease disease = new AppLabelDisease();
                    disease.setLabelColor("gary");
                    disease.setLabelTeamId(group.getLabelTeamId());
                    disease.setLabelSignId(group.getLabelSignId());
                    disease.setLabelId(manage.getId());
                    disease.setLabelTitle(manage.getLabelTitle());
                    disease.setLabelType(manage.getLabelType());
                    disease.setLabelValue(manage.getLabelValue());
                    disease.setLabelAreaCode(group.getLabelAreaCode());
                    sysDao.getServiceDo().add(disease);

                }
            }*/
            //查询服务人群表中同签约单id，同类型重复数据
            /*
            String sql = "SELECT LABEL_VALUE,LABEL_SIGN_ID,\n" +
                    "count(1)  FROM app_label_group\n" +
                    "WHERE LABEL_TYPE = '3'\n" +
                    "GROUP BY LABEL_VALUE,LABEL_SIGN_ID\n" +
                    "HAVING count(1)>1\n" +
                    "ORDER BY count(1) DESC";
            List<Map> list = sysDao.getServiceDo().findSql(sql);
            if(list != null && list.size()>0){
                for(int i=0;i<list.size();i++){
                    String labelValue = list.get(i).get("LABEL_VALUE").toString();//获取服务值
                    String labelSignId = list.get(i).get("LABEL_SIGN_ID").toString();//获取签约单主键
                    map.put("labelValue",labelValue);
                    map.put("labelSignId",labelSignId);
                    //查询重复数据
                    String sqlG = "SELECT * FROM app_label_group\n" +
                            "WHERE \n" +
                            "LABEL_TYPE = '3'\n" +
                            "AND LABEL_VALUE = :labelValue\n" +
                            "AND LABEL_SIGN_ID = :labelSignId\n" +
                            "ORDER BY HS_UPDATE_TIME DESC";
                    List<AppLabelGroup> lisG = sysDao.getServiceDo().findSqlMap(sqlG,map,AppLabelGroup.class);
                    if(lisG != null && lisG.size()>0){
                        for(int j = 0;j<lisG.size();j++){
                            if(j!=0){//保留第一条，删除其他条数据
                                AppLabelGroup labelGroup = lisG.get(j);
                                sysDao.getServiceDo().delete(labelGroup);
                            }
                        }
                    }
                }
            }*/
            //调度随访量、健康指导、健康教育、求助量、未缴费人数 查询单前时间到前6天的数据
            //先删数据
            /*List<String> ls = new ArrayList<String>();
            String startDate = ExtendDate.getYMD(Calendar.getInstance());
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            String delSql = "DELETE FROM APP_MANAGE_OTHER_COUNT";
            sysDao.getServiceDo().deleteSql(delSql);
//            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findByManageCont();
            String[] areaCode = new String[]{AddressType.FZS.getValue(),AddressType.SMS.getValue(),AddressType.NPS.getValue(),
                    AddressType.PTS.getValue(),AddressType.ZZS.getValue(),AddressType.QZS.getValue()};
            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findManageContByAreaCode(areaCode);
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageOtherCount(s,lsTeam);
                }
            }*/

            //建档立卡统计all表调度
           /* String state = PropertiesUtil.getConfValue("manageState");
            List<String> ls = new ArrayList<String>();
            String delSql = " DELETE FROM app_manage_archiving_all_count ";
            sysDao.getServiceDo().deleteSql(delSql);
            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            String areaCode = PropertiesUtil.getConfValue("cityJdCode");
            CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, areaCode);
            String sourceType = "1";
            if(cityCode != null){
                if("0".equals(cityCode.getCodeTitle())){//查询医保数据
                    sourceType = "3";
                }
            }
            Map<String,Object> map = new HashMap<>();
            map.put("SOURCE_TYPE",sourceType);
            String sql = " SELECT ADDR_RURAL_CODE areaCode,ADDR_HOSP_ID hospId FROM app_archivingcard_people " +
                    "where ADDR_RURAL_CODE IS NOT NULL AND SOURCE_TYPE =:SOURCE_TYPE GROUP BY ADDR_RURAL_CODE,ADDR_HOSP_ID ";
            List<AppTeamManagEntity> lsTeam = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageArchivingAllCount(s,lsTeam,sourceType);
                }
            }*/
           /* //处理居民信息表重复数据
            //1查询居民用户表重复身份证
            Map<String,Object> map = new HashMap<>();
            String sql = "SELECT\n" +
                    " PATIENT_IDNO,\n" +
                    "\tcount(1)\n" +
                    "FROM\n" +
                    "\tapp_patient_user\n" +
                    "WHERE\n" +
                    "PATIENT_IDNO IS NOT NULL\n" +
                    "GROUP BY\n" +
                    "\tPATIENT_IDNO\n" +
                    "HAVING\n" +
                    "\tcount(1) > 1\n" +
                    "ORDER BY\n" +
                    "\tcount(1) DESC;";
            List<Map> list = sysDao.getServiceDo().findSql(sql);
            if(list != null && list.size()>0){
                for(int i=0;i<list.size();i++){//循环重复人员身份证数据
                    //获取身份证
                    String idno = list.get(i).get("PATIENT_IDNO").toString();
                    //查询重复人员数据
                    List<AppPatientUser> lsUser = sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",idno);
                    if(lsUser != null && lsUser.size()>0){
                        //查询到重复人员数据，循环人员数据判断是否有签约数据
                        for(AppPatientUser user:lsUser){//逻辑不正确，如果重复人员数据都没有签约单，这种情况下要保留一条不能全删
                            AppSignForm form = sysDao.getAppSignformDao().findSignFormByState(user.getId());
                            if(form == null){//没有签约单，直接赋值99,等同步到各地市就可以直接delete状态为99的数据
                                user.setPatientState("99");
                                sysDao.getServiceDo().modify(user);
                            }else{//如果有签约单

                            }
                        }

                    }

                }

            }*/

            //每年一月一号调度获取当前年份管理指标
//            sysDao.getAppCityAreaDao().getCityAreaInitNew();
            //找回三明服务人群为空的数据
          /*  Map<String,Object> map = new HashMap<>();
            String sql = "SELECT * FROM app_sign_form WHERE SIGN_STATE IN ('0','2')\n" +
                    "AND ID NOT IN (SELECT LABEL_SIGN_ID FROM app_label_group ) ";
            //查询没有服务人群的签约单
            List<AppSignForm> listSF = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
            if(listSF != null && listSF.size()>0){
                int i = 0;
                for(AppSignForm signForm:listSF){
                    i++;
                    //查询添加日志表判断是否有添加记录
                    map.put("labelSignId","%"+signForm.getId()+"%");
                    map.put("className","com.ylz.bizDo.app.po.AppLabelGroup");
                    String sqll = "SELECT\n" +
                            "\t*\n" +
                            "FROM\n" +
                            "\tsys_log_add\n" +
                            "WHERE\n" +
                            "\tBUSINESS_JSON LIKE :labelSignId\n" +
                            "AND CLASS_NAME = :className ";
                    List<SysLogAdd> listLA = sysDao.getServiceDo().findSqlMap(sqll,map,SysLogAdd.class);
                    if(listLA != null && listLA.size()>0){
                        //查到添加记录在判断是否是删除的
                        for(SysLogAdd la:listLA){
                            map.put("deleteId",la.getBusinessId());
                            String sqld = "SELECT\n" +
                                    "\t*\n" +
                                    "FROM\n" +
                                    "\tsys_log_delet\n" +
                                    "WHERE\n" +
                                    "\tBUSINESS_ID = :deleteId\n" +
                                    "AND CLASS_NAME = :className ";
                            List<SysLogDelet> listLD = sysDao.getServiceDo().findSqlMap(sqld,map,SysLogDelet.class);
                            if(listLD != null && listLD.size()>0){//有的话代表该服务人群记录是已删除

                            }else{//没有做恢复操作
                                if(StringUtils.isNotBlank(la.getBusinessJson())){
                                    AppLabelGroupEntity v = JsonUtil.fromJson(la.getBusinessJson(), AppLabelGroupEntity.class);
                                    WebLabelGroup group = new WebLabelGroup();
                                    group.setId(la.getBusinessId());
                                    group.setLabelAreaCode(v.getLabelAreaCode());
                                    group.setLabelId(v.getLabelId());
                                    group.setLabelSignId(v.getLabelSignId());
                                    group.setLabelTeamId(v.getLabelTeamId());
                                    group.setLabelTitle(v.getLabelTitle());
                                    group.setLabelType(v.getLabelType());
                                    group.setLabelValue(v.getLabelValue());
                                    sysDao.getServiceDo().add(group);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("结束恢复");*/
          //统计manageCount数据调度
            /*String state = PropertiesUtil.getConfValue("manageState");
            List<String> ls = new ArrayList<String>();
            if(state.equals(CommonEnable.QIYONG.getValue()) ){
                Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
                //ca.setTime(new Date()); // 设置时间为当前时间
                ca.add(Calendar.YEAR, -1); // 年份减1
                String startDate = ExtendDate.getYMD(ca);
                String endDate = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            }else{
                String date = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(date,date);
            }
            //获取配置的城市地区，判断是否是省内还是其他省 调度工程要加配置cityJdCode(城市编码前四位 例:cityJdCode=3501(代表福州市))
            String[] areaCode = new String[]{AddressType.FZS.getValue(),AddressType.SMS.getValue(),AddressType.NPS.getValue(),
                        AddressType.PTS.getValue(),AddressType.ZZS.getValue(),AddressType.QZS.getValue()};

            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findManageContByAreaCode(areaCode);
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageCount(s,lsTeam);
                }
            }*/
            /*HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
            qqvo.setIdno("350128197212261661");
            qqvo.setType("2");
            qqvo.setUrlType("05913571");
            String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,null,null, DrPatientType.DR.getValue());
            if(StringUtils.isNotBlank(str)) {
                T_dwellerfile file = sysDao.getSecurityCardAsyncBean().analysisFileOne(str, qqvo.getUrlType());
            }*/

           /* List<AppArchivingCardPeople> list = sysDao.getAppArchivingCardPeopeDao().findNotJd();
            if(list != null && list.size()>0){
                for(AppArchivingCardPeople ll:list){
                    if("9aef5cda-d118-11e8-b8bb-fa163e61b828".equals(ll.getId())){
                        if(StringUtils.isNotBlank(ll.getArchivingPatientIdno())){
                            String cityCode = "";
                            if(StringUtils.isNotBlank(ll.getAddrRuralCode())){
                                cityCode = AreaUtils.getAreaCode(ll.getAddrRuralCode(),"2");
                            }else if(StringUtils.isNotBlank(ll.getAddrCountyCode())){
                                cityCode = AreaUtils.getAreaCode(ll.getAddrCountyCode(),"2");
                            }
                            if(StringUtils.isNotBlank(cityCode)){
                                String address = null;
                                String urlType="";
                                JSONObject jsonParam = new JSONObject();
                                AppHealthCardRecodesVo vo = new AppHealthCardRecodesVo();
                                vo.setIdNo(ll.getArchivingPatientIdno());
                                vo.setUrlType(ll.getAddrRuralCode().substring(0,4));
                                CloseableHttpClient client = HttpClients.createDefault();
                                if(StringUtils.isNotBlank(vo.getUrlType())){
                                    if (vo.getUrlType().equals(AddressType.FZS.getValue())) {
                                        urlType = AddressType.FZ.getValue();
                                        address = PropertiesUtil.getConfValue("FZ");
                                    } else if (vo.getUrlType().equals(AddressType.QZS.getValue())) {
                                        urlType = AddressType.QZ.getValue();
                                        address = PropertiesUtil.getConfValue("QZ");
                                    } else if (vo.getUrlType().equals(AddressType.ZZS.getValue())) {
                                        urlType = AddressType.ZZ.getValue();
                                        address = PropertiesUtil.getConfValue("ZZ");
                                    } else if (vo.getUrlType().equals(AddressType.PTS.getValue())) {
                                        urlType = AddressType.PT.getValue();
                                        address = PropertiesUtil.getConfValue("PT");
                                    } else if (vo.getUrlType().equals(AddressType.NPS.getValue())) {
                                        urlType = AddressType.NP.getValue();
                                        address = PropertiesUtil.getConfValue("NP");
                                    } else if (vo.getUrlType().equals(AddressType.SMS.getValue())) {
                                        urlType = AddressType.SM.getValue();
                                        address = PropertiesUtil.getConfValue("SM");
                                    } else if (vo.getUrlType().equals(AddressType.CS.getValue())) {
                                        urlType = AddressType.CS.getValue();
                                        address = PropertiesUtil.getConfValue("CS");
                                    }else{
                                        urlType = AddressType.SXS.getValue();
                                        address = PropertiesUtil.getConfValue("SX");
                                    }
                                    jsonParam.put("idNo",vo.getIdNo());
                                    jsonParam.put("type","2");
                                    jsonParam.put("urlType",urlType);
                                    if(address!=null){
                                        String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                                        if(reslut!=null && reslut != ""){
                                            JSONObject jsonAll = JSONObject.fromObject(reslut);
                                            if(jsonAll.get("entity")!=""){
                                                String entvo=jsonAll.get("entity").toString();
                                                if(StringUtils.isNotBlank(entvo)){
                                                    JSONObject entVo = JSONObject.fromObject(entvo);
                                                    String entity =entVo.get("entity").toString();
                                                    if(StringUtils.isNotBlank(entity)){
                                                        JSONObject entvoAll = JSONObject.fromObject(entity);
                                                        String jmdah = null;
                                                        String jtdah = null;
                                                        if(entvoAll.get("jmdah")!= null && entvoAll.get("jmdah")!= ""){
                                                            jmdah = entvoAll.get("jmdah").toString();

                                                        }
                                                        if(entvoAll.get("jtdah")!= null && entvoAll.get("jtdah")!= ""){
                                                            jtdah = entvoAll.get("jtdah").toString();
                                                        }
                                                        if(StringUtils.isNotBlank(jmdah)){
                                                            ll.setRhfId(jmdah);
                                                            sysDao.getServiceDo().modify(ll);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }*/

            /*List<String> ls = new ArrayList<String>();
            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    String[] areaCode = new String[]{AddressType.FZS.getValue(),AddressType.SMS.getValue(),AddressType.NPS.getValue(),
                            AddressType.PTS.getValue(),AddressType.ZZS.getValue(),AddressType.QZS.getValue()};
                    List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findManageContByAreaCodeAndTime(areaCode,s);
                    sysDao.getSecurityCardAsyncBean().totalManageTeamCount(s,lsTeam);
                }
            }*/


            //调度建档立卡未签约原因数据
           /* List<String> ls = new ArrayList<String>();
            String delSql = " DELETE FROM app_manage_archiving_people ";
            sysDao.getServiceDo().deleteSql(delSql);
            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            String areaCode = PropertiesUtil.getConfValue("cityJdCode");
            CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, areaCode);
            String sourceType = "1";
            if(cityCode != null){
                if("0".equals(cityCode.getCodeTitle())){//查询医保数据
                    sourceType = "3";
                }
            }
            Map<String,Object> map = new HashMap<>();
            map.put("SOURCE_TYPE",sourceType);
            String sql = " SELECT " +
                    " ADDR_RURAL_CODE areaCode," +
                    " ADDR_HOSP_ID hospId " +
                    " FROM app_archivingcard_people " +
                    " where ADDR_RURAL_CODE IS NOT NULL AND SOURCE_TYPE ='3' GROUP BY ADDR_RURAL_CODE,ADDR_HOSP_ID";
            List<AppTeamManagEntity> lsTeam = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);

            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getSecurityCardAsyncBean().totalManageArchivingCountPeople(s,lsTeam,sourceType);
                }
            }*/

            /*List<String> ls = new ArrayList<String>();
            String delSql = " DELETE FROM app_manage_archiving_count ";
            sysDao.getServiceDo().deleteSql(delSql);

            String startDate = "2017-01-01";
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            Map<String,Object> map = new HashMap<>();
            String areaCode = PropertiesUtil.getConfValue("cityJdCode");
            CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, areaCode);
            map.put("SOURCE_TYPE","1");
            String sourceType = "1";
            if(cityCode != null){
                if("0".equals(cityCode.getCodeTitle())){//查询医保数据
                    map.put("SOURCE_TYPE","3");
                    sourceType = "3";
                }
            }
            String sql = " SELECT " +
                    "a.ADDR_RURAL_CODE jdAreaCode," +
                    "a.TEAM_ID teamId," +
                    "(SELECT TEAM_NAME FROM APP_TEAM WHERE ID = a.TEAM_ID) teamName," +
                    "a.HOSP_ID hospId," +
                    "a.ADDR_HOSP_ID addrHospId," +
                    "(SELECT HOSP_AREA_CODE FROM APP_HOSP_DEPT WHERE ID = a.HOSP_ID) areaCode " +
                    " FROM app_archivingcard_people a " +
                    "where a.ADDR_RURAL_CODE IS NOT NULL AND a.TEAM_ID IS NOT NULL AND SOURCE_TYPE =:SOURCE_TYPE  GROUP BY a.ADDR_RURAL_CODE,a.TEAM_ID,a.ADDR_HOSP_ID ";

            List<AppTeamManagEntity> lsTeam = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTeamManagEntity.class);
            if(ls != null && ls.size()>0){
                for(String ss:ls){
                    sysDao.getSecurityCardAsyncBean().totalManageArchivingCount(ss,lsTeam,sourceType);
                }
            }*/

            //初始三明精神病和结核病患者
            /*Map<String,Object> map = new HashMap<>();
            String sql = "SELECT\n" +
                    "\ta.*\n" +
                    "FROM\n" +
                    "\tapp_sign_form a\n" +
                    "INNER JOIN app_label_group b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "WHERE\n" +
                    "\ta.SIGN_STATE IN ('0', '2')\n" +
                    "AND b.LABEL_TYPE = '3'\n" +
                    "AND b.LABEL_VALUE = '7'\n" +
                    "AND a.SIGN_HOSP_ID = 'sm_7273'\n" +
                    "AND a.ID NOT IN (SELECT LABEL_SIGN_ID FROM app_label_disease WHERE LABEL_VALUE = '207')\n" +
                    "GROUP BY\n" +
                    "\ta.ID";
            List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
            if(list != null){
                //初始高血压
                AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("2","207");
                for(AppSignForm ll:list){
                    AppLabelDisease disease = sysDao.getAppLabelGroupDao().findDiseaseByOne("207",ll.getId());
                    if(disease == null){
                        disease = new AppLabelDisease();
                        disease.setLabelAreaCode(ll.getSignAreaCode());
                        disease.setLabelValue(manage.getLabelValue());
                        disease.setLabelType(manage.getLabelType());
                        disease.setLabelTitle(manage.getLabelTitle());
                        disease.setLabelId(manage.getId());
                        disease.setLabelSignId(ll.getId());
                        disease.setLabelTeamId(ll.getSignTeamId());
                        disease.setLabelColor("gray");
                        sysDao.getServiceDo().add(disease);
                    }
                }
            }*/
            //处理三明地区服务人群和疾病标签重复数据
           /* Map<String,Object> map = new HostMap<>();
            String sql  = " SELECT\n" +
                    "\ta.ID,\n" +
                    "\tb.LABEL_VALUE,\n" +
                    "\tcount(a.ID)\n" +
                    "FROM\n" +
                    "\tapp_sign_form a\n" +
                    "INNER JOIN app_label_disease b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "WHERE\n" +
                    "\ta.SIGN_STATE IN ('0', '2')\n" +
                    "AND a.SIGN_AREA_CODE LIKE '3504%'\n" +
                    "AND b.LABEL_TYPE = '2'\n" +
                    "AND b.LABEL_VALUE = '201'\n" +
                    "GROUP BY a.ID\n" +
                    "HAVING count(a.ID)>1 ";*/
//            List<Map> list =

        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 初始化标签
     * @return
     */
    public String initLabelData(){
        try {
          AppShrotQvo qvo = (AppShrotQvo) getAppJson(AppShrotQvo.class);
            if (qvo != null && StringUtils.isNotBlank(qvo.getType())) {
                int pageSize = 1000;
                String sql = "SELECT SIGN_HOSP_ID from app_sign_form where SIGN_GROUP IS NULL GROUP BY SIGN_HOSP_ID  ";
                List<Map> lsMap = sysDao.getServiceDo().findSql(sql);
                if(lsMap != null && lsMap.size() >0){
                    for(Map map1 : lsMap){
                        String hospId = map1.get("SIGN_HOSP_ID").toString();
                        Map<String,Object> map = new HashMap<String, Object>();
                        String sqlCount = "SELECT COUNT(1) FROM APP_SIGN_FORM where SIGN_HOSP_ID = :SIGN_HOSP_ID ";
                        map.put("SIGN_HOSP_ID",hospId);
                        int count = sysDao.getServiceDo().findCount(sqlCount,map);
                        if(count!=0) {
                            int sum = 0;
                            if(count >pageSize){
                                double result = MyMathUtil.div(Double.valueOf(count), Double.valueOf(pageSize), 1);
                                double math = Math.ceil(result); // 向上取整
                                sum = (int) math;
                                for (int j = 0; j < sum; j++) {  // 总数循环 分页
                                    sysDao.getSecurityCardAsyncBean().initLabelData(hospId,j+1,pageSize,qvo.getType());
                                }
                            }else{
                                sum = 1;
                                for (int j = 0; j < sum; j++) {  // 总数循环 分页
                                    sysDao.getSecurityCardAsyncBean().initLabelData(hospId,j+1,pageSize,qvo.getType());
                                }
                            }

                            System.out.println("hospId："+hospId+"次数:"+sum);
                        }
                    }
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 健康教育视频
     * @return
     */
    public String getHepVideo(){
        try{
            AppHepVideoQvo qvo = (AppHepVideoQvo)this.getAppJson(AppHepVideoQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getVideoState())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("状态参数不能为空!");
                    return "ajson";
                }
                List<AppHepVideo> ls = sysDao.getAppHepVideoDao().findListQvo(qvo);
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 极光用户
     * @return
     */
    public String getDrUserEase(){
        try{
            SericuryUtil p = new SericuryUtil();
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = " SELECT * FROM APP_DR_USER where DR_EASE_STATE is NULL ";
            List<AppDrUser> lsDrUser = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
            if(lsDrUser != null && lsDrUser.size()>0){
                for(AppDrUser drUser : lsDrUser){
                    if(drUser != null && StringUtils.isBlank(drUser.getDrEaseState())){
                        this.sysDao.getSecurityCardAsyncBean().registeredEasemobTemp(drUser.getId());
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
     * 极光群主
     * @return
     */
    public String getGroupEase(){
        try{
            SericuryUtil p = new SericuryUtil();
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = "SELECT * FROM APP_TEAM WHERE TEAM_EASE_GROUP_ID IS NUll AND TEAM_DEL_STATE = '0' ";
            List<AppTeam> lsTeam = sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeam team : lsTeam){
                    if(team != null && StringUtils.isBlank(team.getTeamEaseGroupId())){
                        this.sysDao.getSecurityCardAsyncBean().addGroupTemp(team);
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
     * 极光讨论组
     * @return
     */
    public String getRoomEase(){
        try{
            SericuryUtil p = new SericuryUtil();
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = "SELECT * FROM APP_TEAM WHERE TEAM_EASE_ROOM_ID is NULL AND TEAM_DEL_STATE = '0' ";
            List<AppTeam> lsTeam = sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeam team : lsTeam){
                    if(team != null && StringUtils.isBlank(team.getTeamEaseRoomId())){
                        this.sysDao.getSecurityCardAsyncBean().addRoomTeamp(team, CommonShortType.YISHENG.getValue());
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


}