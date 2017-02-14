<%@page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'partnerFeeUnitForm'});
});
</script>

<html:form action="/partnerFeeUnits.do?method=save" styleId="partnerFeeUnitForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">
<font color='red'>*</font>号为必填内容<br>	<br>
<table class="formTable">
	<caption>
		<div class="header center">单位管理</div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="partnerFeeUnit.name" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="name" styleId="name"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  value="${partnerFeeUnitForm.name}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerFeeUnit.remark" />
		</td>
		<td class="content">
			<textarea name="remark" id="remark" 
			          cols="50" class="textarea max" 
			          alt="allowBlank:true,vtext:'',maxLength:500" >${partnerFeeUnitForm.remark}</textarea>	
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty partnerFeeUnitForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('您确认删除吗?')){
						var url=eoms.appPath+'/fee/partnerFeeUnits.do?method=remove&id=${partnerFeeUnitForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${partnerFeeUnitForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
