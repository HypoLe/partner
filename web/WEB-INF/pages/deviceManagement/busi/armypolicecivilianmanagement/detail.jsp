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
<div align="center">军警民共建管理详情页面</div>
	<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
	<c:if test="${armyPoliceCivilian.approvingresult eq '通过审核'}">
<form action="armypolicecivilian.do?method=archived&id=${armyPoliceCivilian.id}" method="post"
	id="ArmyPoliceCivilianForm" name="ArmyPoliceCivilianForm">
	<input type="hidden" value="${armyPoliceCivilian.id}" name="id"/>
	<c:if test="${armyPoliceCivilian.state eq '通过审核'}">
	<input type="submit" value="归档"></c:if>
	</form>
	</c:if>
<div id="info-page">
  <!-- 查看内容信息 -->
  <div id="history-info" class="tabContent">
	<fieldset>
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
				<td class="label">审核建议</td>
				<td class="content" colspan="3">${armyPoliceCivilian.suggest}</td>
			</tr>
			<tr>
				<td class="label">审核时间</td>
				<td class="content" colspan="3">${armyPoliceCivilian.approvingtime}</td>
			</tr>
			
		</table>
		</logic:notEmpty>
		<logic:empty name="armyPoliceCivilian" scope="request">没有记录</logic:empty>
		</fieldset>
		</div>
		
		
		<div id="content-info" class="tabContent">
		<fieldset>
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
					状态
				</td>

				<td class="content" >
					${armyPoliceCivilian.state}
				</td>
				<%----
				<td class="label">
					所属地市
				</td>

				<td class="content">
					<eoms:id2nameDB id="${armyPoliceCivilian.belongTheLocal}"
				beanId="tawSystemAreaDao" />
				</td>---%>


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
				<td class="label"> 
					填报时间
				</td>
				<td class="content">
					${armyPoliceCivilian.writeTime}
				</td>
			</tr>
			<tr>
				<td class="label">
					合作内容
				</td>

				<td class="content" colspan="3" >
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
		</fieldset>
		</div>
		<fieldset>
		<logic:empty name="armyPoliceCivilian" scope="request">没有记录</logic:empty>
	</fieldset>
	</div>
	

<%@ include file="/common/footer_eoms.jsp"%>