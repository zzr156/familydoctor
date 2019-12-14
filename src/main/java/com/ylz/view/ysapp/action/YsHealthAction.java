package com.ylz.view.ysapp.action;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppGuideTemplate;
import com.ylz.bizDo.jtapp.commonEntity.AppHealthEntiry;
import com.ylz.bizDo.jtapp.commonVo.AppEnshrineHealthQvo;
import com.ylz.bizDo.jtapp.commonVo.AppHealthEducationQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrHealthEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrMeddleEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrQvo;
import com.ylz.bizDo.jtapp.drVo.AppGuideTemplateQvo;
import com.ylz.bizDo.jtapp.drVo.AppHealthToQvo;
import com.ylz.bizDo.news.po.NewsTable;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

/**
 * 医生健康教育接口
 * Created by zzl on 2017/6/30.
 */
@SuppressWarnings("all")
@Action(
        value="ysHealth",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsHealthAction extends CommonAction {
    /**
     * 查询医生发的健康教育列表
     * @return
     */
    public String findList(){
        try {
            AppDrUser drUser = this.getAppDrUser();
            if(drUser!=null){
                List<AppDrHealthEntity> ls =this.sysDao.getAppUserHealthEDDao().findList(drUser.getId());
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
     * 查询模板健康教育列表
     * @return
     */
    public String findListMod(){
        try{
            AppDrQvo qvo = (AppDrQvo)getAppJson(AppDrQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setEnshrineId(drUser.getId());
                }
                List<AppHealthEntiry> ls = this.sysDao.getNewsTableDao().findList(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();;
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查看指导内容
     * @return
     */
    public String lookGuideOne(){
        try{
            AppDrQvo qvo = (AppDrQvo)getAppJson(AppDrQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrMeddleEntity vo = this.sysDao.getAppHealthMeddleDao().findById(qvo.getId());
                this.getAjson().setVo(vo);
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
     * 查询指导模板列表
     * @return
     */
    public String findGuideList(){
        try{
            AppDrQvo qvo = (AppDrQvo)getAppJson(AppDrQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                qvo.setHospId(drUser.getDrHospId());
                List<AppDrMeddleEntity> ls = this.sysDao.getAppHealthMeddleDao().findByQvo(qvo);
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
     * 查看指导模板内容
     * @return
     */
    public String lookGuideTwo(){
        try{
            AppDrQvo qvo = (AppDrQvo)getAppJson(AppDrQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrMeddleEntity vo = this.sysDao.getAppHealthMeddleDao().findByIdModdl(qvo.getId());
                this.getAjson().setVo(vo);
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
     * 医生收藏健康教育模板接口
     * @return
     */
    public String enshrineHD(){
        try{
            AppEnshrineHealthQvo qvo = (AppEnshrineHealthQvo) getAppJson(AppEnshrineHealthQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setUserId(drUser.getId());
                    qvo.setUserName(drUser.getDrName());
                    this.sysDao.getAppHealthEnshrineDao().enshrineHD(qvo);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("收藏成功");
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
     * 医生查询收藏的健康教育列表
     * @return
     */
    public String enshrineList(){
        try{
            AppDrQvo qvo = (AppDrQvo)this.getAppJson(AppDrQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setUserId(drUser.getId());
                    List<AppHealthEntiry> ls = this.sysDao.getAppHealthEnshrineDao().findList(qvo);
                    this.getAjson().setRows(ls);
                    this.getAjson().setMsgCode("800");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsgCode(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 删除健康教育模板
     * @return
     */
    public String deleteED(){
        try{
            AppDrQvo qvo = (AppDrQvo)this.getAppJson(AppDrQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isNotBlank(qvo.getId())){
                    this.sysDao.getServiceDo().delete(NewsTable.class,qvo.getId());
                    this.getAjson().setMsg("删除健康教育模板成功");
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsg("参数值不正确");
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
     * 删除健康指导模板
     * @return
     */
    public String delGuide(){
        try{
            AppDrQvo qvo = (AppDrQvo)this.getAppJson(AppDrQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isNotBlank(qvo.getId())){
                    this.sysDao.getServiceDo().delete(AppGuideTemplate.class,qvo.getId());
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("删除健康指导模板成功");
                }else{
                    this.getAjson().setMsg("参数值不正确");
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
     * 修改健康教育模板
     * @return
     */
    public String modifyHealthED(){
        try{
            AppHealthEducationQvo qvo = (AppHealthEducationQvo)getAppJson(AppHealthEducationQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppHealthEntiry vo= this.sysDao.getNewsTableDao().modifyHealth(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setVo(vo);
                this.getAjson().setMsg("修改健康教育成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 修改健康指导模板
     * @return
     */
    public String modifyHealthGuide(){
        try{
            AppGuideTemplateQvo qvo = (AppGuideTemplateQvo)getAppJson(AppGuideTemplateQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    qvo.setUserId(user.getId());
                }
                AppDrMeddleEntity vo=this.sysDao.getAppGuideTemplateDao().modifyGuide(qvo);
                this.getAjson().setVo(vo);
                this.getAjson().setMsg("修改指导成功");
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
     * 根据权限发送健康教育
     * @return
     */
    public String fsHealthRole(){
        try{
            AppHealthEducationQvo qvo = (AppHealthEducationQvo)getAppJson(AppHealthEducationQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                qvo.setDrId(drUser.getId());
                if(drUser.getDrRole().indexOf(AppRoleType.SHENG.getValue())!=-1||drUser.getDrRole().indexOf(AppRoleType.SHI.getValue())!=-1
                        ||drUser.getDrRole().indexOf(AppRoleType.QU.getValue())!=-1||drUser.getDrRole().indexOf(AppRoleType.SHEQU.getValue())!=-1
                        ||drUser.getDrRole().indexOf(AppRoleType.YISHENG.getValue())!=-1){
                    sysDao.getNewsTableDao().fsHealthRole(qvo);
                    this.getAjson().setMsgCode("800");
                }else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("无权限");
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
     * 一键发送健康教育
     * @return
     */
    public String fsHealthToAll(){
        try{
            AppHealthToQvo qvo = (AppHealthToQvo)getAppJson(AppHealthToQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser user = this.getAppDrUser();
                if(StringUtils.isBlank(qvo.getTeamId())){
                    this.getAjson().setMsg("团队id不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getNewsId())){
                    this.getAjson().setMsg("模板不能为空");
                    this.getAjson().setMsgCode("900");
                    return "ajson";
                }
                if(user!=null){
                    qvo.setDrId(user.getId());
                    String str = sysDao.getNewsTableDao().fsHealthAll(qvo);
                    if("true".equals(str)){
                        this.getAjson().setMsg("发送成功");
                        this.getAjson().setMsgCode("800");
                    }else{
                        this.getAjson().setMsg(str);
                        this.getAjson().setMsgCode("900");
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
}
