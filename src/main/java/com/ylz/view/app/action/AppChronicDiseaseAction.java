package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppLabelManageQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.jtapp.commonEntity.AppDiseaseLabelEntity;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.DiseaseType;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/7/24.
 */
@Action(
        value="appNCD",
        results={
                @Result(name="list", location="/intercept/chronicDisease/chronicDisease_list.jsp"),
                @Result(name="edit", location="/intercept/chronicDisease/chronicDisease_modify.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","contentType", "application/json"})
        }
)
public class AppChronicDiseaseAction extends CommonAction {
    private AppDiseaseLabelEntity vo;
    /**
     * 菜单链接
     * @return
     */
    public String forList(){
        return "list";
    }

    /**
     * 准备新增或修改
     * @return
     */
    public String forAddOrEdit(){

        return "edit";
    }

    /**
     * 查询list页面初始数据
     * @return
     */
    public String list() {
        try{
            AppLabelManageQvo qvo = (AppLabelManageQvo)getQvoJson(AppLabelManageQvo.class);
            CdUser user = this.getSessionUser();
            if(user != null){
                qvo.setOrgId(user.getHospId());
                if(user.getAccount().indexOf("admin")==-1){
                    if(StringUtils.isBlank(qvo.getOrgId())){
                        this.newMsgJson("找不到该用户机构");
                        return "json";
                    }
                    if(StringUtils.isNotBlank(user.getDrId())){
                        AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,user.getDrId());
                        if(drUser == null){
                            this.newMsgJson("找不到该用户医生信息");
                            return "json";
                        }else{
                            qvo.setRole(drUser.getDrRole());
                            qvo.setDrId(drUser.getId());
                        }
                    }
                }else{
                    qvo.setRole(AppRoleType.SHEQU.getValue());
                }
            }
            List<AppDiseaseLabelEntity> ls = sysDao.getAppLabelGroupDao().findLabelGroupByQvo(qvo);
            jsons.setRowsQvo(ls,qvo);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
    /**
     * 查询个人数据
     * @return
     */
    public String jsonByOne(){
        try{
            String signId = this.getRequest().getParameter("id");
            AppDiseaseLabelEntity vo = sysDao.getAppLabelGroupDao().findLabelGroupByOne(signId);
            this.setJsonVo(vo);
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }

    /**
     * 页面数据初始
     * @return
     */
    public String findCmmInit(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            //是否状态
            List<CdCode> sf = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON, CommonEnable.QIYONG.getValue());
            map.put("sf",sf);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
        }
        return "json";

    }

    /**
     * 修改
     * @return
     */
    public String modify(){
        try{
            AppDiseaseLabelEntity vo = (AppDiseaseLabelEntity)getVoJson(AppDiseaseLabelEntity.class);
            CdUser cdUser = this.getSessionUser();
            if(vo != null){
                List<AppMarkingLogItem> list = new ArrayList<>();
                AppSignForm form = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,vo.getId());
                if(form != null){
                    //查询患者信息
                    AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,form.getSignPatientId());
                    if(user != null){
                        user.setPatientX(vo.getxCoordinate());
                        user.setPatientY(vo.getyCoordinate());
                        sysDao.getServiceDo().modify(user);
                    }
                    //判断高血压
                    if("1".equals(vo.getHbpLabel())){
                        String oldValue = "";
                        String newValue = "";
                        //是高血压 查询疾病标签
                        AppLabelDisease disease = sysDao.getAppLabelGroupDao().findDiseaseByOne(DiseaseType.GXY.getValue(),form.getId());
                        if(disease == null){
                            disease = new AppLabelDisease();
                            disease.setLabelSignId(form.getId());
                            disease.setLabelTeamId(form.getSignTeamId());
                            oldValue = "gray";
                            if("0".equals(vo.getHbpLabelColor())){
                                disease.setLabelColor("gray");
                                newValue = "gray";
                            }else if("1".equals(vo.getHbpLabelColor())){
                                disease.setLabelColor("red");
                                newValue = "red";
                            }else if("2".equals(vo.getHbpLabelColor())){
                                disease.setLabelColor("yellow");
                                newValue = "yellow";
                            }else if("3".equals(vo.getHbpLabelColor())){
                                disease.setLabelColor("green");
                                newValue = "green";
                            }
                            disease.setLabelType("2");
                            disease.setLabelValue(DiseaseType.GXY.getValue());
                            disease.setLabelAreaCode(form.getSignAreaCode());
                            AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("2",DiseaseType.GXY.getValue());
                            if(manage != null){
                                disease.setLabelTitle(manage.getLabelTitle());
                                disease.setLabelId(manage.getId());
                            }
                            sysDao.getServiceDo().add(disease);
                        }else{//有的话改变颜色
                            oldValue = disease.getLabelColor();
                            if("0".equals(vo.getHbpLabelColor())){
                                disease.setLabelColor("gray");
                                newValue = "gray";
                            }else if("1".equals(vo.getHbpLabelColor())){
                                disease.setLabelColor("red");
                                newValue = "red";
                            }else if("2".equals(vo.getHbpLabelColor())){
                                disease.setLabelColor("yellow");
                                newValue = "yellow";
                            }else if("3".equals(vo.getHbpLabelColor())){
                                disease.setLabelColor("green");
                                newValue = "green";
                            }
                            sysDao.getServiceDo().modify(disease);
                        }
                        if(!newValue.equals(oldValue)){
                            AppMarkingLogItem item = new AppMarkingLogItem();
                            item.setBusinessId(disease.getId());
                            item.setKey("labelColor");
                            item.setType(DiseaseType.GXY.getValue());
                            item.setOldValue(oldValue);
                            item.setNewValue(newValue);
                            item.setBusinessTable("APP_LABEL_DISEASE");
                            list.add(item);
                        }
                    }
                    //判断糖尿病标签
                    if("1".equals(vo.getDmLabel())){
                        String oldValue = "";
                        String newValue = "";
                        //是糖尿病 判断是否有糖尿病疾病标签
                        AppLabelDisease disease = sysDao.getAppLabelGroupDao().findDiseaseByOne(DiseaseType.TNB.getValue(),form.getId());
                        if(disease == null){
                            //没有糖尿病疾病标签，添加疾病标签
                            disease = new AppLabelDisease();
                            disease.setLabelSignId(form.getId());
                            disease.setLabelTeamId(form.getSignTeamId());
                            oldValue = "gray";
                            if("0".equals(vo.getDmLabelColor())){
                                disease.setLabelColor("gray");
                                newValue = "gray";
                            }else if("1".equals(vo.getDmLabelColor())){
                                disease.setLabelColor("red");
                                newValue = "red";
                            }else if("2".equals(vo.getDmLabelColor())){
                                disease.setLabelColor("yellow");
                                newValue = "yellow";
                            }else if("3".equals(vo.getDmLabelColor())){
                                disease.setLabelColor("green");
                                newValue = "green";
                            }
                            disease.setLabelType("2");
                            disease.setLabelValue(DiseaseType.TNB.getValue());
                            disease.setLabelAreaCode(form.getSignAreaCode());
                            AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("2",DiseaseType.TNB.getValue());
                            if(manage != null){
                                disease.setLabelTitle(manage.getLabelTitle());
                                disease.setLabelId(manage.getId());
                            }
                            sysDao.getServiceDo().add(disease);
                        }else {
                            oldValue = disease.getLabelColor();
                            if("0".equals(vo.getDmLabelColor())){
                                disease.setLabelColor("gray");
                                newValue = "gray";
                            }else if("1".equals(vo.getDmLabelColor())){
                                disease.setLabelColor("red");
                                newValue = "red";
                            }else if("2".equals(vo.getDmLabelColor())){
                                disease.setLabelColor("yellow");
                                newValue = "yellow";
                            }else if("3".equals(vo.getDmLabelColor())){
                                disease.setLabelColor("green");
                                newValue = "green";
                            }
                            sysDao.getServiceDo().modify(disease);
                        }
                        if(!newValue.equals(oldValue)){
                            AppMarkingLogItem item = new AppMarkingLogItem();
                            item.setBusinessId(disease.getId());
                            item.setKey("labelColor");
                            item.setOldValue(oldValue);
                            item.setType(DiseaseType.TNB.getValue());
                            item.setNewValue(newValue);
                            item.setBusinessTable("APP_LABEL_DISEASE");
                            list.add(item);
                        }
                    }
                    //判断结核病标签
                    if("1".equals(vo.getTbLabel())){
                        String oldValue = "";
                        String newValue = "";
                        //是结核病 判断是否有结核病疾病标签
                        AppLabelDisease disease = sysDao.getAppLabelGroupDao().findDiseaseByOne(DiseaseType.JHB.getValue(),form.getId());
                        if(disease == null){
                            //没有结核病疾病标签，添加疾病标签
                            disease = new AppLabelDisease();
                            disease.setLabelSignId(form.getId());
                            disease.setLabelTeamId(form.getSignTeamId());
                            oldValue = "gray";
                            if("0".equals(vo.getTbLabelColor())){
                                disease.setLabelColor("gray");
                                newValue = "gray";
                            }else if("1".equals(vo.getTbLabelColor())){
                                disease.setLabelColor("red");
                                newValue = "red";
                            }else if("2".equals(vo.getTbLabelColor())){
                                disease.setLabelColor("yellow");
                                newValue = "yellow";
                            }else if("3".equals(vo.getTbLabelColor())){
                                disease.setLabelColor("green");
                                newValue = "green";
                            }
                            disease.setLabelType("2");
                            disease.setLabelValue(DiseaseType.JHB.getValue());
                            disease.setLabelAreaCode(form.getSignAreaCode());
                            AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("2",DiseaseType.JHB.getValue());
                            if(manage != null){
                                disease.setLabelTitle(manage.getLabelTitle());
                                disease.setLabelId(manage.getId());
                            }
                            sysDao.getServiceDo().add(disease);
                        }else {
                            oldValue = disease.getLabelColor();
                            if("0".equals(vo.getTbLabelColor())){
                                disease.setLabelColor("gray");
                                newValue = "gray";
                            }else if("1".equals(vo.getTbLabelColor())){
                                disease.setLabelColor("red");
                                newValue = "red";
                            }else if("2".equals(vo.getTbLabelColor())){
                                disease.setLabelColor("yellow");
                                newValue = "yellow";
                            }else if("3".equals(vo.getTbLabelColor())){
                                disease.setLabelColor("green");
                                newValue = "green";
                            }
                            sysDao.getServiceDo().modify(disease);
                        }
                        if(!newValue.equals(oldValue)){
                            AppMarkingLogItem item = new AppMarkingLogItem();
                            item.setBusinessId(disease.getId());
                            item.setKey("labelColor");
                            item.setOldValue(oldValue);
                            item.setType(DiseaseType.JHB.getValue());
                            item.setNewValue(newValue);
                            item.setBusinessTable("APP_LABEL_DISEASE");
                            list.add(item);
                        }
                    }
                    //判断严重精神病标签
                    if("1".equals(vo.getPmPLabel())){
                        String oldValue = "";
                        String newValue = "";
                        //是结核病 判断是否有结核病疾病标签
                        AppLabelDisease disease = sysDao.getAppLabelGroupDao().findDiseaseByOne(DiseaseType.YZJSZA.getValue(),form.getId());
                        if(disease == null){
                            //没有结核病疾病标签，添加疾病标签
                            disease = new AppLabelDisease();
                            disease.setLabelSignId(form.getId());
                            disease.setLabelTeamId(form.getSignTeamId());
                            oldValue = "gray";
                            if("0".equals(vo.getPmPLabelColor())){
                                disease.setLabelColor("gray");
                                newValue = "gray";
                            }else if("1".equals(vo.getPmPLabelColor())){
                                disease.setLabelColor("red");
                                newValue = "red";
                            }else if("2".equals(vo.getPmPLabelColor())){
                                disease.setLabelColor("yellow");
                                newValue = "yellow";
                            }else if("3".equals(vo.getPmPLabelColor())){
                                disease.setLabelColor("green");
                                newValue = "green";
                            }
                            disease.setLabelType("2");
                            disease.setLabelValue(DiseaseType.YZJSZA.getValue());
                            disease.setLabelAreaCode(form.getSignAreaCode());
                            AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("2",DiseaseType.YZJSZA.getValue());
                            if(manage != null){
                                disease.setLabelTitle(manage.getLabelTitle());
                                disease.setLabelId(manage.getId());
                            }
                            sysDao.getServiceDo().add(disease);
                        }else {
                            oldValue = disease.getLabelColor();
                            if("0".equals(vo.getPmPLabelColor())){
                                disease.setLabelColor("gray");
                                newValue = "gray";
                            }else if("1".equals(vo.getPmPLabelColor())){
                                disease.setLabelColor("red");
                                newValue = "red";
                            }else if("2".equals(vo.getPmPLabelColor())){
                                disease.setLabelColor("yellow");
                                newValue = "yellow";
                            }else if("3".equals(vo.getPmPLabelColor())){
                                disease.setLabelColor("green");
                                newValue = "green";
                            }
                            sysDao.getServiceDo().modify(disease);
                        }
                        if(!newValue.equals(oldValue)){
                            AppMarkingLogItem item = new AppMarkingLogItem();
                            item.setBusinessId(disease.getId());
                            item.setKey("labelColor");
                            item.setOldValue(oldValue);
                            item.setType(DiseaseType.YZJSZA.getValue());
                            item.setNewValue(newValue);
                            item.setBusinessTable("APP_LABEL_DISEASE");
                            list.add(item);
                        }
                    }
                    if(list != null && list.size()>0 && cdUser != null){
                        sysDao.getAppMarkingLogDao().saveMarkingLog(user.getId(),"分标管理",list,cdUser.getHospId(),cdUser.getUserName(),cdUser.getDrId());
                    }
                }
                this.newMsgJson(finalSuccessrMsg);
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }


    public AppDiseaseLabelEntity getVo() {
        return vo;
    }

    public void setVo(AppDiseaseLabelEntity vo) {
        this.vo = vo;
    }
}
