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
<script type="text/javascript">
var myjs = jQuery.noConflict();
Ext.onReady(function(){
            v = new eoms.form.Validation({form:'mechaicalArmManagement'});
            v.custom = function(){ 
            	return true;
            };
            })
</script>
</script>
<div align="center">军警民共建管理编辑页面</div>
<form action="armypolicecivilian.do?method=edit&id=${armyPoliceCivilian.id}"
	method="post" id="mechaicalArmManagement"
	name="mechaicalArmManagement">
		<fieldset>
	
	<table id="sheet" class="formTable">
		
		<tr>
		<td class="label">
				项目名称*
			</td>
		
			<td class="content" >
			<input class="text" type="text" name="projectName" id="projectName"
				value="${armyPoliceCivilian.projectName}" alt="allowBlank:false" />	
			</td>
			<td class="label">
				审核人*
			</td>
			<td class="content" >
				<input type="text" class="text" name="approvingPerson1"
						id="approvingPerson1"
						value="	<eoms:id2nameDB id="${armyPoliceCivilian.approvingPerson}"
				beanId="tawSystemUserDao"/>" alt="allowBlank:false" readonly="readonly"/>
		   		<input type="hidden" name="approvingPerson2" id="approvingPerson2"  value="${armyPoliceCivilian.approvingPerson}"/>
				<eoms:xbox id="approvingPerson" dataUrl="${app}/xtree.do?method=userFromDept"  
				rootId="2" rootText="用户树"  valueField="approvingPerson2" handler="approvingPerson1" 
				textField="approvingPerson1" checktype="user" single="true" />
			</td>
			
			
		</tr>
		<tr>
				<td class="label">
					军警民共建单位名称*
					<br>
				</td>
				<td class="content">
					<input class="text" type="text" name="nameOfOrganization"
						value="${armyPoliceCivilian.nameOfOrganization}"id="nameOfOrganization" alt="allowBlank:false" />
				</td>

				<td class="label">
					合作内容*
					<br>
				</td>
				<td class="content">
					<input class="text" type="text" name="cooperativeContent"
					value="${armyPoliceCivilian.cooperativeContent}"id="cooperativeContent" alt="allowBlank:false" />
				</td>
			</tr>
			<tr>
				<td class="label">
					负责光缆线路里程（km）*
					<br>
				</td>
				<td class="content">
					<input class="text" type="text" name="lengthOfOpticalCableRoutes"
					 value="${armyPoliceCivilian.lengthOfOpticalCableRoutes}"	id="lengthOfOpticalCableRoutes" alt="allowBlank:false" />
				</td>
				<td class="label">
					实际效果*
				</td>
				<td class="content">
					<input class="text" type="text" name="realEffect"
						value="${armyPoliceCivilian.realEffect}"id="realEffect" alt="allowBlank:false" />
					
				</td>
			</tr>
			<tr>
				<td class="label">
					备注
				</td>
				<td colspan="3">
					<textarea class="textarea max" name="remarks" id="remarks">${armyPoliceCivilian.remarks}</textarea>
					<br>
				</td>
			</tr>
		</table>
	</fieldset>
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="提交"></html:submit>
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</form>
<%@ include file="/common/footer_eoms.jsp"%>