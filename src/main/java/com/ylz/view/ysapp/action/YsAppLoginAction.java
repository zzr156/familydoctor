package com.ylz.view.ysapp.action;

import com.ylz.bizDo.app.po.AppDrPatientKey;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.bizDo.jtapp.commonVo.AppPwdQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTelQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrChangeInfoEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrMAccountEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrUserEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrUserPossEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrUserQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.*;

/**
 * 医生个人信息接口action.
 */
@SuppressWarnings("all")
@Action(
        value="yslogin",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsAppLoginAction extends CommonAction {

    private static final  int yxDay = 15;

    /**
     * app登录接口
     * @return
     */
    public String appLogin(){
        try{
            AppDrUserQvo qvo = (AppDrUserQvo)getAppJson(AppDrUserQvo.class);
            if(qvo!=null){
                if(StringUtils.isNotBlank(qvo.getSelectType())){
                    AppDrUserEntity doc = null;
                    List<AppDrUserEntity> ls = sysDao.getAppDrUserDao().findByUser(qvo.getUserAccount(),qvo.getUserPass(),qvo.getSelectType());
                    if(ls != null && ls.size() == 1){
                        doc = ls.get(0);
                    }else  if(ls != null  && ls.size() > 1){
                        doc = new AppDrUserEntity();
                        boolean flag = false;
                        boolean codeFlag = true;
                        if(qvo.getUserAccount().length() == 11){
                            List<AppDrMAccountEntity> listM = new ArrayList<>();
                            for(AppDrUserEntity mm:ls){
                                AppDrUser user = (AppDrUser)this.getSysDao().getServiceDo().find(AppDrUser.class,mm.getId());
                                if(user != null){
                                    if(qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())){
                                        String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneCheck(user.getDrTel(), user.getDrTel(), qvo.getUserShort());
                                        if("1".equals(code)){
                                            codeFlag = false;
                                        }
                                    }else  if(qvo.getUserPass().equals(user.getDrTelPwd())){
                                        flag = true;
                                    }
                                    AppDrMAccountEntity lm = new AppDrMAccountEntity();
                                    lm.setDrId(mm.getId());
                                    lm.setDrName(mm.getDrName());
                                    lm.setHospName(mm.getDrHospName());
                                    lm.setDrAccount(mm.getDrAccount());
                                    lm.setState("0");
                                    listM.add(lm);
                                }
                            }
                            if(qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())){
                                if(codeFlag){
                                    this.getAjson().setMsgCode("1100");
                                    this.getAjson().setMsg("验证码过期,请重新获取!");
                                    return "ajson";
                                }else{
                                    this.getAjson().setRows(listM);
                                }
                            }else {
                                if(flag){
                                    this.getAjson().setRows(listM);
                                }else{
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg("账号密码不匹配!");
                                }
                            }
                            return "ajson";
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg(qvo.getUserAccount()+"账号重复，请重新输入!");
                            return "ajson";
                        }
                    }

                    if(qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())){
                        if(doc != null){
                            String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneCheck(doc.getDrTel(), doc.getDrTel(), qvo.getUserShort());
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
                    if(doc != null){
                        AppDrUser user = (AppDrUser)this.getSysDao().getServiceDo().find(AppDrUser.class,doc.getId());
                        if(user.getDrTelPwd().equals(qvo.getUserPass()) || qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())||CommonLoginType.YSZJ.getValue().equals(qvo.getSelectType()) ){
//                        if(user.getDrPwd().equals(qvo.getUserPass()) || qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())||CommonLoginType.YSZJ.getValue().equals(qvo.getSelectType()) ){
                            Md5Util util = new Md5Util();
                            String key = qvo.getUserAccount()+ ExtendDate.getYMD_h_m_s(Calendar.getInstance());
                            key = util.MD516(key);
                            AppDrPatientKey uKey = this.sysDao.getAppDrPatientKeyDao().findByDoctorOrPatientId(doc.getId());
                            String day = ExtendDateUtil.addDate(ExtendDate.getYMD(Calendar.getInstance()), ExtendDateType.DAYS.getValue(),yxDay);
                            if(uKey != null){
                                uKey.setDrToken(key);
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.yisheng.getValue());
                                uKey.setDrTempId(null);
                                uKey.setDrTempToken(null);
                                uKey.setDrPatientLastDate(uKey.getHsUpdateTime());
                                this.sysDao.getServiceDo().modify(uKey);
                            }else{
                                uKey = new AppDrPatientKey();
                                uKey.setDrToken(key);
                                uKey.setDrPatientId(doc.getId());
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.yisheng.getValue());
                                uKey.setDrPatientLastDate(Calendar.getInstance());
                                this.getSysDao().getServiceDo().add(uKey);
                            }
                            user.setDrJgState(UserJgType.YISHEZHI.getValue());
                            if(CommonLoginType.YSZJ.getValue().equals(qvo.getSelectType())){
                                //通过医生手机查询医生多账号信息
//                                List<AppDrUser> listDr = sysDao.getServiceDo().loadByPk(AppDrUser.class,"drTel",doc.getDrTel());
//                                if(listDr!=null && listDr.size()>0){
//                                    for(AppDrUser ll:listDr){
//                                        if(qvo.getUserAccount().equals(ll.getId())){
//                                            ll.setDrZxState("1");
//                                        }else{
//                                            ll.setDrZxState("0");
//                                        }
//                                        sysDao.getServiceDo().modify(ll);
//                                    }
//                                }
                                List<AppDrMAccountEntity> listM = sysDao.getAppDrUserDao().findByDrTel(doc.getDrTel());
                                if(listM!=null && listM.size()>0){
                                    for(AppDrMAccountEntity mm:listM){
                                        if(mm.getDrId().equals(qvo.getUserAccount())){
                                            mm.setState("1");
                                        }else{
                                            mm.setState("0");
                                        }
                                    }
                                    doc.setmAccountList(listM);
                                }
                            }
                            this.sysDao.getServiceDo().modify(user);
                            this.getAjson().setUkey(key);
                            this.getAjson().setVo(doc);
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
     * 密码修改
     * @return
     */
    public String appChangePwd(){
        try{
            AppPwdQvo qvo = (AppPwdQvo)getAppJson(AppPwdQvo.class);
            if(qvo!=null){
                    AppDrUser user = this.getAppDrUser();
                    if(user != null){
                        List<AppDrUser> listDr = sysDao.getServiceDo().loadByPk(AppDrUser.class,"drTel",user.getDrTel());
                        if(listDr != null && listDr.size()>0){
                            boolean flag = false;
                            for(AppDrUser drUser:listDr){
                                if(qvo.getOldPwd().equals(drUser.getDrTelPwd())){
//                                 if(qvo.getOldPwd().equals(drUser.getDrPwd())){
                                        flag = true;
                                }
                            }
                            if(flag){
                                for(AppDrUser drUser:listDr){
                                    drUser.setDrTelPwd(qvo.getNewPwd());
//                                    drUser.setDrPwd(qvo.getNewPwd());
                                    drUser.setDrs("1");
                                    this.getSysDao().getServiceDo().modify(drUser);
                                }
                                this.getAjson().setMsg("修改密码成功");
                            }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("旧密码有误，请重新输入");
                            }
                        }
                    }
//                    if(qvo.getOldPwd().equals(user.getDrPwd())){
//                        AppDrUser v = (AppDrUser)this.getSysDao().getServiceDo().find(AppDrUser.class,user.getId());
//                        v.setDrPwd(qvo.getNewPwd());
//                        v.setDrs("1");
//                        this.getSysDao().getServiceDo().modify(v);
//                        this.getAjson().setMsg("修改密码成功");
//                    }else{
//                        this.getAjson().setMsgCode("900");
//                        this.getAjson().setMsg("旧密码有误，请重新输入");
//                    }
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
                AppDrUser user = this.getAppDrUser();
                String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneCheck(qvo.getNewTel(), qvo.getNewTel(), qvo.getUserShort());
                if(!"1".equals(code)){
                    this.getAjson().setMsgCode("1100");
                    this.getAjson().setMsg("验证码过期,请重新获取!");
                    return "ajson";
                }else{
                    boolean tel = this.getSysDao().getAppDrUserDao().findDrUserByTel(qvo.getNewTel());
                    if(tel){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("手机号已存在,不能填写已存在手机!");
                        return "ajson";
                    }
                    AppDrUser v = (AppDrUser)this.getSysDao().getServiceDo().find(AppDrUser.class,user.getId());
                    v.setDrTel(qvo.getNewTel());
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
     * 修改个人信息
     */
    public String appChangeInfo(){
        try{
            AppDrChangeInfoEntity vo = (AppDrChangeInfoEntity)this.getAppJson(AppDrChangeInfoEntity.class);
            if(vo != null){
                AppDrUser user = (AppDrUser)this.getSysDao().getServiceDo().find(AppDrUser.class,vo.getId());
                if(user != null){
                    if(StringUtils.isNotBlank(vo.getDrGender())){
                        user.setDrGender(vo.getDrGender());
                    }
                    if(StringUtils.isNotBlank(vo.getDrName())){
                        user.setDrName(vo.getDrName());
                        //修改医生信息，团队医生姓名跟着改动
                        List<AppTeam> lsTeam = sysDao.getServiceDo().loadByPk(AppTeam.class,"teamDrId",vo.getId());
                        if(lsTeam!=null&&lsTeam.size()>0){
                            for(AppTeam ls:lsTeam){
                                ls.setTeamDrName(vo.getDrName());
                                sysDao.getServiceDo().modify(ls);
                            }
                        }
                        //修改医生信息，团队成员医生姓名跟着改动
                        List<AppTeamMember> lsTeamM = sysDao.getServiceDo().loadByPk(AppTeamMember.class,"memDrId",vo.getId());
                        if(lsTeamM!=null&&lsTeamM.size()>0){
                            for(AppTeamMember ls:lsTeamM){
                                ls.setMemDrName(vo.getDrName());
                                sysDao.getServiceDo().modify(ls);
                            }
                        }
                    }
                    if(StringUtils.isNotBlank(vo.getDrIntro())){
                        user.setDrIntro(vo.getDrIntro());
                    }
                    if(StringUtils.isNotBlank(vo.getDrGood())){
                        user.setDrGood(vo.getDrGood());
                    }
                    if(StringUtils.isNotBlank(vo.getDrImageUrl())){
                        Map<String,Object> map = this.getSysDao().getIoUtils().getCtyunOosSample(vo.getDrImageUrl(),CommonShortType.YISHENG.getValue());
                        user.setDrImageurl(map.get("objectUrl").toString());
                        user.setDrImageName(map.get("objectName").toString());
                    }
                    sysDao.getServiceDo().modify(user);
                    AppDrUserEntity drUser = sysDao.getAppDrUserDao().findUserId(user.getId());
                    this.getAjson().setVo(drUser);
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
     * app忘记密码
     * @return
     */
    public String apForgetPwd(){
        try{
            AppPwdQvo qvo = (AppPwdQvo)getAppJson(AppPwdQvo.class);
            if(qvo != null){
//                AppDrUser dr = this.getSysDao().getAppDrUserDao().findByTelPhone(qvo.getUserTel());
                List<AppDrUser> listDr = sysDao.getServiceDo().loadByPk(AppDrUser.class,"drTel",qvo.getUserTel());
                if(listDr != null && listDr.size() >0){
                    boolean codeFlag = true;
                    for(AppDrUser dr : listDr){
                        String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneCheck(dr.getDrTel(), dr.getDrTel(), qvo.getUserShort());
                        if("1".equals(code)){
                            codeFlag = false;
                        }
                    }
                    if(codeFlag){
                        this.getAjson().setMsgCode("1100");
                        this.getAjson().setMsg("验证码过期,请重新获取!");
                        return "ajson";
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("手机号不存在，请输入正确手机号！");
                    return "ajson";
                }
                if(listDr != null && listDr.size() >0){
                    for(AppDrUser dr : listDr){
                        dr.setDrTelPwd(qvo.getNewPwd());
//                        dr.setDrPwd(qvo.getNewPwd());
                        dr.setDrs("1");
                        this.getSysDao().getServiceDo().modify(dr);
                        this.getAjson().setMsg("修改密码成功");
                    }
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
     * 临时医生切换功能 需带drId 和 token
     * @return
     */
    public String tempDrLogin(){
        try {
            AppDrUserQvo qvo = (AppDrUserQvo) getAppJson(AppDrUserQvo.class);
            if(qvo!=null && StringUtils.isNotBlank(qvo.getDrId())){
                //生成临时key
                AppDrUser user = (AppDrUser)this.getSysDao().getServiceDo().find(AppDrUser.class,qvo.getDrId());//临时医生
                Md5Util util = new Md5Util();
                String key = user.getDrAccount()+"temp"+ ExtendDate.getYMD_h_m_s(Calendar.getInstance());
                key = util.MD516(key);
                AppDrPatientKey uKey = this.sysDao.getAppDrPatientKeyDao().findDrPatientKeyByToken(getToken());//当前医生
                uKey.setDrTempToken(key);
                uKey.setDrTempId(user.getId());
                this.sysDao.getServiceDo().modify(uKey);//保存临时医生
                //登入临时医生
                AppDrUserEntity doc = sysDao.getAppDrUserDao().findUserById(user.getId());
                this.getAjson().setUkey(key);//返回tokey
                this.getAjson().setVo(doc);//返回用户信息
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 返回管理员 需带token
     * @return
     */
    public String backAdmin(){
        try {
                AppDrPatientKey uKey = this.sysDao.getAppDrPatientKeyDao().findDrTempKeyByToken(getToken());//查询管理员
                uKey.setDrTempToken(null);
                uKey.setDrTempId(null);
                this.sysDao.getServiceDo().modify(uKey);//清除临时医生
                AppDrUser user = (AppDrUser)this.getSysDao().getServiceDo().find(AppDrUser.class,uKey.getDrPatientId());//管理员信息
                AppDrUserEntity doc = sysDao.getAppDrUserDao().findUserById(user.getId());//登入管理员
                this.getAjson().setUkey(uKey.getDrToken());//tokey
                this.getAjson().setVo(doc);//返回用户信息
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    public String appLoginPoss(){
        try{
            AppDrUserQvo qvo = (AppDrUserQvo)getAppJson(AppDrUserQvo.class);
            if(qvo!=null){
                if(StringUtils.isNotBlank(qvo.getSelectType())){
                    AppDrUserPossEntity doc = null;
                    List<AppDrUserPossEntity> ls = sysDao.getAppDrUserDao().findByUserPoss(qvo.getUserAccount(),qvo.getUserPass(),qvo.getSelectType());
                    if(ls != null && ls.size() == 1){
                        doc = ls.get(0);
                    }else  if(ls != null  && ls.size() > 1){
                        doc = new AppDrUserPossEntity();
                        boolean flag = false;
                        boolean codeFlag = true;
                        if(qvo.getUserAccount().length() == 11){
                            List<AppDrMAccountEntity> listM = new ArrayList<>();
                            for(AppDrUserPossEntity mm:ls){
                                AppDrUser user = (AppDrUser)this.getSysDao().getServiceDo().find(AppDrUser.class,mm.getId());
                                if(user != null){
                                    if(qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())){
                                        String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneCheck(user.getDrTel(), user.getDrTel(), qvo.getUserShort());
                                        if("1".equals(code)){
                                            codeFlag = false;
                                        }
                                    }else  if(qvo.getUserPass().equals(user.getDrTelPwd())){
                                        flag = true;
                                    }
                                    AppDrMAccountEntity lm = new AppDrMAccountEntity();
                                    lm.setDrId(mm.getId());
                                    lm.setDrName(mm.getDrName());
                                    lm.setHospName(mm.getDrHospName());
                                    lm.setDrAccount(mm.getDrAccount());
                                    lm.setState("0");
                                    listM.add(lm);
                                }
                            }
                            if(qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())){
                                if(codeFlag){
                                    this.getAjson().setMsgCode("1100");
                                    this.getAjson().setMsg("验证码过期,请重新获取!");
                                    return "ajson";
                                }else{
                                    this.getAjson().setRows(listM);
                                }
                            }else {
                                if(flag){
                                    this.getAjson().setRows(listM);
                                }else{
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg("账号密码不匹配!");
                                }
                            }
                            return "ajson";
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg(qvo.getUserAccount()+"账号重复，请重新输入!");
                            return "ajson";
                        }
                    }

                    if(qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())){
                        if(doc != null){
                            String code = this.sysDao.getMsgPhoneAsyncBean().msgPhoneCheck(doc.getDrTel(), doc.getDrTel(), qvo.getUserShort());
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
                    if(doc != null){
                        AppDrUser user = (AppDrUser)this.getSysDao().getServiceDo().find(AppDrUser.class,doc.getId());
                        if(user.getDrTelPwd().equals(qvo.getUserPass()) || qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())||CommonLoginType.YSZJ.getValue().equals(qvo.getSelectType()) ){
//                        if(user.getDrPwd().equals(qvo.getUserPass()) || qvo.getSelectType().equals(CommonLoginType.SHOUJI.getValue())||CommonLoginType.YSZJ.getValue().equals(qvo.getSelectType()) ){
                            Md5Util util = new Md5Util();
                            String key = qvo.getUserAccount()+ ExtendDate.getYMD_h_m_s(Calendar.getInstance());
                            key = util.MD516(key);
                            AppDrPatientKey uKey = this.sysDao.getAppDrPatientKeyDao().findByDoctorOrPatientId(doc.getId());
                            String day = ExtendDateUtil.addDate(ExtendDate.getYMD(Calendar.getInstance()), ExtendDateType.DAYS.getValue(),yxDay);
                            if(uKey != null){
                                uKey.setDrToken(key);
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.yisheng.getValue());
                                uKey.setDrTempId(null);
                                uKey.setDrTempToken(null);
                                uKey.setDrPatientLastDate(uKey.getHsUpdateTime());
                                this.sysDao.getServiceDo().modify(uKey);
                            }else{
                                uKey = new AppDrPatientKey();
                                uKey.setDrToken(key);
                                uKey.setDrPatientId(doc.getId());
                                uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                                uKey.setDrPatientType(CommonDrPartientType.yisheng.getValue());
                                uKey.setDrPatientLastDate(Calendar.getInstance());
                                this.getSysDao().getServiceDo().add(uKey);
                            }
                            user.setDrJgState(UserJgType.YISHEZHI.getValue());
                            if(CommonLoginType.YSZJ.getValue().equals(qvo.getSelectType())){
                                //通过医生手机查询医生多账号信息
//                                List<AppDrUser> listDr = sysDao.getServiceDo().loadByPk(AppDrUser.class,"drTel",doc.getDrTel());
//                                if(listDr!=null && listDr.size()>0){
//                                    for(AppDrUser ll:listDr){
//                                        if(qvo.getUserAccount().equals(ll.getId())){
//                                            ll.setDrZxState("1");
//                                        }else{
//                                            ll.setDrZxState("0");
//                                        }
//                                        sysDao.getServiceDo().modify(ll);
//                                    }
//                                }
                                List<AppDrMAccountEntity> listM = sysDao.getAppDrUserDao().findByDrTel(doc.getDrTel());
                                if(listM!=null && listM.size()>0){
                                    for(AppDrMAccountEntity mm:listM){
                                        if(mm.getDrId().equals(qvo.getUserAccount())){
                                            mm.setState("1");
                                        }else{
                                            mm.setState("0");
                                        }
                                    }
                                    doc.setmAccountList(listM);
                                }
                            }
                            this.sysDao.getServiceDo().modify(user);
                            this.getAjson().setUkey(key);
                            this.getAjson().setVo(doc);
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
}


