<%@ page language="java" import="java.util.*,com.boco.eoms.commons.job.model.TawCommonsJobsubscibe,com.boco.eoms.commons.job.service.ITawCommonsJobsubscibeManager,com.boco.eoms.commons.mms.mmsreporttemplate.model.MmsreportTemplate,com.boco.eoms.commons.mms.mmsreporttemplate.mgr.*,com.boco.eoms.commons.mms.mmsreporttemplate.model.*,com.boco.eoms.commons.mms.base.config.*,com.boco.eoms.commons.mms.base.util.*,com.boco.eoms.base.util.StaticMethod,com.boco.eoms.commons.statistic.base.reference.*" pageEncoding="UTF-8"%>
<%@page import="com.boco.eoms.commons.mms.msssubscribe.util.MsssubscribeDisplaytagDecoratorHelper"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
	
	function closeWindow()
	{
		//alert('closeWindow');
		window.close();
	}

</script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>彩信报预览</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%
		String previewStr = "没有配置预览";
	 %>

<%
	//定制彩信报的id
	String mmsreport_templateId = request.getParameter("mmsreport_templateId");
	//接收人
	String[] receivePerson = request.getParameter("receivePerson").split(",");
	//接收时间（小时）
	String receiveTime_hour = request.getParameter("receiveTime_hour");
	//接收时间（小分钟）
	String receiveTime_min = request.getParameter("receiveTime_min");
	
	//如果没有系统没有com.boco.eoms.base.util.ApplicationContextHolder的情况需要把InitStaticBaseApplicationContextServlet配置到web.xml中
	if (ApplicationContextHolder.getInstance().getCtx() == null) 
	{
		ApplicationContextHolder.getInstance().setCtx(com.boco.eoms.base.util.ApplicationContextHolder.getInstance().getCtx());
	}
	MmsreportTemplateMgr mmsreportTemplateMgr = (MmsreportTemplateMgr)ApplicationContextHolder.getInstance().getBean("mmsreportTemplateMgr");
	MmsreportTemplate mmsreportTemplate = mmsreportTemplateMgr.getMmsreportTemplate(mmsreport_templateId);
	
	//彩信报名称
	String mmsName = mmsreportTemplate.getMmsName();
	//报表类型(执行周期)
	String jobid = mmsreportTemplate.getJobid();
	ITawCommonsJobsubscibeManager tawCommonsJobsubscibeManager = (ITawCommonsJobsubscibeManager)ApplicationContextHolder.getInstance().getBean("ItawCommonsJobsubscibeManager");
	TawCommonsJobsubscibe tawCommonsJobsubscibe = tawCommonsJobsubscibeManager.getTawCommonsJobsubscibeForSubID(jobid);
	String jobCycle = tawCommonsJobsubscibe.getJobCycle();

	String executeCycle = mmsreportTemplate.getExecuteCycle();
	String displayStr = "";
	if("monthReport".equalsIgnoreCase(executeCycle))
   	{
   		String d = jobCycle.substring(6,7);
		if(!" ".equalsIgnoreCase(jobCycle.substring(7,8)))
		{
			d = jobCycle.substring(6,8);
		}
   		displayStr = " 月报表-每月" + d + "号发送";
   		
   	}
   	else if("weekReport".equalsIgnoreCase(executeCycle))
   	{
   		String d = jobCycle.substring(10).trim();
   		if(d.equalsIgnoreCase("MON"))
   			d = "周一";
   		else if(d.equalsIgnoreCase("TUE"))
   			d = "周二";
   		else if(d.equalsIgnoreCase("WED"))
   			d = "周三";
   		else if(d.equalsIgnoreCase("THU"))
   			d = "周四";
   		else if(d.equalsIgnoreCase("FRI"))
   			d = "周五";
   		else if(d.equalsIgnoreCase("SAT"))
   			d = "周六";
   		else if(d.equalsIgnoreCase("SUN"))
   			d = "周日";
   		
   	   	displayStr = " 周报表-每周" + d + "发送";
   	}
   	else if("dailyReport".equalsIgnoreCase(executeCycle))
   	{
   		displayStr = " 日报表-每天发送";
   	}
	//接收时间
	String receiveTime = receiveTime_hour + "点" + receiveTime_min + "分";
	//所有报表id（包括：名称，指标等信息（一个或多个报表））
	String configpath = MMSConstants.REPORT_CONFIG;
	Reports reports = (Reports) ParseXmlService.create().xml2object(
			Reports.class, StaticMethod.getFilePathForUrl(configpath));
	String[] statReportids = mmsreportTemplate.getStatReportId().split(",");
	//reports.getSheetById("");
 %>
  </head>
  
  <body>

	<table class="formTable">
		<tr class="tr_show" >
			<td class="label" colspan="2">彩信报预览</td>
		</tr>
		
		<tr class="tr_show">
			<td class="label">彩信报名称:</td>
			<td><%=mmsName %></td>
		</tr>
		
		<tr class="tr_show">
			<td class="label">彩信报类型:</td>
			<td><%=displayStr %></td>
		</tr>
		
		<tr class="tr_show">
			<td class="label">彩信报接收人:</td>
			<td><%=MsssubscribeDisplaytagDecoratorHelper.getReceivePerson(receivePerson) %></td>
		</tr>
		
		<tr class="tr_show">
			<td class="label">彩信报接收时间:</td>
			<td><%=receiveTime %></td>
		</tr>
		
		<tr class="tr_show">
			<td class="label">彩信报预览大概样式:</td>
			<td>
				<%
					for(int i=0;i<statReportids.length;i++)
					{
						com.boco.eoms.commons.mms.base.config.Sheet sheet = reports.getSheetById(statReportids[i]);
						//报表名称
						String reportName = sheet.getName();
						//报表类型
						String reportType = sheet.getType();
						//定制的指标
						Preview preview = sheet.getPreview();
						if(preview == null)
						{
							continue;
						}
						previewStr = "";
						String[] targets = preview.getTarget();
						String target = "";
						for(int j=0;j<targets.length;j++)
						{
							target += targets[j];
							if(j != targets.length-1)
							{
								target += ",";
							}
						}
				%>
				<table>
					<tr>
						<td>报表名称：<%=reportName %></td>
					</tr>
					
					<tr>
						<td>包含指标：<%=target %></td>
					</tr>
					
					<tr>
						<td width="300" align="center">
							<%
								String imagePath = request.getContextPath() +"/mms/preview/pic/";
								if("column".equalsIgnoreCase(reportType))
								{
									imagePath += "colum.gif";
								}
								if("line".equalsIgnoreCase(reportType))
								{
									imagePath += "line.gif";
								}
								else if("stat".equalsIgnoreCase(reportType))
								{
									imagePath += "table.gif";
								}
								else if("txt".equalsIgnoreCase(reportType))
								{
									imagePath += "txt.gif";
								}
							 %>
							 <img width="170" height="250" src="<%=imagePath %>"></img></br>
						</td>
					</tr>
				</table>
				
				<%
					}
				 %>
				 
				<%=previewStr %>
			</td>
		</tr>
	</table>
	
	<input class="btn" type="button" onclick="closeWindow()" value="关闭">
	
  </body>
</html>

<%@ include file="/common/footer_eoms.jsp"%>
