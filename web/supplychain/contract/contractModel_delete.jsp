<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>   
<%@page import="com.boco.supplychain.util.StaticMethod" %>
 
 <%
		String webapp = request.getContextPath();
		String modelId=StaticMethod.null2String("modelId");

 %>
 <table border="0" width="100%" cellspacing="0" align="center">
				<tr>
					<td width="100%" class="table_title" align="center">
						<b>  ${eoms:a2u('删除合同模板')}</b>
					</td>
				</tr>
			</table>
			<table class="table_show" width="100%" cellspacing="1"
				cellpadding="1" border="0" align="center">
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						 ${eoms:a2u('专业')}
					</td>
					<td width="35%" height="25">
						<bean:write name="contractModelForm" property="specialtyName" />
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						 ${eoms:a2u('合同类型')}
					</td>
					<td width="35%" height="25">
						<bean:write name="contractModelForm" property="contractTypeName" />
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						 ${eoms:a2u('合同模板名称')}
					</td>
					<td width="35%" height="25">
						<bean:write name="contractModelForm" property="modelName" />
					</td>
				</tr>
				<tr class="tr_show">
					<td width="15%" height="25" class="clsfth">
						 ${eoms:a2u('合同扩展字段')}
					</td>
					<td width="35%" height="25">
						<bean:write name="contractModelForm" property="extend_fields_name" />
					</td>
				</tr>
			</table>
			<table border="0" width="100%" cellspacing="0" cellpadding="0"
				align="center">
				<tr>
					<td width="100%" height="32" align="right">
						<html:submit styleClass="clsbtn2" onclick="return confirmDel();">
							  ${eoms:a2u('删除')}
						</html:submit>
						&nbsp;
						<input type="button" class="clsbtn2" onclick="historyBack();"
							value=" ${eoms:a2u('返回')}" />
					</td>
				</tr>
			</table>
	 

	<script type="text/javascript">
<!--
function confirmDel(){
  if( confirm(" ${eoms:a2u('合同模板删除后不能恢复，您确认要删除吗？')}") ){
  	window.location='<%=webapp%>/contractModel/delete.do?modelId=<bean:write name="contractModelForm" property="modelId" />';
    return true;
  }
  else{
    return false;
  }
}

function historyBack(){
  window.location='<%=webapp%>/contractModel/list.do';
}
//-->
</script>
 <%@ include file="/common/footer_eoms_innerpage.jsp"%>
