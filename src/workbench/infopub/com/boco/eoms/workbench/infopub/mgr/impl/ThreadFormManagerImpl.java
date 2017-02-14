package com.boco.eoms.workbench.infopub.mgr.impl;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.workbench.infopub.dao.IThreadFormDao;
import com.boco.eoms.workbench.infopub.mgr.IThreadFormManager;
import com.boco.eoms.workbench.infopub.webapp.form.ThreadForm;

/**
 * 
 * <p>
 * Title:信息（贴子）管理mgr
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Otc 21, 2009 2:26:10 PM
 * </p>
 * 
 * @author 孙圣泰
 * @version 3.5.1
 * 
 */
public class ThreadFormManagerImpl extends BaseManager implements IThreadFormManager {
	/**
	 * 信息dao
	 */
	private IThreadFormDao dao;
	
	public IThreadFormDao getIThreadFormDao() {
		return dao;
	}

	public void setIThreadFormDao(IThreadFormDao dao) {
		this.dao = dao;
	}

	public ThreadForm getThreadForm(HttpServletRequest request) {
		return dao.getThreadForm(request);
	}


}
