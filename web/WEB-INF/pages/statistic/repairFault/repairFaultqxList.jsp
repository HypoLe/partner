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
		<form id="testform" name="testform" action="${app}/activiti/statistics/repairFault.do?method=repairFaultqxList" method="post">	
		<display:table name="repairFaultqxList" cellspacing="0" cellpadding="0"
			id="repairFaultqxList" pagesize="${pageSize}" class="listTable repairFaultqxList"
			export="true" requestURI="repairFault.do" sort="list"
			size="total" partialList="true">
			<display:column sortable="false" headerClass="sortable" title="区县"  property="city" />
			<display:column sortable="false" headerClass="sortable" title="线路故障总数"  property="totalFaultNum" />
			<display:column sortable="false" headerClass="sortable" title="金额"  property="totalFaultAmout" />
			<display:column sortable="false" headerClass="sortable" title="车挂数量"  property="cheguaNum" />
			<display:column sortable="false" headerClass="sortable" title="车挂占比"  property="cheguaRate" />
			<display:column sortable="false" headerClass="sortable" title="外力施工数量"  property="wailiNum" />
			<display:column sortable="false" headerClass="sortable" title="外力施工占比"  property="wailiRate" />
			<display:column sortable="false" headerClass="sortable" title="火烧数量"  property="huoshaoNum" />
			<display:column sortable="false" headerClass="sortable" title="火烧占比"  property="huoshaoRate" />
			<display:column sortable="false" headerClass="sortable" title="被盗或人为破坏数量"  property="renweiNum" />
			
			<display:column sortable="false" headerClass="sortable" title="被盗或人为破坏占比"  property="renweiRate" />
			<display:column sortable="false" headerClass="sortable" title="自然断纤（纤芯裂化）数量"  property="duanxianNum" />
			<display:column sortable="false" headerClass="sortable" title="自然断纤（纤芯裂化）占比"  property="duanxianRate" />
			<display:column sortable="false" headerClass="sortable" title="接头盒进水数量"  property="jinshuiNum" />
			<display:column sortable="false" headerClass="sortable" title="接头盒进水占比"  property="jinshuiRate" />
			<display:column sortable="false" headerClass="sortable" title="尾纤及法兰盘数量"  property="weixianNum" />
			<display:column sortable="false" headerClass="sortable" title="尾纤及法兰盘占比"  property="weixianRate" />
			<display:column sortable="false" headerClass="sortable" title="鸟啄数量"  property="niaozhuoNum" />
			
			<display:column sortable="false" headerClass="sortable" title="鸟啄占比"  property="niaozhuoRate" />
			<display:column sortable="false" headerClass="sortable" title="鼠咬数量"  property="shuyaoNum" />
			<display:column sortable="false" headerClass="sortable" title="鼠咬占比"  property="shuyaoRate" />
			<display:column sortable="false" headerClass="sortable" title="自然灾害数量"  property="zaihaiNum" />
			<display:column sortable="false" headerClass="sortable" title="自然灾害占比"  property="zaihaiRate" />
			<display:column sortable="false" headerClass="sortable" title="其他数量"  property="otherNum" />
			<display:column sortable="false" headerClass="sortable" title="其他占比"  property="otherRate" />
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
		<%@ include file="/common/footer_eoms.jsp"%>