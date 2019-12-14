package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppMyFamilyDao;
import com.ylz.bizDo.app.po.AppMyFamily;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.patientEntity.AppMyFamilyAgeEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppMyFamilyEntity;
import com.ylz.bizDo.jtapp.patientVo.AppMyFamilyRegisterVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.UserJgType;
import com.ylz.packcommon.common.comEnum.UserUpHpisType;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.Md5Util;
import com.ylz.packcommon.common.util.PinyinUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appMyFamilyDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppMyFamilyDaoImpl implements AppMyFamilyDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppMyFamilyEntity> findByOnly(String id) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tID id,\n" +
                "\tMY_PATIENT_ID mfMyPatientId,\n" +
                "\tMF_FM_CARD mfFmPatientCard,\n" +
                "\tMF_FM_PATIENT_ID mfFmPatientId,\n" +
                "\tMF_FM_ID_NO mfFmIdno,\n" +
                "\tMF_FM_NAME mfFmName,\n" +
                "\tMF_FM_NICK_NAME mfFmNickCode,\n" +
                "\tMF_FM_NICK_NAME mfFmNickName,\n" +
                "\tMF_FM_STATE mfFmState\n" +
                "FROM\n" +
                "\tAPP_MY_FAMILY a\n" +
                "WHERE\n" +
                "\t1 = 1 ";
        if(StringUtils.isNotBlank(id)){
            map.put("id", id);
            sql += " AND a.MY_PATIENT_ID = :id";
        }
        return sysDao.getServiceDo().findSqlMapRVo(sql, map, AppMyFamilyEntity.class);
    }

    @Override
    public List<AppMyFamilyAgeEntity> findByOnlyAge(String id) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tID id,\n" +
                "\tMF_FM_ID_NO mfFmIdno,\n" +
                "\tMF_FM_NAME mfFmName,\n" +
                "\tMF_FM_NICK_NAME mfFmNickCode,\n" +
                "\tMF_FM_NICK_NAME mfFmNickName,\n" +
                "\tMF_FM_MYKH mfFmMykh\n"+
                "FROM\n" +
                "\tAPP_MY_FAMILY a\n" +
                "WHERE\n" +
                "\t1 = 1 ";
        if(StringUtils.isNotBlank(id)){
            map.put("id", id);
            sql += " AND a.MY_PATIENT_ID = :id";
        }
        return sysDao.getServiceDo().findSqlMapRVo(sql, map, AppMyFamilyAgeEntity.class);
    }

    @Override
    public AppMyFamily findByBdPatientIdNo(String userIdNo,String userId) throws Exception{
        return (AppMyFamily) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppMyFamily.class)
                .add(Restrictions.eq("mfFmIdno", userIdNo))
                .add(Restrictions.eq("myPatientId", userId))
                .uniqueResult();
    }

    @Override
    public AppMyFamily findByFamilyUserId(String id, String userId) throws Exception{
        return (AppMyFamily) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppMyFamily.class)
                .add(Restrictions.eq("mfFmPatientId", id))
                .add(Restrictions.eq("myPatientId", userId))
                .uniqueResult();
    }

    @Override
    public String addRegisterMyFamily(AppPatientUser vo, AppMyFamilyRegisterVo qvo) throws Exception{
        String result = "";
        try {
            AppPatientUser user = new AppPatientUser();
            Map<String,Object> idNos;
            if(qvo.getUserIdNo().length() == 18){
                idNos = CardUtil.getCarInfo(qvo.getUserIdNo());
            }else{
                idNos = CardUtil.getCarInfo15W(qvo.getUserIdNo());
            }
            user.setPatientName(qvo.getUserName());
            user.setPatientIdno(qvo.getUserIdNo());
            user.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
            user.setPatientAge(AgeUtil.getAgeYear(user.getPatientBirthday()));
            user.setPatientGender(idNos.get("sex").toString());
            user.setPatientCard(qvo.getUserCrad());
            user.setPatientState(CommonEnable.QIYONG.getValue());
            user.setPatientHealthy(CommonEnable.JINYONG.getValue());
//            if(StringUtils.isNotBlank(qvo.getUserTel())){
//                user.setPatientPwd(Md5Util.MD5(qvo.getUserTel().substring(qvo.getUserTel().length()-6,qvo.getUserTel().length())));
//                user.setPatientTel(qvo.getUserTel());
//            }
            if(StringUtils.isNotBlank(qvo.getUserIdNo())){
                user.setPatientPwd(Md5Util.MD5(qvo.getUserIdNo().substring(qvo.getUserIdNo().length()-6,qvo.getUserIdNo().length())));
            }
            user.setPatientProvince(vo.getPatientProvince());
            user.setPatientCity(vo.getPatientCity());
            user.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
            user.setPatientArea(vo.getPatientArea());
            user.setPatientStreet(vo.getPatientStreet());
            user.setPatientAddress(vo.getPatientAddress());
            user.setPatientJgState(UserJgType.WEISHEZHI.getValue());
            user.setPatientEaseState(UserJgType.WEISHEZHI.getValue());
            user.setPatientCard(qvo.getUserCrad());
            if(StringUtils.isNotBlank(user.getPatientName())) {
                user.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(user.getPatientName()));
            }
            user.setPatientUpHpis(UserUpHpisType.WEIJIHUO.getValue());//用户未激活
            this.sysDao.getServiceDo().add(user);
            //添加社保卡号
//            sysDao.getSecurityCardAsyncBean().getSecurityCard(user.getPatientIdno(),user.getPatientName(),user.getId());
            AppPatientUser bdUser = this.sysDao.getAppPatientUserDao().findPatientIdNo(qvo.getUserIdNo());
            AppMyFamily p =new AppMyFamily();
            p.setMfFmIdno(qvo.getUserIdNo());
            p.setMyPatientId(vo.getId());
            p.setMfFmName(bdUser.getPatientName());
            p.setMfFmNickName(qvo.getNickName());
            p.setMfFmPatientId(bdUser.getId());
            p.setMfFMGender(bdUser.getPatientGender());
            p.setMfFmBirthday(bdUser.getPatientBirthday());
            p.setMfFmAge(bdUser.getPatientAge());
            p.setMfFmCard(qvo.getUserCrad());
            p.setMfFmState(CommonEnable.JINYONG.getValue());
            if(StringUtils.isNotBlank(qvo.getUserMykh())) {
                p.setMfFmMykh(qvo.getUserMykh());
            }
            this.sysDao.getServiceDo().add(p);
            result = "添加成功";
        }catch (Exception e){
            e.printStackTrace();
            result = "添加失败";

        }

        return result;
    }

    @Override
    public List<AppMyFamily> findBdMyFamily(String userId,String state) throws Exception{
        return  sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppMyFamily.class)
                .add(Restrictions.eq("mfFmPatientId", userId))
                .add(Restrictions.eq("mfFmState",state))
                .list();
    }
}
