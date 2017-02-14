<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<fmt:bundle basename="com/boco/eoms/partner/workplan/config/applicationResources-partner-workplan">

<content tag="heading">
	<div class="header center">
	<c:if test="${state == ''}">
		工作计划列表
	</c:if>
	<c:if test="${state == '2'}">
		有效工作计划列表
	</c:if>
	<c:if test="${state == '1'}">
		驳回工作计划列表
	</c:if>
	<c:if test="${state == '0'}">
		待审核工作计划列表
	</c:if>
	<c:if test="${state == '3'}">
		以关闭工作计划列表
	</c:if>	
	</div>
</content>
<c:if test="${resultSize!='0'}">
	<display:table name="pnrWorkplanMainList" cellspacing="0" cellpadding="0"
		id="pnrWorkplanMainList" pagesize="${pageSize}" class="table pnrWorkplanMainList"
		export="false"
		requestURI="${app}/partner/workplan/pnrWorkplanMains.do?method=search&state=${state}"
		sort="list" partialList="true" size="resultSize">

	<display:column property="workplanNO" sortable="true"
			headerClass="sortable" titleKey="pnrWorkplanMain.workplanNO"  paramId="id" paramProperty="id"/>

	<display:column property="workplanName" sortable="true"
			headerClass="sortable" titleKey="pnrWorkplanMain.workplanName"  paramId="id" paramProperty="id"/>

	<display:column property="startTime" sortable="true"
			headerClass="sortable" titleKey="pnrWorkplanMain.startTime"  format="{0,date,yyyy-MM-dd HH:mm:ss}" paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" titleKey="pnrWorkplanMain.endTime"  format="{0,date,yyyy-MM-dd HH:mm:ss}" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="pnrWorkplanMain.state">
		<c:if test="${pnrWorkplanMainList.state == 0}">待审核</c:if>
		<c:if test="${pnrWorkplanMainList.state == 1}">驳回</c:if>
		<c:if test="${pnrWorkplanMainList.state == 2}">有效</c:if>
		<c:if test="${pnrWorkplanMainList.state == 3}">已关闭</c:if>
	</display:column>
	<c:if test="${state == ''||state == '2'||state == '3'}">
	<display:column title="查看" headerClass="imageColumn">
		<a href="${app}/partner/workplan/pnrWorkplanMains.do?method=detail&id=${pnrWorkplanMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</c:if>
	<c:if test="${state == '1'}">
	<display:column title="修改" headerClass="imageColumn">
		<a href="${app}/partner/workplan/pnrWorkplanMains.do?method=edit&id=${pnrWorkplanMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</c:if>
	<c:if test="${state == '0'}">
	<display:column title="审核" headerClass="imageColumn">
		<a href="${app}/partner/workplan/pnrWorkplanMains.do?method=audit&id=${pnrWorkplanMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</c:if>
		<display:setProperty name="paging.banner.item_name" value="pnrWorkplanMain" />
		<display:setProperty name="paging.banner.items_name" value="pnrWorkplanMains" />
	</display:table>
	</c:if>
	<c:if test="${resultSize=='0'}">
		<table  class="formTable"  border="1"   bordercolor="#98C0F4">
		</br>
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >工作计划编号</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">工作计划名称</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label" >工作计划开始时间</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">工作计划结束时间</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">状态</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">查看</td>
			</tr>
			<tr>
				<td  style="text-align:center;"  colspan="6" >暂无记录</td>
			</tr>
		</table>
	</c:if>	
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>