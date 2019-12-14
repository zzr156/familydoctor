package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.packcommon.common.util.MyMathUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;

/**
 * Created by asus on 2017/07/23.
 */
public class AppDrEvaluationDrEntity {
    private String serviceAttitude;//专业能力
    private String professionalAbility;//服务态度
    private String recoverySpeed;//回复速度
    private String avegage;//平均数
    private String size;//总数
    private String drAllAvegage;//医生最高平均数


    public String getServiceAttitude() {
        return serviceAttitude;
    }

    public String getStrServiceAttitude(){
        if(StringUtils.isNotBlank(this.size)){
            String size = this.size;
            if(Double.parseDouble(this.getServiceAttitude()) >0){
                double result = MyMathUtil.div(Double.parseDouble(this.getServiceAttitude()),Double.parseDouble(size),2);
                return String.valueOf(result);
            }else{
                return String.valueOf("0");
            }
        }
        return "";
    }

    public void setServiceAttitude(Double serviceAttitude) {
        if(serviceAttitude == null){
            this.serviceAttitude = String.valueOf("0");
        }else{
            this.serviceAttitude = String.valueOf(serviceAttitude);
        }

    }

    public String getProfessionalAbility() {
        return professionalAbility;
    }

    public String getStrProfessionalAbility(){
        if(StringUtils.isNotBlank(this.size) && StringUtils.isNotBlank(this.getProfessionalAbility())){
            String size = this.size;
            if(Double.parseDouble(this.getProfessionalAbility()) >0){
                double result = MyMathUtil.div(Double.parseDouble(this.getProfessionalAbility()),Double.parseDouble(size),2);
                return String.valueOf(result);
            }else{
                return String.valueOf("0");
            }
        }
        return "";
    }

    public void setProfessionalAbility(Double professionalAbility) {
        if(professionalAbility == null){
            this.professionalAbility = String.valueOf("0");
        }else{
            this.professionalAbility = String.valueOf(professionalAbility);
        }
    }

    public String getAvegage() {
        return avegage;
    }

    public String getStrAvegage(){
        if(StringUtils.isNotBlank(this.size) && StringUtils.isNotBlank(this.getAvegage())){
            String size = this.size;
            if(Double.parseDouble(this.getAvegage()) >0){
                double result = MyMathUtil.div(Double.parseDouble(this.getAvegage()),Double.parseDouble(size),2);
                return String.valueOf(result);
            }else{
                return String.valueOf("0");
            }

        }
        return "";
    }

    public void setAvegage(Double avegage) {
        if(avegage == null){
            this.avegage = String.valueOf("0");
        }else{
            this.avegage = String.valueOf(avegage);
        }
    }

    public String getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = String.valueOf(size);
    }

    public String getDrAllAvegage() {
        return drAllAvegage;
    }

    public String getStrDrAllAvegage(){
        if(StringUtils.isNotBlank(this.size)){
            String size = this.size;
            double result = MyMathUtil.div(Double.parseDouble(this.getStrAvegage()),Double.parseDouble(this.getDrAllAvegage()),2);
            return String.valueOf((int)MyMathUtil.mul(result,Double.parseDouble("100")))+"%";
        }
        return "";
    }

    public void setDrAllAvegage(String drAllAvegage) {
        this.drAllAvegage = drAllAvegage;
    }


    public String getRecoverySpeed() {
        return recoverySpeed;
    }

    public void setRecoverySpeed(Double recoverySpeed) {
        if(recoverySpeed == null){
            this.recoverySpeed = String.valueOf("0");
        }else{
            this.recoverySpeed = String.valueOf(recoverySpeed);
        }
    }


    public String getStrRecoverySpeed(){
        if(StringUtils.isNotBlank(this.size)){
            String size = this.size;
            if(Double.parseDouble(this.getRecoverySpeed()) >0){
                double result = MyMathUtil.div(Double.parseDouble(this.getRecoverySpeed()),Double.parseDouble(size),2);
                return String.valueOf(result);
            }else{
                return String.valueOf("0");
            }
        }
        return "";
    }


}
