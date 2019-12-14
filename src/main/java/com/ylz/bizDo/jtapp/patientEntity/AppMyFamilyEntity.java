package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by asus on 2017/6/22.
 */
public class AppMyFamilyEntity {
    private String id;//主键
    private String mfMyPatientId;//患者主键
    private String mfFmPatientId;//成员主键
    private String mfFmIdno;//成身份证
    private String mfFmName;//成员姓名
    private String mfFmNickCode;//昵称code
    private String mfFmNickName;//成员昵称
    private String mfFmPatientCard;//社保卡
    private String mfFmState;//状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMfMyPatientId() {
        return mfMyPatientId;
    }

    public void setMfMyPatientId(String mfMyPatientId) {
        this.mfMyPatientId = mfMyPatientId;
    }

    public String getMfFmPatientId() {
        return mfFmPatientId;
    }

    public void setMfFmPatientId(String mfFmPatientId) {
        this.mfFmPatientId = mfFmPatientId;
    }

    public String getMfFmIdno() {
        return mfFmIdno;
    }

    public void setMfFmIdno(String mfFmIdno) {
        this.mfFmIdno = mfFmIdno;
    }

    public String getMfFmName() {
        return mfFmName;
    }

    public void setMfFmName(String mfFmName) {
        this.mfFmName = mfFmName;
    }

    public String getMfFmNickName() {
        return mfFmNickName;
    }

    public void setMfFmNickName(String mfFmNickName) throws Exception {
        if(StringUtils.isNotBlank(mfFmNickName)){
            if(mfFmNickName.equals("49")){
                this.mfFmNickName = "本人";
            }else{
                SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FAMILYRELATION, mfFmNickName);
                if(value!=null){
                    String[] titles = value.getCodeTitle().split(";");
                    this.mfFmNickName = titles[0];
                }

            }
        }

    }

    public String getMfFmNickCode() {
        return mfFmNickCode;
    }

    public void setMfFmNickCode(String mfFmNickCode) {
        this.mfFmNickCode = mfFmNickCode;
    }

    public String getMfFmPatientCard() {
        return mfFmPatientCard;
    }

    public void setMfFmPatientCard(String mfFmPatientCard) {
        this.mfFmPatientCard = mfFmPatientCard;
    }

    public String getStrPatientGender(){
        if(StringUtils.isNotBlank(this.getMfFmIdno())){
              try{
                  Map<String,Object> idNos;
                  if(this.getMfFmIdno().length() == 18){
                      idNos = CardUtil.getCarInfo(this.getMfFmIdno());
                  }else{
                      idNos = CardUtil.getCarInfo15W(this.getMfFmIdno());
                  }
                  return idNos.get("sex").toString();
              }catch (Exception e){
                  e.printStackTrace();
              }
        }
        return "";
    }

    public String getStrPatientAge(){
        if(StringUtils.isNotBlank(this.getMfFmIdno())){
            try{
                Map<String,Object> idNos;
                if(this.getMfFmIdno().length() == 18){
                    idNos = CardUtil.getCarInfo(this.getMfFmIdno());
                }else{
                    idNos = CardUtil.getCarInfo15W(this.getMfFmIdno());
                }
                String birthday = idNos.get("birthday").toString();
                String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                return age;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getStrPatientBirthday(){
        if(StringUtils.isNotBlank(this.getMfFmIdno())){
            try{
                Map<String,Object> idNos;
                if(this.getMfFmIdno().length() == 18){
                    idNos = CardUtil.getCarInfo(this.getMfFmIdno());
                }else{
                    idNos = CardUtil.getCarInfo15W(this.getMfFmIdno());
                }
                return idNos.get("birthday").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }


    public String getStrCityCode() throws Exception {
        if(StringUtils.isNotBlank(this.getMfFmPatientId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppPatientUser user = dao.getAppPatientUserDao().findByUserId(this.getMfFmPatientId());
            if(user != null && StringUtils.isNotBlank(user.getPatientCity())){
                CdAddress p = dao.getCdAddressDao().findByCode(user.getPatientCity());
                if(p != null){
                    String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                    CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                    if(value != null) {
                        return value.getCodeTitle();
                    }
                }
            }
        }
        return  "";
    }


    public String getPatientSignState() throws Exception {
        if(StringUtils.isNotBlank(this.getMfFmPatientId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            //查询已签约状态
            AppSignForm entity = dao.getAppSignformDao().findSignFormByUser(this.getMfFmPatientId());
            if(entity != null){
                return  entity.getSignState();
            }
        }
        return "99";
    }

    public String getPatientSignPayState() throws Exception {
        if(StringUtils.isNotBlank(this.getMfFmPatientId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            //查询已签约状态
            AppSignForm entity = dao.getAppSignformDao().findSignFormByUser(this.getMfFmPatientId());
            if(entity != null){
                return  entity.getSignPayState();
            }
        }
        return "99";
    }

    public String getSignType() throws Exception {
        if(StringUtils.isNotBlank(this.getMfFmPatientId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppSignForm entity = dao.getAppSignformDao().findSignFormByUser(this.getMfFmPatientId());
            if(entity != null){
                return  entity.getSignType();
            }
        }
        return "";
    }

    public String getMfFmState() {
        return mfFmState;
    }

    public void setMfFmState(String mfFmState) {
        this.mfFmState = mfFmState;
    }
}
