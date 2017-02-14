<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
</script>

<script type="text/javascript">
//不带序号
jq(function(){
	jq("#addPnrActCountyBudgetAmountList tbody tr ").find("td:eq(3)").click(function(){
		//alert(jq(this).parent().find("td:eq(0) input:hidden").val());
		var pare=jq(this).parent().find("td:eq(0) input:hidden");
		var td = jq(this); 
		var txt = td.text(); 
		var input = jq("<input type='text' style='width:100%;' value='" + txt + "'/>"); 
		td.html(input); 
		input.click(function() { return false; }); 
		
		//获取焦点 
		input.trigger("focus"); 
		
		
		
		//文本框失去焦点后提交内容，重新变为文本 <p></p>
		input.blur(function() {
		    var expr = new RegExp("^[0-9]\\d*(\\.\\d+)?$");
			var newtxt = jq(this).val(); 
			//alert("newtxt="+newtxt);
			if (newtxt != txt) {
				if(newtxt==''){
					alert("地市金额不可以为空!");
					td.html(txt); 
					return;
				}
				if(!expr.test(formatStringToFloatNew(newtxt))){
					//alert(newtxt);
					alert("地市金额请输入整数或小数");
					td.html(txt);
				}else{
					if(checkBudgetAmountSum(newtxt)){
						//判断文本有没有修改
						jq.ajax({
							type:"POST",
							url:"${app}/activiti/pnrTransferPrecheck/pnrActCityBudgetAmount.do?method=updateCountyBudgetAmountByAjax",
							data:{"id":pare.val(),"budgetAmount":newtxt},
							dataType:"json",
							success:function(data){
							if(data.result=="success"){
								   alert(data.content);
								   td.html(newtxt);
								}else{
								   alert(data.content);
								   td.html(txt);
								}                           			
							}
						});
					}else{
						alert("区县总金额超出地市金额，请重新修改！");
						td.html(txt);
					}
				}
			} 
		else 
		{ 
			td.html(newtxt); 
		} 	
	}); 	
  });
});

function checkBudgetAmountSum(val){
	var cityBudgetAmount = jq("#cityBudgetAmount").val();
	var sum = 0;
	//alert(jq("#addPnrActCountyBudgetAmountList tbody tr ").find("td:eq(3)").length);
	jq("#addPnrActCountyBudgetAmountList tbody tr ").find("td:eq(3)").each(function(){
		//alert(jq(this).text());
		sum += formatStringToFloatNew(jq(this).text())
	});
	sum += formatStringToFloatNew(val);
	//alert("sum="+sum);	
	if(sum <= cityBudgetAmount){
		return true;
	}else{
		return false;
	}
}

function formatStringToFloatNew(strValue){
	if(strValue!=""){
		return parseFloat(strValue);
	}else{
		return 0;
	}	
}
</script>

<input type="hidden" id="cityBudgetAmount" name="cityBudgetAmount" value="${cityBudgetAmount}" />

<display:table name="addPnrActCountyBudgetAmountList" cellspacing="0" cellpadding="0"
		id="addPnrActCountyBudgetAmountList" pagesize="" class="table addPnrActCountyBudgetAmountList"
		export="false"
		requestURI="${app}/activiti/pnrTransferPrecheck/pnrActCityBudgetAmount.do?method=selectAddPnrActCountyBudgetAmountList"
		sort="list" partialList="true" size="${total}">
		<display:column sortable="false" headerClass="sortable" title="区县" >
			<input type="hidden" value="${addPnrActCountyBudgetAmountList.id}" />
			${addPnrActCountyBudgetAmountList.countyName}
		</display:column>
		<display:column  sortable="false" headerClass="sortable" title="年份">
			${budgetYear}
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="季度">
			<c:if test="${budgetQuarter eq '01'}">
				第一季度
			</c:if>
			<c:if test="${budgetQuarter eq '04'}">
				第二季度
			</c:if>
			<c:if test="${budgetQuarter eq '07'}">
				第三季度
			</c:if>
			<c:if test="${budgetQuarter eq '10'}">
				第四季度
			</c:if>
		</display:column>
		<display:column  sortable="false" headerClass="sortable" title="金额" style="width:200px;height:20px;">
		<fmt:formatNumber value="${addPnrActCountyBudgetAmountList.budgetAmount}" pattern="##.##" maxFractionDigits="8" minFractionDigits="0"/>		
		</display:column>
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>