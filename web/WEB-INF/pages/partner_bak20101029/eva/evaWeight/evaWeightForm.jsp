<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<html:form action="/evaWeight.do?method=saveWeight" styleId="pnrEvaWeightForm" method="post" onsubmit="return validateForm();"> 
<table class="formTable" id="form" style="width:88%" align="center">
	<caption>
		<div class="header center">编辑地市个性权重</div>
	</caption>
	<tr>
		<td class="label">
			地域名字
		</td>
		<td class="content">${areaName}</td>
	</tr>
	<tr>
		<td class="label">
			个性权重
		</td>
		<td class="content">
			<html:text property="weight" styleId="weight" style="width:50%" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请个性权重'" value="${pnrEvaWeightForm.weight}" />
		<br><font color="red">*可分配权重范围：${minWt}~${maxWt}</font>
		<br><font color="red">*制定者设定原始权重：${oldWeight}</font>
		</td>
	</tr>
</table>
<table style="width:88%" align="center">
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</td>
	</tr>
</table>
<html:hidden property="id" value="${pnrEvaWeightForm.id}" />
<html:hidden property="area" value="${pnrEvaWeightForm.area}" />
<html:hidden property="nodeId" value="${pnrEvaWeightForm.nodeId}" />
</html:form>
<script type="text/javascript" />
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
	}
	document.getElementById('weight').focus() ;
</script>
<%@ include file="/common/footer_eoms.jsp"%>
