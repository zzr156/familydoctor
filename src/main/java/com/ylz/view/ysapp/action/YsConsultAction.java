package com.ylz.view.ysapp.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonVo.AppConsultQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrConsultEntity;
import com.ylz.bizDo.jtapp.drVo.AppConsultRecordQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.PerformanceType;
import com.ylz.packcommon.common.util.AreaUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.List;

/**医生咨询
 * Created by zzl on 2017/6/25.
 */
@SuppressWarnings("all")
@Action(
        value="ysCon",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsConsultAction extends CommonAction {
    /**
     * 咨询我的
     * @return
     */
    public String conTome(){
        try{
            AppConsultQvo qvo = (AppConsultQvo)this.getAppJson(AppConsultQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppDrConsultEntity> ls = this.sysDao.getAppConsultDao().findByDr(qvo);
                this.getAjson().setRows(ls);
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
     * 查询已完成的咨询
     * @return
     */
    public String findComplete(){
        try{
            AppConsultQvo qvo = (AppConsultQvo)this.getAppJson(AppConsultQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppDrConsultEntity> ls = this.sysDao.getAppConsultDao().findComplete(qvo);
                this.getAjson().setRows(ls);
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
     * 保存咨询记录
     * @return
     */
    public String saveConsultRecord(){
        try{
            AppConsultRecordQvo qvo = (AppConsultRecordQvo)this.getAppJson(AppConsultRecordQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isBlank(qvo.getTeamId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("团队id不能为空");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getDrId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("医生id不能为空");
                    return "ajson";
                }
                if(StringUtils.isBlank(qvo.getPatientId())){
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("患者id不能为空");
                    return "ajson";
                }
                AppDrUser drUser = (AppDrUser) this.getSysDao().getServiceDo().find(AppDrUser.class,qvo.getDrId());
                AppHospDept hospDept = (AppHospDept)this.getSysDao().getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                AppConsultRecord vo = new AppConsultRecord();
                vo.setConrecDrId(qvo.getDrId());
                vo.setConrecPatientId(qvo.getPatientId());
                vo.setConrecInitiateTime(Calendar.getInstance());
                vo.setConrecReplyTime(Calendar.getInstance());
                vo.setConrecTeamId(qvo.getTeamId());
                vo.setConrecHospId(drUser.getDrHospId());
                vo.setConrecAreaCode(hospDept.getHospAreaCode());
                sysDao.getServiceDo().add(vo);

                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getPatientId());
                //履约数据
                PerformanceDataQvo qqvo = new PerformanceDataQvo();
                //患者信息
                if(user!=null){
                    AppSignForm form= sysDao.getAppSignformDao().getSignFormUserId(user.getId());
                    if(form != null){
                        qqvo.setPerName(user.getPatientName());
                        qqvo.setPerIdno(user.getPatientIdno());
                        qqvo.setPerForeign(vo.getId());
                        if(StringUtils.isNotBlank(user.getPatientCity())){
                            CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                            if(p != null){
                                String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                                if(value != null) {
                                    qqvo.setPerArea(value.getCodeTitle());
                                }
                            }
                        }
                        qqvo.setPerSource("2");//app
                        if(drUser!=null){
                            //医生信息
                            qqvo.setDrId(drUser.getId());
                            qqvo.setDrName(drUser.getDrName());
                            qqvo.setDrAccount(drUser.getDrAccount());
                            qqvo.setDrPwd(drUser.getDrPwd());
                            qqvo.setDrGender(drUser.getDrGender());
                            qqvo.setDrTel(drUser.getDrTel());
                            if(hospDept!=null){
                                //医院信息
                                qqvo.setHospId(hospDept.getId());
                                qqvo.setHospName(hospDept.getHospName());
                                qqvo.setHospAreaCode(hospDept.getHospAreaCode());
                                qqvo.setHospAddress(hospDept.getHospAddress());
                                qqvo.setHospTel(hospDept.getHospTel());
                            }
                        }
                        qqvo.setPerType(PerformanceType.JKZX.getValue());
                        if(StringUtils.isNotBlank(qqvo.getPerArea())){
                            if(StringUtils.isNotBlank(qqvo.getPerType())){
                                String fwType = "";
                                String sermeal = "";//服务包id
                                fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                sermeal = form.getSignpackageid();
                                sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                            }
                        }
                    }
                }
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

}
