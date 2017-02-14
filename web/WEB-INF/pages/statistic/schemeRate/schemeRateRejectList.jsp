<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>

<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/"; 
		%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>		
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
</script>
		<bean:define id="url" value="schemeRateStat.do" />
		<form id="testform" name="testform" action="${app}/activiti/statistics/schemeRateStat.do?method=schemeRateRejectList" method="post">	
		<display:table name="schemeRateRejectModelList" cellspacing="0" cellpadding="0"
			id="schemeRateRejectModelList" pagesize="${pageSize}" class="listTable schemeRateRejectModelList"
			export="true" requestURI="schemeRateStat.do" sort="list"
			size="total" partialList="true">

			<display:column sortable="false" headerClass="sortable" title="区县">
				<eoms:id2nameDB id="${schemeRateRejectModelList.county}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="工单类型">
				<c:choose>
					<c:when test="${schemeRateRejectModelList.themeinterface eq 'interface'}">
						本地网
					</c:when>
					<c:when test="${schemeRateRejectModelList.themeinterface eq 'overhaul'||schemeRateRejectModelList.themeinterface eq 'reform'}">
						大修改造
					</c:when>
					<c:when test="${schemeRateRejectModelList.themeinterface eq 'arteryPrecheck'}">
						干线
					</c:when>
				</c:choose>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="工单主题"  property="theme" />
			<display:column sortable="false" headerClass="sortable" title="退单原因"  property="handleDescription" />
			<display:column sortable="false" headerClass="sortable" title="流程ID"  property="processInstanceId" />
			<display:column sortable="false" headerClass="sortable" title="退单账号"  property="auditor" />
			
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
		<%@ include file="/common/footer_eoms.jsp"%>