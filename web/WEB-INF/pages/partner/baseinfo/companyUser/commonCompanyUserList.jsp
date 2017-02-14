<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js"></script>




<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<html:form action="partnerUsers.do?method=gotoCommonList" method="post"  styleId="partnerUserForm">
<c:if test="${in!=null}">
<%@ include file="/WEB-INF/pages/partner/baseinfo/hrefSearch.jsp"%>
</c:if>
<input type="hidden" name="treeNodeId" value="${treeNodeId }">
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="partnerUser.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			人员姓名
		</td>
		<td class="content">
			<input type="text" name="nameSearch" id="name"
						class="text medium"
						alt="allowBlank:false,vtext:''"  />
		</td>
		<td class="label">
			<fmt:message key="partnerUser.deptName" />
		</td>
		<td class="content">	
		<eoms:pnrComp name="prov" id="prov" />
		</td>
		
		
	</tr>
	<tr>	
		<td class="label">
			<fmt:message key="partnerUser.email" />
		</td>
		<td class="content">
			<input type="text" name="emailSearch" id="email"
						class="text medium"
						alt="allowBlank:true,vtext:''"  />
		</td>
		
		<td class="label">
			<fmt:message key="partnerUser.phone" />
		</td>
		<td class="content">
			<input type="text" name="phoneSearch" id="phone"
						class="text medium"
						alt="allowBlank:true,vtext:''"  />
		</td>
	</tr>
</table>

<table align="center">
	<tr>
		<td>
			<input type="submit" class="btn" value="查询" />
			
			<input type="reset" class="btn" value="重置" >
		</td>
	</tr>
</table>
</html:form>	


<html:form action="partnerUsers" method="post"  styleId="partnerUserForm">
	<input type="hidden" name="treeNodeId" value="${treeNodeId }">
<content tag="heading">
	<fmt:message key="partnerUser.list.heading" />
</content>

	<display:table name="partnerUserList" cellspacing="0" cellpadding="0"
		id="partnerUserList" pagesize="${pageSize}" class="table partnerUserList"
		export="false"
		requestURI="${app}/partner/baseinfo/partnerUsers.do?method=search"
		sort="list" partialList="true" size="resultSize">

   

	<display:column property="name" sortable="true"      
			headerClass="sortable" titleKey="partnerUser.name" href="${app}/partner/baseinfo/resumes.do?method=commonNewExpert&nodeId=${nodeId}" paramId="id" paramProperty="id"/>
	
	<display:column  sortable="true"
			headerClass="sortable" titleKey="partnerUser.sex"  paramId="id" paramProperty="id">
			<eoms:id2nameDB id="${partnerUserList.sex}" beanId="ItawSystemDictTypeDao"/> 
	</display:column>		

	<display:column property="areaName" sortable="true"
			headerClass="sortable" titleKey="partnerUser.areaName"  paramId="id" paramProperty="id"/>

	<display:column property="personCardNo" sortable="true"
			headerClass="sortable" titleKey="partnerUser.personCardNo"  paramId="id" paramProperty="id"/>
			
	<display:column property="deptName" sortable="true"
			headerClass="sortable" titleKey="partnerUser.deptName"  paramId="id" paramProperty="id"/>
 
	<display:column property="phone" sortable="true"
			headerClass="sortable" titleKey="partnerUser.phone"  paramId="id" paramProperty="id"/>

	<display:column property="email" sortable="true"
			headerClass="sortable" titleKey="partnerUser.email"  paramId="id" paramProperty="id"/>
	
	<display:column  sortable="true"
			headerClass="sortable" titleKey="partnerUser.post"  >
				<eoms:id2nameDB id="${partnerUserList.post}" beanId="ItawSystemDictTypeDao"/> 
	</display:column>
				
		<display:setProperty name="paging.banner.item_name" value="partnerUser" />
		<display:setProperty name="paging.banner.items_name" value="partnerUsers" />
	</display:table>

</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
