<%@ page language="java" contentType="text/html;charset=gb2312" %>
<%@ page import="com.boco.eoms.mobilewap.base.tag.imp.WapHrefTag" %>
<%@ page autoFlush="true" %>
<%
String app = request.getContextPath(); 

String wapCardId = request.getParameter("wapCardId");
String wapNodeId = request.getParameter("wapNodeId");
String tagType = request.getParameter("tagType");
 %>
<html>
<head>
<title>管理员WAP配置工具</title>
<link type="text/css" rel="stylesheet" href="<%=app%>/webtool/styles/style.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript">
/**
 * 提交check
 */
function checkInput(){
   // 提交地址不能为空
   var addressValue = form1.href.value;
   if(addressValue == null || addressValue.length==0) {
      alert("提交地址不能为空!");
      document.form1.href.focus();
      return false;
   }
   var textValue = form1.text.value;
   if(textValue == null || textValue.length==0) {
      alert("'链接文字'不能为空!");
      document.form1.text.focus();
      return false;
   }
}
</script>
</head>
<body>
<form name="form1" action="<%=app %>/webtool/tag" onSubmit="return checkInput()">
  <input name="act" type="hidden" value="save"	>
   <input name="tagType" type="hidden" value="<%=tagType %>">
  
  <input name="wapCardId" type="hidden" value="<%=wapCardId %>">
  <input name="classStr" type="hidden" value="com.boco.eoms.mobilewap.base.tag.imp.WapHrefTag">
  <input name="wapNodeId" type="hidden" value="<%=wapNodeId %>">
<table width="100%">
	 <tr>
	   <td class="title">增加一个外部链接</td>
	   <td align="right"></td>
	 </tr>
</table>
<table class="form_table">
    <tr>
		<th>外链接地址：</th>
		<td>
			<input id="href" name="href" type="text" value="wap?cardId="  size="20" ><font color="#FF0000">*</font>
		</td>
	</tr>
	<tr>
		<th>链接文字</th>
		<td>
			<input id="text0" name="text" type="text" value=""  size="20" ><font color="#FF0000">*</font>
			<!-- 
			<input type="button" value="选择参数变量" onclick="window.open('<%=app %>/webtool/common/getNodeVariableList.jsp?wapNodeId=<%=wapNodeId %>&k=0','','height=400,width=340,status=no,toolbar=no,menubar=no,location=no')">	    
			 -->
		</td>
	</tr>
</table>
<table width="100%">
 <tr>
   <td align="right"><input type="submit" class="inputsubmit" value="提交"/></td>
 </tr>
</table> 	  
</form>
</body>
</html>
