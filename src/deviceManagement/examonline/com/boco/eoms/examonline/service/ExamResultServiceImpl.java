package com.boco.eoms.examonline.service;

import java.util.List;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.examonline.dao.ExamResultDao;
import com.boco.eoms.examonline.model.ExamConfig;
import com.boco.eoms.examonline.model.ExamResult;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Feb 21, 2012 4:09:35 PM 
 */
public class ExamResultServiceImpl implements ExamResultService {
	private ExamResultDao examResultDao;
	
	public ExamResult getExamResultById(String id){
		return (ExamResult)examResultDao.getObject(ExamResult.class, id);
	}
	
	public void saveOrUpdateExamResult(ExamResult result){
		examResultDao.saveObject(result);
	}
	
	public PageModel findExamResultList(String issueId,int pageIndex,int pageSize){
		
		String hql = "from ExamResult where issueId=? order by score desc";
		Object[] params = {issueId};
		PageModel pm = examResultDao.searchPaginated(hql.toString(), params, pageIndex, pageSize);
		return pm;
	}
	
	/**
	 * 生成考试结果
	 * @param issueId
	 */
	public void createExamResult(String issueId,String userId){
		String hql ="select userId,sum(mark) from ExamContent  where issueId=? and userId=?"
	         + " group by userId ";
		Object[] params = {issueId,userId};
		List list = examResultDao.find(hql,params);
		ExamConfig config = (ExamConfig)examResultDao.getObject(ExamConfig.class, issueId);
		ExamResult result = null;
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[])list.get(i);
			result = new ExamResult();
			result.setIssueId(issueId);
			result.setStartTime(config.getStartTime());
			result.setEndTime(config.getEndTime());
			result.setTester(obj[0].toString());
			result.setScore(Integer.parseInt(obj[1].toString()));
			result.setTitle(config.getTitle());
			result.setAssignType(0);
			result.setExamType(config.getExamType());
			examResultDao.saveObject(result);
		}
	}

	public ExamResultDao getExamResultDao() {
		return examResultDao;
	}

	public void setExamResultDao(ExamResultDao examResultDao) {
		this.examResultDao = examResultDao;
	}
	
}
