package com.ylz.view.wechat.action;

import com.ylz.bizDo.app.po.AppDrPatientKey;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.drEntity.AppDrUserEntity;
import com.ylz.bizDo.jtapp.drEntity.PatientInfo;
import com.ylz.bizDo.jtapp.drVo.AppDrUserQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserWechatEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPatientUserQvo;
import com.ylz.bizDo.jtapp.wechatEntity.WechatVoucheEntity;
import com.ylz.bizDo.jtapp.wechatVo.WechatVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import com.ylz.packcommon.common.wechat.pojo.SNSUserInfo;
import com.ylz.packcommon.common.wechat.pojo.WeixinOauth2Token;
import com.ylz.packcommon.common.wechatService.LoadService;
import com.ylz.packcommon.common.wechatUtil.AdvancedUtil;
import com.ylz.packcommon.common.wechatUtil.SignUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Action(
        value="wechat",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class WechatAction extends CommonAction {

    private Logger logger = LoggerFactory.getLogger(WechatAction.class);

    private static final  int yxDay = 15;

    /**
     * 接口验证
     * @return
     */
    public void checktInterface(){
        try {
            // 微信加密签名
            String signature = this.getRequest().getParameter("signature");
            // 时间戳
            String timestamp = this.getRequest().getParameter("timestamp");
            // 随机数
            String nonce = this.getRequest().getParameter("nonce");
            // 随机字符串
            String echostr = this.getRequest().getParameter("echostr");

            // 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                logger.info("校验成功反回随机码："+echostr);
                PrintWriter out = this.getResponse().getWriter();
                out.print(echostr);
                out.close();
                out = null;
            }else{
                logger.warn("get校验失败");
            }
        } catch (Exception e) {
            logger.info("不是微信服务器发来的请求,请小心!");
        }
    }


    /**
     * 微信OPENID获取
     * @return
     */
    public String wechatOauthToken(){
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            WechatVo vo = (WechatVo) this.getAppJson(WechatVo.class);
            if (StringUtils.isNotBlank(vo.getCode()) && !"authdeny".equals(vo.getCode())) {  //存在code，进行授权认证获取
                WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(LoadService.APPID,LoadService.APPSECRT,vo.getCode());
                if(weixinOauth2Token!=null){
                    // 网页授权接口访问凭证
                    String accessToken = weixinOauth2Token.getAccessToken();
                    // 用户标识
                    String openId = weixinOauth2Token.getOpenId();
                    // 获取用户信息
                    if(accessToken!=null&&openId!=null){
                        SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
                        map.put("openId",openId);
                        map.put("headImgUrl",snsUserInfo.getHeadImgUrl());
                    }
                }
                this.getAjson().setMap(map);
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
     * 微信登录接口
     * @return
     */
    public String wechatLogin(){
        try{
            AppPatientUserQvo qvo = (AppPatientUserQvo)getAppJson(AppPatientUserQvo.class);
            if(qvo != null){
                if(StringUtils.isNotBlank(qvo.getSelectType())){
                    if(!CommonLoginType.WEIXINOPEN.getValue().equals(qvo.getSelectType()) &&  StringUtils.isBlank(qvo.getWechatOpenId())){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("参数格式错误,OPENID不能为空！");
                        return "ajson";
                    }
                    AppPatientUserWechatEntity patient = sysDao.getAppPatientUserDao().findByWechatUser(qvo.getUserAccount(),qvo.getSelectType());
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
                        if(user.getPatientPwd().equals(qvo.getUserPass()) || qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())
                                || qvo.getSelectType().equals(CommonLoginType.WEIXINOPEN.getValue())){
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
//                                uKey.setDrToken(key);
                                uKey.setDrWechatToken(key);
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
                                patient.setPatientFirstState(CommonEnable.QIYONG.getValue());
                                this.sysDao.getServiceDo().modify(uKey);
                            }else{
                                uKey = new AppDrPatientKey();
                                uKey.setDrWechatToken(key);
                                uKey.setDrPatientId(patient.getId());
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
                                patient.setPatientFirstState(CommonEnable.JINYONG.getValue());
                                this.getSysDao().getServiceDo().add(uKey);
                            }
                            user.setPatientJgState(UserJgType.YISHEZHI.getValue());
                            if(!CommonLoginType.WEIXINOPEN.getValue().equals(qvo.getSelectType())){
                                if(StringUtils.isBlank(user.getPatientOpenId())){
                                    user.setPatientOpenId(qvo.getWechatOpenId());
                                }
                            }
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
                        if(CommonLoginType.WEIXINOPEN.getValue().equals(qvo.getSelectType())){
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

    public String wechatVouche(){
        try{
            WechatVo vo = (WechatVo)this.getAppJson(WechatVo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(vo.getCode())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("凭证code不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(vo.getAppid())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("唯一标识不能为空!");
                    return "ajson";
                }
                if(StringUtils.isBlank(vo.getSecret())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("secret不能为空!");
                    return "ajson";
                }
                String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+vo.getAppid()+"&secret="+vo.getSecret()+"&js_code="+vo.getCode()+"&grant_type=authorization_code";
                String vv = HtmlUtils.getInstance().loadURL(url);
                if(StringUtils.isNotBlank(vv)){
                    WechatVoucheEntity v = JsonUtil.fromJson(vv, WechatVoucheEntity.class);
                    this.getAjson().setVo(v);
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

}

