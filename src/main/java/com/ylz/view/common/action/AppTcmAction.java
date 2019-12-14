package com.ylz.view.common.action;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppGuideTemplate;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppTcmSyndrome;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.*;
import com.ylz.bizDo.jtapp.commonVo.AppTcmGuideListQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTcmGuideQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTcmQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonDrPartientType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/8/4.
 */
@SuppressWarnings("all")
@Action(
        value="appTcm",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class AppTcmAction extends CommonAction {
    /**
     * 保存中医体质辨识
     * @return
     */
    public String saveTcm(){
        try{
            AppTcmQvo qvo = (AppTcmQvo)getAppJson(AppTcmQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(CommonDrPartientType.huanzhe.getValue().equals(qvo.getType())){
                    AppPatientUser user = this.getAppPatientUser();
                    if(user!=null){
                        qvo.setUserId(user.getId());
                        qvo.setUserName(user.getPatientName());
                    }
                }else if(CommonDrPartientType.yisheng.getValue().equals(qvo.getType())){
                    AppDrUser drUser = this.getAppDrUser();
                    if(drUser!=null){
                        qvo.setDrId(drUser.getId());
                        qvo.setDrName(drUser.getDrName());
                    }
                }
                List<AppTcmEntity> ls=sysDao.getAppTcmSyndromeDao().saveTcm(qvo);
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
     * 查询中医体质辨识列表
     * @return
     */
    public String findTcmList(){
        try{
            AppTcmQvo qvo = (AppTcmQvo)getAppJson(AppTcmQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppTcmListEntity vo = sysDao.getAppTcmSyndromeDao().findList(qvo);
                if(vo!=null){
                    this.getAjson().setVo(vo);
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setMsgCode("800");
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
     * 发送中医药保健指导
     * @return
     */
    public String fsGuide(){
        try{
            AppTcmGuideQvo qvo = (AppTcmGuideQvo)getAppJson(AppTcmGuideQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                    sysDao.getAppTcmSyndromeDao().fsGuide(qvo);
                    this.getAjson().setMsg("发送成功");
                    this.getAjson().setMsgCode("800");
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
     * 查询需中医体质辨识对象
     * @return
     */
    public String findPeople(){
        try{
            AppTcmQvo qvo = (AppTcmQvo)getAppJson(AppTcmQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                //AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,qvo.getTeamId());
                List<AppTcmPeopleEntity> ls = sysDao.getAppTcmSyndromeDao().findPeople(qvo);
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
     * 添加中医药体质辨识指导
     * @return
     */
    public String addTcmGuide(){
        try {
            AppTcmGuideQvo qvo = (AppTcmGuideQvo)getAppJson(AppTcmGuideQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    qvo.setDrId(user.getId());
                    qvo.setHospId(user.getDrHospId());
                }
                AppTcmGuideEntity vo = sysDao.getAppGuideTemplateDao().addTcmGuide(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setVo(vo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询中医药体质辨识指导列表
     * @return
     */
    public String findGuideList(){
        try{
            AppTcmGuideQvo qvo = (AppTcmGuideQvo)getAppJson(AppTcmGuideQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppTcmGuideEntity> ls = sysDao.getAppGuideTemplateDao().findZyyGuide(qvo);
                this.getAjson().setQvo(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
    /**
     * 查询体质类型和中医药保健指导类型
     * @return
     */
    public String findTypeList(){
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            List<AppMeddleEntity> tzlx = this.getSysDao().getCodeDao().findMeddle(CodeGroupConstrats.GROUP_TZLX, CommonEnable.QIYONG.getValue());
            List<AppMeddleEntity> zdlx = this.getSysDao().getCodeDao().findMeddle(CodeGroupConstrats.GROUP_ZYYBJZD, CommonEnable.QIYONG.getValue());
            map.put("tzlx",tzlx);
            map.put("zdlx",zdlx);
            this.getAjson().setMap(map);
            this.getAjson().setMsgCode("800");
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 修改中医药保健指导
     * @return
     */
    public String modifyGuide(){
        try{
            AppTcmGuideQvo qvo = (AppTcmGuideQvo)getAppJson(AppTcmGuideQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    qvo.setDrId(user.getId());
                }
                AppTcmGuideEntity vo = sysDao.getAppGuideTemplateDao().modifyTcmGuide(qvo);
                if(vo!=null){
                    this.getAjson().setVo(vo);
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsg("修改失败");
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
     * 删除中医药保健指导模板
     * @return
     */
    public String delete(){
        try{
            AppTcmGuideQvo qvo = (AppTcmGuideQvo)getAppJson(AppTcmGuideQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    AppGuideTemplate gt = (AppGuideTemplate)sysDao.getServiceDo().find(AppGuideTemplate.class,qvo.getId());
                    if(gt!=null){
                        if(gt.getGuideCreateId().equals(drUser.getId())){
                            sysDao.getServiceDo().delete(gt);
                            this.getAjson().setMsgCode("800");
                            this.getAjson().setMsg("删除成功");
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("不是本人创建的模板不能删除");
                        }
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
     * 中医体质辨识答题记录
     * @return
     */
    public String findAnswer(){
        try{
            AppTcmGuideQvo qvo = (AppTcmGuideQvo)getAppJson(AppTcmGuideQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppTcmSyndrome vo =(AppTcmSyndrome)sysDao.getServiceDo().find(AppTcmSyndrome.class,qvo.getId());
                List<AppTcmAnswerEntity> ls = new ArrayList<AppTcmAnswerEntity>();
                if(vo!=null){
                    if(StringUtils.isNotBlank(vo.getTcmQuestionValue())){
                        String[] wts = vo.getTcmQuestionValue().split(";");
                        String[] xzs = vo.getTcmChooseNum().split(";");
                        String[] dfs = vo.getTcmScode().split(";");
                        for(int i=0;i<wts.length;i++){
                            AppTcmAnswerEntity table = new AppTcmAnswerEntity();
                            table.setTh(wts[i]);
                            //根据题号返回题目内容
                            CdCode valuetm = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TMNR,wts[i]);
                            if(valuetm != null){
                                table.setTmnr(valuetm.getCodeTitle());
                            }
                            table.setXx(xzs[i]);
                            //根据选项返回答案内容
                            String group = CodeGroupConstrats.GROUP_DANR;
                            if("9".equals(wts[i])){
                                group = CodeGroupConstrats.GROUP_DANRO;
                            }else if("14".equals(wts[i])){
                                group = CodeGroupConstrats.GROUP_DANRT;
                            }else if("17".equals(wts[i])){
                                group = CodeGroupConstrats.GROUP_DANRTT;
                            }else if("28".equals(wts[i])){
                                group = CodeGroupConstrats.GROUP_DANRF;
                            }
                            CdCode valueda = sysDao.getCodeDao().findCodeGroupValue(group,xzs[i]);
                            if(valueda != null){
                                table.setDanr(valueda.getCodeTitle());
                            }
                            table.setDf(dfs[i]);
                            ls.add(table);
                        }
                    }
                }
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询发送的指导记录
     * @return
     */
    public String findGuideResult(){
        try{
            AppTcmGuideQvo qvo = (AppTcmGuideQvo)getAppJson(AppTcmGuideQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppNewTcmLookEntity> ls = sysDao.getAppHealthMeddleDao().findByTcmGuide(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 添加中医药体质指导
     * @return
     */
    public String saveTcmGuide(){
        try{
            AppTcmGuideListQvo qvo = (AppTcmGuideListQvo)getAppJson(AppTcmGuideListQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                    qvo.setHospId(drUser.getDrHospId());
                    sysDao.getAppTcmSyndromeDao().saveTcmGuide(qvo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


    public String addTcmGuide1(){
        try {
            AppTcmGuideQvo qvo = (AppTcmGuideQvo)getAppJson(AppTcmGuideQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    qvo.setDrId(user.getId());
                    qvo.setHospId(user.getDrHospId());
                }
                List<AppTcmGuideEntity> ls = sysDao.getAppGuideTemplateDao().addTcmGuide1(qvo);
                AppTcmGuideEntity vo = sysDao.getAppGuideTemplateDao().addTcmGuide(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setVo(vo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询个人端中医药体质辨识记录
     * @return
     */
    public String findByPersonal(){
        try{
//            AppTcmQvo qvo = (AppTcmQvo)getAppJson(AppTcmQvo.class);
            AppTcmQvo qvo = new AppTcmQvo();
//            if(qvo==null){
//                this.getAjson().setMsg("参数格式错误");
//                this.getAjson().setMsgCode("900");
//            }else{
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
                qvo.setUserId(user.getId());
                AppTcmListEntity vo = sysDao.getAppTcmSyndromeDao().findByPersonal(qvo);
                if(vo!=null){
                    this.getAjson().setVo(vo);
                    this.getAjson().setMsgCode("800");
                }
            }
//            }

        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }


}
