package com.ylz.packcommon.common.util;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Created by Administrator on 2016/7/5.
 */
public class MyMathUtil {
    public static double add(double d1, double d2) {// 进行加法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }
    public static double sub(double d1, double d2){// 进行减法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }
    public static double mul(double d1, double d2){// 进行乘法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }
    public static double div(double d1,double d2,int len) {// 进行除法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2,len, ROUND_HALF_UP).doubleValue();
    }
    public static double round(double d,int len) {// 进行四舍五入操作
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量表示进行四舍五入的操作
        return b1.divide(b2, len, ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
//        System.out.println("加法运算：" +
//                MyMathUtil.round(MyMathUtil.add(10.345,
//                        3.333), 1));
//        System.out.println("乘法运算：" +
//                MyMathUtil.round(MyMathUtil.mul(10.345,
//                        3.333), 3));
//        System.out.println("除法运算：" +
//                MyMathUtil.div(16000.0, 12, 1));
//        System.out.println("减法运算：" +
//                MyMathUtil.round(MyMathUtil.sub(10.345,
//                        3.333), 3));
        System.out.print(MyMathUtil.div(70,4,2));
        double height = MyMathUtil.div(70,4,5);//身高
        double weight = Double.parseDouble("50");//体重
        double heightResult = MyMathUtil.mul(height,height);
        double result = MyMathUtil.div(weight,heightResult,2);
        System.out.println(result);
    }
}
