package com.ylz.bizDo.plan.Entity;


import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**新生儿家庭访视记录表
 * Created by admin on 2017-05-13.
 */
public class AppNewChildrenTableEntity implements java.io.Serializable{
    private String id;//主键
    private String childVisitId;//外键
    private String childAgeGroup;//年龄段
    private String childPatientId;//随访人员id
    private String childName;//儿童姓名
    private String childCode;//编号
    private String childGender;//性别
    private String childBirthDay;//出生日期
    private String childIdNo;//身份证号
    private String childAddress;//家庭住址
    private String childFatherName;//父亲姓名
    private String childFatherOccupation;//职业
    private String childFatherTel;//联系电话
    private String childFatherBirth;//父亲出生日期
    private String childMotherName;//母亲姓名
    private String childMotherOccupation;//母亲职业
    private String childMotherTel;//母亲电话
    private String childMotherBirth;//母亲出生日期
    private String childGestationalWeeks;//孕周
    private String childMotherSituation;//母亲妊娠期患病情况
    private String childAccoucheOrgId;//助产机构id
    private String childAccoucheOrg;//助产机构名称
    private String childBirthSituation;//出生情况（1顺产 2胎头吸引 3产钳 4剖宫 5双多胎 6臀位 7其他）
    private String childBirthSituationOther;//出生其他情况内容
    private String childAsphyxia;//新生儿窒息情况(1min--1无 ，5min--2有)
    private String childDeformity;//畸形情况(1无 2有)
    private String childDeformityContent;//有畸形情况内容
    private String childHearingScreening;//新生儿听力筛查（1通过 2未通过 3未筛查 4不详）
    private String childDiseaseScreening;//新生儿疾病筛查（1未进行 2检查均阴性 3甲低 4苯丙酮尿症 5其他遗传代谢病）
    private String childBirthWeight;//新生儿出生体重
    private String childWeight;//目前体重
    private String childBirthHeight;//出生身长
    private String childFeedingWay;//喂养方式（1纯母乳 2混合 3人工）
    private String childEatMilk;//吃奶量 ml/次
    private String childEatMilkNum;//吃奶次数 次/日
    private String childVomiting;//呕吐（1无 2有）
    private String childShit;//大便 （1糊状 2稀 3其他）
    private String childShitNum;//大便次数 次/日
    private String childAnimalHeat;//体温
    private String childHeartRate;//心率
    private String childBreathingRate;//呼吸频率
    private String childFace;//面色（1红润 2黄染 3其他）
    private String childFaceOther;//面色其他内容
    private String childJaundiceParts;//黄疸部位(1无 2面部 3躯干 4四肢 5手足)
    private String childBregmaONE;//前囟数据1
    private String childBregmaTWO;//前囟数据2
    private String childBregma;//前囟(1正常 2膨隆 3凹陷 4其他)
    private String childBregmaOther;//前囟其他内容
    private String childEyes;//眼睛（1未见异常 2异常）
    private String childLimbsActivity;//四肢活动度（1未见异常 2异常）
    private String childEarAppearance;//耳外观（1未见异常 2异常）
    private String childNeckBagPiece;//颈部包块（1无 2有）
    private String childNose;//鼻（1未见异常 2异常）
    private String childSkin;//皮肤（1未见异常 2湿疹 3糜烂 4其他）
    private String childOral;//口腔（1未见异常 2异常）
    private String childAnus;//肛门（1未见异常 2异常）
    private String childLungAuscultation;//心肺听诊（1未见异常 2异常）
    private String childChest;//胸部（1未见异常 2异常）
    private String childAbdominalTouch;//腹部触诊（1未见异常 2异常）
    private String childSpine;//脊柱（1未见异常 2异常）
    private String childGenitals;//外生殖器（1未见异常 2异常）
    private String childUmbilicalCord;//脐带（1未脱 2脱落 3脐部有渗出 4其他）
    private String childUmbilicalCordOther;//脐带其他选项内容
    private String childReferral;//转诊情况（0无 1有）
    private String childReferralReason;//转诊建议原因
    private String childReferralOrg;//转诊机构id
    private String childGuide;//指导（1喂养指导 2发育指导 3防病指导 4预防伤害指导 5口腔保健指导 6其他
    private String childGuideOther;//指导其他选项内容
    private String childVisitTime;//本次访视日期
    private String childNextVisitAddress;//下次随访地点
    private String childNextVisiTime;//下次随访日期
    private String childVisitDoctorId;//随访医生签名
    private String childReferralDept;//转诊科室
    private String childVisitDoctorImage;//随访医生签名
    private String childVisitWay;//本次随访方式

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChildVisitId() {
        return childVisitId;
    }

    public void setChildVisitId(String childVisitId) {
        this.childVisitId = childVisitId;
    }

    public String getChildAgeGroup() {
        return childAgeGroup;
    }

    public void setChildAgeGroup(String childAgeGroup) {
        this.childAgeGroup = childAgeGroup;
    }

    public String getChildPatientId() {
        return childPatientId;
    }

    public void setChildPatientId(String childPatientId) {
        this.childPatientId = childPatientId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public String getChildGender() {
        return childGender;
    }

    public void setChildGender(String childGender) {
        this.childGender = childGender;
    }

    public String getChildBirthDay() {
        return childBirthDay;
    }

    public void setChildBirthDay(String childBirthDay) {
        this.childBirthDay = childBirthDay;
    }

    public String getChildIdNo() {
        return childIdNo;
    }

    public void setChildIdNo(String childIdNo) {
        this.childIdNo = childIdNo;
    }

    public String getChildAddress() {
        return childAddress;
    }

    public void setChildAddress(String childAddress) {
        this.childAddress = childAddress;
    }

    public String getChildFatherName() {
        return childFatherName;
    }

    public void setChildFatherName(String childFatherName) {
        this.childFatherName = childFatherName;
    }

    public String getChildFatherOccupation() {
        return childFatherOccupation;
    }

    public void setChildFatherOccupation(String childFatherOccupation) {
        this.childFatherOccupation = childFatherOccupation;
    }

    public String getChildFatherTel() {
        return childFatherTel;
    }

    public void setChildFatherTel(String childFatherTel) {
        this.childFatherTel = childFatherTel;
    }

    public String getChildFatherBirth() {
        return childFatherBirth;
    }

    public void setChildFatherBirth(String childFatherBirth) {
        this.childFatherBirth = childFatherBirth;
    }

    public String getChildMotherName() {
        return childMotherName;
    }

    public void setChildMotherName(String childMotherName) {
        this.childMotherName = childMotherName;
    }

    public String getChildMotherOccupation() {
        return childMotherOccupation;
    }

    public void setChildMotherOccupation(String childMotherOccupation) {
        this.childMotherOccupation = childMotherOccupation;
    }

    public String getChildMotherTel() {
        return childMotherTel;
    }

    public void setChildMotherTel(String childMotherTel) {
        this.childMotherTel = childMotherTel;
    }

    public String getChildMotherBirth() {
        return childMotherBirth;
    }

    public void setChildMotherBirth(String childMotherBirth) {
        this.childMotherBirth = childMotherBirth;
    }

    public String getChildGestationalWeeks() {
        return childGestationalWeeks;
    }

    public void setChildGestationalWeeks(String childGestationalWeeks) {
        this.childGestationalWeeks = childGestationalWeeks;
    }

    public String getChildMotherSituation() {
        return childMotherSituation;
    }

    public void setChildMotherSituation(String childMotherSituation) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childMotherSituation)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ETRCQHB, childMotherSituation);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childMotherSituation = str;
    }

    public String getChildAccoucheOrgId() {
        return childAccoucheOrgId;
    }

    public void setChildAccoucheOrgId(String childAccoucheOrgId) {
        this.childAccoucheOrgId = childAccoucheOrgId;
    }

    public String getChildAccoucheOrg() {
        return childAccoucheOrg;
    }

    public void setChildAccoucheOrg(String childAccoucheOrg) {
        this.childAccoucheOrg = childAccoucheOrg;
    }

    public String getChildBirthSituation() {
        return childBirthSituation;
    }

    public void setChildBirthSituation(String childBirthSituation) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childBirthSituation)){
            String[] ss= childBirthSituation.split(";");
            for(String s:ss){
                SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ETCSQK, s);
                if(value!=null){
                    if(StringUtils.isBlank(str)){
                        str = value.getCodeTitle();
                    }else{
                        str += ","+value.getCodeTitle();
                    }
                }
            }
        }
        this.childBirthSituation = str;
    }

    public String getChildBirthSituationOther() {
        return childBirthSituationOther;
    }

    public void setChildBirthSituationOther(String childBirthSituationOther) {
        this.childBirthSituationOther = childBirthSituationOther;
    }

    public String getChildAsphyxia() {
        return childAsphyxia;
    }

    public void setChildAsphyxia(String childAsphyxia) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childAsphyxia)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFYW, childAsphyxia);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childAsphyxia = str;
    }

    public String getChildDeformity() {
        return childDeformity;
    }

    public void setChildDeformity(String childDeformity) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childDeformity)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFYW, childDeformity);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childDeformity = str;
    }

    public String getChildDeformityContent() {
        return childDeformityContent;
    }

    public void setChildDeformityContent(String childDeformityContent) {
        this.childDeformityContent = childDeformityContent;
    }

    public String getChildHearingScreening() {
        return childHearingScreening;
    }

    public void setChildHearingScreening(String childHearingScreening) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childHearingScreening)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ETTLSC, childHearingScreening);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childHearingScreening = str;
    }

    public String getChildDiseaseScreening() {
        return childDiseaseScreening;
    }

    public void setChildDiseaseScreening(String childDiseaseScreening) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childDiseaseScreening)){
            String[] ss = childDiseaseScreening.split(";");
            for(String s:ss){
                SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ETJBSC, s);
                if(value!=null){
                    if(StringUtils.isBlank(str)){
                        str = value.getCodeTitle();
                    }else{
                        str += "," + value.getCodeTitle();
                    }
                }
            }
        }
        this.childDiseaseScreening = str;
    }

    public String getChildBirthWeight() {
        return childBirthWeight;
    }

    public void setChildBirthWeight(String childBirthWeight) {
        this.childBirthWeight = childBirthWeight;
    }

    public String getChildWeight() {
        return childWeight;
    }

    public void setChildWeight(String childWeight) {
        this.childWeight = childWeight;
    }

    public String getChildBirthHeight() {
        return childBirthHeight;
    }

    public void setChildBirthHeight(String childBirthHeight) {
        this.childBirthHeight = childBirthHeight;
    }

    public String getChildFeedingWay() {
        return childFeedingWay;
    }

    public void setChildFeedingWay(String childFeedingWay) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childFeedingWay)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WYFS, childFeedingWay);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childFeedingWay = str;
    }

    public String getChildEatMilk() {
        return childEatMilk;
    }

    public void setChildEatMilk(String childEatMilk) {
        this.childEatMilk = childEatMilk;
    }

    public String getChildEatMilkNum() {
        return childEatMilkNum;
    }

    public void setChildEatMilkNum(String childEatMilkNum) {
        this.childEatMilkNum = childEatMilkNum;
    }

    public String getChildVomiting() {
        return childVomiting;
    }

    public void setChildVomiting(String childVomiting) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childVomiting)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFYW, childVomiting);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childVomiting = str;
    }

    public String getChildShit() {
        return childShit;
    }

    public void setChildShit(String childShit) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childShit)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ETDBQK, childShit);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childShit = str;
    }

    public String getChildShitNum() {
        return childShitNum;
    }

    public void setChildShitNum(String childShitNum) {
        this.childShitNum = childShitNum;
    }

    public String getChildAnimalHeat() {
        return childAnimalHeat;
    }

    public void setChildAnimalHeat(String childAnimalHeat) {
        this.childAnimalHeat = childAnimalHeat;
    }

    public String getChildHeartRate() {
        return childHeartRate;
    }

    public void setChildHeartRate(String childHeartRate) {
        this.childHeartRate = childHeartRate;
    }

    public String getChildBreathingRate() {
        return childBreathingRate;
    }

    public void setChildBreathingRate(String childBreathingRate) {
        this.childBreathingRate = childBreathingRate;
    }

    public String getChildFace() {
        return childFace;
    }

    public void setChildFace(String childFace) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childFace)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ETMSQK, childFace);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childFace = str;
    }

    public String getChildFaceOther() {
        return childFaceOther;
    }

    public void setChildFaceOther(String childFaceOther) {
        this.childFaceOther = childFaceOther;
    }

    public String getChildJaundiceParts() {
        return childJaundiceParts;
    }

    public void setChildJaundiceParts(String childJaundiceParts) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childJaundiceParts)){
            String[] ss = childJaundiceParts.split(";");
            for(String s:ss){
                SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ETHDBWQK, childJaundiceParts);
                if(value!=null){
                    if(StringUtils.isBlank(str)){
                        str = value.getCodeTitle();
                    }else{
                        str += value.getCodeTitle();
                    }
                }
            }
        }
        this.childJaundiceParts = str;
    }

    public String getChildBregmaONE() {
        return childBregmaONE;
    }

    public void setChildBregmaONE(String childBregmaONE) {
        this.childBregmaONE = childBregmaONE;
    }

    public String getChildBregmaTWO() {
        return childBregmaTWO;
    }

    public void setChildBregmaTWO(String childBregmaTWO) {
        this.childBregmaTWO = childBregmaTWO;
    }

    public String getChildBregma() {
        return childBregma;
    }

    public void setChildBregma(String childBregma) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childBregma)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_QLQK, childBregma);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childBregma = str;
    }

    public String getChildBregmaOther() {
        return childBregmaOther;
    }

    public void setChildBregmaOther(String childBregmaOther) {
        this.childBregmaOther = childBregmaOther;
    }

    public String getChildEyes() {
        return childEyes;
    }

    public void setChildEyes(String childEyes) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childEyes)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childEyes);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childEyes = str;
    }

    public String getChildLimbsActivity() {
        return childLimbsActivity;
    }

    public void setChildLimbsActivity(String childLimbsActivity) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childLimbsActivity)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childLimbsActivity);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childLimbsActivity = str;
    }

    public String getChildEarAppearance() {
        return childEarAppearance;
    }

    public void setChildEarAppearance(String childEarAppearance) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childEarAppearance)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childEarAppearance);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childEarAppearance = str;
    }

    public String getChildNeckBagPiece() {
        return childNeckBagPiece;
    }

    public void setChildNeckBagPiece(String childNeckBagPiece) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childNeckBagPiece)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFYW, childNeckBagPiece);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childNeckBagPiece = str;
    }

    public String getChildNose() {
        return childNose;
    }

    public void setChildNose(String childNose) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childNose)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childNose);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childNose = str;
    }

    public String getChildSkin() {
        return childSkin;
    }

    public void setChildSkin(String childSkin) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childSkin)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childSkin);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childSkin = str;
    }

    public String getChildOral() {
        return childOral;
    }

    public void setChildOral(String childOral) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childOral)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childOral);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childOral = str;
    }

    public String getChildAnus() {
        return childAnus;
    }

    public void setChildAnus(String childAnus) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childAnus)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childAnus);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childAnus = str;
    }

    public String getChildLungAuscultation() {
        return childLungAuscultation;
    }

    public void setChildLungAuscultation(String childLungAuscultation) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childLungAuscultation)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childLungAuscultation);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childLungAuscultation = str;
    }

    public String getChildChest() {
        return childChest;
    }

    public void setChildChest(String childChest) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childChest)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childChest);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childChest = str;
    }

    public String getChildAbdominalTouch() {
        return childAbdominalTouch;
    }

    public void setChildAbdominalTouch(String childAbdominalTouch) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childAbdominalTouch)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childAbdominalTouch);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childAbdominalTouch = str;
    }

    public String getChildSpine() {
        return childSpine;
    }

    public void setChildSpine(String childSpine) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childSpine)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childSpine);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childSpine = str;
    }

    public String getChildGenitals() {
        return childGenitals;
    }

    public void setChildGenitals(String childGenitals) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childGenitals)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YCQK, childGenitals);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childGenitals = str;
    }

    public String getChildUmbilicalCord() {
        return childUmbilicalCord;
    }

    public void setChildUmbilicalCord(String childUmbilicalCord) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childUmbilicalCord)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ETQDQK, childUmbilicalCord);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childUmbilicalCord = str;
    }

    public String getChildUmbilicalCordOther() {
        return childUmbilicalCordOther;
    }

    public void setChildUmbilicalCordOther(String childUmbilicalCordOther) {
        this.childUmbilicalCordOther = childUmbilicalCordOther;
    }

    public String getChildReferral() {
        return childReferral;
    }

    public void setChildReferral(String childReferral) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childReferral)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFCOMMON, childReferral);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childReferral = str;
    }

    public String getChildReferralReason() {
        return childReferralReason;
    }

    public void setChildReferralReason(String childReferralReason) {
        this.childReferralReason = childReferralReason;
    }

    public String getChildReferralOrg() {
        return childReferralOrg;
    }

    public void setChildReferralOrg(String childReferralOrg) {
        this.childReferralOrg = childReferralOrg;
    }

    public String getChildGuide() {
        return childGuide;
    }

    public void setChildGuide(String childGuide) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childGuide)){
            String[] ss = childGuide.split(";");
            for(String s:ss){
                SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ETXSEZD, s);
                if(value!=null){
                    str += value.getCodeTitle()+";";
                }
            }
        }
        this.childGuide = str;
    }

    public String getChildGuideOther() {
        return childGuideOther;
    }

    public void setChildGuideOther(String childGuideOther) {
        this.childGuideOther = childGuideOther;
    }

    public String getChildVisitTime() {
        return childVisitTime;
    }

    public void setChildVisitTime(String childVisitTime) {
        this.childVisitTime = childVisitTime;
    }

    public String getChildNextVisitAddress() {
        return childNextVisitAddress;
    }

    public void setChildNextVisitAddress(String childNextVisitAddress) {
        this.childNextVisitAddress = childNextVisitAddress;
    }

    public String getChildNextVisiTime() {
        return childNextVisiTime;
    }

    public void setChildNextVisiTime(String childNextVisiTime) {
        this.childNextVisiTime = childNextVisiTime;
    }

    public String getChildVisitDoctorId() {
        return childVisitDoctorId;
    }

    public void setChildVisitDoctorId(String childVisitDoctorId) {
        this.childVisitDoctorId = childVisitDoctorId;
    }

    public String getChildReferralDept() {
        return childReferralDept;
    }

    public void setChildReferralDept(String childReferralDept) {
        this.childReferralDept = childReferralDept;
    }

    public String getChildVisitDoctorImage() {
        return childVisitDoctorImage;
    }

    public void setChildVisitDoctorImage(String childVisitDoctorImage) {
        this.childVisitDoctorImage = childVisitDoctorImage;
    }

    public String getChildVisitWay() {
        return childVisitWay;
    }

    public void setChildVisitWay(String childVisitWay) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(childVisitWay)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FOLLOWWAY, childVisitWay);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.childVisitWay = str;
    }

    /**
     * 获取医生姓名
     * @return
     */
    public String getDrName(){
        if(StringUtils.isNotBlank(this.getChildVisitDoctorId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getChildVisitDoctorId());
            if(drUser!=null){
                return drUser.getDrName();
            }
        }
        return "";
    }
}
