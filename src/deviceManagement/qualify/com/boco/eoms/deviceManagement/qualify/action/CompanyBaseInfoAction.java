package com.boco.eoms.deviceManagement.qualify.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;

/** 
 * Description: 代维公司基本信息Action
 * Copyright:   Copyright (c)2011
 * Company:     BOCO
 * @author:     Liuchang
 * @version:    1.0 
 * Create at:   20110913
 */
public class CompanyBaseInfoAction extends BaseAction {
	
	public ActionForward listCompanyBaseInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("uploadPhoto");
	}
}
