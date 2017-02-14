 <%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="com.boco.eoms.workplan.util.TawwpUtil"%>
<%@page import="com.boco.eoms.workplan.vo.TawwpExecuteContentVO"%>
<%@page import="com.boco.eoms.workplan.vo.TawwpExecuteFileVO"%>
<%@page import="com.boco.eoms.workplan.model.TawwpExecuteFile,com.boco.eoms.base.util.StaticMethod"%>
<%@page import="java.util.*"%>
 
<%
  String a = request.getAttribute("startdate") == null ? TawwpUtil.getCurrentDateTime("yyyy-MM-dd") : (String) request.getAttribute("startdate");
  String b = request.getAttribute("enddate") == null ? TawwpUtil.getCurrentDateTime("yyyy-MM-dd") : (String) request.getAttribute("enddate");
  String cycleName = (String)request.getAttribute("cyclename");
  String cycle = (String)request.getAttribute("cycle");
  String monthPlanId = (String)request.getAttribute("monthplanid");
  String startDate = (String)request.getAttribute("startdate");
  String endDate = (String)request.getAttribute("enddate");
%>
<script language="JavaScript">
function GoBack()
{
  window.history.back()
}

function onExport()
{
  window.navigate( "contentexport.do?monthplanid=<%=monthPlanId%>&cycle=<%=cycle%>&startdate=<%=startDate%>&enddate=<%=endDate%>&reaction=/addons/filedownload.jsp");
}

function SubmitCheck(){
var time1 = document.tawwplogform.startdate.value;
var time2 = document.tawwplogform.enddate.value;
if(time1 == ""){
  return false;
}

if(time2 == ""){
  return false;
}

var setdate,settime,tmptime1,tmptime2;

setdate = time1.split(" ")[0];
settime = time1.split(" ")[1];
tmptime1 = new Date(setdate.split("-")[0],setdate.split("-")[1] - 1,setdate.split("-")[2],settime.split(":")[0],settime.split(":")[1]);

setdate = time2.split(" ")[0];
settime = time2.split(" ")[1];
tmptime2 = new Date(setdate.split("-")[0],setdate.split("-")[1] - 1,setdate.split("-")[2],settime.split(":")[0],settime.split(":")[1]);

var temp = tmptime2.getTime() - tmptime1.getTime();
if(temp<0){
  alert("结束时间比起始时间小,请重新选择时间！");
  document.tawwplogform.enddate.focus();
  return false;
}else{
  return true;
  }
}

</script><table width="700" align=center style="margin:0pt 0pt 2pt 0pt">
  <tr>
    <td width="700" align="center" class="table_title">作业计划执行内容</td>
  </tr>
</table>
<form name='tawwplogform' action="../tawwpcheck/checkmonthplancontent.do" onsubmit="return SubmitCheck()">
 <table width="700" align=center  class="formTable">
  <tr>
    <td width="500" align="center" class="label">      开始时间：
    <input type="text" name="startdate"  value="<%=request.getAttribute("startdate")%>" size="20" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
     &nbsp;&nbsp;
      结束时间：
      <input type="text" name="enddate"  value="<%=request.getAttribute("enddate")%>" size="20" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
      
      <input type="submit" value="查询" name="B1" Class="button">
    </td>
    <td width="200" align="center" class="label">
      周期类型:&nbsp;&nbsp;<%=cycleName%>
    </td>
  </tr>
  <input type="hidden" name="monthplanid" value="<%=request.getParameter("monthplanid")%>">
  <input type="hidden" name="cycle" value="<%=cycle%>">
</table>
</form>
<br/>
<br/>
<br/>
<table border="0" width="100%"  class="listTable" align=center>
  <tr>
    <td class="label">执行内容</td>
    <td class="label">周期</td>
    <td class="label">计划时间</td>
    <td class="label">完成时间</td>
    <td class="label">记录人</td>
    <td class="label">记录内容</td>
    <td class="label">附件</td>
    <td class="label">附加表</td>
    <td class="label">补填原因</td>
  </tr>
<%
  Vector vector = (Vector) request.getAttribute("datearray");
  Hashtable hashtable = (Hashtable) request.getAttribute("executecontenthash");
  List list = null;
  String date = null;
  TawwpExecuteContentVO tawwpExecuteContentVO = null;
  for (int i = 0; i < vector.size(); i++) {
    date = (String) vector.get(i);
    list = (List) hashtable.get(date.substring(0, 10));
    if (list != null) {
      for (int j = 0; j < list.size(); j++) {
        tawwpExecuteContentVO = (TawwpExecuteContentVO) list.get(j);
      
%>
  <tr>
    <td><%=tawwpExecuteContentVO.getName()%>    </td>
    <td><%=tawwpExecuteContentVO.getCycleName()%>    </td>
    <td><%=tawwpExecuteContentVO.getStartDate().substring(0,10)%>    </td>
    <td>
    <%if (!tawwpExecuteContentVO.getExecuteFlag().equals("0")) {    %>
		<%=tawwpExecuteContentVO.getCrtime().substring(0,10)%>    <%}    %>
    </td>
    <td><%=tawwpExecuteContentVO.getCruser()%>    </td>
    <td>
    <%if (!tawwpExecuteContentVO.getExecuteFlag().equals("0")) {    %>
	<%=tawwpExecuteContentVO.getContent()%>    <%}    %>
    </td>
    <td>
    <%
      List fileList = tawwpExecuteContentVO.getExecuteFileListVO();
      for (int k = 0; k < fileList.size(); k++) {
        TawwpExecuteFileVO tawwpExecutefile = (TawwpExecuteFileVO) fileList.get(k);
    %>
      <li><a href='${app}/workplan/tawwpexecute/filedown.do?fileId=<%=tawwpExecutefile.getFileCodeName()%>'><%=tawwpExecutefile.getFileName()%></a>  <% } %>
    </td>
    <td>    </td>
    <td>
    <%=StaticMethod.null2String(tawwpExecuteContentVO.getReason())%>
    <%--
    <%
      List listUser = tawwpExecuteContentVO.getExecuteContentUserListVO();
      if (listUser != null) {
        for (int lll = 0; lll < listUser.size(); lll++) {
          com.boco.eoms.workplan.vo.TawwpExecuteContentUserVO uservo = (com.boco.eoms.workplan.vo.TawwpExecuteContentUserVO) listUser.get(lll);
          if (uservo.getReason() != null && uservo.getReason().trim().length() > 0) {
            out.write(uservo.getReason() + " ");
          }
        }
      }
    %>
    --%></td>
  </tr>
<%
  }
  }
      }
%>
  <tr align="center">
    <td width="700" bgcolor="#e5edf8" class="clsthd2" colspan="9">
      <input type="button" value="导出" onclick="javascript:onExport();" class="button">
      <INPUT type=button value=返回 name=button Onclick="GoBack();" Class="button">
    </td>
  </tr>
</table>
