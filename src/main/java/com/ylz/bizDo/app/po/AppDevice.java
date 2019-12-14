package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**设备表
 * Created by zzl on 2017/6/17.
 */
@Entity
@Table(name = "APP_DEVICE")
public class AppDevice extends BasePO {
    // Fields
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "DEV_NAME", length =50 )
    private String devName;//设备名称
    @Column(name = "DEV_IMAGE_URL", length =200 )
    private String devImageUrl;//设备图片
    @Column(name = "DEV_CREATE_ID", length =36 )
    private String devCreateId; //创建人id
    @Column(name = "DEV_CREATE_TIME")
    private Calendar devCreateTime;//创建时间
    @Column(name = "DEV_TYPE", length =10 )
    private String devType;//设备类型
    @Column(name = "DEV_FACTORY_MODEL", length = 50)
    private String devFactoryModel;//厂家型号


    public void setId(String id) {
        this.id = id;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public void setDevImageUrl(String devImageUrl) {
        this.devImageUrl = devImageUrl;
    }

    public void setDevCreateId(String devCreateId) {
        this.devCreateId = devCreateId;
    }

    public void setDevCreateTime(Calendar devCreateTime) {
        this.devCreateTime = devCreateTime;
    }

    public String getId() {
        return id;
    }

    public String getDevName() {
        return devName;
    }

    public String getDevImageUrl() {
        return devImageUrl;
    }

    public String getDevCreateId() {
        return devCreateId;
    }

    public Calendar getDevCreateTime() {
        return devCreateTime;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getDevFactoryModel() {
        if(StringUtils.isBlank(this.devFactoryModel)){
            return "";
        }
        return devFactoryModel;
    }

    public void setDevFactoryModel(String devFactoryModel) {
        this.devFactoryModel = devFactoryModel;
    }

    /**
     * 处理时间
     * @return
     */
    public String getStrDevCreateTime(){
        if(this.getDevCreateTime()!=null){
            return ExtendDate.getYMD(this.getDevCreateTime());
        }else{
            return "";
        }
    }

    /**
     * 创建者姓名
     * @return
     */
    public String getDevCreateName(){
        SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
        if(StringUtils.isNotBlank(this.getDevCreateId())){
            CdUser user = (CdUser) dao.getServiceDo().find(CdUser.class,this.getDevCreateId());
            if(user!=null){
                if(StringUtils.isNotBlank(user.getUserName())){
                    return user.getUserName();
                }
            }
        }
        return "";
    }

    /**
     * 设备类型名称
     * @return
     */
    public String getDevTypeName() throws Exception{
        SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
        if(StringUtils.isNotBlank(this.getDevType())){
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_DEVTYPE, this.getDevType());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return  "";
    }
}
