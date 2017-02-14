<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js">
</script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js">
</script>


<script type="text/javascript">
var myjs=jQuery.noConflict();
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
					var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '内容信息 ');
        	tabs.addTab('history-info', '审批信息 ');
    		tabs.activate(0);})
    		</script>
<div align="center">军警民共建管理</div>
<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
<div id="info-page">

      <div id="content-info" class="tabContent">
		<logic:notEmpty name="armyPoliceCivilian" scope="request">
		
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					填报人
				</td>
				<td class="content">
					<eoms:id2nameDB id="${armyPoliceCivilian.writePerson}"
						beanId="tawSystemUserDao" />
				</td>
				<td class="label"> 
					填报时间
				</td>
				<td class="content">
					${armyPoliceCivilian.writeTime}
				</td>
				<%---
				<td class="label">
					所属地市
				</td>

				<td class="content">
					<eoms:id2nameDB id="${armyPoliceCivilian.belongTheLocal}"
				beanId="tawSystemAreaDao" />
				</td>
----%>

			</tr>
			<tr>
				<td class="label">
					审核人
				</td>
				<td class="content">
					<eoms:id2nameDB id="${armyPoliceCivilian.approvingPerson}"
				beanId="tawSystemUserDao" />
				</td>


				<td class="label">
					项目名称
				</td>

				<td class="content">
					${armyPoliceCivilian.projectName}
				</td>

			</tr>
			<tr>
				<td class="label">
					军警民共建单位名称

				</td>
				<td class="content">
					${armyPoliceCivilian.nameOfOrganization}
				</td>

				<td class="label">
					负责光缆线路里程（km）
				</td>
				<td class="content">
					${armyPoliceCivilian.lengthOfOpticalCableRoutes}
				</td>
			</tr>
			<tr>
				<td class="label">
					实际效果
				</td>
				<td class="content">
					${armyPoliceCivilian.realEffect}
				</td>
				
			</tr>
			<tr>
				<td class="label">
					合作内容
				</td>

				<td class="content" colspan="3">
					${armyPoliceCivilian.cooperativeContent}
				</td>
			</tr>
			<tr>
				<td class="label">
					备注
				</td>
				<td colspan="3" class="content">
					${armyPoliceCivilian.remarks}
				</td>
			</tr>	
		</table>
		
		</logic:notEmpty>
		<c:if test="${'edit' eq type}" >
		
<html:form 
	action="armypolicecivilian.do?method=approveing&id=${armyPoliceCivilian.id}"
	styleId="FaultSheetManagementForm"  >
	
	<div class="ui-widget-header">信息审核</div>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					审核结果*
				</td>

				<td class="content" >
				<select name="approvingresult" id="approvingresult">
				<option value="1">通过审核</option>
				<option value="2">驳回</option>
				</select>
				</td>
			</tr>
			<tr>
				<td class="label">
					建议
				</td>
				<td class="content" >
					<textarea class="textarea max" name="suggest" id="suggest"></textarea>
				</td>
			</tr>
			
		</table>
	
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="确定"></html:submit>
		<html:reset styleClass="btn" property="method.reset"
		styleId="method.reset" value="取消"></html:reset>
</html:form>
</c:if>	
		</div>
		<div id="history-info" class="tabContent">
			<logic:notEmpty name="armyPoliceCivilian" scope="request">
	
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					审核人
				</td>
				<td class="content">
					<eoms:id2nameDB id="${armyPoliceCivilian.approvingPerson}"
				beanId="tawSystemUserDao" />
				</td>
				<td class="label">审核结果</td>
				<td class="content">${armyPoliceCivilian.approvingresult}</td>
			</tr>
			<tr>
				<td class="label">审核意见</td>
				<td class="content" colspan="3">${armyPoliceCivilian.suggest}</td>
			</tr>
			<tr>
				<td class="label">审核时间</td>
				<td class="content" colspan="3">${armyPoliceCivilian.approvingtime}</td>
			</tr>
			
		</table>
		</logic:notEmpty>
		<logic:empty name="armyPoliceCivilian" scope="request">没有记录</logic:empty>
		</div>
		
		</div>


<%@ include file="/common/footer_eoms.jsp"%>