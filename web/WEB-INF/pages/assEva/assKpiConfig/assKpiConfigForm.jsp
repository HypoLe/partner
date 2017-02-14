<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'assKpiConfigForm'});
});
function submitSave(){
	if(!v.check()){
		return false;
	}
	document.getElementById('numRelation').value = document.getElementById('numRelationOne').value + ',' + document.getElementById('numRelationTwo').value
	$("assKpiConfigForm").submit(); 
}

</script>

<html:form action="/assKpiConfigs.do?method=save" styleId="assKpiConfigForm" method="post"> 
<fmt:bundle basename="com/boco/eoms/assEva/config/ApplicationResources-partner-assEva">
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="assKpiConfig.form.heading"/></div>
	</caption>
	<%
		String algorithmValue = String.valueOf(request.getAttribute("algorithmValue"));
		if(!"1".equals(algorithmValue)){
	%>
<c:if test="${assKpiConfigForm.parentNodeId!=1}">
	<tr>
		<td  class="label">
			<fmt:message key="assKpiConfig.minValue" />
		</td>
		<td class="content">
			<html:text property="minValue" styleId="minValue"
						styleClass="text medium"
						alt="re:/^[0-9]+\.{0,1}[0-9]{0,2}$/,allowBlank:false,vtext:''" value="${assKpiConfigForm.minValue}" />
		</td>
	</tr>

	<tr>
		<td  class="label">
			<fmt:message key="assKpiConfig.maxValue" />
		</td>
		<td class="content">
			<html:text property="maxValue" styleId="maxValue"
						styleClass="text medium" onblur="changeRate(document.getElementById('minValue').value,document.getElementById('maxValue').value)"
						alt="re:/^[0-9]+\.{0,1}[0-9]{0,2}$/,allowBlank:false,vtext:''" value="${assKpiConfigForm.maxValue}" />
		</td>
	</tr>

	<tr>
		<td  class="label">
			<fmt:message key="assKpiConfig.valueConfig" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-assEva" dictId="config" defaultId="${assKpiConfigForm.valueConfig}" 
								 selectId="valueConfig" beanId="selectXML" alt="allowBlank:false,vtext:'请选择值域判断标识'" />		
		</td>
	</tr>
</c:if>
<c:if test="${assKpiConfigForm.parentNodeId==1}">
	<tr>
		<td  class="label">
			<fmt:message key="assKpiConfig.numConfig" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-assEva" dictId="config" defaultId="${assKpiConfigForm.numConfig}" 
								 selectId="numConfig" beanId="selectXML" alt="allowBlank:false,vtext:'请选择设备数量判断标识'" />
		</td>
	</tr>
	<tr>		
		<td  class="label">
			<fmt:message key="assKpiConfig.algorithm" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-assEva" dictId="algorithm" defaultId="${assKpiConfigForm.algorithm}" 
								 selectId="algorithm" beanId="selectXML" alt="allowBlank:false,vtext:'请选择得分算法'" />
		</td>
	</tr>
	<tr>		
		<td  class="label">
			<fmt:message key="assKpiConfig.numRelation" />
		</td>
		<td class="content">
			<input id="numRelationOne" styleClass="text medium" style="width:50px;" alt="re:/^[0-9]+$/,re_vt:'请输入分数'"  />~<input id="numRelationTwo" styleClass="text medium" style="width:50px;" alt="re:/^[0-9]+$/,re_vt:'请输入分数'" value="" />		
		</td>		
	</tr>	
</c:if>	
	<tr>
		<td  class="label">
			<fmt:message key="assKpiConfig.weight" />
		</td>
		<td class="content">
			<html:text property="weight" styleId="weight"
						styleClass="text medium"
						alt="re:/^-?[0-9]+\.{0,1}[0-9]{0,2}$/,allowBlank:false,vtext:''" value="${assKpiConfigForm.weight}" />
		</td>
	</tr>
	<%} %>
	<tr>
		<td  class="label">
			<fmt:message key="assKpiConfig.remark" />
		</td>
		<td class="content">
			<textarea name="remark" id="remark" maxLength="1000" rows="2" style="width:98%;" alt="allowBlank:true,vtext:'',maxLength:255" >${assKpiConfigForm.remark}</textarea>		
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
		<c:if test="${assKpiConfigForm.parentNodeId==1}">
			<input type="button"  class="btn" onclick="submitSave();" value="<fmt:message key="button.save"/>" />
		</c:if>
		<c:if test="${assKpiConfigForm.parentNodeId!=1}">
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</c:if>		
			<c:if test="${not empty assKpiConfigForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('你确定删除?')){
						var url='${app}/assEva/assKpiConfigs.do?method=remove&nodeId=${assKpiConfigForm.nodeId}';
						location.href=url}"
						/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="numRelation" value="${assKpiConfigForm.numRelation}" />
<html:hidden property="id" value="${assKpiConfigForm.id}" />
<html:hidden property="nodeId" value="${assKpiConfigForm.nodeId}" />
<html:hidden property="parentNodeId" value="${assKpiConfigForm.parentNodeId}" />
<html:hidden property="leaf" value="${assKpiConfigForm.leaf}" />
<html:hidden property="kpiId" value="${assKpiConfigForm.kpiId}" />

<c:if test="${assKpiConfigForm.parentNodeId==1}">
	<html:hidden property="nodeType" value="group" />
</c:if>
<c:if test="${assKpiConfigForm.parentNodeId!=1}">
	<html:hidden property="nodeType" value="leaf" />
</c:if>
</html:form>
<script type="text/javascript">
var numRelationValue = '${assKpiConfigForm.numRelation}';
if(''!=numRelationValue){
	var score = numRelationValue.split(',');
	document.getElementById('numRelationOne').value = score[0];
	document.getElementById('numRelationTwo').value = score[1];
}
function submitSave(){
	var temp = document.getElementById('numRelationOne').value + ',' + document.getElementById('numRelationTwo').value;
	document.getElementById('numRelation').value = temp;
	if(!v.check()){
		return false;
	}
	$("assKpiConfigForm").submit(); 
}
function changeRate(x,y){
	if(parseFloat(y)< parseFloat(x)){
		alert('最小值不能大于最大值！');
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>