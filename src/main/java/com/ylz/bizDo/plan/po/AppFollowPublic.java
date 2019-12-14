package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**随访公共数据表
 * Created by zzl on 2017/8/9.
 */
@Entity
@Table(name = "APP_FOLLOW_PUBLIC")
public class AppFollowPublic extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "BLOOD_PRESSURE_ONE", length = 50)
    private String bloodPressureOne;//血压（mmHg）收缩压
    @Column(name = "WEIGHT", length = 50)
    private String weight;//体重（kg）
    @Column(name = "HEIGHT", length = 10)
    private String height;//身高
    @Column(name = "BMI", length = 50)
    private String bmi;//体质指数（kg/m^2）
    @Column(name = "SOMKING_TO_DAY", length = 20)
    private String smokingToDay;//日吸烟量（支）
    @Column(name = "SOMKING_NEXTTO_DAY", length = 20)
    private String smokingNextToDay;//下次目标日吸烟量（支）
    @Column(name = "DRINKING_TO_DAY", length = 20)
    private String drinkingToDay;//日饮酒量（两）
    @Column(name = "DRINKING_NEXTTO_DAY", length = 20)
    private String drinkingNextToDay;//下次目标日饮酒量（两）
    @Column(name = "ACTIVITY_TO_WEEK", length = 20)
    private String activityToWeek;//运动（次/周）
    @Column(name = "ACTIVITY_TO_TIME", length = 20)
    private String activityToTime;//运动（分钟/次）
    @Column(name = "ACTIVITY_NEXTTO_WEEK", length = 20)
    private String activityNextToWeek;//下次目标运动（次/周）
    @Column(name = "ACTIVITY_NEXTTO_TIME", length = 20)
    private String activityNextToTime;//下次目标运动（分钟/次）
    @Column(name = "BLOOD_PRESSURE_TWO", length = 50)
    private String bloodPressureTwo;//血压（mmHg）舒张压
    @Column(name = "CREATE_TIME")
    private Calendar createTime;//创建时间
    @Column(name = "FOLLOW_NUM",length = 100)
    private String followNum;//随访编号
    @Column(name = "NEXT_WEIGHT",length = 10)
    private String nextWeight;//下一次体重
    @Column(name = "HEART_RATE", length = 50)
    private String heartRate;//心率（次/分钟）
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBloodPressureOne() {
        return bloodPressureOne;
    }

    public void setBloodPressureOne(String bloodPressureOne) {
        this.bloodPressureOne = bloodPressureOne;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getSmokingToDay() {
        return smokingToDay;
    }

    public void setSmokingToDay(String smokingToDay) {
        this.smokingToDay = smokingToDay;
    }

    public String getSmokingNextToDay() {
        return smokingNextToDay;
    }

    public void setSmokingNextToDay(String smokingNextToDay) {
        this.smokingNextToDay = smokingNextToDay;
    }

    public String getDrinkingToDay() {
        return drinkingToDay;
    }

    public void setDrinkingToDay(String drinkingToDay) {
        this.drinkingToDay = drinkingToDay;
    }

    public String getDrinkingNextToDay() {
        return drinkingNextToDay;
    }

    public void setDrinkingNextToDay(String drinkingNextToDay) {
        this.drinkingNextToDay = drinkingNextToDay;
    }

    public String getActivityToWeek() {
        return activityToWeek;
    }

    public void setActivityToWeek(String activityToWeek) {
        this.activityToWeek = activityToWeek;
    }

    public String getActivityToTime() {
        return activityToTime;
    }

    public void setActivityToTime(String activityToTime) {
        this.activityToTime = activityToTime;
    }

    public String getActivityNextToWeek() {
        return activityNextToWeek;
    }

    public void setActivityNextToWeek(String activityNextToWeek) {
        this.activityNextToWeek = activityNextToWeek;
    }

    public String getActivityNextToTime() {
        return activityNextToTime;
    }

    public void setActivityNextToTime(String activityNextToTime) {
        this.activityNextToTime = activityNextToTime;
    }

    public String getBloodPressureTwo() {
        return bloodPressureTwo;
    }

    public void setBloodPressureTwo(String bloodPressureTwo) {
        this.bloodPressureTwo = bloodPressureTwo;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public String getFollowNum() {
        return followNum;
    }

    public void setFollowNum(String followNum) {
        this.followNum = followNum;
    }

    public String getNextWeight() {
        return nextWeight;
    }

    public void setNextWeight(String nextWeight) {
        this.nextWeight = nextWeight;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }
}
