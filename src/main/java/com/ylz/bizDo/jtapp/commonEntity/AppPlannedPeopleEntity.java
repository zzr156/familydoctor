package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by asus on 2017-07-16.
 */
public class AppPlannedPeopleEntity {
    private String year;//年份
    private String month;//月
    private String ouid;//区划代码
    private int reg_family;//户籍地户数
    private int reg;//户籍人口数
    private int reg_child;//户籍儿童数
    private int reg_elder;//户籍老年人口数
    private int out;//流出人口数
    private int out_child;//流出儿童人口数
    private int out_elder;//流出老年人口数
    private int in;//流入人口数
    private int in_child;//流入儿童人口数
    private int in_elder;//流入老年人口数
    private int resident;//常住人口数
    private int resident_child;//常住儿童人口数
    private int resident_elder;//常住老年人口数


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
    }

    public int getReg_family() {
        return reg_family;
    }

    public void setReg_family(int reg_family) {
        this.reg_family = reg_family;
    }

    public int getReg() {
        return reg;
    }

    public void setReg(int reg) {
        this.reg = reg;
    }

    public int getReg_child() {
        return reg_child;
    }

    public void setReg_child(int reg_child) {
        this.reg_child = reg_child;
    }

    public int getReg_elder() {
        return reg_elder;
    }

    public void setReg_elder(int reg_elder) {
        this.reg_elder = reg_elder;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public int getOut_child() {
        return out_child;
    }

    public void setOut_child(int out_child) {
        this.out_child = out_child;
    }

    public int getOut_elder() {
        return out_elder;
    }

    public void setOut_elder(int out_elder) {
        this.out_elder = out_elder;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getIn_child() {
        return in_child;
    }

    public void setIn_child(int in_child) {
        this.in_child = in_child;
    }

    public int getIn_elder() {
        return in_elder;
    }

    public void setIn_elder(int in_elder) {
        this.in_elder = in_elder;
    }

    public int getResident() {
        return resident;
    }

    public void setResident(int resident) {
        this.resident = resident;
    }

    public int getResident_child() {
        return resident_child;
    }

    public void setResident_child(int resident_child) {
        this.resident_child = resident_child;
    }

    public int getResident_elder() {
        return resident_elder;
    }

    public void setResident_elder(int resident_elder) {
        this.resident_elder = resident_elder;
    }
}
