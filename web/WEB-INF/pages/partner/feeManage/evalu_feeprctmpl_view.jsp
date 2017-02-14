<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" 
 import="java.util.*,com.boco.eoms.partner.feeManage.util.PrcFilter"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<form action="${app}/partner/feeManage/evaluFee.do?method=saveOrUpdatePrcTmpl&opertyp=update" method="post" id="prctmplForm" name="prctmplForm">  
    <input type="hidden" name="prctmplid" value="${prctmplid}"/>
    <table id="sheet1" class="formTable">
        <caption>
           <div class="header center">考核费用单价模板</div>
         </caption>
        <tr>
	        <td class="label">
				模板名称<font color='red'> * </font>
			</td>
			<td>${prctmpl.prctmplnm}</td>	    
			<td class="label">
				专业
			</td>
			<td><eoms:id2nameDB id="${prctmpl.majorid}" beanId="ItawSystemDictTypeDao" />
			    <input type="hidden" id="majorid" name="majorid" value="${prctmpl.majorid}"/>
			</td>	     	 			
	    </tr>    
	    <tr>	       		
			<td class="label">
				区域
			</td>
			<td><eoms:id2nameDB id='${prctmpl.areaid}' beanId='tawSystemAreaDao'/>
				<input type="hidden" id="area" name="area" value="${prctmpl.areaid}"/>
			</td>	   
			<td class="label">代维公司</td>
			<td class="content"><eoms:id2nameDB id='${prctmpl.compid}' beanId='partnerDeptDao'/>
				<input type="hidden" id="comp" name="comp" value="${prctmpl.compid}"/>
			</td>   	  	
	    </tr>
	    <tr>
	      <logic:notEmpty name="feeRule" scope="request">
	        <td class="label">所使用的费用计算规则</td>
	        <td class="content">
			  <a  href="${app}/partner/feeManage/feeRule.do?method=viewRule&id=${feeRule.id}"
				target="view2" shape="rect"> 
				 ${feeRule.name}
              </a>
	        </td>
	      </logic:notEmpty>
	      
	      <td class="label">备注</td>
	      <td class="content">
			<pre> ${prctmpl.remark}</pre>
	      </td>
	    <tr> 
    </table>
   
    <!-- 单价明细 -->   
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
	       </tr>	       
	     </thead>
	     <tbody id="prcdtltbody" name="prcdtltbody">	   
	          <c:forEach items="${dtlprctmplList}" var="dtlprc" varStatus="status">
	            <tr>
	              <td name="num">${status.count}</td> <td><eoms:id2nameDB id='${prctmpl.majorid}' beanId='ItawSystemDictTypeDao' /></td> 
	              <c:forEach items="${prcFilterList}" var="prcFilter">
	                   <td>
	                     <!-- 属性值来源于字典 -->
	                     <c:set var="dictname" value="${prcFilter.businessName}dict"/>
	                     <c:if test="${prcFilter.dictYN}">	                       	                      
	                       <eoms:id2nameDB id="${dtlprc[prcFilter['businessName']]}" beanId="ItawSystemDictTypeDao" />                     
	                     </c:if>
	                     <!-- 属性值来源于数据表 -->
	                     <c:set var="tablename" value="${prcFilter.businessName}table"/>
	                     <c:set var="colname" value="${prcFilter.businessName}col"/>
	                     <c:if test="${prcFilter.dictYN==false}">
	                       ${dtlprc[prcFilter['businessName']]}		                      	                        
	                     </c:if>
	                   </td> 
	                </c:forEach>
	                <td>${dtlprc.dtlprc}</td>	                
	            </tr>
	          </c:forEach>  
	     </tbody>
	  </table>     	        
</form>
  
<%@ include file="/common/footer_eoms.jsp"%>

