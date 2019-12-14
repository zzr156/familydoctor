package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**居民未及时更新体征数据记录表
 * Created by zzl on 2017/11/3.
 */
@Entity
@Table(name = "APP_SIGNS_WARNING_RECORD")
public class AppSignsWarningRecord extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "SWR_DR_ID",length = 36)
    private String swrDrId;//医生id
    @Column(name = "SWR_USER_ID",length = 36)
    private String swrUserId;//患者id
    @Column(name = "SWR_DIS_TYPE",length = 10)
    private String swrDisType;//疾病类型
    @Column(name = "SWR_COLOR",length = 20)
    private String swrColor;//颜色
    @Column(name = "SWR_DAY_NUM",length = 10)
    private String swrDayNum;//天数
    @Column(name = "SWR_TX_STATE",length = 10)
    private String swrTxState;//提醒状态
    @Column(name = "SWR_CREATE_TIME")
    private Calendar swrCreateTime;//创建时间
    @Column(name = "SWR_TEAM_ID",length = 36)
    private String swrTeamId;//团队id


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSwrDrId() {
        return swrDrId;
    }

    public void setSwrDrId(String swrDrId) {
        this.swrDrId = swrDrId;
    }

    public String getSwrUserId() {
        return swrUserId;
    }

    public void setSwrUserId(String swrUserId) {
        this.swrUserId = swrUserId;
    }

    public String getSwrDisType() {
        return swrDisType;
    }

    public void setSwrDisType(String swrDisType) {
        this.swrDisType = swrDisType;
    }

    public String getSwrColor() {
        return swrColor;
    }

    public void setSwrColor(String swrColor) {
        this.swrColor = swrColor;
    }

    public String getSwrDayNum() {
        return swrDayNum;
    }

    public void setSwrDayNum(String swrDayNum) {
        this.swrDayNum = swrDayNum;
    }

    public String getSwrTxState() {
        return swrTxState;
    }

    public void setSwrTxState(String swrTxState) {
        this.swrTxState = swrTxState;
    }

    public Calendar getSwrCreateTime() {
        return swrCreateTime;
    }

    public void setSwrCreateTime(Calendar swrCreateTime) {
        this.swrCreateTime = swrCreateTime;
    }

    public String getDisTypeName() throws Exception {
        if(StringUtils.isNotBlank(this.getSwrDisType())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppLabelManage labelManage = dao.getAppLabelManageDao().findLabelByValue("2",this.getSwrDisType());
            if(labelManage!=null){
                return labelManage.getLabelTitle();
            }
        }
        return "";
    }

    public String getSwrTeamId() {
        return swrTeamId;
    }

    public void setSwrTeamId(String swrTeamId) {
        this.swrTeamId = swrTeamId;
    }


    /**
     * 获取医生姓名
     * @return
     */
    public String getDrName(){
        if(StringUtils.isNotBlank(this.getSwrDrId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getSwrDrId());
            if(drUser!=null){
                return  drUser.getDrName();
            }
        }
        return "";
    }
    /**
     * 获取医生在团队的类型
     * @return
     */
    public String getDrWorkName() throws Exception {
        if(StringUtils.isNotBlank(this.getSwrTeamId())&&StringUtils.isNotBlank(this.getSwrDrId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppTeamMember team = dao.getAppTeamMemberDao().findMemByDrId(this.getSwrDrId(),this.getSwrTeamId());
            if(team!=null){
                return team.getMemWorkTypeName();
            }
        }
        return "";
    }
}
