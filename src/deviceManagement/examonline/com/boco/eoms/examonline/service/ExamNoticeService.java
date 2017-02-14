package com.boco.eoms.examonline.service;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.examonline.model.ExamNotice;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Feb 15, 2012 4:04:19 PM 
 */
public interface ExamNoticeService {
	public PageModel findExamNoticeList(ExamNotice examNotice,int pageIndex,int pageSize);
	public void deleteExamNotice(String id);
	public void saveExamNotice(ExamNotice examNotice);
	public ExamNotice getExamNotice(String id);
}
