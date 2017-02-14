package com.boco.eoms.examonline.service;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.examonline.model.ExamResult;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Feb 21, 2012 4:09:18 PM 
 */
public interface ExamResultService {
	public ExamResult getExamResultById(String id);
	
	public PageModel findExamResultList(String issueId,int pageIndex,int pageSize);
	
	public void saveOrUpdateExamResult(ExamResult result);
	
	/**
	 * 生成考试结果
	 * @param issueId
	 * @param userId
	 */
	public void createExamResult(String issueId,String userId);
}
