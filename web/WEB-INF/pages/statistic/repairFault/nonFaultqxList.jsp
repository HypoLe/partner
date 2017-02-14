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
		<bean:define id="url" value="repairFault.do" />
		<form id="testform" name="testform" action="${app}/activiti/statistics/repairFault.do?method=nonFaultqxList" method="post">	
		<display:table name="nonFaultqxList" cellspacing="0" cellpadding="0"
			id="nonFaultqxList" pagesize="${pageSize}" class="listTable nonFaultqxList"
			export="true" requestURI="repairFault.do" sort="list"
			size="total" partialList="true">
			<display:column sortable="false" headerClass="sortable" title="区县"  property="city" />
			<display:column sortable="false" headerClass="sortable" title="非故障触发工单数"  property="nonFaultNum" />
			<display:column sortable="false" headerClass="sortable" title="非故障触发材料金额"  property="nonFaultAmout" />
			<display:column sortable="false" headerClass="sortable" title="更换电杆数量"  property="genghuandianganNum" />
			<display:column sortable="false" headerClass="sortable" title="更换电杆占比"  property="genghuandianganRate" />
			<display:column sortable="false" headerClass="sortable" title="更换吊线数量"  property="genghuandiaoxianNum" />
			<display:column sortable="false" headerClass="sortable" title="更换吊线占比"  property="genghuandiaoxianRate" />
			<display:column sortable="false" headerClass="sortable" title="更换拉线数量"  property="genghuanlaxianNum" />
			<display:column sortable="false" headerClass="sortable" title="更换拉线占比"  property="genghuanlaxianRate" />
			<display:column sortable="false" headerClass="sortable" title="光缆脱落整治数量"  property="guanglantuoluoNum" />
			
			<display:column sortable="false" headerClass="sortable" title="光缆脱落整治占比"  property="guanglantuoluoRate" />
			<display:column sortable="false" headerClass="sortable" title="电力线防护数量"  property="dianlixianNum" />
			<display:column sortable="false" headerClass="sortable" title="电力线防护占比"  property="dianlixianRate" />
			<display:column sortable="false" headerClass="sortable" title="光交箱整治数量"  property="guangjiaoxiangNum" />
			<display:column sortable="false" headerClass="sortable" title="光交箱整治占比"  property="guangjiaoxiangRate" />
			<display:column sortable="false" headerClass="sortable" title="人井盖增补数量"  property="renjinggaiNum" />
			<display:column sortable="false" headerClass="sortable" title="人井盖增补占比"  property="renjinggaiRate" />
			<display:column sortable="false" headerClass="sortable" title="其他安全隐患处理数量"  property="otherNum" />
			
			<display:column sortable="false" headerClass="sortable" title="其他安全隐患处理占比"  property="otherRate" />
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
		<%@ include file="/common/footer_eoms.jsp"%>