<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'partnerUserAndAreaForm'});
});
Ext.onReady(function(){
	var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';
			new xbox({
				btnId:'name',dlgId:'dlg-audit',dlgTitle:'请选择人员',
				treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'所有人员',treeChkMode:'single',treeChkType:'user',
				showChkFldId:'name',saveChkFldId:'userId'
			}); 
			
			
	new xbox({
		btnId:'areatree',
		treeDataUrl:'${app}/xtree.do?method=area',treeRootId:'${province}',treeRootText:'全省',treeChkMode:'',treeChkType:'area',
		showChkFldId:'areaNames',saveChkFldId:'areaId',returnJSON:false
	});
			
})
function openSelectAreas(){
    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
}
</script>

<html:form action="/partnerUserAndAreas.do?method=save" styleId="partnerUserAndAreaForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable middle">
	<caption>
		<div class="header center"><fmt:message key="partnerUserAndArea.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="partnerUserAndArea.name" />
		</td>
		<td class="content">
			<html:text property="name" styleId="name"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${partnerUserAndAreaForm.name}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerUserAndArea.userId" />
		</td>
		<td class="content">
			<html:text property="userId" styleId="userId"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${partnerUserAndAreaForm.userId}" /><font style="color: red;">${fallure }</font>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerUserAndArea.areaNames" />
		</td>
		<td class="content">

			<input type="button" name="areatree" id="areatree" value="选择地域" class="btn"/><br/>

			<html:textarea property="areaNames" styleId="areaNames"
						styleClass="textarea" 
						readonly="true" 
					    value="${partnerUserAndAreaForm.areaNames}" />

	<!-- 	<input type="hidden" name="areaId" id="areaId"/> -->		
			<html:hidden property="areaId" styleId="areaId" />	
			
			<html:hidden property="areaType" styleId="areaType" />


<!-- 		<html:text property="areaNames" styleId="areaNames" onclick="openSelectAreas();"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${partnerUserAndAreaForm.areaNames}" />
			<html:hidden property="areaType" styleId="areaType" />
-->		
		
		</td>
		
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty partnerUserAndAreaForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确定删除吗?')){
						var url=eoms.appPath+'/partner/baseinfo/partnerUserAndAreas.do?method=remove&id=${partnerUserAndAreaForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${partnerUserAndAreaForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>