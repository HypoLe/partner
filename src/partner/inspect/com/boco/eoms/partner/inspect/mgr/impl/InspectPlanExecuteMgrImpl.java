package com.boco.eoms.partner.inspect.mgr.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.inspect.dao.IInspectPlanMainDao;
import com.boco.eoms.partner.inspect.dao.IInspectPlanResDao;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanExecuteMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * Description: 巡检人员任务查询和执行等 
 * Copyright: Copyright (c)2012 
 * Company: BOCO
 * @author: Liuchang
 * @version: 1.0 
 * Create at: Jul 18, 2012 3:36:56 PM
 */
public class InspectPlanExecuteMgrImpl implements IInspectPlanExecuteMgr {

	private IInspectPlanMainDao inspectPlanMainDao;
	private IInspectPlanResDao inspectPlanResDao;

	public IInspectPlanMainDao getInspectPlanMainDao() {
		return inspectPlanMainDao;
	}

	public void setInspectPlanMainDao(IInspectPlanMainDao inspectPlanMainDao) {
		this.inspectPlanMainDao = inspectPlanMainDao;
	}

	public IInspectPlanResDao getInspectPlanResDao() {
		return inspectPlanResDao;
	}

	public void setInspectPlanResDao(IInspectPlanResDao inspectPlanResDao) {
		this.inspectPlanResDao = inspectPlanResDao;
	}

	public Map<String, Object> queryInspectPlanMain(
			Map<String, String> queryMap, Search search) {
		Search querySearch;
		if (null == search) {
			search = new Search();
			search = CommonUtils.getSqlFromRequestMap(queryMap, search);
			querySearch = search;
		} else {
			querySearch = new Search();
			if (null != queryMap) {
				Set<Map.Entry<String, String>> set = queryMap.entrySet();
				for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
						.hasNext();) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) it
							.next();
					querySearch
							.addFilterEqual(entry.getKey(), entry.getValue());
				}
			}
		}
		SearchResult<InspectPlanMain> searchResult = inspectPlanMainDao
				.searchAndCount(querySearch);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("list", searchResult.getResult());
		returnMap.put("count", searchResult.getTotalCount());
		return returnMap;
	}

	public Map<String, Object> queryInspectPlanRes(
			Map<String, String> queryMap, final Integer curPage,
			final Integer pageSize) {
		return inspectPlanResDao.queryInspectPlanRes(queryMap, curPage,
				pageSize);
	}

	public boolean isCheckUser(String userDeptId, String planResExecuteObject) {
			if (null != userDeptId && null != planResExecuteObject
					&& userDeptId.equals(planResExecuteObject)) {
				return true;
			} else {
				return false;
			}
	}
}
