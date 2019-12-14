package com.ylz.bizDo.assessment.config;

import java.util.HashMap;
import java.util.Map;

public class InterfaceUrl {

    public static final String electricRecordUrl = "/sqyljk/rest/jtyssf/AccessController/findElectronicArchives";
    public static final String visitRecordUrl = "/sqyljk/rest/jtyssf/AccessController/execute";
    public static final String password = "jwylzdoctoryw(*&^%";
    public static Map<String, String> map = new HashMap<>();

    static {
        map.put("12", "findElectronicArchives");
        map.put("21", "findVisitRecords");
        map.put("41", "findHealthfile");
        map.put("42", "findHealthfile");
        map.put("43", "findHealthfile");
        map.put("45", "findHealthfile");
        map.put("47", "findHealthfile");
        map.put("49", "findHealthfile");
    }

    private static class InterfaceUrlInner {
        private static InterfaceUrl interfaceUrl = new InterfaceUrl();
    }

    private static InterfaceUrl getInstance() {
        return InterfaceUrlInner.interfaceUrl;
    }
}
