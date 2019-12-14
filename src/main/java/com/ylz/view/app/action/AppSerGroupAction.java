package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppServeGroupQvo;
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
        value="appSerGroup",
        results={
                @Result(name="list", location="/intercept/app/serveGroup/appSerGroup_list.jsp"),
                @Result(name="edit", location="/intercept/app/serveGroup/appSerGroup_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppSerGroupAction extends CommonAction {
    private static final long serialVersionUID = 1L;
    private AppServeGroup vo;
    private AppServeGroupQvo qvo;


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
     * 条件查询
     * @return
     */
    public String list(){
        try{
            AppServeGroupQvo qvo = (AppServeGroupQvo)getQvoJson(AppServeGroupQvo.class);
            CdUser user = this.getSessionUser();
            if(user!=null) {
                if (!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
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
                List<AppServeGroup> ls = sysDao.getAppServeGroupDao().findList(qvo);
               if(ls!=null && ls.size()>0){
                   for(AppServeGroup ll:ls){
                       AppServeTab tab = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"6");
                       if(tab!=null){
                           if(ll.getId().equals(tab.getSerTabSerId())){
                               ll.setSergTabState("1");
                           }else{
                               ll.setSergTabState("0");
                           }
                       }
                   }
               }
                jsons.setRowsQvo(ls, qvo);
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
            this.setJsonVo((AppServeGroup) sysDao.getServiceDo().find(AppServeGroup.class, id));
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
            AppServeGroup vo =(AppServeGroup)getVoJson(AppServeGroup.class);
            if (vo != null) {
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setSergCreateId(user.getUserId());
                    if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                            "zzadmin".equals(user.getAccount())||
                            "fzadmin".equals(user.getAccount())||
                            "ptadmin".equals(user.getAccount())||
                            "npadmin".equals(user.getAccount())||
                            "qzadmin".equals(user.getAccount())){
                        vo.setSergLevel("0");//系统等级
                        vo.setSergAreaCode("0");
                        vo.setSergOpenState("0");//默认不开启
                    }else{
                        if(StringUtils.isNotBlank(user.getHospId())){
                            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                            vo.setSergAreaCode(dept.getHospAreaCode());
                            vo.setSergDeptId(dept.getId());
                            if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                                vo.setSergLevel("1");//市级
                            }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                                vo.setSergLevel("2");//医院级
                            }
                        }
                    }
                }
                vo.setSergPkValue(vo.getSergPkValue().substring(0,vo.getSergPkValue().length()-1));
                vo.setSergCreateTime(Calendar.getInstance());
                String strTitle = "";
                String strType = "";
                if(StringUtils.isNotBlank(vo.getSergPkId())){
                    String[] strs = vo.getSergPkId().split(";");
                    for(String pk:strs){
                        AppServePackage ls = (AppServePackage)sysDao.getServiceDo().find(AppServePackage.class,pk);
                        if(ls!=null){
                            if(StringUtils.isBlank(strTitle)){
                                strTitle=ls.getSerpkName();
                                strType = ls.getSerpkBaseType();
                            }else{
                                strTitle+=","+ls.getSerpkName();
                                strType+=";"+ls.getSerpkBaseType();
                            }
                        }
                    }
                }
                vo.setSergPkType(strType);
                vo.setSergPkTitle(strTitle);
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
            AppServeGroup vo =(AppServeGroup)getVoJson(AppServeGroup.class);
            if (vo != null) {
                AppServeGroup table = (AppServeGroup)sysDao.getServiceDo().find(AppServeGroup.class,vo.getId());
                if(table!=null){
                    table.setSergGroupFee(vo.getSergGroupFee());
                    table.setSergObjectValue(vo.getSergObjectValue());
                    table.setSergObjectId(vo.getSergObjectId());
                    table.setSergPkId(vo.getSergPkId());
                    table.setSergPkValue(vo.getSergPkValue().substring(0,vo.getSergPkValue().length()-1));
                    table.setSergValue(vo.getSergValue());
                    String strTitle = "";
                    String strType = "";
                    if(StringUtils.isNotBlank(vo.getSergPkId())){
                        String[] strs = vo.getSergPkId().split(";");
                        for(String pk:strs){
                            AppServePackage ls = (AppServePackage)sysDao.getServiceDo().find(AppServePackage.class,pk);
                            if(ls!=null){
                                if(StringUtils.isBlank(strTitle)){
                                    strTitle=ls.getSerpkName();
                                    strType = ls.getSerpkBaseType();
                                }else{
                                    strTitle+=","+ls.getSerpkName();
                                    strType+=";"+ls.getSerpkBaseType();
                                }
                            }
                        }
                    }
                    table.setSergPkType(strType);
                    table.setSergPkTitle(strTitle);
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
                    AppServeGroup pk = (AppServeGroup)sysDao.getServiceDo().find(AppServeGroup.class,s);
                    if(pk!=null){
                        if(user!=null){
                            if(!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
                                    !"zzadmin".equals(user.getAccount())&&
                                    !"fzadmin".equals(user.getAccount())&&
                                    !"ptadmin".equals(user.getAccount())&&
                                    !"npadmin".equals(user.getAccount())&&
                                    !"qzadmin".equals(user.getAccount())){
                                if(user.getHospId().equals(pk.getSergDeptId())){
                                    sysDao.getServiceDo().delete(AppServeGroup.class,s);
                                    List<AppOpenObject> ls = sysDao.getServiceDo().loadByPk(AppOpenObject.class,"openSerId",s);
                                    if(ls!=null&&ls.size()>0){
                                        for(AppOpenObject ll:ls){
                                            sysDao.getServiceDo().delete(ll);
                                        }
                                    }
                                }
                            }else{
                                sysDao.getServiceDo().delete(AppServeGroup.class,s);
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
     * 查询服务人群
     * @return
     */
    public String findCmmObject(){
        try{
            AppServeGroupQvo qvo = (AppServeGroupQvo)getQvoJson(AppServeGroupQvo.class);
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
//                List<AppServeObject> ls = sysDao.getAppServeObjectDao().findByPeople(qvo.getType(),qvo.getHospId(),qvo.getAreaCode());
                List<AppServeObject> ls = sysDao.getAppServeObjectDao().findByPeople(qvo);
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
     * 统一初始化
     * @return
     */
    public String findCmmInit(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            AppServeGroupQvo qvo = new AppServeGroupQvo();
            CdUser user = this.getSessionUser();
            if(user!=null) {
                if (!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
                        !"zzadmin".equals(user.getAccount())&&
                        !"fzadmin".equals(user.getAccount())&&
                        !"ptadmin".equals(user.getAccount())&&
                        !"npadmin".equals(user.getAccount())&&
                        !"qzadmin".equals(user.getAccount())) {
                    qvo.setType("2");//非管理员admin
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
                    qvo.setType("1");
                }
                List<AppServePackage> baseFw = sysDao.getAppServePackageDao().findListByType(qvo.getType(),qvo.getHospId(),qvo.getAreaCode(),"0");
                List<AppServePackage> fw = sysDao.getAppServePackageDao().findListByType(qvo.getType(),qvo.getHospId(),qvo.getAreaCode(),"1");
                map.put("baseFw",baseFw);
                map.put("tsFw",fw);
            }
            //查询编号
            String code = sysDao.getAppServeGroupDao().findCode();
            String areaCode = "";
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
//            if(code != null){
//                Integer num = Integer.parseInt(code)+1;
//                map.put("code",num);
//            }else{
//                map.put("code",1);
//            }
            this.getJsons().setMap(map);
            return "json";
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
            List<AppServeGroup> ls = sysDao.getServiceDo().loadByPk(AppServeGroup.class,"sergValue",value);
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
     * list页面初始化信息
     * @return
     */
    public String findCmmList(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            List<CdCode> ls = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_GROUPCXTYPE, CommonEnable.QIYONG.getValue());
            map.put("cxtj",ls);
            this.getJsons().setMap(map);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }
    /**
     * 改变开启状态
     * @return
     */
    public String openState(){
        try{
            String id = this.getRequest().getParameter("id");
            AppServeGroup pk = (AppServeGroup)sysDao.getServiceDo().find(AppServeGroup.class,id);
            if(pk!=null){
                if("1".equals(pk.getSergOpenState())){
                    pk.setSergOpenState("0");
                }else{
                    pk.setSergOpenState("1");
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
            String method=this.getRequest().getParameter("method");
            AppServeGroup pk = (AppServeGroup)sysDao.getServiceDo().find(AppServeGroup.class,id);
            if(pk!=null){
                CdUser user = this.getSessionUser();
                if(user!=null){
                    if(!"admin".equals(user.getAccount())  && !"smadmin".equals(user.getAccount())&&
                            !"zzadmin".equals(user.getAccount())&&
                            !"fzadmin".equals(user.getAccount())&&
                            !"ptadmin".equals(user.getAccount())&&
                            !"npadmin".equals(user.getAccount())&&
                            !"qzadmin".equals(user.getAccount())){
                        if(!user.getHospId().equals(pk.getSergDeptId())){
                            this.newMsgJson("无权限操作");
                            return "json";
                        }else if("delete".equals(method)&&sysDao.getAppServeGroupDao().isReferencedByMeal(pk)){
                            this.newMsgJson("有[服务套餐]正在引用该[服务组合],无法删除!");
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
                        st = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"6");
                        table.setSerTabDeptId(user.getCdDeptId());
                    }else{
                        st = sysDao.getAppServeTabDao().findByDept(user.getHospId(),"6");
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
                    table.setSerTabType("6");
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
    public AppServeGroup getVo() {
        return vo;
    }

    public void setVo(AppServeGroup vo) {
        this.vo = vo;
    }

    public AppServeGroupQvo getQvo() {
        return qvo;
    }

    public void setQvo(AppServeGroupQvo qvo) {
        this.qvo = qvo;
    }
}
