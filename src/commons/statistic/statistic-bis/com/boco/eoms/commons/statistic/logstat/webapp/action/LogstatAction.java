package com.boco.eoms.commons.statistic.logstat.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.commons.statistic.base.webapp.action.IStatMethod;
import com.boco.eoms.commons.statistic.base.webapp.action.StatAction;

public class LogstatAction extends StatAction{
	 public ActionForward performStatistic(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception{		
			IStatMethod statMethod = (IStatMethod) getBean(mapping.getAttribute());
//			String servletPath = getServlet().getServletContext().getRealPath("");//WebServer path		
			statMethod.performStatistic(mapping, form, request, response);
			
			String reportFromType = String.valueOf(request.getAttribute("reportFromType"));
			//liquan add
			String reportIndex = String.valueOf(request.getAttribute("reportIndex"));
			String excelConfigURL = String.valueOf(request.getAttribute("excelConfigURL"));
			//end
			ActionForward actionForward = null;
			if("StatFrom".equalsIgnoreCase(reportFromType))
			{   //liquan modify
				if("logstat_all".equals(excelConfigURL)){
					if("0".equals(reportIndex)){
						actionForward = mapping.findForward("firstresult");	
					}else if("1".equals(reportIndex)){
						actionForward = mapping.findForward("fifthresult");	
					}else if("2".equals(reportIndex)){
						actionForward = mapping.findForward("fourthresult");	
					 }else if("3".equals(reportIndex)){
						 actionForward = mapping.findForward("sixthresult");	
					 }else if("5".equals(reportIndex)){
						actionForward = mapping.findForward("fourthfifresult");	
					 }else if("6".equals(reportIndex)){
						 actionForward = mapping.findForward("sixthfifresult");	
					 }else{
					actionForward = mapping.findForward("statisticresult");	
					 }	
				}else{
					actionForward = mapping.findForward("statisticresult");	
				}
				
			}
			else if("graphicsFrom".equalsIgnoreCase(reportFromType))
			{
				//graphicsFrom 閸ユ儳鑸伴幎銉ㄣ��
				actionForward = mapping.findForward("showGraphicsStatisticPage");
			}
			else if("StatFrom_graphicsFrom".equalsIgnoreCase(reportFromType))
			{
				//閸氬本妞傞弰鍓с仛缂佺喕顓搁崪灞芥禈瑜般垺濮ょ悰锟�
				actionForward = mapping.findForward("showStatAndGraphicsStatisticPage");
			}
	        return actionForward;
			
		}
}
