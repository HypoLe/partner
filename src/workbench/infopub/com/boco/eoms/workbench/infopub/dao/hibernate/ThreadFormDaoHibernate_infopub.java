package com.boco.eoms.workbench.infopub.dao.hibernate;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.log4j.Logger;
import com.boco.eoms.workbench.infopub.dao.IThreadFormDao;
import com.boco.eoms.workbench.infopub.webapp.form.ThreadForm;
/**
 * 
 * <p>
 * Title:外部接口实现
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Otc 21, 2009 2:18:45 PM
 * </p>
 * 
 * @author 孙圣泰
 * @version 3.5.1
 * 
 */
public class ThreadFormDaoHibernate_infopub extends BaseDaoHibernate implements IThreadFormDao{

	/**
	 * log4j
	 */
	private final static Logger logger = Logger
			.getLogger(ThreadFormDaoHibernate_infopub.class);

	public ThreadForm getThreadForm(HttpServletRequest request) {
		String forumsId = StaticMethod.null2String(request.getParameter("forumsId"));
		String createrId = StaticMethod.null2String(request.getParameter("createrId"));
		String createrName = StaticMethod.null2String(request.getParameter("createrName"));
		String title = StaticMethod.null2String(request.getParameter("title"));
		
		ThreadForm threadForm = new ThreadForm();
		threadForm.setForumsId(forumsId);
		threadForm.setCreaterId(createrId);
		threadForm.setCreaterName(createrName);
		threadForm.setTitle(title);
		return threadForm;
	}

	

}
