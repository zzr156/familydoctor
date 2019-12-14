package com.ylz.view.internetHospital.action;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.entity.CdAddressEntity;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthEntity.AppEnterpatientEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.ResidentHealthFiles;
import com.ylz.bizDo.jtapp.basicHealthEntity.ResidentRecordsEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.commonEntity.InternetHospAreaEntity;
import com.ylz.bizDo.jtapp.commonEntity.InternetHospOrgEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.InternetHospQvo;
import com.ylz.bizDo.jtapp.drEntity.AppServeEntity;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.JsonUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 互联网医院接口专用action
 * Created by zzl on 2019/3/18.
 */
@SuppressWarnings("all")
@Action(
        value="netHosp",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class InternetHospitalAction extends CommonAction {

    /**
     * 根据身份证判断是否签约
     * @return
     */
    public String judgeSignStateNet(){
        try{
            InternetHospQvo qvo = (InternetHospQvo)this.getAppJson(InternetHospQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getPatientIdno())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("居民身份证号不能为空！");
                }else{
                    Map<String,Object> map = new HashMap<>();
                    map.put("signState","3");//未签约状态
                    map.put("drId",null);
                    map.put("drName",null);
                    map.put("drTel",null);
                    map.put("hospId",null);
                    map.put("hospName",null);
                    map.put("signFromDate",null);
                    map.put("signToDate",null);
                    map.put("sex",null);
                    map.put("imageUrl",null);
                    //根据身份证查询该居民是否注册
                    AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(qvo.getPatientIdno());
                    if(user != null){
                        //根据居民主键查询居民签约状态
                        AppSignForm form = sysDao.getAppSignformDao().findSignById(user.getId());
                        if(form != null){
                            if(SignFormType.YQY.getValue().equals(form.getSignState()) || SignFormType.YUQY.getValue().equals(form.getSignState())){
                                map.put("signState","2");
                            }else{
                                map.put("signState",form.getSignState());
                            }
                            if(form.getSignFromDate() != null){
                                map.put("signFromDate", ExtendDate.getYMD_h_m_s(form.getSignFromDate()));
                            }
                            if(form.getSignToDate() != null){
                                map.put("signToDate",ExtendDate.getYMD_h_m_s(form.getSignToDate()));
                            }
                            //医生信息
                            AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,form.getSignDrId());
                            if(drUser != null){
                                String[] drIds = drUser.getId().split("_");
                                if(drIds.length>=2){
                                    map.put("drId",drIds[1]);
                                }else{
                                    map.put("drId",drIds[0]);
                                }
                                map.put("drName",drUser.getDrName());
                                map.put("drTel",drUser.getDrTel());
                                map.put("sex",drUser.getDrGender());
                                map.put("imageUrl",drUser.getDrImageurl());
                            }
                            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,form.getSignHospId());
                            if(dept != null){
                                String[] hospIds = dept.getId().split("_");
                                if(hospIds.length>=2){
                                    map.put("hospId",hospIds[1]);
                                }else{
                                    map.put("hospId",hospIds[0]);
                                }
                                map.put("hospName",dept.getHospName());
                            }
                        }
                        this.getAjson().setMap(map);
                        this.getAjson().setMsgCode("800");
                    }else{
                        this.getAjson().setMsg("查无该居民信息");
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

    /**
     * 根据互联网医院请求的机构id查询该机构服务包
     * @return
     */
    public String listSerPkByNetOrgId(){
        try{
            InternetHospQvo qvo = (InternetHospQvo)this.getAppJson(InternetHospQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getOrgId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("机构id不能为空！");
                }else if(StringUtils.isBlank(qvo.getAreaCode())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请求行政区划参数不能为空");
                }else{
                    String sp = "";
                    if(AddressType.FZS.getValue().equals(qvo.getAreaCode().substring(0,4))){

                    }else if(AddressType.PTS.getValue().equals(qvo.getAreaCode().substring(0,4))){
                        sp = "pt_";
                    }else if(AddressType.SMS.getValue().equals(qvo.getAreaCode().substring(0,4))){
                        sp = "sm_";
                    }else if(AddressType.QZS.getValue().equals(qvo.getAreaCode().substring(0,4))){
                        sp = "qz_";
                    }else if(AddressType.ZZS.getValue().equals(qvo.getAreaCode().substring(0,4))){
                        sp = "zz_";
                    }else if(AddressType.NPS.getValue().equals(qvo.getAreaCode().substring(0,4))){
                        sp = "np_";
                    }else if(AddressType.LYS.getValue().equals(qvo.getAreaCode().substring(0,4))){
                        sp = "ly_";
                    }else if(AddressType.NDS.getValue().equals(qvo.getAreaCode().substring(0,4))){
                        sp = "nd_";
                    }
                    if(StringUtils.isNotBlank(sp)){
                        if(qvo.getOrgId().indexOf(sp) ==-1){
                            qvo.setOrgId(sp+qvo.getOrgId());
                        }
                    }
                    //根据机构主键查询该机构信息
                    AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,qvo.getOrgId());
                    if(dept != null){
                        List<AppServeEntity> list = sysDao.getAppServeSetmealDao().findSeverMeal(dept.getId());
                        this.getAjson().setRows(list);
                        this.getAjson().setMsgCode("800");
                    }else {
                        this.getAjson().setMsg("查无该机构信息");
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

    /**
     * 根据服务包主键查询服务包详情
     * @return
     */
    public String getSerPkByNetId(){
        try{
            InternetHospQvo qvo = (InternetHospQvo)this.getAppJson(InternetHospQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getPkId())){
                    this.getAjson().setMsg("服务包主键不能为空");
                    this.getAjson().setMsgCode("900");
                }else{
                    AppServeEntity entity = sysDao.getAppServeSetmealDao().findOneSeverMealByIds(qvo.getPkId());
                    if(entity != null){
                        this.getAjson().setVo(entity);
                        this.getAjson().setMsgCode("800");
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无服务包信息");
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
     * 根据行政区划查询
     * @return
     */
    public String getOrgListByNetArea(){
        try{
            InternetHospQvo qvo = (InternetHospQvo)this.getAppJson(InternetHospQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getAreaCode())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("请求行政区划参数不能为空");
                }else{
                    List<InternetHospOrgEntity> list = sysDao.getAppHospDeptDao().findNetOrgByQvo(qvo);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setRows(list);
                    this.getAjson().setQvo(qvo);
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
     * 根据上级行政区划查询区域数据
     * @return
     */
    public String listAreaByNetUpId(){
        try{
            InternetHospQvo qvo = (InternetHospQvo)this.getAppJson(InternetHospQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<InternetHospAreaEntity> list = sysDao.getCdAddressDao().findByNetUpId(qvo.getAreaCode());
                this.getAjson().setRows(list);
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
     * 判断是否建档
     * @return
     */
    public String judgeJdState(){
        try {
            HomeServiceItemsQvo qqvo = (HomeServiceItemsQvo)this.getAppJson(HomeServiceItemsQvo.class);
            if(qqvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                Map<String,Object> map = new HashMap<>();
                if(StringUtils.isBlank(qqvo.getIdno())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("身份证参数不能为空");
                }else if(StringUtils.isBlank(qqvo.getOrgId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("机构id参数不能为空");
                }else{
                    qqvo.setType("2");
                    AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qqvo.getOrgId());
                    if(dept != null){
                        if(StringUtils.isBlank(dept.getHospAreaCode())){
                            this.getAjson().setMsg("机构行政区划为空");
                            this.getAjson().setMsgCode("900");
                            return "ajson";
                        }
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, dept.getHospAreaCode().substring(0,4));
                        if(value!=null){
                            qqvo.setUrlType(value.getCodeTitle());
                        }
                        String requestUserId = null;
                        String requestUserName = null;
                        String userType = null;
                        AppCommQvo qvoFile = new AppCommQvo();
                        AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(qqvo.getIdno());
                        if(user != null ){
                            userType = "1";
                            qvoFile.setPatientId(user.getId());
                            qvoFile.setSignType(userType);
                            qvoFile.setSignAreaCode(dept.getHospAreaCode().substring(0,4));
                            requestUserId = user.getId();
                            requestUserName = user.getPatientName();
                        }
                        List<AppHealthFile> files = sysDao.getAppHealthFileDao().findFile(qvoFile);
                        if(files != null && files.size()>0){
                            this.getAjson().setMsg("已申请建档功能,医生还未审核");
                        }else{
                            String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,requestUserId,requestUserName,userType);
                            if(AddressType.ND.getValue().equals(qqvo.getUrlType()) || AddressType.PINGT.getValue().equals(qqvo.getUrlType())){//宁德平潭开启建档签约判断居民档案
                                if(org.apache.commons.lang.StringUtils.isNotBlank(str)){
                                    System.out.println("宁德居民档案:"+str);
                                    JSONObject jsonall = JSONObject.fromObject(str);
                                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                                        if(jsonall.get("entity") != null) {
                                            JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                            if(entity != null && "800".equals(entity.get("msgCode").toString())){
                                                List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(),new TypeToken<List<AppEnterpatientEntity>>() {}.getType());
                                                if(list != null && list.size()>0){
                                                    if(org.apache.commons.lang.StringUtils.isBlank(list.get(0).getDf_id())){
                                                        this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                                        this.getAjson().setMsgCode("900");
                                                        return "ajson";
                                                    }else{
                                                        map.put("jmda",list.get(0).getDf_id());
                                                        this.getAjson().setMsgCode("800");
                                                        this.getAjson().setMap(map);
                                                    }
                                                }else{
                                                    this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                                    this.getAjson().setMsgCode("900");
                                                    return "ajson";
                                                }
                                            }else{
                                                this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                                this.getAjson().setMsgCode("900");
                                                return "ajson";
                                            }
                                        }else{
                                            this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                            this.getAjson().setMsgCode("900");
                                            return "ajson";
                                        }
                                    }else {
                                        this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                        this.getAjson().setMsgCode("900");
                                        return "ajson";
                                    }
                                }else {
                                    this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                    this.getAjson().setMsgCode("900");
                                    return "ajson";
                                }
                            }else{
                                if(org.apache.commons.lang.StringUtils.isNotBlank(str)){
                                    JSONObject jsonall = JSONObject.fromObject(str);
                                    if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                                        if(jsonall.get("entity") != null) {
                                            JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                            if ("true".equals(entity.get("success").toString())) {
                                                T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(),T_dwellerfile.class);
                                                if(entitys!=null){
                                                    if(org.apache.commons.lang.StringUtils.isNotBlank(entitys.getJmdah())){
                                                        map.put("jmda",entitys.getJmdah());
                                                        this.getAjson().setMsgCode("800");
                                                        this.getAjson().setMap(map);
                                                    }else{
                                                        this.getAjson().setMsg("您当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                                        this.getAjson().setMsgCode("901");
                                                        return "ajson";
                                                    }
                                                }else{
                                                    this.getAjson().setMsg("您当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                                    this.getAjson().setMsgCode("901");
                                                    return "ajson";
                                                }
                                            }else{
                                                this.getAjson().setMsg("您当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                                this.getAjson().setMsgCode("901");
                                                return "ajson";
                                            }
                                        }else{
                                            this.getAjson().setMsg("您当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                            this.getAjson().setMsgCode("901");
                                            return "ajson";
                                        }
                                    }else{//基卫未查到健康档案
                                        this.getAjson().setMsg("您当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                        this.getAjson().setMsgCode("901");
                                        return "ajson";
                                    }
                                }else{
                                    this.getAjson().setMsg("您当前未建立健康档案信息，请先预建档或前往就近社区建档");
                                    this.getAjson().setMsgCode("901");
                                    return "ajson";
                                }
                            }
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无机构信息");
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
}
