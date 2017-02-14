<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<fmt:bundle
	basename="com/boco/eoms/message/config/ApplicationResources-msg">
	<display:table name="logList" cellspacing="0" cellpadding="0"
		id="logList" pagesize="${pageSize}"
		class="table logList" export="false"
		requestURI="${app}/msg/smsMobilesTemplates.do?method=search"
		sort="list" partialList="true" size="resultSize">

		<display:column property="name" titleKey="smsUserMgr.userName" />
		<display:column property="mobile" titleKey="smsUserMgr.mobile" />
		<display:column property="dateTime" titleKey="smsUserMgr.dateTime" />
		<display:column property="dateTime" titleKey="smsUserMgr.dateTime" />
		<display:column property="content" titleKey="deptList.content" />
		</display:column>
	</display:table>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
