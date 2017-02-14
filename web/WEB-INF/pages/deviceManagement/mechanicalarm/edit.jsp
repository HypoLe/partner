<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<div align="center">大型机械手管理编辑页面</div>
<form action="mechanicalArmManagement.do?method=edit&id=${MechanicalArmList.id}"
	method="post" id="mechaicalArmManagement"
	name="mechaicalArmManagement">
		<fieldset>
	
	<table id="sheet" class="formTable">
		
		<tr>
			<td class="label">
				审核人*
			</td>
			<td class="content" >
				<input class="text" type="text" name="approvingPerson" id="approvingPerson"
					value="<eoms:id2nameDB id="${MechanicalArmList.approvingPerson}"
				beanId="tawSystemUserDao" />"alt="allowBlank:false" />
			</td>
			<td class="label">
				项目名称*
			</td>
		
			<td class="content" >
			<input class="text" type="text" name="projectName" id="projectName"
				value="${MechanicalArmList.projectName}" alt="allowBlank:false" />	
			</td>
			
		</tr>
		<tr>
			<td class="label">
				大型施工机械名称*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="constructionMachineryName" id="constructionMachineryName"
					value="${MechanicalArmList.constructionMachineryName}" alt="allowBlank:false" />	
			</td>
			
			<td class="label">
				大型施工机械施工地点*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="mechanicalArm_workyard" id="mechanicalArm_workyard"
					value="${MechanicalArmList.mechanicalArm_workyard}" alt="allowBlank:false" />	
			</td>
			</tr>
			

		<tr>
			<td class="label">
				大型施工机械手姓名*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="mechanicalArmName" id="mechanicalArmName"
					value="${MechanicalArmList.mechanicalArmName}" alt="allowBlank:false" />	
			</td>
			<td class="label">
				联系电话*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="contactNumber" id="contactNumber"
					value="${MechanicalArmList.contactNumber}"alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">
				走访情况（每日/每周）*
				<br>
			</td>
			
	
			<td class="content" colspan="3">
				<textarea class="textarea max" name="visitSituation"
					id="visitSituation" alt="allowBlank:false">${MechanicalArmList.visitSituation}</textarea>
			</td>
		</tr>
		<tr>
			<td class="label">
				备注
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="remarks"
				id="remarks" >${MechanicalArmList.remarks}</textarea>
				<br>
			</td>
		</tr>
	</table>
	</fieldset>
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="保存"></html:submit>
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</form>
<%@ include file="/common/footer_eoms.jsp"%>