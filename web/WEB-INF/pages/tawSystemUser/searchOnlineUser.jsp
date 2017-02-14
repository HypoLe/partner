<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%>
<html:html>
<head>
	<title>search</title>
</head>
<body>
	<center>
		<table cellSpacing="0" cellPadding="0" width="85%" border="0">
			<tr>
				<td class="table_title" align="center">
					<b>查询日志</b>
				</TD>
			</tr>
		</table>
		<html:form action="/searchOnlineUsersResult.do?method=searchOnlineUser" method="post" styleId="tawSystemUserForm">
			<table border="0" width="500" cellspacing="1" cellpadding="1" class="formTable" align=center>
				<tr class="tr_show">
					<td class="label">
						用户名
					</td>
					<td >
        				<html:text property="username" styleId="username" styleClass="text medium"/>
					</td>
				</tr>
				<tr class="tr_show">
					<td class="label">
						帐号
					</td>
					<td >
        				<html:text property="userid" styleId="userid" styleClass="text medium"/>
					</td>
				</tr>
				<tr class="tr_show">
					<td class="label">
						开始时间
					</td>
                   <td width="70%" height="25" colspan="2">
                     <html:text styleClass="clstext" property="loginBeginTime" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="readonly" size="21" value="<%=StaticMethod.getLocalString()%>"/>
                  </td>
				</tr>
				<tr class="tr_show">
					<td class="label">
						结束时间
					</td>
					<td >
                     <html:text styleClass="clstext" property="loginEndTime" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="readonly" size="21" value="<%=StaticMethod.getLocalString()%>"/>
					</td>
				</tr>
			</table>
			<BR>

			<TABLE cellSpacing="0" cellPadding="0" width="95%" border="0" align=center>
				<TR>
					<TD align="right">
						<html:submit styleClass="button">
							<fmt:message key="button.query"/>
						</html:submit>
						&nbsp;
					</TD>
				</TR>
			</TABLE>
		</html:form>
	</center>
</body>
</html:html>

<%@ include file="/common/footer_eoms.jsp"%>