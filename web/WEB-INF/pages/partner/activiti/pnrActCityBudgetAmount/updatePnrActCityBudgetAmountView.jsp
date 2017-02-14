<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
	
	function updateBudgetAmount(){
		//判断金额是否为空
		var tempBudgetAmount=document.getElementById("budgetAmount").value;
		if(tempBudgetAmount==""||tempBudgetAmount==null){
			alert("金额不能为空");
			return false;
		}else{
			var a=/^(\d+)(\.\d+)?$/;
			if(!a.test(tempBudgetAmount)){
			alert("项目金额估算请输入整数或小数");
		//	document.getElementById("budgetAmount").value=""; 
			return false;
			}else{
				//alert("OK");
				jq("#pnrActCityBudgetAmountForm").submit();
			}
		}
	}
</script>


<html:form action="/pnrActCityBudgetAmount.do?method=saveUpdatePnrActCityBudgetAmount"  styleId="pnrActCityBudgetAmountForm" method="post"> 
<html:hidden property="id" value="${pnrActCityBudgetAmount.id}" />

<table class="formTable middle">
	<caption>
		<div class="header center">地市预算金额修改</div>
	</caption>

	<tr>
		<td class="label">
			地市*
		</td>
		<td class="content">
			${pnrActCityBudgetAmount.cityName}
		</td>
	</tr>

	<tr>
		<td class="label">
				年份*
			</td>
			<td class="content">
				${pnrActCityBudgetAmount.budgetYear}
			</td>
	</tr>
	
	<tr>
		<td class="label">
			季度*
		</td>
		<td class="content">
			<c:if test="${pnrActCityBudgetAmount.budgetQuarter eq '01'}">
				第一季度
			</c:if>
			<c:if test="${pnrActCityBudgetAmount.budgetQuarter eq '04'}">
				第二季度
			</c:if>
			<c:if test="${pnrActCityBudgetAmount.budgetQuarter eq '07'}">
				第三季度
			</c:if>
			<c:if test="${pnrActCityBudgetAmount.budgetQuarter eq '10'}">
				第四季度
			</c:if>				
		</td>
	</tr>
	
	<tr>
		<td class="label">
			金额*
		</td>
		<td class="content">
		<input type="text" id="budgetAmount" name="budgetAmount" value="<fmt:formatNumber value="${pnrActCityBudgetAmount.budgetAmount}" pattern="##.##" maxFractionDigits="8" minFractionDigits="0"/>"  style="width: 150px;"/>		
		</td>
	</tr>

	

</table>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="更新" onclick="updateBudgetAmount();"/>
		</td>
	</tr>
</table>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>