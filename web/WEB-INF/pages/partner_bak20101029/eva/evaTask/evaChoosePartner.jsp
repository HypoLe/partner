<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<jsp:directive.page import="com.boco.eoms.partner.eva.model.PnrEvaTemplate"/>
<jsp:directive.page import="com.boco.eoms.partner.eva.model.PnrEvaKpiFactury"/>
<jsp:directive.page import="java.util.List"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'pnrEvaKpiInstanceForm'});
})
</script>
<%
PnrEvaTemplate pnrEvaTemplate = (PnrEvaTemplate)request.getAttribute("pnrEvaTemplate");
%>
<html:form action="/evaTasks.do?method=taskDetailList" styleId="pnrEvaKpiInstanceForm" method="post" > 
<table class="formTable" id="form">
	<caption>
		<div class="header center">考核实例执行</div>
	</caption>
	<tr>
		<td class="label">
			选择考核模板
		</td>
		<td class="content">
		<eoms:id2nameDB id="${templateTypeId}" beanId="pnrEvaTreeDao" />
		<td class="label">
			模板名称
		</td>
		<td class="content">
		<eoms:id2nameDB id="${templateId}" beanId="pnrEvaTemplateDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			时间周期类型
		</td>
		<td class="content">
			月度
		</td>
		<td class="label">
			选择时间
		</td>
		<td class="content">
		 ${year}年${month}月
		</td>
	</tr>
	<%
	if(pnrEvaTemplate.getLeaf().equals(PnrEvaConstants.NODE_LEAF)){
		PnrEvaKpiFactury pnrEvaKpiFactury =null;
		List facturyList = (List)request.getAttribute("facturyList");
	%>
	<tr>
			<td class="label">
			选择合作伙伴
		</td>
		<td class="content" colspan="3">
		<select name="partner" id="partner" >
					<option value="">
						--非厂商--
					</option>
					<%
					for(int i=0;i<facturyList.size();i++){
						pnrEvaKpiFactury = (PnrEvaKpiFactury)facturyList.get(i);
					%>
					<option value="<%=pnrEvaKpiFactury.getFactury() %>"><%=pnrEvaKpiFactury.getFacturyName() %></option>
					<%
					}
					%>
				</select>
		</td>
	</tr>
	<%
	}else{
	%>
	<tr><td  colspan="4">
	该考核表为汇总表,下级模板执行情况直接影响该表格的结果
	</td></tr>
	<%
	}
	%>
</table>
<table id="submit-btn" align="left">
	<tr>
		<td>
			<input type="submit" class="btn" value="确定考核" />
		</td>
	</tr>
</table>
	<input type="hidden" id="queryType" name="queryType" value="run"/>
	<input type="hidden" id="templateTypeId" name="templateTypeId" value="${templateTypeId}"/>
	<input type="hidden" id="taskId" name="taskId" value="${taskId}"/>
	<input type="hidden" id="year" name="year" value="${year}"/>
	<input type="hidden" id="month" name="month" value="${month}"/>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
