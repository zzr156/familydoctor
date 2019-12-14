package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppOpenObject;
import com.ylz.bizDo.app.po.AppServeGov;
import com.ylz.bizDo.app.po.AppServeTab;
import com.ylz.bizDo.app.vo.AppServeGovQvo;
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
 * Created by zzl on 2017/8/16.
 */
@SuppressWarnings("all")
@Action(
        value="appGov",
        results={
                @Result(name="list", location="/intercept/app/serveGov/appSerGov_list.jsp"),
                @Result(name="edit", location="/intercept/app/serveGov/appSerGov_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppSerGovAction extends CommonAction {
    private static final long serialVersionUID = 1L;
    private AppServeGov vo;
    private AppServeGovQvo qvo;
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
            AppServeGovQvo qvo = (AppServeGovQvo)getQvoJson(AppServeGovQvo.class);
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
                List<AppServeGov> ls = sysDao.getAppServeGovDao().findList(qvo);
               if(ls!=null && ls.size()>0){
                   for(AppServeGov ll:ls){
                       AppServeTab tab = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"3");
                       if(tab!=null){
                           if(ll.getId().equals(tab.getSerTabSerId())){
                               ll.setGovTabState("1");
                           }else{
                               ll.setGovTabState("0");
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
            this.setJsonVo((AppServeGov) sysDao.getServiceDo().find(AppServeGov.class, id));
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
            AppServeGov vo =(AppServeGov)getVoJson(AppServeGov.class);
            if (vo != null) {
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setGovCreateId(user.getUserId());
                    if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                            "zzadmin".equals(user.getAccount())||
                            "fzadmin".equals(user.getAccount())||
                            "ptadmin".equals(user.getAccount())||
                            "npadmin".equals(user.getAccount())||
                            "qzadmin".equals(user.getAccount())){
                        vo.setGovJcType("0");
                        vo.setGovOpenState("0");
                        vo.setGovLevel("0");
                        vo.setGovAreaCode("0");
                        vo.setGovDeptId(user.getCdDeptId());
                    }else{
                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                        vo.setGovAreaCode(dept.getHospAreaCode());
                        vo.setGovDeptId(dept.getId());
                        if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                            vo.setGovLevel("1");//市级
                        }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                            vo.setGovLevel("2");//医院级
                        }
                        vo.setGovJcType("1");
                    }
                }
                vo.setGovState("0");
                vo.setGovCreateTime(Calendar.getInstance());
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
            AppServeGov vo =(AppServeGov)getVoJson(AppServeGov.class);
            if (vo != null) {
                AppServeGov table = (AppServeGov)sysDao.getServiceDo().find(AppServeGov.class,vo.getId());
                if(table!=null){
                    table.setGovTitle(vo.getGovTitle());
                    table.setGovValue(vo.getGovValue());
                    table.setGovType(vo.getGovType());
                    table.setGovMoney(vo.getGovMoney());
                    table.setGovHcProjectId(vo.getGovHcProjectId());
                    table.setGovHcProjectName(vo.getGovHcProjectName());
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
                    AppServeGov pk = (AppServeGov)sysDao.getServiceDo().find(AppServeGov.class,s);
                    if(pk!=null){
                        if(user!=null){
                            if(!"admin".equals(user.getAccount())   && !"smadmin".equals(user.getAccount())&&
                                    !"zzadmin".equals(user.getAccount())&&
                                    !"fzadmin".equals(user.getAccount())&&
                                    !"ptadmin".equals(user.getAccount())&&
                                    !"npadmin".equals(user.getAccount())&&
                                    !"qzadmin".equals(user.getAccount())){
                                if(user.getHospId().equals(pk.getGovDeptId())){
                                    sysDao.getServiceDo().delete(AppServeGov.class,s);
                                    List<AppOpenObject> ls = sysDao.getServiceDo().loadByPk(AppOpenObject.class,"openSerId",s);
                                    if(ls!=null&&ls.size()>0){
                                        for(AppOpenObject ll:ls){
                                            sysDao.getServiceDo().delete(ll);
                                        }
                                    }
                                }
                            }else{
                                sysDao.getServiceDo().delete(AppServeGov.class,s);
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
            List<AppServeGov> ls = sysDao.getServiceDo().loadByPk(AppServeGov.class,"govValue",value);
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
            String method = this.getRequest().getParameter("method");
            AppServeGov pk = (AppServeGov)sysDao.getServiceDo().find(AppServeGov.class,id);
            if(pk!=null){
                CdUser user = this.getSessionUser();
                if(user!=null){
                    if(!"admin".equals(user.getAccount())   && !"smadmin".equals(user.getAccount())&&
                            !"zzadmin".equals(user.getAccount())&&
                            !"fzadmin".equals(user.getAccount())&&
                            !"ptadmin".equals(user.getAccount())&&
                            !"npadmin".equals(user.getAccount())&&
                            !"qzadmin".equals(user.getAccount())){
                        if(!user.getHospId().equals(pk.getGovDeptId())){
                            this.newMsgJson("无权限操作");
                            return "json";
                        }else if("delete".equals(method)&&sysDao.getAppServeGovDao().isReferencedByEG(pk)){
                            this.newMsgJson("有[经济与补贴]正在引用该[政府补贴],无法删除!");
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
            AppServeGov pk = (AppServeGov)sysDao.getServiceDo().find(AppServeGov.class,id);
            if(pk!=null){
                if("1".equals(pk.getGovOpenState())){
                    pk.setGovOpenState("0");
                }else{
                    pk.setGovOpenState("1");
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
                        st = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"3");
                        table.setSerTabDeptId(user.getCdDeptId());
                    }else{
                        st = sysDao.getAppServeTabDao().findByDept(user.getHospId(),"3");
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
                    table.setSerTabType("3");
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

    /**
     * 数据初始化
     * @return
     */
    public String findCmmInit(){
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            //服务人群
            List<CdCode> govType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_GOVTYPE, CommonEnable.QIYONG.getValue());
            //查询编号
            String code = sysDao.getAppServeGovDao().findCode();
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
            map.put("govType",govType);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
    public AppServeGov getVo() {
        return vo;
    }

    public void setVo(AppServeGov vo) {
        this.vo = vo;
    }

    public AppServeGovQvo getQvo() {
        return qvo;
    }

    public void setQvo(AppServeGovQvo qvo) {
        this.qvo = qvo;
    }
}
