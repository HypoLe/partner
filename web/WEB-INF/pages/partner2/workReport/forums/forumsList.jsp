<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/forums.do?method=edit'/>'"
		value="新增" />

</c:set>
	<display:table name="forumsList" cellspacing="0" cellpadding="0"
		id="forumsList" pagesize="${pageSize}" class="table forumsList"
		export="true"
		requestURI="${app }/partner2/workReport/forums.do?method=search"
		sort="list" partialList="true" size="resultSize">

<display:setProperty name="export.rtf" value="false"></display:setProperty>

		<display:column property="title" sortable="true"
			headerClass="sortable" titleKey="workReportForumsForm.title" href="${app }/partner2/workReport/thread.do?method=list4forumId" paramId="forumsId" paramProperty="id"/>
		
		<display:column property="description" sortable="true"
			headerClass="sortable" titleKey="workReportForumsForm.description" />
			
			
		<display:column property="createrId" sortable="true"
			headerClass="sortable" titleKey="workReportForumsForm.createrId" />

		<display:column property="createTime" sortable="true"
			headerClass="sortable" titleKey="workReportForumsForm.createTime" />


		<display:column property="parentId" sortable="true"
			headerClass="sortable" titleKey="workReportForumsForm.parentId" />

		<display:setProperty name="paging.banner.item_name" value="forums" />
		<display:setProperty name="paging.banner.items_name" value="forumss" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />
<%@ include file="/common/footer_eoms.jsp"%>
