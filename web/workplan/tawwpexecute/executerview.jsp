<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.Hashtable"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpMonthPlanVO"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpMonthExecuteVO"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpExecuteContentVO"%>
<%@ page import ="com.boco.eoms.workplan.util.TawwpUtil"%>

<%
  TawwpMonthPlanVO tawwpMonthPlanVO = (TawwpMonthPlanVO)request.getAttribute("tawwpmonthplanvo");
  String firstDay = tawwpMonthPlanVO.getYearFlag() + "-" + tawwpMonthPlanVO.getMonthFlag() + "-"
                  + "01 00:00:00";
  String monthFlag = tawwpMonthPlanVO.getYearFlag() + tawwpMonthPlanVO.getMonthFlag();
  int week = TawwpUtil.getWeek(firstDay)-1;
  String dayFlag = TawwpUtil.getCurrentDateTime("yyyyMMdd");
  int day = Integer.parseInt(dayFlag);
  int dayCount = TawwpUtil.getDayCountOfMonth(tawwpMonthPlanVO.getYearFlag(),tawwpMonthPlanVO.getMonthFlag());
  TawwpMonthExecuteVO tawwpMonthExecuteVO = null;
  Hashtable monthExecuteVOHash = tawwpMonthPlanVO.getMonthExecuteVOHash();
  Hashtable executeContentVOHash = null;
  String tempStr = "";
  String currUser = (String)request.getAttribute("curruser");
  String userId = "";
  if("1".equals(tawwpMonthPlanVO.getStubFlag())){
    userId = tawwpMonthPlanVO.getUserByStub();
  }
  else{
    userId = currUser;
  }
  TawwpExecuteContentVO tawwpExecuteContentVO = null;
%>
<script language="JavaScript">
Ext.onReady(function(){
	colorRows('listTable');
})

function GoBack()
{
  location.href="../tawwpexecute/dailyexecutelist.do";
}

function onExecuteAdd(_executeContentId)
{
  var monthPlanId = "<%=tawwpMonthPlanVO.getId()%>";
  var userByStub = "<%=tawwpMonthPlanVO.getUserByStub()%>";
  location.href="../tawwpexecute/executeadd.do?executecontentid=" + _executeContentId + "&monthplanid=" + monthPlanId + "&userbystub=" + userByStub;
}

function onExecuteEdit(_executeContentId)
{
  var monthPlanId = "<%=tawwpMonthPlanVO.getId()%>";
  var executeType = "<%=tawwpMonthPlanVO.getExecuteType()%>";
  var userByStub = "<%=tawwpMonthPlanVO.getUserByStub()%>";
  location.href="../tawwpexecute/executeedit.do?executecontentid=" + _executeContentId + "&monthplanid=" + monthPlanId + "&executetype=" + executeType + "&userbystub=" + userByStub;
}
</script>

<!--  body begin  -->
<br>
<table class="listTable" id="listTable">

  <thead>

      <%=tawwpMonthPlanVO.getYearFlag()%><bean:message key="executerview.title.labYear" />
      <%=tawwpMonthPlanVO.getMonthFlag()%><bean:message key="executerview.title.labMonth" />
      &lt;&nbsp;<%=tawwpMonthPlanVO.getName()%>&nbsp;&gt;
      <%
          if("1".equals(tawwpMonthPlanVO.getStubFlag())){
      %>
      <bean:message key="executerview.title.labAgent" />
      <%
          }
      %>

  </thead>
  <tbody>
  <tr>
    <td width="20%" rowspan="2" height="36"><bean:message key="executerview.title.formContent" /></td>
    <td width="5%" rowspan="2" height="36"><bean:message key="executerview.title.formCycle" /></td>
    <td width="3%" height="16"><bean:message key="executerview.title.formCount" /></td>
    <%
      for(int i=1; i<(dayCount+1); i++){
    %>
    <td width="3%" height="16">
      <%
        if("1".equals(tawwpMonthPlanVO.getStubFlag())){
      %>
      <a href="../tawwpexecute/executeadds.do?monthplanid=<%=tawwpMonthPlanVO.getId()%>&flag=daily&userbystub=<%=tawwpMonthPlanVO.getUserByStub()%>&date=<%=i%>"><%=i%></a>
      <%
        }else{
      %>
      <a href="../tawwpexecute/executeadds.do?monthplanid=<%=tawwpMonthPlanVO.getId()%>&flag=daily&date=<%=i%>"><%=i%></a>
      <%
        }
      %>
    </td>
    <%
      }
    %>
  </tr>
  
  
  <tr>
    
    <td width="3%" height="14"><bean:message key="executerview.title.formWeek" /></td>
     
    <%
      for(int j=1; j<(dayCount+1); j++){
        if(week == 7){
    %>
    <td width="3%" height="16">
      <bean:message key="executerview.title.formDay" />
    </td>
    <%
        week = 1;
        }
        else
        {
    %>
    <td width="3%" height="16">
      <%=week%>
    </td>
    <%
        week++;
        }
      }
    %>
  </tr>
  <%
    List monthExecuteVOList = tawwpMonthPlanVO.getMonthExecuteVOList();
    for(int k=0; k<monthExecuteVOList.size(); k++){
      tawwpMonthExecuteVO = (TawwpMonthExecuteVO)monthExecuteVOList.get(k);
      executeContentVOHash = (Hashtable)monthExecuteVOHash.get(tawwpMonthExecuteVO);
  %>
  <tr>
    
    <td width="20%" rowspan="2" height="38">
      <%=tawwpMonthExecuteVO.getName()%>
    </td>
    <td width="5%" rowspan="2" height="38">
      <%=tawwpMonthExecuteVO.getCycleName()%>
    </td>
    <td width="3%" height="16"><bean:message key="executerview.title.formCount" /></td>
    <%
      char[] temp = (tawwpMonthExecuteVO.getExecuteDate()).toCharArray();
      for(int x=1; x<(temp.length+1); x++){
        if(temp[x-1] == '1'){
          if(x<10){
            tempStr = tawwpMonthExecuteVO.getId()+"0"+ String.valueOf(x);
          }else{
            tempStr = tawwpMonthExecuteVO.getId()+String.valueOf(x);
          }
          //System.out.println("调试信息：tempStr="+tempStr);
          tawwpExecuteContentVO = (TawwpExecuteContentVO)(executeContentVOHash.get(tempStr));
          if(tawwpExecuteContentVO!=null){
	          if ("1".equals(tawwpExecuteContentVO.getExecuteFlag())) {
	              if ("0".equals(tawwpMonthPlanVO.getExecuteType()) ||
	               ( ("," + tawwpExecuteContentVO.getCruser() + ",").indexOf("," +
	               userId + ",") >= 0)) {
	    %>
	    <td width="3%" height="16">
	      <a href="javascript:onExecuteEdit('<%=tawwpExecuteContentVO.getId()%>');">
	        <img src="${app }/images/icons/yes.gif">
	      </a>
	    </td>
	    <%
	              }
	              else
	              {
	    %>
	    <td width="3%" height="16">
	      <a href="javascript:onExecuteAdd('<%=tawwpExecuteContentVO.getId()%>');">
	        <img src="${app }/images/icons/yes.gif">
	      </a>
	    </td>
	    <%
	              }
	          }else if ("2".equals(tawwpExecuteContentVO.getExecuteFlag())){
	             if ("0".equals(tawwpMonthPlanVO.getExecuteType()) ||
	               ( ("," + tawwpExecuteContentVO.getCruser() + ",").indexOf("," +
	               userId + ",") >= 0)) {
	    %>
	    <td width="3%" height="16">
	      <a href="javascript:onExecuteEdit('<%=tawwpExecuteContentVO.getId()%>');">
	        <img src="${app }/images/icons/no.gif">
	      </a>
	    </td>
	    <%
	              }
	              else
	              {
	    %>
	    <td width="3%" height="16">
	      <a href="javascript:onExecuteAdd('<%=tawwpExecuteContentVO.getId()%>');">
	        <img src="${app }/images/icons/no.gif">
	      </a>
	    </td>
	    <%
	              }
	          }
	          else
	          {
	    %>
	    <td width="3%" height="16">
	      <a href="javascript:onExecuteAdd('<%=tawwpExecuteContentVO.getId()%>');">
	        <img src="${app }/images/icons/yes.gif">
	      </a>
	    </td>
	    <%
	         }
	      }else{
%>	      	<td width="3%" height="16">
		     </td> 
<%	      }
        }else{
    %>
    <td width="3%" height="16">
    </td>
    <%
        }
      }
    %>
  </tr>
  <tr class="complete">
    <td width="3%" height="14"><bean:message key="executerview.title.formExecute" /></td>
    <%
    String xx = "";
      for(int x=1; x<(dayCount+1); x++){
          if(x<10){
          xx = "0"+ String.valueOf(x);
            tempStr = tawwpMonthExecuteVO.getId()+"0"+ String.valueOf(x);
          }else{
          xx = String.valueOf(x);
            tempStr = tawwpMonthExecuteVO.getId()+String.valueOf(x);
          }

          tawwpExecuteContentVO = (TawwpExecuteContentVO)(executeContentVOHash.get(tempStr));
          if(tawwpExecuteContentVO!=null){
            if (!"0".equals(tawwpExecuteContentVO.getExecuteFlag())) {
              if ("0".equals(tawwpMonthPlanVO.getExecuteType()) ||
               ( ("," + tawwpExecuteContentVO.getCruser() + ",").indexOf("," +
               userId + ",") >= 0)) {
    %>
  <!--   <td width="3%" height="16" class="icon"><!-- 就这里 -->
 <!--   
    </td> -->
    
    <td> <font color="green"><%=x%></font></td>
    
    <%
              }
              else
              {
     %>
     <td width="3%" height="16">
     </td>
     <%
            }
          }
          else 
          if(Integer.parseInt(monthFlag + xx)<day){
          
          %>
          <td width="3%" height="16"><img src="${app }/images/icons/nodetype/empty.gif"> </td>
          <%
          }else
          {
     %>
     <td width="3%" height="16">
     </td>
     <%
          }
        }
        else
        {
    %>
    <td width="3%" height="16">
     </td>
    <%
        }
      }
    %>
  </tr>
  <%
    }
  %>
  </tbody>
</table>
<br>
<INPUT type=button value="<bean:message key="executerview.title.btnBack" />"  name="button" Onclick="GoBack();" class="button">

<!--  body end   -->

<%@ include file="/common/footer_eoms.jsp"%>



