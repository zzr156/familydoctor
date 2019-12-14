package com.ylz.view.gairui.action;

import com.ylz.bizDo.app.po.AppMsg;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.jtapp.gaiRuiEntity.*;
import com.ylz.bizDo.jtapp.gaiRuiVo.GaiRuiQvo;
import com.ylz.bizDo.jtapp.gaiRuiVo.GaiRuiVo;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamEntity;
import com.ylz.bizDo.web.vo.WebSignUpVo;
import com.ylz.bizDo.web.vo.WebUpVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2019/3/21.
 */
@SuppressWarnings("all")
@Action(
        value="gaiRui",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"}),
                @Result(name = "upjson", type = "json",params={"root","upjson","contentType", "application/json"})
        }
)
public class GaiRuiAction extends CommonAction {
//    private static final String ylzCsKey = "ylzdocortest@)!*)!";//测试key
    private static final String ylzCsKey = "familyDoctor@)!*ylz";//正式key

    /**
     * 上传签约数据
     * @return
     */
    public String upWebSign(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzCsKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            if(p != null){
                byte[] decryptResult = AESR.decrypt(Base64Utils.decode(p.getStrJson()),ylzCsKey,16);
                String json = new String(decryptResult,"UTF-8");
                //把json字符串转json
                JSONObject jsonall = JSONObject.fromObject(json);
                String appId = "";
                String orgId = "";
                String deviceId = "";
                String drId = "";
                String token = "";
                if(jsonall.get("appId") != null){
                    appId = jsonall.get("appId").toString();
                    //根据appId查询
                    AppMsg msg = sysDao.getAppMsgDao().findMsgByAppId(appId);
                    if(msg == null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("非法连接");
                    }
                }else{
                    this.getAjson().setMsg("appId不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("orgId") != null){
                    orgId = jsonall.get("orgId").toString();
                }else{
                    this.getAjson().setMsg("社区id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("deviceId") != null){
                    deviceId= jsonall.get("deviceId").toString();
                }else{
                    this.getAjson().setMsg("设备序列号不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("drId") != null){
                    drId= jsonall.get("drId").toString();
                }else{
                    this.getAjson().setMsg("操作用户id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("token") != null){
                    token= jsonall.get("token").toString();
                }else{
                    this.getAjson().setMsg("token不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("data") != null){
                    WebSignUpVo qvo = JsonUtil.fromJson(jsonall.get("data").toString(),WebSignUpVo.class);
                    if(qvo == null){
                        this.getAjson().setMsg("参数格式错误");
                        this.getAjson().setMsgCode("900");
                    }else{
                        AppSignForm form = sysDao.getWebSignUpDao().gairuiSignUp(qvo);
                        if(form != null){
                            Map<String,Object> map = new HashMap<String,Object>();
                            map.put("signFormId",form.getId());
                            this.getAjson().setMap(map);
                            this.getAjson().setMsgCode("800");
                        }
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请求data数据不能为空");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            try{
                String result = JsonUtil.toJson(this.getAjson());
                byte[] encryptResult = AESR.encrypt(result, ylzCsKey,16);
                this.getUpjson().setEntity(Base64Utils.encode(encryptResult));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "upjson";
    }

    /**
     * 查询居民是否签约接口
     * @return
     */
    public String getSignState(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzCsKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            if(p != null){
                byte[] decryptResult = AESR.decrypt(Base64Utils.decode(p.getStrJson()),ylzCsKey,16);
                String json = new String(decryptResult,"UTF-8");
                //把json字符串转json
                JSONObject jsonall = JSONObject.fromObject(json);
                String appId = "";
                String orgId = "";
                String deviceId = "";
                String drId = "";
                String token = "";
                if(jsonall.get("appId") != null){
                    appId = jsonall.get("appId").toString();
                    //根据appId查询
                    AppMsg msg = sysDao.getAppMsgDao().findMsgByAppId(appId);
                    if(msg == null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("非法连接");
                    }
                }else{
                    this.getAjson().setMsg("appId不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("orgId") != null){
                    orgId = jsonall.get("orgId").toString();
                }else{
                    this.getAjson().setMsg("社区id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("deviceId") != null){
                    deviceId= jsonall.get("deviceId").toString();
                }else{
                    this.getAjson().setMsg("设备序列号不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("drId") != null){
                    drId= jsonall.get("drId").toString();
                }else{
                    this.getAjson().setMsg("操作用户id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("token") != null){
                    token= jsonall.get("token").toString();
                }else{
                    this.getAjson().setMsg("token不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("data") != null){
                    WebSignUpVo qvo = JsonUtil.fromJson(jsonall.get("data").toString(),WebSignUpVo.class);
                    if(qvo == null){
                        this.getAjson().setMsg("参数格式错误");
                        this.getAjson().setMsgCode("900");
                    }else{
                        if(StringUtils.isNotBlank(qvo.getPatientIdno())){
                            //根据身份证查询居民信息
                            AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(qvo.getPatientIdno());
                            if(user != null){
                                Map<String,Object> map = new HashMap<>();
                                AppSignForm form = sysDao.getAppSignformDao().findSignFormByUser(user.getId());
                                if(form != null){
                                    if(SignFormType.DQY.getValue().equals(form.getSignState())){
                                        map.put("signState",form.getSignState());
                                    }else{
                                        map.put("signState",SignFormType.YQY.getValue());
                                    }
                                }else{
                                    map.put("signState","3");
                                }
                                this.getAjson().setMsgCode("800");
                                this.getAjson().setMap(map);
                            }else{
                                this.getAjson().setMsg("查无居民信息");
                                this.getAjson().setMsgCode("900");
                            }
                        }else {
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("身份证不能为空");
                        }

                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请求data数据不能为空");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            try{
                String result = JsonUtil.toJson(this.getAjson());
                byte[] encryptResult = AESR.encrypt(result, ylzCsKey,16);
                this.getUpjson().setEntity(Base64Utils.encode(encryptResult));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "upjson";
    }

    /**
     * 查询行政区划
     * @return
     */
    public String getAreaList(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzCsKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            if(p != null){
                byte[] decryptResult = AESR.decrypt(Base64Utils.decode(p.getStrJson()),ylzCsKey,16);
                String json = new String(decryptResult,"UTF-8");
                //把json字符串转json
                JSONObject jsonall = JSONObject.fromObject(json);
                String appId = "";
                String orgId = "";
                String deviceId = "";
                String drId = "";
                String token = "";
                if(jsonall.get("appId") != null){
                    appId = jsonall.get("appId").toString();
                    //根据appId查询
                    AppMsg msg = sysDao.getAppMsgDao().findMsgByAppId(appId);
                    if(msg == null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("非法连接");
                    }
                }else{
                    this.getAjson().setMsg("appId不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("orgId") != null){
                    orgId = jsonall.get("orgId").toString();
                }else{
                    this.getAjson().setMsg("社区id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("deviceId") != null){
                    deviceId= jsonall.get("deviceId").toString();
                }else{
                    this.getAjson().setMsg("设备序列号不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("drId") != null){
                    drId= jsonall.get("drId").toString();
                }else{
                    this.getAjson().setMsg("操作用户id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("token") != null){
                    token= jsonall.get("token").toString();
                }else{
                    this.getAjson().setMsg("token不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("data") != null){
                    GaiRuiQvo qvo = JsonUtil.fromJson(jsonall.get("data").toString(),GaiRuiQvo.class);
                    if(qvo == null){
                        this.getAjson().setMsg("参数格式错误");
                        this.getAjson().setMsgCode("900");
                    }else{
                        List<GaiRuiAreaEntity> list = sysDao.getCdAddressDao().findListByUpId(qvo.getUpId());
                        this.getAjson().setRows(list);
                        this.getAjson().setMsgCode("800");
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请求data数据不能为空");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            try{
                String result = JsonUtil.toJson(this.getAjson());
                byte[] encryptResult = AESR.encrypt(result, ylzCsKey,16);
                this.getUpjson().setEntity(Base64Utils.encode(encryptResult));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "upjson";
    }

    /**
     * 查询团队信息
     * @return
     */
    public String getTeamList(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzCsKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            if(p != null){
                byte[] decryptResult = AESR.decrypt(Base64Utils.decode(p.getStrJson()),ylzCsKey,16);
                String json = new String(decryptResult,"UTF-8");
                //把json字符串转json
                JSONObject jsonall = JSONObject.fromObject(json);
                String appId = "";
                String orgId = "";
                String deviceId = "";
                String drId = "";
                String token = "";
                if(jsonall.get("appId") != null){
                    appId = jsonall.get("appId").toString();
                    //根据appId查询
                    AppMsg msg = sysDao.getAppMsgDao().findMsgByAppId(appId);
                    if(msg == null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("非法连接");
                    }
                }else{
                    this.getAjson().setMsg("appId不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("orgId") != null){
                    orgId = jsonall.get("orgId").toString();
                }else{
                    this.getAjson().setMsg("社区id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("deviceId") != null){
                    deviceId= jsonall.get("deviceId").toString();
                }else{
                    this.getAjson().setMsg("设备序列号不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("drId") != null){
                    drId= jsonall.get("drId").toString();
                }else{
                    this.getAjson().setMsg("操作用户id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("token") != null){
                    token= jsonall.get("token").toString();
                }else{
                    this.getAjson().setMsg("token不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("data") != null){
                    GaiRuiQvo qvo = JsonUtil.fromJson(jsonall.get("data").toString(),GaiRuiQvo.class);
                    if(StringUtils.isNotBlank(qvo.getHospId())){
                        if(StringUtils.isNotBlank(qvo.getAreaCode())){
                            //先判断机构id是否包含地市前缀
                            String str = "";
                            if(qvo.getHospId().indexOf("_")==-1){
                                if(AddressType.SMS.getValue().equals(AreaUtils.getAreaCode(qvo.getAreaCode(),"2"))){
                                    str = "sm_";
                                }else if(AddressType.PTS.getValue().equals(AreaUtils.getAreaCode(qvo.getAreaCode(),"2"))){
                                    str = "pt_";
                                }else if(AddressType.QZS.getValue().equals(AreaUtils.getAreaCode(qvo.getAreaCode(),"2"))){
                                    str = "qz_";
                                }else if(AddressType.ZZS.getValue().equals(AreaUtils.getAreaCode(qvo.getAreaCode(),"2"))){
                                    str = "zz_";
                                }else if(AddressType.NPS.getValue().equals(AreaUtils.getAreaCode(qvo.getAreaCode(),"2"))){
                                    str = "np_";
                                }
                            }
                            qvo.setHospId(str+qvo.getHospId());
                            List<GaiRuiTeamEntity> list = sysDao.getAppTeamDao().findTeamByGaiRuiHospId(qvo.getHospId());
                            this.getAjson().setMsgCode("800");
                            this.getAjson().setRows(list);
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("行政区划不能为空");
                        }

                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("机构主键不能为空");
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请求data数据不能为空");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            try{
                String result = JsonUtil.toJson(this.getAjson());
                byte[] encryptResult = AESR.encrypt(result, ylzCsKey,16);
                this.getUpjson().setEntity(Base64Utils.encode(encryptResult));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "upjson";
    }

    /**
     * 查询团队成员列表
     * @return
     */
    public String getTeamDrList(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzCsKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            if(p != null){
                byte[] decryptResult = AESR.decrypt(Base64Utils.decode(p.getStrJson()),ylzCsKey,16);
                String json = new String(decryptResult,"UTF-8");
                //把json字符串转json
                JSONObject jsonall = JSONObject.fromObject(json);
                String appId = "";
                String orgId = "";
                String deviceId = "";
                String drId = "";
                String token = "";
                if(jsonall.get("appId") != null){
                    appId = jsonall.get("appId").toString();
                    //根据appId查询
                    AppMsg msg = sysDao.getAppMsgDao().findMsgByAppId(appId);
                    if(msg == null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("非法连接");
                    }
                }else{
                    this.getAjson().setMsg("appId不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("orgId") != null){
                    orgId = jsonall.get("orgId").toString();
                }else{
                    this.getAjson().setMsg("社区id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("deviceId") != null){
                    deviceId= jsonall.get("deviceId").toString();
                }else{
                    this.getAjson().setMsg("设备序列号不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("drId") != null){
                    drId= jsonall.get("drId").toString();
                }else{
                    this.getAjson().setMsg("操作用户id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("token") != null){
                    token= jsonall.get("token").toString();
                }else{
                    this.getAjson().setMsg("token不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("data") != null){
                    GaiRuiQvo qvo = JsonUtil.fromJson(jsonall.get("data").toString(),GaiRuiQvo.class);
                    if(StringUtils.isNotBlank(qvo.getTeamId())){
                        List<GaiRuiDrEntity> list = sysDao.getAppTeamMemberDao().findDrByTeamId(qvo.getTeamId());
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setRows(list);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("团队主键不能为空");
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请求data数据不能为空");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            try{
                String result = JsonUtil.toJson(this.getAjson());
                byte[] encryptResult = AESR.encrypt(result, ylzCsKey,16);
                this.getUpjson().setEntity(Base64Utils.encode(encryptResult));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "upjson";
    }

    /**
     * 查询服务人群
     * @return
     */
    public String getFwGroupList(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzCsKey);
        try {
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            if (p != null) {
                byte[] decryptResult = AESR.decrypt(Base64Utils.decode(p.getStrJson()),ylzCsKey,16);
                String json = new String(decryptResult,"UTF-8");
                GaiRuiVo qvo = JsonUtil.fromJson(json, GaiRuiVo.class);
                if(qvo != null){
                    if(StringUtils.isNotBlank(qvo.getAppId())){
                        //根据appId查询
                        AppMsg msg = sysDao.getAppMsgDao().findMsgByAppId(qvo.getAppId());
                        if(msg == null){
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("非法连接");
                        }
                    }else{
                        this.getAjson().setMsg("appId不能为空");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    }
                    if(StringUtils.isBlank(qvo.getOrgId())){
                        this.getAjson().setMsg("社区id不能为空");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    }
                    if(StringUtils.isBlank(qvo.getDeviceId())){
                        this.getAjson().setMsg("设备序列号不能为空");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    }
                    if(StringUtils.isBlank(qvo.getDrId())){
                        this.getAjson().setMsg("操作用户id不能为空");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    }
                    if(StringUtils.isBlank(qvo.getToken())){
                        this.getAjson().setMsg("token不能为空");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    }
                    //查询服务人群列表
                    List<GaiRuiLabelEntity> list = sysDao.getAppLabelManageDao().findLabelByGroup("3");
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setRows(list);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            try{
                String result = JsonUtil.toJson(this.getAjson());
                byte[] encryptResult = AESR.encrypt(result, ylzCsKey,16);
                this.getUpjson().setEntity(Base64Utils.encode(encryptResult));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "upjson";
    }

    /**
     * 查询经济类型
     * @return
     */
    public String getJjGroupList(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzCsKey);
        try {
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            if (p != null) {
                byte[] decryptResult = AESR.decrypt(Base64Utils.decode(p.getStrJson()),ylzCsKey,16);
                String json = new String(decryptResult,"UTF-8");
                GaiRuiVo qvo = JsonUtil.fromJson(json, GaiRuiVo.class);
                if(qvo != null){
                    if(StringUtils.isNotBlank(qvo.getAppId())){
                        //根据appId查询
                        AppMsg msg = sysDao.getAppMsgDao().findMsgByAppId(qvo.getAppId());
                        if(msg == null){
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("非法连接");
                        }
                    }else{
                        this.getAjson().setMsg("appId不能为空");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    }
                    if(StringUtils.isBlank(qvo.getOrgId())){
                        this.getAjson().setMsg("社区id不能为空");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    }
                    if(StringUtils.isBlank(qvo.getDeviceId())){
                        this.getAjson().setMsg("设备序列号不能为空");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    }
                    if(StringUtils.isBlank(qvo.getDrId())){
                        this.getAjson().setMsg("操作用户id不能为空");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    }
                    if(StringUtils.isBlank(qvo.getToken())){
                        this.getAjson().setMsg("token不能为空");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    }
                    //查询经济类型列表
                    List<GaiRuiLabelEntity> list = sysDao.getAppLabelManageDao().findLabelByGroup("4");
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setRows(list);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            try{
                String result = JsonUtil.toJson(this.getAjson());
                byte[] encryptResult = AESR.encrypt(result, ylzCsKey,16);
                this.getUpjson().setEntity(Base64Utils.encode(encryptResult));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "upjson";
    }

    /**
     * 查询服务套餐列表
     * @return
     */
    public String getServePkList(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzCsKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            if(p != null){
                byte[] decryptResult = AESR.decrypt(Base64Utils.decode(p.getStrJson()),ylzCsKey,16);
                String json = new String(decryptResult,"UTF-8");
                //把json字符串转json
                JSONObject jsonall = JSONObject.fromObject(json);
                String appId = "";
                String orgId = "";
                String deviceId = "";
                String drId = "";
                String token = "";
                if(jsonall.get("appId") != null){
                    appId = jsonall.get("appId").toString();
                    //根据appId查询
                    AppMsg msg = sysDao.getAppMsgDao().findMsgByAppId(appId);
                    if(msg == null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("非法连接");
                    }
                }else{
                    this.getAjson().setMsg("appId不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("orgId") != null){
                    orgId = jsonall.get("orgId").toString();
                }else{
                    this.getAjson().setMsg("社区id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("deviceId") != null){
                    deviceId= jsonall.get("deviceId").toString();
                }else{
                    this.getAjson().setMsg("设备序列号不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("drId") != null){
                    drId= jsonall.get("drId").toString();
                }else{
                    this.getAjson().setMsg("操作用户id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("token") != null){
                    token= jsonall.get("token").toString();
                }else{
                    this.getAjson().setMsg("token不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(jsonall.get("data") != null){
                    GaiRuiQvo qvo = JsonUtil.fromJson(jsonall.get("data").toString(),GaiRuiQvo.class);
                    if(StringUtils.isNotBlank(qvo.getHospId())){
                        if(StringUtils.isNotBlank(qvo.getAreaCode())){
                            //先判断机构id是否包含地市前缀
                            String str = "";
                            if(qvo.getHospId().indexOf("_")==-1){
                                if(AddressType.SMS.getValue().equals(AreaUtils.getAreaCode(qvo.getAreaCode(),"2"))){
                                    str = "sm_";
                                }else if(AddressType.PTS.getValue().equals(AreaUtils.getAreaCode(qvo.getAreaCode(),"2"))){
                                    str = "pt_";
                                }else if(AddressType.QZS.getValue().equals(AreaUtils.getAreaCode(qvo.getAreaCode(),"2"))){
                                    str = "qz_";
                                }else if(AddressType.ZZS.getValue().equals(AreaUtils.getAreaCode(qvo.getAreaCode(),"2"))){
                                    str = "zz_";
                                }else if(AddressType.NPS.getValue().equals(AreaUtils.getAreaCode(qvo.getAreaCode(),"2"))){
                                    str = "np_";
                                }
                            }
                            qvo.setHospId(str+qvo.getHospId());
                            List<GaiRuiMealEntity> list = sysDao.getAppServeSetmealDao().findListMealByHospId(qvo.getHospId());
                            this.getAjson().setMsgCode("800");
                            this.getAjson().setRows(list);
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("行政区划不能为空");
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("机构主键不能为空");
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请求data数据不能为空");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            try{
                String result = JsonUtil.toJson(this.getAjson());
                byte[] encryptResult = AESR.encrypt(result, ylzCsKey,16);
                this.getUpjson().setEntity(Base64Utils.encode(encryptResult));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "upjson";
    }
}
