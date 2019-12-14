package com.ylz.bizDo.performance.vo;

import java.util.List;

/**
 * Created by hzk on 2017/7/1.
 */
public class TeamLsitVo {
    private String teamName;//团队名称
    private List<String> teamDate;//日期
    private List<String> teamWork;//工作进度完成率
    private List<String> teamFollow;//随访完成率
    private List<String> teamMeddle;//健康干预率

    private String teamSort;//团队排名
    private String teamCount;//总量
    private String teamWcount;//未完成率
    private String teamYCount;//已完成率

    public String getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(String teamCount) {
        this.teamCount = teamCount;
    }

    public String getTeamWcount() {
        return teamWcount;
    }

    public void setTeamWcount(String teamWcount) {
        this.teamWcount = teamWcount;
    }

    public String getTeamYCount() {
        return teamYCount;
    }

    public void setTeamYCount(String teamYCount) {
        this.teamYCount = teamYCount;
    }

    public String getTeamSort() {
        return teamSort;
    }

    public void setTeamSort(String teamSort) {
        this.teamSort = teamSort;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<String> getTeamDate() {
        return teamDate;
    }

    public void setTeamDate(List<String> teamDate) {
        this.teamDate = teamDate;
    }

    public List<String> getTeamWork() {
        return teamWork;
    }

    public void setTeamWork(List<String> teamWork) {
        this.teamWork = teamWork;
    }

    public List<String> getTeamFollow() {
        return teamFollow;
    }

    public void setTeamFollow(List<String> teamFollow) {
        this.teamFollow = teamFollow;
    }

    public List<String> getTeamMeddle() {
        return teamMeddle;
    }

    public void setTeamMeddle(List<String> teamMeddle) {
        this.teamMeddle = teamMeddle;
    }
}
