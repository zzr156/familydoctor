package com.ylz.view.ysapp.action;


import com.ylz.bizDo.app.po.AppBloodglu;
import com.ylz.bizDo.app.po.AppBloodpressure;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.jtapp.commonEntity.DeviceEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.AppDeviceVo;
import com.ylz.bizDo.jtapp.drVo.DeviceQvo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.CommonDeviceType;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 设备管理action.
 */
@SuppressWarnings("all")
@Action(
        value="ysDevice",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsDeviceAction extends CommonAction {




    /**
     * 添加设备
     * @param type 设备类型 1血压计 2血糖仪
     * @param code 设备编号
     * @param teamId 归属团队
     * @param drId 医生id
     * @param userIdno1 绑定用户身份证号1(血糖仪只传这个)
     * @param userIdno2 绑定用户身份证号2
     * @return
     */
    public String appAddDevice(){
            try {
                AppDeviceVo vo = (AppDeviceVo)getAppJson(AppDeviceVo.class);
                if(vo != null ){
                    if(StringUtils.isBlank(vo.getCode())){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("设备号不能为空");
                        return "ajson";
                    }
                    boolean flag = sysDao.getSecurityCardAsyncBean().verification(vo.getCode(),vo.getType());
//                    boolean flag = sysDao.getAppDeviceDao().verification(vo.getCode(),vo.getType());
                    if(flag){
                        if(CommonDeviceType.XY.getValue().equals(vo.getType())){
                            AppBloodpressure bp = new AppBloodpressure();
                            List<AppBloodpressure> list = this.sysDao.getServiceDo().loadByPk(AppBloodpressure.class, "bpDevId", vo.getCode());
                            if (list != null && list.size() > 0) {
                            /*bp = list.get(0);
                            if ((bp.getBpUserOne() != null && vo.getUserIdno1()!=null)
                                    || (bp.getBpUserTwo() != null && vo.getUserIdno2()!=null)) {
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("按钮已绑定用户");
                                return "ajson";
                            }*/
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("设备已添加");
                                return "ajson";
                            }
                            if(StringUtils.isNotBlank(vo.getDrId())){
                                bp.setBpDrId(vo.getDrId());
                                AppDrUser user = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,vo.getDrId());
                                if (user!=null && user.getDrHospId() != null) {
                                    bp.setBpHospId(user.getDrHospId());
                                    AppHospDept hosp = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getDrHospId());
                                    bp.setBpAreaCode(hosp.getHospAreaCode());
                                }
                            }
                            if(StringUtils.isNotBlank(vo.getCode())){
                                bp.setBpDevId(vo.getCode());
                            }
                            if(StringUtils.isNotBlank(vo.getType())){
                                bp.setBpType(vo.getType());
                            }
                            if(StringUtils.isNotBlank(vo.getTeamId())){
                                bp.setBpTeamId(vo.getTeamId());
                            }
                            if (StringUtils.isNotBlank(vo.getUserIdno1())) {
                                bp.setBpDevTimeOne(Calendar.getInstance());
                                AppPatientUserEntity patientUserEntity = sysDao.getAppPatientUserDao().findByUserIdNo(vo.getUserIdno1());
                                if(patientUserEntity!=null&&patientUserEntity.getId()!=null){
                                    List<AppBloodpressure> bpList = sysDao.getAppBloodpressureDao().findByUserId(patientUserEntity.getId());
                                    if(!bpList.isEmpty()){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("该用户已绑定过血压计");
                                        return "ajson";
                                    }else{
                                        bp.setBpUserOne(patientUserEntity.getId());
                                        bp.setBpDevTimeOne(Calendar.getInstance());
                                    }
                                }else{
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg("找不到用户");
                                    return "ajson";
                                }
                            }
                            if (StringUtils.isNotBlank(vo.getUserIdno2())) {
                                bp.setBpDevTimeTwo(Calendar.getInstance());
                                AppPatientUserEntity patientUserEntity = sysDao.getAppPatientUserDao().findByUserIdNo(vo.getUserIdno2());
                                if(patientUserEntity!=null&&patientUserEntity.getId()!=null){
                                    List<AppBloodpressure> bpList = sysDao.getAppBloodpressureDao().findByUserId(patientUserEntity.getId());
                                    if(!bpList.isEmpty()){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("该用户已绑定过血压计");
                                        return "ajson";
                                    }
                                    bp.setBpUserTwo(patientUserEntity.getId());
                                    bp.setBpDevTimeTwo(Calendar.getInstance());
                                }
                            }
                            if (bp.getId() == null) {
                                bp.setBpCreateTime(Calendar.getInstance());
                                this.sysDao.getServiceDo().add(bp);
                                this.getAjson().setMsg("添加成功");
                                this.getAjson().setMsgCode("800");
                                this.getAjson().setVo(bp);
                            } else {
                                this.getSysDao().getServiceDo().modify(bp);
                                this.getAjson().setMsg("绑定成功");
                                this.getAjson().setMsgCode("800");
                                this.getAjson().setVo(bp);
                            }
                        }else if(CommonDeviceType.XT.getValue().equals(vo.getType())){
                            AppBloodglu glu = new AppBloodglu();
                            List<AppBloodglu> list = this.sysDao.getServiceDo().loadByPk(AppBloodglu.class, "bgDevId", vo.getCode());
                            if(list!=null && list.size()>0){
                            /*if(list.get(0).getBgPaientId()!=null){
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("该设备已绑定用户");
                                return "ajson";
                            }else{
                                glu=list.get(0);
                            }*/
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setMsg("设备已添加");
                                return "ajson";

                            }
                            if(StringUtils.isNotBlank(vo.getCode())){
                                glu.setBgDevId(vo.getCode());
                            }
                            if(StringUtils.isNotBlank(vo.getUserIdno1())){
                                AppPatientUserEntity patientUserEntity = sysDao.getAppPatientUserDao().findByUserIdNo(vo.getUserIdno1());
                                if(patientUserEntity!=null&&patientUserEntity.getId()!=null){
                                    List<AppBloodglu> gluList = sysDao.getServiceDo().loadByPk(AppBloodglu.class,"bgPaientId",patientUserEntity.getId());
                                    if(!gluList.isEmpty()){
                                        this.getAjson().setMsgCode("900");
                                        this.getAjson().setMsg("该用户已绑定过血糖仪");
                                        return "ajson";
                                    }else{
                                        glu.setBgPaientId(patientUserEntity.getId());
                                        glu.setBgDevTime(Calendar.getInstance());
                                    }
                                }else{
                                    this.getAjson().setMsgCode("900");
                                    this.getAjson().setMsg("找不到用户");
                                    return "ajson";
                                }
                            }
                            if(StringUtils.isNotBlank(vo.getType())){
                                glu.setBgType(vo.getType());
                            }
                            if(StringUtils.isNotBlank(vo.getDrId())){
                                glu.setBgDrId(vo.getDrId());
                                AppDrUser user = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,vo.getDrId());
                                if (user!=null&&user.getDrHospId() != null) {
                                    glu.setBgHospId(user.getDrHospId());
                                    AppHospDept hosp = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getDrHospId());
                                    glu.setBgAreaCode(hosp.getHospAreaCode());
                                }
                            }
                            if(StringUtils.isNotBlank(vo.getTeamId())){
                                glu.setBgTeamId(vo.getTeamId());
                            }
                            if(glu.getId()==null){
                                glu.setBgCreateTime(Calendar.getInstance());
                                this.sysDao.getServiceDo().add(glu);
                                this.getAjson().setMsgCode("800");
                                this.getAjson().setMsg("添加成功");
                                this.getAjson().setVo(glu);
                            }else{
                                sysDao.getServiceDo().modify(glu);
                                this.getAjson().setMsgCode("800");
                                this.getAjson().setMsg("绑定成功");
                                this.getAjson().setVo(glu);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("参数格式错误");
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("绑定不成功,验证失败");
                    }
                }else {
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("参数格式错误");
                }
            }catch (Exception e){
                e.printStackTrace();
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }
            return "ajson";
        }

    /**
     * 修改血压计(添加绑定用户)
     * @param devId 设备id
     * @param teamId 归属团队
     * @param drId 医生id
     * @param userIdno1 绑定用户身份证号1(血糖仪只传这个)
     * @param userIdno2 绑定用户身份证号2
     */
    public String appModifyPressure(){
        AppDeviceVo vo = (AppDeviceVo)getAppJson(AppDeviceVo.class);
        try {
            if(vo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {

                AppBloodpressure bp = (AppBloodpressure) sysDao.getServiceDo().find(AppBloodpressure.class,vo.getDevId());
                if(StringUtils.isNotBlank(vo.getTeamId())){
                    bp.setBpTeamId(vo.getTeamId());
                }
                if(StringUtils.isNotBlank(vo.getDrId())){
                    bp.setBpDrId(vo.getDrId());
                }
                if(StringUtils.isNotBlank(vo.getUserIdno1())){
                    AppPatientUserEntity patientUserEntity = sysDao.getAppPatientUserDao().findByUserIdNo(vo.getUserIdno1());
                    if (patientUserEntity != null && patientUserEntity.getId() != null) {
                        List<AppBloodpressure> bpList = sysDao.getAppBloodpressureDao().findByUserId(patientUserEntity.getId());
                        if (bpList!=null & !bpList.isEmpty()) {
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("该用户已绑定过血压计");
                            return "ajson";
                        } else {
                            bp.setBpUserOne(patientUserEntity.getId());
                            bp.setBpDevTimeOne(Calendar.getInstance());
                        }
                    } else {
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("找不到用户");
                        return "ajson";
                    }

                }
                if(StringUtils.isNotBlank(vo.getUserIdno2())){
                    AppPatientUserEntity patientUserEntity = sysDao.getAppPatientUserDao().findByUserIdNo(vo.getUserIdno2());
                    if (patientUserEntity != null && patientUserEntity.getId() != null) {
                        List<AppBloodpressure> bpList = sysDao.getAppBloodpressureDao().findByUserId(patientUserEntity.getId());
                        if (bpList!=null && !bpList.isEmpty()) {
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("该用户已绑定过血压计");
                            return "ajson";
                        } else {
                            bp.setBpUserTwo(patientUserEntity.getId());
                            bp.setBpDevTimeTwo(Calendar.getInstance());
                        }
                    } else {
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("找不到用户");
                        return "ajson";
                    }
                }
                sysDao.getServiceDo().modify(bp);
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("修改成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 修改血糖仪(添加绑定用户)
     * @param devId 设备id
     * @param teamId 归属团队
     * @param drId 医生id
     * @param userIdno1 绑定用户身份证号
     */
    public String appModifyGlu(){
        AppDeviceVo vo = (AppDeviceVo)getAppJson(AppDeviceVo.class);
        try {
            if(vo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                AppBloodglu glu = (AppBloodglu) sysDao.getServiceDo().find(AppBloodglu.class,vo.getDevId());
                if(StringUtils.isNotBlank(vo.getTeamId())){
                    glu.setBgTeamId(vo.getTeamId());
                }
                if(StringUtils.isNotBlank(vo.getDrId())){
                    glu.setBgDrId(vo.getDrId());
                }
                if(StringUtils.isNotBlank(vo.getUserIdno1())){
                    AppPatientUserEntity patientUserEntity = sysDao.getAppPatientUserDao().findByUserIdNo(vo.getUserIdno1());
                    if(patientUserEntity!=null&&patientUserEntity.getId()!=null){
                        List<AppBloodglu> gluList = sysDao.getServiceDo().loadByPk(AppBloodglu.class,"bgPaientId",patientUserEntity.getId());
                        if(gluList!=null && !gluList.isEmpty()){
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("该用户已绑定过血糖仪");
                            return "ajson";
                        }else{
                            glu.setBgPaientId(patientUserEntity.getId());
                            glu.setBgDevTime(Calendar.getInstance());
                        }
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("找不到用户");
                        return "ajson";
                    }

                }
                sysDao.getServiceDo().modify(glu);
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("修改成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询团队下的居民血压设备列表
     * @param teamId
     */
    public String appPressureList(){
        DeviceQvo vo = (DeviceQvo)getAppJson(DeviceQvo.class);
        try {
            if(vo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                HashMap<String, Object> map = new HashMap<>();
                map.put("teamId",vo.getTeamId());
                String sql = "select * from APP_BLOODPRESSURE WHERE BP_TEAM_ID = :teamId order by BP_CREATE_TIME DESC";
                List<AppBloodpressure> list = sysDao.getServiceDo().findSqlMap(sql, map, AppBloodpressure.class, vo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
                this.getAjson().setVo(vo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询团队下的居民血糖设备列表
     * @param teamId
     */
    public String appGluList(){
        DeviceQvo vo = (DeviceQvo)getAppJson(DeviceQvo.class);
        try {
            if(vo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                HashMap<String, Object> map = new HashMap<>();
                map.put("teamId",vo.getTeamId());
                String sql = "select * from APP_BLOODGLU WHERE BG_TEAM_ID = :teamId order by BG_CREATE_TIME DESC";
                List<AppBloodglu> list = sysDao.getServiceDo().findSqlMap(sql, map, AppBloodglu.class, vo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
                this.getAjson().setVo(vo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 根据设备号或姓名搜索设备
     * @paramn serachValue
     */
    public String appFindDevice(){
        AppCommQvo vo = (AppCommQvo)getAppJson(AppCommQvo.class);
        try {
            if(vo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                List<DeviceEntity> list = sysDao.getAppDeviceDao().findLike(vo);
                this.getAjson().setRows(list);
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
     * 删除设备
     * @return
     */
    public String deleteDevic(){
        try{
            DeviceQvo qvo = (DeviceQvo)this.getAppJson(DeviceQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if("2".equals(qvo.getType())){//血糖
                    AppBloodglu vo = (AppBloodglu)sysDao.getServiceDo().find(AppBloodglu.class,qvo.getId());
                    if(vo != null){
                        sysDao.getServiceDo().delete(vo);
                        this.getAjson().setMsg("删除成功!");
                    }else{
                        this.getAjson().setMsg("查无该设备");
                        this.getAjson().setMsgCode("900");
                    }
                }else{//血压
                    AppBloodpressure vo = (AppBloodpressure)sysDao.getServiceDo().find(AppBloodpressure.class,qvo.getId());
                    if(vo != null){
                        sysDao.getServiceDo().delete(vo);
                        this.getAjson().setMsg("删除成功!");
                    }else{
                        this.getAjson().setMsg("查无该设备");
                        this.getAjson().setMsgCode("900");
                    }
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
