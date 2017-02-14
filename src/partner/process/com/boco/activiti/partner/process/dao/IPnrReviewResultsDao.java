package com.boco.activiti.partner.process.dao;

import java.util.Map;

import com.boco.activiti.partner.process.model.PnrReviewResults;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;


public interface IPnrReviewResultsDao extends CommonGenericDao<PnrReviewResults, String> {
	/**
	 * 根据条件查询会审结果列表
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getReviewResultsList(final Integer curPage, final Integer pageSize,final String whereStr,final String importStartTime,final String importEndTime);
	
}
