package com.ylz.bizDo.app.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ylz.bizDo.app.dao.AppExerciseCountNewDao;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.SignFormType;

/**
 * 新履约管理
 */
@Service("appExerciseCountNewDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppExerciseCountNewDaoImpl implements AppExerciseCountNewDao {

	@Autowired
	private SysDao sysDao;

	@Override
	public List<Map> dispatchExerciseCount(int year, int num) throws Exception {
		StringBuffer sb = new StringBuffer("");
		HashMap<String, Object> map = new HashMap<String, Object>();

		switch (num) {
		case 0:
			map.put("startTime", year + "-01-01 00:00:00");
			map.put("endTime", year + "-12-31 23:59:59");
			break;
		case 1:
			map.put("startTime", year + "-01-01 00:00:00");
			map.put("endTime", year + "-01-31 23:59:59");
			break;
		case 2:
			map.put("startTime", year + "-02-01 00:00:00");
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				map.put("endTime", year + "-02-29 23:59:59");
			} else {
				map.put("endTime", year + "-02-28 23:59:59");
			}
			break;
		case 3:
			map.put("startTime", year + "-03-01 00:00:00");
			map.put("endTime", year + "-03-31 23:59:59");
			break;
		case 4:
			map.put("startTime", year + "-04-01 00:00:00");
			map.put("endTime", year + "-04-30 23:59:59");
			break;
		case 5:
			map.put("startTime", year + "-05-01 00:00:00");
			map.put("endTime", year + "-05-31 23:59:59");
			break;
		case 6:
			map.put("startTime", year + "-06-01 00:00:00");
			map.put("endTime", year + "-06-30 23:59:59");
			break;
		case 7:
			map.put("startTime", year + "-07-01 00:00:00");
			map.put("endTime", year + "-07-31 23:59:59");
			break;
		case 8:
			map.put("startTime", year + "-08-01 00:00:00");
			map.put("endTime", year + "-08-31 23:59:59");
			break;
		case 9:
			map.put("startTime", year + "-09-01 00:00:00");
			map.put("endTime", year + "-09-30 23:59:59");
			break;
		case 10:
			map.put("startTime", year + "-10-01 00:00:00");
			map.put("endTime", year + "-10-31 23:59:59");
			break;
		case 11:
			map.put("startTime", year + "-11-01 00:00:00");
			map.put("endTime", year + "-11-30 23:59:59");
			break;
		case 12:
			map.put("startTime", year + "-12-01 00:00:00");
			map.put("endTime", year + "-12-31 23:59:59");
			break;
		default:
			map.put("startTime", year + "-01-01 00:00:00");
			map.put("endTime", year + "-12-31 23:59:59");
			break;
		}
		
		String[] signState = new String[] { SignFormType.YUQY.getValue(), SignFormType.YQY.getValue() };
		map.put("signState", signState);
		map.put("jyState", SignFormType.YJY.getValue());

		sb.append(" SELECT ");
		sb.append(" 	aa.SIGN_PACKAGEID AS SIGN_PACKAGEID, ");
		sb.append(" 	aa.LABEL_VALUE AS LABEL_VALUE, ");
		sb.append(" 	COUNT(aa.SIGN_PACKAGEID) AS SIGN_NUMBER, ");
		sb.append(" 	aa.SIGN_AREA_CODE AS SIGN_AREA_CODE, ");
		sb.append(" 	aa.SIGN_DR_ID AS SIGN_DR_ID ");
		sb.append(" FROM ");
		sb.append(" 	( ");
		sb.append(" 		SELECT ");
		sb.append(" 			GROUP_CONCAT(DISTINCT LABEL_VALUE) AS LABEL_VALUE, ");
		sb.append(" 			fm.SIGN_PACKAGEID AS SIGN_PACKAGEID, ");
		sb.append(" 			sign_area_code, ");
		sb.append(" 			fm.SIGN_PATIENT_ID AS SIGN_PATIENT_ID, ");
		sb.append(" 			fm.SIGN_DR_ID AS SIGN_DR_ID ");
		sb.append(" 		FROM ");
		sb.append(" 			app_sign_form fm ");
		sb.append(" 		LEFT JOIN app_label_group gp ON fm.ID = gp.LABEL_SIGN_ID ");
		sb.append(" 		WHERE ");
		sb.append(" 			gp.LABEL_TYPE = '3' ");
		sb.append(" 		AND ( ");
		sb.append(" 			( ");
		sb.append(" 				fm.SIGN_STATE IN :signState ");
		sb.append(" 				AND ( ");
		sb.append(" 					( ");
		sb.append(" 						fm.SIGN_FROM_DATE >= :startTime ");
		sb.append(" 						AND fm.SIGN_FROM_DATE <= :endTime ");
		sb.append(" 					) ");
		sb.append(" 					OR ( ");
		sb.append(" 						fm.SIGN_TO_DATE >= :startTime ");
		sb.append(" 						AND fm.SIGN_TO_DATE <= :endTime ");
		sb.append(" 					) ");
		sb.append(" 				) ");
		sb.append(" 			) ");
		sb.append(" 			OR ( ");
		sb.append(" 				fm.SIGN_STATE = :jyState ");
		sb.append(" 				AND ( ");
		sb.append(" 					fm.SIGN_SURRENDER_DATE >= :startTime ");
		sb.append(" 					AND fm.SIGN_SURRENDER_DATE <= :endTime ");
		sb.append(" 				) ");
		sb.append(" 			) ");
		sb.append(" 		) ");
		sb.append(" 		AND fm.SIGN_PACKAGEID IS NOT NULL  ");
		sb.append(" 		AND fm.SIGN_PACKAGEID <> ''  ");
		sb.append(" 		GROUP BY ");
		sb.append(" 			fm.SIGN_PATIENT_ID, ");
		sb.append(" 			fm.SIGN_DR_ID ");
		sb.append(" 	) aa ");
		sb.append(" GROUP BY ");
		sb.append(" 	aa.aa.LABEL_VALUE, ");
		sb.append(" 	aa.SIGN_PACKAGEID, ");
		sb.append(" 	aa.SIGN_AREA_CODE, ");
		sb.append(" 	aa.SIGN_DR_ID ");

		List<Map> list = sysDao.getServiceDo().findSqlMap(sb.toString(), map );
		return list;
	}

	@Override
	public List<Map> getExerciseCountByYearAndAreaCode(String year, String areaCode) throws Exception{
		StringBuffer sb = new StringBuffer("");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("areaCode", areaCode + "%");
		map.put("year", year);

		sb.append(" SELECT ");
		sb.append("     SIGN_PACKAGEID, ");
		sb.append("     LABEL_VALUE, ");
		sb.append("     SUM(SIGN_NUMBER) AS SIGN_NUMBER, ");
		sb.append("     SIGN_AREA_CODE ");
		sb.append(" FROM ");
		sb.append("     APP_EXERCISE_COUNT_NEW ");
		sb.append(" WHERE ");
		sb.append("     SIGN_AREA_CODE LIKE :areaCode ");
		sb.append(" AND SIGN_YEAR = :year ");
		sb.append(" GROUP BY ");
		sb.append("     SIGN_PACKAGEID, ");
		sb.append("     LABEL_VALUE, ");
		sb.append("     SIGN_AREA_CODE ");

		List<Map> list = sysDao.getServiceDo().findSqlMap(sb.toString(), map);
		return list;
	}

	@Override
	public List<Map> getExerciseCountByYearAndDrId(String year, String drId) throws Exception{
		StringBuffer sb = new StringBuffer("");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("drId", drId);
		map.put("year", year);

		sb.append(" SELECT ");
		sb.append(" 	SIGN_PACKAGEID, ");
		sb.append(" 	LABEL_VALUE, ");
		sb.append(" 	SIGN_NUMBER, ");
		sb.append(" 	SIGN_DR_ID ");
		sb.append(" FROM ");
		sb.append(" 	APP_EXERCISE_COUNT_NEW ");
		sb.append(" WHERE ");
		sb.append(" 	SIGN_DR_ID = :drId ");
		sb.append(" AND SIGN_YEAR = :year ");

		List<Map> list = sysDao.getServiceDo().findSqlMap(sb.toString(), map);
		return list;
	}

}
