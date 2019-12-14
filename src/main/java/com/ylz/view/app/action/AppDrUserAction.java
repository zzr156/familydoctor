package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppRole;
import com.ylz.bizDo.app.vo.AppDrUserQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.cd.vo.CdAddressSvo;
import com.ylz.bizDo.jtapp.commonEntity.AppFindDeptEntity;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.ExcelReader;
import com.ylz.packcommon.common.util.Md5Util;
import com.ylz.packcommon.common.util.PinyinUtil;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/15.
 */
@SuppressWarnings("all")
@Action(
        value="appdr",
        results={
                @Result(name="list", location="/intercept/app/drUser/drUser_list.jsp"),
                @Result(name="edit", location="/intercept/app/drUser/drUser_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
                @Result(name = "jsonUpload", type = "json",params={"root","jsons","contentType", "text/html"})
        }
)
public class AppDrUserAction extends CommonAction {

    private static final long serialVersionUID = 1L;

    private AppDrUser vo;
    private AppDrUserQvo qvo;
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
     * 查询list页面初始数据
     * @return
     */
    public String list() {
        try{
            AppDrUserQvo qvo = (AppDrUserQvo)getQvoJson(AppDrUserQvo.class);
            List<AppDrUser> ls = sysDao.getAppDrUserDao().findList(qvo);
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
     * 查询个人数据
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, id));
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }

    }

    /**
     * 新增加医生用户
     * @return
     */
    public String add(){
        try {
            AppDrUser vo = (AppDrUser)getVoJson(AppDrUser.class);
            if (vo != null) {
                //判断账号是否存在
                List<AppDrUser> drUsers = this.sysDao.getServiceDo().loadByPk(AppDrUser.class,"drAccount",vo.getDrAccount());
                if (drUsers != null && drUsers.size() > 0) {
                    this.newMsgJson("此账号已存在");
                    return "json";
                }
                // 加密
                String md5UserPassword = Md5Util.MD5(vo.getDrPwd());
                vo.setDrPwd(md5UserPassword);
                vo.setDrTelPwd(md5UserPassword);
                vo.setDrBopomofo(PinyinUtil.getPinYinHeadChar(vo.getDrName()));
                vo.setDrRole(vo.getDrRole().substring(0,vo.getDrRole().length()-1));
                this.sysDao.getServiceDo().add(vo);
                String result = PropertiesUtil.getConfValue("messageCode");
                if(result.equals("0")){
                    //添加环信账号
                    this.getSysDao().getSecurityCardAsyncBean().registeredEasemob(vo.getId());
                }
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
     * 修改
     * @return
     */
    public String modify(){
        try{
            AppDrUser vo =(AppDrUser) getVoJson(AppDrUser.class);
            if(vo!=null){
                AppDrUser vos = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class,vo.getId());
                if(vos!=null){
                    if(!vo.getDrPwd().equals(vos.getDrPwd())){
                        // 加密
                        String md5UserPassword = Md5Util.MD5(vo.getDrPwd());
                        vos.setDrPwd(md5UserPassword);
                        vos.setDrTelPwd(md5UserPassword);
                    }
                    vos.setDrAccount(vo.getDrAccount());
                    vos.setDrName(vo.getDrName());
                    vos.setDrGender(vo.getDrGender());
                    vos.setDrHospId(vo.getDrHospId());
                    vos.setDrCode(vo.getDrCode());
                    vos.setDrRole(vo.getDrRole().substring(0,vo.getDrRole().length()-1));
                    vos.setDrTel(vo.getDrTel());
                    vos.setDrJobTitle(vo.getDrJobTitle());
                    vos.setDrIntro(vo.getDrIntro());
                    vos.setDrGood(vo.getDrGood());
                    vos.setDrType(vo.getDrType());
                    vos.setDrState(vo.getDrState());
                    vos.setDrImageurl(vo.getDrImageurl());
                    vos.setDrReferralState(vo.getDrReferralState());
                    vos.setDrImageName(vo.getDrImageName());
                    vos.setDrDepartmentId(vo.getDrDepartmentId());
                    vos.setDrWebState(vo.getDrWebState());
                    //session出现实体重复时的处理
                    this.sysDao.getServiceDo().removePoFormSession(vo);
                    this.sysDao.getServiceDo().modify(vos);
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
     * 删除个人和批量删除
     * @return
     */
    public String delete(){
        try{
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                String[] ids = id.split(";");//批量删除
                for(String s:ids){
                    sysDao.getServiceDo().delete(AppDrUser.class,s);
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
     * 页面初始化
     * @return
     */
    public String findCmmInit() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            //性别
            List<CdCode> sex = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CODESEX,CommonEnable.QIYONG.getValue());
            //在职状态
            List<CdCode> state = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_WORKSTATE, CommonEnable.QIYONG.getValue());
            //省
            List<CdAddressSvo> ls= this.getSysDao().getCdAddressDao().findByPidList(null);
            map.put("province",ls);
//        //医生类别
            List<CdCode> ysType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_YSTYPE,CommonEnable.QIYONG.getValue());
            List<AppRole> roles = this.getSysDao().getServiceDo().findAll(AppRole.class);
            List<CdCode> sf = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON,CommonEnable.QIYONG.getValue());
            map.put("drGender",sex);
            map.put("drState",state);
            map.put("drType",ysType);
            map.put("roles",roles);
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
     * 无分页查询全部
     * @return
     */
    public String findCmmList(){
        try{
            CdUser us= this.getSessionUser();
            if(us!=null) {
                if (us.getCdDept() != null) {
                    List<AppDrUser> ls = sysDao.getAppDrUserDao().findAll(us.getCdDeptId());
                    jsons.setRows(ls);
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

    public String findCmmByHosp(){
        try{
            String hospId = this.getRequest().getParameter("hospId");
            List<AppDrUser> ls = sysDao.getAppDrUserDao().findByHosp(hospId);
            jsons.setRows(ls);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }
    public String findCmmDepartment(){
        try{
            String hospId = this.getRequest().getParameter("hospId");
            List<AppFindDeptEntity> list = sysDao.getAppHospitalDepartmentsDao().findDeptList(hospId);
            jsons.setRows(list);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 医生数据导入
     * @return
     */
    public String importExcel(){
        try{
            ExcelReader excelReader = new ExcelReader();
            InputStream is2 = new FileInputStream(upExcel);
            Map<Integer, String> map = excelReader.readExcelContent(is2,1);//读取Excel数据内容
            if(map.size() >0) {
                CdUser user = this.getSessionUser();
                String result = this.getSysDao().getAppDrUserDao().addImportExcelDr(map);
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
    public AppDrUser getVo() {
        return vo;
    }

    public void setVo(AppDrUser vo) {
        this.vo = vo;
    }

    public AppDrUserQvo getQvo() {
        return qvo;
    }

    public void setQvo(AppDrUserQvo qvo) {
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
