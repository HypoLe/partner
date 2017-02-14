<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.siteEva.util.SiteEvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'siteEvaTemplateForm'});
	
	// 定义panel
	var tabs = new Ext.TabPanel('tabs');
	var tplTab = tabs.addTab('tpl', "模板");
	var kpiTab = tabs.addTab('kpi', "指标");
	tplTab.on('activate',function(){
	   	//location.href = "${app}/partner/siteEva/siteEvaTemplates.do?method=newTemplate&nodeId="
	});
	kpiTab.on('activate',function(){
	   	//location.href = "${app}/partner/siteEva/siteEvaKpis.do?method=newKpi&nodeId="
	});
	var tplId = document.forms[0].id.value;
	if ("" != tplId) {
	   	//$('kpiList').src="${app}/partner/siteEva/siteEvaTemplateKpis.do?method=listKpiOfTemplate&templateId=${siteEvaTemplateForm.id}";
		
	} else {
	   	kpiTab.disabled = true;
	}
	tabs.activate('tpl');
<%--
	// 部门树
	var	deptTreeAction='${app}/xtree.do?method=dept';
	deptViewer = new Ext.JsonView("dept-list",
		'<div id="dept-{id}" class="viewlistitem-{nodeType}">{name}</div>',
		{ 
			multiSelect: true,
			emptyText : '<div>未选择地市</div>'
		}
	);
	var deptStr = '${requestScope.orgIds}';
	deptViewer.jsonData = eoms.JSONDecode(deptStr);
	deptViewer.refresh();
	
	deptTree = new xbox({
		btnId:'deptTreeBtn',dlgId:'dlg-dept',
		treeDataUrl:deptTreeAction,treeRootId:'-1',treeRootText:'部门',treeChkMode:'',treeChkType:'dept',
		viewer:deptViewer,saveChkFldId:'orgIds'
	});
 --%>		
	// 部门树
	var	parTreeAction='${app}/xtree.do?method=dept';
	parViewer = new Ext.JsonView("par-list",
		'<div id="dept-{id}" class="viewlistitem-{nodeType}">{name}</div>',
		{ 
			multiSelect: true,
			emptyText : '<div>未选择合作伙伴</div>'
		}
	);
	var parStr = '${requestScope.parIds}';
	parViewer.jsonData = eoms.JSONDecode(parStr);
	parViewer.refresh();	
	deptTree = new xbox({
		btnId:'parTreeBtn',dlgId:'dlg-par',
		treeDataUrl:parTreeAction,treeRootId:'2',treeRootText:'合作伙伴',treeChkMode:'',treeChkType:'dept',
		viewer:parViewer,saveChkFldId:'parIds'
	});	
})

function activeTemplate() {
	var msg = "模板下属指标权重和值为"+'${sessionScope.siteEvaTW}'+"，确认激活？";
		if (confirm(msg)==true){
			var url = "${app}/partner/siteEva/siteEvaTemplates.do?method=activeTemplate&templateId=${siteEvaTemplateForm.id}&nodeId=${requestScope.parentNodeId}";
			location.href = url;
		}	
}

function selectChange(templateTypeId) {
	document.forms[0].action = "siteEvaTemplates.do?method=newTemplate";
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
<html:form action="/siteEvaTemplates.do?method=saveTemplate" styleId="siteEvaTemplateForm" method="post"> 
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
		<td class="label">
			模板名称
		</td>
		<td class="content" colspan="3">
			<html:text property="templateName" styleId="templateName" style="width:88%" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请输入模版名称'" value="${siteEvaTemplateForm.templateName}" />
		</td>
	</tr>
	<c:if test="${not empty siteEvaTemplateForm.id}">
	<tr>
		<td class="label">
			创建人
		</td>
		<td class="content">
			<eoms:id2nameDB id="${siteEvaTemplateForm.creator}" beanId="tawSystemUserDao" />
		</td>
		
		<td class="label">
			创建时间
		</td>
		<td class="content">
			${siteEvaTemplateForm.createTime}
		</td>
	</tr>   
	
	<tr>
		<td class="label">
			模板状态
		</td>
		<td class="content" colspan="3">
			<eoms:dict key="dict-partner-siteEva" dictId="activated" itemId="${siteEvaTemplateForm.activated}" beanId="id2nameXML" />
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
						value="${siteEvaTemplateForm.professional}" />
		</td>
		<td class="label">
			考核周期
		</td>
		<td class="content">
			<c:choose>
				<c:when test="${siteEvaTemplateForm.cycle != null}">
					<eoms:dict key="dict-partner-siteEva" dictId="cycle" defaultId="${siteEvaTemplateForm.cycle}"
						 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核周期'" />
				</c:when>
				<c:otherwise>
					<eoms:dict key="dict-partner-siteEva" dictId="cycle" defaultId="month"
						 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核周期'" />
				</c:otherwise>
			</c:choose>
		</td>		
	</tr>
	<%-- 
	<tr>
		<td class="label">
			所属地市
		</td>
		<td class="content" colspan="3">
			<div id="dept-list" class="viewer-box"></div>
			<input type="button" value="选择所属地市" id="deptTreeBtn" class="btn"/>
			
		</td>
		<input type="hidden" id="orgIds" name="orgIds" value = "中国联通山东公司" alt="allowBlank:false,vtext:'请选择所属地市'" />
	</tr>
	--%>
	<input type="hidden" id="orgIds" name="orgIds" value = "中国联通山东公司" alt="allowBlank:false,vtext:'请选择所属地市'" />
	<tr>
		<td class="label">
			考核合作伙伴
		</td>
		<td class="content" colspan="3">
			<div id="par-list" class="viewer-box"></div>
			<input type="button" value="选择合作伙伴" id="parTreeBtn" class="btn"/>
			<input type="hidden" id="parIds" name="parIds" alt="allowBlank:false,vtext:'请选择合作伙伴'" />
		</td>	
	</tr>
	<!-- 	
	<tr>
		<td class="label">
			接收单位类型
		</td>
		<td class="content">
			<c:choose>
				<c:when test="${siteEvaTemplateForm.orgType != null}">
					<eoms:dict key="dict-partner-siteEva" dictId="orgType" defaultId="${siteEvaTemplateForm.orgType}"
						 selectId="orgType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择组织类型'" />
				</c:when>
				<c:otherwise>
					<eoms:dict key="dict-partner-siteEva" dictId="orgType" defaultId="dept"
						 selectId="orgType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择组织类型'" />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	 -->
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" style="width:88%" 
						styleClass="textarea"  value="${siteEvaTemplateForm.remark}" />
		</td>
	</tr>
	<c:if test="${empty siteEvaTemplateForm.id}">
	<tr>
		<td class="label">
			复制模板指标
		</td>
		<c:if test="${empty requestScope.templateTypeId}">
		<td class="content" colspan="3">
			<input type="checkbox" id="isCopy" name="isCopy"
							onclick="javascript:copy(this)"/>复制
			&nbsp;&nbsp;&nbsp;&nbsp;
			模板分类：
			<select name="templateTypeId" id="templateTypeId" disabled="disabled" onchange="selectChange(this.options[this.options.selectedIndex].value)">
			<option value="">--请选择--</option>	
			<logic:iterate id="ttList" name="ttList">
					<option value="${ttList.nodeId}">
						<eoms:id2nameDB id="${ttList.nodeId}" beanId="siteEvaTreeDao" />
					</option>
				</logic:iterate>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			模板：
			<select name="templateId" id="templateId" disabled="disabled">
			<option value="">--请选择--</option>
				<logic:iterate id="tList" name="tList">
					<option value="${tList.nodeId}">
						<eoms:id2nameDB id="${tList.nodeId}" beanId="siteEvaTreeDao" />
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
			<select name="templateTypeId" id="templateTypeId"  onchange="selectChange(this.options[this.options.selectedIndex].value)">
			<option value=""><eoms:id2nameDB id="${requestScope.templateTypeId}" beanId="siteEvaTreeDao" /></option>	
			<logic:iterate id="ttList" name="ttList">
					<option value="${ttList.nodeId}">
						<eoms:id2nameDB id="${ttList.nodeId}" beanId="siteEvaTreeDao" />
					</option>
				</logic:iterate>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			模板：
			<select name="templateId" id="templateId">
			<option value="">--请选择--</option>
				<logic:iterate id="tList" name="tList">
					<option value="${tList.nodeId}">
						<eoms:id2nameDB id="${tList.nodeId}" beanId="siteEvaTreeDao" />
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
		<c:if test="${not empty siteEvaTemplateForm.id 
			&& not empty requestScope.parentNodeId 
				&& siteEvaTemplateForm.activated == 0}">
		<td>
			<input type="button" class="btn" value="激活模板" onclick="activeTemplate()"/>
		</td>
		</c:if>
	</tr>
</table>
<input type="hidden" id="parentNodeId" name="parentNodeId" value="${requestScope.parentNodeId}" />
<input type="hidden" id="id" name="id" value="${siteEvaTemplateForm.id}" />
</html:form>
</div>

<!-- 指标面板 -->
<div id="kpi" class="tab-content">
	<c:if test="${not empty siteEvaTemplateForm.id }">
	<iframe id="fileList" name="fileList" frameborder="0" width="100%" height="500" 
		src="${app}/partner/siteEva/siteEvaKpis.do?method=listNextLevelKpi&parentNodeId=${requestScope.parentNodeId}">
	</iframe>
	</c:if>
</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>