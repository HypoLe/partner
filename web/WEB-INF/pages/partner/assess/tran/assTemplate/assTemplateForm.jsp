<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.assess.util.AssConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'assTemplateForm'});
	
	// 定义panel
	var tabs = new Ext.TabPanel('tabs');
	var tplTab = tabs.addTab('tpl', "模板");
	var kpiTab = tabs.addTab('kpi', "指标");
	tplTab.on('activate',function(){
	   	//location.href = "${app}/partner/assess/tranAssTemplates.do?method=newTemplate&nodeId="
	});
	kpiTab.on('activate',function(){
	   	//location.href = "${app}/partner/assess/assKpis.do?method=newKpi&nodeId="
	});
	var tplId = document.forms[0].id.value;
	if ("" != tplId) {
	   	//$('kpiList').src="${app}/partner/assess/assTemplateKpis.do?method=listKpiOfTemplate&templateId=${assTemplateForm.id}";
		
	} else {
	   	kpiTab.disabled = true;
	}
	tabs.activate('tpl');
	
})

function activeTemplate() {
	var msg = "模板下属指标权重和值为"+'${sessionScope.assTW}'+"，确认激活？";
		if (confirm(msg)==true){
			var url = "${app}/partner/assess/tranAssTemplates.do?method=activeTemplate&templateId=${assTemplateForm.id}&nodeId=${requestScope.parentNodeId}";
			location.href = url;
		}	
}

function selectTran(templateTypeId) {
	document.forms[0].action = "tranAssTemplates.do?method=newTemplate";
	document.forms[0].submit();
} 

function copy(checkbox){	
	document.getElementById("templateTypeId").disabled=!checkbox.checked;
	document.getElementById("templateId").disabled=!checkbox.checked;
}
</script>

<div id="tabs">
<!-- 模板面板 -->
<div id="tpl" class="tab-content">
<html:form action="/tranAssTemplates.do?method=saveTemplate" styleId="assTemplateForm" method="post"> 
<table class="formTable" id="tplForm" style="width:88%" align="center">
	<caption>
		<div class="header center">评估模板</div>
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
		<td class="label">
			模板名称
		</td>
		<td class="content" colspan="3">
			<html:text property="templateName" styleId="templateName" style="width:88%" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请输入模版名称'" value="${assTemplateForm.templateName}" />
		</td>
	</tr>
	<c:if test="${empty assTemplateForm.id}">
		<tr>
			<td class="label">
				设备类型
			</td>	
			<td class="content" colspan="3">
				<eoms:dict key="dict-partner-assess" dictId="deviceType" defaultId="${assTemplateForm.deviceType}"
							selectId="deviceType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择设备类型'" />
			</td>						
		</tr>
	</c:if>
	<c:if test="${not empty assTemplateForm.id}">
	<tr>
		<td class="label">
			创建人
		</td>
		<td class="content">
			<eoms:id2nameDB id="${assTemplateForm.creator}" beanId="tawSystemUserDao" />
		</td>
		
		<td class="label">
			创建时间
		</td>
		<td class="content">
			${assTemplateForm.createTime}
		</td>
	</tr>   
	
	<tr>
		<td class="label">
			模板状态
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-assess" dictId="activated" itemId="${assTemplateForm.activated}" beanId="id2nameXML" />
		</td>
		<td class="label">
			设备类型
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-assess" dictId="deviceType" defaultId="${assTemplateForm.deviceType}"
							selectId="deviceType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择设备类型'" />
		</td>		
	</tr>
	</c:if>
	<tr>
		<td class="label">
			所属专业
		</td>
		<td class="content" >
			<html:text property="professional" styleId="professional" style="width:88%" 
						styleClass="text medium"
						value="传输" />
		</td>
		<td class="label">
			评估周期
		</td>
		<td class="content">
			<c:choose>
				<c:when test="${assTemplateForm.cycle != null}">
					<eoms:dict key="dict-partner-assess" dictId="cycle" defaultId="${assTemplateForm.cycle}"
						 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择评估周期'" />
				</c:when>
				<c:otherwise>
					<eoms:dict key="dict-partner-assess" dictId="cycle" defaultId="month"
						 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择评估周期'" />
				</c:otherwise>
			</c:choose>
		</td>		
	</tr>
	<input type="hidden" id="orgIds" name="orgIds" value = "中国联通山东公司" alt="allowBlank:false,vtext:'请选择所属地市'" />

	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" style="width:88%" 
						styleClass="textarea"  value="${assTemplateForm.remark}" />
		</td>
	</tr>
	<c:if test="${empty assTemplateForm.id}">
	<tr>
		<td class="label">
			复制模板指标
		</td>
		<c:if test="${empty requestScope.templateTypeId}">
		<td class="content" colspan="3">
			<input type="checkbox" id="isCopy" name="isCopy"
							onclick="javascript:copy(this)"/>复制<br>
			&nbsp;&nbsp;&nbsp;&nbsp;
			模板分类：
			<select name="templateTypeId" id="templateTypeId" disabled="disabled" onchange="selectTran(this.options[this.options.selectedIndex].value)">
			<option value="">--请选择--</option>	
			<logic:iterate id="ttList" name="ttList">
					<option value="${ttList.nodeId}">
						<eoms:id2nameDB id="${ttList.nodeId}" beanId="tranAssTreeDao" />
					</option>
				</logic:iterate>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			模板：
			<select name="templateId" id="templateId" disabled="disabled">
			<option value="">--请选择--</option>
				<logic:iterate id="tList" name="tList">
					<option value="${tList.nodeId}">
						<eoms:id2nameDB id="${tList.nodeId}" beanId="tranAssTreeDao" />
					</option>
				</logic:iterate>
			</select>
		</td>
		</c:if>
		
		<c:if test="${not empty requestScope.templateTypeId}">
		<td class="content" colspan="3">
			<input type="checkbox" id="isCopy" name="isCopy"
							onclick="javascript:copy(this)" checked="checked"/>是否复制
			&nbsp;&nbsp;&nbsp;&nbsp;
			模板分类：
			<select name="templateTypeId" id="templateTypeId"  onchange="selectTran(this.options[this.options.selectedIndex].value)">
			<option value=""><eoms:id2nameDB id="${requestScope.templateTypeId}" beanId="tranAssTreeDao" /></option>	
			<logic:iterate id="ttList" name="ttList">
					<option value="${ttList.nodeId}">
						<eoms:id2nameDB id="${ttList.nodeId}" beanId="tranAssTreeDao" />
					</option>
				</logic:iterate>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			模板：
			<select name="templateId" id="templateId">
			<option value="">--请选择--</option>
				<logic:iterate id="tList" name="tList">
					<option value="${tList.nodeId}">
						<eoms:id2nameDB id="${tList.nodeId}" beanId="tranAssTreeDao" />
					</option>
				</logic:iterate>
			</select>
		</td>
		</c:if>
	</tr>
	
	</c:if>
</table>
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</td>
		<c:if test="${not empty assTemplateForm.id 
			&& not empty requestScope.parentNodeId 
				&& assTemplateForm.activated == 0}">
		<td>
			<input type="button" class="btn" value="激活模板" onclick="activeTemplate()"/>
		</td>
		</c:if>
	</tr>
</table>
<input type="hidden" id="parentNodeId" name="parentNodeId" value="${requestScope.parentNodeId}" />
<input type="hidden" id="id" name="id" value="${assTemplateForm.id}" />
</html:form>
</div>

<!-- 指标面板 -->
<div id="kpi" class="tab-content">
	<c:if test="${not empty assTemplateForm.id }">
	<iframe id="fileList" name="fileList" frameborder="0" width="100%" height="500" 
		src="${app}/partner/assess/tranAssKpis.do?method=listNextLevelKpi&parentNodeId=${requestScope.parentNodeId}">
	</iframe>
	</c:if>
</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>