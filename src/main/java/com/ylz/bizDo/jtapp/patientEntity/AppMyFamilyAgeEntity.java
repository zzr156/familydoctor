package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by asus on 2017/6/22.
 */
public class AppMyFamilyAgeEntity {
    private String id;//患者主键
    private String mfFmIdno;//成身份证
    private String mfFmName;//成员姓名
    private String mfFmNickCode;//昵称code
    private String mfFmNickName;//成员昵称
    private String mfFmMykh;//免疫卡号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setMfFmNickName(String mfFmNickName)throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FAMILYRELATION, mfFmNickName);
        if(value!=null){
            String[] titles = value.getCodeTitle().split(";");
            this.mfFmNickName = titles[0];
        }

    }

    public String getMfFmNickCode() {
        return mfFmNickCode;
    }

    public void setMfFmNickCode(String mfFmNickCode) {
        this.mfFmNickCode = mfFmNickCode;
    }

    public String getMfFmMykh() {
        return mfFmMykh;
    }

    public void setMfFmMykh(String mfFmMykh) {
        this.mfFmMykh = mfFmMykh;
    }

    public String getStrAge(){
        String age = "";
        if(StringUtils.isNotBlank(this.getMfFmIdno())){
            try{
                Map<String,Object> idNos;
                if(this.getMfFmIdno().length() == 18){
                    idNos = CardUtil.getCarInfo(this.getMfFmIdno());
                }else{
                    idNos = CardUtil.getCarInfo15W(this.getMfFmIdno());
                }
                String birthday = idNos.get("birthday").toString();
                age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return age;
    }

    public String getStrGender(){
        String sex = "";
        if(StringUtils.isNotBlank(this.getMfFmIdno())){
            try{
                Map<String,Object> idNos;
                if(this.getMfFmIdno().length() == 18){
                    idNos = CardUtil.getCarInfo(this.getMfFmIdno());
                }else{
                    idNos = CardUtil.getCarInfo15W(this.getMfFmIdno());
                }
                sex = idNos.get("sex").toString();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return sex;
    }

    public String getStrBirthday(){
        String birthday = "";
        if(StringUtils.isNotBlank(this.getMfFmIdno())){
            try{
                Map<String,Object> idNos;
                if(this.getMfFmIdno().length() == 18){
                    idNos = CardUtil.getCarInfo(this.getMfFmIdno());
                }else{
                    idNos = CardUtil.getCarInfo15W(this.getMfFmIdno());
                }
                birthday = idNos.get("birthday").toString();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return birthday;
    }
}
