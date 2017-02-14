<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.boco.supplychain.util.StaticMethod,com.boco.supplychain.util.StaticVariable" %>
<%
String webapp=request.getContextPath();
 %>
<html>
	<head>
		<link rel="stylesheet"
			href="<%=webapp%>/css/screen.css" type="text/css"
			media="screen, print" />
		<link rel="stylesheet"
			href="<%=webapp%>/css/table_style.css"
			type="text/css">
		<style type="text/css">
		table	{ font-size: 12px;
		margin-top: 0em;
		margin-left: 0em;
		margin-right: 0em;
		margin-bottom: 2em;
		}
		</style>
	</head>
	<body>

		<CENTER>
			<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
				<b>合同维护清单
				</b>
			</P>
				<display:table name="requestScope.serviceDeviceVoList" id="row" cellspacing="1" cellpadding="1">	
					<display:column property="cityName" title="县市"
						style="width:200px" />
					<display:column property="deviceTypeName" title="设备类型"
						style="width:200px" />
					<display:column property="deviceAmount" title="设备数量"
						style="width:200px" />
					<display:column property="price" title="设备维护单价"
						style="width:200px" />	
				</display:table>
			<TABLE width="98%" cellpadding="1" cellspacing="1" border="0"
				class="table_show" align="center">
				<TR class="tr_show">
					<td class="td_right" width="100%">
						<button class="clsbtn2" onclick="history.back();">
							返回
						</button>
					</td>
				</TR>
			</TABLE>
		</CENTER>
	</body>
</html>
