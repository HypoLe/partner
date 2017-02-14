<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>



<fmt:bundle basename="com/boco/eoms/partner/tempTask/config/applicationResources-partner-tempTask">

<content tag="heading">
	<div class="header center">
	<c:if test="${state == ''}">
		临时任务列表
	</c:if>
	<c:if test="${state == '2'}">
		有效临时任务列表
	</c:if>
	<c:if test="${state == '1'}">
		驳回临时任务列表
	</c:if>
	<c:if test="${state == '0'}">
		待审核临时任务列表
	</c:if>
	</div>
</content>
<c:if test="${resultSize!='0'}">
	<display:table name="pnrTempTaskMainList" cellspacing="0" cellpadding="0"
		id="pnrTempTaskMainList" pagesize="${pageSize}" class="table pnrTempTaskMainList"
		export="false"
		requestURI="${app}/partner/tempTask/pnrTempTaskMains.do?method=search&state=${state}"
		sort="list" partialList="true" size="resultSize">

	<display:column property="tempTaskNO" sortable="true"
			headerClass="sortable" titleKey="pnrTempTaskMain.tempTaskNO"  paramId="id" paramProperty="id"/>

	<display:column property="tempTaskName" sortable="true"
			headerClass="sortable" titleKey="pnrTempTaskMain.tempTaskName"  paramId="id" paramProperty="id"/>

	<display:column property="startTime" sortable="true"
			headerClass="sortable" titleKey="pnrTempTaskMain.startTime"  format="{0,date,yyyy-MM-dd}" paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" titleKey="pnrTempTaskMain.endTime"  format="{0,date,yyyy-MM-dd}" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="pnrTempTaskMain.state">
		<c:if test="${pnrTempTaskMainList.state == 0}">待审核</c:if>
		<c:if test="${pnrTempTaskMainList.state == 1}">驳回</c:if>
		<c:if test="${pnrTempTaskMainList.state == 2}">有效</c:if>
	</display:column>
	<c:if test="${state == ''||state == '2'}">
	<display:column title="查看" headerClass="imageColumn">
		<a href="${app}/partner/tempTask/pnrTempTaskMains.do?method=detail&id=${pnrTempTaskMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</c:if>
	<c:if test="${state == '1'}">
	<display:column title="修改" headerClass="imageColumn">
		<a href="${app}/partner/tempTask/pnrTempTaskMains.do?method=edit&id=${pnrTempTaskMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</c:if>
	<c:if test="${state == '0'}">
	<display:column title="审核" headerClass="imageColumn">
		<a href="${app}/partner/tempTask/pnrTempTaskMains.do?method=audit&id=${pnrTempTaskMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</c:if>
		<display:setProperty name="paging.banner.item_name" value="pnrTempTaskMain" />
		<display:setProperty name="paging.banner.items_name" value="pnrTempTaskMains" />
	</display:table>
	</c:if>
	<c:if test="${resultSize=='0'}">
		<table  class="formTable"  border="1"   bordercolor="#98C0F4">
		</br>
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >临时任务编号</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">临时任务名称</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label" >临时任务开始时间</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">临时任务结束时间</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">状态</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">查看</td>
			</tr>
			<tr>
				<td  style="text-align:center;"  colspan="6" >暂无记录</td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${state == ''||state == 2}">
		<c:out value="${buttons}" escapeXml="false" />
	</c:if>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>