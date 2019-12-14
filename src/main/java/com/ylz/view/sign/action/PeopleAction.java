package com.ylz.view.sign.action;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.drEntity.AppDrTargetEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrTargetQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;

@SuppressWarnings("all")
@Action(
        value="peopleAction",
        results={
                @Result(name = "json", type = "json",params={"root","jsons","contentType", "application/json"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","contentType", "application/json"})
        }
)
public class PeopleAction extends CommonAction {


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

    //查询当前机构指标
    public String findPeopleByAreaCode(){
        try{
            String orgId=getRequest().getParameter("id");
            if(StringUtils.isNotBlank(orgId)) {
                AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,orgId);
                if(dept!=null) {
                    CdAddressPeople vo=sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                    int count = 0;//建档立卡数
                    if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                        CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(dept.getHospAreaCode(),"2"));
                        String sourType = "1";
                        if(cityCode != null){
                            if("0".equals(cityCode.getCodeTitle())){//查询医保数据
                                sourType = "3";
                            }
                        }
                        if(AreaUtils.getAreaCode(dept.getHospAreaCode()).length() > 6){
                            count = sysDao.getAppArchivingCardPeopeDao().findCountBySourceType(sourType,dept.getId(),null);
                        }else{
                            String areaCode =    AreaUtils.getAreaCode(dept.getHospAreaCode());
                            count = sysDao.getAppArchivingCardPeopeDao().findCountBySourceType(sourType,null,areaCode);
                        }
                    }
                    if(count > 0){
                        vo.setAreaEconomicJklm(String.valueOf(count));
                    }
                    getJsons().setVo(vo);
                }
            }
        }catch (Exception e){
            new ActionException(getClass(),getAct(),getJsons(),e);
        }
        return "json";
    }

    /**
     * 修改指标
     * @return
     */
    public String modify(){
        try{
            AppDrTargetQvo qvo = (AppDrTargetQvo)getVoJson(AppDrTargetQvo.class);
            if(qvo==null){
                this.getJsons().setCode("900");
                this.getJsons().setMsg("参数格式错误");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser != null){
                    if(StringUtils.isBlank(qvo.getOrgid())){
                        qvo.setOrgid(drUser.getDrHospId());
                    }
                }
                AppDrTargetEntity vo=this.sysDao.getCdAddressPeopleDao().modifyTarget(qvo);
                this.getJsons().setVo(vo);
                this.getJsons().setCode("800");
                this.getJsons().setMsg("修改成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg(e.getMessage());
        }
        return "json";
    }
}
