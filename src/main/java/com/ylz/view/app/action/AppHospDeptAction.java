package com.ylz.view.app.action;


import com.ylz.bizDo.app.entity.AppHospAuthorizationEntity;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppHospExtend;
import com.ylz.bizDo.app.po.AppSignServeTeam;
import com.ylz.bizDo.app.po.AppWorkType;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppHospExtend;
import com.ylz.bizDo.app.po.AppSignServeTeam;
import com.ylz.bizDo.app.po.AppWorkType;

import com.ylz.bizDo.app.po.*;

import com.ylz.bizDo.app.vo.AppHospDeptQvo;
import com.ylz.bizDo.cd.entity.CdAddressEntity;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.cd.vo.CdAddressSvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExcelReader;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/15.
 */
@SuppressWarnings("all")
@Action(
        value="apphosp",
        results={
                @Result(name="list", location="/intercept/app/hospDept/hospDept_list.jsp"),
                @Result(name="extend", location="/intercept/app/hospDept/hospDeptExtend_edit.jsp"),
                @Result(name="edit", location="/intercept/app/hospDept/hospDept_edit.jsp"),
                @Result(name="addHosp",location="/intercept/app/hospDept/hospDept_addHosp.jsp"),
                @Result(name="addUploadImage",location="/intercept/app/hospDept/hospDept_uploadImage.jsp"),
                @Result(name="signTeam", location="/intercept/app/hospDept/hospDept_sign.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
                @Result(name = "jsonUpload", type = "json",params={"root","jsons","contentType", "text/html"})
        }
)
public class AppHospDeptAction extends CommonAction {

    private static final long serialVersionUID = 1L;

    private AppHospDept vo;
    private AppHospDeptQvo qvo;
    private AppHospExtend vv;
    private AppSignServeTeam tv;
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
     * 准备配制签约服务团队
     * @return
     */
    public String signTeamCmm(){
        return "signTeam";
    }

    /**
     * 准备添加公章图片
     * @return
     */
    public String addUploadImage(){
        return "addUploadImage";
    }

    /**
     * 准备添加上级医院
     * @return
     */
    public String addUpHosp(){
        return "addHosp";
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
                    table.setHospLevelType(vo.getHospLevelType());
                    table.setLabelState(vo.getLabelState());
                    table.setHospX(vo.getHospX());
                    table.setHospY(vo.getHospY());
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
            List<CdCode> hospLevel = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HOSPLEVEL,CommonEnable.QIYONG.getValue());
//        //页面风格
            List<CdCode> pageStyle = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_PAGESTYLE,CommonEnable.QIYONG.getValue());
//        //状态
            List<CdCode> state = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE,CommonEnable.QIYONG.getValue());
//        //是否
            List<CdCode> sf = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON,CommonEnable.QIYONG.getValue());
            //医院类型
            List<CdCode> hospLevelType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HOSPLEVELTYPE,CommonEnable.QIYONG.getValue());
            map.put("hospLevelType",hospLevelType);
            map.put("hospLevel",hospLevel);
            map.put("pageStyle",pageStyle);
            map.put("state",state);
            map.put("sf",sf);
            //省
            List<CdAddressSvo> ls= this.getSysDao().getCdAddressDao().findByPidList(null);
            map.put("province",ls);

            this.getJsons().setMap(map);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
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
            AppHospDeptQvo qvo = (AppHospDeptQvo)getQvoJson(AppHospDeptQvo.class);
//            List<AppHospDept> ls = sysDao.getAppHospDeptDao().findByAreaCode();
            List<AppHospDept> ls = sysDao.getAppHospDeptDao().findByQvo(qvo);
            jsons.setQvo(qvo);
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
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
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



    /**
     * cjw 查询区县
     * @return
     */
    public String findCmmByArea(){
        try{
            AppHospDeptQvo qvo = (AppHospDeptQvo)getQvoJson(AppHospDeptQvo.class);
            List<CdAddressEntity> ls = sysDao.getAppHospDeptDao().findByCpctiy(qvo);
            jsons.setQvo(qvo);
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
     * cjw 签约授权
     * @return
     */
    public String addauthorization()
    {
        try{
            AppHospAuthorizationEntity vo = (AppHospAuthorizationEntity)getVoJson(AppHospAuthorizationEntity.class);
            if(vo!=null){
                List<AppHospDept> ls= this.sysDao.getAppHospDeptDao().findauthorization(vo);
                String[] code=vo.getAreaCode().split(";");
                String[] codenockde=vo.getCodenockde().split(";");
                for(int i=0;i<ls.size();i++){
                    //签约授权
                            for(int j=0;j<code.length;j++){
                                if(code[j].equals(ls.get(i).getId())){
                                    ls.get(i).setHospSerState("1");
                                    this.sysDao.getAppHospDeptDao().modify(ls.get(i));
                                }
                            }
                            for(int j=0;j<codenockde.length;j++){
                                if(codenockde[j].equals(ls.get(i).getId())){
                                    ls.get(i).setHospSerState("0");
                                    this.sysDao.getAppHospDeptDao().modify(ls.get(i));
                                }
                            }

               /*     if(vo.getType().equals("0")){ //机构
                        *//*for(int j=0;j<code.length;j++){
                            if(code[j].equals(ls.get(i).getId())){  // 判断反选
                                ls.get(i).setHospSerState("1");
                                this.sysDao.getAppHospDeptDao().modify(ls.get(i));

                            }else{
                                ls.get(i).setHospSerState("0");
                                this.sysDao.getAppHospDeptDao().modify(ls.get(i));

                            }
                        }*//*
                    }else{
                        ls.get(i).setHospSerState("1");
                        this.sysDao.getAppHospDeptDao().modify(ls.get(i));
                    }*/
                }
                jsons.setCode("800");
            }else {
                jsons.setCode("900");
            }
        }catch(Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";

    }



    /**
     * 查询本市机构列表
     * @return
     */
    public String findCmmUpHosp(){
        try{
            String hospId = this.getRequest().getParameter("hospId");
            List<AppHospDept> ls = sysDao.getAppHospDeptDao().findHospByArea(hospId);
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
     * 查询数据列表
     * @return
     */
    public String findUpHosp(){
        try{
            AppHospDeptQvo qvo = (AppHospDeptQvo)getQvoJson(AppHospDeptQvo.class);
            List<AppUpHospTable> list = sysDao.getAppHospDeptDao().findUpHospList(qvo);
            jsons.setRowsQvo(list,qvo);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
    /**
     * 添加上级机构
     * @return
     */
    public String addUpHospCmm(){
        try{
            AppUpHospTable upHospTable =(AppUpHospTable)getVoJson(AppUpHospTable.class);
            if (upHospTable != null) {
                AppUpHospTable table = sysDao.getAppHospDeptDao().findOneUpHosp(upHospTable.getUpId(),upHospTable.getUpHospId());
                if(table!=null){
                    this.newMsgJson("已添加，不可重复操作");
                }else{
                    upHospTable.setUpState("1");
                    this.sysDao.getServiceDo().add(upHospTable);
                    this.newMsgJson(this.finalSuccessrMsg);
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
     * 删除上级机构
     * @return
     */
    public String deleteUpHosp(){
        try{
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                String[] ids = id.split(";");//批量删除
                for(String s:ids){
                    sysDao.getServiceDo().delete(AppUpHospTable.class,s);
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
     * 修改状态
     * @return
     */
    public String chageState(){
        try{
            String id = this.getRequest().getParameter("id");
            AppUpHospTable table = (AppUpHospTable)sysDao.getServiceDo().find(AppUpHospTable.class,id);
            if(table!=null){
                if("1".equals(table.getUpState())){
                    table.setUpState("0");
                }else{
                    table.setUpState("1");
                }
                sysDao.getServiceDo().modify(table);
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
     * 上传公章图片
     * @return
     */
    public String modifyUploadImage(){
        try{
            AppHospAuthorizationEntity vo = (AppHospAuthorizationEntity)getVoJson(AppHospAuthorizationEntity.class);
            if(vo != null){
                AppHospDept hospDept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,vo.getId());
                if(hospDept != null && StringUtils.isNotBlank(vo.getImageBase())){
                    Map<String, Object> map = sysDao.getIoUtils().getCtyunOosSample(vo.getImageBase(), CommonShortType.YISHENG.getValue());
                    String url = map.get("objectUrl").toString();
                    String urlTop = PropertiesUtil.getConfValue("urlTop") + url.substring(url.indexOf("/picture"), url.length());
                    hospDept.setHospCachetAbroabUrl(url);
                    hospDept.setHospCachetWithinUrl(urlTop);
                    sysDao.getServiceDo().modify(hospDept);
                    this.newMsgJson(finalSuccessrMsg);
                }else{
                    this.newMsgJson("参数异常!");
                }
            }else{
                this.newMsgJson(this.finalErrorMsg);
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
     * 机构数据导入
     * @return
     */
    public String importExcel(){
        try{
            ExcelReader excelReader = new ExcelReader();
            InputStream is2 = new FileInputStream(upExcel);
            Map<Integer, String> map = excelReader.readExcelContent(is2,1);//读取Excel数据内容
            if(map.size() >0) {
                CdUser user = this.getSessionUser();
                String result = this.getSysDao().getAppHospDeptDao().addImportExcelHosp(map);
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
