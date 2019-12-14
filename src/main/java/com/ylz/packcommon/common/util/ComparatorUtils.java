package com.ylz.packcommon.common.util;

import com.ylz.bizDo.jtapp.drEntity.AppSignFormListEntity;
import com.ylz.bizDo.mangecount.entity.ManageTeamCountEntity;
import com.ylz.bizDo.performance.vo.TeamLsitVo;
import com.ylz.packcommon.common.comEnum.SignFormType;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by lintingjie on 2017/7/18.
 */
public class ComparatorUtils {
    public static java.util.Comparator getComparator() {
        return new java.util.Comparator() {

            public int compare(Object o1, Object o2) {
                if (o1 instanceof Map) {
                    return compare((Map<String,Object>) o1, (Map<String,Object>) o2);
                } else if (o1 instanceof TeamLsitVo) {
                    return compare((TeamLsitVo) o1, (TeamLsitVo) o2);
                } else {
                    System.err.println("未找到合适的比较器");
                    return 1;
                }
            }
            public int compare(Map<String,Object> o1, Map<String,Object> o2) {
                if(o1.get("areaRank") != null){
                    return ((BigDecimal)o2.get("areaRank")).compareTo((BigDecimal)o1.get("areaRank"));
                }
                if(o1.get("qywcl") != null){
                    if (Double.valueOf(o1.get("qywcl").toString()) < Double.valueOf(o2.get("qywcl").toString())) {
                        return 1;
                    }
                    if (Double.valueOf(o1.get("qywcl").toString()) == Double.valueOf(o2.get("qywcl").toString())) {
                        return 0;
                    }
                    return -1;
                }else if(o1.get("yqy") != null){
                    if ((Integer)o1.get("yqy") < (Integer)o2.get("yqy")) {
                        return 1;
                    }
                    if ((Integer)o1.get("yqy") == (Integer)o2.get("yqy")) {
                        return 0;
                    }
                    return -1;
                }else if(o1.get("scout") != null){
                    if (Double.valueOf(o1.get("scout").toString()) < Double.valueOf(o2.get("scout").toString())) {
                        return 0;
                    }
                    if (Double.valueOf(o1.get("scout").toString()) == Double.valueOf(o2.get("scout").toString())) {
                        return 0;
                    }
                    return -1;
                }else if(o1.get("count") != null){
                    if ((Integer)o1.get("count") < (Integer)o2.get("count")) {
                        return 1;
                    }
                    if ((Integer)o1.get("count") == (Integer)o2.get("count")) {
                        return 0;
                    }
                    return -1;
                }
                return  -1;
            }
            public int compare(TeamLsitVo o1, TeamLsitVo o2) {
                if (Double.valueOf(o1.getTeamYCount()) < Double.valueOf(o2.getTeamYCount())) {
                    return 1;
                }
                if (o1.getTeamYCount() == o2.getTeamYCount()) {
                    return 0;
                }
                return -1;
            }
        };
    }

    public static java.util.Comparator getComparatorMotoe() {
        return new java.util.Comparator() {

            public int compare(Object o1, Object o2) {
                if (o1 instanceof Map) {
                    return compare((Map<String,Object>) o1, (Map<String,Object>) o2);
                } else if (o1 instanceof TeamLsitVo) {
                    return compare((TeamLsitVo) o1, (TeamLsitVo) o2);
                } else {
                    System.err.println("未找到合适的比较器");
                    return 1;
                }
            }
            public int compare(Map<String,Object> o1, Map<String,Object> o2) {
                if(o1.get("yqy") != null){
                    if ((Integer)o1.get("yqy") < (Integer)o2.get("yqy")) {
                        return 1;
                    }
                    if ((Integer)o1.get("yqy") == (Integer)o2.get("yqy")) {
                        return 0;
                    }
                    return -1;
                }
                return  -1;
            }
            public int compare(TeamLsitVo o1, TeamLsitVo o2) {
                if (Double.valueOf(o1.getTeamYCount()) < Double.valueOf(o2.getTeamYCount())) {
                    return 1;
                }
                if (o1.getTeamYCount() == o2.getTeamYCount()) {
                    return 0;
                }
                return -1;
            }
        };
    }
    public static java.util.Comparator getComparatorSign(){
        return new java.util.Comparator() {
            public int compare(Object o1, Object o2) {
                if (o1 instanceof AppSignFormListEntity) {
                    return compare((AppSignFormListEntity) o1, (AppSignFormListEntity) o2);
                } else {
                    System.err.println("未找到合适的比较器");
                    return 1;
                }
            }
            public int compare(AppSignFormListEntity o1,AppSignFormListEntity o2) {
                if(o1.getSignState() != null){
                    if (SignFormType.DQY.getValue().equals(o1.getSignState())||SignFormType.YUQY.getValue().equals(o1.getSignState())||SignFormType.YQY.equals(o1.getSignState())) {
                        return -1;
                    }else if(SignFormType.DQY.getValue().equals(o1.getSignState())||SignFormType.YUQY.getValue().equals(o2.getSignState())||SignFormType.YQY.equals(o2.getSignState())){
                        return 1;
                    }else{
                        Calendar o1cal = ExtendDate.getCalendar(o1.getSignDate());
                        Calendar o2cal = ExtendDate.getCalendar(o2.getSignDate());
                        int num = o1cal.compareTo(o2cal);
                        if(num<1){
                            return 1;
                        }else if(num==0){
                            return 0;
                        }else{
                            return -1;
                        }
                    }
                }
                return  -1;
            }

        };
    }

    public static java.util.Comparator getComparatorList() {
        return new java.util.Comparator() {

            public int compare(Object o1, Object o2) {
                if (o1 instanceof Map) {
                    return compare((Map<String,Object>) o1, (Map<String,Object>) o2);
                } else if (o1 instanceof ManageTeamCountEntity) {
                    return compare((ManageTeamCountEntity) o1, (ManageTeamCountEntity) o2);
                } else {
                    System.err.println("未找到合适的比较器");
                    return 1;
                }
            }
            public int compare(Map<String,Object> o1, Map<String,Object> o2) {
                if(o1.get("yqy") != null){
                    if ((Integer)o1.get("yqy") < (Integer)o2.get("yqy")) {
                        return 1;
                    }
                    if ((Integer)o1.get("yqy") == (Integer)o2.get("yqy")) {
                        return 0;
                    }
                    return -1;
                }
                return  -1;
            }
            public int compare(ManageTeamCountEntity o1, ManageTeamCountEntity o2) {
                if (Integer.parseInt(o1.getRate()) < Integer.parseInt(o2.getRate())) {
                    return 1;
                }
                if (Integer.parseInt(o1.getRate()) == Integer.parseInt(o2.getRate())) {
                    return 0;
                }
                return -1;
            }
        };
    }

}
