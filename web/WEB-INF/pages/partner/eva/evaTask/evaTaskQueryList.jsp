<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.net.URLEncoder"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'pnrEvaKpiInstanceForm'});
})
</script>

<html:form action="/evaTasks.do?method=saveTaskDetail"
	styleId="pnrEvaKpiInstanceForm" method="post">
	<table class="listTable" id="list-table">
		<caption>
			<div class="header center">
				草稿列表
			</div>
		</caption>
		
		<thead>
		
			<tr>
				<td>
					编号
				</td>
				<td>
					任务名称
				</td>
				<td>
					周期类型
				</td>
				<td>
					考核时间
				</td>
				<td>
					合作伙伴
				</td>
				<td>
					是否上报
				</td>
				<td>
					查看
				</td>
			</tr>
		</thead>
		<tbody>
			<logic:iterate id="taskDetail" name="taskDetailList" indexId="index">
				<tr>
					<td>
						${index + 1}
					</td>
					<td>
						<bean:write name="taskDetail" property="taskName" />
					</td>
					<td>
						<bean:write name="taskDetail" property="timeType" />
					</td>
					<td>
						<bean:write name="taskDetail" property="time" />
					</td>
					<td>
					<eoms:id2nameDB id="${taskDetail.partnerName}" beanId="areaDeptTreeDao" />
					</td>
					<td>
						<bean:define id="isPublish" name="taskDetail" property="isPublish" />
						<%if(PnrEvaConstants.TASK_PUBLISHED.equals(isPublish)){%>
						已发布
						<%}else{%>
						未发布
						<%}%>
					</td>
					<td>
					<c:if test="${executeOrg=='0'}">
						<a href="${app}/partner/eva/evaTasks.do?method=taskDetailList&taskId=${taskDetail.taskId}&partner=${taskDetail.partnerId}&time=${taskDetail.time}&timeType=${taskDetail.timeType}&queryType=${isPublish }" title="查看" />查看
					</c:if>
					<c:if test="${executeOrg!='0'}">
						<a href="${app}/partner/eva/evaTasks.do?method=taskDetailListForAllCity&taskId=${taskDetail.taskId}&partner=${taskDetail.partnerId}&time=${taskDetail.time}&timeType=${taskDetail.timeType}&queryType=${isPublish }" title="查看" />查看
					</c:if>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
