<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'pnrEvaKpiInstanceForm'});
})
</script>
<c:if test="${not empty taskId}">
<html:form action="/evaTaskAudit.do?method=taskReportInfoList" styleId="pnrEvaKpiInstanceForm" method="post">
<table class="listTable" id="list-table">
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-合作伙伴(<eoms:id2nameDB id="${requestScope.partner}" beanId="areaDeptTreeDao" />)-周期(${requestScope.timeType})-时间(${requestScope.time}) 考核执行列表
		</div>
	</caption>
	
	<thead>
	<tr>
		<td colspan="${requestScope.titleColspan}">
			
		</td>
		<td>
			评分人：<eoms:id2nameDB id="${requestScope.assessUserId}" beanId="tawSystemUserDao"/>
		</td>
		<td>
			评分部门：<eoms:id2nameDB id="${requestScope.assessDeptId}" beanId="tawSystemDeptDao"/>
		</td>
		<td colspan="2">
			最终得分:${requestScope.allScore}
		</td>

	</tr>
	<tr>
		<td colspan="${requestScope.titleColspan}">
			考核指标
		</td>
		<td>
			算法描述
		</td>
		<td>
			实际扣分
		</td>
		<td>
			扣分原因
		</td>
		<td>
			备注
		</td>
	</tr>
	</thead>
	<tbody>
	<logic:iterate id="rowList" name="tableList" type="java.util.List" indexId="index">
	<tr>
		<logic:iterate id="taskDetail" name="rowList" indexId="indexid">
		<bean:define id="leaf" name="taskDetail" property="leaf" />
		<td rowspan="${taskDetail.rowspan}" colspan="${taskDetail.colspan}" class="label" style="vertical-align:middle;text-align:left">
			<eoms:id2nameDB id="${taskDetail.kpiId}" beanId="pnrEvaKpiDao" />(${taskDetail.weight}分)
		</td>
		<%if (PnrEvaConstants.NODE_LEAF.equals(leaf)) { %>
			<bean:define id="isPublish" name="taskDetail" property="isPublish" />
		<td>
			${taskDetail.algorithm}
		</td>
			<%if(PnrEvaConstants.TASK_PUBLISHED.equals(isPublish)){%>
		<td>
			${taskDetail.realScore}
		</td>
		<td>
			${taskDetail.reduceReason}
		</td>
		<td>
			${taskDetail.remark}
		</td>
		<%}else{%>
		<td>
			${taskDetail.realScore}
		</td>
		<td>
			${taskDetail.reduceReason}
		</td>
		<td>
			${taskDetail.remark}
		</td>
		<%}
		}%>
		</logic:iterate>
	</tr>
	</logic:iterate>  
	</tbody>
</table>
	<input type="hidden" id="taskId" name="taskId" value="${requestScope.taskId}"/>
	<input type="hidden" name="templateId" value="${requestScope.templateId}">	
	<input type="hidden" name="type" value="<%=PnrEvaConstants.NODETYPE_KPI %>"/>
	<input type="hidden" id="partner" name="partner" value="${requestScope.partner}"/>
	<input type="hidden" name="timeType" value="${requestScope.timeType}"/>
	<input type="hidden" name="time" value="${requestScope.time}"/>
	<input type="hidden" name="areaId" value="${areaId}"/>
</html:form>
</c:if>
<c:if test="${empty taskId}">
没有记录！
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>
