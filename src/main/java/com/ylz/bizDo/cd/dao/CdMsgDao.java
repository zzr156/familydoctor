package com.ylz.bizDo.cd.dao;


import com.ylz.bizDo.cd.po.CdMsg;
import com.ylz.bizDo.cd.vo.CdMsgQvo;
import com.ylz.bizDo.cd.vo.CdMsgVo;
import com.ylz.bizDo.news.vo.NewsMsgQvo;

import java.util.Calendar;
import java.util.List;

/**
 * Created by PC on 2016/2/15.
 * 消息业务定义
 */
public interface CdMsgDao {
    /**
     * 消息综合查询
     * @param qvo 查询条件
     * @return
     */
    public List<CdMsg> findList(CdMsgQvo qvo) throws Exception;

    /**
     * 发送消息
     * @param vo
     * @param userids 接收者id
     * @param saname 接收者姓名
     */
    public void addMsg(CdMsg vo, String userids, String saname) throws Exception;

    /**
     * 发送消息
     * @param vo
     * @param userids
     */
    public void addMsg(CdMsg vo, String userids) throws Exception;

    /**
     * 删除消息包括接收者
     * @param id 主键
     */
    public void deleteMsg(String id) throws Exception;

    /**
     * 设置为已读
     * @param msgid
     * @param userid
     */
    public void readMsg(String msgid, String userid) throws Exception;

    public List<CdMsg> findListtest(CdMsgQvo qvo) throws Exception;
    
    public int findMsgCount(String userId) throws Exception;

	public List<CdMsgVo> findByUserId(NewsMsgQvo newsMsgQvo) throws Exception;
	
	public List<CdMsgVo> findByUserId(String msgId,String userId) throws Exception;
	
	public List<CdMsg> findIdByContent(CdMsgQvo qvo) throws Exception;
	
	public void addMsg(String msgTitle, Calendar msgCreateTime, String msgText, String msgUserId, String msgType, String contentId, String spState, String acceptUserId) throws Exception;
	
	public void delMsg(String contentId, String spState) throws Exception;//根据spId和spState查询消息并删除
	
	
	
	
	
}
