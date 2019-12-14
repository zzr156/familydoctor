package com.ylz.view.web.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.web.dao.WebSignUpDao;
import com.ylz.bizDo.web.po.WebDrUser;
import com.ylz.bizDo.web.po.WebHospDept;
import com.ylz.bizDo.web.po.WebTeam;
import com.ylz.bizDo.web.vo.*;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.UserUpHpisType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.AesEncrypt;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
@Action(
        value="webSign",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"}),
                @Result(name = "upjson", type = "json",params={"root","upjson","contentType", "application/json"})
        }
)
public class WebSignAction extends CommonAction {

    private Logger logger = LoggerFactory.getLogger(WebSignAction.class);

    private static final String ylzTestKey = "ylzdocortest@)!*)!";
    private static final String ylzKey = "familyDoctor@)!*ylz";

    //福州签约web版接口 对外
    public String webSignUp(){
        try{

            WebSignVo vo = (WebSignVo)getAppJson(WebSignVo.class);
            if(vo != null ){
                String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                    }
                }
                AppSignForm vos=sysDao.getWebSignFormDao().webSignUp(vo);
                if(vos!=null && StringUtils.isNotBlank(vos.getId())) {
//                    AppSignControl control = new AppSignControl();
//                    control.setSignDrId(vos.getSignDrId());
//                    control.setSignId(vos.getId());
//                    control.setSignPatientId(vos.getSignPatientId());
//                    control.setSignState(UserUpHpisType.WEIJIHUO.getValue());
//                    control.setSignTeamId(vos.getSignTeamId());
//                    this.getSysDao().getServiceDo().add(control);
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsg("上传成功");
                    this.getAjson().setMsgCode("800");

//                    AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,vos.getSignDrId());
//                    if(drUser != null && StringUtils.isBlank(drUser.getDrEaseState())){
//                        this.sysDao.getSecurityCardAsyncBean().registeredEasemob(drUser.getId());
//                    }
//                    AppPatientUser user = (AppPatientUser) this.sysDao.getServiceDo().find(AppPatientUser.class,vos.getSignPatientId());
//                    if(user != null && StringUtils.isBlank(user.getPatientEaseState())){
//                        this.sysDao.getSecurityCardAsyncBean().registeredEasemob(user.getId());
//                    }
//                    AppTeam team = (AppTeam)this.getSysDao().getServiceDo().find(AppTeam.class,vos.getSignTeamId());
//                    if(team != null && StringUtils.isBlank(team.getTeamEaseGroupId())){
//                        this.sysDao.getSecurityCardAsyncBean().addGroup(team);
//                    }else{
//                        AppTeamMember member = this.getSysDao().getAppTeamMemberDao().findMemByDrId(vos.getSignDrId(),team.getId());
//                        this.sysDao.getSecurityCardAsyncBean().addGroupMembers(team.getTeamEaseGroupId(),member.getMemDrId());
//                    }
//                    if(team != null && StringUtils.isBlank(team.getTeamEaseRoomId())){
//                        this.sysDao.getSecurityCardAsyncBean().addRoom(team);
//                    }else{
//                        AppTeamMember member = this.getSysDao().getAppTeamMemberDao().findMemByDrId(vos.getSignDrId(),team.getId());
//                        this.sysDao.getSecurityCardAsyncBean().addRoomMembers(team.getTeamEaseRoomId(),member.getMemDrId());
//                    }
                }else {
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("上传失败");
                }
                logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+this.getAjson().getMsgCode());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }


    //签约web版接口 对外
    public String webSignUpTempPt(){
        try{
            WebSignVo vo = (WebSignVo)getAppJson(WebSignVo.class);
            if(vo != null ){
                String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                    }
                }
                WebSignVo vos=sysDao.getWebSignFormDao().webSignUpTempPt(vo);
                if(vos!=null) {
//                    AppSignControl control = new AppSignControl();
//                    control.setSignDrId(vos.getDrId());
////                    control.setSignId(vos.getId());
////                    control.setSignPatientId(vos.getSignPatientId());
//                    control.setSignState(UserUpHpisType.WEIJIHUO.getValue());
//                    control.setSignTeamId(vos.getTeamId());
//                    this.getSysDao().getServiceDo().add(control);
                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("800");

//                    AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,vos.getSignDrId());
//                    if(drUser != null && StringUtils.isBlank(drUser.getDrEaseState())){
//                        this.sysDao.getSecurityCardAsyncBean().registeredEasemob(drUser.getId());
//                    }
//                    AppPatientUser user = (AppPatientUser) this.sysDao.getServiceDo().find(AppPatientUser.class,vos.getSignPatientId());
//                    if(user != null && StringUtils.isBlank(user.getPatientEaseState())){
//                        this.sysDao.getSecurityCardAsyncBean().registeredEasemob(user.getId());
//                    }
//                    AppTeam team = (AppTeam)this.getSysDao().getServiceDo().find(AppTeam.class,vos.getSignTeamId());
//                    if(team != null && StringUtils.isBlank(team.getTeamEaseGroupId())){
//                        this.sysDao.getSecurityCardAsyncBean().addGroup(team);
//                    }else{
//                        AppTeamMember member = this.getSysDao().getAppTeamMemberDao().findMemByDrId(vos.getSignDrId(),team.getId());
//                        this.sysDao.getSecurityCardAsyncBean().addGroupMembers(team.getTeamEaseGroupId(),member.getMemDrId());
//                    }
//                    if(team != null && StringUtils.isBlank(team.getTeamEaseRoomId())){
//                        this.sysDao.getSecurityCardAsyncBean().addRoom(team);
//                    }else{
//                        AppTeamMember member = this.getSysDao().getAppTeamMemberDao().findMemByDrId(vos.getSignDrId(),team.getId());
//                        this.sysDao.getSecurityCardAsyncBean().addRoomMembers(team.getTeamEaseRoomId(),member.getMemDrId());
//                    }
                }else {
                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("上传失败");
                }
                logger.info("传输数据"+ JsonUtil.toJson(vos)+"成功状态:"+this.getAjson().getMsgCode());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }


    /**
     * 各地市web签约数据上传接口
     * @return
     */
    public String upWebSign(){
        //            AesEncrypt aesEncrypt = new AesEncrypt(ylzKey);
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            if(p != null){
                String json = aesEncrypt.decrypt(p.getStrJson());
                WebSignUpVo vo = JsonUtil.fromJson(json,WebSignUpVo.class);
                if(vo != null ){
                    String result = null;
                    String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                    CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                    if(code != null){
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
                        if(StringUtils.isNotBlank(result)){
                            vo.setDrId(result+vo.getDrId());
                            vo.setHospId(result+vo.getHospId());
                        }
                    }
                    AppSignForm vos=sysDao.getWebSignUpDao().webSignUp(vo);
                    if(vos!=null && StringUtils.isNotBlank(vos.getId())) {
//                        AppSignControl control = new AppSignControl();
//                        control.setSignDrId(vos.getSignDrId());
//                        control.setSignId(vos.getId());
//                        control.setSignPatientId(vos.getSignPatientId());
//                        control.setSignState(UserUpHpisType.WEIJIHUO.getValue());
//                        control.setSignTeamId(vos.getSignTeamId());
//                        this.getSysDao().getServiceDo().add(control);
//                    this.getAjson().setVo(vos);
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("signFormId",vos.getId());
                        this.getAjson().setMap(map);
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setMsg("上传成功!");
                    }else {
//                    this.getAjson().setVo(vos);
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("上传失败");
                    }
                    logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+this.getAjson().getMsgCode());
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数错误");
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

    /**
     * 添加或修改医院机构
     * @return
     */
    public String webSaveHosp(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            String json = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(json,WebSignUpVo.class);
            if(vo != null ){
                String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setHospId(result+vo.getHospId());
                    }
                }
                WebHospDept webDept = sysDao.getWebSignUpDao().webSaveHosp(vo);
                if(webDept!=null && StringUtils.isNotBlank(webDept.getId())) {
//                    this.getAjson().setVo(webDept);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("上传成功!");
                }else {
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("上传失败");
                }
//                logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+this.getAjson().getMsgCode());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

    /**
     * 添加或修改医生信息
     * @return
     */
    public String webSaveDr(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            String json = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(json,WebSignUpVo.class);
            if(vo != null ){
                String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                    }
                }
                WebDrUser webDr = sysDao.getWebSignUpDao().webSaveDr(vo);
                if(webDr!=null && StringUtils.isNotBlank(webDr.getId())) {
//                    this.getAjson().setVo(webDr);
                    this.getAjson().setMsg("上传成功!");
                    this.getAjson().setMsgCode("800");
                }else {
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("上传失败");
                }
//                logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+this.getAjson().getMsgCode());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

    /**
     * 添加或修改团队信息
     * @return
     */
    public String webSaveTeam(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            String json = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(json,WebSignUpVo.class);
            if(vo != null ){
                String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                    }
                }
                WebTeam webTeam = sysDao.getWebSignUpDao().webSaveTeam(vo);
                if(webTeam!=null && StringUtils.isNotBlank(webTeam.getId())) {
//                    this.getAjson().setVo(webTeam);
                    this.getAjson().setMsg("上传成功!");
                    this.getAjson().setMsgCode("800");
                }else {
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("上传失败");
                }
//                logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+this.getAjson().getMsgCode());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

    /**
     * 添加或删除团队成员
     * @return
     */
    public String webSaveTeamM(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            String json = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(json,WebSignUpVo.class);
            if(vo != null ){
                String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                    }
                }
                AppTeamMember webTeam = sysDao.getWebSignUpDao().webSaveTeamM(vo);
                if(webTeam!=null && StringUtils.isNotBlank(webTeam.getId())&&"1".equals(vo.getTeamMerType())) {
//                    this.getAjson().setVo(webTeam);
                    this.getAjson().setMsg("上传成功!");
                    this.getAjson().setMsgCode("800");
                }else {
                    if(webTeam==null&&"2".equals(vo.getTeamMerType())){
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setMsg("删除成功");
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("上传失败");
                    }
                }
//                logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+this.getAjson().getMsgCode());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

    /**
     * 删除当前签约单
     * @return
     */
    public String webDeleteSign(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            String json = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(json,WebSignUpVo.class);
            if(vo != null ){
               /* String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                    }
                }*/
                AppSignForm signForm = sysDao.getWebSignUpDao().webDeleteSign(vo);
                if(signForm!=null && StringUtils.isNotBlank(signForm.getId())) {
//                    this.getAjson().setVo(signForm);
                    this.getAjson().setMsg("上传成功!");
                    this.getAjson().setMsgCode("800");
                }else {
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("上传失败");
                }
//                logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+this.getAjson().getMsgCode());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

    /**
     * 修改签约单缴费状态
     * @return
     */
    public String webModifySign(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            String json = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(json,WebSignUpVo.class);
            if(vo != null ){
                /*String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                    }
                }*/
                AppSignForm signForm = sysDao.getWebSignUpDao().webModifySign(vo);
                if(signForm!=null && StringUtils.isNotBlank(signForm.getId())) {
//                    this.getAjson().setVo(signForm);
                    this.getAjson().setMsg("上传成功!");
                    this.getAjson().setMsgCode("800");
                }else {
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("上传失败");
                }
//                logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+this.getAjson().getMsgCode());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

    /**
     * 签约单团队变更
     * @return
     */
    public String webChangeSign(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            String json = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(json,WebSignUpVo.class);
            if(vo != null ){
                String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                    }
                }
                AppSignForm signForm = sysDao.getWebSignUpDao().webChangeSign(vo);
                if(signForm!=null && StringUtils.isNotBlank(signForm.getId())) {
//                    this.getAjson().setVo(signForm);
                    this.getAjson().setMsg("变更成功!");
                    this.getAjson().setMsgCode("800");
                }else {
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("上传失败");
                }
//                logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+this.getAjson().getMsgCode());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

    /**
     * 添加服务套餐信息
     * @return
     */
    public String addServeMeal(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            String json = aesEncrypt.decrypt(p.getStrJson());
            WebServeMealVo vo = JsonUtil.fromJson(json,WebServeMealVo.class);
            if(vo != null ){
                String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setStrAre(result);
                    }
                }
                AppServeSetmeal meal = sysDao.getWebSignUpDao().webAddServeMeal(vo);
                if(meal!=null && StringUtils.isNotBlank(meal.getId())) {
//                    this.getAjson().setVo(meal);
                    this.getAjson().setMsg("上传成功!");
                    this.getAjson().setMsgCode("800");
                }else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("上传失败");
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

    /**
     * 履约数据上传
     * @return
     */
    public String webAppPerformanceData(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            String json = aesEncrypt.decrypt(p.getStrJson());
            PerformanceDataQvo vo = JsonUtil.fromJson(json,PerformanceDataQvo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppPatientUser user = sysDao.getAppPatientUserDao().findByIdnoAndName(vo.getPerIdno(),vo.getPerName());
                if(user != null){
                    AppSignForm form= sysDao.getAppSignformDao().getSignFormUserId(user.getId());
                    if(form != null){
                        if(StringUtils.isNotBlank(vo.getPerArea())){
                            if(StringUtils.isNotBlank(vo.getPerType())){
                                String result = null;
                                if (vo.getPerArea().equals(AddressType.FZ.getValue())) {

                                } else if (vo.getPerArea().equals(AddressType.QZ.getValue())) {
                                    result = "qz_";
                                } else if (vo.getPerArea().equals(AddressType.ZZ.getValue())) {
                                    result = "zz_";
                                } else if (vo.getPerArea().equals(AddressType.PT.getValue())) {
                                    result = "pt_";
                                } else if (vo.getPerArea().equals(AddressType.NP.getValue())) {
                                    result = "np_";
                                } else if (vo.getPerArea().equals(AddressType.SM.getValue())) {
                                    result = "sm_";
                                }
                                if(StringUtils.isNotBlank(result)){
                                    vo.setDrId(result+vo.getDrId());
                                    vo.setHospId(result+vo.getHospId());
                                }
                                String fwType = "";
                                String sermeal = "";//服务包id
                                fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                sermeal = form.getSignpackageid();
                                this.sysDao.getAppPerformanceStatisticsDao().addPerformanceData(vo,sermeal,fwType);
                                this.getAjson().setMsgCode("800");
                                this.getAjson().setMsg("添加成功!");
                            }else{
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("接收类型不能为空");
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("接收地区不能为空");
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("该居民未签约");
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("该居民未注册");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

    /**
     * 转签数据
     * @return
     */
    public String webGoToSign(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            String json = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(json,WebSignUpVo.class);
            if(vo != null ){
                String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                        if(StringUtils.isNotBlank(vo.getDrOperatorId())){//操作医生主键
                            vo.setDrOperatorId(result+vo.getDrOperatorId());
                        }
                        if(StringUtils.isNotBlank(vo.getDrAssistantId())){//助理医生主键
                            vo.setDrAssistantId(result+vo.getDrAssistantId());
                        }
                    }
                }
                AppSignForm vos=sysDao.getWebSignUpDao().webGotoSignUp(vo);
                if(vos!=null && StringUtils.isNotBlank(vos.getId())) {
//                    AppSignControl control = new AppSignControl();
//                    control.setSignDrId(vos.getSignDrId());
//                    control.setSignId(vos.getId());
//                    control.setSignPatientId(vos.getSignPatientId());
//                    control.setSignState(UserUpHpisType.WEIJIHUO.getValue());
//                    control.setSignTeamId(vos.getSignTeamId());
//                    this.getSysDao().getServiceDo().add(control);
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("signFormId",vos.getId());
                    this.getAjson().setMap(map);
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("上传成功!");
                }else {
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("上传失败");
                }
                logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+this.getAjson().getMsgCode());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }

    /**
     * 续签
     * @return
     */
    public String webContinueSign(){
        AesEncrypt aesEncrypt = new AesEncrypt(ylzTestKey);
        try{
            WebUpVo p = (WebUpVo) this.getAppJson(WebUpVo.class);
            String json = aesEncrypt.decrypt(p.getStrJson());
            WebSignUpVo vo = JsonUtil.fromJson(json,WebSignUpVo.class);
            if(vo != null ){
                String result = null;
                String cityCode = AreaUtils.getAreaCode(vo.getAreaCodeCity(),"2");
                CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                if(code != null){
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
                    if(StringUtils.isNotBlank(result)){
                        vo.setDrId(result+vo.getDrId());
                        vo.setHospId(result+vo.getHospId());
                        if(StringUtils.isNotBlank(vo.getDrOperatorId())){//操作医生主键
                            vo.setDrOperatorId(result+vo.getDrOperatorId());
                        }
                        if(StringUtils.isNotBlank(vo.getDrAssistantId())){//助理医生主键
                            vo.setDrAssistantId(result+vo.getDrAssistantId());
                        }
                    }
                }
                AppSignForm vos=sysDao.getWebSignUpDao().webContinueSign(vo);
                if(vos!=null && StringUtils.isNotBlank(vos.getId())) {
//                    AppSignControl control = new AppSignControl();
//                    control.setSignDrId(vos.getSignDrId());
//                    control.setSignId(vos.getId());
//                    control.setSignPatientId(vos.getSignPatientId());
//                    control.setSignState(UserUpHpisType.WEIJIHUO.getValue());
//                    control.setSignTeamId(vos.getSignTeamId());
//                    this.getSysDao().getServiceDo().add(control);
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("上传成功!");
                }else {
//                    this.getAjson().setVo(vos);
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("上传失败");
                }
                logger.info("传输姓名:"+vo.getPatientName()+"身份证:"+vo.getPatientIdno()+"签约单主键:"+vos.getId()+"成功状态:"+this.getAjson().getMsgCode());
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
//            new ActionException(getClass(),getAct(),getJsons(),e);
            if(StringUtils.isNotBlank(e.getMessage())) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.toString());
            }
        }finally {
            String result = JsonUtil.toJson(this.getAjson());
            this.getUpjson().setEntity(aesEncrypt.encrypt(result));
        }
        return "upjson";
    }
}

