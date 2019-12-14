package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppServeObjectQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
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
 * Created by zzl on 2017/8/14.
 */
@SuppressWarnings("all")
@Action(
        value="appSerPeople",
        results={
                @Result(name="list", location="/intercept/app/servePeople/appSerPeople_list.jsp"),
                @Result(name="edit", location="/intercept/app/servePeople/appSerPeople_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppSerPeopelAction extends CommonAction {
    private static final long serialVersionUID = 1L;
    private AppServeObject vo;
    private AppServeObjectQvo qvo;

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
     * 页面初始查询
     * @return
     */
    public String list(){
        try{
            AppServeObjectQvo qvo = (AppServeObjectQvo)getQvoJson(AppServeObjectQvo.class);
            CdUser user = this.getSessionUser();
            if(user!=null){
                if(!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
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
                List<AppServeObject> ls = sysDao.getAppServeObjectDao().findList(qvo);
                if(ls!=null && ls.size()>0){
                    for(AppServeObject ll:ls){
                        AppServeTab tab = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"5");
                        if(tab!=null){
                            if(ll.getId().equals(tab.getSerTabSerId())){
                                ll.setSeroTabState("1");
                            }else{
                                ll.setSeroTabState("0");
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
     * 查询单个记录数据
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((AppServeObject) sysDao.getServiceDo().find(AppServeObject.class, id));
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }

    /**
     * 新增服务人群
     * @return
     */
    public String add(){
        try {
            AppServeObject vo =(AppServeObject)getVoJson(AppServeObject.class);
            if (vo != null) {
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setSeroCreateId(user.getUserId());
                    if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                            "zzadmin".equals(user.getAccount())||
                            "fzadmin".equals(user.getAccount())||
                            "ptadmin".equals(user.getAccount())||
                            "npadmin".equals(user.getAccount())||
                            "qzadmin".equals(user.getAccount())){
                        vo.setSeroType("0");
                        vo.setSeroAreaCode("0");
                        vo.setSeroLevel("0");//系统等级
                        vo.setSeroOpenState("0");//默认不开启
                    }else{
                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                        vo.setSeroAreaCode(dept.getHospAreaCode());
                        vo.setSeroDeptId(dept.getId());
                        vo.setSeroType("1");
                        if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                            vo.setSeroLevel("1");//市级
                        }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                            vo.setSeroLevel("2");//医院级
                        }
                    }
                }
                vo.setSeroCreateTime(Calendar.getInstance());
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
     * 修改服务人群
     * @return
     */
    public String modify(){
        try {
            AppServeObject vo =(AppServeObject)getVoJson(AppServeObject.class);
            if (vo != null) {
                AppServeObject table = (AppServeObject)sysDao.getServiceDo().find(AppServeObject.class,vo.getId());
                if(table!=null){
                    table.setSeroName(vo.getSeroName());
                    table.setSeroState(vo.getSeroState());
                    table.setSeroValue(vo.getSeroValue());
                    table.setSeroLabelType(vo.getSeroLabelType());
                    table.setSeroFwType(vo.getSeroFwType());
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
                    AppServeObject pk = (AppServeObject)sysDao.getServiceDo().find(AppServeObject.class,s);
                    if(pk!=null){
                        if(user!=null){
                            if(!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
                                    !"zzadmin".equals(user.getAccount())&&
                                    !"fzadmin".equals(user.getAccount())&&
                                    !"ptadmin".equals(user.getAccount())&&
                                    !"npadmin".equals(user.getAccount())&&
                                    !"qzadmin".equals(user.getAccount())){
                                if(user.getHospId().equals(pk.getSeroDeptId())){
                                    sysDao.getServiceDo().delete(AppServeObject.class,s);
                                    List<AppOpenObject> ls = sysDao.getServiceDo().loadByPk(AppOpenObject.class,"openSerId",s);
                                    if(ls!=null&&ls.size()>0){
                                        for(AppOpenObject ll:ls){
                                            sysDao.getServiceDo().delete(ll);
                                        }
                                    }
                                }
                            }else{
                                sysDao.getServiceDo().delete(AppServeObject.class,s);
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
     * 初始数据
     * @return
     */
    public String findCmmInit(){
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            //是否
            List<CdCode> state = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON, CommonEnable.QIYONG.getValue());
//        List<AppLabelManage> seroType = this.getSysDao().getAppLabelManageDao().findByType("3");
            String code = sysDao.getAppServeObjectDao().findCode();
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
            map.put("state",state);
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
     * 查询编号
     * @return
     */
    public String findCmmValue(){
        try{
            String value = this.getRequest().getParameter("value");
            List<AppServeObject> ls = sysDao.getServiceDo().loadByPk(AppServeObject.class,"seroValue",value);
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
     * 开启
     * @return
     */
    public String openState(){
        try{
            String id = this.getRequest().getParameter("id");
            AppServeObject pk = (AppServeObject)sysDao.getServiceDo().find(AppServeObject.class,id);
            if(pk!=null){
                if("1".equals(pk.getSeroOpenState())){
                    pk.setSeroOpenState("0");
                }else{
                    pk.setSeroOpenState("1");
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
     * 判断权限是否有权限进行修改，删除
     * @return
     */
    public String roleCmm(){
        try{
            String id = this.getRequest().getParameter("id");
            String method=this.getRequest().getParameter("method");
            AppServeObject pk = (AppServeObject)sysDao.getServiceDo().find(AppServeObject.class,id);
            if(pk!=null){
                CdUser user = this.getSessionUser();
                if(user!=null){
                    if(!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
                            !"zzadmin".equals(user.getAccount())&&
                            !"fzadmin".equals(user.getAccount())&&
                            !"ptadmin".equals(user.getAccount())&&
                            !"npadmin".equals(user.getAccount())&&
                            !"qzadmin".equals(user.getAccount())){
                        if(!user.getHospId().equals(pk.getSeroDeptId())){
                            this.newMsgJson("无权限操作");
                            return "json";
                        }else if("delete".equals(method)&&sysDao.getAppServeObjectDao().isReferencedByGroup(pk)){
                            this.newMsgJson("有[服务组合]正在引用该[服务人群],无法删除!");
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
                        st = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"5");
                        table.setSerTabDeptId(user.getCdDeptId());
                    }else{
                        st = sysDao.getAppServeTabDao().findByDept(user.getHospId(),"5");
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
                    table.setSerTabType("5");
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
    public String findFwCmm(){
        try {
            String value = this.getRequest().getParameter("value");
            List<AppLabelManage> seroType = this.getSysDao().getAppLabelManageDao().findByType(value);
            jsons.setRows(seroType);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
    public AppServeObject getVo() {
        return vo;
    }

    public void setVo(AppServeObject vo) {
        this.vo = vo;
    }

    public AppServeObjectQvo getQvo() {
        return qvo;
    }

    public void setQvo(AppServeObjectQvo qvo) {
        this.qvo = qvo;
    }
}
