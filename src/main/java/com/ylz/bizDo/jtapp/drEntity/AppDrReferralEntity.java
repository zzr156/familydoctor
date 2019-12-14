package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppHospitalDepartments;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.ReferralState;
import com.ylz.packcommon.common.comEnum.ReferralType;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * 转诊数据返回护送
 * Created by zzl on 2017/12/11.
 */
public class AppDrReferralEntity {
    private String id;//主键
    private String patientId;//患者id
    private String name;//患者姓名
    private String age;//年龄
    private String idNo;//身份证
    private String sex;//性别
    private String card;//社保卡
    private String disName;//服务人群名称
    private String disColor;//服务人群颜色
    private String phone;//联系电话
    private String address;//居住地
    private String cbyx;//初步印象
    private String zyxbs;//主要现病史
    private String zyjws;//主要既往史
    private String zljg;//治疗经过
    private String outOrgId;//转诊发起机构id
    private String outOrgName;//转诊发起机构名称
    private String outDrId;//转诊发起医生id
    private String outDrName;//转诊发起医生姓名
    private String lxfs;//联系方式
    private String inOrgId;//转诊接收机构id
    private String inOrgName;//转诊接收机构名称
    private String inDeptId;//转诊接收科室
    private String inDeptName;//转诊接收科室名称
    private String indrId;//转诊接收医生id
    private String indrName;//转诊接收医生姓名
    private String sqzzsj;//申请转诊时间
    private String yyzzsj;//预约转诊时间
    private String jzsj;//接诊日期
    private String jjzsj;//拒接诊时间
    private String jjzyy;//拒接诊原因
    private String kfzhsj;//康复转回时间
    private String zzsj;//终止时间
    private String zzyy;//终止原因
    private String zdjg;//诊断结果
    private String zyjcjg;//主要检查结果
    private String xycjy;//下一次治疗方案及康复建议
    private String teamId;//团队id
    private String areaCode;//区域编号
    private String state;//状态
    private String type;//类型
    private String signId;//签约单id
    private String imageUrl;//头像

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getZdjg() {
        return zdjg;
    }

    public void setZdjg(String zdjg) {
        this.zdjg = zdjg;
    }

    public String getZyjcjg() {
        return zyjcjg;
    }

    public void setZyjcjg(String zyjcjg) {
        this.zyjcjg = zyjcjg;
    }

    public String getXycjy() {
        return xycjy;
    }

    public void setXycjy(String xycjy) {
        this.xycjy = xycjy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDisName() {
        return disName;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }

    public String getDisColor() {
        return disColor;
    }

    public void setDisColor(String disColor) {
        this.disColor = disColor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCbyx() {
        return cbyx;
    }

    public void setCbyx(String cbyx) {
        this.cbyx = cbyx;
    }

    public String getZyxbs() {
        return zyxbs;
    }

    public void setZyxbs(String zyxbs) {
        this.zyxbs = zyxbs;
    }

    public String getZyjws() {
        return zyjws;
    }

    public void setZyjws(String zyjws) {
        this.zyjws = zyjws;
    }

    public String getZljg() {
        return zljg;
    }

    public void setZljg(String zljg) {
        this.zljg = zljg;
    }

    public String getOutOrgId() {
        return outOrgId;
    }

    public void setOutOrgId(String outOrgId) {
        this.outOrgId = outOrgId;
    }

    public String getOutOrgName() {
        return outOrgName;
    }

    public void setOutOrgName(String outOrgName) {
        if(StringUtils.isNotBlank(this.getOutOrgId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept) dao.getServiceDo().find(AppHospDept.class,this.getOutOrgId());
            if(dept!=null){
                outOrgName = dept.getHospName();
            }
        }
        this.outOrgName = outOrgName;
    }

    public String getOutDrId() {
        return outDrId;
    }

    public void setOutDrId(String outDrId) {
        this.outDrId = outDrId;
    }

    public String getOutDrName() {
        return outDrName;
    }

    public void setOutDrName(String outDrName) {
        if(StringUtils.isNotBlank(this.getOutDrId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser user = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getOutDrId());
            if(user !=null){
                outDrName = user.getDrName();
            }
        }
        this.outDrName = outDrName;
    }

    public String getLxfs() {
        return lxfs;
    }

    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    public String getInOrgId() {
        return inOrgId;
    }

    public void setInOrgId(String inOrgId) {
        this.inOrgId = inOrgId;
    }

    public String getInOrgName() {
        return inOrgName;
    }

    public void setInOrgName(String inOrgName) {
        if(StringUtils.isNotBlank(this.getInOrgId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getInOrgId());
            if(dept!=null){
                inOrgName = dept.getHospName();
            }
        }
        this.inOrgName = inOrgName;
    }

    public String getInDeptId() {
        return inDeptId;
    }

    public void setInDeptId(String inDeptId) {
        this.inDeptId = inDeptId;
    }

    public String getInDeptName() {
        return inDeptName;
    }

    public void setInDeptName(String inDeptName) {
        if(StringUtils.isNotBlank(this.getInDeptId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppHospitalDepartments dept = (AppHospitalDepartments)dao.getServiceDo().find(AppHospitalDepartments.class,this.getInDeptId());
            if(dept != null){
                inDeptName = dept.getHdSectionName();
            }
        }
        this.inDeptName = inDeptName;
    }

    public String getSqzzsj() {
        return sqzzsj;
    }

    public void setSqzzsj(Timestamp sqzzsj) {
        if(sqzzsj != null){
            this.sqzzsj = ExtendDate.getYMD(sqzzsj);
        }else{
            this.sqzzsj = "";
        }
    }

    public String getYyzzsj() {
        return yyzzsj;
    }

    public void setYyzzsj(Timestamp yyzzsj) {
        if(yyzzsj != null){
            this.yyzzsj = ExtendDate.getYMD(yyzzsj);
        }else{
            this.yyzzsj = "";
        }
    }

    public String getJzsj() {
        return jzsj;
    }

    public void setJzsj(Timestamp jzsj) {
        if(jzsj != null){
            this.jzsj = ExtendDate.getYMD(jzsj);
        }else{
            this.jzsj = "";
        }
    }

    public String getJjzsj() {
        return jjzsj;
    }

    public void setJjzsj(Timestamp jjzsj) {
        if(jjzsj != null){
            this.jjzsj = ExtendDate.getYMD(jjzsj);
        }else{
            this.jjzsj = "";
        }
    }

    public String getJjzyy() {
        return jjzyy;
    }

    public void setJjzyy(String jjzyy) {
        this.jjzyy = jjzyy;
    }

    public String getKfzhsj() {
        return kfzhsj;
    }

    public void setKfzhsj(Timestamp kfzhsj) {
        if(kfzhsj != null){
            this.kfzhsj = ExtendDate.getYMD(kfzhsj);
        }else{
            this.kfzhsj = "";
        }
    }

    public String getZzsj() {
        return zzsj;
    }

    public void setZzsj(Timestamp zzsj) {
        if(zzsj !=null){
            this.zzsj = ExtendDate.getYMD(zzsj);
        }else{
            this.zzsj = "";
        }
    }

    public String getZzyy() {
        return zzyy;
    }

    public void setZzyy(String zzyy) {
        this.zzyy = zzyy;
    }

    public String getIndrId() {
        return indrId;
    }

    public void setIndrId(String indrId) {
        this.indrId = indrId;
    }

    public String getIndrName() {
        return indrName;
    }

    public void setIndrName(String indrName) {
        if(StringUtils.isNotBlank(this.getIndrId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser user = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getIndrId());
            if(user!=null){
                indrName = user.getDrName();
            }
        }
        this.indrName = indrName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser) dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user!=null){
                imageUrl = user.getPatientImageurl();
            }
        }
        this.imageUrl = imageUrl;
    }
}
