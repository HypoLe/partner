<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlanGisAction.do"
		sort="list" partialList="true" size="${pagesize}" 
	>
		<display:column title="资源名称" >
			${resName }
		</display:column>
		<display:column title="资源经度" >
			${list.signLatitude}
		</display:column>
		<display:column title="资源纬度" >
			${list.signLongitude}
		</display:column>
		<display:column title="巡检时间" >
			${list.signTime}
		</display:column>
		<display:column title="巡检时刻" >
			<c:if test="${list.status eq 1}">
			到站
			</c:if>
			<c:if test="${list.status eq 0}">
			离站
			</c:if>
			
		</display:column>
		
	</display:table>
	<br/>
	<input type="button" onclick="javascript:history.back()" value="返回" class="btn">
 
<%@ include file="/common/footer_eoms.jsp"%>