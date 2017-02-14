<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'assOperateStepForm'});
});
</script>
<%
int resultSize = StaticMethod.nullObject2int(request.getAttribute("resultSize"));
%>
<html:form action="/lineAssOperateStep.do?method=save" styleId="assOperateStepForm" method="post">
	<display:table name="assOperateStepList" cellspacing="0" cellpadding="0" id="assOperateStepList" pagesize="${pageSize}" class="table assOperateStepList" export="false"
		requestURI="${app}/partner/assess/lineAssOperateStep.do?method=undoList" sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.eva.util.AuditInfoListDisplaytagDecorator">
		<display:column sortable="true" headerClass="sortable" title="评估表名称">
		 	<a href="${app}/partner/assess/lineAssOperateStep.do?method=stepDetail&id=${assOperateStepList.id}&areaId=${assOperateStepList.area}&time=${assOperateStepList.time}&partnerId=${assOperateStepList.partnerId}">
		 		${assOperateStepList.taskName}
		 	</a>
		</display:column>
		<display:column property="time"  sortable="false" headerClass="sortable" title="后评估时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		<display:column sortable="true" headerClass="sortable" title="地域">
			<eoms:id2nameDB id="${assOperateStepList.area}" beanId="tawSystemAreaDao" /> 
		</display:column>

		<display:column sortable="true" headerClass="sortable" title="合作伙伴">
			<eoms:id2nameDB id="${assOperateStepList.partnerId}" beanId="partnerDeptDao" />
		</display:column>

		<display:column property="stateName"  sortable="false" headerClass="sortable" title="当前状态" />
	</display:table>
</html:form>
 
<%@ include file="/common/footer_eoms.jsp"%>
