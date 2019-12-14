package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppEconAndGovQvo;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.*;

/**
 * Created by zzl on 2017/8/21.
 */
@SuppressWarnings("all")
@Action(
        value="appEag",
        results={
                @Result(name="list", location="/intercept/app/serveEconAndGov/appEconAndGov_list.jsp"),
                @Result(name="edit", location="/intercept/app/serveEconAndGov/appEconAndGov_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppEconAndGovAction extends CommonAction {
    private static final long serialVersionUID = 1L;
    private AppEconAndGov vo;
    private AppEconAndGovQvo qvo;
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
            AppEconAndGovQvo qvo = (AppEconAndGovQvo)getQvoJson(AppEconAndGovQvo.class);
            CdUser user = this.getSessionUser();
            if(user!=null){
                if (!"admin".equals(user.getAccount())   && !"smadmin".equals(user.getAccount())&&
                        !"zzadmin".equals(user.getAccount())&&
                        !"fzadmin".equals(user.getAccount())&&
                        !"ptadmin".equals(user.getAccount())&&
                        !"npadmin".equals(user.getAccount())&&
                        !"qzadmin".equals(user.getAccount())) {
                    qvo.setRoleType("2");//非管理员admin
                    qvo.setHospId(user.getHospId());
                    if (AppRoleType.SHEQU.getValue().equals(user.getRole().get(0).getId())) {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getHospId());
                        if (dept != null) {
                            if (StringUtils.isNotBlank(dept.getHospAreaCode())) {
                                qvo.setAreaCode(dept.getHospAreaCode().substring(0, 4) + "00000000");
                            }
                        }
                    }
                } else {
                    qvo.setRoleType("1");
                }
                List<AppEconAndGov> ls = sysDao.getAppEconAndGovDao().findList(qvo);
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
     * 查询单个数据
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((AppEconAndGov) sysDao.getServiceDo().find(AppEconAndGov.class, id));
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }
    /**
     * 新增
     * @return
     */
    public String add(){
        try {
            AppEconAndGov vo =(AppEconAndGov)getVoJson(AppEconAndGov.class);
            if (vo != null) {
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setEagCreateId(user.getUserId());
                    if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                            "zzadmin".equals(user.getAccount())||
                            "fzadmin".equals(user.getAccount())||
                            "ptadmin".equals(user.getAccount())||
                            "npadmin".equals(user.getAccount())||
                            "qzadmin".equals(user.getAccount())){
                        vo.setEagOpenState("0");
                        vo.setEagAreaCode("0");
                        vo.setEagLevel("0");
                        vo.setEagDeptId(user.getCdDeptId());
                    }else{
                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                        vo.setEagAreaCode(dept.getHospAreaCode());
                        vo.setEagDeptId(dept.getId());
                        if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                            vo.setEagLevel("1");//市级
                        }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                            vo.setEagLevel("2");//医院级
                        }
                    }
                }
                if(StringUtils.isNotBlank(vo.getEagGovValue())){
                    vo.setEagGovValue(vo.getEagGovValue().substring(0,vo.getEagGovValue().length()-1));
                    String[] strs = vo.getEagGovId().split(";");
                    String strgov = "";
                    for(String str:strs){
//                        List<AppServeGov> lss = sysDao.getServiceDo().loadByPk(AppServeGov.class,"govValue",str);
                        AppServeGov govv = (AppServeGov)sysDao.getServiceDo().find(AppServeGov.class,str);
                        if(govv!=null){
                            if(StringUtils.isBlank(strgov)){
                                strgov = govv.getGovTitle();
                            }else{
                                strgov += ";"+govv.getGovTitle();
                            }
                        }
                    }
                    vo.setEagGovTitle(strgov);
                }
                vo.setEagCreateTime(Calendar.getInstance());
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
     * 修改
     * @return
     */
    public String modify(){
        try {
            AppEconAndGov vo =(AppEconAndGov)getVoJson(AppEconAndGov.class);
            if (vo != null) {
                AppEconAndGov table = (AppEconAndGov)sysDao.getServiceDo().find(AppEconAndGov.class,vo.getId());
                if(table!=null){
                    table.setEagEconValue(vo.getEagEconValue());
                    table.setEagGovValue(vo.getEagGovValue());
                    table.setEagEconId(vo.getEagEconId());
                    table.setEagGovId(vo.getEagGovId());
                    String strgov = "";
                    if(StringUtils.isNotBlank(vo.getEagGovId())){
                        String[] govids = vo.getEagGovId().split(";");
                        for(String govId:govids){
                            AppServeGov govv = (AppServeGov)sysDao.getServiceDo().find(AppServeGov.class,govId);
                            if(govv!=null){
                                if(StringUtils.isBlank(strgov)){
                                    strgov = govv.getGovTitle();
                                }else{
                                    strgov += ";"+govv.getGovTitle();
                                }
                            }
                        }
                        table.setEagGovTitle(strgov);
                    }
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
     * 删除
     * @return
     */
    public String delete(){
        try{
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                String[] ids = id.split(";");//批量删除
                CdUser user = this.getSessionUser();
                for(String s:ids){
                    AppEconAndGov pk = (AppEconAndGov)sysDao.getServiceDo().find(AppEconAndGov.class,s);
                    if(pk!=null){
                        if(user!=null){
                            if(!"admin".equals(user.getAccount())   && !"smadmin".equals(user.getAccount())&&
                                    !"zzadmin".equals(user.getAccount())&&
                                    !"fzadmin".equals(user.getAccount())&&
                                    !"ptadmin".equals(user.getAccount())&&
                                    !"npadmin".equals(user.getAccount())&&
                                    !"qzadmin".equals(user.getAccount())){
                                if(user.getHospId().equals(pk.getEagDeptId())){
                                    sysDao.getServiceDo().delete(AppEconAndGov.class,s);
                                    List<AppOpenObject> ls = sysDao.getServiceDo().loadByPk(AppOpenObject.class,"openSerId",s);
                                    if(ls!=null&&ls.size()>0){
                                        for(AppOpenObject ll:ls){
                                            sysDao.getServiceDo().delete(ll);
                                        }
                                    }
                                }
                            }else{
                                sysDao.getServiceDo().delete(AppEconAndGov.class,s);
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
            String method=this.getRequest().getParameter("method");
            AppEconAndGov pk = (AppEconAndGov)sysDao.getServiceDo().find(AppEconAndGov.class,id);
            if(pk!=null){
                CdUser user = this.getSessionUser();
                if(user!=null){
                    if(!"admin".equals(user.getAccount())   && !"smadmin".equals(user.getAccount())&&
                            !"zzadmin".equals(user.getAccount())&&
                            !"fzadmin".equals(user.getAccount())&&
                            !"ptadmin".equals(user.getAccount())&&
                            !"npadmin".equals(user.getAccount())&&
                            !"qzadmin".equals(user.getAccount())){
                        if(!user.getHospId().equals(pk.getEagDeptId())){
                            this.newMsgJson("无权限操作");
                            return "json";
                        }else if("delete".equals(method)&&sysDao.getAppEconAndGovDao().isReferencedByMeal(pk)){
                            this.newMsgJson("有[服务套餐]正在引用该[经济与补贴],无法删除!");
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
     * 开启
     * @return
     */
    public String openState(){
        try{
            String id = this.getRequest().getParameter("id");
            AppEconAndGov pk = (AppEconAndGov)sysDao.getServiceDo().find(AppEconAndGov.class,id);
            if(pk!=null){
                if("1".equals(pk.getEagOpenState())){
                    pk.setEagOpenState("0");
                }else{
                    pk.setEagOpenState("1");
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
     * 初始信息
     * @return
     */
    public String findCmmInit(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            AppEconAndGovQvo qvo = new AppEconAndGovQvo();
            CdUser user = this.getSessionUser();
            List<AppServeEcon> jjlx = new ArrayList<AppServeEcon>();
            List<AppServeGov> btfs = new ArrayList<AppServeGov>();
            if(user!=null) {
                if (!"admin".equals(user.getAccount())   && !"smadmin".equals(user.getAccount())&&
                        !"zzadmin".equals(user.getAccount())&&
                        !"fzadmin".equals(user.getAccount())&&
                        !"ptadmin".equals(user.getAccount())&&
                        !"npadmin".equals(user.getAccount())&&
                        !"qzadmin".equals(user.getAccount())) {
                    qvo.setRoleType("2");//非管理员admin
                    qvo.setHospId(user.getHospId());
                    if (AppRoleType.SHEQU.getValue().equals(user.getRole().get(0).getId())) {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getHospId());
                        if (dept != null) {
                            if (StringUtils.isNotBlank(dept.getHospAreaCode())) {
                                qvo.setAreaCode(dept.getHospAreaCode().substring(0, 4) + "00000000");
                            }
                        }
                    }
                    map.put("jjlx",jjlx);
                    map.put("btfs",btfs);
                } else {
                    qvo.setRoleType("1");
                }
                jjlx = sysDao.getAppServeEconDao().findAllList(qvo.getRoleType(),qvo.getAreaCode(),qvo.getHospId());
                btfs = sysDao.getAppServeGovDao().findAllList(qvo.getRoleType(),qvo.getAreaCode(),qvo.getHospId());
            }
            map.put("jjlx",jjlx);
            map.put("btfs",btfs);
            this.getJsons().setMap(map);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }

    public AppEconAndGov getVo() {
        return vo;
    }

    public void setVo(AppEconAndGov vo) {
        this.vo = vo;
    }

    public AppEconAndGovQvo getQvo() {
        return qvo;
    }

    public void setQvo(AppEconAndGovQvo qvo) {
        this.qvo = qvo;
    }
}
