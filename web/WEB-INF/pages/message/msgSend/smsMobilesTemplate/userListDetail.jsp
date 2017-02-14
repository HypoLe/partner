<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
 <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
<%
	String aaa = (String) request.getParameter("id");
%>
<fmt:bundle
	basename="com/boco/eoms/message/config/ApplicationResources-msg">


	<html:hidden property="id" value="2" />
	<display:table name="tawContractList" cellspacing="0" cellpadding="0"
		id="tawContractList" pagesize="${pageSize}"
		class="table tawContractList" export="false"
		requestURI="${app}/msg/smsMobilesTemplates.do?method=search"
		sort="list" partialList="true" size="resultSize">

		<display:column property="name" titleKey="smsUserMgr.userName" />
		<display:column property="mobile" titleKey="smsUserMgr.mobile" />
		<display:column property="dept" titleKey="deptList.heading" />
		<display:column property="position" titleKey="smsUserMgr.position" />
		<display:column headerClass="sortable" titleKey="button.edit">
			<html:link href="${app}/msg/smsMobilesTemplates.do?method=editUser"
				paramId="id" paramProperty="id" paramName="tawContractList">
					编辑
					</html:link>
		</display:column>
		<display:column headerClass="sortable" titleKey="button.delete">
			<html:link href="${app}/msg/smsMobilesTemplates.do?method=removeUser"
				paramId="id" paramProperty="id" paramName="tawContractList"
				onclick="javascrip:return confirm('确定要删除？')">
					删除
					</html:link>
		</display:column>
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />
	<input type="button" value="<fmt:message key="button.add" />"
		onclick="javascript:window.location='${app}/msg/smsMobilesTemplates.do?method=addNewUser&teamId=<%=aaa%>'"
		class="button" />
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
