<%@ include file="/common/header_tpl_json.jsp"%>
<%-- @see com.boco.eoms.commons.system.dept.model.TawSystemDept --%>
<json:array name="depts" items="${list}" var="item">
	<json:object>
		<json:property name="id" value="${item.areaid}"/>
		<json:property name="text" value="${item.areaname}" />
		<json:property name="leaf" value="${item.leaf=='0'?0:1}" />
		<json:property name="nodeType" value="area" />
		<json:property name="parentAreaid" value="${item.parentAreaid}" />
		<%--
		<json:property name="id" value="${item.areaid=='1014'?"":item.areaid}"/>
		==1014 ? "":item.areaid
		<json:property name="id" value="${item.areaid}" />
		<json:property name="nodeType" value="${item.leaf=='0'?'depts':'dept'}" />
		<json:property name="leaf" value="true" />
		<json:property name="capital" value="capital" />
		--%>
		<%--<json:property name="parentAreaid" value="${item.parentAreaid}" />
		<json:property name="text" value="${item.deptName}" />
		<json:property name="isPartners" value="${item.isPartners}" />
	--%></json:object>
</json:array>