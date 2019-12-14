package com.ylz.webservices.server.impl;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppJson;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.web.po.WebDrUser;
import com.ylz.bizDo.web.po.WebHospDept;
import com.ylz.bizDo.web.po.WebTeam;
import com.ylz.bizDo.web.vo.WebJson;
import com.ylz.bizDo.web.vo.WebServeMealVo;
import com.ylz.bizDo.web.vo.WebSignUpVo;
import com.ylz.bizDo.web.vo.WebUpVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.UserUpHpisType;
import com.ylz.packcommon.common.util.AesEncrypt;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.JsonUtil;
import com.ylz.view.web.action.WebSignAction;
import com.ylz.webservices.server.SignFamilyService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 对外签约接口
 */
public class SignFamilyServiceImpl implements SignFamilyService{

    private Logger logger = LoggerFactory.getLogger(SignFamilyServiceImpl.class);

    private static final String ylzTestKey = "ylzdocortest@)!*)!";
    private static final String ylzKey = "familyDoctor@)!*ylz";

    @Autowired
    public SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");

    /**
     * 各地市web签约数据上传接口
     * @return
     */
    @Override
    public String upWebSign(String json) {
        //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        AppJson ajson = new AppJson();
        WebJson webJson = new WebJson();
        try{
            WebUpVo p = JsonUtil.fromJson(json,WebUpVo.class);
            if(p != null){
                String strJson = aesEncrypt.decrypt(p.getStrJson());
                WebSignUpVo vo = JsonUtil.fromJson(strJson,WebSignUpVo.class);
                if(vo != null ){
                    String result = this.webAreaCodeResult(vo.getAreaCodeCity());
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                    }
                    AppSignForm vos = sysDao.getWebSignUpDao().webSignUp(vo);
                    if(vos!=null && StringUtils.isNotBlank(vos.getId())) {
//                        AppSignControl control = new AppSignControl();
//                        control.setSignDrId(vos.getSignDrId());
//                        control.setSignId(vos.getId());
//                        control.setSignPatientId(vos.getSignPatientId());
//                        control.setSignState(UserUpHpisType.WEIJIHUO.getValue());
//                        control.setSignTeamId(vos.getSignTeamId());
//                        this.sysDao.getServiceDo().add(control);
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("signFormId",vos.getId());
                        ajson.setMap(map);
                        ajson.setMsgCode("800");
                        ajson.setMsg("上传成功!");
                    }else {
                        ajson.setMsgCode("900");
                        ajson.setMsg("上传失败");
                    }
                    logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+ajson.getMsgCode());
                }else{
                    ajson.setMsgCode("900");
                    ajson.setMsg("参数错误");
                }
            }else{
                ajson.setMsgCode("900");
                ajson.setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            if(StringUtils.isNotBlank(e.getMessage())) {
                ajson.setMsgCode("900");
                ajson.setMsg(e.getMessage());
            }else {
                ajson.setMsgCode("900");
                ajson.setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(ajson);
            webJson.setEntity(aesEncrypt.encrypt(result));
        }
        return JsonUtil.toJson(webJson);
    }

    /**
     * 添加或修改医院机构
     * @return
     */
    @Override
    public String webSaveHosp(String json) {
        //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        AppJson ajson = new AppJson();
        WebJson webJson = new WebJson();
        try{
            WebUpVo p = JsonUtil.fromJson(json,WebUpVo.class);;
            String strJson = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(strJson,WebSignUpVo.class);
            if(vo != null ){
                String result = this.webAreaCodeResult(vo.getAreaCodeCity());
                if(StringUtils.isNotBlank(result)){
                    vo.setHospId(result+vo.getHospId());
                }
                WebHospDept webDept = sysDao.getWebSignUpDao().webSaveHosp(vo);
                if(webDept!=null && StringUtils.isNotBlank(webDept.getId())) {
                    ajson.setMsgCode("800");
                    ajson.setMsg("上传成功!");
                }else {
                    ajson.setMsgCode("900");
                    ajson.setMsg("上传失败");
                }
            }else{
                ajson.setMsgCode("900");
                ajson.setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            if(StringUtils.isNotBlank(e.getMessage())) {
                ajson.setMsgCode("900");
                ajson.setMsg(e.getMessage());
            }else {
                ajson.setMsgCode("900");
                ajson.setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(ajson);
            webJson.setEntity(aesEncrypt.encrypt(result));
        }
        return JsonUtil.toJson(webJson);
    }

    /**
     * 添加或修改医生信息
     * @return
     */
    @Override
    public String webSaveDr(String json) {
        //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        AppJson ajson = new AppJson();
        WebJson webJson = new WebJson();
        try{
            WebUpVo p = JsonUtil.fromJson(json,WebUpVo.class);;
            String strJson = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(strJson,WebSignUpVo.class);
            if(vo != null ){
                String result = this.webAreaCodeResult(vo.getAreaCodeCity());
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                    }
                WebDrUser webDr = sysDao.getWebSignUpDao().webSaveDr(vo);
                if(webDr!=null && StringUtils.isNotBlank(webDr.getId())) {
                    ajson.setMsg("上传成功!");
                    ajson.setMsgCode("800");
                }else {
                    ajson.setMsgCode("900");
                    ajson.setMsg("上传失败");
                }
            }else{
                ajson.setMsgCode("900");
                ajson.setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            if(StringUtils.isNotBlank(e.getMessage())) {
                ajson.setMsgCode("900");
                ajson.setMsg(e.getMessage());
            }else {
                ajson.setMsgCode("900");
                ajson.setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(ajson);
            webJson.setEntity(aesEncrypt.encrypt(result));
        }
        return JsonUtil.toJson(webJson);
    }

    /**
     * 添加或修改团队信息
     * @return
     */
    @Override
    public String webSaveTeam(String json) {
        //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        AppJson ajson = new AppJson();
        WebJson webJson = new WebJson();
        try{
            WebUpVo p = JsonUtil.fromJson(json,WebUpVo.class);;
            String strJson = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(strJson,WebSignUpVo.class);
            if(vo != null ){
                String result = this.webAreaCodeResult(vo.getAreaCodeCity());
                if(StringUtils.isNotBlank(result)){
                    vo.setDrId(result+vo.getDrId());
                    vo.setHospId(result+vo.getHospId());
                }
                WebTeam webTeam = sysDao.getWebSignUpDao().webSaveTeam(vo);
                if(webTeam!=null && StringUtils.isNotBlank(webTeam.getId())) {
                    ajson.setMsg("上传成功!");
                    ajson.setMsgCode("800");
                }else {
                    ajson.setMsgCode("900");
                    ajson.setMsg("上传失败");
                }
            }else{
                ajson.setMsgCode("900");
                ajson.setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            if(StringUtils.isNotBlank(e.getMessage())) {
                ajson.setMsgCode("900");
                ajson.setMsg(e.getMessage());
            }else {
                ajson.setMsgCode("900");
                ajson.setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(ajson);
            webJson.setEntity(aesEncrypt.encrypt(result));
        }
        return JsonUtil.toJson(webJson);
    }

    /**
     * 添加或删除团队成员
     * @return
     */
    @Override
    public String webSaveTeamM(String json) {
        //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        AppJson ajson = new AppJson();
        WebJson webJson = new WebJson();
        try{
            WebUpVo p = JsonUtil.fromJson(json,WebUpVo.class);;
            String strJson = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(strJson,WebSignUpVo.class);
            if(vo != null ){
                String result = this.webAreaCodeResult(vo.getAreaCodeCity());
                if(StringUtils.isNotBlank(result)){
                    vo.setDrId(result+vo.getDrId());
                    vo.setHospId(result+vo.getHospId());
                }
                AppTeamMember webTeam = sysDao.getWebSignUpDao().webSaveTeamM(vo);
                if(webTeam!=null && StringUtils.isNotBlank(webTeam.getId())&&"1".equals(vo.getTeamMerType())) {
                    ajson.setMsg("上传成功!");
                    ajson.setMsgCode("800");
                }else {
                    if(webTeam == null && "2".equals(vo.getTeamMerType())){
                        ajson.setMsgCode("800");
                        ajson.setMsg("删除成功");
                    }else{
                        ajson.setMsgCode("900");
                        ajson.setMsg("上传失败");
                    }
                }
            }else{
                ajson.setMsgCode("900");
                ajson.setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            if(StringUtils.isNotBlank(e.getMessage())) {
                ajson.setMsgCode("900");
                ajson.setMsg(e.getMessage());
            }else {
                ajson.setMsgCode("900");
                ajson.setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(ajson);
            webJson.setEntity(aesEncrypt.encrypt(result));
        }
        return JsonUtil.toJson(webJson);
    }

    /**
     * 签约单团队变更
     * @return
     */
    @Override
    public String webChangeSign(String json) {
        //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        AppJson ajson = new AppJson();
        WebJson webJson = new WebJson();
        try{
            WebUpVo p = JsonUtil.fromJson(json,WebUpVo.class);;
            String strJson = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(strJson,WebSignUpVo.class);
            if(vo != null ){
                String result = this.webAreaCodeResult(vo.getAreaCodeCity());
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                    }
                AppSignForm signForm = sysDao.getWebSignUpDao().webChangeSign(vo);
                if(signForm!=null && StringUtils.isNotBlank(signForm.getId())) {
                    ajson.setMsg("变更成功!");
                    ajson.setMsgCode("800");
                }else {
                    ajson.setMsgCode("900");
                    ajson.setMsg("上传失败");
                }
            }else{
                ajson.setMsgCode("900");
                ajson.setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            if(StringUtils.isNotBlank(e.getMessage())) {
                ajson.setMsgCode("900");
                ajson.setMsg(e.getMessage());
            }else {
                ajson.setMsgCode("900");
                ajson.setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(ajson);
            webJson.setEntity(aesEncrypt.encrypt(result));
        }
        return JsonUtil.toJson(webJson);
    }

    /**
     * 添加服务套餐信息
     * @param json
     * @return
     */
    @Override
    public String addServeMeal(String json) {
        //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        AppJson ajson = new AppJson();
        WebJson webJson = new WebJson();
        try{
            WebUpVo p = JsonUtil.fromJson(json,WebUpVo.class);;
            String strJson = aesEncrypt.decrypt(p.getStrJson());
            WebServeMealVo vo = JsonUtil.fromJson(strJson,WebServeMealVo.class);
            if(vo != null ){
                String result = this.webAreaCodeResult(vo.getAreaCodeCity());
                if(StringUtils.isNotBlank(result)){
                    vo.setStrAre(result);
                }
                AppServeSetmeal meal = sysDao.getWebSignUpDao().webAddServeMeal(vo);
                if(meal!=null && StringUtils.isNotBlank(meal.getId())) {
                    ajson.setMsg("上传成功!");
                    ajson.setMsgCode("800");
                }else {
                    ajson.setMsgCode("900");
                    ajson.setMsg("上传失败");
                }
            }else{
                ajson.setMsgCode("900");
                ajson.setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            if(StringUtils.isNotBlank(e.getMessage())) {
                ajson.setMsgCode("900");
                ajson.setMsg(e.getMessage());
            }else {
                ajson.setMsgCode("900");
                ajson.setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(ajson);
            webJson.setEntity(aesEncrypt.encrypt(result));
        }
        return JsonUtil.toJson(webJson);
    }

    /**
     * 履约数据上传
     * @param json
     * @return
     */
    @Override
    public String webAppPerformanceData(String json) {
        //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        AppJson ajson = new AppJson();
        WebJson webJson = new WebJson();
        try{
            WebUpVo p = JsonUtil.fromJson(json,WebUpVo.class);;
            String strJson = aesEncrypt.decrypt(p.getStrJson());
            PerformanceDataQvo vo = JsonUtil.fromJson(strJson,PerformanceDataQvo.class);
            if(vo==null){
                ajson.setMsgCode("900");
                ajson.setMsg("参数格式错误");
            }else{
                AppPatientUser user = sysDao.getAppPatientUserDao().findByIdnoAndName(vo.getPerIdno(),vo.getPerName());
                if(user != null) {
                    AppSignForm form = sysDao.getAppSignformDao().getSignFormUserId(user.getId());
                    if (form != null) {
                        if(StringUtils.isNotBlank(vo.getPerArea())){
                            if(StringUtils.isNotBlank(vo.getPerType())){
                                String result = this.webAreaCodeResult(vo.getPerArea());
                                if(StringUtils.isNotBlank(result)){
                                    vo.setDrId(result+vo.getDrId());
                                    vo.setHospId(result+vo.getHospId());
                                }
                                String fwType = "";
                                String sermeal = "";//服务包id
                                fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                sermeal = form.getSignpackageid();
                                this.sysDao.getAppPerformanceStatisticsDao().addPerformanceData(vo,sermeal,fwType);
                                ajson.setMsgCode("800");
                                ajson.setMsg("添加成功!");
                            }else{
                                ajson.setMsgCode("900");
                                ajson.setMsg("接收类型不能为空");
                            }
                        }else{
                            ajson.setMsgCode("900");
                            ajson.setMsg("接收地区不能为空");
                        }
                    }else{
                        ajson.setMsgCode("900");
                        ajson.setMsg("该居民未签约");
                    }
                }else{
                    ajson.setMsgCode("900");
                    ajson.setMsg("该居民未注册");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            if(StringUtils.isNotBlank(e.getMessage())) {
                ajson.setMsgCode("900");
                ajson.setMsg(e.getMessage());
            }else {
                ajson.setMsgCode("900");
                ajson.setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(ajson);
            webJson.setEntity(aesEncrypt.encrypt(result));
        }
        return JsonUtil.toJson(webJson);
    }


    public String webAreaCodeResult(String areaCode)  throws Exception{
        String result = null;
        String cityCode = AreaUtils.getAreaCode(areaCode, "2");
        CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
        if (code != null) {
            if (code.getCodeTitle().equals(AddressType.FZ.getValue())) {

            } else if (code.getCodeTitle().equals(AddressType.QZ.getValue())) {
                result = "qz_";
            } else if (code.getCodeTitle().equals(AddressType.ZZ.getValue())) {
                result = "zz_";
            } else if (code.getCodeTitle().equals(AddressType.PT.getValue())) {
                result = "pt_";
            } else if (code.getCodeTitle().equals(AddressType.NP.getValue())) {
                result = "np_";
            } else if (code.getCodeTitle().equals(AddressType.SM.getValue())) {
                result = "sm_";
            }
        }
        return result;
    }
}
