package com.ylz.view.sign.action;

import com.ylz.bizDo.app.entity.AppArchivingCardPeopeEntity;
import com.ylz.bizDo.app.entity.AppManageArchivingCountEntity;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.vo.AppArchivingCardPeopleQvo;
import com.ylz.bizDo.app.vo.AppManageArchivingCountQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/7/17.
 */
@SuppressWarnings("all")
@Action(
        value="manageArchivingAction",
        results={
                @Result(name = "json", type = "json",params={"root","jsons","contentType", "application/json"}),
                @Result(name = "jList", type = "json",params={"root","jList","contentType", "application/json"}),
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","excludeNullProperties","true","contentType", "application/json"})
        }
)
public class ManageArchivingAction extends CommonAction {
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
    //统计数据信息查询
    public String findManageArchivingWeb(){
        try {
            AppManageArchivingCountQvo qvo = (AppManageArchivingCountQvo)getJsonLay(AppManageArchivingCountQvo.class);
            if(qvo==null){
                qvo=new AppManageArchivingCountQvo();
            }
            CdUser user = this.getSessionUser();
            List<AppManageArchivingCountEntity> ls = sysDao.getAppSignAnalysisDao().findCountList(qvo);
//                qvo.setItemCount(ls.size());
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }
    //导出数据
    public String findManageArchivingXxWebExcel(){
        try {
            AppManageArchivingCountQvo qvo = (AppManageArchivingCountQvo)getJsonLay(AppManageArchivingCountQvo.class);
            if(qvo==null){
                qvo=new AppManageArchivingCountQvo();
            }
            String type = getRequest().getParameter("typeNum");
            String numberCount = getRequest().getParameter("numberCount");
            if(type.equals("2")){
                qvo.setPageSize(99999999);
            }else{
                qvo.setPageSize(Integer.valueOf(numberCount));
            }
            CdUser user = this.getSessionUser();
            List<AppManageArchivingCountEntity> ls = sysDao.getAppSignAnalysisDao().findCountList(qvo);
            ExcelUtil<AppManageArchivingCountEntity> ex = new ExcelUtil<AppManageArchivingCountEntity>();
            String titles = "";
            if(StringUtils.isNotBlank(qvo.getHospId())){
                AppHospDept dd = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
                if(dd!=null){
                    titles = dd.getHospName()+"建档立卡居民签约情况汇总表";
                }
            }else if(StringUtils.isNotBlank(qvo.getPatientArea())){
                CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientArea());
                if(address != null){
                    titles = address.getAreaName()+"建档立卡居民签约情况汇总表";
                }
            }else if(StringUtils.isNotBlank(qvo.getPatientCity())){
                CdAddress address = sysDao.getCdAddressDao().findByCacheCode(qvo.getPatientCity());
                if(address != null){
                    titles = address.getAreaName()+"建档立卡居民签约情况汇总表";
                }
            }else{
                titles = "福建省建档立卡居民签约情况汇总表";
            }
            if(StringUtils.isNotBlank(qvo.getHospId())){
                String[] headers = { "地区名称","第一责任人","团队成员","已签约","一般人群","0-6岁儿童","老年人","高血压","糖尿病","孕产妇","精神病","结核病","残疾人"};
                String[] datasetName={ "name","dyzrr","tdcy","signCount","ptr","et","lnr","gxy","tnb","ycf","jsb","jhb","cjr"};
                getResponse().reset();
                getResponse().setContentType("application/vnd..ms-excel");
                getResponse().setHeader("content-Disposition","attachment;filename="+ URLEncoder.encode("建档立卡居民签约情况汇总表.xls","utf-8"));
                ex.exportExcell("建档立卡居民签约情况汇总表",headers,datasetName, ls, getResponse().getOutputStream(),"",titles,"");
            }else if(StringUtils.isNotBlank(qvo.getPatientArea())){
                String[] headers = { "地区名称","已签约","一般人群","0-6岁儿童","老年人","高血压","糖尿病","孕产妇","精神病","结核病","残疾人"};
                String[] datasetName={ "name","signCount","ptr","et","lnr","gxy","tnb","ycf","jsb","jhb","cjr"};
                getResponse().reset();
                getResponse().setContentType("application/vnd..ms-excel");
                getResponse().setHeader("content-Disposition","attachment;filename="+ URLEncoder.encode("建档立卡居民签约情况汇总表.xls","utf-8"));
                ex.exportExcell("建档立卡居民签约情况汇总表",headers,datasetName, ls, getResponse().getOutputStream(),"",titles,"");
            }else{
                String[] headers = { "地区名称","应签约","已签约","一般人群","0-6岁儿童","老年人","高血压","糖尿病","孕产妇","精神病","结核病","残疾人"};
                String[] datasetName={ "name","shouldSignCount","signCount","ptr","et","lnr","gxy","tnb","ycf","jsb","jhb","cjr"};
                getResponse().reset();
                getResponse().setContentType("application/vnd..ms-excel");
                getResponse().setHeader("content-Disposition","attachment;filename="+ URLEncoder.encode("建档立卡居民签约情况汇总表.xls","utf-8"));
                ex.exportExcell("建档立卡居民签约情况汇总表",headers,datasetName, ls, getResponse().getOutputStream(),"",titles,"");
            }
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());


        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return null;
    }
    public String findHospByArea(){
        try {
            AppManageArchivingCountQvo qvo = (AppManageArchivingCountQvo) getJsonLay(AppManageArchivingCountQvo.class);
            if (qvo == null) {
                qvo = new AppManageArchivingCountQvo();
            }
            CdUser user = this.getSessionUser();
//            String areaCode = AreaUtils.getAreaCode(qvo.getPatientStreet());
//            if(areaCode.length()<9){
//                List<AppHospDept> list = sysDao.getAppHospDeptDao().findHospByStreet(qvo.getPatientStreet());
//            }
            List<AppHospDept> listHosp = sysDao.getAppHospDeptDao().findHospByStreet(qvo.getPatientStreet(),qvo.getIsFindState());
//                qvo.setItemCount(ls.size());
            jsons.setRows(listHosp);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
}
