package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

import java.util.List;

/**团队请求条件
 * Created by zzl on 2017/7/10.
 */
public class AppDrTeamQvo  extends CommConditionVo {
    private String id;//团队id
    private String hospId;//机构id
    private String hospName;//机构名称
    private String drId;//医生id
    private String drName;//医生姓名
    private String drCode;//医生编号
    private String teamName;//团队名称
    private String teamCode;//团队编号
    private Integer teamSort;//团队排序
    private String teamState;//团队有效标志
    private String teamRemarks;//备注
    private String tel;//电话
    private String type;//团队类型
    private String workType;//工作类型
    private String memId;//成员数据主键
    private String teamMemId;//成员医生id（多个用";"隔开）
    private String teamMemName;//成员医生姓名（多个用";"隔开）
    private List<AppDrTeamMemQvo> teamMems;//团队成员信息

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

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrCode() {
        return drCode;
    }

    public void setDrCode(String drCode) {
        this.drCode = drCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public Integer getTeamSort() {
        return teamSort;
    }

    public void setTeamSort(Integer teamSort) {
        this.teamSort = teamSort;
    }

    public String getTeamState() {
        return teamState;
    }

    public void setTeamState(String teamState) {
        this.teamState = teamState;
    }

    public String getTeamRemarks() {
        return teamRemarks;
    }

    public void setTeamRemarks(String teamRemarks) {
        this.teamRemarks = teamRemarks;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getTeamMemId() {
        return teamMemId;
    }

    public void setTeamMemId(String teamMemId) {
        this.teamMemId = teamMemId;
    }

    public String getTeamMemName() {
        return teamMemName;
    }

    public void setTeamMemName(String teamMemName) {
        this.teamMemName = teamMemName;
    }

    public List<AppDrTeamMemQvo> getTeamMems() {
        return teamMems;
    }

    public void setTeamMems(List<AppDrTeamMemQvo> teamMems) {
        this.teamMems = teamMems;
    }
}
