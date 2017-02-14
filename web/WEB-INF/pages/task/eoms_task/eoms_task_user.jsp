<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<title></title>
<content tag="heading">
<%String auditUser = request.getAttribute("auditUser").toString(); %>
<eoms:id2nameDB id="${threadForm.forumsId }" beanId="forumsDao" />
</content>
<fmt:bundle basename="com/boco/eoms/task/config/applicationResource-task">
<script type="text/javascript">
	Ext.onReady(function(){
		auditUserViewer = new Ext.JsonView("view",
			'<div id="role-user-{id}" class="viewlistitem-user">{name}</div>',
			{ 
				emptyText : '<div><fmt:message key='form.nonemes'/></div>'
			}
		);
		var auditUser='<%=auditUser %>';
		auditUserViewer.jsonData = eoms.JSONDecode(auditUser);
		auditUserViewer.refresh();
		
		
		var	treeAction='${app}/xtree.do?method=userFromDept';
		auditTree = new xbox({
			btnId:'clkAudit',dlgId:'hello-audit',
			treeDataUrl:treeAction,treeRootId:'-1',treeRootText:'选择任务执行人',treeChkMode:'multiple',treeChkType:'user',
			showChkFldId:'showAuditUser',saveChkFldId:'showAuditUser',viewer:auditUserViewer,returnJSON:true
		});
	});
	




</script>








<html:form action="/taskuser.do?method=save" method="post" styleId="eomstaskForm">

   <input type="hidden"  id="userid" name="userid"  value="${usermes.id}"/>
   <input type="hidden"  id="usid" name="usid"  value="${usermes.userid}"/>
   <input type="hidden"  id="username" name="username"  value="${usermes.username}"/>
	
	<div class="list-title">

选择任务执行人
</div>
	<ul>
		
		<li>
			<input type="hidden" readonly id="showAuditUser" name="showAuditUser" />
					<input type="button" id="clkAudit" name="clkAudit" value="选择执行人" class="btn" />
			<div id="view" class="viewer-list"></div>
		</li>
		
		<li>
         <input type="submit" value="保存执行人" class="btn" />
       </li>
     

</div>  
      
       
     
	</ul>
</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
