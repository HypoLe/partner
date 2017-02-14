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
import com.boco.eoms.partner.baseinfo.model.PartnerUser;

public class PartnerStaffSearchAction extends BaseAction{
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
		if(paramMap.containsKey("u0diplomatypelikedict")){
			search+= " and u.diploma='"+new String(((String[])paramMap.get("u0diplomatypelikedict"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
			
		}
		if(paramMap.containsKey("u0sextypelikedict")){
			search+= " and u.sex='"+new String(((String[])paramMap.get("u0sextypelikedict"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
			
		}
		
		String searchSql="select u.* from  pnr_user u,pnr_dept d,(select name,id,deleted from pnr_dept where id = interface_head_id) as dt " +
				" where u.partnerid=d.id and dt.id=d.interface_head_id  and d.deleted='0' and dt.deleted='0' and u.deleted='0' " +
				search;
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
			PartnerUser  staff= new PartnerUser();
			staff.setId((String)listOrderedMap.get("id"));
			staff.setName((String)listOrderedMap.get("name"));
			staff.setSex((String)listOrderedMap.get("sex"));
			staff.setAreaName((String)listOrderedMap.get("area_name"));
			staff.setPersonCardNo((String)listOrderedMap.get("person_card_no"));
			staff.setDeptName((String)listOrderedMap.get("dept_name"));
			staff.setPhone((String)listOrderedMap.get("phone"));
			staff.setEmail((String)listOrderedMap.get("email"));
			staff.setPost((String)listOrderedMap.get("post"));
			list.add(staff);
		}
		request.setAttribute("staffList", list);
		request.setAttribute("resultSize", list.size());
		return mapping.findForward("staffList");
	}
}
