<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/xtreelibs.jsp"%>
<script type="text/javascript">
Ext.onReady(function(){
	var	userTreeAction='${app}/xtree.do?method=dept';
			new xbox({
				btnId:'deptName',dlgId:'dlg-audit',dlgTitle:'请选择部门',
				treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'所有部门',treeChkMode:'single',treeChkType:'dept',
				showChkFldId:'deptName',saveChkFldId:'deptid'
			}); 
})
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawRmEmergencyForm'});
});
 
</script>
<table>
	<caption>
		<bean:message key="tawRmEmergency.addEvent" />
	</caption>
</table>

<html:form action="/tawRmEmergency.do?method=serachList" method="post" styleId="tawRmEmergencyForm">

	<table border=0 cellspacing="1" cellpadding="1" class="listTable">
		<!--éå è¡¨ä»¥åXMLæä»¶åºæ¬å±æ§è¡¨æ ¼ï¼å¼å§-->
		<tr>
			<td COLSPAN="4" class="label">
				<bean:message key="tawRmEmergency.deptid" />
			</td>
			<td COLSPAN="10">
				<input type="hidden" name="deptid" value="${tawRmEmergencyForm.deptid}">
				<html:text property="deptName" styleId="deptName" styleClass="text medium" readonly="true" />

			</td>
	 
			<td COLSPAN="4" class="label">
				<bean:message key="tawRmEmergency.specialty" />
			</td>
			<td COLSPAN="10">
			 <html:text property="specialty" styleId="specialty" styleClass="text medium" />
				 
			</td>
			
		</tr>
		<tr>
			<td COLSPAN="4" class="label">
				<bean:message key="tawRmEmergency.immobility" />
			</td>
			<td COLSPAN="10">
				 <html:text property="immobility" styleId="immobility" styleClass="text medium" />
			</td>
			
		 
			<td COLSPAN="4" class="label">
				<bean:message key="tawRmEmergency.other" />
			</td>
			<td COLSPAN="10">
				<html:text property="other" styleId="other" styleClass="text medium" />
				</td>
		</tr>
		 


		<!--onClick="javascript:onSave(1);"-->
		<tr>
		<td COLSPAN="28">
			<input type="submit" value="<bean:message key="designer.title.btnSubmit" />" class="button">
		</td>
		</tr>

	</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
