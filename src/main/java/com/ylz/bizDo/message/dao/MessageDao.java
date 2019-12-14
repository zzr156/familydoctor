package com.ylz.bizDo.message.dao;

import com.ylz.bizDo.message.vo.MessageDrInfo;
import com.ylz.bizDo.message.vo.MessagePatientInfo;
import com.ylz.bizDo.message.vo.MessageQvo;

import java.util.List;

/**
 * Created by hzk on 2017/7/4.
 */
public interface MessageDao {
    /**
     * 患者根据用户id查询 团队所有医生
     * @param qvo patientId 患者id name 姓名 模糊查询
     * @return
     */
    public List<MessageDrInfo> findDrListByPatientId(MessageQvo qvo) throws Exception;

    /**
     * 医生 用团队id查询 患者列表
     * @param qvo teamId 团队id name 姓名 模糊查询
     * @return
     */
    public List<MessagePatientInfo> findPatientListByTeamId(MessageQvo qvo) throws Exception;
}
