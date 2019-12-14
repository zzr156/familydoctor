package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 肺结核患者第一次入户随访记录表
 * Created by zzl on 2019/4/8.
 */
@Entity
@Table(name = "APP_FIRST_TB_FOLLOW_VISIT")
public class AppFirstTBFollowVisitTable extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "FTB_VISIT_ID",length = 36)
    private String ftbVisitId;//随访外建
    @Column(name = "FTB_PATIENT_ID",length = 36)
    private String ftbPatientId;// 患者主键
    @Column(name = "FTB_PATIENT_NAME",length = 20)
    private String ftbPatientName;// 姓名
    @Column(name = "FTB_CODE",length = 10)
    private String ftbCode;// 编号
    @Column(name = "FTB_VISIT_TIME",length = 50)
    private String ftbVisitTime;// 随访时间
    @Column(name = "FTB_VISIT_WAY",length = 10)
    private String ftbVisitWay;// 随访方式(1门诊 2.家庭)
    @Column(name = "FTB_PATIENT_TYPE",length = 10)
    private String ftbPatientType;// 患者类型(1初治 2复治 )
    @Column(name = "FTB_SPUTUM_CONDITION",length = 10)
    private String ftbSputumCondition;// 痰菌情况(1阳性 2阴性 3未查痰)
    @Column(name = "FTB_DRUG_RESISTANCE",length = 10)
    private String ftbDrugResistance;// 耐药情况 (1耐药 2非耐药 3未检测)
    @Column(name = "FTB_SYMPTOMS_AND_SIGNS",length = 10)
    private String ftbSymptomsAndSigns;// 症状及体征((0没有症状 1咳嗽咳痰 2低热盗汗 3咯血或血痰 4 胸痛消瘦 5恶心纳差 6头痛失眠 7视物模糊 8皮肤瘙痒、皮疹 9 耳鸣、听力下降 10其他)
    @Column(name = "FTB_SYMPTOMS_AND_SIGNS_OTHER",length = 255)
    private String ftbSymptomsAndSignsOther;//其他症状和体征内容
    @Column(name = "FTB_CHEMOTHERAPY_REGIMEN",length = 255)
    private String ftbChemotherapyRegimen;// 化疗方案
    @Column(name = "FTB_USAGE",length = 10)
    private String ftbUsage;// 用法（1每日  2 间歇）
    @Column(name = "FTB_DRUGS_DOSAGE_TYPE",length = 10)
    private String ftbDrugsDosageType;// 药品剂型(1 固定剂量复合制剂 2 散装药 3 板式组合药 4 注射剂)
    @Column(name = "FTB_SUPERVISORY_STAFF",length = 10)
    private String ftbSupervisoryStaff;// 督导人员选择 (1医生 2家属 3自服药 4 其他)
    @Column(name = "FTB_ALONE_ROOM",length = 10)
    private String ftbAloneRoom;// 单独的居室(1有 2无)
    @Column(name = "FTB_VENTILATION_CONDITION",length = 10)
    private String ftbVentilationCondition;// 通风情况(1良好 2一般 3差)
    @Column(name = "FTB_SMOKE_CIGARETTE",length = 10)
    private String ftbSmokeCigarette;// 吸烟（支）
    @Column(name = "FTB_SMOKE_DAY",length = 10)
    private String ftbSmokeDay;// 吸烟(天)
    @Column(name = "FTB_DRINK_WINE",length = 10)
    private String ftbDrinkWine;// 饮酒(两)
    @Column(name = "FTB_DRINK_WINE_DAY",length = 10)
    private String ftbDrinkWineDay;// 饮酒（天）
    @Column(name = "FTB_TAKE_MEDICINE_ADDR",length = 200)
    private String ftbTakeMedicineAddr;// 取药地点
    @Column(name = "FTB_TAKE_MEDICINE_TIME",length = 50)
    private String ftbTakeMedicineTime;// 取药时间（年月日）
    @Column(name = "FTB_MEDICATION_RECORD_CARD",length = 10)
    private String ftbMedicationRecordCard;// 服务记录卡填写（1掌握 2未掌握）
    @Column(name = "FTB_TAKING_MEDICINE",length = 10)
    private String ftbTakingMedicine;//服药方法及药品存放(1掌握 2未掌握)
    @Column(name = "FTB_TREATMENT_COURSE",length = 10)
    private String ftbTreatmentCourse;// 肺结核治疗疗程(1掌握 2未掌握)
    @Column(name = "FTB_TAKING_MEDICINE_HARM",length = 10)
    private String ftbTakingMedicineHarm;// 不规律服药危害(1掌握 2未掌握)
    @Column(name = "FTB_HANDLING_ADVERSE_REACTIONS",length = 10)
    private String ftbHandlingAdverseReactions;// 服药后不良反应及处理(1掌握 2未掌握)
    @Column(name = "FTB_FURTHER_CONSULTATION",length = 10)
    private String ftbFurtherConsultation;// 治疗期间复诊查痰(1掌握 2未掌握)
    @Column(name = "FTB_GOOUT_TAKE_MEDICINE",length = 10)
    private String ftbGoOutTakeMedicine;// 外出期间如何坚持服药(1掌握 2未掌握)
    @Column(name = "FTB_LIFE_MATTERS",length = 10)
    private String ftbLifeMatters;//生活习惯及注意事项(1掌握 2未掌握)
    @Column(name = "FTB_CONTACT_INSPECTION",length = 10)
    private String ftbContactInspection;// 密切接触者检查(1掌握 2未掌握)
    @Column(name = "FTB_NEXT_TIME",length = 50)
    private String ftbNextTime;// 下次随访时间
    @Column(name = "FTB_DOCTOR_ID",length = 36)
    private String ftbDoctorId;// 评估医生id
    @Column(name = "FTB_DOCTOR_NAME",length = 20)
    private String ftbDoctorName;// 评估医生签名
    @Column(name = "IS_BASIC",length = 10)
    private String isBasic="0";//是基卫数据(0否 1是)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFtbVisitId() {
        return ftbVisitId;
    }

    public void setFtbVisitId(String ftbVisitId) {
        this.ftbVisitId = ftbVisitId;
    }

    public String getFtbPatientId() {
        return ftbPatientId;
    }

    public void setFtbPatientId(String ftbPatientId) {
        this.ftbPatientId = ftbPatientId;
    }

    public String getFtbPatientName() {
        return ftbPatientName;
    }

    public void setFtbPatientName(String ftbPatientName) {
        this.ftbPatientName = ftbPatientName;
    }

    public String getFtbCode() {
        return ftbCode;
    }

    public void setFtbCode(String ftbCode) {
        this.ftbCode = ftbCode;
    }

    public String getFtbVisitTime() {
        return ftbVisitTime;
    }

    public void setFtbVisitTime(String ftbVisitTime) {
        this.ftbVisitTime = ftbVisitTime;
    }

    public String getFtbVisitWay() {
        return ftbVisitWay;
    }

    public void setFtbVisitWay(String ftbVisitWay) {
        this.ftbVisitWay = ftbVisitWay;
    }

    public String getFtbPatientType() {
        return ftbPatientType;
    }

    public void setFtbPatientType(String ftbPatientType) {
        this.ftbPatientType = ftbPatientType;
    }

    public String getFtbSputumCondition() {
        return ftbSputumCondition;
    }

    public void setFtbSputumCondition(String ftbSputumCondition) {
        this.ftbSputumCondition = ftbSputumCondition;
    }

    public String getFtbDrugResistance() {
        return ftbDrugResistance;
    }

    public void setFtbDrugResistance(String ftbDrugResistance) {
        this.ftbDrugResistance = ftbDrugResistance;
    }

    public String getFtbSymptomsAndSigns() {
        return ftbSymptomsAndSigns;
    }

    public void setFtbSymptomsAndSigns(String ftbSymptomsAndSigns) {
        this.ftbSymptomsAndSigns = ftbSymptomsAndSigns;
    }

    public String getFtbSymptomsAndSignsOther() {
        return ftbSymptomsAndSignsOther;
    }

    public void setFtbSymptomsAndSignsOther(String ftbSymptomsAndSignsOther) {
        this.ftbSymptomsAndSignsOther = ftbSymptomsAndSignsOther;
    }

    public String getFtbChemotherapyRegimen() {
        return ftbChemotherapyRegimen;
    }

    public void setFtbChemotherapyRegimen(String ftbChemotherapyRegimen) {
        this.ftbChemotherapyRegimen = ftbChemotherapyRegimen;
    }

    public String getFtbUsage() {
        return ftbUsage;
    }

    public void setFtbUsage(String ftbUsage) {
        this.ftbUsage = ftbUsage;
    }

    public String getFtbDrugsDosageType() {
        return ftbDrugsDosageType;
    }

    public void setFtbDrugsDosageType(String ftbDrugsDosageType) {
        this.ftbDrugsDosageType = ftbDrugsDosageType;
    }

    public String getFtbSupervisoryStaff() {
        return ftbSupervisoryStaff;
    }

    public void setFtbSupervisoryStaff(String ftbSupervisoryStaff) {
        this.ftbSupervisoryStaff = ftbSupervisoryStaff;
    }

    public String getFtbAloneRoom() {
        return ftbAloneRoom;
    }

    public void setFtbAloneRoom(String ftbAloneRoom) {
        this.ftbAloneRoom = ftbAloneRoom;
    }

    public String getFtbVentilationCondition() {
        return ftbVentilationCondition;
    }

    public void setFtbVentilationCondition(String ftbVentilationCondition) {
        this.ftbVentilationCondition = ftbVentilationCondition;
    }

    public String getFtbSmokeCigarette() {
        return ftbSmokeCigarette;
    }

    public void setFtbSmokeCigarette(String ftbSmokeCigarette) {
        this.ftbSmokeCigarette = ftbSmokeCigarette;
    }

    public String getFtbSmokeDay() {
        return ftbSmokeDay;
    }

    public void setFtbSmokeDay(String ftbSmokeDay) {
        this.ftbSmokeDay = ftbSmokeDay;
    }

    public String getFtbDrinkWine() {
        return ftbDrinkWine;
    }

    public void setFtbDrinkWine(String ftbDrinkWine) {
        this.ftbDrinkWine = ftbDrinkWine;
    }

    public String getFtbDrinkWineDay() {
        return ftbDrinkWineDay;
    }

    public void setFtbDrinkWineDay(String ftbDrinkWineDay) {
        this.ftbDrinkWineDay = ftbDrinkWineDay;
    }

    public String getFtbTakeMedicineAddr() {
        return ftbTakeMedicineAddr;
    }

    public void setFtbTakeMedicineAddr(String ftbTakeMedicineAddr) {
        this.ftbTakeMedicineAddr = ftbTakeMedicineAddr;
    }

    public String getFtbTakeMedicineTime() {
        return ftbTakeMedicineTime;
    }

    public void setFtbTakeMedicineTime(String ftbTakeMedicineTime) {
        this.ftbTakeMedicineTime = ftbTakeMedicineTime;
    }

    public String getFtbMedicationRecordCard() {
        return ftbMedicationRecordCard;
    }

    public void setFtbMedicationRecordCard(String ftbMedicationRecordCard) {
        this.ftbMedicationRecordCard = ftbMedicationRecordCard;
    }

    public String getFtbTakingMedicine() {
        return ftbTakingMedicine;
    }

    public void setFtbTakingMedicine(String ftbTakingMedicine) {
        this.ftbTakingMedicine = ftbTakingMedicine;
    }

    public String getFtbTreatmentCourse() {
        return ftbTreatmentCourse;
    }

    public void setFtbTreatmentCourse(String ftbTreatmentCourse) {
        this.ftbTreatmentCourse = ftbTreatmentCourse;
    }

    public String getFtbTakingMedicineHarm() {
        return ftbTakingMedicineHarm;
    }

    public void setFtbTakingMedicineHarm(String ftbTakingMedicineHarm) {
        this.ftbTakingMedicineHarm = ftbTakingMedicineHarm;
    }

    public String getFtbHandlingAdverseReactions() {
        return ftbHandlingAdverseReactions;
    }

    public void setFtbHandlingAdverseReactions(String ftbHandlingAdverseReactions) {
        this.ftbHandlingAdverseReactions = ftbHandlingAdverseReactions;
    }

    public String getFtbFurtherConsultation() {
        return ftbFurtherConsultation;
    }

    public void setFtbFurtherConsultation(String ftbFurtherConsultation) {
        this.ftbFurtherConsultation = ftbFurtherConsultation;
    }

    public String getFtbGoOutTakeMedicine() {
        return ftbGoOutTakeMedicine;
    }

    public void setFtbGoOutTakeMedicine(String ftbGoOutTakeMedicine) {
        this.ftbGoOutTakeMedicine = ftbGoOutTakeMedicine;
    }

    public String getFtbLifeMatters() {
        return ftbLifeMatters;
    }

    public void setFtbLifeMatters(String ftbLifeMatters) {
        this.ftbLifeMatters = ftbLifeMatters;
    }

    public String getFtbContactInspection() {
        return ftbContactInspection;
    }

    public void setFtbContactInspection(String ftbContactInspection) {
        this.ftbContactInspection = ftbContactInspection;
    }

    public String getFtbNextTime() {
        return ftbNextTime;
    }

    public void setFtbNextTime(String ftbNextTime) {
        this.ftbNextTime = ftbNextTime;
    }

    public String getFtbDoctorId() {
        return ftbDoctorId;
    }

    public void setFtbDoctorId(String ftbDoctorId) {
        this.ftbDoctorId = ftbDoctorId;
    }

    public String getFtbDoctorName() {
        return ftbDoctorName;
    }

    public void setFtbDoctorName(String ftbDoctorName) {
        this.ftbDoctorName = ftbDoctorName;
    }

    public String getIsBasic() {
        return isBasic;
    }

    public void setIsBasic(String isBasic) {
        this.isBasic = isBasic;
    }
}
