package com.ylz.view.sign.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppTeamMemberQvo;
import com.ylz.bizDo.app.vo.AppTeamQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.vo.DeptSelect;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMemberEntity;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.criterion.Restrictions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Action(
        value="teamAction",
        results={
                @Result(name = "json", type = "json",params={"root","jsons","contentType", "application/json"}),
                @Result(name = "jList", type = "json",params={"root","jList","contentType", "application/json"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","contentType", "application/json"})
        }
)
public class TeamAction extends CommonAction {
    private List jList;
    /**
     * 初始数据
     * @return
     */
    public String commList(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            //团队类型
            List<CdCode> teamType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_TEAMTYPE, CommonEnable.QIYONG.getValue());
            //工作类型
            List<CdCode> workType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_WORKTYPE, CommonEnable.QIYONG.getValue());
            map.put("teamType",teamType);
            map.put("workType",workType);
            this.getJsons().setMap(map);
        }catch (Exception e){
            new ActionException(getClass(),getAct(),getJsons(),e);
        }
        return "json";
    }

    /**
     * 查询团队
     * @return
     */
    public String list() {
        try {
            AppTeamQvo qvo = (AppTeamQvo)getJsonLay(AppTeamQvo.class);
            if(qvo==null){
                qvo=new AppTeamQvo();
            }
            List<AppTeam> ls = sysDao.getAppTeamDao().findListQvo2(qvo);
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        } catch (Exception e) {
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }

    //查询机构
    public String findOrg(){
        try {
            String name=getRequest().getParameter("name");
            if(StringUtils.isNotBlank(name)) {
                List<DeptSelect> ls = sysDao.getCdDeptDao().findDeptByName(name);
                this.setjList(ls);
            }
        } catch (Exception e) {
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jList";
    }


    public String findOrgById(){
        try {
            String id=getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)) {
                AppHospDept l = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,id);
                getJsons().setVo(l);
            }
        } catch (Exception e) {
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    /**
     * 新增
     * @return
     */
    public String add(){
        try{
            AppTeam vo = (AppTeam) getVoJson(AppTeam.class);
            if (vo != null) {
                //判断团队名是否已经存在
                List<AppTeam> ls=this.getSysDao().getServiceDo().getSessionFactory().getCurrentSession().createCriteria(AppTeam.class)
                        .add(Restrictions.eq("teamHospId",vo.getTeamHospId()))
                        .add(Restrictions.eq("teamName",vo.getTeamName()))
                        .list();
                if(ls!=null && ls.size()>0){
                    this.newMsgJson("团队创建失败,团队名称已存在!");
                    return "json";
                }
                //判断编号是否已经存在
                String code = vo.getTeamHospId().substring(0,3);
                if("pt_".equals(code)){
                    List<AppTeam> ls2=this.getSysDao().getServiceDo().getSessionFactory().getCurrentSession().createCriteria(AppTeam.class)
                            .add(Restrictions.ilike("teamHospId",code))
                            .add(Restrictions.eq("teamCode",vo.getTeamCode()))
                            .list();
                    if(ls2!=null && ls2.size()>0){
                        this.newMsgJson("编号已存在,请使用其他编号!");
                        return "json";
                    }
                }else{
                    List<AppTeam> ls2=this.getSysDao().getServiceDo().getSessionFactory().getCurrentSession().createCriteria(AppTeam.class)
                            .add(Restrictions.eq("teamHospId",vo.getTeamHospId()))
                            .add(Restrictions.eq("teamCode",vo.getTeamCode()))
                            .list();
                    if(ls2!=null && ls2.size()>0){
                        this.newMsgJson("编号已存在,请使用其他编号!");
                        return "json";
                    }
                }

                //添加
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
     * 查询单个记录
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            getJsons().setVo((AppTeam) sysDao.getServiceDo().find(AppTeam.class, id));
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
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
                this.sysDao.getServiceDo().modify(vo);
                //原来队长
                AppTeamMember mem= (AppTeamMember) this.getSysDao().getServiceDo().getSessionFactory().getCurrentSession().createCriteria(AppTeamMember.class)
                        .add(Restrictions.eq("memTeamid",vo.getId()))
                        .add(Restrictions.eq("memState","0")).uniqueResult();
                //查询新队长是否存在
                AppTeamMember mem2= (AppTeamMember) this.getSysDao().getServiceDo().getSessionFactory().getCurrentSession().createCriteria(AppTeamMember.class)
                        .add(Restrictions.eq("memTeamid",vo.getId()))
                        .add(Restrictions.eq("memDrId",vo.getTeamDrId())).uniqueResult();
                //成员存在直接改为队长
                if(mem2!=null){
                    mem2.setMemState("0");
                    sysDao.getServiceDo().modify(mem2);//修改新队长
                    //原队长改为成员
                    if(mem!=null){
                        if(!mem.getMemDrId().equals(vo.getTeamDrId())){
                            mem.setMemState("1");
                            this.sysDao.getServiceDo().modify(mem);
                        }
                    }
                }else {
                    //原队长改为成员
                    if(mem!=null){
                        if(!mem.getMemDrId().equals(vo.getTeamDrId())){
                            mem.setMemState("1");
                            this.sysDao.getServiceDo().modify(mem);
                        }
                    }
                    AppTeamMember v = new AppTeamMember();
                    v.setMemDrId(vo.getTeamDrId());
                    v.setMemTeamid(vo.getId());
                    v.setMemTeamName(vo.getTeamName());
                    v.setMemWorkType("3");
                    v.setMemState("0");
                    AppDrUser duer= (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,vo.getTeamDrId());
                    v.setDrTel(duer.getDrTel());
                    v.setMemDrName(vo.getTeamDrName());
                    sysDao.getServiceDo().add(v);//新增新队长
                }
                sysDao.getAppTeamMemberDao().updateTeamMember(vo);
                this.newMsgJson(finalSuccessrMsg);
            }else{
                this.newMsgJson(finalErrorMsg);
            }
        }catch (Exception e) {
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
    public String delete() {
        try {
            String id = this.getRequest().getParameter("id");
            String[] ids = id.split(";");
            if (ids != null && ids.length > 0) {
                for (String s : ids) {
                    List<AppSignForm> ls = this.sysDao.getAppSignformDao().findSignFormByTeamId(s);
                    if(ls!=null && ls.size()>0){
                        this.newMsgJson("团队存在签约人员，团队无法删除！");
                        return "json";
                    }
//                    List<AppTeamMember> mems = this.sysDao.getServiceDo().loadByPk(AppTeamMember.class, "memTeamid", s);
//                    for (AppTeamMember mem : mems) {
//                        sysDao.getServiceDo().delete(mem);
//                    }
//                    String result = PropertiesUtil.getConfValue("messageCode");
//                    if (result.equals("0")) {
//                        AppTeam team = (AppTeam) this.getSysDao().getServiceDo().find(AppTeam.class, s);
//                        //环信删群
//                        this.getSysDao().getSecurityCardAsyncBean().delGroup(team.getTeamEaseGroupId());
//                        this.getSysDao().getSecurityCardAsyncBean().delRoom(team.getTeamEaseRoomId());
//                    }
                    //删除团队不能真删，修改团队有效标志状态
                    AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,s);
                    if(team != null){
                        team.setTeamDelState("1");
                        team.setTeamDelTime(Calendar.getInstance());
                        sysDao.getServiceDo().modify(team);
                    }
//                    sysDao.getServiceDo().delete(AppTeam.class, s);
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
     *  Dmao
     * 机构id找团队 签约初始化
     * @return
     */
    public String findAll(){
        try{
            String hospid = this.getRequest().getParameter("hospid");
            if(StringUtils.isNotBlank(hospid)){
                List<AppTeam> teams = this.sysDao.getAppTeamDao().findAll(hospid);
                this.setjList(teams);
            }
        }catch (Exception e) {
            new ActionException(getClass(), getAct(), e);
        }
        return "jList";
    }

    /**
     *  Dmao
     * 团队id 找团队信息
     * @return
     */

    public String findteemAll(){
        try{
            String teamid = this.getRequest().getParameter("teamid");
            if(StringUtils.isNotBlank(teamid)){
                List<AppTeamMemberEntity> teamMemE = this.sysDao.getAppTeamMemberDao().findteamidM(teamid);
                this.setjList(teamMemE);
            }
        }catch (Exception e){
            new ActionException(getClass(), getAct(), e);
        }
        return "jList";
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
                List<AppDrUser> ls = this.sysDao.getAppDrUserDao().findByHosp(team.getTeamHospId());
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

    /**
     * 查询团队成员列表
     * @return
     */
    public String listMemCmm(){
        try{
            AppTeamMemberQvo qvo = (AppTeamMemberQvo)getJsonLay(AppTeamMemberQvo.class);
            List<AppTeamMember> ls = sysDao.getAppTeamMemberDao().findListQvo(qvo);
            this.getJsonLayui().setData(ls);
            this.getJsonLayui().setCount(qvo.getItemCount());
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "jsonLayui";
        }
        return "jsonLayui";
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
                if(StringUtils.isNotBlank(mem.getDrTel())){
                    AppDrUser druser= (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,mem.getMemDrId());
                    druser.setDrTel(mem.getDrTel());
                    sysDao.getServiceDo().modify(druser);
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

    public String modifyMemCmm(){
        try{
            AppTeamMember mem = (AppTeamMember) getVoJson(AppTeamMember.class);
            if (mem != null) {
                AppTeamMember vo = (AppTeamMember) this.sysDao.getServiceDo().find(AppTeamMember.class,mem.getId());
                vo.setMemWorkType(mem.getMemWorkType());
                vo.setMemDrName(mem.getMemDrName());
                vo.setMemDrId(mem.getMemDrId());
                vo.setDrRole(mem.getDrRole());
                vo.setDrTel(mem.getDrTel());
                if(StringUtils.isNotBlank(mem.getDrTel())){
                    AppDrUser druser= (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,mem.getMemDrId());
                    druser.setDrTel(mem.getDrTel());
                    sysDao.getServiceDo().modify(druser);
                }
                sysDao.getServiceDo().removePoFormSession(mem);
                sysDao.getServiceDo().modify(vo);
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


    public List getjList() {
        return jList;
    }

    public void setjList(List jList) {
        this.jList = jList;
    }
}
