<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script language="javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'pnrEvaAuditForm'});
	
	// 定义panel
	var tabs = new Ext.TabPanel('tabs');
	var tplTab = tabs.addTab('tpl', "模板审核");
	//var kpiTab = tabs.addTab('kpi', "指标");
	tplTab.on('activate',function(){
	   	//location.href = "${app}/partner/eva/evaTemplates.do?method=newTemplate&nodeId="
	});
	//kpiTab.on('activate',function(){
	   	//location.href = "${app}/partner/eva/evaKpis.do?method=newKpi&nodeId="
	//});
	var tplId = document.forms[0].id.value;
	if ("" != tplId) {
	   	//$('kpiList').src="${app}/partner/eva/evaTemplateKpis.do?method=listKpiOfTemplate&templateId=${pnrEvaTemplateForm.id}";
		
	} //else {
	   //	kpiTab.disabled = true;
	//}
	tabs.activate('tpl');	
})
function check(){
      if(pnrEvaAuditForm.auditResult.value == ""){
       alert("请选择审核状态！");
       return false;
       }
     pnrEvaAuditForm.submit();
     return true;
  }
</script>
<div id="tabs">
<!-- 模板面板 -->
<div id="tpl" class="tab-content">
<html:form action="/evaAudit.do?method=audit" styleId="pnrEvaAuditForm" method="post" onsubmit="return validateForm();"> 
<div class="list-title">
	模板${template.templateName}详细信息
</div>
<table class="formTable" id="form" width="60%">
	<tr>
		<td class="label">
			模板名称
		</td>
		<td class="content" colspan="3">
			${template.templateName}
		</td>
	</tr>
	<tr>
		<td class="label">
			创建时间
		</td>
		<td class="content">
			${template.createTime}
		</td>
		<td class="label">
			创建人
		</td>
		<td class="content">
			${template.creator}
		</td>
	</tr>
	<tr>
		<td class="label">
			考核周期
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="cycle" itemId="${template.cycle}" beanId="id2nameXML" />
		</td>
		<td class="label">
			所属专业
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="professional" itemId="${template.professional}" beanId="id2nameXML" />
		</td>
	</tr>
	<tr>
		<td class="label">
			数据来源
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="dataSource" itemId="${template.dataSource}" beanId="id2nameXML" />
		</td>
		<td class="label">
			评估阶段
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="evaluationPhase" itemId="${template.evaluationPhase}" beanId="id2nameXML" />
		</td>
	</tr>
	<tr>
		<td class="label">
			是否客观评价
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="isImpersonal" itemId="${template.isImpersonal}" beanId="id2nameXML" />
		</td>
		<td class="label">
			考核层面
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="executeType" itemId="${template.executeType}" beanId="id2nameXML" />
		</td>
		
	</tr>
	<tr>
		<td class="label">
			权重锁定状态
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="isImpersonal" itemId="${template.isImpersonal}" beanId="id2nameXML" />
		</td>
		<td class="label">
			权重值
		</td>
		<td class="content">
			${template.weight}
		</td>
	</tr>
	<tr>
		<td class="label">
			考核内容
		</td>
		<td class="content" colspan="3">
			${template.content}
		</td>
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			${template.remark}
		</td>
	</tr>
</table>
<br/>
<div class="list-title">
	审批
</div>


<table class="formTable" id="tplForm" width="60%">
<input type="hidden" name="auditFlag">
	<tr>
		<td class="label">
		审核人	
		</td>
		<td class="content">
		${pnrEvaAuditForm.auditUser}
		</td>
	</tr>
		<tr>
		<td class="label">
		审核
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="auditResult" isQuery="false" 
			           defaultId="${pnrEvaAuditForm.auditResult}" selectId="auditResult" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择模板审核(字典)...'"/>	
		</td>
	</tr>
	<tr height="50">
		<td width="30%" class="label"> 
			审核说明 
		</td>
		<td width="100%" colspan="3">
			<html:textarea property="remark" styleId="remark" style="width:50%"
						styleClass="textarea" alt="maxLength:400" />
		</td>
</table>
<table>
	<tr>
		<td>
			<input type="button" class="btn" value="提交" onClick="return check();"/>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${pnrEvaAuditForm.id}" />
<html:hidden property="templateId" value="${pnrEvaAuditForm.templateId}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
</div>
</div>
