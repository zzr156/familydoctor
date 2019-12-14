package com.ylz.view.common.action;

import com.ylz.bizDo.app.po.AppConsult;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppConDrEntiry;
import com.ylz.bizDo.jtapp.commonEntity.AppCousultEntity;
import com.ylz.bizDo.jtapp.commonVo.AppConsultQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrCount;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

/**
 * 咨询
 * Created by zzl on 2017/6/26.
 */
@SuppressWarnings("all")
@Action(
        value="appComcon",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class AppConsultComAction extends CommonAction {
    /**
     * 保存咨询记录
     * @return
     */
    public String saveCon(){
        try{
            AppConsultQvo qvo = (AppConsultQvo)getAppJson(AppConsultQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else {
                this.sysDao.getAppConsultDao().saveCon(qvo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询咨询记录
     * @return
     */
    public String findList(){
        try{
            AppConsultQvo qvo = (AppConsultQvo)getAppJson(AppConsultQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppCousultEntity> ls=this.sysDao.getAppConsultDao().findList(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setMsg("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 我回复的咨询
     * @return
     */
    public String findReply(){
        try{
            AppConsultQvo qvo = (AppConsultQvo)getAppJson(AppConsultQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppCousultEntity> ls = this.sysDao.getAppConsultDao().findReply(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setMsg("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 修改咨询状态
     * @return
     */
    public String modify(){
        try{
            AppConsultQvo qvo = (AppConsultQvo)getAppJson(AppConsultQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppConsult v = (AppConsult) this.sysDao.getServiceDo().find(AppConsult.class,qvo.getId());
                if(v!=null){
                    v.setConState("1");
                    this.sysDao.getServiceDo().modify(v);
                    this.getAjson().setMsg("800");
                    this.getAjson().setMsg("修改成功");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("系统错误,请联系管理员");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";

    }

    /**
     * 查询咨询回复信息
     * @return
     */
    public String findDetailed(){
        try{
            AppConsultQvo qvo = (AppConsultQvo)getAppJson(AppConsultQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isNotBlank(qvo.getId())){
                    List<AppCousultEntity> ls=this.sysDao.getAppConsultDao().findDetailed(qvo);
                    this.getAjson().setRows(ls);
                    this.getAjson().setMsg("800");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数值不能为空");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 咨询类型接口
     * @return
     */
    public String ConsultType(){
        try{
            List<CdCode> ls = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CONSULTTYPE, CommonEnable.QIYONG.getValue());
            this.getAjson().setRows(ls);
            this.getAjson().setMsgCode("800");
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 查询咨询医生列表
     * @return
     */
    public String drList(){
        try{

            AppDrCount qvo = (AppDrCount)getAppJson(AppDrCount.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppPatientUser user = this.getAppPatientUser();
                if(user!=null){
                    AppSignForm from = this.sysDao.getAppSignformDao().getSignFormUserId(user.getId());
                    if(from != null){
                        AppTeam team = (AppTeam) this.getSysDao().getServiceDo().find(AppTeam.class,from.getSignTeamId());
                        this.getAjson().setVo(team);
                    }
                    qvo.setPatientId(user.getId());
//                    List<AppConDrEntiry> ls = this.sysDao.getAppPatientUserDao().findDrById(user.getId());
                    List<AppConDrEntiry> ls = this.sysDao.getAppPatientUserDao().findDrByIdList(qvo);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setRows(ls);
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
