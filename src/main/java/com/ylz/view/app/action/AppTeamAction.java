package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.bizDo.app.vo.AppTeamMemberQvo;
import com.ylz.bizDo.app.vo.AppTeamQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.cd.vo.CdAddressSvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.ExcelReader;
import com.ylz.packcommon.common.util.JsonUtil;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
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
 * Created by zzl on 2017/6/16.
 */
@Action(
        value="appteam",
        results={
                @Result(name="list", location="/intercept/app/team/team_list.jsp"),
                @Result(name="edit", location="/intercept/app/team/team_edit.jsp"),
                @Result(name="editMem", location="/intercept/app/team/teamMem_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
                @Result(name = "jsonUpload", type = "json",params={"root","jsons","contentType", "text/html"})
        }
)
public class AppTeamAction extends CommonAction {
    private static final long serialVersionUID = 1L;
    private AppTeam vo;
    private File upExcel; //上传的文件
    private String upExcelFileName; //文件名称
    private String upExcelContentType; //文件类型

    /**
     * 准备查询
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
     * 准备添加成员
     * @return
     */
    public String forAddMem(){
        return "editMem";
    }
    /**
     * 根据分页查询全部
     * @return
     */
    public String list(){
        try{
            AppTeamQvo qvo = (AppTeamQvo)getQvoJson(AppTeamQvo.class);
            List<AppTeam> ls = sysDao.getAppTeamDao().findListQvo(qvo);
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
     * 查询单个记录
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((AppTeam) sysDao.getServiceDo().find(AppTeam.class, id));
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }

    /**
     * 新增
     * @return
     */
    public String add(){
        try{
            AppTeam vo = (AppTeam) getVoJson(AppTeam.class);
            if (vo != null) {
                this.sysDao.getAppTeamDao().addTeam(vo);
                String result = PropertiesUtil.getConfValue("messageCode");
                if(result.equals("0")){
                    //添加团队
                    this.getSysDao().getSecurityCardAsyncBean().addGroup(vo);
                    this.getSysDao().getSecurityCardAsyncBean().addRoom(vo, CommonShortType.YISHENG.getValue());
                }
                this.newMsgJson(this.finalSuccessrMsg);
            }else{
                this.newMsgJson(finalErrorMsg);
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
     * 修改
     * @return
     */
    public String modify(){
        try {
            AppTeam vo = (AppTeam)getVoJson(AppTeam.class);
            if (vo != null) {
                AppTeam role = (AppTeam) sysDao.getServiceDo().find(AppTeam.class,vo.getId());
                role.setTeamName(vo.getTeamName());
                role.setTeamType(vo.getTeamType());
                role.setTeamRemarks(vo.getTeamRemarks());
                role.setTeamTel(vo.getTeamTel());
                role.setTeamSort(vo.getTeamSort());
                role.setTeamCode(vo.getTeamCode());
                role.setTeamState(vo.getTeamState());
                this.sysDao.getServiceDo().removePoFormSession(role);
                this.sysDao.getServiceDo().modify(role);
                if(StringUtils.isNotBlank(vo.getTeamName())){//如果修改团队名称，成员表的团队名称也要跟着改
                    List<AppTeamMember> list = sysDao.getServiceDo().loadByPk(AppTeamMember.class,"memTeamid",role.getId());
                    if(list != null && list.size()>0){
                        for(AppTeamMember tt:list){
                            tt.setMemTeamName(role.getTeamName());
                            sysDao.getServiceDo().modify(tt);
                        }
                    }
                }
                this.newMsgJson(finalSuccessrMsg);
            }else{
                this.newMsgJson(finalErrorMsg);
            }
        }catch (DaoException e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 删除
     * @return
     */
    public String delete(){
        try {
            String id = this.getRequest().getParameter("id");
            String[] ids = id.split(";");
            if(ids != null && ids.length >0){
                for(String s : ids){
                    List<AppSignForm> ls = this.sysDao.getAppSignformDao().findSignFormByTeamId(s);
                    if(ls!=null && ls.size()>0){
                        this.newMsgJson("团队存在签约人员，团队无法删除！");
                        return "json";
                    }

                    //新团队删除，不能真删需求
                    AppTeam team = (AppTeam)this.getSysDao().getServiceDo().find(AppTeam.class,s);
                    if(team != null){
                        team.setTeamDelState("1");
                        team.setTeamDelTime(Calendar.getInstance());
                        sysDao.getServiceDo().modify(team);
                    }

                    //旧的判断
                   /* //判断该团队有没有签约数据,有签约数据不予许删除
                    boolean flag = sysDao.getAppSignformDao().findSignByteam(s);
                    if(flag){//有签约数据不能真删除团队信息，修改团队状态
                        AppTeam team = (AppTeam)this.getSysDao().getServiceDo().find(AppTeam.class,s);
                        if(team != null){
                            team.setTeamDelState("1");
                            sysDao.getServiceDo().modify(team);
                        }
//                        this.newMsgJson("团队有签约数据，不可删除");
//                        return "json";
                    }else{//没有签约数据可以删除
                        List<AppTeamMember> mems = this.sysDao.getServiceDo().loadByPk(AppTeamMember.class,"memTeamid",s);
                        for(AppTeamMember mem:mems){
                            sysDao.getServiceDo().delete(mem);
                        }
                        String result = PropertiesUtil.getConfValue("messageCode");
                        if(result.equals("0")){
                            AppTeam team = (AppTeam)this.getSysDao().getServiceDo().find(AppTeam.class,s);
                            //环信删群
                            this.getSysDao().getSecurityCardAsyncBean().delGroup(team.getTeamEaseGroupId());
                            this.getSysDao().getSecurityCardAsyncBean().delRoom(team.getTeamEaseRoomId());
                        }
                        sysDao.getServiceDo().delete(AppTeam.class,s);
                    }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        this.newMsgJson(finalSuccessrMsg);
        return "json";

    }
    /**
     * 页面初始化
     * @return
     */
    public String findCmmInit(){
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            //获取当前用户机构
            String message="";
            CdUser us= this.getSessionUser();
            if(us!=null){
                if(us.getCdDept()!=null){
                    message=us.getCdDeptId()+";"+us.getCdDept().getSname();
                    //家庭医生
                    List<AppDrUser> dr = this.sysDao.getAppDrUserDao().findByType("4",us.getCdDeptId());
                    //团队名称(查当前机构下的团队）
                    List<AppTeam> teams = this.sysDao.getAppTeamDao().findAll(us.getCdDeptId());
                    //成员姓名
                    List<AppDrUser> drUsers = this.sysDao.getAppDrUserDao().findAll(us.getCdDeptId());

                    //工作类型
                    List<CdCode> workType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_WORKTYPE, CommonEnable.QIYONG.getValue());
                    //有效标识
                    List<CdCode> teamState = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_TEAM_STATE, CommonEnable.QIYONG.getValue());
                    //团队类型
                    List<CdCode> teamType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_TEAMTYPE,CommonEnable.QIYONG.getValue());
                    //省
                    List<CdAddressSvo> ls= this.getSysDao().getCdAddressDao().findByPidList(null);
                    map.put("province",ls);
                    map.put("dr",dr);
                    map.put("message",message);
                    map.put("teamState",teamState);
                    map.put("teams",teams);
                    map.put("drUsers",drUsers);
                    map.put("workType",workType);
                    map.put("teamType",teamType);
                    this.getJsons().setMap(map);
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
     * 查询团队成员列表
     * @return
     */
    public String listMemCmm(){
        try{
            AppTeamMemberQvo qvo = (AppTeamMemberQvo)getQvoJson(AppTeamMemberQvo.class);
            List<AppTeamMember> ls = sysDao.getAppTeamMemberDao().findListQvo(qvo);
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
     * 保存团队成员表
     * @return
     */
    public String addMemCmm(){
        try{
            AppTeamMember mem = (AppTeamMember) getVoJson(AppTeamMember.class);
            if (mem != null) {
                List<AppTeamMember> list = this.sysDao.getServiceDo().loadByPk(AppTeamMember.class,"memTeamid",mem.getMemTeamid());
                if(list.size()>0){
                    for(AppTeamMember ls:list){
                        if(ls.getMemDrId().equals(mem.getMemDrId())){
                            this.newMsgJson("成员已存在，无需在加！");
                            return "json";
                        }
                    }
                }

                if(StringUtils.isNotBlank(mem.getMemTeamid())){
                    AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,mem.getMemTeamid());
                    if(team!=null){
                        if(mem.getMemDrId().equals(team.getTeamDrId())){
                            mem.setMemState("0");
                        }else{
                            mem.setMemState("1");
                        }
                        mem.setMemTeamName(team.getTeamName());
                    }
                }
                this.sysDao.getServiceDo().add(mem);
                String result = PropertiesUtil.getConfValue("messageCode");
                if(result.equals("0")){
                    AppTeam team = (AppTeam) this.getSysDao().getServiceDo().find(AppTeam.class,mem.getMemTeamid());
                    //环信加群
                    this.getSysDao().getSecurityCardAsyncBean().addGroupMembers(team.getTeamEaseGroupId(),mem.getMemDrId());
                    //环信聊天室
                    this.getSysDao().getSecurityCardAsyncBean().addRoomMembers(team.getTeamEaseRoomId(),mem.getMemDrId(),CommonShortType.YISHENG.getValue());
                }
                this.newMsgJson(this.finalSuccessrMsg);
            }else{
                this.newMsgJson(finalErrorMsg);
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
     * 删除成员
     * @return
     */
    public String delMemCmm(){
        try {
            String id = this.getRequest().getParameter("id");
            String[] ids = id.split(";");
            if(ids != null && ids.length >0){
                for(String s : ids){
                    String result = PropertiesUtil.getConfValue("messageCode");
                    if(result.equals("0")){
                        AppTeamMember member = (AppTeamMember)this.getSysDao().getServiceDo().find(AppTeamMember.class,s);
                        if(member != null){
                            AppTeam team = (AppTeam) this.getSysDao().getServiceDo().find(AppTeam.class,member.getMemTeamid());
                            //环信退群
                            this.getSysDao().getSecurityCardAsyncBean().removeGroupMembers(team.getTeamEaseGroupId(),member.getMemDrId());
                            this.getSysDao().getSecurityCardAsyncBean().removeRoomMembers(team.getTeamEaseRoomId(),member.getMemDrId(),CommonShortType.YISHENG.getValue());
                        }
                    }
                    sysDao.getServiceDo().delete(AppTeamMember.class,s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        this.newMsgJson(finalSuccessrMsg);
        return "json";
    }

    /**
     * 获取机构下的医生
     * @return
     */
    public String findDrCmm(){
        try{
            String teamId = this.getRequest().getParameter("teamId");
            AppTeam team = (AppTeam)this.sysDao.getServiceDo().find(AppTeam.class,teamId);
            if(team!=null){
                List<AppDrUser> ls = this.sysDao.getAppDrUserDao().findListByHospId(team.getTeamHospId(),team.getTeamDrId());
                jsons.setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return "json";
    }

    public String findDeptCmm(){
        CdUser user = this.getSessionUser();
        if(!user.getAccount().equals("admin")){
            Map<String,Object> map = new HashMap<String,Object>();
            String hospId = user.getCdDeptId();
            String hospName = user.getCdDept().getSname();
            map.put("hospId",hospId);
            map.put("hospName",hospName);
            this.getJsons().setMap(map);
        }
        return "json";
    }
    /**
     * 导入团队数据excel
     * @return
     */
    public String importExcel(){
        try{
            ExcelReader excelReader = new ExcelReader();
            InputStream is2 = new FileInputStream(upExcel);
            Map<Integer, String> map = excelReader.readExcelContent(is2,1);//读取Excel数据内容
            if(map.size() >0) {
                CdUser user = this.getSessionUser();
                String result = this.getSysDao().getAppTeamDao().addImportExcelTeam(map);
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

    public AppTeam getVo() {
        return vo;
    }

    public void setVo(AppTeam vo) {
        this.vo = vo;
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
