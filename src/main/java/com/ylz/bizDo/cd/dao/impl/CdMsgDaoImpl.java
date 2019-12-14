package com.ylz.bizDo.cd.dao.impl;


import com.ylz.bizDo.cd.dao.CdMsgDao;
import com.ylz.bizDo.cd.po.CdMsg;
import com.ylz.bizDo.cd.po.CdMsgAccepter;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.cd.vo.CdMsgQvo;
import com.ylz.bizDo.cd.vo.CdMsgVo;
import com.ylz.bizDo.news.vo.NewsMsgQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PC on 2016/2/15.
 * 消息业务实现
 */
@Service("cdMsgDao")
@Transactional(rollbackForClassName={"Exception"})
public class CdMsgDaoImpl   implements CdMsgDao {
    @Autowired
    public SysDao sysDao;

    public List<CdMsg> findList(CdMsgQvo qvo) throws Exception{
        HashMap map=new HashMap();
        StringBuffer sql =new StringBuffer("SELECT a.* FROM CD_MSG a,CD_MSG_ACCEPTER b WHERE a.id = b.ACCEPTER_MSG_ID");
        if(StringUtils.isNotBlank(qvo.getSenduserid())) {
            sql.append(" AND a.MSG_USERID=:MSG_USERID ");
            map.put("MSG_USERID",qvo.getSenduserid());
        }
        if(StringUtils.isNotBlank(qvo.getMsgtitle())){
            sql.append(" and a.MSG_TITLE like:MSG_TITLE ");
            map.put("MSG_TITLE","%"+qvo.getMsgtitle()+"%");
        }
        if(StringUtils.isNotBlank(qvo.getSendstartdate())){
            sql.append(" and a.MSG_CREATER_DATE>=:MSG_CREATER_DATE_START ");
            map.put("MSG_CREATER_DATE_START",qvo.getSendstartdate());
        }
        if(StringUtils.isNotBlank(qvo.getSendenddate())){
            sql.append(" and a.MSG_CREATER_DATE<=:MSG_CREATER_DATE_END ");
            map.put("MSG_CREATER_DATE_END",qvo.getSendenddate());
        }
        if(StringUtils.isNotBlank(qvo.getAccepteruserid())){
            sql.append(" and b.ACCEPTER_USER_ID=:ACCEPTER_USER_ID");
            map.put("ACCEPTER_USER_ID",qvo.getAccepteruserid());

            if(StringUtils.isNotBlank(qvo.getIsread())){
                sql.append(" and b.READ_DATE is null");
            }
        }
        if(StringUtils.isNotBlank(qvo.getMsgType())){
            sql.append(" and a.MSG_TYPE=:msgType ");
            map.put("msgType",qvo.getMsgType());
        }
        sql.append(" GROUP BY a.id,a.baseid,a.MSG_CREATER_DATE,a.MSG_TEXT,a.MSG_TITLE,a.MSG_USERID,a.MSG_USER_NAME");
        if(StringUtils.isNotBlank(qvo.getAccepteruserid())){
            sql.append(",b.ACCEPTER_USER_ID");
        }

        if (StringUtils.isNotBlank(qvo.getSort())){
            if (qvo.getSort().equals("msgCreaterDate")){
                sql.append(" ORDER BY a.MSG_CREATER_DATE "+qvo.getOrder());
            }
        }else {
            sql.append(" ORDER BY a.MSG_CREATER_DATE DESC");
        }

        List<CdMsg> ls=sysDao.getServiceDo().findSqlMap(sql.toString(),map,CdMsg.class,qvo);
        if(ls!=null) {
            return ls;
        }
        return null;
    }

    public List<CdMsg> findListtest(CdMsgQvo qvo) throws Exception{
        HashMap map=new HashMap();
        StringBuffer sql =new StringBuffer("SELECT a.* FROM CD_MSG a where 1=1");
        if(StringUtils.isNotBlank(qvo.getMsgtitle())){
            sql.append(" and a.MSG_TITLE like:MSG_TITLE ");
            map.put("MSG_TITLE","%"+qvo.getMsgtitle()+"%");
        }
        List<CdMsg> ls=sysDao.getServiceDo().findSqlMap(sql.toString(),map,CdMsg.class,qvo);
        if(ls!=null) {
            return ls;
        }
        return null;
    }

    public void addMsg(CdMsg vo,String userids,String saname) throws Exception{
        String[] ss=userids.split(";");
        String[] ssname=saname.split(";");
        if(userids!=null){
            sysDao.getServiceDo().add(vo);
            for (int i = 0; i < ss.length; i++){
                CdMsgAccepter ac=new CdMsgAccepter();
                ac.setAccepterMsgId(vo.getId());
                ac.setAccepterUserId(ss[i]);
                CdUser users = (CdUser) sysDao.getServiceDo().find(CdUser.class,ss[i]);
                ac.setAccepterName(users.getUserName());
                sysDao.getServiceDo().add(ac);
            }
        }
    }


    public void deleteMsg(String id) throws Exception{
        sysDao.getServiceDo().delete(CdMsg.class,id);
        List<CdMsgAccepter> ls=sysDao.getServiceDo().loadByPk(CdMsgAccepter.class,"AccepterMsgId",id);
        if(ls!=null && ls.size()>0){
            for (CdMsgAccepter l:ls){
                sysDao.getServiceDo().delete(l);
            }
        }
    }

    public void readMsg(String msgid,String userid) throws Exception{
        HashMap map=new HashMap();
        map.put("ACCEPTER_MSG_ID",msgid);
        map.put("ACCEPTER_USER_ID",userid);
        StringBuffer sql =new StringBuffer("SELECT * from CD_MSG_ACCEPTER a where a.ACCEPTER_MSG_ID=:ACCEPTER_MSG_ID and a.ACCEPTER_USER_ID=:ACCEPTER_USER_ID and a.READ_DATE is null");
        List<CdMsgAccepter> ls=sysDao.getServiceDo().findSqlMap(sql.toString(), map, CdMsgAccepter.class);
        if(ls!=null && ls.size()>0){
            ls.get(0).setReadDate(Calendar.getInstance());
            sysDao.getServiceDo().modify(ls.get(0));
        }
    }

    public void addMsg(CdMsg vo,String userids) throws Exception{
        String[] ss=userids.split(";");
        if(userids!=null){
            sysDao.getServiceDo().add(vo);
            for (int i = 0; i < ss.length; i++){
                CdMsgAccepter ac=new CdMsgAccepter();
                ac.setAccepterMsgId(vo.getId());
                ac.setAccepterUserId(ss[i]);
                ac.setAccepterName(((CdUser) sysDao.getServiceDo().find(CdUser.class, ss[i])).getUserName());
                sysDao.getServiceDo().add(ac);
            }
        }
    }

	@SuppressWarnings("all")
	public int findMsgCount(String userId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer sql =new StringBuffer("SELECT count(1) FROM CD_MSG a,CD_MSG_ACCEPTER b WHERE a.id = b.ACCEPTER_MSG_ID");
        sql.append(" and b.ACCEPTER_USER_ID=:ACCEPTER_USER_ID");
        map.put("ACCEPTER_USER_ID",userId);
        sql.append(" and b.READ_DATE is null");
		return sysDao.getServiceDo().gteSqlCount(sql.toString(),map);
	}
	
	
    
	@Override
	public List<CdMsgVo> findByUserId(NewsMsgQvo qvo) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer sql =new StringBuffer("SELECT a.id msgId,a.MSG_TITLE msgtitle,a.MSG_TEXT msgtext,a.MSG_CREATER_DATE msgCreaterDate,a.MSG_USER_NAME msgUserName,b.READ_DATE readDate FROM CD_MSG a,CD_MSG_ACCEPTER b WHERE a.id = b.ACCEPTER_MSG_ID ");
        if(StringUtils.isNotBlank(qvo.getUserId())){
    		sql.append(" and b.ACCEPTER_USER_ID=:ACCEPTER_USER_ID");
            map.put("ACCEPTER_USER_ID",qvo.getUserId());
        }
        sql.append(" ORDER BY a.MSG_CREATER_DATE DESC ");
		return sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, CdMsgVo.class,qvo);
	}
	
	public List<CdMsgVo> findByUserId(String msgId,String userId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer sql =new StringBuffer("SELECT a.id msgId,a.MSG_TITLE msgtitle,a.MSG_TEXT msgtext,a.MSG_CREATER_DATE msgCreaterDate,a.MSG_USER_NAME msgUserName,b.READ_DATE readDate FROM CD_MSG a,CD_MSG_ACCEPTER b WHERE a.id = b.ACCEPTER_MSG_ID ");
        if(StringUtils.isNotBlank(userId)){
    		sql.append(" and b.ACCEPTER_USER_ID=:ACCEPTER_USER_ID");
            map.put("ACCEPTER_USER_ID",userId);
        }
        if(StringUtils.isNotBlank(msgId)){
    		sql.append(" and a.id=:MSG_ID");
            map.put("MSG_ID",msgId);
        }
        sql.append(" ORDER BY a.MSG_CREATER_DATE DESC ");
		return sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, CdMsgVo.class);
	}

	public static void main(String[] args) throws Exception {
		Calendar calendar=Calendar.getInstance();
		System.out.println(ExtendDate.getYMD_h_m_s(calendar));
		calendar.add(Calendar.HOUR, -12); //减填负数
		System.out.println(ExtendDate.getYMD_h_m_s(calendar));
	}

	@Override
	public List<CdMsg> findIdByContent(CdMsgQvo qvo) throws Exception {
		HashMap map=new HashMap();
        StringBuffer sql =new StringBuffer("SELECT a.* FROM CD_MSG a where 1=1");
        if(StringUtils.isNotBlank(qvo.getContentId())){
        	map.put("contentId",qvo.getContentId());
            sql.append(" and a.MSG_CONTENT_ID =:contentId ");
        }
        if(StringUtils.isNotBlank(qvo.getSpState())){
        	map.put("spState",qvo.getSpState());
            sql.append(" and a.MSG_SP_STATE =:spState ");
        }
        List<CdMsg> ls=sysDao.getServiceDo().findSqlMap(sql.toString(),map,CdMsg.class,qvo);
        return ls;
	}

	@Override
	public void addMsg(String msgTitle, Calendar msgCreateTime, String msgText, String msgUserId, String msgType,
			String contentId, String spState, String acceptUserId) throws Exception {
		CdMsg cdMsg = new CdMsg();
		cdMsg.setMsgTitle(msgTitle);
		cdMsg.setMsgCreaterDate(msgCreateTime);
		cdMsg.setMsgText(msgText);
		cdMsg.setMsgUserid(msgUserId);
		cdMsg.setMsgType(msgType);//1为消息中心，2为预警中心
		cdMsg.setMsgContentId(contentId);
		cdMsg.setMsgSpState(spState);
		this.sysDao.getCdMsgDao().addMsg(cdMsg, acceptUserId);
		
		
	}

	@Override
	public void delMsg(String contentId, String spState) throws Exception {
		CdMsgQvo qvo = new CdMsgQvo();
		qvo.setContentId(contentId);
		qvo.setSpState(spState);
		List<CdMsg> ls = this.sysDao.getCdMsgDao().findIdByContent(qvo);
		if(ls!=null&&ls.size()>0){
			for (int i = 0; i < ls.size(); i++) {
				this.sysDao.getCdMsgDao().deleteMsg(ls.get(i).getId());
			}
			
		}
		
	}




	
    

}
