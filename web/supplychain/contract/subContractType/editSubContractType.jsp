<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="com.boco.supplychain.util.StaticVariable"%>

<%
String webapp = request.getContextPath();
%>

	<head>
		<title>${eoms:a2u('更改合同项类别')}</title>
		<link rel="stylesheet"
			href="<%=webapp%>/css/table_style.css"
			type="text/css">
		<script type="text/javascript" src="<%=webapp%>/js/form/checkform.js"></script>
		<script type="text/javascript">
			function checkForm(){
				var obj = document.forms[0].subContractTypeName;
				return checkLength(obj,1,10);
			}
		</script>
	</head>

	<!---------------------------------------页面主体开始--------------------------------------->


		<!------------------标识页面位置------------------>
		<p align="left">
			${eoms:a2u('所在位置－>合同项类别管理->更改合同项类别
		</p>
	<center>
		<!---------------------------------------专业新增定制--------------------------------------->
		<html:form method="post" action="/contract/subContractType.do?method=update" onsubmit="return checkForm();">
			<table width="40%" height="10%" border="0" class="table_show"
				cellspacing="1" cellpadding="1">
				<tr height="50%" class="tr_show">
					<td width="50%" align="center">
						${eoms:a2u('合同项类别名称')}
					</td>
					<td width="50%" align="center">
					    <html:hidden property="subContractTypeId"/>
						<html:text property="subContractTypeName" styleClass="clstext" maxlength="10" title="${eoms:a2u('合同项类别名称')}" />
					</td>
				</tr>
				<!------------------维护合同模版其它定制选项------------------>
				<tr height="50%" class="tr_show" align="right">
					<td colspan="2">
						<html:submit styleClass="clsbtn2">
							<bean:message key="label.save" />
						</html:submit>
						<html:reset styleClass="clsbtn2">
							<bean:message key="label.reset" />
						</html:reset>
					</td>
				</tr>
			</table>
		</html:form>
	</center>
<%@ include file="/common/footer_eoms_innerpage.jsp"%>
