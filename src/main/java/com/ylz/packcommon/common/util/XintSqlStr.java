package com.ylz.packcommon.common.util;

import com.ylz.packcommon.common.comEnum.LabelManageType;

/**
 * @author robin
 */
public class XintSqlStr {
    //服务人群(普通人群,儿童(0-6岁),孕产妇,老年人,高血压 ,糖尿病,严重精神障碍,结核病,残疾人,未知)
    private final static String xstrGroupSrc = "0000000000";
    //疾病类型(高血压,糖尿病,冠心病,慢性阻塞性肺疾病,恶性肿瘤,脑卒中,严重精神障碍,结核病,肝炎,其他法定传染病,职业病,其他,未知)
    private final static String xstrDiseaseSrc = "0000000000000";
    //经济类型(一般人口,建档立卡贫困人口,低保户,特困户（五保户）,计生特殊家庭)
    private final static String xstrEconomicsSrc = "00000";

    private final static String xstrSrc = "00000000000000000000";

    private int rtni = 0;

    public static XintSqlStr getInstance(int i) {
        XintSqlStr v = new XintSqlStr();
        v.rtni = i;
        return v;
    }

    /**
     * 加1到第几位
     * @param idx
     * @return
     */
    public XintSqlStr addToIdx(int idx) {
        String str = String.valueOf(Integer.toBinaryString(rtni));
        if (str.length() < xstrSrc.length())
            str = String.format("%0" + (xstrSrc.length() - str.length()) + "d", 0) + str;
        StringBuilder xml = new StringBuilder(str.substring(0, idx - 1));
        xml.append("1").append(str.substring(idx));

        this.rtni = Integer.parseInt(xml.toString(), 2);
        return this;
    }

    public int toXint() {
        return rtni;
    }

    public String toXintStr() {
        return String.valueOf(Integer.toBinaryString(rtni));
    }

    /**
     * 生成第几位的条件
     * @param idx
     * @return
     */
    public static int getIdxQuery(int idx) {

        int n = xstrSrc.length() - idx;
        String s = String.format("%" + ("0" + idx) + "d", 1);
        if (idx < xstrSrc.length())
            s += String.format("%1$0" + (n == 0 ? "" : n) + "d", 0);


        return Integer.parseInt(s, 2);
    }

    public static void main(String[] args) {
        XintSqlStr vo = new XintSqlStr();
//       String i= vo.addToIdx(1).addToIdx(2).addToIdx(3).toXintStr();
        for (int i=1;i<=20;i++)
        {
            vo.addToIdx(i);
        }
        System.out.println(vo.toXint()+" "+vo.toXintStr());
        int j= vo.addToIdx(1).addToIdx(2).addToIdx(3).toXint();
        int z = getIdxQuery(4);

        System.out.println(j);

        System.out.println((j^z)<j);


    }

}