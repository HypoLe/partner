<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.Hashtable"%>
<%@ page import ="java.util.Enumeration"%>
<%@ page import ="com.boco.eoms.workplan.model.TawwpMonthPlan"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpMonthPlanVO"%>
<%@ page import ="com.boco.eoms.workplan.util.TawwpUtil"%>
<%@ page import ="com.boco.eoms.base.util.StaticMethod"%>
<script language="javascript">
Ext.onReady(function(){
	colorRows('list-table');
})

function check()
{

}
</script>

<form name="search" action="../tawwpexecute/searchBynettype.do">
<input type="hidden" name="deptid" value=<%=request.getAttribute("deptid") %>>
<input type="hidden" name="nettypeid" value=<%=request.getAttribute("nettypeid") %>> 
<table>
<tr>
<td width="50" >
        <bean:message key="querymonthplan.title.formYearFlag"/>
      </td>
<td width="100">
        <select name="year" class="select">
        <%
          String yyyy =  TawwpUtil.getCurrentDateTime("yyyy");
          int year = StaticMethod.null2int(yyyy);
          for (int i = year;i>year-10;i--){
              if(i==year){
        %>
          <option value='<%=i%>' selected><%=i%></option>
        <%    }else{%>
          <option value='<%=i%>'><%=i%></option>
        <%    }
          }
        %>
        </select>
      </td>
      <td width="20"></td>
        <td width="50" >
        <bean:message key="querymonthplan.title.formMonthFlag"/>
      </td>
      <td width="200" colspan="2">
        <select name="months" class="select">
        <% String m=(String)request.getAttribute("m");
           int month=Integer.parseInt(m);
         
         for(int i=1;i<13;i++)
         {
         
         if(i<10)
         {
         if(month==i)
         {
         %>
         <option value='0<%=i%>' selected>0<%=i%></option>
         <%}
         else{ %>
         <option value='0<%=i%>'>0<%=i%></option>
        <%
        }}else{
          if(month==i)
          { %> 
         <option value='<%=i%>' selected><%=i %></option>
         <% 
         }
         else{
         %>
          <option value='<%=i%>' ><%=i %></option>
        
        <%}}} %>
        </select>
      </td>
     <td><input type="submit" value="确定" class="button" ></td>

</tr>
</table>
</form>
<br>
<!--  body begin -->

<form name="dailyexecuteplan">

  <table class="listTable" id="list">
    <caption><bean:message key="dailyexecutelist.title.formTitle" /></caption>
   
    <thead>
    <tr>
      <td width="15%">
        <bean:message key="dailyexecutelist.title.formPlanName"/>
      </td>
      <td width="14%">
        <bean:message key="dailyexecutelist.title.formConcernedNet"/>
      </td>
      <td width="14%">
        <bean:message key="dailyexecutelist.title.formDeptName"/>
      </td>
      <td width="12%">
        <bean:message key="dailyexecutelist.title.formExecuteMonth"/>
      </td>
      <td width="12%">
        <bean:message key="dailyexecutelist.title.formExecuteUser"/>
      </td>
      <td width="8%">
        <bean:message key="dailyexecutelist.title.formAdd" />
      </td>
      <td width="8%">
        <bean:message key="dailyexecutelist.title.formView"/>
      </td>
      <td>
       <bean:message key="viewlist.title.formState"/>
       </td>
      <td width="8%">
        <bean:message key="dailyexecutelist.title.formConfirm"/>
      </td>
    </tr>
    </thead>
    <%
  
  Hashtable monthPlanVOHash = (Hashtable)request.getAttribute("monthplanvohash");
 
List  list = (List)request.getAttribute("monthplanlist");
for (int i=0;i<list.size();i++){
	TawwpMonthPlan tawwpMonthPlan = (TawwpMonthPlan)list.get(i);
	TawwpMonthPlanVO tawwpMonthPlanVO  = (TawwpMonthPlanVO)monthPlanVOHash.get(tawwpMonthPlan);




%>
    <tbody>
 
    <tr>
      <td width="15%">
        <%=tawwpMonthPlanVO.getName()%>
      </td>
      <td width="14%">
      <%
        if(tawwpMonthPlanVO.getNetName().equals("无网元")){
      %>
        <%=tawwpMonthPlanVO.getNetTypeName()%>
      <% }else{%>
        <%=tawwpMonthPlanVO.getNetName()%>
      <% }%>
      </td>
      <td width="14%">
        <%=tawwpMonthPlanVO.getDeptName()%>
      </td>
      <td width="12%">
        <%=tawwpMonthPlanVO.getYearFlag()%><bean:message key="dailyexecutelist.title.formYear"/>
        <%=tawwpMonthPlanVO.getMonthFlag()%><bean:message key="dailyexecutelist.title.formMonth"/>
      </td>
      <td width="12%">
       <%=tawwpMonthPlanVO.getPrincipal() %>
      </td>
    <td width="8%">
        <%
        if(!"0".equals(tawwpMonthPlanVO.getMonthExecuteUserState())){
        %>
        <a href="../tawwpexecute/executeadds.do?monthplanid=<%=tawwpMonthPlanVO.getId()%>&flag=daily&userbystub=<%=tawwpMonthPlanVO.getUserByStub()%>">
          <bean:message key="dailyexecutelist.title.formAdd" />
        </a>
        <%
        }
        %>
      </td>
      
      <td width="8%">
        <%
        if(!"0".equals(tawwpMonthPlanVO.getMonthExecuteUserState())){
        %>
        <a href="../tawwpexecute/executedeptview.do?monthplanid=<%=tawwpMonthPlanVO.getId()%>&userbystub=<%=tawwpMonthPlanVO.getUserByStub()%>">
          <bean:message key="dailyexecutelist.title.formView"/>
        </a>
        <%
        }
        %>
      </td>
     <td> <%=tawwpMonthPlanVO.getExecuteStateName()%></td>
      <td width="8%">
        <%
        if("0".equals(tawwpMonthPlanVO.getMonthExecuteUserState())){
        %>
        <a href="../tawwpexecute/confirm.do?monthexecuteuserid=<%=tawwpMonthPlanVO.getMonthExecuteUserId()%>">
          <bean:message key="dailyexecutelist.title.formConfirm"/>
        </a>
        <%
        }
        %>
     
      
      </td>
            </tr>
            
       </tbody>
         <%} %>
  </table>

</form>
<%@ include file="/common/footer_eoms.jsp"%>