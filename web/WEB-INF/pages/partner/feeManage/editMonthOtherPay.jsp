<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form action="${app}/partner/feeManage/monthOtherPay.do?method=add2"
	method="post" id="filterForm" name="filterForm">
			<input type="hidden" name="feePoolMainId" id="feePoolMainId"  value="${feePoolMain.id}"/>
			<input type="hidden" name="parentOtherPayId" id="parentOtherPayId"  value="${parentOtherPayId}"/>
			<input type="hidden" name="areaId" id="areaId"  value="${feePoolMain.region}"/>
			<input type="hidden" name="compId" id="compId"  value="${feePoolMain.company}"/>
			<input type="hidden" name="year" id="year"  value="${feePoolMain.feeYear}"/>
			<input type="hidden" name="month" id="month" " value="${feePoolMain.feeMonth}"/>
			<input type="hidden" name="majorId" id="majorId"  value="${feePoolMain.special}"/>
			
<div id="div">
<logic:notEmpty name="list" scope="request">
    <span style="font-weight:bold;color:#FF0000">
        费用申请明细:
    </span>
    <display:table name="list" cellspacing="0" cellpadding="0"
		id="listEL"  class="table list" 
		export="false"  partialList="true" size="${fn:length(list)}">
		<display:column  title="申请单编号">
		    <a  href="${app}/partner/feeManage/feeApplication.do?method=goToFeeApplicationView&feeApplicationId=${listEL.feeAplId}"
				target="view" shape="rect"> 
				${listEL.aplBillCd }
			</a>
			<input type="hidden" name="feeAplId" value="${listEL.feeAplId}"/>
			<input type="hidden" name="aplBillCd" value="${listEL.aplBillCd}"/>
		</display:column>		
		<display:column  title="申请人">
		     <eoms:id2nameDB id="${listEL.creatorId}" beanId="tawSystemUserDao" />
		     <input type="hidden" name="creatorId" value="${listEL.creatorId}"/>
		</display:column>	
		<display:column title="申请时间">
		  ${listEL.creatdtTm}
		  <input type="hidden" name="creatdtTm" value="${listEL.creatdtTm}"  readOnly/>
		</display:column>		
		<display:column title="状态" >	
		   通过<!--编辑时都是已经通过了的申请费用  -->
		</display:column>
		<display:column  title="作用年" >
		  ${listEL.year}
		  <input type="hidden" name="year" value="${listEL.year}"  readOnly/>	
		</display:column>
		<display:column  title="作用月">
		  ${listEL.month}
		  <input type="hidden" name="month" value="${listEL.month}"  readOnly/>	
		</display:column>
		<display:column  title="费用类型" >
		  <input type="hidden" name="costType" value="${listEL.costType}" />	
		  <c:if test="${listEL.costType eq '1'}">奖励</c:if>
		  <c:if test="${listEL.costType eq '2'}">补贴</c:if>
		  <c:if test="${listEL.costType eq '3'}">报销</c:if>
		  <c:if test="${listEL.costType eq '4'}">其他</c:if>
		</display:column>
		<display:column  title="款项依据" >
		   ${listEL.costVoucher}
		   <textarea name="costVoucher" value="${listEL.costVoucher}" readOnly style="display:none" />${listEL.costVoucher}</textarea>
		</display:column>	
		<display:column  title="应付款">
		   ${listEL.shdMnyAmt}
		   <input type="hidden" name="shdMnyAmt" value="${listEL.shdMnyAmt}" readOnly/>	
		</display:column>
		<display:column  title="实付款">
		   <input type="text" class="text" name="realMnyAmt" alt="allowBlank:false" value="${listEL.realMnyAmt }"/>	
		</display:column>
		
    </display:table>               
</logic:notEmpty>    
</div>

<br/>
<input type="button" value="提交" onclick="submitForm()" />
</form>


<script type="text/javascript">
window.onload=function(){
   var v = new eoms.form.Validation({form:"filterForm"});
   v.custom = function(){
     return true;
   }
}


  function notNumber(value){
  	   	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var value=value; 
   		if(value.match(price) && ""!=value){
   			return false;
      	}else{
           	return true;
      	}
  }

function submitForm() {
	try {		 
		var realMnyAmts = document.getElementsByName("realMnyAmt");
		if (realMnyAmts && realMnyAmts.length > 0) {
			for (var i = 0; i < realMnyAmts.length; i++) {
				if (notNumber(realMnyAmts[i].value.trim())
						|| parseInt(realMnyAmts[i].value.trim()) <= 0) {
					realMnyAmts[i].focus();
					alert("实付款必须是大于0的数字，最多两位小数");
					return false;
				}
			}
		}
	} catch (e) {
		return false;
	}

	if (!confirm("确认要提交？")) {
		return false; 
	}
	document.getElementById("filterForm").submit();
}

</script>
<%@ include file="/common/footer_eoms.jsp"%>