package com.ylz.bizDo.plan.vo;

import com.ylz.bizDo.plan.po.*;

import java.util.List;

/** 保存随访记录
 * Created by zzl on 2017/7/24.
 */
public class AppSaveFollowQvo{
    private AppHdBlooPressureTable saveHdBloo;//保存高血压随访记录
    private AppDiabetesTable saveDiabetes;//保存糖尿病随访记录
    private AppChild saveNewChild;//保存新生儿随访记录
    private AppMentalVisitTable saveMentalVisit;//保存精神病随访记录
    private List<AppMedicationTable> userMedicine;//用药情况
    private AppPostpartumVisit postPar;//产后访视记录表
    private String sfXaxis;//x轴
    private String sfYaxis;//y轴
    private AppFirstTBFollowVisitTable saveFTB;//保存肺结核第一次入户随访记录
    private AppTBFollowVisitTable saveTB;//保存肺结核随访记录

    public AppHdBlooPressureTable getSaveHdBloo() {
        return saveHdBloo;
    }

    public void setSaveHdBloo(AppHdBlooPressureTable saveHdBloo) {
        this.saveHdBloo = saveHdBloo;
    }

    public List<AppMedicationTable> getUserMedicine() {
        return userMedicine;
    }

    public void setUserMedicine(List<AppMedicationTable> userMedicine) {
        this.userMedicine = userMedicine;
    }

    public AppDiabetesTable getSaveDiabetes() {
        return saveDiabetes;
    }

    public void setSaveDiabetes(AppDiabetesTable saveDiabetes) {
        this.saveDiabetes = saveDiabetes;
    }

    public String getSfXaxis() {
        return sfXaxis;
    }

    public void setSfXaxis(String sfXaxis) {
        this.sfXaxis = sfXaxis;
    }

    public String getSfYaxis() {
        return sfYaxis;
    }

    public void setSfYaxis(String sfYaxis) {
        this.sfYaxis = sfYaxis;
    }

    public AppChild getSaveNewChild() {
        return saveNewChild;
    }

    public void setSaveNewChild(AppChild saveNewChild) {
        this.saveNewChild = saveNewChild;
    }

    public AppPostpartumVisit getPostPar() {
        return postPar;
    }

    public void setPostPar(AppPostpartumVisit postPar) {
        this.postPar = postPar;
    }

    public AppMentalVisitTable getSaveMentalVisit() {
        return saveMentalVisit;
    }

    public void setSaveMentalVisit(AppMentalVisitTable saveMentalVisit) {
        this.saveMentalVisit = saveMentalVisit;
    }

    public AppFirstTBFollowVisitTable getSaveFTB() {
        return saveFTB;
    }

    public void setSaveFTB(AppFirstTBFollowVisitTable saveFTB) {
        this.saveFTB = saveFTB;
    }

    public AppTBFollowVisitTable getSaveTB() {
        return saveTB;
    }

    public void setSaveTB(AppTBFollowVisitTable saveTB) {
        this.saveTB = saveTB;
    }
}
