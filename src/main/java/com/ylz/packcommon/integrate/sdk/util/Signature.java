package com.ylz.packcommon.integrate.sdk.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ylz.packcommon.common.util.AESR;
import com.ylz.packcommon.common.util.Base64Utils;
import com.ylz.packcommon.common.util.Md5Util;
import com.ylz.packcommon.integrate.sdk.bean.RequestParams;
import com.ylz.packcommon.integrate.sdk.util.dtkl.DtklUtil;

import java.util.*;

/**
 * Created by asus on 2019/01/28.
 */
public class Signature {

    private static List<String> signature = new ArrayList();

    public Signature() {
    }

    public static Map<String,Object> createSign(RequestParams requestParams, String appSecret)throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sign = createSign(JSON.parseObject(JSON.toJSONString(requestParams)), appSecret);
        String code = DtklUtil.encrypt(requestParams.getAppId(), appSecret, sign);
        map.put("sign",sign);
        map.put("code",code);
        return map;
    }


    private static String createSign(JSONObject jsonObject, String appSecret) throws Exception{
        Map<String, String> signMap = new TreeMap();
        Set<Map.Entry<String, Object>> jsonEntrys = jsonObject.entrySet();
        Iterator iterator = jsonEntrys.iterator();
        if(iterator != null){
            while(iterator.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)iterator.next();
                if(!Signature.isEmpty(entry.getValue()) && !signature.contains(entry.getKey())) {
                    signMap.put(entry.getKey(), getValue(entry.getValue()));
                }
            }
        }
        String sign = getSign(signMap, appSecret);
        return sign;
    }

    public static String getSign(Map<String, String> map, String key) throws Exception{
        if(map == null) {
            return "";
        } else {
            List<String> list = new ArrayList();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry)iterator.next();
                if(isNotEmpty(getObjString(entry.getValue()))) {
                    list.add((String)entry.getKey() + "=" + (String)entry.getValue() + "&");
                }
            }
            int size = list.size();
            String[] arrayToSort = (String[])list.toArray(new String[size]);
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < size; ++i) {
                sb.append(arrayToSort[i]);
            }
            String result = sb.toString();
            result = result + "key=" + key;
            byte[] encryptResult = AESR.encrypt(result, key,16);
            result = Base64Utils.encode(encryptResult);
            result = Md5Util.MD5(result).toUpperCase();
            return result;
        }
    }



    private static String getValue(Object value) {
        return value instanceof String ?getObjString(value):treeJsonParam(value);
    }

    private static String treeJsonParam(Object value) {
        String jsoNParam = null;
        TreeMap jsonMap;
        Iterator iterator;
        Map.Entry nestedEntry;
        if(value instanceof Map) {
            jsonMap = new TreeMap();
            Map<?, ?> nestedMap = (Map)value;
            iterator = nestedMap.entrySet().iterator();

            while(iterator.hasNext()) {
                nestedEntry = (Map.Entry)iterator.next();
                jsonMap.put((String)nestedEntry.getKey(), nestedEntry.getValue());
            }

            jsoNParam = JSONObject.toJSONString(treeParams(jsonMap));
        } else if(value instanceof JSONObject) {
            jsonMap = new TreeMap();
            JSONObject nestedMap = (JSONObject)value;
            iterator = nestedMap.entrySet().iterator();

            while(iterator.hasNext()) {
                nestedEntry = (Map.Entry)iterator.next();
                jsonMap.put((String)nestedEntry.getKey(), nestedEntry.getValue());
            }
            jsoNParam = JSONObject.toJSONString(treeParams(jsonMap));
        } else if(value instanceof ArrayList) {
            ArrayList<?> ar = (ArrayList)value;
            jsoNParam = JSONObject.toJSONString(treeList(ar));
        } else if(value instanceof JSONArray) {
            JSONArray jarr = (JSONArray)value;
            jsoNParam = JSONObject.toJSONString(treeJsonArray(jarr));
        } else if(value != null) {
            jsoNParam = value.toString();
        }
        return jsoNParam;
    }

    private static Map<String, Object> treeParams(Map<String, Object> params) {
        if(params == null) {
            return new TreeMap();
        } else {
            Map<String, Object> treeParams = new TreeMap();
            Iterator iterator = params.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)iterator.next();
                String key = (String)entry.getKey();
                Object value = entry.getValue();
                TreeMap treeNestedMap;
                Iterator iteratorMap;
                Map.Entry nestedEntry;
                if(value instanceof Map) {
                    treeNestedMap = new TreeMap();
                    Map<?, ?> nestedMap = (Map)value;
                    iteratorMap = nestedMap.entrySet().iterator();
                    while(iteratorMap.hasNext()) {
                        nestedEntry = (Map.Entry)iteratorMap.next();
                        treeNestedMap.put((String)nestedEntry.getKey(), nestedEntry.getValue());
                    }
                    treeParams.put(key, treeParams(treeNestedMap));
                } else if(!(value instanceof JSONObject)) {
                    if(value instanceof ArrayList) {
                        ArrayList<?> ar = (ArrayList)value;
                        treeParams.put(key, treeList(ar));
                    } else if(value instanceof JSONArray) {
                        JSONArray ar = (JSONArray)value;
                        treeParams.put(key, treeJsonArray(ar));
                    } else if(!"".equals(value) && value != null) {
                        treeParams.put(key, value.toString());
                    }
                } else {
                    treeNestedMap = new TreeMap();
                    JSONObject nestedMap = (JSONObject)value;
                    iteratorMap = nestedMap.entrySet().iterator();
                    while(iteratorMap.hasNext()) {
                        nestedEntry = (Map.Entry)iteratorMap.next();
                        treeNestedMap.put(key, nestedEntry.getValue());
                    }
                    treeParams.put(key, treeParams(treeNestedMap));
                }
            }
            return treeParams;
        }
    }

    private static JSONArray treeList(ArrayList<?> list) {
        if(list != null && list.size() != 0) {
            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < list.size(); ++i) {
                jsonArray.add(i, list.get(i));
            }
            return treeJsonArray(jsonArray);
        } else {
            return null;
        }
    }

    private static JSONArray treeJsonArray(JSONArray array) {
        if(array != null && array.size() != 0) {
            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < array.size(); ++i) {
                Object value = array.get(i);
                TreeMap treeNestedMap = null;
                Iterator iterator = null;
                Map.Entry nestedEntry = null;
                if(value instanceof Map) {
                    treeNestedMap = new TreeMap();
                    Map<?, ?> nestedMap = (Map)value;
                    iterator = nestedMap.entrySet().iterator();
                    while(iterator.hasNext()) {
                        nestedEntry = (Map.Entry)iterator.next();
                        treeNestedMap.put((String)nestedEntry.getKey(), nestedEntry.getValue());
                    }
                    jsonArray.add(i, treeParams(treeNestedMap));
                } else if(!(value instanceof JSONObject)) {
                    if(value instanceof ArrayList) {
                        ArrayList<?> ar = (ArrayList)value;
                        jsonArray.add(i, treeList(ar));
                    } else if(value instanceof JSONArray) {
                        JSONArray ar = (JSONArray)value;
                        jsonArray.add(i, treeJsonArray(ar));
                    } else if(value != null && !"".equals(value)) {
                        jsonArray.add(i, value.toString());
                    }
                } else {
                    treeNestedMap = new TreeMap();
                    JSONObject nestedMap = (JSONObject)value;
                    iterator = nestedMap.entrySet().iterator();
                    while(iterator.hasNext()) {
                        nestedEntry = (Map.Entry)iterator.next();
                        treeNestedMap.put((String)nestedEntry.getKey(), nestedEntry.getValue());
                    }
                    jsonArray.add(i, treeParams(treeNestedMap));
                }
            }
            return jsonArray;
        } else {
            return null;
        }
    }

    private static String getObjString(Object object) {
        return object == null?"":(String)object;
    }
    private static boolean isEmpty(String[] string) {
        return string == null || string.length == 0;
    }

    private static boolean isEmpty(String string) {
        return string == null || "".equals(string.trim()) || "null".equals(string.trim());
    }

    private static boolean isEmpty(Object o) {
        return o == null || "".equals(o);
    }

    private static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }
}
