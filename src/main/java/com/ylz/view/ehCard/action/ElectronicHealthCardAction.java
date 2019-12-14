package com.ylz.view.ehCard.action;

import com.alibaba.fastjson.JSONObject;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.assessment.vo.view.AntenatalRecordsDTO;
import com.ylz.bizDo.jtapp.ehcVo.AppEHCvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.util.JsonUtil;
import com.ylzinfo.ehc.portal.sdk.bean.ResponseParams;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * 电子健康卡
 * Created by zzl on 2019/1/14.
 */
@SuppressWarnings("all")
@Action(
        value="ehc",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class ElectronicHealthCardAction extends CommonAction {
    /**
     * 获取电子健康卡领卡url
     * @return
     */
    public String getEhcUrl(){
        try{
            AppEHCvo qvo = (AppEHCvo)this.getAppJson(AppEHCvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                Map<String,Object> map = new HashMap<>();
                Map<String, Object> reqParam = new HashMap<String, Object>();
                reqParam.put("extUserId", qvo.getExtUserId());
                reqParam.put("mobilePhone", qvo.getMobilePhone());
                reqParam.put("userName", qvo.getUserName());
                reqParam.put("idNo", qvo.getIdNo());
                String str = sysDao.getSecurityCardAsyncBean().getEhcUrl(reqParam);
                if(str != null){
                    map.put("url",str);
                    this.getAjson().setMap(map);
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("获取领卡地址失败");
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
     * 获取电子健康卡发卡状态和卡号
     * @return
     */
    public String getEhcCarStateAndNo(){
        try{
            AppEHCvo qvo = (AppEHCvo)this.getAppJson(AppEHCvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getExtUserId())){
                    this.getAjson().setMsg("外部用户编号不能为空");
                    this.getAjson().setMsgCode("900");
                }else{
                    ResponseParams vo = sysDao.getSecurityCardAsyncBean().getEhcCarStateAndNo(qvo.getExtUserId());
                    if(vo != null){
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
     * 获取电子健康卡居民信息
     * @return
     */
    public String getEhcPeople(){
        try{
            AppEHCvo qvo = (AppEHCvo)this.getAppJson(AppEHCvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getExtUserId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("外部用户编号不能为空");
                }else{
                    ResponseParams vo = sysDao.getSecurityCardAsyncBean().getEhcPeople(qvo.getExtUserId());
                    if(vo != null){
                        AppPatientUser patientUser = sysDao.getAppPatientUserDao().findByUserId(qvo.getExtUserId());
                        if(patientUser != null){
                            if(vo.getParam() != null){
                                String json = JsonUtil.toJson(vo.getParam());
                                net.sf.json.JSONObject jsonAll = net.sf.json.JSONObject.fromObject(json);
                                if (jsonAll.get("cardNo") != null && jsonAll.get("ehcId") != null) {
                                    patientUser.setPatientEHCId(jsonAll.get("ehcId").toString());
                                    patientUser.setPatientEHCNo(jsonAll.get("cardNo").toString());
                                    sysDao.getServiceDo().modify(patientUser);
                                }
                            }
                        }
                        this.getAjson().setVo(vo);
                        this.getAjson().setMsgCode("800");
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
