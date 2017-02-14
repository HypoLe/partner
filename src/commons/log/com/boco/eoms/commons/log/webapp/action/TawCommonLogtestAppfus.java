package com.boco.eoms.commons.log.webapp.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.log.webapp.bo.ITawCommonLogSearchBo;
import com.boco.eoms.commons.system.priv.dao.TawSystemPrivOperationDao;
import com.boco.eoms.commons.system.priv.model.TawSystemPrivOperation;

public class TawCommonLogtestAppfus extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		TawSystemPrivOperationDao tawSystemPrivOperationDao = (TawSystemPrivOperationDao) getBean("tawSystemPrivOperationDao");
		String whereStr = " where parentcode=-1 and deleted=0 and hided=0 ";
		Map map = tawSystemPrivOperationDao.getTawSystemPrivOperations(0, 30, whereStr);
		List<TawSystemPrivOperation> list =  (List<TawSystemPrivOperation>) map.get("result");
		LocalDate date = new LocalDate();
		LocalDate date1 = date.minusDays(2);
		request.setAttribute("searchbyoper", request.getParameter("searchbyoper"));
		request.setAttribute("nowDay", date.toString());
		request.setAttribute("minDay", date1.toString());
		request.setAttribute("list", list);
		return mapping.findForward("list");
	}
	
public String getNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = request.getParameter("node");

		ITawCommonLogSearchBo treebo = (ITawCommonLogSearchBo) ApplicationContextHolder
		.getInstance().getBean("iTawCommonLogSearchBo");
		JSONArray jsonRoot = treebo.getSmsServiceTreeXml(nodeId, "aa1");

		response.setContentType("text/xml;charset=UTF-8");
		response.getWriter().print(jsonRoot.toString());
		return null;
	}
	
}
