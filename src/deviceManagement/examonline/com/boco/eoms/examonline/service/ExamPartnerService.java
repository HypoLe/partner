package com.boco.eoms.examonline.service;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.examonline.model.ExamConfig;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Feb 18, 2012 4:30:43 PM 
 */
public interface ExamPartnerService {
	public PageModel findExamConfigList(ExamConfig config,int examType,int pageIndex,int pageSize);
	
	public PageModel findExamResultList(String deptId,String issueId,int pageIndex,int pageSize);

}
