package com.boco.activiti.partner.process.dao;

public interface IPnrReviewResultsJDBCDao {
	
	/**
	 * 删除会审人员结果
	 * @param whereStr
	 */
	public void deletePnrReviewResults(String whereStr);

}
