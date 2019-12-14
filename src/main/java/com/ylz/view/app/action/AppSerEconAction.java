package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppServeEconQvo;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.AreaUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/8/16.
 */
@SuppressWarnings("all")
@Action(
        value="appEcon",
        results={
                @Result(name="list", location="/intercept/app/serveEcon/appSerEcon_list.jsp"),
                @Result(name="edit", location="/intercept/app/serveEcon/appSerEcon_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppSerEconAction extends CommonAction {
    private static final long serialVersionUID = 1L;
    private AppServeEcon vo;
    private AppServeEconQvo qvo;

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
            AppServeEconQvo qvo = (AppServeEconQvo)getQvoJson(AppServeEconQvo.class);
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
                List<AppServeEcon> ls = sysDao.getAppServeEconDao().findList(qvo);
               if(ls!=null && ls.size()>0){
                   for(AppServeEcon ll:ls){
                       AppServeTab tab = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"2");
                       if(tab!=null){
                           if(ll.getId().equals(tab.getSerTabSerId())){
                               ll.setEconTabState("1");
                           }else{
                               ll.setEconTabState("0");
                           }
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
     * 查询单个数据
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((AppServeEcon) sysDao.getServiceDo().find(AppServeEcon.class, id));
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
            AppServeEcon vo =(AppServeEcon)getVoJson(AppServeEcon.class);
            if (vo != null) {
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setEconCreateId(user.getUserId());
                    if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                            "zzadmin".equals(user.getAccount())||
                            "fzadmin".equals(user.getAccount())||
                            "ptadmin".equals(user.getAccount())||
                            "npadmin".equals(user.getAccount())||
                            "qzadmin".equals(user.getAccount())){
                        vo.setEconJcType("0");
                        vo.setEconOpenState("0");
                        vo.setEconAreaCode("0");
                        vo.setEconLevel("0");
                        vo.setEconDeptId(user.getCdDeptId());
                    }else{
                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                        vo.setEconAreaCode(dept.getHospAreaCode());
                        vo.setEconDeptId(dept.getId());
                        if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                            vo.setEconLevel("1");//市级
                        }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                            vo.setEconLevel("2");//医院级
                        }
                        vo.setEconJcType("1");
                    }
                }
                vo.setEconState("0");
                vo.setEconCreateTime(Calendar.getInstance());
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
            AppServeEcon vo =(AppServeEcon)getVoJson(AppServeEcon.class);
            if (vo != null) {
                AppServeEcon table = (AppServeEcon)sysDao.getServiceDo().find(AppServeEcon.class,vo.getId());
                if(table!=null){
                    table.setEconValue(vo.getEconValue());
                    table.setEconTitle(vo.getEconTitle());
                    table.setEconLabelType(vo.getEconLabelType());
                    table.setEconFwType(vo.getEconFwType());
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
                    AppServeEcon pk = (AppServeEcon)sysDao.getServiceDo().find(AppServeEcon.class,s);
                    if(pk!=null){
                        if(user!=null){
                            if(!"admin".equals(user.getAccount())   && !"smadmin".equals(user.getAccount())&&
                                    !"zzadmin".equals(user.getAccount())&&
                                    !"fzadmin".equals(user.getAccount())&&
                                    !"ptadmin".equals(user.getAccount())&&
                                    !"npadmin".equals(user.getAccount())&&
                                    !"qzadmin".equals(user.getAccount())){
                                if(user.getHospId().equals(pk.getEconDeptId())){
                                    sysDao.getServiceDo().delete(AppServeEcon.class,s);
                                    List<AppOpenObject> ls = sysDao.getServiceDo().loadByPk(AppOpenObject.class,"openSerId",s);
                                    if(ls!=null&&ls.size()>0){
                                        for(AppOpenObject ll:ls){
                                            sysDao.getServiceDo().delete(ll);
                                        }
                                    }
                                }
                            }else{
                                sysDao.getServiceDo().delete(AppServeEcon.class,s);
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
     * 查询编号是否存在
     * @return
     */
    public String findCmmValue(){
        try{
            String value = this.getRequest().getParameter("value");
            List<AppServeEcon> ls = sysDao.getServiceDo().loadByPk(AppServeEcon.class,"econValue",value);
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

   /* *//**
     * 标记功能
     * @return
     *//*
    public String bjCmm(){
        try{
            String id = this.getRequest().getParameter("id");
            if(id!=null){
                CdUser user = this.getSessionUser();
                if(user!=null){
                    if(!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
                        !"zzadmin".equals(user.getAccount())&&
                        !"fzadmin".equals(user.getAccount())&&
                        !"ptadmin".equals(user.getAccount())&&
                        !"npadmin".equals(user.getAccount())&&
                        !"qzadmin".equals(user.getAccount())){
                        List<AppServeEcon> lss = sysDao.getServiceDo().loadByPk(AppServeEcon.class,"econDeptId",user.getCdDeptId());
                        if(lss!=null&&lss.size()>0){
                            for(AppServeEcon ls:lss){
                                ls.setEconState("0");
                                sysDao.getServiceDo().modify(ls);
                            }
                            AppServeEcon table = (AppServeEcon)sysDao.getServiceDo().find(AppServeEcon.class,id);
                            if(table!=null){
                                table.setEconState("1");
                                sysDao.getServiceDo().modify(table);
                                this.newMsgJson(this.finalErrorMsg);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }*/

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
            String method=this.getRequest().getParameter("delete");
            AppServeEcon pk = (AppServeEcon)sysDao.getServiceDo().find(AppServeEcon.class,id);
            if(pk!=null){
                CdUser user = this.getSessionUser();
                if(user!=null){
                    if(!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
                            !"zzadmin".equals(user.getAccount())&&
                            !"fzadmin".equals(user.getAccount())&&
                            !"ptadmin".equals(user.getAccount())&&
                            !"npadmin".equals(user.getAccount())&&
                            !"qzadmin".equals(user.getAccount())){
                        if(!user.getHospId().equals(pk.getEconDeptId())){
                            this.newMsgJson("无权限操作");
                            return "json";
                        }else if("delete".equals(method)&&sysDao.getAppServeEconDao().isReferencedByEG(pk)){
                            this.newMsgJson("有[经济与补贴]正在引用该[经济类型],无法删除!");
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
            AppServeEcon pk = (AppServeEcon)sysDao.getServiceDo().find(AppServeEcon.class,id);
            if(pk!=null){
                if("1".equals(pk.getEconOpenState())){
                    pk.setEconOpenState("0");
                }else{
                    pk.setEconOpenState("1");
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
                        st = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"2");
                        table.setSerTabDeptId(user.getCdDeptId());
                    }else{
                        st = sysDao.getAppServeTabDao().findByDept(user.getHospId(),"2");
                        table.setSerTabDeptId(user.getHospId());
                    }
                    if(st!=null){
                        if(id.equals(st.getSerTabSerId())){
                            sysDao.getServiceDo().delete(st);
                            this.newMsgJson("888");
                            return "json";
                        }
                        sysDao.getServiceDo().delete(st);
                    }
                    table.setSerTabSerId(id);
                    table.setSerTabType("2");
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
    public String findCmmInit(){
        try {
            Map<String,Object> map = new HashMap<>();
            //查询编号
            String code = sysDao.getAppServeEconDao().findCode();
            String areaCode = "";
            CdUser user = this.getSessionUser();
            if(user != null) {
                if (StringUtils.isNotBlank(user.getHospId())) {
                    AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getHospId());
                    if (dept != null) {
                        areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode(), "2");
                    }
                }
            }
            if(code !=null){
                //查询各地市编号
                if(code.indexOf(areaCode)!=-1){
                    Integer num = Integer.parseInt(code)+1;
                    map.put("code",num);
                }else{
                    map.put("code",areaCode+"0001");
                }
            }else{
                map.put("code",areaCode+"0001");
            }
//        if(code != null){
//            Integer num = Integer.parseInt(code)+1;
//            map.put("code",num);
//        }else{
//            map.put("code",1);
//        }
//        List<AppLabelManage> econJjType = this.getSysDao().getAppLabelManageDao().findByType("4");
//        map.put("econJjType",econJjType);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
    public AppServeEcon getVo() {
        return vo;
    }

    public void setVo(AppServeEcon vo) {
        this.vo = vo;
    }

    public AppServeEconQvo getQvo() {
        return qvo;
    }

    public void setQvo(AppServeEconQvo qvo) {
        this.qvo = qvo;
    }
}
