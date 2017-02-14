package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.Resume;
import com.boco.eoms.partner.baseinfo.mgr.IResumeMgr;
import com.boco.eoms.partner.baseinfo.dao.IResumeDao;

/**
 * <p>
 * Title:工作简历
 * </p>
 * <p>
 * Description:工作简历
 * </p>
 * <p>
 * Fri Dec 18 16:50:43 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class ResumeMgrImpl implements IResumeMgr {
 
	private IResumeDao  resumeDao;
 	
	public IResumeDao getResumeDao() {
		return this.resumeDao;
	}
 	
	public void setResumeDao(IResumeDao resumeDao) {
		this.resumeDao = resumeDao;
	}
 	
    public List getResumes() {
    	return resumeDao.getResumes();
    }
    
    public Resume getResume(final String id) {
    	return resumeDao.getResume(id);
    }
    
    public void saveResume(Resume resume) {
    	resumeDao.saveResume(resume);
    }
    
    public void removeResume(final String id) {
    	resumeDao.removeResume(id);
    }
    
    public Map getResumes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return resumeDao.getResumes(curPage, pageSize, whereStr);
	}
	
	public Map getResumes(final Integer curPage, final Integer pageSize,
			final String whereStr, final Date ccds ,final Date ccde ,final Date dds, final Date dde) {
		return resumeDao.getResumes(curPage, pageSize, whereStr, ccds, ccde, dds, dde);
	}
	
	public List getResumes(final String where){
		return resumeDao.getResumes(where);
	}
}