<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'partnerUserAndDeptForm'});
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
		treeDataUrl:'${app}/xtree.do?method=userFromDept',treeRootId:'-1',treeRootText:'部门',treeChkType:'dept',
		showChkFldId:'deptNames',saveChkFldId:'deptId',returnJSON:false
	});
			
})
</script>

<html:form action="/partnerUserAndDepts.do?method=save" styleId="partnerUserAndDeptForm" method="post"> 


<table class="formTable middle">
	<caption>
		<div class="header center">人员所属部门</div>
	</caption>

	<tr>
		<td class="label">
		人员姓名
		</td>
		<td class="content">
			<html:text property="name" styleId="name"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${partnerUserAndDeptForm.name}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			用户ID
		</td>
		<td class="content">
			<html:text property="userId" styleId="userId"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${partnerUserAndDeptForm.userId}" /><font style="color: red;">${fallure}</font>
		</td>
	</tr>

	<tr>
		<td class="label">
			部门
		</td>
		<td class="content">

			<input type="button" name="areatree" id="areatree" value="选择部门" class="btn"/><br/>

			<html:textarea property="deptNames" styleId="deptNames"
						styleClass="textarea" 
						readonly="true" 
					    value="${partnerUserAndDeptForm.deptNames}" />

			<html:hidden property="deptId" styleId="deptId" />	
			
		</td>
		
	</tr>

</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="保存" />
			<c:if test="${not empty partnerUserAndDeptForm.id}">
				<input type="button" class="btn" value="删除" 
					onclick="javascript:if(confirm('确定删除吗?')){
						var url=eoms.appPath+'/partner/baseinfo/partnerUserAndDepts.do?method=remove&id=${partnerUserAndDeptForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${partnerUserAndDeptForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>