<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'reportMultiPartnerForm'});
})
function isSubmit(){
    return true;//document.getElementById("pnrEvaKpiInstanceForm").submit();
}

</script>

<html:form action="/evaReportInfos.do?method=partnerList" styleId="reportMultiPartnerForm" method="post" > 
<table class="formTable" id="form">
	<caption>
		<div class="header center">同一月份不同厂商查看</div>
	</caption>
	<input type="hidden" id="queryType" name="queryType" value="run"/>
	<tr>
		<td class="label">
			选择考核模板
		</td>
		<td class="content">
				<eoms:id2nameDB id="${templateTypeId}" beanId="pnrEvaTreeDao" />
				</select>
		</td>
		<td class="label">
			考核模板
		</td>
		<td class="content">
			${taskName}
		</td>
	</tr>
	<tr>
		<td class="label">
			时间周期类型
		</td>
		<td class="content">
			${timeType}
		</td>
		<td class="label">
			选择时间
		</td>
		<td>
			${time}
		</td>
	</tr>
	<tr>
		<td class="label">
			选择地域
		</td>
		<td class="content" colspan="3">
			<select name="areaId" id="areaId" alt="allowBlank:false,vtext:'请选择地域'">
				<option value="">请选择</option>
				<logic:present name="areaInfo" scope="request">
				<logic:iterate id="task" name="areaInfo" scope="request">
				<option value="${task.orgId}"><eoms:id2nameDB id="${task.orgId}" beanId="tawSystemAreaDao" /></option>
				</logic:iterate>
				</logic:present>
			</select>
		</td>
	</tr>
</table>
<input type="hidden" name="taskId" value="${taskId}"/>
<input type="hidden" name="time" value="${time}"/>
<input type="hidden" name="timeType" value="${timeType}"/>
<input type="hidden" name="templateTypeId" value="${templateTypeId}"/>
<input type="hidden" name="templateId" value="${templateId}"/>

<table id="submit-btn" align="left">
	<tr>
		<td>
			<input type="submit" class="btn" value="查看" />
		</td>
	</tr>
</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
