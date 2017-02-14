<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
  <link rel="stylesheet" type="text/css" href="${app}/scripts/ext3/resources/css/ext-all.css" /> 
  <script type="text/javascript" src="${app}/scripts/ext3/adapter/ext/ext-base.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext3/ext-all.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext/source/locale/ext-lang-zh_CN.js"></script>
  
  <script type="text/javascript" src="${app}/scripts/form/Options.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/combobox.js"></script>
  <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/validation.js"></script>
<%@ include file="/common/body.jsp"%>
<%@page import="java.util.List"%>
<script type="text/javascript">
  var app="${app}" ;  
</script>

 <div>
 <table  cellpadding="0" class="table protocolMainList" cellspacing="0">
   <thead>
	 <tr>
	   <c:forEach items="${headList}"  var="headList" >
	     <c:if test="${headList.show}">
	      <th rowspan="${headList.rowspan}" colspan="${headList.colspan}"> ${headList.name} </th>  
	     </c:if>
	   </c:forEach>
	 </tr>
   </thead>
   <tbody>
     <c:forEach items="${templateLlt}"  var="tdList">
     <tr>
       <c:forEach items="${tdList}" var="td">
         <c:if test="${td.show}">
          <c:if test="${td.datatype=='template' or td.datatype=='xiangmu'}">
                <td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name}(${td.proportion}分) </td>
          </c:if>
          <c:if test="${td.datatype=='zhuanye'}">
                <td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name}-专业(100.0) </td>
           </c:if>
           <c:if test="${td.datatype=='programme' or td.datatype=='indicator'}">
              <td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name}(${td.proportion}%) </td>
           </c:if>
           <c:if test="${td.datatype=='indicatorextra'}"><td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name} </td></c:if>
         </c:if>
       </c:forEach>
     </tr>
     </c:forEach> 
   </tbody>  
 </table>
</div>
</div>


<%@ include file="/common/footer_eoms.jsp"%>