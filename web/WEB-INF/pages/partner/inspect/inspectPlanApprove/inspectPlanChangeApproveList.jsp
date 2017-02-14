<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
</script>
	
</hr>

<!-- Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pagesize}" requestURI="inspectPlanChange.do?method=findInspectPlanChangeList" 
		partialList="true" size="${size}">
		<display:column property="changeTitle" title="变更标题" />
		<display:column property="changeOption" title="变更说明" />
		<display:column title="变更申请日期" >
			<bean:write name="list" property="changeTime" format="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column title="变更申请人" >
			<eoms:id2nameDB id="${list.creator}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column title="审批状态">
			${approveStatusMap[list.state]}
		</display:column>
		<display:column title="变更详情">
			<a target="blank" shape="rect" href="${app }/partner/inspect/inspectPlanChange.do?method=findInspectPlanResChangeApproveList&planId=${list.planId }&id=${list.id }"> 
				<img src="${app }/images/icons/table.gif"><a>
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="审批" media="html">
			<a href="${app }/partner/inspect/inspectPlanChange.do?method=toApproveInspectPlanChange&planChgId=${list.id}&planId=${list.planId}"> 
				<img src="${app }/images/icons/edit.gif"><a>
		</display:column>
	</display:table>
</logic:present>
	</br>
	</div>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
