<%@ page language="java" pageEncoding="GB2312"%>
<%
String webapp = request.getContextPath();
%>
<html>
	<head>
		<title>合同过期提醒</title>
	</head>
	<body>
		<center>
			<table border="0" width="100%" height="100%">
				<tr height="50%">
					<td>
							<iframe name="serviceContractOverDueFrm"
								src="<%=webapp%>/contract/listOverdue.do?contractTypeId=1"
								style="HEIGHT: 100%;WIDTH: 100%; Z-INDEX: 0" frameborder="0"
								scrolling="auto"></iframe>
					</td>
				</tr>
				<tr height="50%">
					<td>
							<iframe name="serviceContractOverDueFrm"
								src="<%=webapp%>/contract/listOverdue.do?contractTypeId=2"
								style="HEIGHT: 100%;WIDTH: 100%; Z-INDEX: 0" frameborder="0"
								scrolling="auto"></iframe>
					</td>
				</tr>
			</table>





		</center>
	</body>
</html>
