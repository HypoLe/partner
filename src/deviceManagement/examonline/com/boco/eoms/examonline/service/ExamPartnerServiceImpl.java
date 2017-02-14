package com.boco.eoms.examonline.service;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.examonline.dao.ExamDAO;
import com.boco.eoms.examonline.dao.ExamPartnerDao;
import com.boco.eoms.examonline.model.ExamConfig;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Feb 18, 2012 4:30:56 PM 
 */
public class ExamPartnerServiceImpl implements ExamPartnerService {
	private ExamPartnerDao examPartnerDao;
	private ExamDAO examDao;
	
	public PageModel findExamConfigList(ExamConfig config,int examType,int pageIndex,int pageSize){
		StringBuffer hql = new StringBuffer(" from ExamConfig where examType=? " +
				"order by startTime desc");
		Object[] params = {examType};
		PageModel pm = examPartnerDao.searchPaginated(hql.toString(), params, pageIndex, pageSize);
		return pm;
	}
	
	public PageModel findExamResultList(String deptId,String issueId,int pageIndex,int pageSize){
//		String sql ="select sum(content.mark)+0,sum(content.right)+0,count(*)+0,content.user_Id "
//         + "from ExamContent content where content.issue_Id=? "
//         + " group by content.user_Id order by sum(content.mark) desc,count(*) desc";
		String sql ="select user_Id,to_char(sum(mark)) from ExamContent  where issue_Id=? "
	         + " group by user_Id order by sum(mark) desc";
		Object[] params = {issueId};
		PageModel pm = examDao.searchSqlPaginated(sql.toString(), params, pageIndex, pageSize);
		return pm;
	}
	
	public ExamPartnerDao getExamPartnerDao() {
		return examPartnerDao;
	}

	public void setExamPartnerDao(ExamPartnerDao examPartnerDao) {
		this.examPartnerDao = examPartnerDao;
	}

	public ExamDAO getExamDao() {
		return examDao;
	}

	public void setExamDao(ExamDAO examDao) {
		this.examDao = examDao;
	}
	
	

}
