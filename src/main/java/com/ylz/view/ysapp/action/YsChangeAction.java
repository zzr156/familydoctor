package com.ylz.view.ysapp.action;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.commonEntity.AppPeopleHealthEntity;
import com.ylz.bizDo.jtapp.ysChangeEntity.*;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeCountQvo;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeSureQvo;
import com.ylz.packaccede.common.action.CommonAction;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**变更医生
 * Created by zzl on 2017/9/4.
 */
@SuppressWarnings("all")
@Action(
        value="ysChange",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsChangeAction extends CommonAction {
    /**
     * 根据团队id和医生id查询服务人群、健康情况、疾病状态统计数
     * @return
     */
    public String findCount(){
        try{
            YsChangeCountQvo qvo = (YsChangeCountQvo)getAppJson(YsChangeCountQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                }
                Map<String,Object> map = new HashMap<String,Object>();
                List<AppPeopleHealthEntity> lsCount = sysDao.getAppPatientUserDao().findTypeCountChange(qvo);
                map.put("count",lsCount);
                List<YsChangeCountEntity> ls =sysDao.getAppPatientUserDao().findByTypeChange(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setMap(map);
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
     * 查询变更医生列表
     * @return
     */
    public String findChangeDrList(){
        try{
            YsChangeCountQvo qvo = (YsChangeCountQvo)getAppJson(YsChangeCountQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                    qvo.setHospId(drUser.getDrHospId());
                }
                List<YsChangeDrEntity> ls = sysDao.getAppDrUserDao().findChangeDrList(qvo);
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
     * 查询变更医生团队
     * @return
     */
    public String findChangeTeam(){
        try{
            YsChangeCountQvo qvo = (YsChangeCountQvo)getAppJson(YsChangeCountQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setHospId(drUser.getDrHospId());
                }
                List<YsChangeTeamEntity> ls = sysDao.getAppTeamDao().findChangeTeam(qvo);
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
     * 提交变更
     * @return
     */
    public String submitChange(){
        try{
            YsChangeCountQvo qvo = (YsChangeCountQvo)getAppJson(YsChangeCountQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());//当前申请医生id
                    qvo.setHospId(drUser.getDrHospId());//当前申请医生医院id
                    String result = sysDao.getAppSignformDao().changeState(qvo);
                    if("true".equals(result)){
                        this.getAjson().setMsg("提交申请成功");
                        this.getAjson().setMsgCode("800");
                    }else if("false".equals(result)){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无人员信息");
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
     * 查询变更审核信息
     * @return
     */
    public String findChangeMessage(){
        try{
            YsChangeCountQvo qvo = (YsChangeCountQvo)getAppJson(YsChangeCountQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                    List<YsChangeMsgEntity> ls =  sysDao.getAppSignformDao().findChangeList(qvo);
                    this.getAjson().setRows(ls);
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
     * 同意或拒绝变更
     * @return
     */
    public String sureChange(){
        try{
            YsChangeSureQvo qvo = (YsChangeSureQvo)getAppJson(YsChangeSureQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                    qvo.setDrName(drUser.getDrName());
                }
                if(StringUtils.isBlank(qvo.getUpHpis())){
                    qvo.setUpHpis("1");
                }
                String msg = sysDao.getAppSignformDao().sureChange(qvo);
                if("true".equals(msg)){
                    this.getAjson().setMsg(qvo.getChangeType());
                    this.getAjson().setMsgCode("800");
                }else{
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
     * 查询变更记录
     * @return
     */
    public String findList(){
        try{
            AppDrUser drUser = this.getAppDrUser();
            if(drUser!=null){
                List<YsChangeMsgEntity> ls = sysDao.getAppSignformDao().findChangeHList(drUser.getId());
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
     * 查询变更成员列表信息
     * @return
     */
    public String findPeopleList(){
        try{
            YsChangeSureQvo qvo = (YsChangeSureQvo)getAppJson(YsChangeSureQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<YsChangePatientEntity> ls = sysDao.getAppPatientUserDao().findChangePatient(qvo);
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
     * 模糊查询人员列表
     * @return
     */
    public String findPeople(){
        try{
            YsChangeCountQvo qvo = (YsChangeCountQvo)getAppJson(YsChangeCountQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                }
                List<YsChangePeopleEntity> ls = sysDao.getAppPatientUserDao().findPeople(qvo);
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


}
