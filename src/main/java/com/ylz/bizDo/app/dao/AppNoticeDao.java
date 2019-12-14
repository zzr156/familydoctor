package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppNotice;
import com.ylz.bizDo.app.po.AppSignsRecordTable;
import com.ylz.bizDo.jtapp.commonEntity.AppNoticeCountEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppNoticeListEntity;
import com.ylz.bizDo.jtapp.commonEntity.NoticeViewEntity;
import com.ylz.bizDo.jtapp.commonVo.AppNoticeCountQvo;
import com.ylz.bizDo.jtapp.drVo.AppSignsWarningRecordQvo;

import java.util.List;

/**
 * Created by asus on 2017/6/20.
 */
public interface AppNoticeDao {

    /**
     * 添加公告消息
     * @param title
     * @param content
     * @param type
     * @param createPeople
     * @param receivePeople
     * @param typeUser 1 患者 2 医生
     */
    public void addNotices(String title, String content, String type, String createPeople, String receivePeople,String forEginId,String typeUser) throws Exception;

    /**
     * 添加公告消息
     * @param title
     * @param content
     * @param noticeType
     * @param createPeople
     * @param receivePeople
     * @param forEginId
     * @param type
     * @param areaCode
     * @param assigned
     * @return
     */
    public void addNoticesAllTag(String title, String content, String noticeType, String createPeople, String receivePeople,String forEginId,String type,String areaCode,boolean assigned) throws Exception;

    /**
     * 查询未读条数
     * @param drPatientId
     * @return
     */
    public List<AppNoticeCountEntity> findUnReadNoticeCount(String drPatientId) throws Exception;

    public List<AppNoticeListEntity> findNoticeList(AppNoticeCountQvo qvo) throws Exception;

    public NoticeViewEntity findNoticeView(String id) throws Exception;

    public List<AppNotice> findNoticeAll(AppNoticeCountQvo qvo) throws Exception;

    public AppNoticeListEntity findNoticeEntityById(String id) throws Exception;

    /**
     * 已读最新数据
     * @param drPatientId
     * @return
     */
    public List<AppNoticeCountEntity> findReadNoticeCount(String drPatientId,String type) throws Exception;

    public void updateNoticeRead(String patientId,String type) throws Exception;

    public void addNoticeTg(AppNoticeCountQvo qvo) throws Exception;

    /**
     * 发送体征预警消息提醒
     * @param qvo
     * @return
     * @throws Exception
     */
    public String fsTzxxToPatient(AppSignsWarningRecordQvo qvo) throws Exception;

    public List<AppSignsRecordTable> findTzxxPeople(AppSignsWarningRecordQvo qvo) throws Exception;

    /**
     * 添加公告消息
     * @param title
     * @param content
     * @param type
     * @param createPeople
     * @param receivePeople
     * @param typeUser 1 患者 2 医生
     */
    public void addOnlyNotices(String title, String content, String type, String createPeople, String receivePeople,String forEginId,String typeUser) throws Exception;
}
