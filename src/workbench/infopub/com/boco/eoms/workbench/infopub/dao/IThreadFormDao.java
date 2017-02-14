package com.boco.eoms.workbench.infopub.dao;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.workbench.infopub.webapp.form.ThreadForm;

/**
 * 
 * <p>
 * Title:外部系统接口
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Otc 21, 2009 1:50:20 PM
 * </p>
 * 
 * @author 孙圣泰
 * @version 3.5.1
 * 
 */
public interface IThreadFormDao extends Dao {

	
	public ThreadForm getThreadForm(HttpServletRequest request);


}
