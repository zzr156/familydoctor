package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.po.AppServeRole;
import com.ylz.bizDo.app.po.AppSignSetting;
import com.ylz.bizDo.app.vo.AppSignSetQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdDept;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.LabelManageType;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Role;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 市权限签约设置
 * Created by zzl on 2018/1/15.
 */
@SuppressWarnings("all")
@Action(
        value="appSignSet",
        results={
                @Result(name="list", location="/intercept/app/signSet/appSignSet_list.jsp"),
                @Result(name="edit", location="/intercept/app/signSet/appSignSet_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppSignSetAction extends CommonAction {

    public AppSignSetting vo;

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
     * 查询
     * @return
     */
    public String list(){
        try{
            AppSignSetQvo qvo = (AppSignSetQvo)getQvoJson(AppSignSetQvo.class);
            CdUser user = this.getSessionUser();
            if(user!=null){
                if(!"admin".equals(user.getAccount())   && !"smadmin".equals(user.getAccount())&&
                        !"zzadmin".equals(user.getAccount())&&
                        !"fzadmin".equals(user.getAccount())&&
                        !"ptadmin".equals(user.getAccount())&&
                        !"npadmin".equals(user.getAccount())&&
                        !"qzadmin".equals(user.getAccount())){
                    if(StringUtils.isNotBlank(user.getHospId())){
                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                        if(dept!=null){
                            if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                qvo.setAreaCode(dept.getHospAreaCode().substring(0,4));
                            }
                        }
                    }
                }
            }
            List<AppSignSetting> ls = sysDao.getAppSignSetDao().findList(qvo);
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
     * 新增
     * @return
     */
    public String add(){
        try {
            AppSignSetting vo = (AppSignSetting)getVoJson(AppSignSetting.class);
            if (vo != null) {
                List<AppSignSetting> listSet = sysDao.getServiceDo().loadByPk(AppSignSetting.class,"signsAreaCode",vo.getSignsAreaCode());
                if(listSet!=null && listSet.size()>0){
                    this.newMsgJson("该市的设置已存在，请重新选择");
                    return "json";
                }
                CdUser user = this.getSessionUser();
                if(user!=null){
                    if(StringUtils.isNotBlank(user.getDrId())){
                        vo.setSignsCreateId(user.getDrId());
                        vo.setSignsDeptId(user.getHospId());
                    }else{
                        vo.setSignsCreateId(user.getUserId());
                        vo.setSignsDeptId(user.getCdDeptId());
                    }
                    vo.setSignsCreateTime(Calendar.getInstance());
                }
                sysDao.getServiceDo().add(vo);
                this.newMsgJson(this.finalSuccessrMsg);
            }else{
                this.newMsgJson(finalErrorMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 查询登入用户对应市数据
     * @return
     */
    public String findModify(){
        CdUser user = this.getSessionUser();
        AppSignSetting signSet = null;
        if(user!=null){
            if(!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
                    !"zzadmin".equals(user.getAccount())&&
                    !"fzadmin".equals(user.getAccount())&&
                    !"ptadmin".equals(user.getAccount())&&
                    !"npadmin".equals(user.getAccount())&&
                    !"qzadmin".equals(user.getAccount())){
                if(user.getRole().indexOf(AppRoleType.SHI.getValue())!=-1){//该用户有市权限
                    if(StringUtils.isNotBlank(user.getHospId())){//app用户
                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                        if(dept!=null){
                            if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                String areaCode = dept.getHospAreaCode().substring(0,4);
                                List<AppSignSetting> signSets = sysDao.getServiceDo().loadByPk(AppSignSetting.class,"signsAreaCode",areaCode);
                                if(signSets!=null&&signSets.size()>0){
                                    signSet = signSets.get(0);
                                }
                            }
                        }
                    }else{
                        if(StringUtils.isNotBlank(user.getCdDeptId())){
                            CdDept dept = (CdDept)sysDao.getServiceDo().find(CdDept.class,user.getCdDeptId());
                            if(dept!=null){
                                if(StringUtils.isNotBlank(dept.getArea())){
                                    String areaCode = dept.getArea().substring(0,4);
                                    List<AppSignSetting> signSets = sysDao.getServiceDo().loadByPk(AppSignSetting.class,"signsAreaCode",areaCode);
                                    if(signSets!=null&&signSets.size()>0){
                                        signSet = signSets.get(0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.setJsonVo(signSet);
        return "jsonVo";
    }

    /**
     * 查询单个数据
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            AppSignSetting signSet = (AppSignSetting)sysDao.getServiceDo().find(AppSignSetting.class,id);
            this.setJsonVo(signSet);
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }

    /**
     * 修改
     * @return
     */
    public String modify(){
        try{
            AppSignSetting vo = (AppSignSetting)getVoJson(AppSignSetting.class);
            if(vo!=null){
                AppSignSetting vv = (AppSignSetting) sysDao.getServiceDo().find(AppSignSetting.class,vo.getId());
                if(vv!=null){
                    vv.setSignsJjType(vo.getSignsJjType());
                    vv.setSignsSignType(vo.getSignsSignType());
                    this.sysDao.getServiceDo().removePoFormSession(vo);
                    sysDao.getServiceDo().modify(vv);
                    this.newMsgJson(this.finalSuccessrMsg);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 删除
     * @return
     */
    public String delete(){
        try{
            String id = this.getRequest().getParameter("id");
            if(id!=null){
                AppSignSetting vv = (AppSignSetting)sysDao.getServiceDo().find(AppSignSetting.class,id);
                if(vv!=null){
                    sysDao.getServiceDo().delete(vv);
                    this.newMsgJson(this.finalSuccessrMsg);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
    /**
     * 新增页面初始化
     * @return
     */
    public String findCmmInit(){
        try {
            Map<String,Object> map = new HashMap<>();
            List<AppLabelManage> list = sysDao.getAppLabelManageDao().findByType(LabelManageType.JJLX.getValue());
            List<CdCode> signType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_MODULE_MANY_SIGN, CommonEnable.QIYONG.getValue());
            List<CdCode> sfcommon = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON,CommonEnable.QIYONG.getValue());
            map.put("jjlxs",list);
            map.put("signType",signType);
            map.put("sfcommon",sfcommon);
            this.getJsons().setMap(map);
        }catch (Exception e){
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
    public AppSignSetting getVo() {
        return vo;
    }

    public void setVo(AppSignSetting vo) {
        this.vo = vo;
    }
}
