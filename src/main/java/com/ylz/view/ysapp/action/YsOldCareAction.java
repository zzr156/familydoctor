package com.ylz.view.ysapp.action;


import com.ylz.bizDo.app.po.AppHomeCareSetting;
import com.ylz.bizDo.app.po.AppOldCare;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.drEntity.AppManageCardEntity;
import com.ylz.bizDo.jtapp.drVo.AppManageCardQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamEntity;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.util.CopyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 居家养老action.
 */
@SuppressWarnings("all")
@Action(
        value="ysCare",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsOldCareAction extends CommonAction {

    /**
     * 新增居家养老管理单
     * @return
     */
    public String appAddOldCare(){
                try {
                        AppOldCare po = (AppOldCare)getAppJson(AppOldCare.class);
                        if(po != null){
                              sysDao.getServiceDo().add(po);
                              this.getAjson().setMsgCode("800");
                              this.getAjson().setMsg("保存成功");
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
     * 通过医生id查询管理单
     * @return
     */
    public String appFindOldCare(){
                try {
                    AppOldCare vo = (AppOldCare)getAppJson(AppOldCare.class);
                        if(vo != null){
                                List<AppOldCare> list = sysDao.getServiceDo().loadByPk(AppOldCare.class,"ocDrId",vo.getOcDrId());
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
     * 查询当前管理单
     * @return
     */
    public String appFindOne(){
        try {
            AppOldCare po = (AppOldCare)getAppJson(AppOldCare.class);
            if(po != null){
                AppOldCare oldCare = (AppOldCare) sysDao.getServiceDo().find(AppOldCare.class,po.getId());
                this.getAjson().setVo(oldCare);
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
     * 修改居家养老管理单
     * @return
     */
    public String appModifyOldCare(){
        try {
            AppOldCare po = (AppOldCare)getAppJson(AppOldCare.class);
            if(po != null){
                this.sysDao.getServiceDo().modify(po);
                this.getAjson().setMsg("修改成功");
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
     * 添加居家养老频率
     * @return
     */
    public String appAddManageCare(){
        try {
            AppHomeCareSetting vo = (AppHomeCareSetting)getAppJson(AppHomeCareSetting.class);
            if(vo != null){
                List<AppTeamEntity> lsTeam = this.getSysDao().getAppTeamDao().findTeam(vo.getHomeHospId());
                if(lsTeam != null && lsTeam.size() >0){
                    for(AppTeamEntity p : lsTeam){
                        AppHomeCareSetting v = new AppHomeCareSetting();
                        CopyUtils.Copy(vo,v);
                        v.setHomeCrateTime(Calendar.getInstance());
                        v.setHomeTeamId(p.getTeamId());
                        sysDao.getServiceDo().add(v);
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setMsg("保存成功");
                    }
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
     * 修改居家养老频率
     * @return
     */
    public String appModifyManageCare(){
        try {
            AppHomeCareSetting vo = (AppHomeCareSetting)getAppJson(AppHomeCareSetting.class);
            if(vo != null){
                AppHomeCareSetting p = (AppHomeCareSetting)this.getSysDao().getServiceDo().find(AppHomeCareSetting.class,vo.getId());
                if(p != null){
                    if(StringUtils.isBlank(vo.getHomeManageLevel())){
                        this.getAjson().setMsg("预警等级不能为空");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    }
                    p.setHomeManageCycle(vo.getHomeManageCycle());
                    p.setHomeManageFrequency(vo.getHomeManageFrequency());
                    p.setHomeManageLevel(vo.getHomeManageLevel());
                    p.setHomeManageStyle(vo.getHomeManageStyle());
                    p.setHomeManagePeminderDays(vo.getHomeManagePeminderDays());
                    //sysDao.getServiceDo().removePoFormSession(vo);
                    sysDao.getServiceDo().modify(p);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("修改成功");
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
     * 删除居家养老频率
     * @param id
     * @return
     */
    public String appDeleteManageCare(){
        try {
            AppManageCardQvo qvo = (AppManageCardQvo)getAppJson(AppManageCardQvo.class);
            if(qvo != null && qvo.getId()!=null){
                sysDao.getServiceDo().delete(AppHomeCareSetting.class,qvo.getId());
                this.getAjson().setMsg("删除成功");
                this.getAjson().setMsgCode("800");
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
     * 查询居家养老频率
     * @return
     */
    public String appManageCareByOne(){
        try {
            AppManageCardQvo qvo = (AppManageCardQvo)getAppJson(AppManageCardQvo.class);
            if(qvo != null){
                AppHomeCareSetting p = (AppHomeCareSetting)this.getSysDao().getServiceDo().find(AppHomeCareSetting.class,qvo.getId());
                this.getAjson().setVo(p);
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
     * 居家养老频率
     * @return
     */
    public String appManageCare(){
        try {
            AppManageCardQvo qvo = (AppManageCardQvo)this.getAppJson(AppManageCardQvo.class);
            if(qvo != null){
                Map<String, Object> map = new HashMap<String, Object>();
                //红黄绿标
                List<CdCode> lsColor = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_COLOR, CommonEnable.QIYONG.getValue());
                //居民方式
                List<CdCode> lsMange = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HOMECAREMANGE, CommonEnable.QIYONG.getValue());
                //居民周期
                List<CdCode> lsCycle = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HOMECARECYCLE, CommonEnable.QIYONG.getValue());
                //居民频次
                List<CdCode> lsFrequency = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HOMECAREFREQUENCY,CommonEnable.QIYONG.getValue());
                map.put("lsColor",lsColor);
                map.put("lsMange",lsMange);
                map.put("lsCycle",lsCycle);
                map.put("lsFrequency",lsFrequency);
                this.getAjson().setMap(map);
                //查询列表
                List<AppManageCardEntity> ls = this.getSysDao().getAppHomeCareSettingDao().findByTeamId(qvo);
                this.getAjson().setRows(ls);
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

}
