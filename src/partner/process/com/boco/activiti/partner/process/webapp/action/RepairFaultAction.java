package com.boco.activiti.partner.process.webapp.action;

import java.text.DateFormat;
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
import com.boco.activiti.partner.process.model.FaultType;
import com.boco.activiti.partner.process.model.NonFaultType;

import com.boco.activiti.partner.process.model.SchemeRateRejectModel;
import com.boco.activiti.partner.process.service.IRepairFaultService;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;

public class RepairFaultAction  extends SheetAction {
	/**
	 * 地市故障类型统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public ActionForward faultTypeListPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		String startTime=StaticMethod.nullObject2String(request.getParameter("startTime"));
		String endTime=StaticMethod.nullObject2String(request.getParameter("endTime"));
		String type=StaticMethod.nullObject2String(request.getParameter("type"));
		IRepairFaultService repairFaultService = (IRepairFaultService) getBean("repairFaultService");
		List<FaultType> repairFaultList=null;
		String size="";
		if("1".equals(type)){
			Map<String,Object> map=repairFaultService.faultTypeListPage(startTime,endTime);
			size=map.get("size").toString();
			repairFaultList=(List<FaultType>)map.get("repairFaultList");
			
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
		request.setAttribute("repairFaultList", repairFaultList);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("pageSize", size);
		request.setAttribute("total",Integer.parseInt(size));
		return mapping.findForward("repairFaultList");
	}
	/**
	 * 区县故障类型统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public ActionForward repairFaultqxList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String city = StaticMethod.nullObject2String(request.getParameter("city"));
		String startTime=StaticMethod.nullObject2String(request.getParameter("startTime"));
		String endTime=StaticMethod.nullObject2String(request.getParameter("endTime"));
		String themeinterface=StaticMethod.nullObject2String(request.getParameter("themeinterface"));
		
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		
		IRepairFaultService repairFaultService = (IRepairFaultService) getBean("repairFaultService");
		Map<String,Object> map=repairFaultService.repairFaultqxList(city,startTime,endTime);
		String size=map.get("size").toString();
		List<FaultType> repairFaultqxList=(List<FaultType>)map.get("repairFaultqxList");
		//request.setAttribute("size", size);
		request.setAttribute("repairFaultqxList", repairFaultqxList);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("pageSize", size);
		request.setAttribute("total",Integer.parseInt(size));
		return mapping.findForward("repairFaultqxList");
		
		
	}
	/**
	 * 城市非故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public ActionForward nonFaultcsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String startTime=StaticMethod.nullObject2String(request.getParameter("startTime"));
		String endTime=StaticMethod.nullObject2String(request.getParameter("endTime"));
		String type=StaticMethod.nullObject2String(request.getParameter("type"));
		IRepairFaultService repairFaultService = (IRepairFaultService) getBean("repairFaultService");
		List<NonFaultType> nonFaultcsList=null;
		String size="";
		if("1".equals(type)){
			Map<String,Object> map=repairFaultService.nonFaultcsList(startTime,endTime);
			 size=map.get("size").toString();
			 nonFaultcsList=(List<NonFaultType>)map.get("nonFaultcsList");
			
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
		request.setAttribute("nonFaultcsList", nonFaultcsList);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("pageSize", size);
		request.setAttribute("total",Integer.parseInt(size));
		return mapping.findForward("nonFaultcsList");
	}
	/**
	 * 区县非故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public ActionForward nonFaultqxList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String city = StaticMethod.nullObject2String(request.getParameter("city"));
		String startTime=StaticMethod.nullObject2String(request.getParameter("startTime"));
		String endTime=StaticMethod.nullObject2String(request.getParameter("endTime"));
		IRepairFaultService repairFaultService = (IRepairFaultService) getBean("repairFaultService");
		Map<String,Object> map=repairFaultService.nonFaultqxList(city,startTime,endTime);
		String size=map.get("size").toString();
		List<NonFaultType> nonFaultqxList=(List<NonFaultType>)map.get("nonFaultqxList");
		//request.setAttribute("size", size);
		request.setAttribute("nonFaultqxList", nonFaultqxList);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("pageSize", size);
		request.setAttribute("total",Integer.parseInt(size));
		return mapping.findForward("nonFaultqxList");
	}
}
