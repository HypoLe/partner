package com.boco.eoms.partner.statistically.action;

import java.util.ArrayList;
import java.util.Date;
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
import com.boco.eoms.partner.baseinfo.model.TawPartnerCar;

public class PartnerCarSearchAction extends BaseAction{
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
		if(paramMap.containsKey("c0usetypelikedict")){
			search+= " and c.use='"+new String(((String[])paramMap.get("c0usetypelikedict"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
			
		}
		
		
		String searchSql="select c.* from  pnr_dept as d, pnr_partner_car c  , (select name,id,deleted from pnr_dept where id = interface_head_id) as dt "+ 
		"where d.id =c.partnerid and  d.deleted='0' and dt.deleted='0'and  c.bigpartnerid=dt.id" +
				search;
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
			TawPartnerCar  PartnerCar= new TawPartnerCar();
			PartnerCar.setId((String)listOrderedMap.get("id"));
			PartnerCar.setCar_number((String)listOrderedMap.get("car_number"));
			PartnerCar.setFactory((String)listOrderedMap.get("factory"));
			PartnerCar.setStart_time((Date)listOrderedMap.get("start_time"));
			PartnerCar.setDept_id((String)listOrderedMap.get("dept_id"));
			PartnerCar.setArea_id((String)listOrderedMap.get("area_id"));
			list.add(PartnerCar);
		}
		request.setAttribute("tawPartnerCarList", list);
		request.setAttribute("resultSize", list.size());
		return mapping.findForward("partnerCarList");
	}
}
