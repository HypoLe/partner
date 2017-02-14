package com.boco.eoms.aggregation.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import com.boco.eoms.aggregation.dao.AggregationDao;
import com.boco.eoms.aggregation.model.Aggregation;
import com.boco.eoms.aggregation.service.AggregationService;
import com.boco.eoms.aggregation.util.AggregationType;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;

public class AggregationServiceImpl extends
		CommonGenericServiceImpl<Aggregation> implements AggregationService {

	public void setAggregationDao(AggregationDao aggregationDao) {
		this.setCommonGenericDao(aggregationDao);
	}

	public List<String> specialityDictList(String fatherDictId) {// 父字典值
		List<TawSystemDictType> specialityDictList = new ArrayList<TawSystemDictType>();
		ITawSystemDictTypeManager dictMgr = (ITawSystemDictTypeManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDictTypeManager");
		specialityDictList = dictMgr.getDictSonsByDictid(fatherDictId);// 得到所有专区对应字典表每条数据
		List<String> specialityList = new ArrayList<String>();
		for (int r = 0; r < specialityDictList.size(); r++) {
			String specialityDict = specialityDictList.get(r).getDictId();
			specialityList.add(specialityDict);
		}
		return specialityList;
	}

	/**
	 * 查询个人引用的模块ID查询个人引用的模块ID
	 */
	public List<ListOrderedMap> allResultList(String userId) {
		// 查询个人引用的模块ID查询个人引用的模块ID
		CommonSpringJdbcService getModuleID = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "select moduleID from portal_aggregationUser where creatUser="
				+ "'"
				+ userId
				+ "'"
				+ "and deleted="
				+ "'"
				+ AggregationType.DELETE_FALSE + "'";
		List<ListOrderedMap> resultList = getModuleID.queryForList(sql);
		List<ListOrderedMap> allResultList = null;
		String sql1 = "select * from portal_aggregationSet ";
		if (resultList.size() != 0) {
			for (int i = 0; i < resultList.size(); i++) {
				Map map = resultList.get(i);
				if (i != 0) {
					String and = " or id='" + map.get("moduleID").toString()
							+ "' ";
					sql1 = sql1 + and;
				} else {
					String and = "where id= '" + map.get("moduleID").toString()
							+ "' ";
					sql1 = sql1 + and;
				}
			}
			CommonSpringJdbcService getAll = (CommonSpringJdbcService) ApplicationContextHolder
					.getInstance().getBean("commonSpringJdbcService");
			allResultList = getAll.queryForList(sql1);
		}
		return allResultList;
	}

	/**
	 * 查询推荐内容
	 */
	public List<ListOrderedMap> commendList(String userId, String commend) {
		CommonSpringJdbcService getModuleID = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "select id from portal_aggregationSet where commend="
				+ "'" + commend + "'" + "and deleted=" + "'"
				+ AggregationType.DELETE_FALSE + "'";
		List<ListOrderedMap> resultList = getModuleID.queryForList(sql);
		String sql1 = "select * from portal_aggregationUser where deleted='"
				+ AggregationType.DELETE_FALSE + "' moduleID!=";
		for (int i = 0; i < resultList.size(); i++) {
			Map map = resultList.get(i);
			if (i != 0) {
				String and = " and moduleID='" + map.get("id").toString()
						+ "' ";
				sql1 = sql1 + and;
			} else {
				String and = " '" + map.get("id").toString() + "' ";
				sql1 = sql1 + and;
			}
		}
		CommonSpringJdbcService getAll = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		List<ListOrderedMap> commendList = getAll.queryForList(sql1);
		return commendList;
	}

}
