<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%> 
<%@ page import ="com.boco.eoms.base.util.StaticMethod"%>
<%@ page import ="com.boco.eoms.workplan.vo.*"%>
<%@ page import ="com.boco.eoms.commons.system.dept.model.*"%>
<%@ page import ="com.boco.eoms.workplan.model.*"%>
<%@ page import ="java.util.*"%>
<%
	 int yearFlag = StaticMethod.nullObject2int(request.getAttribute("yearflag"));
	  request.getSession().setAttribute("yearflag",request.getAttribute("yearflag"));
      int monthFlag = StaticMethod.nullObject2int(request.getAttribute("monthflag")); 
      request.getSession().setAttribute("monthflag",request.getAttribute("monthflag"));
      int dayNow = StaticMethod.nullObject2int(request.getAttribute("dayNow"));
      request.getSession().setAttribute("dayNow",String.valueOf(dayNow));
	  int yearNow = StaticMethod.nullObject2int(request.getAttribute("yearNow"));
      int monthNow = StaticMethod.nullObject2int(request.getAttribute("monthNow")); 
      request.getSession().setAttribute("monthNow",String.valueOf(monthNow));
%>
<script type='text/javascript'>
 function onMonth(){
 	var yearflag = document.getElementById("yearflag").value;
 	var monthflag = document.getElementById("monthflag").value;
 	window.location.href("${app}/workplanpartner/workplanpartners.do?method=deptStat&node=104010101&yearflag="+yearflag+"&monthflag="+monthflag);
 }
 </script>
 <form name='statyear'>
<table width='700' align=center class="listTable" >
    <thead>
    <tr>
        <select size='1' id='yearflag' name='yearflag' class="select">
        <% for (int i = yearNow-10;i<=yearNow;i++) {
        	if(i == yearFlag){
        %>
          <option value='<%=i %>' selected><%=i %></option>
          <%}else{ %>
          <option value='<%=i %>'><%=i %></option>
          <% }}%>
        </select>年
        <select size='1' id='monthflag' name='monthflag' class="select">
        <% for (int j = 1;j<=12;j++) {
        	if(j == monthFlag){
        %>
           <option value='<%=j %>'selected><%=j %></option>
          <%}else{ %>
          <option value='<%=j %>'><%=j %></option>
          <% }}%>
        </select>月
        <input type="button" value="月度选择" name="B1" onclick="onMonth()"  Class="button">
    </tr>
    </thead>
  </table>
 </form>
 <table class="listTable">
 <thead>
 <tr >
 <td rowspan='1'>月度作业计划名称</td>
 <td colspan='1'>网元名称</td>
  <td colspan='1'>所属部门</td>
 <td colspan='1'>时间</td>
 <td colspan='1'>按时完成率</td>
 <td colspan='1'>总共完成率</td>
 <td rowspan='1'>查看</td>
 </tr>
  </thead>
 <%
 List namelist = (List)request.getAttribute("namelist");
  request.getSession().setAttribute("namelist",namelist);
  List volist = (List)request.getAttribute("completelist");
 for(int i = 0;i<volist.size();i++){ 
 TawwpStatVO voi = (TawwpStatVO)volist.get(i);
 String workplanid = voi.getTawWpMonthPlan().getId();
 int allcount = voi.getAllCount(); 
int outTimeCount = voi.getAlloutTimeCount(); 
int inTimeCount = voi.getAllinTimeCount(); 
float allcountf = Float.parseFloat(allcount+"");
float outTimeCountf = Float.parseFloat(outTimeCount+"");
float inTimeCountf = Float.parseFloat(inTimeCount+"");

String allComplete = String.valueOf(((outTimeCountf+inTimeCountf)/allcountf)*100)+"%";
String inTimeComplete = String.valueOf((inTimeCountf/allcountf)*100)+"%";	
 TawSystemDept depti = voi.getTawDept();
 String deptid = depti.getDeptId();
 %>
 <tr>
 <td><%=voi.getTawWpMonthPlan().getName() %>传输代维作业</td>
 <td>无</td>
  <td><%=depti.getDeptName() %></td>
 <td><%=yearFlag %>年<%=monthFlag %>月</td>
 <td><%=inTimeComplete %></td>
 <td><%=allComplete %></td>
 <td><a href="${app}/workplanpartner/workplanpartners.do?method=personstat&deptid=<%=deptid %>&workplanid=<%=workplanid %> ">查看详细</a></td>
 </tr>
  <%} %>
 </table >
 <br>

 <%@ include file="/common/footer_eoms.jsp"%>