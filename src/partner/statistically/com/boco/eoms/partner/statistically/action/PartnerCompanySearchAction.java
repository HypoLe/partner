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
import com.boco.eoms.partner.baseinfo.model.PartnerDept;

public class PartnerCompanySearchAction extends BaseAction{
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
		if(paramMap.containsKey("d0aptitudetypelikedict")){
			search+= " and d.aptitude='"+new String(((String[])paramMap.get("d0aptitudetypelikedict"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
		}
		String searchSql="select d.* from pnr_dept d, "+
							" (select name,id,deleted,ifcompany from pnr_dept where id = interface_head_id and ifcompany='yes'and organizationno is not null) as dt  "+
							" where  dt.id=d.interface_head_id  and d.deleted='0' and dt.deleted='0'   and d.ifcompany==dt.ifcompany  " +
				search;
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
			PartnerDept  dept= new PartnerDept();
			dept.setId((String)listOrderedMap.get("id"));
			dept.setName((String)listOrderedMap.get("name"));
			dept.setManager((String)listOrderedMap.get("manager"));
			dept.setAreaName((String)listOrderedMap.get("area_name"));
			dept.setPhone((String)listOrderedMap.get("phone"));
			dept.setAptitude((String)listOrderedMap.get("aptitude"));
			dept.setDeadline((String)listOrderedMap.get("deadline"));
			dept.setBank((String)listOrderedMap.get("bank"));
			dept.setAccount((String)listOrderedMap.get("account"));
			list.add(dept);
		}
		request.setAttribute("partnerDeptList", list);
		request.setAttribute("resultSize", list.size());
		return mapping.findForward("companyList");
	}
}
