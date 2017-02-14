package com.boco.eoms.partner.statistically.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.baseinfo.model.TawApparatus;

public class PartnerApparatusSearchAction extends BaseAction{
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String search ="";
		Map paramMap = request.getParameterMap();
		if(paramMap.containsKey("dt0name")){
			search+= " and dt.name= '"+new String(((String[])paramMap.get("dt0name"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
		}
		if(paramMap.containsKey("d0area_name")){
			search+= " and d.area_name='"+new String(((String[])paramMap.get("d0area_name"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
		}
		if(paramMap.containsKey("d0name")){
			search+= " and d.name='"+new String(((String[])paramMap.get("d0name"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
		}
		if(paramMap.containsKey("app0typetypelikedict")){
			search+= " and app.type='"+new String(((String[])paramMap.get("app0typetypelikedict"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
			
		}
		
		
		String searchSql="select app.* from  pnr_dept as d, pnr_partner_apparatus app  , (select name,id,deleted from pnr_dept where id = interface_head_id) as dt "+ 
		"where d.id =app.partnerid and  d.deleted='0' and dt.deleted='0'and  app.bigpartnerid=dt.id" +
				search;
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
			TawApparatus  apparatus= new TawApparatus();
			apparatus.setId((String)listOrderedMap.get("id"));
			apparatus.setApparatusName((String)listOrderedMap.get("apparatusname"));
			apparatus.setFactory((String)listOrderedMap.get("factory"));
			apparatus.setType((String)listOrderedMap.get("type"));
			apparatus.setModel((String)listOrderedMap.get("model"));
			apparatus.setState((String)listOrderedMap.get("state"));
			apparatus.setDept_id((String)listOrderedMap.get("dept_id"));
			apparatus.setArea_id((String)listOrderedMap.get("area_id"));
			list.add(apparatus);
		}
		request.setAttribute("tawApparatusList", list);
		request.setAttribute("resultSize", list.size());
		return mapping.findForward("tawApparatusList");
	}
}
