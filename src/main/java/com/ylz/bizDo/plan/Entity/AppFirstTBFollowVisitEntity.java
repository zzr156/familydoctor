package com.ylz.bizDo.plan.Entity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * 结核病第一次入户随访信息
 * Created by zzl on 2019/4/10.
 */
public class AppFirstTBFollowVisitEntity {
    private String id;
    private String ftbVisitId;//随访外建
    private String ftbPatientId;// 患者主键
    private String ftbPatientName;// 姓名
    private String ftbCode;// 编号
    private String ftbVisitTime;// 随访时间
    private String ftbVisitWay;// 随访方式(1门诊 2.家庭)
    private String ftbPatientType;// 患者类型(1初治 2复治 )
    private String ftbSputumCondition;// 痰菌情况(1阳性 2阴性 3未查痰)
    private String ftbDrugResistance;// 耐药情况 (1耐药 2非耐药 3未检测)
    private String ftbSymptomsAndSigns;// 症状及体征((0没有症状 1咳嗽咳痰 2低热盗汗 3咯血或血痰 4 胸痛消瘦 5恶心纳差 6头痛失眠 7视物模糊 8皮肤瘙痒、皮疹 9 耳鸣、听力下降 10其他)
    private String ftbSymptomsAndSignsOther;//其他症状和体征内容
    private String ftbChemotherapyRegimen;// 化疗方案
    private String ftbUsage;// 用法（1每日  2 间歇）
    private String ftbDrugsDosageType;// 药品剂型(1 固定剂量复合制剂 2 散装药 3 板式组合药 4 注射剂)
    private String ftbSupervisoryStaff;// 督导人员选择 (1医生 2家属 3自服药 4 其他)
    private String ftbAloneRoom;// 单独的居室(1有 2无)
    private String ftbVentilationCondition;// 通风情况(1良好 2一般 3差)
    private String ftbSmokeCigarette;// 吸烟（支）
    private String ftbSmokeDay;// 吸烟(天)
    private String ftbDrinkWine;// 饮酒(两)
    private String ftbDrinkWineDay;// 饮酒（天）
    private String ftbTakeMedicineAddr;// 取药地点
    private String ftbTakeMedicineTime;// 取药时间
    private String ftbMedicationRecordCard;// 服务记录卡填写（1掌握 2未掌握）
    private String ftbTakingMedicine;//服药方法及药品存放(1掌握 2未掌握)
    private String ftbTreatmentCourse;// 肺结核治疗疗程(1掌握 2未掌握)
    private String ftbTakingMedicineHarm;// 不规律服药危害(1掌握 2未掌握)
    private String ftbHandlingAdverseReactions;// 服药后不良反应及处理(1掌握 2未掌握)
    private String ftbFurtherConsultation;// 治疗期间复诊查痰(1掌握 2未掌握)
    private String ftbGoOutTakeMedicine;// 外出期间如何坚持服药(1掌握 2未掌握)
    private String ftbLifeMatters;//生活习惯及注意事项(1掌握 2未掌握)
    private String ftbContactInspection;// 密切接触者检查(1掌握 2未掌握)
    private String ftbNextTime;// 下次随访时间
    private String ftbDoctorId;// 评估医生id
    private String ftbDoctorName;// 评估医生签名

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

    public void setFtbVisitWay(String ftbVisitWay) throws Exception{
        if(StringUtils.isNotBlank(ftbVisitWay)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBWAY, ftbVisitWay);
            if(value!=null){
                ftbVisitWay = value.getCodeTitle();
            }
        }
        this.ftbVisitWay = ftbVisitWay;
    }

    public String getFtbPatientType() {
        return ftbPatientType;
    }

    public void setFtbPatientType(String ftbPatientType) throws Exception{
        if(StringUtils.isNotBlank(ftbPatientType)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBHZLX, ftbPatientType);
            if(value!=null){
                ftbPatientType = value.getCodeTitle();
            }
        }
        this.ftbPatientType = ftbPatientType;
    }

    public String getFtbSputumCondition() {
        return ftbSputumCondition;
    }

    public void setFtbSputumCondition(String ftbSputumCondition) throws Exception{
        if(StringUtils.isNotBlank(ftbSputumCondition)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBTJQK, ftbSputumCondition);
            if(value!=null){
                ftbSputumCondition = value.getCodeTitle();
            }
        }
        this.ftbSputumCondition = ftbSputumCondition;
    }

    public String getFtbDrugResistance() {
        return ftbDrugResistance;
    }

    public void setFtbDrugResistance(String ftbDrugResistance) throws Exception{
        if(StringUtils.isNotBlank(ftbDrugResistance)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBNYQK, ftbDrugResistance);
            if(value!=null){
                ftbDrugResistance = value.getCodeTitle();
            }
        }
        this.ftbDrugResistance = ftbDrugResistance;
    }

    public String getFtbSymptomsAndSigns() {
        return ftbSymptomsAndSigns;
    }

    public void setFtbSymptomsAndSigns(String ftbSymptomsAndSigns) throws Exception{
        String str = "";
        if (StringUtils.isNotBlank(ftbSymptomsAndSigns)) {
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            String[] ss = ftbSymptomsAndSigns.split(";");
            for(String s:ss){
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBZZTZ, s);
                if(value!=null){
                    if(StringUtils.isBlank(str)){
                        str = value.getCodeTitle();
                    }else{
                        str += ";"+value.getCodeTitle();
                    }
                }
            }
        }
        this.ftbSymptomsAndSigns = str;
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

    public void setFtbUsage(String ftbUsage) throws Exception{
        if(StringUtils.isNotBlank(ftbUsage)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBYF, ftbUsage);
            if(value!=null){
                ftbUsage = value.getCodeTitle();
            }
        }
        this.ftbUsage = ftbUsage;
    }

    public String getFtbDrugsDosageType() {
        return ftbDrugsDosageType;
    }

    public void setFtbDrugsDosageType(String ftbDrugsDosageType) throws Exception{
        if(StringUtils.isNotBlank(ftbDrugsDosageType)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBYPJX, ftbDrugsDosageType);
            if(value!=null){
                ftbDrugsDosageType = value.getCodeTitle();
            }
        }
        this.ftbDrugsDosageType = ftbDrugsDosageType;
    }

    public String getFtbSupervisoryStaff() {
        return ftbSupervisoryStaff;
    }

    public void setFtbSupervisoryStaff(String ftbSupervisoryStaff) throws Exception{
        if(StringUtils.isNotBlank(ftbSupervisoryStaff)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBDDRY, ftbSupervisoryStaff);
            if(value!=null){
                ftbSupervisoryStaff = value.getCodeTitle();
            }
        }
        this.ftbSupervisoryStaff = ftbSupervisoryStaff;
    }

    public String getFtbAloneRoom() {
        return ftbAloneRoom;
    }

    public void setFtbAloneRoom(String ftbAloneRoom) throws Exception{
        if(StringUtils.isNotBlank(ftbAloneRoom)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBDJ, ftbAloneRoom);
            if(value!=null){
                ftbAloneRoom = value.getCodeTitle();
            }
        }
        this.ftbAloneRoom = ftbAloneRoom;
    }

    public String getFtbVentilationCondition() {
        return ftbVentilationCondition;
    }

    public void setFtbVentilationCondition(String ftbVentilationCondition)throws Exception {
        if(StringUtils.isNotBlank(ftbVentilationCondition)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_LHC, ftbVentilationCondition);
            if(value!=null){
                ftbVentilationCondition = value.getCodeTitle();
            }
        }
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

    public void setFtbMedicationRecordCard(String ftbMedicationRecordCard) throws Exception{
        if(StringUtils.isNotBlank(ftbMedicationRecordCard)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBZWWZW, ftbMedicationRecordCard);
            if(value!=null){
                ftbMedicationRecordCard = value.getCodeTitle();
            }
        }
        this.ftbMedicationRecordCard = ftbMedicationRecordCard;
    }

    public String getFtbTakingMedicine() {
        return ftbTakingMedicine;
    }

    public void setFtbTakingMedicine(String ftbTakingMedicine) throws Exception{
        if(StringUtils.isNotBlank(ftbTakingMedicine)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBZWWZW, ftbTakingMedicine);
            if(value!=null){
                ftbTakingMedicine = value.getCodeTitle();
            }
        }
        this.ftbTakingMedicine = ftbTakingMedicine;
    }

    public String getFtbTreatmentCourse() {
        return ftbTreatmentCourse;
    }

    public void setFtbTreatmentCourse(String ftbTreatmentCourse)throws Exception {
        if(StringUtils.isNotBlank(ftbTreatmentCourse)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBZWWZW, ftbTreatmentCourse);
            if(value!=null){
                ftbTreatmentCourse = value.getCodeTitle();
            }
        }
        this.ftbTreatmentCourse = ftbTreatmentCourse;
    }

    public String getFtbTakingMedicineHarm() {
        return ftbTakingMedicineHarm;
    }

    public void setFtbTakingMedicineHarm(String ftbTakingMedicineHarm) throws Exception{
        if(StringUtils.isNotBlank(ftbTakingMedicineHarm)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBZWWZW, ftbTakingMedicineHarm);
            if(value!=null){
                ftbTakingMedicineHarm = value.getCodeTitle();
            }
        }
        this.ftbTakingMedicineHarm = ftbTakingMedicineHarm;
    }

    public String getFtbHandlingAdverseReactions() {
        return ftbHandlingAdverseReactions;
    }

    public void setFtbHandlingAdverseReactions(String ftbHandlingAdverseReactions)throws Exception {
        if(StringUtils.isNotBlank(ftbHandlingAdverseReactions)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBZWWZW, ftbHandlingAdverseReactions);
            if(value!=null){
                ftbHandlingAdverseReactions = value.getCodeTitle();
            }
        }
        this.ftbHandlingAdverseReactions = ftbHandlingAdverseReactions;
    }

    public String getFtbFurtherConsultation() {
        return ftbFurtherConsultation;
    }

    public void setFtbFurtherConsultation(String ftbFurtherConsultation)throws Exception {
        if(StringUtils.isNotBlank(ftbFurtherConsultation)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBZWWZW, ftbFurtherConsultation);
            if(value!=null){
                ftbFurtherConsultation = value.getCodeTitle();
            }
        }
        this.ftbFurtherConsultation = ftbFurtherConsultation;
    }

    public String getFtbGoOutTakeMedicine() {
        return ftbGoOutTakeMedicine;
    }

    public void setFtbGoOutTakeMedicine(String ftbGoOutTakeMedicine)throws Exception {
        if(StringUtils.isNotBlank(ftbGoOutTakeMedicine)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBZWWZW, ftbGoOutTakeMedicine);
            if(value!=null){
                ftbGoOutTakeMedicine = value.getCodeTitle();
            }
        }
        this.ftbGoOutTakeMedicine = ftbGoOutTakeMedicine;
    }

    public String getFtbLifeMatters() {
        return ftbLifeMatters;
    }

    public void setFtbLifeMatters(String ftbLifeMatters) throws Exception{
        if(StringUtils.isNotBlank(ftbLifeMatters)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBZWWZW, ftbLifeMatters);
            if(value!=null){
                ftbLifeMatters = value.getCodeTitle();
            }
        }
        this.ftbLifeMatters = ftbLifeMatters;
    }

    public String getFtbContactInspection() {
        return ftbContactInspection;
    }

    public void setFtbContactInspection(String ftbContactInspection) throws Exception{
        if(StringUtils.isNotBlank(ftbContactInspection)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBZWWZW, ftbContactInspection);
            if(value!=null){
                ftbContactInspection = value.getCodeTitle();
            }
        }
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
}
