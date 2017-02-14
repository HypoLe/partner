<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	});


function returnBack() {
	window.history.back();
}
</script>
<table id="stylesheet" class="formTable">
	<caption>
	<div class="header center">资源设置页面</div>
	</caption>
	<br />
	<br />
	<tr>
		<td class="label">模块名称*</td>
		<td class="content" colspan="3">${aggregationList.moduleName}</td>
	</tr>
	<tr>
		<td class="label">模块URL*</td>
		<td class="content" colspan="3">${aggregationList.moduleUrl}</td>
	</tr>
	<tr>
		<td class="label">模块介绍*</td>
		<td class="content" colspan="3">${aggregationList.content}</td>
	</tr>
    <tr>
	<td  valign="middle">图像</td>
	<td>
				 <img id="imgUser" name="imgUser" src="${aggregationList.photo}"  border="0" width="130" height="130">
		   
		</td>
	</tr>
	<tr>
		<td class="label">备注</td>
		<td class="remark" colspan="3">${aggregationList.remark}</td>
	</tr>

</table>
<br />
<table>
	<tr>
		<td><input type="button" style="margin-right: 5px"
			onclick="returnBack();" value="返回" class="btn" /></td>
	</tr>
</table>

<%@ include file="/common/footer_eoms.jsp"%>
