package com.ylz.view.hzapp.action;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthEntity.AppEnterpatientEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppCodeEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppFamilyInfo;
import com.ylz.bizDo.jtapp.commonEntity.AppFamilyinfoEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppMyFamilyAgeEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppMyFamilyEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.jtapp.patientVo.AppMyFamilyRegisterVo;
import com.ylz.bizDo.jtapp.patientVo.AppMyFamilyVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.comEnum.UserUpHpisType;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.JsonUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的家庭
 */
@SuppressWarnings("all")
@Action(
        value="hzFamily",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class HzFamilyAction extends CommonAction{

    private static final String familyCode = "48";//亲人
    private static final String familyMe = "49";//本人
    private static final Integer familyAge = 7;//默认年龄
    private static final String fmailyType ="非亲属";//类型
    /**
     * @return
     * 查询7岁以下儿童信息
     */
    public String appFindAgeMyFamily(){
        try {
            AppPatientUser user  = getAppPatientUser();
            if(user != null){
                List<AppMyFamilyAgeEntity> list = new ArrayList<AppMyFamilyAgeEntity>();
                List<AppMyFamilyAgeEntity> ls = this.getSysDao().getAppMyFamilyDao().findByOnlyAge(user.getId());
                if(ls != null && ls.size() >0){
                    for(AppMyFamilyAgeEntity p : ls){
                        Map<String,Object> idNos = new HashMap<String,Object>();
                        if(StringUtils.isNotBlank(p.getMfFmIdno())){
                            if(p.getMfFmIdno().length() == 18){
                                idNos = CardUtil.getCarInfo(p.getMfFmIdno());
                            }else{
                                idNos = CardUtil.getCarInfo15W(p.getMfFmIdno());
                            }
                            String birthday = idNos.get("birthday").toString();
                            int age = Integer.parseInt(AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday)));
                            if(age <= familyAge){
                                list.add(p);
                            }
                        }
                    }
                }
                this.getAjson().setRows(list);
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("用户未登入!");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    /**
     * 查询我的家庭
     * @return
     */
    public String appFindMyFamily(){
        try {
            AppPatientUser user  = getAppPatientUser();
            AppMyFamilyVo qvo = (AppMyFamilyVo)this.getAppJson(AppMyFamilyVo.class);
            List<AppMyFamilyEntity> ls = new ArrayList<AppMyFamilyEntity>();
            String result = null;
            if(user != null){
                result = user.getId();
            }else if(qvo != null && StringUtils.isNotBlank(qvo.getUserIdNo())){
                result = qvo.getUserIdNo();
            }
            if(StringUtils.isNotBlank(result)){
                ls = this.getSysDao().getAppMyFamilyDao().findByOnly(result);
            }
            if(user == null){
                user = this.getSysDao().getAppPatientUserDao().findByUserId(result);
            }
            AppMyFamilyEntity entity = new AppMyFamilyEntity();
            entity.setMfFmPatientId(user.getId());
            entity.setMfMyPatientId(user.getId());
            entity.setMfFmIdno(user.getPatientIdno());
            entity.setMfFmPatientCard(user.getPatientCard());
            entity.setMfFmNickCode(familyMe);
            entity.setMfFmNickName(familyMe);
            entity.setMfFmName(user.getPatientName());
            entity.setMfFmState("0");
            ls.add(0,entity);
            this.getAjson().setRows(ls);
//
//            if(ls != null && ls.size() >0){
//                this.getAjson().setRows(ls);
//            }else{
//                if(user != null){
//                    List<AppFamilyInfo> lsInfo = this.getSysDao().getSecurityCardAsyncBean().getFetchFamily(user.getPatientIdno(),user.getPatientName());
//                    if(lsInfo != null && lsInfo.size()>0){
//                        for(AppFamilyInfo info : lsInfo){
//                            if(fmailyType.equals(info.getRelation())){
//
//                            }else {
//                                if(info.getCode().equals(user.getPatientIdno())){
//                                    continue;
//                                }else{
//                                    AppMyFamily p = this.getSysDao().getAppMyFamilyDao().findByBdPatientIdNo(info.getCode(),user.getId());
//                                    if(p != null){
//
//                                    }else{
//                                        AppPatientUser bdUser = this.getSysDao().getAppPatientUserDao().findPatientIdNo(info.getCode());
//                                        if(bdUser != null){
//                                            p =new AppMyFamily();
//                                            p.setMfFmIdno(info.getCode());
//                                            p.setMyPatientId(user.getId());
//                                            p.setMfFmName(bdUser.getPatientName());
//                                            p.setMfFmNickName(faimlyCode);
//                                            p.setMfFmPatientId(bdUser.getId());
//                                            p.setMfFmAge(bdUser.getPatientAge());
//                                            p.setMfFmBirthday(bdUser.getPatientBirthday());
//                                            p.setMfFMGender(bdUser.getPatientGender());
//                                            this.getSysDao().getServiceDo().add(p);
//                                        }else{
//                                            AppMyFamilyRegisterVo vo = new AppMyFamilyRegisterVo();
//                                            vo.setNickName(faimlyCode);
//                                            vo.setUserIdNo(info.getCode());
//                                            vo.setUserName(info.getName());
//                                            vo.setUserTel(info.getTel());
//                                            this.getSysDao().getAppMyFamilyDao().addRegisterMyFamily(user,vo);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        ls = this.getSysDao().getAppMyFamilyDao().findByOnly(user.getId());
//                        this.getAjson().setRows(ls);
//                    }
//                }
//            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询计生接口家庭成员
     * @return
     */
    public String appFindSystemFmily(){
        try {
            AppPatientUser user  = getAppPatientUser();
            List<AppFamilyInfo> ls = new ArrayList<AppFamilyInfo>();
            String requestUserId = null;
            String requestUserName = null;
            String userType = null;
            if(user != null){
                userType = "1";
                requestUserId = user.getId();
                requestUserName = user.getPatientName();
            }
            List<AppFamilyInfo> lsFamily = this.getSysDao().getSecurityCardAsyncBean().getFetchFamily(user.getPatientIdno(),user.getPatientName(),requestUserId,requestUserName,userType);
            if(lsFamily != null && lsFamily.size() >0){
                for(AppFamilyInfo p : lsFamily){
                    if(p.getCode().equals(user.getPatientIdno())){

                    }else{
                        AppFamilyInfo v = new AppFamilyInfo();
                        v.setCode(p.getCode());
                        v.setName(p.getName());
                        v.setRelation(p.getRelation());
                        v.setTel(p.getTel());
                        v.setPatientId(user.getId());
                        ls.add(v);
                    }
                }
            }
            this.getAjson().setRows(ls);
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 添加家庭关系(多人)
     * @return
     */
    public String appAddMyFamilyMany(){
        try {
            AppPatientUser user  = getAppPatientUser();
            AppMyFamilyVo qvo = (AppMyFamilyVo)this.getAppJson(AppMyFamilyVo.class);
            if(qvo != null){
                String[] idNos = null;
                if(StringUtils.isNotBlank(qvo.getUserIdNo())){
                    idNos = qvo.getUserIdNo().split(";");
                }
                String[] names = null;
                if(StringUtils.isNotBlank(qvo.getUserName())){
                    names = qvo.getUserName().split(";");
                }
                String[] nickCodes = null;
                if(StringUtils.isNotBlank(qvo.getNickCode())){
                    nickCodes = qvo.getNickCode().split(";");
                }
                String[] userCards = null;
                if(StringUtils.isNotBlank(qvo.getUserCard())){
                    userCards = qvo.getUserCard().split(";");
                }
                if(idNos != null){
                    for(int i=0;i<idNos.length;i++){
                        if(idNos[i].equals(user.getPatientIdno())){
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("暂不允许添加本人信息!");
                            return  "ajson";
                        }else{
                            Map<String,Object> idNoMap;
                            if(qvo.getUserIdNo().length() == 18){
                                idNoMap = CardUtil.getCarInfo(idNos[i]);
                            }else{
                                idNoMap = CardUtil.getCarInfo15W(idNos[i]);
                            }
                            String birthday = idNoMap.get("birthday").toString();
                            String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                            AppMyFamily p = this.getSysDao().getAppMyFamilyDao().findByBdPatientIdNo(idNos[i],user.getId());
                            if(p != null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("该用户已在本家庭成员里,不能重复添加!");
                                return "ajson";
                            }else{
                                AppPatientUser bdUser = this.getSysDao().getAppPatientUserDao().findPatientIdNo(idNos[i]);
                                if(bdUser != null){
                                    p =new AppMyFamily();
                                    p.setMfFmIdno(idNos[i]);
                                    p.setMyPatientId(user.getId());
                                    p.setMfFmName(bdUser.getPatientName());
                                    p.setMfFmNickName(nickCodes[i]);
                                    p.setMfFmPatientId(bdUser.getId());
                                    p.setMfFmAge(bdUser.getPatientAge());
                                    p.setMfFmBirthday(bdUser.getPatientBirthday());
                                    p.setMfFMGender(bdUser.getPatientGender());
                                    p.setMfFmState(CommonEnable.JINYONG.getValue());
                                    if(StringUtils.isNotBlank(bdUser.getPatientCard())) {
                                        p.setMfFmCard(bdUser.getPatientCard());
                                    }else{
                                        p.setMfFmCard(userCards[i]);
                                        bdUser.setPatientCard(userCards[i]);
                                        sysDao.getServiceDo().modify(bdUser);
                                    }
                                    this.getSysDao().getServiceDo().add(p);
                                    this.getAjson().setMsg("添加成功");
                                }else{
                                    AppMyFamilyRegisterVo vo = new AppMyFamilyRegisterVo();
                                    vo.setNickName(nickCodes[i]);
                                    vo.setUserIdNo(idNos[i]);
                                    vo.setUserName(names[i]);
                                    vo.setUserCrad(userCards[i]);
                                    this.getSysDao().getAppMyFamilyDao().addRegisterMyFamily(user,vo);
                                    this.getAjson().setMsg("添加成功");
                                }
                            }
                        }
                    }
                }
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
                return "ajson";
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 添加家庭关系
     * @return
     */
    public String appAddMyFamily(){
        try {
            AppPatientUser user  = getAppPatientUser();
            AppMyFamilyVo qvo = (AppMyFamilyVo)this.getAppJson(AppMyFamilyVo.class);
            if(qvo != null){
                if(qvo.getUserIdNo().equals(user.getPatientIdno())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("暂不允许添加本人信息!");
                    return  "ajson";
                }else{
                    String resultIdNo = null;
                    if(StringUtils.isNotBlank(qvo.getUserIdNo())){
                        resultIdNo = CardUtil.IDCardValidate(qvo.getUserIdNo().toLowerCase());
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("身份证不予许为空!");
                        return "ajson";
                    }

                    if(StringUtils.isBlank(qvo.getUserCard())){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("社保卡不予许为空!");
                        return "ajson";
                    }

                    if(StringUtils.isNotBlank(resultIdNo)){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(resultIdNo);
                        return "ajson";
                    }
                    Map<String,Object> idNos;
                    if(qvo.getUserIdNo().length() == 18){
                        idNos = CardUtil.getCarInfo(qvo.getUserIdNo());
                    }else{
                        idNos = CardUtil.getCarInfo15W(qvo.getUserIdNo());
                    }
                    String birthday = idNos.get("birthday").toString();
                    String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
//                if(Integer.parseInt(age) <= 7){
                    AppMyFamily p = this.getSysDao().getAppMyFamilyDao().findByBdPatientIdNo(qvo.getUserIdNo(),user.getId());
                    if(p != null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("该用户已在本家庭成员里,不能重复添加!");
                        return "ajson";
                    }else{
                        boolean result = true;
                        if(StringUtils.isNotBlank(qvo.getMykh())) {
                            String str = this.sysDao.getSecurityCardAsyncBean().getFmqBymykh(qvo.getMykh(), user.getPatientName(),qvo.getUserIdNo(),user.getId(), DrPatientType.PATIENT.getValue());
                            if (str.equals("1")) {

                            } else {
                                result = false;
                            }
                        }
                        if(result){
                            AppPatientUser bdUser = this.getSysDao().getAppPatientUserDao().findPatientIdNo(qvo.getUserIdNo());
                            if(bdUser != null){
                                p =new AppMyFamily();
                                p.setMfFmIdno(qvo.getUserIdNo());
                                p.setMyPatientId(user.getId());
                                p.setMfFmName(bdUser.getPatientName());
                                p.setMfFmNickName(qvo.getNickCode());
                                p.setMfFmPatientId(bdUser.getId());
                                p.setMfFmAge(bdUser.getPatientAge());
                                p.setMfFmBirthday(bdUser.getPatientBirthday());
                                p.setMfFMGender(bdUser.getPatientGender());
//                                if(StringUtils.isNotBlank(bdUser.getPatientCard()))
                                p.setMfFmCard(qvo.getUserCard());
                                p.setMfFmState(CommonEnable.JINYONG.getValue());
                                if(StringUtils.isNotBlank(qvo.getMykh())) {
                                    p.setMfFmMykh(qvo.getMykh());
                                }
                                if(StringUtils.isBlank(bdUser.getPatientCard())){
                                    bdUser.setPatientCard(qvo.getUserCard());
                                    sysDao.getServiceDo().modify(bdUser);
                                }
                                this.getSysDao().getServiceDo().add(p);
                                this.getAjson().setMsg("添加成功");
                            }else{
                                AppMyFamilyRegisterVo vo = new AppMyFamilyRegisterVo();
                                vo.setNickName(qvo.getNickCode());
                                vo.setUserIdNo(qvo.getUserIdNo());
                                vo.setUserName(qvo.getUserName());
                                vo.setUserMykh(qvo.getMykh());
                                vo.setUserCrad(qvo.getUserCard());
                                this.getSysDao().getAppMyFamilyDao().addRegisterMyFamily(user,vo);
                                this.getAjson().setMsg("添加成功");
                            }
                        }else {
                            this.getAjson().setMsg("该儿童免疫卡号与父母信息对应不了，不能进行绑定!");
                            this.getAjson().setMsgCode("900");
                            return "ajson";
                        }
                    }
//                }else {
//                    this.getAjson().setMsg("系统暂只支持添加8岁以下家庭成员!");
//                    this.getAjson().setMsgCode("900");
//                }
                }
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
                return "ajson";
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 修改家庭关系(type:1免疫卡号,2昵称)
     * @return
     */
    public String appModifyMyFamily(){
        try {
            AppPatientUser user  = getAppPatientUser();
            AppMyFamilyVo qvo = (AppMyFamilyVo)this.getAppJson(AppMyFamilyVo.class);
            if(qvo != null){
                AppMyFamily family = (AppMyFamily)this.getSysDao().getServiceDo().find(AppMyFamily.class,qvo.getFamilyId());
                if(family != null){
                    if(qvo.getType().equals("1")){//修改免疫卡号
                        String str = this.sysDao.getSecurityCardAsyncBean().getFmqBymykh(qvo.getMykh(),user.getPatientName(),family.getMfFmIdno(),user.getId(),DrPatientType.PATIENT.getValue());
                        if(str.equals("1")){
                            family.setMfFmMykh(qvo.getMykh());
                        }else {
                            this.getAjson().setMsg("您好，匹配不到你输入的条形码，请重新输入!");
                            this.getAjson().setMsgCode("900");
                            return "ajson";
                        }
                    } else if(qvo.getType().equals("2")){//修改昵称
                        family.setMfFmNickName(qvo.getNickCode());
                    }
                    this.getSysDao().getServiceDo().modify(family);
                    this.getAjson().setMsg("修改成功!");
                }
            }else {
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
     * 删除家庭关系
     * @return
     */
    public String appDeleteMyFamily(){
        try {
            AppPatientUser user  = getAppPatientUser();
            AppMyFamilyVo qvo = (AppMyFamilyVo)this.getAppJson(AppMyFamilyVo.class);
            if(qvo != null){
                if(StringUtils.isNotBlank(qvo.getFamilyId())){
                    this.getSysDao().getServiceDo().delete(AppMyFamily.class,qvo.getFamilyId());
                    this.getAjson().setMsg("删除成功!");
                }else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("主键参数不能为空!");
                }
            }else {
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
     * 查询个人信息
     * @return
     */
    public String appFamilyIndividual(){
        try {
            AppMyFamilyVo qvo = (AppMyFamilyVo)this.getAppJson(AppMyFamilyVo.class);
            if(qvo != null){
                AppMyFamily family = (AppMyFamily)this.getSysDao().getServiceDo().find(AppMyFamily.class,qvo.getFamilyId());
                if(family != null){
                    Map<String,Object> idNos = new HashMap<String,Object>();
                    if(StringUtils.isNotBlank(family.getMfFmIdno())){
                        if(family.getMfFmIdno().length() == 18){
                            idNos = CardUtil.getCarInfo(family.getMfFmIdno());
                        }else{
                            idNos = CardUtil.getCarInfo15W(family.getMfFmIdno());
                        }
                        String birthday = idNos.get("birthday").toString();
                        String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                        family.setMfFmAge(age);
                    }

                   this.getAjson().setVo(family);
                }
            }else {
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
     * 注册家庭并添加家庭关系
     * @return
     */
    public String appRegisterMyFamily(){
        try {
            AppPatientUser vo  = getAppPatientUser();
            AppMyFamilyRegisterVo qvo = (AppMyFamilyRegisterVo)this.getAppJson(AppMyFamilyRegisterVo.class);
            if(qvo != null){
                boolean idNo = this.getSysDao().getAppPatientUserDao().findPatientByIdNo(qvo.getUserIdNo(), UserUpHpisType.JIHUO.getValue());
                if(idNo){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("身份证已存在,不能重复注册!");
                    return "ajson";
                }
                if(StringUtils.isNotBlank(qvo.getUserCrad())){
                    boolean card = this.getSysDao().getAppPatientUserDao().findPatientByCard(qvo.getUserCrad(),UserUpHpisType.JIHUO.getValue());
                    if(card){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("社保卡已存在,不能重复注册!");
                        return "ajson";
                    }
                }
                boolean tel = this.getSysDao().getAppPatientUserDao().findPatientByTel(qvo.getUserTel(),UserUpHpisType.JIHUO.getValue());
                if(tel){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("手机号已存在,不能重复注册!");
                    return "ajson";
                }
                qvo.setUserIdNo(qvo.getUserIdNo().toLowerCase());
                String resultIdNo = CardUtil.IDCardValidate(qvo.getUserIdNo());
                if(StringUtils.isNotBlank(resultIdNo)){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg(resultIdNo);
                    return "ajson";
                }else{
                    AppMyFamily p = this.getSysDao().getAppMyFamilyDao().findByBdPatientIdNo(qvo.getUserIdNo(),vo.getId());
                    if(p != null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("该用户已在本家庭成员里,不能重复添加!");
                    }else{
                       String result =  this.getSysDao().getAppMyFamilyDao().addRegisterMyFamily(vo,qvo);
                        this.getAjson().setMsg(result);
                    }
                }
            }else {
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
     * 查询家庭关系
     * @return
     */
    public String appFindMyFamilyCode(){
        try {
            AppPatientUser user  = getAppPatientUser();
//            List<CdCode> ls = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FAMILYRELATION,CommonEnable.QIYONG.getValue());
            List<AppCodeEntity> ls = this.getSysDao().getCodeDao().findCodeFamily();
            if(ls != null && ls.size() >0){
                this.getAjson().setRows(ls);
            }else{
                this.getAjson().setMsg("查无数据!");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 添加家庭关系
     * @return
     */
    public String appAddMyFamilySm(){
        try {
            AppMyFamilyVo qvo = (AppMyFamilyVo)this.getAppJson(AppMyFamilyVo.class);
            if(qvo != null){
//                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getUserId());
                List<AppPatientUser> lss = sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",qvo.getIdCard());
                if(lss!=null&&lss.size()>0){
                    AppPatientUser user = lss.get(0);
                    if(user!=null){
                        if(qvo.getUserIdNo().equals(user.getPatientIdno())){
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("暂不允许添加本人信息!");
                        }else{
                            Map<String,Object> idNos;
                            if(qvo.getUserIdNo().length() == 18){
                                idNos = CardUtil.getCarInfo(qvo.getUserIdNo());
                            }else{
                                idNos = CardUtil.getCarInfo15W(qvo.getUserIdNo());
                            }
                            String birthday = idNos.get("birthday").toString();
                            String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
//                if(Integer.parseInt(age) <= 7){
                            AppMyFamily p = this.getSysDao().getAppMyFamilyDao().findByBdPatientIdNo(qvo.getUserIdNo(),user.getId());
                            if(p != null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("该用户已在本家庭成员里,不能重复添加!");
                            }else{
                                boolean result = true;
                                if(StringUtils.isNotBlank(qvo.getMykh())) {
                                    String str = this.sysDao.getSecurityCardAsyncBean().getFmqBymykh(qvo.getMykh(), user.getPatientName(),qvo.getUserIdNo(),user.getId(),DrPatientType.PATIENT.getValue());
                                    if (str.equals("1")) {

                                    } else {
                                        result = false;
                                    }
                                }
                                if(result){
                                    AppPatientUser bdUser = this.getSysDao().getAppPatientUserDao().findPatientIdNo(qvo.getUserIdNo());
                                    if(bdUser != null){
                                        p =new AppMyFamily();
                                        p.setMfFmIdno(qvo.getUserIdNo());
                                        p.setMyPatientId(user.getId());
                                        p.setMfFmName(bdUser.getPatientName());
                                        p.setMfFmNickName(qvo.getNickCode());
                                        p.setMfFmPatientId(bdUser.getId());
                                        p.setMfFmAge(bdUser.getPatientAge());
                                        p.setMfFmBirthday(bdUser.getPatientBirthday());
                                        p.setMfFMGender(bdUser.getPatientGender());
                                        if(StringUtils.isNotBlank(bdUser.getPatientCard())) {
                                            p.setMfFmCard(bdUser.getPatientCard());
                                        }
                                        if(StringUtils.isNotBlank(qvo.getMykh())) {
                                            p.setMfFmMykh(qvo.getMykh());
                                        }
                                        this.getSysDao().getServiceDo().add(p);
                                        this.getAjson().setMsg("添加成功");
                                    }else{
                                        AppMyFamilyRegisterVo vo = new AppMyFamilyRegisterVo();
                                        vo.setNickName(qvo.getNickCode());
                                        vo.setUserIdNo(qvo.getUserIdNo());
                                        vo.setUserName(qvo.getUserName());
                                        vo.setUserMykh(qvo.getMykh());
                                        this.getSysDao().getAppMyFamilyDao().addRegisterMyFamily(user,vo);
                                        this.getAjson().setMsg("添加成功");
                                    }
                                }else {
                                    this.getAjson().setMsg("该儿童免疫卡号与父母信息对应不了，不能进行绑定!");
                                    this.getAjson().setMsgCode("900");
                                }
                            }
//                }else {
//                    this.getAjson().setMsg("系统暂只支持添加8岁以下家庭成员!");
//                    this.getAjson().setMsgCode("900");
//                }
                        }

                    }else{
                        this.getAjson().setMsg("查询不到当前用户信息");
                        this.getAjson().setMsgCode("900");
                    }
                }else{
                    this.getAjson().setMsg("查询不到当前用户信息");
                    this.getAjson().setMsgCode("900");
                }

            }else {
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
     * 查询我的家庭
     * @return
     */
    public String appFindMyFamilySm(){
        try {
            AppMyFamilyVo qvo = (AppMyFamilyVo)this.getAppJson(AppMyFamilyVo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppMyFamilyEntity> ls = new ArrayList<AppMyFamilyEntity>();
                List<AppPatientUser> lss = sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",qvo.getIdCard());
                if(lss!=null&&lss.size()>0){
                    AppPatientUser user = lss.get(0);
                    if(user != null){
                        ls = this.getSysDao().getAppMyFamilyDao().findByOnly(user.getId());
                    }
                    if(qvo != null){
                        ls = this.getSysDao().getAppMyFamilyDao().findByOnly(user.getId());
                    }

                    if(ls != null && ls.size() >0){
                        this.getAjson().setRows(ls);
                    }else{
                        if(user != null){
                            String requestUserId = null;
                            String requestUserName = null;
                            String userType = null;
                            if(user != null){
                                userType = "1";
                                requestUserId = user.getId();
                                requestUserName = user.getPatientName();
                            }
                            List<AppFamilyInfo> lsInfo = this.getSysDao().getSecurityCardAsyncBean().getFetchFamily(user.getPatientIdno(),user.getPatientName(),requestUserId,requestUserName,userType);
                            if(lsInfo != null && lsInfo.size()>0){
                                for(AppFamilyInfo info : lsInfo){
                                    if(fmailyType.equals(info.getRelation())){

                                    }else {
                                        if(info.getCode().equals(user.getPatientIdno())){
                                            continue;
                                        }else{
                                            AppMyFamily p = this.getSysDao().getAppMyFamilyDao().findByBdPatientIdNo(info.getCode(),user.getId());
                                            if(p != null){

                                            }else{
                                                AppPatientUser bdUser = this.getSysDao().getAppPatientUserDao().findPatientIdNo(info.getCode());
                                                if(bdUser != null){
                                                    p =new AppMyFamily();
                                                    p.setMfFmIdno(info.getCode());
                                                    p.setMyPatientId(user.getId());
                                                    p.setMfFmName(bdUser.getPatientName());
                                                    p.setMfFmNickName(familyCode);
                                                    p.setMfFmPatientId(bdUser.getId());
                                                    p.setMfFmAge(bdUser.getPatientAge());
                                                    p.setMfFmBirthday(bdUser.getPatientBirthday());
                                                    p.setMfFMGender(bdUser.getPatientGender());
                                                    this.getSysDao().getServiceDo().add(p);
                                                }else{
                                                    AppMyFamilyRegisterVo vo = new AppMyFamilyRegisterVo();
                                                    vo.setNickName(familyCode);
                                                    vo.setUserIdNo(info.getCode());
                                                    vo.setUserName(info.getName());
                                                    vo.setUserTel(info.getTel());
                                                    this.getSysDao().getAppMyFamilyDao().addRegisterMyFamily(user,vo);
                                                }
                                            }
                                        }
                                    }
                                }
                                ls = this.getSysDao().getAppMyFamilyDao().findByOnly(user.getId());
                                this.getAjson().setRows(ls);
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

    /**
     * 医生查询居民计生接口家庭成员
     * @return
     */
    public String appFindSystemFmilyTwo(){
        try {
            AppMyFamilyVo qvo = (AppMyFamilyVo)this.getAppJson(AppMyFamilyVo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppFamilyinfoEntity> ls = new ArrayList<AppFamilyinfoEntity>();
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppDrUser drUser = this.getAppDrUser();
                AppHospDept dept = null;
                if(drUser != null){
                    userType = "2";
                    requestUserId = drUser.getId();
                    requestUserName = drUser.getDrName();
                    dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                }
                if(StringUtils.isBlank(qvo.getUserIdNo())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("居民身份证不能为空!");
                    return "ajson";
                }
                List<AppFamilyInfo> lsFamily = this.getSysDao().getSecurityCardAsyncBean().getFetchFamily(qvo.getUserIdNo(),qvo.getUserName(),requestUserId,requestUserName,userType);
                if(lsFamily != null && lsFamily.size() >0){
                    for(AppFamilyInfo p : lsFamily){
                        if(p.getCode().equals(qvo.getUserIdNo())){

                        }else{
                            AppFamilyinfoEntity v = new AppFamilyinfoEntity();
                            v.setCode(p.getCode());
                            v.setName(p.getName());
                            v.setRelation(p.getRelation());
                            v.setTel(p.getTel());
                            v.setPatientId(qvo.getUserIdNo());
                            HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                            qqvo.setIdno(p.getCode());
                            qqvo.setType("2");
                            if(dept!=null){
                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, dept.getHospAreaCode().substring(0,4));
                                if(value!=null){
                                    qqvo.setUrlType(value.getCodeTitle());
                                }
                            }else{
                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, p.getCode().substring(0,4));
                                if(value!=null){
                                    qqvo.setUrlType(value.getCodeTitle());
                                }
                            }
                            String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,requestUserId,requestUserName,userType);
                            if(StringUtils.isNotBlank(str)) {
                                JSONObject jsonall = JSONObject.fromObject(str);
                                if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")){
                                    if (jsonall.get("entity") != null) {
                                        JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                        if(AddressType.ND.getValue().equals(qqvo.getUrlType()) || AddressType.PINGT.getValue().equals(qqvo.getUrlType())){//宁德
                                            if(entity != null && "800".equals(entity.get("msgCode").toString())){
                                                List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(),new TypeToken<List<AppEnterpatientEntity>>() {}.getType());
                                                if(list != null && list.size()>0){
                                                    v.setJdState(list.get(0).getDf_id());
                                                }
                                            }
                                        }else{
                                            if ("true".equals(entity.get("success").toString())) {
                                                T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                                if (entitys != null) {
                                                    if (StringUtils.isNotBlank(entitys.getJmdah())) {
                                                        v.setJdState(entitys.getJmdah());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            //查询签约情况
                            AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(p.getCode(), UserUpHpisType.JIHUO.getValue());
                            if(user!=null){
                                AppSignForm form = sysDao.getAppSignformDao().findSignFormByUser(user.getId());
                                if(form!=null){
                                    v.setSignState(form.getSignState());
                                }
                            }
                            ls.add(v);
                        }
                    }
                }
                this.getAjson().setRows(ls);
            }

        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
}
