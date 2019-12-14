package com.ylz.view.common.action;


import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppThreeBloodPressureDataVo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.*;
import com.ylz.bizDo.jtapp.commonVo.*;
import com.ylz.bizDo.jtapp.commonVo.booldSugr.AppBooldSugrData;
import com.ylz.bizDo.jtapp.drVo.AppGroupVo;
import com.ylz.bizDo.jtapp.drVo.AppWarningSettingQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.CommonBindUser;
import com.ylz.packcommon.common.comEnum.CommonDeviceType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.JsonUtil;
import com.ylz.packcommon.common.util.SericuryUtil;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 健康监测接口action.
 */
@SuppressWarnings("all")
@Action(
        value = "monitor",
        results = {
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"}),
                @Result(name = "snJson", type = "json", params = {"root", "snJson", "contentType", "application/json"}),
                @Result(name = "threeJson", type = "json", params = {"root", "threeJson", "contentType", "application/json"})
        }
)
public class AppMonitorAction extends CommonAction {

    /**
     * 绑定血压计
     *
     * @param userId--患者id
     * @param type              --设备型号 1血压计 2血糖仪
     * @param code":"110",--SN码
     * @param bindUser--绑定按钮    1爸爸用户 2妈妈用户
     * @return
     */
    public String appBindBpDev() {

        try {
            AppDrUser user = getAppDrUser();
            AppDeviceVo vo = (AppDeviceVo) getAppJson(AppDeviceVo.class);
            if (vo != null) {
                if(StringUtils.isNotBlank(vo.getUserId())){
                    List<AppBloodpressure> list1 =sysDao.getAppBloodpressureDao().findByUserId(vo.getUserId());
                    if(list1!=null && !list1.isEmpty()){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("该用户已绑定血压计");
                        return "ajson";
                    }
                }
                AppBloodpressure bp = new AppBloodpressure();
                List<AppBloodpressure> list = this.sysDao.getServiceDo().loadByPk(AppBloodpressure.class, "bpDevId", vo.getCode());
                if (list != null && list.size() > 0) {
                    bp = list.get(0);
                    if ((StringUtils.isNotBlank(bp.getBpUserOne())&& vo.getBindUser().equals(CommonBindUser.BB.getValue()))
                            || (StringUtils.isNotBlank(bp.getBpUserTwo())&& vo.getBindUser().equals(CommonBindUser.MM.getValue()))) {
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("按钮已绑定用户");
                        return "ajson";
                    }
                }

                if (user != null) {
                    bp.setBpDrId(user.getId());
                    if (user.getDrHospId() != null) {
                        bp.setBpHospId(user.getDrHospId());
                        AppHospDept hosp = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getDrHospId());
                        bp.setBpAreaCode(hosp.getHospAreaCode());
                    }
                }
                bp.setBpDevId(vo.getCode());
                bp.setBpTeamId(vo.getTeamId());
                bp.setBpType(vo.getType());
                if (vo.getBindUser().equals(CommonBindUser.BB.getValue())) {
                    bp.setBpDevTimeOne(Calendar.getInstance());
                    bp.setBpUserOne(vo.getUserId());
                }
                if (vo.getBindUser().equals(CommonBindUser.MM.getValue())) {
                    bp.setBpDevTimeTwo(Calendar.getInstance());
                    bp.setBpUserTwo(vo.getUserId());
                }
                if (bp.getId() == null) {
                    bp.setBpCreateTime(Calendar.getInstance());
                    this.sysDao.getServiceDo().add(bp);
                } else {
                    this.getSysDao().getServiceDo().modify(bp);
                }

                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("绑定成功");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 绑定血糖仪
     *
     * @param userId
     * @param code   设备编号
     * @param type   设备类型
     * @return
     */
    public String appBindGluDev() {

        try {
            AppDrUser user = getAppDrUser();
            AppDeviceVo vo = (AppDeviceVo) getAppJson(AppDeviceVo.class);
            if (vo != null) {
                AppBloodglu glu = new AppBloodglu();
                if(StringUtils.isNotBlank(vo.getUserId())){
                    List<AppBloodglu> list1 = sysDao.getServiceDo().loadByPk(AppBloodglu.class,"bgPaientId",vo.getUserId());
                    if(list1!=null && !list1.isEmpty()){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("该用户已绑定血糖仪");
                        return "ajson";
                    }
                }
                glu.setBgTeamId(vo.getTeamId());
                glu.setBgDevId(vo.getCode());
                glu.setBgPaientId(vo.getUserId());
                glu.setBgType(vo.getType());
                glu.setBgDevTime(Calendar.getInstance());
                glu.setBgCreateTime(Calendar.getInstance());
                if (user != null) {
                    glu.setBgDrId(user.getId());
                    if (user.getDrHospId() != null) {
                        glu.setBgHospId(user.getDrHospId());
                        AppHospDept hosp = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class, user.getDrHospId());
                        glu.setBgAreaCode(hosp.getHospAreaCode());
                    }

                }
                this.sysDao.getServiceDo().add(glu);
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("绑定成功");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    //设备删除 解绑
    public String appDeldevice() {
        try {
            //AppPatientUser user  = getAppPatientUser();
            AppDeviceVo vo = (AppDeviceVo) getAppJson(AppDeviceVo.class);
            if (vo != null) {
                if (vo.getType().equals(CommonDeviceType.XT.getValue())) { //血糖仪
                    List<AppBloodglu> ls = this.sysDao.getServiceDo().loadByPk(AppBloodglu.class, "bgPaientId", vo.getUserId());
                    if (ls != null && ls.size() > 0) {
                        AppBloodglu vv = ls.get(0);
                        vv.setBgPaientId(null);
                        sysDao.getServiceDo().modify(vv);
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setMsg("解绑成功");
                    } else {
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("未找到血糖设备");
                    }
                } else if (vo.getType().equals(CommonDeviceType.XY.getValue())) {//血压计
                    List<AppBloodpressure> list = this.getSysDao().getAppBloodpressureDao().findByUserId(vo.getUserId());
                    if (list != null && list.size() > 0) {
                        AppBloodpressure bp = list.get(0);
                        if (vo.getUserId().equals(bp.getBpUserOne())) {
                            bp.setBpUserOne(null);
                            bp.setBpDevTimeOne(null);
                        } else {
                            bp.setBpUserTwo(null);
                            bp.setBpDevTimeTwo(null);
                        }
                        sysDao.getServiceDo().modify(bp);
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setMsg("解绑成功");
                    } else {
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("未找到血压设备");
                    }
                } else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("设备类型错误");
                }

            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 添加血糖数据
     *
     * @return
     */
    public String appBloodglu() {
        try {
            AppUserBloodgluVo vo = (AppUserBloodgluVo) getAppJson(AppUserBloodgluVo.class);
            if (vo != null) {
                this.getSysDao().getAppUserBloodgluDao().appBloodglu(vo);
                this.getAjson().setMsg("添加成功");
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 添加血压数据
     *
     * @return
     */
    public String appPressure() {
        try {
            AppUserBloodpressureVo vo = (AppUserBloodpressureVo) getAppJson(AppUserBloodpressureVo.class);
            if (vo != null) {
                sysDao.getAppUserBloodpressureDao().appPressure(vo);
                this.getAjson().setMsg("添加成功");
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    //查询最新的时段
    public String findNewState() {
        try {
            AppUserBloodgluVo vo = (AppUserBloodgluVo) getAppJson(AppUserBloodgluVo.class);
            String bgState = sysDao.getAppUserBloodgluDao().findNewGxt(vo.getUserId());
            this.getAjson().setMsg(bgState);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 查询血压数据(分页）
     *
     * @return
     */
    public String findPressure() {
        try {
            AppUserBloodpressureVo qvo = (AppUserBloodpressureVo) getAppJson(AppUserBloodpressureVo.class);
            if (qvo != null) {
                List<AppPressureEntity> ls = this.sysDao.getAppUserBloodpressureDao().findById(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setVo(qvo);
                this.getAjson().setMsgCode("800");
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 查询血糖数据(分页）
     *
     * @return
     */
    public String findGlu() {
        try {
            AppUserBloodgluVo qvo = (AppUserBloodgluVo) getAppJson(AppUserBloodgluVo.class);
            if (qvo != null) {
                List<AppGluEntity> ls = this.sysDao.getAppUserBloodgluDao().findById(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setVo(qvo);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 查询血糖数据(不分页）
     *
     * @return
     */
    public String findGluAll() {
        try {
            AppUserBloodgluVo qvo = (AppUserBloodgluVo) getAppJson(AppUserBloodgluVo.class);
            if (qvo != null) {
                List<AppGluEntity> ls = this.sysDao.getAppUserBloodgluDao().findAll(qvo.getUserId());
                this.getAjson().setRows(ls);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 体征预警设置
     *
     * @param userId
     * @param
     * @return
     */
    public String appWarningSetting() {
        try {
            AppWarningSettingQvo vo = (AppWarningSettingQvo) getAppJson(AppWarningSettingQvo.class);
            List<AppWarningSetting> list = sysDao.getServiceDo().loadByPk(AppWarningSetting.class, "wsUserId", vo.getUserId());
            if (list.isEmpty()) {
                AppWarningSetting set = new AppWarningSetting();
                set.setWsUserId(vo.getUserId());
                set.setWsState(vo.getState());
                sysDao.getServiceDo().add(set);
            } else {
                AppWarningSetting set = list.get(0);
                set.setWsUserId(vo.getUserId());
                set.setWsState(vo.getState());
                sysDao.getServiceDo().modify(set);
            }
            this.getAjson().setMsg("设置成功");
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
            return "ajson";
        }
        return "ajson";
    }

    /**
     * 来福数据接口 血糖
     *
     * @param devid
     * @param testtime
     * @param bgstate  1饭后 2饭前
     * @param temptur
     * @param bloodglu
     * @return
     */
    public String applfxt() {
        try {
            SericuryUtil util = new SericuryUtil();
            String result = this.getRequest().getParameter("strJson");
            result = util.decrypt(result);
            DevUserBloodgluVo xt = JsonUtil.fromJson(result, DevUserBloodgluVo.class);
            //System.out.println(result);
            if (xt != null) {
                sysDao.getAppUserBloodgluDao().addlfBlu(xt);
            } else {
                this.getAjson().setMsg("参数错误");
                this.getAjson().setMsgCode("900");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 来福数据接口 高血压
     *
     * @return
     */
    public String applfgxy() {
        try {
            SericuryUtil util = new SericuryUtil();
            String result = this.getRequest().getParameter("strJson");
            result = util.decrypt(result);
            AppUserBloodpressureVo gxy = JsonUtil.fromJson(result, AppUserBloodpressureVo.class);
            if (gxy != null) {
                sysDao.getAppUserBloodpressureDao().addlfPressure(gxy);
            } else {
                this.getAjson().setMsg("数据为null!");
                this.getAjson().setMsgCode("900");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 饭前饭后 基础数据
     */
    public String appGluState() {
        try {
            AppGroupVo vo = new AppGroupVo();
            vo.setGroupCode(CodeGroupConstrats.GROUP_BGSTATE);
            vo.setState(CommonEnable.QIYONG.getValue());
            List<AppGroupEntity> list = sysDao.getCodeDao().findAppGroup(vo);
            this.getAjson().setRows(list);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 统计血糖数据
     *
     * @param userId 患者id
     * @param period 1周 2月 3三月
     */
    public String appGluCount() {
        try {
            AppUserBloodgluVo vo = (AppUserBloodgluVo) getAppJson(AppUserBloodgluVo.class);
            if (vo != null) {
                AppGluCountEntity count = sysDao.getAppUserBloodgluDao().findCount(vo);
                this.getAjson().setVo(count);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsg("参数错误");
                this.getAjson().setMsgCode("900");
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 统计血压数据
     *
     * @param userId 患者id
     * @param period 1周 2月 3三月
     */
    public String appPressureCount() {
        try {
            AppUserBloodpressureVo vo = (AppUserBloodpressureVo) getAppJson(AppUserBloodpressureVo.class);
            if (vo != null) {
                AppPressureCountEntity count = sysDao.getAppUserBloodpressureDao().findCount(vo);
                this.getAjson().setVo(count);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsg("参数错误");
                this.getAjson().setMsgCode("900");
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 血压设备呼叫
     *
     * @param imei
     */
    public String appPressureSOS() {
        try {
            SericuryUtil util = new SericuryUtil();
            String result = this.getRequest().getParameter("strJson");
            result = util.decrypt(result);
            AppUserBloodpressureVo vo = JsonUtil.fromJson(result, AppUserBloodpressureVo.class);
            if (vo != null) {
                sysDao.getAppSeekHelpDao().sendHelpMsg(vo.getImei());
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsg("参数错误");
                this.getAjson().setMsgCode("900");
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询我的设备
     *
     * @param patientId
     */
    public String appFindDev() {
        try {
            AppCommQvo vo = (AppCommQvo) getAppJson(AppCommQvo.class);
            if (vo != null) {
                List<AppDevEntity> list = new ArrayList<AppDevEntity>();
                List<AppBloodpressure> xyList = sysDao.getAppBloodpressureDao().findByUserId(vo.getPatientId());
                for (AppBloodpressure bp : xyList) {
                    AppDevEntity entity = new AppDevEntity();
                    entity.setId(bp.getId());
                    entity.setSim(bp.getBpDevId());
                    entity.setType(bp.getBpType());
                    if (bp.getBindTimeOne() != null && vo.getPatientId().equals(bp.getBpUserOne())) {
                        entity.setTime(ExtendDate.getYMD_h_m(bp.getBpDevTimeOne()));
                    } else if (bp.getBindTimeTwo() != null) {
                        entity.setTime(ExtendDate.getYMD_h_m(bp.getBpDevTimeTwo()));
                    }
                    if (StringUtils.isNotBlank(bp.getBpDrId())) {
                        AppDrUser dr = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, bp.getBpDrId());
                        if (dr != null) {
                            entity.setDrName(dr.getDrName());
                            if(StringUtils.isNotBlank(dr.getDrJobTitle())) {
                                SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
                                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_MEMBERSTITLE, dr.getDrJobTitle());
                                if(value!=null){
                                    entity.setDrTitle(value.getCodeTitle());
                                }else{
                                    entity.setDrTitle(dr.getDrJobTitle());
                                }
                            }else{
                                entity.setDrTitle("");
                            }
                        } else {
                            entity.setDrName("");
                            entity.setDrTitle("");
                        }
                    } else {
                        entity.setDrName("");
                        entity.setDrTitle("");
                    }
                    list.add(entity);
                }
                List<AppBloodglu> xtList = sysDao.getServiceDo().loadByPk(AppBloodglu.class, "bgPaientId", vo.getPatientId());
                for (AppBloodglu xt : xtList) {
                    AppDevEntity entity = new AppDevEntity();
                    entity.setId(xt.getId());
                    entity.setSim(xt.getBgDevId());
                    entity.setType(xt.getBgType());
                    entity.setTime(ExtendDate.getYMD_h_m(xt.getBgDevTime()));
                    if (StringUtils.isNotBlank(xt.getBgDrId())) {
                        AppDrUser dr = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, xt.getBgDrId());
                        if (dr != null) {
                            entity.setDrName(dr.getDrName());
                            if(StringUtils.isNotBlank(dr.getDrJobTitle())) {
                                SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
                                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_MEMBERSTITLE, dr.getDrJobTitle());
                                if(value!=null){
                                    entity.setDrTitle(value.getCodeTitle());
                                }else{
                                    entity.setDrTitle(dr.getDrJobTitle());
                                }
                            }else{
                                entity.setDrTitle("");
                            }
                        } else {
                            entity.setDrName("");
                            entity.setDrTitle("");
                        }
                    } else {
                        entity.setDrName("");
                        entity.setDrTitle("");
                    }
                    list.add(entity);
                }
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsg("参数错误");
                this.getAjson().setMsgCode("900");
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查看血压数据
     *
     * @param period 1日 2周 3月
     * @param userId 患者id
     */
    public String appPressureLook() {
        try {
            AppUserBloodpressureVo vo = (AppUserBloodpressureVo) getAppJson(AppUserBloodpressureVo.class);
            if (vo != null) {
                List<AppPressureEntity> list = sysDao.getAppUserBloodpressureDao().findLook(vo.getUserId(), vo.getPeriod());
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsg("参数错误");
                this.getAjson().setMsgCode("900");
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查看血糖数据
     *
     * @param period  1日 2周 3月
     * @param bgstate 1空腹 2餐前 3餐后 4全天
     * @param userId  患者id
     */
    public String appGluLook() {
        try {
            AppUserBloodgluVo vo = (AppUserBloodgluVo) getAppJson(AppUserBloodgluVo.class);
            if (vo != null) {
                List<AppGluEntity> list = sysDao.getAppUserBloodgluDao().findLook(vo.getUserId(), vo.getBgstate(), vo.getPeriod());
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsg("参数错误");
                this.getAjson().setMsgCode("900");
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查看血糖最新数据
     * @param userId  患者id
     */
    public String appGluLatest() {
        try {
            AppUserBloodgluVo vo = (AppUserBloodgluVo) getAppJson(AppUserBloodgluVo.class);
            if (vo != null) {
                AppGluEntity entity = sysDao.getAppUserBloodgluDao().findLatest(vo.getUserId());
                this.getAjson().setVo(entity);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsg("参数错误");
                this.getAjson().setMsgCode("900");
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查看当天血压平局值(当天没有数据则取最近一条测量值)
     * @param userId  患者id
     */
    public String appPressureLatest() {
        try {
            AppUserBloodgluVo vo = (AppUserBloodgluVo) getAppJson(AppUserBloodgluVo.class);
            if (vo != null) {
                AppPressureEntity entity = sysDao.getAppUserBloodpressureDao().findLatest(vo.getUserId());
                this.getAjson().setVo(entity);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsg("参数错误");
                this.getAjson().setMsgCode("900");
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 接收三诺平台血糖数据
     * @return
     */
    public String appBooldSugrData(){
        try {
            AppBooldSugrData vo = (AppBooldSugrData) getAppJson(AppBooldSugrData.class);
            if(vo != null){
                if(vo.getMsgdata() != null){
                    DevUserBloodgluVo vv = new DevUserBloodgluVo();
                    vv.setSourceType(vo.getSourceType());
                    vv.setBloodGlu(vo.getMsgdata().getResult());
                    vv.setTestTime(vo.getMsgdata().getTesttime());
                    vv.setCodeNum(vo.getMsgdata().getUsercode());
                    vv.setDevid(vo.getMsgdata().getDevicesn());
                    vv.setName(vo.getName());
                    vv.setIdno(vo.getIdno());
                    if(StringUtils.isBlank(vo.getMsgdata().getFoodstatus())){
//                        this.getSnJson().setDesc("血糖测试时间段标志不能为空!");
//                        this.getSnJson().setStatusCode("02");
                        vv.setBgState("0");//随机
                    }else{
                        if("0".equals(vo.getMsgdata().getFoodstatus())){//餐前（三诺状态）
                            vv.setBgState("2");//（数据库状态）
                        }else if("1".equals(vo.getMsgdata().getFoodstatus())){//餐后（三诺状态）
                            vv.setBgState("1");//（数据库状态）
                        }else{
                            vv.setBgState("0");//随机
                        }
                    }
                   /* if("2AI4QIR2511".equals(vv.getDevid())){
                        sysDao.getSecurityCardAsyncBean().pushXt(vo);
                    }*/
                    sysDao.getAppUserBloodgluDao().addlfBlu(vv);
                    this.getSnJson().setStatusCode("00");
                    this.getSnJson().setDesc("推送成功!");
                }
            }else{
                this.getSnJson().setDesc("参数格式错误!");
                this.getSnJson().setStatusCode("02");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getSnJson().setDesc(e.getMessage());
            this.getSnJson().setStatusCode("03");
        }
        return "snJson";
    }

    /**
     * 添加第三方血压数据
     * @return
     */
    public String addThreeBloodPressureData(){
        try{
            String strJson = getRequest().getParameter("threeJsonData");
            System.out.println(strJson);
            AppThreeBloodPressureDataVo qvo = JsonUtil.fromJson(strJson,AppThreeBloodPressureDataVo.class);
            if(qvo == null){
                this.getThreeJson().setMsg("参数格式错误");
                this.getThreeJson().setStatus("false");
                this.getThreeJson().setCode("2");
            }else{
//                if("06B31809040407".equals(qvo.getDeviceSim())){
//                    sysDao.getSecurityCardAsyncBean().pushXy(qvo);
//                }
                sysDao.getAppUserBloodpressureDao().addThreeBloodPressureData(qvo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getThreeJson().setMsg(e.getMessage());
            this.getThreeJson().setStatus("false");
            this.getThreeJson().setCode("2");
        }
        return "threeJson";
    }

    /**
     * 一键求助
     * @return
     */
    public String oneButtonSos(){
        try {
            AppUserBloodpressureVo qvo = (AppUserBloodpressureVo)this.getAppJson(AppUserBloodpressureVo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                sysDao.getAppSeekHelpDao().sendHelpMsg(qvo.getImei());
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }

        return "ajson";
    }

    /**
     * 保存poss机血糖数据
     * @return
     */
    public String savePossBooldSugrData(){
        try{
            DevUserBloodgluVo qvo = (DevUserBloodgluVo)this.getAppJson(DevUserBloodgluVo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                sysDao.getAppUserBloodgluDao().addlfBlu(qvo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 保存poss机血压数据
     * @return
     */
    public String savePossBloodPressureData(){
        try{
            AppThreeBloodPressureDataVo qvo = (AppThreeBloodPressureDataVo)this.getAppJson(AppThreeBloodPressureDataVo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                sysDao.getAppUserBloodpressureDao().addThreeBloodPressureData(qvo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 添加第三方血压数据(用于蒲公英，接收正式设备推送的血压数据)
     * @return
     */
    public String addThreeBloodPressureDataTwo(){
        try{
            AppThreeBloodPressureDataVo qvo = (AppThreeBloodPressureDataVo)this.getAppJson(AppThreeBloodPressureDataVo.class);
            JSONObject json = JSONObject.fromObject(qvo);
            System.out.println(json.toString());
            if(qvo == null){
                this.getThreeJson().setMsg("参数格式错误");
                this.getThreeJson().setStatus("false");
                this.getThreeJson().setCode("2");
            }else{
                sysDao.getAppUserBloodpressureDao().addThreeBloodPressureData(qvo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getThreeJson().setMsg(e.getMessage());
            this.getThreeJson().setStatus("false");
            this.getThreeJson().setCode("2");
        }
        return "threeJson";
    }
}
