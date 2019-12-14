package com.ylz.packcommon.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 功能说明：汉字转换拼音工具类
 * Created by asus on 2017/7/6.
 */
public class PinyinUtil {

    // 将汉字转换为拼音的全拼的大写，非汉字的原样输出
    public static StringBuilder getQuanPin(String str) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char s = str.charAt(i);
            /**
             * PinyinHelper.toHanyuPinyinStringArray说明：
             * 1、参数传中文，返回值是这个汉字的小写拼音+这个汉字是几声。比如：参数传 "汉 "，return的String[ ] 就是 [han4]
             * 2、当传字母时，返回值是null。
             * 注意：toHanyuPinyinStringArray接收的参数是char，意思就是说一次只能转换一个，
             * 比如“美”是string，toHanyuPinyinStringArray不能直接接收，
             * 每次只能传一个，返回的那个String数组里肯定只有一个元素。
             */
            String[] s1 = PinyinHelper.toHanyuPinyinStringArray(s);

            if (null == s1) {
                sb.append(s);
            } else {
                String up = s1[0].substring(0, s1[0].length() - 1);
                up = up.toUpperCase();
                sb.append(up);
            }
        }
        return sb;
    }


    /**
     * 得到 全拼
     *
     * @param src
     * @return
     */
    public static String getPingYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += java.lang.Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    /**
     * 得到中文首字母
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str) {

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param cnStr
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            // System.out.println(Integer.toHexString(bGBK[i]&0xff));
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

    /**
     * 判断是否全文字
     * @param name
     * @return
     */
    public static boolean checkname(String name) {
        int n = 0;
        for(int i = 0; i < name.length(); i++) {
            n = (int)name.charAt(i);
            if(!(19968 <= n && n <40869)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        String result = PinyinUtil.getQuanPin("筼筜").toString();
        System.out.println("筼筜=="+result.toLowerCase());
        String result1 = PinyinUtil.getQuanPin("重量").toString();
        System.out.println("重量=="+result1.toLowerCase());

        String cnStr = "筼筜";
        System.out.println(getPingYin(cnStr));
        System.out.println(getPinYinHeadChar(cnStr));
        String cnStr2 = "重量";
        System.out.println(getPingYin(cnStr2));
        System.out.println(getPinYinHeadChar(cnStr2));
    }
}
