<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.Hashtable"%>
<%@ page import ="java.util.Enumeration"%>
<%@ page import ="com.boco.eoms.workplan.model.TawwpMonthPlan"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpMonthPlanVO"%>
<%@ page import ="com.boco.eoms.workplan.util.TawwpUtil"%>
<%@ page import= "com.boco.eoms.base.util.StaticMethod"%>
<script type="text/javascript" src="${app}/workplan/script/tableSort.js"></script>
<script language="javascript">
Ext.onReady(function(){
	colorRows('list-table');
})
function changeDateSubmit(){
  var year = dailyexecuteplan.year.options[dailyexecuteplan.year.selectedIndex].value;
  var month = dailyexecuteplan.month.options[dailyexecuteplan.month.selectedIndex].value;
  dailyexecuteplan.action='../tawwpexecute/gxdailyexecutelist.do?year='+year+'&month='+month;
  dailyexecuteplan.submit(); 
}
</script>

<%
  List listKey = (List)request.getAttribute("listKey");
  Hashtable monthPlanVOHash = (Hashtable)request.getAttribute("monthplanvohash");
  List stubMonthPlanList = (List)request.getAttribute("stubmonthplanlist");
  Enumeration hashKeys = null;
   TawwpMonthPlan tawwpMonthPlan = null;
  TawwpMonthPlanVO tawwpMonthPlanVO = null;
  String monthPlanId = "";
  Hashtable tempHash = null;
    String year = (String)request.getAttribute("year");
    String month = (String)request.getAttribute("month");    
    String firstdate = year+"-"+month+"-01 00:00:00";
    int week = TawwpUtil.getWeek(firstdate)-1;
    if(week==0){
      week=7;
    }
    
    int dayCount = TawwpUtil.getDayCountOfMonth(year,month);
%>

<!--  body begin -->

<form name="dailyexecuteplan">
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
    <caption><bean:message key="dailyexecutelist.title.formTitle" />   <font color="red" >点击列表头排序</font></caption>
    <%
      if(monthPlanVOHash.size() != 0 || stubMonthPlanList.size() != 0){
    %>
    <thead>
    <tr>
     <td width="15%" onclick="sort('list',0,'char')"><span style="cursor: hand"><bean:message key="dailyexecutelist.title.formPlanName"/></span></td>
      <td width="14%" onclick="sort('list',1,'char')"><span style="cursor: hand"><bean:message key="dailyexecutelist.title.formDeptName"/></span></td>
       <td width="12%" onclick="sort('list',2,'char')"><span style="cursor: hand"> <bean:message key="dailyexecutelist.title.formExecuteMonth"/></span></td>
        <td  width="12%" onclick="sort('list',3,'char')"><span style="cursor: hand"> <bean:message key="dailyexecutelist.title.formExecuteUser"/></span></td>
         <td width="12%" onclick="sort('list',4,'char')"><span style="cursor: hand"> <bean:message key="viewlist.title.formState"/></span></td>
          <td width="12%" onclick="sort('list',5,'char')"><span style="cursor: hand"> 执行</span></td>
           
      <%--
      <td width="8%">
        <bean:message key="dailyexecutelist.title.formView"/>
      </td>
      --%>
      <!--td width="8%">
        <bean:message key="dailyexecutelist.title.formConfirm"/>
      </td-->
    </tr>
    </thead>
    
    <tbody>
    <%
        hashKeys = monthPlanVOHash.keys();
        for(int z=0;z<listKey.size();z++){
           tawwpMonthPlan = (TawwpMonthPlan)listKey.get(z);
         // monthPlanId = (String)(hashKeys.nextElement());
         // tawwpMonthPlanVO = (TawwpMonthPlanVO)monthPlanVOHash.get(monthPlanId);
         tawwpMonthPlanVO = (TawwpMonthPlanVO)monthPlanVOHash.get(tawwpMonthPlan);
    %>

    <tr>
      <td width="15%">
        <%=tawwpMonthPlanVO.getName()%>
      </td>
      <%--
      <td width="14%">
      <%
        if(tawwpMonthPlanVO.getNetName().equals("无网元")){
      %>
        <%=tawwpMonthPlanVO.getNetTypeName()%>
      <% }else{%>
        <%=tawwpMonthPlanVO.getNetName()%>
      <% }%>
      </td>
      --%>
      <td width="14%">
        <%=tawwpMonthPlanVO.getDeptName()%>
      </td>
      <td width="12%">
        <%=tawwpMonthPlanVO.getYearFlag()%><bean:message key="dailyexecutelist.title.formYear"/>
        <%=tawwpMonthPlanVO.getMonthFlag()%><bean:message key="dailyexecutelist.title.formMonth"/>
      </td>
      <td width="12%">
        <%=tawwpMonthPlanVO.getPrincipal()%>
      </td>
      <td> <%=tawwpMonthPlanVO.getExecuteStateName()%></td>
      <td width="8%">
        
        <a href="../tawwpexecute/gxexecuteadds.do?yearPlanid=<%=tawwpMonthPlanVO.getYearPlanId()%>&monthexecuteuserid=<%=tawwpMonthPlanVO.getMonthExecuteUserId()%>&monthExecuteUserState=<%=tawwpMonthPlanVO.getMonthExecuteUserState()%>&flag=daily">
         执行
        </a>
        
      </td>
      <%--<td width="8%">
        <%
        if(!"0".equals(tawwpMonthPlanVO.getMonthExecuteUserState())){
        %>
        <a href="../tawwpexecute/executeview.do?monthplanid=<%=tawwpMonthPlanVO.getId()%>">
          <bean:message key="dailyexecutelist.title.formView"/>
        </a>
        <%
        }
        %>
      </td>
       --%>
    
    </tr>

    <%
        }
        for(int i=0; i<stubMonthPlanList.size(); i++){
          tempHash = (Hashtable)stubMonthPlanList.get(i);
          hashKeys = tempHash.keys();
          while (hashKeys.hasMoreElements()) {
            monthPlanId = (String)(hashKeys.nextElement());
            tawwpMonthPlanVO = (TawwpMonthPlanVO)tempHash.get(monthPlanId);
    %>

    <tr>
      <td width="20%">
        <%=tawwpMonthPlanVO.getName()%>
        [<font color="red"><bean:message key="dailyexecutelist.title.formAgent"/></font>]
      </td>
       <%--
      <td width="15%">
        <%
        if(tawwpMonthPlanVO.getNetName().equals("无网元")){
      %>
        <%=tawwpMonthPlanVO.getNetTypeName()%>
      <% }else{%>
        <%=tawwpMonthPlanVO.getNetName()%>
      <% }%>
      </td>--%>
      <td width="15%">
        <%=tawwpMonthPlanVO.getDeptName()%>
      </td>
      <td width="12%">
        <%=tawwpMonthPlanVO.getYearFlag()%><bean:message key="dailyexecutelist.title.formYear"/>
        <%=tawwpMonthPlanVO.getMonthFlag()%><bean:message key="dailyexecutelist.title.formMonth"/>
      </td>
      <td width="13%">
        <%=tawwpMonthPlanVO.getPrincipal()%>
      </td>
      <td>
     <%=tawwpMonthPlanVO.getExecuteStateName()%></td>
      <td width="8%">
   
        <a href="../tawwpexecute/gxexecuteadds.do?yearPlanid=<%=tawwpMonthPlanVO.getYearPlanId()%>&monthexecuteuserid=<%=tawwpMonthPlanVO.getMonthExecuteUserId()%>&monthExecuteUserState=<%=tawwpMonthPlanVO.getMonthExecuteUserState()%>&flag=daily&userbystub=<%=tawwpMonthPlanVO.getUserByStub()%>">
        执行
        </a>
     
      </td>
      
      <%--<td width="8%">
        <%
        if(!"0".equals(tawwpMonthPlanVO.getMonthExecuteUserState())){
        %>
        <a href="../tawwpexecute/executeview.do?monthplanid=<%=tawwpMonthPlanVO.getId()%>&userbystub=<%=tawwpMonthPlanVO.getUserByStub()%>">
          <bean:message key="dailyexecutelist.title.formView"/>
        </a>
        <%
        }
        %>
      </td>
       --%>

    </tr>

    <%    }
        }
      }
      else
      {
    %>
    <tr>
      <td height="25" colspan="8">
        <bean:message key="dailyexecutelist.title.nolist"/>
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
