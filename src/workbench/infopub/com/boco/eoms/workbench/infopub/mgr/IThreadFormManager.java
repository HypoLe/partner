package com.boco.eoms.workbench.infopub.mgr;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.service.Manager;
import com.boco.eoms.workbench.infopub.webapp.form.ThreadForm;

/**
 * 
 * <p>
 * Title:接口
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Otc 21, 2009 2:24:10 PM
 * </p>
 * 
 * @author 孙圣泰
 * @version 3.5.1
 * 
 */
public interface IThreadFormManager extends Manager {
	public ThreadForm getThreadForm(HttpServletRequest request);	
}
