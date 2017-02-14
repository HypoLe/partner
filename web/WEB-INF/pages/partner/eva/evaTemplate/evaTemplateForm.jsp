<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<jsp:directive.page import="com.boco.eoms.partner.eva.model.PnrEvaTemplate"/>
<%@ page import="com.boco.eoms.partner.eva.util.PnrEvaDateUtil"%>
<%@ page import="com.boco.eoms.partner.eva.model.PnrEvaAuditInfo"%>
<%@ page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<%
List auditInfoList = new ArrayList();
if(request.getAttribute("auditInfoList")!=null){
	auditInfoList = (List)request.getAttribute("auditInfoList"); 
}
%>

  <div id="info-page">
  <!-- 模板面板 -->
  <div id="tpl-info" class="tabContent">
<html:form action="/evaTemplates.do?method=saveTemplate" styleId="pnrEvaTemplateForm" method="post" onsubmit="return validateForm();"> 
<table class="formTable" id="tplForm" style="width:88%" align="center">
	<caption>
	<div class="header center">考核模板
	</div>
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
						alt="allowBlank:false,vtext:'请输入模版名称'" value="${pnrEvaTemplateForm.templateName}" />
		</td>
	</tr>
	<c:if test="${not empty pnrEvaTemplateForm.id}">
	<tr>
		<td class="label">
			创建人
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrEvaTemplateForm.creator}" beanId="tawSystemUserDao" />
		</td>
		
		<td class="label">
			创建时间
		</td>
		<td class="content" format="{0,date,yyyy-MM-dd HH:mm:ss}">
			${pnrEvaTemplateForm.createTime}
		</td>
	</tr>
	
	<tr>
		<td class="label">
			模板状态
		</td>
		<td class="content" colspan="3">
			<eoms:dict key="dict-eva" dictId="activated" itemId="${pnrEvaTemplateForm.activated}" beanId="id2nameXML" />
		</td>
	</tr>
	</c:if>
	<tr>
		<td class="label">
			考核周期
		</td>
		<td class="content">
		<!-- 
		<eoms:dict key="dict-partner-eva" dictId="cycle" isQuery="false" 
			           defaultId="${pnrEvaTemplateForm.cycle}" selectId="cycle" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择...'"/>	
		 -->	 
		 月
		<input type="hidden" id="cycle" name="cycle" value="month" />           
		</td>
		<td class="label">
			数据来源
		</td>
		<td class="content">
		<!--
		<eoms:dict key="dict-partner-eva" dictId="dataSource" isQuery="false" 
			           defaultId="${pnrEvaTemplateForm.dataSource}" selectId="dataSource" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择...'"/>	
		 -->	
		 手动
		<input type="hidden" id="dataSource" name="dataSource" value="0" />            
		</td>
	</tr>
	<tr>
		<td class="label">
			所属专业
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="professional" isQuery="false" 
			           defaultId="${pnrEvaTemplateForm.professional}" selectId="professional" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择...'"/>	
		</td>
		<td class="label">
			考核层面 
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="executeType" isQuery="false" 
			           defaultId="${pnrEvaTemplateForm.executeType}" selectId="executeType" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择...'" onchange="changeExecuteType();showExecuteOrg();"/>	
		</td>
	</tr>
	<tr id='executeOrgTr'>
		<td class="label">
			评分执行者			
		</td>
		<td class="content" colspan="3">
			<select name="executeOrg" id="executeOrg" >
				<option value="<%=PnrEvaConstants.EXECUTE_ORG_EC%>">各地市</option>
				<option value="<%=PnrEvaConstants.EXECUTE_ORG_NMC%>">省网管中心</option>
				<option value="<%=PnrEvaConstants.EXECUTE_ORG_ND%>">省网络部</option>
				</select>
		</td>
	</tr>
	<tr>
		<td class="label">
			评估阶段
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-eva" dictId="evaluationPhase" isQuery="false" 
			           defaultId="${pnrEvaTemplateForm.evaluationPhase}" selectId="evaluationPhase" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择知识等级(字典)...'"/>	
		</td>
		<td class="label">
			是否客观评价
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="isImpersonal" isQuery="false" 
			           defaultId="${pnrEvaTemplateForm.isImpersonal}" selectId="isImpersonal" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择...'"/>	
		</td>
	</tr>
	
	<tr>
		<td class="label">
			锁定权重
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="weightLocked" isQuery="false" 
			           defaultId="${pnrEvaTemplateForm.isLock}" selectId="isLock" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择...'"/>	
		</td>
<td class="label">
			汇总方式
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="sumType" isQuery="false" 
			           defaultId="${pnrEvaTemplateForm.sumType}" selectId="sumType" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择...'"/>	
		</td>
	</tr>
	<tr>
		<td class="label">
		权重	
		</td>
		<td class="content" colspan="3">
		<input type="text" style="width:50%" name="weight" value="${pnrEvaTemplateForm.weight}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数',vtext:'请输入权重值;请输入100以下的数字',maxLength:6"/>
		<br>
		<font color="red">*可分配权重范围：${minWt}~${maxWt}</font>
		</td>
	</tr>
	<tr>
		<td class="label">
			考核内容
		</td>
		<td class="content" colspan="3">
			<html:textarea property="content" styleId="content" style="width:88%" 
						styleClass="textarea"  value="${pnrEvaTemplateForm.content}" alt="maxLength:500"/>
		</td>
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" style="width:88%" 
						styleClass="textarea"  value="${pnrEvaTemplateForm.remark}" alt="maxLength:500"/>
		</td>
	</tr>


	<!-- 建立叶子节点和模板审核状态的隐藏域 -->
	<html:hidden property="auditFlag" value="${pnrEvaTemplateForm.auditFlag}"/>
	<c:if test="${empty pnrEvaTemplateForm.id}">
	
	</c:if>
</table>
<table>
	<tr>
		<td>

		<c:if test="${pnrEvaTemplateForm.activated != 1 && pnrEvaTemplateForm.auditFlag != 1 }">
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</c:if>	
		<c:if test="${pnrEvaTemplateForm.activated == 1}">
			<input type="submit" class="btn" value="修改并解除激活" />
		</c:if>	
		</td>
		<c:if test="${not empty pnrEvaTemplateForm.id  && not empty requestScope.parentNodeId 
				&& pnrEvaTemplateForm.activated != 1}">
		<td>
		<c:if test="${pnrEvaTemplateForm.auditFlag != 1 && pnrEvaTemplateForm.auditFlag != 3}">
			<input type="button" class="btn" value="激活模板" onclick="activeTemplate()"/>&nbsp;
		</c:if>	
			<c:if test="${pnrEvaTemplateForm.auditFlag == 0 or pnrEvaTemplateForm.auditFlag == 3}">
			<input type="button" class="btn" value="送审" onclick="toAudit();"/>
			</c:if>
		</td>
		</c:if>
	</tr>
</table>
<input type="hidden" id="parentNodeId" name="parentNodeId" value="${requestScope.parentNodeId}" />
<input type="hidden" id="id" name="id" value="${pnrEvaTemplateForm.id}" />
<input type="hidden" id="area" name="area" value="${areaId}" />
</html:form>
</div>
  <div id="audit-info" class="tabContent">
<%
if(auditInfoList.size()>0){
%>
<table class="formTable" id="tplForm" width="60%">
		<%
		for(int i=0;i<auditInfoList.size();i++){
		PnrEvaAuditInfo auditInfo = (PnrEvaAuditInfo)auditInfoList.get(i);
		%>
	<tr>
	<td colspan="4">
	<div class="header center">审核记录
	</div>
	</td>
</tr>

	<tr>
		<td class="label">
		送审时间	
		</td>
		<td class="content">
		<%=PnrEvaDateUtil.date2String(auditInfo.getCreateTime())%>
		</td>
	</tr>
	<tr>
		<td class="label">
		审核角色	
		</td>
		<td class="content">
		<eoms:id2nameDB id="<%=auditInfo.getAuditOrg() %>" beanId="tawSystemSubRoleDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
		审核时间
		</td>
		<td class="content">
		<%=PnrEvaDateUtil.date2String(auditInfo.getAuditTime())%>
		</td>
	</tr>
	<tr>
		<td class="label">
		审核人	
		</td>
		<td class="content">
		<eoms:id2nameDB id="<%=auditInfo.getAuditUser()%>" beanId="tawSystemUserDao" />
		</td>
	</tr>
		<tr>
		<td class="label">
		审核结果
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-eva" dictId="auditFlag" itemId="<%=auditInfo.getAuditResult()%>" beanId="id2nameXML" />
		</td>
	</tr>
	<tr>
		<td class="label">
		审核说明	
		</td>
		<td class="content">
		<%=StaticMethod.nullObject2String(auditInfo.getRemark())%>
		</td>
	</tr>
	<%
		}
	%>
</table>
<%
}
%>
 </div>
 </div>
 		<%
 		PnrEvaTemplate parentTemplate = (PnrEvaTemplate)request.getAttribute("parentTemplate");  
		%>
<script type="text/javascript" >
	Ext.onReady(function(){
		var readTree;
		v = new eoms.form.Validation({form:'pnrEvaTemplateForm'});
		var tabs = new Ext.TabPanel('info-page');
    	var tplTab = tabs.addTab('tpl-info', '模板信息 ');
    	var tpaTab = tabs.addTab('audit-info', '审核信息 ');
		tabs.activate(0);
		<%
		if(auditInfoList.size()==0){
		%>
		tpaTab.disabled = true;
		<%
		}
		%>
		var parentExecuteType = <%=StaticMethod.nullObject2String(parentTemplate.getExecuteType(),"-1") %>;
		var executeType = document.getElementById("executeType").value;
		if(parentExecuteType!=-1){
		if(parentExecuteType==<%=PnrEvaConstants.EXECUTE_TYPE_PROVINCE%>){
			eoms.form.Options.del("executeType","<%=PnrEvaConstants.EXECUTE_TYPE_TOWN%>");
		}else if(parentExecuteType==<%=PnrEvaConstants.EXECUTE_TYPE_CITY%>){
			eoms.form.Options.del("executeType","<%=PnrEvaConstants.EXECUTE_TYPE_PROVINCE%>");
		}else if(parentExecuteType==<%=PnrEvaConstants.EXECUTE_TYPE_TOWN%>){
			eoms.form.Options.del("executeType","<%=PnrEvaConstants.EXECUTE_TYPE_PROVINCE%>");
			eoms.form.Options.del("executeType","<%=PnrEvaConstants.EXECUTE_TYPE_CITY%>");
		}
		}
		document.getElementById("executeType").value = executeType;
		showExecuteOrg();
		document.getElementById("executeOrg").value="${pnrEvaTemplateForm.executeOrg}";
	});

function changeExecuteType() {
		var parentExecuteType =  <%=StaticMethod.nullObject2String(parentTemplate.getExecuteType(),"-1") %>;
		var executeType = document.getElementById("executeType").value;
		if(parentExecuteType!=-1){
		if(executeType==parentExecuteType){
		eoms.form.Options.del("sumType","<%=PnrEvaConstants.SUMTYPE_INPUT%>");
		eoms.form.Options.del("sumType","<%=PnrEvaConstants.SUMTYPE_MIN%>");
		eoms.form.Options.del("sumType","<%=PnrEvaConstants.SUMTYPE_RATIO%>");
		eoms.form.Options.add("sumType","直接得分","<%=PnrEvaConstants.SUMTYPE_INPUT%>");
		document.getElementById("sumType").value = <%=PnrEvaConstants.SUMTYPE_INPUT %>;
		}else{
		eoms.form.Options.del("sumType","<%=PnrEvaConstants.SUMTYPE_INPUT%>");
		eoms.form.Options.del("sumType","<%=PnrEvaConstants.SUMTYPE_MIN%>");
		eoms.form.Options.del("sumType","<%=PnrEvaConstants.SUMTYPE_RATIO%>");
		eoms.form.Options.add("sumType","短板得分","<%=PnrEvaConstants.SUMTYPE_MIN%>");
		eoms.form.Options.add("sumType","维护比例","<%=PnrEvaConstants.SUMTYPE_RATIO%>");
		}
		}
} 

	function showExecuteOrg() {
		var executeType = document.getElementById("executeType").value;
		if(executeType==<%=PnrEvaConstants.EXECUTE_TYPE_CITY%>){
		document.getElementById("executeOrgTr").style.display = "";
		}else{
		document.getElementById("executeOrgTr").style.display = "none";  
		//如果不是地市，将executeOrg置为空
		document.getElementById("executeOrg").value="<%=PnrEvaConstants.EXECUTE_ORG_EC%>";
		}
	}

	function validateForm() {
		var weight = document.forms[0].weight.value;
		if (weight == '' || weight.length <= 0) {
			alert('请输入权重');
			return false;
		} else if (isNaN(weight)) {
			alert('权重值请输入数字');
			return false;
		} else if (weight < ${minWt}) {
			alert('您输入的权重不在可分配范围内');
			return false;
		} else if (-1!=${maxWt}&&weight > ${maxWt}) {
			alert('您输入的权重不在可分配范围内');
			return false;
		} else {
			return true;
		}
		document.getElementById('weight').focus() ;
		
	}
	function activeTemplate() {
	var msg = "模板下属节点权重和值为"+'${sessionScope.evaTW}'+"，确认激活？";
		if (confirm(msg)==true){
			var url = eoms.appPath+"/partner/eva/evaTemplates.do?method=activeTemplate&templateId=${pnrEvaTemplateForm.id}&nodeId=${requestScope.parentNodeId}";
			location.href = url;
		}	
	}

	function selectChange(templateTypeId) {
		document.forms[0].action = "evaTemplates.do?method=newTemplate";
		document.forms[0].submit();
	} 

	function toAudit(){	
		document.forms[0].action = "evaTemplates.do?method=saveAuditInfo&id=${pnrEvaTemplateForm.id}&area=${areaId}";
		document.forms[0].submit();
	}
</script>

<%@ include file="/common/footer_eoms.jsp"%>
