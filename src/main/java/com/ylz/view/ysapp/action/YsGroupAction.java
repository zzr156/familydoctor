package com.ylz.view.ysapp.action;


import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthEntity.AppEnterpatientEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppGroupEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppPatientSignAndJdEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.PatientInfo;
import com.ylz.bizDo.jtapp.drVo.AppGroupVo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientChangeInfoEntity;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
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
 * 分组管理action.
 */
@SuppressWarnings("all")
@Action(
        value="ysGroup",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsGroupAction extends CommonAction {




    /**
     * 系统默认分组
     * @return
     */
    public String appFindGroup(){
                try {
                        AppGroupVo vo = (AppGroupVo)getAppJson(AppGroupVo.class);
                        if(vo != null){
                            List<AppGroupEntity> list = sysDao.getCodeDao().findAppGroup(vo);
                            this.getAjson().setRows(list);
                            this.getAjson().setMsgCode("800");
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
     *通过患者id查询用户信息和签约信息
     * @return
     */
    public String appFindPatientInfo(){
        try {
            AppCommQvo vo = (AppCommQvo)getAppJson(AppCommQvo.class);
            if(vo != null){
                if(StringUtils.isNotBlank(vo.getPatientDrType())){
                    if(vo.getPatientDrType().equals( CommonShortType.HUANGZHE.getValue())){
                        AppPatientUser info = sysDao.getAppPatientUserDao().findByUserId(vo.getPatientId());
                        if(info!=null){
                            String securtyState = this.getSysDao().getAppSecurtySettingDao().findByUserTypeId(vo.getPatientId(),vo.getPatientDrType());
                            Map<String,Object> map = new HashMap<String,Object>();
                            map.put("securtyState",securtyState);
                            map.put("jmdnh",info.getPatientjmda());
                            map.put("orgId",null);
                            this.getAjson().setMsgCode("800");
                            this.getAjson().setVo(info);
                            this.getAjson().setMap(map);
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("未找到用户信息");
                        }
                    }else if(vo.getPatientDrType().equals( CommonShortType.YISHENG.getValue())){
                        PatientInfo info = sysDao.getAppPatientUserDao().findUserInfo(vo.getPatientId(),"0",vo.getSignFormId());
                        if(info!=null){
                            String securtyState = this.getSysDao().getAppSecurtySettingDao().findByUserTypeId(vo.getPatientId(),vo.getPatientDrType());
                            Map<String,Object> map = new HashMap<String,Object>();
                            map.put("jmdnh",null);
                            map.put("orgId",null);
                            AppDrUser drUser = this.getAppDrUser();
                            if(drUser != null){
                                if(StringUtils.isNotBlank(drUser.getDrHospId())){
                                    AppHospDept hospDept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                    if(hospDept != null){
                                        HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                                        qqvo.setIdno(info.getIdno());
                                        qqvo.setType("2");
                                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, hospDept.getHospAreaCode().substring(0,4));
                                        if(value!=null){
                                            qqvo.setUrlType(value.getCodeTitle());
                                        }
                                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,drUser.getId(),drUser.getDrName(), DrPatientType.DR.getValue());
                                        if(StringUtils.isNotBlank(str)){
                                            T_dwellerfile file = sysDao.getSecurityCardAsyncBean().analysisFileOne(str, qqvo.getUrlType());
                                            if(file != null){
                                                map.put("jmdnh",file.getJmdah());
                                                String result = "";
                                                if (AddressType.FZ.getValue().equals(value.getCodeTitle())) {

                                                } else if (AddressType.QZ.getValue().equals(value.getCodeTitle())) {
                                                    result = "qz_";
                                                } else if (AddressType.ZZ.getValue().equals(value.getCodeTitle())) {
                                                    result = "zz_";
                                                } else if (AddressType.PT.getValue().equals(value.getCodeTitle())) {
                                                    result = "pt_";
                                                } else if (AddressType.NP.getValue().equals(value.getCodeTitle())) {
                                                    result = "np_";
                                                } else if (AddressType.SM.getValue().equals(value.getCodeTitle())) {
                                                    result = "sm_";
                                                } else if (AddressType.LY.getValue().equals(value.getCodeTitle())) {
                                                    result = "ly_";
                                                } else if (AddressType.ND.getValue().equals(value.getCodeTitle())) {
                                                    result = "nd_";
                                                } else if (AddressType.XM.getValue().equals(value.getCodeTitle())) {
                                                    result = "xm_";
                                                } else if (AddressType.PINGT.getValue().equals(value.getCodeTitle())) {
                                                    result = "pg_";
                                                }
                                                map.put("orgId",result+file.getSqh());
                                            }

                                            /*JSONObject jsonall = JSONObject.fromObject(str);
                                            if(jsonall.get("vo")!=null && "800".equals(jsonall.get("msgCode"))) {
                                                if (jsonall.get("entity") != null) {
                                                    if(!"null".equals(jsonall.get("entity").toString())){
                                                        JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                                        if ("true".equals(entity.get("success").toString())) {
                                                            T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                                            if (entitys != null) {
//                                                                map.put("jtdnh",entitys.getJtdah());
                                                                map.put("jmdnh",entitys.getJmdah());
                                                                String result = "";
                                                                if (AddressType.FZ.getValue().equals(value.getCodeTitle())) {

                                                                } else if (AddressType.QZ.getValue().equals(value.getCodeTitle())) {
                                                                    result = "qz_";
                                                                } else if (AddressType.ZZ.getValue().equals(value.getCodeTitle())) {
                                                                    result = "zz_";
                                                                } else if (AddressType.PT.getValue().equals(value.getCodeTitle())) {
                                                                    result = "pt_";
                                                                } else if (AddressType.NP.getValue().equals(value.getCodeTitle())) {
                                                                    result = "np_";
                                                                } else if (AddressType.SM.getValue().equals(value.getCodeTitle())) {
                                                                    result = "sm_";
                                                                } else if (AddressType.LY.getValue().equals(value.getCodeTitle())) {
                                                                    result = "ly_";
                                                                } else if (AddressType.ND.getValue().equals(value.getCodeTitle())) {
                                                                    result = "nd_";
                                                                } else if (AddressType.XM.getValue().equals(value.getCodeTitle())) {
                                                                    result = "xm_";
                                                                } else if (AddressType.PINGT.getValue().equals(value.getCodeTitle())) {
                                                                    result = "pg_";
                                                                }
                                                                map.put("orgId",result+entitys.getSqh());
                                                            }
                                                        }
                                                    }
                                                }
                                            }*/
                                        }
                                    }
                                }
                            }
                            map.put("securtyState",securtyState);
                            this.getAjson().setMsgCode("800");
                            this.getAjson().setVo(info);
                            this.getAjson().setMap(map);
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("未找到用户信息");
                        }
                    }else{
                        PatientInfo info = sysDao.getAppPatientUserDao().findUserInfo(vo.getPatientId(),"1",null);
                        if(info!=null){
                            String securtyState = this.getSysDao().getAppSecurtySettingDao().findByUserTypeId(vo.getPatientId(),vo.getPatientDrType());
                            Map<String,Object> map = new HashMap<String,Object>();
                            map.put("securtyState",securtyState);
                            map.put("jmdnh",null);
                            map.put("orgId",null);
                            AppDrUser drUser = this.getAppDrUser();
                            if(drUser != null){
                                if(StringUtils.isNotBlank(drUser.getDrHospId())){
                                    AppHospDept hospDept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                    if(hospDept != null){
                                        HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                                        qqvo.setIdno(info.getIdno());
                                        qqvo.setType("2");
                                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, hospDept.getHospAreaCode().substring(0,4));
                                        if(value!=null){
                                            qqvo.setUrlType(value.getCodeTitle());
                                        }
                                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,drUser.getId(),drUser.getDrName(), DrPatientType.DR.getValue());
                                        if(StringUtils.isNotBlank(str)){
                                            T_dwellerfile file = sysDao.getSecurityCardAsyncBean().analysisFileOne(str, qqvo.getUrlType());
                                            if(file != null){
                                                map.put("jmdnh",file.getJmdah());
                                                String result = "";
                                                if (AddressType.FZ.getValue().equals(value.getCodeTitle())) {

                                                } else if (AddressType.QZ.getValue().equals(value.getCodeTitle())) {
                                                    result = "qz_";
                                                } else if (AddressType.ZZ.getValue().equals(value.getCodeTitle())) {
                                                    result = "zz_";
                                                } else if (AddressType.PT.getValue().equals(value.getCodeTitle())) {
                                                    result = "pt_";
                                                } else if (AddressType.NP.getValue().equals(value.getCodeTitle())) {
                                                    result = "np_";
                                                } else if (AddressType.SM.getValue().equals(value.getCodeTitle())) {
                                                    result = "sm_";
                                                } else if (AddressType.LY.getValue().equals(value.getCodeTitle())) {
                                                    result = "ly_";
                                                } else if (AddressType.ND.getValue().equals(value.getCodeTitle())) {
                                                    result = "nd_";
                                                } else if (AddressType.XM.getValue().equals(value.getCodeTitle())) {
                                                    result = "xm_";
                                                } else if (AddressType.PINGT.getValue().equals(value.getCodeTitle())) {
                                                    result = "pg_";
                                                }
                                                map.put("orgId",result+file.getSqh());
                                            }
                                            /*JSONObject jsonall = JSONObject.fromObject(str);
                                            if(jsonall.get("vo")!=null && "800".equals(jsonall.get("msgCode"))) {
                                                if (jsonall.get("entity") != null) {
                                                    if(!"null".equals(jsonall.get("entity").toString())){
                                                        JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                                        if ("true".equals(entity.get("success").toString())) {
                                                            T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                                            if (entitys != null) {
//                                                                map.put("jtdnh",entitys.getJtdah());
                                                                map.put("jmdnh",entitys.getJmdah());
                                                                String result = "";
                                                                if (AddressType.FZ.getValue().equals(value.getCodeTitle())) {

                                                                } else if (AddressType.QZ.getValue().equals(value.getCodeTitle())) {
                                                                    result = "qz_";
                                                                } else if (AddressType.ZZ.getValue().equals(value.getCodeTitle())) {
                                                                    result = "zz_";
                                                                } else if (AddressType.PT.getValue().equals(value.getCodeTitle())) {
                                                                    result = "pt_";
                                                                } else if (AddressType.NP.getValue().equals(value.getCodeTitle())) {
                                                                    result = "np_";
                                                                } else if (AddressType.SM.getValue().equals(value.getCodeTitle())) {
                                                                    result = "sm_";
                                                                } else if (AddressType.LY.getValue().equals(value.getCodeTitle())) {
                                                                    result = "ly_";
                                                                } else if (AddressType.ND.getValue().equals(value.getCodeTitle())) {
                                                                    result = "nd_";
                                                                } else if (AddressType.XM.getValue().equals(value.getCodeTitle())) {
                                                                    result = "xm_";
                                                                } else if (AddressType.PINGT.getValue().equals(value.getCodeTitle())) {
                                                                    result = "pg_";
                                                                }
                                                                map.put("orgId",result+entitys.getSqh());
                                                            }
                                                        }
                                                    }
                                                }
                                            }*/
                                        }
                                    }
                                }
                            }
                            this.getAjson().setMsgCode("800");
                            this.getAjson().setVo(info);
                            this.getAjson().setMap(map);
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("未找到用户信息");
                        }
                    }
                }else{
                    PatientInfo info = sysDao.getAppPatientUserDao().findUserInfo(vo.getPatientId(),"0",null);
                    if(info!=null){
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setVo(info);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("未找到用户信息");
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
     * 根据身份证查询患者信息和签约信息
     * @return
     */
    public String findPeopleInformation(){
        try{
            AppCommQvo vo = (AppCommQvo)getAppJson(AppCommQvo.class);
            if(vo != null){
                PatientInfo info = sysDao.getAppPatientUserDao().findUserInfooo(vo.getPatientIdno(),vo.getDrId());
                if(info!=null){
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setVo(info);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("未找相关信息");
                }
            }else{
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
     * 根据居民身份证查询签约状态和建档状态
     * @return
     */
    public String findSignAndJd(){
        try{
            Map<String,Object> map = new HashMap<>();
            map.put("signState",null);
            map.put("jsState",null);
            AppCommQvo vo = (AppCommQvo)getAppJson(AppCommQvo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                qqvo.setIdno(vo.getPatientIdno());
                qqvo.setType("2");
                AppDrUser drUser = this.getAppDrUser();
                if(drUser == null){
                    this.getAjson().setMsg("查无医生信息");
                    this.getAjson().setMsgCode("900");
                }else{
                    AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(vo.getPatientIdno());
                    if(user!=null){
                        this.getAjson().setVo(user);
                        AppSignForm form = sysDao.getAppSignformDao().findSignFormByUser(user.getId());
                        if(form!=null){
                            map.put("signState",form.getSignState());
                        }
                    }
                    AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                    if(dept != null){
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, dept.getHospAreaCode().substring(0,4));
                        if(value!=null){
                            qqvo.setUrlType(value.getCodeTitle());
                        }
                        String requestUserId = null;
                        String requestUserName = null;
                        String userType = null;
                        if(user != null ){
                            userType = "2";
                            requestUserId = drUser.getId();
                            requestUserName = drUser.getDrName();
                        }
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,requestUserId,requestUserName,userType);
                        if(StringUtils.isNotBlank(str)) {
                            JSONObject jsonall = JSONObject.fromObject(str);
                            if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")) {
                                if (jsonall.get("entity") != null) {
                                    JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                    if(AddressType.ND.getValue().equals(qqvo.getUrlType()) || AddressType.PINGT.getValue().equals(qqvo.getUrlType())){//宁德
                                        if(entity != null && "800".equals(entity.get("msgCode").toString())){
                                            List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(),new TypeToken<List<AppEnterpatientEntity>>() {}.getType());
                                            if(list != null && list.size()>0){
                                                if(StringUtils.isNotBlank(list.get(0).getDf_id())){
                                                    map.put("jsState",list.get(0).getDf_id());

                                                }
                                            }
                                        }
                                    }else{
//                                        System.out.println(JsonUtil.toJson(entity));
                                        if(entity != null && entity.get("success") != null){
                                            if ("true".equals(entity.get("success").toString())) {
                                                T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                                if (entitys != null) {
                                                    if (StringUtils.isNotBlank(entitys.getJmdah())) {
                                                        map.put("jsState",entitys.getJmdah());
                                                        String result = "";
                                                        if (AddressType.FZ.getValue().equals(value.getCodeTitle())) {

                                                        } else if (AddressType.QZ.getValue().equals(value.getCodeTitle())) {
                                                            result = "qz_";
                                                        } else if (AddressType.ZZ.getValue().equals(value.getCodeTitle())) {
                                                            result = "zz_";
                                                        } else if (AddressType.PT.getValue().equals(value.getCodeTitle())) {
                                                            result = "pt_";
                                                        } else if (AddressType.NP.getValue().equals(value.getCodeTitle())) {
                                                            result = "np_";
                                                        } else if (AddressType.SM.getValue().equals(value.getCodeTitle())) {
                                                            result = "sm_";
                                                        } else if (AddressType.LY.getValue().equals(value.getCodeTitle())) {
                                                            result = "ly_";
                                                        } else if (AddressType.ND.getValue().equals(value.getCodeTitle())) {
                                                            result = "nd_";
                                                        } else if (AddressType.XM.getValue().equals(value.getCodeTitle())) {
                                                            result = "xm_";
                                                        } else if (AddressType.PINGT.getValue().equals(value.getCodeTitle())) {
                                                            result = "pg_";
                                                        }
                                                        map.put("orgId",result+entitys.getSqh());
                                                    }
                                                }
                                            }
                                        }
                                    }


                                }
                            }
                        }
                    }
                }
            }
            this.getAjson().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 根据姓名、性别、出生日期、身份证搜索居民是否签约和是否建档
     * @return
     */
    public String findSignAndJdByQvo(){
        try{
            AppCommQvo vo = (AppCommQvo)getAppJson(AppCommQvo.class);
            if(vo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<Map<String,Object>> listMap = new ArrayList<>();
                //有身份证查询单人签约状态和建档状态
                if(StringUtils.isNotBlank(vo.getPatientIdno())){
                    Map<String,Object> map = new HashMap<>();
                    map.put("name",vo.getPatientName());//居民姓名
                    map.put("idno",vo.getPatientIdno());//居民身份证
                    map.put("signState",null);//居民签约状态
                    map.put("sex",vo.getSex());//居民性别
                    map.put("birthDay",vo.getBirthday());//居民出生日期
                    map.put("signId",null);//居民签约单主键
                    map.put("jmdah",null);//居民档案号
                    HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                    AppDrUser drUser = this.getAppDrUser();
                    String requestUserId = null;
                    String requestUserName = null;
                    String userType = null;
                    qqvo.setType("2");
                    if(drUser == null){
                        this.getAjson().setMsg("查无医生信息");
                        this.getAjson().setMsgCode("900");
                    }else {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
                        if (dept != null) {
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, dept.getHospAreaCode().substring(0, 4));
                            if (value != null) {
                                qqvo.setUrlType(value.getCodeTitle());
                            }
                            requestUserId = drUser.getId();
                            requestUserName = drUser.getDrName();
                            userType = null;
                        }
                        qqvo.setIdno(vo.getPatientIdno());
                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo, requestUserId, requestUserName, userType);
                        if (StringUtils.isNotBlank(str)) {
                            JSONObject jsonall = JSONObject.fromObject(str);
                            if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")) {
                                if (jsonall.get("entity") != null) {
                                    JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                    if(AddressType.ND.getValue().equals(qqvo.getUrlType()) || AddressType.PINGT.getValue().equals(qqvo.getUrlType())){//宁德
                                        if(entity != null && "800".equals(entity.get("msgCode").toString())){
                                            List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(),new TypeToken<List<AppEnterpatientEntity>>() {}.getType());
                                            if(list != null && list.size()>0){
                                                if(StringUtils.isNotBlank(list.get(0).getDf_id())){
                                                    map.put("jmdah", list.get(0).getDf_id());
                                                }
                                                if(StringUtils.isNotBlank(list.get(0).getName())){
                                                    map.put("name",list.get(0).getName());
                                                }
                                                if(StringUtils.isNotBlank(list.get(0).getSex())){
                                                    map.put("sex",list.get(0).getSex());
                                                }
                                                if(StringUtils.isNotBlank(list.get(0).getBirthday())){
                                                    map.put("birthDay", list.get(0).getBirthday());
                                                }
                                            }
                                        }
                                    }else{
                                        if ("true".equals(entity.get("success").toString())) {
                                            T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                            if (entitys != null) {
                                                if (StringUtils.isNotBlank(entitys.getJmdah())) {
                                                    map.put("jmdah", entitys.getJmdah());
                                                    if(StringUtils.isNotBlank(entitys.getJmxm())){
                                                        map.put("name",entitys.getJmxm());
                                                    }
                                                    map.put("sex",entitys.getXb());
                                                    if(StringUtils.isNotBlank(entitys.getCsrq())){
                                                        map.put("birthDay", ExtendDate.getYMD(ExtendDate.getYYYYMMD(entitys.getCsrq())));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //判断签约状态
                        AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(vo.getPatientIdno());
                        if(user != null){
                            if(StringUtils.isNotBlank(user.getPatientName())){
                                map.put("name",user.getPatientName());
                            }
                            AppSignForm form = sysDao.getAppSignformDao().findSignFormByState(user.getId());
                            if(form != null){
                                map.put("signState",form.getSignState());
                                map.put("signId",form.getId());
                            }
                        }
                        listMap.add(map);
                    }
                }else{
                    HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                    AppDrUser drUser = this.getAppDrUser();
                    String requestUserId = null;
                    String requestUserName = null;
                    String userType = null;
                    qqvo.setType("2");
                    if(drUser == null){
                        this.getAjson().setMsg("查无医生信息");
                        this.getAjson().setMsgCode("900");
                    }else {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
                        if (dept != null) {
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, dept.getHospAreaCode().substring(0, 4));
                            if (value != null) {
                                qqvo.setUrlType(value.getCodeTitle());
                            }
                            requestUserId = drUser.getId();
                            requestUserName = drUser.getDrName();
                            userType = null;
                        }
                        //身份证为空，模糊查询多个人
                        List<AppPatientSignAndJdEntity> listP = sysDao.getAppPatientUserDao().findSignAndJdByQvo(vo);
                        if(listP != null && listP.size()>0){
                            for (AppPatientSignAndJdEntity ss:listP){
                                Map<String,Object> map = new HashMap<>();
                                map.put("name",ss.getName());//居民姓名
                                map.put("idno",ss.getIdno());//居民身份证
                                map.put("sex",ss.getSex());//居民性别
                                map.put("birthDay",ss.getBirthDay());//居民出生日期
                                map.put("signState",null);//居民签约状态
                                map.put("signId",null);//居民签约单主键
                                map.put("jmdah",null);//居民档案号
                                qqvo.setIdno(ss.getIdno());
                                String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo, requestUserId, requestUserName, userType);
                                if (StringUtils.isNotBlank(str)) {
                                    JSONObject jsonall = JSONObject.fromObject(str);
                                    if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")) {
                                        if (jsonall.get("entity") != null) {
                                            JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                            if ("true".equals(entity.get("success").toString())) {
                                                T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(), T_dwellerfile.class);
                                                if (entitys != null) {
                                                    if (StringUtils.isNotBlank(entitys.getJmdah())) {
                                                        map.put("jmdah", entitys.getJmdah());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                //判断签约状态
                                AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(ss.getIdno());
                                if(user != null){
                                    if(StringUtils.isNotBlank(user.getPatientName())){
                                        map.put("name",user.getPatientName());
                                    }
                                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByState(user.getId());
                                    if(form != null){
                                        map.put("signState",form.getSignState());
                                        map.put("signId",form.getId());
                                    }
                                }
                                listMap.add(map);
                            }
                        }
                    }
                }
                this.getAjson().setRows(listMap);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 修改手机号和居民姓名 居住地进行修改并保存功能
     * @return
     */
    public String modifyPeopleXx(){
        try {
            AppPatientChangeInfoEntity vo = (AppPatientChangeInfoEntity)this.getAppJson(AppPatientChangeInfoEntity.class);
            if(vo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(vo.getId())){
                    this.getAjson().setMsg("居民主键不能为空");
                    this.getAjson().setMsgCode("900");
                }else{
                    AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,vo.getId());
                    if(user != null){
                        if(StringUtils.isNotBlank(vo.getPatientName())){
                            user.setPatientName(vo.getPatientName());
                        }
                        if(StringUtils.isNotBlank(vo.getPatientTel())){
                            boolean tel = this.getSysDao().getAppPatientUserDao().findPatientByTel(vo.getPatientTel(),UserUpHpisType.JIHUO.getValue());
                            if(tel){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("手机号已存在,不能填写已存在手机!");
                                return "ajson";
                            }
                            user.setPatientTel(vo.getPatientTel());
                        }
                        if(StringUtils.isNotBlank(vo.getPatientProvince())){
                            user.setPatientProvince(vo.getPatientProvince());
                        }
                        if(StringUtils.isNotBlank(vo.getPatientCity())){
                            user.setPatientCity(vo.getPatientCity());
                        }
                        if(StringUtils.isNotBlank(vo.getPatientArea())){
                            user.setPatientArea(vo.getPatientArea());
                        }
                        if(StringUtils.isNotBlank(vo.getPatientStreet())){
                            user.setPatientStreet(vo.getPatientStreet());
                        }
                        if(StringUtils.isNotBlank(vo.getPatientNeighborhoodCommittee())){
                            user.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
                        }
                        if(StringUtils.isNotBlank(vo.getPatientAddress())){
                            user.setPatientAddress(vo.getPatientAddress());
                        }

//                        if(StringUtils.isNotBlank(vo.getEconomicsType())){//经济类型修改
//                            //查询签约单信息
//                            AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(user.getId());
//                            if(form != null){//有签约单才能改经济类型
//                                boolean flag = false;
//                                //先判断该居民是否是建档立卡患者，是建档立卡的话，建档立卡经济类型必须有
//                                String jjType = sysDao.getAppLabelGroupDao().findJjValue(form.getId());
//                                if(jjType != null){
//                                    if(jjType.indexOf(EconomicType.JDLKPKRK.getValue())!=-1){
//                                        flag = true;
//                                    }
//                                }
//                                if(flag){//原先经济类型包含建档立卡
//                                    //1判断传入参数是不是一般人口，是的话不做修改，返回提示
//                                    if(vo.getEconomicsType().indexOf(EconomicType.YBRK.getValue())!=-1){
//                                        this.getAjson().setMsg("该居民原先为建档立卡经济类型，不可修改为一般人口");
//                                        this.getAjson().setMsgCode("900");
//                                        return "ajson";
//                                    }else{//传入参数不是一般人口，判断传参是否包含建档立卡经济类型，没有做添加，有则直接修改经济类型标签
//                                        if(vo.getEconomicsType().indexOf(EconomicType.JDLKPKRK.getValue())!=-1){
//                                            sysDao.getAppLabelGroupDao().addLabel(vo.getEconomicsType().split(";"),form.getId(),form.getSignTeamId(),form.getSignAreaCode(),LabelManageType.JJLX.getValue());
//                                        }else{
//                                            //判断传入参数最后一位是分号还是数值
//                                            if(vo.getEconomicsType().substring(vo.getEconomicsType().length()-1,vo.getEconomicsType().length()).equals(";")){//是
//                                                vo.setEconomicsType(vo.getEconomicsType()+EconomicType.JDLKPKRK.getValue());
//                                            }else{
//                                                vo.setEconomicsType(vo.getEconomicsType()+";"+EconomicType.JDLKPKRK.getValue());
//                                            }
//                                            sysDao.getAppLabelGroupDao().addLabel(vo.getEconomicsType().split(";"),form.getId(),form.getSignTeamId(),form.getSignAreaCode(),LabelManageType.JJLX.getValue());
//                                        }
//                                    }
//                                }else{//原先经济类型非建档立卡可直接修改
//                                    sysDao.getAppLabelGroupDao().addLabel(vo.getEconomicsType().split(";"),form.getId(),form.getSignTeamId(),form.getSignAreaCode(),LabelManageType.JJLX.getValue());
//                                }
//                            }
//                        }
                        sysDao.getServiceDo().modify(user);
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无居民信息");
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
     * 根据身份证查询签约状态
     * @return
     */
    public String findSignByIdno(){
        try{
            AppCommQvo qvo = (AppCommQvo)getAppJson(AppCommQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                Map<String,Object> map = new HashMap<>();
                map.put("signId",null);
                map.put("signState",null);
                map.put("hospId",null);
                map.put("hospName",null);
                AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(qvo.getPatientIdno());
                if(user != null){
                    AppSignForm form = sysDao.getAppSignformDao().findSignById(user.getId());
                    if(form != null){
                        map.put("signId",form.getId());
                        map.put("signState",form.getSignState());
                        if(StringUtils.isNotBlank(form.getSignHospId())){
                            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,form.getSignHospId());
                            if(dept != null){
                                map.put("hospId",dept.getId());
                                map.put("hospName",dept.getHospName());
                            }
                        }
                    }
                }
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }
}
