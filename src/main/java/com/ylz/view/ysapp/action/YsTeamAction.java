package com.ylz.view.ysapp.action;

import com.ylz.bizDo.jtapp.commonEntity.AppMeddleEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrChangeInfoEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTeamEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTeamMemEntiry;
import com.ylz.bizDo.jtapp.drVo.AppDrHospDrQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrTeamQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

/**医生团队接口
 * Created by zzl on 2017/7/10.
 */
@SuppressWarnings("all")
@Action(
        value="ysTeam",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsTeamAction extends CommonAction {

    /**
     * 添加团队
     * @return
     */
    public String addTeam(){
        try{
            AppDrTeamQvo qvo = (AppDrTeamQvo)this.getAppJson(AppDrTeamQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrTeamEntity vo = this.sysDao.getAppTeamDao().saveTeam(qvo);
                this.getAjson().setVo(vo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("添加团队成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询团队列表
     * @return
     */
    public String findTeam(){
        try{
            AppDrTeamQvo qvo = (AppDrTeamQvo)this.getAppJson(AppDrTeamQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppDrTeamEntity> ls = this.sysDao.getAppTeamDao().findTeamList(qvo);
                this.getAjson().setRows(ls);
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
     * 添加团队成员
     * @return
     */
    public String addTeamMem(){
        try{
            AppDrTeamQvo qvo = (AppDrTeamQvo)this.getAppJson(AppDrTeamQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                this.sysDao.getAppTeamMemberDao().addTeamMem(qvo);
                this.getAjson().setMsg("添加成员成功");
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
     * 查询团队成员
     * @return
     */
    public String findTeamMem(){
        try{
            AppDrTeamQvo qvo = (AppDrTeamQvo)this.getAppJson(AppDrTeamQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppDrTeamMemEntiry> ls = this.sysDao.getAppTeamMemberDao().findTeamMem(qvo.getId());
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
     * 删除团队
     * @return
     */
    public String delTeam(){
        try{
            AppDrTeamQvo qvo = (AppDrTeamQvo)this.getAppJson(AppDrTeamQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                this.sysDao.getAppTeamDao().delTeam(qvo.getId());
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("删除团队成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 删除团队成员
     * @return
     */
    public String delTeamMem(){
        try{
            AppDrTeamQvo qvo = (AppDrTeamQvo)this.getAppJson(AppDrTeamQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                this.sysDao.getAppTeamMemberDao().delTeamMem(qvo.getId());

//                sysDao.getServiceDo().delete(AppTeamMember.class,qvo.getId());
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("删除成员成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 修改团队接口
     * @return
     */
    public String modifyTeam(){
        try{
            AppDrTeamQvo qvo = (AppDrTeamQvo)this.getAppJson(AppDrTeamQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrTeamEntity vo=this.sysDao.getAppTeamDao().modifyTeam(qvo);
                this.getAjson().setVo(vo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("修改团队信息成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 修改团队成员信息
     * @return
     */
    public String modifyTeamMem(){
        try{
            AppDrTeamQvo qvo = (AppDrTeamQvo)this.getAppJson(AppDrTeamQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrTeamMemEntiry vo = this.sysDao.getAppTeamMemberDao().modifyTeamMem(qvo);
                if(vo==null){
                    this.getAjson().setMsg("未找到成员信息");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                this.getAjson().setMsgCode("800");
                this.getAjson().setVo(vo);
                this.getAjson().setMsg("修改成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询医院下的家庭医生列表
     * @return
     */
    public String findHospDr(){
        try{
            AppDrHospDrQvo qvo = (AppDrHospDrQvo)this.getAppJson(AppDrHospDrQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppDrChangeInfoEntity> ls = this.sysDao.getAppDrUserDao().findDrList(qvo);
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
     * 查询不在此团队下的本机构医生列表
     *
     * @return
     */
    public String findNoTeamDr(){
        try{
            AppDrTeamQvo qvo = (AppDrTeamQvo)getAppJson(AppDrTeamQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppDrChangeInfoEntity> ls = this.sysDao.getAppTeamMemberDao().findDr(qvo);
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
     * 查询成员医生的工作类型
     * @return
     */
    public String findWorkType(){
        try{
            List<AppMeddleEntity> ls = this.getSysDao().getCodeDao().findMeddle(CodeGroupConstrats.GROUP_WORKTYPE, CommonEnable.QIYONG.getValue());
            this.getAjson().setRows(ls);
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 根据医生id查询医生所在的团队列表
     * @return
     */
    public String findOneByTeam(){
        try{
            AppDrTeamQvo qvo = (AppDrTeamQvo)getAppJson(AppDrTeamQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppDrTeamEntity> ls=this.sysDao.getAppTeamDao().findOneByTeam(qvo.getDrId());
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
