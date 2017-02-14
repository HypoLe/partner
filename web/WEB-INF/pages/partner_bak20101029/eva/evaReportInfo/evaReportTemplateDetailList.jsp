<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<jsp:directive.page import="java.util.Map"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<c:if test="${not empty taskId}">
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
			地域
		</td>
		<td>
			考核模板
		</td>
		<td>
			算法描述
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
	<%
	Map areaRowspan = (Map)request.getAttribute("areaRowspan");
	String areaStr = "";
	%>
	<logic:iterate id="rowList" name="tableList" type="java.util.List">

		<logic:iterate id="taskDetail" name="rowList">
		<bean:define id="leaf" name="taskDetail" property="leaf" />
		<bean:define id="rowspan" name="taskDetail" property="rowspan" />
		<bean:define id="algorithm" name="taskDetail" property="algorithm" />
		<bean:define id="nodeAreaStr" name="taskDetail" property="nodeAreaStr" />
		<bean:define id="nodeId" name="taskDetail" property="nodeId" />
		<bean:define id="area" name="taskDetail" property="area" />
	<tr>
	<%
	if(!areaStr.equals(area)){
		areaStr = area.toString();
	%>
		<td rowspan=<%=StaticMethod.nullObject2int(areaRowspan.get(area)) %>>
			<eoms:id2nameDB id="${taskDetail.area}" beanId="tawSystemAreaDao" /> 
		</td>
	<%
	}
	%>
		<td class="label" style="vertical-align:middle;text-align:left">
			<a href="${app}/partner/eva/evaReportInfos.do?method=taskReportInfoList&partnerId=${requestScope.partner}&timeType=${requestScope.timeType}&time=${requestScope.time}&areaId=${taskDetail.area}&startTime=${startTime}&endTime=${endTime}&templateId=${taskDetail.templateId}&showType=detail" target=_blank><eoms:id2nameDB id="${taskDetail.templateId}" beanId="pnrEvaTemplateDao" /></a>(${taskDetail.weight}%)
		</td>
		<td style="vertical-align:middle;text-align:left">
			<eoms:dict key="dict-partner-eva" dictId="sumType" itemId="${taskDetail.algorithm}" beanId="id2nameXML" />
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
		</logic:iterate>
	</logic:iterate>
	</tr>
	</tbody>
</table>
</c:if>
<c:if test="${empty taskId}">
没有记录！
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>
