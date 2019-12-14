package com.ylz.bizDo.cd.entity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * Created by asus on 2017/08/14.
 */
public class AddressTreeEntity {
    private String id;
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsParent() throws Exception {
        boolean result = true;
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            int num = dao.getCdAddressDao().findByupIdCount(this.getId());
            if(num == 0){
                result = false;
            }
        }

        return  result;
    }

//    public boolean isOpen(){
//        return true;
//    }

}
