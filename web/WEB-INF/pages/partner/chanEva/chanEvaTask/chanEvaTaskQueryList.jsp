<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.partner.chanEva.util.ChanEvaConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.net.URLEncoder"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'chanEvaKpiInstanceForm'});
})
</script>

<html:form action="/chanEvaTasks.do?method=saveTaskDetail"
	styleId="chanEvaKpiInstanceForm" method="post">
	<table class="listTable" id="list-table">
		<caption>
			<div class="header center">
				任务(${requestScope.taskName})-周期(${requestScope.timeType})-时间(${requestScope.startTime}~${requestScope.endTime})
				考核结果查询列表
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
				<%--
				<td>
					合作伙伴
				</td>
				 --%>
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
						<eoms:dict key="dict-partner-chanEva" dictId="cycle" itemId="${taskDetail.timeType}" beanId="id2nameXML" />
					</td>
					<td>
						<bean:write name="taskDetail" property="time" />
					</td>
					<%--
					<td>
						<bean:write name="taskDetail" property="partnerName" />
					</td>
					--%>
					<td>
						<bean:define id="isPublish" name="taskDetail" property="isPublish" />
						<%if(ChanEvaConstants.TASK_PUBLISHED.equals(isPublish)){%>
						已发布
						<%}else{%>
						未发布
						<%}%>
					</td>
					<td>
						<a href="${app}/partner/chanEva/chanEvaTasks.do?method=taskDetailList&taskId=${taskDetail.taskId}&partnerId=${taskDetail.partnerId}&time=${taskDetail.time}&cycle=${taskDetail.timeType}&queryType=${isPublish}&region=${taskDetail.city}&specialty=${specialty}" title="查看" />查看
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
