package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppServeGovQvo;
import com.ylz.bizDo.app.vo.AppServeSetmealQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExcelReader;
import com.ylz.packcommon.common.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by zzl on 2017/8/15.
 */
@SuppressWarnings("all")
@Action(
        value="appSerMeal",
        results={
                @Result(name="list", location="/intercept/app/serveMeal/appSerMeal_list.jsp"),
                @Result(name="edit", location="/intercept/app/serveMeal/appSerMeal_edit.jsp"),
                @Result(name="print", location="/intercept/app/serveMeal/appSerMeal_print.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
                @Result(name = "jsonUpload", type = "json",params={"root","jsons","contentType", "text/html"})
        }
)
public class AppSerMealAction extends CommonAction {
    private static final long serialVersionUID = 1L;
    private AppServeSetmeal vo ;
    private AppServeSetmealQvo qvo;
    private File upExcel; //上传的文件
    private String upExcelFileName; //文件名称
    private String upExcelContentType; //文件类型

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
            AppServeSetmealQvo qvo = (AppServeSetmealQvo)getQvoJson(AppServeSetmealQvo.class);
            CdUser user = this.getSessionUser();
            if(user!=null) {
                if (!"admin".equals(user.getAccount()) &&
                        !"smadmin".equals(user.getAccount())&&
                        !"zzadmin".equals(user.getAccount())&&
                        !"fzadmin".equals(user.getAccount())&&
                        !"ptadmin".equals(user.getAccount())&&
                        !"npadmin".equals(user.getAccount())&&
                        !"qzadmin".equals(user.getAccount()) ) {
                    qvo.setRoleType("2");//非管理员admin
                    qvo.setHospId(user.getHospId());
                    /*if (AppRoleType.SHEQU.getValue().equals(user.getRole().get(0).getId())) {
                        AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getHospId());
                        if (dept != null) {
                            if (StringUtils.isNotBlank(dept.getHospAreaCode())) {
                                qvo.setAreaCode(dept.getHospAreaCode().substring(0, 4) + "00000000");
                            }
                        }
                    }*/
                    AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getHospId());
                    if(dept!=null){
                        qvo.setAreaCode(dept.getHospAreaCode());
                    }
                } else {
                    qvo.setRoleType("1");
                }
                List<AppServeSetmeal> ls = sysDao.getAppServeSetmealDao().findList(qvo);
                if(ls != null && ls.size() >0){
                    for(AppServeSetmeal ll:ls){
                        AppServeTab tab = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"1");
                        if(tab!=null){
                            if(ll.getId().equals(tab.getSerTabSerId())){
                                ll.setSersmTabState("1");
                            }else{
                                ll.setSersmTabState("0");
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
            this.setJsonVo((AppServeSetmeal) sysDao.getServiceDo().find(AppServeSetmeal.class, id));
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
            AppServeSetmeal vo =(AppServeSetmeal)getVoJson(AppServeSetmeal.class);
            if (vo != null) {
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setSersmCreateId(user.getUserId());
                    if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                            "zzadmin".equals(user.getAccount())||
                            "fzadmin".equals(user.getAccount())||
                            "ptadmin".equals(user.getAccount())||
                            "npadmin".equals(user.getAccount())||
                            "qzadmin".equals(user.getAccount())){
                        vo.setSersmAreaCode("0");
                        vo.setSersmLevel("0");//系统等级
                        vo.setSersmOpenState("0");//默认不开启
                    }else{
                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                        vo.setSersmAreaCode(dept.getHospAreaCode());
                        vo.setSersmCreateDept(dept.getId());
                        if(AppRoleType.SHI.getValue().equals(user.getRoleid())){
                            vo.setSersmLevel("1");//市级
                        }else if(AppRoleType.SHEQU.getValue().equals(user.getRoleid())){
                            vo.setSersmLevel("2");//医院级
                        }
                    }
                }
                vo.setSersmCreateTime(Calendar.getInstance());
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
            AppServeSetmeal vo =(AppServeSetmeal)getVoJson(AppServeSetmeal.class);
            if (vo != null) {
                AppServeSetmeal table = (AppServeSetmeal)sysDao.getServiceDo().find(AppServeSetmeal.class,vo.getId());
                if(table!=null){
                    table.setSersmValue(vo.getSersmValue());
                    table.setSersmName(vo.getSersmName());
                    table.setSersmBgDr(vo.getSersmBgDr());
                    table.setSersmBook(vo.getSersmBook());
                    table.setSersmDownState(vo.getSersmDownState());
                    table.setSersmEndTime(vo.getSersmEndTime());
                    table.setSersmGroupValue(vo.getSersmGroupValue());
                    table.setSersmImageName(vo.getSersmImageName());
                    table.setSersmImageUrl(vo.getSersmImageUrl());
                    table.setSersmJjType(vo.getSersmJjType());
                    table.setSersmObjectTitle(vo.getSersmObjectTitle());
                    table.setSersmObjectType(vo.getSersmObjectType());
                    table.setSersmObjectValue(vo.getSersmObjectValue());
                    table.setSersmPkTitle(vo.getSersmPkTitle());
                    table.setSersmPkValue(vo.getSersmPkValue());
                    table.setSersmPkType(vo.getSersmPkType());
                    table.setSersmStartTime(vo.getSersmStartTime());
                    table.setSersmSubsidyWay(vo.getSersmSubsidyWay());
                    table.setSersmTotalFee(vo.getSersmTotalFee());
                    table.setSersmYxTimeType(vo.getSersmYxTimeType());
                    table.setSersmJcState(vo.getSersmJcState());
                    table.setSersmJjId(vo.getSersmJjId());
                    table.setSersmOpenArea(vo.getSersmOpenArea());
                    table.setSersmOneFee(vo.getSersmOneFee());
                    table.setSersmTotalOneFee(vo.getSersmTotalOneFee());
                    table.setSersmGroupId(vo.getSersmGroupId());
                    table.setSersmIsDiscount(vo.getSersmIsDiscount());
                    table.setSersmDiscount(vo.getSersmDiscount());
                    table.setSersmCode(vo.getSersmCode());
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
                    AppServeSetmeal pk = (AppServeSetmeal)sysDao.getServiceDo().find(AppServeSetmeal.class,s);
                    if(pk!=null){
                        if(user!=null){
                            if(!"admin".equals(user.getAccount()) &&   !"smadmin".equals(user.getAccount())&&
                                    !"zzadmin".equals(user.getAccount())&&
                                    !"fzadmin".equals(user.getAccount())&&
                                    !"ptadmin".equals(user.getAccount())&&
                                    !"npadmin".equals(user.getAccount())&&
                                    !"qzadmin".equals(user.getAccount())){
                                if(user.getHospId().equals(pk.getSersmCreateDept())){
                                    sysDao.getServiceDo().delete(AppServeSetmeal.class,s);
                                    List<AppOpenObject> ls = sysDao.getServiceDo().loadByPk(AppOpenObject.class,"openSerId",s);
                                    if(ls!=null&&ls.size()>0){
                                        for(AppOpenObject ll:ls){
                                            sysDao.getServiceDo().delete(ll);
                                        }
                                    }
                                }
                            }else{
                                sysDao.getServiceDo().delete(AppServeSetmeal.class,s);
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
     * list页面初始化信息
     * @return
     */
    public String findCmmList(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            List<CdCode> ls = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_MEALCXTYPE, CommonEnable.QIYONG.getValue());
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
     * 查询组合数据
     * @return
     */
    public String findCmmGroup(){
        try{
            AppServeSetmealQvo qvo = (AppServeSetmealQvo)getQvoJson(AppServeSetmealQvo.class);
            CdUser user = this.getSessionUser();
            if(user!=null) {
                if (!"admin".equals(user.getAccount()) &&   !"smadmin".equals(user.getAccount())&&
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
//                List<AppServeGroup> ls = sysDao.getAppServeGroupDao().findByGroup(qvo.getRoleType(),qvo.getHospId(),qvo.getAreaCode());
                List<AppServeGroup> ls = sysDao.getAppServeGroupDao().findByGroup(qvo);
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
     * edit页面数据初始化
     * @return
     */
    public String findCmmInit(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            //是否
            List<CdCode> ls = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON, CommonEnable.QIYONG.getValue());
            //经济类型
           // List<CdCode> jjlx = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JJLX, CommonEnable.QIYONG.getValue());
            //政府补贴方式
          //  List<CdCode> btfs = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_PAYTYPE, CommonEnable.QIYONG.getValue());
            List<AppServeEcon> jjlx = new ArrayList<AppServeEcon>();
            List<AppServeGov> btfs = new ArrayList<AppServeGov>();
            AppServeSetmealQvo qvo = new AppServeSetmealQvo();
            CdUser user = this.getSessionUser();
            if(user!=null) {
                if (!"admin".equals(user.getAccount()) &&   !"smadmin".equals(user.getAccount())&&
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
                jjlx = sysDao.getAppServeEconDao().findAllList(qvo.getRoleType(),qvo.getAreaCode(),qvo.getHospId());
                btfs = sysDao.getAppServeGovDao().findAllList(qvo.getRoleType(),qvo.getAreaCode(),qvo.getHospId());
            }

            //套餐有效期
            List<CdCode> tcyx = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_TCYXLX, CommonEnable.QIYONG.getValue());
            String code = sysDao.getAppServeSetmealDao().findCode();
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
//            if( code !=null){
//                Integer num = Integer.parseInt(code)+1;
//                map.put("code",num);
//            }else {
//                map.put("code",1);
//            }
            map.put("ls",ls);
            map.put("jjlx",jjlx);
            map.put("btfs",btfs);
            map.put("tcyx",tcyx);
            map.put("sf",ls);
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
     * 查询编号是否存在
     * @return
     */
    public String findCmmValue(){
        try{
            String value = this.getRequest().getParameter("value");
            List<AppServeSetmeal> ls = sysDao.getServiceDo().loadByPk(AppServeSetmeal.class,"sersmValue",value);
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
            AppServeSetmeal pk = (AppServeSetmeal)sysDao.getServiceDo().find(AppServeSetmeal.class,id);
            if(pk!=null){
                if("1".equals(pk.getSersmOpenState())){
                    pk.setSersmOpenState("0");
                }else{
                    pk.setSersmOpenState("1");
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
//            String areaCode = this.getRequest().getParameter("areaCode");
//            //通过areaCode查询医院
//            String hospId = sysDao.getAppHospDeptDao().findHosp(areaCode);
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
            AppServeSetmeal pk = (AppServeSetmeal)sysDao.getServiceDo().find(AppServeSetmeal.class,id);
            if(pk!=null){
                CdUser user = this.getSessionUser();
                if(user!=null){
                    if(!"admin".equals(user.getAccount()) && !"smadmin".equals(user.getAccount())&&
                            !"zzadmin".equals(user.getAccount())&&
                            !"fzadmin".equals(user.getAccount())&&
                            !"ptadmin".equals(user.getAccount())&&
                            !"npadmin".equals(user.getAccount())&&
                            !"qzadmin".equals(user.getAccount())){
                        if(!user.getHospId().equals(pk.getSersmCreateDept())){
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
    public String findCmmBt(){
        try{
            String id = this.getRequest().getParameter("id");
            AppEconAndGov gov =(AppEconAndGov) sysDao.getServiceDo().find(AppEconAndGov.class,id);
            if(gov!=null){
                if(StringUtils.isNotBlank(gov.getEagGovId())){
                    String[] strs = gov.getEagGovId().split(";");
                    List<AppServeGov> ls = sysDao.getAppServeGovDao().findByEcon(strs);
                    this.getJsons().setRows(ls);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
    public String findCmmEag(){
        try{
            AppServeGovQvo qvo = new AppServeGovQvo();
            CdUser user = this.getSessionUser();
            if(user!=null){
                if (!"admin".equals(user.getAccount()) &&  !"smadmin".equals(user.getAccount())&&
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
                List<AppEconAndGov> ls = sysDao.getAppEconAndGovDao().findByStr(qvo.getRoleType(),qvo.getAreaCode(),qvo.getHospId());
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
     * 标记
     * @return
     */
    public String bjCmm(){
        try{
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                CdUser user = this.getSessionUser();
                if(user!=null){
                    if(StringUtils.isNotBlank(user.getHospId())){
                        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                        if(dept != null){
                            dept.setHospMealId(id);
                            sysDao.getServiceDo().modify(dept);
                        }
                    }
                    AppServeTab st = new AppServeTab();
                    AppServeTab table = new AppServeTab();
                    if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
                            "zzadmin".equals(user.getAccount())||
                            "fzadmin".equals(user.getAccount())||
                            "ptadmin".equals(user.getAccount())||
                            "npadmin".equals(user.getAccount())||
                            "qzadmin".equals(user.getAccount())){
                        st = sysDao.getAppServeTabDao().findByDept(user.getCdDeptId(),"1");
                        table.setSerTabDeptId(user.getCdDeptId());
                    }else{
                        st = sysDao.getAppServeTabDao().findByDept(user.getHospId(),"1");
                        table.setSerTabDeptId(user.getHospId());
                    }
                    if(st!=null){
                        if(id.equals(st.getSerTabSerId())){
                            sysDao.getServiceDo().delete(st);
                            if(StringUtils.isNotBlank(user.getHospId())){
                                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
                                if(dept != null){
                                    dept.setHospMealId(null);
                                    sysDao.getServiceDo().modify(dept);
                                }
                            }
                            this.newMsgJson("888");
                            return "json";
                        }
                        sysDao.getServiceDo().delete(st);
                    }
                    table.setSerTabSerId(id);
                    table.setSerTabType("1");
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
     * 准备打印
     * @return
     */
    public String print(){
        return "print";
    }



    /**
     * 导入套餐数据excel
     * @return
     */
    public String importExcel(){
        try{
            ExcelReader excelReader = new ExcelReader();
            InputStream is2 = new FileInputStream(upExcel);
            Map<Integer, String> map = excelReader.readExcelContent(is2,3);//读取Excel数据内容
            if(map.size() >0) {
                CdUser user = this.getSessionUser();
                String result = this.getSysDao().getAppServeSetmealDao().addImportExcelMeal(map,user);
                System.out.println(JsonUtil.toJson(map));
                this.newMsgJson(result);
            }else{
                this.newMsgJson("exceel无数据!");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.jsons.setMsg("系统出错,请联系管理员!");
        }
        return "jsonUpload";
    }

    /**
     * 导出服务包
     * cxw
     * @throws Exception
     */
    public void pListPrintByPeoIds() throws Exception{
        String strIds = this.getRequest().getParameter("strIds");
        InputStream is2 = new FileInputStream(this.getRequest().getSession().getServletContext().getRealPath("/")+"\\intercept\\file\\jtysqyfwb.xls");
        OutputStream out=this.getResponse().getOutputStream();
        this.getResponse().reset();
        this.getResponse().setHeader("Content-disposition", "attachment; filename=jtysqyfwb.xls");
        this.getResponse().setContentType("application/msexcel");
        POIFSFileSystem fs = new POIFSFileSystem(is2);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int i=3;
        for(String peopleId:strIds.split(";")){
            HSSFRow row = sheet.createRow(i);
            AppServeSetmeal table = (AppServeSetmeal)sysDao.getServiceDo().find(AppServeSetmeal.class,peopleId);
            if(table!=null){
                row.createCell(0).setCellValue(table.getId());//服务包id
                row.createCell(1).setCellValue(table.getSersmValue());//服务包编号  o.getSex().equals("2")?"女":"男"
                row.createCell(2).setCellValue(table.getSersmName());//服务包名称
                row.createCell(3).setCellValue(table.getSersmDownState());//是否补贴
                row.createCell(4).setCellValue(table.getSersmTotalFee());//服务包总金额
                row.createCell(5).setCellValue(table.getSersmYxTimeType());//有效期
                row.createCell(6).setCellValue(table.getSersmBgDr());//有效期是否可变更医生
                row.createCell(7).setCellValue(table.getSersmJcState());//是否是基础服务包
                row.createCell(8).setCellValue("");//有效开始时间
                row.createCell(9).setCellValue("");//有效结束时间
                if(StringUtils.isNotBlank(table.getSersmGroupId())){
                    String [] st = table.getSersmGroupId().split(";");
                    for(int j=0;j<st.length;j++){
                        AppServeGroup group = (AppServeGroup)sysDao.getServiceDo().find(AppServeGroup.class,st[j]);
                        if(group!=null){
                            row.createCell(10).setCellValue(group.getSergValue());//组合id
                            row.createCell(11).setCellValue(group.getSergGroupFee());//服务组合费用
                            if(StringUtils.isNotBlank(group.getSergObjectId())){
                                AppServeObject bj = (AppServeObject)sysDao.getServiceDo().find(AppServeObject.class,group.getSergObjectId());
                                if(bj!=null){
                                    row.createCell(12).setCellValue(bj.getSeroValue());//服务对象编号
                                    row.createCell(13).setCellValue(bj.getSeroName());//服务对象名称
                                    row.createCell(14).setCellValue(bj.getSeroLabelType());//所属服务类型1
                                    row.createCell(15).setCellValue(bj.getSeroFwType());//所属服务类型2
                                    row.createCell(16).setCellValue(bj.getSeroState());//是否是基本公共卫生服务对象
                                }
                            }
                            if(StringUtils.isNotBlank(group.getSergPkId())) {
                                String [] str = group.getSergPkId().split(";");
                                for(int k=0;k<str.length;k++){
                                    AppServePackage pg = (AppServePackage)sysDao.getServiceDo().find(AppServePackage.class,str[k]);
                                    if(pg!=null){
                                        row.createCell(17).setCellValue(pg.getSerpkValue());//服务内容编号
                                        row.createCell(18).setCellValue(pg.getSerpkName());//服务包名称
                                        row.createCell(19).setCellValue(pg.getSerpkOpenState());//是否开启频次状态
                                        if("1".equals(pg.getSerpkOpenState())){
                                            row.createCell(20).setCellValue(pg.getSerpkTime());//频次
                                            row.createCell(21).setCellValue(pg.getSerpkNum()+";"+pg.getSerpkIntervalType());//间隔
                                        }
                                        row.createCell(22).setCellValue(pg.getSerpkBaseType());//是否是特色服务（0否 1是）
                                        row.createCell(23).setCellValue(pg.getSerpkRemark());//服务内容介绍
                                        if(k!=str.length-1){
                                            i++;
                                            row = sheet.createRow(i);
                                        }
                                    }
                                }
                            }
                            if(j!=st.length-1){
                                i++;
                                row = sheet.createRow(i);
                            }
                        }
                    }
                }
            }
            i++;
        }
        wb.write(out);
        out.close();
    }

    /**
     * 下载模板
     * cxw
     * @throws Exception
     */
    public void noExcelDownload() throws Exception{
        InputStream is2 = new FileInputStream(this.getRequest().getSession().getServletContext().getRealPath("/")+"\\intercept\\file\\jtysqyfwbmb.xls");
        OutputStream out=this.getResponse().getOutputStream();
        this.getResponse().reset();
        this.getResponse().setHeader("Content-disposition", "attachment; filename=jtysqyfwbmb.xls");
        this.getResponse().setContentType("application/msexcel");
        POIFSFileSystem fs = new POIFSFileSystem(is2);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        wb.write(out);
        out.close();
    }

    public AppServeSetmeal getVo() {
        return vo;
    }

    public void setVo(AppServeSetmeal vo) {
        this.vo = vo;
    }

    public AppServeSetmealQvo getQvo() {
        return qvo;
    }

    public void setQvo(AppServeSetmealQvo qvo) {
        this.qvo = qvo;
    }

    public File getUpExcel() {
        return upExcel;
    }

    public void setUpExcel(File upExcel) {
        this.upExcel = upExcel;
    }

    public String getUpExcelFileName() {
        return upExcelFileName;
    }

    public void setUpExcelFileName(String upExcelFileName) {
        this.upExcelFileName = upExcelFileName;
    }

    public String getUpExcelContentType() {
        return upExcelContentType;
    }

    public void setUpExcelContentType(String upExcelContentType) {
        this.upExcelContentType = upExcelContentType;
    }
}
