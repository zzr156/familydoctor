package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**团队返回数据-
 * Created by zzl on 2017/7/10.
 */
public class AppDrTeamEntity  {
    private String id;//团队主键
    private String teamName;//团队名称
    private String time;//创建时间
    private String tel;//团队电话
    private String drId;//队长id
    private String drName;//队长姓名
    private String remarks;//备注
    private String type;//团队类型
    private String typeName;//团队类型名称
    private String teamCode;//团队编号
    private String workType;//工作类型
    private String workTypeName;//工作类型名称
    private String memState;//医生在此团队的角色
    private String teamEaseGroupId;//环信团队主键
    private String teamEaseRoomId;//环信聊天室主键
    private String teamEaseRoomName;//环信聊天室名称
    private String teamEaseGroupName;//环信聊天室名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName)  throws Exception {
        if(StringUtils.isNotBlank(this.getType())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TEAMTYPE, this.getType());
            if(value!=null) {
                typeName = value.getCodeTitle();
            }
        }
        this.typeName = typeName;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) throws Exception {
        if(StringUtils.isNotBlank(this.getWorkType())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKTYPE, this.getWorkType());
            if(value!=null) {
                workTypeName = value.getCodeTitle();
            }
        }
        this.workTypeName = workTypeName;
    }

    public String getMemState() {
        return memState;
    }

    public void setMemState(String memState) {
        this.memState = memState;
    }

    public String getTeamEaseGroupId() {
        return teamEaseGroupId;
    }

    public void setTeamEaseGroupId(String teamEaseGroupId) {
        this.teamEaseGroupId = teamEaseGroupId;
    }

    public String getTeamEaseRoomId() {
        return teamEaseRoomId;
    }

    public void setTeamEaseRoomId(String teamEaseRoomId) {
        this.teamEaseRoomId = teamEaseRoomId;
    }

    public String getTeamEaseRoomName() {
        return teamEaseRoomName;
    }

    public void setTeamEaseRoomName(String teamEaseRoomName) {
        this.teamEaseRoomName = teamEaseRoomName;
    }

    public String getTeamEaseGroupName() {
        return teamEaseGroupName;
    }

    public void setTeamEaseGroupName(String teamEaseGroupName) {
        this.teamEaseGroupName = teamEaseGroupName;
    }
}
