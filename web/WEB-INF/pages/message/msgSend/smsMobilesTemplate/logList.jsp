<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<fmt:bundle
	basename="com/boco/eoms/message/config/ApplicationResources-msg">
	<display:table name="tawContractList" cellspacing="0" cellpadding="0"
		id="tawContractList" pagesize="${pageSize}"
		class="table logList" export="false"
		requestURI="${app}/msg/smsUserLog.do?method=search"
		sort="list" partialList="true" size="resultSize">

		<display:column property="userName" titleKey="smsUserMgr.userName" />
		<display:column property="mobile" titleKey="smsUserMgr.mobile" />
		<display:column property="teamName" titleKey="smsUserMgr.team" />
		<display:column property="dateTime" titleKey="smsUserMgr.dateTime" />
		<display:column property="content" titleKey="deptList.content" />
		
	</display:table>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
