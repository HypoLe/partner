<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

var jq=jQuery.noConflict();

jq(function(){
	jq("#detail").hide();
})

function newAdd(){
    window.location.href= '${app }/partner/inspect/inspectPlanChange.do?method=toAddInspectPlanChange&planId=${planId}';
}

function addInspectRes(){
	
	document.getElementById("detail").click();
}


function backList(){
	 window.location.href= '${app }/partner/inspect/inspectPlanChange.do?method=findInspectPlanMainList';
}
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
		<display:column title="编辑">
			<c:if test="${(list.state==3 || list.state==0) && sessionScope.sessionform.userid == list.creator}" var="result">
				<a href="${app }/partner/inspect/inspectPlanChange.do?method=toAddInspectPlanChange&id=${list.id }&planId=${list.planId }">
				<img src="${app }/images/icons/edit.gif"><a>
			</c:if>
		</display:column>
		<display:column title="变更巡检任务">
			<c:if test="${(list.state==3 || list.state==0) && sessionScope.sessionform.userid == list.creator}" var="result">
				<a href="${app }/partner/inspect/inspectPlanChange.do?method=findInspectPlanResChangeList&planId=${list.planId }&id=${list.id }">
					<img src="${app }/images/icons/edit.gif"><a>
			</c:if>
		</display:column>
				<display:column title="变更详情">
		<a target="blank" shape="rect" href="${app }/partner/inspect/inspectPlanChange.do?method=findInspectPlanResChangeApproveList&planId=${list.planId }&id=${list.id }"> 
				<img src="${app }/images/icons/table.gif"><a>
		</display:column>
	</display:table>
</logic:present>
	</br>
	<c:if test="${haveAddRight}">
		<input type="button" class="btn" value="新增变更" onclick="newAdd();" />
		<input type="button" id="addInspect" class="btn" value="新增临时元任务" onclick="addInspectRes()"/>
		<a id="detail"
				href="${app }/partner/inspect/inspectGenerateAction.do?method=toPnrResConfigGenerate"
				target="blank" shape="rect"> aa</a>
				
		
	</c:if>
	<input type="button" class="btn" value="返回" onclick="backList();"/>
	</div>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
