package com.ylz.view.smNcd.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.entity.AddressHospEntity;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfileYlkDTO;
import com.ylz.bizDo.jtapp.basicHealthVo.DwellerfileQvo;
import com.ylz.bizDo.jtapp.commonVo.AppAddressQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamEntity;
import com.ylz.bizDo.smjq.po.NcdNewTable;
import com.ylz.bizDo.smjq.smEntity.*;
import com.ylz.bizDo.smjq.smVo.*;
import com.ylz.bizDo.web.po.WebCdDept;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.*;

/**
 * 三明高血压和糖尿病数据处理action
 * Created by zzl on 2018/7/25.
 */
@SuppressWarnings("all")
@Action(
        value="smNcd",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class SmNcdAction extends CommonAction {
    private static final  int yxDay = 15;
    /**
     * 登录接口
     * @return
     */
    public String mbLogin(){
        try{
            AppSmLoginVo qvo = (AppSmLoginVo)this.getAppJson(AppSmLoginVo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getAccount())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("账号不能为空");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getPassWord())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("密码不能为空");
                    return "ajson";
                }
                String areaId = "";//区域编码
                String areaName = "";
                String areaLevel = "";
                String areaCodee = "";
                String hospX = "";//机构x轴
                String hospY = "";//机构y轴
//                String md5UserPassword = Md5Util.MD5(qvo.getPassWord());
                CdUser user = sysDao.getUserDo().findUser(qvo.getAccount(), qvo.getPassWord());
                if (user == null) {
                    AppDrUser drUser = sysDao.getAppDrUserDao().findByUserWeb(qvo.getAccount(),  qvo.getPassWord());
                    if (drUser == null) {
                        this.getAjson().setMsg("该用户不存在或密码错误,请重新输入!");
                        this.getAjson().setMsgCode("900");
                        return "ajson";
                    } else {
                        if ("0".equals(drUser.getDrWebState())) {
                            this.getAjson().setMsg("该用户不存在或密码错误,请重新输入!");
                            this.getAjson().setMsgCode("900");
                            return "ajson";
                        } else {
                            this.getRequest().getSession().setAttribute("DrTypeRole",drUser.getDrTypeRole()!=null?drUser.getDrTypeRole():"");
                            CdUser cdUser = new CdUser();
                            cdUser.setAccount(drUser.getDrAccount());
                            cdUser.setCdDpetId(drUser.getDrHospId());
                            cdUser.setHospId(drUser.getDrHospId());
                            cdUser.setUserPassword(drUser.getDrPwd());
                            cdUser.setUserName(drUser.getDrName());
                            cdUser.setDrId(drUser.getId());
                            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
                            if (dept != null) {
                                hospX = dept.getHospX();
                                hospY = dept.getHospY();
                                areaCodee = dept.getHospAreaCode();
                                String areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode());
                                if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                    if(drUser.getDrRole().indexOf(AppRoleType.SHENG.getValue())!=-1){
                                        areaId = dept.getHospAreaCode().substring(0,2)+"0000000000";
                                        CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,areaId);
                                        if(address != null){
                                            areaName = address.getAreaSname();
                                            areaLevel = address.getLevel();
                                        }
                                    }else if(drUser.getDrRole().indexOf(AppRoleType.SHI.getValue())!=-1){
                                        areaId = dept.getHospAreaCode().substring(0,4)+"00000000";
                                        CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,areaId);
                                        if(address != null){
                                            areaName = address.getAreaSname();
                                            areaLevel = address.getLevel();
                                        }
                                    }else if(drUser.getDrRole().indexOf(AppRoleType.QU.getValue())!=-1){
                                        areaId = dept.getHospAreaCode().substring(0,6)+"000000";
                                        CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,areaId);
                                        if(address != null){
                                            areaName = address.getAreaSname();
                                            areaLevel = address.getLevel();
                                        }
                                    }else{
                                        areaId = dept.getId();
                                        areaName = dept.getHospName();
                                        areaLevel = AppRoleType.SHEQU.getValue();
                                    }
                                }

                                WebCdDept cdDept = (WebCdDept) sysDao.getServiceDo().find(WebCdDept.class, dept.getId());
                                if (cdDept == null) {
                                    cdDept = new WebCdDept();
                                    cdDept.setCjsj(new Date());
                                    cdDept.setArea(dept.getHospAreaCode());
                                    cdDept.setId(dept.getId());
                                    cdDept.setDeptType("0");
                                    sysDao.getServiceDo().add(cdDept);
                                }
                                String[] roleIds = sysDao.getUserDo().getRoleIdd(areaCode,drUser.getDrRole(),null);
                                if(roleIds.length>0){
                                    cdUser.setRoleid(roleIds);
                                }
                                cdUser.setCdDpetId(cdDept.getId());
                                sysDao.getServiceDo().add(cdUser);
                            }
                            user = cdUser;
                        }
                    }
                }else{
                    AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,user.getDrId());
                    if(drUser!=null){
                        if(org.apache.commons.lang3.StringUtils.isNotBlank(drUser.getDrHospId())){
                            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
                            if (dept != null) {
                                hospX = dept.getHospX();
                                hospY = dept.getHospY();
                                if(StringUtils.isNotBlank(dept.getHospAreaCode())){
                                    areaCodee = dept.getHospAreaCode();
                                    if(drUser.getDrRole().indexOf(AppRoleType.SHENG.getValue())!=-1){
                                        areaId = dept.getHospAreaCode().substring(0,2)+"0000000000";
                                        CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,areaId);
                                        if(address != null){
                                            areaName = address.getAreaSname();
                                            areaLevel = address.getLevel();
                                        }
                                    }else if(drUser.getDrRole().indexOf(AppRoleType.SHI.getValue())!=-1){
                                        areaId = dept.getHospAreaCode().substring(0,4)+"00000000";
                                        CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,areaId);
                                        if(address != null){
                                            areaName = address.getAreaSname();
                                            areaLevel = address.getLevel();
                                        }
                                    }else if(drUser.getDrRole().indexOf(AppRoleType.QU.getValue())!=-1){
                                        areaId = dept.getHospAreaCode().substring(0,6)+"000000";
                                        CdAddress address = (CdAddress)sysDao.getServiceDo().find(CdAddress.class,areaId);
                                        if(address != null){
                                            areaName = address.getAreaSname();
                                            areaLevel = address.getLevel();
                                        }
                                    }else{
                                        areaId = dept.getId();
                                        areaName = dept.getHospName();
                                        areaLevel = AppRoleType.SHEQU.getValue();
                                    }
                                }
                                String roleStr = "";
                                if(user.getRoleid().length>0){
                                    for(String role:user.getRoleid()){
                                        if(org.apache.commons.lang3.StringUtils.isBlank(roleStr)){
                                            roleStr = role;
                                        }else {
                                            roleStr +=";"+ role;
                                        }
                                    }
                                }
                                String areaCode = AreaUtils.getAreaCode(dept.getHospAreaCode());
                                String[] roleIds = sysDao.getUserDo().getRoleIdd(areaCode,drUser.getDrRole(),roleStr);
                                if(roleIds.length>0){
                                    user.setRoleid(roleIds);
                                    sysDao.getServiceDo().modify(user);
                                }
                            }
                        }
                        this.getRequest().getSession().setAttribute("DrTypeRole",drUser.getDrTypeRole()!=null?drUser.getDrTypeRole():"");
                    }
                }
                AppSmLoginEntity vo = sysDao.getAppDrUserDao().findDrinfoxx(user.getDrId());
                if(vo != null){
                    Md5Util util = new Md5Util();
                    String key = qvo.getAccount()+ ExtendDate.getYMD_h_m_s(Calendar.getInstance());
                    key = util.MD516(key);
                    AppDrPatientKey uKey = this.sysDao.getAppDrPatientKeyDao().findByDoctorOrPatientId(user.getDrId());
                    String day = ExtendDateUtil.addDate(ExtendDate.getYMD(Calendar.getInstance()), ExtendDateType.DAYS.getValue(),yxDay);
                    if(uKey != null){
                        uKey.setDrTvToken(key);
                        uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                        uKey.setDrPatientType(CommonDrPartientType.yisheng.getValue());
                        uKey.setDrTempId(null);
                        uKey.setDrTempToken(null);
                        uKey.setDrPatientLastDate(uKey.getHsUpdateTime());
                        this.sysDao.getServiceDo().modify(uKey);
                    }else{
                        uKey = new AppDrPatientKey();
                        uKey.setDrTvToken(key);
                        uKey.setDrPatientId(user.getDrId());
                        uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
                        uKey.setDrPatientType(CommonDrPartientType.yisheng.getValue());
                        uKey.setDrPatientLastDate(Calendar.getInstance());
                        this.getSysDao().getServiceDo().add(uKey);
                    }
                    this.getAjson().setUkey(uKey.getDrTvToken());
                    //山西慢病登入开发token加密
                    if(StringUtils.isNotBlank(vo.getOrgId())){
                        AppHospDept orgDept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,vo.getOrgId());
                        if(orgDept != null){
                            hospX = orgDept.getHospX();
                            hospY = orgDept.getHospY();
                            if("14".equals(AreaUtils.getAreaCode(orgDept.getHospAreaCode(),"1"))){
                                String rule = PropertiesUtil.getConfValue("ruleAuthorization");;
                                String token = uKey.getDrTvToken();
                                StringBuilder sb =new StringBuilder(token);
                                System.out.println(token);
                                int v = 1;
                                for(int i=0;i<rule.length();i++){
                                    String jg = token.substring(token.length()-v,token.length()-v+1);
                                    sb.insert(Integer.parseInt(rule.substring(i,i+1)),jg);
                                    v++;
                                }
                                System.out.println(sb);
                                this.getAjson().setUkey(String.valueOf(sb));
                            }
                        }
                    }
                }
                Map<String,Object> map = new HashMap<>();
                map.put("areaId",areaId);
                map.put("areaName",areaName);
                map.put("areaLevel",areaLevel);
                map.put("areaCode",areaCodee);
                map.put("hospX",hospX);
                map.put("hospY",hospY);
                this.getAjson().setMap(map);
                this.getAjson().setVo(vo);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询基础信息接口
     * @return
     */
    public String findPeopleBasic(){
        try{
            AppSmPeopleBasicVo qvo = (AppSmPeopleBasicVo)this.getAppJson(AppSmPeopleBasicVo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppSmPeopleBasicEntity vo = new AppSmPeopleBasicEntity();
                if("1".equals(qvo.getType())){//查询血压数据
                    vo = sysDao.getAppUserBloodpressureDao().findPeopleBasic(qvo);
                }else if("2".equals(qvo.getType())){//查询血糖数据
                    vo = sysDao.getAppUserBloodgluDao().findPeopleBasic(qvo);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查询类型不规范");
                }
                this.getAjson().setVo(vo);
                this.getAjson().setQvo(qvo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询机构糖尿病高血压居民
     * @return
     */
    public String findPeopleByOrg(){
        try{
            AppSmPeopleBasicVo qvo = (AppSmPeopleBasicVo)this.getAppJson(AppSmPeopleBasicVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppPeopleBasicEntity> list = sysDao.getAppSignformDao().findPeopleByOrg(qvo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
                this.getAjson().setQvo(qvo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询三明尤溪高血压和糖尿病居民信息
     * @return
     */
    public String paopleList(){
        try{
            AppSmyxPatientVo qvo = new AppSmyxPatientVo();
            qvo.setAreaCode("350426");
            List<AppSmyxPatientEntity> list = sysDao.getAppSignformDao().findPeopleList(qvo);
            this.getAjson().setRows(list);
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 修改居民x、y轴坐标
     * @return
     */
    public String modifyXYaxis(){
        try{
            AppSmyxPatientVo qvo = (AppSmyxPatientVo) this.getAppJson(AppSmyxPatientVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isBlank(qvo.getPatientId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("居民主键不能为空");
                }else{
                    AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getPatientId());
                    if(user == null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无居民信息");
                    }else{
                        if(StringUtils.isBlank(user.getPatientX())){
                            user.setPatientX(qvo.getxAxis());
                        }
                        if(StringUtils.isBlank(user.getPatientY())){
                            user.setPatientY(qvo.getyAxis());
                        }
                        sysDao.getServiceDo().modify(user);
                        sysDao.getAppLabelGroupDao().addLabelDis(user);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 根据区域编码查询区域列表
     * @return
     */
    public String findAreaByCode(){
        try{
            AppAddressQvo qvo = (AppAddressQvo) this.getAppJson(AppAddressQvo.class);
            if (qvo != null) {
                if(StringUtils.isNotBlank(qvo.getUpId())){
                    CdAddress entity = this.getSysDao().getCdAddressDao().findByCacheCode(qvo.getUpId());
                    if(entity != null){
                        if(entity.getLevel().equals("1")){//省查各市
                            List<AddressHospEntity> ls = this.getSysDao().getCdAddressDao().findByUpIdOrNotTs(qvo.getUpId());
                            this.getAjson().setRows(ls);
                        }else if(entity.getLevel().equals("2")){//市查各区
                            List<AddressHospEntity> ls = this.getSysDao().getCdAddressDao().findByUpIdOrNotTs(qvo.getUpId());
                            this.getAjson().setRows(ls);
                        }else if (entity.getLevel().equals("3")){//区查各街道
                            List<AddressHospEntity> ls = this.getSysDao().getAppHospDeptDao().findByAreaTsId(qvo.getUpId());
                            this.getAjson().setRows(ls);
                        }else if(entity.getLevel().equals("4")){//街道查询医院
                            List<AppHospDept> hospDepts = sysDao.getAppHospDeptDao().findUpHospListRead(qvo.getUpId());
                            this.getAjson().setRows(hospDepts);
                        }
                    }
                }else if(StringUtils.isNotBlank(qvo.getHospId())){//医院查询团队
                    List<AppTeam> ls = this.getSysDao().getAppTeamDao().findTeamByHospIdAndState(qvo.getHospId());
                    this.getAjson().setRows(ls);
                } else if(StringUtils.isNotBlank(qvo.getTeamId())){//团队查询成员
                    List<AppDrUser> list = sysDao.getAppTeamMemberDao().findDrListByTeamId(qvo.getTeamId());
                    this.getAjson().setRows(list);
                }
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询分标管理修改日志记录
     * @return
     */
    public String findModifyLog(){
        try{
            AppSmPeopleBasicVo qvo = (AppSmPeopleBasicVo)this.getAppJson(AppSmPeopleBasicVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppModifyLogEntity> list = sysDao.getAppMarkingLogDao().findMarkingLogList(qvo);
                this.getAjson().setQvo(qvo);
                this.getAjson().setRows(list);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 根据条件查询有修改居民信息
     * @return
     */
    public String findModifyPeopleList(){
        try{
            AppSmPeopleBasicVo qvo = (AppSmPeopleBasicVo)this.getAppJson(AppSmPeopleBasicVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppModifyPeopleListEntity> list = sysDao.getAppMarkingLogDao().findModifyPeopleList(qvo);
                this.getAjson().setQvo(qvo);
                this.getAjson().setRows(list);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 尤溪慢病统计
     * @return
     */
    public String manageCount(){
        try{
            AppSmMnanageQvo qvo = (AppSmMnanageQvo)this.getAppJson(AppSmMnanageQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
//                if(StringUtils.isBlank(qvo.getAreaCode())){
//                    qvo.setAreaCode("350426000000");//默认三明尤溪县
//                }
                Map<String,Object> amp = sysDao.getAppSignAnalysisDao().findManageSmNCD(qvo);
                this.getAjson().setMap(amp);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 慢病系统退出接口
     * @return
     */
    public String mbExit(){
        try{
            AppSmPeopleBasicVo qvo = (AppSmPeopleBasicVo)this.getAppJson(AppSmPeopleBasicVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isNotBlank(qvo.getDrId())){
                    AppDrPatientKey key = sysDao.getAppDrPatientKeyDao().findByDrOrPatient(qvo.getDrId());
                    if(key != null){
                        sysDao.getServiceDo().delete(key);
                    }
                }else{
                    this.getAjson().setMsg("查无医生信息");
                    this.getAjson().setMsgCode("900");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询机构糖尿病、高血压、精神病、结核病居民 或关系查询
     * @return
     */
    public String findPeopleByOrgNew(){
        try{
            AppSmPeopleBasicVo qvo = (AppSmPeopleBasicVo)this.getAppJson(AppSmPeopleBasicVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                Map<String,Object> map = new HashMap<>();
//                map = sysDao.
                map = sysDao.getAppSignformDao().findMapCount(qvo);
                this.getAjson().setMap(map);
                List<AppPeopleBasicEntity> list = sysDao.getAppSignformDao().findPeopleByOrgNew(qvo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
                this.getAjson().setQvo(qvo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询精神病和结核病坐标为空患者
     * @return
     */
    public String findPeopleNew(){
        try{
            AppSmyxPatientVo qvo = new AppSmyxPatientVo();
            String areaCode = this.getRequest().getParameter("areaCode");
            String hospId = this.getRequest().getParameter("hospId");
//            qvo.setAreaCode("350426");
            qvo.setAreaCode(areaCode);
            qvo.setHospId(hospId);
            if(StringUtils.isNotBlank(qvo.getAreaCode())||StringUtils.isNotBlank(qvo.getHospId())){
                List<AppSmyxPatientEntity> list = sysDao.getAppSignformDao().findPeopleListNew(qvo);
                this.getAjson().setRows(list);
            }else{
                this.getAjson().setMsg("参数不能为空");
                this.getAjson().setMsgCode("900");
            }

        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 添加慢病健康教育系统模板
     * @return
     */
    public String saveHealthEducationByNcd(){
        try{
            AppNcdHEQvo vo= (AppNcdHEQvo) this.getAppJson(AppNcdHEQvo.class);
            if(StringUtils.isBlank(vo.getId())){
                this.getJsons().setCode("900");
                this.getJsons().setMsg("教育模板主键不能为空!");
            }else if(StringUtils.isBlank(vo.getTableTitle())){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("健康教育标题不能为空!");
            }else if(StringUtils.isBlank(vo.getTableContent())){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("健康教育内容不能为空!");
            }else if(StringUtils.isBlank(vo.getCityCode())){
                this.getAjson().setMsg("地市编码不能为空!");
                this.getAjson().setMsgCode("900");
            }else{
                String str = "";
                if(AddressType.FZS.getValue().equals(vo.getCityCode())){

                }else if(AddressType.PTS.getValue().equals(vo.getCityCode())){
                    str = "pt_";
                }else if(AddressType.SMS.getValue().equals(vo.getCityCode())){
                    str = "sm_";
                }else if(AddressType.QZS.getValue().equals(vo.getCityCode())){
                    str = "qz_";
                }else if(AddressType.ZZS.getValue().equals(vo.getCityCode())){
                    str = "zz_";
                }else if(AddressType.NPS.getValue().equals(vo.getCityCode())){
                    str = "np_";
                }
                vo.setDrId(str+vo.getDrId());
                vo.setTableHospId(str+vo.getTableHospId());
                vo.setTableCjrid(str+vo.getTableCjrid());
                String result = sysDao.getNewsTableDao().saveHealthByNcd(vo);
                if("true".equals(result)){
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("推送成功");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 直接上传档案到基卫（用于省库）
     * @return
     */
    public String upFileToBasic(){
        try{
            AppFileNcdQvo vo = (AppFileNcdQvo)this.getAppJson(AppFileNcdQvo.class);
            if(vo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                String requestUserId = null;
                String requestUserName = null;
                String url = "";
                DwellerfileQvo qqvo = new DwellerfileQvo();
                qqvo.setFileQvo(vo.getFileVo());
                qqvo.setUrlType(vo.getUrlType());
                /*if (StringUtils.isNotBlank(vo.getUrlType())) {
                    if (vo.getUrlType().equals(AddressType.FZ.getValue())) {
                        url = PropertiesUtil.getConfValue("FZ");
                    } else if (vo.getUrlType().equals(AddressType.QZ.getValue())) {
                        url = PropertiesUtil.getConfValue("QZ");
                    } else if (vo.getUrlType().equals(AddressType.ZZ.getValue())) {
                        url = PropertiesUtil.getConfValue("ZZ");
                    } else if (vo.getUrlType().equals(AddressType.PT.getValue())) {
                        url = PropertiesUtil.getConfValue("PT");
                    } else if (vo.getUrlType().equals(AddressType.NP.getValue())) {
                        url = PropertiesUtil.getConfValue("NP");
                    } else if (vo.getUrlType().equals(AddressType.SM.getValue())) {
                        url = PropertiesUtil.getConfValue("SM");
                    } else if (vo.getUrlType().equals(AddressType.CS.getValue())) {
                        url = PropertiesUtil.getConfValue("CS");
                    } else {
                        url = PropertiesUtil.getConfValue("SX");
                    }
                }*/
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = JSONObject.fromObject(qqvo);
                url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=uploadHealthCareRecord";
                String str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),requestUserId,requestUserName,str, DrPatientType.DR.getValue(),"uploadHealthCareRecord");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if(jsonall.get("entity") != null){
                        JSONObject entity = JSONObject.fromObject(jsonall.get("entity"));
                        if("true".equals(entity.get("success").toString())){
                            this.getAjson().setMsg("上传成功");
                            this.getAjson().setMsgCode("800");
                        }else{
                            this.getAjson().setMsg(entity.get("message").toString());
                            this.getAjson().setMsgCode("900");
                        }
                    }else{
                        this.getAjson().setMsg("上传失败");
                        this.getAjson().setMsgCode("900");
                    }
                }else{
                    this.getAjson().setMsg("上传失败");
                    this.getAjson().setMsgCode("900");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }
}


