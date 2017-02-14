<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" import="java.sql.*"  %>
<%@ page import ="com.boco.eoms.common.tree.WKTree"%>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%> 
<%@ page import ="com.boco.eoms.common.log.BocoLog"%> 
<%@ page import ="java.util.*"%>
<%@ page import ="java.lang.*"%>
<%@ page import ="java.text.*"%>
<%@ page import ="com.boco.eoms.workplan.model.TawwpMonthExecuteUser"%>
<%@ page import ="com.boco.eoms.workplan.model.TawwpExecuteContent"%>

<table class="listTable">
 <caption>
     <bean:message key="gzquerymonthexecuser.title.labDateStart" /> <%=StaticMethod.null2String(request.getAttribute("startDategz").toString())%>
     <bean:message key="gzquerymonthexecuser.title.labDateTo" />   <%=StaticMethod.null2String(request.getAttribute("endDategz").toString())%>
 </caption>
 <thead>
 <tr>
    <td width="10%"> <bean:message key="gzquerymonthexecuser.title.formPlanName" /></td>
    <td width="10%"> <bean:message key="gzquerymonthexecuser.title.formPlanType" /></td>
    <td width="10%"> <bean:message key="gzquerymonthexecuser.title.formStartDate" /></td>
    <td width="10%"> <bean:message key="gzquerymonthexecuser.title.formEndDate" /></td>
    <td width="10%"> <bean:message key="gzquerymonthexecuser.title.formExecuteUser" /></td>
    <td width="8%"><bean:message key="gzquerymonthexecuser.title.formAllCounts" /></td>
    <td width="10%"> <bean:message key="gzquerymonthexecuser.title.formInTimeCount" /></td>
    <td width="10%"> <bean:message key="gzquerymonthexecuser.title.formOutTimeCount" /></td>
    <td width="8%"> <bean:message key="gzquerymonthexecuser.title.formUnexecutedCounts" /></td>
    <td width="8%"> <bean:message key="gzquerymonthexecuser.title.formView" /></td>
  </tr>
  </thead>
  <tbody>

          <%
            //要用到的变量 (局部变量在具体的方法里面)
           String monthPlanId="",startDategz="",endDategz="",executer="";
            //从提交页面获取参数
           monthPlanId=StaticMethod.null2String(request.getAttribute("monthPlanId").toString());
           startDategz=StaticMethod.null2String(request.getAttribute("startDategz").toString());
           endDategz=StaticMethod.null2String(request.getAttribute("endDategz").toString());
           executer=StaticMethod.null2String(request.getAttribute("executer").toString());
           %>
           
		  <% if(request.getAttribute("monthExecuteuserId")!=null){   
          List monthExecuteuserId=(List)request.getAttribute("monthExecuteuserId");
          List anshi=(List)request.getAttribute("anshi");
          List chaoshi=(List)request.getAttribute("chaoshi");
          List weizhixing=(List)request.getAttribute("weizhixing");
          List monthexecuteuserdetail=(List)monthExecuteuserId.get(3); 
          List montplanId = (List)monthExecuteuserId.get(2); 
          List monthType = (List)monthExecuteuserId.get(1); 
          String type = StaticMethod.null2String((String)monthType.get(1)); 
          String typeName = "";
          if(!"".equals(type)){
          if("0".equals(type)){
          	typeName = "人员";
          }else if("1".equals(type)){
          	typeName = "部门";
          }else if ("2".equals(type)){
          	typeName = "组";
          }else if("3".equals(type)){
          	typeName = "机房";
          }
          }
          %>
            <tr>
            <td width="10%"><%= monthexecuteuserdetail.get(3)%></td>
            <td width="10%"><%=typeName %></td>
            <td width="10%"><%=startDategz.substring(0,10)%></td>
            <td width="10%"><%=endDategz.substring(0,10)%></td>
            <td width="10%"><%=executer %></td>
            <td width="10%"><%=monthType.size() %></td>
            <td width="10%"><%=anshi.size() %></td>
            <td width="10%"><%=chaoshi.size() %></td>
            <td width="10%"><%=weizhixing.size() %></td>
            <td width="9%">
            <a href="../tawwpexecute/executeviewstat.do?monthplanid=<%=montplanId.get(2).toString()%>&startDategz=<%=startDategz%>&endDategz=<%=endDategz%>">
             <bean:message key="gzquerymonthexecuser.title.formView" /> 
            </a>
            </td>
            </tr>
            <%
             
      }
            %>
  </tbody>
</table>
<%@ include file="/common/footer_eoms.jsp"%>
