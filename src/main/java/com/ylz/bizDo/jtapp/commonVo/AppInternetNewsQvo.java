package com.ylz.bizDo.jtapp.commonVo;

/**
 * 推送消息
 * Created by zzl on 2019/2/11.
 */
public class AppInternetNewsQvo {
    private String title;//标题
    private String msgType;//消息类型 医生同意消息YW001，体征异常预警消息YW002 ,健康档案审核消息YW003
    private String userId;//用户ID
    private String userName;//用户姓名
    private String idType;//证件类型
    private String idNo;//证件号码
    private String phone;//手机号码
    private String hospId;//医院ID
    private String hospName;//医院名称
    private String deptId;//科室ID
    private String deptName;//科室名称
    private String doctorId;//医生ID
    private String doctorName;//医生姓名
    private String content;//消息内容
    private String redirectUrl;//跳转链接地址
    private String field01;//预留字段01
    private String field02;//预留字段02
    private String field03;//预留字段03
    private String field04;//预留字段04
    private String field05;//预留字段05
    private String field06;//预留字段06
    private String type;//类型(展示详情的方式)类型 1:链接,2:参数,3:链接+参数，4其它(无详情)
    private String msgShowType;//1:系统通知，2:重要消息
    private AppInternetNewsSonQvo urlParam;//当type为3必传

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getField01() {
        return field01;
    }

    public void setField01(String field01) {
        this.field01 = field01;
    }

    public String getField02() {
        return field02;
    }

    public void setField02(String field02) {
        this.field02 = field02;
    }

    public String getField03() {
        return field03;
    }

    public void setField03(String field03) {
        this.field03 = field03;
    }

    public String getField04() {
        return field04;
    }

    public void setField04(String field04) {
        this.field04 = field04;
    }

    public String getField05() {
        return field05;
    }

    public void setField05(String field05) {
        this.field05 = field05;
    }

    public String getField06() {
        return field06;
    }

    public void setField06(String field06) {
        this.field06 = field06;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AppInternetNewsSonQvo getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(AppInternetNewsSonQvo urlParam) {
        this.urlParam = urlParam;
    }

    public String getMsgShowType() {
        return msgShowType;
    }

    public void setMsgShowType(String msgShowType) {
        this.msgShowType = msgShowType;
    }
}
