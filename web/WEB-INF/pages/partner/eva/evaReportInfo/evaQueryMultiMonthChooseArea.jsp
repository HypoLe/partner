<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<%
String partnerNodeType = StaticMethod.nullObject2String(request.getAttribute("partnerNodeType"));
System.out.println("-------------------"+partnerNodeType);
%>
<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'queryMultiMonthFrom'});
});
</script>

<html:form action="/evaReportInfos.do?method=queryInitMultiList" styleId="queryMultiMonthFrom" method="post"> 
<table class="formTable" id="form" name="form">
	<caption>
		<div class="header center">考核报表查询(不同月份-同一厂商)</div>
	</caption>
	<tr>
		<td class="label">
			考核模板
		</td>
		<td class="content">
			${templateName}
		</td>
		<td class="label">
			时间周期类型
		</td>
		<td class="content">
			月度
		</td>
	</tr>
	<tr>
		<td class="label">
			开始时间
		</td>
		<td class="content">
			${startYear}年${startMonth}月
		</td>
		<td class="label">
			结束时间
		</td>
		<td class="content">
			${endYear}年${endMonth}月
		</td>
	</tr>
	<tr>
		<td class="label">
			选择地域
		</td>
		<td class="content">
		<%
		if("templateType".equals(partnerNodeType)){
		%>
		<eoms:id2nameDB id="${areaId}" beanId="tawSystemAreaDao" />
		<input type="hidden" name="area" value="${areaId}"/>
		<input type="hidden" name="taskId" value="${taskId}"/>
		<%	
		}else{
		%>
			<select name="area" id="area" alt="allowBlank:false,vtext:'请选择地域'">
				<option value="">
						--请选择地域--
				</option>
				<logic:present name="areas" scope="request">
				<logic:iterate id="areas" name="areas" scope="request">
				<option value="${areas.areaid}">${areas.areaname}</option>
				</logic:iterate>
				</logic:present>
			</select>	
		<%
		}
		%>
		</td>
		<td class="label">
			选择厂商
		</td>
		<td>
			<select name="partnerId" id="partnerId" >
				<c:if test="${not empty allKpiFactury}">
				<option value="">
						--请选择合作厂商--
				</option>
				</c:if>
				<c:if test="${empty allKpiFactury}">
				<option value="">
						目前还没有合作厂商
				</option>
				</c:if>
				<logic:present name="allKpiFactury" scope="request">
				<logic:iterate id="allFactury" name="allKpiFactury" scope="request">
				<option value="${allFactury.factury}">${allFactury.facturyName}</option>
				</logic:iterate>
				</logic:present>
			</select>
		</td>
	</tr>
</table>
<input type="hidden" name="timeType" value="${timeType}"/>
<input type="hidden" name="startYear" value="${startYear}"/>
<input type="hidden" name="startMonth" value="${startMonth}"/>
<input type="hidden" name="endYear" value="${endYear}"/>
<input type="hidden" name="endMonth" value="${endMonth}"/>
<input type="hidden" name="templateId" value="${templateId}"/>
<input type="hidden" name="taskId" value="${taskId}"/>
<input type="hidden" name="nodeId" value="${nodeId}"/>
<inupt type="hidden" name="templateTypeId" value="${templateTypeId}"/>
<table id="submit-btn" align="left">
	<tr>
		<td>
			<input type="submit" class="btn" value="查看" />
		</td>		
	</tr>
</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
