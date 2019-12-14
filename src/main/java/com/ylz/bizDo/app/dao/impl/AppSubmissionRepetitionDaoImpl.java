package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSubmissionRepetitionDao;
import com.ylz.bizDo.app.po.AppSubmissionRepetition;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2018/05/31.
 */
@Service("appSubmissionRepetitionDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSubmissionRepetitionDaoImpl implements AppSubmissionRepetitionDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public AppSubmissionRepetition getDataOne(String md5Result) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SUBMISSION_REPETITION WHERE 1=1";
        if(StringUtils.isNotBlank(md5Result)){
            map.put("SIGN_MD5_RESULT",md5Result);
            sql += " AND SIGN_MD5_RESULT = :SIGN_MD5_RESULT ";
        }
        List<AppSubmissionRepetition> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppSubmissionRepetition.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;
    }
}
