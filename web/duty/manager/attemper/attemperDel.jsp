<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'attemperForm'});
}); 

function validateForm() {
	var frm = document.forms["attemperForm"];
	
	if (document.getElementById("delReason").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemper.detail.title" />'
		,'<fmt:message key="attemper.delReason" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("delReason").focus();
 		return false;
 	}
 	if(confirm('<fmt:message key="faultCommont.is.delete"/>')) {
 	 	frm.submit();
 	}
 }
</script>

<html:form action="/attempers.do?method=remove" styleId="attemperForm" method="post"> 
<table class="formTable">		
	<tr>
		<td class="label">
			<fmt:message key="attemper.delReason" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="delReason" styleId="delReason" style="width:13.5cm;"
						styleClass="text medium" rows="5"
						alt="allowBlank:false,vtext:''" value="${attemperForm.delReason}" />
		</td>
	</tr>
</table>
<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.delete"/>" onclick="validateForm();" />
		</td>
	</tr>
</table>
<html:hidden property="id" value="${attemperForm.id}" />
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>