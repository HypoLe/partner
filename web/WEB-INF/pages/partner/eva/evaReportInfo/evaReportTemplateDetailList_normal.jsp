<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<jsp:directive.page import="java.util.Map"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'pnrEvaKpiInstanceForm'});
})
</script>
<%
Map minSorceMap = (Map)request.getAttribute("minSorceMap");
%>
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
		<td colspan="2">
			
		</td>
		<td>
			评分部门：<eoms:id2nameDB id="${requestScope.assessUserId}" beanId="tawSystemUserDao"/>
		</td>
		<td>
			评分人：<eoms:id2nameDB id="${requestScope.assessDeptId}" beanId="tawSystemDeptDao"/>
		</td>
		<td colspan="3">
			最终得分:${requestScope.allScore}

	</tr>
	<tr>
		<td>
			考核模板
		</td>
		<td>
			算法描述
		</td>
		<td>
			地域
		</td>
		<td>
			计算比例
		</td>
		<td>
			汇总分数
		</td>
		<td>
			备注
		</td>
	</tr>
	</thead>
	<tbody>	
	<tr>
	<logic:iterate id="rowList" name="tableList" type="java.util.List">

		<logic:iterate id="taskDetail" name="rowList">
		<bean:define id="leaf" name="taskDetail" property="leaf" />
		<bean:define id="isPublish" name="taskDetail" property="isPublish" />
		<bean:define id="rowspan" name="taskDetail" property="rowspan" />
		<bean:define id="algorithm" name="taskDetail" property="algorithm" />
		<bean:define id="nodeAreaStr" name="taskDetail" property="nodeAreaStr" />
		<bean:define id="nodeId" name="taskDetail" property="nodeId" />
		<%if (PnrEvaConstants.NODE_LEAF.equals(leaf)) { %>

			<%if(PnrEvaConstants.TASK_PUBLISHED.equals(isPublish)){%>
		<td>
			<a href="${app}/partner/eva/evaReportInfos.do?method=taskReportInfoList&partnerId=${requestScope.partner}&timeType=${requestScope.timeType}&time=${requestScope.time}&areaId=${taskDetail.area}&startTime=${startTime}&endTime=${endTime}&templateId=${taskDetail.templateId}&showType=detail" target=_blank><eoms:id2nameDB id="${taskDetail.area}" beanId="tawSystemAreaDao" /></a>  
		</td>
		<td>
			${taskDetail.maintenanceRatio}
		</td>
		<td>
			${taskDetail.realScore}
		</td>
		<td>
			${taskDetail.remark}
		</td>
		</tr>
		<tr>
		<%}else if(algorithm.equals(PnrEvaConstants.SUMTYPE_MIN)){%>
		<td>
			<a href="${app}/partner/eva/evaReportInfos.do?method=taskReportInfoList&partnerId=${requestScope.partner}&timeType=${requestScope.timeType}&time=${requestScope.time}&areaId=${taskDetail.area}&startTime=${startTime}&endTime=${endTime}&templateId=${taskDetail.templateId}&showType=detail" target=_blank><eoms:id2nameDB id="${taskDetail.area}" beanId="tawSystemAreaDao" /></a>  
		</td>
		<%
		String minSorceIfo = StaticMethod.nullObject2String(minSorceMap.get(nodeId));
		if(minSorceIfo.equals(nodeAreaStr)){
			%>
			<td>
			100
		</td>
		<%}else{%>
		<td>
			${taskDetail.maintenanceRatio}
		</td>
		<%} %>
		<td>
			${taskDetail.realScore}
		</td>
		<td>
			${taskDetail.remark}
		</td>
		</tr>
		<tr>
		<%}else if(algorithm.equals(PnrEvaConstants.SUMTYPE_INPUT)){%>
		<td>
		<a href="${app}/partner/eva/evaReportInfos.do?method=taskReportInfoList&partnerId=${requestScope.partner}&timeType=${requestScope.timeType}&time=${requestScope.time}&areaId=${taskDetail.area}&templateId=${taskDetail.templateId}&showType=detail" target=_blank><eoms:id2nameDB id="${taskDetail.area}" beanId="tawSystemAreaDao" /></a>  
	</td>
	<td>
		100
	</td>
	<td>
		${taskDetail.realScore}
	</td>
	<td>
		${taskDetail.remark}
	</td>
	</tr>
	<tr>
	<%}else{%>
		<td>
		<a href="${app}/partner/eva/evaReportInfos.do?method=taskReportInfoList&partnerId=${requestScope.partner}&timeType=${requestScope.timeType}&time=${requestScope.time}&areaId=${taskDetail.area}&startTime=${startTime}&endTime=${endTime}&templateId=${taskDetail.templateId}&showType=detail" target=_blank><eoms:id2nameDB id="${taskDetail.area}" beanId="tawSystemAreaDao" /></a> 
	</td>
	<td>
		${taskDetail.maintenanceRatio}
	</td>
	<td>
		${taskDetail.realScore}
	</td>
	<td>
		${taskDetail.remark}
	</td>
	</tr>
	<tr>
	<%}
		}else{%>
		<td rowspan="${taskDetail.rowspan}" class="label" style="vertical-align:middle;text-align:left">
			<eoms:id2nameDB id="${taskDetail.templateId}" beanId="pnrEvaTemplateDao" />(${taskDetail.weight}%)
		</td>
		<td rowspan="${taskDetail.rowspan}"  style="vertical-align:middle;text-align:left">
			<eoms:dict key="dict-partner-eva" dictId="sumType" itemId="${taskDetail.algorithm}" beanId="id2nameXML" />
		</td>
		<%
		}
		%>
		</logic:iterate>
	</logic:iterate>
	</tr>
	</tbody>
</table>
	<input type="hidden" id="taskId" name="taskId" value="${requestScope.taskId}"/>
	<input type="hidden" id="partner" name="partner" value="${requestScope.partner}"/>
	<input type="hidden" name="timeType" value="${requestScope.timeType}"/>
	<input type="hidden" name="time" value="${requestScope.time}"/>
	<input type="hidden" name="type" value="<%=PnrEvaConstants.NODETYPE_TEMPLATE %>"/>
	<input type="hidden" name="startTime" value="${startTime}"/>
	<input type="hidden" name="endTime" value="${endTime}"/>
	<input type="hidden" name="partner" value="${partner}"/>
	<input type="hidden" name="areaId" value="${areaId}"/>
</html:form>
</c:if>
<c:if test="${empty taskId}">
没有记录！
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>
