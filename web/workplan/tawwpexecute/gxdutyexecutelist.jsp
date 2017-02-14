<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpMonthPlanVO"%>
<%@ page import= "com.boco.eoms.base.util.StaticMethod"%>
<script type="text/javascript" src="${app}/workplan/script/tableSort.js"></script>
<script language="javascript">
Ext.onReady(function(){
	colorRows('list-table');
})
function changeDateSubmit(){
  var year = dutyexecuteplan.year.options[dutyexecuteplan.year.selectedIndex].value;
  var month = dutyexecuteplan.month.options[dutyexecuteplan.month.selectedIndex].value;
  dutyexecuteplan.action='../tawwpexecute/gxdutyexecutelist.do?year='+year+'&month='+month;
  dutyexecuteplan.submit(); 
}
</script>
<%
  List monthPlanVOList = (List)request.getAttribute("monthplanvolist");
  TawwpMonthPlanVO tawwpMonthPlanVO = null;
  String year = (String)request.getAttribute("year");
  String month = (String)request.getAttribute("month");    
  String firstdate = year+"-"+month+"-01 00:00:00";
%>

<!--  body begin  -->

<form name="dutyexecuteplan">
<table class="formTable small">
  <tr>
    <td class="content">
      <select name="year" class="select" onChange="changeDateSubmit()">
        <%
          int yyyy = StaticMethod.null2int(year);
          for(int i = yyyy-5;i<yyyy+5;i++){
            if(i==yyyy){
        %>
        <option value="<%=i %>" selected><%=i %></option>
        <%  }else{ %>
        <option value="<%=i %>" ><%=i %></option>
        <%  } }%>
      </select>
    </td>
    <td class="label">
      年
    </td>
    <td class="content">
      <select name="month" class="select" onChange="changeDateSubmit()">
        <%
          for(int j =1;j<=12;j++){
            String tempmonth = j+"";
            if(j<10){
              tempmonth = "0"+tempmonth;
            }
            if(tempmonth.equals(month)){
        %>
        <option value="<%=tempmonth %>" selected><%=tempmonth %></option>
        <%  }else{ %>
        <option value="<%=tempmonth %>" ><%=tempmonth %></option>
        <%  } }%>
      </select>
    </td>
    <td class="label">
      月
    </td>
  </tr>
</table>
<br>
  <table class="listTable" id="list">
    <caption><bean:message key="dutyexecutelist.title.formTitle" /><font color="red" >点击列表头排序</font></caption>
    <%
      if(monthPlanVOList.size() != 0){
    %>
    <thead>
    <tr>
      
     <td onclick="sort('list',0,'char')"><span style="cursor: hand"><bean:message key="dutyexecutelist.title.formPlanName"/></span></td>
      <td onclick="sort('list',1,'char')"><span style="cursor: hand"><bean:message key="dutyexecutelist.title.formDeptName"/></span></td>
       <td onclick="sort('list',2,'char')"><span style="cursor: hand"> <bean:message key="dutyexecutelist.title.formExecuteMonth"/></span></td>
        <td onclick="sort('list',3,'char')"><span style="cursor: hand"><bean:message key="dutyexecutelist.title.formExecuteUser"/></span></td>
     <td onclick="sort('list',4,'char')"><span style="cursor: hand"><bean:message key="viewlist.title.formState"/></span></td>
       
      <td width="8%">
        <bean:message key="dutyexecutelist.title.formAdd"/>
      </td>
      
    </tr>
    </thead>
    
    <tbody>
    <%
        for(int i=0; i<monthPlanVOList.size(); i++){
          tawwpMonthPlanVO = (TawwpMonthPlanVO)monthPlanVOList.get(i);
    %>

    <tr>
      <td width="20%">
        <%=tawwpMonthPlanVO.getName()%>
      </td>
      <td width="15%">
        <%=tawwpMonthPlanVO.getDeptName()%>
      </td>
      <td width="12%">
        <%=tawwpMonthPlanVO.getYearFlag()%><bean:message key="dutyexecutelist.title.formYear"/>
        <%=tawwpMonthPlanVO.getMonthFlag()%><bean:message key="dutyexecutelist.title.formMonth"/>
      </td>
      <td width="13%">
        <%=tawwpMonthPlanVO.getPrincipal()%>
      </td>
      
      <td width="8%"> <%=tawwpMonthPlanVO.getExecuteStateName()%></td>
      <td width="8%">
        <a href="../tawwpexecute/gxexecuteadds.do?yearPlanid=<%=tawwpMonthPlanVO.getYearPlanId()%>&flag=duty">
          <bean:message key="dutyexecutelist.title.formAdd"/>
        </a>
        
      </td>
    </tr>

    <%
        }
      }else{
    %>
    <tr>
      <td height="25" colspan="6">
       <bean:message key="dutyexecutelist.title.nolist"/>
      </td>
    </tr>
    <%
      }
    %>
    
  </tbody>
  </table>
</form>

<!--  body end  -->
<%@ include file="/common/footer_eoms.jsp"%>
