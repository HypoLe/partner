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
var myjs = jQuery.noConflict();
Ext.onReady(function() {
	v = new eoms.form.Validation( {
		form : 'ArmyPoliceCivilianForm'
	});
	v.custom = function() {
		return true;
	};
})
myjs(function() {
	myjs('input#lengthOfOpticalCableRoutes').bind('change', preExp);
});
function preExp(event) {

	var phone = /^[0-9]{0,7}\.{0,1}[0-9]{0,1}[0-9]{0,1}$/;

	var contactNumber = myjs("input#lengthOfOpticalCableRoutes").val();
	var aa = myjs('#fuck');
	aa.removeClass('ui-state-error');
	if (contactNumber.match(phone)) {
		aa.addClass('ui-state-highlight ui-corner-all');
		aa.children("div").html("输入正确");
		aa.children("span").attr("class", "ui-icon ui-icon-info");
	} else {
		aa.addClass('ui-state-error ui-corner-all');
		aa.children("div").html("输入不合法,最多保留2位小数");
		aa.children("span").attr("class", "ui-icon ui-icon-alert");

	}

}
</script>
<div align="center">
	军警民共建添加页面
</div>
<form action="armypolicecivilian.do?method=add"
	id="ArmyPoliceCivilianForm" method="post" name="ArmyPoliceCivilianForm">
	<fieldset>

		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					项目名称*
				</td>

				<td class="content" colspan="3">
					<input class="text" type="text" name="projectName" id="projectName"
						alt="allowBlank:false" />
				</td>
			</tr>
			<tr>
				<td class="label">
					审核人*
				</td>

				<td class="content">
					<input type="text" class="text" name="approvingPerson1"
						id="approvingPerson1" value="" alt="allowBlank:false"
						readonly="readonly" />
					<input type="hidden" name="approvingPerson" id="approvingPerson"
						value="" />
					<eoms:xbox id="approvingPerson"
						dataUrl="${app}/xtree.do?method=userFromDept" rootId="2"
						rootText="用户树" valueField="approvingPerson"
						handler="approvingPerson1" textField="approvingPerson1"
						checktype="user" single="true" />

				</td>
				<td class="label">
					军警民共建单位名称*
					<br>
				</td>
				<td class="content">
					<input class="text" type="text" name="nameOfOrganization"
						id="nameOfOrganization" alt="allowBlank:false" />
				</td>
			</tr>
			<tr>



			</tr>
			<tr>
				<td class="label">
					负责光缆线路里程（km）*
					<br>
				</td>
				<td class="content">
					<input class="text" type="text" name="lengthOfOpticalCableRoutes"
						id="lengthOfOpticalCableRoutes"
						alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'" / />
					<div id="fuck" class="ui-state-highlight ui-corner-all"
						style="width: 150px">
						<span class="ui-icon ui-icon-circle-triangle-e"
							style="float: left; margin-right: .6em;"></span>
						<div>
							请输入正确的格式
						</div>
					</div>
				</td>
				<td class="label">
					实际效果*
				</td>
				<td class="content">
					<input class="text" type="text" name="realEffect" id="realEffect"
						alt="allowBlank:false" />

				</td>
			</tr>
			<tr>
				<td class="label">
					合作内容*
				</td>
				<td colspan="3">
					<textarea class="textarea max" name="cooperativeContent"
						id="cooperativeContent"></textarea>
					<br>
				</td>
			</tr>
			<tr>
				<td class="label">
					备注
				</td>
				<td colspan="3">
					<textarea class="textarea max" name="remarks" id="remarks"></textarea>
					<br>
				</td>
			</tr>
		</table>
	</fieldset>
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="提交审核"></html:submit>
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</form>
<%@ include file="/common/footer_eoms.jsp"%>