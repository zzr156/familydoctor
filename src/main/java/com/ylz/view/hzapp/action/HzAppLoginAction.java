package com.ylz.view.hzapp.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppMsgQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthEntity.EnterpatientEntity;
import com.ylz.bizDo.jtapp.basicHealthVo.EnterpatientVo;
import com.ylz.bizDo.jtapp.commonEntity.AppCardEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppCardPerfectEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCardQvo;
import com.ylz.bizDo.jtapp.commonVo.AppPwdQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTelQvo;
import com.ylz.bizDo.jtapp.drEntity.PatientInfo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientChangeInfoEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientPerfectDataEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserWechatEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPatientResgisterQvo;
import com.ylz.bizDo.jtapp.patientVo.AppPatientUserQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import com.ylz.packcommon.common.wechat.entity.OBJ;
import com.ylz.packcommon.integrate.sdk.bean.RequestParams;
import com.ylz.packcommon.integrate.sdk.enums.HtmlType;
import com.ylz.packcommon.integrate.sdk.util.Signature;
import com.ylz.packcommon.integrate.sdk.util.dtkl.DtklUtil;
import com.ylz.task.async.IdCard.IdCardEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.net.URLDecoder;
import java.util.*;

/**
 * 个人信息登录接口action.
 */
@SuppressWarnings("all")
@Action(
        value="hzlogin",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class HzAppLoginAction extends CommonAction {

    private static final  int yxDay = 15;

    /**
     * app登录接口
     * @return
     */
    public String appLogin(){
        try{
            AppPatientUserQvo qvo = (AppPatientUserQvo)getAppJson(AppPatientUserQvo.class);
            if(qvo != null){
                if(StringUtils.isNotBlank(qvo.getSelectType())){
                    AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findByUser(qvo.getUserAccount(),qvo.getUserPass(),qvo.getSelectType());
                    if(qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())){
                        if(patient != null){
                            String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneCheck(patient.getPatientIdno(), patient.getPatientTel(), qvo.getUserShort());
                            if(!"1".equals(code)){
                                this.getAjson().setMsgCode("1100");
                                this.getAjson().setMsg("验证码过期,请重新获取!");
                                return "ajson";
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("手机号不存在，请输入正确手机号！");
                            return "ajson";
                        }
                    }
                    if(patient != null){
                        AppPatientUser user = (AppPatientUser)this.getSysDao().getServiceDo().find(AppPatientUser.class,patient.getId());
                        if(user.getPatientPwd().equals(qvo.getUserPass()) || qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())){
                            if(StringUtils.isNotBlank(patient.getPatientNeighborhoodCommittee())){
                                AppHospDept dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientNeighborhoodCommittee());
                                if(dept != null){
                                    patient.setPatientCommunity(dept.getId());
                                }else{
                                    dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                                    if(dept != null) {
                                        patient.setPatientCommunity(dept.getId());
                                    }
                                }
                            }else if(StringUtils.isNotBlank(patient.getPatientStreet())){
                                AppHospDept dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                                if(dept != null) {
                                    patient.setPatientCommunity(dept.getId());
                                }
                            }
                            Md5Util util = new Md5Util();
                            String key = qvo.getUserAccount()+ ExtendDate.getYMD_h_m_s(Calendar.getInstance());
                            key = util.MD516(key);
                            AppDrPatientKey uKey = this.sysDao.getAppDrPatientKeyDao().findByDoctorOrPatientId(patient.getId());
                            String day = ExtendDateUtil.addDate(ExtendDate.getYMD(Calendar.getInstance()), ExtendDateType.DAYS.getValue(),yxDay);
                            if(uKey != null){
                                uKey.setDrToken(key);
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
                                patient.setPatientFirstState(CommonEnable.QIYONG.getValue());
                                uKey.setDrPatientLastDate(uKey.getHsUpdateTime());
                                this.sysDao.getServiceDo().modify(uKey);
                            }else{
                                uKey = new AppDrPatientKey();
                                uKey.setDrToken(key);
                                uKey.setDrPatientId(patient.getId());
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
                                uKey.setDrPatientLastDate(Calendar.getInstance());
                                if(StringUtils.isBlank(patient.getPatientIdno())){
                                    patient.setPatientFirstState(CommonEnable.JINYONG.getValue());
                                }else{
                                    patient.setPatientFirstState(CommonEnable.QIYONG.getValue());
                                }
                                this.getSysDao().getServiceDo().add(uKey);
                            }
                            user.setPatientJgState(UserJgType.YISHEZHI.getValue());
                            this.sysDao.getServiceDo().modify(user);
                            PatientInfo info = sysDao.getAppPatientUserDao().findUserInfo(patient.getId(),"0",null);
                            if(info!=null){
                                patient.setSignType(info.getJQ());
                                patient.setRetireHome(info.getJjyl());
                                patient.setRetireHomeColor(info.getJjylcolor());
                                patient.setLabTitle(info.getLabtitle());
                                patient.setLabColor(info.getLabcolor());
                            }
                            this.getAjson().setUkey(key);
                            this.getAjson().setVo(patient);
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("账号密码不匹配!");
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("账号不存在，请输入正确账号！");
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
                }
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
     * 注册
     * @return
     */
    public String appRegister(){
        try{
            AppPatientResgisterQvo qvo = (AppPatientResgisterQvo)getAppJson(AppPatientResgisterQvo.class);
            if(qvo!=null){
                String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneCheck(qvo.getUserTel(), qvo.getUserTel(), qvo.getUserShort());
                if(!"1".equals(code)){
                    this.getAjson().setMsgCode("1100");
                    this.getAjson().setMsg("验证码过期,请重新获取!");
                }else{
                    if(StringUtils.isNotBlank(qvo.getUserIdNo())){
                        boolean idNo = this.getSysDao().getAppPatientUserDao().findPatientByIdNo(qvo.getUserIdNo(),UserUpHpisType.JIHUO.getValue());
                        if(idNo){
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("身份证已存在,不能重复注册!");
                            return "ajson";
                        }
                    }
                    if(StringUtils.isNotBlank(qvo.getUserCard())){
                        boolean card = this.getSysDao().getAppPatientUserDao().findPatientByCard(qvo.getUserCard(),UserUpHpisType.JIHUO.getValue());
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
                    String userIdNo = null;
                    String resultIdNo = null;
                    if(StringUtils.isNotBlank(qvo.getUserIdNo())){
                        userIdNo = qvo.getUserIdNo();
                        qvo.setUserIdNo(qvo.getUserIdNo().toLowerCase());
                        resultIdNo = CardUtil.IDCardValidate(qvo.getUserIdNo());
                    }

                    if(StringUtils.isNotBlank(resultIdNo)){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(resultIdNo);
                        return "ajson";
                    }else{
                        AppPatientUser user = new AppPatientUser();
                        if(StringUtils.isNotBlank(qvo.getUserIdNo())){
                            Map<String,Object> idNos;
                            if(qvo.getUserIdNo().length() == 18){
                                idNos = CardUtil.getCarInfo(qvo.getUserIdNo());
                            }else{
                                idNos = CardUtil.getCarInfo15W(qvo.getUserIdNo());
                            }
                            user.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));//出生日期
                            user.setPatientAge(AgeUtil.getAgeYear(user.getPatientBirthday()));//年龄
                            user.setPatientGender(idNos.get("sex").toString());//性别
                            user.setPatientIdno(userIdNo.toUpperCase());//身份证
                        }
                        if(StringUtils.isNotBlank(qvo.getUserName())){
                            user.setPatientName(qvo.getUserName());
                        }
                        if(StringUtils.isNotBlank(qvo.getUserCard())){
                            user.setPatientCard(qvo.getUserCard());
                        }
                        user.setPatientTel(qvo.getUserTel());
                        user.setPatientState(CommonEnable.QIYONG.getValue());
                        user.setPatientHealthy(CommonEnable.JINYONG.getValue());
                        user.setPatientJgState(UserJgType.WEISHEZHI.getValue());
                        user.setPatientEaseState(UserJgType.WEISHEZHI.getValue());
                        user.setPatientPwd(Md5Util.MD5(qvo.getUserTel().substring(qvo.getUserTel().length()-6,qvo.getUserTel().length())));
                        user.setPatientUpHpis(UserUpHpisType.JIHUO.getValue());//用户激活
                        this.getSysDao().getServiceDo().add(user);
                        //添加社保卡号
                        this.getSysDao().getSecurityCardAsyncBean().getSecurityCard(user.getPatientIdno(),user.getPatientName(),user.getId());
                        this.getAjson().setMsg("注册成功!");
                    }
                }
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
     * 健康三明注册
     * @param userTel
     * @param password
     * @return
     */
    public String appJksmRegister(){
        try{
            AppPatientResgisterQvo qvo = (AppPatientResgisterQvo)getAppJson(AppPatientResgisterQvo.class);
            if(qvo!=null){
                boolean tel = this.getSysDao().getAppPatientUserDao().findPatientByTel(qvo.getUserTel(),UserUpHpisType.JIHUO.getValue());
                if(tel){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("手机号已存在,不能重复注册!");
                    return "ajson";
                }

                AppPatientUser user = new AppPatientUser();

                user.setPatientTel(qvo.getUserTel());
                user.setPatientState(CommonEnable.QIYONG.getValue());
                user.setPatientHealthy(CommonEnable.JINYONG.getValue());
                user.setPatientJgState(UserJgType.WEISHEZHI.getValue());
                user.setPatientEaseState(UserJgType.WEISHEZHI.getValue());
                if(StringUtils.isNotBlank(qvo.getPassword())) {
                    user.setPatientPwd(Md5Util.MD5(qvo.getPassword()));
                }
                user.setPatientUpHpis(UserUpHpisType.JIHUO.getValue());//用户激活
                this.getSysDao().getServiceDo().add(user);
                //添加社保卡号
                this.getSysDao().getSecurityCardAsyncBean().getSecurityCard(user.getPatientIdno(),user.getPatientName(),user.getId());
                this.getAjson().setMsg("注册成功!");


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
     * 完善资料
     * @return
     */
    public String appPerfectData(){
        try{
            AppPatientPerfectDataEntity vo = (AppPatientPerfectDataEntity)this.getAppJson(AppPatientPerfectDataEntity.class);
            if(vo != null){
                AppPatientUser user = (AppPatientUser)this.getSysDao().getServiceDo().find(AppPatientUser.class,vo.getId());
                if(user != null){
                    if(StringUtils.isNotBlank(user.getPatientName())){
                        vo.setPatientName(user.getPatientName());
                    }
                    if(StringUtils.isBlank(vo.getPatientName())){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("请填写姓名!");
                        return "ajson";
                    }
                    if(StringUtils.isBlank(vo.getPatientIdNo())){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("请填写身份证!");
                        return "ajson";
                    }else{
                        //医生代签约的居民信息已有身份证信息，在保存完善资料时总提示身份证已存在而不能保存
//                        boolean flag = sysDao.getAppPatientUserDao().findByIdNoAndJh(vo.getId(),vo.getPatientIdNo(),UserUpHpisType.JIHUO.getValue());

                        boolean idNo = this.getSysDao().getAppPatientUserDao().findPatientByIdNo(vo.getPatientIdNo(),UserUpHpisType.JIHUO.getValue());
                        if(idNo){
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("身份证已存在,不能重复填写!");
                            return "ajson";
                        }
                        String userIdNo = null;
                        String resultIdNo = null;
                        if(StringUtils.isNotBlank(vo.getPatientIdNo())){
                            userIdNo = vo.getPatientIdNo();
                            vo.setPatientIdNo(vo.getPatientIdNo().toLowerCase());
                            resultIdNo = CardUtil.IDCardValidate(vo.getPatientIdNo());
                        }

                        if(StringUtils.isNotBlank(resultIdNo)){
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg(resultIdNo);
                            return "ajson";
                        }
                    }


                    //完善资料
                    String userId = this.getSysDao().getAppPatientUserDao().addPerfectData(vo);

                    AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findUserId(userId);
                    if(StringUtils.isNotBlank(patient.getPatientNeighborhoodCommittee())){
                        AppHospDept dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientNeighborhoodCommittee());
                        if(dept != null){
                            patient.setPatientCommunity(dept.getId());
                        }else{
                            dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                            if(dept != null) {
                                patient.setPatientCommunity(dept.getId());
                            }
                        }
                    }else if(StringUtils.isNotBlank(patient.getPatientStreet())){
                        AppHospDept dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                        if(dept != null) {
                            patient.setPatientCommunity(dept.getId());
                        }
                    }
                    PatientInfo info = sysDao.getAppPatientUserDao().findUserInfo(patient.getId(),"0",null);
                    if(info!=null){
                        patient.setSignType(info.getJQ());
                        patient.setRetireHome(info.getJjyl());
                        patient.setRetireHomeColor(info.getJjylcolor());
                        patient.setLabTitle(info.getLabtitle());
                        patient.setLabColor(info.getLabcolor());
                    }
                    this.getAjson().setVo(patient);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查无该信息");
                }
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
     * 密码修改
     * @return
     */
    public String appChangePwd(){
        try{
            AppPwdQvo qvo = (AppPwdQvo)getAppJson(AppPwdQvo.class);
            if(qvo!=null){
                AppPatientUser user = this.getAppPatientUser();
                if(qvo.getOldPwd().equals(user.getPatientPwd())){
                    AppPatientUser v = (AppPatientUser)this.getSysDao().getServiceDo().find(AppPatientUser.class,user.getId());
                    v.setPatientPwd(qvo.getNewPwd());
                    v.setPatientd("1");
                    this.getSysDao().getServiceDo().modify(v);
                    this.getAjson().setMsg("修改密码成功");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("旧密码有误，请重新输入");
                }
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
     * 修改手机
     * @return
     */
    public String appChangeTel(){
        try{
            AppTelQvo qvo = (AppTelQvo)getAppJson(AppTelQvo.class);
            if(qvo!=null){
                AppPatientUser user = this.getAppPatientUser();
                String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneCheck(qvo.getNewTel(), qvo.getNewTel(), qvo.getUserShort());
                if(!"1".equals(code)){
                    this.getAjson().setMsgCode("1100");
                    this.getAjson().setMsg("验证码过期,请重新获取!");
                    return "ajson";
                }else{
                    boolean tel = this.getSysDao().getAppPatientUserDao().findPatientByTel(qvo.getNewTel(),UserUpHpisType.JIHUO.getValue());
                    if(tel){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("手机号已存在,不能填写已存在手机!");
                        return "ajson";
                    }
                    AppPatientUser v = (AppPatientUser)this.getSysDao().getServiceDo().find(AppPatientUser.class,user.getId());
                    v.setPatientTel(qvo.getNewTel());
                    this.getSysDao().getServiceDo().modify(v);
                    this.getAjson().setMsg("修改手机成功");
                }
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
     * 查询社保卡
     * @return
     */
    public String appFindChangeCard(){
        try{
            AppCardQvo qvo = (AppCardQvo)getAppJson(AppCardQvo.class);
            if(qvo!=null){
                if(StringUtils.isBlank(qvo.getUserIdNo()) && StringUtils.isNotBlank(qvo.getUserName())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("身份证和身份证姓名参数不能为空!");
                    return "ajson";
                }else {
                    String requestUserId = null;
                    String requestUserName = null;
                    String userType = null;
                    AppPatientUser patientUser = this.getAppPatientUser();
                    if(patientUser!=null){
                        userType = "1";
                        requestUserId = patientUser.getId();
                        requestUserName = patientUser.getPatientName();
                    }else{
                        AppDrUser drUser = this.getAppDrUser();
                        if(drUser!=null){
                            userType = "2";
                            requestUserId = drUser.getId();
                            requestUserName = drUser.getDrName();
                        }
                    }
                    String result = this.getSysDao().getSecurityCardAsyncBean().getSecurityCardNotAsync(qvo.getUserIdNo(),null,requestUserId,requestUserName,userType);
                    AppCardEntity entity = new AppCardEntity();
                    entity.setUserCard(result);
                    this.getAjson().setVo(entity);
                }
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
     * 查询社保卡(完善资料)
     * @return
     */
    public String appFindChangeCardPerfect(){
        try{
            AppCardQvo qvo = (AppCardQvo)getAppJson(AppCardQvo.class);
            if(qvo!=null){
                if(StringUtils.isBlank(qvo.getUserIdNo()) && StringUtils.isNotBlank(qvo.getUserName())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("身份证和身份证姓名参数不能为空!");
                    return "ajson";
                }else {
                    List<AppCardPerfectEntity> lsEntity = new ArrayList<AppCardPerfectEntity>();
                    String requestUserId = null;
                    String requestUserName = null;
                    String userType = null;
                    AppPatientUser patientUser = this.getAppPatientUser();
                    if(patientUser!=null){
                        userType = "1";
                        requestUserId = patientUser.getId();
                        requestUserName = patientUser.getPatientName();
                    }else{
                        AppDrUser drUser = this.getAppDrUser();
                        if(drUser!=null){
                            userType = "2";
                            requestUserId = drUser.getId();
                            requestUserName = drUser.getDrName();
                        }
                    }
                    List<IdCardEntity> ls = this.getSysDao().getSecurityCardAsyncBean().getSecurityCardListAsync(qvo.getUserIdNo(),null,requestUserId,requestUserName,userType);
                    if(ls != null && ls.size() >0){
                        for(IdCardEntity entity : ls){
                            if(IdCardStatus.ZC.getValue().equals(entity.getStatusCode())){
                                AppCardPerfectEntity p = new AppCardPerfectEntity();
                                p.setUserCard(entity.getSsCard());
                                EnterpatientVo qvos = new EnterpatientVo();
                                qvos.setCurrentPage("1");
                                qvos.setIdcardno(qvo.getUserIdNo());
                                String code = null;
                                if(StringUtils.isNotBlank(entity.getAreaCode())){
                                    code = AreaUtils.getAreaCode(entity.getAreaCode(),"2");
                                }else{
                                    code = IdCardAreaUtils.getIdCardidCard(entity.getIdCard());
                                }
                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                                if(value != null) {
                                    qvos.setUrlType(value.getCodeTitle());
                                }
                                List<EnterpatientEntity> lsEnter = this.getSysDao().getSecurityCardAsyncBean().getEnterpatientList(qvos,requestUserId,requestUserName,userType);
                                if(lsEnter != null && lsEnter.size()>0){
                                    p.setPatientProvinceName(lsEnter.get(0).getAdress_pro());
                                    p.setPatientCityName(lsEnter.get(0).getAdress_city());
                                    p.setPatientAreaName(lsEnter.get(0).getAdress_county());
                                    p.setPatientStreetName(lsEnter.get(0).getAdress_rural());
                                    p.setPatientAddress(lsEnter.get(0).getAdress_village()+lsEnter.get(0).getAdrss_hnumber());
                                    if(StringUtils.isNotBlank(p.getPatientProvinceName())){
                                        CdAddress address = this.getSysDao().getCdAddressDao().findByName(p.getPatientProvinceName(),null);
                                        if(address!=null) {
                                            p.setPatientProvince(address.getCtcode());
                                        }else {
                                            p.setPatientProvince(null);
                                        }
                                    }
                                    if(StringUtils.isNotBlank(p.getPatientCityName())){
                                        CdAddress address = this.getSysDao().getCdAddressDao().findByName(p.getPatientCityName(),p.getPatientProvince());
                                        if(address!=null) {
                                            p.setPatientCity(address.getCtcode());
                                        }else{
                                            p.setPatientCity(null);
                                        }
                                    }
                                    if(StringUtils.isNotBlank(p.getPatientAreaName())){
                                        CdAddress address = this.getSysDao().getCdAddressDao().findByNameAndUp(p.getPatientAreaName(),p.getPatientCity());
                                        if(address!=null) {
                                            p.setPatientArea(address.getCtcode());
                                        }else {
                                            p.setPatientArea(null);
                                        }
                                    }
                                    if(StringUtils.isNotBlank(p.getPatientStreetName())){
                                        CdAddress address = this.getSysDao().getCdAddressDao().findByNameAndUp(p.getPatientStreetName(),p.getPatientArea());
                                        if(address!=null) {
                                            p.setPatientStreet(address.getCtcode());
                                        }else{
                                            p.setPatientStreetName(null);
                                        }
                                    }
                                }
                                lsEntity.add(p);
                            }
                        }
                    }
                    this.getAjson().setRows(lsEntity);
                }
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
     * 社保卡修改
     * @return
     */
    public String appChangeCard(){
        try{
            AppCardQvo qvo = (AppCardQvo)getAppJson(AppCardQvo.class);
            AppPatientUser user = this.getAppPatientUser();
            if(qvo!=null && user != null){
                if(!qvo.getNewUserCard().equals(user.getPatientCard())){
                    boolean card = this.getSysDao().getAppPatientUserDao().findPatientByCard(qvo.getNewUserCard(),UserUpHpisType.JIHUO.getValue());
                    if(card){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("社保卡已存在,不能重复填写!");
                        return "ajson";
                    }
                }
                AppPatientUser v = (AppPatientUser)this.getSysDao().getServiceDo().find(AppPatientUser.class,user.getId());
                v.setPatientCard(qvo.getNewUserCard());
                this.getSysDao().getServiceDo().modify(v);
                this.getAjson().setMsg("修改社保卡成功");
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
     *修改个人资料
     * @return
     */
    public String appChangeInfo(){
        try{
            AppPatientChangeInfoEntity vo = (AppPatientChangeInfoEntity)this.getAppJson(AppPatientChangeInfoEntity.class);
            if(vo != null){
                AppPatientUser user = (AppPatientUser)this.getSysDao().getServiceDo().find(AppPatientUser.class,vo.getId());
                if(user != null){
                    if(StringUtils.isNotBlank(vo.getPatientGender())){
                        user.setPatientGender(vo.getPatientGender());
                    }
                    if(StringUtils.isNotBlank(vo.getPatientName())){
                        user.setPatientName(vo.getPatientName());
                    }
                    if(StringUtils.isNotBlank(vo.getPatientHealthy())){
                        user.setPatientHealthy(vo.getPatientHealthy());
                    }
                    if(StringUtils.isNotBlank(vo.getPatientProvince())){
                        user.setPatientProvince(vo.getPatientProvince());
                    }
                    if(StringUtils.isNotBlank(vo.getPatientCity())){
                        user.setPatientCity(vo.getPatientCity());
                    }
                    if(StringUtils.isNotBlank(vo.getPatientNeighborhoodCommittee())){
                        user.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
                    }
                    if(StringUtils.isNotBlank(vo.getPatientArea())){
                        user.setPatientArea(vo.getPatientArea());
                    }
                    if(StringUtils.isNotBlank(vo.getPatientStreet())){
                        user.setPatientStreet(vo.getPatientStreet());
                    }
                    if(StringUtils.isNotBlank(vo.getPatientAbscissa())){
                        user.setPatientAbscissa(vo.getPatientAbscissa());
                        user.setPatientOrdinate(vo.getPatientOrdinate());
                    }
                    if(StringUtils.isNotBlank(vo.getPatientImageurl())){
                        Map<String,Object> map = this.getSysDao().getIoUtils().getCtyunOosSample(vo.getPatientImageurl(),CommonShortType.HUANGZHE.getValue());
                        user.setPatientImageurl(map.get("objectUrl").toString());
                        user.setPatientImageName(map.get("objectName").toString());
                    }
//                    if(StringUtils.isNotBlank(vo.getPatientAddress())){
                        user.setPatientAddress(vo.getPatientAddress());
//                    }
                    if(StringUtils.isNotBlank(vo.getPatientHeight())){
                        user.setPatientHeight(vo.getPatientHeight());
                    }
                    if(StringUtils.isNotBlank(vo.getPatientWeight())){
                        user.setPatientWeight(vo.getPatientWeight());
                    }
                    if(StringUtils.isNotBlank(user.getPatientWeight()) && StringUtils.isNotBlank(user.getPatientHeight())){
                        double height = MyMathUtil.div(Double.parseDouble(user.getPatientHeight()),100,2);//身高
                        double weight = Double.parseDouble(user.getPatientWeight());//体重
                        double heightResult = MyMathUtil.mul(height,height);
                        double result = MyMathUtil.div(weight,heightResult,2);
                        user.setPatientBmi(String.valueOf(result));
                    }
                    this.getSysDao().getServiceDo().modify(user);
                    AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findUserId(user.getId());
                    Map<String,Object> idNos = new HashMap<String,Object>();
                    if(StringUtils.isNotBlank(patient.getPatientIdno())){
                        if(patient.getPatientIdno().length() == 18){
                            idNos = CardUtil.getCarInfo(patient.getPatientIdno());
                        }else{
                            idNos = CardUtil.getCarInfo15W(patient.getPatientIdno());
                        }
                        String birthday = idNos.get("birthday").toString();
                        patient.setPatientAge(AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday)));
                    }
                    if(StringUtils.isNotBlank(patient.getPatientNeighborhoodCommittee())){
                        AppHospDept dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientNeighborhoodCommittee());
                        if(dept != null){
                            patient.setPatientCommunity(dept.getId());
                        }else{
                            dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                            if(dept != null) {
                                patient.setPatientCommunity(dept.getId());
                            }
                        }
                    }else if(StringUtils.isNotBlank(patient.getPatientStreet())){
                        AppHospDept dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                        if(dept != null) {
                            patient.setPatientCommunity(dept.getId());
                        }
                    }
                    PatientInfo info = sysDao.getAppPatientUserDao().findUserInfo(patient.getId(),"0",null);
                    if(info!=null){
                        patient.setSignType(info.getJQ());
                        patient.setRetireHome(info.getJjyl());
                        patient.setRetireHomeColor(info.getJjylcolor());
                        patient.setLabTitle(info.getLabtitle());
                        patient.setLabColor(info.getLabcolor());
                    }
                    this.getAjson().setVo(patient);
                    this.setMsg("修改成功");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查无该信息");
                }
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
     * 查询个人信息
     */
    public String appPatientUserInfo(){
        try{
            AppPatientChangeInfoEntity vo = (AppPatientChangeInfoEntity)this.getAppJson(AppPatientChangeInfoEntity.class);
            if(vo != null){
                AppPatientUser user = (AppPatientUser)this.getSysDao().getServiceDo().find(AppPatientUser.class,vo.getId());
                AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findUserId(user.getId());
                Map<String,Object> idNos = new HashMap<String,Object>();
                if(StringUtils.isNotBlank(patient.getPatientIdno())){
                    if(patient.getPatientIdno().length() == 18){
                        idNos = CardUtil.getCarInfo(patient.getPatientIdno());
                    }else{
                        idNos = CardUtil.getCarInfo15W(patient.getPatientIdno());
                    }
                    String birthday = idNos.get("birthday").toString();
                    String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                    patient.setPatientAge(age);
                }
                if(StringUtils.isNotBlank(patient.getPatientNeighborhoodCommittee())){
                    AppHospDept dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientNeighborhoodCommittee());
                    if(dept != null){
                        patient.setPatientCommunity(dept.getId());
                    }else{
                        dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                        if(dept != null) {
                            patient.setPatientCommunity(dept.getId());
                        }
                    }
                }else if(StringUtils.isNotBlank(patient.getPatientStreet())){
                    AppHospDept dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                    patient.setPatientCommunity(dept.getId());
                }
                PatientInfo info = sysDao.getAppPatientUserDao().findUserInfo(patient.getId(),"0",null);
                if(info!=null){
                    patient.setSignType(info.getJQ());
                    patient.setRetireHome(info.getJjyl());
                    patient.setRetireHomeColor(info.getJjylcolor());
                    patient.setLabTitle(info.getLabtitle());
                    patient.setLabColor(info.getLabcolor());
                }
                this.getAjson().setVo(patient);
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("查无该信息");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }


    /**
     * app忘记密码
     * @return
     */
    public String apForgetPwd(){
        try{
            AppPwdQvo qvo = (AppPwdQvo)getAppJson(AppPwdQvo.class);
            if(qvo != null){
                    AppPatientUser patient = this.getSysDao().getAppPatientUserDao().findByTelPhone(qvo.getUserTel());
                        if(patient != null){
                            String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneCheck(patient.getPatientIdno(), patient.getPatientTel(), qvo.getUserShort());
                            if(!"1".equals(code)){
                                this.getAjson().setMsgCode("1100");
                                this.getAjson().setMsg("验证码过期,请重新获取!");
                                return "ajson";
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("手机号不存在，请输入正确手机号！");
                            return "ajson";
                        }
                    if(patient != null){
                        patient.setPatientPwd(qvo.getNewPwd());
                        this.getSysDao().getServiceDo().modify(patient);
                        this.getAjson().setMsg("修改密码成功");
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("账号不存在，请输入正确账号！");
                    }
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
     * tv接口
     * @return
     */
    public String tvLogin(){
        try{
            AppPatientUserQvo qvo = (AppPatientUserQvo)getAppJson(AppPatientUserQvo.class);
            if(qvo != null){
                if(StringUtils.isNotBlank(qvo.getSelectType())){
                    if(CommonLoginType.ZNKT.getValue().equals(qvo.getSelectType()) &&  StringUtils.isBlank(qvo.getEquipmentNumber())){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("参数格式错误,设备号不能为空！");
                        return "ajson";
                    }
                    AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findByUser(qvo.getUserAccount(),qvo.getUserPass(),qvo.getSelectType());
                    if(patient != null){
                        AppPatientUser user = (AppPatientUser)this.getSysDao().getServiceDo().find(AppPatientUser.class,patient.getId());
                        if(user.getPatientPwd().equals(qvo.getUserPass()) || qvo.getSelectType().equals(CommonLoginType.ZNKT.getValue())){
                            if(StringUtils.isNotBlank(patient.getPatientNeighborhoodCommittee())){
                                AppHospDept dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientNeighborhoodCommittee());
                                if(dept != null){
                                    patient.setPatientCommunity(dept.getId());
                                }else{
                                    dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                                    if(dept != null) {
                                        patient.setPatientCommunity(dept.getId());
                                    }
                                }
                            }else if(StringUtils.isNotBlank(patient.getPatientStreet())){
                                AppHospDept dept = (AppHospDept)this.getSysDao().getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                                if(dept != null) {
                                    patient.setPatientCommunity(dept.getId());
                                }
                            }
                            Md5Util util = new Md5Util();
                            String key = qvo.getUserAccount()+ ExtendDate.getYMD_h_m_s(Calendar.getInstance());
                            key = util.MD516(key);
                            AppDrPatientKey uKey = this.sysDao.getAppDrPatientKeyDao().findByDoctorOrPatientId(patient.getId());
                            String day = ExtendDateUtil.addDate(ExtendDate.getYMD(Calendar.getInstance()), ExtendDateType.DAYS.getValue(),yxDay);
                            if(uKey != null){
                                uKey.setDrTvToken(key);
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
                                patient.setPatientFirstState(CommonEnable.QIYONG.getValue());
                                uKey.setDrPatientLastDate(uKey.getHsUpdateTime());
                                this.sysDao.getServiceDo().modify(uKey);
                            }else{
                                uKey = new AppDrPatientKey();
                                uKey.setDrTvToken(key);
                                uKey.setDrPatientId(patient.getId());
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
                                uKey.setDrPatientLastDate(Calendar.getInstance());
                                patient.setPatientFirstState(CommonEnable.JINYONG.getValue());
                                this.getSysDao().getServiceDo().add(uKey);
                            }
                            user.setPatientJgState(UserJgType.YISHEZHI.getValue());
                            this.sysDao.getServiceDo().modify(user);
                            PatientInfo info = sysDao.getAppPatientUserDao().findUserInfo(patient.getId(),"0",null);
                            if(info!=null){
                                patient.setSignType(info.getJQ());
                                patient.setRetireHome(info.getJjyl());
                                patient.setRetireHomeColor(info.getJjylcolor());
                                patient.setLabTitle(info.getLabtitle());
                                patient.setLabColor(info.getLabcolor());
                            }
                            this.getAjson().setUkey(key);
                            this.getAjson().setVo(patient);
                            if(CommonLoginType.ZNKT.getValue().equals(qvo.getSelectType())){
                                String result = JsonUtil.toJson(this.getAjson());
                                sysDao.getSecurityCardAsyncBean().push_tv(qvo.getEquipmentNumber(),"登录成功!",result);
                                this.getAjson().setMsg("推送成功!");
                                this.getAjson().setUkey(null);
                                this.getAjson().setVo(null);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("信息不匹配!");
                        }
                    }else{
                        if(CommonLoginType.ZNKT.getValue().equals(qvo.getSelectType())){
                            this.getAjson().setMsgCode("9000");
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("账号不存在，请输入正确账号！");
                        }

                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
                }
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
     * 注册极光
     * @return
     */
    public String tvRegister(){
        try{
            AppPatientResgisterQvo qvo = (AppPatientResgisterQvo)getAppJson(AppPatientResgisterQvo.class);
            if(qvo!=null){
                if(StringUtils.isBlank(qvo.getEquipmentNumber())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("设备号不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getEquipmentName())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("设备名称不能为空!");
                    return "ajson";
                }
               String result = sysDao.getSecurityCardAsyncBean().registeredEasemobTv(qvo.getEquipmentNumber(),qvo.getEquipmentName());
               if(result.equals("1")){
                   this.getAjson().setMsg("注册成功!");
               }else{
                   this.getAjson().setMsgCode("900");
                   this.getAjson().setMsg("极光注册异常");
               }
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
     * sdk对外登录
     * @return
     */
    public String appExternalLogin(){
        try{
            AppPatientPerfectDataEntity vo = (AppPatientPerfectDataEntity)getAppJson(AppPatientPerfectDataEntity.class);
            if(vo!=null){
                    if(StringUtils.isNotBlank(vo.getId())){
                        AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findUserId(vo.getId());
                        if(patient != null){
                                Map<String,Object> map = sysDao.getAppPatientUserDao().getPatientInfoLogin(patient,vo,"1");
                                if(map.get("key") != null && map.get("patient") != null){
                                    this.getAjson().setUkey(map.get("key").toString());
                                    this.getAjson().setVo(JsonUtil.fromJson(map.get("patient").toString(),AppPatientUserEntity.class));
                                    return "ajson";
                                }
                        }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("传入patientId有误!");
                                return "ajson";
                        }
                    }else{
                        if(StringUtils.isNotBlank(vo.getPatientIdNo())){
                            boolean idNo = this.getSysDao().getAppPatientUserDao().findPatientByIdNo(vo.getPatientIdNo(),UserUpHpisType.JIHUO.getValue());
                            if(idNo){
                                AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findByUserIdNo(vo.getPatientIdNo());
                                if(patient != null){
                                    Map<String,Object> map = sysDao.getAppPatientUserDao().getPatientInfoLogin(patient,vo,"1");
                                    if(map.get("key") != null && map.get("patient") != null){
                                        this.getAjson().setUkey(map.get("key").toString());
                                        this.getAjson().setVo(JsonUtil.fromJson(map.get("patient").toString(),AppPatientUserEntity.class));
                                        return "ajson";
                                    }
                                }
                            }else{
                                String userIdNo = null;
                                String resultIdNo = null;
                                if(StringUtils.isNotBlank(vo.getPatientIdNo())){
                                    userIdNo = vo.getPatientIdNo();
                                    vo.setPatientIdNo(vo.getPatientIdNo().toLowerCase());
                                    resultIdNo = CardUtil.IDCardValidate(vo.getPatientIdNo());
                                }
                                if(StringUtils.isNotBlank(resultIdNo)){
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg(resultIdNo);
                                    return "ajson";
                                }else{
                                    //完善资料
                                    String userId = this.getSysDao().getAppPatientUserDao().addExternalPerfectData(vo);
                                    AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findUserId(userId);
                                    if(patient != null){
                                        Map<String,Object> map = sysDao.getAppPatientUserDao().getPatientInfoLogin(patient,vo,"1");
                                        if(map.get("key") != null && map.get("patient") != null){
                                            this.getAjson().setUkey(map.get("key").toString());
                                            this.getAjson().setVo(JsonUtil.fromJson(map.get("patient").toString(),AppPatientUserEntity.class));
                                            return "ajson";
                                        }
                                    }else{
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("系统异常,请联系管理员!");
                                        return "ajson";
                                    }
                                }
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("身份证参数不能为空!");
                            return "ajson";
                        }
                    }
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
     * html页面授权
     * @return
     */
    public String appExternalHtmlLogin(){
        try{
            String data = this.getRequest().getParameter("data");
            if(StringUtils.isNotBlank(data)){
                data = URLDecoder.decode(data, "UTF-8");
                RequestParams requestParams = JsonUtil.fromJson(data,RequestParams.class);
                if(requestParams != null){
                    if(StringUtils.isBlank(requestParams.getSign())){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("sign不能为空!");
                        return "ajson";
                    }
                    if(StringUtils.isNotBlank(requestParams.getAppId())){
                        Map<String,Object> mapObject = new HashMap<String,Object>();
                        mapObject.put("type",requestParams.getType());
                        AppMsgQvo qvo = new AppMsgQvo();
                        qvo.setAppId(requestParams.getAppId());
                        AppMsg msg = sysDao.getAppMsgDao().findMsgByQvo(qvo);
                        if(msg != null){
                            mapObject.put("cityCode",msg.getAppAreaCode());
//                            System.out.println(JsonUtil.toJson(msg));
//                            System.out.println(JsonUtil.toJson(requestParams));
                            System.out.println("kkCode:"+requestParams.getCode());
                            String checkRsult = DtklUtil.check(msg.getAppId(), msg.getAppMasterSecret(), requestParams.getSign(),requestParams.getCode());
//                            if(DtklUtil.checkok.equals(checkRsult)){
                            if(true){
                                String oldSign = requestParams.getSign();
                                requestParams.setSign(null);
                                requestParams.setCode(null);
                                Map<String,Object> map = Signature.createSign(requestParams, msg.getAppMasterSecret());
                                if(map != null && map.size() >0){
                                    String sign = map.get("sign").toString();
//                                    System.out.println(sign);
//                                    System.out.println(oldSign);
                                    if(sign.equals(oldSign)){
                                        String json = JsonUtil.toJson(requestParams.getParam());
                                        AppPatientPerfectDataEntity vo = JsonUtil.fromJson(json,AppPatientPerfectDataEntity.class);
                                        System.out.println("电子健康卡："+vo.getEhcId());
                                        if(StringUtils.isNotBlank(vo.getPatientIdNo())){
                                            if(requestParams.getType().equals(HtmlType.JKJC.getValue())){
                                                mapObject.put("deviceType",vo.getDeviceType());
                                            }
//                                            if(requestParams.getType().equals(HtmlType.JKJC.getValue())){
//                                                if(StringUtils.isNotBlank(vo.getDeviceType())){
//                                                    mapObject.put("deviceType",vo.getDeviceType());
//                                                }else{
//                                                    this.getAjson().setMsgCode("900");
//                                                    this.getAjson().setMsg("deviceType参数不能为空!");
//                                                    return "ajson";
//                                                }
//                                            }
                                            boolean idNo = this.getSysDao().getAppPatientUserDao().findPatientByIdNo(vo.getPatientIdNo(),UserUpHpisType.JIHUO.getValue());
                                            if(idNo){
                                                AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findByUserIdNo(vo.getPatientIdNo());
                                                if(patient != null){
                                                    Map<String,Object> mapResult = sysDao.getAppPatientUserDao().getPatientInfoLogin(patient,vo,"2");
                                                    if(mapResult.get("key") != null && mapResult.get("patient") != null){
                                                        this.getAjson().setMap(mapObject);
                                                        this.getAjson().setUkey(mapResult.get("key").toString());
//                                                        this.getAjson().setVo(vo);
                                                        this.getAjson().setVo(JsonUtil.fromJson(mapResult.get("patient").toString(),AppPatientUserEntity.class));
                                                        return "ajson";
                                                    }

                                                }
                                            }else{
                                                String userIdNo = null;
                                                String resultIdNo = null;
                                                if(StringUtils.isNotBlank(vo.getPatientIdNo())){
                                                    userIdNo = vo.getPatientIdNo();
                                                    vo.setPatientIdNo(vo.getPatientIdNo().toLowerCase());
                                                    resultIdNo = CardUtil.IDCardValidate(vo.getPatientIdNo());
                                                }
                                                if(StringUtils.isNotBlank(resultIdNo)){
                                                    this.getAjson().setMsgCode("900");
                                                    this.getAjson().setMsg(resultIdNo);
                                                    return "ajson";
                                                }else{
                                                    //完善资料
                                                    String userId = this.getSysDao().getAppPatientUserDao().addExternalPerfectData(vo);
                                                    AppPatientUserEntity patient = sysDao.getAppPatientUserDao().findUserId(userId);
                                                    if(patient != null){
                                                        Map<String,Object> mapResult = sysDao.getAppPatientUserDao().getPatientInfoLogin(patient,vo,"2");
                                                        if(mapResult.get("key") != null && mapResult.get("patient") != null){
                                                            this.getAjson().setMap(mapObject);
                                                            this.getAjson().setUkey(mapResult.get("key").toString());
//                                                            this.getAjson().setVo(vo);
                                                            this.getAjson().setVo(JsonUtil.fromJson(mapResult.get("patient").toString(),AppPatientUserEntity.class));
                                                            return "ajson";
                                                        }
                                                    }else{
                                                        this.getAjson().setMsgCode("900");
                                                        this.getAjson().setMsg("系统异常,请联系管理员!");
                                                        return "ajson";
                                                    }
                                                }
                                            }
                                        }else{
                                            this.getAjson().setMsgCode("900");
                                            this.getAjson().setMsg("身份证参数不能为空!");
                                            return "ajson";
                                        }
                                    }else{
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("sign签名异常!");
                                    }
                                }
                            }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("code已过期,请重新获取!");
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("appId有误!");
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("appId不能为空!");
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
                }
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




}
