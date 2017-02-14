<jsp:directive.page import="com.boco.eoms.operuser.util.OperuserConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<fmt:bundle basename="com/boco/eoms/operuser/config/applicationResource-operuser">
<!--  <script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
-->
<script type="text/javascript">

function validate() { 
	var frm = document.forms[0];
	if(frm.name.value ==''){
		alert("请输入用户名称");
			return false;
	}else{
		return true;
	}
	
	}	
</script>

	<html:form action="/operusers.do?method=query" method="post" styleId="theform" onsubmit="return validate();">
		<table style="width:85%;">
			<tr height="50">
				    <td>
				        <center><fmt:message key="operuser.name" /></center>
				    </td>
				    <td colspan="3">
				      	<html:text property="name" styleId="name"
							styleClass="text medium"
							alt="allowBlank:false,vtext:''" />	        
				    </td>
				    <td>
				        <center><fmt:message key="operuser.deptname" /></center>
				    </td>
				    <td colspan="3">
				      	<html:text property="deptname" styleId="deptname"
							styleClass="text medium"/>	        
				    </td>			    
			</tr>		
		
		
				<tr align="right" height="40">
					<td colspan="4">
					<html:submit><fmt:message key="operuser.submit" /></html:submit>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					
				</tr>
			
		</table>
	</html:form>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>