<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.boco.eoms.partner.home.mgr.PublishMgr"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.boco.eoms.partner.home.model.PublishInfo"%>
<%@page import="org.jdom.Element"%>
<%@page import="com.boco.eoms.commons.util.xml.XMLProperties"%>
<%@page import="com.boco.eoms.commons.util.xml.XmlManage"%>
<%@page import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm"%>
<%@page import="com.boco.eoms.partner.netresource.action.PnrMainPageAction"%>
<%@ include file="/common/taglibs.jsp"%>

<% String type = request.getParameter("type"); %>

<%-- 加载首页公告数据 begin--%>
<%
	if("publish".equals(type)) {
%>
<%
	ApplicationContext ctx = (ApplicationContext)request.getAttribute("applicationContext");
	PublishMgr publishMgr = (PublishMgr) ctx.getBean("refpublishMgr");
	ArrayList<String> pubList = new ArrayList<String>();
	for (PublishInfo info : publishMgr.getNewInfo(request)) {
		pubList.add("<a target='_blank' href='home/publish.do?method=getGsp&pageName=detail&isRead=0&id="
							+info.getId()+"'>"+info.getSubject()+"</a>");
	}
	request.setAttribute("pubList",pubList);
%>
	<c:if test="${!empty requestScope.pubList[0]}">
		<marquee behavior="scroll" scrolldelay="200" direction="up" width="100%" height="100%" onmouseover="this.stop()"  onmouseout="this.start()">
			<ul class="sbr-box-ul">
				<c:forEach var="info" items="${requestScope.pubList}" varStatus="s">
					<li style="">
						${s.index+1 }：${info }
					</li>
				</c:forEach>
			</ul>
		</marquee>	
	</c:if>
	<c:if test="${empty requestScope.pubList[0]}">
		<div style="margin:5px;color:red;font-size:12px;text-align:center;text-align: center;height: 140px;line-height: 140px;">
				无未读公告信息!
		</div>
	</c:if>
<%		
	}
%>
<%-- 加载首页公告数据 end--%>





<%-- 代维处理人员工单统计 begin--%>
<%
	TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
	String deptId = sessionInfo.getDeptid();
	String userType = deptId.substring(0,1);
	if("sheet_statistics".equals(type) && "2".equals(userType)) {
%>
<%
		//XMLProperties xml = XmlManage.getFile("/com/boco/eoms/partner/dataSynch/config/dataSyncConstant.xml");
		//String LOCAL_IP = xml.getElement().getChild("LOCAL_IP").getText().trim();
		String userId = sessionInfo.getUserid();
		String sql = "select pro_get_areaname(province) as province,"+
				        " province as province_id, "+
				        " pro_get_areaname(city) as city, "+
				        " city as city_id, "+
				        " pro_get_areaname(country) as country,"+
				        " country as country_id,"+
				        " pro_get_deptname(groupdeptid) as groupdept,"+
				        " groupdeptid as groupdept_id,"+
				        " pro_get_username(dealuserid) as dealuser,"+
				        " dealuserid as dealuser_id,"+
				        " holdyear,"+
				        " holdhalfyear,"+
				        " holdquarter,"+
				        " holdmonth,"+
				        " holdday,"+
				        " pro_get_dictname(holdstatisfied) as holdstatisfied,"+
				        " holdstatisfied as holdstatisfied_id,"+
				        " count(*) as sheetcount,"+
				        " round((count(*)-sum(timelyflag))/count(*)*100,2) || '%' as intime_deg,"+
				        " round(sum(qualifiedflag)/count(*)*100,2) || '%' as qualified_deg"+
				 " from pnr_metering_main "+
				 " group by province,city,country,groupdeptid,dealuserid,"+
				                                   "holdyear,holdhalfyear,holdquarter,holdmonth,holdday,holdstatisfied"+
				 " having dealuserid = '"+userId+"'"+
				 " order by province,city,country,groupdept,dealuser,"+
				                                   " holdyear,holdhalfyear,holdquarter,holdmonth,holdday,sheetcount,intime_deg,qualified_deg,holdstatisfied";
		PnrMainPageAction ppa = new PnrMainPageAction();
		List<Map<String,Object>> dataList = ppa.getDataListBySQL(sql);
		request.setAttribute("dataList",dataList);
%>
	<c:if test="${!empty requestScope.dataList[0]}">
		<table width="98%" height="92%" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<th class="title">省</th>
					<th class="title">地市</th>
					<th class="title">区县</th>
					<th class="title">代维小组</th>
					<th class="title">处理人员</th>
					<th class="title">归档年</th>
					<th class="title">归档半年</th>
					<th class="title">归档季度</th>
					<th class="title">归档月</th>
					<th class="title">归档日</th>
					<th class="title">满意度</th>
					<th class="title">事件数量</th>
					<th class="title">及时率</th>
					<!-- 
					<th class="title">合格率</th>
					 -->
				</tr>
				<c:forEach var="data" items="${requestScope.dataList}" varStatus="s">
					<tr>
						<td class="title">${data['province']}</td>
						<td class="title">${data['city']}</td>
						<td class="title">${data['country']}</td>
						<td class="title">${data['groupdept']}</td>
						<td class="title">${data['dealuser']}</td>
						<td class="title">${data['holdyear']}</td>
						<td class="title">${data['holdhalfyear']}</td>
						<td class="title">${data['holdquarter']}</td>
						<td class="title">${data['holdmonth']}</td>
						<td class="title">${data['holdday']}</td>
						<td class="title">${data['holdstatisfied']}</td>
						<td class="title"><a style="color:red" href="${app}/partner/mainPage/pnrHomePageAction.do?method=goToSheetStatisticsDetailPage&province=${data['province_id']}&city=${data['city_id']}&country=${data['country_id']}&groupdept=${data['groupdept_id']}&dealuser=${data['dealuser_id']}&holdyear=${data['holdyear']}&holdhalfyear=${data['holdhalfyear']}&holdquarter=${data['holdquarter']}&holdmonth=${data['holdmonth']}&holdday=${data['holdday']}&holdstatisfied=${data['holdstatisfied_id']}" target="_blank">${data['sheetcount']}</a></td>
						<td class="title">${data['intime_deg']}</td>
						<!-- 
						<td class="title">${data['qualified_deg']}</td>
						 -->
					</tr>
				</c:forEach>
			</thead>
			<tbody>
			</tbody>
		</table>	
	</c:if>
	<c:if test="${empty dataList}">
		<div style="margin:5px;color:red;font-size:12px;text-align:center;text-align: center;height: 140px;line-height: 140px;">
				无统计数据!
		</div>
	</c:if>
<%
	}
%>
<%-- 代维处理人员工单统计 end--%>


<%-- 移动人员工单统计 end--%>
<%
	if("sheet_statistics".equals(type) && "1".equals(userType)) {
%>
<%
		//XMLProperties xml = XmlManage.getFile("/com/boco/eoms/partner/dataSynch/config/dataSyncConstant.xml");
		//String LOCAL_IP = xml.getElement().getChild("LOCAL_IP").getText().trim();
		String userId = sessionInfo.getUserid();
		String sql = "select pro_get_areaname(province) as province,"+
				        " province as province_id, "+
				        " pro_get_areaname(city) as city, "+
				        " city as city_id, "+
				        " pro_get_areaname(country) as country,"+
				        " country as country_id,"+
				        " pro_get_deptname(groupdeptid) as groupdept,"+
				        " groupdeptid as groupdept_id,"+
				        " pro_get_username(dealuserid) as dealuser,"+
				        " dealuserid as dealuser_id,"+
				        " holdyear,"+
				        " holdhalfyear,"+
				        " holdquarter,"+
				        " holdmonth,"+
				        " holdday,"+
				        " pro_get_dictname(holdstatisfied) as holdstatisfied,"+
				        " holdstatisfied as holdstatisfied_id,"+
				        " count(*) as sheetcount,"+
				        " round((count(*)-sum(timelyflag))/count(*)*100,2) || '%' as intime_deg,"+
				        " round(sum(qualifiedflag)/count(*)*100) || '%' as qualified_deg"+
				 " from pnr_metering_main "+
				 " group by province,city,country,groupdeptid,dealuserid,"+
				                                   "holdyear,holdhalfyear,holdquarter,holdmonth,holdday,holdstatisfied"+
				 " order by province,city,country,groupdept,dealuser,"+
				                                   " holdyear,holdhalfyear,holdquarter,holdmonth,holdday,sheetcount,intime_deg,qualified_deg,holdstatisfied";
		PnrMainPageAction ppa = new PnrMainPageAction();
		List<Map<String,Object>> dataList = ppa.getDataListBySQL(sql);
		request.setAttribute("dataList",dataList);
%>
	<c:if test="${!empty requestScope.dataList[0]}">
		<table width="98%" height="92%" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<th class="title">省</th>
					<th class="title">地市</th>
					<th class="title">区县</th>
					<th class="title">代维小组</th>
					<th class="title">处理人员</th>
					<th class="title">归档年</th>
					<th class="title">归档半年</th>
					<th class="title">归档季度</th>
					<th class="title">归档月</th>
					<th class="title">归档日</th>
					<th class="title">满意度</th>
					<th class="title">事件数量</th>
					<th class="title">及时率</th>
					<!--
					<th class="title">合格率</th>
					 -->
				</tr>
				<c:forEach var="data" items="${requestScope.dataList}" varStatus="s">
					<tr>
						<td class="title">${data['province']}</td>
						<td class="title">${data['city']}</td>
						<td class="title">${data['country']}</td>
						<td class="title">${data['groupdept']}</td>
						<td class="title">${data['dealuser']}</td>
						<td class="title">${data['holdyear']}</td>
						<td class="title">${data['holdhalfyear']}</td>
						<td class="title">${data['holdquarter']}</td>
						<td class="title">${data['holdmonth']}</td>
						<td class="title">${data['holdday']}</td>
						<td class="title">${data['holdstatisfied']}</td>
						<td class="title"><a style="color:red" href="${app}/partner/mainPage/pnrHomePageAction.do?method=goToSheetStatisticsDetailPage&province=${data['province_id']}&city=${data['city_id']}&country=${data['country_id']}&groupdept=${data['groupdept_id']}&dealuser=${data['dealuser_id']}&holdyear=${data['holdyear']}&holdhalfyear=${data['holdhalfyear']}&holdquarter=${data['holdquarter']}&holdmonth=${data['holdmonth']}&holdday=${data['holdday']}&holdstatisfied=${data['holdstatisfied_id']}" target="_blank">${data['sheetcount']}</a></td>
						<td class="title">${data['intime_deg']}</td>
						<!--
						<td class="title">${data['qualified_deg']}</td>
						-->
					</tr>
				</c:forEach>
			</thead>
			<tbody>
			</tbody>
		</table>	
	</c:if>
	<c:if test="${empty dataList}">
		<div style="margin:5px;color:red;font-size:12px;text-align:center;text-align: center;height: 140px;line-height: 140px;">
				无统计数据!
		</div>
	</c:if>
<%
	}
%>
<%-- 移动人员工单统计 end--%>

