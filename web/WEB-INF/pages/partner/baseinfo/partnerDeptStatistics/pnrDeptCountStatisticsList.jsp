<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<table class="formTable">
  	<caption><div class="header center">组织数量统计列表</div></caption>
</table>
	<br>	<div>操作提示：点击组织名称可查看详细信息</div><br>
	<display:table name="partnerDeptList" cellspacing="0" cellpadding="0"
		id="partnerDeptList" pagesize="${pageSize}" class="table" requestURI="${app}/partner/baseinfo/pnrDeptStatistics.do?method=pnrDeptCountSearchInto"
		export="false" sort="list" partialList="true" size="${resultSize}">
			<display:column property="name" sortable="true" headerClass="sortable" title="组织名称"
				href="${app}/partner/baseinfo/aptitudes.do?method=newExpert&detail=detail&searchInto=Y" paramId="id" paramProperty="id" />
			<display:column property="organizationNo" sortable="true" headerClass="sortable" title="组织编码" paramId="id" paramProperty="id" />
			<display:column property="areaName" sortable="true" headerClass="sortable" title="所属区域" paramId="id" paramProperty="id" />
			<display:column property="registerTime" sortable="true" headerClass="sortable" title="注册日期" paramId="id" paramProperty="id" />
			<display:column sortable="true" headerClass="sortable" title="巡检专业" paramId="id" paramProperty="id">
				<c:forTokens items="${partnerDeptList.bigType}" delims="," var="professional" varStatus="s1">
					<eoms:id2nameDB id="${professional}" beanId="ItawSystemDictTypeDao" />
					<c:if test="${!s1.last}">,</c:if>
				</c:forTokens>
			</display:column>
			<display:column property="phone" sortable="true" headerClass="sortable" title="联系电话" paramId="id" paramProperty="id" />
				
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>