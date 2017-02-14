<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/pnrAgreementMains.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">

<content tag="heading">
	<div class="header center">
	<c:if test="${state == ''}">
		服务协议管理列表
	</c:if>
	<c:if test="${state == '2'}">
		有效服务协议管理列表
	</c:if>
	<c:if test="${state == '1'}">
		驳回服务协议管理列表
	</c:if>
	<c:if test="${state == '0'}">
		待审核服务协议管理列表
	</c:if>
	<c:if test="${state == '6'}">
		待总结服务协议管理列表
	</c:if>
	</div>
</content>
<c:if test="${resultSize!='0'}">
	<display:table name="pnrAgreementMainList" cellspacing="0" cellpadding="0"
		id="pnrAgreementMainList" pagesize="${pageSize}" class="table pnrAgreementMainList"
		export="false"
		requestURI="${app}/partner/agreement/pnrAgreementMains.do?method=search&state=${state}"
		sort="list" partialList="true" size="resultSize">

	<display:column property="agreementNO" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.agreementNO"  paramId="id" paramProperty="id"/>

	<display:column property="agreementName" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.agreementName"  paramId="id" paramProperty="id"/>

	<display:column property="startTime" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.startTime"  format="{0,date,yyyy-MM-dd}" paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.endTime"  format="{0,date,yyyy-MM-dd}" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="pnrAgreementMain.state">
		<c:if test="${pnrAgreementMainList.state == '0'}">新建待确认</c:if>
		<c:if test="${pnrAgreementMainList.state == '1'}">新建驳回</c:if>
		<c:if test="${pnrAgreementMainList.state == '2'}">有效</c:if>
		<c:if test="${pnrAgreementMainList.state == '3'}">归档待审核</c:if>
		<c:if test="${pnrAgreementMainList.state == '4'}">归档</c:if>
		<c:if test="${pnrAgreementMainList.state == '5'}">归档驳回</c:if>
		<c:if test="${pnrAgreementMainList.state == '6'}">待总结</c:if>
		<c:if test="${pnrAgreementMainList.state == '7'}">待归档</c:if>
	</display:column>
	<display:column title="查看" headerClass="imageColumn">
		<a href="${app}/partner/agreement/pnrAgreementMains.do?method=detail&id=${pnrAgreementMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	<c:if test="${pnrAgreementMainList.state == '1'}">
	<display:column title="修改" headerClass="imageColumn">
		<a href="${app}/partner/agreement/pnrAgreementMains.do?method=edit&id=${pnrAgreementMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</c:if>
	<c:if test="${pnrAgreementMainList.state == '6'}">
	<display:column title="总结" headerClass="imageColumn">
			<img src="${app}/images/icons/icon1.gif" onClick="javascript:var id = '${pnrAgreementMainList.id }';
		                												var url='${app}/partner/agreement/pnrAgreementMains.do?method=uploadSumUp';
		                												var win_props = 'toolbar=no,scrollBars=yes,width=800,height=450';
		                												url = url + '&id=' + id ;
		                												window.open(url,'',win_props);"/>
	</display:column>
	</c:if>
	<c:if test="${all != 'all'}">
	<c:if test="${pnrAgreementMainList.state == '5'||pnrAgreementMainList.state == '7'}">
	<display:column title="归档" headerClass="imageColumn">
			<img src="${app}/images/icons/icon1.gif" onClick="javascript:var agreementId = '${pnrAgreementMainList.id }';
		                												var url='${app}/partner/agreement/pnrAgreementMains.do?method=closeAgreement';
		                												var win_props = '';
		                												url = url + '&agreementId=' + agreementId ;
		                												window.open(url,'',win_props);"/>
	</display:column>
	</c:if>
	</c:if>
		<display:setProperty name="paging.banner.item_name" value="pnrAgreementMain" />
		<display:setProperty name="paging.banner.items_name" value="pnrAgreementMains" />
	</display:table>
	</c:if>
	<c:if test="${resultSize=='0'}">
		<table  class="formTable"  border="1"   bordercolor="#98C0F4">
		</br>
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >服务协议编号</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">服务协议名称</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label" >服务协议开始时间</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">服务协议结束时间</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">状态</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">查看</td>
			</tr>
			<tr>
				<td  style="text-align:center;"  colspan="6" >暂无记录</td>
			</tr>
		</table>
	</c:if>
	<!-- 
	<c:if test="${state == ''||state == '2'||state == '5'}">
			<c:if test="${retPnr==''||retPnr==null}">
				<c:out value="${buttons}" escapeXml="false" />
			</c:if>
	</c:if>
	 -->
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>