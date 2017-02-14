<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<logic:present name="tableList" scope="request">

<div>
<table id="sheet" class="formTable">
     	<thead>
	 <tr >
	 <logic-el:present name="headList">
	 <c:forEach items="${headList}"  var="headlist" >
	 <td class="label">
	 ${headlist}
	 </td >
	 
	 </c:forEach>
	 
     </logic-el:present>
     </tr>
     </thead>

     <logic-el:present name="tableList">
     <tbody>
     <c:forEach items="${tableList}" var="tdList">
     <tr>
     <c:forEach items="${tdList}" var="td">
    <c:if test="${td.show}">
     <td rowspan="${td.rowspan} }">
  
   <c:if test="${!empty td.url}">
     <a href="javascript:void(0);" onclick="window.open('${app}${td.url}');">
     	<img src="${app }/images/icons/table.gif">
     </a>
     </c:if>
     <c:if test="${empty td.url}">
      ${td.name}
     </c:if>
    
     </td>
      </c:if>
     </c:forEach>
     </tr>
     </c:forEach>
	  </tbody>
	  </logic-el:present>
 </table>
</div></logic:present>
<%@ include file="/common/footer_eoms.jsp"%>