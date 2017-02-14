<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.contract.table.util.CtTableGeneralConstants"/>
<jsp:directive.page import="com.boco.eoms.eva.util.EvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'evaTemplateForm'});
	// 定义panel
	var tabs = new Ext.TabPanel('tabs');
	var tplTab = tabs.addTab('tpl', "模板");
	var kpiTab = tabs.addTab('kpi', "指标");
	tplTab.on('activate',function(){
	   	//location.href = "${app}/eva/evaTemplates.do?method=newTemplate&nodeId="
	});
	kpiTab.on('activate',function(){
	   	//location.href = "${app}/eva/evaKpis.do?method=newKpi&nodeId="
	});
	var tplId = document.forms[0].id.value;
	if ("" != tplId) {
	   	//$('kpiList').src="${app}/eva/evaTemplateKpis.do?method=listKpiOfTemplate&templateId=${evaTemplateForm.id}";
	} else {
	   	kpiTab.disabled = true;
	}
	tabs.activate('tpl');

})

function activeTemplate() {
	var msg = "模板下属指标权重和值为"+'${sessionScope.evaTW}'+"，确认激活？";
		if (confirm(msg)==true){
			document.forms[0].action = "${app}/eva/evaTemplates.do?method=activeTemplate";
			document.forms[0].submit();	
		}	
}

function selectChange(templateTypeId) {
	document.forms[0].action = "evaTemplates.do?method=newTemplate";
	document.forms[0].submit();
} 


</script>
	<eoms:xbox id="orgId" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="orgId" handler="orgIdName"
		textField="orgIdName"
		checktype="dept" single="true"		
	  ></eoms:xbox>
	<eoms:xbox id="partnerDept" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="partnerDept" handler="partnerDeptName"
		textField="partnerDeptName"
		checktype="dept" single="true"		
	  ></eoms:xbox>
<div id="tabs">
<!-- 模板面板 -->
<div id="tpl" class="tabContent">
<html:form action="/evaTemplates.do?method=saveTemplate" styleId="evaTemplateForm" method="post"> 
<table class="formTable" id="tplForm" style="width:88%" align="center">
	<caption>
		<div class="header center">考核模板</div>
		<c:if test="${not empty requestScope.failInfo}">
		<div class="help">     
        <span>
      	<dl>
      	  <dt class="warn">${requestScope.failInfo}</dt>
      	</dl>
      	</span>
        <div class="clear"></div>
      	</div>
      	</c:if>
	</caption>
	<tr>
		<td class="label" width="25%">
			模板名称
		</td>
		<td class="content" colspan="3" width="75%">
			<html:text property="templateName" styleId="templateName" style="width:88%" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请输入模版名称'" value="${evaTemplateForm.templateName}" />
		</td>
	</tr>
	<c:if test="${not empty evaTemplateForm.id}">
	<tr>
		<td class="label">
			创建人
		</td>
		<td class="content">
			<eoms:id2nameDB id="${evaTemplateForm.creator}" beanId="tawSystemUserDao" />
		</td>
		
		<td class="label">
			创建时间
		</td>
		<td class="content">
			${evaTemplateForm.createTime}
		</td>
	</tr>
	
	<tr>
		<td class="label">
			模板状态
		</td>
		<td class="content">
			<eoms:dict key="dict-eva" dictId="activated" itemId="${evaTemplateForm.activated}" beanId="id2nameXML" />
		</td>
		<td class="label">
			关联协议
		</td>
		<td class="content">
			<c:if test="${evaTemplateForm.agrwor!='tempTask'}">
				<a href="${app}/partner/agreement/pnrAgreementMains.do?method=detail&id=${evaTemplateForm.agreementId}" target="_blank">
					<eoms:id2nameDB id="${evaTemplateForm.agreementId}" beanId="pnrAgreementMainDao" />
				</a>
			</c:if>
			<c:if test="${evaTemplateForm.agrwor=='tempTask'}">
				<a href="${app}/partner/tempTask/pnrTempTaskMains.do?method=detail&id=${evaTemplateForm.agreementId}" target="_blank">
					<eoms:id2nameDB id="${evaTemplateForm.agreementId}" beanId="pnrTempTaskMainDao" />
				</a>
			</c:if>
		<input type="hidden" id="agreementId" name="agreementId" value="${evaTemplateForm.agreementId}"/>			
		<input type="hidden" id="contractId" name="contractId" value="${evaTemplateForm.contractId}"/>
		<input type="hidden" id="themeName" name="themeName" value="${evaTemplateForm.themeName}"/>				
		<input type="hidden" id="themeId" name="themeId" value="${evaTemplateForm.themeId}"/>						
		</td>
	</tr>
	</c:if>
	<c:if test="${evaTemplateForm.id==null}">
		<tr>
		<td class="label">
			模板状态
		</td>
		<td class="content">
			新建
		</td>
		<td class="label">
			关联协议
		</td>
		<td class="content">
			<c:if test="${evaTemplateForm.agrwor!='tempTask'}">
				<a href="${app}/partner/agreement/pnrAgreementMains.do?method=detail&id=${evaTemplateForm.agreementId}" target="_blank">
					<eoms:id2nameDB id="${evaTemplateForm.agreementId}" beanId="pnrAgreementMainDao" />
				</a>
			</c:if>
			<c:if test="${evaTemplateForm.agrwor=='tempTask'}">
				<a href="${app}/partner/tempTask/pnrTempTaskMains.do?method=detail&id=${evaTemplateForm.agreementId}" target="_blank">
					<eoms:id2nameDB id="${evaTemplateForm.agreementId}" beanId="pnrTempTaskMainDao" />
				</a>
			</c:if>
		<input type="hidden" id="agreementId" name="agreementId" value="${evaTemplateForm.agreementId}"/>			
		<input type="hidden" id="contractId" name="contractId" value="${evaTemplateForm.contractId}"/>
		<input type="hidden" id="themeName" name="themeName" value="${evaTemplateForm.themeName}"/>				
		<input type="hidden" id="themeId" name="themeId" value="${evaTemplateForm.themeId}"/>						
		</td>
	</tr>
	</c:if>
	<tr>
		<td class="label">
			开始时间
		</td>
		<td class="content">
			<input type="text" name="startTime" size="20" onclick="popUpCalendar(this, this,null,null,null,true,-1);" value="${evaTemplateForm.startTime}" readonly="true" class="text" alt="allowBlank:false,vtext:'请选择考核开始时间...'" />
		</td>
		
		<td class="label">
			结束时间
		</td>
		<td class="content">
			<input type="text" name="endTime" size="20" onclick="popUpCalendar(this, this,null,null,null,true,-1);" value="${evaTemplateForm.endTime}" readonly="true" class="text" alt="allowBlank:false,vtext:'请选择考核计划截止时间...'" />
		</td>
	</tr>
	<tr>
		<td class="label" width="25%">
			所属专业
		</td>
		<td class="content" width="25%">
			<html:text property="professional" styleId="professional" size="20" 
						styleClass="text medium"
						value="${evaTemplateForm.professional}" />
		</td>
		<td class="label">
			考核周期
		</td>
		<td class="content" colspan="3">
					<eoms:dict key="dict-eva" dictId="cycle" defaultId="${evaTemplateForm.cycle}"
						 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核周期'" />
		</td>
	</tr>
	<tr>
		<td class="label">
			执行者
		</td>
		<td class="content">
			<input type="text" id="orgIdName" name="orgIdName" class="text medium"
						value='<eoms:id2nameDB id="${evaTemplateForm.orgId}" beanId="tawSystemDeptDao"/>'
						alt="allowBlank:false,vtext:''" readonly="true"/>
			<html:hidden property="orgId" value="${evaTemplateForm.orgId}"/>
		</td>
		<td class="label">
			被考核公司
		</td>
		<td class="content">
			<input type="text" id="partnerDeptName" name="partnerDeptName" class="text medium"
						value='<eoms:id2nameDB id="${evaTemplateForm.partnerDept}" beanId="tawSystemDeptDao"/>'
						alt="allowBlank:false,vtext:''" readonly="true"/>
			<html:hidden property="partnerDept" value="${evaTemplateForm.partnerDept}"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" style="width:88%" 
						styleClass="textarea"  value="${evaTemplateForm.remark}" />
		</td>
	</tr>
			
</table>
<table>
	<tr>
		<td>
		<c:if test="${evaTemplateForm.activated != 1}">
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</c:if>
		
		<c:if test="${not empty evaTemplateForm.id 
			&& not empty requestScope.parentNodeId 
				&& evaTemplateForm.activated == 0}">
		
			<input type="button" class="btn" value="激活模板" onclick="activeTemplate()"/>
		
		</c:if>
	</tr>
</table>
<input type="hidden" id="parentNodeId" name="parentNodeId" value="${requestScope.parentNodeId}" />
<input type="hidden" id="id" name="id" value="${evaTemplateForm.id}" />
</html:form>
</div>

<!-- 指标面板 -->
<div id="kpi" class="tabContent">
	<c:if test="${not empty evaTemplateForm.id }">
	<iframe id="fileList" name="fileList" frameborder="0" width="100%" height="500" 
		src="${app}/eva/evaKpis.do?method=listNextLevelKpi&parentNodeId=${requestScope.parentNodeId}">
	</iframe>
	</c:if>
</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>