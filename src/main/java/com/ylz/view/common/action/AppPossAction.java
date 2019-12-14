package com.ylz.view.common.action;

import com.ylz.bizDo.app.entity.AppSignServiceRecordEntity;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppPossEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppPossPatientEntity;
import com.ylz.bizDo.jtapp.commonVo.AppPossPatientVo;
import com.ylz.bizDo.jtapp.commonVo.AppPossQvo;
import com.ylz.bizDo.jtapp.commonVo.CreditCardScoreVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.PropertiesUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * poss机action
 * Created by zzl on 2018/8/20.
 */
@SuppressWarnings("all")
@Action(
        value="appPoss",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class AppPossAction extends CommonAction {

    /**
     * 查询poss绑定设备信息
     * @return
     */
    public String findPossBind(){
        try{
            AppPossQvo qvo = (AppPossQvo)this.getAppJson(AppPossQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppPossBinding vo = sysDao.getAppPossBindingDao().findByPossSn(qvo);
                if(vo == null){
                    vo = new AppPossBinding();
                    vo.setPossSn(qvo.getPossSn());
                    sysDao.getServiceDo().add(vo);
                }
                this.getAjson().setVo(vo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }
    /**
     * 验证poss机绑定
     * @return
     */
    public String possVerification(){
        try{
            AppPossQvo qvo = (AppPossQvo)this.getAppJson(AppPossQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                AppPossBinding vo = sysDao.getAppPossBindingDao().findByPossSn(qvo);
                if(vo != null){
                    if(StringUtils.isNotBlank(qvo.getGlucometerSn())){//血糖仪sn码验证
                        if(!qvo.getGlucometerSn().equals(vo.getGlucometerSn())){
                            boolean flag = sysDao.getAppPossBindingDao().findByGluSn(qvo.getGlucometerSn());
                            if(!flag){//该血糖仪已经绑定
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("该血糖仪已经绑定");
                                return "ajson";
                            }else{
                                vo.setGlucometerSn(qvo.getGlucometerSn());
                            }
                        }else{
                            vo.setGlucometerSn(qvo.getGlucometerSn());
                        }
                    }
                    if(StringUtils.isNotBlank(qvo.getSphygmomanometerSn())){//血压计sn码验证
                        if(!qvo.getSphygmomanometerSn().equals(vo.getSphygmomanometerSn())){
                            boolean flag = sysDao.getAppPossBindingDao().findBySphSn(qvo.getSphygmomanometerSn());
                            if(!flag){//该血压计已经绑定
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("该血压计已经绑定");
                                return "ajson";
                            }else{
                                vo.setSphygmomanometerSn(qvo.getSphygmomanometerSn());
                            }
                        }else{
                            vo.setSphygmomanometerSn(qvo.getSphygmomanometerSn());
                        }
                    }
                }
                sysDao.getServiceDo().modify(vo);
                this.getAjson().setVo(vo);
                this.getAjson().setMsgCode("900");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 漳浦手持机服务记录之上传医生签字
     * @return
     */
    public String possUploadSignature(){
        try {
            AppPossQvo qvo = (AppPossQvo)this.getAppJson(AppPossQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser != null){
                    Map<String, Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(), CommonShortType.YISHENG.getValue());
                    String url = map.get("objectUrl").toString();
                    String urlTop = PropertiesUtil.getConfValue("urlTop") + url.substring(url.indexOf("/picture"), url.length());
                    drUser.setDrAbroabUrl(url);
                    drUser.setDrWithinUrl(urlTop);
                    sysDao.getServiceDo().modify(drUser);
                    this.getAjson().setVo(drUser);
                    this.getAjson().setMsgCode("800");
                }else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查无医生数据");
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
     * 漳浦手持机服务记录之查询居民信息
     * @return
     */
    public String findInformation(){
        try{
            //根据居民主键查询
            AppPossQvo qvo = (AppPossQvo)this.getAppJson(AppPossQvo.class);
            if(qvo != null){
                qvo.setSignState(SignFormType.YUQY.getValue()+";"+SignFormType.YQY.getValue());
                AppPossPatientEntity vo = sysDao.getAppPossBindingDao().findInformationByPatient(qvo);
                this.getAjson().setVo(vo);
                //获取服务类型和经济类型选项
                Map<String,Object> map = new HashMap<>();
                List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
                List<AppLabelManage> jjLs = this.sysDao.getAppLabelManageDao().findByType("4");
                List<CdCode> listFw = this.sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SERVICECONTENT, CommonEnable.QIYONG.getValue());
                List<CdCode> listAddr = this.sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SERVICEADDR, CommonEnable.QIYONG.getValue());
                map.put("fwVo",ls);
                map.put("jjVo",jjLs);
                map.put("fwnr",listFw);
                map.put("fwdd",listAddr);
                this.getAjson().setMap(map);
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
     * 漳浦手持机服务记录之保存服务记录
     * @return
     */
    public String saveInformation(){
        try{
            AppPossPatientVo qvo = (AppPossPatientVo)this.getAppJson(AppPossPatientVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser != null){
                    if(StringUtils.isBlank(qvo.getDrId())){
                        qvo.setDrId(drUser.getId());
                    }
                }
                if(StringUtils.isNotBlank(qvo.getPatientId())){//修改居民紧急联系人
                    AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getPatientId());
                    if(user != null){
                        user.setPatientEmergencyContactName(qvo.getEmergencyContactName());
                        user.setPatientEmergencyContactTel(qvo.getEmergencyContactTel());
                        sysDao.getServiceDo().modify(user);
                        qvo.setPatientName(user.getPatientName());
                        AppSignServiceRecord vo = sysDao.getAppPossBindingDao().saveInformation(qvo);
                        this.getAjson().setVo(vo);
                        this.getAjson().setMsgCode("800");
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
     * 漳浦手持机查询服务记录列表
     * @return
     */
    public String findSignServeRecordList(){
        try{
            AppPossQvo qvo = (AppPossQvo)this.getAppJson(AppPossQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppPossEntity> list = sysDao.getAppPossBindingDao().findSignServeRecordList(qvo);
                this.getAjson().setRows(list);
                this.getAjson().setQvo(qvo);
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
     * 漳浦手持机查询服务记录
     * @return
     */
    public String findSignServeRecord(){
        try{
            AppPossQvo qvo = (AppPossQvo)this.getAppJson(AppPossQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查询条件不能为空");
                }else{
                    AppSignServiceRecordEntity vo = sysDao.getAppPossBindingDao().findSignServeRecord(qvo.getId());
                    if(vo != null){
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setVo(vo);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据");
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
     * 莆田绩效考核手持机刷社保卡得分
     * @return
     */
    public String creditCardScoreOfPoss(){
        try{
            String requestId = null;
            String requestName = null;
            CreditCardScoreVo qvo = (CreditCardScoreVo)this.getAppJson(CreditCardScoreVo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getOrgId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("机构主键不能为空");
                }else if(StringUtils.isBlank(qvo.getDrId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("医生主键不能为空");
                }else if(StringUtils.isBlank(qvo.getIdNo())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("居民身份证号不能为空");
                }else if(StringUtils.isBlank(qvo.getYxsksj())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("有效时间不能为空");
                }else if(StringUtils.isBlank(qvo.getUrlType())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("地市编码不能为空");
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser != null){
                        requestId = drUser.getId();
                        requestName = drUser.getDrName();
                    }else{
                        AppPatientUser user = this.getAppPatientUser();
                        if(user != null){
                            requestId = drUser.getId();
                            requestName = drUser.getDrName();
                        }
                    }
                    String str =  this.getSysDao().getSecurityCardAsyncBean().creditCardScoreOfPoss(qvo,requestId,requestName);
                    if(StringUtils.isNotBlank(str)){
                        JSONObject jsonall = JSONObject.fromObject(str);
                        if(jsonall.get("msgCode").equals("800")){
                            this.getAjson().setMsgCode("800");
                        }else{
                            this.getAjson().setMsg(jsonall.get("msg").toString());
                            this.getAjson().setMsgCode("900");
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
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
}
