package com.ylz.view.hzapp.action;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthEntity.AppEnterpatientEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.ResidentHealthFiles;
import com.ylz.bizDo.jtapp.basicHealthEntity.ResidentRecordsEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.AppSignFormListEntity;
import com.ylz.bizDo.jtapp.drEntity.DrInfo;
import com.ylz.bizDo.jtapp.drVo.AppFileAuditQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppHealthListEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppSMSignEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMemberEntity;
import com.ylz.bizDo.jtapp.patientVo.*;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.*;

/**
 * 签约接口action.
 */
@SuppressWarnings("all")
@Action(
        value="hzSign",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class HzSignAction extends CommonAction {

        /**
         * 根据医生id和团队id查询医生详细信息
         * @return
         */
        public String findDrInfoByid(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                DrInfo info = sysDao.getAppDrUserDao().findDrInfoByid(qvo.getDrId(),qvo.getTeamId());
                                this.getAjson().setVo(info);
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
         * 查询团队
         * @return
         */
        public String appTeamList(){
                try{
                        AppTeamVo qvo = (AppTeamVo)getAppJson(AppTeamVo.class);
                        if(qvo!=null){
                                if(StringUtils.isNotBlank(qvo.getTeamHospId())){
                                        AppHospDept hosp = (AppHospDept)this.sysDao.getServiceDo().find(AppHospDept.class,qvo.getTeamHospId());
                                        //查询对应机构下的对团队 返回团队id、团队名称、团队下的成员数量
                                        List<AppTeamEntity> teamE = this.sysDao.getAppTeamDao().findTeam(qvo.getTeamHospId());
                                        if(hosp != null) {
                                            this.getAjson().setMsg(hosp.getHospName());
                                        }
                                        this.getAjson().setRows(teamE);
                                }
                        }else {
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
         * 根据名称模糊搜索团队
         * @return
         */
        public String appFindTeam(){
                try{
                        AppTeamVo qvo = (AppTeamVo)getAppJson(AppTeamVo.class);
                        if(qvo!=null){
                                if(StringUtils.isNotBlank(qvo.getTeamName())){
                                        //查询对应机构下的对团队 返回团队id、团队名称、团队下的成员数量
                                        List<AppTeamEntity> teamE = this.sysDao.getAppTeamDao().findTeamByName(qvo.getTeamName());
                                        this.getAjson().setRows(teamE);
                                }
                        }else {
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
         * 根据团队id查询成员
         * @return
         */
        public String appTeamMemList(){
                try{
                        AppTeamMemberVo qvo = (AppTeamMemberVo)getAppJson(AppTeamMemberVo.class);
                        if(qvo!=null){
                                if(StringUtils.isNotBlank(qvo.getTeamId())){
                                        //查询对应团队下的成员 返回成员信息
                                        List<AppTeamMemberEntity> teamMemE = this.sysDao.getAppTeamMemberDao().findMemByTeamId(qvo.getTeamId());
                                        this.getAjson().setRows(teamMemE);
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                }
                return "ajson";
        }

        /**
         * 提交申请签约接口 个人签约
         * //@param patientId 患者
         * @param teamid 团队
         * @param drId 医生id
         */
        public String signFormUser(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
//                                System.out.println("图片："+qvo.getSignImageUrl());
                                AppSignForm signForm = sysDao.getAppSignformDao().findSignOne(getAppPatientUser().getId());
                                if(signForm!=null){
                                        this.getAjson().setMsgCode("800");
                                        this.getAjson().setMsg("已有签约记录!");
                                        return "ajson";
                                }
                                AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                                if(drUser!=null){
                                        AppHospDept dept =  (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                        if(dept!=null){
                                                CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                                                if(cdAdd!=null){
                                                        AppCommQvo app = new AppCommQvo();
                                                        app.setSignType("0");//普通人群查询
                                                        if("1".equals(cdAdd.getAreaSignWay())){//团队上限签约人数
                                                                app.setTeamId(qvo.getTeamId());
                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())) {
                                                                        this.getAjson().setMsg("该团队签约人数已达上限，请重新选择签约团队!");
                                                                        this.getAjson().setMsgCode("900");
                                                                        return "ajson";
                                                                }
                                                        }else if("0".equals(cdAdd.getAreaSignWay())){
//                                                                app.setTeamId(qvo.getTeamId());
//                                                                app.setDrId(qvo.getDrId());
//                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
//                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())) {
//                                                                        this.getAjson().setMsg("该医生的签约人数已上限,请重新选择签约医生!");
//                                                                        this.getAjson().setMsgCode("900");
//                                                                        return "ajson";
//                                                                }
                                                                app.setTeamId(qvo.getTeamId());
                                                                int count = sysDao.getAppTeamMemberDao().findTeamPeopleCount(qvo.getTeamId());//查询整个团队人员数
                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);//查询整个团队签约数
                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())*count) {
                                                                        this.getAjson().setMsg("该团队签约人数已达上限，请重新选择签约团队!");
                                                                        this.getAjson().setMsgCode("900");
                                                                        return "ajson";
                                                                }
                                                        }
                                                }
                                                //判断该医院所在市是否开启建档才可签约限制
                                                if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                                        List<AppSignSetting> sets = sysDao.getServiceDo().loadByPk(AppSignSetting.class,"signsAreaCode",dept.getHospAreaCode().substring(0,4));
                                                        if(sets!=null && sets.size()>0){
                                                                if(StringUtils.isNotBlank(qvo.getSignImageType()) && "0".equals(qvo.getSignImageType())){

                                                                }else{
                                                                        //判断附件上传条件
                                                                        if(sets.get(0)!= null){
                                                                                if("1".equals(sets.get(0).getSerImageState())){//该市必须上传附件
                                                                                        if(StringUtils.isBlank(qvo.getSignImageUrl())){//还没上传附件
                                                                                                this.getAjson().setMsgCode("900");
                                                                                                this.getAjson().setMsg("请上传附件图片");
                                                                                                return "ajson";
                                                                                        }
                                                                                }
                                                                        }
                                                                }
                                                                if("1".equals(sets.get(0).getSignsOpenJd())){//开启建档签约限制，要去查询健康档案
                                                                        HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                                                                        qqvo.setIdno(this.getAppPatientUser().getPatientIdno());
//                                                                        qqvo.setOrgId(dept.getId());
                                                                        qqvo.setType("2");
                                                                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, dept.getHospAreaCode().substring(0,4));
                                                                        if(value!=null){
                                                                                qqvo.setUrlType(value.getCodeTitle());
                                                                        }
                                                                        String requestUserId = null;
                                                                        String requestUserName = null;
                                                                        String userType = null;
                                                                        AppCommQvo qvoFile = new AppCommQvo();
                                                                        AppPatientUser user = this.getAppPatientUser();
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
                                                                                        if(StringUtils.isNotBlank(str)){
                                                                                                System.out.println("宁德居民档案:"+str);
                                                                                                JSONObject jsonall = JSONObject.fromObject(str);
                                                                                                if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                                                                                                        if(jsonall.get("entity") != null) {
                                                                                                                JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                                                                                                if(entity != null && "800".equals(entity.get("msgCode").toString())){
                                                                                                                        List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(),new TypeToken<List<AppEnterpatientEntity>>() {}.getType());
                                                                                                                        if(list != null && list.size()>0){
                                                                                                                                if(StringUtils.isBlank(list.get(0).getDf_id())){
                                                                                                                                        this.getAjson().setMsg("请到社区或联系社区医生建立居民健康档案");
                                                                                                                                        this.getAjson().setMsgCode("900");
                                                                                                                                        return "ajson";
                                                                                                                                }else{
                                                                                                                                        T_dwellerfile file = new T_dwellerfile();
                                                                                                                                        file.setCsrq(list.get(0).getBirthday().replaceAll("-",""));
                                                                                                                                        file.setJdrq(ExtendDate.getYYYYMMD(ExtendDate.getCalendar(list.get(0).getCdate())));
                                                                                                                                        file.setJmdah(list.get(0).getDf_id());
                                                                                                                                        file.setJmxm(list.get(0).getName());
                                                                                                                                        file.setJwhbh(list.get(0).getRef_cjid());
                                                                                                                                        file.setMphm(list.get(0).getAdrss_hnumber());
                                                                                                                                        file.setSfyxda(list.get(0).getSfyxda());
                                                                                                                                        file.setTel(list.get(0).getTelphone());
                                                                                                                                        file.setSfzh(list.get(0).getIdcardno());
                                                                                                                                        if(StringUtils.isNotBlank(list.get(0).getAdress_pro())){
                                                                                                                                                CdAddress addPro = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_pro());
                                                                                                                                                if(addPro != null){
                                                                                                                                                        file.setSheng(addPro.getAreaSname());
                                                                                                                                                }
                                                                                                                                        }
                                                                                                                                        if(StringUtils.isNotBlank(list.get(0).getAdress_city())){
                                                                                                                                                CdAddress addCity = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_city());
                                                                                                                                                if(addCity != null){
                                                                                                                                                        file.setShi(addCity.getAreaSname());
                                                                                                                                                }
                                                                                                                                        }
                                                                                                                                        if(StringUtils.isNotBlank(list.get(0).getAdress_county())){
                                                                                                                                                CdAddress addCoun = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_county());
                                                                                                                                                if(addCoun != null){
                                                                                                                                                        file.setXian(addCoun.getAreaSname());
                                                                                                                                                }
                                                                                                                                        }
                                                                                                                                        if(StringUtils.isNotBlank(list.get(0).getAdress_rural())){
                                                                                                                                                CdAddress addRural = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_rural());
                                                                                                                                                if(addRural != null){
                                                                                                                                                        file.setXiang(addRural.getAreaSname());
                                                                                                                                                }
                                                                                                                                        }
                                                                                                                                        if(StringUtils.isNotBlank(list.get(0).getAdress_village())){
                                                                                                                                                file.setXzqydm(list.get(0).getAdress_village());
                                                                                                                                                CdAddress address = sysDao.getCdAddressDao().findByCacheCode(list.get(0).getAdress_village());
                                                                                                                                                if(address != null){
                                                                                                                                                        file.setCun(address.getAreaSname());
                                                                                                                                                }
                                                                                                                                        }
                                                                                                                                        file.setXb(list.get(0).getSex());
                                                                                                                                        this.getAjson().setVo(file);
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
                                                                                        if(StringUtils.isNotBlank(str)){
                                                                                                JSONObject jsonall = JSONObject.fromObject(str);
                                                                                                if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")){
                                                                                                        if(jsonall.get("entity") != null) {
                                                                                                                JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                                                                                                if ("true".equals(entity.get("success").toString())) {
                                                                                                                        T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(),T_dwellerfile.class);
                                                                                                                        if(entitys!=null){
                                                                                                                                if(StringUtils.isNotBlank(entitys.getJmdah())){
                                                                                                                                        //基卫有健康档案
                                                                                                                                        String strr = this.getSysDao().getSecurityCardAsyncBean().getResidentRecords(entitys.getJmdah(),null,value.getCodeTitle(),requestUserId,requestUserName,userType);
                                                                                                                                        JSONObject jsonall1 = JSONObject.fromObject(strr);
                                                                                                                                        if(jsonall1.get("rows")!=null && jsonall1.get("msgCode").equals("800")){
                                                                                                                                                List<ResidentRecordsEntity> ls = JsonUtil.fromJson(jsonall1.get("rows").toString(),new TypeToken<List<ResidentRecordsEntity>>() {}.getType());
                                                                                                                                                if(ls!=null && ls.size()>0){
                                                                                                                                                        ResidentHealthFiles t_dwellerfile = ls.get(0).getT_dwellerfile();
                                                                                                                                                        if(t_dwellerfile!=null){
                                                                                                                                                                JSONObject json = JSONObject.fromObject(t_dwellerfile);//将java对象转换为json对象
                                                                                                                                                                String sstr = json.toString();
                                                                                                                                                                AppHealthFile file = sysDao.getAppHealthFileDao().findFileByPatientId(getAppPatientUser().getId());
                                                                                                                                                                if(file==null){
                                                                                                                                                                        file = new AppHealthFile();
                                                                                                                                                                        file.setData(sstr);
                                                                                                                                                                        file.setHfTeamId(qvo.getTeamId());
                                                                                                                                                                        file.setHfDrId(qvo.getDrId());
                                                                                                                                                                        file.setHfAreaCode(dept.getHospAreaCode());
                                                                                                                                                                        file.setHfHospId(dept.getId());
                                                                                                                                                                        file.setHfPatientId(getAppPatientUser().getId());
                                                                                                                                                                        file.setHfAuditState("1");
                                                                                                                                                                        sysDao.getServiceDo().add(file);
                                                                                                                                                                }else{
                                                                                                                                                                        file.setData(sstr);
                                                                                                                                                                        file.setHfState("0");
                                                                                                                                                                        sysDao.getServiceDo().modify(file);
                                                                                                                                                                }
                                                                                                                                                        }
                                                                                                                                                }
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
                                                                }
                                                        }
                                                }
                                        }
                                }
                                if(StringUtils.isNotBlank(qvo.getSignUpHpis())){
                                        AppSignForm form=sysDao.getAppSignformDao().signFormUser(getAppPatientUser().getId(),qvo.getTeamId(),qvo.getDrId(),qvo.getSignpackageid(),qvo.getSignUpHpis(),qvo.getSignatureImageUrl(),qvo.getSignWay(),qvo.getSignImageUrl(),qvo.getSignMobileType());
                                        this.getAjson().setVo(form);
                                }else{
                                        this.getAjson().setMsg("签约来源不能为空!");
                                        this.getAjson().setMsgCode("900");
                                }

//                                this.getAjson().setMsgCode("800");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

       /* *//**
         *  续签
         * @param renew 是否续签 1是 0否
         * @teamId
         * @drId
         *//*
        public String reSignForm(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                AppSignForm form=sysDao.getAppSignformDao().reSignForm(getAppPatientUser().getId(),qvo);
                                this.getAjson().setVo(form);
                                this.getAjson().setMsgCode("800");
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }*/

        /**
         * 签约单查询列表 根据用户id或团队id查询签约单列表
         * @param teamId 团队
         * @param patientId 患者id或创建者id
         * @param signState 为空查询所有类型 签约状态 1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约 8拒签
         * @param pageStartNo 当前页 默认1
         */
        public String findSignFormByUserOrTeam(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                String result = "1";
                                AppDrUser drUser = this.getAppDrUser();
                                if(drUser != null){
                                        qvo.setDrId(drUser.getId());
                                        //判断市是否开启家庭签约
                                        if(StringUtils.isNotBlank(drUser.getDrHospId())){
                                                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                                if(dept!=null){
                                                        if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                                                String city = AreaUtils.getAreaCode(dept.getHospAreaCode(),"2");
                                                                AppSignSetting setting = sysDao.getAppSignSettingDao().findByAreaCode(city);
                                                                if(setting != null){
                                                                        result = setting.getSignsSignType();
                                                                }
                                                        }
                                             }
                                        }
                                }
                                List<AppSignFormListEntity> ls = new ArrayList<>();
                                if("1".equals(result)){//个人签约
                                        ls = sysDao.getAppSignformDao().findSignFormByUserOrTeam(qvo);
                                }else{
                                        ls = sysDao.getAppSignformDao().findSignFormByUserOrTeamT(qvo);
                                }
                                if(ls!=null && StringUtils.isNotBlank(qvo.getPatientId())){
                                        Collections.sort(ls, ComparatorUtils.getComparatorSign());
                                }
                                this.getAjson().setQvo(qvo);
                                this.getAjson().setRows(ls);
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
         * 发起解约
         * @param signFormId 签约单id
         * @param signUrrenderReasonPatient 患者解约原因
         */
        public String surrendersStartSignForm(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                AppSignForm sign= (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,qvo.getSignFormId());
                                AppPatientUser user = this.getSysDao().getAppPatientUserDao().findByUserId(sign.getSignPatientId());
                                AppTeamMember member = this.getSysDao().getAppTeamMemberDao().findMemByDrId(sign.getSignDrId(),sign.getSignTeamId());
                                sign.setSignState(SignFormType.JYZ.getValue());
                                sign.setSignUrrenderReasonPatient(qvo.getSignUrrenderReasonPatient());
                                sysDao.getServiceDo().modify(sign);
                                this.getAjson().setVo(sign);
                                this.getAjson().setMsgCode("800");
                                String dcontent = user.getPatientName()+ "：申请解约,请及时处理!"+"(" +member.getMemTeamName()+")";
                                sysDao.getAppNoticeDao().addNotices("解约消息", dcontent, NoticesType.QYXX.getValue(), user.getId(), member.getMemDrId(), user.getId(), DrPatientType.DR.getValue());
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 个人端查询健康教育列表
         * @return
         */
        public String findByIdHealth(){
                try{
                        //个人id
                        AppPatientHealthListQvo qvo = (AppPatientHealthListQvo)getAppJson(AppPatientHealthListQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsg("参数格式错误");
                                this.getAjson().setMsgCode("900");
                        }else{
                                if(StringUtils.isNotBlank(qvo.getPatientId())||StringUtils.isNotBlank(qvo.getDrId())){
                                        List<AppHealthListEntity> ls = this.sysDao.getNewsTableDao().findByUserId(qvo);
                                        this.getAjson().setRows(ls);
                                        this.getAjson().setMsgCode("800");
                                }else{
                                        this.getAjson().setMsg("参数错误");
                                        this.getAjson().setMsgCode("900");
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
         * 个人收藏的健康教育
         * @return
         */
        public String findHzEnshrine(){
                try{
                        AppPatientHealthListQvo qvo = (AppPatientHealthListQvo)getAppJson(AppPatientHealthListQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsg("参数格式错误");
                                this.getAjson().setMsgCode("900");
                        }else{
                                if(StringUtils.isNotBlank(qvo.getPatientId())){
                                        List<AppHealthListEntity> ls = this.sysDao.getNewsTableDao().findByIsEnshrine(qvo);
                                        this.getAjson().setRows(ls);
                                        this.getAjson().setMsgCode("800");
                                }else{
                                        this.getAjson().setMsg("参数错误");
                                        this.getAjson().setMsgCode("900");
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
         * 个人代签约
         * @param patientId 代签人
         * @param patientIds 多患者id使用;号隔开
         *  @param teamid 团队 代签人自己的团队
         *  @param drId 医生id
         */
        public String patientSignFormUser(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                AppTeam team= (AppTeam) sysDao.getServiceDo().find(AppTeam.class,qvo.getTeamId());//团队
                                if(team != null){
                                        int num = 0;
                                        if(StringUtils.isNotBlank(qvo.getPatientId())){
                                                //获取代签人数
                                                num = qvo.getPatientId().split(";").length;
                                                AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                                                if(drUser!=null){
                                                        AppHospDept dept =  (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                                        if(dept!=null){
                                                                CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                                                                if(cdAdd!=null){
                                                                        AppCommQvo app = new AppCommQvo();
                                                                        app.setSignType("0");//普通人群查询
                                                                        if("1".equals(cdAdd.getAreaSignWay())){//团队上限签约人数
                                                                                app.setTeamId(qvo.getTeamId());
                                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())) {
                                                                                        this.getAjson().setMsg("该团队签约人数已达上限，请重新选择团队!");
                                                                                        this.getAjson().setMsgCode("900");
                                                                                        return "ajson";
                                                                                }else {
                                                                                        int mm = Integer.parseInt(cdAdd.getAreaSignTop())-result;
                                                                                        if(num>mm){
                                                                                                this.getAjson().setMsg("团队签约名额不足");
                                                                                                this.getAjson().setMsgCode("900");
                                                                                                return "ajson";
                                                                                        }
                                                                                }
                                                                        }else if("0".equals(cdAdd.getAreaSignWay())){
                                                                                app.setTeamId(qvo.getTeamId());
//                                                                              app.setDrId(qvo.getDrId());
                                                                                int count = sysDao.getAppTeamMemberDao().findTeamPeopleCount(qvo.getTeamId());//查询整个团队人员数
                                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())*count) {
                                                                                        this.getAjson().setMsg("该团队签约人数已达上限，请重新选择团队!");
                                                                                        this.getAjson().setMsgCode("900");
                                                                                        return "ajson";
                                                                                }else {
                                                                                        int mm=Integer.parseInt(cdAdd.getAreaSignTop())*count - result;
                                                                                        if(num>mm){
                                                                                                this.getAjson().setMsg("团队签约名额不足");
                                                                                                this.getAjson().setMsgCode("900");
                                                                                                return "ajson";
                                                                                        }
                                                                                }
                                                                        }
                                                                }
                                                        }

                                                }
                                        }
                                        AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(qvo.getPatientId());
                                        if(form != null){
                                                AppSignBatch batck=sysDao.getAppSignformDao().patientSignFormUser(getAppPatientUser().getId(),qvo.getPatientIds(),qvo.getTeamId(),qvo.getDrId(),qvo.getSignpackageid(),qvo.getSignUpHpis(),qvo.getSignImageUrl());
                                                this.getAjson().setVo(batck);
                                                this.getAjson().setMsgCode("800");
                                        }else{
                                                this.getAjson().setMsgCode("900");
                                                this.getAjson().setMsg("该用户未进行签约,无法代理签约!");
                                        }
                                }else{
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("需本人完成签约后，方可进行代理签约!");
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
         * 查询团队
         * @return
         */
        public String appTeamListTwo(){
                try{
                        AppTeamVo qvo = (AppTeamVo)getAppJson(AppTeamVo.class);
                        if(qvo!=null){
                                //根据机构id查询团队
                                if(StringUtils.isNotBlank(qvo.getTeamHospId())){
                                        Map<String,Object> map = new HashMap<>();
                                        AppHospDept hosp = (AppHospDept)this.sysDao.getServiceDo().find(AppHospDept.class,qvo.getTeamHospId());
                                        //查询对应机构下的对团队 返回团队id、团队名称、团队下的成员数量
                                        List<AppTeamEntity> teamE = this.sysDao.getAppTeamDao().findTeamm(qvo);
                                        if(hosp != null) {
                                                this.getAjson().setMsg(hosp.getHospName());
                                                if(StringUtils.isNotBlank(hosp.getHospAreaCode())){
                                                        String cityCode = AreaUtils.getAreaCode(hosp.getHospAreaCode(),"2");
                                                        CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                                                        String[] ids = hosp.getId().split("_");
                                                        String id = null;
                                                        if(ids.length>1){
                                                              id = ids[1];
                                                        }else{
                                                              id = ids[0];
                                                        }
                                                        map.put("orgId",id);
                                                        map.put("urlType",code.getCodeTitle());
                                                }

                                        }
                                        this.getAjson().setRows(teamE);
                                        CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(hosp.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                                        if(cdAdd!=null){
                                                map.put("toplimit",cdAdd.getAreaSignWay());
                                        }
                                        this.getAjson().setMap(map);
                                }
                        }else {
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
         * 通过患者姓名、身份证、电话获取个人签约信息
         * @return
         */
        public String hqqyxxSm(){
                try{
                        AppPatientQyQvo qvo = (AppPatientQyQvo)getAppJson(AppPatientQyQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                AppSMSignEntity vo = sysDao.getAppSignformDao().findSignXx(qvo);
                                this.getAjson().setVo(vo);
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
         * 查询转签原因
         * @return
         */
        public String findReason(){
                try{
                        List<CdCode> ls = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ZZREASON, CommonEnable.QIYONG.getValue());
                        this.getAjson().setRows(ls);
                        this.getAjson().setMsgCode("800");
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsg(e.getMessage());
                        this.getAjson().setMsgCode("900");
                }
                return "ajson";
        }

        /**
         * 患者转签 提交新的签约单
         * @return
         */
        public String goToSign(){
                try {
                        AppGoToSignQvo qvo = (AppGoToSignQvo)this.getAppJson(AppGoToSignQvo.class);
                        if(qvo == null){
                               this.getAjson().setMsgCode("900");
                               this.getAjson().setMsg("参数格式错误");
                        }else{
                                AppPatientUser user = this.getAppPatientUser();
                                if(user!=null){
                                        qvo.setPatientId(user.getId());
                                        String str = sysDao.getAppSignformDao().goToSign(qvo);
                                        if("true".equals(str)){
                                                this.getAjson().setMsgCode("800");
                                                this.getAjson().setMsg("转签成功");
                                        }else{
                                                this.getAjson().setMsgCode("900");
                                                this.getAjson().setMsg(str);
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
         * 转签统计
         * @return
         */
        public String goToSignStatistics(){
                try{
                        AppGoToSignQvo qvo = (AppGoToSignQvo)this.getAppJson(AppGoToSignQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                //医生id查询医生签约数 已签约数+转签数
                                //转签数
                                //转签记录表
                                AppDrUser drUser = this.getAppDrUser();
                                if(drUser!=null){
                                        qvo.setDrId(drUser.getId());
                                        //Map<String,Object> map = sysDao.getAppSignformDao().findGoToSign(qvo);
//                                        AppGotoSignRecord
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
	 * 居民履约统计
	 * 
	 * @return
	 */
	public String appSignPerformance() {
		try {
			AppCommQvo qvo = (AppCommQvo) getAppJson(AppCommQvo.class);
			if (qvo != null) {
				if (StringUtils.isNotBlank(qvo.getPatientId())) {
					AppSignForm form = this.getSysDao().getAppSignformDao().findSignFormByUserState(qvo.getPatientId());
					if (form != null) {
						// 旧履约统计
						Map<String, Object> mapPerformance = this.getSysDao().getAppPerformanceStatisticsDao().findAppSingPerformance(qvo);
						// 新履约统计
						// Map<String, Object> mapPerformance = this.getSysDao().getAppPerformanceStatisticsDao().findNewAppSingPerformance(qvo);
						this.getAjson().setMap(mapPerformance);
					} else {
						this.getAjson().setMsgCode("901");
						this.getAjson().setMsg("该居民还未签约,暂无履约数据!");
					}
				} else {
					this.getAjson().setMsgCode("900");
					this.getAjson().setMsg("居民主键不能为空!");
				}
			} else {
				this.getAjson().setMsgCode("900");
				this.getAjson().setMsg("参数格式错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getAjson().setMsgCode("900");
			this.getAjson().setMsg("系统错误,请联系管理员");
		}
		return "ajson";
	}

        /**
         * 患者续签
         * @return
         */
        public String reSignForm(){
                try{
                        AppCommQvo qvo = (AppCommQvo)this.getAppJson(AppCommQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                AppPatientUser user = this.getAppPatientUser();
                                if(user!=null) {//患者端
                                        qvo.setPatientId(user.getId());
                                        AppSignForm form=sysDao.getAppSignformDao().renewSign(qvo);
                                        this.getAjson().setVo(form);
                                        this.getAjson().setMsgCode("800");
                                        this.getAjson().setMsg("续签成功");
                                }else{
                                        if(StringUtils.isNotBlank(qvo.getSignFormId())){
                                                AppSignForm oldSign = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,qvo.getSignFormId());
                                                if(oldSign != null){
                                                        qvo.setPatientId(oldSign.getSignPatientId());
                                                        AppSignForm form=sysDao.getAppSignformDao().renewSign(qvo);
                                                        this.getAjson().setVo(form);
                                                        this.getAjson().setMsgCode("800");
                                                        this.getAjson().setMsg("续签成功");
                                                }else{
                                                        this.getAjson().setMsgCode("900");
                                                        this.getAjson().setMsg("查无旧签约数据");
                                                }
                                        }else{
                                                this.getAjson().setMsgCode("900");
                                                this.getAjson().setMsg("旧签约单主键不能为空");
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
         * 家庭版签约提交申请签约接口 个人签约
         * //@param patientId 患者
         * @param teamid 团队
         * @param drId 医生id
         */
        public String signFormFamily(){
                try {
                        AppFamilySignQvo qvo=(AppFamilySignQvo)getAppJson(AppFamilySignQvo.class);
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                int num=0;
                                if(qvo.getSonList()!=null){
                                        num = qvo.getSonList().size();
                                }
                                String requestUserId = null;
                                String requestUserName = null;
                                String userType = null;
                                AppPatientUser user = this.getAppPatientUser();
                                if(user != null ){
                                        userType = "1";
                                        requestUserId = user.getId();
                                        requestUserName = user.getPatientName();
                                }
                                AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                                if(drUser!=null){
                                        AppHospDept dept =  (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                        if(dept!=null){
                                                CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                                                if(cdAdd!=null){
                                                        AppCommQvo app = new AppCommQvo();
                                                        app.setSignType("0");//普通人群查询
                                                        if("1".equals(cdAdd.getAreaSignWay())){//团队上限签约人数
                                                                app.setTeamId(qvo.getTeamId());
                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())) {
                                                                        this.getAjson().setMsg("该团队签约人数已达上限，请重新选择团队!");
                                                                        this.getAjson().setMsgCode("900");
                                                                        return "ajson";
                                                                }else {
                                                                        int mm = Integer.parseInt(cdAdd.getAreaSignTop())-result;
                                                                        if(num>mm){
                                                                                this.getAjson().setMsg("团队签约名额不足");
                                                                                this.getAjson().setMsgCode("900");
                                                                                return "ajson";
                                                                        }
                                                                }
                                                        }else if("0".equals(cdAdd.getAreaSignWay())){
                                                                app.setTeamId(qvo.getTeamId());
//                                                                app.setDrId(qvo.getDrId());
                                                                int count = sysDao.getAppTeamMemberDao().findTeamPeopleCount(qvo.getTeamId());//查询整个团队人员数
                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())*count) {
                                                                        this.getAjson().setMsg("该团队签约人数已达上限，请重新选择团队!");
                                                                        this.getAjson().setMsgCode("900");
                                                                        return "ajson";
                                                                }else {
                                                                        int mm=Integer.parseInt(cdAdd.getAreaSignTop())*count - result;
                                                                        if(num>mm){
                                                                                this.getAjson().setMsg("团队签约名额不足");
                                                                                this.getAjson().setMsgCode("900");
                                                                                return "ajson";
                                                                        }
                                                                }
                                                        }
                                                }
                                                //判断该医院所在市是否开启建档才可签约限制
                                                if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                                        List<AppSignSetting> sets = sysDao.getServiceDo().loadByPk(AppSignSetting.class,"signsAreaCode",dept.getHospAreaCode().substring(0,4));
                                                        if(sets!=null && sets.size()>0){
                                                                if("1".equals(sets.get(0).getSignsOpenJd()) || AddressType.NDS.getValue().equals(sets.get(0).getSignsAreaCode())){//开启建档签约限制，要去查询健康档案
                                                                        HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                                                                        qqvo.setIdno(this.getAppPatientUser().getPatientIdno());
//                                                                        qqvo.setOrgId(dept.getId());
                                                                        qqvo.setType("2");
                                                                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, dept.getHospAreaCode().substring(0,4));
                                                                        if(value!=null){
                                                                                qqvo.setUrlType(value.getCodeTitle());
                                                                        }
                                                                        String str = this.getSysDao().getSecurityCardAsyncBean().getHealthCareRecords(qqvo,requestUserId,requestUserName,userType);
                                                                        if(StringUtils.isNotBlank(str)){
                                                                            JSONObject jsonall = JSONObject.fromObject(str);
                                                                            if(jsonall.get("vo")!=null && jsonall.get("msgCode").equals("800")) {
                                                                                if (jsonall.get("entity") != null) {
                                                                                    JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                                                                                    if(AddressType.ND.getValue().equals(qqvo.getUrlType()) || AddressType.PINGT.getValue().equals(qqvo.getUrlType())){//宁德
                                                                                        if(entity != null && "800".equals(entity.get("msgCode").toString())){
                                                                                            List<AppEnterpatientEntity> list = JsonUtil.fromJson(entity.get("rows").toString(),new TypeToken<List<AppEnterpatientEntity>>() {}.getType());
                                                                                            if(list != null && list.size()>0){
                                                                                                if(StringUtils.isBlank(list.get(0).getDf_id())){
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
                                                                                        if ("true".equals(entity.get("success").toString())) {
                                                                                            T_dwellerfile entitys = JsonUtil.fromJson(entity.get("entity").toString(),T_dwellerfile.class);
                                                                                            if(entitys!=null){
                                                                                                if(StringUtils.isBlank(entitys.getJmdah())) {
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
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                                qvo.setPatientId(getAppPatientUser().getId());
                                List<AppSignForm> list = sysDao.getAppSignformDao().signFormFamily(qvo);
                                this.getAjson().setRows(list);
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
         * 个人端转签（个人变更）
         * @return
         */
        public String subChangeUser(){
                try {
                        AppGoToSignQvo qvo = (AppGoToSignQvo)this.getAppJson(AppGoToSignQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsg("参数格式错误");
                                this.getAjson().setMsgCode("900");
                        }else{
                                sysDao.getAppSignformDao().subChangeUser(qvo);
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg(e.getMessage());
                }
                return "ajson";
        }

        /**
         * 判断签约上限
         * @return
         */
        public String isToplimit(){
                try{
                        AppToplimitQvo qvo = (AppToplimitQvo)this.getAppJson(AppToplimitQvo.class);
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else{
                                if(StringUtils.isNotBlank(qvo.getHospId())){
                                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
                                        if(dept!=null){
                                                Map<String,Object> map = new HashMap<>();
                                                map.put("type","0");
                                                CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                                                if(cdAdd!=null){
                                                        AppCommQvo app = new AppCommQvo();
                                                        app.setSignType("0");//普通人群查询
                                                        if ("1".equals(qvo.getType())) {//团队上限签约人数
                                                                app.setTeamId(qvo.getTeamId());
                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);
                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())) {
                                                                        map.put("type","1");
                                                                        map.put("msg","该团队签约人数已达上限!,请重新选择签约团队!");
                                                                }
                                                        }else if("0".equals(qvo.getType())){//医生上限
//                                                        app.setDrId(qvo.getDrId());
                                                                app.setTeamId(qvo.getTeamId());
                                                                int count = sysDao.getAppTeamMemberDao().findTeamPeopleCount(qvo.getTeamId());//查询整个团队人员数
                                                                Integer result = sysDao.getAppSignformDao().findSignXxCount(app);//查询整个团队签约数
                                                                if (result >= Integer.parseInt(cdAdd.getAreaSignTop())*count) {
                                                                        map.put("type","2");
                                                                        map.put("msg","该团队签约人数已达上限!,请重新选择签约团队!");
                                                                }
                                                        }else{
                                                                map.put("type","0");
                                                        }
                                                }
                                                this.getAjson().setMap(map);
                                        }
                                }else{
                                        this.getAjson().setMsg("机构id不能为空");
                                        this.getAjson().setMsgCode("900");
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
         * 申请自动撤销
         * @return
         */
        public String getSignApplicationRevocation(){
                AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
                try {
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误");
                        }else {
                                if(!PinyinUtil.checkname(qvo.getSignOthnerReason())){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("撤销原因:只能填写文字!");
                                        return "ajson";
                                }
                                if(StringUtils.isNotBlank(qvo.getSignOthnerReason())){
                                        AppSignForm sign= (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,qvo.getSignFormId());
                                        if(sign != null){
                                                if(SignFormType.DQY.getValue().equals(sign.getSignState())||SignFormType.ZQ.getValue().equals(sign.getSignState())){
                                                        AppPatientUser user = this.getSysDao().getAppPatientUserDao().findByUserId(sign.getSignPatientId());
                                                        AppTeamMember member = this.getSysDao().getAppTeamMemberDao().findMemByDrId(sign.getSignDrId(),sign.getSignTeamId());
                                                        sign.setSignState(SignFormType.CH.getValue());//撤销
                                                        sign.setSignOthnerReason(qvo.getSignOthnerReason());//撤销原因
                                                        sign.setSignUrrenderReasonPatient(qvo.getSignUrrenderReasonPatient());
                                                        sysDao.getServiceDo().modify(sign);
                                                        this.getAjson().setVo(sign);
                                                        this.getAjson().setMsgCode("800");
                                                        String dcontent = user.getPatientName()+ "：撤销原因,"+qvo.getSignOthnerReason();
                                                        sysDao.getAppNoticeDao().addNotices("撤销消息", dcontent, NoticesType.QYXX.getValue(), user.getId(), member.getMemDrId(), user.getId(), DrPatientType.DR.getValue());
                                                }else{
                                                        this.getAjson().setMsgCode("900");
                                                        this.getAjson().setMsg("该签约单已签约不可撤销!");
                                                }
                                        }else{
                                                this.getAjson().setMsgCode("900");
                                                this.getAjson().setMsg("查无该签约单!");
                                        }
                                }else{
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("请填写撤销原因!");
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
         * 患者提交预健康档案
         * @return
         */
        public String saveFile(){
                try{
                        AppFileAuditQvo qvo = (AppFileAuditQvo)this.getAppJson(AppFileAuditQvo.class);
                        if(qvo==null){
                                this.getAjson().setMsg("参数格式错误");
                                this.getAjson().setMsgCode("900");
                        }else{
                                AppPatientUser user = this.getAppPatientUser();
                                if(user!=null){
                                        qvo.setHfPatientId(user.getId());
                                }
                                if(StringUtils.isNotBlank(qvo.getHfDrId())){
                                        AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,qvo.getHfDrId());
                                        if(drUser!=null){
                                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                                if(dept!=null){
                                                        qvo.setHfHospId(dept.getId());
                                                        qvo.setHfAreaCode(dept.getHospAreaCode());
                                                }
                                        }
                                }
                                AppHealthFile file = sysDao.getAppHealthFileDao().saveFile(qvo);
                                if(file!=null){
                                        this.getAjson().setMsgCode("800");
                                        this.getAjson().setVo(file);
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
         * 居民履约统计
         * @return
         */
        public String appNewSignPerformance(){
                try{
                        AppCommQvo qvo = (AppCommQvo)getAppJson(AppCommQvo.class);
                        if(qvo!=null){
                                if(StringUtils.isNotBlank(qvo.getPatientId())){
                                        AppSignForm form = this.getSysDao().getAppSignformDao().findSignFormByUserState(qvo.getPatientId());
                                        if(form != null) {
                                                qvo.setDrId(form.getSignDrId());
                                                qvo.setSignFormId(form.getId());
                                                qvo.setSignpackageid(form.getSignpackageid());
                                                Map<String, Object> mapPerformance = this.getSysDao().getAppPerformanceStatisticsDao().findNewAppSingPerformance(qvo);
                                                this.getAjson().setMap(mapPerformance);
                                        }else{
                                                this.getAjson().setMsgCode("901");
                                                this.getAjson().setMsg("该居民还未签约,暂无履约数据!");
                                        }
                                }else{
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("居民主键不能为空!");
                                }
                        }else {
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
         * 根据身份证查询居民签约的经济类型
         * @return
         */
        public String findJjByIdno(){
                try{
                        AppCommQvo qvo = (AppCommQvo)getAppJson(AppCommQvo.class);
                        if(qvo == null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("参数格式错误！");
                        }else{
                                if(StringUtils.isBlank(qvo.getPatientIdno())){
                                        this.getAjson().setMsg("身份证号不能为空");
                                        this.getAjson().setMsgCode("900");
                                }else{
                                        //根据身份证查询居民信息
                                        AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(qvo.getPatientIdno());
                                        if(user != null){
                                                //根据居民主键查询签约单
                                                String[] signState = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
                                                AppSignForm form = sysDao.getAppSignformDao().findSignBySignState(signState,user.getId());
                                                if(form != null){
                                                        Map<String,Object> map = new HashMap<String,Object>();
                                                        map.put("signState","1");
                                                        map.put("signTeamId",form.getSignTeamId());
                                                        String drId = form.getSignDrId();
                                                        String[] drIds = drId.split("_");
                                                        if(drIds != null && drIds.length>1){
                                                                map.put("signDrId",drIds[1]);
                                                        }else{
                                                                map.put("signDrId",drId);
                                                        }

                                                        String hospId = form.getSignHospId();
                                                        String[] hospIds = hospId.split("_");
                                                        if(hospIds != null && hospIds.length>1){
                                                                map.put("signHospId",hospIds[1]);
                                                        }else{
                                                                map.put("signHospId",hospId);
                                                        }
                                                        //根据签约主键查询下疾病类型
                                                        List<AppLabelDisease> lsDise = sysDao.getAppLabelGroupDao().findBySignDisease(form.getId(),"2");
                                                        if(lsDise != null && lsDise.size()>0){
                                                                for(AppLabelDisease vo : lsDise){
                                                                        if(vo.getLabelValue().equals("201")){
                                                                                String hdb = null;
                                                                                if(vo.getLabelColor().equals("gray")){
                                                                                        hdb = "1";
                                                                                }else if(vo.getLabelColor().equals("green")){
                                                                                        hdb = "2";
                                                                                }else if(vo.getLabelColor().equals("yellow")){
                                                                                        hdb = "3";
                                                                                }else if(vo.getLabelColor().equals("red")){
                                                                                        hdb = "4";
                                                                                }
                                                                                if(hdb != null){
                                                                                        map.put("signDiseaseHbpColor",hdb);
                                                                                }

                                                                        }
                                                                        if(vo.getLabelValue().equals("202")){
                                                                                String dm = null;
                                                                                if(vo.getLabelColor().equals("gray")){
                                                                                        dm = "1";
                                                                                }else if(vo.getLabelColor().equals("green")){
                                                                                        dm = "2";
                                                                                }else if(vo.getLabelColor().equals("yellow")){
                                                                                        dm = "3";
                                                                                }else if(vo.getLabelColor().equals("red")){
                                                                                        dm = "4";
                                                                                }
                                                                                if(dm != null){
                                                                                        map.put("signDiseaseDmColor",dm);
                                                                                }

                                                                        }
                                                                }
                                                        }
                                                        //根据签约主键查询下经济类型
                                                        List<AppLabelEconomics> list = sysDao.getAppLabelGroupDao().findBySignEconomics(form.getId(),"4");
                                                        this.getAjson().setRows(list);
                                                        this.getAjson().setVo(map);
                                                        this.getAjson().setMsgCode("800");
                                                }else{
                                                        this.getAjson().setMsgCode("900");
                                                        this.getAjson().setMsg("该居民未签约");
                                                }
                                        }else {
                                                this.getAjson().setMsgCode("900");
                                                this.getAjson().setMsg("查无居民信息！");
                                        }
                                }
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("系统错误,请联系管理员");
                }
                return "ajson";
        }
}
