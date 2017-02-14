<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tranKpiConfigForm'});
});
function submitSave(){
	if(!v.check()){
		return false;
	}
	document.getElementById('numRelation').value = document.getElementById('numRelationOne').value + ',' + document.getElementById('numRelationTwo').value
	$("tranKpiConfigForm").submit(); 
}

</script>

<html:form action="/tranKpiConfigs.do?method=save" styleId="tranKpiConfigForm" method="post"> 
<fmt:bundle basename="com/boco/eoms/partner/tranEva/config/ApplicationResources-partner-tranEva">
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tranKpiConfig.form.heading"/></div>
	</caption>
	<%
		String algorithmValue = String.valueOf(request.getAttribute("algorithmValue"));
		if(!"1".equals(algorithmValue)){
	%>
<c:if test="${tranKpiConfigForm.parentNodeId!=1}">
	<tr>
		<td  class="label">
			<fmt:message key="tranKpiConfig.minValue" />
		</td>
		<td class="content">
			<html:text property="minValue" styleId="minValue"
						styleClass="text medium"
						alt="re:/^[0-9]+\.{0,1}[0-9]{0,2}$/,allowBlank:false,vtext:''" value="${tranKpiConfigForm.minValue}" />
		</td>
	</tr>

	<tr>
		<td  class="label">
			<fmt:message key="tranKpiConfig.maxValue" />
		</td>
		<td class="content">
			<html:text property="maxValue" styleId="maxValue"
						styleClass="text medium" onblur="changeRate(document.getElementById('minValue').value,document.getElementById('maxValue').value)"
						alt="re:/^[0-9]+\.{0,1}[0-9]{0,2}$/,allowBlank:false,vtext:''" value="${tranKpiConfigForm.maxValue}" />
		</td>
	</tr>

	<tr>
		<td  class="label">
			<fmt:message key="tranKpiConfig.valueConfig" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-tranEva" dictId="config" defaultId="${tranKpiConfigForm.valueConfig}" 
								 selectId="valueConfig" beanId="selectXML" alt="allowBlank:false,vtext:'请选择值域判断标识'" />		
		</td>
	</tr>
</c:if>
<c:if test="${tranKpiConfigForm.parentNodeId==1}">
	<tr>
		<td  class="label">
			<fmt:message key="tranKpiConfig.numConfig" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-tranEva" dictId="config" defaultId="${tranKpiConfigForm.numConfig}" 
								 selectId="numConfig" beanId="selectXML" alt="allowBlank:false,vtext:'请选择设备数量判断标识'" />
		</td>
	</tr>
	<tr>		
		<td  class="label">
			<fmt:message key="tranKpiConfig.algorithm" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-tranEva" dictId="algorithm" defaultId="${tranKpiConfigForm.algorithm}" 
								 selectId="algorithm" beanId="selectXML" alt="allowBlank:false,vtext:'请选择得分算法'" />
		</td>
	</tr>
	<tr>		
		<td  class="label">
			<fmt:message key="tranKpiConfig.numRelation" />
		</td>
		<td class="content">
			<input id="numRelationOne" styleClass="text medium" style="width:50px;" alt="re:/^[0-9]+$/,re_vt:'请输入分数'"  />~<input id="numRelationTwo" styleClass="text medium" style="width:50px;" alt="re:/^[0-9]+$/,re_vt:'请输入分数'" value="" />		
		</td>		
	</tr>	
</c:if>	
	<tr>
		<td  class="label">
			<fmt:message key="tranKpiConfig.weight" />
		</td>
		<td class="content">
			<html:text property="weight" styleId="weight"
						styleClass="text medium"
						alt="re:/^-?[0-9]+\.{0,1}[0-9]{0,2}$/,allowBlank:false,vtext:''" value="${tranKpiConfigForm.weight}" />
		</td>
	</tr>
	<%} %>
	<tr>
		<td  class="label">
			<fmt:message key="tranKpiConfig.remark" />
		</td>
		<td class="content">
			<textarea name="remark" id="remark" maxLength="1000" rows="2" style="width:98%;" alt="allowBlank:true,vtext:'',maxLength:255" >${tranKpiConfigForm.remark}</textarea>		
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
		<c:if test="${tranKpiConfigForm.parentNodeId==1}">
			<input type="button"  class="btn" onclick="submitSave();" value="<fmt:message key="button.save"/>" />
		</c:if>
		<c:if test="${tranKpiConfigForm.parentNodeId!=1}">
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</c:if>		
			<c:if test="${not empty tranKpiConfigForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('你确定删除?')){
						var url='${app}/partner/tranEva/tranKpiConfigs.do?method=remove&nodeId=${tranKpiConfigForm.nodeId}';
						location.href=url}"
						/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="numRelation" value="${tranKpiConfigForm.numRelation}" />
<html:hidden property="id" value="${tranKpiConfigForm.id}" />
<html:hidden property="nodeId" value="${tranKpiConfigForm.nodeId}" />
<html:hidden property="parentNodeId" value="${tranKpiConfigForm.parentNodeId}" />
<html:hidden property="leaf" value="${tranKpiConfigForm.leaf}" />
<html:hidden property="kpiId" value="${tranKpiConfigForm.kpiId}" />

<c:if test="${tranKpiConfigForm.parentNodeId==1}">
	<html:hidden property="nodeType" value="group" />
</c:if>
<c:if test="${tranKpiConfigForm.parentNodeId!=1}">
	<html:hidden property="nodeType" value="leaf" />
</c:if>
</html:form>
<script type="text/javascript">
var numRelationValue = '${tranKpiConfigForm.numRelation}';
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
	$("tranKpiConfigForm").submit(); 
}
function changeRate(x,y){
	if(parseFloat(y)< parseFloat(x)){
		alert('最小值不能大于最大值！');
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>