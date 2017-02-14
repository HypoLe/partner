<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%> 
<%@ page import ="com.boco.eoms.base.util.StaticMethod"%>
<%@ page import ="com.boco.eoms.workplan.vo.*"%>
<%@ page import ="java.util.*"%>
<%@ page import ="com.boco.eoms.commons.system.dept.model.*"%>
<%@ page import ="com.boco.eoms.commons.system.user.model.*"%>
<%@ page import ="com.boco.eoms.workplan.model.*"%>

 <table class="listTable">
 <thead>
 <tr >
  <td colspan='1'>序号</td>
 <td rowspan='1'>作业计划名称</td>
  <td colspan='1'>所属部门</td>
 <td colspan='1'>开始时间</td>
 <td colspan='1'>截至时间</td>
 <td colspan='1'>执行人</td>
 <td colspan='1'>作业总数</td>
 <td colspan='1'>按时完成率</td>
 <td colspan='1'>总共完成率</td>
 <td rowspan='1'>查看</td>
 </tr>
  </thead>
 <%
 List completelist = (List)request.getAttribute("completelist");
 for(int i = 0;i<completelist.size();i++){ 
 TawwpStatVO tawwpStatVO = (TawwpStatVO)completelist.get(i);
 //TawSystemDept dept = tawwpStatVO.getTawDept();
  String deptname = "";
 TawSystemUser user = tawwpStatVO.getTawSystemUser();
  String username = user.getUsername();
   //if(null!=user.getDeptname() && !"".equals(user.getDeptname())){
   //	deptname = user.getDeptname();
   //}
   deptname = (String)request.getAttribute("deptname");
   TawwpMonthPlan tawwpMonthPlan = tawwpStatVO.getTawWpMonthPlan();

   String plantid = tawwpMonthPlan.getId();
 int allcount = tawwpStatVO.getAllCount();
int outTimeCount = tawwpStatVO.getAlloutTimeCount();
int inTimeCount = tawwpStatVO.getAllinTimeCount();
float allcountf = Float.parseFloat(allcount+""); 
float outTimeCountf = Float.parseFloat(outTimeCount+""); 
float inTimeCountf = Float.parseFloat(inTimeCount+"");
String allComplete =  String.valueOf((outTimeCountf+inTimeCountf)/allcountf*100)+"%";
String allinTimeComplete = String.valueOf(inTimeCountf/allcountf*100)+"%";

String monthflag = (String) request.getSession().getAttribute(
				"monthflag");
String yearflag = (String) request.getSession()
				.getAttribute("yearflag");
				
 %>
 <tr>
   <td><%=i+1 %></td>
 <td><%=tawwpMonthPlan.getName() %>传输代维作业</td>
 <td><%=deptname%></td>
 <td><%=yearflag %>年<%= monthflag%>月1日</td>
 <td><%=yearflag %>年<%= monthflag%>月
<%
	Calendar cDate = new GregorianCalendar();
  	cDate.set(Integer.parseInt(yearflag),Integer.parseInt(monthflag)-1,01);
  	int lastday = cDate.getActualMaximum(Calendar.DAY_OF_MONTH);
 %><%=lastday %>
</td>
 <td><%=username %></td>
   <td><%=allcount %></td>
 <td><%= allinTimeComplete%></td>
 <td><%= allComplete%></td>
 <td><a href="${app}/workplan/tawwpexecute/viewall.do?monthplanid=<%=plantid %>">查看详细</a></td>
 
 </tr>
 

 <%} %>
 </table >
 <br>

 <%@ include file="/common/footer_eoms.jsp"%>