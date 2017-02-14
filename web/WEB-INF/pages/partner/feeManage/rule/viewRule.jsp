<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" 
 import="java.util.*,com.boco.eoms.partner.feeManage.util.*" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 <table id="sheet" class="formTable">
		<tr>
		    <td class="label">
				<font color='red'> * </font>规则名称
			</td>
			<td>
				 ${feeRule.name}
			</td>
		
			<td class="label">
				备注
			</td>
			<td class="content">
				<pre>${feeRule.remark}</pre>
			</td>
		</tr>
		<tr>
		  <td colspan="4">
		    <span style="color:red;">
		      <font style="font-weight:bold;">计算公式说明:</font><br/>
		       计算公式由字母和数字的四则运算表达式组成，字母表示变量。<br/>
		       考核费用的计算公式，最多含有两个变量，A:当月全额代维费用;B:月度考核得分.<br/>
		      例如：A*(1-(95-B)*1.2/100)
		    </span>
		  </td>
		</tr>		
	</table>
    <br/>
   
    <table id="formulaTable" name="formulaTable" class="table protocolMainList">	   
      <thead>
	    <tr>
	      <th rowspan="1" colspan="1">左边限 </th>
	      <th rowspan="1" colspan="1">右边限</th>  
	      <th rowspan="1" colspan="1">公式</th>  
	    </tr>
     </thead>
     <tbody>	        
	       <c:forEach items="${feeFormulaList}"  var="feeFormula" >
	         <tr>
	           <td >${feeFormula.minscore }</td>
		       <td >${feeFormula.maxscore }</td>
		       <td >${feeFormula.expression }</td>
	        </tr>
	      </c:forEach>
	  </tbody>    
	</table>        
<%@ include file="/common/footer_eoms.jsp"%>