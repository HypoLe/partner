<%@page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.boco.eoms.resmanage.entity.*,com.boco.eoms.common.util.*"%>
<%@page import="mcs.common.db.*"%>
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
	request.setCharacterEncoding("GBK");
	String tabIndex = null;
	String pi_id    = null;
	if(request.getParameter("id") != null)
		pi_id = request.getParameter("id");
	if(request.getParameter("tabname") != null)
		tabIndex = request.getParameter("tabname");
%>
<SCRIPT language=JavaScript>

<!--
function call(htmlurl){
var newwin=window.open(htmlurl,"newsssWin_searchout","top=150,left=150,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=900,height=600");
newwin.focus();
return false;
}

function prodWin(file,window1){
	MsgWin=open(file,window1,'scrollbars=1,toolbar=0, resizable=no,width=560,height=400,ScreenX=30,screenY=30,top=30,left=30');
	MsgWin.window.focus();
}
function goBack()
{
	history.go(-1);
}
function closewin()
{
	window.close();
}
//-->
</SCRIPT>
<html>
<head>
<title>资源详细信息</title>

</head>
<body>
<form name="editform" action="editSave.jsp" method=post>
<%	
entityoperate Entity = new entityoperate();
//得到实体类型
systabindex SysTab = new systabindex();
Vector TabVect = new Vector();
TabVect = Entity.getTabinfor(tabIndex);
String tabname = null;
for(int i=0;i<TabVect.size();i++)
{
	SysTab = (systabindex)TabVect.get(i);
	tabname   = StaticMethod.dbNull2String(SysTab.getCc_name());
}
out.println("<tr class=td_label>");
out.println("<td align=left width='50%'><font size=2>网络资源  >"+tabname+"详细信息</font></td>");
%>
<%
	syscolindex SysColIndex = new syscolindex();

	Vector SysVect = new Vector();

	SysVect = Entity.getcolinfor(tabIndex);

	int colNum = SysVect.size();				//列信息

	if(colNum != 0)
	{
		/******************	构造显示的详细信息	*****************/
		out.println("<br><table width='100%' border=0 cellspacing=0><tr><td>");
		out.println("<table class=table_show width='100%' border=0 cellspacing=1><tr class=td_label><td align=center width='40%'><font size=2><b>属性</b></font></td><td align=center><font size=2><b>值</b></font></td>");
		//<td align=center><font size=2><b>属性</b></font></td><td align=center><font size=2><b>值</b></font></td></tr>"

		for(int Col = 1; Col < SysVect.size(); Col ++)
		{
			SysColIndex = (syscolindex)SysVect.get(Col);
			String cc_name = StaticMethod.dbNull2String(SysColIndex.getCc_name());
			String cc_code = SysColIndex.getCc_code();
			int ref_flag = SysColIndex.getCi_ref_flag();
			int ass_flag = SysColIndex.getCi_ass_flag();
            //========得到列的值-----
			String colValue = StaticMethod.dbNull2String(Entity.getColValue(tabIndex,SysColIndex.getCc_code(),pi_id));
			if (colValue==null)
			{
				colValue = "暂缺";
			}
			/*
			if((Col-1)%2 == 0)
			{*/
				out.println("<tr class='tr_show'>");
			//}

				out.println("<td align=center width='40%'><font size=2 color=#000000 face='Verdana, Arial, Helvetica, sans-serif'>"+cc_name+"</font></td>");
				
				switch(ref_flag)
				{
					case 0:
						out.println("<td align=center><font size=2 color=#000000 face='Verdana, Arial, Helvetica, sans-serif'>"+colValue+"</font></td>");
						break;
					case 1:
					   out.println("<td align=center><font size=2 color=#000000 face='Verdana, Arial, Helvetica, sans-serif'>");
					
					   refcoldata RefColData = new refcoldata();

					   Vector RefColVec = new Vector();

					   //得到参考列的id,name的value
					   /*	Only One Value in Vector	*/
					   //out.println(SysColIndex.getPi_id());
					   //out.println(pi_id);
					   RefColVec = Entity.getrefColValue(Integer.toString(SysColIndex.getPi_id()),colValue);
					   
					   for (int i=0;i<RefColVec.size();i++)
						{
						   int  idvalue   = ((refcoldata)RefColVec.elementAt(i)).getPi_idvalue();

						   String namevalue = StaticMethod.dbNull2String(((refcoldata)RefColVec.elementAt(i)).getCc_namevalue());
						   //是否输出连接的标志
						   int ci_link_flag = ((refcoldata)RefColVec.elementAt(i)).getCi_link_flag();
						   if (ci_link_flag ==1)
							{
							   String RefTableId  =Entity.getRefTableId(Integer.toString(SysColIndex.getPi_id()));
							   out.println("<a href=editDetail.jsp?id="+colValue+"&&tabname="+RefTableId+" target=_blank>"+namevalue+"</a>");
							}
							else
							{
								out.println(namevalue);
							}
						}
					   out.println("</font></td>");
					   break;
					   case 2:
					out.println("<td align=center><font size=2 color=#000000 face='Verdana, Arial, Helvetica, sans-serif'>"+colValue+"</font></td>");
					break;
					default:
						break;
				}
				String linkJsp = Entity.getLinkJsp(Integer.toString(SysColIndex.getPi_id()));
				if (linkJsp==null || linkJsp.equals(""))
				{
					linkJsp = "blank.jsp?id=";
				}
				if (ass_flag == 1) //普通辅助信息
				{
					out.println("<td align=center><font size=2 color=#000000 face='Verdana, Arial,  Helvetica, sans-serif'><a href="+"../"+ linkJsp +pi_id+"  target=_blank>查看"+"</a></font></td>");
					//out.println("<td align=center><font size=2 color=#000000 face='Verdana, Arial,  Helvetica, sans-serif'><a href=javascript:prodWin('../"+linkJsp+pi_id+"','prodpop');>查看"+"</a></font></td>");
				}
				else if(ass_flag == 2) //是拓扑图
				{			
					out.println("<td align=center><font size=2 color=#000000 face='Verdana, Arial,  Helvetica, sans-serif'><a href="+"../"+ linkJsp +pi_id+"&title="+cc_name+" onClick='return call(this.href);' target=_blank>查看"+"</a></font></td>");
					//out.println("<td align=center><font size=2 color=#000000 face='Verdana, Arial,  Helvetica, sans-serif'><a href=javascript:prodWin('../"+linkJsp+pi_id+"','prodpop');>查看"+"</a></font></td>");
				}

				//if(Col%2 == 0)
					out.println("</tr>");
			}
			
			out.println("</table></td></tr></table>");
		
	}
	else
		out.println("<br><br><br><br><font size=2 color=#000000 face='Verdana, Arial, Helvetica, sans-serif'>No Info at This Id : "+ tabIndex+"</font>");
%>
</form>

<!-- <p align=center><font size=2><a href="javascript:goBack()">返回</a></font></p>

 
<p align=center><font size=2><a href="javascript:closewin()">关闭</a></font></p>
 -->
</body>
</html>
