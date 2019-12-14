package com.ylz.task.async;


import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.group.CreateGroupResult;
import cn.jmessage.api.user.UserInfoResult;
import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.entity.AppSignFormEntity;
import com.ylz.bizDo.app.entity.AppWebSignFormListEntity;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.*;
import com.ylz.bizDo.assessment.vo.AssessReadJwVo;
import com.ylz.bizDo.assessment.vo.interfaceQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressConfiguration;
import com.ylz.bizDo.jtapp.basicHealthEntity.AppEnterpatientEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.EnterpatientEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthEntity.jktj.Fy_message;
import com.ylz.bizDo.jtapp.basicHealthEntity.jktj.JwSignsEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.jzYy.*;
import com.ylz.bizDo.jtapp.basicHealthVo.*;
import com.ylz.bizDo.jtapp.commonEntity.*;
import com.ylz.bizDo.jtapp.commonVo.*;
import com.ylz.bizDo.jtapp.commonVo.booldSugr.AppBooldSugrData;
import com.ylz.bizDo.jtapp.drEntity.AppDrPatientFwEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamExerciseEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;
import com.ylz.bizDo.jtapp.patientVo.AppMaternalPlanQvo;
import com.ylz.bizDo.smjq.smEntity.AppBloodSugarEntity;
import com.ylz.bizDo.smjq.smEntity.AppBloodSugarTwoEntity;
import com.ylz.bizDo.smjq.smEntity.AppSmHbpEntity;
import com.ylz.bizDo.smjq.smEntity.AppSmHbpTwoEntity;
import com.ylz.bizDo.web.vo.WebPtCbxxboVo;
import com.ylz.bizDo.web.vo.WebPtQzjSignStateVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import com.ylz.task.async.IdCard.IdCardEntity;
import com.ylzinfo.ehc.portal.sdk.EhcPortalClient;
import com.ylzinfo.ehc.portal.sdk.bean.ResponseParams;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

@Component("securityCardAsyncBean")
public class SecurityCardAsyncBean {

    private static final Logger log = LoggerFactory.getLogger(SecurityCardAsyncBean.class);

    @Autowired
    private SysDao sysDao;

    /**
     *获取社保卡接口
     * @return
     */
    @Async()
    public String getSecurityCard(String idNo, String idName, String userId){
        String result = "";
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String str1 = null;
            jsonParam.put("idCard",idNo);
//            if(StringUtils.isNotBlank(idName))
//                jsonParam.put("userName",idName);
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getSecurityCard";
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(userId,idName,jsonParam.toString(),idNo,"getSecurityCard");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),userId,idName,str1, DrPatientType.PATIENT.getValue(),"getSecurityCard");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<IdCardEntity> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<IdCardEntity>>() {}.getType());
                    if(ls != null && ls.size() >0){
                        String temp  = "";
                        for(int i =0;i<ls.size();i++){
                            IdCardEntity v = ls.get(i);
                            if(IdCardStatus.TY.getValue().equals(v.getStatusCode()) || IdCardStatus.ZX.getValue().equals(v.getStatusCode())){

                            }else{
                                if(StringUtils.isBlank(result)){
                                    result = v.getSsCard();
                                    temp = v.getSsCard();
                                }else {
                                    if(temp.equals(v.getSsCard())){

                                    }else{
                                        result += ","+v.getSsCard();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(result)){
                AppPatientUser v =  (AppPatientUser)this.sysDao.getServiceDo().find(AppPatientUser.class,userId);
                if(v != null){
                    String[] sz = result.split(",");
                    if(sz.length > 1){
                        v.setPatientIdCardTemp(result);
                    }else{
                        v.setPatientCard(result);
                    }
                    this.sysDao.getServiceDo().modify(v);
                }
                AppMyFamily family = this.sysDao.getAppMyFamilyDao().findByBdPatientIdNo(v.getPatientIdno(),v.getId());
                if(family != null){
                    family.setMfFmCard(result);
                    this.sysDao.getServiceDo().modify(family);
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }


    public String getSecurityCardNotAsync(String idNo, String idName,String requestUserId,String requestUserName,String userType){
        String result = "";
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getSecurityCard";
            jsonParam.put("idCard",idNo);
            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),idNo,"getSecurityCard");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getSecurityCard");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<IdCardEntity> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<IdCardEntity>>() {}.getType());
                    if(ls != null && ls.size() >0){
                        String temp  = "";
                        for(int i =0;i<ls.size();i++){
                            IdCardEntity v = ls.get(i);
                            if(IdCardStatus.TY.getValue().equals(v.getStatusCode()) || IdCardStatus.ZX.getValue().equals(v.getStatusCode())){

                            }else{
                                if(StringUtils.isBlank(result)){
                                    result = v.getSsCard();
                                    temp = v.getSsCard();
                                }else {
                                    if(temp.equals(v.getSsCard())){

                                    }else{
                                        result += ","+v.getSsCard();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 社保卡
     * @param idNo
     * @param idName
     * @return
     */
    public List<IdCardEntity> getSecurityCardListAsync(String idNo, String idName,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getSecurityCard";
            jsonParam.put("idCard",idNo);
            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),idNo,"getSecurityCard");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getSecurityCard");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<IdCardEntity> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<IdCardEntity>>() {}.getType());
                    return  ls;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 网上搜索健康档案
     * @param qvo
     * @return
     */
    public List<EnterpatientEntity> getEnterpatientList(EnterpatientVo qvo,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            if(AddressType.ND.getValue().equals(qvo.getUrlType())){
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("idNo",qvo.getIdcardno());
                jsonParam.put("urlType", qvo.getUrlType());
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getHealthCareRecords";
                String str = null;
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                System.out.println(str);
                if (org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")) {
                        if (jsonall.get("entity") != null) {
                            if (!jsonall.get("entity").toString().equals("null")) {
                                JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                if(entity != null && "800".equals(entity.get("msgCode").toString())) {
                                    List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(), new TypeToken<List<AppEnterpatientEntity>>() {
                                    }.getType());
                                    if (list != null && list.size() > 0) {
                                        AppEnterpatientEntity vv = list.get(0);
                                        EnterpatientEntity vo = new EnterpatientEntity();
                                        vo.setDf_id(vv.getDf_id());
                                        vo.setName(vv.getName());
                                        vo.setCdate(vv.getCdate());
                                        vo.setIdcardno(vv.getIdcardno());
                                        vo.setTelphone(vv.getTelphone());
                                        vo.setBirthday(vv.getBirthday());
                                        vo.setSex(vv.getSex());
                                        vo.setRef_ci_id(vv.getRef_ci_id());
                                        vo.setAge(vv.getAge());
                                        vo.setCi_id(vv.getCi_id());
                                        vo.setIsgxy(vv.getIsgxy());
                                        vo.setIstnb(vv.getIstnb());
                                        vo.setIslnr(vv.getIslnr());
                                        vo.setSfyxda(vv.getSfyxda());
                                        vo.setRef_cjid(vv.getRef_cjid());
                                        vo.setJname(vv.getJname());
                                        vo.setAddress(vv.getAddress());
                                        vo.setAdress_pro(vv.getAdress_pro());
                                        vo.setAdress_city(vv.getAdress_city());
                                        vo.setAdress_county(vv.getAdress_county());
                                        vo.setAdress_rural(vv.getAdress_rural());
                                        vo.setAdress_village(vv.getAdress_village());
                                        vo.setAdrss_hnumber(vv.getAdrss_hnumber());

                                        List<EnterpatientEntity> ls = new ArrayList<>();
                                        ls.add(vo);
                                        if(ls != null && ls.size()>0){
                                            return  ls;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }else{
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("currentPage",qvo.getCurrentPage());
                jsonParam.put("ref_ci_id", qvo.getRef_ci_id());
                jsonParam.put("name", qvo.getName());
                jsonParam.put("vnum", qvo.getVnum());
                jsonParam.put("idcardno", qvo.getIdcardno());
                jsonParam.put("ccl_id", qvo.getCcl_id());
                jsonParam.put("xcsfrqfr", qvo.getXcsfrqfr());
                jsonParam.put("xcsfrqto", qvo.getXcsfrqto());
                jsonParam.put("csrqfr", qvo.getCsrqfr());
                jsonParam.put("csrqto", qvo.getCsrqto());
                jsonParam.put("birthday", qvo.getBirthday());
                jsonParam.put("urlType", qvo.getUrlType());
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getEnterpatientList";
                String str = null;

                if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                }else{
                    str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdcardno(),"getEnterpatientList");
                }
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getEnterpatientList");
                JSONObject jsonall = JSONObject.fromObject(str);
                if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                    List<EnterpatientEntity> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<EnterpatientEntity>>() {}.getType());
                    return ls;
                }
            }

        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }





    /**
     * 环信注册账号
     * @param userId
     */
//    @Async()
    public void registeredEasemob(String userId){
        try{
            SericuryUtil p = new SericuryUtil();
            userId = p.encrypt(userId);
            AppPatientUser v =  (AppPatientUser)this.sysDao.getServiceDo().find(AppPatientUser.class,p.decrypt(userId));
            if(v != null){
                boolean flag = false;
                UserInfoResult userInfoResult = JiguangJMessageUtils.getJmessageUserInfo(userId, CommonShortType.HUANGZHE.getValue());
                if(userInfoResult != null){
                    flag = true;
                }else{
                    String users = JiguangJMessageUtils.getJmessageregisterUsers(userId,v.getPatientName(),v.getPatientGender(),CommonShortType.HUANGZHE.getValue());
                    if(StringUtils.isNotBlank(users)){
                        flag = true;
                    }
                }
                if(flag){
                    v.setPatientEaseState(UserJgType.YISHEZHI.getValue());
                    this.sysDao.getServiceDo().modify(v);
                    AppSignForm form  = sysDao.getAppSignformDao().findSignFormByUser(v.getId());
                    if(form != null ){
                        if(StringUtils.isNotBlank(form.getSignTeamId())){
                            List<AppTeamMember> lsTeam = this.sysDao.getAppTeamMemberDao().findTeamId(form.getSignTeamId());
                            if(lsTeam != null && lsTeam.size() >0){
                                for(AppTeamMember t : lsTeam){
                                    this.sysDao.getSecurityCardAsyncBean().addFridenSignl(t.getMemDrId(),form.getSignPatientId());
                                }
                            }
                            AppTeam team = (AppTeam)this.sysDao.getServiceDo().find(AppTeam.class,form.getSignTeamId());
                            if(team != null) {
                                this.sysDao.getSecurityCardAsyncBean().addRoomMembers(team.getTeamEaseRoomId(),p.decrypt(userId),CommonShortType.HUANGZHE.getValue());
                            }
                        }
                    }
                }
            }else{
                AppDrUser dr =  (AppDrUser)this.sysDao.getServiceDo().find(AppDrUser.class,p.decrypt(userId));
                if(dr != null){
                    boolean flag = false;
                    UserInfoResult userInfoResult = JiguangJMessageUtils.getJmessageUserInfo(userId, CommonShortType.YISHENG.getValue());
                    if(userInfoResult != null){
                        flag = true;
                    }else{
                        String users = JiguangJMessageUtils.getJmessageregisterUsers(userId,dr.getDrName(),dr.getDrGender(),CommonShortType.YISHENG.getValue());
                        if(StringUtils.isNotBlank(users)){
                            flag = true;
                        }
                    }
                    if(flag){
                        dr.setDrEaseState(UserJgType.YISHEZHI.getValue());
                        this.sysDao.getServiceDo().modify(dr);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void registeredEasemobTemp(String userId){
        try{
            SericuryUtil p = new SericuryUtil();
            userId = p.encrypt(userId);
            AppPatientUser v =  (AppPatientUser)this.sysDao.getServiceDo().find(AppPatientUser.class,p.decrypt(userId));
            if(v != null){
                boolean flag = false;
                UserInfoResult userInfoResult = JiguangJMessageUtils.getJmessageUserInfo(userId, CommonShortType.HUANGZHE.getValue());
                if(userInfoResult != null){
                    flag = true;
                }else{
                    String users = JiguangJMessageUtils.getJmessageregisterUsers(userId,v.getPatientName(),v.getPatientGender(),CommonShortType.HUANGZHE.getValue());
                    if(StringUtils.isNotBlank(users)){
                        flag = true;
                    }
                }
                if(flag){
                    v.setPatientEaseState(UserJgType.YISHEZHI.getValue());
                    this.sysDao.getServiceDo().modify(v);
                    AppSignForm form  = sysDao.getAppSignformDao().findSignFormByUser(v.getId());
                    if(form != null ){
                        if(StringUtils.isNotBlank(form.getSignTeamId())){
                            List<AppTeamMember> lsTeam = this.sysDao.getAppTeamMemberDao().findTeamId(form.getSignTeamId());
                            if(lsTeam != null && lsTeam.size() >0){
                                for(AppTeamMember t : lsTeam){
                                    this.sysDao.getSecurityCardAsyncBean().addFridenSignlTemp(t.getMemDrId(),form.getSignPatientId());
                                }
                            }
                            AppTeam team = (AppTeam)this.sysDao.getServiceDo().find(AppTeam.class,form.getSignTeamId());
                            if(team != null) {
                                this.sysDao.getSecurityCardAsyncBean().addRoomMembersTemp(team.getTeamEaseRoomId(),p.decrypt(userId),CommonShortType.HUANGZHE.getValue());
                            }
                        }
                    }
                }
            }else{
                AppDrUser dr =  (AppDrUser)this.sysDao.getServiceDo().find(AppDrUser.class,p.decrypt(userId));
                if(dr != null){
                    boolean flag = false;
                    UserInfoResult userInfoResult = JiguangJMessageUtils.getJmessageUserInfo(userId, CommonShortType.YISHENG.getValue());
                    if(userInfoResult != null){
                        flag = true;
                    }else{
                        String users = JiguangJMessageUtils.getJmessageregisterUsers(userId,dr.getDrName(),dr.getDrGender(),CommonShortType.YISHENG.getValue());
                        if(StringUtils.isNotBlank(users)){
                            flag = true;
                        }
                    }
                    if(flag){
                        dr.setDrEaseState(UserJgType.YISHEZHI.getValue());
                        this.sysDao.getServiceDo().modify(dr);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * huan环信添加好友
     * @param userId
     * @param fridenId
     */
    @Async()
    public void addFridenSignl(String userId,String fridenId){
        try{
            SericuryUtil p = new SericuryUtil();
            userId = p.encrypt(userId);
            fridenId = p.encrypt(fridenId);
//            EasemobIMUsers ease = new EasemobIMUsers();
//            Object result =  ease.addFriendSingle(userId,fridenId);
            ResponseWrapper responseWrapper = JiguangJMessageUtils.getJmessageaddCrossFriends(userId,fridenId,PropertiesUtil.getConfValue("appKeyPatient"),CommonShortType.YISHENG.getValue());
            log.info(JsonUtil.toJson(responseWrapper));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addFridenSignlTemp(String userId,String fridenId){
        try{
            SericuryUtil p = new SericuryUtil();
            userId = p.encrypt(userId);
            fridenId = p.encrypt(fridenId);
            ResponseWrapper responseWrapper = JiguangJMessageUtils.getJmessageaddCrossFriends(userId,fridenId,PropertiesUtil.getConfValue("appKeyPatient"),CommonShortType.YISHENG.getValue());
            log.info(JsonUtil.toJson(responseWrapper));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //创建群
    @Async()
    public void addGroup(AppTeam appTeam){
//        String groupId = "";
        try{
            SericuryUtil p = new SericuryUtil();
            String userId = p.encrypt(appTeam.getTeamDrId());
//            io.swagger.client.model.Group payload = new io.swagger.client.model.Group()
//                    .groupname(appTeam.getTeamName()+"(团队)")
//                    .desc(appTeam.getTeamName()+"(团队)")
//                    ._public(true)
//                    .approval(true)
//                    .maxusers(2000)
//                    .owner(userId);
//            EasemobChatGroup group = new EasemobChatGroup();
//            Object result = group.createChatGroup(payload);
            CreateGroupResult result = JiguangJMessageUtils.getJmessageCreateGroup(userId,appTeam.getTeamName()+"(团队)",null,CommonShortType.YISHENG.getValue());
            if(result != null){
                String groupId = String.valueOf(result.getGid());
                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,appTeam.getId());
                team.setTeamEaseGroupId(groupId);
                team.setTeamEaseGroupName(appTeam.getTeamName()+"(团队)");
                this.sysDao.getServiceDo().modify(team);
                List<AppTeamMember> ls = this.sysDao.getAppTeamMemberDao().findTeamId(team.getId(),team.getTeamDrId());
                if(ls != null && ls.size() >0){
                    for(AppTeamMember v : ls){
                        this.addGroupMembersTemp(groupId,p.encrypt(v.getMemDrId()));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        return groupId;
    }

    //创建群
    public void addGroupTemp(AppTeam appTeam){
        try{
            SericuryUtil p = new SericuryUtil();
            String userId = p.encrypt(appTeam.getTeamDrId());
//            io.swagger.client.model.Group payload = new io.swagger.client.model.Group()
//                    .groupname(appTeam.getTeamName()+"(团队)")
//                    .desc(appTeam.getTeamName()+"(团队)")
//                    ._public(true)
//                    .approval(true)
//                    .maxusers(2000)
//                    .owner(userId);
//            EasemobChatGroup group = new EasemobChatGroup();
//            Object result = group.createChatGroup(payload);
//            if(result != null){
//                JSONObject jsonAll = JSONObject.fromObject(result.toString());
//                String data = jsonAll.get("data").toString();
//                JSONObject jsonObject = JSONObject.fromObject(data);
//                String groupId = jsonObject.get("groupid").toString();
//                System.out.println(groupId);
//                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,appTeam.getId());
//                team.setTeamEaseGroupId(groupId);
//                team.setTeamEaseGroupName(appTeam.getTeamName()+"(团队)");
//                this.sysDao.getServiceDo().modify(team);
//                List<AppTeamMember> ls = this.sysDao.getAppTeamMemberDao().findTeamId(team.getId(),team.getTeamDrId());
//                if(ls != null && ls.size() >0){
//                    for(AppTeamMember v : ls){
//                        this.addGroupMembersTemp(groupId,p.encrypt(v.getMemDrId()));
//                    }
//                }
//            }
            CreateGroupResult result = JiguangJMessageUtils.getJmessageCreateGroup(userId,appTeam.getTeamName()+"(团队)",null,CommonShortType.YISHENG.getValue());
            if(result != null){
                String groupId = String.valueOf(result.getGid());
                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,appTeam.getId());
                team.setTeamEaseGroupId(groupId);
                team.setTeamEaseGroupName(appTeam.getTeamName()+"(团队)");
                this.sysDao.getServiceDo().modify(team);
                List<AppTeamMember> ls = this.sysDao.getAppTeamMemberDao().findTeamId(team.getId(),team.getTeamDrId());
                if(ls != null && ls.size() >0){
                    for(AppTeamMember v : ls){
                        this.addGroupMembersTemp(groupId,p.encrypt(v.getMemDrId()));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //添加成员
    public void addGroupMembersTemp(String groupId,String userId){
        try{
//            EasemobChatGroup group = new EasemobChatGroup();
//            Object result = group.addSingleUserToChatGroup(groupId,userId);
            String[] addList = new String[]{userId};
            String result = JiguangJMessageUtils.getJmessageaddOrRemoveMembers(groupId,addList,null,CommonShortType.YISHENG.getValue());
            log.error(groupId+"群,添加成员:"+userId+"=="+result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //添加成员
    @Async()
    public void addGroupMembers(String groupId,String userId){
        try{
            SericuryUtil p = new SericuryUtil();
//            EasemobChatGroup group = new EasemobChatGroup();
//            group.addSingleUserToChatGroup(groupId,p.encrypt(userId));
            String[] addList = new String[]{p.encrypt(userId)};
            String result = JiguangJMessageUtils.getJmessageaddOrRemoveMembers(groupId,addList,null,CommonShortType.YISHENG.getValue());
            log.error(groupId+"群,添加成员:"+userId+"=="+result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //删除成员
    @Async()
    public void removeGroupMembers(String groupId,String userId){
        try{
            SericuryUtil p = new SericuryUtil();
//            EasemobChatGroup group = new EasemobChatGroup();
//            group.removeSingleBlockUserFromChatGroup(groupId,p.encrypt(userId));
            String[] removeList = new String[]{p.encrypt(userId)};
            String result = JiguangJMessageUtils.getJmessageaddOrRemoveMembers(groupId,null,removeList,CommonShortType.YISHENG.getValue());
            log.error(groupId+"群,删除成员:"+userId+"=="+result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //删除群
    @Async()
    public void delGroup(String groupId){
        try{
//            SericuryUtil p = new SericuryUtil();
//            EasemobChatGroup group = new EasemobChatGroup();
//            group.deleteChatGroup(groupId);
            JiguangJMessageUtils.getJmessageDelGroup(groupId,CommonShortType.YISHENG.getValue());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 创建聊天室
     */
    @Async()
    public void addRoom(AppTeam appTeam,String type){
        try{
            SericuryUtil p = new SericuryUtil();
            String userId = p.encrypt(appTeam.getTeamDrId());
//            io.swagger.client.model.Group payload = new io.swagger.client.model.Group()
//                    .groupname(appTeam.getTeamName()+"(居民)")
//                    .desc(appTeam.getTeamName()+"(居民)")
//                    ._public(true)
//                    .approval(true)
//                    .maxusers(2000)
//                    .owner(userId);
//            EasemobChatGroup group = new EasemobChatGroup();
//            Object result = group.createChatGroup(payload);
            CreateGroupResult result = JiguangJMessageUtils.getJmessageCreateGroup(userId,appTeam.getTeamName()+"(居民)",null,CommonShortType.YISHENG.getValue());
            if(result != null){
                String groupId = String.valueOf(result.getGid());
                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,appTeam.getId());
                team.setTeamEaseRoomId(groupId);
                team.setTeamEaseRoomName(appTeam.getTeamName()+"(居民)");
                this.sysDao.getServiceDo().modify(team);
                List<AppTeamMember> ls = this.sysDao.getAppTeamMemberDao().findTeamId(team.getId(),team.getTeamDrId());
                if(ls != null && ls.size() >0){
                    for(AppTeamMember v : ls){
                        this.addRoomMembers(groupId,v.getMemDrId(),type);
                    }
                }
            }
//            if(result != null){
//                JSONObject jsonAll = JSONObject.fromObject(result.toString());
//                String data = jsonAll.get("data").toString();
//                JSONObject jsonObject = JSONObject.fromObject(data);
//                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,appTeam.getId());
//                team.setTeamEaseRoomId(jsonObject.get("groupid").toString());
//                team.setTeamEaseRoomName(appTeam.getTeamName()+"(居民)");
//                this.sysDao.getServiceDo().modify(team);
//                List<AppTeamMember> ls = this.sysDao.getAppTeamMemberDao().findTeamId(team.getId(),team.getTeamDrId());
//                if(ls != null && ls.size() >0){
//                    for(AppTeamMember v : ls){
//                        this.addGroupMembersTemp(jsonObject.get("groupid").toString(),p.encrypt(v.getMemDrId()));
//                    }
//                }
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void addRoomTeamp(AppTeam appTeam,String type){
        try{
            SericuryUtil p = new SericuryUtil();
            String userId = p.encrypt(appTeam.getTeamDrId());
//            io.swagger.client.model.Group payload = new io.swagger.client.model.Group()
//                    .groupname(appTeam.getTeamName()+"(居民)")
//                    .desc(appTeam.getTeamName()+"(居民)")
//                    ._public(true)
//                    .approval(true)
//                    .maxusers(2000)
//                    .owner(userId);
//            EasemobChatGroup group = new EasemobChatGroup();
//            Object result = group.createChatGroup(payload);

            CreateGroupResult result = JiguangJMessageUtils.getJmessageCreateGroup(userId,appTeam.getTeamName()+"(居民)",null,CommonShortType.YISHENG.getValue());
            if(result != null){
                String groupId = String.valueOf(result.getGid());
                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,appTeam.getId());
                team.setTeamEaseRoomId(groupId);
                team.setTeamEaseRoomName(appTeam.getTeamName()+"(居民)");
                this.sysDao.getServiceDo().modify(team);
                List<AppTeamMember> ls = this.sysDao.getAppTeamMemberDao().findTeamId(team.getId(),team.getTeamDrId());
                if(ls != null && ls.size() >0){
                    for(AppTeamMember v : ls){
                        this.addRoomMembersTemp(groupId,v.getMemDrId(),type);
                    }
                }
            }

//            if(result != null){
//                JSONObject jsonAll = JSONObject.fromObject(result.toString());
//                String data = jsonAll.get("data").toString();
//                JSONObject jsonObject = JSONObject.fromObject(data);
//                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,appTeam.getId());
//                team.setTeamEaseRoomId(jsonObject.get("groupid").toString());
//                team.setTeamEaseRoomName(appTeam.getTeamName()+"(居民)");
//                this.sysDao.getServiceDo().modify(team);
//                List<AppTeamMember> ls = this.sysDao.getAppTeamMemberDao().findTeamId(team.getId(),team.getTeamDrId());
//                if(ls != null && ls.size() >0){
//                    for(AppTeamMember v : ls){
//                        this.addGroupMembersTemp(jsonObject.get("groupid").toString(),p.encrypt(v.getMemDrId()));
//                    }
//                }
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //添加成员
    public void addRoomMembersTemp(String roomId,String userId,String type){
        try{
            SericuryUtil p = new SericuryUtil();
//            EasemobChatGroup group = new EasemobChatGroup();
//            Object result = group.addSingleUserToChatGroup(roomId,userId);
//            System.out.println(result);
            String[] addList = new String[]{p.encrypt(userId)};
            if(type.equals(CommonShortType.HUANGZHE.getValue())){
                ResponseWrapper result = JiguangJMessageUtils.getJmessageaddOrRemoveCrossMembers
                        (roomId,addList,null,PropertiesUtil.getConfValue("appKeyPatient"), CommonShortType.YISHENG.getValue());
                log.error(roomId+"群,添加成员:"+userId+"=="+JsonUtil.toJson(result));
            }else if(type.equals(CommonShortType.YISHENG.getValue())){
                String result = JiguangJMessageUtils.getJmessageaddOrRemoveMembers(roomId,addList,null,CommonShortType.YISHENG.getValue());
                log.error(roomId+"群,添加成员:"+userId+"=="+JsonUtil.toJson(result));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Async()
    public void addRoomMembers(String roomId,String userId,String type){
        try{
            SericuryUtil p = new SericuryUtil();
//            EasemobChatGroup group = new EasemobChatGroup();
//            Object result = group.addSingleUserToChatGroup(roomId,userId);
            String[] addList = new String[]{p.encrypt(userId)};
            if(type.equals(CommonShortType.HUANGZHE.getValue())){
                ResponseWrapper result = JiguangJMessageUtils.getJmessageaddOrRemoveCrossMembers
                        (roomId,addList,null,PropertiesUtil.getConfValue("appKeyPatient"), CommonShortType.YISHENG.getValue());
                log.error(roomId+"群,添加成员:"+userId+"=="+JsonUtil.toJson(result));
            }else if(type.equals(CommonShortType.YISHENG.getValue())){
                String result = JiguangJMessageUtils.getJmessageaddOrRemoveMembers(roomId,addList,null,CommonShortType.YISHENG.getValue());
                log.error(roomId+"群,添加成员:"+userId+"=="+JsonUtil.toJson(result));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //删除成员
    @Async()
    public void removeRoomMembers(String roomId,String userId,String type){
        try{
            SericuryUtil p = new SericuryUtil();
//            EasemobChatGroup room = new EasemobChatGroup();
//            room.removeSingleBlockUserFromChatGroup(roomId,p.encrypt(userId));
            String[] removeList = new String[]{p.encrypt(userId)};
            if(type.equals(CommonShortType.HUANGZHE.getValue())){
                ResponseWrapper result = JiguangJMessageUtils.getJmessageaddOrRemoveCrossMembers
                        (roomId,null,removeList,PropertiesUtil.getConfValue("appKeyPatient"), CommonShortType.YISHENG.getValue());
                log.error(roomId+"群,删除成员:"+userId+"=="+JsonUtil.toJson(result));
            }else if(type.equals(CommonShortType.YISHENG.getValue())){
                String result = JiguangJMessageUtils.getJmessageaddOrRemoveMembers(roomId,null,removeList,CommonShortType.YISHENG.getValue());
                log.error(roomId+"群,删除成员:"+userId+"=="+JsonUtil.toJson(result));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //删除群
    @Async()
    public void delRoom(String roomId){
        try{
//            SericuryUtil p = new SericuryUtil();
//            EasemobChatGroup room = new EasemobChatGroup();
//            System.out.println(room.deleteChatGroup(roomId));
            JiguangJMessageUtils.getJmessageDelGroup(roomId,CommonShortType.YISHENG.getValue());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 医生端极光推送
     * @param alias
     * @param alert
     * @param content
     */
    @Async()
    public void push_dr(String alias, String alert,String content){
        JiguangPushUtils.push_dr(alias,alert,content);
//        JiguangPushUtils.push_dr(alias,alert,content,"1");
//        JiguangPushUtils.push_dr(alias,alert,content,"2");

    }

    /**
     * 患者端极光推送
     * @param alias
     * @param alert
     * @param content
     */
    @Async()
    public void push_patient(String alias, String alert,String content){
        JiguangPushUtils.push_patient(alias,alert,content);
//        JiguangPushUtils.push_patient(alias,alert,content,"1");
//        JiguangPushUtils.push_patient(alias,alert,content,"2");
    }

    /**
     * 标签极光推送
     * @param tag
     * @param alert
     * @param content
     */
    @Async()
    public void push_tag(String tag, String alert,String content){
        JiguangPushUtils.push_tag(tag,alert,content);
//        JiguangPushUtils.push_tag(tag,alert,content,"1");
//        JiguangPushUtils.push_tag(tag,alert,content,"2");
    }

    /**
     * tv端极光推送
     * @param alias
     * @param alert
     * @param content
     */
    @Async()
    public void push_tv(String alias, String alert,String content){
        JiguangPushUtils.push_tv(alias,alert,content);
//        JiguangPushUtils.push_patient(alias,alert,content,"1");
//        JiguangPushUtils.push_patient(alias,alert,content,"2");
    }

    public String registeredEasemobTv(String userId,String userName){
        String result = "0";
        try{
            UserInfoResult userInfoResult = JiguangJMessageUtils.getJmessageUserInfo(userId, CommonShortType.ZNKT.getValue());
            if(userInfoResult != null){
                result = "1";
            }else{
                String users = JiguangJMessageUtils.getJmessageregisterUsers(userId,userName,null,CommonShortType.ZNKT.getValue());
                result = "1";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 标签极光推送（定时和实时）
     * @param tag
     * @param tagAnd
     * @param alert
     * @param content
     */
    @Async()
    public void push_tag_tagANd(String tag,String tagAnd, String alert,String content){
//        if(type.equals("1")){
            JiguangPushUtils.push_tag_tagAnd(tag,tagAnd,alert,content);
//            JiguangPushUtils.push_tag_tagAnd(tag,tagAnd,alert,content,"1");
//            JiguangPushUtils.push_tag_tagAnd(tag,tagAnd,alert,content,"2");
//        }else{
//            JiguangPushUtils.push_tag_tagAnd(tag,tagAnd,alert,content,"0",title,time);
//            JiguangPushUtils.push_tag_tagAnd(tag,tagAnd,alert,content,"1",title,time);
//        }
    }


    /**
     * 血压设备闹钟
     * @param imei
     * @param clockOne
     * @param clockTwo
     * @param clockThree
     * @param clockFour
     */
    @Async()
    public void pressureClock(String imei,String clockOne,String clockTwo,String clockThree,String clockFour) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String clock1 = "";
            String clock2 = "";
            String clock3 = "";
            String clock4 = "";
            if(StringUtils.isNotBlank(clockOne)){
                clock1 = clockOne+":0";
            }
            if(StringUtils.isNotBlank(clockTwo)){
                clock2 = clockTwo+":0";
            }
            if(StringUtils.isNotBlank(clockThree)){
                clock3 = clockThree+":0";
            }
            if(StringUtils.isNotBlank(clockFour)){
                clock4 = clockFour+":0";
            }
            String url = PropertiesUtil.getConfValue("pressureClockUrl")+"/hdbp.action?act=changeClolcks";
            List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("imei",imei));
		    params.add(new BasicNameValuePair("clock1",clock1));
		    params.add(new BasicNameValuePair("clock2",clock2));
            params.add(new BasicNameValuePair("clock3",clock3));
            params.add(new BasicNameValuePair("clock4",clock4));
            params.add(new BasicNameValuePair("clock5","clock5"));
            String str1 = HtmlUtils.getInstance().executeResponse(client, "post", url, params,"utf-8");
//            System.out.println(str1);
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 查询计生家庭接口
     * @param idNo
     * @param idName
     * @return
     */
    public List<AppFamilyInfo> getFetchFamily(String idNo, String idName,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getFetchFamily";
            jsonParam.put("idNo",idNo);
            if(StringUtils.isNotBlank(idName)) {
                jsonParam.put("name",idName);
            }

            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),idNo,"getFetchFamily");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getFetchFamily");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<AppFamilyInfo> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<AppFamilyInfo>>() {}.getType());
                    return  ls;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 计生人口接口
     * @param year
     * @param month
     * @param addr
     * @return
     */
    public AppPlannedPeopleEntity getPlannedPeople(String year,String month, String addr){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getPlannedPeople";
            jsonParam.put("year",year);
            jsonParam.put("month",month);
            jsonParam.put("addr",addr);
            String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("vo") != null){
                    AppPlannedPeopleEntity v = JsonUtil.fromJson(jsonAll.get("vo").toString(), AppPlannedPeopleEntity.class);
                    return  v;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }




    /**
     * 是否在计免库存在
     * @return
     */
    public String getFmqBymykh(String mykh,String name,String idNo,String requestUserId,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getFmqBymykh";
            jsonParam.put("mykh",mykh);
            jsonParam.put("name",name);

            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,name,jsonParam.toString(),idNo,"getFmqBymykh");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,name,str1, userType,"getFmqBymykh");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("entity") != null){
                    return jsonAll.get("entity").toString();
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 计免未种
     * @return
     */
    public List<AppAllreservedWzxx> getWzymBymykh(String mykh,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getWzymBymykh";
            jsonParam.put("mykh",mykh);
            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),mykh,"getWzymBymykh");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getWzymBymykh");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<AppAllreservedWzxx> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<AppAllreservedWzxx>>() {}.getType());
                    return ls;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 计免已种
     * @return
     */
    public List<AppAllreservedYzxx> getYzYmBymykh(String mykh,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getYzYmBymykh";
            jsonParam.put("mykh",mykh);
            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),mykh,"getYzYmBymykh");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getYzYmBymykh");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<AppAllreservedYzxx> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<AppAllreservedYzxx>>() {}.getType());
                    return ls;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 疫苗接口
     * @return
     */
    public List<YiMiaoListJk> getInoculateYmJk(String ymlx,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getInoculateYmJk";
            jsonParam.put("ymlx",ymlx);
            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),ymlx,"getInoculateYmJk");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getInoculateYmJk");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<YiMiaoListJk> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<YiMiaoListJk>>() {}.getType());
                    return ls;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 接种门诊
     * @return
     */
    public List<DeptFqJk> getInoculateJzMz(String ymbm,String jzrq,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getInoculateJzMz";
            jsonParam.put("ymbm",ymbm);
            jsonParam.put("jzrq",jzrq);

            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),ymbm,"getInoculateJzMz");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getInoculateJzMz");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<DeptFqJk> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<DeptFqJk>>() {}.getType());
                    return ls;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 儿童信息
     * @return
     */
    public AllreservedJzEt findErtongByMykh(String etMykh,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=findErtongByMykh";
            jsonParam.put("mykh",etMykh);

            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),etMykh,"findErtongByMykh");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"findErtongByMykh");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("vo") != null){
                    AllreservedJzEt vo = JsonUtil.fromJson(jsonAll.get("vo").toString(), AllreservedJzEt.class);
                    return vo;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询预约日期
     * @return
     */
    public List<JieZhongRi> getInoculateJzYyRqMykh(String mykh,String ymbm,String jzMzId,String jzzc,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getInoculateJzYyRqMykh";
            jsonParam.put("mykh",mykh);
            jsonParam.put("ymbm",ymbm);
            jsonParam.put("jzMzId",jzMzId);
            jsonParam.put("jzzc",jzzc);
            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),mykh,"getInoculateJzYyRqMykh");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getInoculateJzYyRqMykh");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<JieZhongRi> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<JieZhongRi>>() {}.getType());
                    return ls;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询预约时间段
     * @return
     */
    public YuyueSjD getInoculateDateYyMykh(String mykh,String ymbm,String jzMzId,
                                           String jzYyrq,String jzYyxx,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getInoculateDateYyMykh";
            jsonParam.put("mykh",mykh);
            jsonParam.put("ymbm",ymbm);
            jsonParam.put("jzMzId",jzMzId);
            jsonParam.put("jzYyRq",jzYyrq);
            jsonParam.put("jzYyxx",jzYyxx);
            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),mykh,"getInoculateDateYyMykh");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getInoculateDateYyMykh");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("msgCode").toString().equals("900")){
                    YuyueSjD vo = new YuyueSjD();
                    vo.setEntity(jsonAll.get("entity").toString());
                    return vo;
                }else{
                    if(jsonAll.get("vo") != null){
                        YuyueSjD vo = JsonUtil.fromJson(jsonAll.get("vo").toString(), YuyueSjD.class);
                        return vo;
                    }
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     *确认预约
     * @return
     */
    public String getInoculateYuyueMykh(String mykh,String ymbm,
                     String jzMzId,String jzYyrq,String jzYyxx,String jzzc,String sfmf,String yySJd,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getInoculateYuyueMykh";
            jsonParam.put("mykh",mykh);
            jsonParam.put("ymbm",ymbm);
            jsonParam.put("jzMzId",jzMzId);
            jsonParam.put("jzYyRq",jzYyrq);
            jsonParam.put("jzYyxx",jzYyxx);
            jsonParam.put("jzzc",jzzc);
            jsonParam.put("sfMf",sfmf);
            jsonParam.put("yySjd",yySJd);
            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),mykh,"getInoculateYuyueMykh");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getInoculateYuyueMykh");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("entity") != null){
                    return jsonAll.get("entity").toString();
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 我的预约
     * @return
     */
    public List<JzYuyueJk> getInoculateMyYyMykh(String mykh,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getInoculateMyYyMykh";
            jsonParam.put("mykh",mykh);
            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),mykh,"getInoculateMyYyMykh");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getInoculateMyYyMykh");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<JzYuyueJk> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<JzYuyueJk>>() {}.getType());
                    return ls;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 我的预约
     * @return
     */
    public List<JzYuyueJk> getInoculateQxyyMykh(String yyId,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getInoculateQxyyMykh";
            jsonParam.put("yyId",yyId);
            String str1 = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            }else{
                str1 = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),yyId,"getInoculateQxyyMykh");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str1, userType,"getInoculateQxyyMykh");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<JzYuyueJk> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<JzYuyueJk>>() {}.getType());
                    return ls;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询计免儿童全程标准方案
     * @return
     */
    public List<AlgorithEpiVoList> getInoculateMySchByBth(String childBirth){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getInoculateMySchByBth";
            jsonParam.put("childBirth",childBirth);
            String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("rows") != null){
                    List<AlgorithEpiVoList> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<AlgorithEpiVoList>>() {}.getType());
                    return ls;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 健康档案同步
     */
    @Async
    public void getHealthInfoPatientSynchro(String idNo,String card,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            boolean result = true;
            String type = "1;2;3;5";
            String[] typeString = type.split(";");
            String state = PropertiesUtil.getConfValue("openTheInterface");
            if(typeString != null){
                for(String s : typeString){
                    AppHealthData data = sysDao.getAppHealthDataDao().findByType(s,idNo);
                    if(data != null){
                        Calendar cal = Calendar.getInstance();
                        result = ExtendDateUtil.sameDate(data.getHealthDate().getTime(),cal.getTime());
                    }
                    if(!result){
                        String methods = "getHealthInfoPatient";
                        JSONObject jsonParam = new JSONObject();
                        String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getHealthInfoPatient";
                        jsonParam.put("idNo",idNo);
                        jsonParam.put("card",card);
                        jsonParam.put("type", s);
                        if(s.equals("5")){
                            urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getHealthInfoPatientExamReport";
                            methods = "getHealthInfoPatientExamReport";
                        }
                        String str = null;
                        if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                            str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                        }else {
                            str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),idNo,methods);
                        }
                        sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,methods);
                        JSONObject jsonall = JSONObject.fromObject(str);
                        sysDao.getAppHealthDataDao().addHealthDataImplements(jsonall,idNo,card,s,requestUserId,requestUserName,userType);
                    }
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * @param card
     * @param organizationCode
     * @param ghh000
     * @param patientId
     * @param areaCode
     * @param method
     */
    public String getHealthInSynchro(String card,String organizationCode,String ghh000,String patientId,String areaCode,String method,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("card", card);
            jsonParam.put("organizationCode", organizationCode);
            jsonParam.put("ghh000", ghh000);
            jsonParam.put("patientId", patientId);
            jsonParam.put("areaCode", areaCode);
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act="+method;
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),patientId,method);
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,method);
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  null;
    }


    /**
     *
     * @param card
     * @param idNo
     * @param type
     * @return
     */
    public String getHealthInfoSynchro(String card,String idNo,String type,String method,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            if(StringUtils.isNotBlank(type)){
                jsonParam.put("type", type);
            }
            jsonParam.put("idNo", idNo);
            jsonParam.put("card", card);
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act="+method;
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),idNo,method);
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,method);
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  null;
    }

    /**
     * 下载单个或者多个居民档案
     * @param dfId
     * @param idNo
     * @param type
     * @return
     */
    public String getResidentRecords(String dfId,String idNo,String type,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            if(StringUtils.isNotBlank(dfId)) {
                jsonParam.put("df_id",dfId);
            }
            if(StringUtils.isNotBlank(idNo)) {
                jsonParam.put("idNo",idNo);
            }
            jsonParam.put("urlType",type);

            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getResidentRecords";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),idNo,"getResidentRecords");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getResidentRecords");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  null;
    }

    /**
     * 修改健康三明用户激活信息
     * @return
     */
    @Async
    public String activeJksm(String mobile,String password,String healthCard,String userName,String idCard,String userFace,String userSex,String userAge){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            if(StringUtils.isNotBlank(mobile)){
                String urlLogin = PropertiesUtil.getConfValue("jksmUrl") + "api/user/jkyApp_register.jksm";
               List<NameValuePair> params = new ArrayList<NameValuePair>();
               params.add(new BasicNameValuePair("mobile",mobile));
               params.add(new BasicNameValuePair("password",password));
               params.add(new BasicNameValuePair("healthCard",healthCard));
               params.add(new BasicNameValuePair("userName",userName));
               params.add(new BasicNameValuePair("idCard",idCard));
               params.add(new BasicNameValuePair("userFace",userFace));
               params.add(new BasicNameValuePair("userSex",userSex));
               params.add(new BasicNameValuePair("userAge",userAge));
                String res = HtmlUtils.getInstance().executeResponse(client,"post",urlLogin,params,"utf-8");
//                String result = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam, "utf-8");
                return res;
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  null;
    }

    private static int allPage = 0;
    private static int page = 0;

    public void testSm(){
        try{
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("STATE","0");
            if(allPage == 0){
                String sqlCount = "SELECT count(1) from APP_TEST_SM WHERE STATE = :STATE";
                int allCount = sysDao.getServiceDo().findCount(sqlCount,map);
                allPage  =(allCount+500-1)/500;
            }
            if(allPage != 0){
                for (int i = 0; i < allPage; i++) {
                    page++;
                    System.out.println("循环======" + page);
                    sysDao.getSecurityCardAsyncBean().upDateSm(page);
                }
            }
        }catch (Exception e){

        }
    }

    @Async
    public void upDateSm(int upage){
        try{
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("STATE","0");
            String sql = "SELECT * from APP_TEST_SM WHERE STATE = :STATE";
            CommConditionVo qvo=new CommConditionVo();
            qvo.setPageStartNo(upage);
            qvo.setPageSize(500);
            List<Map> ls = sysDao.getServiceDo().findSqlMap(sql,map,qvo);
            System.out.println("ls++"+ls.size());
            if(ls != null && ls.size() >0){
                for(Map p : ls){
                    String id = p.get("ID").toString();
                    String hospId = p.get("HOSP_ID").toString();
                    String drId = p.get("DR_ID").toString();
                    String teamId = null;
                    if(p.get("TEAM_ID") != null){
                        teamId = p.get("TEAM_ID").toString();
                    }
                    String areaCode = p.get("AREA_CODE").toString();
                    String idNo = p.get("IDNO").toString();
                    String tel = null;
                    if(p.get("TEL") != null){
                        tel = p.get("TEL").toString();
                    }
                    String signDate = p.get("SIGN_DATE").toString();
                    String signType = p.get("SIGN_TYPE").toString();
                    String state = p.get("STATE").toString();
                    String name = p.get("NAMES").toString();
                    String age = null;
                    String gender = null;
                    String patientId = null;
                    AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,hospId);
                    AppDrUser user = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,drId);
                    AppTeam team = null;
                    if(StringUtils.isNotBlank(teamId)){
                        team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,teamId);
                    }else {
                        team = sysDao.getAppTeamDao().findHospDrId(hospId,drId);
                        if(team != null) {
                            teamId = team.getId();
                        }
                    }
                    if(team == null){
                        team = new AppTeam();
                        team.setTeamDrId(drId);
                        team.setTeamCode(user.getDrCode());
                        team.setTeamCreateTime(Calendar.getInstance());
                        team.setTeamHospId(hospId);
                        team.setTeamDrName(user.getDrName());
                        team.setTeamHospName(dept.getHospName());
                        team.setTeamName(user.getDrName()+"服务团队");
                        team.setTeamState("1");
                        team.setTeamTel(user.getDrTel());
                        team.setTeamType("0");
                        sysDao.getServiceDo().add(team);
                        AppTeamMember men = new AppTeamMember();
                        men.setMemDrId(team.getTeamDrId());
                        men.setMemDrName(team.getTeamDrName());
                        men.setMemState("1");
                        men.setMemWorkType("3");
                        men.setMemTeamid(team.getId());
                        men.setMemTeamName(team.getTeamName());
                        sysDao.getServiceDo().add(men);
                        teamId = team.getId();
                    }

                    List<AppPatientUser> puser= sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",idNo);
                    if(puser!=null && !puser.isEmpty()){
                        patientId = puser.get(0).getId();//用户存在 替换成当前用户
                        name = puser.get(0).getPatientName();
                        gender = puser.get(0).getPatientGender();
                        age=puser.get(0).getPatientAge();
                    }else{
                        AppPatientUser wpuser=new AppPatientUser();
                        wpuser.setPatientName(name);
                        Map<String,Object> idNos = new HashMap<String,Object>();
                        if(idNo.trim().length() == 18){
                            idNos = CardUtil.getCarInfo(idNo.trim());
                            wpuser.setPatientIdno(idNo.toUpperCase());
                            wpuser.setPatientPwd(Md5Util.MD5(idNo.substring(idNo.length()-6,idNo.length())));
                        }else if(idNo.trim().length() == 15){
                            idNos = CardUtil.getCarInfo15W(idNo.trim());
                            wpuser.setPatientIdno(idNo.toUpperCase());
                            wpuser.setPatientPwd(Md5Util.MD5(idNo.substring(idNo.length()-6,idNo.length())));
                        }
                        if(StringUtils.isNotBlank(wpuser.getPatientName())) {
                            wpuser.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(wpuser.getPatientName()));
                        }
                        if(idNos.get("age") != null){
                            String birthday = idNos.get("birthday").toString();
                            age =AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                            wpuser.setPatientAge(age);
                        }
                        if(idNos.get("sex") != null) {
                            gender = idNos.get("sex").toString();
                            wpuser.setPatientGender(idNos.get("sex").toString());
                        }
                        if(idNos.get("birthday") != null) {
                            wpuser.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
                        }
                        wpuser.setPatientUpHpis(UserUpHpisType.WEIJIHUO.getValue());//用户未激活
                        sysDao.getServiceDo().add(wpuser);
                        patientId = wpuser.getId();
                    }

                    //签约----------------------------------------------
                    System.out.println("patientId--"+patientId+"--teamid--"+teamId+"--drId--"+drId+"---hospId---"+hospId);
                    AppSignBatch batch=new AppSignBatch();//批次
                    AppSignForm form=new AppSignForm();//签约单
                    batch.setBatchCreateDate(Calendar.getInstance());
                    batch.setBatchTeamId(teamId);
                    batch.setBatchTeamName(team.getTeamName());
                    batch.setBatchCreatePersid(patientId);
                    batch.setBatchPatientName(name);
                    //组织批次号
                    if(dept!=null && StringUtils.isNotBlank(dept.getHospAreaCode())) {
                        AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
                        if(serial!=null) {
                            Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),"");
                            serial.setSerialNo(bcnum.get("old").toString());
                            sysDao.getServiceDo().modify(serial);
                            batch.setBatchNum(bcnum.get("new").toString());//批次号
                        }
                    }
                    //
                    sysDao.getServiceDo().add(batch);
                    form.setSignBatchId(batch.getId());
                    //组织编码
                    if(dept!=null && dept.getHospAreaCode()!=null) {
                        form.setSignHospId(dept.getId());
                        form.setSignAreaCode(dept.getHospAreaCode());
                        AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
                        if (serialSign != null) {
                            Map<String,Object> sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo(),"");
                            serialSign.setSerialNo(sinum.get("old").toString());
                            sysDao.getServiceDo().modify(serialSign);
                            form.setSignNum(sinum.get("new").toString());//签约编码
                        }
                    }
                    //
                    form.setSignPatientId(patientId);
                    form.setSignDate(ExtendDate.getCalendar(signDate));
                    form.setSignPayState("0");//0：未缴费
                    form.setSignType("1");//1家庭签约
                    form.setSignTeamId(teamId);
                    form.setSignTeamName(team.getTeamName());
                    form.setSignWay("2");//医生代签
                    form.setSignState("2");//已签约
                    form.setSignFromDate(form.getSignDate());
                    form.setSignPatientGender(gender);
                    if(StringUtils.isNotBlank(age)) {
                        form.setSignPatientAge(Integer.parseInt(age));
                    }
                    Calendar end= Calendar.getInstance();
                    end.setTime(form.getSignDate().getTime());
                    end.add(Calendar.YEAR,1);
                    end.add(Calendar.DATE,-1);
                    form.setSignToDate(end);
                    form.setSignDrId(drId);
                    form.setSignContractState("0");//1是 0否
                    form.setSignGreenState("0");//1是 0否
                    form.setSignYellowState("0");//1是 0否
                    form.setSignRedState("0");//1是 0否
//					form.setSignsJjType(vo.getSignsJjType());
                    form.setUpHpis("2");//数据来源web
                    form.setSignHealthGroup("199");
                    sysDao.getServiceDo().add(form);

                    //改服务人群为多选
                    if(StringUtils.isNotBlank(signType)){
                        String[] groups = signType.split(",");
                        if (groups != null && groups.length > 0) {
                            form.setSignPersGroup(groups[0]);//支持之前版
                            sysDao.getServiceDo().modify(form);
                            sysDao.getAppLabelGroupDao().addLabel(groups,form.getId(),form.getSignTeamId(),form.getSignAreaCode(), LabelManageType.FWRQ.getValue());
                        }
                    }
                    map = new HashMap<>();
                    map.put("STATE","1");
                    map.put("ID",id);
                    sql = "UPDATE APP_TEST_SM t SET t.STATE = :STATE WHERE ID = :ID";
                    sysDao.getServiceDo().update(sql,map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 初始化全省统计数据
     */
    public void appInitialiseTotal(String type,String date){
        try{
            List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findByManageCont();
            List<String> ls = new ArrayList<String>();
//            String startDate = sysDao.getAppManageCountDao().findSignFirstDate();
            if(type.equals("1")){
                String startDate = date;
                String endDate = ExtendDate.getYMD(Calendar.getInstance());
                ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            }else{
                ls = sysDao.getAppManageCountDao().findSignDate(date);
            }
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getAppManageCountDao().totalManageCount(s,lsTeam);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Async
    public void totalManageCount(String date, List<AppTeamManagEntity> lsTeam){
        try{
            sysDao.getAppManageCountDao().totalManageCount(date,lsTeam);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Async
    public void totalManageArchivingCount(String date,List<AppTeamManagEntity> lsTeam,String sourceType){
        try{
            sysDao.getAppManageCountDao().totalManageArchivingCount(date,lsTeam,sourceType);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Async
    public void totalManageArchivingAllCount(String date, List<AppTeamManagEntity> lsTeam,String sourceType){
        try{
            sysDao.getAppManageCountDao().totalManageArchivingAllCount(date,lsTeam,sourceType);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void totalExerciseCount(String date, List<AppTeamExerciseEntity> lsTeam) {
        try{
            sysDao.getAppExerciseCountDao().totalExerciseCount(date,lsTeam);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 初始化团队数据
     * @param type
     * @param date
     */
    public void appInitialiseTeamTotal(String type,String date){
        try{
            List<AppTeam> lsTeam = sysDao.getAppTeamDao().findtTeamAll();
            List<String> ls = new ArrayList<String>();
            String startDate = date;
            String endDate = ExtendDate.getYMD(Calendar.getInstance());
            ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
            ls.addAll(sysDao.getAppManageCountDao().findSignDate(date));
            if(ls != null && ls.size() >0){
                for(String s : ls){
                    sysDao.getAppManageCountDao().totalManageTeamCount(s,lsTeam);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 获取家签服务项目列表
     * @param qvo
     * @return
     */
    public String findHomeServiceItems(HomeServiceItemsQvo qvo,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idno",qvo.getIdno());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findHomeServiceItems";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdno(),"findHomeServiceItems");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findHomeServiceItems");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取居民所有体检记录和指定日期体检记录
     * @param qvo
     * @return
     */
    public String findJmjktjList(JmjktjQvo qvo,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("df_id",qvo.getDf_id());
            jsonParam.put("edate",qvo.getEdate());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findJmjktjList";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getDf_id(),"findJmjktjList");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findJmjktjList");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取居民体检记录详细信息
     * @param qvo
     * @return
     */
    public String getJmjktj(JmjktjQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("df_id",qvo.getDf_id());
            jsonParam.put("jktjcs",qvo.getJktjcs());
            jsonParam.put("jktj_ybzkid",qvo.getJktj_ybzkid());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getJmjktj";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getDf_id(),"getJmjktj");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getJmjktj");
           return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 出生医学证明
     * @return
     */
    public String findBirthCertificate(BirthCertificateQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idno",qvo.getIdno());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findBirthCertificate";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdno(),"findBirthCertificate");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findBirthCertificate");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取新生儿家庭访视记录
     * @param qvo
     * @return
     */
    public String findNeonateList(NeonateQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idno",qvo.getIdno());
            if(StringUtils.isNotBlank(qvo.getAgeType())) {
                jsonParam.put("ageType",qvo.getAgeType());
            }
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findNeonateList";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdno(),"findNeonateList");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findNeonateList");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 获取传染病报告
     * @param qvo
     * @param requestUserId
     * @param requestUserName
     * @return
     */
    public String findInfectiousReport(NeonateQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idno",qvo.getIdno());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findInfectiousReport";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdno(),"findInfectiousReport");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findInfectiousReport");
            return  str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据身份证号获取患者转出信息
     * @param qvo
     * @return
     */
    public String getReferralOrg(ReferralQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idno",qvo.getIdno());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getReferralOrg";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdno(),"getReferralOrg");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getReferralOrg");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取转诊记录
     * @param qvo
     * @param requestUserId
     * @param requestUserName
     * @return
     */
    public String getReferralRcord(ReferralQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idno",qvo.getIdno());
            jsonParam.put("orgid",qvo.getOrgid());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getReferralRcord";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdno(),"getReferralRcord");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getReferralRcord");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 首次产前随访服务
     * @param qvo
     * @param requestUserId
     * @param requestUserName
     * @return
     */
    public String findSccqsf(HomeServiceItemsQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idno",qvo.getIdno());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findSccqssfList";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdno(),"findSccqssfList");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findSccqssfList");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 产前随访服务
     * @param qvo
     * @return
     */
    public String  findCqsf(HomeServiceItemsQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idno",qvo.getIdno());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findEdwcsfList";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdno(),"findEdwcsfList");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findEdwcsfList");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 产后访视
     * @param qvo
     * @param requestUserId
     * @param requestUserName
     * @return
     */
    public String findChsf(HomeServiceItemsQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idno",qvo.getIdno());
            jsonParam.put("urlType", qvo.getUrlType());
            jsonParam.put("xm0000",qvo.getXm0000());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findChfsjlList";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdno(),"findChfsjlList");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findChfsjlList");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 重性精神病患者个人信息登记
     * @param qvo
     * @return
     */
    public String findZxjsbxx(JmjktjQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("df_id",qvo.getDf_id());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findZxjsjbList";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getDf_id(),"findZxjsjbList");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findZxjsjbList");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取重性精神疾病随访详细信息
     * @param qvo
     * @param requestUserId
     * @param requestUserName
     * @return
     */
    public String findZxjsbsf(JmjktjQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("sf_bhid",qvo.getSf_bhid());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findZxsfxx";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getSf_bhid(),"findZxsfxx");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findZxsfxx");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取重性精神疾病随访详细信息列表
     * @param qvo
     * @param requestUserId
     * @param requestUserName
     * @return
     */
    public String findZxjsbsfList(JmjktjQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            if(StringUtils.isNotBlank(qvo.getKsrj00())){
                jsonParam.put("ksrj00",qvo.getKsrj00());
            }
            jsonParam.put("df_id",qvo.getDf_id());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findZxsfxxList";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getDf_id(),"findZxsfxxList");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findZxsfxxList");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 保存基卫体征数据
     */
    public void findTzshuju(){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("startTime","");
            jsonParam.put("endTime","");
            Map<String,Object> map = new HashMap<String,Object>();
            String sql1="SELECT signs_time startDate FROM sys_update";
            List<Map> lmap = sysDao.getServiceDo().findSql(sql1);
            if(lmap!=null && lmap.size()>0 ){
                if(lmap.get(0).get("startDate")!=null){
                    String time = lmap.get(0).get("startDate").toString();
                    time=time.replaceAll("-","").replaceAll(":","").replaceAll(" ","");
                    jsonParam.put("startTime",time);
                    Calendar cal = Calendar.getInstance();
                    String endTime = ExtendDate.getYMD_h_m_s(cal);
                    endTime = endTime.replaceAll("-","").replaceAll(":","").replaceAll(" ","");
                    jsonParam.put("endTime",endTime);
                }
            }
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findGxyAndTnb";
            String str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            JSONObject jsonall = JSONObject.fromObject(str);
            if(jsonall.get("rows")!=null && jsonall.get("msgCode").equals("800")){
                List<JwSignsEntity> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<JwSignsEntity>>() {}.getType());
                if(ls!=null && ls.size()>0){
                    for(JwSignsEntity ll:ls){
                        String receivetime = ll.getExamDate();
                        String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
                        receivetime = receivetime.replaceAll(reg, "$1-$2-$3 $4:$5:$6");
                        //beforeMeal  afterMeal
                        if(ll.getBloodSugar()!=null){
                            //保存血糖
                            AppUserBloodglu xt = new AppUserBloodglu();
                            xt.setSourceType(SourceType.SF.getValue());
                            xt.setUgCode(ll.getDeviceNo());//设备号
                            xt.setUgTestTime(ExtendDate.getCalendar(receivetime));
                            //0-11早餐后  11-16午餐后  16-24晚餐后
                            //0-5凌晨  5-10空腹  10- 14午餐前  14-20晚餐前  20-24睡前
                            if("afterMeal".equals(ll.getBloodSugar().getSugarType())){
                                if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=0&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<11){
                                    xt.setUgBgstate("2");
                                }else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=11&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<16){
                                    xt.setUgBgstate("4");
                                }else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=16&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<24){
                                    xt.setUgBgstate("6");
                                }
                            }else if("beforeMeal".equals(ll.getBloodSugar().getSugarType())){
                                if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=0&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<5){
                                    xt.setUgBgstate("8");
                                }else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=5&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<10){
                                    xt.setUgBgstate("1");
                                }else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=10&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<14){
                                    xt.setUgBgstate("3");
                                }
                                else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=14&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<20){
                                    xt.setUgBgstate("5");
                                }
                                else if(ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)>=20&&ExtendDate.getCalendar(receivetime).get(Calendar.HOUR_OF_DAY)<24){
                                    xt.setUgBgstate("7");
                                }
                            }else{
                                xt.setUgBgstate("9");
                            }
                            xt.setUgTemptur("");//温度值
                            xt.setUgCodeNum("");//血糖仪code码
                            xt.setUgBloodglu(Double.parseDouble(ll.getBloodSugar().getSugarValue()));
                            List<AppPatientUser> users = sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",ll.getIdno());
                            if(users!=null && users.size()>0){
                                xt.setUgUserId(users.get(0).getId());
                                xt.setUgCode(ll.getDeviceNo());
                            }else{
                                xt.setUgUserId(ll.getIdno());
                            }
                            sysDao.getServiceDo().add(xt);
                        }
                        if(ll.getBaseExam()!=null){//保存血压数据
                            AppUserBloodpressure pressure = new AppUserBloodpressure();
                            pressure.setSourceType(SourceType.SF.getValue());
                            pressure.setUpImei(ll.getDeviceNo());
                            List<AppPatientUser> users = sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",ll.getIdno());
                            if(users!=null && users.size()>0){
                                pressure.setUpUser(users.get(0).getId());
                            }else{
                                pressure.setUpUser(ll.getIdno());
                            }
                            if(StringUtils.isNotBlank(ll.getBaseExam().getHeartRate())){
                                pressure.setUpPul(Integer.parseInt(ll.getBaseExam().getHeartRate()));
                                if(60<=Integer.parseInt(ll.getBaseExam().getHeartRate()) && Integer.parseInt(ll.getBaseExam().getHeartRate())<=100){
                                    pressure.setUpAno("0");
                                }else{
                                    pressure.setUpAno("1");
                                }
                            }
                            pressure.setUpSys(Integer.parseInt(ll.getBaseExam().getBpRsbp()));//高压
                            pressure.setUpDia(Integer.parseInt(ll.getBaseExam().getBpRdbp()));//低压
                            pressure.setUpTime(ExtendDate.getCalendar(receivetime));
                        }
                    }
                }
                Calendar cc = Calendar.getInstance();
                String upTime = ExtendDate.getYMD_h_m(cc);
                map.put("time",upTime);
                String sql2 = "UPDATE sys_update\n" +
                        "SET \n" +
                        " signs_time = :time";
                sysDao.getServiceDo().update(sql2);
            }

        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * 患者调度
     * @param userId
     */
    @Async
    public void patientScheduling(String userId) throws Exception{
        //儿童体检提醒
        sysDao.getSchedulingAsyncBean().medicalAlert(userId);
        //药品存量预警
        sysDao.getSchedulingAsyncBean().drugRunOut(userId);
        //孕妇保健计划提醒
        sysDao.getSchedulingAsyncBean().pregnantPlan(userId);
        //续签提醒
        sysDao.getSchedulingAsyncBean().renewalReminder(userId);
        //居家养老提醒
        sysDao.getSchedulingAsyncBean().homeCareReminder(userId);
        //儿童体检免疫提醒
        sysDao.getSchedulingAsyncBean().chileInoculation(userId);
//        用药提醒
//        sysDao.getSchedulingAsyncBean().drugReminder(userId);
    }
    /**
     * 医生调度
     * @param drUser
     */
    @Async
    public void ysScheduling(AppDrUser drUser) throws Exception {
        String userId  = drUser.getId();
        sysDao.getSchedulingAsyncBean().followVisit(userId);

        sysDao.getSchedulingAsyncBean().followNow(userId);

        sysDao.getSchedulingAsyncBean().drugRunOutToDr(userId);

        sysDao.getSchedulingAsyncBean().findTzyjToDr(drUser);

        //到期前一个月还未考核的人数消息调度
        sysDao.getSchedulingAsyncBean().assessMonthXx(drUser);
        //每月需上传佐证的考核项目，提前一周提醒还有那些人还未考核
        sysDao.getSchedulingAsyncBean().assessWeekXx(drUser);


        //编号生成
        String lyNum1 =getNum(drUser.getDrHospId());
        //健康教育履约提醒
        sysDao.getSchedulingAsyncBean().healthEdution(drUser,lyNum1,"1");

        String lyNum2 =getNum(drUser.getDrHospId());
        //健康指导履约提醒
        sysDao.getSchedulingAsyncBean().healthGuide(drUser,lyNum2,"1");

        String lyNum3 =getNum(drUser.getDrHospId());
        //新生儿家庭访视履约提醒
        sysDao.getSchedulingAsyncBean().newChildVisit(drUser,lyNum3,"1");

        String lyNum4 =getNum(drUser.getDrHospId());
        //0-6岁儿童健康检查履约提醒
        sysDao.getSchedulingAsyncBean().childHealth(drUser,lyNum4,"1");

        String lyNum5 =getNum(drUser.getDrHospId());
        //中医药健康指导（0-36月龄）履约提醒
        sysDao.getSchedulingAsyncBean().tcmHealthGuide(drUser,lyNum5,"1");

        String lyNum6 =getNum(drUser.getDrHospId());
        //孕期保健服务履约提醒
        sysDao.getSchedulingAsyncBean().prenatalCare(drUser,lyNum6,"1");

        String lyNum7 =getNum(drUser.getDrHospId());
        //产后视访履约提醒
        sysDao.getSchedulingAsyncBean().postpartumVisit(drUser,lyNum7,"1");

        String lyNum8 =getNum(drUser.getDrHospId());
        //产后42天健康检查记录履约提醒
        sysDao.getSchedulingAsyncBean().postpartum(drUser,lyNum8,"1");

        String lyNum9 =getNum(drUser.getDrHospId());
        //健康体检履约提醒
        sysDao.getSchedulingAsyncBean().healthyCheckUp(drUser,lyNum9,"1");

        String lyNum10 =getNum(drUser.getDrHospId());
        //用药指导履约提醒
        sysDao.getSchedulingAsyncBean().guidelines(drUser,lyNum10,"1");

        //定期随访履约提醒
        String lyNum11 =getNum(drUser.getDrHospId());
        sysDao.getSchedulingAsyncBean().HighBloodPressure(drUser,lyNum11,"1");//高血压

       String lyNum12 =getNum(drUser.getDrHospId());
        sysDao.getSchedulingAsyncBean().diabetes(drUser,lyNum12,"1");//糖尿病

        String lyNum13 =getNum(drUser.getDrHospId());
        sysDao.getSchedulingAsyncBean().MentalIllness(drUser,lyNum13,"1");//重性精神病

        String lyNum14 =getNum(drUser.getDrHospId());
        sysDao.getSchedulingAsyncBean().tuberculosis(drUser,lyNum14,"1");//肺结核

       drUser.setDrTxState("1");
        sysDao.getServiceDo().modify(drUser);
    }

    /**
     * 获取编号
     * @param hospId
     * @return
     */
    public String getNum(String hospId) throws Exception{
        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,hospId);
        String areaCode = "";
        if(dept!=null){
            if(StringUtils.isNotBlank(dept.getHospAreaCode())) {
                AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "ly");
                Map<String,Object> bcnum = null;
                if (serial != null) {
                    bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
                    serial.setSerialNo(bcnum.get("old").toString());
                    sysDao.getServiceDo().modify(serial);
                    return bcnum.get("new").toString();
                }
            }
        }
        return "";
    }

    /**
     * 儿童或孕产妇出院信息
     * @param signId 签约单id
     * @param userId 用户id
     * @param idNo 身份证号
     * @param type 类型（1为新生儿，需要传入母亲身份证号，2为孕产妇）
     * @param urlType
     * @return
     */
    @Async
    public void findChildOrWoman(String signId,String userId,String idNo,String type,String urlType,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idNo", idNo);
            jsonParam.put("type", type);
            jsonParam.put("urlType", urlType);
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findChildOrWoman";
            String result = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                result = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                result = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),idNo,"findChildOrWoman");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,result, userType,"findChildOrWoman");
            if (result != null) {
                JSONObject jsonAll = JSONObject.fromObject(result);
                if (jsonAll.get("rows") != null && jsonAll.get("msgCode").equals("800")) {
                    List<Fy_message> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<Fy_message>>() {}.getType());
                    if (ls != null && ls.size() > 0) {
                        for (Fy_message ll : ls) {
                            AppSignChildOrWomen table = new AppSignChildOrWomen();
                            table.setScowType(type);
                            table.setScowPatientIdNo(idNo);
                            table.setScowPatientId(userId);
                            table.setScowSignId(signId);
                            table.setScowOutHospitalDate(ExtendDate.getCalendar(ll.getCyrq00()));
                            String date = ExtendDateUtil.addDate(ll.getMcyj00(), ExtendDateType.WEEKS.getValue(), 13);
                            table.setScowExpect13Weeks(ExtendDate.getCalendar(date));
                            table.setScowpregnancy13Weeks(ExtendDate.getCalendar(ll.getY13rq0()));
                            table.setScowLastMenstrualDate(ExtendDate.getCalendar(ll.getMcyj00()));
                            sysDao.getServiceDo().add(table);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 莆田签约数据推送农合前置机
     * cxw
     * @param svo
     */
    public void ptSignToYiBao(AppSignInfoAllVo svo,int count){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            UUID id=UUID.randomUUID();
            String[] idd=id.toString().split("-");
            jsonParam.put("lsh000", idd[0]+idd[1]+idd[2]+idd[3]);//流水号
            jsonParam.put("grbh00",svo.getSignNum());//个人编号  *
            jsonParam.put("xm0000",svo.getName());//姓名
            jsonParam.put("shzf00",svo.getPatientIdno());//身份证号  *
            jsonParam.put("sbkh00",null);//社保卡号
            jsonParam.put("ylzh00",svo.getPtsscno());//医疗证号   *
            jsonParam.put("kssj00",svo.getSignFromDate());//签约开始时间
            jsonParam.put("jzsj00",svo.getSignToDate());//签约截止时间
            jsonParam.put("qysj00",svo.getSignDate());//签约时间
            jsonParam.put("czry00",svo.getOperatorName());//操作人
            String url = PropertiesUtil.getConfValue("ptqzjUrl") + "/SignCommon.action?act=SetSign";
            String result = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            if(result!=null && result!=""){
                JSONObject jsont = JSONObject.fromObject(result);
                if(jsont.get("msgCode").toString().equals("1001")){
                    System.out.println("---成功---num:"+count);
                }else if(jsont.get("msgCode").toString().equals("1002")){
                    if(StringUtils.isNotBlank(jsont.get("msg").toString())){//返回身份证不为空
                        System.out.println("开始更新前置机QYBZGX状态为2，代表已更新.....Go!");
                        JSONObject jsonParams = new JSONObject();
                        jsonParams.put("ptIdNolist",jsont.get("msg").toString());
                        String urls = PropertiesUtil.getConfValue("ptqzjUrl") + "/SignCommon.action?act=UpdateQzjState";
                        String results = HtmlUtils.getInstance().executeResponseJson(client, "post", urls, jsonParams, "utf-8");
                        if(StringUtils.isNotBlank(results)) {
                            JSONObject jsonts = JSONObject.fromObject(results);
                            if(jsonts.get("msgCode").toString().equals("800")){
                                System.out.println("---成功---");
                            }else{
                                System.out.println("---失败---");
                            }
                        }
                    }
                }else if(jsont.get("msgCode").toString().equals("1003")){
                    System.out.println("---已存在---num:"+count);
                }else{
                    System.out.println("---失败---num:"+count);
                }
                System.out.println(jsont.get("msgCode").toString());
                System.out.println(jsont.get("msg").toString());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *   签约调度取前置机数据 更新签约状态为2
     *   cxw
     */
    public void ptYiBaoQzjToSign(){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            String url = PropertiesUtil.getConfValue("ptqzjUrl") + "/SignCommon.action?act=SignFind";
            String result = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            if(result!=null && result!="") {
                JSONObject jsont = JSONObject.fromObject(result);
                if(jsont.get("vo")!=null){
                    List<WebPtQzjSignStateVo> ls = JsonUtil.fromJson(jsont.get("vo").toString(),new TypeToken<List<WebPtQzjSignStateVo>>() {}.getType());
                    if(ls!=null && ls.size()>0){
                        int succ = 0;
                        int fal = 0;
                        int yl = 0;
                        String resultIdNo = null;
                        for(int i=0;i<ls.size();i++){
                            AppSignForm signForm = sysDao.getAppSignformDao().findSignWebByPtIdNo(ls.get(i).getShzf00(),"0");
                            if(signForm!=null){
                                signForm.setSignState("2");
                                sysDao.getServiceDo().modify(signForm);
                                succ++;
                                System.out.println("更新状态成功 ："+ succ);
                                if(StringUtils.isNotBlank(resultIdNo)){
                                    resultIdNo += "," + ls.get(i).getShzf00();
                                }else{
                                    resultIdNo = ls.get(i).getShzf00();
                                }
                            }else{
                                AppSignForm signForms = sysDao.getAppSignformDao().findSignWebByPtIdNo(ls.get(i).getShzf00(),"2");
                                if(signForms!=null){
                                    yl++;
                                    System.out.println("状态本为2 ："+ yl);
                                    if(StringUtils.isNotBlank(resultIdNo)){
                                        resultIdNo += "," + ls.get(i).getShzf00();
                                    }else{
                                        resultIdNo = ls.get(i).getShzf00();
                                    }
                                }else{
                                    fal++;
                                    System.out.println("更新失败："+fal);
                                }
                            }
                        }
                        System.out.println("all: "+ls.size()+"条；true ："+succ+ "条; false: "+fal+"条; 已有："+yl+"条;---");
                        System.out.println("SignForm表签约状态更新完毕....End!");
                        System.out.println("开始更新前置机QYBZGX状态为2，代表已更新.....Go!");
                        JSONObject jsonParams = new JSONObject();
                        jsonParams.put("ptIdNolist",resultIdNo);
                        String urls = PropertiesUtil.getConfValue("ptqzjUrl") + "/SignCommon.action?act=UpdateQzjState";
                         String results = HtmlUtils.getInstance().executeResponseJson(client, "post", urls, jsonParams, "utf-8");
                        if(StringUtils.isNotBlank(results)) {
                            JSONObject jsonts = JSONObject.fromObject(results);
                            if(jsonts.get("msgCode").toString().equals("800")){
                                System.out.println("---成功---");
                            }else{
                                System.out.println("---失败,无数据---");
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 取前置机农合参保数据
     * cxw
     */
    public void ptGetNhSignSsc(){
        CloseableHttpClient client = HttpClients.createDefault();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        Calendar calendar = Calendar.getInstance();
        int t = 0 ;
        int f = 0 ;
        try{
            JSONObject jsonParam = new JSONObject();
            String url = PropertiesUtil.getConfValue("ptqzjUrl") + "/SignCommon.action?act=PtcbxxboFind";
            String result = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            if(result!=null && result!="") {
                JSONObject jsont = JSONObject.fromObject(result);
                if (jsont.get("vo") != null) {
                    List<WebPtCbxxboVo> ls = JsonUtil.fromJson(jsont.get("vo").toString(), new TypeToken<List<WebPtCbxxboVo>>() {}.getType());
                    if(ls!=null && ls.size()>0){
                        System.out.println("总共 ："+ls.size()+"条");
                        for(int i=0;i<ls.size();i++){
                            AppHfsSignSsc hfsSignSsc = new AppHfsSignSsc();
                            if(StringUtils.isNotBlank(ls.get(i).getSfzh00())){
                                hfsSignSsc = sysDao.getWebSignFormDao().findHfsSignSscByPtIdNo(ls.get(i).getSfzh00());
                                if(hfsSignSsc!=null){
                                    hfsSignSsc.setPtNumber(ls.get(i).getGrbh00());
                                    hfsSignSsc.setPtNature(ls.get(i).getHsx000());
                                    hfsSignSsc.setPtName(ls.get(i).getXm0000());
                                    hfsSignSsc.setPtGender(ls.get(i).getXb0000());
                                    if(StringUtils.isNotBlank(ls.get(i).getCsrq00())){
                                        calendar.setTime(sdf.parse(ls.get(i).getCsrq00()));
                                        hfsSignSsc.setPtBirth(calendar);
                                    }
                                    hfsSignSsc.setPtUserPh(ls.get(i).getYhzgx0());
                                    hfsSignSsc.setPtUserName(ls.get(i).getHzxm00());
                                    hfsSignSsc.setPtIdNo(ls.get(i).getSfzh00());
                                    hfsSignSsc.setPtRegion(ls.get(i).getCxzmc0());
                                    hfsSignSsc.setPtType(ls.get(i).getCBZT00());
                                    hfsSignSsc.setPtLx("3");
                                    hfsSignSsc.setPtJtbh(ls.get(i).getJtbh00());
                                    sysDao.getServiceDo().modify(hfsSignSsc);
                                    t++;
                                    System.out.println("修改成功 ： "+ t +"条");
                                }else{
                                    hfsSignSsc = new AppHfsSignSsc();
                                    hfsSignSsc.setId(UUID.randomUUID().toString());
                                    hfsSignSsc.setPtNumber(ls.get(i).getGrbh00());
                                    hfsSignSsc.setPtNature(ls.get(i).getHsx000());
                                    hfsSignSsc.setPtName(ls.get(i).getXm0000());
                                    hfsSignSsc.setPtGender(ls.get(i).getXb0000());
                                    if(StringUtils.isNotBlank(ls.get(i).getCsrq00())){
                                        calendar.setTime(sdf.parse(ls.get(i).getCsrq00()));
                                        hfsSignSsc.setPtBirth(calendar);
                                    }
                                    hfsSignSsc.setPtUserPh(ls.get(i).getYhzgx0());
                                    hfsSignSsc.setPtUserName(ls.get(i).getHzxm00());
                                    hfsSignSsc.setPtIdNo(ls.get(i).getSfzh00());
                                    hfsSignSsc.setPtRegion(ls.get(i).getCxzmc0());
                                    hfsSignSsc.setPtType(ls.get(i).getCBZT00());
                                    hfsSignSsc.setPtLx("3");
                                    if (StringUtils.isNotBlank(ls.get(i).getYlzh00())){
                                        hfsSignSsc.setPtSccNo(ls.get(i).getYlzh00());
                                        String code = ls.get(i).getYlzh00().substring(0,8);
                                        CdAddressConfiguration configuration =(CdAddressConfiguration) sysDao.getServiceDo().find(CdAddressConfiguration.class,code);
                                        if(configuration!=null){
                                            hfsSignSsc.setPtOrgid(configuration.getAreaCodeJw());
                                        }else{
                                            hfsSignSsc.setPtOrgid("1962");
                                        }
                                    }
                                    hfsSignSsc.setPtJtbh(ls.get(i).getJtbh00());
                                    sysDao.getServiceDo().add(hfsSignSsc);
                                    f++;
                                    System.out.println("新增成功 ： "+ f +"条");
                                }
                            }
                        }
                        System.out.println("all: " +ls.size()+"; change: "+t+" ; add : "+ f +" ;");
                    }
                }else{
                    System.out.println("查询失败,无数据");
                }
            }else{
                System.out.println("查询失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public  void InLabelGrooup(int upage)throws Exception
    {
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = " SELECT f.ID ,f.SIGN_TEAM_ID ,t.SVS_PKG ,f.SIGN_AREA_CODE FROM APP_SIGN_FORM  f INNER JOIN WEB_SIGN_TEMP t on f.SIGN_PATIENT_IDNO = t.PT_ID_NO  ";
            CommConditionVo qvo=new CommConditionVo();
            qvo.setPageStartNo(upage);
            qvo.setPageSize(500);
            List<AppSignFormEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppSignFormEntity.class,qvo);
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

    }


    public void AppOutpatientCount(String date, List<AppTeamManagEntity> lsTeam){
        try{
            sysDao.getAppOutpatientCountDao().AppOutpatientCount(date,lsTeam);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取居民健康档案
     * @param qvo
     * @return
     */
    public String getHealthCareRecords(HomeServiceItemsQvo qvo,String requestUserId,String requestUserName,String userType){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idNo",qvo.getIdno());
            jsonParam.put("urlType", qvo.getUrlType());
            jsonParam.put("orgId","");
            jsonParam.put("type",qvo.getType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getHealthCareRecords";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdno(),"getHealthCareRecords");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"getHealthCareRecords");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询基卫孕产妇产检计划
     * @param qvo
     * @return
     */
    public String findMaternalPlan(AppMaternalPlanQvo qvo){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(qvo.getUserIdno());
            if(user!=null){
                jsonParam.put("idNo",user.getPatientIdno());
                jsonParam.put("urlType",qvo.getUrlType());
                jsonParam.put("type",qvo.getType());
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findMaternalPlan";
                String str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                return str;
            }

        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询基卫老年人健康计划
     * @param qvo
     * @return
     */
    public String findOldHealthPlan(AppMaternalPlanQvo qvo){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            AppPatientUserEntity user = sysDao.getAppPatientUserDao().findByUserIdNo(qvo.getUserIdno());
            if(user!=null){
                jsonParam.put("idNo",user.getPatientIdno());
                jsonParam.put("urlType",qvo.getUrlType());
                jsonParam.put("type",qvo.getType());
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findMaternalPlan";
                String str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                return str;
            }

        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 公共调用接口工程数据方法
     * @param userId 操作人主键
     * @param name 操作人姓名
     * @param requiredParam 请求参数
     * @param patientId 对象主键
     * @param strMethod 接口方法
     * @return
     */
    public static String getDateBasic(String userId,String name,String requiredParam,String patientId,String strMethod ){
        SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");
        String result = null;
        try{
            AppBasicData abd = sysDao.getAppBasicDataDao().findByPatientIdAndMethodsType(patientId,strMethod);
            if(abd!=null) {
                if (StringUtils.isNotBlank(abd.getData())) {
                    result = abd.getData();
                }else{
                    long startTime=System.currentTimeMillis();
                    boolean bo = true;
                    while(bo){
                        long endTime=System.currentTimeMillis();   //获取当前毫秒数
                        long time = endTime-startTime;
                        if(time >= 30*1000) {
                            bo = false;   //若超过20秒则跳出
                        }
                        AppBasicDataTemp temp = sysDao.getAppBasicDataTempDao().getBasicDataTemp(abd.getId());
                        if(temp != null){
                            AppBasicData data = (AppBasicData) sysDao.getServiceDo().find(AppBasicData.class,abd.getId());
                            if(data != null && StringUtils.isNotBlank(data.getData())){
                                result = data.getData();
                                bo = false;
                                sysDao.getServiceDo().delete(AppBasicDataTemp.class,temp.getId());
                            }
                        }
                        sleep(1000);
                    }
                }
            }else{
                String id =sysDao.getAppBasicDataDao().saveBasicData(userId,name,requiredParam,patientId,null,strMethod);
                long startTime=System.currentTimeMillis();
                boolean bo = true;
                while(bo){
                    long endTime=System.currentTimeMillis();   //获取当前毫秒数
                    long time = endTime-startTime;
                    if(time >= 30*1000) {
                        bo = false;   //若超过20秒则跳出
                    }
                    AppBasicDataTemp temp = sysDao.getAppBasicDataTempDao().getBasicDataTemp(id);
                    if(temp != null){
                        AppBasicData data = (AppBasicData) sysDao.getServiceDo().find(AppBasicData.class,id);
//                        data = sysDao.getAppBasicDataDao().findById(id);
                        sysDao.getServiceDo().removePoFormSession(data);
                        if(data != null && StringUtils.isNotBlank(data.getData())){
                            result = data.getData();
                            bo = false;
                            sysDao.getServiceDo().delete(AppBasicDataTemp.class,temp.getId());
                        }
                    }
                    sleep(1000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * 公共调用接口工程日志保存方法
     * @param requestData 请求参数
     * @param userId 请求人主键
     * @param userName 请求人姓名
     * @param resultData 结果数据
     * @param userType 请求人类型（1患者 2医生）
     * @param method 请求方法
     * @return
     */
    @Async
    public static void getBasicLog(String requestData,String userId,String userName,
                                      String resultData,String userType,String method){
        SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");
        AppBasicLog log = new AppBasicLog();
        log.setRequestData(requestData);
        log.setRequestMethod(method);
        log.setRequestUserId(userId);
        log.setRequestUserName(userName);
        log.setRequestUserType(userType);
        log.setResultData(resultData);
        sysDao.getServiceDo().add(log);
    }

//    @Async
    public void totalPerformanceCount(String date, List<AppTeamManagEntity> lsTeam){
        try{
            sysDao.getAppPerformeRankCountDao().totalPerformanceCount(date,lsTeam);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean verification(String devCode,String type){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("icSerial",devCode);
            jsonParam.put("icSerialType",type);
//            if(StringUtils.isNotBlank(type)){
//                //血压
//                if(CommonDeviceType.XY.getValue().equals(type)){
//                    jsonParam.put("icSerialType","98");
//                }
//                //血糖
//                if(CommonDeviceType.XT.getValue().equals(type)){
//                    jsonParam.put("icSerialType","99");
//                }
//            }
            String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=verification";
            String str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            if(StringUtils.isNotBlank(str)){
                JSONObject jsonall = JSONObject.fromObject(str);
                if(jsonall.get("msgCode") != null) {
                    if (jsonall.get("msgCode").toString().equals("800")) {
                        return true;
                    }
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 建档立卡各未签原因调度统计
     * @param date 时间
     * @param lsTeam
     */
    @Async
    public void totalManageArchivingCountPeople( String date, List<AppTeamManagEntity> lsTeam ,String sourceType){
        try{
            sysDao.getAppManageCountDao().totalManageArchivingPeople(date,lsTeam,sourceType);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 血压、血糖异常数据推送（三明尤溪）
     * @param user
     * @param hbp
     * @param dm
     */
    @Async
    public void addAbnormal(AppPatientUser user, AppSmHbpTwoEntity hbp, AppBloodSugarTwoEntity dm, String orgId, String teamId){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            if(StringUtils.isNotBlank(user.getPatientProvince())){//取省名称
                CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,user.getPatientProvince());
                if(address != null){
                    jsonParam.put("province",address.getAreaSname());
                }
            }
            if(StringUtils.isNotBlank(user.getPatientCity())){
                CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,user.getPatientCity());
                if(address != null){
                    jsonParam.put("city",address.getAreaSname());
                }
            }
            if(StringUtils.isNotBlank(user.getPatientArea())){
                CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,user.getPatientArea());
                if(address != null){
                    jsonParam.put("area",address.getAreaSname());
                }
            }
            if(StringUtils.isNotBlank(user.getPatientStreet())){
                CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,user.getPatientStreet());
                if(address != null){
                    jsonParam.put("street",address.getAreaSname());
                }
            }
            if(StringUtils.isNotBlank(user.getPatientNeighborhoodCommittee())){
                CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,user.getPatientNeighborhoodCommittee());
                if(address != null){
                    jsonParam.put("village",address.getAreaSname());
                }
            }
            jsonParam.put("addr",user.getPatientAddress());
            if(hbp != null){
                List<AppSmHbpTwoEntity> listHbp = new ArrayList<>();
                listHbp.add(hbp);
                jsonParam.put("listHbp",listHbp);
            }
            if(dm != null){
                List<AppBloodSugarTwoEntity> listBS = new ArrayList<>();
                listBS.add(dm);
                jsonParam.put("listBS",listBS);
            }
            String cityCode = "";
            if(StringUtils.isNotBlank(orgId)){
                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,orgId);
                if(dept != null){
                    jsonParam.put("orgId",dept.getId());
                    jsonParam.put("orgName",dept.getHospName());
                    cityCode = AreaUtils.getAreaCode(dept.getHospAreaCode(),"2");
                }
            }
            jsonParam.put("paitentTel",user.getPatientTel());
            jsonParam.put("patientId",user.getId());
            jsonParam.put("patientIdno",user.getPatientIdno());
            jsonParam.put("patientName",user.getPatientName());
            jsonParam.put("patientGender",user.getPatientGender());
            jsonParam.put("xaxis",user.getPatientX());
            jsonParam.put("yaxis",user.getPatientY());
            if(StringUtils.isNotBlank(teamId)){
                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,teamId);
                if(team != null){
                    jsonParam.put("teamId",team.getId());
                    jsonParam.put("teamName",team.getTeamName());
                }
            }
            String urlLogin = PropertiesUtil.getConfValue("smDataUrl")+"/mbgl/mbgl.action?act=addAbnormal";
            if(AddressType.XMS.getValue().equals(cityCode)){
                urlLogin = PropertiesUtil.getConfValue("xmDataUrl")+"/mbgl/mbgl.action?act=addAbnormal";
            }else if(AddressType.GPS.getValue().equals(cityCode)){
                urlLogin = PropertiesUtil.getConfValue("gpDataUrl")+"/mbgl/mbgl.action?act=addAbnormal";
            }
            String str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
//            System.out.println(urlLogin);
//            System.out.println(str);
            if(StringUtils.isNotBlank(str)){
                JSONObject jsonall = JSONObject.fromObject(str);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 上传中医体质辨识到基卫
     * @param qvo
     * @return
     */
    public boolean uploadTcmToBasic(AppTcmRecordQvo qvo){
        boolean flag = false;
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("urlType",qvo.getUrlType());
            jsonParam.put("ybqk",qvo.getYbqk());
            jsonParam.put("bsjl",qvo.getBsjl());
            jsonParam.put("jlvo",qvo.getJlvo());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=uploadTcmToBasic";
            String str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            if(StringUtils.isNotBlank(str)){
                JSONObject jsonall = JSONObject.fromObject(str);
                if(jsonall != null && jsonall.get("msgCode")  != null && jsonall.get("msgCode").toString().equals("800")){
                    return true;
                }else{
                    return false;
                }
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    @Async
    public void totalManageChronicCount(String date, List<AppTeamManagEntity> lsTeam){
        try{
            sysDao.getAppManageCountDao().totalManageChronicCount(date,lsTeam);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 推送sos到尤溪
     * @param vv
     */
    @Async
    public void fsSos(AppPatientUser user,AppSmHbpTwoEntity hbp, AppBloodSugarTwoEntity dm, String orgId, String teamId,SeekHelpVo vv){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            if(StringUtils.isNotBlank(user.getPatientProvince())){//取省名称
                CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,user.getPatientProvince());
                if(address != null){
                    jsonParam.put("province",address.getAreaSname());
                }
            }
            if(StringUtils.isNotBlank(user.getPatientCity())){
                CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,user.getPatientCity());
                if(address != null){
                    jsonParam.put("city",address.getAreaSname());
                }
            }
            if(StringUtils.isNotBlank(user.getPatientArea())){
                CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,user.getPatientArea());
                if(address != null){
                    jsonParam.put("area",address.getAreaSname());
                }
            }
            if(StringUtils.isNotBlank(user.getPatientStreet())){
                CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,user.getPatientStreet());
                if(address != null){
                    jsonParam.put("street",address.getAreaSname());
                }
            }
            if(StringUtils.isNotBlank(user.getPatientNeighborhoodCommittee())){
                CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,user.getPatientNeighborhoodCommittee());
                if(address != null){
                    jsonParam.put("village",address.getAreaSname());
                }
            }
            jsonParam.put("addr",user.getPatientAddress());
            if(hbp != null){
                List<AppSmHbpTwoEntity> listHbp = new ArrayList<>();
                listHbp.add(hbp);
                jsonParam.put("listHbp",listHbp);
            }
            if(dm != null){
                List<AppBloodSugarTwoEntity> listBS = new ArrayList<>();
                listBS.add(dm);
                jsonParam.put("listBS",listBS);
            }
            String cityCode = "";
            if(StringUtils.isNotBlank(orgId)){
                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,orgId);
                if(dept != null){
                    jsonParam.put("orgId",dept.getId());
                    jsonParam.put("orgName",dept.getHospName());
                    cityCode = AreaUtils.getAreaCode(dept.getHospAreaCode(),"2");
                }
            }
            jsonParam.put("paitentTel",user.getPatientTel());
            jsonParam.put("patientId",user.getId());
            jsonParam.put("patientIdno",user.getPatientIdno());
            jsonParam.put("patientName",user.getPatientName());
            jsonParam.put("patientGender",user.getPatientGender());
            jsonParam.put("xaxis",user.getPatientX());
            jsonParam.put("yaxis",user.getPatientY());
            if(StringUtils.isNotBlank(teamId)){
                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,teamId);
                if(team != null){
                    jsonParam.put("teamId",team.getId());
                    jsonParam.put("teamName",team.getTeamName());
                }
            }
            jsonParam.put("seekHelp",vv);

            String urlLogin = PropertiesUtil.getConfValue("smDataUrl")+"/mbgl/mbgl.action?act=addAbnormal";
            if(AddressType.XMS.getValue().equals(cityCode)){
                urlLogin = PropertiesUtil.getConfValue("xmDataUrl")+"/mbgl/mbgl.action?act=addAbnormal";
            }else if(AddressType.GPS.getValue().equals(cityCode)){
                urlLogin = PropertiesUtil.getConfValue("gpDataUrl")+"/mbgl/mbgl.action?act=addAbnormal";
            }

//            System.out.println("url:"+urlLogin);
            String str = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
//            System.out.println("结果："+str);
            if(StringUtils.isNotBlank(str)){
                JSONObject jsonall = JSONObject.fromObject(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //解析健康档案
    public T_dwellerfile analysisFileOne(String str,String urlType)  throws Exception{
        T_dwellerfile file = null;
        JSONObject jsonall = JSONObject.fromObject(str);
        if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
            if(jsonall.get("entity") != null) {
                JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                if(AddressType.ND.getValue().equals(urlType)||AddressType.PINGT.getValue().equals(urlType)){//宁德居民基础信息
                    if(entity != null && entity.toString() != "null"){
                        if(entity.get("msgCode")!=null){
                            if("800".equals(entity.get("msgCode").toString())){
                                List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(),new TypeToken<List<AppEnterpatientEntity>>() {}.getType());
                                if(list != null && list.size()>0){
                                    file = new T_dwellerfile();
                                    String birthDay = list.get(0).getBirthday();
                                    if(StringUtils.isNotBlank(birthDay)){
                                        birthDay = birthDay.replaceAll("-","");
                                        file.setCsrq(birthDay);
                                    }
                                    file.setJdrq(ExtendDate.getYYYYMMD(ExtendDate.getCalendar(list.get(0).getCdate())));
                                    file.setJmdah(list.get(0).getDf_id());
                                    file.setJmxm(list.get(0).getName());
//                                        file.setJtdah(list.get(0).get);
                                    file.setJwhbh(list.get(0).getRef_cjid());
                                    file.setMphm(list.get(0).getAdrss_hnumber());
                                    file.setSfyxda(list.get(0).getSfyxda());
                                    file.setTel(list.get(0).getTelphone());
                                    file.setSfzh(list.get(0).getIdcardno());
                                    if(StringUtils.isNotBlank(list.get(0).getAdress_pro())){
                                        CdAddress addPro = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_pro());
                                        if(addPro != null){
                                            file.setSheng(addPro.getAreaSname());
                                        }
                                    }
                                    if(StringUtils.isNotBlank(list.get(0).getAdress_city())){
                                        CdAddress addCity = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_city());
                                        if(addCity != null){
                                            file.setShi(addCity.getAreaSname());
                                        }
                                    }
                                    if(StringUtils.isNotBlank(list.get(0).getAdress_county())){
                                        CdAddress addCoun = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_county());
                                        if(addCoun != null){
                                            file.setXian(addCoun.getAreaSname());
                                        }
                                    }
                                    if(StringUtils.isNotBlank(list.get(0).getAdress_rural())){
                                        CdAddress addRural = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_rural());
                                        if(addRural != null){
                                            file.setXiang(addRural.getAreaSname());
                                        }
                                    }
                                    if(StringUtils.isNotBlank(list.get(0).getAdress_village())){
                                        file.setXzqydm(list.get(0).getAdress_village());
                                        CdAddress address = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_village());
                                        if(address != null){
                                            file.setCun(address.getAreaSname());
                                        }
                                    }
                                    file.setXb(list.get(0).getSex());
                                }
                            }
                        }
                    }
                }else{
                    if(entity != null && entity.toString() != "null") {
                        if ("true".equals(entity.get("success").toString())) {
                            file = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                        }
                    }
                }
            }
        }
        return file;
    }
//    @Async
    public void totalManageTeamCount(String date, List<AppTeamManagEntity> lsTeam){
        try{
            sysDao.getAppManageCountDao().totalManageTeam(date,lsTeam);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String findHealthByIdnoAndOrg(AppAioQvo qvo,String requestUserId,String requestUserName,String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idno", qvo.getIdno());
            if(StringUtils.isNotBlank(qvo.getUploadOrgId()) && StringUtils.isNotBlank(qvo.getUrlType())){
                if(AddressType.FZ.getValue().equals(qvo.getUrlType())){
                    jsonParam.put("uploadOrgId", "FZ_"+qvo.getUploadOrgId());
                }else if(AddressType.QZ.getValue().equals(qvo.getUrlType()) || AddressType.ZZ.getValue().equals(qvo.getUrlType()) || AddressType.PT.getValue().equals(qvo.getUrlType())
                        || AddressType.NP.getValue().equals(qvo.getUrlType()) || AddressType.SM.getValue().equals(qvo.getUrlType())){
                    jsonParam.put("uploadOrgId",qvo.getUploadOrgId().toUpperCase());
                }
            }
            if(StringUtils.isNotBlank(qvo.getDataSource())){
                jsonParam.put("dataSource",qvo.getDataSource());
            }
            if(StringUtils.isNotBlank(qvo.getStartDate())){

                jsonParam.put("startDate",qvo.getStartDate().replaceAll("-",""));
            }
            if(StringUtils.isNotBlank(qvo.getEndDate())){
                jsonParam.put("endDate",qvo.getEndDate().replaceAll("-",""));
            }
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getHealthExamListByIdnoAndUploadOrgId";
            String str = null;
            if (state.equals(OpenTheInterfaceState.NOT.getValue())) {
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            } else {
                str = this.getDateBasic(requestUserId, requestUserName, jsonParam.toString(), qvo.getIdno(), "getHealthExamListByIdnoAndUploadOrgId");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(), requestUserId, requestUserName, str, userType, "getHealthExamListByIdnoAndUploadOrgId");
            return str;
        } catch (Exception e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 调阅居民健康档案
     * @return
     */
    public String findElectronicArchivesDetail(AssessReadJwVo qvo, String requestUserId, String requestUserName, String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("idcardno",qvo.getIdcardno());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findElectronicArchivesDetail";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdcardno(),"findElectronicArchivesDetail");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findElectronicArchivesDetail");
            AppJson json = JsonUtil.fromJson(str,AppJson.class);
            if(json != null){
                if(json.getMsgCode().equals("800")){
                    return json.getEntity();
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 调阅所有随访记录总路口
     * @return
     */
    public String findExecuteDetail(AssessReadJwVo qvo, String requestUserId, String requestUserName, String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            if(StringUtils.isNotBlank(qvo.getChildName())) {
                jsonParam.put("childName", qvo.getChildName());
            }
            if(StringUtils.isNotBlank(qvo.getBirthday())) {
                jsonParam.put("birthday", qvo.getBirthday());
            }
            if(StringUtils.isNotBlank(qvo.getIdcardno())) {
                jsonParam.put("idcardno", qvo.getIdcardno());
            }
            if(StringUtils.isNotBlank(qvo.getType())) {
                jsonParam.put("type", qvo.getType());
            }
            if(StringUtils.isNotBlank(qvo.getSignStartTime())) {
                jsonParam.put("signStartTime", qvo.getSignStartTime());
            }
            if(StringUtils.isNotBlank(qvo.getSignEndTime())) {
                jsonParam.put("signEndTime", qvo.getSignEndTime());
            }
            jsonParam.put("act", qvo.getAct());
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findExecuteDetail";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdcardno(),"findExecuteDetail");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findExecuteDetail");
            AppJson json = JsonUtil.fromJson(str,AppJson.class);
            if(json != null){
                if(json.getMsgCode().equals("800")){
                    return json.getEntity();
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 健康档案分数
     * @return
     */
    public String findElectronicArchives(interfaceQvo qvo, String requestUserId, String requestUserName, String userType) {
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            if(StringUtils.isNotBlank(qvo.getIdno())) {
                jsonParam.put("idno", qvo.getIdno());
            }
            if(StringUtils.isNotBlank(qvo.getSignTimeStart())) {
                jsonParam.put("signTimeStart", qvo.getSignTimeStart());
            }
            if(StringUtils.isNotBlank(qvo.getSignTimeEnd())) {
                jsonParam.put("signTimeEnd", qvo.getSignTimeEnd());
            }
            if(StringUtils.isNotBlank(qvo.getType())) {
                jsonParam.put("type", qvo.getType());
            }
            jsonParam.put("urlType", qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findElectronicArchives";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdno(),"findElectronicArchives");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, userType,"findElectronicArchives");
            AppJson json = JsonUtil.fromJson(str,AppJson.class);
            if(json != null){
                if(json.getMsgCode().equals("800")){
                    return json.getEntity();
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 初始化签约单
     * @throws Exception
     */
    @Async()
    public void initLabelData(String hospId,int pageStartNo,int pageSize,String type) throws Exception {
//        long start = Calendar.getInstance().getTimeInMillis();
//        System.out.println("机构主键:"+hospId+"第"+pageStartNo+"次开始时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
//        Map<String,Object> map = new HashMap<String, Object>();
//        CommConditionVo qvo = new CommConditionVo();
//        qvo.setPageStartNo(pageStartNo);
//        qvo.setPageSize(pageSize);
//        String sql = "SELECT * FROM APP_SIGN_FORM where SIGN_HOSP_ID = :SIGN_HOSP_ID";
//        map.put("SIGN_HOSP_ID",hospId);
//        List<AppSignForm>  ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class, qvo);
//        if (ls != null && ls.size() > 0) {
//            List<AppLabelManage> lsmanage = sysDao.getAppLabelManageDao().findByType(type);
//            for (AppSignForm s : ls) {
//                String resultStr = null;
//                if(LabelManageType.JBLX.getValue().equals(type)){//疾病类型
//                    List<AppLabelDisease> lsDisease = sysDao.getAppLabelGroupDao().findBySignDisease(s.getId(),LabelManageType.JBLX.getValue());
//                    if(lsDisease != null && lsDisease.size()>0){
//                        for(AppLabelDisease v : lsDisease){
//                            if(lsmanage != null && lsmanage.size()>0){
//                                for(AppLabelManage g : lsmanage){
//                                    if(v.getLabelId().equals(g.getId())){
//                                        if(resultStr != null){
//                                            resultStr += ";"+ g.getLabelSort();
//                                        }else{
//                                            resultStr = g.getLabelSort();
//                                        }
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }else if(LabelManageType.FWRQ.getValue().equals(type)){//服务人群
//                    List<AppLabelGroup> lsGroup = sysDao.getAppLabelGroupDao().findBySignGroup(s.getId(),LabelManageType.FWRQ.getValue());
//                    if(lsGroup != null && lsGroup.size()>0){
//                        for(AppLabelGroup v : lsGroup){
//                            if(lsmanage != null && lsmanage.size()>0){
//                                for(AppLabelManage g : lsmanage){
//                                    if(v.getLabelId().equals(g.getId())){
//                                        if(resultStr != null){
//                                            resultStr += ";"+ g.getLabelSort();
//                                        }else{
//                                            resultStr = g.getLabelSort();
//                                        }
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }else if(LabelManageType.JJLX.getValue().equals(type)){//经济类型
//                    List<AppLabelEconomics> lsEconnmics = sysDao.getAppLabelGroupDao().findBySignEconomics(s.getId(),LabelManageType.JJLX.getValue());
//                    if(lsEconnmics != null && lsEconnmics.size()>0){
//                        for(AppLabelEconomics v : lsEconnmics){
//                            if(lsmanage != null && lsmanage.size()>0){
//                                for(AppLabelManage g : lsmanage){
//                                    if(v.getLabelId().equals(g.getId())){
//                                        if(resultStr != null){
//                                            resultStr += ";"+ g.getLabelSort();
//                                        }else{
//                                            resultStr = g.getLabelSort();
//                                        }
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                sysDao.getAppLabelGroupDao().addSignLabelData(resultStr,type,s.getId());
//            }
//        }
//        System.out.println("机构主键:"+hospId+"第"+pageStartNo+"次结束时间:"+ExtendDate.getYMD_h_m_s(Calendar.getInstance()));
//        Long s = (Calendar.getInstance().getTimeInMillis() - start) / (1000 * 60);
//        System.out.println("机构主键:"+hospId+"第"+pageStartNo+"次总耗时:"+s);
    }

    /**
     * 查询获取获取建档立卡信息履约信息
     * @param patientIdNo
     * @param urlType
     * @param startDate
     * @param endDate
     * @param requestUserId
     * @param requestUserName
     * @return
     */
    public String getPerformance(String patientIdNo,String urlType,String startDate,String endDate,String requestUserId,String requestUserName){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("patientIdNo",patientIdNo);
            jsonParam.put("urlType", urlType);
            jsonParam.put("startDate",startDate);
            jsonParam.put("endDate",endDate);
            jsonParam.put("yearPay",ExtendDate.getYYYY(Calendar.getInstance()));
            String address = null;
            if (urlType.equals(AddressType.FZ.getValue())) {
                address = PropertiesUtil.getConfValue("FZly");
            } else if (urlType.equals(AddressType.QZ.getValue())) {
                address = PropertiesUtil.getConfValue("QZly");
            } else if (urlType.equals(AddressType.ZZ.getValue())) {
                address = PropertiesUtil.getConfValue("ZZly");
            } else if (urlType.equals(AddressType.PT.getValue())) {
                address = PropertiesUtil.getConfValue("PTly");
            } else if (urlType.equals(AddressType.NP.getValue())) {
                address = PropertiesUtil.getConfValue("NPly");
            } else if (urlType.equals(AddressType.SM.getValue())) {
                address = PropertiesUtil.getConfValue("SMly");
            }else{
                address = PropertiesUtil.getConfValue("SXly");
            }

            String url = address + "/appCommon.action?act=findPerformance";
//            String url =  "http://10.130.5.48:8080/interface-system/appCommon.action?act=findPerformance";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),patientIdNo,"findPerformance");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, urlType,"findPerformance");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 莆田绩效考核手持机刷社保卡得分
     * @return
     */
    public String creditCardScoreOfPoss(CreditCardScoreVo qvo, String requestUserId, String requestUserName){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("orgId",qvo.getOrgId());
            jsonParam.put("drId", qvo.getDrId());
            jsonParam.put("idNo",qvo.getIdNo());
            jsonParam.put("yxsksj",qvo.getYxsksj());
            jsonParam.put("sbkh",qvo.getSbkh());
            jsonParam.put("urlType",qvo.getUrlType());
            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=creditCardScoreOfPoss";
            String str = null;
            if(state.equals(OpenTheInterfaceState.NOT.getValue())){
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getIdNo(),"creditCardScoreOfPoss");
            }
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, qvo.getUrlType(),"creditCardScoreOfPoss");
            return str;
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 计生人口接口
     * @param year
     * @param month
     * @param addr
     * @return
     */
    public AppPlannedPeopleEntity getPlannedPeopleNew(String year,String month, String addr,String cityCode){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            String urlType = "";
            String address = "";
            if (cityCode.equals(AddressType.FZS.getValue())) {
                urlType = AddressType.FZ.getValue();
                address = PropertiesUtil.getConfValue("FZ").replace("getHealthCareRecords","getPlannedPeople");
            } else if (cityCode.equals(AddressType.QZS.getValue())) {
                urlType = AddressType.QZ.getValue();
                address = PropertiesUtil.getConfValue("QZ").replace("getHealthCareRecords","getPlannedPeople");
            } else if (cityCode.equals(AddressType.ZZS.getValue())) {
                urlType = AddressType.ZZ.getValue();
                address = PropertiesUtil.getConfValue("ZZ").replace("getHealthCareRecords","getPlannedPeople");
            } else if (cityCode.equals(AddressType.PTS.getValue())) {
                urlType = AddressType.PT.getValue();
                address = PropertiesUtil.getConfValue("PT").replace("getHealthCareRecords","getPlannedPeople");
            } else if (cityCode.equals(AddressType.NPS.getValue())) {
                urlType = AddressType.NP.getValue();
                address = PropertiesUtil.getConfValue("NP").replace("getHealthCareRecords","getPlannedPeople");
            } else if (cityCode.equals(AddressType.SMS.getValue())) {
                urlType = AddressType.SM.getValue();
                address = PropertiesUtil.getConfValue("SM").replace("getHealthCareRecords","getPlannedPeople");
            }else{
                urlType = AddressType.SXS.getValue();
                address = PropertiesUtil.getConfValue("SX").replace("getHealthCareRecords","getPlannedPeople");
            }
            String urlLogin = address;
            jsonParam.put("year",year);
            jsonParam.put("month",month);
            jsonParam.put("addr",addr);
            String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
            if(StringUtils.isNotBlank(str1)){
                JSONObject jsonAll = JSONObject.fromObject(str1);
                if(jsonAll.get("vo") != null){
                    AppPlannedPeopleEntity v = JsonUtil.fromJson(jsonAll.get("vo").toString(), AppPlannedPeopleEntity.class);
                    return  v;
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询是否已经签约 web查询省库数据
     * @param qvo
     * @return
     */
    public AppWebSignFormListEntity findSign(AppWebSignQvo qvo,String urlType,String requestUserId,String requestUserName){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
//            String state = PropertiesUtil.getConfValue("openTheInterface");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("ptidno",qvo.getPtidno());
            jsonParam.put("ptsscno", qvo.getPtsscno());
            String url = PropertiesUtil.getConfValue("appIpUrl") + "/signAction.action?act=signfindMessage";
            String str = null;
            str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            /*if(state.equals(OpenTheInterfaceState.NOT.getValue())){

            }else{
                str = this.getDateBasic(requestUserId,requestUserName,jsonParam.toString(),qvo.getPtidno(),"signfind");
            }*/
//            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, DrPatientType.DR.getValue(),"signfind");
            if(StringUtils.isNotBlank(str)){
                JSONObject jsonall = JSONObject.fromObject(str);
                if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")) {
                    AppWebSignFormListEntity entitys = JsonUtil.fromJson(jsonall.get("vo").toString(), AppWebSignFormListEntity.class);
                    if (entitys != null) {
                        return entitys;
                    }
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取电子健康卡领卡页面url
     * @return
     */
    public String getEhcUrl(Map<String, Object> reqParam) throws Exception{
        try{
            String appId = PropertiesUtil.getConfValue("appIdSX");
            String appSecret = PropertiesUtil.getConfValue("appSecretSX");
            String serverUrl = PropertiesUtil.getConfValue("serverUrlSX");
            String url = EhcPortalClient.getUrl(serverUrl, appId, appSecret, reqParam);
            return url;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取电子健康卡发卡状态和卡号
     * @param extUserId
     * @return
     */
    public ResponseParams getEhcCarStateAndNo(String extUserId){
        try{
            String appId = PropertiesUtil.getConfValue("appIdSX");
            String appSecret = PropertiesUtil.getConfValue("appSecretSX");
            String serverUrl = PropertiesUtil.getConfValue("serverCardUrlSX");
            String serviceId = "portal.sdk.ehcStatus";
            EhcPortalClient ehcPortalClient = new EhcPortalClient(appId, appSecret, "app", serverUrl);
            Map<String, Object> reqParam = new HashMap<String, Object>();
            reqParam.put("extUserId", extUserId);
            ResponseParams res = ehcPortalClient.execute(reqParam, serviceId);
            if(res != null){
                return res;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取电子健康卡居民信息
     * @return
     */
    public ResponseParams getEhcPeople(String extUserId){
        try{
            String appId = PropertiesUtil.getConfValue("appIdSX");
            String appSecret = PropertiesUtil.getConfValue("appSecretSX");
            String serverUrl = PropertiesUtil.getConfValue("serverCardUrlSX");
            String serviceId = "portal.sdk.ehcRealNameInfo";
            EhcPortalClient ehcPortalClient = new EhcPortalClient(appId, appSecret, "app", serverUrl);
            Map<String, Object> reqParam = new HashMap<String, Object>();
            reqParam.put("extUserId", extUserId);
            ResponseParams res = ehcPortalClient.execute(reqParam, serviceId);
            if(res != null){
                return res;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Async
    public void totalManageOtherCount(String date, List<AppTeamManagEntity> lsTeam){
        try{
            sysDao.getAppManageCountDao().totalManageOtherCount(date,lsTeam);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    @Async
    public void totalManageRankCount(String date,List<AppTeamManagEntity> lsTeam){
        try{
            sysDao.getAppManageCountDao().totalManageRankCount(date,lsTeam);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //推送消息到互联网接口(患者消息异步调用)
    @Async
    public void sendOutInternetNews(AppInternetNewsQvo qvo){
        try{
            CloseableHttpClient client = HttpClients.createDefault();
            String appId = PropertiesUtil.getConfValue("appIdInterNet");
            String serviceId = PropertiesUtil.getConfValue("serviceIdInterNet");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("appId",appId);
            jsonParam.put("serviceId", serviceId);
            String timestamp = ExtendDate.getTime(Calendar.getInstance());
            jsonParam.put("timestamp",timestamp);
//            qvo.setUserId("57624AFDF05E7D8796F61A5C48E95EF4D6968222974C0CCAFF9D487724BC76E3");//测试
            jsonParam.put("param",JsonUtil.toJson(qvo));
            String url = PropertiesUtil.getConfValue("appIpUrlInterNet") ;
            String str = null;
            str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
          /*  if(StringUtils.isNotBlank(str)){
                JSONObject jsonall = JSONObject.fromObject(str);
                if(jsonall.get("respCode")!=null && jsonall.get("respCode").equals("000000")) {//调用接口正常
                    if(jsonall.get("respMsg") != null){

                    }
                }
            }*/
//            System.out.println("推送互联网医院消息成功");
            //添加日志 由于用户id是电子健康卡id长度过长所以此处保存用户身份证号为用户id
            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),qvo.getIdNo(),qvo.getUserName(),str, null,"互联网医院消息");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 推送三诺平台血糖数据到蒲公英
     */
    @Async
    public void pushXt(AppBooldSugrData qvo){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            JSONObject jsonParam1 = new JSONObject();
            jsonParam1.put("result",qvo.getMsgdata().getResult());
            jsonParam1.put("testtime",qvo.getMsgdata().getTesttime());
            jsonParam1.put("usercode",qvo.getMsgdata().getUsercode());
            jsonParam1.put("devicesn",qvo.getMsgdata().getDevicesn());
            jsonParam.put("foodstatus",qvo.getMsgdata().getFoodstatus());

            jsonParam.put("sourceType",qvo.getSourceType());
            jsonParam.put("name",qvo.getName());
            jsonParam.put("idno",qvo.getIdno());
            jsonParam.put("msgdat",jsonParam1);
            String url = PropertiesUtil.getConfValue("pgyUrl") + "/monitor.action?act=appBooldSugrData";
            String str = null;
            str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            AppJson json = JsonUtil.fromJson(str,AppJson.class);
            if(json != null){
                if(json.getMsgCode().equals("800")){
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 推送第三方血压数据到蒲公英
     */
    @Async
    public void pushXy(AppThreeBloodPressureDataVo qvo){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("medicalRecordId",qvo.getMedicalRecordId());
            jsonParam.put("authorizationCode",qvo.getAuthorizationCode());
            jsonParam.put("deviceSim",qvo.getDeviceSim());
            jsonParam.put("highPressure",qvo.getHighPressure());
            jsonParam.put("lowVoltage",qvo.getLowVoltage());
            jsonParam.put("pulse",qvo.getPulse());
            jsonParam.put("uploadTime",qvo.getUploadTime());
            jsonParam.put("aOrB",qvo.getaOrB());
            jsonParam.put("idCard",qvo.getIdCard());
            jsonParam.put("sourceType",qvo.getSourceType());
            jsonParam.put("name",qvo.getName());
            jsonParam.put("idno",qvo.getIdno());
            String url = PropertiesUtil.getConfValue("pgyUrl") + "/monitor.action?act=addThreeBloodPressureDataTwo";
            String str = null;
            str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
            AppJson json = JsonUtil.fromJson(str,AppJson.class);
            if(json != null){
                if(json.getMsgCode().equals("800")){

                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 推送app高血压数据到慢病
     */
    @Async
    public void sendHbpNcd(AppUserBloodpressureVo qvo){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            if(StringUtils.isNotBlank(qvo.getUserId())){
                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getUserId());
                if(user != null){
                    if(StringUtils.isBlank(qvo.getSourceType())){
                        qvo.setSourceType(SourceType.APP.getValue());
                    }
                    JSONObject jsonParam = new JSONObject();

                    jsonParam.put("medicalRecordId",null);
                    jsonParam.put("authorizationCode",null);
                    jsonParam.put("deviceSim",null);

                    jsonParam.put("highPressure",qvo.getSys());
                    jsonParam.put("lowVoltage",qvo.getDia());
                    jsonParam.put("pulse",qvo.getPul());
                    jsonParam.put("uploadTime",ExtendDate.getTime(Calendar.getInstance()));
                    jsonParam.put("aOrB",null);
                    jsonParam.put("idCard",null);
                    jsonParam.put("sourceType",qvo.getSourceType());
                    jsonParam.put("name",user.getPatientName());
                    jsonParam.put("idno",user.getPatientIdno());
                    String url = PropertiesUtil.getConfValue("ncdHbpUrl") ;
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("threeJsonData",jsonParam.toString()));
                    String str = null;
                    str = HtmlUtils.getInstance().executeResponse(client, "post", url, params,"utf-8");
                    AppJson json = JsonUtil.fromJson(str,AppJson.class);
                    if(json != null){
                        if(json.getMsgCode().equals("800")){

                        }
                    }
                }
            }

        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * 推送app血糖数据到慢病
     * @param qvo
     */
    @Async
    public void sendDmNcd(AppUserBloodgluVo qvo){
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            if(StringUtils.isNotBlank(qvo.getUserId())){
                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getUserId());
                if(user != null){
                    if(StringUtils.isBlank(qvo.getSourceType())){
                        qvo.setSourceType(SourceType.APP.getValue());
                    }
                    JSONObject jsonParam = new JSONObject();
                    JSONObject jsonParam1 = new JSONObject();
                    jsonParam1.put("signature",null);
                    jsonParam1.put("timestamp",null);
                    jsonParam1.put("randomnumber",null);
                    jsonParam.put("head",jsonParam1);
                    JSONObject jsonParam2 = new JSONObject();
                    jsonParam2.put("code",null);
                    jsonParam2.put("devicesn",null);
                    jsonParam2.put("usercode",null);
                    jsonParam2.put("openid",null);
                    jsonParam2.put("result",qvo.getBloodglu());
                    jsonParam2.put("unit",null);
                    jsonParam2.put("testtime",qvo.getTesttime()+":00");
                    jsonParam2.put("aimstatus",null);
                    jsonParam2.put("createtime",null);
                    if("2".equals(qvo.getBgstate())){//餐前（）
                        qvo.setBgstate("0");//（三诺状态）
                    }else if("1".equals(qvo.getBgstate())){//餐后（三诺状态）
                        qvo.setBgstate("1");//（数据库状态）
                    }else{
                        qvo.setBgstate("0");//随机
                    }
                    jsonParam2.put("foodstatus",qvo.getBgstate());

                    jsonParam.put("msgdata",jsonParam2);
//                    jsonParam.put("reservedcode","01");
                    jsonParam.put("sourceType",qvo.getSourceType());
                    jsonParam.put("name",user.getPatientName());
                    jsonParam.put("idno",user.getPatientIdno());

                    String url = PropertiesUtil.getConfValue("ncdDmUrl") ;
                    String str = null;
                    str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                    AppJson json = JsonUtil.fromJson(str,AppJson.class);
                    if(json != null){
                        if(json.getMsgCode().equals("800")){

                        }
                    }
                }
            }

        }catch (Exception e){
            log.debug(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
