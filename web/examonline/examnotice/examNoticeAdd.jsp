<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'noticeForm'});
});
		
</script>

<form  action="examNotice.do?method=addExamNotice" id="noticeForm" method="post" > 

<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center">考试发布信息</div>
	</caption>

	<tr>
		<td class="label">
			标题&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<input name="title" class="text max" alt="allowBlank:false"/>
			<span id = "message"></span>			
		</td>
	</tr>

	<tr>
		<td  class="label">内容&nbsp;<font color='red'>*</font></td>
		<td class="content" colspan="2">
		<textarea property="remark" styleId="remark"  name="content" id="remark"
			class="text max" alt="allowBlank:false"></textarea>
		</td></tr>


</table>
<input type="submit" class="btn" value="保存"/>
</form>

<%@ include file="/common/footer_eoms.jsp"%>