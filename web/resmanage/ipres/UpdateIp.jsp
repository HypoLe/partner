<%@page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.boco.eoms.resmanage.query.*"%>
<%@page import="com.boco.eoms.resmanage.entity.*"%>
<%@page import="com.boco.eoms.common.util.*"%>
<%
/**
*@ AHEOMS (resmanage)
*@ Copyright : (c) 2004
*@ Company : BOCO.
*@ ip��Դ����ģ��
*@ author Wuzongxian
*@ version 1.0
*@ date    2004-03-05
**/
%>
<%
	
  //��������
  int optType = 0;
  if (request.getParameter("optType")!=null)
      optType = Integer.parseInt(request.getParameter("optType"));
  //�鿴
  String readflag="";
  if (optType==4)
	 readflag = "readonly";
  int pi_id = Integer.parseInt(request.getParameter("id"));
  IpresOpt ipresopt = new IpresOpt();
  IpModel ipmodel = new IpModel();
  ipmodel = ipresopt.getIpinfor(pi_id);
  String ip = ipmodel.getCc_ip();
  String[] iptmp = StaticMethod.TokenizerString2(ip,".");
  int    fi_segment    = ipmodel.getFi_netsegment();
  int    state   = ipmodel.getFi_state();
  String addinfor = ipmodel.getCc_addresinfo();
  String beginip = "";
  if (request.getParameter("beginip")!=null)
	beginip = request.getParameter("beginip");
  String endip = "";
  if (request.getParameter("endip")!=null)
	endip = request.getParameter("endip");
  //===
  int	search_type = 0;
  if (request.getParameter("search_type")!=null)
	 search_type = Integer.parseInt(request.getParameter("search_type"));
  if (request.getParameter("city")!=null)
	Integer.parseInt(request.getParameter("city"));
  int ipproject = 0;
  if (request.getParameter("ipproject")!=null)
 	ipproject = Integer.parseInt(request.getParameter("ipproject"));
  String ipaddress = "";
  if (request.getParameter("ipaddress")!=null)
	ipaddress = request.getParameter("ipaddress");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<title>IP��ַ����</title>
<script language="javascript">
</script>
</head>
<body background="<%=request.getContextPath()%>/images/img/bg001.gif">
<form id=form1 name=form1 action="IpSave.jsp" method=post>
<table border="0" width="60%" cellspacing="1" bgcolor="#709ED5" align=center>
<tr>
<td width="30%" height="25" bgcolor="#d7dae1" align=right>��������:</td>
<td width="70%" height="25" bgcolor="#E5EDF8">&nbsp;<%=beginip%>&nbsp;---->&nbsp;<%=endip%>&nbsp;
</td>
</tr>
<tr>
<td width="30%" height="25" bgcolor="#d7dae1" align=right>IP��ַ:</td>
<td width="70%" height="25" bgcolor="#E5EDF8">
	<% for (int i=0;i<4;i++){ if (i<3){%>
		<input type="text" name="ip<%=i%>" readonly value='<%=iptmp[i]%>' size="5" maxlength="3"  onkeyup="checknum(this)"><b>.</b>
	<%} else{%>
		<input type="text" name="ip<%=i%>"  <%=readflag%> value='<%=iptmp[i]%>' size="5" maxlength="3"  onkeyup="checknum(this)">
	<%}}%><%if (optType!=4){%><font color="#FF0000">**</font><%}%>
</td>
</tr>
<tr>
  <td width="30%" height="25" bgcolor="#d7dae1" align=right>��ַ״̬:</td>
  <%
  IpPrjmodel prjmodel = new IpPrjmodel();
  Vector stVec = new Vector();
  stVec = ipresopt.getIpprjList("eip_ipstate",0);
  out.println("<td width=70% height=25 bgcolor=#E5EDF8><select name=ipstate"+readflag+">");
  for(int a = 0; a < stVec.size(); a ++)
  {
	prjmodel = (IpPrjmodel)stVec.get(a);
	if (prjmodel.getId()==state)
		out.println("<option value="+prjmodel.getId()+" selected>"+StaticMethod.dbNull2String(prjmodel.getName())+"</option>");
	else
		out.println("<option value="+prjmodel.getId()+">"+StaticMethod.dbNull2String(prjmodel.getName())+"</option>");
  }
  out.println("</select></td>");
%>
</tr>
<tr>
  <td width="30%" height="25" bgcolor="#d7dae1" align=right>��ַ��;:</td>
  <td width="70%" height="25" bgcolor="#E5EDF8">
	<textarea name="addinfor" cols="30" rows="4" <%=readflag%>><%=addinfor%> </textarea>
  </td>
</tr>
<%if (optType!=4){%>
<tr>
  <td width="70%" height="25" bgcolor="#E5EDF8" colspan=2 align=center>
  <input type="button" value="����" onclick='formsubmit()'>
  <input type="button" value="����" onclick="javascript:history.back()">
  </td>
</tr>
<%}%>
</table>
<input type="hidden" name="id" value=<%=pi_id%>>
<input type="hidden" name="optType" value=<%=optType%>>
<input type="hidden" name="search_type" value=<%=search_type%>>
<input type="hidden" name="ipaddress" value=<%=ipaddress%>>
<input type="hidden" name="parentid" value=<%=fi_segment%>>
</form>
<script language="JavaScript">
function formsubmit()
{
	//alert(form1.beginip0.value);
	if (form1.ip0.value=="" || form1.ip1.value=="" || form1.ip2.value=="" || form1.ip3.value=="")
	{
		alert("��ʼ��ַ����Ϊ��");
		return;
	}
	
	form1.submit();
}
function IsDigit(cCheck)
{
	return (('0'<=cCheck) && (cCheck<='9'));
}
function IsNumber(str)
{
	for (var nIndex=0; nIndex<str.length; nIndex++)
	{
		cCheck = str.charAt(nIndex);
		if (!(IsDigit(cCheck)))
			return false;
	}
   return true;
}

function allTrim(ui)
{
	var notValid=/\s/;
	while(notValid.test(ui))
		ui=ui.replace(notValid,"");
	return ui;
}
function checknum(field)
{
	if (!IsNumber(field.value))
	{
		alert("����������!");
		field.value="";
		field.focus();
		return;
	}
    else
	{
		if (field.value>255 || field.value<0)
		{
			alert("������0~255֮��");
		    field.value="";
			field.focus();
			return;
		}
	}
}
</script>

</body>