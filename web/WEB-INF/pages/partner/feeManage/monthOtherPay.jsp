<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form action="${app}/partner/feeManage/monthOtherPay.do?method=add2"
	method="post" id="filterForm" name="filterForm">
			<input type="hidden" name="feePoolMainId" id="feePoolMainId"  value="${feePoolMain.id}"/>
			<input type="hidden" name="areaId" id="areaId"  value="${feePoolMain.region}"/>
			<input type="hidden" name="compId" id="compId"  value="${feePoolMain.company}"/>
			<input type="hidden" name="majorId" id="majorId"  value="${feePoolMain.special}"/>

<div id="div">
<logic:empty name="passList" scope="request">
     <span style="font-weight:bold;color:#FF0000">
       不存在任何本月审批完成的其他费用申请！ (注：此处显示在本月审批通过的费用申请)<br/>
    </span>
 </logic:empty>
<logic:notEmpty name="passList" scope="request">
    <span style="font-weight:bold;color:#FF0000">
        审批通过的费用申请明细:
    </span>
    <display:table name="passList" cellspacing="0" cellpadding="0"
		id="listEL"  class="table list" 
		export="false"  partialList="true" size="${fn:length(passList)}">
		<display:column  title="申请单编号">
		    <a  href="${app}/partner/feeManage/feeApplication.do?method=goToFeeApplicationView&feeApplicationId=${listEL.feeAplId}"
				target="view" shape="rect"> 
				${listEL.aplBillCd }
			</a>
			<input type="hidden" name="feeAplId" value="${listEL.feeAplId}"/>
			<input type="hidden" name="aplBillCd" value="${listEL.aplBillCd}"/>
		</display:column>		
		<display:column  title="申请人">
		     <eoms:id2nameDB id="${listEL.applicant}" beanId="tawSystemUserDao" />
		     <input type="hidden" name="creatorId" value="${listEL.applicant}"/>
		</display:column>	
		<display:column title="申请时间">
		  ${listEL.crDtTm}
		  <input type="hidden" name="creatdtTm" value="${listEL.crDtTm}" readOnly/>
		</display:column>
		<display:column property="status" title="状态"  />	
		<display:column  title="作用年" >
		  ${listEL.year}
		  <input type="hidden" name="year" value="${listEL.year}" readOnly/>	
		</display:column>
		<display:column  title="作用月">
		  ${listEL.month}
		  <input type="hidden" name="month" value="${listEL.month}"  readOnly/>	
		</display:column>
		<display:column  title="费用类型" >
		 ${listEL.costTypeNm} 
		 <input type="hidden" name="costType" value="${listEL.costTypeCd}" />	
		</display:column>
		<display:column  title="款项依据" >
		   ${listEL.costVoucher}
		   <textarea name="costVoucher" value="${listEL.costVoucher}" readOnly style="display:none;" />${listEL.costVoucher}</textarea>
		</display:column>	
		<display:column  title="应付款">
		   ${listEL.mnyAmt}
		   <input type="hidden" name="shdMnyAmt" value="${listEL.mnyAmt}" readOnly/>	
		</display:column>
		<display:column  title="实付款">
		   <input type="text" class="text" name="realMnyAmt" alt="allowBlank:false"/>	
		</display:column>
		
    </display:table>               
</logic:notEmpty>    

</div>

<logic:empty name="notPassList" scope="request">
  <logic:notEmpty name="passList" scope="request">
     <input type="button" value="提交" onclick="submitForm()" />
  </logic:notEmpty>
</logic:empty>
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