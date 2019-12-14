package com.ylz.view.hzapp.action;

import com.ylz.bizDo.app.po.AppMyFamily;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSecurtySetting;
import com.ylz.bizDo.jtapp.patientEntity.AppMyFamilyEntity;
import com.ylz.bizDo.jtapp.patientVo.AppSecurtyQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 安全设置
 */
@SuppressWarnings("all")
@Action(
        value="hzSecurty",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class HzSecurtyAction extends CommonAction{

    /**
     * 查询个人安全设置
     * @return
     */
    public String findSecurtySetting(){
        AppSecurtyQvo qvo=(AppSecurtyQvo)getAppJson(AppSecurtyQvo.class);
        try {
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                List<AppSecurtySetting> ls = this.getSysDao().getAppSecurtySettingDao().findListQvo(qvo);
                if(ls != null && ls.size()>0){
                    this.getAjson().setRows(ls);
                }else {
                    ls = new ArrayList<AppSecurtySetting>();
                    ls.add(this.getSysDao().getAppSecurtySettingDao().addSecurty(qvo.getDrPatientId(), CommonShortType.HUANGZHE.getValue(), CommonEnable.JINYONG.getValue()));
                    ls.add(this.getSysDao().getAppSecurtySettingDao().addSecurty(qvo.getDrPatientId(), CommonShortType.YISHENG.getValue(), CommonEnable.QIYONG.getValue()));
                    this.getAjson().setRows(ls);
                }
                List<AppMyFamilyEntity> familyList = sysDao.getAppMyFamilyDao().findByOnly(getAppPatientUser().getId());
                if (familyList != null && familyList.size() > 0) {
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("myfamily", familyList);
                    this.getAjson().setMap(map);
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
     * 修改安全设置
     * @return
     */
    public String appModifySecurty(){
        AppSecurtyQvo qvo=(AppSecurtyQvo)getAppJson(AppSecurtyQvo.class);
        try {
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
               if(qvo.getType().equals("1")){
                   AppSecurtySetting p = (AppSecurtySetting) this.getSysDao().getServiceDo().find(AppSecurtySetting.class,qvo.getId());
                   if(p != null){
                       p.setSecurtyState(qvo.getState());//状态
                       this.getSysDao().getServiceDo().modify(p);
                       this.getAjson().setMsg("修改成功!");
                   }else {
                       this.getAjson().setMsgCode("900");
                       this.getAjson().setMsg("参数格式错误");
                   }
               }else if(qvo.getType().equals("2")){
                   AppPatientUser user = this.getAppPatientUser();
                   AppMyFamily p = sysDao.getAppMyFamilyDao().findByFamilyUserId(qvo.getId(),user.getId());
                   if (p != null ) {
                       p.setMfFmState(qvo.getState());
                       sysDao.getServiceDo().modify(p);
                       this.getAjson().setMsgCode("800");
                   } else {
                       this.getAjson().setMsg("未找到家庭成员数据!");
                       this.getAjson().setMsgCode("900");
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



}
