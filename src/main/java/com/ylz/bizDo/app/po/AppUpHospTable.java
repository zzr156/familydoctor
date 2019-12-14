package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by zzl on 2017/12/19.
 */
@Entity
@Table(name = "APP_UP_HOSP_TABLE")
public class AppUpHospTable extends BasePO{

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "UP_ID",length = 36)
    private String upId;//本医院id
    @Column(name = "UP_HOSP_ID",length = 36)
    private String upHospId;//上级医院id
    @Column(name = "UP_STATE",length = 10)
    private String upState="0";//状态 0禁用 1启用

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpId() {
        return upId;
    }

    public void setUpId(String upId) {
        this.upId = upId;
    }

    public String getUpHospId() {
        return upHospId;
    }

    public void setUpHospId(String upHospId) {
        this.upHospId = upHospId;
    }

    public String getUpState() {
        return upState;
    }

    public void setUpState(String upState) {
        this.upState = upState;
    }

    /**
     * 获取本医院名称
     * @return
     */
    public String getHospName(){
        if(StringUtils.isNotBlank(this.getUpId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getUpId());
            if(dept!=null){
                return dept.getHospName();
            }
        }
        return "";
    }

    public String getUpHospName(){
        if(StringUtils.isNotBlank(this.getUpHospId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getUpHospId());
            if(dept!=null){
                return dept.getHospName();
            }
        }
        return "";
    }
}
