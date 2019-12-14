package com.ylz.view.sign.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.assessment.vo.AssessReadJwVo;
import com.ylz.bizDo.assessment.vo.view.*;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

@Action(
        value = "assessmentReadJwAction",
        results = {
                @Result(name = "json", type = "json", params = {"root", "jsons", "contentType", "application/json"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","excludeNullProperties","true","contentType", "application/json"})
        }

)
/**
 * 考核调阅基卫相关信息
 */
public class AssessmentReadJwAction extends CommonAction {
    static String password = "jwylzdoctoryw(*&^%";

    //entity判空赋空数组
    public String entityParseArray(String resultEntityStr){
        if(StringUtils.isBlank(resultEntityStr)){
            return "[]";
        }else if(resultEntityStr.charAt(0) == '{'){
            return "[" + resultEntityStr + "]";
        }
        return resultEntityStr;
    }
    //entity判空赋空对象
    public String entityParseObject(String resultEntityStr){
        if(StringUtils.isBlank(resultEntityStr)){
            return "{}";
        }else if(resultEntityStr.charAt(0) == '['){
            return "{" + resultEntityStr +"}";
        }
        return resultEntityStr;
    }
    /**
     * 调阅居民健康档案
     *
     * @author lyy
     * @return
     */
    public String findElectronicArchivesDetail() {
        try {
            AssessReadJwVo vo = (AssessReadJwVo) getJson(AssessReadJwVo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if(user != null){
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                }else{
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser != null){
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vo.getAreaCode().substring(0, 4));
                if (value != null) {
                    vo.setUrlType(value.getCodeTitle());
                }
                String str = this.getSysDao().getSecurityCardAsyncBean().findElectronicArchivesDetail(vo,requestUserId,requestUserName,userType);
                this.getJsons().setResult(str);
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "json";
    }
    /**
     * 调阅儿童体检表
     *
     * @author lyy
     * @return
     */
    public String findChildHealthRecordsDetail() {
        try {
                AssessReadJwVo vo = (AssessReadJwVo) getJson(AssessReadJwVo.class);
                if(vo==null){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
                }else {
                    String requestUserId = null;
                    String requestUserName = null;
                    String userType = null;
                    AppPatientUser user = this.getAppPatientUser();
                    if (user != null) {
                        userType = "1";
                        requestUserId = user.getId();
                        requestUserName = user.getPatientName();
                    } else {
                        AppDrUser drUser = this.getAppDrUser();
                        if (drUser != null) {
                            userType = "2";
                            requestUserId = drUser.getId();
                            requestUserName = drUser.getDrName();
                        }
                    }
                    vo.setAct("findChildHealthRecordsDetail");
                    CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vo.getAreaCode().substring(0, 4));
                    if (value != null) {
                        vo.setUrlType(value.getCodeTitle());
                    }
                    String resultEntityStr = this.getSysDao().getSecurityCardAsyncBean().findExecuteDetail(vo, requestUserId, requestUserName, userType);
                    net.sf.json.JSONObject jsonAll = net.sf.json.JSONObject.fromObject(resultEntityStr);
                    if (jsonAll.get("success") != null && jsonAll.get("entity") != null) {
                        if (jsonAll.get("success").toString().equals("true")) {
                            List<ChildHealthRecordsDTO> list = JSONArray.parseArray(jsonAll.get("entity").toString(), ChildHealthRecordsDTO.class);
                            this.getJsonLayui().setData(list);
                        }
                    }
                 }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "jsonLayui";
    }
    /**
     * 调阅儿童随访记录
     *
     * @author lyy
     * @return
     */
    public String findChildFollowUpRecordsDetail() {
        try {
            AssessReadJwVo vo = (AssessReadJwVo) getJson(AssessReadJwVo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if (user != null) {
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                } else {
                    AppDrUser drUser = this.getAppDrUser();
                    if (drUser != null) {
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                vo.setAct("findChildFollowUpRecordsDetail");
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vo.getAreaCode().substring(0, 4));
                if (value != null) {
                    vo.setUrlType(value.getCodeTitle());
                }
                String resultEntityStr = this.getSysDao().getSecurityCardAsyncBean().findExecuteDetail(vo, requestUserId, requestUserName, userType);
                net.sf.json.JSONObject jsonAll = net.sf.json.JSONObject.fromObject(resultEntityStr);
                if (jsonAll.get("success") != null && jsonAll.get("entity") != null) {
                    if (jsonAll.get("success").toString().equals("true")) {
                        ChildHealthRecordsDTO dto = JSONObject.parseObject(jsonAll.get("entity").toString(), ChildHealthRecordsDTO.class);
                        this.getJsonLayui().setData(dto.getFyEtfsjlList());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "jsonLayui";
    }
    /**
     * 调阅体检表
     *
     * @author lyy
     * @return
     */
    public String findHealthfileDetail() {
        try {
            AssessReadJwVo vo = (AssessReadJwVo) getJson(AssessReadJwVo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if (user != null) {
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                } else {
                    AppDrUser drUser = this.getAppDrUser();
                    if (drUser != null) {
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                vo.setType("1");// 1:健康人群 2:老年人 3:慢性病
                vo.setAct("findHealthfileDetail");
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vo.getAreaCode().substring(0, 4));
                if (value != null) {
                    vo.setUrlType(value.getCodeTitle());
                }
                String resultEntityStr = this.getSysDao().getSecurityCardAsyncBean().findExecuteDetail(vo, requestUserId, requestUserName, userType);
                net.sf.json.JSONObject jsonAll = net.sf.json.JSONObject.fromObject(resultEntityStr);
                if (jsonAll.get("success") != null && jsonAll.get("entity") != null) {
                    if (jsonAll.get("success").toString().equals("true")) {
                        List<TjbgAllInfoDTO2> list = JSONArray.parseArray(jsonAll.get("entity").toString(), TjbgAllInfoDTO2.class);
                        this.getJsonLayui().setData(list);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "jsonLayui";
    }
    /**
     * 调阅慢性病高血压随访记录
     *
     * @author lyy
     * @return
     */
    public String findChronicDiseaseFollowUpRecordsDetail() {
        try {
            AssessReadJwVo vo = (AssessReadJwVo) getJson(AssessReadJwVo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if (user != null) {
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                } else {
                    AppDrUser drUser = this.getAppDrUser();
                    if (drUser != null) {
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                vo.setType("1");// 1:健康人群 2:老年人 3:慢性病
                vo.setAct("findChronicDiseaseFollowUpRecordsDetail");
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vo.getAreaCode().substring(0, 4));
                if (value != null) {
                    vo.setUrlType(value.getCodeTitle());
                }
                String resultEntityStr = this.getSysDao().getSecurityCardAsyncBean().findExecuteDetail(vo, requestUserId, requestUserName, userType);
                net.sf.json.JSONObject jsonAll = net.sf.json.JSONObject.fromObject(resultEntityStr);
                if (jsonAll.get("success") != null && jsonAll.get("entity") != null) {
                    if (jsonAll.get("success").toString().equals("true")) {
                        ChronicDiseaseFollowUpRecordsDTO dto = JSONObject.parseObject(jsonAll.get("entity").toString(), ChronicDiseaseFollowUpRecordsDTO.class);
                        this.getJsonLayui().setData(dto.getMxjbsfDTOList());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "jsonLayui";
    }
    /**
     * 调阅慢性病糖尿病随访记录
     *
     * @author lyy
     * @return
     */
    public String findChronicDiseaseFollowUpRecordsDetail2() {
        try {
            AssessReadJwVo vo = (AssessReadJwVo) getJson(AssessReadJwVo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if (user != null) {
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                } else {
                    AppDrUser drUser = this.getAppDrUser();
                    if (drUser != null) {
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                vo.setType("3");// 1:健康人群 2:老年人 3:慢性病
                vo.setAct("findChronicDiseaseFollowUpRecordsDetail");
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vo.getAreaCode().substring(0, 4));
                if (value != null) {
                    vo.setUrlType(value.getCodeTitle());
                }
                String resultEntityStr = this.getSysDao().getSecurityCardAsyncBean().findExecuteDetail(vo, requestUserId, requestUserName, userType);
                net.sf.json.JSONObject jsonAll = net.sf.json.JSONObject.fromObject(resultEntityStr);
                if (jsonAll.get("success") != null && jsonAll.get("entity") != null) {
                    if (jsonAll.get("success").toString().equals("true")) {
                        ChronicDiseaseFollowUpRecordsDTO dto = JSONObject.parseObject(jsonAll.get("entity").toString(), ChronicDiseaseFollowUpRecordsDTO.class);
                        this.getJsonLayui().setData(dto.getMxjbsfDTOList());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "jsonLayui";
    }
    /**
     * 获取高血压人员信息
     *
     * @author lyy
     * @return
     */
    public String findGXYPeopleInfo() {
        try {
            AssessReadJwVo vo = (AssessReadJwVo) getJson(AssessReadJwVo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if (user != null) {
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                } else {
                    AppDrUser drUser = this.getAppDrUser();
                    if (drUser != null) {
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                vo.setAct("findGXYPeopleInfo");
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vo.getAreaCode().substring(0, 4));
                if (value != null) {
                    vo.setUrlType(value.getCodeTitle());
                }
                String resultEntityStr = this.getSysDao().getSecurityCardAsyncBean().findExecuteDetail(vo, requestUserId, requestUserName, userType);
                this.getJsons().setResult(resultEntityStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "json";
    }
    /**
     * 获取糖尿病人员信息
     *
     * @author lyy
     * @return
     */
    public String findTNBPeopleInfo() {
        try {
            AssessReadJwVo vo = (AssessReadJwVo) getJson(AssessReadJwVo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if (user != null) {
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                } else {
                    AppDrUser drUser = this.getAppDrUser();
                    if (drUser != null) {
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                vo.setAct("findTNBPeopleInfo");
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vo.getAreaCode().substring(0, 4));
                if (value != null) {
                    vo.setUrlType(value.getCodeTitle());
                }
                String resultEntityStr = this.getSysDao().getSecurityCardAsyncBean().findExecuteDetail(vo, requestUserId, requestUserName, userType);
                this.getJsons().setResult(resultEntityStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "json";
    }
    /**
     * 调阅插卡记录
     *
     * @author lyy
     * @return
     */
    public String findVisitRecordsDetail() {
        try {
            AssessReadJwVo vo = (AssessReadJwVo) getJson(AssessReadJwVo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if (user != null) {
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                } else {
                    AppDrUser drUser = this.getAppDrUser();
                    if (drUser != null) {
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                vo.setAct("findVisitRecordsDetail");
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vo.getAreaCode().substring(0, 4));
                if (value != null) {
                    vo.setUrlType(value.getCodeTitle());
                }
                String resultEntityStr = this.getSysDao().getSecurityCardAsyncBean().findExecuteDetail(vo, requestUserId, requestUserName, userType);
                net.sf.json.JSONObject jsonAll = net.sf.json.JSONObject.fromObject(resultEntityStr);
                if (jsonAll.get("success") != null && jsonAll.get("entity") != null) {
                    if (jsonAll.get("success").toString().equals("true")) {
                        List<JxkhRecordDTO> list = JSONArray.parseArray(jsonAll.get("entity").toString(), JxkhRecordDTO.class);
                        this.getJsonLayui().setData(list);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "jsonLayui";
    }
    /**
     * 孕产妇随访记录
     *
     * @author lyy
     * @return
     */
    public String findGravidaFollowUpRecordsDetail() {
        try {
            AssessReadJwVo vo = (AssessReadJwVo) getJson(AssessReadJwVo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if (user != null) {
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                } else {
                    AppDrUser drUser = this.getAppDrUser();
                    if (drUser != null) {
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                vo.setAct("findGravidaFollowUpRecordsDetail");
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vo.getAreaCode().substring(0, 4));
                if (value != null) {
                    vo.setUrlType(value.getCodeTitle());
                }
                String resultEntityStr = this.getSysDao().getSecurityCardAsyncBean().findExecuteDetail(vo, requestUserId, requestUserName, userType);
                net.sf.json.JSONObject jsonAll = net.sf.json.JSONObject.fromObject(resultEntityStr);
                if (jsonAll.get("success") != null && jsonAll.get("entity") != null) {
                    if (jsonAll.get("success").toString().equals("true")) {
                        GravidaFollowUpRecordsDTO dto = JSONObject.parseObject(jsonAll.get("entity").toString(), GravidaFollowUpRecordsDTO.class);
                        this.getJsons().setVo(dto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "json";
    }
    /**
     * 孕产妇产检记录
     *
     * @author lyy
     * @return
     */
    public String findAntenatalRecordsDetail() {
        try {
            AssessReadJwVo vo = (AssessReadJwVo) getJson(AssessReadJwVo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                String requestUserId = null;
                String requestUserName = null;
                String userType = null;
                AppPatientUser user = this.getAppPatientUser();
                if (user != null) {
                    userType = "1";
                    requestUserId = user.getId();
                    requestUserName = user.getPatientName();
                } else {
                    AppDrUser drUser = this.getAppDrUser();
                    if (drUser != null) {
                        userType = "2";
                        requestUserId = drUser.getId();
                        requestUserName = drUser.getDrName();
                    }
                }
                vo.setAct("findAntenatalRecordsDetail");
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, vo.getAreaCode().substring(0, 4));
                if (value != null) {
                    vo.setUrlType(value.getCodeTitle());
                }
                String resultEntityStr = this.getSysDao().getSecurityCardAsyncBean().findExecuteDetail(vo, requestUserId, requestUserName, userType);
                net.sf.json.JSONObject jsonAll = net.sf.json.JSONObject.fromObject(resultEntityStr);
                if (jsonAll.get("success") != null && jsonAll.get("entity") != null) {
                    if (jsonAll.get("success").toString().equals("true")) {
                        AntenatalRecordsDTO dto = JSONObject.parseObject(jsonAll.get("entity").toString(), AntenatalRecordsDTO.class);
                        this.getJsons().setVo(dto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "json";
    }

}
