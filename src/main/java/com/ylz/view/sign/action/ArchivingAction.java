package com.ylz.view.sign.action;

import com.ylz.bizDo.app.entity.AppArchivingCardAddrEntity;
import com.ylz.bizDo.app.entity.AppArchivingCardPeopeEntity;
import com.ylz.bizDo.app.entity.AppArchivingPatientEntity;
import com.ylz.bizDo.app.po.AppArchivingCardPeople;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.bizDo.app.vo.AppArchivingCardPeopleQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdRole;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.jtapp.commonVo.AppAddressQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.SourceType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExcelUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/7/17.
 */
@SuppressWarnings("all")
@Action(
        value="archivingAction",
        results={
                @Result(name = "json", type = "json",params={"root","jsons","contentType", "application/json"}),
                @Result(name = "jList", type = "json",params={"root","jList","contentType", "application/json"}),
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","excludeNullProperties","true","contentType", "application/json"})
        }
)
public class ArchivingAction extends CommonAction {
    private List jList;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 初始数据
     * @return
     */
    public String commList(){
        try{

        }catch (Exception e){
            new ActionException(getClass(),getAct(),getJsons(),e);
        }
        return "json";
    }

    //居民信息查询
    public String findArchivingWeb(){
        try {
            AppArchivingCardPeopleQvo qvo = (AppArchivingCardPeopleQvo)getJsonLay(AppArchivingCardPeopleQvo.class);
            if(qvo==null){
                qvo=new AppArchivingCardPeopleQvo();
            }
            CdUser user = this.getSessionUser();
            if(user!=null){
                if(StringUtils.isNotBlank(user.getDrId())){
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,user.getDrId());
                    if(drUser != null){
                        if(drUser.getDrRole().equals(AppRoleType.YISHENG.getValue())){
                            qvo.setRole(AppRoleType.YISHENG.getValue());
                            qvo.setDrId(drUser.getId());
                            String teamIds = sysDao.getAppTeamMemberDao().findByDrId(drUser.getId());
                            qvo.setTeamIds(teamIds);
                        }
                    }
                }
            }
            List<AppArchivingCardPeopeEntity> ls = sysDao.getAppArchivingCardPeopeDao().findPeopleList(qvo);
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }
//导出数据
    public String findArchivingXxWebExcel(){
        try {
            AppArchivingCardPeopleQvo qvo = (AppArchivingCardPeopleQvo)getJsonLay(AppArchivingCardPeopleQvo.class);
            if(qvo==null){
                qvo=new AppArchivingCardPeopleQvo();
            }
            if(StringUtils.isNotBlank(qvo.getPatientName())){
                String selStr=java.net.URLDecoder.decode(qvo.getPatientName(),"UTF-8");
                qvo.setPatientName(selStr);
            }
            if("0".equals(qvo.getJdSourceType())){
                if(StringUtils.isNotBlank(qvo.getPatientNeighborhoodCommittee())){
                    String selStr=java.net.URLDecoder.decode(qvo.getPatientNeighborhoodCommittee(),"UTF-8");
                    qvo.setPatientNeighborhoodCommittee(selStr);
                }
            }
            String type = getRequest().getParameter("typeNum");
            String numberCount = getRequest().getParameter("numberCount");
            if(type.equals("2")){
                qvo.setPageSize(99999999);
            }else{
                qvo.setPageSize(Integer.valueOf(numberCount));
            }
            CdUser user = this.getSessionUser();
            if(user!=null){
                if(StringUtils.isNotBlank(user.getDrId())){
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,user.getDrId());
                    if(drUser != null){
                        if(drUser.getDrRole().equals(AppRoleType.YISHENG.getValue())){
                            qvo.setRole(AppRoleType.YISHENG.getValue());
                            String teamIds = sysDao.getAppTeamMemberDao().findByDrId(drUser.getId());
                            qvo.setTeamIds(teamIds);
                        }
                    }
                }
            }
            List<AppArchivingCardPeopeEntity> ls = sysDao.getAppArchivingCardPeopeDao().findPeopleList(qvo);
            ExcelUtil<AppArchivingCardPeopeEntity> ex = new ExcelUtil<AppArchivingCardPeopeEntity>();
            String[] headers = null;
            String[] datasetName =null;
            if("0".equals(qvo.getJdSourceType())){
                headers = new String[]{ "健康档案号","县","街道(乡镇)","社区(村)","姓名","身份证","联系电话","签约状态","是否脱贫","未签原因","对象类型"};
                datasetName= new String[]{ "rhfId","addrCountyName","addrRuralName","addrVillageName","patientName","patientIdno","patientTel","signState","isNotPoverty","notSignReason","provincialInsurance"};
            }else{
                headers = new String[]{ "健康档案号","县","街道(乡镇)","社区(村)","姓名","身份证","联系电话","低保户","特困户","签约状态","是否脱贫","未签原因"};
                datasetName= new String[]{ "rhfId","addrCountyName","addrRuralName","addrVillageName","patientName","patientIdno","patientTel","lowInsured","poorHouseholds","signState","isNotPoverty","notSignReason"};
            }
//            String[] headers = { "健康档案号","县","街道(乡镇)","社区(村)","姓名","身份证","联系电话","低保户","特困户","签约状态","是否脱贫","未签原因"};
//            String[] datasetName={ "rhfId","addrCountyName","addrRuralName","addrVillageName","patientName","patientIdno","patientTel","lowInsured","poorHouseholds","signState","isNotPoverty","notSignReason"};
            getResponse().reset();
            getResponse().setContentType("application/vnd..ms-excel");
            String titless = "";
            Map<String,Object> returnMap = sysDao.getAppArchivingCardPeopeDao().findMap(qvo);
            if(returnMap != null){
                titless += " 建档立卡人数："+returnMap.get("totalCount").toString();
                titless += "; 未建档人数："+ returnMap.get("wjd").toString();
                titless += "; 未建档已签约人数："+ returnMap.get("wjdyqy").toString();
                titless += "; 已建档未签约人数："+ returnMap.get("yjdwqy").toString();
                titless += "; 脱贫人数："+ returnMap.get("tp").toString();
                titless += "; 异常数据："+ returnMap.get("ycs").toString();
//                titless += "; 重复数据："+ returnMap.get("cfs").toString();
            }
            String titles = "";
            if(StringUtils.isNotBlank(qvo.getPatientNeighborhoodCommittee())){
                if("0".equals(qvo.getJdSourceType())){
                    CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientStreet());
                    if(address != null){
                        titles = address.getAreaName()+qvo.getPatientNeighborhoodCommittee()+"建档立卡居民花名册";
                    }
                }else{
                    CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientNeighborhoodCommittee());
                    if(address != null){
                        titles = address.getAreaName()+"建档立卡居民花名册";
                    }
                }

            }else if(StringUtils.isNotBlank(qvo.getPatientStreet())){
                CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientStreet());
                if(address != null){
                    titles = address.getAreaName()+"建档立卡居民花名册";
                }
            }else if(StringUtils.isNotBlank(qvo.getPatientArea())){
                CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientArea());
                if(address != null){
                    titles = address.getAreaName()+"建档立卡居民花名册";
                }
            }else if(StringUtils.isNotBlank(qvo.getPatientCity())){
                CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientCity());
                if(address != null){
                    titles = address.getAreaName()+"建档立卡居民花名册";
                }
            }else{
                titles = "福建省建档立卡居民花名册";
            }
            if(StringUtils.isNotBlank(qvo.getNotConfirm())){
                titles+="(异常)";
            }
            String test = titles;
            String orgName = "";
            if(StringUtils.isNotBlank(user.getHospId())){
                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                if(dept!= null){
                    orgName = dept.getHospName();
                }
            }else {
                orgName = user.getDeptName();
            }
            if(StringUtils.isNotBlank(orgName)){
                titles+=";;;  填报机构："+orgName+"                     填报日期："+ExtendDate.getChineseYMD(Calendar.getInstance());
            }else{
                titles+=";;; 填报日期："+ExtendDate.getChineseYMD(Calendar.getInstance());
            }
            getResponse().setHeader("content-Disposition","attachment;filename="+ URLEncoder.encode(test+".xls","utf-8"));
            ex.exportExcell(test,headers,datasetName, ls, getResponse().getOutputStream(),"",titles,titless);
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return null;
    }

    /**
     * 查询居民建档立卡信息
     * @return
     */
    public String archivingLook(){
        try{
            AppArchivingCardPeopleQvo qvo=(AppArchivingCardPeopleQvo)getVoJson(AppArchivingCardPeopleQvo.class);
            if(qvo != null){
                if(StringUtils.isNotBlank(qvo.getId())){
                    AppArchivingPatientEntity vo =sysDao.getAppArchivingCardPeopeDao().archivingLook(qvo);
                    this.jsons.setVo(vo);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            new ActionException(getClass(), getAct(), e);
        }
        return "json";
    }

    /**
     * 修改
     * @return
     */
    public String archivingModify(){
       try{
           AppArchivingCardPeopleQvo qvo=(AppArchivingCardPeopleQvo)getVoJson(AppArchivingCardPeopleQvo.class);
           if(qvo!=null){
               sysDao.getSysLogBusinessDo().addSysLog("建档立卡居民","APP_ARCHIVINGCARD_PEOPLE",qvo.getId(),getLogVoList(),getSessionUser());
               AppArchivingCardPeople avo=(AppArchivingCardPeople) sysDao.getServiceDo().find(AppArchivingCardPeople.class,qvo.getId());
               if(avo!=null){
                   if(StringUtils.isNotBlank(qvo.getPatientTel())){
                       avo.setPatientTel(qvo.getPatientTel());
                   }

                    /*if(StringUtils.isNotBlank(qvo.getRemarks())){
                       avo.setRemarks(qvo.getRemarks());
                   }else{
                       avo.setRemarks(null);
                   }*/
                   if(StringUtils.isNotBlank(qvo.getPatientIdno())){
                       if(!qvo.getPatientIdno().equals(avo.getArchivingPatientIdno())){
                           avo.setArchivingPatientIdno(qvo.getPatientIdno());
                           avo.setRemarks("1");
                       }
                   }

                   //只允许修改手机号 qvo.getJdSourceType() 的值为0只能修改手机,身份证、未签原因
                   if(!"0".equals(qvo.getJdSourceType())){
                       if(StringUtils.isNotBlank(qvo.getNotSignReason())){
                           avo.setOtherReason(null);
                           if("0".equals(qvo.getNotSignReason())){
                               avo.setNotSignReason("");
                           }else{
                               avo.setNotSignReason(qvo.getNotSignReason());
                               if("20".equals(qvo.getNotSignReason())){
                                   avo.setOtherReason(qvo.getOtherReason());
                               }
                           }
                       }
                       if(StringUtils.isNotBlank(qvo.getJdgfState())){
                           if("0".equals(qvo.getJdgfState())){
                               avo.setDelState("2");
                           }
                       }
                       if(StringUtils.isBlank(avo.getSex())){
                           if(StringUtils.isNotBlank(qvo.getPatientIdno())){//身份证不为空，根据身份证赋值性别和出生日期
                               String str = CardUtil.IDCardValidate(qvo.getPatientIdno());
                               if(StringUtils.isBlank(str)){
                                   Map<String,Object> map = new HashMap<>();
                                   if(qvo.getPatientIdno().length()>=18){
                                       map = CardUtil.getCarInfo(qvo.getPatientIdno());
                                   }else{
                                       map = CardUtil.getCarInfo15W(qvo.getPatientIdno());
                                   }
                                   if(map.get("sex")!=null){
                                       avo.setSex(map.get("sex").toString());
                                   }
                                   if(map.get("birthday")!=null){
                                       avo.setBirthday(ExtendDate.getCalendar(map.get("birthday").toString()));
                                   }
                               }
                           }
                       }
                       if(StringUtils.isNotBlank(qvo.getAddrRuralCode())){
                           avo.setAddrRuralCode(qvo.getAddrRuralCode());
                           avo.setAddrRuralName(qvo.getAddrRuralName());
                       }
                       if(StringUtils.isNotBlank(qvo.getAddrVillageCode())){
                           avo.setAddrVillageCode(qvo.getAddrVillageCode());
                           avo.setAddrVillageName(qvo.getAddrVillageName());
                       }
                       if(StringUtils.isNotBlank(qvo.getPatientIdno())){
                           avo.setArchivingPatientIdno(qvo.getPatientIdno());
                       }
                       avo.setIsNotPoverty(qvo.getIsNotPoverty());
                       avo.setLowInsured(qvo.getLowInsured());
                       avo.setPoorHouseholds(qvo.getPoorHouseholds());
                   }else{//
                       if(StringUtils.isNotBlank(qvo.getNotSignReason())){
                           avo.setOtherReason(null);
                           if("0".equals(qvo.getNotSignReason())){
                               avo.setNotSignReason("");
                           }else{
                               avo.setNotSignReason(qvo.getNotSignReason());
                               if("20".equals(qvo.getNotSignReason())){
                                   avo.setOtherReason(qvo.getOtherReason());
                               }
                           }
                       }

                   }
//                this.sysDao.getServiceDo().removePoFormSession(vo);
                   this.sysDao.getServiceDo().modify(avo);
                   this.jsons.setVo(avo);
                   this.jsons.setCode("800");
               }
           }
       }catch (Exception e){
            e.printStackTrace();

       }
        return "json";
    }

    public String findRole(){
        try{
            CdUser user = this.getSessionUser();
            boolean flag = false;
            if(user != null){
                if(StringUtils.isNotBlank(user.getAccount())){
                    if(user.getAccount().indexOf("admin")!=-1){
                        flag = true;
                    }
                }
                if(StringUtils.isNotBlank(user.getDrId())){
                    AppDrUser  drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,user.getDrId());
                    if(drUser != null){
                        if(drUser.getDrRole().indexOf(AppRoleType.SHENG.getValue())!=-1){
                            flag = true;
                        }else if(drUser.getDrRole().indexOf(AppRoleType.SHI.getValue())!=-1){
                            flag = true;
                        }else if(drUser.getDrRole().indexOf(AppRoleType.QU.getValue())!=-1){
                            flag = true;
                        }
                    }
                }
            }
            if(flag){
                this.jsons.setCode("800");
            }else{
                this.jsons.setCode("900");
            }
        }catch(Exception e){
            e.printStackTrace();
            new ActionException(getClass(), getAct(), e);
        }
        return "json";
    }

    /**
     * 异常数据查询
     * @return
     */
    public String findArchivingWebT(){
        try {
            AppArchivingCardPeopleQvo qvo = (AppArchivingCardPeopleQvo)getJsonLay(AppArchivingCardPeopleQvo.class);
            if(qvo==null){
                qvo=new AppArchivingCardPeopleQvo();
            }
            CdUser user = this.getSessionUser();
            if(user!=null){
                if(StringUtils.isNotBlank(user.getDrId())){
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,user.getDrId());
                    if(drUser != null){
                        if(drUser.getDrRole().equals(AppRoleType.YISHENG.getValue())){
                            qvo.setRole(AppRoleType.YISHENG.getValue());
                            qvo.setDrId(drUser.getId());
                            String teamIds = sysDao.getAppTeamMemberDao().findByDrId(drUser.getId());
                            qvo.setTeamIds(teamIds);
                        }
                    }
                }
            }
            List<AppArchivingCardPeopeEntity> ls = sysDao.getAppArchivingCardPeopeDao().findPeopleListT(qvo);
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }

    public String findArchivingXxWebExcelT(){
        try {
            AppArchivingCardPeopleQvo qvo = (AppArchivingCardPeopleQvo)getJsonLay(AppArchivingCardPeopleQvo.class);
            if(qvo==null){
                qvo=new AppArchivingCardPeopleQvo();
            }
            if(StringUtils.isNotBlank(qvo.getPatientName())){
                String selStr=java.net.URLDecoder.decode(qvo.getPatientName(),"UTF-8");
                qvo.setPatientName(selStr);
            }
            if("0".equals(qvo.getJdSourceType())){
                if(StringUtils.isNotBlank(qvo.getPatientNeighborhoodCommittee())){
                    String selStr=java.net.URLDecoder.decode(qvo.getPatientNeighborhoodCommittee(),"UTF-8");
                    qvo.setPatientNeighborhoodCommittee(selStr);
                }
            }
            String type = getRequest().getParameter("typeNum");
            String numberCount = getRequest().getParameter("numberCount");
            if(type.equals("2")){
                qvo.setPageSize(99999999);
            }else{
                qvo.setPageSize(Integer.valueOf(numberCount));
            }
            CdUser user = this.getSessionUser();
            if(user!=null){
                if(StringUtils.isNotBlank(user.getDrId())){
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,user.getDrId());
                    if(drUser != null){
                        if(drUser.getDrRole().equals(AppRoleType.YISHENG.getValue())){
                            qvo.setRole(AppRoleType.YISHENG.getValue());
                            String teamIds = sysDao.getAppTeamMemberDao().findByDrId(drUser.getId());
                            qvo.setTeamIds(teamIds);
                        }
                    }
                }
            }
            List<AppArchivingCardPeopeEntity> ls = sysDao.getAppArchivingCardPeopeDao().findPeopleListT(qvo);
            ExcelUtil<AppArchivingCardPeopeEntity> ex = new ExcelUtil<AppArchivingCardPeopeEntity>();
            String[] headers = null;
            String[] datasetName =null;
            if("0".equals(qvo.getJdSourceType())){
                headers = new String[]{ "健康档案号","县","街道(乡镇)","社区(村)","姓名","身份证","联系电话","签约状态","是否脱贫","未签原因","对象类型"};
                datasetName= new String[]{ "rhfId","addrCountyName","addrRuralName","addrVillageName","patientName","patientIdno","patientTel","signState","isNotPoverty","notSignReason","provincialInsurance"};
            }else{
                headers = new String[]{ "健康档案号","县","街道(乡镇)","社区(村)","姓名","身份证","联系电话","低保户","特困户","签约状态","是否脱贫","未签原因"};
                datasetName= new String[]{ "rhfId","addrCountyName","addrRuralName","addrVillageName","patientName","patientIdno","patientTel","lowInsured","poorHouseholds","signState","isNotPoverty","notSignReason"};
            }

            getResponse().reset();
            getResponse().setContentType("application/vnd..ms-excel");
            String titless = "";
            Map<String,Object> returnMap = sysDao.getAppArchivingCardPeopeDao().findMapT(qvo);
            if(returnMap != null){
                titless += " 建档立卡人数："+returnMap.get("totalCount").toString();
                titless += "; 未建档人数："+ returnMap.get("wjd").toString();
                titless += "; 未建档已签约人数："+ returnMap.get("wjdyqy").toString();
                titless += "; 已建档未签约人数："+ returnMap.get("yjdwqy").toString();
                titless += "; 脱贫人数："+ returnMap.get("tp").toString();
                titless += "; 异常数据："+ returnMap.get("ycs").toString();
//                titless += "; 重复数据："+ returnMap.get("cfs").toString();
            }
            String titles = "";
            if(StringUtils.isNotBlank(qvo.getPatientNeighborhoodCommittee())){
                if("0".equals(qvo.getJdSourceType())){
                    CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientStreet());
                    if(address != null){
                        titles = address.getAreaName()+qvo.getPatientNeighborhoodCommittee()+"建档立卡居民花名册";
                    }
                }else{
                    CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientNeighborhoodCommittee());
                    if(address != null){
                        titles = address.getAreaName()+"建档立卡居民花名册";
                    }
                }

            }else if(StringUtils.isNotBlank(qvo.getPatientStreet())){
                CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientStreet());
                if(address != null){
                    titles = address.getAreaName()+"建档立卡居民花名册";
                }
            }else if(StringUtils.isNotBlank(qvo.getPatientArea())){
                CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientArea());
                if(address != null){
                    titles = address.getAreaName()+"建档立卡居民花名册";
                }
            }else if(StringUtils.isNotBlank(qvo.getPatientCity())){
                CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientCity());
                if(address != null){
                    titles = address.getAreaName()+"建档立卡居民花名册";
                }
            }else{
                titles = "福建省建档立卡居民花名册";
            }
            if(StringUtils.isNotBlank(qvo.getNotConfirm())){
                titles+="(异常)";
            }
            String test = titles;
            String orgName = "";
            if(StringUtils.isNotBlank(user.getHospId())){
                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                if(dept!= null){
                    orgName = dept.getHospName();
                }
            }else {
                orgName = user.getDeptName();
            }
            if(StringUtils.isNotBlank(orgName)){
                titles+=";;;  填报机构："+orgName+"                     填报日期："+ExtendDate.getChineseYMD(Calendar.getInstance());
            }else{
                titles+=";;; 填报日期："+ExtendDate.getChineseYMD(Calendar.getInstance());
            }
            getResponse().setHeader("content-Disposition","attachment;filename="+ URLEncoder.encode(test+".xls","utf-8"));
            ex.exportExcell(test,headers,datasetName, ls, getResponse().getOutputStream(),"",titles,titless);
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return null;
    }

    public String deleteState(){
        try {
            AppArchivingCardPeopleQvo qvo = (AppArchivingCardPeopleQvo)getJsonLay(AppArchivingCardPeopleQvo.class);
            if(qvo==null){
                qvo=new AppArchivingCardPeopleQvo();
            }
            CdUser user = this.getSessionUser();
            if(user!=null){
                if(StringUtils.isNotBlank(user.getDrId())){
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,user.getDrId());
                    if(drUser != null){
                        if(drUser.getDrRole().equals(AppRoleType.YISHENG.getValue())){
                            qvo.setRole(AppRoleType.YISHENG.getValue());
                            qvo.setDrId(drUser.getId());
                            String teamIds = sysDao.getAppTeamMemberDao().findByDrId(drUser.getId());
                            qvo.setTeamIds(teamIds);
                        }
                    }
                }
            }
            List<AppArchivingCardPeopeEntity> ls = sysDao.getAppArchivingCardPeopeDao().findPeopleListT(qvo);
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }

    /**
     * 删除
     * @return
     */
    public String archivingDelete(){
        try{
            AppArchivingCardPeopleQvo qvo=(AppArchivingCardPeopleQvo)getVoJson(AppArchivingCardPeopleQvo.class);
            if(qvo!=null){
                sysDao.getSysLogBusinessDo().addSysLog("建档立卡居民","APP_ARCHIVINGCARD_PEOPLE",qvo.getId(),getLogVoList(),getSessionUser());
                AppArchivingCardPeople avo=(AppArchivingCardPeople) sysDao.getServiceDo().find(AppArchivingCardPeople.class,qvo.getId());
                if(avo!=null){
                    if(StringUtils.isNotBlank(qvo.getDelReason())){
                        avo.setDelReason(qvo.getDelReason());
                        avo.setDelState("1");
                    }
                    this.sysDao.getServiceDo().modify(avo);
                    this.jsons.setVo(avo);
                    this.jsons.setCode("800");
                }
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return "json";
    }

    /**
     * 注销
     * @return
     */
    public String archivingRevoke(){
        try{
            AppArchivingCardPeopleQvo qvo=(AppArchivingCardPeopleQvo)getVoJson(AppArchivingCardPeopleQvo.class);
            if(qvo!=null){
                sysDao.getSysLogBusinessDo().addSysLog("建档立卡居民","APP_ARCHIVINGCARD_PEOPLE",qvo.getId(),getLogVoList(),getSessionUser());
                AppArchivingCardPeople avo=(AppArchivingCardPeople) sysDao.getServiceDo().find(AppArchivingCardPeople.class,qvo.getId());
                if(avo!=null){
                    if(StringUtils.isNotBlank(qvo.getRevokeState())){
                        avo.setDelState(qvo.getRevokeState());
                        avo.setRevokeDate(ExtendDate.getCalendar(qvo.getRevokeDate()));
                        if("4".equals(qvo.getRevokeState())){//注销死亡
                            avo.setRevokeReason(qvo.getRevokeReason());
                        }else{//注销失访
                            avo.setRevokeReason(null);
                        }
                    }
                    this.sysDao.getServiceDo().modify(avo);
                    this.jsons.setVo(avo);
                    this.jsons.setCode("800");
                }
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return "json";
    }

    /**
     * 判断数据是不是删除状态
     * @return
     */
    public String findDeleteState(){
        try{
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                AppArchivingCardPeople people = (AppArchivingCardPeople)sysDao.getServiceDo().find(AppArchivingCardPeople.class,id);
                if(people != null){
                    if("1".equals(people.getDelState())){
                        this.jsons.setCode("900");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            new ActionException(getClass(), getAct(), e);
        }
        return "json";
    }

    /**
     * 查询社区名称集合
     * @return
     */
    public String findJnameList(){
        try{
            AppAddressQvo qvo = (AppAddressQvo) this.getVoJson(AppAddressQvo.class);
            if(StringUtils.isNotBlank(qvo.getUpId())){
                List<AppArchivingCardAddrEntity> list = sysDao.getAppArchivingCardPeopeDao().findJnameList(qvo.getUpId());
                if(list != null && list.size()>0){
                    this.getJsons().setRows(list);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
        }
        return "json";
    }
}
