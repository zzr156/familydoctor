package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by lenovo on 2017/12/27.
 * 医保登记返回 明细表
 */

@Entity
@Table(name = "APP_SIGN_REGISTER")
public class AppSignRegister extends BasePO {

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "REGISTER_NO", nullable = false, length = 20)
    private String registerNo;//签约流水号
    @Column(name = "REGISTER_PATIENT_NAME", length = 20)
    private String registerPatientName;//居民名称
    @Column(name = "REGISTER_IDNO", length = 18)
    private String registerIdno;//居民身份证
    @Column(name = "REGISTER_PATIENT_CARD", length = 18)
    private String registerPatientCard;//社保卡
    @Column(name = "REGISTER_ADDRESS", length = 200)
    private String registerAddress;//参保地址
    @Column(name = "REGISTER_PUBLICHEALTH_COST", length = 18)
    private String registerPpublichealthCost;//公共卫生金额
    @Column(name = "REGISTER_SERVICE_COST", length = 18)
    private String registerServiceCost;//服务费
    @Column(name = "REGISTER_FUND_COST", length = 18)
    private String registerFundCost;//基金金额
    @Column(name = "REGISTER_PERSONAL_COST", length = 18)
    private String registerPersonalCost;//个人自付金额
    @Column(name = "REGISTER_PATIENT_ID", length = 36)
    private String registerPatientId;//居民id
    @Column(name = "REGISTER_FORMDATE")
    private Calendar registerFormdate;//开始时间
    @Column(name = "REGISTER_TODATE")
    private Calendar registerTodate;//截止时间
    @Column(name = "REGISTER_CREATEDATE")
    private Calendar registerCreatedate;//创建时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getRegisterPatientName() {
        return registerPatientName;
    }

    public void setRegisterPatientName(String registerPatientName) {
        this.registerPatientName = registerPatientName;
    }

    public String getRegisterIdno() {
        return registerIdno;
    }

    public void setRegisterIdno(String registerIdno) {
        this.registerIdno = registerIdno;
    }

    public String getRegisterPatientCard() {
        return registerPatientCard;
    }

    public void setRegisterPatientCard(String registerPatientCard) {
        this.registerPatientCard = registerPatientCard;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getRegisterPpublichealthCost() {
        return registerPpublichealthCost;
    }

    public void setRegisterPpublichealthCost(String registerPpublichealthCost) {
        this.registerPpublichealthCost = registerPpublichealthCost;
    }

    public String getRegisterServiceCost() {
        return registerServiceCost;
    }

    public void setRegisterServiceCost(String registerServiceCost) {
        this.registerServiceCost = registerServiceCost;
    }

    public String getRegisterFundCost() {
        return registerFundCost;
    }

    public void setRegisterFundCost(String registerFundCost) {
        this.registerFundCost = registerFundCost;
    }

    public String getRegisterPersonalCost() {
        return registerPersonalCost;
    }

    public void setRegisterPersonalCost(String registerPersonalCost) {
        this.registerPersonalCost = registerPersonalCost;
    }

    public String getRegisterPatientId() {
        return registerPatientId;
    }

    public void setRegisterPatientId(String registerPatientId) {
        this.registerPatientId = registerPatientId;
    }

    public Calendar getRegisterFormdate() {
        return registerFormdate;
    }

    public void setRegisterFormdate(Calendar registerFormdate) {
        this.registerFormdate = registerFormdate;
    }

    public Calendar getRegisterTodate() {
        return registerTodate;
    }

    public void setRegisterTodate(Calendar registerTodate) {
        this.registerTodate = registerTodate;
    }

    public Calendar getRegisterCreatedate() {
        return registerCreatedate;
    }

    public void setRegisterCreatedate(Calendar registerCreatedate) {
        this.registerCreatedate = registerCreatedate;
    }
}
