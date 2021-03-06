<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
String jsonString = request.getAttribute("jsonString").toString();
%>
<!--根据给定的实例名生成标题 -->
<title><fmt:message key="smsMobliesTemplateDetail.title"/></title>
<!-- <content tag="heading"><fmt:message key="smsMobliesTemplateDetail.heading"/></content> -->
<script type="text/javascript">
<!--
function getUserPhone(data){
  var phone = [];
  eoms.log(data);
  Ext.each(data,function(user){
    phone.push(user.mobile);
  });
  Ext.getDom('mobiles').value = phone.join(',');
  var name = [];
  eoms.log(data);
  Ext.each(data,function(user){
    name.push(user.id);
  });
  Ext.getDom('users').value = name.join(',');
}
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'smsMobilesTemplateForm'});
	var	userTreeAction='${app}/xtree.do?method=userFromDept';
	userViewer = new Ext.JsonView("user-list",	
		'<div id="user-{id}" class="viewlistitem-user">{name}</div>',
		{ 
			multiSelect: true,		
			emptyText : '<div><bean:message key="smsService.choosepeople"/></div>'								
		}
	);
	var s = '<%=jsonString%>';
	userViewer.jsonData = eoms.JSONDecode(s);
	userViewer.refresh();
	
	userTree = new xbox({
		btnId:'userTreeBtn',dlgId:'dlg-user',	
		treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'<bean:message key="smsService.deptuser"/>',treeChkMode:'',treeChkType:'user',
		viewer:userViewer,saveChkFldId:'users',returnJSON:true,callback:getUserPhone
	});
	
})

//-->
</script>

<html:form action="/saveSmsMobilesTemplate.do?method=xsaveTeam&deleted=2" method="post" styleId="smsMobilesTemplateForm"> 
	<table class="formTable">
		<tr>
			<td class="label">
				<bean:message key='smsMobilesTemplate.name'/>
			</td>
			<td class="content max">
				<html:text property="name" ></html:text>
			</td>
		</tr>
		<tr>
			<td class="label">
				<bean:message key='smsMobilesTemplate.remark'/>
			</td>
			<td class="content max">
				<html:text property="remark" ></html:text>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="left">
				<html:submit property="save"><bean:message key="button.save"/></html:submit>	
			</td>
		</tr>		
	</table>
	<html:hidden property="id"/>
	<html:hidden property="deleted"/>
	<input type="hidden" id="users" name="users"/> 
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>