package com.boco.eoms.partner.netresource.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.model.PnrOrgFinalistSheet;
import com.boco.eoms.partner.dataSynch.util.DataSynchJdbcUtil;
import com.boco.eoms.partner.netresource.service.IEomsService;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.SearchResult;


public class PnrNetResourceStatisticsAction extends BaseAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("gotoAddPnrOrgFinalistSheet");
	}
	
	
	
	/**
	 * 地域树
	 * @template tpl-area-xtree
	 */
	public ActionForward area(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String template = StaticMethod.null2String(request.getParameter("tpl"),
		"tpl-area-xtree");
		ArrayList list = new ArrayList();

		try {
			ITawSystemAreaManager mgr = (ITawSystemAreaManager) ApplicationContextHolder
			.getInstance().getBean("ItawSystemAreaManager");
			list = (ArrayList) mgr.getSonAreaByAreaId(node);

		} catch (Exception ex) {
			BocoLog.error(this, "生成地域树图时报错：" + ex);
		}

		request.setAttribute("list", list);
		
		//转向的页面就是构造json(本处template为tpl-area-xtree，转向的页面是tpl-area-xtree.jsp)
		return mapping.findForward(template);
	}

	
}
