<%@page contentType="text/html;charset=ISO8859_1"%>
<%@page import="com.boco.eoms.resmanage.entity.*"%>
<%@page import="mcs.common.db.*"%>
<%@page import="java.util.*"%>
<%
/**
*@ E-DIS (四川省)
*@ Copyright : (c) 2003
*@ Company : BOCO.
*@ 记录信息维护
*@ author wuzongxian
*@ version 1.0
*@ date    2003-05-09
**/
%>
<%
    
	request.setCharacterEncoding("GBK");

    String pi_id = null;
	if(request.getParameter("pi_id") != null)
		pi_id = request.getParameter("pi_id");
	else
		pi_id = "0";
   //out.println("pi_id is: "+pi_id);
   int opttype = Integer.parseInt(request.getParameter("opttype"));
%>
<html>
<head>
<title>选择所属设备类型</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO8859_1">

</head>
<script language=javascript>
function submitForm()
{
	var opttype = <%=opttype%>;
	if (opttype == 1)
	{
		condition.action = "editLogInsert.jsp";
	}
	if (opttype==2)
	{
		condition.action = "editLogUpdate.jsp";
	}
	condition.submit();
}
function goBack()
{
	history.go(-1);
}
</script>
<body>
<font size=2>请您选择一个设备类型:</font>
<table bgcolor="#dddddd" width='100%'>
<tr><td align=center width='100%'><font size=2><B>所属设备类型选择</B></font></td></tr>
</table>
<table bgcolor="#dddddd" width='100%'>
<form name=condition method=post>
<%
String sId = null;
if(request.getParameter("id") != null)
	sId = request.getParameter("id");
else
	sId = "0";
Vector devT = new Vector();
LogOpt logopt = new LogOpt();

devT = logopt.getTabInforByType(sId);
systabindex TabIndex = new systabindex();
for(int i = 0; i < devT.size(); i ++)
{
	TabIndex = (systabindex)devT.get(i);
	if(i%4 == 0)
		out.println("<tr class=td_label>");
	out.println("<td align=center width='25%'><input type=radio name=type value="+TabIndex.getPi_id()+" onclick=javascript:submitForm()><font size=2 face='Verdana, Arial, Helvetica, sans-serif'>"+TabIndex.getCc_name()+"</font></td>");
	if((i+1)%4 == 0)
		out.println("</tr>");
}
if(devT.size() == 0)
	out.println("<tr class=td_label><td align=center><font size=2 color=red>暂无设备类型，请核实后重试</font></td></tr>");
%>
<input type=hidden name=id value=<%=sId%>>
<input type=hidden name=pi_id value=<%=pi_id%>>
</form>
</table>
<br><br>
<table width='100%'>
<tr><td align=center><font size=2><a href='javascript:goBack()'>返回</a></font></td></tr>
</table>
</body>
</html>


