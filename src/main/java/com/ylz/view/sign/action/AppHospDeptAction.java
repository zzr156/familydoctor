package com.ylz.view.sign.action;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppHospExtend;
import com.ylz.bizDo.app.po.AppSignServeTeam;
import com.ylz.bizDo.app.po.AppWorkType;
import com.ylz.bizDo.app.vo.AppHospDeptQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.cd.vo.CdAddressSvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/15.
 */
@SuppressWarnings("all")
@Action(
        value="openapphosp",
        results={
                @Result(name="list", location="/intercept/app/hospDept/hospDept_list.jsp"),
                @Result(name="extend", location="/intercept/app/hospDept/hospDeptExtend_edit.jsp"),
                @Result(name="edit", location="/intercept/app/hospDept/hospDept_edit.jsp"),
                @Result(name="signTeam", location="/intercept/app/hospDept/hospDept_sign.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppHospDeptAction extends CommonAction {

    private static final long serialVersionUID = 1L;

    private AppHospDept vo;
    private AppHospDeptQvo qvo;
    private AppHospExtend vv;
    private AppSignServeTeam tv;

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
     * 准备配制签约服务团队
     * @return
     */
    public String signTeamCmm(){
        return "signTeam";
    }
    /**
     * 新增
     * @return
     */
    public String add(){
        try {
            AppHospDept vo =(AppHospDept)getVoJson(AppHospDept.class);
            if (vo != null) {

                if(StringUtils.isNotBlank(vo.getHospCityareaId())){
                    CdAddress addr = (CdAddress) this.sysDao.getServiceDo().find(CdAddress.class,vo.getHospCityareaId());
                    if(addr!=null){
                        vo.setHospAreaCode(addr.getCtcode());//区域编码
                        vo.setHospUpcityareaId(addr.getPid());//添加区域上一级主键
                    }
                }
                this.sysDao.getServiceDo().add(vo);
                AppHospExtend vv = new AppHospExtend();
                vv.setExtHreState("1");
                vv.setExtCreateTime(Calendar.getInstance());
                vv.setExtGreenDay("30");
                vv.setExtYellowDay("10");
                vv.setExtRedDay("2");
                vv.setExtHospId(vo.getId());
                this.sysDao.getServiceDo().add(vv);

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
     * 删除个人和批量删除
     * @return
     */
    public String delete(){
        try{
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                String[] ids = id.split(";");//批量删除
                for(String s:ids){
                    sysDao.getServiceDo().delete(AppHospDept.class,s);
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
     * 修改
     * @return
     */
    public String modify(){
        try{
            AppHospDept vo =(AppHospDept)getVoJson(AppHospDept.class);
            if(vo!=null){
                AppHospDept table = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,vo.getId());
                if(table!=null){
                    table.setHospName(vo.getHospName());
                    table.setHospCode(vo.getHospCode());
                    table.setHospLevel(vo.getHospLevel());
                    table.setHospSerState(vo.getHospSerState());
                    table.setHospState(vo.getHospState());
                    table.setHospTel(vo.getHospTel());
                    table.setHospAddress(vo.getHospAddress());
                    table.setHospIntro(vo.getHospIntro());
                    table.setHospImageurl(vo.getHospImageurl());
                    table.setHospPageStyle(vo.getHospPageStyle());
                    this.sysDao.getServiceDo().removePoFormSession(vo);
                    this.sysDao.getAppHospDeptDao().modify(table);
                    this.newMsgJson(finalSuccessrMsg);
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

    /**
     * 查询list数据
     * @return
     */
    public String list(){
        try{
            AppHospDeptQvo qvo = (AppHospDeptQvo)getQvoJson(AppHospDeptQvo.class);
            List<AppHospDept> ls = sysDao.getAppHospDeptDao().findList(qvo);
            jsons.setRowsQvo(ls,qvo);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 根据id查询数据
     * @return
     */
    public String jsonByOne(){
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, id));
            return "jsonVo";
    }
    /**
     * 页面初始化
     * @return
     */
    public String findCmmInit() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
//        //医院级别
//        List<CdCode> hospLevel = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HOSPLEVEL,CommonEnable.QIYONG.getValue());
//        //页面风格
//        List<CdCode> pageStyle = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_PAGESTYLE,CommonEnable.QIYONG.getValue());
//        //状态
//        List<CdCode> state = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE,CommonEnable.QIYONG.getValue());
//        //是否
//        List<CdCode> sf = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON,CommonEnable.QIYONG.getValue());
//        map.put("hospLevel",hospLevel);
//        map.put("pageStyle",pageStyle);
//        map.put("state",state);
//        map.put("sf",sf);
            //省
            List<CdAddressSvo> ls= this.getSysDao().getCdAddressDao().findByPidList(null);
            map.put("province",ls);

            this.getJsons().setMap(map);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
    }

    /**
     * 无分页查询全部
     * @return
     */
    public String findCmmList(){
        try{
            List<AppHospDept> ls = sysDao.getAppHospDeptDao().findAll();
            jsons.setRows(ls);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";

    }

    /**
     * 根据地区编号查询
     * @return
     */
    public String findCmmByAreaCode(){
        try{
            String areaCode = this.getRequest().getParameter("areaCode");
            List<AppHospDept> ls = sysDao.getAppHospDeptDao().findByAreaCode(areaCode);
            jsons.setRows(ls);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 准备设置
     * @return
     */
    public String forExtend(){
        return "extend";
    }

    /**
     * 查询机构设置信息
     * @return
     */
    public String findExtendCmm(){
        try{
            String id = this.getRequest().getParameter("id");
            List<AppHospExtend> vvs = sysDao.getServiceDo().loadByPk(AppHospExtend.class,"extHospId",id);
            if(vvs!=null&& vvs.size()>0){
                this.setJsonVo(vvs.get(0));
            }
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }
    /**
     * 保存、修改设置
     */
    public String addCmm(){
        try {
            AppHospExtend vv =(AppHospExtend)getVoJson(AppHospExtend.class);
            if (vv != null) {
                if(StringUtils.isBlank(vv.getId())){
                    vv.setExtCreateTime(Calendar.getInstance());
                    this.sysDao.getServiceDo().add(vv);
                }else{
                    AppHospExtend table = (AppHospExtend)sysDao.getServiceDo().find(AppHospExtend.class,vv.getId());
                    table.setExtYellowDay(vv.getExtYellowDay());
                    table.setExtGreenDay(vv.getExtGreenDay());
                    table.setExtRedDay(vv.getExtRedDay());
                    table.setExtHreState(vv.getExtHreState());
                    table.setExtFormulaMode(vv.getExtFormulaMode());
                    this.sysDao.getServiceDo().removePoFormSession(vv);
                    this.sysDao.getServiceDo().modify(table);
                }
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
     * 初始设置
     * @return
     */
    public String findCmm(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            //是否状态
            List<CdCode> state = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON,CommonEnable.QIYONG.getValue());
            //上限方式
            List<CdCode> extFormulaMode = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FORMULAMODE,CommonEnable.QIYONG.getValue());
            map.put("state",state);
            map.put("mode",extFormulaMode);
            this.getJsons().setMap(map);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }

    }

    /**
     * 查询签约服务团队配制
     * @return
     */
    public String findSingCmm(){
        try{
            String sstDeptId = this.getRequest().getParameter("sstDeptId");
            List<AppSignServeTeam> ls= sysDao.getServiceDo().loadByPk(AppSignServeTeam.class,"sstDeptId",sstDeptId);
            if(ls!=null&&ls.size()>0){
                this.setJsonVo(ls.get(0));
            }
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }

    /**
     * 保存/修改签约服务团队配制
     * @return
     */
    public String modifySignCmm(){
        try {
            AppSignServeTeam vv =(AppSignServeTeam)getVoJson(AppSignServeTeam.class);
            if (vv != null) {
                if(StringUtils.isNotBlank(vv.getId())){
                    AppSignServeTeam table = (AppSignServeTeam)sysDao.getServiceDo().find(AppSignServeTeam.class,vv.getId());
                    table.setSstSignToDr(vv.getSstSignToDr());
                    table.setSstSignToTeam(vv.getSstSignToTeam());
                    table.setSstWorkState(vv.getSstWorkState());
                    if(StringUtils.isNotBlank(vv.getSstWorkValue())){
                        table.setSstWorkValue(vv.getSstWorkValue().substring(0,vv.getSstWorkValue().length()-1));
                    }
                    table.setSstSignState(vv.getSstSignState());
                    this.sysDao.getServiceDo().removePoFormSession(vv);
                    this.sysDao.getServiceDo().modify(table);
                }else{
                    CdUser user = this.getSessionUser();
                    if(user!=null){
                        vv.setSstCreateId(user.getUserId());
                    }
                    if(StringUtils.isNotBlank(vv.getSstWorkValue())){
                        vv.setSstWorkValue(vv.getSstWorkValue().substring(0,vv.getSstWorkValue().length()-1));
                    }
                    vv.setSstCreateTime(Calendar.getInstance());
                    sysDao.getServiceDo().add(vv);
                }
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
     * 签约服务配制初始
     * @return
     */
    public String findSignCmmInit(){
       try {
           Map<String, Object> map = new HashMap<String, Object>();
           //是否状态
           List<CdCode> state = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON,CommonEnable.QIYONG.getValue());
           List<AppWorkType> ls = sysDao.getAppWorkTypeDao().findAllList();
           map.put("state",state);
           map.put("ls",ls);
           this.getJsons().setMap(map);
       }catch (Exception e){
           e.printStackTrace();
           new ActionException(this.getClass(), e.getMessage());
           this.newMsgJson(this.finalErrorMsg);
           return "json";
       }
        return "json";
    }

    /**
     * 查询开放服务状态的医院
     * @return
     */
    public String findAllCmm(){
        try{
            List<AppHospDept> ls = sysDao.getAppHospDeptDao().findBySerState();
            jsons.setRows(ls);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
    public AppHospDept getVo() {
        return vo;
    }

    public void setVo(AppHospDept vo) {
        this.vo = vo;
    }

    public AppHospDeptQvo getQvo() {
        return qvo;
    }

    public void setQvo(AppHospDeptQvo qvo) {
        this.qvo = qvo;
    }

    public AppHospExtend getVv() {
        return vv;
    }

    public void setVv(AppHospExtend vv) {
        this.vv = vv;
    }

    public AppSignServeTeam getTv() {
        return tv;
    }

    public void setTv(AppSignServeTeam tv) {
        this.tv = tv;
    }
}
