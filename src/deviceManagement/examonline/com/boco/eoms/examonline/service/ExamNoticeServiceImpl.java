package com.boco.eoms.examonline.service;

import java.util.List;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.examonline.dao.ExamNoticeDao;
import com.boco.eoms.examonline.model.ExamNotice;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Feb 15, 2012 4:04:29 PM 
 */
public class ExamNoticeServiceImpl implements ExamNoticeService {
	private ExamNoticeDao examNoticeDao;

	public ExamNoticeDao getExamNoticeDao() {
		return examNoticeDao;
	}

	public void setExamNoticeDao(ExamNoticeDao examNoticeDao) {
		this.examNoticeDao = examNoticeDao;
	}
	
	public void saveExamNotice(ExamNotice examNotice){
		examNoticeDao.saveObject(examNotice);
	}
	
	public PageModel findExamNoticeList(ExamNotice examNotice,int pageIndex,int pageSize){
		String hql = "from ExamNotice order by createTime desc";
		Object[] params = {};
		PageModel pm = examNoticeDao.searchPaginated(hql.toString(), params, pageIndex, pageSize);
		return pm;
	}
	
	public void deleteExamNotice(String id){
		examNoticeDao.removeObject(ExamNotice.class, id);
	}
	
	public ExamNotice getExamNotice(String id){
		return  (ExamNotice)examNoticeDao.getObject(ExamNotice.class, id);
	}
}
