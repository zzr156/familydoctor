package com.ylz.packcommon.common.util;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hzk
 * Date: 13-1-16
 * Time: 上午6:00
 * To change this template use File | Settings | File Templates.
 */
public class QtUtils {
    //转Unicode码
    public String chinaToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }
    /**
     * 执行某个Field的getField方法
     * @param owner 类
     * @param fieldName 类的属性名称
     * @param args 参数，默认为null
     * @return
     */
    private Object invokeMethod(Object owner, String fieldName, Object[] args)
    {
        Class<? extends Object> ownerClass = owner.getClass();
        //fieldName -> FieldName
        String methodName = fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
        Method method = null;
        try
        {
            method = ownerClass.getMethod("get" + methodName);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return "";
        }
        try
        {
            return method.invoke(owner);
        }
        catch (Exception e)
        {
            return "";
        }
    }

    //根据类名，类属性名取属性值
    private  String getFieldValue(Object owner, String fieldName)
    {
        return invokeMethod(owner, fieldName,null).toString();
    }

    /**
     * 排序
     * @param ls
     * @return
     */
    public List listSort(List ls){
//        Collections.sort(ls, new Comparator<TeamLsitVo>() {
//            public int compare(TeamLsitVo o1, TeamLsitVo o2) {
//                if (Double.valueOf(o1.getTeamYCount()) < Double.valueOf(o2.getTeamYCount())) {
//                    return 1;
//                }
//                if (o1.getTeamYCount() == o2.getTeamYCount()) {
//                    return 0;
//                }
//                return -1;
//            }});
        return null;
    }
}
