<%@page contentType="text/html;charset=ISO8859_1"%>
<%@page import="java.util.*"%>
<%@page import="com.boco.eoms.resmanage.entity.*"%>
<%@page import="mcs.common.db.*"%>
<%@page import ="com.boco.eoms.resmanage.operator.*"%>
<%//@include file="../power.jsp"%>
<%
/**
*@ E-DIS (四川省)
*@ Copyright : (c) 2003
*@ Company : BOCO.
*@ 显示资源列表信息
*@ version 1.0
**/
%>
<%
 //if(!bflag)
//out.println("<script>alert('您已经掉线，请重新登陆！');parent.location='../index.jsp';</script>");
request.setCharacterEncoding("GBK");

int pageid;
if(request.getParameter("pageid") != null)
	pageid = Integer.parseInt(request.getParameter("pageid"));
else
	pageid=1;

String sId = "0";

//out.println("<script>alert('欢迎使用搜索引擎，您的搜索引擎号码："+opt.getIUserInt()+"');</script>");
%>
<html>
<head>
<title>显示资源信息列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO8859_1">

</head>
<body bgcolor="#eeeeee" text="#000000" class="listStyle">
<%
entityoperate Entity = new entityoperate();
systabindex SysTabIndex = new systabindex();

Vector TabVect = new Vector();
TabVect = Entity.getTabinfor(sId);

int TabNum = TabVect.size();

if(TabNum != 0)
{
	/******************	构造内容	*****************/
	//out.println("<br><table bgcolor=#dddddd width='100%'><tr>");
	%>
	<tr class=td_label>
	<td align=left width='50%'><font size=2>网络基础数据->资源管理列表</font></td>
	</tr>
	<br>
	<br>
	<font size=2>请选择一个您要管理的实体:</font>
	<br><br>
	<table bgcolor=#dddddd width='100%'><tr>
	<td align=center colspan="4"><font size=2 color=#000000><b>实体名称</b></font></td>
	</tr>
	<%
	int flag = 0;
	for(int Col = 0; Col < TabNum; Col ++)
	{
		if(Col%4 == 0)
			out.println("<tr class=td_label>");

		//out.println("<td align=center><font size=2>"+ Col + "</font></td>");
		SysTabIndex = (systabindex)TabVect.get(Col);
		out.println("<td align=center><font size=2 color=#000000><a href=editList.jsp?id="+SysTabIndex.getPi_id()+">"+SysTabIndex.getCc_name()+"</a></font></td>");
		if((Col-3)%4 == 0)
			out.println("</tr>");
	}
	%>
	</table>
<%
}
else
	out.println("<br><br><br><br><font size=2 color=#000000 face='Verdana, Arial, Helvetica, sans-serif'>No Info at This Id : "+ sId+"</font>");
%>
</body>
</html>