package com.boco.activiti.partner.process.webapp.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.model.FaultStatModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IFaultStatService;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;

public class FaultStatAction extends SheetAction {
	 /**
	  * 地市故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public ActionForward faultStatByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String startTime=StaticMethod.nullObject2String(request.getParameter("startTime"));
		String endTime=StaticMethod.nullObject2String(request.getParameter("endTime"));
		String type=StaticMethod.nullObject2String(request.getParameter("type"));
		IFaultStatService faultStatService = (IFaultStatService) getBean("faultStatService");
		List<FaultStatModel> list=null;
		String size="";
		if("1".equals(type)){
			Map<String,Object> faultStatMap=faultStatService.faultStatCityList(startTime, endTime);	
			 list=(List<FaultStatModel>) faultStatMap.get("faultStatCityList");
			 size=faultStatMap.get("size").toString();
		}else{
			 size="0";
		}
		if("".equals(startTime) && "".equals(endTime)){
			Date day=new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM"); 
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd"); 
			startTime=df.format(day)+"-01";
			endTime=df2.format(day);
		}
		
		request.setAttribute("faultStatCityList", list);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("pageSize", size);
		request.setAttribute("total",Integer.parseInt(size));
		return mapping.findForward("faultStatCityList");
		
	}
	 /**
	  * 区县故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public ActionForward faultStatByCounty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String startTime=StaticMethod.nullObject2String(request.getParameter("startTime"));
		String endTime=StaticMethod.nullObject2String(request.getParameter("endTime"));
		String city=StaticMethod.nullObject2String(request.getParameter("city"));
		IFaultStatService faultStatService = (IFaultStatService) getBean("faultStatService");
		Map<String,Object> faultStatMap=faultStatService.faultStatCountyList(startTime, endTime,city);
		List<FaultStatModel> list=(List<FaultStatModel>) faultStatMap.get("faultStatCountyList");
		String size=faultStatMap.get("size").toString();
		request.setAttribute("faultStatCountyList", list);
		request.setAttribute("faultStatCountyList", list);
		request.setAttribute("cityId", city);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("pageSize", size);
		request.setAttribute("total",Integer.parseInt(size));
		return mapping.findForward("faultStatCountyList");
		
	}
	
	 /**
	  * 超时工单数量查询
	  * @author xujinliang 
	  * @title: timeoutGongdanList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public ActionForward timeoutGongdanList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String type=StaticMethod.nullObject2String(request.getParameter("type"));
		String city=StaticMethod.nullObject2String(request.getParameter("city"));
		String cityId=StaticMethod.nullObject2String(request.getParameter("cityId"));
		String startTime=StaticMethod.nullObject2String(request.getParameter("startTime"));
		String endTime=StaticMethod.nullObject2String(request.getParameter("endTime"));
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+city);
		IFaultStatService faultStatService = (IFaultStatService) getBean("faultStatService");
		Map<String,Object> faultStatMap=faultStatService.timeoutGongdanList(type,city,startTime,endTime,cityId);
		List<TaskModel> list=(List<TaskModel>) faultStatMap.get("taskList");
		String size=faultStatMap.get("size").toString();
		request.setAttribute("taskList", list);
		request.setAttribute("pageSize", size);
		request.setAttribute("type", type);
		request.setAttribute("total",Integer.parseInt(size));
		return mapping.findForward("timeoutGongdanList");
		
	}
}
