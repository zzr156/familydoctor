package com.ylz.packcommon.common.util;


/**
 * Created by hzk on 2017/1/12.
 */
public class CheckPswMeter {
    /**
     * 密码强度
     *
     * @return Z = 字母 S = 数字 T = 特殊字符
     */
    public String checkPassword(String passwordStr) {
        String regexZ = "\\d*";
        String regexS = "[a-zA-Z]+";
        String regexT = "\\W+$";
        String regexZT = "\\D*";
        String regexST = "[\\d\\W]*";
        String regexZS = "\\w*";
        String regexZST = "[\\w\\W]*";
        if (passwordStr.length()<6) {
            return "弱";
        }
        if (passwordStr.matches(regexZ)) {
            return "弱";
        }
        if (passwordStr.matches(regexS)) {
            return "弱";
        }
        if (passwordStr.matches(regexT)) {
            return "弱";
        }
        if (passwordStr.matches(regexZT)) {
            return "中";
        }
        if (passwordStr.matches(regexST)) {
            return "中";
        }
        if (passwordStr.matches(regexZS)) {
            return "中";
        }
        if (passwordStr.matches(regexZST)) {
            return "强";
        }
        return passwordStr;
    }

    public static void main(String[] args) {
        CheckPswMeter p=new CheckPswMeter();
        System.out.println(p.checkPassword("123a21fff#"));
    }

}
