<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" 
 import="java.util.*,com.boco.eoms.partner.feeManage.util.PrcFilter"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  
<form>
  
  <logic:notPresent name="monthEvaluEntity" scope="request">
  <font style="color:red;">没有记录</font>
  </logic:notPresent>
  
  <logic:present name="monthEvaluEntity" scope="request">
     <fieldset>
        <legend>计量详情-模板:${monthEvaluEntity.ownfeeprctmp}</legend>
        <c:set var="cnt" value="1"/>    
	    <table id="prcdtltbl" name="prcdtltbl" class="table protocolMainList">
	     <thead>
	       <tr> 
	           <th>序号</th>
	           <th>专业</th>
	         <c:forEach items="${prcFilterList}"  var="prcFilter" varStatus="status">
	           <th>
	            ${prcFilter.text}
               </th>
	         </c:forEach>
	          <th>单价</th>
	          <th>数量<font color="red">*</font></th>
	       </tr>	       
	     </thead>
	     <tbody id="prcdtltbody" name="prcdtltbody">	   
	          <c:forEach items="${dtlentityList}" var="dtlentity" varStatus="status">
	            <tr>
	              <td name="num">${status.count}</td> 
	              <td><eoms:id2nameDB id='${dtlentity.majorid}' beanId='ItawSystemDictTypeDao' /></td> 
	              <c:forEach items="${prcFilterList}" var="prcFilter">
	                   <td>
	                     <!-- 属性值来源于字典，id2nameDB -->
	                     <c:if test="${prcFilter.dictYN}">	                       	                      
	                       <eoms:id2nameDB id="${dtlentity[prcFilter['businessName']]}" beanId="ItawSystemDictTypeDao" />	                                          
	                     </c:if>
	                     <!-- 属性值来源于数据表,直接显示 -->
	                     <c:if test="${prcFilter.dictYN==false}">
	                       ${dtlentity[prcFilter['businessName']]}		                                                  
	                     </c:if>
	                   </td> 
	                </c:forEach>
	                <td>${dtlentity.dtlprc}</td>
	                <td>${dtlentity.dtlaamt}
	                </td> 
	            </tr>
	          </c:forEach>  
	          <tr>
	            <td colspan="${(fn:length(prcFilterList)) + 2 + 2 - 2 }"></td>
	            <td>金额总计</td>
	            <td>${amtTotalDouble}</td>
	          </tr>
	     </tbody>
	  </table>   
      </fieldset>
      
      <br/>   
    <!-- 显示考核费用需填写的信息 -->
      <TABLE class="formTable">
        <tr>	    
            <td class="label">考核得分<font color="red">*</font></td>
            <td class="content">${monthEvaluEntity.evalscore}</td>  
			   
			<td class="label"> 扣款（元）<font color="red">*</font> </td>   
			<td class="content">${monthEvaluEntity.reducemnyamt }</td>			 		  	 			
	    </tr>   
	    <tr>	    
            <td class="label">应付款（元）</td>
			<td class="content">${monthEvaluEntity.shdmnyamt }</td>   
			   
			<td class="label">实付款（元）</td>
			<td class="content">${monthEvaluEntity.realmnyamt }</td>	 		  	 			
	    </tr>  
	    <!--  <tr>	    
            <td class="label">费用数据确认人<font color="red">*</font></td>
			<td class="content">	
			   <eoms:id2nameDB id="kf10086" beanId="tawSystemUserDao" />
			</td>   
			   				 		  	 			
	    </tr>  -->
	    <tr>
	      <td class="label">备注</td>
	      <td colspan="3">
	        <pre>${monthEvaluEntity.remark }</pre>
	      </td>
	    </tr> 
      </TABLE>
   </logic:present>   
</form>

<%@ include file="/common/footer_eoms.jsp"%>