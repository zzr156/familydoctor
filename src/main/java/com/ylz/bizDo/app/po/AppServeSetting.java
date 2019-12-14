package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 服务设置表
 * Created by zzl on 2017/7/21.
 */
@Entity
@Table(name = "APP_SERVE_SETTING")
public class AppServeSetting extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "SER_CREATE_STATE",length = 10)
    private String serCreateState;//创建状态
    @Column(name = "SER_CREATE_USER",length = 100)
    private String serCreateUser;//创建用户
    @Column(name ="SER_OBJECT_VALUE",length = 10)
    private String serObjectValue;//服务人群值
    @Column(name = "SER_OBJECT_TITLE",length = 100)
    private String serObjectTitle;//服务人群名称
    @Column(name = "SER_VALUE",length = 10)
    private String serValue;//服务类型值
    @Column(name = "SER_TITLE",length = 100)
    private String serTitle;//服务类型名称
    @Column(name = "SER_START_STATE",length = 10)
    private String serStartState;//服务启用状态
    @Column(name = "SER_SET_NUM",length = 10)
    private String serSetNum;//次数
    @Column(name = "SER_IMAGE_NAME",length = 100)
    private String serImageName;//图片名称
    @Column(name = "SER_IMAGE_TITLE")
    private String serImageTitle;//图片介绍内容

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerCreateState() {
        return serCreateState;
    }

    public void setSerCreateState(String serCreateState) {
        this.serCreateState = serCreateState;
    }

    public String getSerCreateUser() {
        return serCreateUser;
    }

    public void setSerCreateUser(String serCreateUser) {
        this.serCreateUser = serCreateUser;
    }

    public String getSerObjectValue() {
        return serObjectValue;
    }

    public void setSerObjectValue(String serObjectValue) {
        this.serObjectValue = serObjectValue;
    }

    public String getSerObjectTitle() {
        return serObjectTitle;
    }

    public void setSerObjectTitle(String serObjectTitle) {
        this.serObjectTitle = serObjectTitle;
    }

    public String getSerValue() {
        return serValue;
    }

    public void setSerValue(String serValue) {
        this.serValue = serValue;
    }

    public String getSerTitle() {
        return serTitle;
    }

    public void setSerTitle(String serTitle) {
        this.serTitle = serTitle;
    }

    public String getSerStartState() {
        return serStartState;
    }

    public void setSerStartState(String serStartState) {
        this.serStartState = serStartState;
    }

    public String getSerSetNum() {
        return serSetNum;
    }

    public void setSerSetNum(String serSetNum) {
        this.serSetNum = serSetNum;
    }

    public String getSerImageName() {
        return serImageName;
    }

    public void setSerImageName(String serImageName) {
        this.serImageName = serImageName;
    }

    /**
     * 创建人名称
     * @return
     */
    public String getUserName(){
        if(StringUtils.isNotBlank(this.getSerCreateUser())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdUser user = (CdUser) dao.getServiceDo().find(CdUser.class,this.getSerCreateUser());
            if(user!=null){
                return user.getUserName();
            }

        }
        return "";
    }

    /**
     * 状态名称
     * @return
     */
    public String getStateName() throws Exception{
        if(StringUtils.isNotBlank(this.getSerStartState())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFCOMMON, this.getSerStartState());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getSerImageTitle() {
        return serImageTitle;
    }

    public void setSerImageTitle(String serImageTitle) {
        this.serImageTitle = serImageTitle;
    }


}
