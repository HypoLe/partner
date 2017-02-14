<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="com.boco.supplychain.util.StaticVariable"%>

<%
String webapp = request.getContextPath();
%>
<script type="text/javascript">
			function checkForms(){
				var obj = document.forms[0].subContractTypeName;
				 
				return checkLength(obj,1,10);
			}
			function checkLength(item, nMin, nMax)	// confirm not null
			{
				var str = item.value.Trim();
				if ( 0!=nMin && 0==str.length)
				{
					alert ("请输入您的 [" + item.title + "] ");
					item.focus();
					return false;
				}
				else if (nMin>str.length || nMax<str.length)
				{	
					alert ("[" + item.title + "] 的长度必须在 " + nMin + " ~ " + nMax + " 之间！");
					item.focus();
					return false;
				}
				else
					return true;
			}
		</script>

<p align="left">
	${eoms:a2u('所在位置－>合同项类别管理->新增合同项类别')}
</p>
<center>
	<!---------------------------------------专业新增定制--------------------------------------->
	<html:form method="post"
		action="/contract/subContractType.do?method=add"
		onsubmit="return checkForms();">
		<table width="40%" height="10%" border="0" class="table_show"
			cellspacing="1" cellpadding="1">
			<tr height="50%" class="tr_show">
				<td width="50%" align="center">
					${eoms:a2u('合同项类别名称')}
				</td>
				<td align="center">
					<html:text property="subContractTypeName" styleClass="clstext"
						maxlength="10" title="${eoms:a2u('合同项类别名称')}" />
				</td>
			</tr>
			<!------------------维护合同模版其它定制选项------------------>
			<tr height="50%" clas s="tr_show" align="right">
				<td colspan="2">
					<html:submit styleClass="clsbtn2">
							${eoms:a2u('保存')}
						</html:submit>
					<html:reset styleClass="clsbtn2">
							${eoms:a2u('重置')}
						</html:reset>
				</td>
			</tr>
		</table>
	</html:form>
</center>
<%@ include file="/common/footer_eoms_innerpage.jsp"%>

