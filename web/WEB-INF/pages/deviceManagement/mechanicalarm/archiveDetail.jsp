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
<div align="center">大型机械手管理详情页面</div>
<form action="mechanicalArmManagement.do?method=overIt&id=${MechanicalArmList.id}" method="post"
	id="mechaicalArmManagement" name="mechaicalArmManagement">
	<input type="hidden" value="${MechanicalArmList.id}" name="id"></input>
	<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
<div id="info-page">
  <!-- 查看内容信息 -->
	<div id="history-info" class="tabContent">
	<fieldset>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					审核人
				</td>
				<td class="content">
				<eoms:id2nameDB id="${MechanicalArmList.approvingPerson}"
				beanId="tawSystemUserDao" />
				</td>
				<td class="label">审核结果</td>
				<td class="content">${MechanicalArmList.approvingresult}</td>
			</tr>
			<tr>
				<td class="label">审核意见</td>
				<td class="content" colspan="3">${MechanicalArmList.suggest}</td>
			</tr>
			<tr>
				<td class="label">审核时间</td>
				<td class="content">${MechanicalArmList.approvingtime}</td>
			</tr>
			
		</table>
		</fieldset>
		</div>
		
		<div id="content-info" class="tabContent">
		<fieldset>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					填报人
				</td>
				<td class="content">
					<eoms:id2nameDB id="${MechanicalArmList.writePerson}"
						beanId="tawSystemUserDao" />
				</td>
			<%-- 	<td class="label">
					所属地市
				</td>

				<td class="content">
					<eoms:id2nameDB id="${MechanicalArmList.belongTheLocal}"
				beanId="tawSystemAreaDao" />
				</td>--%>


			</tr>
			<tr>
				<td class="label">
					审核人
				</td>
				<td class="content">
				<eoms:id2nameDB id="${MechanicalArmList.approvingPerson}"
				beanId="tawSystemUserDao" />
				</td>


				<td class="label">
					项目名称
				</td>

				<td class="content">
					${MechanicalArmList.projectName}
				</td>

			</tr>
			<tr>
				<td class="label">
					大型施工机械名称

				</td>
				<td class="content">
					${MechanicalArmList.constructionMachineryName}
				</td>

				<td class="label">
					大型施工机械施工地点
				</td>
				<td class="content">
					${MechanicalArmList.mechanicalArm_workyard}
				</td>
			</tr>
			<tr>
				<td class="label">
					大型施工机械手姓名
				</td>
				<td class="content">
					${MechanicalArmList.mechanicalArmName}
				</td>
				<td class="label">
					联系电话
				</td>
				<td class="content">
					${MechanicalArmList.contactNumber}
				</td>
			</tr>
			<tr>
				<td class="label">
					走访情况
				</td>
				<td class="content" colspan="3">
					${MechanicalArmList.visitSituation}
				</td>
			</tr>
			
			<tr>
				<td class="label">
					备注
				</td>
				<td colspan="3" class="content">
					${MechanicalArmList.remarks}
				</td>
			</tr>
			<tr>
				<td class="label">
					填报时间
				</td>

				<td class="content" conlspan="3">
					${MechanicalArmList.writeTime}
				</td>
			</tr>
		</table>
	</fieldset>
	</div>
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="归档"></html:submit>

</form>

<%@ include file="/common/footer_eoms.jsp"%>