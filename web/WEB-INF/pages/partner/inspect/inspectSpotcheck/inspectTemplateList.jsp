<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%response.setHeader("cache-control","public"); %>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
jq(function(){
	 v = new eoms.form.Validation({form:'taskOrderForm'});
})

</script>

<form action="${app}/partner/inspect/inspectSpotcheckAction.do?method=findInspectTemplateList" id="taskOrderForm" method="post">
		<table id="sheet" class="listTable">
			<tr>
				<td class="label">
					模版名称
				</td>
				<td class="content">
					<input class="text" type="text" name="templateName"
						id="templateNameStringLike"  />
				</td>
				<td class="label">
					巡检专业
				</td>
				<td class="content">
					<eoms:comboBox name="specialty" id="specialty" 
						initDicId="11225"  styleClass="select" />
				</td>
			</tr>
			<tr>
				<td class="label">年<span style="color:red">*</span></td>
				<td class="content">
					<eoms:dict key="dict-partner-inspect" selectId="year" dictId="yearflag" 
						beanId="selectXML" cls="select" alt="allowBlank:false" />
				</td>
				<td class="label">月<span style="color:red">*</span></td>
				<td class="content">
					<eoms:dict key="dict-partner-inspect" selectId="month" dictId="monthflag" 
						beanId="selectXML" cls="select" alt="allowBlank:false"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div align="left">
						<input type="submit" class="btn" value="查询" />
						&nbsp;&nbsp;&nbsp;
					</div>
				</td>
			</tr>
		</table>
</form>

<br>		
<logic:present name="inspectTemplateList" scope="request">
	<display:table name="inspectTemplateList" cellspacing="0" 
		cellpadding="0" id="inspectTemplateList" pagesize="${pagesize}"
		class="table inspectTemplateList" requestURI="inspectSpotcheckAction.do" 
		sort="list" partialList="true" size="${resultSize}">
				
		<display:column property="templateName" title="模版名称" />
		<display:column sortable="true" headerClass="sortable" title="专业">
			<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${inspectTemplateList.specialty}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="资源类型">
			<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${inspectTemplateList.res_type}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="添加人员">
			<eoms:id2nameDB beanId="tawSystemUserDao" id="${inspectTemplateList.add_user}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="制作单位">
			<eoms:id2nameDB id="${inspectTemplateList.dept}" beanId="tawSystemDeptDao"/>
		</display:column>
		<display:column  title="编辑">
			<a href="${app }/partner/inspect/inspectSpotcheckAction.do?method=findSpotcheckTemplateList&inspectTemplateId=${inspectTemplateList.template_id}&year=${year }&month=${month }">
				<img src="${app }/images/icons/edit.gif"><a>
		</display:column>
	</display:table>
</logic:present>

<%@ include file="/common/footer_eoms.jsp"%>