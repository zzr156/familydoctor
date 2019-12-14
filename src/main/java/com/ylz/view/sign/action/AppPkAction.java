package com.ylz.view.sign.action;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppOpenObject;
import com.ylz.bizDo.app.po.AppServePackage;
import com.ylz.bizDo.app.po.AppServeTab;
import com.ylz.bizDo.app.vo.AppServePackageQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/8/14.
 */
@SuppressWarnings("all")
@Action(
        value="openappPk",
        results={
                @Result(name="list", location="/intercept/sign/servePk/appSerPk_list.jsp"),
                @Result(name="edit", location="/intercept/sign/servePk/appSerPk_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppPkAction extends CommonAction {
    private static final long serialVersionUID = 1L;
    private AppServePackage vo;
    private AppServePackageQvo qvo;

    /**
     * 菜单链接
     * @return
     */
    public String forList(){
        return "list";
    }

    /**
     * 查询list页面初始数据
     * @return
     */
    public String list(){
        try{
            AppServePackageQvo qvo = (AppServePackageQvo)getQvoJson(AppServePackageQvo.class);
            CdUser user = this.getSessionUser();
            if(user!=null){
                if(!"admin".equals(user.getAccount())   && !"smadmin".equals(user.getAccount())&&
                        !"zzadmin".equals(user.getAccount())&&
                        !"fzadmin".equals(user.getAccount())&&
                        !"ptadmin".equals(user.getAccount())&&
                        !"npadmin".equals(user.getAccount())&&
                        !"qzadmin".equals(user.getAccount())){
                    qvo.setType("2");//非管理员admin
                    qvo.setHospId(user.getHospId());
                    if(AppRoleType.SHEQU.getValue().equals(user.getRole().get(0).getId())){
                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                        if(dept!=null){
                            if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                qvo.setAreaCode(dept.getHospAreaCode().substring(0,4)+"00000000");
                            }
                        }
                    }
                }else{
                    qvo.setType("1");
                }
                List<AppServePackage> ls = sysDao.getAppServePackageDao().findList(qvo);
                for(AppServePackage ll:ls){
                    AppServeTab tab = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"4");
                    if(tab!=null){
                        if(ll.getId().equals(tab.getSerTabSerId())){
                            ll.setSerpkTabState("1");
                        }else{
                            ll.setSerpkTabState("0");
                        }
                    }
                }
                jsons.setRowsQvo(ls,qvo);
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
     * 准备新增或修改
     * @return
     */
    public String forAddOrEdit(){
        return "edit";
    }

    /**
     * 新增服务包
     * @return
     */
    public String add(){
        try {
            AppServePackage vo =(AppServePackage)getVoJson(AppServePackage.class);
            if (vo != null) {
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setSerpkCreateId(user.getUserId());
                    if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())
                            || "zzadmin".equals(user.getAccount()) ||
                            "fzadmin".equals(user.getAccount()) ||
                            "ptadmin".equals(user.getAccount())||
                            "npadmin".equals(user.getAccount())||
                            "qzadmin".equals(user.getAccount())){
                        vo.setSerpkType("0");//服务类型
                        vo.setSerpkAreaCode("0");
                        vo.setSerpkLeve("0");//系统等级
                        vo.setSerpkState("0");//默认不开启
                    }else{
                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                        vo.setSerpkAreaCode(dept.getHospAreaCode());
                        vo.setSerpkDeptId(dept.getId());
                        vo.setSerpkType("1");
                        if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                            vo.setSerpkLeve("1");//市级
                        }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                            vo.setSerpkLeve("2");//医院级
                        }
                    }
                }
                vo.setSerpkCreateTime(Calendar.getInstance());
                sysDao.getServiceDo().add(vo);
                this.newMsgJson(this.finalSuccessrMsg);
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
     * 修改服务包
     * @return
     */
    public String modify(){
        try {
            AppServePackage vo =(AppServePackage)getVoJson(AppServePackage.class);
            if (vo != null) {
               AppServePackage table = (AppServePackage)sysDao.getServiceDo().find(AppServePackage.class,vo.getId());
               if(table!=null){
                   table.setSerpkName(vo.getSerpkName());
                   table.setSerpkRemark(vo.getSerpkRemark());
                   table.setSerpkOpenState(vo.getSerpkOpenState());
                   table.setSerpkNum(vo.getSerpkNum());
                   table.setSerpkImageUrl(vo.getSerpkImageUrl());
                   table.setSerpkValue(vo.getSerpkValue());
                   table.setSerpkBaseType(vo.getSerpkBaseType());
                   //session出现实体重复时的处理
                   this.sysDao.getServiceDo().removePoFormSession(vo);
                   this.sysDao.getServiceDo().modify(table);
                   this.newMsgJson(this.finalSuccessrMsg);
               }
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
     * 删除服务包
     * @return
     */
    public String delete(){
        try{
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                String[] ids = id.split(";");//批量删除
                CdUser user = this.getSessionUser();
                for(String s:ids){
                    AppServePackage pk = (AppServePackage)sysDao.getServiceDo().find(AppServePackage.class,s);
                    if(pk!=null){
                        if(user!=null){
                            if(!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
                                    !"zzadmin".equals(user.getAccount())&&
                                    !"fzadmin".equals(user.getAccount())&&
                                    !"ptadmin".equals(user.getAccount())&&
                                    !"npadmin".equals(user.getAccount())&&
                                    !"qzadmin".equals(user.getAccount())){
                                if(user.getHospId().equals(pk.getSerpkDeptId())){
                                    sysDao.getServiceDo().delete(AppServePackage.class,s);
                                    List<AppOpenObject> ls = sysDao.getServiceDo().loadByPk(AppOpenObject.class,"openSerId",s);
                                    if(ls!=null&&ls.size()>0){
                                        for(AppOpenObject ll:ls){
                                            sysDao.getServiceDo().delete(ll);
                                        }
                                    }
                                }
                            }else{
                                sysDao.getServiceDo().delete(AppServePackage.class,s);
                                List<AppOpenObject> ls = sysDao.getServiceDo().loadByPk(AppOpenObject.class,"openSerId",s);
                                if(ls!=null&&ls.size()>0){
                                    for(AppOpenObject ll:ls){
                                        sysDao.getServiceDo().delete(ll);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.newMsgJson(finalSuccessrMsg);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 查询当个记录数据
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((AppServePackage) sysDao.getServiceDo().find(AppServePackage.class, id));
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }
    /**
     * 查询编号
     * @return
     */
    public String findCmmValue(){
        try{
            String value = this.getRequest().getParameter("value");
            List<AppServePackage> ls = sysDao.getServiceDo().loadByPk(AppServePackage.class,"serpkValue",value);
            if(ls!=null&&ls.size()>0){
                this.newMsgJson(this.finalSuccessrMsg);
                return "json";
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
    /**
     * 初始数据
     * @return
     */
    public String findCmmInit(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            //是否
            List<CdCode> openState = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON, CommonEnable.QIYONG.getValue());
            //图标
            List<CdCode> icon = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ICON, CommonEnable.QIYONG.getValue());
            List<CdCode> sf = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON, CommonEnable.QIYONG.getValue());
            map.put("openState",openState);
            map.put("icon",icon);
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
     * 改变开启状态
     * @return
     */
    public String openState(){
        try{
            String id = this.getRequest().getParameter("id");
            AppServePackage pk = (AppServePackage)sysDao.getServiceDo().find(AppServePackage.class,id);
            if(pk!=null){
                if("1".equals(pk.getSerpkState())){
                    pk.setSerpkState("0");
                }else{
                    pk.setSerpkState("1");
                }
                sysDao.getServiceDo().modify(pk);
                this.newMsgJson(this.finalSuccessrMsg);
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 指定开放对象
     * @return
     */
    public String openObject(){
        try{
            String id = this.getRequest().getParameter("id");
            String hospIds = this.getRequest().getParameter("hospIds");
            List<AppOpenObject> lists = sysDao.getServiceDo().loadByPk(AppOpenObject.class,"openSerId",id);
            if(lists!=null&&lists.size()>0){
                for(AppOpenObject list:lists){
                    sysDao.getServiceDo().delete(list);
                }
            }
            if(StringUtils.isNotBlank(hospIds)){
                String[] hospIdss = hospIds.split(";");
                for(String hospId:hospIdss){
                    AppOpenObject table = new AppOpenObject();
                    table.setOpenHospId(hospId);
                    table.setOpenSerId(id);
                    sysDao.getServiceDo().add(table);
                }
                this.newMsgJson(this.finalSuccessrMsg);
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 全部开放
     * @return
     */
    public String openAllCmm(){
        try{
            String id = this.getRequest().getParameter("id");
            List<AppOpenObject> lists = sysDao.getServiceDo().loadByPk(AppOpenObject.class,"openSerId",id);
            if(lists!=null&&lists.size()>0){
                for(AppOpenObject list:lists){
                    sysDao.getServiceDo().delete(list);
                }
            }
            List<AppHospDept> lss = sysDao.getServiceDo().loadByPk(AppHospDept.class,"hospSerState","1");
            if(lss!=null&&lss.size()>0){
                for(AppHospDept ls:lss){
                    AppOpenObject table = new AppOpenObject();
                    table.setOpenHospId(ls.getId());
                    table.setOpenSerId(id);
                    sysDao.getServiceDo().add(table);
                }
            }
            this.newMsgJson(this.finalSuccessrMsg);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 查询开放医院
     * @return
     */
    public String findHospCmm(){
        try{
            String id = this.getRequest().getParameter("id");
            List<AppOpenObject> ls = sysDao.getServiceDo().loadByPk(AppOpenObject.class,"openSerId",id);
            Map<String,Object> map = new HashMap<String,Object>();
            String strs = "";
            if(ls!=null&&ls.size()>0){
                for(AppOpenObject ll:ls){
                    strs +=ll.getOpenHospId()+";";
                }
            }
            map.put("hospIds",strs);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 判断权限是否有权限进行修改，删除
     * @return
     */
    public String roleCmm(){
        try{
            String id = this.getRequest().getParameter("id");
            AppServePackage pk = (AppServePackage)sysDao.getServiceDo().find(AppServePackage.class,id);
            if(pk!=null){
                CdUser user = this.getSessionUser();
                if(user!=null){
                    if(!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
                            !"zzadmin".equals(user.getAccount())&&
                            !"fzadmin".equals(user.getAccount())&&
                            !"ptadmin".equals(user.getAccount())&&
                            !"npadmin".equals(user.getAccount())&&
                            !"qzadmin".equals(user.getAccount())){
                        if(!user.getHospId().equals(pk.getSerpkDeptId())){
                            this.newMsgJson("无权限操作");
                            return "json";
                        }
                    }
                }
            }
            this.newMsgJson(this.finalSuccessrMsg);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 标记
     * @return
     */
    public String bjCmm(){
        try{
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                CdUser user = this.getSessionUser();
                if(user!=null){
                    AppServeTab st = new AppServeTab();
                    AppServeTab table = new AppServeTab();
                    if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                            "zzadmin".equals(user.getAccount())||
                            "fzadmin".equals(user.getAccount())||
                            "ptadmin".equals(user.getAccount())||
                            "npadmin".equals(user.getAccount())||
                            "qzadmin".equals(user.getAccount())){
                        st = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"4");
                        table.setSerTabDeptId(user.getCdDeptId());
                    }else{
                        st = sysDao.getAppServeTabDao().findByDept(user.getHospId(),"4");
                        table.setSerTabDeptId(user.getHospId());
                    }
                    if(st!=null){
                        if(id.equals(st.getSerTabSerId())){
                            sysDao.getServiceDo().delete(st);
                            this.newMsgJson("888");
                            return "json";
                        }else{
                            sysDao.getServiceDo().delete(st);
                        }
                    }
                    table.setSerTabSerId(id);
                    table.setSerTabType("4");
                    sysDao.getServiceDo().add(table);
                    this.newMsgJson(finalSuccessrMsg);
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
    public AppServePackage getVo() {
        return vo;
    }

    public void setVo(AppServePackage vo) {
        this.vo = vo;
    }

    public AppServePackageQvo getQvo() {
        return qvo;
    }

    public void setQvo(AppServePackageQvo qvo) {
        this.qvo = qvo;
    }
}
