<%@page contentType="text/html;charset=ISO8859_1"%>
<%@page import="com.boco.eoms.resmanage.query.*"%>
<%@page import="com.boco.eoms.resmanage.operator.*"%>
<%@page import="java.util.*"%>
<%
/**
*@ E-DIS (四川省)
*@ Copyright : (c) 2003
*@ Company : BOCO.
*@ 资料查询模块
*@ author LiuYang
*@ version 1.0
*@ date    2003-05-09
**/
%>
<%
resClass cls = new resClass();
Vector resV = new Vector();
queryRes qr = new queryRes();
resV = qr.getResClass();
%>
<script language=javascript>
function goSearch()
{
	resclass.action = 'typeSelect.jsp';
	resclass.submit();
}
function goStat()
{
	resclass.action = 'statSelect.jsp';
	resclass.submit();
}
</script>
<html>
<head>
<title>资源统计列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO8859_1">

</head>
<body bgcolor="#eeeeee" text="#000000" class="content14">
<br>
<font size=2>请选择一个您要查询或统计的类别：</font>
<br>
<table width='100%' bgcolor=#dddddd>
<tr><td align=center><font color=#000000 size=2><b>类别列表</b></font></td></tr>
<tr><td>
<table width='100%' bgcolor=#eeeeee>
<!--<form name=resclass method=post action='conditionSelect.jsp'>-->
<form name=resclass method=post action='typeSelect.jsp'>
<tr>
<td align=center>请选择类型：</td><td align=center><select name=class onchange='ShowSelect(this.value)'>
<%
for(int i = 0; i < resV.size(); i ++)
{
	cls = (resClass)resV.get(i);
	out.println("<option value="+cls.getId()+">"+cls.getName()+"</option>");
}
%>
</select>
</td>
</tr>
</table>
<%
for(int i = 0; i < resV.size(); i ++)
{
	cls=(resClass)resV.get(i);
	String classid = cls.getId()+"";
	out.println("<table id="+mtools.removeString(cls.getName())+" style='display:none'><tr><td align=center><select name=type>");
	Vector devT = new Vector();
	queryRes qre = new queryRes();
	devT = qre.getSubResClass(classid);
	resClass rc = new resClass();
	for(int a = 0; a < devT.size(); a ++)
	{
		rc = (resClass)devT.get(a);
		out.println("<option value="+rc.getId()+">"+rc.getName()+"</option>");
	}
	out.println("</select></td></tr></table>");
}
%>
</form>
</table>
</td></tr>
</table>
<br><br><br>
<table bgcolor=#eeeeee width='100%'>
<tr><td align=center>
<a href='javascript:goSearch()'><img src='../images/button_query.gif' border=0></a></td>
<td align=center><a href='javascript:goStat()'><img src='../images/button_stat.gif' border=0></a></td>
</tr>
</table>
</body>
</html>