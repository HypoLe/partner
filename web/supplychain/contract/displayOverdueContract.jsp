<%@ page language="java" pageEncoding="GB2312"
	import="com.boco.supplychain.util.StaticMethod"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<html>
	<%
	String webapp = request.getContextPath();
	%>
	<head>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/screen.css" type="text/css"
			media="screen, print" />
					<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/table_style.css"
			type="text/css">
		<style type="text/css">
			table	{ font-size: 12px;
			margin-top: 0em;
			margin-left: 0em;
			margin-right: 0em;
			margin-bottom: 2em;
			}
		</style>
		<title>��ͬ��������</title>
	</head>	
	<body>
			<center>
			<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
						<b><bean:write name="contractTypeName"/>��ͬ��������<bean:message key="label.list" /> </b>
				</P>
			<display:table name="sessionScope.contractFormCollection" id="row"
				cellspacing="1" cellpadding="1">
				<logic:equal value="1" name="contractTypeId">
					<display:column property="specialtyName" title="רҵ"
						style="width:60px" />
				</logic:equal>
				<logic:equal value="2" name="contractTypeId">
				<display:column property="workAroundName" title="������"
					style="width:60px" />
					</logic:equal>

				<display:column property="contractNo" title="��ͬ��"
					style="width:60px" />
				<display:column property="contractName" title="��ͬ����"
					style="width:60px" />
				<display:column property="strEndDate" title="��������(��)"
					style="width:100px" />
						<logic:equal value="1" name="contractTypeId">
				<display:column property="companyName" title="��ͬ�Է�(��ά��˾)"
					style="width:60px" />
					</logic:equal>
					<logic:equal value="2" name="contractTypeId">
						<display:column property="partB" title="��ͬ�Է�"
				style="width:60px" />
					</logic:equal>
				<display:column property="partB" title="��ͬ����"
					style="width:60px" />
				<display:column property="strSubscribeDate" title="��Ч����"
					style="width:100px" />
				<display:column title="����" style="width:100px">
					<a
						href="<%=webapp%>/contract/controller.do?contractId=<bean:write name='row' property='contractId'/>&controller_flag=<bean:write name='row' property='contractType'/>&view_type=viewView" />
						<img src="<%=webapp%>/images/bottom/an_xs.gif" border="0"
							alt="<bean:message key="label.view"/>"> </a>
					<a
						href="<%=webapp%>/contract/controller.do?contractId=<bean:write name='row' property='contractId'/>&controller_flag=<bean:write name='row' property='contractType'/>&view_type=modifyView" />
						<img src="<%=webapp%>/images/bottom/an_bj.gif" border="0"
							alt="<bean:message key="label.edit"/>"> </a>
					<a
						href="<%=webapp%>/contract/controller.do?contractId=<bean:write name='row' property='contractId'/>&controller_flag=<bean:write name='row' property='contractType'/>&view_type=deleteView" />
						<img src="<%=webapp%>/images/bottom/an_sc.gif" border="0"
							alt="<bean:message key="label.remove"/>"> </a>
				</display:column>
			</display:table>
			<TABLE width="98%" cellpadding="1" cellspacing="1" border="0"
				class="table_show" align="center">
				<TR class="tr_show">
					<td class="td_right" width="100%">
						<bean:write name="pagerHeader" scope="session" filter="false" />
					</td>
				</TR>
			</TABLE>
	</center>
	</body>
</html>
