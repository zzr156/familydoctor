package com.ylz.packcommon.common.util;

/**
 * Created by hzk on 2017/6/27.
 */
public class AreaUtils {
    public static String getAreaCode(String areacode,String level){
        int lev=Integer.valueOf(level);
        if(lev==1){
            return areacode.substring(0,2);
        }
        if (lev==2){
            return areacode.substring(0,4);
        }
        if (lev==3){
            return areacode.substring(0,6);
        }
        if (lev==4){
            return areacode.substring(0,9);
        }
        return areacode;
    }

    public static String getAreaCode(String areacode){
        for(int i=areacode.length();i>1;i--){
            if(areacode.substring(i-1,i).equals("0")){
                areacode=areacode.substring(0,i-1);
            }else {break;}
        }
        return areacode;
    }

    public static String getAreaCodePrefix(String areacode) {
        // 如果长度没有12位则位数用0补足12位为止
        if (areacode.length() != 12) {
            for (int i = areacode.length(); i < 12; i++) {
                areacode = areacode + "0";
            }
        }
        // 截取
        if ("000".equals(areacode.substring(9, 12))) {
            areacode = areacode.substring(0, 9);
            if ("000".equals(areacode.substring(6, 9))) {
                areacode = areacode.substring(0, 6);
                if ("00".equals(areacode.substring(4, 6))) {
                    areacode = areacode.substring(0, 4);
                    if ("00".equals(areacode.substring(2, 4))) {
                        areacode = areacode.substring(0, 2);
                    }
                }
            }
        }
        return areacode;
    }

    public static String getAreaCodeAll(String areacode,String level){
        int lev=Integer.valueOf(level);
        if(lev==1){
            return areacode.substring(0,2)+"0000000000";
        }
        if (lev==2){
            return areacode.substring(0,4)+"00000000";
        }
        if (lev==3){
            return areacode.substring(0,6)+"000000";
        }
        if(lev==4){
            return areacode.substring(0,9)+"000";
        }
        return areacode;
    }

    public static void main(String[] args) {
        String url = AreaUtils.getAreaCode("350200","2");
        System.out.println(url);
    }

}
