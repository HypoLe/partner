<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrActCityBudgetAmountForm'});
});

Ext.onReady(function(){
	//地域树
    new xbox({
		btnId:'cityName',
		treeDataUrl:'${app}/xtree.do?method=area&areaMaxLevel=2',treeRootId:'28',treeRootText:'全省',treeChkType:'area',
		showChkFldId:'cityName',saveChkFldId:'cityId',returnJSON:false
	});
})
</script>
<script type="text/javascript">
	
	function validateUnique(){
	
		//判断地市是否为空
		var tempCityId=document.getElementById("cityId").value;
		if(tempCityId==""||tempCityId==null){
			alert("地市不能为空");
			return false;
		}
		
		//判断年份是否为空
		var tempBudgetYear=document.getElementById("budgetYear").value;
		if(tempBudgetYear==""||tempBudgetYear==null){
			alert("年份不能为空");
			return false;
		}
		
		//判断季度是否为空
		var tempBudgetQuarter=document.getElementById("budgetQuarter").value;
		if(tempBudgetQuarter==""||tempBudgetQuarter==null){
			alert("季度不能为空");
			return false;
		}
		
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
			}
		}
		
	  //校验唯一性
      var tempCityName=document.getElementById("cityName").value;
      var urlStr = "${app}/activiti/pnrTransferPrecheck/pnrActCityBudgetAmount.do?method=validateUnique";
      jq.ajax({
			type : "POST",
			url : urlStr, 
			data : {"cityId": tempCityId,"cityName":tempCityName,"budgetYear":tempBudgetYear,"budgetQuarter":tempBudgetQuarter},
			dataType:"json",
			success : function(data){ 
					if(data.result=="success"){
        				//alert("进入成功了吗？");
        				jq("#pnrActCityBudgetAmountForm").submit();
        			}else{
        				alert(data.content);
        			}  
			}	
		});
	}
</script>


<html:form action="/pnrActCityBudgetAmount.do?method=savePnrActCityBudgetAmount"  styleId="pnrActCityBudgetAmountForm" method="post"> 
<html:hidden property="id" value="${pnrActCityBudgetAmount.id}" />

<table class="formTable middle">
	<caption>
		<div class="header center">地市预算金额新增</div>
	</caption>

	<tr>
		<td class="label">
			地市*
		</td>
		<td class="content">
			<html:text property="cityName" styleId="cityName"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${pnrActCityBudgetAmount.cityName}" />
			<html:hidden property="cityId" styleId="cityId" value="${pnrActCityBudgetAmount.cityId}"/>
		</td>
	</tr>

	<tr>
		<td class="label">
				年份*
			</td>
			<td class="content">
				<eoms:dict key="dict-partner-inspect" selectId="budgetYear"
					dictId="yearflag" beanId="selectXML" cls="select" />
			</td>
	</tr>
	
	<tr>
		<td class="label">
			季度*
		</td>
		<td class="content">
			<select id="budgetQuarter" name="budgetQuarter" style="width: 150px;">
				<option value="">请选择</option>
				<option value="01">第一季度</option>
				<option value="04">第二季度</option>
				<option value="07">第三季度</option>
				<option value="010">第四季度</option>
			</select>				
		</td>
	</tr>
	
	<tr>
		<td class="label">
			金额*
		</td>
		<td class="content">
		<input type="text" id="budgetAmount" name="budgetAmount" value="${pnrActCityBudgetAmount.budgetAmount}" style="width: 150px;"/>		
		</td>
	</tr>

	

</table>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="保存" onclick="validateUnique();" />
		</td>
	</tr>
</table>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>